/*
 * Created on Jun 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentDAO {
	private static final String SQL_GET_APPORTIONMENT_FOR_SEC = "SELECT appo.SEC_APPORTIONMENT_ID, "
			+ "appo.PRIORITY_RANKING, " + "appo.PRIORITY_RANKING_AMOUNT, " + "appo.APPORTION_AMT, "
			+ "appo.CMS_COLLATERAL_ID, " + "appo.CMS_LSP_APPR_LMTS_ID, " + "appo.CMS_REF_ID, " + "appo.PERC_AMT_IND, "
			+ "appo.BY_ABSOLUTE_AMT, " + "appo.BY_PERCENTAGE, " + "appo.MIN_PERC_AMT_IND, " + "appo.MIN_AMT, "
			+ "appo.MIN_PERCENTAGE, " + "appo.MAX_PERC_AMT_IND, " + "appo.MAX_AMT, " + "appo.MAX_PERCENTAGE, "
			+ "appo.CHARGE_DETAIL_ID, " + "sec.FSV, " + "sec.FSV_CURRENCY, " + "lmt.LMT_ID, "
			+ "lmt.LMT_CRRNCY_ISO_CODE, " + "lmt.LMT_AMT, " + "lmt.CMS_ACTIVATED_LIMIT, " + "lmt.LMT_LE_ID, "
			+ "lmt.LMT_LSP_ID, " + "charge.SECURITY_RANK, " + "charge.CHARGE_AMOUNT, " + "mainprof.LMP_SHORT_NAME, "
			+ "codeent.ENTRY_NAME "
			+ "FROM CMS_SECURITY_APPORTIONMENT appo, CMS_SECURITY sec, CMS_CHARGE_DETAIL charge, "
			+ "SCI_LSP_APPR_LMTS lmt, SCI_LE_MAIN_PROFILE mainprof, COMMON_CODE_CATEGORY_ENTRY codeent "
			+ "WHERE sec.CMS_COLLATERAL_ID = ? AND " + "appo.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID AND "
			+ "appo.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID AND "
			+ "appo.CHARGE_DETAIL_ID = charge.CHARGE_DETAIL_ID AND " + "lmt.LMT_LE_ID = mainprof.LMP_LE_ID AND "
			+ "lmt.LMT_PRD_TYPE_NUM = codeent.CATEGORY_CODE AND "
			+ "lmt.LMT_PRD_TYPE_VALUE = codeent.ENTRY_CODE ORDER BY appo.SEC_APPORTIONMENT_ID";

	private static final String SQL_GET_STG_APPORTIONMENT_FOR_SEC = "SELECT appo.SEC_APPORTIONMENT_ID, "
			+ "appo.PRIORITY_RANKING, " + "appo.PRIORITY_RANKING_AMOUNT, " + "appo.APPORTION_AMT, "
			+ "appo.CMS_COLLATERAL_ID, " + "appo.CMS_LSP_APPR_LMTS_ID, " + "appo.CMS_REF_ID, " + "appo.PERC_AMT_IND, "
			+ "appo.BY_ABSOLUTE_AMT, " + "appo.BY_PERCENTAGE, " + "appo.MIN_PERC_AMT_IND, " + "appo.MIN_AMT, "
			+ "appo.MIN_PERCENTAGE, " + "appo.MAX_PERC_AMT_IND, " + "appo.MAX_AMT, " + "appo.MAX_PERCENTAGE, "
			+ "appo.CHARGE_DETAIL_ID, " + "sec.FSV, " + "sec.FSV_CURRENCY, " + "lmt.LMT_ID, "
			+ "lmt.LMT_CRRNCY_ISO_CODE, " + "lmt.LMT_AMT, " + "lmt.CMS_ACTIVATED_LIMIT, " + "lmt.LMT_LE_ID, "
			+ "lmt.LMT_LSP_ID, " + "charge.SECURITY_RANK, " + "charge.CHARGE_AMOUNT, " + "mainprof.LMP_SHORT_NAME, "
			+ "codeent.ENTRY_NAME "
			+ "FROM CMS_STAGE_SEC_APPORTIONMENT appo, CMS_STAGE_SECURITY sec, CMS_CHARGE_DETAIL charge, "
			+ "SCI_LSP_APPR_LMTS lmt, SCI_LE_MAIN_PROFILE mainprof, COMMON_CODE_CATEGORY_ENTRY codeent "
			+ "WHERE sec.CMS_COLLATERAL_ID = ? AND " + "appo.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID AND "
			+ "appo.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID AND "
			+ "appo.CHARGE_DETAIL_ID = charge.CHARGE_DETAIL_ID AND " + "lmt.LMT_LE_ID = mainprof.LMP_LE_ID AND "
			+ "lmt.LMT_PRD_TYPE_NUM = codeent.CATEGORY_CODE AND "
			+ "lmt.LMT_PRD_TYPE_VALUE = codeent.ENTRY_CODE ORDER BY appo.SEC_APPORTIONMENT_ID";

	private static final String SQL_GET_LIMIT_INFO_BY_SEC = "SELECT lmt.CMS_LSP_APPR_LMTS_ID, " + "lmt.LMT_ID, "
			+ "lmt.LMT_CRRNCY_ISO_CODE, " + "lmt.LMT_AMT, " + "lmt.CMS_ACTIVATED_LIMIT, " + "lmt.LMT_LE_ID, "
			+ "lmt.LMT_LSP_ID, " + "mainprof.LMP_SHORT_NAME, " + "charge.CHARGE_DETAIL_ID, " + "charge.SECURITY_RANK, "
			+ "CONVERT_AMT(charge.CHARGE_AMOUNT, charge.CHARGE_CURRENCY_CODE, sec.FSV_CURRENCY) AS CHARGE_AMOUNT, "
			+ "codeent.ENTRY_NAME " + "FROM CMS_LIMIT_CHARGE_MAP chargemap, CMS_SECURITY sec, "
			+ "SCI_LSP_APPR_LMTS lmt, CMS_CHARGE_DETAIL charge, CMS_LIMIT_SECURITY_MAP secmap, "
			+ "SCI_LE_MAIN_PROFILE mainprof, COMMON_CODE_CATEGORY_ENTRY codeent "
			+ "WHERE chargemap.CMS_COLLATERAL_ID = ? AND " + "chargemap.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID AND "
			+ "chargemap.STATUS = 'ACTIVE' AND " + "chargemap.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID AND "
			+ "chargemap.CHARGE_DETAIL_ID = charge.CHARGE_DETAIL_ID AND " + "charge.CHARGE_TYPE = 'G' AND "
			+ "(charge.STATUS IS NULL OR charge.STATUS <> 'DELETED') AND "
			+ "chargemap.CHARGE_ID = secmap.CHARGE_ID AND secmap.UPDATE_STATUS_IND <> 'D' AND "
			+ "lmt.LMT_TYPE_VALUE = 'OUTER' AND "
			+ "(lmt.CMS_LIMIT_STATUS IS NULL OR lmt.CMS_LIMIT_STATUS = 'ACTIVE') AND "
			+ "lmt.LMT_LE_ID = mainprof.LMP_LE_ID AND "
			+ "TO_CHAR(lmt.LMT_PRD_TYPE_NUM, '99999999999999999999') = codeent.CATEGORY_CODE AND "
			+ "lmt.LMT_PRD_TYPE_VALUE = codeent.ENTRY_CODE ORDER BY charge.SECURITY_RANK";

	private static final String SQL_GET_BCA_COUNT_FOR_SEC = "SELECT COUNT(DISTINCT concat(lmt.LMT_LE_ID, lmt.LMT_LSP_ID)) "
			+ "FROM CMS_LIMIT_SECURITY_MAP secmap, "
			+ "SCI_LSP_APPR_LMTS lmt "
			+ "WHERE secmap.CMS_COLLATERAL_ID = ? AND "
			+ "secmap.UPDATE_STATUS_IND <> 'D' AND "
			+ "secmap.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID AND "
			+ "lmt.LMT_TYPE_VALUE = 'OUTER' AND "
			+ "lmt.CMS_LIMIT_STATUS = 'ACTIVE'";

	private static final String SQL_GET_COBO_COUNT_FOR_SEC = "SELECT COUNT(secmap.CHARGE_ID) "
			+ "FROM CMS_LIMIT_SECURITY_MAP secmap, CMS_SECURITY sec " + "WHERE secmap.CMS_COLLATERAL_ID = ? AND "
			+ "secmap.UPDATE_STATUS_IND <> 'D' AND " + "secmap.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID AND "
			+ "sec.STATUS = 'ACTIVE' AND " + "secmap.CMS_LSP_CO_BORROW_LMT_ID IS NULL";

	// retrieve the apportionment tied to particular security
	public List getApportionmentsForSecurity(long collateralId) throws SearchDAOException {
		List result = new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SQL_GET_APPORTIONMENT_FOR_SEC);
			dbUtil.setLong(1, collateralId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBSecApportionmentDtl dtl = new OBSecApportionmentDtl();
				dtl.setSecApportionmentID(rs.getLong("SEC_APPORTIONMENT_ID"));
				dtl.setPriorityRanking(rs.getInt("PRIORITY_RANKING"));
				dtl.setPriorityRankingAmount(rs.getDouble("PRIORITY_RANKING_AMOUNT"));
				dtl.setApportionAmount(rs.getDouble("APPORTION_AMT"));
				dtl.setApportionAmountPrev(dtl.getApportionAmount());
				dtl.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
				dtl.setLimitID(rs.getLong("CMS_LSP_APPR_LMTS_ID"));
				dtl.setRefID(rs.getLong("CMS_REF_ID"));
				dtl.setPercAmtInd(rs.getString("PERC_AMT_IND"));
				dtl.setByAbsoluteAmt(rs.getDouble("BY_ABSOLUTE_AMT"));
				dtl.setByPercentage(rs.getDouble("BY_PERCENTAGE"));
				dtl.setMinPercAmtInd(rs.getString("MIN_PERC_AMT_IND"));
				dtl.setMinAbsoluteAmt(rs.getDouble("MIN_AMT"));
				dtl.setMinPercentage(rs.getDouble("MIN_PERCENTAGE"));
				dtl.setMaxPercAmtInd(rs.getString("MAX_PERC_AMT_IND"));
				dtl.setMaxAbsoluteAmt(rs.getDouble("MAX_AMT"));
				dtl.setMaxPercentage(rs.getDouble("MAX_PERCENTAGE"));
				dtl.setChargeDetailId(rs.getLong("CHARGE_DETAIL_ID"));
				dtl.setCurrencyCode(rs.getString("FSV_CURRENCY"));
				dtl.setActivatedLimitCcy(rs.getString("LMT_CRRNCY_ISO_CODE"));
				dtl.setActivatedLimitAmt(rs.getString("CMS_ACTIVATED_LIMIT"));
				dtl.setApprovedLimitCcy(rs.getString("LMT_CRRNCY_ISO_CODE"));
				dtl.setApprovedLimitAmt(rs.getString("LMT_AMT"));
				dtl.setLeId(rs.getString("LMT_LE_ID"));
				dtl.setSubProfileId(rs.getString("LMT_LSP_ID"));
				dtl.setLeName(rs.getString("LMP_SHORT_NAME"));
				dtl.setLimitIdDisp(rs.getString("LMT_ID"));
				dtl.setProductDesc(rs.getString("ENTRY_NAME"));
				dtl.setChargeRank(rs.getString("SECURITY_RANK"));
				dtl.setChargeAmount(rs.getDouble("CHARGE_AMOUNT"));
				result.add(dtl);
			}
			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException("Caught Exception in getApportionmentsForSecurity", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	// retrieve the stage apportionment tied to particular security
	public List getStgApportionmentsForSecurity(long collateralId) throws SearchDAOException {
		List result = new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SQL_GET_STG_APPORTIONMENT_FOR_SEC);
			dbUtil.setLong(1, collateralId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBSecApportionmentDtl dtl = new OBSecApportionmentDtl();
				dtl.setSecApportionmentID(rs.getLong("SEC_APPORTIONMENT_ID"));
				dtl.setPriorityRanking(rs.getInt("PRIORITY_RANKING"));
				dtl.setPriorityRankingAmount(rs.getDouble("PRIORITY_RANKING_AMOUNT"));
				dtl.setApportionAmount(rs.getDouble("APPORTION_AMT"));
				dtl.setApportionAmountPrev(dtl.getApportionAmount());
				dtl.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
				dtl.setLimitID(rs.getLong("CMS_LSP_APPR_LMTS_ID"));
				dtl.setRefID(rs.getLong("CMS_REF_ID"));
				dtl.setPercAmtInd(rs.getString("PERC_AMT_IND"));
				dtl.setByAbsoluteAmt(rs.getDouble("BY_ABSOLUTE_AMT"));
				dtl.setByPercentage(rs.getDouble("BY_PERCENTAGE"));
				dtl.setMinPercAmtInd(rs.getString("MIN_PERC_AMT_IND"));
				dtl.setMinAbsoluteAmt(rs.getDouble("MIN_AMT"));
				dtl.setMinPercentage(rs.getDouble("MIN_PERCENTAGE"));
				dtl.setMaxPercAmtInd(rs.getString("MAX_PERC_AMT_IND"));
				dtl.setMaxAbsoluteAmt(rs.getDouble("MAX_AMT"));
				dtl.setMaxPercentage(rs.getDouble("MAX_PERCENTAGE"));
				dtl.setChargeDetailId(rs.getLong("CHARGE_DETAIL_ID"));
				dtl.setCurrencyCode(rs.getString("FSV_CURRENCY"));
				dtl.setActivatedLimitCcy(rs.getString("LMT_CRRNCY_ISO_CODE"));
				dtl.setActivatedLimitAmt(rs.getString("CMS_ACTIVATED_LIMIT"));
				dtl.setApprovedLimitCcy(rs.getString("LMT_CRRNCY_ISO_CODE"));
				dtl.setApprovedLimitAmt(rs.getString("LMT_AMT"));
				dtl.setLeId(rs.getString("LMT_LE_ID"));
				dtl.setSubProfileId(rs.getString("LMT_LSP_ID"));
				dtl.setLeName(rs.getString("LMP_SHORT_NAME"));
				dtl.setLimitIdDisp(rs.getString("LMT_ID"));
				dtl.setProductDesc(rs.getString("ENTRY_NAME"));
				dtl.setChargeRank(rs.getString("SECURITY_RANK"));
				dtl.setChargeAmount(rs.getDouble("CHARGE_AMOUNT"));
				result.add(dtl);
			}
			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException("Caught Exception in getStgApportionmentsForSecurity", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	public List getLimitDetailForNewApportionment(String collateralId) throws SearchDAOException {
		List result = new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SQL_GET_LIMIT_INFO_BY_SEC);
			dbUtil.setString(1, collateralId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				OBSecApportionLmtDtl dtl = new OBSecApportionLmtDtl();
				dtl.setCmsLspApprLmtId(rs.getLong("CMS_LSP_APPR_LMTS_ID"));
				dtl.setLimitId(rs.getLong("LMT_ID"));
				dtl.setLeId(rs.getLong("LMT_LE_ID"));
				dtl.setLeName(rs.getString("LMP_SHORT_NAME"));
				dtl.setChargeId(rs.getLong("CHARGE_DETAIL_ID"));
				dtl.setChargeRank(rs.getInt("SECURITY_RANK"));
				dtl.setChargeAmount(rs.getDouble("CHARGE_AMOUNT"));
				dtl.setSubProfileId(rs.getLong("LMT_LSP_ID"));
				dtl.setProductDesc(rs.getString("ENTRY_NAME"));
				dtl.setApprovedLimitAmt(rs.getString("LMT_AMT"));
				dtl.setApprovedLimitCcy(rs.getString("LMT_CRRNCY_ISO_CODE"));
				dtl.setActivatedLimitAmt(rs.getString("CMS_ACTIVATED_LIMIT"));
				dtl.setActivatedLimitCcy(rs.getString("LMT_CRRNCY_ISO_CODE"));
				result.add(dtl);
			}
			return result;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException("Caught Exception in getLimitDetailForNewApportionment", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
	}

	// check whether a security require apportionment, apportionment is required
	// only if
	// it is tied to more than 1 BCA
	public boolean checkSecurityRequireApportion(String collateralId) throws SearchDAOException {
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SQL_GET_COBO_COUNT_FOR_SEC);
			dbUtil.setString(1, collateralId);
			rs = dbUtil.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				// if (count > 1)
				if (count > 0) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException("Caught Exception in checkSecurityRequireApportion", ex);
		}
		finally {
			finalize(dbUtil, rs);
		}
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
