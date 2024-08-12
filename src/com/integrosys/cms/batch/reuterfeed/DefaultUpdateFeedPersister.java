/*
 * Created on May 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultUpdateFeedPersister implements IFeedPersister {
	private boolean allFields = false;

	private List selectedFields;

	private List conditionFields;

	private String updateQuery;

	private List paramList;

	public DefaultUpdateFeedPersister() {
		paramList = new ArrayList();
	}

	private void formUpdateQuery(String tableName, Map currentFeedLine) throws Exception {
		paramList.clear();
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append(tableName);
		sb.append(" SET ");
		Collection col = currentFeedLine.values();
		Iterator iter = col.iterator();
		while (iter.hasNext()) {
			FeedFieldDef nextField = (FeedFieldDef) (iter.next());
			String colName = nextField.getColumnName();
			String fieldName = nextField.getFieldName();
			if (checkRequireAddField(fieldName, colName)) {
				sb.append(colName);
				sb.append("=?, ");
				paramList.add(nextField.getFieldValue());
			}
		}
		int lastInd = sb.length() - 2;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		if (conditionFields.size() > 0) {
			sb.append(" WHERE ");
			for (int j = 0; j < conditionFields.size(); j++) {
				String nextConditionName = (String) (conditionFields.get(j));
				FeedFieldDef nextField = (FeedFieldDef) (currentFeedLine.get(nextConditionName));
				String colName = nextField.getColumnName();
				sb.append(colName);
				sb.append(" = ? AND ");
				paramList.add(nextField.getFieldValue());
			}
			updateQuery = sb.toString();
			int newLen = updateQuery.length() - 4;
			if (updateQuery.endsWith("AND ")) {
				updateQuery = updateQuery.substring(0, newLen);
			}
		}
		DefaultLogger.debug(this, updateQuery);
	}

	private boolean checkRequireAddField(String fieldName, String colName) {
		if (colName == null) {
			return false;
		}
		else {
			if (allFields) {
				return true;
			}
			else {
				return selectedFields.contains(fieldName);
			}
		}
	}

	/**
	 * @return Returns the allFields.
	 */
	public boolean isAllFields() {
		return allFields;
	}

	/**
	 * @param allFields The allFields to set.
	 */
	public void setAllFields(boolean allFields) {
		this.allFields = allFields;
	}

	/**
	 * @return Returns the conditionFields.
	 */
	public List getConditionFields() {
		return conditionFields;
	}

	/**
	 * @param conditionFields The conditionFields to set.
	 */
	public void setConditionFields(List conditionFields) {
		this.conditionFields = conditionFields;
	}

	/**
	 * @return Returns the selectedFields.
	 */
	public List getSelectedFields() {
		return selectedFields;
	}

	/**
	 * @param selectedFields The selectedFields to set.
	 */
	public void setSelectedFields(List selectedFields) {
		this.selectedFields = selectedFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedPersister#performPersistence
	 * (java.util.Map)
	 */
	public void performPersistence(String tableName, Map currentFeedLine) throws Exception {
		// TODO Auto-generated method stub
		DBUtil dbUtil = null;
		try {
			formUpdateQuery(tableName, currentFeedLine);
			if (paramList.size() > 0) {
				dbUtil = new DBUtil();
				dbUtil.setSQL(updateQuery);
				FeedUtil.setParams(paramList, dbUtil);
				dbUtil.executeUpdate();
			}
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
