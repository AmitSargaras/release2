/*
 * Created on Mar 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.eventmonitor.seccoverage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: jzhan $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/06/07 09:46:33 $ Tag: $Name: $
 */
public class SecCoverageCalDAO {

	private static final String SQL_FIND_BCA_WITH_PSCC_SCC = "SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE "
			+ "WHERE SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID IN " + "(SELECT lmtprof.CMS_LSP_LMT_PROFILE_ID "
			+ "FROM SCI_LSP_LMT_PROFILE lmtprof, TRANSACTION trx, CMS_PSCC_GENERATED pscc "
			+ "WHERE trx.TRANSACTION_TYPE LIKE 'PSCC' AND " + "trx.STATUS <> 'CLOSED' AND "
			+ "trx.REFERENCE_ID = pscc.PSCC_ID AND " + "pscc.CMS_LSP_LMT_PROFILE_ID = lmtprof.CMS_LSP_LMT_PROFILE_ID "
			+ "UNION " + "SELECT lmtprof.CMS_LSP_LMT_PROFILE_ID "
			+ "FROM SCI_LSP_LMT_PROFILE lmtprof, TRANSACTION trx, CMS_SCC_GENERATED scc "
			+ "WHERE trx.TRANSACTION_TYPE LIKE 'SCC' AND " + "trx.STATUS <> 'CLOSED' AND "
			+ "trx.REFERENCE_ID = scc.SCC_ID AND " + "scc.CMS_LSP_LMT_PROFILE_ID = lmtprof.CMS_LSP_LMT_PROFILE_ID)";

	private static final String SQL_FIND_SECURITY_CHARGE_BY_LIMIT = "SELECT SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID, "
			+ "CMS_SECURITY.CMS_COLLATERAL_ID, "
			+ "CMS_SECURITY.FSV, "
			+ "CMS_SECURITY.FSV_CURRENCY, "
			+ "CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID, "
			+ "CMS_CHARGE_DETAIL.CHARGE_TYPE, "
			+ "CMS_CHARGE_DETAIL.SECURITY_RANK, "
			+ "CMS_CHARGE_DETAIL.PRIOR_CHARGE_AMOUNT, "
			+ "CMS_CHARGE_DETAIL.PRIOR_CHARGE_CURRENCY, "
			+ "CMS_CHARGE_DETAIL.CHARGE_AMOUNT, "
			+ "CMS_CHARGE_DETAIL.CHARGE_CURRENCY_CODE "
			+ "FROM SCI_LSP_APPR_LMTS, CMS_SECURITY, CMS_LIMIT_CHARGE_MAP, CMS_CHARGE_DETAIL, CMS_LIMIT_SECURITY_MAP "
			+ "WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID in (#apprLimitIdList#) AND "
			+ "SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_CHARGE_MAP.CMS_LSP_APPR_LMTS_ID AND "
			+ "CMS_LIMIT_CHARGE_MAP.CHARGE_DETAIL_ID = CMS_CHARGE_DETAIL.CHARGE_DETAIL_ID AND "
			+ "CMS_LIMIT_CHARGE_MAP.STATUS = 'ACTIVE' AND "
			+ "CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_CHARGE_MAP.CMS_LSP_APPR_LMTS_ID AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = CMS_LIMIT_CHARGE_MAP.CMS_COLLATERAL_ID AND "
			+ "CMS_LIMIT_SECURITY_MAP.UPDATE_STATUS_IND <> 'D' AND "
			+ "CMS_CHARGE_DETAIL.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID AND CMS_SECURITY.STATUS = 'ACTIVE' "
			+ "ORDER BY CMS_SECURITY.CMS_COLLATERAL_ID, CMS_CHARGE_DETAIL.SECURITY_RANK, SCI_LSP_APPR_LMTS.LMT_ID";

	private static final String SQL_FIND_ACTIVE_PROFILE_BY_LIMIT = "SELECT SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID, "
			+ "SCI_LSP_APPR_LMTS.LMT_ID, " + "SCI_LSP_APPR_LMTS.LMT_AMT, " + "SCI_LSP_APPR_LMTS.CMS_ACTIVATED_LIMIT, "
			+ "SCI_LSP_APPR_LMTS.LMT_CRRNCY_ISO_CODE, " + "SCI_LSP_APPR_LMTS.LMT_TYPE_VALUE, "
			+ "SCI_LSP_APPR_LMTS.LMT_OUTER_LMT_ID, " + "SCI_LSP_APPR_LMTS.CMDT_OP_LMT, "
			+ "SCI_LSP_APPR_LMTS.CMS_REQ_SEC_COVERAGE, " + "TEMP1.COMMONDITY_COUNT " + "FROM SCI_LSP_APPR_LMTS, "
			+ "(SELECT COUNT(sec.CMS_COLLATERAL_ID) AS COMMONDITY_COUNT, " + "lmts.CMS_LSP_APPR_LMTS_ID "
			+ "FROM CMS_SECURITY sec, CMS_LIMIT_SECURITY_MAP map, SCI_LSP_APPR_LMTS lmts "
			+ "WHERE sec.SECURITY_SUB_TYPE_ID LIKE 'CF%' AND " + "sec.CMS_COLLATERAL_ID = map.CMS_COLLATERAL_ID AND "
			+ "map.CMS_LSP_APPR_LMTS_ID = lmts.CMS_LSP_APPR_LMTS_ID AND "
			+ "lmts.CMS_LIMIT_PROFILE_ID = ? GROUP BY lmts.CMS_LSP_APPR_LMTS_ID) TEMP1 "
			+ "WHERE SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = ? AND "
			+ "SCI_LSP_APPR_LMTS.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ "SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = TEMP1.CMS_LSP_APPR_LMTS_ID(+)";

	private static final String SQL_FIND_LIMIT_PROFILE_BY_LEID = "SELECT SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE "
			+ "SCI_LSP_LMT_PROFILE.LLP_LE_ID IN (#leIdList#)";

	private static final String SQL_UPDATE_LMT_PROF_ACT_SECCOV = "UPDATE SCI_LSP_APPR_LMTS SET CMS_ACT_SEC_COVERAGE = ? WHERE CMS_LSP_APPR_LMTS_ID = ?";

	private static final String SQL_UPDATE_STG_LMT_PROF_ACT_SECCOV = "UPDATE STAGE_LIMIT SET CMS_ACT_SEC_COVERAGE = ?  WHERE CMS_LSP_APPR_LMTS_ID = ?";

	private static final String SQL_UPDATE_SEC_FSV_BALANCE = "UPDATE CMS_SECURITY SET FSV_BALANCE = ?, FSV_BALANCE_CCY = ? WHERE CMS_COLLATERAL_ID = ?";

	private static final String SQL_UPDATE_STG_SEC_FSV_BALANCE = "UPDATE CMS_STAGE_SECURITY SET FSV_BALANCE = ? , FSV_BALANCE_CCY = ?  WHERE CMS_COLLATERAL_ID = ?";

	private static final String SQL_UPDATE_BCA_ACT_SECCOV = "UPDATE SCI_LSP_LMT_PROFILE SET CMS_ACT_SEC_COVERAGE = ? WHERE CMS_LSP_LMT_PROFILE_ID = ?";

	private static final String SQL_UPDATE_STG_BCA_ACT_SECCOV = "UPDATE STAGE_LIMIT_PROFILE SET CMS_ACT_SEC_COVERAGE = ?   WHERE CMS_LSP_LMT_PROFILE_ID = ?";

	private static final String SQL_CLEAR_LIMIT_ACT_SECCOV = "UPDATE SCI_LSP_APPR_LMTS SET CMS_ACT_SEC_COVERAGE = null WHERE CMS_LIMIT_PROFILE_ID = ?";

	private static final String SQL_CLEAR_STG_LIMIT_ACT_SECCOV = "UPDATE STAGE_LIMIT SET CMS_ACT_SEC_COVERAGE = null WHERE CMS_LIMIT_PROFILE_ID = ?";

	private static final String SQL_CLEAR_FSV_BALANCE = "UPDATE CMS_SECURITY SET FSV_BALANCE = null , FSV_BALANCE_CCY = null WHERE "
			+ "CMS_SECURITY.CMS_COLLATERAL_ID IN (SELECT CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID FROM "
			+ "CMS_LIMIT_SECURITY_MAP, SCI_LSP_APPR_LMTS WHERE "
			+ "SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = ? AND "
			+ "CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID)";

	private static final String SQL_CLEAR_STG_FSV_BALANCE = "UPDATE CMS_STAGE_SECURITY SET FSV_BALANCE = null , FSV_BALANCE_CCY = null WHERE "
			+ "CMS_STAGE_SECURITY.CMS_COLLATERAL_ID IN (SELECT CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID FROM "
			+ "CMS_LIMIT_SECURITY_MAP, SCI_LSP_APPR_LMTS WHERE "
			+ "SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = ? AND "
			+ "CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID)";

	// get a list of BCA id whose security coverage should be calculated, the
	// condition is that PSCC or SCC for that BCA need to be generated
	public List getLimitProfileIdWithPsccOrScc() throws SearchDAOException {
		List result = new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			StringBuffer strBuffer = new StringBuffer(SQL_FIND_BCA_WITH_PSCC_SCC);
			// DefaultLogger.debug(this,
			// "In method getLimitProfileIdWithPsccOrScc, query is: ");
			// DefaultLogger.debug(this, strBuffer.toString());
			dbUtil.setSQL(strBuffer.toString());
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(1));
			}
			return result;
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Caught Exception in getLimitProfileIdWithPsccOrScc!", ex);
			throw new SearchDAOException("Caught Exception in getLimitProfileIdWithPsccOrScc", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	// find a list of active limit profile for the limit, the result will be
	// stored
	// in a map format with key being the limit id and value being an instance
	// of
	// ActivateLimitInfoModel, notice that the result map will contain only
	// outer limit
	// whereby inner limit associated with the outer limit stored in the
	// ActivateLimitInfoModel
	// object
	public Map getActiveLimitProfileByLimit(String limitId) throws SearchDAOException {
		Map result = new HashMap();
		List tempList = new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			StringBuffer strBuffer = new StringBuffer(SQL_FIND_ACTIVE_PROFILE_BY_LIMIT);
			// DefaultLogger.debug(this,
			// "In method getActiveLimitProfileByLimit, query is: ");
			// DefaultLogger.debug(this, strBuffer.toString());
			dbUtil.setSQL(strBuffer.toString());
			dbUtil.setString(1, limitId);
			dbUtil.setString(2, limitId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBActivateLimitInfo limitInfoModel = new OBActivateLimitInfo();
				limitInfoModel.setLimitID(rs.getString("CMS_LSP_APPR_LMTS_ID"));

				limitInfoModel.setSciLmtID(rs.getString("LMT_ID"));

				limitInfoModel.setApprovedLimitAmount(rs.getString("LMT_AMT"));

				limitInfoModel.setActivatedLimitAmount(rs.getString("CMS_ACTIVATED_LIMIT"));

				limitInfoModel.setLimitCurrency(rs.getString("LMT_CRRNCY_ISO_CODE"));

				limitInfoModel.setLimitType(rs.getString("LMT_TYPE_VALUE"));

				limitInfoModel.setOuterLimitID(rs.getString("LMT_OUTER_LMT_ID"));

				limitInfoModel.setOperationalAmount(rs.getString("CMDT_OP_LMT"));

				limitInfoModel.setRequiredCoverage(rs.getString("CMS_REQ_SEC_COVERAGE"));

				String commondityCount = rs.getString("COMMONDITY_COUNT");
				if (commondityCount != null) {
					try {
						int cc = Integer.parseInt(commondityCount);
						if (cc >= 1) {
							limitInfoModel.setHasCommondity(true);
						}
					}
					catch (Exception ce) {
					}
				}

				tempList.add(limitInfoModel);
			}

			// process the list, for each outer limit find associated inner
			// limits
			for (int j = 0; j < tempList.size(); j++) {
				OBActivateLimitInfo limitInfoModel = (OBActivateLimitInfo) (tempList.get(j));
				if (ICMSConstant.CCC_OUTER_LIMIT.equals(limitInfoModel.getLimitType())) {
					for (int k = 0; k < tempList.size(); k++) {
						OBActivateLimitInfo nextInfoModel = (OBActivateLimitInfo) (tempList.get(k));
						if (ICMSConstant.CCC_INNER_LIMIT.equals(nextInfoModel.getLimitType())
								&& limitInfoModel.getLimitID().equals(nextInfoModel.getOuterLimitID())) {
							limitInfoModel.addInnerLimit(nextInfoModel);
						}
					}
					result.put(limitInfoModel.getLimitID(), limitInfoModel);
				}
			}
			return result;
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Caught Exception in getActiveLimitProfileByLimit!", ex);
			throw new SearchDAOException("Caught Exception in getActiveLimitProfileByLimit", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	// for the list of outer limits for the BCA, get the associated security and
	// charges
	// the result will be a map of SecCovChargeInfoModel
	// each SecCovChargeInfoModel will contain a list of SecCovChargeInfoModel
	// for the security
	// ordered by security rank and sci_limit_id, the SecCovChargeInfoModel also
	// contains limit id
	// it associated with. Note that here we are under the assumption that only
	// one limit is tied
	// to a charge
	public Map getSecurityCoverageInfoModels(List outerLimitIdList) throws SearchDAOException {
		Map result = new HashMap();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		if ((outerLimitIdList == null) || (outerLimitIdList.size() == 0)) {
			return result;
		}
		try {
			dbUtil = new DBUtil();
			StringBuffer strBuffer = new StringBuffer(SQL_FIND_SECURITY_CHARGE_BY_LIMIT);
			StringBuffer idList = new StringBuffer();
			for (int i = 0; i < outerLimitIdList.size(); i++) {
				String nextLimitId = (String) (outerLimitIdList.get(i));
				idList.append(nextLimitId);
				idList.append(", ");
			}
			int strLen = idList.length();
			idList.delete(strLen - 2, strLen);
			int replaceStart = SQL_FIND_SECURITY_CHARGE_BY_LIMIT.indexOf("#");
			strBuffer.replace(replaceStart, replaceStart + 17, idList.toString());
			// DefaultLogger.debug(this,
			// "In method getSecurityCoverageInfoModels, query is: ");
			// DefaultLogger.debug(this, strBuffer.toString());
			dbUtil.setSQL(strBuffer.toString());
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBSecCovSecurityInfo securityInfoModel = new OBSecCovSecurityInfo();
				OBSecCovChargeInfo chargeInfoModel = new OBSecCovChargeInfo();

				String limitId = rs.getString("CMS_LSP_APPR_LMTS_ID");

				String securityId = rs.getString("CMS_COLLATERAL_ID");

				String fsv = rs.getString("FSV");

				String fsvCurrency = rs.getString("FSV_CURRENCY");

				String chargeDetailId = rs.getString("CHARGE_DETAIL_ID");

				String chargeType = rs.getString("CHARGE_TYPE");

				String securityRank = rs.getString("SECURITY_RANK");

				String priorChargeAmount = rs.getString("PRIOR_CHARGE_AMOUNT");

				String priorChargeCurrency = rs.getString("PRIOR_CHARGE_CURRENCY");

				String chargeAmount = rs.getString("CHARGE_AMOUNT");

				String chargeCurrency = rs.getString("CHARGE_CURRENCY_CODE");
				if (result.containsKey(securityId)) {
					securityInfoModel = (OBSecCovSecurityInfo) (result.get(securityId));
				}
				else {
					securityInfoModel.setCollateralID(securityId);
					securityInfoModel.setFsv(fsv);
					securityInfoModel.setFsvCurrency(fsvCurrency);
					result.put(securityId, securityInfoModel);
				}
				chargeInfoModel.setChargeDetailId(chargeDetailId);
				chargeInfoModel.setLimitId(limitId);
				chargeInfoModel.setChargeType(chargeType);
				chargeInfoModel.setSecurityRank(securityRank);
				chargeInfoModel.setPriorChargeAmount(priorChargeAmount);
				chargeInfoModel.setPriorChargeCurrency(priorChargeCurrency);
				chargeInfoModel.setChargeAmount(chargeAmount);
				chargeInfoModel.setChargeCurrency(chargeCurrency);
				securityInfoModel.addCharge(chargeInfoModel);
			}
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Caught Exception in getSecurityCoverageInfoModels!", ex);
			throw new SearchDAOException("Caught Exception in getSecurityCoverageInfoModels", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
		return result;
	}

	public void updateLmtProfActualSecCoverage(String actualCoverage, String limitProfileId) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_LMT_PROF_ACT_SECCOV;
			// DefaultLogger.debug(this,
			// "In method updateLmtProfActualSecCoverage update limit: " +
			// limitProfileId
			// + " actual security coverage: " + actualCoverage);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, actualCoverage);
			dbUtil.setString(2, limitProfileId);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateLmtProfActualSecCoverage!", e);
			throw new SearchDAOException("Caught Exception in updateLmtProfActualSecCoverage", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void updateStgLmtProfActualSecCoverage(String actualCoverage, String limitProfileId)
			throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_STG_LMT_PROF_ACT_SECCOV;
			// DefaultLogger.debug(this,
			// "In method updateStgLmtProfActualSecCoverage update limit: " +
			// limitProfileId
			// + " actual security coverage: " + actualCoverage);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, actualCoverage);
			dbUtil.setString(2, limitProfileId);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateStgLmtProfActualSecCoverage!", e);
			throw new SearchDAOException("Caught Exception in updateStgLmtProfActualSecCoverage", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void updateSecFsvBalance(String fsvBalance, String balanceCurrency, String securityId)
			throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_SEC_FSV_BALANCE;
			// DefaultLogger.debug(this,
			// "In method updateStgSecFsvBalance update security: " + securityId
			// + " FSV balance: " + fsvBalance);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, fsvBalance);
			dbUtil.setString(2, balanceCurrency);
			dbUtil.setString(3, securityId);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateSecFsvBalance!", e);
			throw new SearchDAOException("Caught Exception in updateSecFsvBalance", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void updateStgSecFsvBalance(String fsvBalance, String balanceCurrency, String securityId)
			throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_STG_SEC_FSV_BALANCE;
			// DefaultLogger.debug(this,
			// "In method updateStgSecFsvBalance update security: " + securityId
			// + " FSV balance: " + fsvBalance);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, fsvBalance);
			dbUtil.setString(2, balanceCurrency);
			dbUtil.setString(3, securityId);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateStgSecFsvBalance!", e);
			throw new SearchDAOException("Caught Exception in updateStgSecFsvBalance", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void updateBCAActualSecCoverage(String actualCoverage, String bcaLimitId) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_BCA_ACT_SECCOV;
			// DefaultLogger.debug(this,
			// "In method updateBCAActualSecCoverage update bca: " + bcaLimitId
			// + " actual security coverage: " + actualCoverage);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, actualCoverage);
			dbUtil.setString(2, bcaLimitId);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateBCAActualSecCoverage!", e);
			throw new SearchDAOException("Caught Exception in updateBCAActualSecCoverage", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void updateStgBCAActualSecCoverage(String actualCoverage, String bcaLimitId) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_STG_BCA_ACT_SECCOV;
			// DefaultLogger.debug(this,
			// "In method updateStgBCAActualSecCoverage update limit: " +
			// bcaLimitId
			// + " actual security coverage: " + actualCoverage);
			dbUtil.setSQL(sql);
			dbUtil.setString(1, actualCoverage);
			dbUtil.setString(2, bcaLimitId);
			dbUtil.executeUpdate();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in updateStgBCAActualSecCoverage!", e);
			throw new SearchDAOException("Caught Exception in updateStgBCAActualSecCoverage", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public void cleanUp(String limitId) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_BCA_ACT_SECCOV;
			dbUtil.setSQL(sql);
			dbUtil.setString(1, null);
			dbUtil.setString(2, limitId);
			dbUtil.executeUpdate();
			DefaultLogger.debug(this, "Clear actual security coverage for bca");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in cleanUp!", e);
			throw new SearchDAOException("Caught Exception in cleanUp", e);
		}
		finally {
			finalize(dbUtil, null);
		}

		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_UPDATE_STG_BCA_ACT_SECCOV;
			dbUtil.setSQL(sql);
			dbUtil.setString(1, null);
			dbUtil.setString(2, limitId);
			dbUtil.executeUpdate();
			DefaultLogger.debug(this, "Clear stage actual security coverage for bca");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in cleanUp!", e);
			throw new SearchDAOException("Caught Exception in cleanUp", e);
		}
		finally {
			finalize(dbUtil, null);
		}

		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_CLEAR_LIMIT_ACT_SECCOV;
			dbUtil.setSQL(sql);
			dbUtil.setString(1, limitId);
			dbUtil.executeUpdate();
			DefaultLogger.debug(this, "Clear actual security coverage for limits");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in cleanUp!", e);
			throw new SearchDAOException("Caught Exception in cleanUp", e);
		}
		finally {
			finalize(dbUtil, null);
		}

		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_CLEAR_STG_LIMIT_ACT_SECCOV;
			dbUtil.setSQL(sql);
			dbUtil.setString(1, limitId);
			dbUtil.executeUpdate();
			DefaultLogger.debug(this, "Clear stage actual security coverage for limits");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in cleanUp!", e);
			throw new SearchDAOException("Caught Exception in cleanUp", e);
		}
		finally {
			finalize(dbUtil, null);
		}

		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_CLEAR_FSV_BALANCE;
			dbUtil.setSQL(sql);
			dbUtil.setString(1, limitId);
			dbUtil.executeUpdate();
			DefaultLogger.debug(this, "Clear security fsv balance");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in cleanUp!", e);
			throw new SearchDAOException("Caught Exception in cleanUp", e);
		}
		finally {
			finalize(dbUtil, null);
		}

		try {
			// starts connection
			dbUtil = new DBUtil();
			String sql = SQL_CLEAR_STG_FSV_BALANCE;
			dbUtil.setSQL(sql);
			dbUtil.setString(1, limitId);
			dbUtil.executeUpdate();
			DefaultLogger.debug(this, "Clear stg security fsv balance");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in cleanUp!", e);
			throw new SearchDAOException("Caught Exception in cleanUp", e);
		}
		finally {
			finalize(dbUtil, null);
		}
	}

	public List getLimitProfileIdByLE(List leIdList) throws SearchDAOException {
		List result = new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			StringBuffer strBuffer = new StringBuffer(SQL_FIND_LIMIT_PROFILE_BY_LEID);
			StringBuffer idList = new StringBuffer();
			for (int i = 0; i < leIdList.size(); i++) {
				String nextLimitId = (String) (leIdList.get(i));
				idList.append(nextLimitId);
				idList.append(", ");
			}
			int strLen = idList.length();
			idList.delete(strLen - 2, strLen);
			int replaceStart = SQL_FIND_LIMIT_PROFILE_BY_LEID.indexOf("#");
			strBuffer.replace(replaceStart, replaceStart + 10, idList.toString());
//			System.out.println(strBuffer);
			dbUtil.setSQL(strBuffer.toString());
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(1));
			}
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Caught Exception in getLimitProfileIdByLE!", ex);
			throw new SearchDAOException("Caught Exception in getLimitProfileIdByLE", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
		return result;
	}

	private void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
		}
	}
}
