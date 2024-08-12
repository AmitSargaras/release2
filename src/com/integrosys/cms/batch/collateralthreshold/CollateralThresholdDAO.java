/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/collateralthreshold/CollateralThresholdDAO.java,v 1.26 2006/02/16 08:26:04 lini Exp $
 */
package com.integrosys.cms.batch.collateralthreshold;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.RMConnectionManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAOConstants;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAOConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
 * DAO for collateral threshold.
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.26 $
 * @since $Date: 2006/02/16 08:26:04 $ Tag: $Name: $
 */
public class CollateralThresholdDAO implements ICollateralDAOConstants, ILimitDAOConstant {
	// private final static String QUERY_GET_THRESHOLD_COLLATERAL =
	// "SELECT DISTINCT CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID, CMS_CHARGE_DETAIL.CMS_COLLATERAL_ID FROM CMS_SECURITY, SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, CMS_LIMIT_CHARGE_MAP, CMS_CHARGE_DETAIL WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CHARGE_ID = CMS_LIMIT_CHARGE_MAP.CHARGE_ID AND CMS_LIMIT_CHARGE_MAP.STATUS <> 'DELETED' AND CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID AND SCI_LSP_APPR_LMTS.CMS_REQ_SEC_COVERAGE IS NOT NULL"
	// ;
	private final static String QUERY_GET_THRESHOLD_COLLATERAL = "SELECT DISTINCT cms_charge_detail.charge_detail_id, "
			+ "                cms_charge_detail.cms_collateral_id "
			+ "           FROM cms_security, "
			+ "                sci_lsp_appr_lmts, "
			+ "                cms_limit_security_map, "
			+ "                cms_limit_charge_map, "
			+ "                cms_charge_detail, "
			+ "                sci_lsp_lmt_profile lmtprof "
			+ "          WHERE sci_lsp_appr_lmts.cms_lsp_appr_lmts_id = "
			+ "                                   cms_limit_security_map.cms_lsp_appr_lmts_id "
			+ "            AND cms_limit_security_map.charge_id = "
			+ "                                                cms_limit_charge_map.charge_id "
			+ "            AND cms_limit_charge_map.status <> 'DELETED' "
			+ "            AND cms_limit_charge_map.charge_detail_id = "
			+ "                                            cms_charge_detail.charge_detail_id "
			+ "            AND cms_limit_security_map.cms_collateral_id = "
			+ "                                                cms_security.cms_collateral_id "
			+
			// For General Charge we have to check Limit Profile Req Security
			// Coverage
			// For Specific Charge we have to check Limit level Req Security
			// Coverage
			// "            AND sci_lsp_appr_lmts.cms_req_sec_coverage IS NOT NULL "
			// +
			// Not checking for Required Security Coverage Null as per Kui Lin's
			// mail dated 15-2-2006
			// "            AND ( " +
			// " 			     sci_lsp_appr_lmts.cms_req_sec_coverage IS NOT NULL " +
			// " 			 OR  lmtprof.cms_req_sec_coverage  IS NOT NULL " +
			// " 			    ) " +
			"            AND lmtprof.cms_lsp_lmt_profile_id = sci_lsp_appr_lmts.cms_limit_profile_id "
			+ "            AND lmtprof.cms_lsp_lmt_profile_id in "
			+
			// Added condition for checking if PSCC or SCC has been issued
			"           (SELECT lmtprof.cms_lsp_lmt_profile_id "
			+ "              FROM TRANSACTION trx, cms_pscc_generated pscc, sci_lsp_lmt_profile lmtprof "
			+ "             WHERE trx.transaction_type LIKE 'PSCC' " + "               AND trx.status <> 'CLOSED' "
			+ "               AND trx.reference_id = pscc.pscc_id "
			+ "               AND pscc.cms_lsp_lmt_profile_id = lmtprof.cms_lsp_lmt_profile_id "
			+ "           UNION   " + "            SELECT lmtprof.cms_lsp_lmt_profile_id  "
			+ "              FROM TRANSACTION trx, cms_scc_generated scc, sci_lsp_lmt_profile lmtprof "
			+ "             WHERE trx.transaction_type LIKE 'SCC'  " + "               AND trx.status <> 'CLOSED'  "
			+ "               AND trx.reference_id = scc.scc_id  "
			+ "               AND scc.cms_lsp_lmt_profile_id = lmtprof.cms_lsp_lmt_profile_id ) ";

	// private final static String QUERY_GET_THRESHOLD_LIMIT_PROFILE =
	// "SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_REQ_SEC_COVERAGE IS NOT NULL AND CMS_REQ_SEC_COVERAGE > 0"
	// ;
	// Not checking for Required Security Coverage Null as per Kui Lin's mail
	// dated 15-2-2006
	private final static String QUERY_GET_THRESHOLD_LIMIT_PROFILE = "SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE "
			+ // WHERE CMS_REQ_SEC_COVERAGE IS NOT NULL AND CMS_REQ_SEC_COVERAGE
				// > 0" +
			// Added condition for checking if PSCC or SCC has been issued
			// "               AND cms_lsp_lmt_profile_id in " +
			"             WHERE cms_lsp_lmt_profile_id in "
			+ "           (SELECT lmtprof.cms_lsp_lmt_profile_id "
			+ "              FROM TRANSACTION trx, cms_pscc_generated pscc, sci_lsp_lmt_profile lmtprof "
			+ "             WHERE trx.transaction_type LIKE 'PSCC' "
			+ "               AND trx.status <> 'CLOSED' "
			+ "               AND trx.reference_id = pscc.pscc_id "
			+ "               AND pscc.cms_lsp_lmt_profile_id = lmtprof.cms_lsp_lmt_profile_id "
			+ "             UNION   "
			+ "            SELECT lmtprof.cms_lsp_lmt_profile_id "
			+ "              FROM TRANSACTION trx, cms_scc_generated scc, sci_lsp_lmt_profile lmtprof "
			+ "             WHERE trx.transaction_type LIKE 'SCC' "
			+ "               AND trx.status <> 'CLOSED' "
			+ "               AND trx.reference_id = scc.scc_id "
			+ "               AND scc.cms_lsp_lmt_profile_id = lmtprof.cms_lsp_lmt_profile_id) ";

	// private final static String QUERY_GET_THRESHOLD_LIMIT =
	// "SELECT DISTINCT SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID FROM CMS_SECURITY, SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, CMS_LIMIT_CHARGE_MAP, CMS_CHARGE_DETAIL WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CHARGE_ID = CMS_LIMIT_CHARGE_MAP.CHARGE_ID AND CMS_LIMIT_CHARGE_MAP.STATUS <> 'DELETED' AND CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID AND CMS_CHARGE_DETAIL.CHARGE_TYPE = 'S' AND SCI_LSP_APPR_LMTS.CMS_REQ_SEC_COVERAGE IS NOT NULL AND SCI_LSP_APPR_LMTS.CMS_REQ_SEC_COVERAGE > 0"
	// ;
	private final static String QUERY_GET_THRESHOLD_LIMIT = "         SELECT DISTINCT sci_lsp_appr_lmts.cms_lsp_appr_lmts_id "
			+ "           FROM cms_security, "
			+ "                sci_lsp_appr_lmts, "
			+ "                cms_limit_security_map, "
			+ "                cms_limit_charge_map, "
			+ "                cms_charge_detail, "
			+ "                sci_lsp_lmt_profile lmtprof "
			+ "          WHERE sci_lsp_appr_lmts.cms_lsp_appr_lmts_id = "
			+ "                                   cms_limit_security_map.cms_lsp_appr_lmts_id "
			+ "            AND cms_limit_security_map.charge_id = "
			+ "                                                cms_limit_charge_map.charge_id "
			+ "            AND cms_limit_charge_map.status <> 'DELETED' "
			+ "            AND cms_limit_charge_map.charge_detail_id = "
			+ "                                            cms_charge_detail.charge_detail_id "
			+ "            AND cms_limit_security_map.cms_collateral_id = "
			+ "                                                cms_security.cms_collateral_id "
			+ "            AND cms_charge_detail.charge_type = 'S' "
			+
			// Not checking for Required Security Coverage Null as per Kui Lin's
			// mail dated 15-2-2006
			// "            AND sci_lsp_appr_lmts.cms_req_sec_coverage IS NOT NULL "
			// +
			// "            AND sci_lsp_appr_lmts.cms_req_sec_coverage > 0 " +
			"            AND lmtprof.CMS_LSP_LMT_PROFILE_ID = sci_lsp_appr_lmts.CMS_LIMIT_PROFILE_ID "
			+
			// Added condition for checking if PSCC or SCC has been issued
			"               AND lmtprof.cms_lsp_lmt_profile_id in "
			+ "           (SELECT lmtprof.cms_lsp_lmt_profile_id "
			+ "              FROM TRANSACTION trx, cms_pscc_generated pscc, sci_lsp_lmt_profile lmtprof "
			+ "             WHERE trx.transaction_type LIKE 'PSCC' "
			+ "               AND trx.status <> 'CLOSED' "
			+ "               AND trx.reference_id = pscc.pscc_id "
			+ "               AND pscc.cms_lsp_lmt_profile_id = lmtprof.cms_lsp_lmt_profile_id "
			+ "             UNION   "
			+ "            SELECT lmtprof.cms_lsp_lmt_profile_id "
			+ "              FROM TRANSACTION trx, cms_scc_generated scc, sci_lsp_lmt_profile lmtprof "
			+ "             WHERE trx.transaction_type LIKE 'SCC' "
			+ "               AND trx.status <> 'CLOSED' "
			+ "               AND trx.reference_id = scc.scc_id "
			+ "               AND scc.cms_lsp_lmt_profile_id = lmtprof.cms_lsp_lmt_profile_id) ";

	private final static String QUERY_GET_CHARGE_BY_LIMIT_PROFILE = "SELECT DISTINCT CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID, CMS_CHARGE_DETAIL.CMS_ACTUAL_CHARGE_AMOUNT FROM CMS_SECURITY, SCI_LSP_LMT_PROFILE, SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, CMS_LIMIT_CHARGE_MAP, CMS_CHARGE_DETAIL WHERE SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CHARGE_ID = CMS_LIMIT_CHARGE_MAP.CHARGE_ID AND CMS_LIMIT_CHARGE_MAP.STATUS <> 'DELETED' AND CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID AND SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = ?";

	private final static String QUERY_GET_CHARGE_BY_LIMIT = "SELECT DISTINCT CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID, CMS_CHARGE_DETAIL.CMS_ACTUAL_CHARGE_AMOUNT FROM CMS_SECURITY, SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, CMS_LIMIT_CHARGE_MAP, CMS_CHARGE_DETAIL WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CHARGE_ID = CMS_LIMIT_CHARGE_MAP.CHARGE_ID AND CMS_LIMIT_CHARGE_MAP.STATUS <> 'DELETED' AND CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID AND CMS_CHARGE_DETAIL.CHARGE_TYPE = 'S' AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = ?";

	private final static String STMT_UPDATE_CHARGE_AMT = "UPDATE CMS_CHARGE_DETAIL SET CMS_ACTUAL_CHARGE_AMOUNT = ? , CMS_ACTUAL_CHARGE_CCY = ?  WHERE CHARGE_DETAIL_ID = ?";

	private final static String QUERY_GET_CHARGE_AMT = "SELECT CMS_ACTUAL_CHARGE_AMOUNT FROM CMS_CHARGE_DETAIL WHERE CHARGE_DETAIL_ID = ?";

	private final static String QUERY_GET_COLLATERAL_MAP = "SELECT DISTINCT SCI_LSP_LMT_PROFILE.CMS_REQ_SEC_COVERAGE FROM SCI_LSP_LMT_PROFILE, SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, CMS_LIMIT_CHARGE_MAP, CMS_CHARGE_DETAIL WHERE SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID AND SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CHARGE_ID = CMS_LIMIT_CHARGE_MAP.CHARGE_ID AND CMS_LIMIT_CHARGE_MAP.STATUS <> 'DELETED' AND CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = ? AND CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID = ?";

	private final static String QUERY_GET_LIMIT_AND_PROFILE_BY_CHARGE = "SELECT DISTINCT SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID, SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID FROM SCI_LSP_APPR_LMTS, CMS_LIMIT_SECURITY_MAP, CMS_LIMIT_CHARGE_MAP WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CHARGE_ID = CMS_LIMIT_CHARGE_MAP.CHARGE_ID AND CMS_LIMIT_CHARGE_MAP.STATUS <> 'DELETED' AND CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = ?";

	// private final static String STMT_UPDATE_FSV_AMT =
	// "UPDATE CMS_SECURITY SET FSV_BALANCE = ? , FSV_BALANCE_CCY = ?  WHERE CMS_COLLATERAL_ID = ?"
	// ;
	private final static String STMT_UPDATE_STG_FSV_AMT = "UPDATE CMS_STAGE_SECURITY SET FSV_BALANCE = ? , FSV_BALANCE_CCY = ?  WHERE CMS_COLLATERAL_ID = ?";

	private final static String QUERY_GET_STG_COL_ID = "SELECT STAGING_REFERENCE_ID FROM TRANSACTION WHERE TRANSACTION_TYPE='COL' AND REFERENCE_ID = ?";

	private final static String QUERY_GET_STG_LMT_ID = "SELECT STAGING_REFERENCE_ID FROM TRANSACTION WHERE TRANSACTION_TYPE='LIMIT' AND REFERENCE_ID = ?";

	private final static String STMT_UPDATE_ACT_SEC_COV = "UPDATE SCI_LSP_APPR_LMTS SET CMS_ACT_SEC_COVERAGE = ?   WHERE CMS_LSP_APPR_LMTS_ID = ?";

	private final static String STMT_UPDATE_STG_ACT_SEC_COV = "UPDATE STAGE_LIMIT SET CMS_ACT_SEC_COVERAGE = ?   WHERE CMS_LSP_APPR_LMTS_ID = ?";

	private final static String QUERY_GET_STG_LMTPRF_ID = "SELECT STAGING_REFERENCE_ID FROM TRANSACTION WHERE TRANSACTION_TYPE='LIMITPROFILE' AND REFERENCE_ID = ?";

	private final static String STMT_UPDATE_LMT_PRF_ACT_SEC_COV = "UPDATE SCI_LSP_LMT_PROFILE SET CMS_ACT_SEC_COVERAGE = ?   WHERE CMS_LSP_LMT_PROFILE_ID = ?";

	private final static String STMT_UPDATE_LMT_PRF_STG_ACT_SEC_COV = "UPDATE STAGE_LIMIT_PROFILE SET CMS_ACT_SEC_COVERAGE = ?   WHERE CMS_LSP_LMT_PROFILE_ID = ?";

	/**
	 * Default Constructor
	 */
	public CollateralThresholdDAO() {
	}

	/**
	 * Returns an array of collateral ID, of collaterals which are eligible for
	 * threshold computation
	 * 
	 * @return Long[]
	 */
	public Long[] getThresholdCollaterals() throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			String sql = QUERY_GET_THRESHOLD_COLLATERAL;
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			ArrayList aList = new ArrayList();

			while (rs.next()) {
				aList.add(new Long(rs.getLong(2)));
			}
			if (aList.size() == 0) {
				return null;
			}
			else {
				return (Long[]) aList.toArray(new Long[0]);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getThresholdCollaterals!", e);
			throw new SearchDAOException("Caught Exception in getThresholdCollaterals", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	/**
	 * Returns an array of LimitProfile ID, of LimitProfile which are eligible
	 * for threshold computation
	 * 
	 * @return Long[]
	 */
	public Long[] getThresholdLimitProfiles() throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			String sql = QUERY_GET_THRESHOLD_LIMIT_PROFILE;
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			ArrayList aList = new ArrayList();

			while (rs.next()) {
				aList.add(new Long(rs.getLong(1)));
			}
			if (aList.size() == 0) {
				return null;
			}
			else {
				return (Long[]) aList.toArray(new Long[0]);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getThresholdLimitProfiles!", e);
			throw new SearchDAOException("Caught Exception in getThresholdLimitProfiles", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	/**
	 * Returns an array of Limit ID, of LimitProfile which are eligible for
	 * threshold computation
	 * 
	 * @return Long[]
	 */
	public Long[] getThresholdLimits() throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			String sql = QUERY_GET_THRESHOLD_LIMIT;
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			ArrayList aList = new ArrayList();

			while (rs.next()) {
				aList.add(new Long(rs.getLong(1)));
			}
			if (aList.size() == 0) {
				return null;
			}
			else {
				return (Long[]) aList.toArray(new Long[0]);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getThresholdLimits!", e);
			throw new SearchDAOException("Caught Exception in getThresholdLimits", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	/**
	 * Returns a sum of all actual charge amount given a profile ID
	 * 
	 * @return double
	 */
	public double getChargeAmountByProfile(long profileID) throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			String sql = QUERY_GET_CHARGE_BY_LIMIT_PROFILE;
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, profileID);

			rs = dbUtil.executeQuery();

			double total = 0;

			while (rs.next()) {
				double value = rs.getDouble(2);
				total += value;
			}
			return total;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getChargeAmountByProfile", e);
			throw new SearchDAOException("Caught Exception in getChargeAmountByProfile", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	/**
	 * Returns a sum of all actual charge amount given a profile ID
	 * 
	 * @return double
	 */
	public double getChargeAmountByLimit(long limitID) throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			String sql = QUERY_GET_CHARGE_BY_LIMIT;
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, limitID);

			rs = dbUtil.executeQuery();

			double total = 0;

			while (rs.next()) {
				total += rs.getDouble(2);
			}
			return total;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getChargeAmountByLimit!", e);
			throw new SearchDAOException("Caught Exception in getChargeAmountByLimit", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	/**
	 * Update the charge amount. This method assumes that the currency in DB is
	 * == to currency in HashMap, and hence no forex will be performed.
	 * 
	 * @param chargeMap is a HashMap where the Key is the Long value of the
	 *        charge detail ID, and the value is an Amount object
	 * @param reset is a boolean value, which if true, will cause the method to
	 *        update the value as seen in the Amount object into the DB. However
	 *        if this value is false, then the method will ADD the value in
	 *        HashMap into the value in DB instead of ovewriting it.
	 */
	public void updateActualChargeAmount(HashMap chargeMap, boolean reset) throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		DBUtil readUtil = null;
		ResultSet rs = null;

		try {
			if (false == reset) {
				readUtil = new DBUtil();
				String readSql = QUERY_GET_CHARGE_AMT;
				readUtil.setSQL(readSql);
			}
			Iterator i = chargeMap.keySet().iterator();
			// starts connection
			dbUtil = new DBUtil();
			String sql = STMT_UPDATE_CHARGE_AMT;
			dbUtil.setSQL(sql);

			while (i.hasNext()) {
				Long chargeLong = (Long) i.next();
				long chargeID = chargeLong.longValue();

				Amount amtObj = (Amount) chargeMap.get(chargeLong);
				double amt = amtObj.getAmount();
				if (false == reset) {
					readUtil.setLong(1, chargeID);
					rs = readUtil.executeQuery();
					if (rs.next()) {
						double prevAmt = rs.getDouble(1);
						amt += prevAmt;
						rs.close();
					}
				}
				dbUtil.setDouble(1, amt);
				dbUtil.setString(2, amtObj.getCurrencyCode());
				dbUtil.setLong(3, chargeID);
				dbUtil.executeUpdate();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			try {
				// rmCon.rollbackConnection (this);
				DefaultLogger.error(this, "Rolling back transaction!", null);
			}
			catch (Exception ee) {
				DefaultLogger.error(this, "Caught Exception while rolling back transaction!", ee);
			}
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
			finalize(readUtil, null);
		}
	}

	/**
	 * Update the HashMap given to the method with the key containing
	 * collateralID, and the value containing BCA Required Security Coverage
	 * 
	 * @param colMap is the HashMap that contains key of collateralID in Long,
	 *        and value containing Required Security Coverage as Double. If the
	 *        required key already exist in the HashMap, the same HashMap will
	 *        be returned and no queries will be made
	 * @param collateralID is the collateralID to be search
	 * @return HashMap
	 */
	public HashMap getMapByCollateral(HashMap colMap, long collateralID, long chargeDetailID) throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			Long chargeID = new Long(chargeDetailID);
			Double coverage = (Double) colMap.get(chargeID);
			if (null != coverage) {
				return colMap; // since it exist;
			}
			dbUtil = new DBUtil();
			String sql = QUERY_GET_COLLATERAL_MAP;
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralID);
			dbUtil.setLong(2, chargeDetailID);

			rs = dbUtil.executeQuery();

			if (rs.next()) {
				double value = rs.getDouble(1);
				coverage = new Double(value);
				colMap.put(chargeID, coverage);
			}
			return colMap;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getMapByCollateral!", e);
			throw new SearchDAOException("Caught Exception in getMapByCollateral", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	/**
	 * Return a hashmap where the key is limit ID, and the value is limit
	 * profile ID
	 * 
	 * @param chargeDetailID is the charge detail to be queried
	 * @return HashMap
	 */
	public HashMap getLimitAndProfileByCharge(long chargeDetailID) throws SearchDAOException {
		RMConnectionManager rmCon = null;
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();
			String sql = QUERY_GET_LIMIT_AND_PROFILE_BY_CHARGE;
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, chargeDetailID);

			HashMap hmap = new HashMap();

			rs = dbUtil.executeQuery();
			while (rs.next()) {
				long limitID = rs.getLong(1);
				long profileID = rs.getLong(2);

				hmap.put(new Long(limitID), new Long(profileID));
			}
			return hmap;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in getLimitAndProfileByCharge!", e);
			throw new SearchDAOException("Caught Exception in getLimitAndProfileByCharge", e);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	public void updateSTGFSVAmount(ICollateral col, Amount colAmt) throws SearchDAOException {

		DBUtil dbUtil = null;
		DBUtil readUtil = null;
		ResultSet rs = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			readUtil = new DBUtil();
			String readSql = QUERY_GET_STG_COL_ID;
			String sql = STMT_UPDATE_STG_FSV_AMT;
			readUtil.setSQL(readSql);
			dbUtil.setSQL(sql);
			long colID = col.getCollateralID();
			readUtil.setLong(1, colID);
			rs = readUtil.executeQuery();
			if (rs.next()) {
				long stgColID = rs.getLong(1);
				rs.close();
				dbUtil.setDouble(1, colAmt.getAmountAsBigDecimal().doubleValue());
				dbUtil.setString(2, colAmt.getCurrencyCode());
				dbUtil.setLong(3, stgColID);
				dbUtil.executeUpdate();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			// try {
			// //rmCon.rollbackConnection (this);
			// DefaultLogger.error(this, "Rolling back transaction!", null);
			// }
			// catch(Exception ee) {
			// DefaultLogger.error(this,
			// "Caught Exception while rolling back transaction!", ee);}
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
			finalize(readUtil, null);
		}
	}

	/**
	 * Update the cms_security given to the method with the key containing
	 * collateralID, and the value containing FSV Amount.
	 * 
	 * @param col is the ICollateral that contains key of collateralID in Long,
	 *        and value containing Required Security Coverage as Double.
	 * @param colAmt Amount to be inserted into the cms_security table.
	 */
	public void updateFSVAmount(ICollateral col, Amount colAmt) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = STMT_UPDATE_CHARGE_AMT;
			dbUtil.setSQL(sql);

			dbUtil.setDouble(1, colAmt.getAmountAsBigDecimal().doubleValue());
			dbUtil.setString(2, colAmt.getCurrencyCode());
			dbUtil.setLong(3, col.getCollateralID());
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			// try {
			// //rmCon.rollbackConnection (this);
			// DefaultLogger.error(this, "Rolling back transaction!", null);
			// }
			// catch(Exception ee) {
			// DefaultLogger.error(this,
			// "Caught Exception while rolling back transaction!", ee);
			// }
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
		}

	}

	public void updateSTGActualSecCoverage(ILimit iLmt, float percent) throws SearchDAOException {

		DBUtil dbUtil = null;
		DBUtil readUtil = null;
		ResultSet rs = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			readUtil = new DBUtil();
			String readSql = QUERY_GET_STG_LMT_ID;
			String sql = STMT_UPDATE_STG_ACT_SEC_COV;
			readUtil.setSQL(readSql);
			dbUtil.setSQL(sql);
			long lmtID = iLmt.getLimitID();
			readUtil.setLong(1, lmtID);
			rs = readUtil.executeQuery();
			if (rs.next()) {
				long stgLmtID = rs.getLong(1);
				rs.close();
				dbUtil.setFloat(1, percent);
				dbUtil.setLong(2, stgLmtID);
				dbUtil.executeUpdate();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			// try {
			// //rmCon.rollbackConnection (this);
			// DefaultLogger.error(this, "Rolling back transaction!", null);
			// }
			// catch(Exception ee) {
			// DefaultLogger.error(this,
			// "Caught Exception while rolling back transaction!", ee);}
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
			finalize(readUtil, null);
		}
	}

	/**
	 * Update the cms_security given to the method with the key containing
	 * collateralID, and the value containing FSV Amount.
	 * 
	 * @param iLmt is the ICollateral that contains key of collateralID in Long,
	 *        and value containing Required Security Coverage as Double.
	 * @param percent Amount to be inserted into the cms_security table.
	 */
	public void updateActualSecCoverage(ILimit iLmt, float percent) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = STMT_UPDATE_ACT_SEC_COV;
			dbUtil.setSQL(sql);

			dbUtil.setFloat(1, percent);
			dbUtil.setLong(2, iLmt.getLimitID());
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			// try {
			// //rmCon.rollbackConnection (this);
			// DefaultLogger.error(this, "Rolling back transaction!", null);
			// }
			// catch(Exception ee) {
			// DefaultLogger.error(this,
			// "Caught Exception while rolling back transaction!", ee);
			// }
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
		}

	}

	public void updateSTGLmtPrfActualSecCoverage(ILimitProfile iLmt, float percent) throws SearchDAOException {

		DBUtil dbUtil = null;
		DBUtil readUtil = null;
		ResultSet rs = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			readUtil = new DBUtil();
			String readSql = QUERY_GET_STG_LMTPRF_ID;
			String sql = STMT_UPDATE_LMT_PRF_STG_ACT_SEC_COV;
			readUtil.setSQL(readSql);
			dbUtil.setSQL(sql);
			long lmtID = iLmt.getLimitProfileID();
			readUtil.setLong(1, lmtID);
			rs = readUtil.executeQuery();
			if (rs.next()) {
				long stgLmtID = rs.getLong(1);
				rs.close();
				dbUtil.setFloat(1, percent);
				dbUtil.setLong(2, stgLmtID);
				dbUtil.executeUpdate();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			// try {
			// //rmCon.rollbackConnection (this);
			// DefaultLogger.error(this, "Rolling back transaction!", null);
			// }
			// catch(Exception ee) {
			// DefaultLogger.error(this,
			// "Caught Exception while rolling back transaction!", ee);}
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
			finalize(readUtil, null);
		}
	}

	/**
	 * Update the cms_security given to the method with the key containing
	 * collateralID, and the value containing FSV Amount.
	 * 
	 * @param iLmt is the ICollateral that contains key of collateralID in Long,
	 *        and value containing Required Security Coverage as Double.
	 * @param percent Amount to be inserted into the cms_security table.
	 */
	public void updateLmtPrfActualSecCoverage(ILimitProfile iLmt, float percent) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = STMT_UPDATE_LMT_PRF_ACT_SEC_COV;
			dbUtil.setSQL(sql);

			dbUtil.setFloat(1, percent);
			dbUtil.setLong(2, iLmt.getLimitProfileID());
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateActualChargeAmount!", e);
			// try {
			// //rmCon.rollbackConnection (this);
			// DefaultLogger.error(this, "Rolling back transaction!", null);
			// }
			// catch(Exception ee) {
			// DefaultLogger.error(this,
			// "Caught Exception while rolling back transaction!", ee);
			// }
			throw new SearchDAOException("Caught Exception in updateActualChargeAmount", e);
		}
		finally {
			finalize(dbUtil, null);
		}

	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil, ResultSet rs) throws SearchDAOException {
		try {
			if (null != rs) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources!", e);
		}
	}
}