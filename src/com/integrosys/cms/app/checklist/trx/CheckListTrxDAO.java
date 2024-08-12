/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/CheckListTrxDAO.java,v 1.1 2006/07/20 01:45:26 wltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

import java.sql.ResultSet;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for checklist transaction.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/07/20 01:45:26 $ Tag: $Name: $
 */
public class CheckListTrxDAO {

	private DBUtil dbUtil;

	private static final String SELECT_TRX_SUBTYPE = "SELECT trx.transaction_subtype, chklist.CATEGORY"
			+ "  FROM stage_checklist chklist, TRANSACTION trx" + " WHERE trx.transaction_type = 'CHECKLIST'   "
			+ "   AND trx.staging_reference_id = chklist.checklist_id" + "   AND trx.transaction_id = ? ";

	/**
	 * Default Constructor
	 */
	public CheckListTrxDAO() {
	}

	/**
	 * Get transaction sub-type and checklist category for a checklist
	 * transaction.
	 * 
	 * @param aTrxID - primitive long denoting the checklist transaction
	 * @return result[0] - String denoting the transaction sub-type
	 * @return result[1] - String denoting the checklist category
	 * @throws SearchDAOException
	 */
	public String[] getTrxSubTypeAndCheckListCategoryByTrxID(long aTrxID) throws SearchDAOException {
		if (aTrxID == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_TRX_SUBTYPE);
			dbUtil.setLong(1, aTrxID);
			ResultSet rs = dbUtil.executeQuery();
			String trxSubType = null;
			String chklistCategory = null;
			if (rs.next()) {
				trxSubType = rs.getString(1);
				chklistCategory = rs.getString(2);
			}
			rs.close();
			return new String[] { trxSubType, chklistCategory };
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting trx sub type for transaction id : " + aTrxID, e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources.");
		}
	}
}
