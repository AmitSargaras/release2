/*
 * Created on May 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedHistoryPersister implements ICustomizedFeedPersister {

	private String PROP_KEY_FEED_IDCOL = "feedidcol";

	private String PROP_KEY_HIST_TBNAME = "histtbname";

	private String PROP_KEY_HIST_KEYCOL = "histkeycol";

	private String PROP_KEY_HIST_KEYSEQ = "histkeyseq";

	private String PROP_KEY_UNIT_PRICFD = "unitpricefield";

	private Map properties;

	private List histDataList;

	private List selectParamList;

	private List insertParamList;

	private List updateParamList;

	private String[] quarterLastTradingDate = { "03/31", "06/30", "09/30", "12/31" };

	public FeedHistoryPersister() {
		histDataList = new ArrayList();
		selectParamList = new ArrayList();
		insertParamList = new ArrayList();
		updateParamList = new ArrayList();
	}

	/**
	 * @return Returns the properties.
	 */
	public Map getProperties() {
		return properties;
	}

	/**
	 * @param properties The properties to set.
	 */
	public void setProperties(Map properties) {
		this.properties = properties;
	}

	private String formSelectQuery(String tableName, Map currentFeedLine) {
		histDataList.clear();
		selectParamList.clear();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		Collection col = currentFeedLine.values();
		Iterator iter = col.iterator();

		while (iter.hasNext()) {
			FeedFieldDef nextField = (FeedFieldDef) (iter.next());
			String colName = nextField.getColumnName();
			String colType = nextField.getFieldType();
			if (colName != null) {
				sb.append(colName);
				sb.append(", ");
				Object[] arr = new Object[3];
				arr[0] = colName;
				arr[1] = colType;
				histDataList.add(arr);
			}
		}
		int lastInd = sb.length() - 2;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		sb.append(" FROM ");
		sb.append(tableName);
		sb.append(" WHERE ");

		String conditionStr = formConditions(currentFeedLine, selectParamList);
		sb.append(conditionStr);
		DefaultLogger.debug(this, sb.toString());
		return sb.toString();
	}

	private String formConditions(Map currentFeedLine, List paramList) {
		String keyColNames = (String) properties.get(PROP_KEY_FEED_IDCOL);
		List keyCols = parseKeyColNames(keyColNames);
		Collection col = currentFeedLine.values();
		Iterator iter = col.iterator();
		StringBuffer conditions = new StringBuffer();

		while (iter.hasNext()) {
			FeedFieldDef nextField = (FeedFieldDef) (iter.next());
			String colName = nextField.getColumnName();
			Object colVal = nextField.getFieldValue();
			if (checkIsKeyCol(keyCols, colName)) {
				conditions.append(colName);
				conditions.append(" = ? AND ");
				paramList.add(colVal);
			}
		}
		String conditionStr = conditions.toString();
		if (conditionStr.endsWith("AND ")) {
			conditionStr = conditionStr.substring(0, conditionStr.length() - 4);
		}
		return conditionStr;
	}

	private List parseKeyColNames(String keyColNames) {
		List l = new ArrayList();
		StringTokenizer st = new StringTokenizer(keyColNames, ",");
		while (st.hasMoreTokens()) {
			l.add(st.nextToken());
		}
		return l;
	}

	private boolean checkIsKeyCol(List keyColList, String colName) {
		for (int i = 0; i < keyColList.size(); i++) {
			String nextKeyCol = (String) (keyColList.get(i));
			if (nextKeyCol.equalsIgnoreCase(colName)) {
				return true;
			}
		}
		return false;
	}

	private String formInsertQuery(String tableName, Map currentFeedLine) throws Exception {
		insertParamList.clear();
		StringBuffer sb = new StringBuffer();
		String histTableName = (String) properties.get(PROP_KEY_HIST_TBNAME);
		String histKeyCol = (String) properties.get(PROP_KEY_HIST_KEYCOL);
		String histKeySeq = (String) properties.get(PROP_KEY_HIST_KEYSEQ);
		sb.append("INSERT INTO ");
		sb.append(histTableName);
		sb.append("(");

		sb.append(histKeyCol);
		insertParamList.add(new SequenceManager().getSeqNum(histKeySeq, true));
		sb.append(",");

		Collection col = currentFeedLine.values();
		Iterator iter = col.iterator();
		while (iter.hasNext()) {
			FeedFieldDef nextField = (FeedFieldDef) (iter.next());
			String colName = nextField.getColumnName();
			String fieldName = nextField.getFieldName();
			if (colName != null) {
				sb.append(colName);
				sb.append(",");
				insertParamList.add(nextField.getFieldValue());
			}
		}
		int lastInd = sb.length() - 1;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		sb.append(") VALUES(");
		for (int i = 0; i < insertParamList.size(); i++) {
			sb.append("?,");
		}
		lastInd = sb.length() - 1;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		sb.append(")");
		DefaultLogger.debug(this, sb.toString());
		return sb.toString();
	}

	private String formUpdateQuery(String tableName, Map currentFeedLine) throws Exception {
		updateParamList.clear();
		StringBuffer sb = new StringBuffer();
		Date currentDate = new Date();
		sb.append("UPDATE ");
		sb.append(tableName);
		sb.append(" SET LAST_UPDATED_DATE = ?, ");
		updateParamList.add(currentDate);
		sb.append(" PREV_DAY_PRICE = UNIT_PRICE, ");
		if (isLastTradingDayOfQuarter(currentDate)) {
			sb.append(" PREV_QUARTER_PRICE = ? ");
			String unitPriceFieldName = (String) (properties.get(PROP_KEY_UNIT_PRICFD));
			FeedFieldDef unitPriceField = (FeedFieldDef) (currentFeedLine.get(unitPriceFieldName));
			updateParamList.add(unitPriceField.getFieldValue());
		}
		int lastInd = sb.length() - 2;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		sb.append(" WHERE ");
		String conditionStr = formConditions(currentFeedLine, updateParamList);
		sb.append(conditionStr);
		DefaultLogger.debug(this, sb.toString());
		return sb.toString();
	}

	private boolean isLastTradingDayOfQuarter(Date d) {
		try {
			SimpleDateFormat sdc = new SimpleDateFormat("MM/dd");
			String dateFormat = sdc.format(d);
			for (int i = 0; i < quarterLastTradingDate.length; i++) {
				if (quarterLastTradingDate[i].equals(dateFormat)) {
					return true;
				}
			}
		}
		catch (Exception ex) {
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedPersister#performPersistence
	 * (java.lang.String, java.util.Map)
	 */
	public void performPersistence(String tableName, Map currentFeedLine) throws Exception {
		// TODO Auto-generated method stub
		try {
			retrieveCurrentFeedRecord(tableName, currentFeedLine);
			updateTimestampPrevDayPrevQuarterPrice(tableName, currentFeedLine);
			insertIntoFeedHistory(tableName, currentFeedLine);
		}
		catch (Exception ex) {
			throw ex;
		}
	}

	private void retrieveCurrentFeedRecord(String tableName, Map currentFeedLine) throws Exception {
		String query = formSelectQuery(tableName, currentFeedLine);
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			FeedUtil.setParams(selectParamList, dbUtil);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				for (int i = 0; i < histDataList.size(); i++) {
					Object[] arr = (Object[]) (histDataList.get(i));
					String colType = (String) (arr[1]);
					if (FeedConfiguration.FIELD_TYPE_STRING.equalsIgnoreCase(colType)) {
						arr[2] = rs.getString(i + 1);
					}
					else if (FeedConfiguration.FIELD_TYPE_INTEGER.equalsIgnoreCase(colType)) {
						arr[2] = new Integer(rs.getString(i + 1));
					}
					else if (FeedConfiguration.FIELD_TYPE_LONG.equalsIgnoreCase(colType)) {
						arr[2] = new Long(rs.getString(i + 1));
					}
					else if (FeedConfiguration.FIELD_TYPE_FLOAT.equalsIgnoreCase(colType)) {
						arr[2] = new Float(rs.getString(i + 1));
					}
					else if (FeedConfiguration.FIELD_TYPE_DOUBLE.equalsIgnoreCase(colType)) {
						arr[2] = new Double(rs.getString(i + 1));
					}
					else if (FeedConfiguration.FIELD_TYPE_DATE.equalsIgnoreCase(colType)) {
						arr[2] = rs.getDate(i + 1);
					}
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (Exception ex) {
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception ex) {
			}
		}
	}

	private void updateTimestampPrevDayPrevQuarterPrice(String tableName, Map currentFeedLine) throws Exception {
		String updateQry = formUpdateQuery(tableName, currentFeedLine);
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(updateQry);
			FeedUtil.setParams(updateParamList, dbUtil);
			dbUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception ex) {
			}
		}
	}

	private void insertIntoFeedHistory(String tableName, Map currentFeedLine) throws Exception {
		String insertQry = formInsertQuery(tableName, currentFeedLine);
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(insertQry);
			FeedUtil.setParams(insertParamList, dbUtil);
			dbUtil.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception ex) {
			}
		}
	}

}
