/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/DeferralRequestDAO.java,v 1.3 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;

/**
 * DAO for SCC
 * @author $Author: czhou $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */

public class DeferralRequestDAO implements IDeferralRequestDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_DEF_REQ_TRX_COUNT = "SELECT COUNT(*)  FROM STAGE_DEFERRAL_GENERATED, TRANSACTION WHERE "
			+ "TRANSACTION.TRANSACTION_TYPE = 'DEFER_REQ' AND "
			+ "TRANSACTION.STAGING_REFERENCE_ID = STAGE_DEFERRAL_GENERATED.DEFERRAL_ID";

	/**
	 * To get the number of deferral that satisfy the criteria
	 * @param aCriteria of DeferralRequestSearchCriteria type
	 * @return int - the number of Deferral request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria) throws SearchDAOException {
		String searchString = getStagingSearchString(aCriteria);
		String sql = SELECT_DEF_REQ_TRX_COUNT;

		if (searchString != null) {
			sql = sql + " AND " + searchString;
		}
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getNoOfDeferralRequest", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfDeferralRequest", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfDeferralRequest", ex);
			}
		}
	}

	private String getStagingSearchString(DeferralRequestSearchCriteria aCriteria) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		boolean sqlAppended = false;
		if (aCriteria.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(STAGE_DEFTBL_LIMIT_PROFILE_ID_PREF);
			buf.append(" = ");
			buf.append(aCriteria.getLimitProfileID());
			sqlAppended = true;
		}

		if ((aCriteria.getTrxStatusList() != null) && (aCriteria.getTrxStatusList().length > 0)) {
			String[] trxStatus = aCriteria.getTrxStatusList();
			if (sqlAppended) {
				buf.append(" AND ");
			}
			buf.append(TRX_STATUS_PREF);
			buf.append(" IN (");
			for (int ii = 0; ii < trxStatus.length; ii++) {
				if (ii > 0) {
					buf.append(", ");
				}
				buf.append("'");
				buf.append(trxStatus[ii]);
				buf.append("'");
			}
			buf.append(")");
			sqlAppended = true;
		}
		return buf.toString();
	}

	/**
	 * Utilty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		return true;
	}
}
