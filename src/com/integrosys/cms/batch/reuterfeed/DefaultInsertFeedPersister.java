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
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultInsertFeedPersister implements IFeedPersister {
	private boolean allFields = false;

	private List selectedFields;

	private boolean autoGenKey = true;

	private String keyColumn;

	private String keySequence;

	private String insertQuery;

	private List paramList;

	public DefaultInsertFeedPersister() {
		paramList = new ArrayList();
	}

	private void formInsertQuery(String tableName, Map currentFeedLine) throws Exception {
		paramList.clear();
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(tableName);
		sb.append("(");
		if (autoGenKey) {
			sb.append(keyColumn);
			paramList.add(new SequenceManager().getSeqNum(keySequence, true));
			sb.append(",");
		}
		Collection col = currentFeedLine.values();
		Iterator iter = col.iterator();
		while (iter.hasNext()) {
			FeedFieldDef nextField = (FeedFieldDef) (iter.next());
			String colName = nextField.getColumnName();
			String fieldName = nextField.getFieldName();
			if (checkRequireAddField(fieldName, colName)) {
				sb.append(colName);
				sb.append(",");
				paramList.add(nextField.getFieldValue());
			}
		}
		int lastInd = sb.length() - 1;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		sb.append(") VALUES(");
		for (int i = 0; i < paramList.size(); i++) {
			sb.append("?,");
		}
		lastInd = sb.length() - 1;
		if (sb.charAt(lastInd) == ',') {
			sb.deleteCharAt(lastInd);
		}
		sb.append(")");
		insertQuery = sb.toString();
		DefaultLogger.debug(this, insertQuery);
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
	 * @return Returns the autoGenKey.
	 */
	public boolean isAutoGenKey() {
		return autoGenKey;
	}

	/**
	 * @param autoGenKey The autoGenKey to set.
	 */
	public void setAutoGenKey(boolean autoGenKey) {
		this.autoGenKey = autoGenKey;
	}

	/**
	 * @return Returns the keyColumn.
	 */
	public String getKeyColumn() {
		return keyColumn;
	}

	/**
	 * @param keyColumn The keyColumn to set.
	 */
	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	/**
	 * @return Returns the keySequence.
	 */
	public String getKeySequence() {
		return keySequence;
	}

	/**
	 * @param keySequence The keySequence to set.
	 */
	public void setKeySequence(String keySequence) {
		this.keySequence = keySequence;
	}

	/**
	 * @return Returns the selectedColumns.
	 */
	public List getSelectedFields() {
		return selectedFields;
	}

	/**
	 * @param selectedColumns The selectedColumns to set.
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
			formInsertQuery(tableName, currentFeedLine);
			if (paramList.size() > 0) {
				dbUtil = new DBUtil();
				dbUtil.setSQL(insertQuery);
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
