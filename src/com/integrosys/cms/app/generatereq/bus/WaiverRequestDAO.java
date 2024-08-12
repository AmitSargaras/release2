/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/WaiverRequestDAO.java,v 1.3 2004/10/15 10:52:07 wltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * DAO for SCC
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/10/15 10:52:07 $ Tag: $Name: $
 */

public class WaiverRequestDAO implements IWaiverRequestDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_WAV_REQ_TRX = null;

	private static String SELECT_WAV_REQ_TRX_COUNT = null;

	static {
		StringBuffer strBuf = new StringBuffer();
		strBuf = new StringBuffer();
		strBuf.append("SELECT COUNT(*) ");
		strBuf.append(" FROM ");
		strBuf.append(STAGE_WAIVER_REQ_TABLE);
		strBuf.append(", ");
		strBuf.append(TRX_TABLE);
		strBuf.append(" WHERE ");
		strBuf.append(TRX_TYPE_PREF);
		strBuf.append(" = '");
		strBuf.append(ICMSConstant.INSTANCE_WAIVER_REQ);
		strBuf.append("' AND ");
		strBuf.append(TRX_STAGE_REF_ID_PREF);
		strBuf.append(" = ");
		strBuf.append(STAGE_WAVTBL_WAIVER_ID_PREF);
		SELECT_WAV_REQ_TRX_COUNT = strBuf.toString();
	}

	/**
	 * To get the number of waiver request that satisfy the criteria
	 * @param aCriteria of WaiverRequestSearchCriteria type
	 * @return int - the number of waiver request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws SearchDAOException {
		String searchString = getStagingSearchString(aCriteria);
		String sql = SELECT_WAV_REQ_TRX_COUNT;

		if (searchString != null) {
			sql = sql + " AND " + searchString;
		}
		DefaultLogger.debug(this, "SQL: " + sql);
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getNoOfWaiverRequest", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfWaiverRequest", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfWaiverRequest", ex);
			}
		}
	}

	private String getStagingSearchString(WaiverRequestSearchCriteria aCriteria) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		boolean sqlAppended = false;
		if (aCriteria.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			buf.append(STAGE_WAVTBL_LIMIT_PROFILE_ID_PREF);
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
