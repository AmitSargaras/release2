package com.integrosys.cms.app.checklist.bus;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.eventmonitor.IMonitorDAOResult;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Jul 21, 2003 Time:
 * 5:17:39 PM To change this template use Options | File Templates.
 */
public class CheckListItemMonitorResultWrapper implements IMonitorDAOResult {
	ResultSet rs;

	DBUtil dbUtil;

	OBCheckListItem ob;

	public CheckListItemMonitorResultWrapper(ResultSet rs, DBUtil dbUtil) {
		this.dbUtil = dbUtil;
		this.rs = rs;
	}

	public boolean hasNextElement() {
		// todo
		try {
			processResultSet();
			if (this.ob != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (SQLException e) {
			// todo log the monitor exception appropriately...
		}
		return false;
	}

	public Object getElement() {
		return this.ob;
	}

	public void close() {
		try {
			dbUtil.close();
		}
		catch (SQLException e) {
			// todo log the monitor exception appropriately...
		}
	}

	/**
	 * Process the check list item search result
	 * @param rs - ResultSet
	 * @throws SQLException if errors
	 */
	private void processResultSet() throws SQLException {
		ob = null;
		if ((rs != null) && rs.next()) {
			ob = new OBCheckListItem();
			ob.setCheckListItemID(rs.getLong(ICheckListItemDAO.DOC_ITEM_NO));
			ob.setCheckListItemDesc(rs.getString(ICheckListItemDAO.DOC_DESCRIPTION));
			ob.setCheckListItemRef(rs.getLong(ICheckListItemDAO.DOC_ITEM_REF));
			ob.setExpiryDate(rs.getDate(ICheckListItemDAO.EXPIRY_DATE));
			// item.setIsAuditInd(rs.getString(IS_AUDIT));
			// item.setIsExtCustInd();
			// item.setIsInVaultInd();
			// item.setIsMandatoryInd();
			// item.setItemStatus();
		}
	}

}
