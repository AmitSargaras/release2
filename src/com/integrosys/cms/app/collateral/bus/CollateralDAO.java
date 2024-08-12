/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralDAO.java,v 1.66 2006/11/10 09:00:44 jzhai Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.ibm.db2.jcc.am.SqlException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.OBMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationBeanFactory;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.common.util.SecurityAccessValidationUtils;
import com.integrosys.cms.app.eod.bus.IEODStatus;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry;
import com.integrosys.cms.app.fileUpload.bus.FileUploadJdbcImpl;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.batch.eod.EODConstants;
import com.integrosys.cms.ui.collateral.property.PropertyForm;
import com.integrosys.cms.ui.collateral.property.propcommgeneral.PropCommGeneralForm;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.batch.fcc.col.liquidation.fd.upload.FccColFdFileUploadHeader;
import com.integrosys.cms.batch.mtmvalprocess.fd.upload.FixedDepositFileUploadHeader;
import com.integrosys.cms.batch.mtmvalprocess.fd.upload.FixedDepositFileUploadNewHeader;
import com.integrosys.cms.batch.mtmvalprocess.mfequity.upload.MfEquityFileUploadHeader;
import com.integrosys.cms.batch.mtmvalprocess.mfequity.upload.MfEquityFileUploadNewHeader;
import com.integrosys.cms.batch.mtmvalprocess.sbbgsblc.upload.SBBGSBLCFileUploadHeader;
import com.integrosys.cms.batch.mtmvalprocess.sbbgsblc.upload.SBBGSBLCFileUploadNewHeader;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.IMarketableEquityLineDetailConstants;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.aa.CheckerApproveAADetailCommand;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * DAO for collateral.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.66 $
 * @since $Date: 2006/11/10 09:00:44 $ Tag: $Name: $
 */
public class CollateralDAO extends JdbcDaoSupport implements ICollateralDAO {
	
	private String receivedStatementProcParam;
	private String receivedStatementProc;
	private String fdDeletedBackupProc;
	private String ladExpiryUpdate;
	
	//Start: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	private String ladExpiryUpdateStage;
	//End: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	private String recurNewMview;
	private String facSecNewMview;
	private String custLimitMview;
	private String facChargeIdProc;
	private String cmsInterfaceLogbkupProc;
	
	private String ifscCodeInterfaceLogBkupProc;
	
	public String getIfscCodeInterfaceLogBkupProc() {
		return ifscCodeInterfaceLogBkupProc;
	}

	public void setIfscCodeInterfaceLogBkupProc(String ifscCodeInterfaceLogBkupProc) {
		this.ifscCodeInterfaceLogBkupProc = ifscCodeInterfaceLogBkupProc;
	}


	private String cmsFCUBSLogbkupProc;
	private String cmsPSRLogbkupProc;
	
	private String facilityIntLogbkpProc;
	private String liabilityIntLogbkpProc;
	
		private String deferralInsurancePolicy;
	private String deferralGCPolicy;
	private String inserDeferralTBCre;
	private String insertDeferralChecklistProperty;
	
	public String getInserDeferralTBCre() {
		return inserDeferralTBCre;
	}

	public void setInserDeferralTBCre(String inserDeferralTBCre) {
		this.inserDeferralTBCre = inserDeferralTBCre;
	}

	public String getInsertDeferralChecklistProperty() {
		return insertDeferralChecklistProperty;
	}

	public void setInsertDeferralChecklistProperty(String insertDeferralChecklistProperty) {
		this.insertDeferralChecklistProperty = insertDeferralChecklistProperty;
	}

	public String getDeferralInsurancePolicy() {
		return deferralInsurancePolicy;
	}

	public void setDeferralInsurancePolicy(String deferralInsurancePolicy) {
		this.deferralInsurancePolicy = deferralInsurancePolicy;
	}
	
	public String getDeferralGCPolicy() {
		return deferralGCPolicy;
	}

	public void setDeferralGCPolicy(String deferralGCPolicy) {
		this.deferralGCPolicy = deferralGCPolicy;
	}
	

	public String getLadExpiryUpdate() {
		return ladExpiryUpdate;
	}

	public void setLadExpiryUpdate(String ladExpiryUpdate) {
		this.ladExpiryUpdate = ladExpiryUpdate;
	}

	//Start: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	public String getLadExpiryUpdateStage() {
		return ladExpiryUpdateStage;
	}

	public void setLadExpiryUpdateStage(String ladExpiryUpdateStage) {
		this.ladExpiryUpdateStage = ladExpiryUpdateStage;
	}
	//End: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	public String getCustLimitMview() {
		return custLimitMview;
	}

	public void setCustLimitMview(String custLimitMview) {
		this.custLimitMview = custLimitMview;
	}

	public String getRecurNewMview() {
		return recurNewMview;
	}

	public void setRecurNewMview(String recurNewMview) {
		this.recurNewMview = recurNewMview;
	}

	public String getFacSecNewMview() {
		return facSecNewMview;
	}

	public void setFacSecNewMview(String facSecNewMview) {
		this.facSecNewMview = facSecNewMview;
	}

	public String getFdDeletedBackupProc() {
		return fdDeletedBackupProc;
	}

	public void setFdDeletedBackupProc(String fdDeletedBackupProc) {
		this.fdDeletedBackupProc = fdDeletedBackupProc;
	}

	public String getFacChargeIdProc() {
		return facChargeIdProc;
	}

	public void setFacChargeIdProc(String facChargeIdProc) {
		this.facChargeIdProc = facChargeIdProc;
	}

	public String getReceivedStatementProc() {
		return receivedStatementProc;
	}

	public void setReceivedStatementProc(String receivedStatementProc) {
		this.receivedStatementProc = receivedStatementProc;
	}


	public String getReceivedStatementProcParam() {
		return receivedStatementProcParam;
	}

	public void setReceivedStatementProcParam(String receivedStatementProcParam) {
		this.receivedStatementProcParam = receivedStatementProcParam;
	}


	private DBUtil dbUtil = null;
	
	private static String SELECT_COLLATERAL_MAIN_BORROWER = "SELECT cms_security.SCI_SECURITY_DTL_ID, cms_security.CMS_COLLATERAL_ID \n"
			+ "FROM cms_security_sub_type, CMS_SECURITY_SOURCE source, cms_security \n"
			+ "  LEFT OUTER JOIN cms_limit_security_map \n"
			+ "    ON cms_security.CMS_COLLATERAL_ID = cms_limit_security_map.CMS_COLLATERAL_ID \n"
			+ "  LEFT OUTER JOIN  sci_lsp_appr_lmts \n"
			+ "    ON cms_limit_security_map.CMS_LSP_APPR_LMTS_ID = sci_lsp_appr_lmts.CMS_LSP_APPR_LMTS_ID \n"
			+ "  LEFT OUTER JOIN sci_lsp_lmt_profile \n"
			+ "    ON sci_lsp_appr_lmts.CMS_LIMIT_PROFILE_ID = sci_lsp_lmt_profile.CMS_LSP_LMT_PROFILE_ID \n"
			+ "  LEFT OUTER JOIN sci_le_sub_profile"
			+ "    ON sci_lsp_lmt_profile.CMS_CUSTOMER_ID = sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID \n"
			+ "  LEFT OUTER JOIN sci_le_main_profile \n"
			+ "    ON sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID = sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID \n"
		
			/*+ " FROM cms_security_sub_type, cms_security_source SOURCE, cms_security \n"
			//lines added by dattatray thorat - start
			+ " LEFT OUTER JOIN cms_limit_security_map "
			+ " ON cms_security.CMS_COLLATERAL_ID = cms_limit_security_map.CMS_COLLATERAL_ID " 
			+ " LEFT OUTER JOIN  sci_lsp_appr_lmts "
			+ " ON cms_limit_security_map.CMS_LSP_APPR_LMTS_ID = sci_lsp_appr_lmts.CMS_LSP_APPR_LMTS_ID "*/
			//lines added by dattatray thorat - end			
			+ " WHERE source.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID \n"
			+ " AND cms_security_sub_type.SECURITY_SUB_TYPE_ID = cms_security.SECURITY_SUB_TYPE_ID \n";

	private static String SELECT_COLLATERAL_PLEDGOR = "SELECT cms_security.SCI_SECURITY_DTL_ID, cms_security.CMS_COLLATERAL_ID \n"
			+ "FROM cms_security_sub_type, cms_security_source SOURCE, \n"
			+ "  cms_security, sci_sec_pldgr_map, sci_pledgor_dtl \n"
			+ "WHERE  source.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID \n"
			+ "  AND sci_sec_pldgr_map.CMS_PLEDGOR_DTL_ID = sci_pledgor_dtl.CMS_PLEDGOR_DTL_ID \n"
			+ "  AND sci_sec_pldgr_map.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID \n"
			+ "  AND cms_security_sub_type.SECURITY_SUB_TYPE_ID = cms_security.SECURITY_SUB_TYPE_ID \n";

	private static String SELECT_COLLATERAL_WITH_AA = "SELECT cms_security.SCI_SECURITY_DTL_ID, cms_security.CMS_COLLATERAL_ID "
			+ "FROM cms_security_sub_type, cms_security_source SOURCE, cms_limit_security_map lsm, "
			+ "sci_lsp_appr_lmts lmt, sci_lsp_lmt_profile aa, cms_security "
			+ "LEFT OUTER JOIN sci_sec_pldgr_map "
			+ "ON sci_sec_pldgr_map.cms_collateral_id = cms_security.cms_collateral_id "
			+ "LEFT OUTER JOIN sci_pledgor_dtl "
			+ "ON sci_sec_pldgr_map.cms_pledgor_dtl_id = sci_pledgor_dtl.cms_pledgor_dtl_id "
			+ "WHERE source.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID "
			+ "AND cms_security_sub_type.SECURITY_SUB_TYPE_ID = cms_security.SECURITY_SUB_TYPE_ID "
			+ "AND cms_security.cms_collateral_id = lsm.cms_collateral_id "
			+ "AND lsm.cms_lsp_appr_lmts_id = lmt.cms_lsp_appr_lmts_id "
			+ "AND lmt.cms_limit_profile_id = aa.cms_lsp_lmt_profile_id ";

	// // TODO : need to verify if there's a problem is the same limit is
	// inserted and deleted and inserted again
	private static String SELECT_COLLATERAL_LIMIT = "SELECT cms_security.SCI_SECURITY_DTL_ID, cms_security.CMS_COLLATERAL_ID, \n"
			+ "  cms_security.SECURITY_SUB_TYPE_ID, cms_security.STATUS, \n"
			+ "  cms_security.IS_SECURITY_PERFECTED, cms_security.SECURITY_PERFECTION_DATE, \n"
			+ "  cms_security.SECURITY_LOCATION, cms_security.SECURITY_ORGANISATION, \n"
			+ "  cms_security_sub_type.SECURITY_TYPE_ID, cms_security_sub_type.SUBTYPE_NAME, \n"
			+ "  cms_security_sub_type.SECURITY_TYPE_NAME, \n"
			+ "  sci_lsp_appr_lmts.LMT_ID,sci_lsp_appr_lmts.FACILITY_NAME, \n"
			+ "  sci_lsp_appr_lmts.LMT_PRD_TYPE_VALUE, sci_lsp_appr_lmts.LMT_OUTER_LMT_ID, \n"
			+ "  sci_lsp_appr_lmts.CMS_OUTER_LMT_PROF_ID, sci_lsp_appr_lmts.CMS_LIMIT_PROFILE_ID, \n"
			+ "  sci_lsp_appr_lmts.account_type, sci_lsp_appr_lmts.LMT_CRRNCY_ISO_CODE, \n"
			+ "  sci_lsp_appr_lmts.CMS_OUTER_LMT_REF, sci_lsp_lmt_profile.LLP_BCA_REF_APPR_DATE, \n"
			+ "  sci_lsp_lmt_profile.LLP_BCA_REF_NUM, sci_le_sub_profile.LSP_ID, \n"
			+ "  sci_le_sub_profile.LSP_SHORT_NAME, sci_le_main_profile.LMP_LONG_NAME, \n"
			+ "  sci_le_main_profile.LMP_LE_ID, cms_limit_security_map.CMS_LSP_APPR_LMTS_ID, \n"
			+ "  cms_limit_security_map.UPDATE_STATUS_IND, \n"
			+ "  cms_limit_security_map.CHARGE_ID "
			+ "FROM cms_security_sub_type, CMS_SECURITY_SOURCE source, cms_security \n"
			+ "  LEFT OUTER JOIN cms_limit_security_map \n"
			+ "    ON cms_security.CMS_COLLATERAL_ID = cms_limit_security_map.CMS_COLLATERAL_ID \n"
			+ "  LEFT OUTER JOIN  sci_lsp_appr_lmts \n"
			+ "    ON cms_limit_security_map.CMS_LSP_APPR_LMTS_ID = sci_lsp_appr_lmts.CMS_LSP_APPR_LMTS_ID \n"
			+ "  LEFT OUTER JOIN sci_lsp_lmt_profile \n"
			+ "    ON sci_lsp_appr_lmts.CMS_LIMIT_PROFILE_ID = sci_lsp_lmt_profile.CMS_LSP_LMT_PROFILE_ID \n"
			+ "  LEFT OUTER JOIN sci_le_sub_profile"
			+ "    ON sci_lsp_lmt_profile.CMS_CUSTOMER_ID = sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID \n"
			+ "  LEFT OUTER JOIN sci_le_main_profile \n"
			+ "    ON sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID = sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID \n"
			+ "WHERE  source.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID \n"
			+ "  AND cms_security_sub_type.SECURITY_SUB_TYPE_ID = cms_security.SECURITY_SUB_TYPE_ID \n"
			+ "  AND  sci_lsp_appr_lmts.LMT_ID  != 'null' \n"	//Shiv 
			+ " AND sci_le_sub_profile.STATUS = 'ACTIVE' "
			+ "  AND cms_security.CMS_COLLATERAL_ID = ?" 
			+ " ORDER BY sci_lsp_appr_lmts.FACILITY_NAME desc ";

	private static final String COUNTRY_FILTER_SQL = "( SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? ) ";

	private static final String ORG_FILTER_SQL = "( SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ? ) ";

	private static final String SELECT_MULTI_SOURCE_SYSNAME_BY_COLL_ID = " SELECT DISTINCT source.SOURCE_SECURITY_ID ,"
			+ "(SELECT code.ENTRY_NAME " + "  FROM common_code_category_entry code"
			+ "  WHERE source.SOURCE_ID = code.ENTRY_CODE " + "  AND code.CATEGORY_CODE = '"
			+ ICMSConstant.CATEGORY_SEC_SOURCE + "'" + " ) AS SOURCE_SYSTEM_NAME " + " FROM cms_security_source source"
			+ " WHERE 0 = 0 " + "  AND source.CMS_COLLATERAL_ID  =  ? ";

	private static final String SELECT_CUST_WITHOUT_TRX = " SELECT mp.LMP_LE_ID ," + "       mp.LMP_LONG_NAME ,"
			+ "      lp.LLP_BCA_REF_NUM ," + "      lp.CMS_ORIG_COUNTRY ," + "      sp.CMS_LE_SUB_PROFILE_ID ,"
			+ "      sp.LSP_SHORT_NAME " + " FROM sci_le_main_profile mp, " + "      sci_le_sub_profile sp, "
			+ "      sci_lsp_lmt_profile lp" + " WHERE mp.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID"
			+ "   AND sp.CMS_LE_SUB_PROFILE_ID = lp.CMS_CUSTOMER_ID" + "   AND lp.CMS_LSP_LMT_PROFILE_ID = ? ";

	private static String SELECT_COL_PLEDGOR = "SELECT p.plg_legal_name, p.plg_pledgor_id, p.plg_le_id, p.update_status_ind AS plg_status, "
			+ "cp.sec_pledgor_relationship_value, p.cms_pledgor_dtl_id, cp.sec_pledgor_relationship_num, cp.cms_sec_pldgr_map_id, "
			+ "cp.update_status_ind AS map_status, cp.spm_map_id, cp.spm_sec_id, cp.spm_pledgor_id, p.cms_orig_country, p.plg_id_num_text, "
			+ "p.cms_orig_organisation, p.plg_legal_type_num, p.plg_legal_type_value, p.plg_dmcl_cntry_iso_code, p.plg_grnt_sgmnt_code_value "
			+ "FROM sci_sec_pldgr_map cp, sci_pledgor_dtl p "
			+ "WHERE cp.cms_pledgor_dtl_id = p.cms_pledgor_dtl_id AND cp.cms_collateral_id = ?";

	private static String SELECT_COLLATERAL_AND_LIMIT_CHARGE = "select chg.CHARGE_DETAIL_ID,chg.SECURITY_RANK,chg.CHARGE_AMOUNT,chg.CHARGE_CURRENCY_CODE,"
			+ "chg.PRIOR_CHARGE_AMOUNT,chg.PRIOR_CHARGE_CURRENCY,"
			+ "co.CMS_COLLATERAL_ID,co.FSV_CURRENCY,co.FSV FROM CMS_SECURITY co,CMS_CHARGE_DETAIL chg "
			+ "where co.CMS_COLLATERAL_ID = chg.CMS_COLLATERAL_ID and co.CMS_COLLATERAL_ID = ?";

	private static String SELECT_LIQUIDATION_NPL = " select xref.LSX_EXT_SYS_ACCT_NUM "
			+ "  from SCI_LSP_SYS_XREF xref,    " + "             SCI_LSP_LMTS_XREF_MAP map, "
			+ "             CMS_NPL npl,               " + "             SCI_LSP_APPR_LMTS lmt,     "
			+ "             CMS_LIMIT_SECURITY_MAP lsm " + " where map.CMS_LSP_SYS_XREF_ID = xref.CMS_LSP_SYS_XREF_ID "
			+ "   and lmt.CMS_LSP_APPR_LMTS_ID = map.CMS_LSP_APPR_LMTS_ID "
			+ "   and lsm.CMS_LSP_APPR_LMTS_ID = lmt.CMS_LSP_APPR_LMTS_ID " + "   and lsm.CMS_COLLATERAL_ID = ? "
			+ "   and npl.ACCOUNT_NUMBER = xref.LSX_EXT_SYS_ACCT_NUM     "
			+ "   and (npl.SOURCE_ID = xref.LSX_EXT_SYS_CODE_VALUE       "
			+ "        or npl.SOURCE_ID in (select entry_code            "
			+ "		                       from common_code_category_entry where category_code = '"
			+ ICMSConstant.SOURCE_SYSTEM_MARS_SP + "'))";

	private static String SELECT_COLLATERAL_SUBTYPE = "select SECURITY_SUB_TYPE_ID, SUBTYPE_NAME, SUBTYPE_DESCRIPTION, SECURITY_TYPE_ID, "
			+ "SECURITY_TYPE_NAME, MAX_PERCENT, GROUP_ID, VERSION_TIME, STATUS, SUBTYPE_STANDARDISED_APPROACH, "
			+ "SUBTYPE_FOUNDATION_IRB, SUBTYPE_ADVANCED_IRB from CMS_SECURITY_SUB_TYPE";

	private static String SELECT_PLEDGOR_SECURITY_COUNT = "select count(SCI_SEC_PLDGR_MAP.CMS_COLLATERAL_ID) as SECURITY_COUNT from SCI_SEC_PLDGR_MAP, CMS_SECURITY "
			+ "where SCI_SEC_PLDGR_MAP.CMS_COLLATERAL_ID = CMS_SECURITY.CMS_COLLATERAL_ID "
			+ "and STATUS = 'ACTIVE' and CMS_PLEDGOR_DTL_ID = ?";

	private static String SELECT_VALUER_BY_COUNTRY = "SELECT VALUER_CODE, VALUER_NAME FROM CMS_VALUER where VALUER_COUNTRY = ? ";

	private static String SELECT_ALL_COLLATERAL_TYPES = "select distinct (SECURITY_TYPE_ID), SECURITY_TYPE_NAME from CMS_SECURITY_SUB_TYPE where status = 'ACTIVE'";

	private static String SELECT_DOCUMENT_ID_COUNT_FOR_CREATE = "SELECT COUNT(*) AS DOCUMENT_ID_COUNT FROM CMS_INSURANCE_POLICY WHERE STATUS<>'DELETED' AND DOCUMENT_NO = ?";

	private static String SELECT_DOCUMENT_ID_COUNT_FOR_UPDATE = "SELECT COUNT(*) AS DOCUMENT_ID_COUNT FROM CMS_INSURANCE_POLICY WHERE STATUS<>'DELETED' AND DOCUMENT_NO = ? AND CMS_COLLATERAL_ID<>?";

	private static String UPDATE_SEC_PREFECTION_DATE_BY_AANUMBER = "update CMS_SECURITY set SECURITY_PERFECTION_DATE =? where SECURITY_PERFECTION_DATE is null and CMS_COLLATERAL_ID in ( "
			+ "select distinct( sec.CMS_COLLATERAL_ID ) from SCI_LSP_LMT_PROFILE pro, SCI_LSP_APPR_LMTS lmt, "
			+ "CMS_LIMIT_SECURITY_MAP map, CMS_SECURITY sec "
			+ "where pro.CMS_LSP_LMT_PROFILE_ID = lmt.CMS_LIMIT_PROFILE_ID "
			+ "and lmt.CMS_LSP_APPR_LMTS_ID = map.CMS_LSP_APPR_LMTS_ID "
			+ "and map.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID and pro.CMS_LSP_LMT_PROFILE_ID =? ) ";

	private static String SELECT_COLLATERAL_SEC_SUB_TYPE = "select SECURITY_SUB_TYPE_ID " + "from CMS_SECURITY "
			+ "where CMS_COLLATERAL_ID = ? ";
	
	private static String SELECT_COLLATERAL = "select SUBTYPE_NAME,TYPE_NAME " + "from CMS_SECURITY "
	+ "where CMS_COLLATERAL_ID = ? ";

	private static String SELECT_UNLINK_LIMIT_CHARGE = "SELECT s.SECURITY_SUB_TYPE_ID, c.CHARGE_DETAIL_ID, c.CMS_COLLATERAL_ID , m.CMS_LSP_APPR_LMTS_ID "
			+ "FROM CMS_SECURITY s, "
			+ "CMS_CHARGE_DETAIL c, "
			+ "SCI_LSP_APPR_LMTS m "
			+ "WHERE "
			+ "s.CMS_COLLATERAL_ID = c.CMS_COLLATERAL_ID "
			+ "AND s.STATUS = 'ACTIVE' "
			+ "AND c.STATUS = 'ACTIVE' "
			+ "AND m.CMS_LIMIT_STATUS = 'ACTIVE' "
			+ "AND NOT EXISTS( SELECT '1' FROM CMS_LIMIT_CHARGE_MAP "
			+ "WHERE CMS_COLLATERAL_ID = s.CMS_COLLATERAL_ID "
			+ "AND CMS_LSP_APPR_LMTS_ID = m.CMS_LSP_APPR_LMTS_ID "
			+ "AND STATUS <> 'DELETED') "
			+ "AND m.CMS_LSP_APPR_LMTS_ID = ? "
			+ "AND s.CMS_COLLATERAL_ID = ? "
			+ "ORDER BY CMS_COLLATERAL_ID";

	private static String SELECT_LIMIT_CHARGE_MAP_TO_UNLINK = "SELECT m.CHARGE_ID, c.CHARGE_DETAIL_ID, c.CMS_COLLATERAL_ID , m.CMS_LSP_APPR_LMTS_ID "
			+ "FROM CMS_SECURITY s, "
			+ "CMS_CHARGE_DETAIL c, "
			+ "CMS_LIMIT_SECURITY_MAP m "
			+ "WHERE "
			+ "s.CMS_COLLATERAL_ID = c.CMS_COLLATERAL_ID "
			+ "AND s.CMS_COLLATERAL_ID = m.CMS_COLLATERAL_ID "
			+ "AND m.UPDATE_STATUS_IND <> 'D' "
			+ "AND EXISTS( SELECT '1' FROM CMS_LIMIT_CHARGE_MAP "
			+ "WHERE CMS_COLLATERAL_ID = s.CMS_COLLATERAL_ID "
			+ "AND CMS_LSP_APPR_LMTS_ID = m.CMS_LSP_APPR_LMTS_ID "
			+ "AND STATUS='ACTIVE' ) "
			+ "AND ( s.SECURITY_SUB_TYPE_ID not like 'PT%' AND s.SECURITY_SUB_TYPE_ID not like 'OT%' "
			+ "AND s.SECURITY_SUB_TYPE_ID<>'AB100' AND s.SECURITY_SUB_TYPE_ID<>'AB109' "
			+ "AND s.SECURITY_SUB_TYPE_ID<>'AB103' AND s.SECURITY_SUB_TYPE_ID<>'AB101' "
			+ "AND s.SECURITY_SUB_TYPE_ID<>'AB102' ) "
			+ "AND m.CMS_LSP_APPR_LMTS_ID = ? "
			+ "AND m.CMS_COLLATERAL_ID = ? " + "AND s.CMS_COLLATERAL_ID = ? " + "ORDER BY CMS_COLLATERAL_ID";

	private static final String DOCUMENT_ID_COUNT = "DOCUMENT_ID_COUNT";

	public static final String COL_SUBTYPE_TABLE = "CMS_SECURITY_SUB_TYPE";

	public static final String COL_SUBTYPE_CODE = "SECURITY_SUB_TYPE_ID";

	public static final String COL_SUBTYPE_NAME = "SUBTYPE_NAME";

	public static final String COL_SUBTYPE_DESC = "SUBTYPE_DESCRIPTION";

	public static final String COL_SUBTYPE_STANDARDISED_APPROACH = "SUBTYPE_STANDARDISED_APPROACH";

	public static final String COL_SUBTYPE_FOUNDATION_IRB = "SUBTYPE_FOUNDATION_IRB";

	public static final String COL_SUBTYPE_ADVANCED_IRB = "SUBTYPE_ADVANCED_IRB";

	public static final String COL_TYPE_CODE = "SECURITY_TYPE_ID";

	public static final String COL_TYPE_NAME = "SECURITY_TYPE_NAME";

	public static final String COL_SUBTYPE_MAX_PERCENT = "MAX_PERCENT";

	public static final String COL_SUBTYPE_GROUP_ID = "GROUP_ID";

	public static final String COL_SUBTYPE_VERSION_TIME = "VERSION_TIME";

	public static final String COL_SUBTYPE_STATUS = "STATUS";

	// table name and column names for limit security map.
	public static final String LIMIT_SECURITY_MAP_TABLE = "CMS_LIMIT_SECURITY_MAP";

	public static final String LIMIT_MAP_LIMIT_ID = "CMS_LSP_APPR_LMTS_ID";

	public static final String LIMIT_MAP_COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String LIMIT_MAP_CUST_ID = "SCI_LAS_LSP_ID";

	public static final String LIMIT_MAP_CUST_LE_ID = "SCI_LAS_LE_ID";

	public static final String LIMIT_MAP_LMT_PROD_TYPE = "LMT_TYPE_VALUE";

	public static final String MAP_SCI_LIMIT_ID = ICollateralDAOConstants.MAP_SCI_LIMIT_ID;

	public static final String MAP_SCI_STATUS = ICollateralDAOConstants.MAP_SCI_STATUS;

	public static final String MAP_ID = ICollateralDAOConstants.MAP_ID;

	public static final String SCI_MAP_ID = "SCI_LAS_SYS_GEN_ID";

	// table name and column names for security.
	public static final String SECURITY_TABLE = "CMS_SECURITY";

	public static final String COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String SCI_SECURITY_ID = "SCI_SECURITY_DTL_ID";

	public static final String SOURCE_SYSTEM_NAME = "SOURCE_SYSTEM_NAME";

	public static final String SECURITY_LOCATION = "SECURITY_LOCATION";

	public static final String SECURITY_ORGANISATION = "SECURITY_ORGANISATION";

	public static final String SECURITY_SUBTYPE_CODE = "SECURITY_SUB_TYPE_ID";

	public static final String TYPE_NAME = "TYPE_NAME";

	public static final String SUBTYPE_NAME = "SUBTYPE_NAME";

	private static final String SECURITY_STATUS = ICollateralDAOConstants.SECURITY_STATUS;

	private static final String SECURITY_IS_PERFECTED = ICollateralDAOConstants.SECURITY_IS_PERFECTED;

	private static final String CUSTOMER_NAME = "LSP_SHORT_NAME";

	private static final String LE_CUST_NAME = "LMP_LONG_NAME";

	private static final String LE_ID = "LMP_LE_ID";

	private static final String LP_BCA_REF_NUM = "LLP_BCA_REF_NUM";

	private static final String LP_BCA_APPR_DATE = "LLP_BCA_REF_APPR_DATE";

	private static final String APPR_LMT_LP_CMS_ID = "CMS_LIMIT_PROFILE_ID";

	private static final String APPR_LMT_PROD_TYPE_VALUE = "LMT_PRD_TYPE_VALUE";

	private static final String APPR_LMT_OUTER_ID = "LMT_OUTER_LMT_ID";

	private static final String APPR_LMT_OUTER_PROF_ID = "CMS_OUTER_LMT_PROF_ID";

	private static final String APPR_LMT_SCI_OUTER_ID = "CMS_OUTER_LMT_REF";

	private static final String APPR_LMT_LMT_ID = "LMT_ID";

	private static final String FACILITY_NAME = "FACILITY_NAME";
	
	private static final String PLEDGOR_MAP_SECURITY_COUNT = "SECURITY_COUNT";

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}

	/**
	 * Default Constructor
	 */
	public CollateralDAO() {
	}

	/**
	 * to get CollateralLimitChanges
	 * @param collateralID
	 * @return
	 * @throws SearchDAOException
	 */

	public ICollateral getCollateralLimitChanges(long collateralID) throws SearchDAOException {

		final Set fsvAmountSet = new HashSet(1);
		List limitChargeList = getJdbcTemplate().query(SELECT_COLLATERAL_AND_LIMIT_CHARGE,
				new Object[] { new Long(collateralID) }, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ILimitCharge lmtChg = new OBLimitCharge();
						lmtChg.setChargeDetailID(rs.getLong("CHARGE_DETAIL_ID"));
						Amount chgAmt = null;
						Amount chgPrAmt = null;

						BigDecimal amtChg = rs.getBigDecimal("CHARGE_AMOUNT");
						BigDecimal amtPr = rs.getBigDecimal("PRIOR_CHARGE_AMOUNT");
						BigDecimal amtFsv = rs.getBigDecimal("FSV");
						if (amtChg != null) {
							chgAmt = new Amount(amtChg.doubleValue(), rs.getString("CHARGE_CURRENCY_CODE"));
						}

						if (amtPr != null) {
							chgPrAmt = new Amount(amtPr.doubleValue(), rs.getString("PRIOR_CHARGE_CURRENCY"));
						}

						lmtChg.setChargeAmount(chgAmt);
						lmtChg.setPriorChargeAmount(chgPrAmt);
						lmtChg.setSecurityRank(rs.getInt("SECURITY_RANK"));

						if (amtFsv != null && fsvAmountSet.isEmpty()) {
							Amount fsvAmt = new Amount(amtFsv.doubleValue(), rs.getString("FSV_CURRENCY"));
							fsvAmountSet.add(fsvAmt);
						}

						return lmtChg;
					}
				});

		if (!limitChargeList.isEmpty()) {
			ICollateral col = new OBCollateral();
			col.setCollateralID(collateralID);

			if (!fsvAmountSet.isEmpty()) {
				col.setFSV(((Amount[]) fsvAmountSet.toArray(new Amount[0]))[0]);
			}

			col.setLimitCharges((ILimitCharge[]) limitChargeList.toArray(new OBLimitCharge[0]));
			return col;
		}

		return null;
	}

	public Long getUnlinkChargeDetailID(long cmsLimitID, long collateralID) throws SearchDAOException {

		final List objectCountList = new ArrayList();

		Long chargeId = (Long) getJdbcTemplate().query(SELECT_UNLINK_LIMIT_CHARGE,
				new Object[] { new Long(cmsLimitID), new Long(collateralID) }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Long tempChargeId = null;

						while (rs.next()) {
							tempChargeId = new Long(rs.getLong("CHARGE_DETAIL_ID"));
							objectCountList.add(new Object());
						}

						return tempChargeId;
					}
				});

		if (objectCountList.size() == 1 && chargeId != null) {
			return chargeId;
		}

		return null;
	}

	public ILimitChargeMap getLimitChargeMapToUnlink(final long cmsLimitID, final long collateralID)
			throws SearchDAOException {

		DefaultLogger.debug(this, "param: cmsLimtID=" + cmsLimitID);
		DefaultLogger.debug(this, "param: collateralID=" + collateralID);

		final List objectCountList = new ArrayList();

		ILimitChargeMap limitChargeMap = (ILimitChargeMap) getJdbcTemplate().query(SELECT_LIMIT_CHARGE_MAP_TO_UNLINK,
				new Object[] { new Long(cmsLimitID), new Long(collateralID), new Long(collateralID) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						ILimitChargeMap lmtChargeMap = null;
						while (rs.next()) {
							long chargeDetailID = rs.getLong("CHARGE_DETAIL_ID");
							long chargeID = rs.getLong("CHARGE_ID");

							objectCountList.add(new Object());
							lmtChargeMap = new OBLimitChargeMap();
							lmtChargeMap.setCollateralID(collateralID);
							lmtChargeMap.setLimitID(cmsLimitID);
							lmtChargeMap.setChargeID(chargeID);
							lmtChargeMap.setChargeDetailID(chargeDetailID);
						}

						return lmtChargeMap;
					}
				});

		if (objectCountList.size() == 1 && limitChargeMap != null) {
			return limitChargeMap;
		}

		return null;
	}

	/**
	 * Get if BCA Completed for the given collateral id. No implementation.
	 * 
	 * @param collateralID collateral id
	 * @return true if the bca is completed, otherwise false.
	 * @throws SearchDAOException on error getting the bca flag
	 */
	public boolean isBCACompleted(long collateralID) throws SearchDAOException {

		return true;
	}

	/**
	 * Search collateral based on the given criteria.
	 * 
	 * @param criteria of type CollateralSearchCriteria
	 * @return search result that contains a list of OBCollateralSearchResult
	 * @throws SearchDAOException on error searching the collateral
	 */
	public SearchResult searchCollateral(final CollateralSearchCriteria criteria) throws SearchDAOException {

		if (criteria == null) {
			throw new SearchDAOException("CollateralSearchCriteria is null.");
		}
		
		if (criteria.getSecurityID() != null && (!criteria.getSecurityID().equals("")) && 
				!(Validator.checkNumber(criteria.getSecurityID(), true, 0.0, 99999999999999999999.00)).equals(Validator.ERROR_NONE)) {
			return new SearchResult();
		}

		String sql = null;
		List argList = new ArrayList();
		try {
			sql = getCollateralSQL(criteria, argList);
		}
		catch (DAPFilterException e) {
			DefaultLogger.warn(this, "Data access profile not matched, 'null' will be returned.", e);
			return null;
		}

		if (criteria.isRequiredPagination()) {
			PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(criteria,
					this.recordsPerPageForPagination, this.totalPageForPagination);

			sql = this.paginationUtil.formPagingQuery(sql, pagingBean);
		}
		else {
			sql = this.paginationUtil.formPagingQuery(sql, null);
		}
		System.out.println("Search=>Security=>Query=>"+sql);
//		System.out.println("Search=>Security=>argList.toArray()=>"+argList.toArray());
		SearchResult result = (SearchResult) getJdbcTemplate().query(sql, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				SearchResult searchResult = processCollateralResultSet(criteria, rs);

				return searchResult;
			}
		});

		setCustomerAndSourceSystemDetails(result);

		return result;
	}

	/**
	 * Get all collateral sub types given the type code.
	 * 
	 * @param typeCode security type code
	 * @return a list of collateral sub types
	 * @throws SearchDAOException on error searching the collateral subtypes
	 */
	public ICollateralSubType[] getCollateralSubTypesByTypeCode(String typeCode) throws SearchDAOException {
		String sql = constructColSubTypeByTypeCodeSQL(typeCode);

		return (ICollateralSubType[]) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processColSubTypeByTypeCodeResultSet(rs);
			}
		});
	}

	/**
	 * Get the collateral sub type given the sub type code.
	 * 
	 * @param subTypeCode security sub type code
	 * @return the collateral sub type
	 * @throws SearchDAOException on error searching the collateral subtype
	 */
	public ICollateralSubType getCollateralSubTypesBySubTypeCode(String subTypeCode) throws SearchDAOException {
		String sql = constructColSubTypeBySubTypeCodeSQL(subTypeCode);

		ICollateralSubType[] result = (ICollateralSubType[]) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processColSubTypeByTypeCodeResultSet(rs);
			}
		});

		return result[0];
	}

	/**
	 * Get the collateral sub type ID by collateral ID.
	 * 
	 * @param colID collateral ID
	 * @return the collateral sub type
	 * @throws SearchDAOException on error searching the collateral subtype
	 */
	public ICollateralSubType getCollateralSubTypeByCollateralID(Long colID) throws SearchDAOException {
		if (colID == null) {
			return null;
		}

		String sql = SELECT_COLLATERAL_SEC_SUB_TYPE;

		ICollateralSubType resultSubType = (ICollateralSubType) getJdbcTemplate().query(sql, new Object[] { colID },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							ICollateralSubType subType = new OBCollateralSubType();
							String subTypeID = rs.getString("SECURITY_SUB_TYPE_ID");

							subType.setTypeCode(subTypeID.substring(0, 2));
							subType.setSubTypeCode(subTypeID);

							return subType;
						}
						return null;
					}
				});

		return resultSubType;

	}

	public ICollateralSubType getCollateralByCollateralID(Long colID) throws SearchDAOException {
		if (colID == null) {
			return null;
		}

		String sql = SELECT_COLLATERAL;

		ICollateralSubType resultSubType = (ICollateralSubType) getJdbcTemplate().query(sql, new Object[] { colID },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							ICollateralSubType subType = new OBCollateralSubType();
							String subTypeName = rs.getString("SUBTYPE_NAME");
							String typeName = rs.getString("TYPE_NAME");
							subType.setSubTypeName(subTypeName);
							subType.setTypeName(typeName);
							return subType;
						}
						return null;
					}
				});

		return resultSubType;

	}
	
	/**
	 * Get a list of collateral pledgors.
	 * 
	 * @param collateralID cms collateral id
	 * @return a list of ICollateralPledgor objects
	 * @throws SearchDAOException on error getting the collateral pledgors
	 */
	public ICollateralPledgor[] getCollateralPledgors(long collateralID) throws SearchDAOException {
		List collateralPledgorList = getJdbcTemplate().query(SELECT_COL_PLEDGOR,
				new Object[] { new Long(collateralID) }, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ICollateralPledgor collateralPledgor = new OBCollateralPledgor();
						collateralPledgor.setMapID(rs.getLong("CMS_SEC_PLDGR_MAP_ID"));
						collateralPledgor.setSCIPledgorID(rs.getLong("SPM_PLEDGOR_ID"));
						collateralPledgor.setSCIPledgorMapStatus(rs.getString("MAP_STATUS"));
						collateralPledgor.setSCIMapSysGenID(rs.getLong("SPM_MAP_ID"));
						collateralPledgor.setSCISecID(rs.getString("SPM_SEC_ID"));
						collateralPledgor.setLegalID(rs.getString("PLG_LE_ID"));
						collateralPledgor.setPledgorName(rs.getString("PLG_LEGAL_NAME"));
						collateralPledgor.setPledgorStatus(rs.getString("PLG_STATUS"));
						collateralPledgor.setPledgorRelship(rs.getString("SEC_PLEDGOR_RELATIONSHIP_VALUE"));
						collateralPledgor.setSysGenPledgorID(rs.getLong("PLG_PLEDGOR_ID"));
						collateralPledgor.setPledgorRelshipID(rs.getString("SEC_PLEDGOR_RELATIONSHIP_NUM"));
						collateralPledgor.setPledgorID(rs.getLong("CMS_PLEDGOR_DTL_ID"));
						collateralPledgor.setLegalTypeID(rs.getLong("PLG_LEGAL_TYPE_NUM"));
						collateralPledgor.setLegalType(rs.getString("PLG_LEGAL_TYPE_VALUE"));
						collateralPledgor.setDomicileCountry(rs.getString("PLG_DMCL_CNTRY_ISO_CODE"));
						collateralPledgor.setSegmentCode(rs.getString("PLG_GRNT_SGMNT_CODE_VALUE"));
						collateralPledgor.setPlgIdNumText(rs.getString("PLG_ID_NUM_TEXT"));

						OBBookingLocation bookingLocation = new OBBookingLocation();
						bookingLocation.setCountryCode(rs.getString("CMS_ORIG_COUNTRY"));
						bookingLocation.setOrganisationCode(rs.getString("CMS_ORIG_ORGANISATION"));
						collateralPledgor.setBookingLocation(bookingLocation);

						return collateralPledgor;
					}
				});

		return (ICollateralPledgor[]) collateralPledgorList.toArray(new OBCollateralPledgor[0]);

	}

	/**
	 * Get all collateral types avaiable in CMS based on the criteria.
	 * 
	 * @return a list of collateral types
	 * @throws SearchDAOException on error searching the collateral type
	 */
	public ICollateralType[] getAllCollateralTypes() throws SearchDAOException {
		String sql = SELECT_ALL_COLLATERAL_TYPES;

		return (ICollateralType[]) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processColTypeResultSet(rs);
			}
		});
	}

	/**
	 * Get the number of securities a pledgor has.
	 * 
	 * @param pledgorID pledgor id
	 * @return security count
	 * @throws SearchDAOException on error getting the security count
	 */
	public int getCollateralCountForPledgor(long pledgorID) throws SearchDAOException {
		Integer count = (Integer) getJdbcTemplate().query(SELECT_PLEDGOR_SECURITY_COUNT,
				new Object[] { new Long(pledgorID) }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return new Integer(rs.getInt(PLEDGOR_MAP_SECURITY_COUNT));
						}
						return new Integer(0);
					}
				});
		return count.intValue();

	}

	public Map getCollateralValuer(String countryCode) throws SearchDAOException {

		return (Map) getJdbcTemplate().query(SELECT_VALUER_BY_COUNTRY, new Object[] { countryCode },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						return processColValuer(rs);
					}
				});
	}

	/**
	 * to get DocumentNoCount
	 * @param docNo
	 * @param isCreate
	 * @param insPolicyId
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */
	public int getDocumentNoCount(String docNo, boolean isCreate, long insPolicyId, long collateralId)
			throws SearchDAOException {
		List argList = new ArrayList();
		argList.add(docNo);
		String sql = (isCreate) ? SELECT_DOCUMENT_ID_COUNT_FOR_CREATE : SELECT_DOCUMENT_ID_COUNT_FOR_UPDATE;

		if (!isCreate && (collateralId != ICMSConstant.LONG_INVALID_VALUE)) {
			argList.add(new Long(collateralId));
		}

		Integer count = (Integer) getJdbcTemplate().query(sql, argList.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Integer(rs.getInt(DOCUMENT_ID_COUNT));
				}

				return new Integer(0);
			}
		});

		return count.intValue();
	}

	private Map processColValuer(ResultSet rs) throws SQLException {
		Map result = new HashMap();

		List valuerCode = new ArrayList();
		List valuerName = new ArrayList();

		while (rs.next()) {
			valuerCode.add(rs.getString("VALUER_CODE"));
			valuerName.add(rs.getString("VALUER_NAME"));
		}

		result.put("valuerCode", valuerCode);
		result.put("valuerName", valuerName);
		return result;
	}

	/**
	 * Helper method to construct query for getting collateral subtypes by type
	 * code.
	 * 
	 * @return sql query
	 */
	protected String constructColSubTypeByTypeCodeSQL(String typeCode) {
		if (typeCode == null) {
			typeCode = "";
		}

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_COLLATERAL_SUBTYPE);
		buf.append(" where ");
		buf.append(COL_SUBTYPE_GROUP_ID);
		buf.append(" = (");
		buf.append("select ");
		buf.append("max (");
		buf.append(COL_SUBTYPE_GROUP_ID);
		buf.append(") from ");
		buf.append(COL_SUBTYPE_TABLE);
		buf.append(" where ");
		buf.append(COL_TYPE_CODE);
		buf.append(" = '");
		buf.append(typeCode);
		buf.append("')");
		buf.append(" and ");
		buf.append(COL_TYPE_CODE);
		buf.append(" ='");
		buf.append(typeCode);
		buf.append("' and ");
		buf.append("STATUS");
		buf.append(" ='");
		buf.append("ACTIVE");
		buf.append("' order by ");
		buf.append(COL_SUBTYPE_CODE);

		return buf.toString();
	}

	/**
	 * Helper method to construct query for getting collateral subtypes by sub
	 * type code.
	 * 
	 * @return sql query
	 */
	protected String constructColSubTypeBySubTypeCodeSQL(String subTypeCode) {
		if (subTypeCode == null) {
			subTypeCode = "";
		}

		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_COLLATERAL_SUBTYPE);
		buf.append(" where ");
		buf.append(COL_SUBTYPE_CODE);
		buf.append(" ='");
		buf.append(subTypeCode);
		buf.append("'");

		return buf.toString();
	}

	/**
	 * Get search security query with the pledgor filter.
	 * @param criteria
	 * @param buf
	 * @param params
	 * @throws DAPFilterException
	 * @throws SQLException
	 */
	private void getSecurityLinkMb(CollateralSearchCriteria criteria, StringBuffer buf, List argList)
			throws DAPFilterException {

		buf.append(SELECT_COLLATERAL_MAIN_BORROWER);

		appendCommonSearchFilter(criteria, buf, argList);

		if (criteria.getAdvanceSearch()) {
			appendAdvanceSearchTypeSQL(criteria, buf, argList);
		}
	}

	/**
	 * Get search security query with the pledgor filter.
	 * @param criteria
	 * @param buf
	 * @param params
	 * @throws DAPFilterException
	 * @throws SQLException
	 */
	private void getSecurityLinkPledgor(CollateralSearchCriteria criteria, StringBuffer buf, List argList)
			throws DAPFilterException {

		buf.append(SELECT_COLLATERAL_PLEDGOR);

		appendCommonSearchFilter(criteria, buf, argList);

		// append adv search filter
		if (criteria.getAdvanceSearch()) {
			appendPledgorAdvanceSearchSQL(criteria, buf, argList);
			appendAdvanceSearchTypeSQL(criteria, buf, argList);
		}
	}

	/**
	 * Helper method to append the common search filter
	 * @param criteria
	 * @param buf
	 * @param params
	 * @throws SQLException
	 */
	private void appendCommonSearchFilter(CollateralSearchCriteria criteria, StringBuffer buf, List argList) {
		if (!isEmptyOrNull(criteria.getSecurityID())) {
			if (ICMSConstant.SEARCH_TYPE_LOS.equals(criteria.getSecuritySearchType())) {
				buf.append(" AND source.SOURCE_SECURITY_ID = ? ");
				argList.add(criteria.getSecurityID().toUpperCase());
			}
			else if (ICMSConstant.SEARCH_TYPE_HOST.equals(criteria.getSecuritySearchType())) {
				buf.append(" AND cms_security.sci_security_dtl_id = ? ");
				argList.add(criteria.getSecurityID().toUpperCase());
			}
			else if (ICMSConstant.SEARCH_TYPE_CMS.equals(criteria.getSecuritySearchType())) {
				buf.append(" AND cms_security.cms_collateral_id = ? ");
				argList.add(new Long(criteria.getSecurityID()));
			}
			else {
				DefaultLogger.error(this, "Unknown security search type: " + criteria.getSecuritySearchType());
			}
		}

		if (!isEmptyOrNull(criteria.getSecurityType())) {
			buf.append(" AND cms_security_sub_type.SECURITY_TYPE_ID = ? ");
			argList.add(criteria.getSecurityType());
		}

		if (!isEmptyOrNull(criteria.getSecuritySubType())) {
			buf.append(" AND cms_security.SECURITY_SUB_TYPE_ID = ? ");
			argList.add(criteria.getSecuritySubType());
		}

		if (!isEmptyOrNull(criteria.getSecurityLoc())) {
			buf.append(" AND cms_security.SECURITY_LOCATION = ? ");
			argList.add(criteria.getSecurityLoc());
		}

		if (!isEmptyOrNull(criteria.getBranchCode())) {
			buf.append(" AND cms_security.SECURITY_ORGANISATION = ? ");
			argList.add(criteria.getBranchCode());
		}
		
		if (!isEmptyOrNull(criteria.getSecurityID())) {
			buf.append(" AND cms_security.CMS_COLLATERAL_ID= ? ");
			argList.add(criteria.getSecurityID());
		}

		buf.append(" AND cms_security.STATUS <> ? ");
		//lines added by dattatray thorat - start
		buf.append(" AND sci_lsp_appr_lmts.LMT_ID  != 'null' ");
		//lines added by dattatray thorat - end
		
		argList.add(ICMSConstant.STATE_DELETED);
	}

	private void appendPledgorAdvanceSearchSQL(CollateralSearchCriteria criteria, StringBuffer buf, List argList) {

		// used for advance search for pledgor
		String customerName = criteria.getCustomerName();
		String legalIDType = criteria.getLeIDType();
		String legalID = criteria.getLegalID();
		String idNO = criteria.getIdNO();

		if (!isEmptyOrNull(customerName)) {
			buf.append(" AND sci_pledgor_dtl.PLG_LEGAL_NAME like ? ");
			argList.add(customerName.trim().toUpperCase() + "%");
		}

		if (!isEmptyOrNull(legalID) && !isEmptyOrNull(legalIDType)) {
			buf.append(" AND sci_pledgor_dtl.PLG_LE_ID = ? ");
			argList.add(legalID.trim());

			buf.append(" AND sci_pledgor_dtl.PLG_LE_ID_SRC_VALUE = ? ");
			argList.add(legalIDType.trim());
		}

		if (!isEmptyOrNull(idNO)) {
			buf.append(" AND ( sci_pledgor_dtl.PLG_ID_NUM_TEXT = ? ");
			argList.add(idNO.trim().toUpperCase());
			buf.append(" ) ");
		}
	}

	/**
	 * Helper method to append the DAP filter
	 * @param criteria
	 * @param buf
	 * @param params
	 * @throws DAPFilterException
	 * @throws SQLException
	 */
	private void appendDAPFilter(CollateralSearchCriteria criteria, StringBuffer buf, List argList)
			throws DAPFilterException {
		ITrxContext ctx = criteria.getTrxContext();
		if (ctx != null) {
			ITeam team = ctx.getTeam();
			String[] country = team.getCountryCodes();
			String secLoc = criteria.getSecurityLoc();
			if ((country == null) || (country.length == 0)
					|| !SecurityAccessValidationUtils.isTeamHasAccessOfCountry(team, secLoc)) {
				throw new DAPFilterException("Country List in Team is empty.");
			}
			else {
				getDAPFilterSQL(buf, argList, team.getTeamID());
			}
		}
	}

	/**
	 * Helper method to get the current DAP filter based on security location
	 * only
	 * @param criteria
	 * @param buf
	 * @param params
	 * @throws DAPFilterException
	 * @throws SQLException
	 */
	private void getDAPFilterSQL(StringBuffer buf, List argList, long teamID) {
		buf.append(" AND ( cms_security.SECURITY_LOCATION IN ");
		buf.append(COUNTRY_FILTER_SQL);
		argList.add(new Long(teamID));

	//	buf.append(" AND cms_security.security_organisation in  ");
	//	buf.append(ORG_FILTER_SQL);
		argList.add(new Long(teamID));
		buf.append(" ) ");
	}

	/**
	 * Helper method to get adv search filter
	 */
	private void appendAdvanceSearchTypeSQL(CollateralSearchCriteria criteria, StringBuffer buf, List argList) {

		String securityType = criteria.getSecurityType();

		if ((securityType != null) && ICMSConstant.SECURITY_TYPE_ASSET.equals(securityType.trim())) {
			CollateralAdvancedSearchQueryHelper.getAssetAdvancedSearchQuery(criteria, buf, argList);
		}
		else if ((securityType != null) && ICMSConstant.SECURITY_TYPE_GUARANTEE.equals(securityType.trim())) {
			CollateralAdvancedSearchQueryHelper.getGteAdvSearchSQL(criteria, buf, argList);
		}
		else if ((securityType != null) && ICMSConstant.SECURITY_TYPE_PROPERTY.equals(securityType.trim())) {
			CollateralAdvancedSearchQueryHelper.getPropertyAdvSearchSQL(criteria, buf, argList);
		}
	}

	private boolean isSearchByPledgor(CollateralSearchCriteria criteria) {

		// used for advance search for pledgor
		String customerName = criteria.getCustomerName();
		String legalIDType = criteria.getLeIDType();
		String legalID = criteria.getLegalID();
		String idNO = criteria.getIdNO();

		return (!isEmpty(customerName) || !isEmpty(legalIDType) || !isEmpty(legalID) || !isEmpty(idNO));
	}

	private boolean isEmptyOrNull(String value) {
		if (value == null) {
			return true;
		}
		if ("".equals(value.trim())) {
			return true;
		}
		if ("null".equalsIgnoreCase(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to process the result set of collateral type.
	 * 
	 * @param rs result set
	 * @return a list of collateral types
	 * @throws SQLException on error processing the result set
	 */
	private ICollateralType[] processColTypeResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			OBCollateralSubType subType = new OBCollateralSubType();
			subType.setTypeCode(rs.getString(COL_TYPE_CODE));
			subType.setTypeName(rs.getString(COL_TYPE_NAME));
			arrList.add(subType);
		}
		return (OBCollateralSubType[]) arrList.toArray(new OBCollateralSubType[0]);
	}

	/**
	 * Helper method to process the result set of collateral subtype by type
	 * code.
	 * 
	 * @param rs result set
	 * @return a list of collateral subtypes
	 * @throws SQLException on error processing the result set
	 */
	private ICollateralSubType[] processColSubTypeByTypeCodeResultSet(ResultSet rs) throws SQLException {
		ArrayList arrList = new ArrayList();

		while (rs.next()) {
			OBCollateralSubType subType = new OBCollateralSubType();
			subType.setSubTypeCode(rs.getString(COL_SUBTYPE_CODE));
			subType.setSubTypeName(rs.getString(COL_SUBTYPE_NAME));
			subType.setSubTypeDesc(rs.getString(COL_SUBTYPE_DESC));

			String subTypeStandardisedApproach = rs.getString(COL_SUBTYPE_STANDARDISED_APPROACH);
			if ((subTypeStandardisedApproach != null) && subTypeStandardisedApproach.equals(ICMSConstant.TRUE_VALUE)) {
				subType.setSubTypeStandardisedApproach(true);
			}
			else {
				subType.setSubTypeStandardisedApproach(false);
			}

			String subTypeFoundationIRB = rs.getString(COL_SUBTYPE_FOUNDATION_IRB);
			if ((subTypeFoundationIRB != null) && subTypeFoundationIRB.equals(ICMSConstant.TRUE_VALUE)) {
				subType.setSubTypeFoundationIRB(true);
			}
			else {
				subType.setSubTypeFoundationIRB(false);
			}

			String subTypeAdvancedIRB = rs.getString(COL_SUBTYPE_ADVANCED_IRB);
			if ((subTypeAdvancedIRB != null) && subTypeAdvancedIRB.equals(ICMSConstant.TRUE_VALUE)) {
				subType.setSubTypeAdvancedIRB(true);
			}
			else {
				subType.setSubTypeAdvancedIRB(false);
			}

			subType.setTypeCode(rs.getString(COL_TYPE_CODE));
			subType.setTypeName(rs.getString(COL_TYPE_NAME));
			subType.setGroupID(rs.getLong(COL_SUBTYPE_GROUP_ID));
			subType.setMaxValue(rs.getDouble(COL_SUBTYPE_MAX_PERCENT));
			subType.setVersionTime(rs.getLong(COL_SUBTYPE_VERSION_TIME));
			subType.setStatus(rs.getString(COL_SUBTYPE_STATUS));
			arrList.add(subType);
		}
		return (OBCollateralSubType[]) arrList.toArray(new OBCollateralSubType[0]);
	}

	/**
	 * Helper method to process the result set.
	 * 
	 * @param criteria collateral search criteria
	 * @param resultSet result set
	 * @return SearchResult
	 * @throws SQLException on error processing the result set
	 */
	protected SearchResult processCollateralResultSet(CollateralSearchCriteria criteria, ResultSet resultSet)
			throws SQLException {
		boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;

		final List collectionList = new ArrayList();
		final Vector resultList = new Vector();
		final List secIdList = new ArrayList();
		System.out.println("CollateraDAO.java=> SearchResult processCollateralResultSet=> Insidess");
		// int startIndex = criteria.getStartIndex();
		// int requiredItems = criteria.getNItems();
		//
		// // 10 pages
		// int maxItems = (requiredItems == Integer.MAX_VALUE) ?
		// Integer.MAX_VALUE : requiredItems * 10;
		// if (startIndex >= maxItems) {
		// maxItems = ((int) java.lang.Math.ceil(startIndex + 1 / maxItems)) *
		// maxItems;
		// }
		// int endIndex = (maxItems == Integer.MAX_VALUE) ? Integer.MAX_VALUE :
		// (startIndex + 10);
		int count = 0;

		// prepare result set into both list, avoid jdbc call within result set
		// context.
		List securityIdList = new ArrayList();
		List collateralIdList = new ArrayList();
		while (resultSet.next()) {
			securityIdList.add(resultSet.getString(SCI_SECURITY_ID));
			collateralIdList.add(new Long(resultSet.getLong(COLLATERAL_ID)));
		}
		System.out.println("CollateraDAO.java=> SearchResult processCollateralResultSet=> Going inside securityIdList");
		for (int i = 0; i < securityIdList.size(); i++) {

			if (count < this.recordsPerPageForPagination || !criteria.isRequiredPagination()) {

				final String securityId = (String) securityIdList.get(i);
				final Long collateralId = (Long) collateralIdList.get(i);

				getJdbcTemplate().query(SELECT_COLLATERAL_LIMIT, new Object[] { collateralId },
						new ResultSetExtractor() {

							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								List limitObjList = new ArrayList();

								while (rs.next()) {

									Long limitID = new Long(rs.getLong(LIMIT_MAP_LIMIT_ID));

									OBCollateralLimitResult limitObj = new OBCollateralLimitResult();
									limitObj.setSecurityID(securityId);
									limitObj.setCollateralID(rs.getLong(COLLATERAL_ID));
									limitObj.setLegalName(rs.getString(LE_CUST_NAME));
									limitObj.setLegalID(rs.getString(LE_ID));
									limitObj.setInstructionRefNo(rs.getString(LP_BCA_REF_NUM));
									limitObj.setSCILimitMapStatus(rs.getString(MAP_SCI_STATUS));

									java.sql.Date apprDate1 = rs.getDate(LP_BCA_APPR_DATE);
									if (apprDate1 != null) {
										limitObj.setInstructionApprovedDate(new Date(apprDate1.getTime()));
									}

									String SCILimitID = rs.getString(APPR_LMT_LMT_ID);

									limitObj.setSCILimitID(SCILimitID);
									
									String facilityName = rs.getString(FACILITY_NAME);
									
									limitObj.setSourceSystemName(facilityName);

									limitObj.setCustomerName(rs.getString(CUSTOMER_NAME));

									String productType = "";
									if (PropertiesConstantHelper.isProductDescSpecialHandling()) {
										String accountType = rs.getString("ACCOUNT_TYPE");
										productType = (accountType != null) ? (rs.getString(APPR_LMT_PROD_TYPE_VALUE)
												+ "|" + rs.getString("LMT_CRRNCY_ISO_CODE") + "|" + accountType) : (rs
												.getString(APPR_LMT_PROD_TYPE_VALUE)
												+ "|" + rs.getString("LMT_CRRNCY_ISO_CODE"));
									}
									else {
										productType = rs.getString(APPR_LMT_PROD_TYPE_VALUE);
									}

									limitObj.setProductDesc(productType);
									limitObj.setLimitProfileID(rs.getLong(APPR_LMT_LP_CMS_ID));

									ILimit limit = new OBLimit();
									limit.setLimitID(limitID.longValue());
									limit.setLimitProfileID(rs.getLong(APPR_LMT_LP_CMS_ID));

									if (rs.getLong(APPR_LMT_OUTER_ID) == 0) {
										limitObj.setSCIOuterLimitID(null);
									}
									else {
										limitObj.setSCIOuterLimitID(rs.getString(APPR_LMT_SCI_OUTER_ID));
										limit.setOuterLimitProfileID(rs.getLong(APPR_LMT_OUTER_PROF_ID));
										limit.setOuterLimitID(rs.getLong(APPR_LMT_OUTER_ID));
									}
									limitObj.setIsInnerOuterSameBCA(limit.getIsInnerOuterSameBCA());

									if (!collectionList.contains(collateralId)) {
										collectionList.add(collateralId);
										limitObjList = new ArrayList();
									}

									filterLimitObject(limitObjList, limitObj);

									if (!secIdList.contains(collateralId)) {
										secIdList.add(collateralId);

										OBCollateralSearchResult col = new OBCollateralSearchResult();
										col.setOBCollateralLimitList(limitObjList);
										resultList.add(col);

										col.setCollateralID(rs.getLong(COLLATERAL_ID));
										col.setSecurityID(securityId);

										col.setSubTypeCode(rs.getString(SECURITY_SUBTYPE_CODE));
										String isPerfected = rs.getString(SECURITY_IS_PERFECTED);
										if ((isPerfected != null) && isPerfected.equals(ICMSConstant.TRUE_VALUE)) {
											col.setIsCollateralPerfected(true);
										}

										OBBookingLocation bkg = new OBBookingLocation();
										bkg.setCountryCode(rs.getString(SECURITY_LOCATION));
										col.setSecurityLocation(bkg);
									//	col.setSecurityOrganization(rs.getString(SECURITY_ORGANISATION));

										col.setTypeCode(rs.getString(COL_TYPE_CODE));
										col.setTypeName(rs.getString(COL_TYPE_NAME));
										col.setSubTypeName(rs.getString(COL_SUBTYPE_NAME));
										col.setCollateralStatus(rs.getString(SECURITY_STATUS));
									}
								}
								return null;
							}
						});

			}
			count++;
		}

		return new SearchResult(criteria.getStartIndex(), resultList.size(), (hasTotalCountForCurrentSearch ? criteria
				.getTotalCountForCurrentTotalPages().intValue() : count + criteria.getStartIndex()), resultList);

	}

	/**
	 * This helper is used to set all the customer details with the source Names
	 * @param result
	 * @throws SQLException
	 * @throws Exception
	 */

	private void setCustomerAndSourceSystemDetails(SearchResult result) throws SearchDAOException {
		OBCollateralSearchResult col = null;
System.out.println("CollateralDAO.java=> setCustomerAndSourceSystemDetails => Inside");
		if (result != null) {
			Vector v = (Vector) result.getResultList();
			if (v.size() != 0) {
				for (int i = 0; i < v.size(); i++) {
					col = (OBCollateralSearchResult) v.elementAt(i);

					col.setSecSystemName(getSourceSysNameByCollId(SELECT_MULTI_SOURCE_SYSNAME_BY_COLL_ID, col
							.getCollateralID()));
					System.out.println("CollateralDAO.java=> setCustomerAndSourceSystemDetails => Inside for loop");
					List legalList = col.getOBCollateralLimitList();
					if ((legalList != null) && !legalList.isEmpty()) {
						for (int j = 0; j < legalList.size(); j++) {
							OBCollateralLimitResult limitObj = (OBCollateralLimitResult) legalList.get(j);

							processCustomerDetails(SELECT_CUST_WITHOUT_TRX, limitObj, limitObj.getLimitProfileID());
						}
					}

				}
			}
		}

	}

	/**
	 * Helper method used to set customer details on Collateral search
	 * 
	 * @param dbUtil
	 * @param rs
	 * @param col
	 * @param limitProfileID
	 * @throws SearchDAOException
	 */
	protected void processCustomerDetails(String sql, final OBCollateralLimitResult col, long limitProfileID) {

		getJdbcTemplate().query(sql, new Object[] { new Long(limitProfileID) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					col.setOuterCustomerName(rs.getString("LSP_SHORT_NAME"));
					col.setOuterInstructionRefNo(rs.getString("LLP_BCA_REF_NUM"));
					col.setOuterOrigLocCntry(rs.getString("CMS_ORIG_COUNTRY"));
				}

				return null;
			}
		});
	}

	/**
	 * for filterLimitObject while adding delete/Active limit details
	 * 
	 * @param inputList
	 * @param limitObj
	 * @return
	 */
	private void filterLimitObject(List inputList, OBCollateralLimitResult limitObj) {
		if (limitObj == null) {
			return;
		}

		String strLimitID = limitObj.getSCILimitID();

		boolean found = false;
		if ((inputList != null) && (inputList.size() > 0)) {

			for (int i = 0; i < inputList.size(); i++) {
				OBCollateralLimitResult obj = (OBCollateralLimitResult) inputList.get(i);
				if (obj.getSCILimitID() != null && obj.getSCILimitID().equals(strLimitID)) {
					if ((obj.getSCILimitMapStatus() != null)
							&& obj.getSCILimitMapStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
						inputList.set(i, limitObj);
					}
					found = true;
					break;
				}
			}

			if (!found) {
				inputList.add(limitObj);
			}
		}
		else {
			inputList.add(limitObj);
		}
	}

	private void appendSearchOrderBy(CollateralSearchCriteria criteria, StringBuffer buf) {

		String firstSort = criteria.getFirstSort() == null ? "" : criteria.getFirstSort().trim();

		firstSort = " SCI_SECURITY_DTL_ID ";

		if (!firstSort.equals("")) {
			buf.append(" order by ");
			buf.append(firstSort);
		}
	}

	public SearchResult searchCollateralByIdSubtype(LmtColSearchCriteria criteria) throws SearchDAOException {
		List argList = new ArrayList();

/*		String query = "SELECT DISTINCT COL.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID, COL.SECURITY_SUB_TYPE_ID, TYPE_NAME, SUBTYPE_NAME "
				+ "FROM CMS_SECURITY COL, CMS_SECURITY_SOURCE SS WHERE COL.STATUS = '"
				+ ICMSConstant.STATE_ACTIVE
				+ "' AND " + "SS.STATUS <> 'DELETED' AND COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND ";*/
		
		StringBuffer query =new StringBuffer();	

		query.append("SELECT DISTINCT sub_profile.lsp_short_name, COL.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID, COL.SECURITY_SUB_TYPE_ID, COL.TYPE_NAME,");
		query.append("	COL.SECURITY_LOCATION, COL.SCI_REFERENCE_NOTE, COL.SCI_ORIG_SECURITY_CURRENCY, COL.SECURITY_ORGANISATION, COL.SEC_PRIORITY, "); //A Shiv 041111
		query.append(" 	COL.SUBTYPE_NAME , COLNEWMAST.NEW_COLLATERAL_DESCRIPTION  , COL.COLLATERAL_CODE, PROP.PROPERTY_ID");
		query.append(" FROM ");
		query.append(" 	CMS_SECURITY_SOURCE SS, CMS_LIMIT_SECURITY_MAP SECMAP ");
		query.append(" 	,CMS_COLLATERAL_NEW_MASTER COLNEWMAST,SCI_LSP_APPR_LMTS lmts,");
		query.append(" 	SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sub_profile, CMS_SECURITY COL left outer join  cms_property prop on prop.cms_collateral_id = col.cms_collateral_id ");
		query.append(" WHERE ");
		if (criteria.getCustName() != null && criteria.getPropSearchId() != null 
				&& !criteria.getCustName().trim().equals("") && !criteria.getPropSearchId().trim().equals("")) {
			query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND UPPER(sub_profile.lsp_short_name) Like UPPER('%"+ criteria.getCustName()+"%') AND ");
		}
		if (criteria.getCustName() != null && !criteria.getCustName().trim().equals("")) {
			query.append(" UPPER(sub_profile.lsp_short_name) Like UPPER('%"+ criteria.getCustName()+"%') AND ");
		}
		if (criteria.getPropSearchId() != null && !criteria.getPropSearchId().trim().equals("")) {
			query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND ");
		}
		query.append(" sub_profile.cms_le_sub_profile_id = pf.cms_customer_id AND COL.STATUS = 'ACTIVE' AND SS.STATUS <> 'DELETED'  AND "); 
		query.append(" 	pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID");
		query.append(" 	AND lmts.cms_lsp_appr_lmts_id           = SECMAP.cms_lsp_appr_lmts_id AND pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID");
		query.append(" 	AND lmts.cms_lsp_appr_lmts_id           = SECMAP.cms_lsp_appr_lmts_id AND");
		query.append(" 	SECMAP.CMS_LSP_LMT_PROFILE_ID="+criteria.getLimitProfId()+" AND ");
		
		query.append("  COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID  AND  ");
		if (criteria.getSciSecId() == null || criteria.getSciSecId().trim().equals("")) {
			query.append(" UPPER(SUBTYPE_NAME) NOT LIKE 'GENERAL CHARGE'  AND ");
		}
		
		query.append("COL.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID AND SS.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID ");
		query.append("AND COLNEWMAST.NEW_COLLATERAL_CODE=COL.COLLATERAL_CODE");
		if (criteria.getSciSecId() != null) {
			query.append(" AND SS.CMS_COLLATERAL_ID ='" + criteria.getSciSecId().toUpperCase() + "'");
		}
		query.append(" UNION SELECT DISTINCT sub_profile.lsp_short_name,COL.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID,COL.SECURITY_SUB_TYPE_ID," );
		query.append(" TYPE_NAME,COL.SECURITY_LOCATION,COL.SCI_REFERENCE_NOTE, COL.SCI_ORIG_SECURITY_CURRENCY, COL.SECURITY_ORGANISATION, " );
		query.append(" COL.SEC_PRIORITY,SUBTYPE_NAME , COLNEWMAST.NEW_COLLATERAL_DESCRIPTION , COL.COLLATERAL_CODE, prop.property_id ");
		query.append(" FROM CMS_SECURITY_SOURCE SS, CMS_LIMIT_SECURITY_MAP SECMAP  ");
		query.append(",CMS_COLLATERAL_NEW_MASTER COLNEWMAST ,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,");
		query.append(" SCI_LE_SUB_PROFILE sub_profile, CMS_SECURITY COL left outer join  cms_property prop on prop.cms_collateral_id = col.cms_collateral_id ");
		query.append(" WHERE ");
		 
		if (criteria.getCustName() != null && criteria.getPropSearchId() != null 
				&& !criteria.getCustName().trim().equals("") && !criteria.getPropSearchId().trim().equals("")) {
			query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND UPPER(sub_profile.lsp_short_name) Like UPPER('%"+ criteria.getCustName()+"%') AND ");
		}
		if (criteria.getCustName() != null && !criteria.getCustName().trim().equals("")) {
			query.append(" UPPER(sub_profile.lsp_short_name) Like UPPER('%"+ criteria.getCustName()+"%') AND ");
		}
		if (criteria.getPropSearchId() != null && !criteria.getPropSearchId().trim().equals("")) {
			query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND ");
		}
		
		query.append(" COL.STATUS = 'ACTIVE' AND SS.STATUS <> 'DELETED' AND COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND UPPER(TYPE_NAME) ");
		query.append(" LIKE 'PROPERTY' AND COL.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID AND SS.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID ");
		query.append(" AND COLNEWMAST.NEW_COLLATERAL_CODE=COL.COLLATERAL_CODE AND sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ");
		query.append(" AND pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID ");
		query.append(" AND lmts.cms_lsp_appr_lmts_id           = SECMAP.cms_lsp_appr_lmts_id ");
		
		if (criteria.getSciSecId() != null) {
			query.append(" AND SS.CMS_COLLATERAL_ID ='" + criteria.getSciSecId().toUpperCase() + "'");
		}

		/*		if ((criteria.getSecSubtype() != null) && !criteria.getSecSubtype().trim().equals("")) {
			query.append("AND COL.SECURITY_SUB_TYPE_ID = ? ");
			argList.add(criteria.getSecSubtype());
		}

				if (query.endsWith("WHERE ")) {
			query = query.substring(0, query.length() - 6);
		}

		if (query.endsWith("AND ")) {
			query = query.substring(0, query.length() - 4);
		}*/

		query.append(" ORDER BY SOURCE_SECURITY_ID ");

		PaginationBean pgBean = new PaginationBean(criteria.getStartIndex() + 1, criteria.getStartIndex() + 10);

		int numTotalRecords = criteria.getNItems();
		if (numTotalRecords < 0) {
			numTotalRecords = getJdbcTemplate().queryForInt(this.paginationUtil.formCountQuery(query.toString()),
					argList.toArray());
		}

		List collateralList = getJdbcTemplate().query(this.paginationUtil.formPagingQuery(query.toString(), pgBean),
				argList.toArray(), new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						OBCollateral col = new OBCollateral();
						col.setComment(rs.getString("lsp_short_name"));
						col.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
						col.setSCISecurityID(rs.getString("SOURCE_SECURITY_ID"));
						col.setToBeDischargedInd(rs.getString("NEW_COLLATERAL_DESCRIPTION"));
						col.setCollateralLocation(rs.getString("SECURITY_LOCATION"));
						col.setSCIReferenceNote(rs.getString("SCI_REFERENCE_NOTE"));
						col.setSCICurrencyCode(rs.getString("SCI_ORIG_SECURITY_CURRENCY"));
						col.setSecurityOrganization(rs.getString("SECURITY_ORGANISATION"));
						col.setSecPriority(rs.getString("SEC_PRIORITY"));
						col.setCollateralCode(rs.getString("COLLATERAL_CODE"));
						col.setPropSearchId(rs.getString("PROPERTY_ID"));
						
						OBCollateralSubType subtype = new OBCollateralSubType();
						subtype.setSubTypeCode(rs.getString("SECURITY_SUB_TYPE_ID"));
						subtype.setTypeName(rs.getString("TYPE_NAME"));
						subtype.setSubTypeName(rs.getString("SUBTYPE_NAME"));

						col.setCollateralSubType(subtype);
						return col;
					}

				});

		return new SearchResult(criteria.getStartIndex(), 0, numTotalRecords, collateralList);
	}

	/**
	 * to get list SecSubtypeList
	 * @return
	 * @throws SearchDAOException
	 */

	public List getSecSubtypeList() throws SearchDAOException {
		String query = "SELECT DISTINCT SECURITY_TYPE_ID, SECURITY_SUB_TYPE_ID, "
				+ "SECURITY_TYPE_NAME, SUBTYPE_NAME FROM CMS_SECURITY_SUB_TYPE ORDER BY SECURITY_TYPE_ID, SECURITY_SUB_TYPE_ID";

		return getJdbcTemplate().query(query, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCollateralSubType subtype = new OBCollateralSubType();
				subtype.setTypeCode(rs.getString("SECURITY_TYPE_ID"));
				subtype.setSubTypeCode(rs.getString("SECURITY_SUB_TYPE_ID"));
				subtype.setTypeName(rs.getString("SECURITY_TYPE_NAME"));
				subtype.setSubTypeName(rs.getString("SUBTYPE_NAME"));

				return subtype;
			}
		});
	}

	/**
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */
	public List getDisTaskBcaLocationList(long collateralId) throws SearchDAOException {

		String sql = "select DISTINCT B.CMS_ORIG_COUNTRY,B.CMS_ORIG_ORGANISATION "
				+ "from cms_checklist A,SCI_LSP_LMT_PROFILE B, transaction trx " + "where A.category='S' "
				+ "AND A.DISABLE_COLLABORATION_IND='Y' " + "and b.CMS_LSP_LMT_PROFILE_ID=a.CMS_LSP_LMT_PROFILE_ID "
				+ "and a.CMS_COLLATERAL_ID = ? " + "and a.checklist_id = trx.reference_id "
				+ "and trx.transaction_type = 'CHECKLIST' " + "and trx.status <> 'OBSOLETE' ";

		return getJdbcTemplate().query(sql, new Object[] { new Long(collateralId) }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new OBBookingLocation(rs.getString("CMS_ORIG_COUNTRY"), rs.getString("CMS_ORIG_ORGANISATION"));
			}
		});
	}

	public String getCustomerNameByCollateralID(long collateralID) throws SearchDAOException {
		String sql = "select sp.LSP_SHORT_NAME " + "from cms_limit_security_map lsm, sci_lsp_appr_lmts lmts, "
				+ "sci_lsp_lmt_profile pf, sci_le_sub_profile sp "
				+ "where sp.cms_le_sub_profile_id = pf.cms_customer_id "
				+ "and pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID "
				+ "and lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id " + "and (lsm.update_status_ind <> '"
				+ ICMSConstant.HOST_STATUS_DELETE + "' " + " or lsm.update_status_ind is null) "
				+ "and lsm.cms_collateral_id = ? ";

		return (String) getJdbcTemplate().query(sql, new Object[] { new Long(collateralID) }, new ResultSetExtractor() {
			
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return null;
			}
		});
		
	}
	public String getCustomerIDByCollateralID(long collateralID) throws SearchDAOException {
		String sql = "select sp.CMS_LE_SUB_PROFILE_ID " + "from cms_limit_security_map lsm, sci_lsp_appr_lmts lmts, "
				+ "sci_lsp_lmt_profile pf, sci_le_sub_profile sp "
				+ "where sp.cms_le_sub_profile_id = pf.cms_customer_id "
				+ "and pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID "
				+ "and lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id " + "and (lsm.update_status_ind <> '"
				+ ICMSConstant.HOST_STATUS_DELETE + "' " + " or lsm.update_status_ind is null) "
				+ "and lsm.cms_collateral_id = ? ";

		return  (String)getJdbcTemplate().query(sql, new Object[] { new Long(collateralID) }, new ResultSetExtractor() {
			
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return null;
			}
		});

	}
	
	
	
	public String getCustomerCIFIDByCollateralID(long collateralID) throws SearchDAOException {
		String sql = "select sp.LSP_LE_ID " + "from cms_limit_security_map lsm, sci_lsp_appr_lmts lmts, "
				+ "sci_lsp_lmt_profile pf, sci_le_sub_profile sp "
				+ "where sp.cms_le_sub_profile_id = pf.cms_customer_id "
				+ "and pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID "
				+ "and lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id " + "and (lsm.update_status_ind <> '"
				+ ICMSConstant.HOST_STATUS_DELETE + "' " + " or lsm.update_status_ind is null) "
				+ "and lsm.cms_collateral_id = ? ";

		return  (String)getJdbcTemplate().query(sql, new Object[] { new Long(collateralID) }, new ResultSetExtractor() {
			
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return null;
			}
		});

	}

	/**
	 * update security perfection date by AA Number
	 * 
	 * @param aANumber AA Number
	 * @return updatedrecord count
	 * @throws SearchDAOException on error update the table records
	 */
	public int updateSecPerfectDateByAANumber(long aANumber) throws SearchDAOException {
		String sql = UPDATE_SEC_PREFECTION_DATE_BY_AANUMBER;

		return getJdbcTemplate().update(sql,
				new Object[] { new Timestamp(System.currentTimeMillis()), new Long(aANumber) });

	}

	protected IValuation doGetSourceValuation(String sql, long collateralId) {
		return (OBValuation) getJdbcTemplate().query(sql, new Object[] { new Long(collateralId) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							OBValuation valuation = new OBValuation();
							valuation.setSourceId(rs.getString("SOURCE_ID"));
							valuation.setValuationID(rs.getLong("VALUATION_ID"));
							valuation.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
							valuation.setCurrencyCode(rs.getString("VALUATION_CURRENCY"));
							valuation.setValuerName(rs.getString("VALUER"));
							valuation.setSourceType(rs.getString("SOURCE_TYPE"));
							BigDecimal amtChg = rs.getBigDecimal("CMV");
							if (amtChg != null) {
								Amount amount = new Amount(amtChg.doubleValue(), valuation.getCurrencyCode());
								valuation.setCMV(amount);
							}
							amtChg = rs.getBigDecimal("FSV");
							if (amtChg != null) {
								Amount amount = new Amount(amtChg.doubleValue(), valuation.getCurrencyCode());
								valuation.setFSV(amount);
							}
							amtChg = rs.getBigDecimal("RESERVE_PRICE");
							if (amtChg != null) {
								Amount amount = new Amount(amtChg.doubleValue(), valuation.getCurrencyCode());
								valuation.setReservePrice((amount));
							}

							// Andy Wong: fix date type save as sql.date
							if (rs.getDate("VALUATION_DATE") != null)
								valuation.setValuationDate(new Date(rs.getDate("VALUATION_DATE").getTime()));
							if (rs.getDate("RESERVE_PRICE_DATE") != null)
								valuation.setReservePriceDate(new Date(rs.getDate("RESERVE_PRICE_DATE").getTime()));
							if (rs.getDate("EVALUATION_DATE_FSV") != null)
								valuation.setEvaluationDateFSV(new Date(rs.getDate("EVALUATION_DATE_FSV").getTime()));

							valuation.setValuationType(rs.getString("VALUATION_TYPE"));
							valuation.setRevaluationFreq(rs.getInt("REVAL_FREQ"));
							valuation.setRevaluationFreqUnit(rs.getString("REVAL_FREQ_UNIT"));
							return valuation;

						}
						return null;
					}
				});
	}

	protected IValuation[] doGetValuationFromLOS(String sql, long collateralId) {
		List valuationResultList = getJdbcTemplate().query(sql, new Object[] { new Long(collateralId) },
				new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						OBValuation valuationb = new OBValuation();
						valuationb.setSourceId(rs.getString("SOURCE_ID"));
						valuationb.setValuationID(rs.getLong("VALUATION_ID"));
						valuationb.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
						valuationb.setCurrencyCode(rs.getString("VALUATION_CURRENCY"));
						valuationb.setValuerName(rs.getString("VALUER"));

						BigDecimal amtChg = rs.getBigDecimal("CMV");
						if (amtChg != null) {
							Amount amount = new Amount(amtChg.doubleValue(), valuationb.getCurrencyCode());
							valuationb.setCMV(amount);
						}
						amtChg = rs.getBigDecimal("FSV");
						if (amtChg != null) {
							Amount amount = new Amount(amtChg.doubleValue(), valuationb.getCurrencyCode());
							valuationb.setFSV(amount);
						}
						amtChg = rs.getBigDecimal("RESERVE_PRICE");
						if (amtChg != null) {
							Amount amount = new Amount(amtChg.doubleValue(), valuationb.getCurrencyCode());
							valuationb.setReservePrice((amount));
						}

						// Andy Wong: fix date type save as sql.date
						if (rs.getDate("VALUATION_DATE") != null)
							valuationb.setValuationDate(new Date(rs.getDate("VALUATION_DATE").getTime()));
						if (rs.getDate("UPDATE_DATE") != null)
							valuationb.setUpdateDate(new Date(rs.getDate("UPDATE_DATE").getTime()));
						if (rs.getDate("RESERVE_PRICE_DATE") != null)
							valuationb.setReservePriceDate(new Date(rs.getDate("RESERVE_PRICE_DATE").getTime()));
						if (rs.getDate("EVALUATION_DATE_FSV") != null)
							valuationb.setEvaluationDateFSV(new Date(rs.getDate("EVALUATION_DATE_FSV").getTime()));

						valuationb.setValuationType(rs.getString("VALUATION_TYPE"));
						return valuationb;
					}
				});

		return ((IValuation[]) valuationResultList.toArray(new IValuation[0]));
	}

	/**
	 * Retrieving different types of Valuation
	 * 
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */
	public IValuation getSourceValuation(long collateralId, String sourceType) throws SearchDAOException {
//		For Db2
		/*String sql = "SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY, VALUATION_TYPE, CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE, "
				+ "  SOURCE_TYPE, REVAL_FREQ, REVAL_FREQ_UNIT " + "  FROM CMS_VALUATION WHERE SOURCE_TYPE = '"
				+ sourceType + "' AND CMS_COLLATERAL_ID = ? " + " ORDER BY VALUATION_ID DESC FETCH FIRST 1 ROWS ONLY ";*/
		
//		For Oracle
		String sql = "SELECT * FROM (SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY, VALUATION_TYPE, CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE, "
				+ "  SOURCE_TYPE, REVAL_FREQ, REVAL_FREQ_UNIT " + "  FROM CMS_VALUATION WHERE SOURCE_TYPE = '"
				+ sourceType + "' AND CMS_COLLATERAL_ID = ? " + " ORDER BY VALUATION_ID) TEMP WHERE ROWNUM<=1";
		 
		return doGetSourceValuation(sql, collateralId);

	}

	/**
	 * to get getValuationFromLOS
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */

	public IValuation[] getValuationFromLOS(long collateralId) throws SearchDAOException {
		// For DB2
		/*String sql = "SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY,VALUATION_TYPE, CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, UPDATE_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE "
				+ "  FROM CMS_VALUATION " + " WHERE SOURCE_TYPE = '" + ICMSConstant.VALUATION_SOURCE_TYPE_S
				+ "' AND CMS_COLLATERAL_ID = ?  " + " ORDER BY VALUATION_DATE  DESC, LOS_VALUATION_ID ASC  "
				+ " FETCH FIRST 3 ROWS ONLY ";*/
		//For Oracle
		String sql = "SELECT * FROM( SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY,VALUATION_TYPE, CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, UPDATE_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE "
				+ "  FROM CMS_VALUATION " + " WHERE SOURCE_TYPE = '" + ICMSConstant.VALUATION_SOURCE_TYPE_S
				+ "' AND CMS_COLLATERAL_ID = ?  " + " ORDER BY VALUATION_DATE  DESC, LOS_VALUATION_ID ASC  "
				+ " ) TEMP WHERE ROWNUM<=3";

		return doGetValuationFromLOS(sql, collateralId);

	}

	/**
	 * to get getStageSourceValuation
	 * @param collateralId
	 * @param sourceType
	 * @return
	 * @throws SearchDAOException
	 */
	public IValuation getStageSourceValuation(long collateralId, String sourceType) throws SearchDAOException {
		// For Db2
		/*String sql = "SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY,VALUATION_TYPE,CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE "
				+ "  FROM CMS_STAGE_VALUATION WHERE SOURCE_TYPE = '" + sourceType + "' AND CMS_COLLATERAL_ID = ? "
				+ " ORDER BY VALUATION_ID  DESC FETCH FIRST 1 ROWS ONLY ";*/
		// For Oracle
		String sql = "SELECT * FROM (SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY,VALUATION_TYPE,CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE "
				+ "  FROM CMS_STAGE_VALUATION WHERE SOURCE_TYPE = '" + sourceType + "' AND CMS_COLLATERAL_ID = ? "
				+ " ORDER BY VALUATION_ID  DESC " 
				+ " )TEMP WHERE ROWNUM<=1";

		return doGetSourceValuation(sql, collateralId);

	}

	/**
	 * for getStageValuationFromLOS
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */
	public IValuation[] getStageValuationFromLOS(long collateralId) throws SearchDAOException {
		// For Db2
		/*String sql = "SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY,VALUATION_TYPE,CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, UPDATE_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE "
				+ "  FROM CMS_STAGE_VALUATION  WHERE SOURCE_TYPE = '" + ICMSConstant.VALUATION_SOURCE_TYPE_S
				+ "'  AND CMS_COLLATERAL_ID = ? ORDER BY VALUATION_ID DESC FETCH FIRST 3 ROWS ONLY ";*/
		// For Oracle
		String sql = "SELECT * FROM (SELECT SOURCE_ID, VALUATION_ID, VALUATION_CURRENCY,VALUATION_TYPE,CMS_COLLATERAL_ID, "
				+ "  VALUER, CMV, VALUATION_DATE, UPDATE_DATE, FSV, EVALUATION_DATE_FSV ,RESERVE_PRICE,  RESERVE_PRICE_DATE "
				+ "  FROM CMS_STAGE_VALUATION  WHERE SOURCE_TYPE = '" + ICMSConstant.VALUATION_SOURCE_TYPE_S
				+ "'  AND CMS_COLLATERAL_ID = ? ORDER BY VALUATION_ID DESC" 
				+ " )TEMP WHERE ROWNUM<=3 ";

		return doGetValuationFromLOS(sql, collateralId);

	}

	/**
	 * for getLiquidationIsNPL
	 * @param collateralID
	 * @return
	 * @throws SearchDAOException
	 */
	public String getLiquidationIsNPL(long collateralID) throws SearchDAOException {

		return (String) getJdbcTemplate().query(SELECT_LIQUIDATION_NPL, new Object[] { new Long(collateralID) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return ICMSConstant.TRUE_VALUE;
						}

						return ICMSConstant.FALSE_VALUE;
					}
				});
	}

	/**
	 * FOR canFullDeleteCollateral
	 * 
	 * @param collateralId
	 * @param securityId
	 * @return
	 * @throws SearchDAOException
	 */

	public boolean canFullDeleteCollateral(long collateralId, String securityId) throws SearchDAOException {

		String sqlShareSec = "SELECT COUNT(*) FROM cms_security_source "
				+ "WHERE cms_collateral_id = ? AND source_security_id != ?  AND status != 'D'";
		String sqlLimitSec = "SELECT COUNT(*) FROM  cms_limit_security_map WHERE cms_collateral_id = ? ";

		int count = getJdbcTemplate().queryForInt(sqlShareSec,
				new Object[] { new Long(collateralId), securityId.toUpperCase() });
		if (count > 0) {
			return false;
		}

		count = getJdbcTemplate().queryForInt(sqlLimitSec, new Object[] { new Long(collateralId) });
		if (count > 0) {
			return false;
		}

		return true;

	}

	/**
	 * GET List of getSourceSysName
	 * 
	 * @param collateralId
	 * @return
	 * @throws SearchDAOException
	 */
	public List getSecSystemName(long collateralId) throws SearchDAOException {
		String sql = SELECT_MULTI_SOURCE_SYSNAME_BY_COLL_ID;

		return getSourceSysNameByCollId(sql, collateralId);
	}

	protected List getSourceSysNameByCollId(String sql, long collateralId) throws SearchDAOException {

		List sourceNameList = getJdbcTemplate().query(sql, new Object[] { new Long(collateralId) }, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				StringBuffer sourceNameBuf = new StringBuffer();
				sourceNameBuf.append(rs.getString("SOURCE_SECURITY_ID")).append(" - ");

				if ((rs.getString(SOURCE_SYSTEM_NAME) == null) || "".equals(rs.getString(SOURCE_SYSTEM_NAME))) {
					sourceNameBuf.append(" ");
				}
				else {
					sourceNameBuf.append(rs.getString(SOURCE_SYSTEM_NAME));
				}

				return sourceNameBuf.toString();
			}
		});

		return sourceNameList;
	}

	public IBookingLocation[] getAllBookingLocation() throws SearchDAOException {
		String sql = "select BKL_BKG_LOCTN_ORGN_VALUE, BKL_BKG_LOCTN_DESC, BKL_CNTRY_ISO_CODE "
				+ "from SCI_BKG_LOCTN, COMMON_CODE_CATEGORY_ENTRY "
				+ "where SCI_BKG_LOCTN.BKL_BKG_LOCTN_ORGN_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE "
				+ "and ACTIVE_STATUS = '" + ICMSConstant.CATEGORY_ENTRY_ACTIVE_STATUS + "' and CATEGORY_CODE = '"
				+ ICMSConstant.CATEGORY_CODE_BKGLOC + "' order by BKL_CNTRY_ISO_CODE";

		List bookingLocationList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				IBookingLocation bookingLocation = new OBBookingLocation();
				bookingLocation.setBookingLocationDesc(rs.getString("BKL_BKG_LOCTN_DESC"));
				bookingLocation.setOrganisationCode(rs.getString("BKL_BKG_LOCTN_ORGN_VALUE"));
				bookingLocation.setCountryCode(rs.getString("BKL_CNTRY_ISO_CODE"));

				return bookingLocation;
			}
		});

		return (IBookingLocation[]) bookingLocationList.toArray(new IBookingLocation[0]);
	}

	public void updateOrInsertStpReadyStatus(String transactionId, boolean isStpReady) {
		String selectSql = "SELECT COUNT(1) FROM cms_stp_ready_status_map WHERE transaction_id = ?";

		int count = getJdbcTemplate().queryForInt(selectSql, new Object[] { transactionId });
		if (count == 0) {
			String insertSql = "INSERT INTO cms_stp_ready_status_map "
					+ "(transaction_id, transaction_type, is_stp_ready, last_update_date, created_date) "
					+ "VALUES (?, ?, ?, ?, ?) ";
			getJdbcTemplate().update(
					insertSql,
					new Object[] { transactionId, ICMSConstant.INSTANCE_COLLATERAL, ((isStpReady) ? "Y" : "N"),
							new Date(), new Date() });
		}
		else {
			String updateSql = "UPDATE cms_stp_ready_status_map SET last_update_date = ?, "
					+ "is_stp_ready = ? WHERE transaction_id = ? ";

			getJdbcTemplate().update(
					updateSql,
					new Object[] { new Date(), ((isStpReady) ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE),
							transactionId });
		}
	}

	/**
	 * Get search security query
	 * 
	 * @param criteria
	 * @return AbstractDBUtil
	 * @throws SearchDAOException
	 */
	private String getCollateralSQL(CollateralSearchCriteria criteria, List argList) throws SearchDAOException {

		StringBuffer buf = new StringBuffer();

		buf.append(" ( ");
		if (StringUtils.isNotBlank(criteria.getAaNumber())) {
			buf.append(SELECT_COLLATERAL_WITH_AA);
			if (ICMSConstant.SEARCH_TYPE_HOST.equals(criteria.getAaSearchType())) {
				buf.append("AND aa.llp_bca_ref_num = ? ");
			}
			else if (ICMSConstant.SEARCH_TYPE_LOS.equals(criteria.getAaSearchType())) {
				buf.append("AND aa.los_bca_ref_num = ? ");
			}
			else {
				DefaultLogger.error(this, "Unknown AA search type: " + criteria.getAaSearchType());
			}

			argList.add(criteria.getAaNumber());

			appendCommonSearchFilter(criteria, buf, argList);

			// append adv search filter
			if (criteria.getAdvanceSearch()) {
				appendPledgorAdvanceSearchSQL(criteria, buf, argList);
				appendAdvanceSearchTypeSQL(criteria, buf, argList);
			}
		}
		else {
			boolean isSearchByPledgor = isSearchByPledgor(criteria);
			// main query

			if (!isSearchByPledgor) {
				getSecurityLinkMb(criteria, buf, argList);
			}
			else {
				getSecurityLinkPledgor(criteria, buf, argList);
			}
		}

		// append DAP filter
//		appendDAPFilter(criteria, buf, argList);

		buf.append(" ) ");
		appendSearchOrderBy(criteria, buf);

		return buf.toString();

	}

	/**
	 * Utility method to check if a string value is null or empty.
	 * 
	 * @param aValue string to be checked
	 * @return boolean true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		else {
			return true;
		}
	}

	private class DAPFilterException extends OFAException {
		private static final long serialVersionUID = -1280529343042232922L;

		public DAPFilterException(String msg) {
			super(msg);
		}
	}

	public boolean getPolicyNumber(String policyNo, String insurancePolicyID) throws SearchDAOException {
		String sql = "select DISTINCT(POLICY_NO) from CMS_INSURANCE_POLICY where POLICY_NO = ?";
		String policyNoExist = null;

		StringBuffer buf = new StringBuffer();
		buf.append(sql);
		if (StringUtils.isNotBlank(insurancePolicyID)) {
			buf.append(" and INSR_ID <> ?");
			policyNoExist = (String) getJdbcTemplate().query(buf.toString(),
					new Object[] { policyNo, insurancePolicyID }, new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								return rs.getString(1);
							}
							return null;
						}
					});
		}
		else {
			policyNoExist = (String) getJdbcTemplate().query(buf.toString(), new Object[] { policyNo },
					new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								return rs.getString(1);
							}
							return null;
						}
					});
		}

		if (StringUtils.isNotBlank(policyNoExist)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getCollateralName(String collateralName, long cmsCollateralId) throws SearchDAOException {

		logger.debug("cmsCollateralId ^^^^^^^^^^^^^^ " + cmsCollateralId);

		String sql = "select DISTINCT(sec.SCI_REFERENCE_NOTE) from CMS_SECURITY sec, TRANSACTION trx "
				+ " where sec.CMS_COLLATERAL_ID <> ? and sec.CMS_COLLATERAL_ID = trx.REFERENCE_ID "
				+ " and trx.TRANSACTION_TYPE = 'COL' and trx.STATUS <> 'DELETED' "
				+ " and sec.SCI_REFERENCE_NOTE = ? and sec.SCI_SECURITY_DTL_ID is not null";
		String collateralNameExist = null;

		collateralNameExist = (String) getJdbcTemplate().query(sql,
				new Object[] { new Long(cmsCollateralId), collateralName }, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString(1);
						}
						return null;
					}
				});

		if (StringUtils.isNotBlank(collateralNameExist)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Get all collateral sub types given the type code.
	 * 
	 * @param typeCode security type code
	 * @return a list of collateral sub types
	 * @throws SearchDAOException on error searching the collateral subtypes
	 */
	public List getCollateralCodeBySubTypes(String typeCode) throws SearchDAOException {
		String sql = "SELECT NEW_COLLATERAL_CODE, NEW_COLLATERAL_DESCRIPTION from CMS_COLLATERAL_NEW_MASTER WHERE DEPRECATED ='N' AND STATUS ='ACTIVE' AND NEW_COLLATERAL_SUB_TYPE= '"+typeCode+"'";

		 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	             String[] stringArray = new String[2];
	             stringArray[0] = rs.getString("NEW_COLLATERAL_CODE");
	             stringArray[1] = rs.getString("NEW_COLLATERAL_DESCRIPTION");
	             return stringArray;
	         }
	     });

	     return resultList;
	}
	
	
	
	
	/**
	 * Get all collateral sub types given the type code.
	 * 
	 * @param typeCode security type code
	 * @return a list of collateral sub types
	 * @throws SearchDAOException on error searching the collateral subtypes
	 */
	public List getSeurityNames() throws SearchDAOException {
		String sql = "SELECT NEW_COLLATERAL_CODE, NEW_COLLATERAL_DESCRIPTION from CMS_COLLATERAL_NEW_MASTER WHERE DEPRECATED ='N' AND STATUS ='ACTIVE' ";

		 List resultList = getJdbcTemplate().queryForList(sql);
		 
			List lbValList = new ArrayList();
		 
		for (int i = 0; i < resultList.size(); i++) {
				
			
				
				Map  map = (Map) resultList.get(i);
//			
				String id= map.get("NEW_COLLATERAL_CODE").toString();
				
				
				String val =  map.get("NEW_COLLATERAL_DESCRIPTION").toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}

	     return lbValList;
	}
	
	public List getSeurityCodeAndType(String securityCode) throws SearchDAOException {
		String sql = "select ENTRY_CODE,ENTRY_NAME from COMMON_CODE_CATEGORY_ENTRY where ENTRY_CODE=(select NEW_COLLATERAL_MAIN_TYPE from CMS_COLLATERAL_NEW_MASTER where NEW_COLLATERAL_CODE='"+securityCode+"' and CATEGORY_CODE='31' and ACTIVE_STATUS='1')";

		 List resultList = getJdbcTemplate().queryForList(sql);
		 
			List lbValList = new ArrayList();
		 
		for (int i = 0; i < resultList.size(); i++) {
				
				Map  map = (Map) resultList.get(i);
//			
				String id= map.get("ENTRY_CODE").toString();
				
				String val =  map.get("ENTRY_NAME").toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}

	     return lbValList;
	}
	
	
	public ICountry[] getListAllCountry() throws SearchDAOException {
		String sql = "SELECT COUNTRY_CODE, COUNTRY_NAME FROM CMS_COUNTRY";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				ICountry country = new OBCountry();
				country.setCountryCode(rs.getString("COUNTRY_CODE"));
				country.setCountryName(rs.getString("COUNTRY_NAME"));
				return country;
			}
		});
		 return (ICountry[]) resultList.toArray(new ICountry[0]);
	}
	
	public ISystemBankBranch[] getListAllSystemBankBranch(String country) throws SearchDAOException {
	//	String sql = "SELECT SYSTEM_BANK_BRANCH_CODE, SYSTEM_BANK_BRANCH_NAME FROM CMS_SYSTEM_BANK_BRANCH WHERE COUNTRY= (SELECT ID FROM CMS_COUNTRY WHERE COUNTRY_CODE='"+country+"') AND IS_VAULT='on' AND STATUS='ACTIVE'";
		
		//Changes to remove deleted bank from dropdown
		String sql = "SELECT SYSTEM_BANK_BRANCH_CODE, SYSTEM_BANK_BRANCH_NAME FROM CMS_SYSTEM_BANK_BRANCH WHERE COUNTRY= (SELECT ID FROM CMS_COUNTRY WHERE COUNTRY_CODE='"+country+"') AND IS_VAULT='on' AND STATUS='ACTIVE' AND  deprecated='N'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				ISystemBankBranch branch = new OBSystemBankBranch();
				branch.setSystemBankBranchCode(rs.getString("SYSTEM_BANK_BRANCH_CODE"));
				branch.setSystemBankBranchName(rs.getString("SYSTEM_BANK_BRANCH_NAME"));
				return branch;
			}
		});
		 return (ISystemBankBranch[]) resultList.toArray(new ISystemBankBranch[0]);
	}
	
	public IForexFeedEntry[] getCurrencyList() throws SearchDAOException {
		String sql = "SELECT BUY_CURRENCY,CURRENCY_ISO_CODE,RESTRICTION_TYPE FROM CMS_FOREX WHERE STATUS='ENABLE'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				IForexFeedEntry currency = new OBForexFeedEntry();
				currency.setBuyCurrency(rs.getString("BUY_CURRENCY"));
				currency.setCurrencyIsoCode(rs.getString("CURRENCY_ISO_CODE"));
				currency.setRestrictionType(rs.getString("RESTRICTION_TYPE"));
				return currency;
			}
		});
		 return (IForexFeedEntry[]) resultList.toArray(new IForexFeedEntry[0]);
	}
	//Add by Govind S:7/09/2011, Get System Bank Branch By Cuntry Code and Branch Code
	public ISystemBankBranch getSysBankBranchByCuntryAndBranchCode(String country,String branchCode) throws SearchDAOException {
		String sql = "SELECT SYSTEM_BANK_BRANCH_CODE, SYSTEM_BANK_BRANCH_NAME, CUSTODIAN1, CUSTODIAN2  FROM CMS_SYSTEM_BANK_BRANCH WHERE SYSTEM_BANK_BRANCH_CODE = '"+branchCode+"' AND COUNTRY= (SELECT ID FROM CMS_COUNTRY WHERE COUNTRY_CODE='"+country+"') AND IS_VAULT='on'";
		ISystemBankBranch systemBankBranch = null;
		List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				ISystemBankBranch branch = new OBSystemBankBranch();
				branch.setSystemBankBranchCode(rs.getString("SYSTEM_BANK_BRANCH_CODE"));
				branch.setSystemBankBranchName(rs.getString("SYSTEM_BANK_BRANCH_NAME"));
				branch.setCustodian1(rs.getString("CUSTODIAN1"));
				branch.setCustodian2(rs.getString("CUSTODIAN2"));
				return branch;
			}
		});
		
		if(!resultList.isEmpty())
		{
			systemBankBranch = (ISystemBankBranch)resultList.get(0);
		}
		 return (ISystemBankBranch) systemBankBranch;
	}
	
	//Add by Govind S:7/09/2011:Get Country Name by Country Code
	public ICountry[] getCountryNamebyCode(String countryCode) throws SearchDAOException {
		String sql = "SELECT COUNTRY_CODE, COUNTRY_NAME FROM CMS_COUNTRY WHERE COUNTRY_CODE = '"+countryCode+"'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				ICountry country = new OBCountry();
				country.setCountryCode(rs.getString("COUNTRY_CODE"));
				country.setCountryName(rs.getString("COUNTRY_NAME"));
				return country;
			}
		});
		 return (ICountry[]) resultList.toArray(new ICountry[0]);
	}
	
	public String getPartyNamebyCode(long partyCode) throws SearchDAOException {
		String partyName = null;
		ResultSet rs=null;
		try{
			dbUtil = new DBUtil();
			//String sql = "SELECT COUNTRY_CODE, COUNTRY_NAME FROM CMS_COUNTRY WHERE COUNTRY_CODE = '"+partyCode+"'";
			String partyNm = null;
			String sql = "SELECT SP.LSP_SHORT_NAME FROM  CMS_LIMIT_SECURITY_MAP LSM,SCI_LSP_APPR_LMTS LMTS,SCI_LSP_LMT_PROFILE PF,SCI_LE_SUB_PROFILE SP,SCI_LE_MAIN_PROFILE LMP,CMS_SECURITY CS WHERE "  
				+" CS.CMS_COLLATERAL_ID = LSM.CMS_COLLATERAL_ID AND  SP.CMS_LE_SUB_PROFILE_ID = PF.CMS_CUSTOMER_ID AND PF.CMS_LSP_LMT_PROFILE_ID = LMTS.CMS_LIMIT_PROFILE_ID "
				+" AND LMTS.CMS_LSP_APPR_LMTS_ID = LSM.CMS_LSP_APPR_LMTS_ID AND SP.CMS_LE_MAIN_PROFILE_ID = LMP.CMS_LE_MAIN_PROFILE_ID AND CS.CMS_COLLATERAL_ID = ? ";
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, partyCode);
			rs = dbUtil.executeQuery(); 
			while (rs.next()) {
				partyName = rs.getString("LSP_SHORT_NAME");
			}
		 
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				// Continue to return.
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				// Continue to return.
			}
		}
		return partyName;
	}
	
	public List getSystemList(long partyCode) throws SearchDAOException {
		String sql = "select * from SCI_LE_OTHER_SYSTEM os,SCI_LE_MAIN_PROFILE mp,SCI_LE_SUB_PROFILE sp,SCI_LSP_LMT_PROFILE pf,SCI_LSP_APPR_LMTS lmts,CMS_LIMIT_SECURITY_MAP lsm,CMS_SECURITY cs "+
	      " where os.cms_le_main_profile_id = mp.cms_le_main_profile_id(+) and mp.cms_le_main_profile_id = sp.cms_le_main_profile_id(+) AND sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID "+
	      "  AND lsm.CHARGE_ID IN (SELECT MAX(MAPS2.CHARGE_ID) FROM cms_limit_security_map maps2 WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id "+
				  " AND maps2.cms_collateral_id      = lsm.cms_collateral_id "+
				  " ) AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID AND lmts.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID 	AND (lsm.UPDATE_STATUS_IND != 'D' or lsm.UPDATE_STATUS_IND IS NULL) "+
	      " AND   cs.CMS_COLLATERAL_ID = lsm.CMS_COLLATERAL_ID and cs.cms_collateral_id = " + partyCode;

		 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

		/*String sql = "select   "+
		"distinct os.CMS_LE_SYSTEM_NAME, os.CMS_LE_OTHER_SYS_CUST_ID "+
		"from SCI_LE_OTHER_SYSTEM os,  "+
		"SCI_LE_SUB_PROFILE sp,  "+
		" SCI_LSP_LMT_PROFILE pf,SCI_LSP_APPR_LMTS "+
		" lmts,CMS_LIMIT_SECURITY_MAP lsm  "+
		" where  "+
		" os.cms_le_main_profile_id = sp.cms_le_main_profile_id AND  "+
		" sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID   AND  "+
		"  lsm.CHARGE_ID IN  "+
		"  (SELECT MAX(MAPS2.CHARGE_ID) FROM cms_limit_security_map maps2 WHERE  "+
		" maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id  AND   "+
		" maps2.cms_collateral_id      = lsm.cms_collateral_id  ) "+
		" AND  "+
		"  pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID AND "+
		"  lmts.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID  "+
		"  AND (lsm.UPDATE_STATUS_IND != 'D' or lsm.UPDATE_STATUS_IND IS NULL) "+
		"  and  lsm.cms_collateral_id = ?" ;

		List resultList = getJdbcTemplate().query(sql, new Object[] {partyCode}, new RowMapper() {*/
			
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	             String[] stringArray = new String[2];
	             stringArray[0] = rs.getString("CMS_LE_SYSTEM_NAME");
	             stringArray[1] = rs.getString("CMS_LE_OTHER_SYS_CUST_ID");
	             return stringArray;
	         }
	     });

	     return resultList;
	}
	
	public List getFacilityNameList(long partyCode) throws SearchDAOException {
		String sql = "select distinct FACILITY_NAME from SCI_LE_OTHER_SYSTEM os,SCI_LE_MAIN_PROFILE mp,SCI_LE_SUB_PROFILE sp,SCI_LSP_LMT_PROFILE pf,SCI_LSP_APPR_LMTS lmts,CMS_LIMIT_SECURITY_MAP lsm,CMS_SECURITY cs "+
	      " where os.cms_le_main_profile_id = mp.cms_le_main_profile_id(+) and mp.cms_le_main_profile_id = sp.cms_le_main_profile_id(+) AND sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID "+
	      "  AND lsm.CHARGE_ID IN (SELECT MAX(MAPS2.CHARGE_ID) FROM cms_limit_security_map maps2 WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id "+
				  " AND maps2.cms_collateral_id      = lsm.cms_collateral_id "+
				  " ) AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID AND lmts.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID 	AND (lsm.UPDATE_STATUS_IND != 'D' or lsm.UPDATE_STATUS_IND IS NULL) "+
	      " AND   cs.CMS_COLLATERAL_ID = lsm.CMS_COLLATERAL_ID and cs.cms_collateral_id = " + partyCode;

		 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	             String[] stringArray = new String[1];
	             stringArray[0] = rs.getString("FACILITY_NAME");
	             return stringArray;
	         }
	     });

	     return resultList;
	}
	
	public List getFacilityIdList(String partyCode, String facilityName) throws SearchDAOException {
		String sql = "select distinct LMT_ID from SCI_LE_OTHER_SYSTEM os,SCI_LE_MAIN_PROFILE mp,SCI_LE_SUB_PROFILE sp,SCI_LSP_LMT_PROFILE pf,SCI_LSP_APPR_LMTS lmts,CMS_LIMIT_SECURITY_MAP lsm,CMS_SECURITY cs "+
	      " where os.cms_le_main_profile_id = mp.cms_le_main_profile_id(+) and mp.cms_le_main_profile_id = sp.cms_le_main_profile_id(+) AND sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID "+
	      "  AND lsm.CHARGE_ID IN (SELECT MAX(MAPS2.CHARGE_ID) FROM cms_limit_security_map maps2 WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id "+
				  " AND maps2.cms_collateral_id      = lsm.cms_collateral_id "+
				  " ) AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID AND lmts.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID 	AND (lsm.UPDATE_STATUS_IND != 'D' or lsm.UPDATE_STATUS_IND IS NULL) "+
	      " AND   cs.CMS_COLLATERAL_ID = lsm.CMS_COLLATERAL_ID and cs.cms_collateral_id = '" + partyCode +"' and lmts.FACILITY_NAME = '"+facilityName+"'";

		 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	             String[] stringArray = new String[1];
	             stringArray[0] = rs.getString("LMT_ID");
	             return stringArray;
	         }
	     });

	     return resultList;
	}
	
	public List getLineList(long partyCode) throws SearchDAOException {
	/*	String sql = "select distinct(lm.line_no) from SCI_LSP_APPR_LMTS lm where CMS_LIMIT_PROFILE_ID in (select  lmts.CMS_LIMIT_PROFILE_ID  from "+
							" SCI_LE_MAIN_PROFILE mp,SCI_LE_SUB_PROFILE sp,	SCI_LSP_LMT_PROFILE pf,	SCI_LSP_APPR_LMTS lmts, CMS_LIMIT_SECURITY_MAP lsm, " +
							" CMS_SECURITY cs  where "+  
							 " mp.cms_le_main_profile_id = sp.cms_le_main_profile_id(+) AND sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID "+ 
							 " AND lmts.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID AND (lsm.UPDATE_STATUS_IND != 'D' or lsm.UPDATE_STATUS_IND IS NULL)"+ 
							 " AND   cs.CMS_COLLATERAL_ID = lsm.CMS_COLLATERAL_ID  AND   CS.CMS_COLLATERAL_ID =  '"+ partyCode + "') and lm.line_no is not null";  //'CUS0000059'"
*/
	  // Code Start:Uma Khot 31/08/2015 Phase 3 CR - lien list to display corresponding lien in dropdown
		String sql ="select distinct(lmts.line_no) FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,cms_security sec "+        
		 " where   lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID AND (lsm.UPDATE_STATUS_IND  != 'D' OR lsm.UPDATE_STATUS_IND IS NULL) "+							
		 " and lmts.cms_limit_status <> 'DELETED' AND lsm.CHARGE_ID   in  "+
		 " (SELECT MAX(MAPS2.CHARGE_ID) from cms_limit_security_map maps2 where maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id "+
		 " AND maps2.cms_collateral_id = sec.cms_collateral_id )  "+
		 " AND lsm.UPDATE_STATUS_IND = 'I' and sec.CMS_COLLATERAL_ID = '"+ partyCode + "'";
		// Code End:Uma Khot 31/08/2015 Phase 3 CR - lien list to display corresponding lien in dropdown
		 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	             String[] stringArray = new String[2];
	             stringArray[0] = rs.getString("LINE_NO");
	             stringArray[1] = rs.getString("LINE_NO");
	             return stringArray;
	         }
	     });

	     return resultList;
	}

	public List getRecurrentDueDateListByCustomerAndCollatralID(
			long customerID, long cmsCollatralId) {
		StringBuffer sql=new StringBuffer(" SELECT to_char(chkitem.expiry_date,'DD/Mon/YYYY')||','||chkitem.document_code from CMS_CHECKLIST_ITEM chkItem,")
				.append(" CMS_DOCUMENT_GLOBALLIST recMaster ")
				.append(" where chkitem.checklist_id IN(")
				.append(" select chk.checklist_id from CMS_CHECKLIST chk ")
				.append(" where chk.cms_lsp_lmt_profile_id = ("+getLimitId(customerID)+")")
				.append("  and chk.category='REC')")
				.append(" and chkitem.document_code=recmaster.document_code")
				.append(" and recmaster.category='REC' and recmaster.statement_type='STOCK_STATEMENT'")
				.append(" AND chkitem.status <>'WAIVED'")
				.append(" AND chkitem.IS_DELETED='N'")
				.append(" AND chkitem.expiry_date IS NOT NULL")
				.append(" order by chkitem.expiry_date desc");
		
		return  getJdbcTemplate().query(sql.toString(), new RowMapper() { 
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				return rs.getString(1);
			}
		});
	}

	private String getLimitId(long customerID) {
		String limitSql="select pf.cms_lsp_lmt_profile_id from sci_lsp_lmt_profile pf, sci_le_sub_profile sp " +
				" where sp.cms_le_sub_profile_id = pf.cms_customer_id " +
				" and pf.cms_customer_id= "+customerID;
	  return limitSql;
	}

	/**
	 * Get all collateral code and description.
	 * 
	 * @param void
	 * @return collateral code and description
	 * @throws SearchDAOException on error searching the collateral
	 */
	public List getCollateralCodeDesc() throws SearchDAOException {
		String sql = "SELECT NEW_COLLATERAL_CODE, NEW_COLLATERAL_DESCRIPTION from CMS_COLLATERAL_NEW_MASTER WHERE DEPRECATED ='N' AND STATUS ='ACTIVE'";

		 List resultList = getJdbcTemplate().query(sql, new RowMapper() {

	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	             String[] stringArray = new String[2];
	             stringArray[0] = rs.getString("NEW_COLLATERAL_CODE");
	             stringArray[1] = rs.getString("NEW_COLLATERAL_DESCRIPTION");
	             return stringArray;
	         }
	     });

	     return resultList;
	}
	//Add by Govind S:31/10/2011:Get Country Name List
	public List getCountryNameList() throws SearchDAOException {
		String sql = "SELECT COUNTRY_CODE, COUNTRY_NAME FROM CMS_COUNTRY WHERE DEPRECATED ='N' AND STATUS ='ACTIVE'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[2];
	             stringArray[0] = rs.getString("COUNTRY_CODE");
	             stringArray[1] = rs.getString("COUNTRY_NAME");
	             return stringArray;
			
			}
		});
		 return resultList;
	}
	
	//Add by Govind S:31/10/2011, Get System Bank Branch Name
	public List getSysBankBranchNameList() throws SearchDAOException {
		String sql = "SELECT SYSTEM_BANK_BRANCH_CODE, SYSTEM_BANK_BRANCH_NAME FROM CMS_SYSTEM_BANK_BRANCH WHERE IS_VAULT='on'";
		ISystemBankBranch systemBankBranch = null;
		List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[2];
	             stringArray[0] = rs.getString("SYSTEM_BANK_BRANCH_CODE");
	             stringArray[1] = rs.getString("SYSTEM_BANK_BRANCH_NAME");
	             return stringArray;
			
			}
		});
		 return resultList;
	}
	//added by Anup K.
	//function for get Maximum Basel Serial No from Lien respect with FD Receipt no.
	public String getMaxBaselSerialNo(OBCashDeposit obcashDep){
		String data="";
		//String sql="select max(basel_serial)+1 from CMS_LIEN";
		String sql= "select  max(to_number( basel_serial))+1 from CMS_LIEN where cash_deposit_id in (select cash_deposit_id from cms_cash_deposit where deposit_reference_number= '"+ obcashDep.getDepositRefNo()+"' and status = 'ACTIVE')";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{
				data=rs.getString(1);	
				if(null==data)
					data="1";
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return data;
	}
	
	//get all Active FD using FD Receipt No.
	public List selectCashFdList(String sql,String receiptNo){
		List fdList=new ArrayList();
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String data="";
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{//    
				data=rs.getString("CASH_DEPOSIT_ID")+","+rs.getString("DEPOSITOR_NAME")+","+rs.getString("CMS_COLLATERAL_ID")+","+rs.getString("DEPOSIT_AMOUNT")+","+rs.getString("LIEN_AMOUNT")+","+rs.getString("ISSUE_DATE")+","+rs.getString("DEPOSIT_MATURITY_DATE")+","+rs.getString("DEPOSIT_INTEREST_RATE")+","+rs.getString("LSP_SHORT_NAME");	
				fdList.add(data);
			}
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return fdList;
	}
	
	//fill FD Fields Values using cash id
	public String fillFD(String cashDepID){
		DBUtil dbUtil = null;
		ResultSet rs=null;
		//String sql="SELECT DEPOSIT_AMOUNT,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE,VERIFICATION_DATE FROM CMS_CASH_DEPOSIT WHERE cash_deposit_id =  "+cashDepID;
		
		String sql= "SELECT cd.DEPOSIT_AMOUNT,cd.ISSUE_DATE,cd.DEPOSIT_MATURITY_DATE,cd.DEPOSIT_INTEREST_RATE,cd.VERIFICATION_DATE,sp.LSP_SHORT_NAME " + 
                " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec " +      
            " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID " +
        				 " AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
        				 " AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
        				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
        				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+
        				" AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and "+
                 " cd.cash_deposit_id = "+ cashDepID;
		String data="";
		String ver_date="n";
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{   
				if(null!=rs.getString("VERIFICATION_DATE"))
					ver_date=rs.getString("VERIFICATION_DATE");
				data=rs.getString("DEPOSIT_AMOUNT")+","+rs.getString("ISSUE_DATE")+","+rs.getString("DEPOSIT_MATURITY_DATE")+","+rs.getString("DEPOSIT_INTEREST_RATE")+","+ver_date +","+rs.getString("LSP_SHORT_NAME");	
			}
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return data;
	}
	// End Anup K.
	public String getCamIdByCollateralID(String collateralID) throws SearchDAOException {
		String camId = "";
		ResultSet rs =null;
		try{
		dbUtil = new DBUtil();
		String sql = " select distinct(pf.LLP_BCA_REF_NUM) "+
					" FROM CMS_SECURITY sec, "+
					" CMS_LIMIT_SECURITY_MAP lsm, "+
					" SCI_LSP_APPR_LMTS lmts, "+
					" SCI_LSP_LMT_PROFILE pf "+
					" WHERE  "+
					" SEC.CMS_COLLATERAL_ID   = LSM.CMS_COLLATERAL_ID "+
					" AND pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID "+
					" AND lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id "+
					" AND (lsm.update_status_ind   <> 'D' "+
					" OR lsm.update_status_ind     IS NULL) "+
					" AND sec.CMS_COLLATERAL_ID =  "+collateralID;
		
		dbUtil.setSQL(sql);
		 rs = dbUtil.executeQuery();
		
		while (rs.next()) { 
			camId = rs.getString("LLP_BCA_REF_NUM");
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return camId;
	}
	
	public String getCamIdByCustomerID(String customerId) throws SearchDAOException {
		String camId = "";
		ResultSet rs=null;
		try{
		dbUtil = new DBUtil();
		String sql = " select distinct(pf.LLP_BCA_REF_NUM) "+
					" FROM SCI_LSP_LMT_PROFILE pf "+
					" WHERE  "+
					" pf.CMS_CUSTOMER_ID =  "+customerId;
		
		dbUtil.setSQL(sql);
		rs = dbUtil.executeQuery();
		
		while (rs.next()) { 
			camId = rs.getString("LLP_BCA_REF_NUM");
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return camId;
	}
	
	public ILimit getReleasableAmountByCollateralID(long collateralID) throws SearchDAOException {
		String sql = new StringBuffer(" SELECT lmts.total_released_amount,")
							.append(" lmts.purpose,")
							.append(" lmts.is_dp")
							.append(" FROM cms_limit_security_map lsm,")
							.append("   sci_lsp_appr_lmts lmts,")
							.append("   sci_lsp_lmt_profile pf,")
							.append("   sci_le_sub_profile sp")
							.append(" WHERE sp.cms_le_sub_profile_id = pf.cms_customer_id")
							.append(" AND pf.cms_lsp_lmt_profile_id  = lmts.CMS_LIMIT_PROFILE_ID")
							.append(" AND lmts.cms_lsp_appr_lmts_id  = lsm.cms_lsp_appr_lmts_id")
							.append(" AND (lsm.update_status_ind    <> 'D' OR lsm.update_status_ind      IS NULL)")
							.append(" AND sp.status='ACTIVE'")
							.append(" AND lsm.cms_collateral_id      =  ? ")
							.toString();
		
		return (ILimit) getJdbcTemplate().query(sql, new Object[] { new Long(collateralID) }, new ResultSetExtractor() {
			String totalReleasedAmount ;
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					ILimit oblimit = new OBLimit();

					totalReleasedAmount= rs.getString(1);
					if(totalReleasedAmount!=null){
						oblimit.setTotalReleasedAmount(totalReleasedAmount);
					}else{
						oblimit.setTotalReleasedAmount("0");
					}
					oblimit.setPurpose(rs.getString(2));
					oblimit.setIsDP(rs.getString(3));
					return oblimit;
				}
				return null;
			}
		});
		
	}

	public String getStatementNameByDocCode(String docCode)throws SearchDAOException{
		if(docCode!=null && !"".equals(docCode.trim())){
			String sql = new StringBuffer(" select document_description from CMS_DOCUMENT_GLOBALLIST ")
			.append(" where document_code= ?")
			.toString();
			
			return (String) getJdbcTemplate().query(sql, new Object[] { docCode }, new ResultSetExtractor() {
				
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return null;
				}
			});
		}else{
			return null;
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
			e.printStackTrace();
		}
	}
	
	
	public void removeExtraEntryFromLSM(String partyId) throws SqlException {
		
		try{
		String sql = " DELETE"+
		" FROM cms_stage_limit_security_map lsmap"+
		" WHERE lsmap.charge_id IN"+
		"   (SELECT inn.charge_id"+
		"   FROM cms_stage_limit_security_map inn,"+
		"     sci_lsp_lmt_profile cam"+
		"   WHERE inn.cms_collateral_id NOT IN"+
		"     (SELECT cms_collateral_id FROM cms_security"+
		"     )"+
		"   AND cam.cms_lsp_lmt_profile_id    =inn.cms_lsp_lmt_profile_id"+
		"   AND cam.llp_le_id                 = '"+partyId+"'"+
		"   AND inn.cms_lsp_appr_lmts_id NOT IN"+
		"     (SELECT trx.staging_reference_id"+
		"     FROM transaction trx"+
		"     WHERE trx.from_state    ='ND'"+
		"     AND trx.transaction_type='LIMIT'"+
		"     )"+
		"   )";

		  getJdbcTemplate().execute(sql);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	public String getCustomerLimitProfileIDByCollateralID(long collateralID) throws SearchDAOException {
		String sql = "select pf.CMS_LSP_LMT_PROFILE_ID " + "from cms_limit_security_map lsm, sci_lsp_appr_lmts lmts, "
				+ "sci_lsp_lmt_profile pf, sci_le_sub_profile sp "
				+ "where sp.cms_le_sub_profile_id = pf.cms_customer_id "
				+ "and pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID "
				+ "and lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id " + "and (lsm.update_status_ind <> '"
				+ ICMSConstant.HOST_STATUS_DELETE + "' " + " or lsm.update_status_ind is null) "
				+ "and lsm.cms_collateral_id = ? ";

		return  (String)getJdbcTemplate().query(sql, new Object[] { new Long(collateralID) }, new ResultSetExtractor() {
			
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return null;
			}
		});

	}
	
	public void updateStatusOfChecklistItemForGC(long checklistId, String documentCode,String date) throws SqlException {
		
		try{
			
			
			DefaultLogger.debug(this, "============================2593=============updateStatusOfChecklistItemForGC========= ");
			String sql =" UPDATE CMS_CHECKLIST_ITEM "+
         " SET status = 'RECEIVED',"+
            "received_date= '"+date+"' "+
        " WHERE checklist_id = '"+checklistId+"'"+
        " AND document_code  = '"+documentCode+"'";
				
			

			  getJdbcTemplate().execute(sql);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	
	public void executeReceivedStatementProc( long collateralId) throws Exception {
        try {
            getJdbcTemplate().execute("{call " + getReceivedStatementProcParam() + "("+collateralId+ ")}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception("Error executeReceivedStatementProc.");
        }
    }
	
	public IEODStatus executeReceivedStatementProc(IEODStatus eodStatus) {
		DefaultLogger.debug(this, " CollateralDAO.executeReceivedStatementProc() starts ==== ");
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
	        try {
	        	 getJdbcTemplate().execute("{call " + getReceivedStatementProc() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	        	eodStatus.setStatus(EODConstants.STATUS_PASS);
	        }catch (Exception ex) {
	        	eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeReceivedStatementProc())" );
				ex.printStackTrace();
	        }
		}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		
        DefaultLogger.debug(this, " CollateralDAO.executeReceivedStatementProc() ends ==== ");
        return eodStatus;
    }

	// added By Sachin P. call procedure for remove Old Fd records from table. 
	public IEODStatus executeFdDeletedBackupProc(IEODStatus eodStatus){
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			DefaultLogger.debug(this, " CollateralDAO.executeFdDeletedBackupProc() starts ==== ");
	        try {
	        	 getJdbcTemplate().execute("{call " + getFdDeletedBackupProc() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	        	eodStatus.setStatus(EODConstants.STATUS_PASS);
	        }catch (Exception ex) {
	        	eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeFdDeletedBackupProc())" );
				ex.printStackTrace();
	        }
		}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
        DefaultLogger.debug(this, " CollateralDAO.executeFdDeletedBackupProc() ends ==== ");
        return eodStatus;
    }
	// End By Sachin P. call procedure for remove Old Fd records from table. 
	
	// added By Sachin P. call procedure from table. 
	public boolean executeLADExpiryUpdate( ) {
        try {
        	 getJdbcTemplate().execute("{call " + getLadExpiryUpdate() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        	return true; 
        }
        catch (Exception ex) {
        	
           return false;
        }
    }
	// End By Sachin P. call procedure for remove Old Fd records from table. 
	
	//Start: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	public void executeLADExpiryUpdateStage(){
        try {
        	 getJdbcTemplate().execute("{call " + getLadExpiryUpdateStage() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (Exception ex) {
        	ex.printStackTrace();
           
        }
    }
	//End: Uma Khot:added as LAD expiry taking time for exec. So Splitting into 2 parts.
	// added By Sachin P. call procedure for facility & security report matrerialized view . 
	public void executeFacSecNewMviewProc( ) {
        try {
        	 getJdbcTemplate().execute("{call " + getFacSecNewMview() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (Exception ex) {
        	
           
        }
    }
	// End By Sachin P. call procedure for facility & security report matrerialized view . 

	public void executeCmsInterfaceLogBackupProc() {
		try {
			getJdbcTemplate().execute("{call " + getCmsInterfaceLogbkupProc() + "()}",  new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
					cs.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception ex) {
			
			
		}
	}
	
	public void executeIfscCodeInterfaceLogBackupProc() {
		try {
			getJdbcTemplate().execute("{call " + getIfscCodeInterfaceLogBkupProc() + "()}",  new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
					cs.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception ex) {
			
			
		}
	}

	// added By Sachin P. call procedure for refresh recurent matrerialized view . 
	public void executeCustLimitMviewProc( ) {
        try {
        	DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Refresh Materialize View for Customet Total Limit");
        	 getJdbcTemplate().execute("{call " + getCustLimitMview() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (Exception ex) {
        	
           
        }
    }
	// End By Sachin P. procedure for refresh recurent matrerialized view . 
	
	// added By Sachin P. call procedure for remove Old Fd records from table. 
	/*public void executeSPInactiveFdProc( ) {
        try {
        	 getJdbcTemplate().execute("{call SP_INACTIVE_FD()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (Exception ex) {
        	
           
        }
    }*/
	
	// added By Sachin P. call procedure for remove wrong mapped charge id from table. 
	public IEODStatus executeFacChargeIdProc(IEODStatus eodStatus) {
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			DefaultLogger.debug(this, " CollateralDAO.executeFacChargeIdProc() starts ==== ");
	        try{
	        	 getJdbcTemplate().execute("{call " + getFacChargeIdProc() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	        	 eodStatus.setStatus(EODConstants.STATUS_PASS);
	        }catch (Exception ex) {
	        	eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeFacChargeIdProc())" );
				ex.printStackTrace();
	        }
		}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
        DefaultLogger.debug(this, " CollateralDAO.executeFacChargeIdProc() ends ==== ");
        return eodStatus;  
    }
	// End By Sachin P. call procedure for remove wrong mapped charge id from table. 
	
	
	public IEODStatus executeSPInactiveFdProc(IEODStatus eodStatus) {
		//String sql="update CMS_CASH_DEPOSIT set deposit_reference_number = '"+fd.getDepositRefNo()+"', deposit_amount = "+dep_amt+", issue_date = '"+formatDate(fd.getIssueDate().toString())+"',deposit_maturity_date = '"+formatDate(fd.getDepositMaturityDate().toString())+"',verification_date = '"+ver_date+"',deposit_interest_rate="+fd.getDepositeInterestRate();
	//	sql=sql+" where deposit_reference_number = '"+receiptNo+"' and status = 'ACTIVE'" + " and cash_deposit_id in("+SqlQuery+")";
	//	DBUtil dbUtil = null;
		DefaultLogger.debug(this, " CollateralDAO.executeSPInactiveFdProc() starts ==== ");
		String sql="Update CMS_CASH_DEPOSIT set Active='inactive' where CMS_COLLATERAL_ID in (Select reference_id   from TRANSACTION Where TRANSACTION_TYPE='COL') And DEPOSIT_MATURITY_DATE <= (Select to_Date(PARAM_VALUE) from CMS_GENERAL_PARAM where PARAM_CODE='APPLICATION_DATE')";
		String sqlStage="Update CMS_STAGE_CASH_DEPOSIT set Active='inactive' where CMS_COLLATERAL_ID in (Select staging_reference_id   from TRANSACTION Where TRANSACTION_TYPE='COL') And DEPOSIT_MATURITY_DATE <= (Select to_Date(PARAM_VALUE) from CMS_GENERAL_PARAM where PARAM_CODE='APPLICATION_DATE')";
		boolean sqlQueryExecuted = false;
		boolean sqlStageQueryExecuted = false;
		
		if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
				||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(sql);
			    dbUtil.executeUpdate();			  
//				dbUtil.commit();
//				dbUtil.close(true);
				sqlQueryExecuted = true;
			} catch (DBConnectionException e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeSPInactiveFdProc())" );
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeSPInactiveFdProc())" );
				e.printStackTrace();
			} catch (SQLException e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeSPInactiveFdProc())" );
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
//			finalize(dbUtil,null);	
			
			try {
				dbUtil=new DBUtil();			
				dbUtil.setSQL(sqlStage);
				dbUtil.executeUpdate();	
//				dbUtil.commit();
//				dbUtil.close(true);	
				sqlStageQueryExecuted = true;
			} catch (DBConnectionException e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeSPInactiveFdProc())" );
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeSPInactiveFdProc())" );
				e.printStackTrace();
			} catch (SQLException e) {
				eodStatus.setStatus(EODConstants.STATUS_FAIL);
				DefaultLogger.error(this,"CollateralDAO Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : executeSPInactiveFdProc())" );
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
//			finalize(dbUtil,null);	
			
			if(sqlQueryExecuted && sqlStageQueryExecuted){
				eodStatus.setStatus(EODConstants.STATUS_PASS);
			}
			
		}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
				|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
			eodStatus.setStatus(EODConstants.STATUS_DONE);
		}
		DefaultLogger.debug(this, " CollateralDAO.executeSPInactiveFdProc() ends ==== ");
		return eodStatus;
    }
	// End By Sachin P. call procedure for remove Old Fd records from table. 
	//added by Anup K 
	//Date Format function
	public String formatDate(String d){
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.S aa");
		SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Date d1=null;
		try{
		d1=sdf2.parse(d);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return sdf1.format(d1);
	}
//update By sachin P.
//update all Active FD's with respect to FD Recp no. on approve command
	public void setAllFixedFd(ICashDeposit fd) throws SearchDAOException { // New Receipt No
		String dep_amt=fd.getDepositAmount().getAmountAsBigDecimal().toString();
		String ver_date=null;
		String maker_date=null;
		String checker_date=null;
		String maker_id=null;
		String checker_id=null;
		String receiptNo = fd.getDepositRefNo(); 
		String SqlQuery = "select cd.CASH_DEPOSIT_ID "+
        " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
        " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
     				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
     				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
     				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
     				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
     				" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'";

		
		if(null!=fd.getVerificationDate())
			ver_date=formatDate(fd.getVerificationDate().toString());
		else
			ver_date = "";
		if(null!=fd.getMaker_date())
			maker_date=formatDate(fd.getMaker_date().toString());
		else
			maker_date = "";
		if(null!=fd.getChecker_date())
			checker_date=formatDate(fd.getChecker_date().toString());
		else
			checker_date = "";
		if(null!=fd.getMaker_id())
			maker_id=fd.getMaker_id().toString();
		else
			maker_id = "";
		if(null!=fd.getChecker_id())
			checker_id=fd.getChecker_id().toString();
		else
			checker_id = "";
		String sql="update CMS_CASH_DEPOSIT set deposit_amount = "+dep_amt+",issue_date = '"+formatDate(fd.getIssueDate().toString())+"',deposit_maturity_date = '"+formatDate(fd.getDepositMaturityDate().toString())+"',verification_date = '"+ver_date+"',deposit_interest_rate="+fd.getDepositeInterestRate()+", MAKER_ID = '"+maker_id+"'"+", CHECKER_ID = '"+checker_id+"', MAKER_DATE = '"+maker_date+"'"+", CHECKER_DATE = '"+checker_date+"'";
		sql=sql+" where deposit_reference_number = '"+fd.getDepositRefNo()+"' and status = 'ACTIVE'" + " and cash_deposit_id in("+SqlQuery+")";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.executeUpdate();	
			dbUtil.commit();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
	}
	
	public void setAllFixedFdByReceiptNo(ICashDeposit fd,String receiptNo) throws SearchDAOException { //update existing Receipt no.
		String dep_amt=fd.getDepositAmount().getAmountAsBigDecimal().toString();
		String ver_date=null;
		String maker_date=null;
		String checker_date=null;
		String maker_id=null;
		String checker_id=null;
		//String receiptNo = fd.getDepositRefNo(); 
		String SqlQuery = "select cd.CASH_DEPOSIT_ID "+
        " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
        " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
     				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
     				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
     				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
     				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
     				" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'";
 		
		if(null!=fd.getVerificationDate())
			ver_date=formatDate(fd.getVerificationDate().toString());
		else
			ver_date = "";
		if(null!=fd.getMaker_date())
			maker_date=formatDate(fd.getMaker_date().toString());
		else
			maker_date = "";
		if(null!=fd.getChecker_date())
			checker_date=formatDate(fd.getChecker_date().toString());
		else
			checker_date = "";
		if(null!=fd.getMaker_id())
			maker_id=fd.getMaker_id().toString();
		else
			maker_id = "";
		if(null!=fd.getChecker_id())
			checker_id=fd.getChecker_id().toString();
		else
			checker_id = "";
		String sql="update CMS_CASH_DEPOSIT set deposit_reference_number = '"+fd.getDepositRefNo()+"', deposit_amount = "+dep_amt
		+", issue_date = '"+formatDate(fd.getIssueDate().toString())+"',deposit_maturity_date = '"+formatDate(fd.getDepositMaturityDate().toString())+"',verification_date = '"+ver_date
		+"',deposit_interest_rate="+fd.getDepositeInterestRate()+", MAKER_ID = '"+maker_id+"'"+", CHECKER_ID = '"+checker_id+"', MAKER_DATE = '"+maker_date+"'"+", CHECKER_DATE = '"+checker_date+"'";
		sql=sql+" where deposit_reference_number = '"+receiptNo+"' and status = 'ACTIVE'" + " and cash_deposit_id in("+SqlQuery+")";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.executeUpdate();	
			dbUtil.commit();	
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
	}
//update by sachin p.
//get total lien amount for validation purpose
	public double getAllTotalLienAmount(String receiptNo)
			throws SearchDAOException {
		//List l1=new ArrayList();
		String data="";
		double lienAmt=0.0;
		//String sql="select lien_amount from cms_lien where cash_deposit_id in(select cash_deposit_id from cms_cash_deposit where deposit_reference_number='"+receiptNo+"' and status <> 'DELETED' and active='active')";
		String SqlQuery = "select cd.CASH_DEPOSIT_ID "+
        " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
        " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
     				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
     				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
     				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
     				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
     				" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'";
		
		String sql="select lien_amount from cms_lien where cash_deposit_id in("+SqlQuery+")";
		DefaultLogger.debug(this, "In aurionpro sql :" + sql);
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				data=rs.getString("lien_amount");	
				lienAmt=lienAmt+Double.parseDouble(data);
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return lienAmt;
	}
//get total current selected Fd lien amount for validation purpose
	public double getCurrentLienAmount(String cashDepID,String receiptNo)
			throws SearchDAOException {
		//List l1=new ArrayList();
		String data="";
		double lienAmt=0.0;
		//String sql="select lien_amount from cms_lien where cash_deposit_id in (select cash_deposit_id from cms_cash_deposit where deposit_reference_number="+receiptNo+" and status <> 'DELETED' and active='active' and cash_deposit_id="+cashDepID+")";
		String SqlQuery ="select cd.CASH_DEPOSIT_ID "+
        " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
        " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
     				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
     				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
     				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
     				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
     				" AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'"+ "AND cd.cash_deposit_id ="+cashDepID;
			
		String sql="select lien_amount from cms_lien where cash_deposit_id in ("+SqlQuery+")";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				data=rs.getString("lien_amount");	
				lienAmt=lienAmt+Double.parseDouble(data);
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return lienAmt;
	}
	//get total staging lien amount for validation purpose 
public double getStagingCurrentLienAmount(String cashDepID,String receiptNo)
	throws SearchDAOException {
//List l1=new ArrayList();
String data="";
double lienAmt=0.0;
String sql="select lien_amount from cms_stage_lien where cash_deposit_id in (select cash_deposit_id from cms_stage_cash_deposit where deposit_reference_number="+receiptNo+" and status <> 'DELETED' and active='active' and cash_deposit_id = "+cashDepID+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
	dbUtil=new DBUtil();
	dbUtil.setSQL(sql);
	rs=dbUtil.executeQuery();	
	while(rs.next())
	{ 
		data=rs.getString("lien_amount");	
		lienAmt=lienAmt+Double.parseDouble(data);
	}
} catch (DBConnectionException e) {
	e.printStackTrace();
} catch (NoSQLStatementException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
}
finalize(dbUtil,rs);
return lienAmt;
}

public double getNonStagingCurrentLienAmount(String cashDepID,String receiptNo)
throws SearchDAOException {
//List l1=new ArrayList();
String data="";
double lienAmt=0.0;
String sql="select lien_amount from cms_stage_lien where cash_deposit_id in (select cash_deposit_id from cms_stage_cash_deposit where deposit_reference_number= '"+receiptNo+"' and status <> 'DELETED' and active='active' and cash_deposit_id <> "+cashDepID+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
dbUtil=new DBUtil();
dbUtil.setSQL(sql);
rs=dbUtil.executeQuery();	
while(rs.next())
{ 
	data=rs.getString("lien_amount");	
	lienAmt=lienAmt+Double.parseDouble(data);
}
} catch (DBConnectionException e) {
e.printStackTrace();
} catch (NoSQLStatementException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
finalize(dbUtil,rs);
return lienAmt;
}
//get total current selected Fd lien amount for validation purpose
public double getNonDepositCurrentLienAmount(String cashDepID,String receiptNo)
		throws SearchDAOException {
	//List l1=new ArrayList();
	String data="";
	double lienAmt=0.0;
	//String sql="select lien_amount from cms_lien where cash_deposit_id in (select cash_deposit_id from cms_cash_deposit where deposit_reference_number="+receiptNo+" and status <> 'DELETED' and active='active' and cash_deposit_id="+cashDepID+")";
	String SqlQuery ="select cd.CASH_DEPOSIT_ID "+
    " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
    " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
 				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
 				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
 				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
 				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
 				" AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'"+ "AND cd.cash_deposit_id <> "+cashDepID;
		
	String sql="select lien_amount from cms_lien where cash_deposit_id in ("+SqlQuery+")";
	DBUtil dbUtil = null;
	ResultSet rs=null;
	try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			data=rs.getString("lien_amount");	
			lienAmt=lienAmt+Double.parseDouble(data);
		}
	} catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (NoSQLStatementException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finalize(dbUtil,rs);
	return lienAmt;
}
//get total staging lien amount for validation purpose 

public String getReceiptNoByDepositID(String cashDepID)
throws SearchDAOException {
//List l1=new ArrayList();
String data="";
//double lienAmt=0.0;
//String sql="select lien_amount from cms_lien where cash_deposit_id in(select cash_deposit_id from cms_cash_deposit where deposit_reference_number='"+receiptNo+"' and status <> 'DELETED' and active='active')";
//commented by sachin P  on 10-05-2013
/*String sql = "select cd.deposit_reference_number  "+
" FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
" where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
			" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
			" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
			" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
			" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
			" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.CASH_DEPOSIT_ID = '"+cashDepID+"'";*/
String sql="select deposit_reference_number from cms_cash_deposit where cash_deposit_id = '"+cashDepID+"'";
//String sql="select lien_amount from cms_lien where cash_deposit_id in("+SqlQuery+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
dbUtil=new DBUtil();
dbUtil.setSQL(sql);
rs=dbUtil.executeQuery();	
while(rs.next())
{ 
	data=rs.getString("deposit_reference_number");	
	//lienAmt=lienAmt+Double.parseDouble(data);
}
} catch (DBConnectionException e) {
e.printStackTrace();
} catch (NoSQLStatementException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
finalize(dbUtil,rs);
return data;
}

public String getSerchFdFlagByDepositID(String cashDepID,String receiptNo)
throws SearchDAOException {
//List l1=new ArrayList();
String data="N";
//double lienAmt=0.0;
//String sql="select lien_amount from cms_lien where cash_deposit_id in(select cash_deposit_id from cms_cash_deposit where deposit_reference_number='"+receiptNo+"' and status <> 'DELETED' and active='active')";
//commented by sachin P  on 10-05-2013
/*String sql = "select cd.deposit_reference_number  "+
" FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
" where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
			" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
			" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
			" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
			" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
			" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.CASH_DEPOSIT_ID = '"+cashDepID+"'";*/
String sql="select search_flag from cms_cash_deposit where cash_deposit_id = '"+cashDepID+"' and deposit_reference_number='"+receiptNo+"'";
//String sql="select lien_amount from cms_lien where cash_deposit_id in("+SqlQuery+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
dbUtil=new DBUtil();
dbUtil.setSQL(sql);
rs=dbUtil.executeQuery();	
while(rs.next())
{ 
	data=rs.getString("search_flag");	
	//lienAmt=lienAmt+Double.parseDouble(data);
}
} catch (DBConnectionException e) {
e.printStackTrace();
} catch (NoSQLStatementException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
finalize(dbUtil,rs);
return data;
}

public String getStageReceiptNoByDepositID(String cashDepID)
throws SearchDAOException {
//List l1=new ArrayList();
String data="";
//double lienAmt=0.0;
//String sql="select lien_amount from cms_lien where cash_deposit_id in(select cash_deposit_id from cms_cash_deposit where deposit_reference_number='"+receiptNo+"' and status <> 'DELETED' and active='active')";
//commented by sachin P  on 10-05-2013
/*String sql = "select cd.deposit_reference_number  "+
" FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
" where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
			" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
			" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
			" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
			" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
			" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and cd.CASH_DEPOSIT_ID = '"+cashDepID+"'";*/
String sql="select deposit_reference_number from cms_stage_cash_deposit where cash_deposit_id = '"+cashDepID+"'";
//String sql="select lien_amount from cms_lien where cash_deposit_id in("+SqlQuery+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
dbUtil=new DBUtil();
dbUtil.setSQL(sql);
rs=dbUtil.executeQuery();	
while(rs.next())
{ 
	data=rs.getString("deposit_reference_number");	
	//lienAmt=lienAmt+Double.parseDouble(data);
}
} catch (DBConnectionException e) {
e.printStackTrace();
} catch (NoSQLStatementException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
finalize(dbUtil,rs);
return data;
}

public double getTotalLienAmountByCollID(String CollId)
throws SearchDAOException {
//List l1=new ArrayList();
String data="";
double lienAmt=0.0;
//String sql="select lien_amount from cms_lien where cash_deposit_id in (select cash_deposit_id from cms_cash_deposit where deposit_reference_number="+receiptNo+" and status <> 'DELETED' and active='active' and cash_deposit_id="+cashDepID+")";
String SqlQuery ="select cd.CASH_DEPOSIT_ID "+
" FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
" where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
			" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
			" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
			" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
			" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
			" AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and cd.CMS_COLLATERAL_ID  = "+CollId;

String sql="select sum(lien_amount) as totalLien from cms_lien where cash_deposit_id in ("+SqlQuery+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
dbUtil=new DBUtil();
dbUtil.setSQL(sql);
rs=dbUtil.executeQuery();	
while(rs.next())
{ 
	data=rs.getString("totalLien");	
	if(null != data)
	{
	lienAmt=lienAmt+Double.parseDouble(data);
	}
}
} catch (DBConnectionException e) {
e.printStackTrace();
} catch (NoSQLStatementException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
finalize(dbUtil,rs);
return lienAmt;
}


public int getNoOfDepositByDepositReceiptNo(String receiptNo)
throws SearchDAOException {
//List l1=new ArrayList();
int data=0;
//String sql="select lien_amount from cms_lien where cash_deposit_id in (select cash_deposit_id from cms_cash_deposit where deposit_reference_number="+receiptNo+" and status <> 'DELETED' and active='active' and cash_deposit_id="+cashDepID+")";
String sql ="select count(1) as cnt "+
" FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
" where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
			" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
			" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
			" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
			" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID= CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
			" AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"+receiptNo+"'";

//String sql="select lien_amount from cms_lien where cash_deposit_id in ("+SqlQuery+")";
DBUtil dbUtil = null;
ResultSet rs=null;
try {
dbUtil=new DBUtil();
dbUtil.setSQL(sql);
rs=dbUtil.executeQuery();	
while(rs.next())
{ 
	data=rs.getInt("cnt");	
}
} catch (DBConnectionException e) {
e.printStackTrace();
} catch (NoSQLStatementException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
finalize(dbUtil,rs);
return data;
}

public void executeEOYProc() throws Exception  {
    try {
    	 getJdbcTemplate().execute("{call PROC_STATEMENT_REC_EOY_D ()}",  new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
            	cs.executeUpdate();
                return null;
            }
        });
    }
    catch (Exception ex) {
    	ex.printStackTrace();
        throw new Exception(ex);	
       
    }
}


//Added to get whether to get the data from webservice when it is enabled.

public String getFdEnableFlag()throws SearchDAOException{
	String fdWebServiceFlag="N";
	try{
		
	String sql="select param_value from cms_general_param where param_code='FD_ENABLE'";
	fdWebServiceFlag = (String)getJdbcTemplate().queryForObject(sql, String.class);
	
	if(null!=fdWebServiceFlag && !"Y".equalsIgnoreCase(fdWebServiceFlag)){
		fdWebServiceFlag="N";  //When FD_ENABLE is other that Y or y then setting it as N to not to fetch data from webservice 
	}
	
	}
	  catch (Exception ex) {
	    	ex.printStackTrace();
	      //  throw new Exception(ex);
	    	DefaultLogger.error(this, ex.getMessage());
	       
	    }
	  return fdWebServiceFlag;

	}

public String getCmsInterfaceLogbkupProc() {
	return cmsInterfaceLogbkupProc;
}

public void setCmsInterfaceLogbkupProc(String cmsInterfaceLogbkupProc) {
	this.cmsInterfaceLogbkupProc = cmsInterfaceLogbkupProc;
}


public String getCmsFCUBSLogbkupProc() {
	return cmsFCUBSLogbkupProc;
}

public void setCmsFCUBSLogbkupProc(String cmsFCUBSLogbkupProc) {
	this.cmsFCUBSLogbkupProc = cmsFCUBSLogbkupProc;
}

public String getCmsPSRLogbkupProc() {
	return cmsPSRLogbkupProc;
}

public void setCmsPSRLogbkupProc(String cmsPSRLogbkupProc) {
	this.cmsPSRLogbkupProc = cmsPSRLogbkupProc;
}

//Added by Uma Khot: PHASE 3 CR Start:For Create Multiple Security  validation Popup message
public List<String> getSecurityTypeSubTypeForParty(long limitProfileId) throws SearchDAOException{
	
	String sql= "select  SUB.SECURITY_TYPE_ID, SUB.SECURITY_SUB_TYPE_ID "+
	" from SCI_LSP_LMT_PROFILE PF,SCI_LE_SUB_PROFILE SPRO ,CMS_SECURITY SEC,CMS_SECURITY_SUB_TYPE SUB, "+
	 "SECURITY_TYPE TYP,SCI_LSP_APPR_LMTS SCI,CMS_LIMIT_SECURITY_MAP MAPS where "+
	"SEC.SECURITY_SUB_TYPE_ID  = SUB.SECURITY_SUB_TYPE_ID AND SUB.SECURITY_TYPE_ID = TYP.SECURITY_TYPE_ID"+
	" AND SEC.CMS_COLLATERAL_ID  = MAPS.CMS_COLLATERAL_ID AND MAPS.CMS_LSP_APPR_LMTS_ID   = SCI.CMS_LSP_APPR_LMTS_ID"+
	" AND SCI.CMS_LIMIT_PROFILE_ID  = PF.CMS_LSP_LMT_PROFILE_ID AND PF.CMS_CUSTOMER_ID   = Spro.CMS_LE_SUB_PROFILE_ID"+
	" AND MAPS.CHARGE_ID   in (SELECT MAX(MAPS2.CHARGE_ID) from cms_limit_security_map maps2 where maps2.cms_lsp_appr_lmts_id = SCI.cms_lsp_appr_lmts_id"+
	" AND maps2.cms_collateral_id  =sec.cms_collateral_id "+
	 " ) "+
	" AND MAPS.UPDATE_STATUS_IND = 'I' AND SPRO.STATUS='ACTIVE' AND SCI.CMS_LIMIT_STATUS='ACTIVE'"+
	//" AND SCI.CMS_LIMIT_PROFILE_ID='"+limitProfileId+"'";
	" AND SCI.CMS_LIMIT_PROFILE_ID= ? ";
	List<String> queryForList=new ArrayList<String>();
	
	List<Object> param = new ArrayList<Object>();
	param.add(limitProfileId);
	
	try{
	 queryForList = getJdbcTemplate().query(sql,param.toArray(), new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			String str =rs.getString(1)+":"+rs.getString(2);
			System.out.println("getSecurityTypeSubTypeForParty=>str=>"+str);
            return str;
		}
	});


	}
	catch(Exception e){
		e.printStackTrace();
		DefaultLogger.debug(this, e.getMessage());
	}
	return queryForList;
	
}
//Added by Uma Khot: PHASE 3 CR End:For Create Multiple Security  validation Popup message


// Phase 3 CR :Limit Calculation Dashboard 
public double getAllTotalLienAmountBySubProfileId(String partyId,String lmtId, String lineNo)throws SearchDAOException {

	String data="";
	double lienAmt=0.00;

	String SqlQuery = "select cd.CASH_DEPOSIT_ID "+
    " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "+      
    " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "+
 				" AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "+
 				" AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "+
 				" and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "+
 				" AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "+ 
 				" AND lsm.UPDATE_STATUS_IND = 'I' AND cd.status <> 'DELETED'	AND cd.ACTIVE ='active' and  sp.cms_le_sub_profile_id = '"+partyId+"' and lmts.CMS_LSP_APPR_LMTS_ID in (select CMS_LSP_APPR_LMTS_ID from SCI_LSP_APPR_LMTS where lmt_id='"+lmtId+"')" +
 				" AND lien_number='"+lineNo+"'";
	
	String sql="select lien_amount from cms_lien where cash_deposit_id in("+SqlQuery+")";
	DefaultLogger.debug(this, "CollateralDAO.java line 3898=>getAllTotalLienAmountBySubProfileId () =>In aurionpro sql :" + sql);
	DBUtil dbUtil = null;
	ResultSet rs=null;
	try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			data=rs.getString("lien_amount");	
			lienAmt=lienAmt+Double.parseDouble(data);
		}
	} catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (NoSQLStatementException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finalize(dbUtil,rs);
	return lienAmt;
}

@Override
public void purgeLadData() {

	try{
	DefaultLogger.debug(this, "purging the lad data");	
	String sql="DELETE From Cms_Lad_Filter";
	getJdbcTemplate().execute(sql);
	
		}
	  catch (Exception ex) {
	    	ex.printStackTrace();
	      //  throw new Exception(ex);
	    	DefaultLogger.error(this, ex.getMessage());
	       
	    }


	}
//Start Santosh IRB
public String getDpCalculateManuallyByDateAndDocCode(Date selectedDueDate,String selectedDocCode,Long cmsCollateralId)throws SearchDAOException{
	
		String sql = new StringBuffer(" select DP_CALCULATE_MANUALLY from CMS_ASSET_GC_DET ").append(" where DUE_DATE=? and DOC_CODE= ? and CMS_COLLATERAL_ID= ? ").toString();
		return (String) getJdbcTemplate().query(sql, new Object[] {selectedDueDate, selectedDocCode, new Long(cmsCollateralId) }, new ResultSetExtractor() {
			
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return "NO";
			}
		});
}
//End Santosh IRB


		/*public void executeFCUBSActivities(Date startDate,Date endDate) {
			try {
				
				Map<String,String> cmsSidXrefMap=new HashMap<String,String>();
			    Map<String,Long> cmsSidXrefStgMap=new HashMap<String,Long>();
			   
			    SimpleDateFormat sformat=new SimpleDateFormat("dd-MM-yy");
			    String startD = sformat.format(startDate);
			    String endD = sformat.format(endDate);
			    
				getTheLineDetailsToUpdate(startD,endD, cmsSidXrefMap, cmsSidXrefStgMap);
				System.out.println("cmsSidXrefMap:"+cmsSidXrefMap+" cmsSidXrefStgMap:"+cmsSidXrefStgMap);
				//updateAvailableFlag(startDate,endDate);
				updateAvailableFlag(cmsSidXrefMap,cmsSidXrefStgMap);
				executeFCUBSDataBackUpAndSeqReset(); 
			}
			catch (Exception ex) {
				ex.printStackTrace();
		
			}
}*/

public IEODStatus executeFCUBSActivities(Date startDate,Date endDate,IEODStatus eodStatus) {
	
	DefaultLogger.debug(this, " collateralDAO.executeFCUBSActivities() starts ==== ");
	if (EODConstants.STATUS_FAIL.equalsIgnoreCase(eodStatus.getStatus()) 
			||  EODConstants.STATUS_PENDING.equalsIgnoreCase(eodStatus.getStatus())) {
	try {
		
		Map<String,String> cmsSidXrefMap=new HashMap<String,String>();
	    Map<String,Long> cmsSidXrefStgMap=new HashMap<String,Long>();
	   
	    SimpleDateFormat sformat=new SimpleDateFormat("dd-MM-yy");
	    String startD = sformat.format(startDate);
	    String endD = sformat.format(endDate);
	    
		getTheLineDetailsToUpdate(startD,endD, cmsSidXrefMap, cmsSidXrefStgMap);
		System.out.println("cmsSidXrefMap:"+cmsSidXrefMap+" cmsSidXrefStgMap:"+cmsSidXrefStgMap);
		//updateAvailableFlag(startDate,endDate);
		updateAvailableFlag(cmsSidXrefMap,cmsSidXrefStgMap);
		executeFCUBSDataBackUpAndSeqReset(); 
		eodStatus.setStatus(EODConstants.STATUS_PASS);
	}
	catch (Exception ex) {
		eodStatus.setStatus(EODConstants.STATUS_FAIL);
		System.out.println("Exception caught in collateraldao.executeFCUBSActivities method => ex=>"+ex);
		ex.printStackTrace();
	}
	}else if(EODConstants.STATUS_PASS.equalsIgnoreCase(eodStatus.getStatus())
			|| EODConstants.STATUS_DONE.equalsIgnoreCase(eodStatus.getStatus())) {
		eodStatus.setStatus(EODConstants.STATUS_DONE);
	}
	DefaultLogger.debug(this, " collateralDAO.executeFCUBSActivities() ends ==== ");
	return eodStatus;
}
		
		//For PSR CR
		public void executePSRActivities(Date startDate,Date endDate) {
			try {
				System.out.println("In executePSRActivities().........");
				executePSRDataBackUpAndSeqReset(); 
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private void updateAvailableFlag(Date startDate,Date endDate) {
			try{
				
			    String status = ICMSConstant.FCUBS_STATUS_PENDING;
			    String rejectReason = "";
				String queryStr = "update SCI_LSP_SYS_XREF set AVAILABLE ='N',status = ?,CORE_STP_REJECTED_REASON = ? where DATE_OF_RESET BETWEEN ? AND ?";
			    dbUtil = new DBUtil();
				dbUtil.setSQL(queryStr);
				dbUtil.setString(1,status);
				dbUtil.setString(2,rejectReason);
				dbUtil.setTimestamp(3,new Timestamp(startDate.getTime()));
				dbUtil.setTimestamp(4,new Timestamp(endDate.getTime()));
				dbUtil.executeUpdate();
			
			   
			}
			catch(SQLException e){
				
				e.printStackTrace();
			}
			catch(Exception e){
				
				e.printStackTrace();
			}
			
			finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}

		private void executeFCUBSDataBackUpAndSeqReset() {
			
			
			try {
			getJdbcTemplate().execute("{call " + getCmsFCUBSLogbkupProc()+ "()}",  new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
					cs.executeUpdate();
					return null;
				}
			});
			}
			catch (Exception ex) {
				ex.printStackTrace();
		
			}
		}
		
	private void executePSRDataBackUpAndSeqReset() {

		try {
			getJdbcTemplate().execute("{call " + getCmsPSRLogbkupProc() + "()}", new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
					cs.executeUpdate();
					return null;
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
		private void getTheLineDetailsToUpdate(String startD,String endD,Map<String,String> cmsSidXrefMap,Map<String,Long> cmsSidXrefStgMap) {
			try{
				DefaultLogger.debug(this, " getTheLineDetailsToUpdate started.");
				dbUtil = new DBUtil();
			    
			    String cmsSidStr="";
			    
			    ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				String facilitySystem = "'"+bundle.getString("fcubs.systemName")+"','"+bundle.getString("ubs.systemName")+"'";
				
				String query1="select map.cms_lsp_sys_xref_id as xrefId, map.cms_sid as cmsSid, xref.action  as action, xref.status  as status"+
				" from sci_lsp_lmts_xref_map map, SCI_LSP_SYS_XREF xref "+
				" where xref.cms_lsp_sys_xref_id= map.cms_lsp_sys_xref_id  "+
				" and xref.sendtocore='N' and xref.sendtofile='Y' and  xref.close_flag='N' "+
				" and xref.facility_system in ("+facilitySystem+") and xref.date_of_reset between TO_DATE('"+startD+"', 'DD-MM-YY') and TO_DATE('"+endD+"', 'DD-MM-YY')" ;
				//		"'"+startD+"' and '"+endD+"'";
				
				CheckerApproveAADetailCommand cc=new CheckerApproveAADetailCommand();
				ILimitDAO dao1 = LimitDAOFactory.getDAO();
				ResultSet rs=null;
				 Date d = DateUtil.getDate();
				 String dateFormat = "yyMMdd";
				 SimpleDateFormat s=new SimpleDateFormat(dateFormat);
				 String date = s.format(d);
				 
				dbUtil.setSQL(query1);
				 rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					String xrefId = rs.getString("xrefId");
					String cmsSid = rs.getString("cmsSid");
					//String action = rs.getString("action");
					//String xrefStatus = rs.getString("status");
					//String sourceRefNo=cc.generateSourceNo(dao1,date);
					
					
					cmsSidXrefMap.put(cmsSid, xrefId);
					cmsSidStr=cmsSidStr+"'"+cmsSid+"',";
					}
					
				}
				
				if(!cmsSidStr.isEmpty()){
				if(cmsSidStr.endsWith("',")){
					cmsSidStr=cmsSidStr.substring(0, cmsSidStr.length()-1);
				}
				
				String query2="select cms_lsp_sys_xref_id, cms_sid from stage_limit_xref where cms_sid in ("+cmsSidStr+")";
				dbUtil.setSQL(query2);
				rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					Long xrefId = rs.getLong("cms_lsp_sys_xref_id");
					String cmsSid = rs.getString("cms_sid");
					
					
					if(cmsSidXrefStgMap.containsKey(cmsSid)){
						Long xrefIdStg = cmsSidXrefStgMap.get(cmsSid);
						if(xrefId> xrefIdStg)
						cmsSidXrefStgMap.put(cmsSid, xrefId);
					}else{
						cmsSidXrefStgMap.put(cmsSid,xrefId);
					}
				
							}
						}
			}
			}
			catch(SQLException e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			catch(Exception e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			
			finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					DefaultLogger.debug(this,e.getMessage());
					e.printStackTrace();
				}
			}
			
			DefaultLogger.debug(this, " getTheLineDetailsToUpdate completed.");
		}
		
		private void updateAvailableFlag(Map<String, String> cmsSidXrefMap,Map<String, Long> cmsSidXrefStgMap) {
			try{
				
				DefaultLogger.debug(this, " inside updateAvailableFlag.");
			    final String status = ICMSConstant.FCUBS_STATUS_PENDING;
			    final String rejectReason = "";
				
			    String availableStage="update cms_stage_lsp_sys_xref set AVAILABLE ='No' where cms_lsp_sys_xref_id=?";
				String availableActual="update sci_lsp_sys_xref set AVAILABLE ='No' where cms_lsp_sys_xref_id=?";
				

				
				for(Entry<String, String> entrySet : cmsSidXrefMap.entrySet()){
					final String cmsSid = entrySet.getKey();
					String[] split = entrySet.getValue().split(",");
					final String xrefId = split[0];
					//final String sourceRefNo = split[1];
					//final String action = split[2];
					
					  String stageXrefId="";
					if(null!=cmsSidXrefStgMap.get(cmsSid)){
						Long stageXrefIdLong = cmsSidXrefStgMap.get(cmsSid);
						stageXrefId=stageXrefIdLong.toString();
					}
					final String stageXrefIdFinal=stageXrefId;
				int executeUpdate=getJdbcTemplate().update(availableActual, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							//ps.setString(1, status);
							//ps.setString(2, rejectReason);
							//ps.setString(3, sourceRefNo);
							//ps.setString(4, action);
							ps.setString(1, xrefId);
							
						}
					});
				
				 executeUpdate=getJdbcTemplate().update(availableStage, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						//ps.setString(1, status);
						//ps.setString(2, rejectReason);
						//ps.setString(3, sourceRefNo);
						//ps.setString(4, action);
						ps.setString(1, stageXrefIdFinal);
						
					}
				});
				}
			}
			catch(Exception e){
				DefaultLogger.debug(this, e.getMessage());
				e.printStackTrace();
			}finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public Map getCollateralCategoryAndCersaiInd(Long cmsCollateralId) {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT col_master.NEW_COLLATERAL_CATEGORY, col_master.CERSAI_IND FROM CMS_SECURITY sec "); 
			sql.append("INNER JOIN CMS_COLLATERAL_NEW_MASTER col_master ");
			sql.append("ON col_master.NEW_COLLATERAL_CODE = sec.COLLATERAL_CODE "); 
			sql.append("where sec.CMS_COLLATERAL_ID = ? AND col_master.DEPRECATED = ? AND col_master.STATUS = ?");
			
			List<Object> param = new ArrayList<Object>();
			param.add(cmsCollateralId);
			param.add(ICMSConstant.NO);
			param.add(ICMSConstant.TRX_STATE_ACTIVE);
			
			try{
				return (HashMap) getJdbcTemplate().query(sql.toString(), param.toArray(), new ResultSetExtractor() {
					public HashMap extractData(ResultSet rs) throws SQLException, DataAccessException {
						
						HashMap colMasterMap = new HashMap();
						while(rs.next()) {
							colMasterMap.put("COLLATERAL_CATEGORY", rs.getString("NEW_COLLATERAL_CATEGORY"));
							colMasterMap.put("CERSAI_IND", rs.getString("CERSAI_IND"));
						}
						return colMasterMap;
					}
				});
			}
			catch (Exception e) {
				DefaultLogger.debug(this, " exception in getCollateralCategoryFromCollateralMaster: "+e.getMessage());
				System.out.println("exception in getCollateralCategoryFromCollateralMaster: "+e.getMessage());
	        	e.printStackTrace();
				return null;
			}
		}
		
		public double getCalculateddpValueByDueDate(String CollId)
				throws SearchDAOException {
			String data="";
			double lienAmt=0.0;
			String sql="SELECT CALCULATEDDP FROM CMS_ASSET_GC_DET WHERE GC_DET_ID =  " + 
					"  (SELECT MAX(GC_DET_ID) FROM CMS_ASSET_GC_DET WHERE DUE_DATE =  " + 
					"  (SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID ='"+CollId+"') AND CMS_COLLATERAL_ID ='"+CollId+"')";
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				data=rs.getString("CALCULATEDDP");	
				if(null != data)
				{
				lienAmt=lienAmt+Double.parseDouble(data);
				}
			}
			} catch (DBConnectionException e) {
			e.printStackTrace();
			} catch (NoSQLStatementException e) {
			e.printStackTrace();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			finalize(dbUtil,rs);
			return lienAmt;
			}

		public String getFacilityIntLogbkpProc() {
			return facilityIntLogbkpProc;
		}

		public void setFacilityIntLogbkpProc(String facilityIntLogbkpProc) {
			this.facilityIntLogbkpProc = facilityIntLogbkpProc;
		}

		public String getLiabilityIntLogbkpProc() {
			return liabilityIntLogbkpProc;
		}

		public void setLiabilityIntLogbkpProc(String liabilityIntLogbkpProc) {
			this.liabilityIntLogbkpProc = liabilityIntLogbkpProc;
		}

		public void executeFacilityIntLogBkpProc() {
			try {
				DefaultLogger.debug(this,"calling executeFacilityIntLogBkpProc");
				getJdbcTemplate().execute("{call " + getFacilityIntLogbkpProc() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in executeFacilityIntLogBkpProc:"+ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		public void executeLiabilityIntLogBkpProc() {
			try {
				DefaultLogger.debug(this,"calling executeLiabilityIntLogBkpProc");
				getJdbcTemplate().execute("{call " + getLiabilityIntLogbkpProc() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in executeLiabilityIntLogBkpProc:"+ex.getMessage());
				ex.printStackTrace();
				
			}
		}
		
		public List getFacilityList1() throws SearchDAOException {
			String query = "SELECT D.NEW_FACILITY_CODE,D.ID,NEW_FACILITY_NAME "
					+ "FROM CMS_FACILITY_NEW_MASTER D ORDER BY D.NEW_FACILITY_NAME";

			return getJdbcTemplate().query(query, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					OBCollateralSubType subtype = new OBCollateralSubType();
					subtype.setTypeCode(rs.getString("NEW_FACILITY_CODE"));
					subtype.setSubTypeCode(rs.getString("ID"));
					subtype.setTypeName(rs.getString("NEW_FACILITY_NAME"));

					return subtype;
				}
			});
		}
		
		public ArrayList getFacilityList() {
			 ArrayList xrefIdList = new ArrayList();
			try{
				DefaultLogger.debug(this, " getFacilityList() started.");
				dbUtil = new DBUtil();			   
				
				String query1="SELECT NEW_FACILITY_NAME " + 
						"FROM CMS_FACILITY_NEW_MASTER D " + 
						"ORDER BY D.NEW_FACILITY_NAME " ;
				System.out.println("xrefIdList()=> sql query=>"+query1);
				ResultSet rs=null;
				 
				dbUtil.setSQL(query1);
				 rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					String xrefId = rs.getString("NEW_FACILITY_NAME");
					
					xrefIdList.add(xrefId);
					}
				}
				rs.close();
			}
			catch(SQLException e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			catch(Exception e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			
			finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					DefaultLogger.debug(this,e.getMessage());
					e.printStackTrace();
				}
			}
			
			DefaultLogger.debug(this, " getXrefIdList() completed.");
			//return xrefIdList;
			return xrefIdList;
		}
		
		public ArrayList getGoodsParentCodeList() {
			 ArrayList xrefIdList = new ArrayList();
			 DBUtil dbUtil = null;
			try{
				DefaultLogger.debug(this, " getGoodsParentCodeList() started.");
				dbUtil = new DBUtil();			   
				
				String query1="SELECT GOODS_CODE " + 
						" FROM CMS_GOODS_MASTER D where LENGTH(GOODS_CODE)>=4 and LENGTH(GOODS_CODE)<=6 and STATUS='ACTIVE' " + 
						" ORDER BY D.GOODS_CODE " ;
				System.out.println("xrefIdList()=> sql query=>"+query1);
				ResultSet rs=null;
				 
				dbUtil.setSQL(query1);
				 rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					String xrefId = rs.getString("GOODS_CODE");
					xrefIdList.add(xrefId);
					}
				}
				rs.close();
			}
			catch(SQLException e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			catch(Exception e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					DefaultLogger.debug(this,e.getMessage());
					e.printStackTrace();
				}
			}
			DefaultLogger.debug(this, " getXrefIdList() completed.");
			return xrefIdList;
		}
		
		public List getRestrictionTypeList() {
			 List restrictionTypeList = new ArrayList();
			 DBUtil dbUtil = null;
			try{
				DefaultLogger.debug(this, " getRestrictionTypeList() started.");
				dbUtil = new DBUtil();			   
				
				String query1="SELECT ENTRY_CODE " + 
						"FROM COMMON_CODE_CATEGORY_ENTRY J " + 
						"WHERE J.CATEGORY_CODE = 'RESTRICTED_TYPE' AND J.ACTIVE_STATUS = 1 " ;
				System.out.println("getRestrictionTypeList()=> sql query=>"+query1);
				ResultSet rs=null;
				 
				dbUtil.setSQL(query1);
				 rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					String resId = rs.getString("ENTRY_CODE");
					restrictionTypeList.add(resId);
					}
				}
				rs.close();
			}
			catch(SQLException e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			catch(Exception e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					DefaultLogger.debug(this,e.getMessage());
					e.printStackTrace();
				}
			}
			DefaultLogger.debug(this, " getRestrictionTypeList() completed.");
			return restrictionTypeList;
		}
		
		
		public void updateLatestValId(String collateralId,String valuationId,String columnName) {
			try {
				DefaultLogger.debug(this,"calling updateLatestValId");
				String str="update cms_stage_property set "+columnName+"='"+ valuationId+"' where cms_collateral_ID IN " + 
						"(SELECT STAGING_REFERENCE_ID FROM transaction where reference_id='"+collateralId+"')";
				String str2="update cms_property set "+columnName+"='"+ valuationId+"' where cms_collateral_ID ='"+collateralId+"'";
				getJdbcTemplate().update(str);
				DefaultLogger.debug(this,"completed updateLatestValId for stage");
				getJdbcTemplate().update(str2);
				DefaultLogger.debug(this,"completed updateLatestValId for actual");
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in updateLatestValId:"+ex.getMessage());
				ex.printStackTrace();
				
			}
		}
		
		public void createPreviuosMortgageData(final IPropertyCollateral iPropertyCollateral,final String id) {
			try {
				DefaultLogger.debug(this,"calling createPreviuosMortgageData");
				String str="insert into cms_property_mortgage_det (ID,CMS_COLLATERAL_ID,SALE_PURCHASE_DATE, CERSAI_TRX_REF_NO, DATE_CERSAI_REGISTERATION, CERSAI_ID , " + 
						" legalAuditDate,InterveingPeriSearchDate,MARGAGE_TYPE, SALE_PURCHASE_VALUE ) values (?,?,?,?,?,?,?,?,?,?)";
				getJdbcTemplate().update(str, new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						ps.setLong(1, new Long(id));
						ps.setLong(2, iPropertyCollateral.getCollateralID());
						if(null!=iPropertyCollateral.getSalePurchaseDate()) {
						ps.setTimestamp(3, new Timestamp(iPropertyCollateral.getSalePurchaseDate().getTime()));
						}
						else {
							ps.setTimestamp(3,null);	
						}
						ps.setString(4, iPropertyCollateral.getCersaiTransactionRefNumber());
						
						if(null!=iPropertyCollateral.getDateOfCersaiRegisteration()) {
						ps.setDate(5, new java.sql.Date(iPropertyCollateral.getDateOfCersaiRegisteration().getTime()));
						}else {
							ps.setDate(5, null);	
						}
						ps.setString(6, iPropertyCollateral.getCersaiId());
						if(null!=iPropertyCollateral.getLegalAuditDate()) {
							ps.setTimestamp(7, new Timestamp(iPropertyCollateral.getLegalAuditDate().getTime()));
							}
							else {
								ps.setTimestamp(7,null);	
							}
						if(null!=iPropertyCollateral.getInterveingPeriSearchDate()) {
							ps.setTimestamp(8, new Timestamp(iPropertyCollateral.getInterveingPeriSearchDate().getTime()));
							}
							else {
								ps.setTimestamp(8,null);	
							}
						
						ps.setString(9, iPropertyCollateral.getTypeOfMargage());
						if(null!=iPropertyCollateral.getSalePurchaseValue()) {
						ps.setLong(10, new Long(iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().longValue()));
						}else {
						ps.setLong(10, new Long(""));
						}
					}
				});
				
				
				DefaultLogger.debug(this,"completed createPreviuosMortgageData.");
			}	
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in createPreviuosMortgageData:"+ex.getMessage());
				ex.printStackTrace();
				
			
			}
		}
		
		public int CheckPreviousMortData(long collateralID, String  salePurchaseDate) {
			int previousMorCount=0;
			DefaultLogger.debug(this,"calling CheckPreviousMortData");
			try {
				
				String str="select count(1) from  cms_property_mortgage_det  where CMS_COLLATERAL_ID=? and SALE_PURCHASE_DATE=?";
				previousMorCount = getJdbcTemplate().queryForInt(str, new Object[] {collateralID,salePurchaseDate});
				DefaultLogger.debug(this,"completed CheckPreviousMortData previousMorCount:"+previousMorCount);
			}	
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in CheckPreviousMortData:"+ex.getMessage());
				ex.printStackTrace();
			}
			return previousMorCount;
		}
		
		public void updatePreviousMortagageData(final IPropertyCollateral iPropertyCollateral) {
			try {
				DefaultLogger.debug(this,"calling updatePreviousMortagageData");
				String str="update  cms_property_mortgage_det " + 
						"set CERSAI_TRX_REF_NO=?, DATE_CERSAI_REGISTERATION=?, CERSAI_ID =?," + 
						"legalAuditDate=?,InterveingPeriSearchDate=?,MARGAGE_TYPE=?, SALE_PURCHASE_VALUE=?" + 
						"where cms_collateral_id=? and SALE_PURCHASE_DATE=?";
				getJdbcTemplate().update(str, new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, iPropertyCollateral.getCersaiTransactionRefNumber());
						if(null!=iPropertyCollateral.getDateOfCersaiRegisteration()) {
						ps.setDate(2, new java.sql.Date(iPropertyCollateral.getDateOfCersaiRegisteration().getTime()));
						}else {
							ps.setDate(2, null);	
						}
						ps.setString(3, iPropertyCollateral.getCersaiId());
						if(null!=iPropertyCollateral.getLegalAuditDate()) {
							ps.setTimestamp(4, new Timestamp(iPropertyCollateral.getLegalAuditDate().getTime()));
							}
							else {
								ps.setTimestamp(4,null);	
							}
						if(null!=iPropertyCollateral.getInterveingPeriSearchDate()) {
							ps.setTimestamp(5, new Timestamp(iPropertyCollateral.getInterveingPeriSearchDate().getTime()));
							}
							else {
								ps.setTimestamp(5,null);	
							}
						ps.setString(6, iPropertyCollateral.getTypeOfMargage());
						if(null!=iPropertyCollateral.getSalePurchaseValue()) {
						ps.setLong(7, iPropertyCollateral.getSalePurchaseValue().getAmountAsBigDecimal().longValue());
						}else {
						ps.setLong(7, new Long(""));
						}
						ps.setLong(8, iPropertyCollateral.getCollateralID());
						if(null!=iPropertyCollateral.getSalePurchaseDate()) {
						ps.setTimestamp(9, new Timestamp(iPropertyCollateral.getSalePurchaseDate().getTime()));
						}
						else {
							ps.setTimestamp(9,null);	
						}
					}
				});
				
				
				DefaultLogger.debug(this,"completed updatePreviousMortagageData.");
			}	
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in updatePreviousMortagageData:"+ex.getMessage());
				ex.printStackTrace();
				
			
			}
		}
		
		public Long getNextSequnceNumber(String squenceName) {
			String mortgageSql="select "+squenceName+".nextval from dual";
			
				Long  squenceNum =0L;
				try{
					
					squenceNum = getJdbcTemplate().queryForLong(mortgageSql);
				
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getNextSequnceNumber"+e.getMessage());
					throw new IllegalArgumentException(e.getMessage());
				}
				
				return squenceNum;
			}
		
		public List getPreviousValList(long collateralId) {
			String sql="select  id,VALUATION_DATE_V1 from ( " + 
					"select id,VALUATION_DATE_V1 ,ROW_NUMBER()  OVER (ORDER BY id DESC) AS RowRank from CMS_PROPERTY_VAL1 where CMS_COLLATERAL_ID= ? ) " + 
					"where rowrank <> 1 order by id desc";
			List lbValList = new ArrayList();
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				String id=rs.getString("id");
				Timestamp ts=rs.getTimestamp("VALUATION_DATE_V1");
				String valuationDate=new SimpleDateFormat("dd/MMM/yyyy").format(new Date(ts.getYear(), ts.getMonth(), ts.getDate()));
				lbValList.add(new LabelValueBean(valuationDate,id));
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getPreviousValList"+e.getMessage());
					
				}
			return lbValList;
			}
		
		public List getPreviousVal3List(long collateralId) {
			String sql="select  id,VALUATION_DATE_V3 from ( " + 
					"select id,VALUATION_DATE_V3 ,ROW_NUMBER()  OVER (ORDER BY id DESC) AS RowRank from CMS_PROPERTY_VAL3 where CMS_COLLATERAL_ID= ? ) " + 
					"where rowrank <> 1 order by id desc";
			List lbValList = new ArrayList();
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				String id=rs.getString("id");
				Timestamp ts=rs.getTimestamp("VALUATION_DATE_V3");
				String valuationDate=new SimpleDateFormat("dd/MMM/yyyy").format(new Date(ts.getYear(), ts.getMonth(), ts.getDate()));
				lbValList.add(new LabelValueBean(valuationDate,id));
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getPreviousVal3List"+e.getMessage());
					
				}
			return lbValList;
			}
		
		public List getPreMortgageCreationList(long collateralId) {
			String sql="select id,SALE_PURCHASE_DATE from cms_property_mortgage_det where CMS_COLLATERAL_ID=? order by id desc";
			List lbValList = new ArrayList();
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				String id=rs.getString("id");	
				Timestamp ts=rs.getTimestamp("SALE_PURCHASE_DATE");
				
				String valuationDate=new SimpleDateFormat("dd/MMM/yyyy").format(new Date(ts.getYear(), ts.getMonth(), ts.getDate()));
			
				lbValList.add(new LabelValueBean(valuationDate,id));
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getPreMortgageCreationList"+e.getMessage());
					
				}
			return lbValList;
			}
		
		public List getPreMortgageData(String preMortgageDate, String collateralID ) {
			String sql = "select CERSAI_TRX_REF_NO,DATE_CERSAI_REGISTERATION, CERSAI_ID,legalAuditDate,InterveingPeriSearchDate,MARGAGE_TYPE, +"
					+ " SALE_PURCHASE_VALUE from  cms_property_mortgage_det  where CMS_COLLATERAL_ID=? and SALE_PURCHASE_DATE= to_date(?)";
			List resultList=new ArrayList();
			try {
				resultList= getJdbcTemplate().query(sql, new Object[]{collateralID,preMortgageDate} ,new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				//	OBPropertyCollateral oBPropertyCollateral = new OBPropertyCollateral();
					PropertyForm propertyGenForm=new PropCommGeneralForm();
					
					if(rs.getDate("legalAuditDate")!=null ){
						DefaultLogger.debug(this,"legalAuditDate:"+rs.getDate("legalAuditDate")+ "formatted Date:"+new SimpleDateFormat("dd/MMM/yyyy").format(rs.getDate("legalAuditDate")));
						propertyGenForm.setLegalAuditDate(new SimpleDateFormat("dd/MMM/yyyy").format(rs.getDate("legalAuditDate")));
					}
					else{
						propertyGenForm.setLegalAuditDate(null);
					}
					if(rs.getDate("InterveingPeriSearchDate")!=null ){
						propertyGenForm.setInterveingPeriSearchDate(new SimpleDateFormat("dd/MMM/yyyy").format(rs.getDate("InterveingPeriSearchDate")));
					}
					else{
						propertyGenForm.setInterveingPeriSearchDate(null);
					}
					
					propertyGenForm.setTypeOfMargage(rs.getString("MARGAGE_TYPE"));
					
					propertyGenForm.setSalePurchareAmount(UIUtil.formatWithCommaAndDecimal(rs.getString("SALE_PURCHASE_VALUE")));
					
					propertyGenForm.setCersaiTransactionRefNumber(rs.getString("CERSAI_TRX_REF_NO"));
					if(rs.getDate("DATE_CERSAI_REGISTERATION")!=null ){
						propertyGenForm.setDateOfCersaiRegisteration(new SimpleDateFormat("dd/MMM/yyyy").format(rs.getDate("DATE_CERSAI_REGISTERATION")));
					}
					else{
						propertyGenForm.setDateOfCersaiRegisteration(null);;
					}
					propertyGenForm.setCersaiId(rs.getString("CERSAI_ID"));
					
					return propertyGenForm;
				}
			});
		}
		catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in getPreMortgageData"+e.getMessage());
	
			}
			return resultList;
		}
		
		
		public List getPreviousValList2(long collateralId) {
			String sql="select  id,VALUATION_DATE_V2 from ( " + 
					"select id,VALUATION_DATE_V2 ,ROW_NUMBER()  OVER (ORDER BY id DESC) AS RowRank from CMS_PROPERTY_VAL2 where CMS_COLLATERAL_ID= ? ) " + 
					"where rowrank <> 1 order by id desc";
			List lbValList = new ArrayList();
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				String id=rs.getString("id");
				Timestamp ts=rs.getTimestamp("VALUATION_DATE_V2");
				String valuationDate=new SimpleDateFormat("dd/MMM/yyyy").format(new Date(ts.getYear(), ts.getMonth(), ts.getDate()));
				lbValList.add(new LabelValueBean(valuationDate,id));
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getPreviousValList2"+e.getMessage());
					
				}
			return lbValList;
			}
		
		public int syncPropertyValuation(long collateralId, String valNo, boolean isMaster) {
			String propTable = isMaster ? "cms_property" : "cms_stage_property";
			String valTable = isMaster ? "val.cms_collateral_id" : "(select staging_reference_id from transaction where transaction_type='COL' and reference_id = ?)";
			String secTable = isMaster ? "cms_security" : "cms_stage_security";
			Object[] params = null;
			if(!isMaster) {
				params = new Object[2];
				params[0] = collateralId;
				params[1] = collateralId;
			}else {
				params = new Object[1];
				params[0] = collateralId;
			}
			StringBuffer propertySql = new StringBuffer("merge into " + propTable +" prop using (select * from CMS_PROPERTY_VAL"+ valNo +" where cms_collateral_id = ?) val ") 
					.append("on ( " + valTable + " = prop.cms_collateral_id and val.id  = prop.val"+ valNo +"_id) when matched then ") 
					.append("update set ") 
					.append("prop.PROPERTY_ID = val.PROPERTY_ID, prop.DESCRIPTION_ASSET = val.DESCRIPTION_ASSET,prop.PROPERTY_TYPE = val.PROPERTY_TYPE, ") 
					.append("prop.SALE_PURCHASE_VALUE = val.SALE_PURCHASE_VALUE,prop.SALE_PURCHASE_DATE = val.SALE_PURCHASE_DATE, ") 
					.append("prop.MARGAGE_TYPE = val.MARGAGE_TYPE,prop.MORGAGE_CREATED_BY = val.MORGAGE_CREATED_BY, ") 
					.append("prop.DOCUMENT_RECEIVED = val.DOCUMENT_RECEIVED,prop.DOCUMENT_BLOCK = val.DOCUMENT_BLOCK, ") 
					.append("prop.BIN_NUMBER = val.BIN_NUMBER,prop.CLAIM = val.CLAIM, ") 
					.append("prop.CLAIM_TYPE = val.CLAIM_TYPE,prop.ADVOCATE_LAWYER_NAME = val.ADVOCATE_LAWYER_NAME, ") 
					.append("prop.DEVELOPER_GROUP_COMPANY = val.DEVELOPER_GROUP_COMPANY,prop.LOT_NO = val.LOT_NO, ") 
					.append("prop.LOT_NUMBER_PREFIX = val.LOT_NUMBER_PREFIX,prop.PROPERTY_LOT_LOCATION = val.PROPERTY_LOT_LOCATION, ") 
					.append("prop.OTHER_CITY = val.OTHER_CITY,prop.PROPERTY_ADDRESS = val.PROPERTY_ADDRESS, ") 
					.append("prop.PROPERTY_ADDRESS_2 = val.PROPERTY_ADDRESS_2,prop.PROPERTY_ADDRESS_3 = val.PROPERTY_ADDRESS_3, ") 
					.append("prop.PROPERTY_ADDRESS_4 = val.PROPERTY_ADDRESS_4,prop.PROPERTY_ADDRESS_5 = val.PROPERTY_ADDRESS_5, ") 
					.append("prop.PROPERTY_ADDRESS_6 = val.PROPERTY_ADDRESS_6,prop.PROJECT_NAME = val.PROJECT_NAME, ") 
					.append("prop.VALUATION_DATE = val.VALUATION_DATE_V"+ valNo +",prop.VALUATOR_COMPANY = val.VALUATOR_COMPANY_V"+ valNo +", ") 
					.append("prop.TOTAL_PROPERTY_AMOUNT = val.TOTAL_PROPERTY_AMOUNT_V"+ valNo +",prop.CATEGORY_OF_LAND_USE = val.CATEGORY_OF_LAND_USE_V"+ valNo +", ") 
					.append("prop.DEVELOPER_NAME = val.DEVELOPER_NAME_V"+ valNo +",prop.COUNTRY = val.COUNTRY_V"+ valNo +", ") 
					.append("prop.REGION = val.REGION_V"+ valNo +",prop.STATE = val.STATE_V"+ valNo +",prop.NEAREST_CITY = val.NEAREST_CITY_V"+ valNo +", ") 
					.append("prop.PINCODE = val.PINCODE_V"+ valNo +",prop.LAND_AREA = val.LAND_AREA_V"+ valNo +", ") 
					.append("prop.LAND_AREA_UOM = val.LAND_AREA_UOM_V"+ valNo +",prop.IN_SQFT_LAND_AREA = val.IN_SQFT_LAND_AREA_V"+ valNo +", ") 
					.append("prop.BUILTUP_AREA = val.BUILTUP_AREA_V"+ valNo +",prop.BUILTUP_AREA_UOM = val.BUILTUP_AREA_UOM_V"+ valNo +", ") 
					.append("prop.IN_SQFT_BUILTUP_AREA = val.IN_SQFT_BUILTUP_AREA_V"+ valNo +",prop.PROPERTY_COMPLETION_STATUS = val.PROPERTY_COMPLETION_STATUS_V"+ valNo +", ") 
					.append("prop.LAND_VALUE_IRB = val.LAND_VALUE_IRB_V"+ valNo +",prop.BUILDING_VALUE_IRB = val.BUILDING_VALUE_IRB_V"+ valNo +", ") 
					.append("prop.RECONSTRUCTION_VALUE_IRB = val.RECONSTRUCTION_VALUE_IRB_V"+ valNo +",prop.IS_PHY_INSPECT = val.IS_PHY_INSPECT_V"+ valNo +", ") 
					.append("prop.PHY_INSPECT_FREQ_UNIT = val.PHY_INSPECT_FREQ_UNIT_V"+ valNo +",prop.LAST_PHY_INSPECT_DATE = val.LAST_PHY_INSPECT_DATE_V"+ valNo +", ") 
					.append("prop.NEXT_PHY_INSPECT_DATE = val.NEXT_PHY_INSPECT_DATE_V"+ valNo +",prop.REMARKS_PROPERTY = val.REMARKS_PROPERTY_V"+ valNo +", ") 
					.append("prop.ENV_RISKY_STATUS = val.ENV_RISKY_STATUS,prop.ENV_RISKY_DATE = val.ENV_RISKY_DATE, ") 
					.append("prop.TSR_DATE = val.TSR_DATE,prop.NEXT_TSR_DATE = val.NEXT_TSR_DATE, ") 
					.append("prop.TSR_FREQUENCY = val.TSR_FREQUENCY,prop.CERSIA_REG_DATE = val.DATE_CERSAI_REGISTERATION, ") 
					.append("prop.CONSTITUTION = val.CONSTITUTION,prop.ENV_RISKY_REMARKS = val.ENV_RISKY_REMARKS, ") 
					.append("prop.legalAuditDate = val.legalAuditDate,prop.InterveingPeriSearchDate = val.InterveingPeriSearchDate, ") 
					.append("prop.Date_ofReceiptTitleDeed = val.Date_ofReceiptTitleDeed,prop.PreviousMortCreationDate = val.PreviousMortCreationDate, ") 
					.append("prop.MORTAGE_REGISTERED_REF = val.MORTAGE_REGISTERED_REF,prop.PROPERTY_USAGE = val.PROPERTY_USAGE "); 
			
			int res =  getJdbcTemplate().update(propertySql.toString(), params);
			if(res > 0) {
				StringBuffer secSql = new StringBuffer("merge into " + secTable + " sec using (select * from CMS_PROPERTY_VAL"+ valNo +" where cms_collateral_id = ?) val ") 
						.append("on ( " + valTable + " = sec.cms_collateral_id and val.id  = (select val"+ valNo +"_id from cms_property where cms_collateral_id = val.cms_collateral_id)) ") 
						.append("when matched then update set ") 
						.append("sec.SUBTYPE_NAME = val.SUBTYPE_NAME,sec.TYPE_NAME = val.TYPE_NAME,sec.SCI_REFERENCE_NOTE = val.SCI_REFERENCE_NOTE, ") 
						.append("sec.SCI_SECURITY_CURRENCY = val.SCI_SECURITY_CURRENCY,sec.SEC_PRIORITY = val.SEC_PRIORITY, ") 
						.append("sec.MONITOR_PROCESS = val.MONITOR_PROCESS,sec.MONITOR_FREQUENCY = val.MONITOR_FREQUENCY, ") 
						.append("sec.SECURITY_LOCATION = val.SECURITY_LOCATION,sec.SECURITY_ORGANISATION = val.SECURITY_ORGANISATION, ") 
						.append("sec.COLLATERAL_CODE = val.COLLATERAL_CODE,sec.REVAL_FREQ = val.REVAL_FREQ, ") 
						.append("sec.NEXT_VALUATION_DATE = val.NEXT_VALUATION_DATE,sec.CHANGE_TYPE = val.CHANGE_TYPE, ") 
						.append("sec.OTHER_BANK_CHARGE = val.OTHER_BANK_CHARGE,sec.SECURITY_OWNERSHIP = val.SECURITY_OWNERSHIP, ") 
						.append("sec.OWNER_OF_PROPERTY = val.OWNER_OF_PROPERTY,sec.THIRD_PARTY_ENTITY = val.THIRD_PARTY_ENTITY, ") 
						.append("sec.CIN_THIRD_PARTY = val.CIN_THIRD_PARTY,sec.CERSAI_TRX_REF_NO = val.CERSAI_TRX_REF_NO, ") 
						.append("sec.CERSAI_SECURITY_INTEREST_ID = val.CERSAI_SECURITY_INTEREST_ID,sec.CERSAI_ASSET_ID = val.CERSAI_ASSET_ID, ") 
						.append("sec.DATE_CERSAI_REGISTERATION = val.DATE_CERSAI_REGISTERATION,sec.CERSAI_ID = val.CERSAI_ID, ") 
						.append("sec.SALE_DEED_PURCHASE_DATE = val.SALE_DEED_PURCHASE_DATE,sec.THIRD_PARTY_ADDRESS = val.THIRD_PARTY_ADDRESS, ") 
						.append("sec.THIRD_PARTY_STATE = val.THIRD_PARTY_STATE,sec.THIRD_PARTY_CITY = val.THIRD_PARTY_CITY, ") 
						.append("sec.THIRD_PARTY_PINCODE = val.THIRD_PARTY_PINCODE");
				//, sec.cmv = val.total_property_amount_v" + valNo
				
				return getJdbcTemplate().update(secSql.toString(), params);
			}
			
			return res;
		}
		
		
		public String getPartyIdAndGrade(String collateralId) {
			String sql="select LLP_LE_ID,CMS_APPR_OFFICER_GRADE from sci_lsp_lmt_profile where CMS_LSP_LMT_PROFILE_ID in (" + 
					"select CMS_LIMIT_PROFILE_ID from sci_lsp_appr_lmts where lmt_id in " + 
					"(select SCI_LAS_LMT_ID from cms_limit_security_map where CMS_COLLATERAL_ID=?" + 
					"and UPDATE_STATUS_IND='I'))";
			String partyIdAndGrade="";
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				String partyId=rs.getString("LLP_LE_ID");
				String grade=rs.getString("CMS_APPR_OFFICER_GRADE");
				partyIdAndGrade=partyId+"-"+grade;
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getPartyIdAndGrade "+e.getMessage());
					
				}
			return partyIdAndGrade;
			}
		
		
		public String isValuation2Mandatory(String partyId, String totalPropertyAmt, List<OBValuationAmountAndRating> dataResult) {
			
			BigDecimal amt = new BigDecimal(totalPropertyAmt);
			String mandatory = ICMSConstant.NO;
			for(OBValuationAmountAndRating result: dataResult) {
				BigDecimal valAmt = new BigDecimal(result.getValuationAmount());
				int compare = valAmt.compareTo(amt);
				boolean condition = false;
				if(compare == 0) {
					condition = result.getCriteria().equals("<=") || result.getCriteria().equals(">=") || result.getCriteria().equals("=");
				}else if(compare > 0) {
					condition = result.getCriteria().equals("<=") || result.getCriteria().equals("<");
				}else if(compare < 0) {
					condition = result.getCriteria().equals(">=") || result.getCriteria().equals(">");
				}
				if(condition) {
					boolean isExcluded = false;
					if(result.getExcludePartyId() != null && result.getExcludePartyId() != "") {
						for(String party : result.getExcludePartyId().split(",")) {
							if(partyId.equals(party.trim())) {
								isExcluded = true;
								break;
							}
						}
					}
					if(!isExcluded) {
						mandatory = ICMSConstant.YES;
						break;
					}
				}
			}
			
			return mandatory;
		}
		
		public List<OBValuationAmountAndRating> getValuationByRamRating(String grade) {
			StringBuffer sql = new StringBuffer("select * from cms_valuation_amt_rating where status='ACTIVE' and deprecated = 'N' and ram_rating  = ?");
			
			return getJdbcTemplate().query(sql.toString(), new Object[] {grade}, new RowMapper() {
				
				public OBValuationAmountAndRating mapRow(ResultSet rs, int idx) throws SQLException {
					OBValuationAmountAndRating ob =  new OBValuationAmountAndRating();
					ob.setCriteria(rs.getString("criteria"));
					ob.setExcludePartyId(rs.getString("exclude_party_id"));
					ob.setValuationAmount(rs.getString("valuation_amount"));
					return ob;
				}
			} );
		}
		
		public int getLimitIdByMapping(String collateralId) {
			String sql="select count(1) from cms_limit_security_map where CMS_COLLATERAL_ID=?" + 
					" and UPDATE_STATUS_IND='I'";
			int count=0;
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				 count=rs.getInt("count(1)");
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getLimitIdByMapping "+e.getMessage());
					
				}
			return count;
			}
		
		public String getPartyIdAndGradeFromStage(String collateralId) {
			String sql="select LLP_LE_ID,CMS_APPR_OFFICER_GRADE from sci_lsp_lmt_profile where CMS_LSP_LMT_PROFILE_ID in (" + 
					"select CMS_LIMIT_PROFILE_ID from sci_lsp_appr_lmts where lmt_id in " + 
					"(select SCI_LAS_LMT_ID from cms_limit_security_map where CMS_COLLATERAL_ID in " + 
					"(select reference_id from transaction where TRANSACTION_TYPE='COL' and STAGING_REFERENCE_ID=?) "+
					" and UPDATE_STATUS_IND='I'))";
			String partyIdAndGrade="";
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				String partyId=rs.getString("LLP_LE_ID");
				String grade=rs.getString("CMS_APPR_OFFICER_GRADE");
				partyIdAndGrade=partyId+"-"+grade;
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getPartyIdAndGradeFromStage "+e.getMessage());
					
				}
			return partyIdAndGrade;
			}
		
		public String getVersion(long collateralId,int version ) {
			String sql="";
			if(version==1) {
			 sql="select version1 as version from cms_property where cms_collateral_id=?";
			}else if (version==2) {
				 sql="select version2 as version from cms_property where cms_collateral_id=?";
			}else if( version==3) {
				 sql="select version3 as version from cms_property where cms_collateral_id=?";
			}else if( version==123) {
				 sql="select greatest(version1,version2,version3) as version from cms_property where cms_collateral_id=?";
			}else if( version==13) {
				 sql="select greatest(version1,version3) as version from cms_property where cms_collateral_id=?";
			}else {
				return "";
			}
			String versionValue="";
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, collateralId);
			rs=dbUtil.executeQuery();	
			while(rs.next())
			{ 
				versionValue=rs.getString("version");
				
				
			}
			
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this,"Exception in getVersion "+e.getMessage());
					
				}
			return versionValue;
			}
		
		/*public ArrayList getXrefIdList(long collateralId) {
			 ArrayList xrefIdList = new ArrayList();
			try{
				DefaultLogger.debug(this, " getXrefIdList() started.");
				dbUtil = new DBUtil();
			   
				
				String query1="SELECT DISTINCT b.CMS_LSP_SYS_XREF_ID AS XREF_ID, " + 
						"  b.STATUS " + 
						"FROM SCI_LSP_SYS_XREF b, " + 
						"  SCI_LSP_APPR_LMTS a, " + 
						"  SCI_LSP_LMTS_XREF_MAP c , " + 
						"  CMS_LIMIT_SECURITY_MAP MAP " + 
						"WHERE " + 
						"    b.STATUS = 'SUCCESS' " + 
						"AND  " + 
						"c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID " + 
						"AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
						" " + 
						"AND a.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID " + 
						" AND MAP.deletion_date is NULL " +
						"AND a.CMS_LIMIT_STATUS = 'ACTIVE' " +
						"AND c.CMS_STATUS = 'ACTIVE' " +
						"AND MAP.CMS_COLLATERAL_ID = '"+collateralId+"'" ;
				System.out.println("getXrefIdList(long collateralId)=> sql query=>"+query1);
				ResultSet rs=null;
				 
				dbUtil.setSQL(query1);
				 rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					String xrefId = rs.getString("XREF_ID");
					
					xrefIdList.add(xrefId);
					}
				}
				rs.close();
			}
			catch(SQLException e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			catch(Exception e){
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
			
			finally{
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}catch (Exception e) {
					DefaultLogger.debug(this,e.getMessage());
					e.printStackTrace();
				}
			}
			
			DefaultLogger.debug(this, " getXrefIdList() completed.");
			return xrefIdList;
		}*/
		
		public ArrayList getXrefIdList(long collateralId) {
//			 ArrayList xrefIdList = new ArrayList();
			
				DefaultLogger.debug(this, " getXrefIdList() started.");
				System.out.println(" getXrefIdList() started.");
				String query1="SELECT DISTINCT b.CMS_LSP_SYS_XREF_ID AS XREF_ID, " + 
						"  b.STATUS " + 
						"FROM SCI_LSP_SYS_XREF b, " + 
						"  SCI_LSP_APPR_LMTS a, " + 
						"  SCI_LSP_LMTS_XREF_MAP c , " + 
						"  CMS_LIMIT_SECURITY_MAP MAP, " + 
						" CMS_SECURITY SECURITY " +
						"WHERE " + 
						"    b.STATUS = 'SUCCESS' " + 
						"AND  " + 
						"c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID " + 
						"AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
						" " + 
						"AND a.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID " + 
						" AND MAP.deletion_date is NULL " +
						"AND a.CMS_LIMIT_STATUS = 'ACTIVE' " +
						"AND c.CMS_STATUS = 'ACTIVE' " +
						"AND MAP.CMS_COLLATERAL_ID = ? " +
						"AND SECURITY.CMS_COLLATERAL_ID = MAP.CMS_COLLATERAL_ID " +
						"AND SECURITY.STATUS = 'ACTIVE' " +
						"AND MAP.CHARGE_ID   in   " + 
						"					               (SELECT MAX(maps2.CHARGE_ID)   " + 
						"					       from cms_limit_security_map maps2   " + 
						"					       where maps2.cms_lsp_appr_lmts_id = a.cms_lsp_appr_lmts_id   " + 
						"					       AND maps2.cms_collateral_id      =MAP.cms_collateral_id   " + 
						 " AND MAP.cms_collateral_id = SECURITY.CMS_COLLATERAL_ID "
						+ " AND SECURITY.SECURITY_SUB_TYPE_ID = 'AB100' " + 
						"					       )    " + 
						"	AND (MAP.UPDATE_STATUS_IND <> 'D' OR MAP.UPDATE_STATUS_IND IS NULL) ";
				System.out.println("getXrefIdList(long collateralId)=> sql query=>"+query1);
				return (ArrayList) getJdbcTemplate().query(query1, new Object[]{collateralId}, new ResultSetExtractor() {
					public List extractData(ResultSet rs) throws SQLException, DataAccessException {
						ArrayList xrefIdList = new ArrayList();
						while(rs.next()) {
							String xrefId = rs.getString("XREF_ID");
							xrefIdList.add(xrefId);
						}
						return xrefIdList;
					}
				});
		}
		
		public void updateStatusSuccessToPending(ArrayList xrefIdList,ArrayList sourceRefNoList,String stockDocMonth,String stockDocYear) {
				DefaultLogger.debug(this, " updateStatusSuccessToPending(ArrayList xrefIdList) started.");
				try{
					FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
					
					System.out.println("xrefIdList.size() =>"+xrefIdList.size());
					for(int i=0;i<xrefIdList.size();i++) {
						   String xrefId = (String) xrefIdList.get(i);
						   String sourceRefNo=(String) sourceRefNoList.get(i);
					String sql =" update SCI_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' , SOURCE_REF_NO ='"+sourceRefNo+"', STOCK_LIMIT_FLAG = 'Y', STOCK_DOC_MONTH_LMT = '"+stockDocMonth+"', STOCK_DOC_YEAR_LMT = '"+stockDocYear+"'  , SENDTOCORE = 'N'  where CMS_LSP_SYS_XREF_ID ='"+xrefId+"' AND STATUS ='SUCCESS' ";
					System.out.println("updateStatusSuccessToPending(ArrayList xrefIdList) => sql query =>"+sql);
					  getJdbcTemplate().execute(sql);
					}
					}
					catch (Exception e) {
						System.out.println("exception in updateStatusSuccessToPending(ArrayList xrefIdList)");
						e.printStackTrace();
					}
		}
		
		/*public void updateStagingStatusSuccessToPending(ArrayList xrefIdList,ArrayList sourceRefNoList,String stockDocMonth,String stockDocYear) {
			String facilitySystemId = "";
			String lineno = "";
			String facilitySystem = "";
			String liabBranch = "";
			String sql2 = "";
			
			DefaultLogger.debug(this, " updateStagingStatusSuccessToPending(ArrayList xrefIdList) started.");
			try{
				System.out.println("xrefIdList.size() =>"+xrefIdList.size());
				for(int i=0;i<xrefIdList.size();i++) {
					   String xrefId = (String) xrefIdList.get(i);
					   String sourceRefNo=(String) sourceRefNoList.get(i);
				String sql =" SELECT FACILITY_SYSTEM_ID,LINE_NO,FACILITY_SYSTEM,LIAB_BRANCH  FROM SCI_LSP_SYS_XREF WHERE CMS_LSP_SYS_XREF_ID = '"+xrefId+"' ";
				System.out.println("updateStagingStatusSuccessToPending(ArrayList xrefIdList) => sql query =>"+sql);
				ResultSet rs=null;
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				 rs = dbUtil.executeQuery();
				if(null!=rs){
					while(rs.next()){
					 facilitySystemId = rs.getString("FACILITY_SYSTEM_ID");
					 lineno = rs.getString("LINE_NO");
					 facilitySystem = rs.getString("FACILITY_SYSTEM");
					 liabBranch = rs.getString("LIAB_BRANCH");
					
//					 sql2 = " update CMS_STAGE_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' " 
//					 		+ " where CMS_LSP_SYS_XREF_ID = (SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM   CMS_STAGE_LSP_SYS_XREF WHERE LIAB_BRANCH = '"+liabBranch+"' AND FACILITY_SYSTEM_ID='"+facilitySystemId+"' AND LINE_NO='"+lineno+"' AND FACILITY_SYSTEM = '"+facilitySystem+"') ";
					
					 sql2 = "update CMS_STAGE_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' , SOURCE_REF_NO ='"+sourceRefNo+"', STOCK_LIMIT_FLAG = 'Y' , STOCK_DOC_MONTH_LMT = '"+stockDocMonth+"', STOCK_DOC_YEAR_LMT = '"+stockDocYear+"'  , SENDTOCORE = 'N'   " + 
					 		" where CMS_LSP_SYS_XREF_ID = "
					 		+ " (SELECT max(b.CMS_LSP_SYS_XREF_ID) " + 
					 		"					 FROM  " + 
					 		"					 CMS_STAGE_LSP_SYS_XREF b, " + 
					 		"					 STAGE_LIMIT a, " + 
					 		"					 STAGE_LIMIT_XREF c " + 
					 		"					 WHERE  " + 
					 		"					 c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID " + 
					 		"					 AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
					 		"					 AND b.LIAB_BRANCH = '"+liabBranch+"' AND b.FACILITY_SYSTEM_ID='"+facilitySystemId+"' AND b.LINE_NO='"+lineno+"' AND b.FACILITY_SYSTEM = '"+facilitySystem+"' and b.status = 'SUCCESS') ";
					
					 
					 System.out.println("updateStagingStatusSuccessToPending sql2 query =>"+sql2);

					 getJdbcTemplate().execute(sql2);
					 
					}
				}
				rs.close();
				if (dbUtil != null) {
					dbUtil.close();
				}
				}
				}
				catch (Exception e) {
					System.out.println("exception in updateStatusSuccessToPending(ArrayList xrefIdList)");
					e.printStackTrace();
				}
	}*/
	
		public void updateStagingStatusSuccessToPending(ArrayList xrefIdList,ArrayList sourceRefNoList,String stockDocMonth,String stockDocYear) {
			String sql2 = "";
			
			DefaultLogger.debug(this, " updateStagingStatusSuccessToPending(ArrayList xrefIdList) started.");
			System.out.println(" updateStagingStatusSuccessToPending(ArrayList xrefIdList) started.");
			try {
				System.out.println("xrefIdList.size() =>"+xrefIdList.size());
				for(int i=0;i<xrefIdList.size();i++) {
					   String xrefId = (String) xrefIdList.get(i);
					   String sourceRefNo=(String) sourceRefNoList.get(i);
					
//					 sql2 = " update CMS_STAGE_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' " 
//					 		+ " where CMS_LSP_SYS_XREF_ID = (SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM   CMS_STAGE_LSP_SYS_XREF WHERE LIAB_BRANCH = '"+liabBranch+"' AND FACILITY_SYSTEM_ID='"+facilitySystemId+"' AND LINE_NO='"+lineno+"' AND FACILITY_SYSTEM = '"+facilitySystem+"') ";
					
					 sql2 = "update CMS_STAGE_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' , SOURCE_REF_NO ='"+sourceRefNo+"', STOCK_LIMIT_FLAG = 'Y' , STOCK_DOC_MONTH_LMT = '"+stockDocMonth+"', STOCK_DOC_YEAR_LMT = '"+stockDocYear+"' , SENDTOCORE = 'N'  " + 
						 		" where CMS_LSP_SYS_XREF_ID = "
						 		+ " (SELECT  distinct MAX(CMS_LSP_SYS_XREF_ID) FROM   STAGE_LIMIT_XREF WHERE CMS_SID = (SELECT DISTINCT CMS_SID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID = '"+xrefId+"')) AND STATUS ='SUCCESS' ";
					 
					 System.out.println("updateStagingStatusSuccessToPending sql2 query =>"+sql2);
					 getJdbcTemplate().execute(sql2);
				}
		}
		catch (Exception e) {
			System.out.println("exception in updateStagingStatusSuccessToPendingThroughFacility(ArrayList xrefIdList)");
			e.printStackTrace();
		}
	}
		
		
		
		public List getDetailsForFccColFdileUploadJob(String systemName) {
			StringBuffer sql = new StringBuffer(" SELECT DISTINCT CCD.SYSTEM_ID AS SYSTEM_ID, " + 
					"CL.LIEN_NUMBER AS LIEN_NUMBER, " + 
					"CL.SERIAL_NO AS SERIAL_NO, " + 
					"CASE " + 
					"    WHEN  CL.LIEN_NUMBER is not null and CL.SERIAL_NO is not null " + 
					"    THEN  CCD.CMS_COLLATERAL_ID || '_' || CCD.DEPOSIT_REFERENCE_NUMBER || '_'|| CL.LIEN_NUMBER || '_' || CL.SERIAL_NO " + 
					"     ELSE " + 
					"     CCD.CMS_COLLATERAL_ID || '_' || CCD.DEPOSIT_REFERENCE_NUMBER " + 
					"    END AS CMS_COLLATERAL_ID, " + 
					"CCNM.NEW_COLLATERAL_DESCRIPTION AS COLLATERAL_CODE, " + 
					"CASE " + 
					"    WHEN CSEC.SECURITY_SUB_TYPE_ID = 'CS202' " + 
					"    THEN 'FD' " + 
					"    END AS SUBTYPE_NAME, " + 
					"CCD.DEPOSIT_REFERENCE_NUMBER AS DEPOSIT_REFERENCE_NUMBER, " + 
					"CCD.DEPOSIT_AMOUNT AS DEPOSIT_AMOUNT, " + 
					"CL.LIEN_AMOUNT AS LIEN_AMOUNT, " + 
					"APPR.LMT_CRRNCY_ISO_CODE  AS DEPOSIT_AMOUNT_CURRENCY, " +
					"CSEC.SCI_SECURITY_CURRENCY AS SCI_SECURITY_CURRENCY, " +
					"CC.COUNTRY_NAME AS SYSTEM_NAME " +
//					"CCD.SYSTEM_NAME AS SYSTEM_NAME " + 
					"FROM CMS_CASH_DEPOSIT CCD, " + 
					"CMS_SECURITY CSEC, " + 
					"CMS_LIEN CL, " +
					"CMS_COLLATERAL_NEW_MASTER CCNM, " +
					"CMS_COUNTRY CC, " +
					" SCI_LSP_APPR_LMTS APPR, " + 
					"  CMS_LIMIT_SECURITY_MAP MAP, " +
					" SCI_LSP_LMT_PROFILE LMT, " + 
					"	SCI_LE_SUB_PROFILE SLSP, " +
					" SCI_LSP_SYS_XREF b, " + 
					"  SCI_LSP_LMTS_XREF_MAP c " +
					"WHERE  " + 
					"CSEC.CMS_COLLATERAL_ID = CCD.CMS_COLLATERAL_ID AND " + 
					"CCD.CASH_DEPOSIT_ID = CL.CASH_DEPOSIT_ID AND " + 
					"CCD.SYSTEM_NAME = '"+systemName+"'  " + 
//					"AND CSEC.TYPE_NAME IN ('Cash','Deposits') " + 
					
					" AND CSEC.CMS_COLLATERAL_ID            = MAP.CMS_COLLATERAL_ID " + 
					"AND APPR.CMS_LSP_APPR_LMTS_ID        = MAP.CMS_LSP_APPR_LMTS_ID " + 
					" AND LMT.CMS_LSP_LMT_PROFILE_ID       = APPR.CMS_LIMIT_PROFILE_ID " + 
					"AND LMT.LLP_LE_ID                    =SLSP.LSP_LE_ID " + 
					"AND SLSP.STATUS = 'ACTIVE' " +
					"AND c.CMS_LSP_APPR_LMTS_ID = APPR.CMS_LSP_APPR_LMTS_ID " + 
					"AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID  " +
					"AND b.FACILITY_SYSTEM = CCD.SYSTEM_NAME " + 
					"AND  b.FACILITY_SYSTEM_ID = CCD.SYSTEM_ID " + 
					"AND b.LINE_NO = CL.LIEN_NUMBER  " + 
					"AND b.SERIAL_NO = CL.SERIAL_NO " +
					
					"AND MAP.deletion_date is NULL " + 
					"AND APPR.CMS_LIMIT_STATUS = 'ACTIVE' " +
					
//					"AND CSEC.SUBTYPE_NAME = 'Fixed Deposit' " + 
					" AND CSEC.SECURITY_SUB_TYPE_ID = 'CS202' " +
					"AND CSEC.STATUS = 'ACTIVE' " + 
					"AND CCNM.NEW_COLLATERAL_CODE = CSEC.COLLATERAL_CODE " +
					"AND CCD.STATUS = 'ACTIVE' "+
					"AND CSEC.SECURITY_LOCATION = CC.COUNTRY_CODE " +
					" AND APPR.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID " + 
					"AND MAP.CHARGE_ID   in " + 
					"               (SELECT MAX(maps2.CHARGE_ID) " + 
					"       from cms_limit_security_map maps2 " + 
					"       where maps2.cms_lsp_appr_lmts_id = APPR.cms_lsp_appr_lmts_id " + 
					"       AND maps2.cms_collateral_id      =MAP.cms_collateral_id " + 
					"        " + 
					"       )  " + 
					"AND (MAP.UPDATE_STATUS_IND <> 'D' OR MAP.UPDATE_STATUS_IND IS NULL) " +
//					"AND CCD.FD_REBOOKING = 'Y' " +
					"AND CCD.ACTIVE = 'active' " );
//					"AND (CCD.ACTIVE = 'active'  OR CCD.FD_REBOOKING = 'Y') ");
			
			System.out.println("getDetailsForFccColFdileUploadJob(String systemName)=> sql =>"+sql);
			Date today = DateUtil.clearTime(new Date());
			
			return getJdbcTemplate().query(sql.toString(), new Object[] {}, new RowMapper() {
				public HashMap<String, String> mapRow(ResultSet rs, int idx) throws SQLException {
					HashMap<String, String> objMap = new HashMap<String, String>();
					FccColFdFileUploadHeader[] headerItem = FccColFdFileUploadHeader.values();
					for(FccColFdFileUploadHeader item: headerItem) {
						objMap.put(item.name(), rs.getString(item.name()));
					}
					return objMap;
				}
				
			});
			
		}
		
			//Stock DP calculation
		 public List getStockAndDateDetailsWithAssetId(Long secId) throws SearchDAOException {
		        String query = "select genchglist.GC_ASSET_ID,stock.GC_DET_ID, stock.CALCULATEDDP, " + 
		        		" stock.DOC_CODE||','||doc.DOCUMENT_DESCRIPTION docdtls,stock.DP_CALCULATE_MANUALLY,  " + 
		        		" stock.DUE_DATE,stock.DP_SHARE,stock.STOCK_DOC_MONTH,stock.STOCK_DOC_YEAR, stock.REMARK_BY_MAKER" + 
		        		" from CMS_ASSET_GC_DET stock, " + 
		        		" CMS_GEN_ASSET_CHG_DTLS genchglist ,CMS_DOCUMENT_GLOBALLIST doc " + 
		        		" where stock.GC_ASSET_ID = genchglist.GC_ASSET_ID " + 
		        		" and stock.DOC_CODE = doc.DOCUMENT_CODE " + 
		        		" and stock.CMS_COLLATERAL_ID = '"+secId+"' "+
		        		" order by due_date ";
		   	 
		   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
		            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	OBGeneralChargeDetails result = new OBGeneralChargeDetails();
		    			result.setDueDate(rs.getDate("DUE_DATE"));
		    			result.setDpCalculateManually(rs.getString("DP_CALCULATE_MANUALLY"));
		    			result.setDpShare(rs.getString("DP_SHARE"));
		    			result.setCalculatedDP(rs.getString("CALCULATEDDP"));
		    			result.setGeneralChargeDetailsID(rs.getLong("GC_DET_ID"));
		    			result.setDocCode(rs.getString("docdtls"));
		    			result.setStockdocMonth(rs.getString("STOCK_DOC_MONTH"));
		    			result.setStockdocYear(rs.getString("STOCK_DOC_YEAR"));
		    			result.setRemarkByMaker(rs.getString("REMARK_BY_MAKER"));
		                return result;
		            }
		        }
		        );
		   	 return resultList;
		
}

		public List getDetailsForSBBGSBLCFileUploadJob() {
			ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
			final String invrstmentAccNoFlag = resbundle.getString("wms.investment.acc.flag");
	StringBuffer sql;
			
			if(invrstmentAccNoFlag.equalsIgnoreCase("true")) {
				sql= new StringBuffer("SELECT DISTINCT (SELECT region_name FROM cms_region WHERE id=sub_profile.rm_region) region, ")
			  .append("sub_profile.lsp_le_id party_id,sub_profile.lsp_short_name party_name, ")
			  .append("(SELECT CMS_LE_OTHER_SYS_CUST_ID FROM SCI_LE_OTHER_SYSTEM WHERE CMS_LE_MAIN_PROFILE_ID = sub_profile.CMS_LE_MAIN_PROFILE_ID ")
			  .append("AND CMS_LE_SYSTEM_NAME= lmts.facility_system AND ROWNUM=1 ) customer_id, ")
			  .append("(select rm_mgr_name from cms_relationship_mgr where id = sub_profile.relation_mgr) rm_name, ")
			  .append("secDetail.margin,(select entry_name from COMMON_CODE_CATEGORY_ENTRY where entry_code =  ")
			  .append("sub_profile.lsp_sgmnt_code_value and category_code = 'HDFC_SEGMENT') segment, ")
			  .append("secdetail.cms_collateral_id source_sec_id,secdetail.type_name security_type, ")
			  .append("secdetail.subtype_name security_sub_type,secdetail.sci_security_currency currency_code, ")
			  .append("secdetail.cmv security_omv, ld.line_level_sec_omv line_level_sec_omv, ")
			  .append("(translate(guarantee.address_line1,chr(10)||chr(11)||chr(13),' ')||' '|| translate(guarantee.address_line2,chr(10)||chr(11)||chr(13),' ')||' '||replace(guarantee.address_line3,chr(10)||chr(11)||chr(13),' ')) address, ")
			  .append("(SELECT DISTINCT(cri.customer_ram_id) FROM SCI_LE_SUB_PROFILE sp, ")
			  .append("sci_le_subline subline,sci_le_cri cri ")
			  .append("WHERE sp.cms_le_sub_profile_id = subline.cms_le_subline_party_id ")
			  .append("AND subline.cms_le_main_profile_id = sub_profile.cms_le_main_profile_id ")
			  .append("AND sub_profile.cms_le_main_profile_id = cri.cms_le_main_profile_id ")
			  .append(") ram_id, ")
			  .append("to_char(guarantee.guarantee_date,'DD/Mon/YYYY') start_date, ")
			  .append("ld.line_no,ld.lcn_no,ld.serial_no,lmts.facility_system system_name, ")
			  .append("guarantee.guaranters_name bank_name, ")
			  .append("(SELECT translate(system_bank_branch_code,chr(10)||chr(11)||chr(13),' ') ||'-'||translate(system_bank_branch_name,chr(10)||chr(11)||chr(13),' ') ")
			  .append("FROM CMS_SYSTEM_BANK_BRANCH WHERE system_bank_branch_code = secDetail.security_organisation ")
			  .append(") branch_name, ")
			  .append("guarantee.reference_no,translate(guarantee.discription_of_assets,chr(10)||chr(11)||chr(13),' ') description_of_assets, ")
			  .append("TO_CHAR(secdetail.security_maturity_date,'DD/Mon/YYYY') maturity_date, ")
			  .append("TO_CHAR(guarantee.follow_up_date,'DD/Mon/YYYY') follow_up_date, ")
			  .append("case when (lmts.facility_system= 'BAHRAIN' AND sys_xref.INVESTMENT_ACCOUNT_NUMBER is not null) then sys_xref.INVESTMENT_ACCOUNT_NUMBER ")
		      .append("else ' ' ")
			  .append(" END AS INVESTMENT_ACCOUNT_NUMBER ")
			  .append("FROM CMS_LIMIT_SECURITY_MAP lsm, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE pf, ")
			  .append("SCI_LE_SUB_PROFILE sub_profile, CMS_SECURITY secDetail, CMS_GUARANTEE guarantee, ")
			  .append("CMS_SYSTEM_BANK_BRANCH sbb, CMS_LINE_DETAIL ld ")
		       .append(", SCI_LSP_SYS_XREF sys_xref, SCI_LSP_LMTS_XREF_MAP lmt_xref ")
			  .append("where	sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ")
			  .append("AND secDetail.SECURITY_ORGANISATION = sbb.SYSTEM_BANK_BRANCH_CODE(+) ")
			  .append("AND pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID ")
			  .append("AND lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id ")
			  .append("AND (lsm.update_status_ind <> 'D' ")
			  .append("OR lsm.update_status_ind IS NULL) ")
			  .append("AND secdetail.cms_collateral_id = LSM.CMS_COLLATERAL_ID ")
			  .append("AND guarantee.cms_collateral_id = LSM.CMS_COLLATERAL_ID ")
			  .append("AND secDetail.SECURITY_SUB_TYPE_ID IN ('GT400','GT402') ")
			  .append("AND sub_profile.status != 'INACTIVE' ")
			  .append("AND lmts.cms_limit_status <> 'DELETED' ")
			  .append("and secDetail.status = 'ACTIVE' ")
			  .append("and ld.cms_collateral_id(+) = secDetail.cms_collateral_id ")
			  .append("and trunc(secDetail.SECURITY_MATURITY_DATE) > = trunc(sysdate-1) ")
			  .append("and lmts.facility_system in ('BAHRAIN', 'HONGKONG', 'GIFTCITY') ")
			  .append("and lmt_xref.CMS_LSP_SYS_XREF_ID = sys_xref.CMS_LSP_SYS_XREF_ID ")      
			  .append("and sys_xref.LINE_NO = ld.LINE_NO ")
			  .append(" and sys_xref.SERIAL_NO = ld.SERIAL_NO ")
			  .append("order by sub_profile.lsp_short_name ");
			}
			else {
				 sql= new StringBuffer("SELECT DISTINCT (SELECT region_name FROM cms_region WHERE id=sub_profile.rm_region) region, ")
						  .append("sub_profile.lsp_le_id party_id,sub_profile.lsp_short_name party_name, ")
						  .append("(SELECT CMS_LE_OTHER_SYS_CUST_ID FROM SCI_LE_OTHER_SYSTEM WHERE CMS_LE_MAIN_PROFILE_ID = sub_profile.CMS_LE_MAIN_PROFILE_ID ")
						  .append("AND CMS_LE_SYSTEM_NAME= lmts.facility_system AND ROWNUM=1 ) customer_id, ")
						  .append("(select rm_mgr_name from cms_relationship_mgr where id = sub_profile.relation_mgr) rm_name, ")
						  .append("secDetail.margin,(select entry_name from COMMON_CODE_CATEGORY_ENTRY where entry_code =  ")
						  .append("sub_profile.lsp_sgmnt_code_value and category_code = 'HDFC_SEGMENT') segment, ")
						  .append("secdetail.cms_collateral_id source_sec_id,secdetail.type_name security_type, ")
						  .append("secdetail.subtype_name security_sub_type,secdetail.sci_security_currency currency_code, ")
						  .append("secdetail.cmv security_omv, ld.line_level_sec_omv line_level_sec_omv, ")
						  .append("(translate(guarantee.address_line1,chr(10)||chr(11)||chr(13),' ')||' '|| translate(guarantee.address_line2,chr(10)||chr(11)||chr(13),' ')||' '||replace(guarantee.address_line3,chr(10)||chr(11)||chr(13),' ')) address, ")
						  .append("(SELECT DISTINCT(cri.customer_ram_id) FROM SCI_LE_SUB_PROFILE sp, ")
						  .append("sci_le_subline subline,sci_le_cri cri ")
						  .append("WHERE sp.cms_le_sub_profile_id = subline.cms_le_subline_party_id ")
						  .append("AND subline.cms_le_main_profile_id = sub_profile.cms_le_main_profile_id ")
						  .append("AND sub_profile.cms_le_main_profile_id = cri.cms_le_main_profile_id ")
						  .append(") ram_id, ")
						  .append("to_char(guarantee.guarantee_date,'DD/Mon/YYYY') start_date, ")
						  .append("ld.line_no,ld.lcn_no,ld.serial_no,lmts.facility_system system_name, ")
						  .append("guarantee.guaranters_name bank_name, ")
						  .append("(SELECT translate(system_bank_branch_code,chr(10)||chr(11)||chr(13),' ') ||'-'||translate(system_bank_branch_name,chr(10)||chr(11)||chr(13),' ') ")
						  .append("FROM CMS_SYSTEM_BANK_BRANCH WHERE system_bank_branch_code = secDetail.security_organisation ")
						  .append(") branch_name, ")
						  .append("guarantee.reference_no,translate(guarantee.discription_of_assets,chr(10)||chr(11)||chr(13),' ') description_of_assets, ")
						  .append("TO_CHAR(secdetail.security_maturity_date,'DD/Mon/YYYY') maturity_date, ")
			  .append("TO_CHAR(guarantee.follow_up_date,'DD/Mon/YYYY') follow_up_date ")
			  .append("FROM CMS_LIMIT_SECURITY_MAP lsm, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE pf, ")
			  .append("SCI_LE_SUB_PROFILE sub_profile, CMS_SECURITY secDetail, CMS_GUARANTEE guarantee, ")
			  .append("CMS_SYSTEM_BANK_BRANCH sbb, CMS_LINE_DETAIL ld ")
			  .append("where	sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ")
			  .append("AND secDetail.SECURITY_ORGANISATION = sbb.SYSTEM_BANK_BRANCH_CODE(+) ")
			  .append("AND pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID ")
			  .append("AND lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id ")
			  .append("AND (lsm.update_status_ind <> 'D' ")
			  .append("OR lsm.update_status_ind IS NULL) ")
			  .append("AND secdetail.cms_collateral_id = LSM.CMS_COLLATERAL_ID ")
			  .append("AND guarantee.cms_collateral_id = LSM.CMS_COLLATERAL_ID ")
			  .append("AND secDetail.SECURITY_SUB_TYPE_ID IN ('GT400','GT402') ")
			  .append("AND sub_profile.status != 'INACTIVE' ")
			  .append("AND lmts.cms_limit_status <> 'DELETED' ")
			  .append("and secDetail.status = 'ACTIVE' ")
			  .append("and ld.cms_collateral_id(+) = secDetail.cms_collateral_id ")
			  .append("and trunc(secDetail.SECURITY_MATURITY_DATE) > = trunc(sysdate-1) ")
			  .append("and lmts.facility_system in ('BAHRAIN', 'HONGKONG', 'GIFTCITY') ")
			  .append("order by sub_profile.lsp_short_name ");
			}
			Date today = DateUtil.clearTime(new Date());
			return getJdbcTemplate().query(sql.toString(),new RowMapper() {
				public HashMap<String, String> mapRow(ResultSet rs, int idx) throws SQLException {
					HashMap<String, String> objMap = new HashMap<String, String>();
					
					if(invrstmentAccNoFlag.equalsIgnoreCase("true"))
					{
						SBBGSBLCFileUploadNewHeader[] headerItem = SBBGSBLCFileUploadNewHeader.values();
						for(SBBGSBLCFileUploadNewHeader item: headerItem) {
							objMap.put(item.name(), rs.getString(item.name()));
					}
					}
					else {	
					SBBGSBLCFileUploadHeader[] headerItem = SBBGSBLCFileUploadHeader.values();
					for(SBBGSBLCFileUploadHeader item: headerItem) {
						objMap.put(item.name(), rs.getString(item.name()));
					}
					}
					return objMap;
				}
				
			});
		}
		
		public List<HashMap<String, String>> getDetailsForMfEquityFileUploadJob(){
			StringBuffer sql;
			
			ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
			final String invrstmentAccNoFlag = resbundle.getString("wms.investment.acc.flag");
			
			if(invrstmentAccNoFlag.equalsIgnoreCase("true")) {
				sql= new StringBuffer()
					.append("select distinct sub_profile.lsp_le_id as party_id , mf.LMP_LONG_NAME as party_name,  ")
							.append("lmts.facility_system as segment_name, ")//sys_xref.FACILITY_SYSTEM
							.append("lmts.facility_system as branch_code, ")
							.append("sec.cms_collateral_id source_sec_id,  ")
							.append("sec.subtype_name security_sub_type,  ")
							.append("sec.COLLATERAL_CODE, ")
							.append("sec.sci_security_currency currency_code, ")
							.append("translate(dtl.remark,chr(10)||chr(11)||chr(13),' ') remark, ")
							.append("sys_xref.FACILITY_SYSTEM_ID as customer_id, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then portitem.STOCK_CODE  ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then portitem.isin_code  ")
							.append("end as isin, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then  ")
							.append("(select SCHEME_NAME from CMS_MUTUAL_FUNDS_FEED where SCHEME_CODE = (select STOCK_CODE from cms_portfolio_item where ITEM_ID = portitem.ITEM_ID)) ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then (select NAME from cms_price_feed where ISIN_CODE = ")
							.append("(select ISIN_CODE from cms_portfolio_item where ITEM_ID = portitem.ITEM_ID) ")
							.append("AND EXCHANGE =(select DECODE(STOCK_EXCHANGE, '001','BSE','002','NSE','003','Others','') from cms_portfolio_item where ITEM_ID = portitem.ITEM_ID )) ")
							.append("end as scheme_name, ")
							.append("portitem.no_of_units as units, ")
							.append("(select NEW_COLLATERAL_DESCRIPTION from CMS_COLLATERAL_NEW_MASTER where NEW_COLLATERAL_CODE = sec.COLLATERAL_CODE) as collateral_code_name, ")
							.append("dtl.line_no, dtl.serial_no, dtl.ltv, dtl.fas_no, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then round(portitem.nominal_value,4)  ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then round(portitem.UNIT_PRICE,4) ")
							.append("end as unit_price, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then round(portitem.NO_OF_UNITS * portitem.NOMINAL_VALUE,4) ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then round(portitem.NO_OF_UNITS * portitem.UNIT_PRICE,4)  ")
						.append("end as total_value, ")
						.append("case when (lmts.facility_system= 'BAHRAIN' AND sys_xref.INVESTMENT_ACCOUNT_NUMBER is not null) then sys_xref.INVESTMENT_ACCOUNT_NUMBER ") 
						.append("else ' ' ")
						.append("END AS INVESTMENT_ACCOUNT_NUMBER ")
						.append("from sci_lsp_appr_lmts lmts  ")
						.append("INNER JOIN sci_lsp_lmt_profile pf on pf. cms_lsp_lmt_profile_id = lmts.cms_limit_profile_id ")
						.append("INNER JOIN sci_le_sub_profile sub_profile on sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ")
						.append("INNER JOIN SCI_LE_MAIN_PROFILE mf on mf.CMS_LE_MAIN_PROFILE_ID = sub_profile.CMS_LE_MAIN_PROFILE_ID ")
						.append("INNER JOIN cms_limit_security_map lsm on lsm.cms_lsp_appr_lmts_id =  lmts.cms_lsp_appr_lmts_id ")
						.append("INNER JOIN cms_security sec on sec.cms_collateral_id = lsm.cms_collateral_id ")
						.append("INNER JOIN cms_portfolio_item portitem on portitem.cms_collateral_id = lsm.cms_collateral_id ")
						.append("INNER JOIN SCI_LSP_LMTS_XREF_MAP lmt_xref on lmts.CMS_LSP_APPR_LMTS_ID = lmt_xref.CMS_LSP_APPR_LMTS_ID  ")
						.append("INNER JOIN SCI_LSP_SYS_XREF sys_xref on lmt_xref.CMS_LSP_SYS_XREF_ID = sys_xref.CMS_LSP_SYS_XREF_ID ")
						.append("LEFT JOIN cms_line_detail dtl on dtl.portfolio_item_id = portitem.item_id ")
						.append("where ")
						.append("sec.security_sub_type_id in ('MS600','MS605') ")
						.append("and (lsm.update_status_ind <> 'D' ")
						.append("OR lsm.update_status_ind IS NULL) ")
						.append("AND sub_profile.status != 'INACTIVE' ")
						.append("AND lmts.cms_limit_status <> 'DELETED' ")
						.append("AND sec.status = 'ACTIVE' ")
						.append("AND portitem.status<> 'DELETED' ")
						.append("AND lmts.facility_system IN  ('BAHRAIN', 'HONGKONG', 'GIFTCITY') ")
						.append("AND sys_xref.LINE_NO = dtl.LINE_NO ")
						.append("AND sys_xref.SERIAL_NO = dtl.SERIAL_NO ")
						.append("order by mf.LMP_LONG_NAME ");
		
			}
			else {
			sql= new StringBuffer()
					.append("select distinct sub_profile.lsp_le_id as party_id , mf.LMP_LONG_NAME as party_name,  ")
							.append("lmts.facility_system as segment_name, ")//sys_xref.FACILITY_SYSTEM
							.append("lmts.facility_system as branch_code, ")
							.append("sec.cms_collateral_id source_sec_id,  ")
							.append("sec.subtype_name security_sub_type,  ")
							.append("sec.COLLATERAL_CODE, ")
							.append("sec.sci_security_currency currency_code, ")
							.append("translate(dtl.remark,chr(10)||chr(11)||chr(13),' ') remark, ")
							.append("sys_xref.FACILITY_SYSTEM_ID as customer_id, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then portitem.STOCK_CODE  ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then portitem.isin_code  ")
							.append("end as isin, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then  ")
							.append("(select SCHEME_NAME from CMS_MUTUAL_FUNDS_FEED where SCHEME_CODE = (select STOCK_CODE from cms_portfolio_item where ITEM_ID = portitem.ITEM_ID)) ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then (select NAME from cms_price_feed where ISIN_CODE = ")
							.append("(select ISIN_CODE from cms_portfolio_item where ITEM_ID = portitem.ITEM_ID) ")
							.append("AND EXCHANGE =(select DECODE(STOCK_EXCHANGE, '001','BSE','002','NSE','003','Others','') from cms_portfolio_item where ITEM_ID = portitem.ITEM_ID )) ")
							.append("end as scheme_name, ")
							.append("portitem.no_of_units as units, ")
							.append("(select NEW_COLLATERAL_DESCRIPTION from CMS_COLLATERAL_NEW_MASTER where NEW_COLLATERAL_CODE = sec.COLLATERAL_CODE) as collateral_code_name, ")
							.append("dtl.line_no, dtl.serial_no, dtl.ltv, dtl.fas_no, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then round(portitem.nominal_value,4)  ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then round(portitem.UNIT_PRICE,4) ")
							.append("end as unit_price, ")
							.append("case when sec.SECURITY_SUB_TYPE_ID = 'MS600' then round(portitem.NO_OF_UNITS * portitem.NOMINAL_VALUE,4) ")
							.append("when sec.SECURITY_SUB_TYPE_ID = 'MS605' then round(portitem.NO_OF_UNITS * portitem.UNIT_PRICE,4)  ")
							.append("end as total_value ")
							.append("from sci_lsp_appr_lmts lmts  ")
							.append("INNER JOIN sci_lsp_lmt_profile pf on pf. cms_lsp_lmt_profile_id = lmts.cms_limit_profile_id ")
							.append("INNER JOIN sci_le_sub_profile sub_profile on sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ")
							.append("INNER JOIN SCI_LE_MAIN_PROFILE mf on mf.CMS_LE_MAIN_PROFILE_ID = sub_profile.CMS_LE_MAIN_PROFILE_ID ")
							.append("INNER JOIN cms_limit_security_map lsm on lsm.cms_lsp_appr_lmts_id =  lmts.cms_lsp_appr_lmts_id ")
							.append("INNER JOIN cms_security sec on sec.cms_collateral_id = lsm.cms_collateral_id ")
							.append("INNER JOIN cms_portfolio_item portitem on portitem.cms_collateral_id = lsm.cms_collateral_id ")
							.append("INNER JOIN SCI_LSP_LMTS_XREF_MAP lmt_xref on lmts.CMS_LSP_APPR_LMTS_ID = lmt_xref.CMS_LSP_APPR_LMTS_ID  ")
							.append("INNER JOIN SCI_LSP_SYS_XREF sys_xref on lmt_xref.CMS_LSP_SYS_XREF_ID = sys_xref.CMS_LSP_SYS_XREF_ID ")
							.append("LEFT JOIN cms_line_detail dtl on dtl.portfolio_item_id = portitem.item_id ")
							.append("where ")
							.append("sec.security_sub_type_id in ('MS600','MS605') ")
							.append("and (lsm.update_status_ind <> 'D' ")
							.append("OR lsm.update_status_ind IS NULL) ")
							.append("AND sub_profile.status != 'INACTIVE' ")
							.append("AND lmts.cms_limit_status <> 'DELETED' ")
							.append("AND sec.status = 'ACTIVE' ")
							.append("AND portitem.status<> 'DELETED' ")
							.append("AND lmts.facility_system IN  ('BAHRAIN', 'HONGKONG', 'GIFTCITY') ")
							.append("order by mf.LMP_LONG_NAME ");
			}
					return getJdbcTemplate().query(sql.toString(), new RowMapper() {
						public HashMap<String, String> mapRow(ResultSet rs, int idx) throws SQLException {
							HashMap<String, String> objMap = new HashMap<String, String>();
							if(invrstmentAccNoFlag.equalsIgnoreCase("true"))
							{
								MfEquityFileUploadNewHeader[] headerItem = MfEquityFileUploadNewHeader.values();
								for(MfEquityFileUploadNewHeader item: headerItem) {
									if(item.equals(MfEquityFileUploadNewHeader.BRANCH_CODE)) {
										objMap.put(item.name(), PropertyManager.getValue(IMarketableEquityLineDetailConstants.BRANCH_CODE+rs.getString(item.name()).toLowerCase()));
									}
									else {
										objMap.put(item.name(), rs.getString(item.name()));
									}
								}
							}
							else {	
								MfEquityFileUploadHeader[] headerItem = MfEquityFileUploadHeader.values();
								for(MfEquityFileUploadHeader item: headerItem) {
									if(item.equals(MfEquityFileUploadHeader.BRANCH_CODE)) {
										objMap.put(item.name(), PropertyManager.getValue(IMarketableEquityLineDetailConstants.BRANCH_CODE+rs.getString(item.name()).toLowerCase()));
									}
									else {
										objMap.put(item.name(), rs.getString(item.name()));
									}
								}
							}
							
							return objMap;
						}
						
					});
		}
		
		public List<HashMap<String, String>> getDetailsForFdDepositFileUploadJob(){
			ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
			final String invrstmentAccNoFlag = resbundle.getString("wms.investment.acc.flag");
			Date today = DateUtil.clearTime(new Date());
			
			StringBuffer sql;
			
			if(invrstmentAccNoFlag.equalsIgnoreCase("true")) {
				sql= new StringBuffer()
							.append("SELECT sp.LSP_LE_ID as party_id, sp.LSP_SHORT_NAME as party_name, ce.entry_name AS segment_name, sp.BRANCH_CODE, sec.cms_collateral_id AS SOURCE_SEC_ID, ")
							.append("cd.DEPOSIT_AMOUNT as FD_AMOUNT, CD.SYSTEM_ID as customer_id, CD.DEPOSIT_REFERENCE_NUMBER as fd_receipt_number, ")
							.append("TO_CHAR(cd.DEPOSIT_MATURITY_DATE,'DD/Mon/YYYY') as maturity_date, TO_CHAR(cd.ISSUE_DATE,'DD/Mon/YYYY') as deposit_date, cl.LIEN_AMOUNT as fd_lien_amt, ")
							.append("cd.DEPOSIT_INTEREST_RATE as interest_rate, sec.SCI_SECURITY_CURRENCY as currency_code, ")
							.append("(SELECT RM_MGR_NAME FROM CMS_RELATIONSHIP_MGR WHERE id = sp.RELATION_MGR) AS rm_name, ")
							.append("(SELECT REGION_NAME FROM CMS_REGION WHERE id = sp.rm_region) AS rm_region, ")
							.append("CASE WHEN cd.IS_OWN_BANK = 'Y' THEN sp.LSP_SHORT_NAME ")
							.append("  WHEN cd.IS_OWN_BANK != 'Y' THEN cd.DEPOSITOR_NAME ")
							.append("END AS depositor_name, ")
							.append("lmts.FACILITY_NAME as facility_name, cl.lien_number as line_no, cl.serial_no as serial_no, translate(cl.remark,chr(10)||chr(11)||chr(13),' ') as remarks, ")
							.append("cl.LCN_NO as lcn, ")
							.append("get_released_amount(lmts.CMS_LSP_APPR_LMTS_ID,cl.SERIAL_NO) as released_amount, ")
							.append("'FD' as assets_type, temp.amt AS weighted_avg_interest, sec.spread, ")
							.append("(sec.spread + temp.amt) AS effective_rate, cd.maker_id as edited_by, TO_CHAR(cd.maker_date ,'DD/Mon/YYYY') AS edited_on, ")
							.append("cd.checker_id AS last_approved_by, TO_CHAR(cd.checker_date ,'DD/Mon/YYYY') AS last_approved_on, sec.FD_REBOOKING, ")
							.append("case when (lmts.facility_system= 'BAHRAIN' AND xref.INVESTMENT_ACCOUNT_NUMBER is not null) then xref.INVESTMENT_ACCOUNT_NUMBER ")
							.append(" else ' ' ")
							.append(" END AS INVESTMENT_ACCOUNT_NUMBER ")
							.append("FROM CMS_LIMIT_SECURITY_MAP lsm, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE pf, SCI_LE_SUB_PROFILE sp, CMS_CASH_DEPOSIT cd, ")
							.append("CMS_LIEN CL, COMMON_CODE_CATEGORY_ENTRY ce, SCI_LE_REG_ADDR addr, CMS_REGION reg, cms_security sec, ")
							.append("(select round(sum(cd1.deposit_interest_rate * cl1.lien_amount)/sum(cl1.lien_amount),2) as amt ,sec1.cms_collateral_id ")
							.append("from cms_lien cl1,cms_cash_deposit cd1 , cms_security sec1 where sec1.cms_collateral_id = cd1.cms_collateral_id ")
							.append("and cl1.cash_deposit_id = cd1.cash_deposit_id and  CD1.STATUS = 'ACTIVE' and cd1.ACTIVE = 'active' and sec1.status = 'ACTIVE' ")
							.append("group by sec1.cms_collateral_id) temp, SCI_LSP_LMTS_XREF_MAP lmt_xref, SCI_LSP_SYS_XREF xref ")
							.append("WHERE sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID ")
							.append("AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL) AND CD.CASH_DEPOSIT_ID = CL.CASH_DEPOSIT_ID(+) ")
							.append("AND sp.LSP_SGMNT_CODE_VALUE =ce.ENTRY_CODE(+) AND (CD.STATUS = 'ACTIVE') AND sp.status != 'INACTIVE' ")
							.append("AND sp.cms_le_main_profile_id  = addr.cms_le_main_profile_id AND addr.LRA_TYPE_VALUE = 'CORPORATE' ")
							.append("AND addr.lra_region  = reg.id AND ce.category_code = 'HDFC_SEGMENT' and lmts.cms_limit_status <> 'DELETED' ")
							.append("and sec.cms_collateral_id = cd.cms_collateral_id AND lsm.CHARGE_ID in ")
							.append("(SELECT MAX(MAPS2.CHARGE_ID) from cms_limit_security_map maps2 ")
							.append("where maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id AND maps2.cms_collateral_id =sec.cms_collateral_id) ")
							.append("AND lsm.UPDATE_STATUS_IND = 'I' and temp.cms_collateral_id(+) = sec.cms_collateral_id and cd.ACTIVE = 'active' ")
							.append("AND lmts.LMT_ID = CL.FACILITY_ID ")
							.append("AND lmts.facility_system IN  ('BAHRAIN', 'HONGKONG', 'GIFTCITY') ")
							.append("and trunc(cd.DEPOSIT_MATURITY_DATE) >= trunc(sysdate-1) ")
							.append("and xref.SERIAL_NO = CL.SERIAL_NO ")
							.append(" and xref.LINE_NO = CL.LIEN_NUMBER ")
							.append("And lmts.CMS_LSP_APPR_LMTS_ID = lmt_xref.CMS_LSP_APPR_LMTS_ID ")
							.append(" AND lmt_xref.CMS_LSP_SYS_XREF_ID = xref.CMS_LSP_SYS_XREF_ID ")
							.append("order by sp.LSP_SHORT_NAME ");
			}
			else
			{   sql= new StringBuffer()
                    .append("SELECT sp.LSP_LE_ID as party_id, sp.LSP_SHORT_NAME as party_name, ce.entry_name AS segment_name, sp.BRANCH_CODE, sec.cms_collateral_id AS SOURCE_SEC_ID, ")
					.append("cd.DEPOSIT_AMOUNT as FD_AMOUNT, CD.SYSTEM_ID as customer_id, CD.DEPOSIT_REFERENCE_NUMBER as fd_receipt_number, ")
					.append("TO_CHAR(cd.DEPOSIT_MATURITY_DATE,'DD/Mon/YYYY') as maturity_date, TO_CHAR(cd.ISSUE_DATE,'DD/Mon/YYYY') as deposit_date, cl.LIEN_AMOUNT as fd_lien_amt, ")
					.append("cd.DEPOSIT_INTEREST_RATE as interest_rate, sec.SCI_SECURITY_CURRENCY as currency_code, ")
					.append("(SELECT RM_MGR_NAME FROM CMS_RELATIONSHIP_MGR WHERE id = sp.RELATION_MGR) AS rm_name, ")
					.append("(SELECT REGION_NAME FROM CMS_REGION WHERE id = sp.rm_region) AS rm_region, ")
					.append("CASE WHEN cd.IS_OWN_BANK = 'Y' THEN sp.LSP_SHORT_NAME ")
					.append("  WHEN cd.IS_OWN_BANK != 'Y' THEN cd.DEPOSITOR_NAME ")
					.append("END AS depositor_name, ")
					.append("lmts.FACILITY_NAME as facility_name, cl.lien_number as line_no, cl.serial_no as serial_no, translate(cl.remark,chr(10)||chr(11)||chr(13),' ') as remarks, ")
					.append("cl.LCN_NO as lcn, ")
					.append("get_released_amount(lmts.CMS_LSP_APPR_LMTS_ID,cl.SERIAL_NO) as released_amount, ")
					.append("'FD' as assets_type, temp.amt AS weighted_avg_interest, sec.spread, ")
					.append("(sec.spread + temp.amt) AS effective_rate, cd.maker_id as edited_by, TO_CHAR(cd.maker_date ,'DD/Mon/YYYY') AS edited_on, ")
							.append("cd.checker_id AS last_approved_by, TO_CHAR(cd.checker_date ,'DD/Mon/YYYY') AS last_approved_on, sec.FD_REBOOKING ")
							.append("FROM CMS_LIMIT_SECURITY_MAP lsm, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE pf, SCI_LE_SUB_PROFILE sp, CMS_CASH_DEPOSIT cd, ")
							.append("CMS_LIEN CL, COMMON_CODE_CATEGORY_ENTRY ce, SCI_LE_REG_ADDR addr, CMS_REGION reg, cms_security sec, ")
							.append("(select round(sum(cd1.deposit_interest_rate * cl1.lien_amount)/sum(cl1.lien_amount),2) as amt ,sec1.cms_collateral_id ")
							.append("from cms_lien cl1,cms_cash_deposit cd1 , cms_security sec1 where sec1.cms_collateral_id = cd1.cms_collateral_id ")
							.append("and cl1.cash_deposit_id = cd1.cash_deposit_id and  CD1.STATUS = 'ACTIVE' and cd1.ACTIVE = 'active' and sec1.status = 'ACTIVE' ")
							.append("group by sec1.cms_collateral_id) temp ")
							.append("WHERE sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID ")
							.append("AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL) AND CD.CASH_DEPOSIT_ID = CL.CASH_DEPOSIT_ID(+) ")
							.append("AND sp.LSP_SGMNT_CODE_VALUE =ce.ENTRY_CODE(+) AND (CD.STATUS = 'ACTIVE') AND sp.status != 'INACTIVE' ")
							.append("AND sp.cms_le_main_profile_id  = addr.cms_le_main_profile_id AND addr.LRA_TYPE_VALUE = 'CORPORATE' ")
							.append("AND addr.lra_region  = reg.id AND ce.category_code = 'HDFC_SEGMENT' and lmts.cms_limit_status <> 'DELETED' ")
							.append("and sec.cms_collateral_id = cd.cms_collateral_id AND lsm.CHARGE_ID in ")
							.append("(SELECT MAX(MAPS2.CHARGE_ID) from cms_limit_security_map maps2 ")
							.append("where maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id AND maps2.cms_collateral_id =sec.cms_collateral_id) ")
							.append("AND lsm.UPDATE_STATUS_IND = 'I' and temp.cms_collateral_id(+) = sec.cms_collateral_id and cd.ACTIVE = 'active' ")
							.append("AND lmts.LMT_ID = CL.FACILITY_ID ")
							.append("AND lmts.facility_system IN  ('BAHRAIN', 'HONGKONG', 'GIFTCITY') ")
							.append("and trunc(cd.DEPOSIT_MATURITY_DATE) >= trunc(sysdate-1) ")
							.append("order by sp.LSP_SHORT_NAME ");
			}
					return getJdbcTemplate().query(sql.toString(), new RowMapper() {
						public HashMap<String, String> mapRow(ResultSet rs, int idx) throws SQLException {
							HashMap<String, String> objMap = new HashMap<String, String>();
							if(invrstmentAccNoFlag.equalsIgnoreCase("true"))
							{
								FixedDepositFileUploadNewHeader[] headerItem = FixedDepositFileUploadNewHeader.values();
								for(FixedDepositFileUploadNewHeader item: headerItem) {
									objMap.put(item.name(), rs.getString(item.name()));
							}
							}
							else {	
								FixedDepositFileUploadHeader[] headerItem = FixedDepositFileUploadHeader.values();
								for(FixedDepositFileUploadHeader item: headerItem) {
									objMap.put(item.name(), rs.getString(item.name()));
								}
							}
							
							return objMap;
						}
					});
		
		}
		
		
		public void updateStagingLoccalCads(String rmCode) {
			DefaultLogger.debug(this, " updateStagingLoccalCads(String rmCode) started.");
			try{
				String sql =" UPDATE CMS_STG_LOCAL_CAD SET STATUS = 'ACTIVE' WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS IN ('PENDING_DELETE','PENDING_CREATE') ";
				System.out.println("updateStagingLoccalCads(String rmCode) => sql query =>"+sql);
				  getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateStagingLoccalCads(String rmCode)");
					e.printStackTrace();
				}
	}
		
		public void updateActualLoccalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus) {
			DefaultLogger.debug(this, " updateActualLoccalCadsDelete(String rmCode) started.");
			try{
				String sql =" UPDATE CMS_LOCAL_CAD SET STATUS = '"+localCADStatus+"' , DEPRECATED =  'Y' WHERE RM_MGR_ID = '"+rmCode+"' AND CAD_EMPLOYEE_CODE = '"+CadEmployeeId+"' ";
				System.out.println("updateActualLoccalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus) => sql query =>"+sql);
				  getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateActualLoccalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus)");
					e.printStackTrace();
				}
	}
		
		public void updateStagingLoccalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus) {
			DefaultLogger.debug(this, " updateStagingLoccalCadsDelete(String rmCode) started.");
			try{
				String sql =" UPDATE CMS_STG_LOCAL_CAD SET STATUS = '"+localCADStatus+"' , DEPRECATED =  'Y' WHERE RM_MGR_ID = '"+rmCode+"' AND CAD_EMPLOYEE_CODE = '"+CadEmployeeId+"' AND STATUS = 'PENDING_DELETE'";
				System.out.println("updateStagingLoccalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus) => sql query =>"+sql);
				  getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateStagingLoccalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus)");
					e.printStackTrace();
				}
	}

		public void updateStagingLoccalCadsCreate(String rmCode,String CadEmployeeId,String localCADStatus) {
			DefaultLogger.debug(this, " updateStagingLoccalCadsCreate(String rmCode) started.");
			try{
				String sql =" UPDATE CMS_STG_LOCAL_CAD SET STATUS = '"+localCADStatus+"' , DEPRECATED =  'N' WHERE RM_MGR_ID = '"+rmCode+"' AND CAD_EMPLOYEE_CODE = '"+CadEmployeeId+"' AND STATUS = 'PENDING_CREATE'";
				System.out.println("updateStagingLoccalCadsCreate(String rmCode) => sql query =>"+sql);
				  getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateStagingLoccalCadsCreate(String rmCode)");
					e.printStackTrace();
				}
	}
		
		
		public void updateStageNewCreatedLocalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus) {
			DefaultLogger.debug(this, " updateStageNewCreatedLocalCadsDelete(String rmCode) started.");
			try{
				String sql =" UPDATE CMS_STG_LOCAL_CAD SET STATUS = '"+localCADStatus+"' WHERE RM_MGR_ID = '"+rmCode+"' AND CAD_EMPLOYEE_CODE = '"+CadEmployeeId+"' AND STATUS = 'PENDING_CREATE' ";
				System.out.println("updateStageNewCreatedLocalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus) => sql query =>"+sql);
				  getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateStageNewCreatedLocalCadsDelete(String rmCode,String CadEmployeeId,String localCADStatus)");
					e.printStackTrace();
				}
	}
		

		public void updateStatusSuccessToPendingThroughFacility(ArrayList xrefIdList,ArrayList sourceRefNoList,String stockDocMonth,String stockDocYear) {
			DefaultLogger.debug(this, " updateStatusSuccessToPendingThroughFacility(ArrayList xrefIdList) started.");
			try{
				FileUploadJdbcImpl fileUpload=new FileUploadJdbcImpl();
				
				System.out.println("xrefIdList.size() =>"+xrefIdList.size());
				for(int i=0;i<xrefIdList.size();i++) {
					   String xrefId = (String) xrefIdList.get(i);
					   String sourceRefNo=(String) sourceRefNoList.get(i);
				String sql =" update SCI_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' , SOURCE_REF_NO ='"+sourceRefNo+"', STOCK_LIMIT_FLAG = 'N', STOCK_DOC_MONTH_LMT = '"+stockDocMonth+"', STOCK_DOC_YEAR_LMT = '"+stockDocYear+"' , SENDTOCORE = 'N'  where CMS_LSP_SYS_XREF_ID ='"+xrefId+"' AND STATUS ='SUCCESS' ";
				System.out.println("updateStatusSuccessToPendingThroughFacility(ArrayList xrefIdList) => sql query =>"+sql);
				  getJdbcTemplate().execute(sql);
				}
				}
				catch (Exception e) {
					System.out.println("exception in updateStatusSuccessToPendingThroughFacility(ArrayList xrefIdList). e=>"+e);
					e.printStackTrace();
				}
	}
	
	public void updateStagingStatusSuccessToPendingThroughFacility(ArrayList xrefIdList,ArrayList sourceRefNoList,String stockDocMonth,String stockDocYear) {
		String sql2 = "";
		
		DefaultLogger.debug(this, " updateStagingStatusSuccessToPendingThroughFacility(ArrayList xrefIdList) started.");
		try{
			System.out.println("xrefIdList.size() =>"+xrefIdList.size());
			for(int i=0;i<xrefIdList.size();i++) {
				   String xrefId = (String) xrefIdList.get(i);
				   String sourceRefNo=(String) sourceRefNoList.get(i);
				
//				 sql2 = " update CMS_STAGE_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' " 
//				 		+ " where CMS_LSP_SYS_XREF_ID = (SELECT MAX(CMS_LSP_SYS_XREF_ID) FROM   CMS_STAGE_LSP_SYS_XREF WHERE LIAB_BRANCH = '"+liabBranch+"' AND FACILITY_SYSTEM_ID='"+facilitySystemId+"' AND LINE_NO='"+lineno+"' AND FACILITY_SYSTEM = '"+facilitySystem+"') ";
				
				   sql2 = "update CMS_STAGE_LSP_SYS_XREF set STATUS ='PENDING', ACTION = '"+ICMSConstant.FCUBSLIMIT_ACTION_MODIFY+"' , SOURCE_REF_NO ='"+sourceRefNo+"', STOCK_LIMIT_FLAG = 'N' , STOCK_DOC_MONTH_LMT = '"+stockDocMonth+"', STOCK_DOC_YEAR_LMT = '"+stockDocYear+"' , SENDTOCORE = 'N'  " + 
					 		" where CMS_LSP_SYS_XREF_ID = "
					 		+ " (SELECT  distinct MAX(CMS_LSP_SYS_XREF_ID) FROM   STAGE_LIMIT_XREF WHERE CMS_SID = (SELECT DISTINCT CMS_SID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID = '"+xrefId+"')) AND STATUS ='SUCCESS' ";
				 
				 System.out.println("updateStagingStatusSuccessToPendingThroughFacility sql2 query =>"+sql2);
				 getJdbcTemplate().execute(sql2);
			}
			}
			catch (Exception e) {
				System.out.println("exception in updateStagingStatusSuccessToPendingThroughFacility(ArrayList xrefIdList). e=>"+e);
				e.printStackTrace();
			}
}
	
	public ArrayList getXrefIdListFromLmtId(String facilityId) {
//		 ArrayList xrefIdList = new ArrayList();
		
			DefaultLogger.debug(this, " getXrefIdListFromLmtId() started.");
			System.out.println(" getXrefIdListFromLmtId() started.");
			String query1="SELECT DISTINCT b.CMS_LSP_SYS_XREF_ID AS XREF_ID " + 
//					"A.LMT_ID, " + 
//					"  b.STATUS, " + 
//					"  C.CMS_SID " + 
					"FROM SCI_LSP_SYS_XREF b, " + 
					"  SCI_LSP_APPR_LMTS a, " + 
					"  SCI_LSP_LMTS_XREF_MAP c " + 
					"WHERE " + 
					"b.STATUS                 = 'SUCCESS' " + 
					"AND  " + 
					"c.CMS_LSP_APPR_LMTS_ID     = a.CMS_LSP_APPR_LMTS_ID " + 
					"AND c.CMS_LSP_SYS_XREF_ID      = b.CMS_LSP_SYS_XREF_ID " + 
					"AND A.LMT_ID = ? ";
			System.out.println("getXrefIdListFromLmtId(long collateralId,String facilityId)=> sql query=>"+query1);
			return (ArrayList) getJdbcTemplate().query(query1, new Object[]{facilityId}, new ResultSetExtractor() {
				public List extractData(ResultSet rs) throws SQLException, DataAccessException {
					ArrayList xrefIdList = new ArrayList();
					while(rs.next()) {
						String xrefId = rs.getString("XREF_ID");
						xrefIdList.add(xrefId);
					}
					return xrefIdList;
				}
			});
	}
	

	@Override
	public String getCustomerLimitProfileIDByStagingCollateralID(long collateralID) throws SearchDAOException {
		StringBuilder sb = new StringBuilder()
			.append("select prof.CMS_LSP_LMT_PROFILE_ID from transaction trx ")
			.append("inner join sci_lsp_lmt_profile prof on prof.CMS_CUSTOMER_ID = trx.CUSTOMER_ID ")
			.append("where trx.STAGING_REFERENCE_ID= ?");
		
		try {
			return (String)getJdbcTemplate().queryForObject(String.valueOf(sb), new Object[]{collateralID}, String.class);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in getCustomerLimitProfileIDByStagingCollateralID :"+e.getMessage(), e);
			e.printStackTrace();
		}
							
		return null;
	}
	public ArrayList getChildsAgaintParentGoodsCode(String goodsParentCode) {
		 ArrayList childGoodsCodeList = new ArrayList();
		try{
			DefaultLogger.debug(this, " getChildsAgaintParentGoodsCode() started.");
			dbUtil = new DBUtil();			   
			
			String query1="SELECT GOODS_CODE FROM CMS_GOODS_MASTER WHERE GOODS_PARENT_CODE="+goodsParentCode+" AND STATUS='ACTIVE' ";
			System.out.println("Query to fetch Child Goods Code Against Parent Code=> sql query=>"+query1);
			ResultSet rs=null;
			 
			dbUtil.setSQL(query1);
			 rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
				String goodsCode = rs.getString("GOODS_CODE");
				
				childGoodsCodeList.add(goodsCode);
				}
			}
			rs.close();
		}
		catch(SQLException e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		
		finally{
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}catch (Exception e) {
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
		}
		
		DefaultLogger.debug(this, " getChildsAgaintParentGoodsCode completed.");
		return childGoodsCodeList;
	}
	
	public List getFacilityNameList() throws SearchDAOException {
		String sql = "SELECT NEW_FACILITY_NAME,NEW_FACILITY_CODE FROM CMS_FACILITY_NEW_MASTER D WHERE D.STATUS='ACTIVE' ORDER BY D.NEW_FACILITY_NAME";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("NEW_FACILITY_CODE");
				mgnrLst[1] = rs.getString("NEW_FACILITY_NAME");
				return mgnrLst;
			}
		});

		return resultList;
	}
	
    @Override
	public String getMigrationflagStatusUsingDuedate(String duedate, String selectedDocCode,Long cmsCollateralId) {

		
		String sql = "select MIGRATION_FLAG_DP_CR from CMS_ASSET_GC_DET where DUE_DATE='"+duedate+"' and DOC_CODE='"+selectedDocCode+"' and CMS_COLLATERAL_ID="+cmsCollateralId ;
		String MigartionFlagfromDB = "NotPresent";
		DBUtil dbUtil = null;
		ResultSet rs=null; 
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			MigartionFlagfromDB=rs.getString("MIGRATION_FLAG_DP_CR");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in getMigrationflagStatusUsingDuedate "+e.getMessage());
				
			}
		return MigartionFlagfromDB;
	
	}


		 public List getStockAndDateDetailsForEachAssetId(String assetId) throws SearchDAOException {
		        /*String query = "select stock.GC_DET_ID, stock.CALCULATEDDP, " + 
		        		"	stock.DOC_CODE,doc.DOCUMENT_DESCRIPTION,stock.DP_CALCULATE_MANUALLY, " + 
		        		"	stock.DUE_DATE,stock.DP_SHARE,stock.STOCK_DOC_MONTH,stock.STOCK_DOC_YEAR " + 
		        		"   from CMS_ASSET_GC_DET stock, CMS_DOCUMENT_GLOBALLIST doc " + 
		        		"	where stock.DOC_CODE = doc.DOCUMENT_CODE  " + 
		        		"	and stock.GC_DET_ID =  '"+assetId+"' ";*/
			 
			 String query = "select  distinct gcdtl.GC_DET_ID, gcdtl.CALCULATEDDP,  " + 
			 		" (select city_name from cms_city where id = stock.location_id) location_name,stock.LOCATION_DETAIL,gcdtl.FUNDEDSHARE, " + 
			 		" gcdtl.DOC_CODE||','||doc.DOCUMENT_DESCRIPTION docdtls,gcdtl.DP_CALCULATE_MANUALLY, " + 
			 		" gcdtl.DUE_DATE,gcdtl.DP_SHARE,gcdtl.STOCK_DOC_MONTH,gcdtl.STOCK_DOC_YEAR , gcdtl.REMARK_BY_MAKER " + 
			 		" from CMS_ASSET_GC_DET gcdtl, CMS_DOCUMENT_GLOBALLIST doc  ,CMS_ASSET_GC_STOCK_DET stock " + 
			 		" where gcdtl.GC_DET_ID = stock.GC_DET_ID and gcdtl.DOC_CODE = doc.DOCUMENT_CODE "+
			 		" and stock.GC_DET_ID =  '"+assetId+"' ";
			 
		   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
		            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	OBGeneralChargeDetails result = new OBGeneralChargeDetails();
		    			result.setDueDate(rs.getDate("DUE_DATE"));
		    			result.setDpCalculateManually(rs.getString("DP_CALCULATE_MANUALLY"));
		    			result.setDpShare(rs.getString("DP_SHARE"));
		    			result.setCalculatedDP(rs.getString("CALCULATEDDP"));
		    			result.setGeneralChargeDetailsID(rs.getLong("GC_DET_ID"));
		    			result.setDocCode(rs.getString("docdtls"));
		    			result.setStockdocMonth(rs.getString("STOCK_DOC_MONTH"));
		    			result.setStockdocYear(rs.getString("STOCK_DOC_YEAR"));
		    			result.setFundedShare(rs.getString("FUNDEDSHARE"));
		    			result.setRemarkByMaker(rs.getString("REMARK_BY_MAKER"));
		                return result;
		            }
		        }
		        );
		   	 return resultList;
		
}
		 
			public List getLocationForEachAssetId(String assetId) {
				String query  = "select  distinct gcdtl.GC_DET_ID  , " + 
						" (select city_name from cms_city where id = stock.location_id) location_name,stock.LOCATION_DETAIL ,stock.LOCATION_ID " + 
						" from CMS_ASSET_GC_DET gcdtl, CMS_DOCUMENT_GLOBALLIST doc  ,CMS_ASSET_GC_STOCK_DET stock " + 
						" where gcdtl.GC_DET_ID = stock.GC_DET_ID and gcdtl.DOC_CODE = doc.DOCUMENT_CODE  "+
						" and stock.GC_DET_ID =  '"+assetId+"'";

				List resultList = getJdbcTemplate().query(query, new RowMapper() {
			     public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			     	OBGeneralChargeStockDetails result = new OBGeneralChargeStockDetails();
						//result.setApplicableForDp(rs.getString("APPLICABLE_FOR_DP"));
			     	result.setGeneralChargeDetailsID(rs.getLong("GC_DET_ID"));
						result.setLocationId(rs.getLong("LOCATION_ID"));
						result.setLocationDetail(rs.getString("location_name"));
						result.setLonable("0");
			         return result;
			     }
			 }
			 );
				return resultList;
			}
			
	public List<LabelValue> getStockDocLocations(String docCode, long collateralId) {
		if(StringUtils.isBlank(docCode) && collateralId>0)
			return Collections.emptyList();
		
		String sql = "select distinct city.id, city.city_name from cms_city city " + 
				" inner join CMS_ASSET_GC_STOCK_DET stock on stock.location_id = city.id " + 
				" inner join CMS_ASSET_GC_DET charge on charge.gc_det_id = stock.gc_det_id " + 
				" where charge.doc_code = ? and CMS_COLLATERAL_ID = ?";
	
		return getJdbcTemplate().query(sql, new Object[] { docCode, Long.valueOf(collateralId) }, new RowMapper() {
			@Override
			public LabelValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				LabelValue lv = new LabelValue();
				lv.setLabel(rs.getString("city_name"));
				lv.setValue(rs.getString("id"));
				return lv;
			}
		});
	}
			
			public List getStockDisplayList(String assetId) {
				
				 String query = "select (select city_name from cms_city where id = location_id) location_name,LOCATION_ID,LOCATION_DETAIL,GC_STOCK_DET_ID,GC_DET_ID,COMPONENT_AMOUNT,COMPONENT, " + 
				 		" APPLICABLE_FOR_DP,STOCK_TYPE,MARGIN_TYPE,MARGIN,LONABLE " + 
				 		" from CMS_ASSET_GC_STOCK_DET where  GC_DET_ID =  '"+assetId+"' ";
			   	 
			   	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
			            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	OBGeneralChargeStockDetails result = new OBGeneralChargeStockDetails();
			    			result.setApplicableForDp(rs.getString("APPLICABLE_FOR_DP"));
			    			result.setLocationId(rs.getLong("LOCATION_ID"));
			    			result.setLocationDetail(rs.getString("location_name"));
			    			result.setLonable(rs.getString("LONABLE"));
			                return result;
			            }
			        }
			        );
			   	 return resultList;
			}
			
	@SuppressWarnings("unchecked")
	public List<LabelValue> getDueDateList(Long customerId) {
		if(customerId == null || customerId <=0)
			Collections.emptyList();
		
		String sql =" SELECT to_char(cci.expiry_date,'DD/Mon/YYYY') expiry_date , cci.document_code, document_description " + 
				"FROM cms_checklist_item cci  " + 
				"INNER JOIN cms_document_globallist glo ON glo.document_code = cci.document_code " + 
				"INNER JOIN cms_checklist cc ON cc.checklist_id = cci.checklist_id " + 
				"WHERE cc.cms_lsp_lmt_profile_id IN ( " + 
				"    SELECT pf.cms_lsp_lmt_profile_id FROM sci_lsp_lmt_profile pf, sci_le_sub_profile sp  " + 
				"    WHERE sp.cms_le_sub_profile_id = pf.cms_customer_id  " + 
				"    AND pf.cms_customer_id=?) " + 
				"AND cc.CATEGORY='REC' " + 
				"AND glo.CATEGORY='REC' AND glo.statement_type='STOCK_STATEMENT' " + 
				"AND cci.status <>'WAIVED' AND cci.is_deleted='N' " + 
				"AND cci.expiry_date IS NOT NULL " + 
				"ORDER BY cci.expiry_date DESC";
		
		return getJdbcTemplate().query(sql, new Object[] {customerId}, new RowMapper() {
			@Override
			public LabelValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				LabelValue lv = new LabelValue();
				lv.setLabel(rs.getString("expiry_date"));
				lv.setValue(rs.getString("document_code"));
				lv.setDescription(rs.getString("document_description"));
				return lv;
			}
		});

	}

	public Map<String, String> getStatementNames(Long stageCollateralId) throws SearchDAOException {
		if (stageCollateralId == null) 
			return Collections.emptyMap();
		
			String sql = "SELECT document_code, document_description FROM cms_document_globallist doc " + 
					" INNER JOIN cms_stage_asset_gc_det gcd ON gcd.doc_code = doc.document_code " + 
					" WHERE cms_collateral_id=? ";

			return (Map<String, String>) getJdbcTemplate().query(sql, new Object[] { stageCollateralId }, new ResultSetExtractor() {
				@Override
				public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> result = new HashMap<String, String>();
					while(rs.next())
						result.put(rs.getString("document_code"), rs.getString("document_description"));	
					return result;
				}
			});
	}
	
	public Map<String, String> getNonApprovedLinkedFacilities(Long collateralId) {
		if(collateralId==null)
			return Collections.emptyMap();
		
		StringBuilder sb = new StringBuilder()
				.append("SELECT DISTINCT APPR.LMT_ID ,LMT.LLP_LE_ID AS CIF ")
				.append("FROM SCI_LSP_APPR_LMTS APPR ,CMS_LIMIT_SECURITY_MAP SEC_MAP ,SCI_LSP_LMT_PROFILE LMT,TRANSACTION TRX  ")
				.append("WHERE SEC_MAP.CMS_LSP_APPR_LMTS_ID = APPR.CMS_LSP_APPR_LMTS_ID AND APPR.Cms_Limit_Status ='ACTIVE' ")
				.append("AND LMT.CMS_LSP_LMT_PROFILE_ID = APPR.CMS_LIMIT_PROFILE_ID AND APPR.CMS_LSP_APPR_LMTS_ID = TRX.REFERENCE_ID  ")
				.append("AND TRX.STATUS NOT IN ( 'ACTIVE','DELETED') ")
				.append("AND TRX.TRANSACTION_TYPE           = 'LIMIT' ")
				.append("AND SEC_MAP.CMS_COLLATERAL_ID      = ? ");
		
		try {
			return (Map<String, String>) getJdbcTemplate().query(String.valueOf(sb), new Object[] { collateralId }, new ResultSetExtractor() {
				@Override
				public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> result = new HashMap<String, String>();
					while(rs.next())
						result.put(rs.getString("LMT_ID"), rs.getString("cif"));
					return result;
				}
			});
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Excepton caught in getNonApprovedLinkedFacilities :"+e.getMessage(), e);
		}
		
		return Collections.emptyMap();
	}
	
	public BigDecimal getTotalLimitReleasedAmtForLinkedFacilities(Long collateralId) {
		if(collateralId==null)
			return BigDecimal.ZERO;
		StringBuilder sb = new StringBuilder()
				.append("select sum(CONVERT_AMT(TOTAL_RELEASED_AMOUNT,TRIM(LMT_CRRNCY_ISO_CODE),'INR')) as TOTAL_RELEASED_AMOUNT ")
				.append("from SCI_LSP_APPR_LMTS where   IS_DP='Y' and IS_DP_REQUIRED='Y' AND CMS_LIMIT_STATUS='ACTIVE' "
						+ "AND LMT_TYPE_VALUE ='No' AND CMS_LSP_APPR_LMTS_ID IN  ")
				.append("(select distinct CMS_LSP_APPR_LMTS_ID from CMS_LIMIT_SECURITY_MAP  "
						+ "where (UPDATE_STATUS_IND='I' OR UPDATE_STATUS_IND is null) AND CMS_COLLATERAL_ID = ?  )");
		
		try {
			return (BigDecimal) getJdbcTemplate().queryForObject(String.valueOf(sb), new Object[] {collateralId}, BigDecimal.class);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Excepton caught in getNonApprovedLinkedFacilities :"+e.getMessage(), e);
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal getCalculatedDpForGCCollateral(Long collateralId) {
		if(collateralId==null)
			return BigDecimal.ZERO;
		StringBuilder sb = new StringBuilder()
				.append("select CALCULATEDDP from (select gc.CALCULATEDDP from cms_security sec ")
				.append("inner join CMS_ASSET_GC_DET gc on sec.CMS_COLLATERAL_ID = gc.CMS_COLLATERAL_ID ")
				.append("where sec.CMS_COLLATERAL_ID = ? and gc.DUE_DATE is not null ")
				.append("order by gc.DUE_DATE desc) where rownum=1");
		
		try {
			  if(null != getJdbcTemplate().queryForObject(String.valueOf(sb), new Object[] {collateralId}, BigDecimal.class) ) {
				return  (BigDecimal)  getJdbcTemplate().queryForObject(String.valueOf(sb), new Object[] {collateralId}, BigDecimal.class) ;
			  }else {
				  return BigDecimal.ZERO;
			  }
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Excepton caught in getCalculatedDpForGCCollateral, since could not find Due Date records for :"+collateralId+" - "+e.getMessage(), e);
		}
		return BigDecimal.ZERO;
	}
	
		public OBCollateral searchCollateralByIdSubtypeRest(LmtColSearchCriteria criteria) throws SearchDAOException {
		List argList = new ArrayList();

		/*		String query = "SELECT DISTINCT COL.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID, COL.SECURITY_SUB_TYPE_ID, TYPE_NAME, SUBTYPE_NAME "
						+ "FROM CMS_SECURITY COL, CMS_SECURITY_SOURCE SS WHERE COL.STATUS = '"
						+ ICMSConstant.STATE_ACTIVE
						+ "' AND " + "SS.STATUS <> 'DELETED' AND COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND ";*/
				
				StringBuffer query =new StringBuffer();	

				query.append("SELECT DISTINCT sub_profile.lsp_short_name, COL.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID, COL.SECURITY_SUB_TYPE_ID, COL.TYPE_NAME,");
				query.append("	COL.SECURITY_LOCATION, COL.SCI_REFERENCE_NOTE, COL.SCI_ORIG_SECURITY_CURRENCY, COL.SECURITY_ORGANISATION, COL.SEC_PRIORITY, "); //A Shiv 041111
				query.append(" 	COL.SUBTYPE_NAME , COLNEWMAST.NEW_COLLATERAL_DESCRIPTION  , COL.COLLATERAL_CODE, PROP.PROPERTY_ID");
				query.append(" FROM ");
				query.append(" 	CMS_SECURITY_SOURCE SS, CMS_LIMIT_SECURITY_MAP SECMAP ");
				query.append(" 	,CMS_COLLATERAL_NEW_MASTER COLNEWMAST,SCI_LSP_APPR_LMTS lmts,");
				query.append(" 	SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sub_profile, CMS_SECURITY COL left outer join  cms_property prop on prop.cms_collateral_id = col.cms_collateral_id ");
				query.append(" WHERE ");
				/*if (criteria.getCustName() != null && criteria.getPropSearchId() != null 
						&& !criteria.getCustName().trim().equals("") && !criteria.getPropSearchId().trim().equals("")) {
					query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND UPPER(sub_profile.lsp_short_name) Like UPPER('%"+ criteria.getCustName()+"%') AND ");
				}*/
				if (criteria.getCustName() != null && !criteria.getCustName().trim().equals("")) {
					query.append(" sub_profile.LSP_LE_ID = '"+ criteria.getCustName()+"' AND ");
				}
				/*if (criteria.getPropSearchId() != null && !criteria.getPropSearchId().trim().equals("")) {
					query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND ");
				}*/
				query.append(" sub_profile.cms_le_sub_profile_id = pf.cms_customer_id AND COL.STATUS = 'ACTIVE' AND SS.STATUS <> 'DELETED'  AND "); 
				query.append(" 	pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID");
				query.append(" 	AND lmts.cms_lsp_appr_lmts_id           = SECMAP.cms_lsp_appr_lmts_id AND pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID");
				query.append(" 	AND lmts.cms_lsp_appr_lmts_id           = SECMAP.cms_lsp_appr_lmts_id AND");
				query.append(" 	SECMAP.CMS_LSP_LMT_PROFILE_ID="+criteria.getLimitProfId()+" AND ");
				
				query.append("  COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID  AND  ");
				if (criteria.getSciSecId() == null || criteria.getSciSecId().trim().equals("")) {
					query.append(" UPPER(SUBTYPE_NAME) NOT LIKE 'GENERAL CHARGE'  AND ");
				}
				
				query.append("COL.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID AND SS.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID ");
				query.append("AND COLNEWMAST.NEW_COLLATERAL_CODE=COL.COLLATERAL_CODE");
				if (criteria.getSciSecId() != null) {
					query.append(" AND SS.CMS_COLLATERAL_ID ='" + criteria.getSciSecId().toUpperCase() + "'");
				}
				if (criteria.getSecSubtype() != null) {
					query.append(" AND COL.SECURITY_SUB_TYPE_ID ='" + criteria.getSecSubtype().toUpperCase() + "'");
				}
				query.append(" UNION SELECT DISTINCT sub_profile.lsp_short_name,COL.CMS_COLLATERAL_ID, SS.SOURCE_SECURITY_ID,COL.SECURITY_SUB_TYPE_ID," );
				query.append(" TYPE_NAME,COL.SECURITY_LOCATION,COL.SCI_REFERENCE_NOTE, COL.SCI_ORIG_SECURITY_CURRENCY, COL.SECURITY_ORGANISATION, " );
				query.append(" COL.SEC_PRIORITY,SUBTYPE_NAME , COLNEWMAST.NEW_COLLATERAL_DESCRIPTION , COL.COLLATERAL_CODE, prop.property_id ");
				query.append(" FROM CMS_SECURITY_SOURCE SS, CMS_LIMIT_SECURITY_MAP SECMAP  ");
				query.append(",CMS_COLLATERAL_NEW_MASTER COLNEWMAST ,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,");
				query.append(" SCI_LE_SUB_PROFILE sub_profile, CMS_SECURITY COL left outer join  cms_property prop on prop.cms_collateral_id = col.cms_collateral_id ");
				query.append(" WHERE ");
				 
				/*if (criteria.getCustName() != null && criteria.getPropSearchId() != null 
						&& !criteria.getCustName().trim().equals("") && !criteria.getPropSearchId().trim().equals("")) {
					query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND UPPER(sub_profile.lsp_short_name) Like UPPER('%"+ criteria.getCustName()+"%') AND ");
				}*/
				if (criteria.getCustName() != null && !criteria.getCustName().trim().equals("")) {
					query.append("sub_profile.LSP_LE_ID = '"+ criteria.getCustName()+"' AND ");
				}
				/*if (criteria.getPropSearchId() != null && !criteria.getPropSearchId().trim().equals("")) {
					query.append(" UPPER(prop.property_id) Like UPPER('%"+ criteria.getPropSearchId()+"%') AND ");
				}*/
				
				query.append(" COL.STATUS = 'ACTIVE' AND SS.STATUS <> 'DELETED' AND COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND UPPER(TYPE_NAME) ");
				query.append(" LIKE 'PROPERTY' AND COL.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID AND SS.CMS_COLLATERAL_ID=SECMAP.CMS_COLLATERAL_ID ");
				query.append(" AND COLNEWMAST.NEW_COLLATERAL_CODE=COL.COLLATERAL_CODE AND sub_profile.cms_le_sub_profile_id = pf.cms_customer_id ");
				query.append(" AND pf.cms_lsp_lmt_profile_id           = lmts.CMS_LIMIT_PROFILE_ID ");
				query.append(" AND lmts.cms_lsp_appr_lmts_id           = SECMAP.cms_lsp_appr_lmts_id ");
				
				if (criteria.getSciSecId() != null) {
					query.append(" AND SS.CMS_COLLATERAL_ID ='" + criteria.getSciSecId().toUpperCase() + "'");
				}
				if (criteria.getSecSubtype() != null) {
					query.append(" AND COL.SECURITY_SUB_TYPE_ID ='" + criteria.getSecSubtype().toUpperCase() + "'");
				}

				/*		if ((criteria.getSecSubtype() != null) && !criteria.getSecSubtype().trim().equals("")) {
					query.append("AND COL.SECURITY_SUB_TYPE_ID = ? ");
					argList.add(criteria.getSecSubtype());
				}

						if (query.endsWith("WHERE ")) {
					query = query.substring(0, query.length() - 6);
				}

				if (query.endsWith("AND ")) {
					query = query.substring(0, query.length() - 4);
				}*/
				return (OBCollateral) getJdbcTemplate().query(query.toString(), new Object[]{},
						new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next())
						{
							OBCollateral col = new OBCollateral();
							col.setComment(rs.getString("lsp_short_name"));
							col.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
							col.setSCISecurityID(rs.getString("SOURCE_SECURITY_ID"));
							col.setToBeDischargedInd(rs.getString("NEW_COLLATERAL_DESCRIPTION"));
							col.setCollateralLocation(rs.getString("SECURITY_LOCATION"));
							col.setSCIReferenceNote(rs.getString("SCI_REFERENCE_NOTE"));
							col.setSCICurrencyCode(rs.getString("SCI_ORIG_SECURITY_CURRENCY"));
							col.setSecurityOrganization(rs.getString("SECURITY_ORGANISATION"));
							col.setSecPriority(rs.getString("SEC_PRIORITY"));
							col.setCollateralCode(rs.getString("COLLATERAL_CODE"));
							col.setPropSearchId(rs.getString("PROPERTY_ID"));

							OBCollateralSubType subtype = new OBCollateralSubType();
							subtype.setSubTypeCode(rs.getString("SECURITY_SUB_TYPE_ID"));
							subtype.setTypeName(rs.getString("TYPE_NAME"));
							subtype.setSubTypeName(rs.getString("SUBTYPE_NAME"));

							col.setCollateralSubType(subtype);
							return col;
						}

						return null;
					}
				});
				
				 
	}
	
		public ICollateralAllocation[] retrieveCollateralList(StringBuffer sb, ICollateralAllocation[] allocList) {



		String sql = "SELECT COL.CMS_COLLATERAL_ID,COL.COLLATERAL_CODE,COL.SECURITY_SUB_TYPE_ID,COL.CMV,COL.SEC_PRIORITY, COL.SECURITY_LOCATION, COL.SECURITY_ORGANISATION, COL.SCI_SECURITY_CURRENCY,SS.SOURCE_SECURITY_ID, COL.MONITOR_PROCESS, col.MONITOR_FREQUENCY, col.SCI_REFERENCE_NOTE FROM CMS_SECURITY COL,CMS_SECURITY_SOURCE SS "


				+ "WHERE COL.CMS_COLLATERAL_ID IN(" + sb.toString() + ")"
						+ " AND SS.STATUS <> 'DELETED' AND COL.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID";
		DefaultLogger.debug(this, "********** IN MILmtProxyHelper of retrieveCollateralList function 5548 SQL:"+sql);
		HashMap hmap = new HashMap();
		
		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				OBCollateral collateralItem = new OBCollateral();

				collateralItem.setCollateralID(rs.getLong("CMS_COLLATERAL_ID"));
				collateralItem.setCollateralCode(rs.getString("COLLATERAL_CODE"));
				collateralItem.setSourceSecuritySubType(rs.getString("SECURITY_SUB_TYPE_ID"));
				collateralItem.setSecPriority(rs.getString("SEC_PRIORITY"));
				collateralItem.setCollateralLocation(rs.getString("SECURITY_LOCATION"));
				collateralItem.setSecurityOrganization(rs.getString("SECURITY_ORGANISATION"));
				collateralItem.setMonitorProcess(rs.getString("MONITOR_PROCESS"));
				collateralItem.setMonitorFrequency(rs.getString("MONITOR_FREQUENCY"));
				collateralItem.setSCIReferenceNote(rs.getString("SCI_REFERENCE_NOTE"));
				collateralItem.setSCISecurityID(rs.getString("SOURCE_SECURITY_ID"));
				collateralItem.setCurrencyCode(rs.getString("SCI_SECURITY_CURRENCY"));
				BigDecimal amtChgval = rs.getBigDecimal("CMV");
				if (amtChgval != null) {
					Amount amount = new Amount(amtChgval.doubleValue(), collateralItem.getCurrencyCode());
					collateralItem.setCMV(amount);
				}
				
				ICollateralSubType coll = getCollateralByCollateralID(collateralItem.getCollateralID());
				String subTypeCode = rs.getString("SECURITY_SUB_TYPE_ID");
				coll.setSubTypeCode(subTypeCode);
				collateralItem.setCollateralSubType(coll);
				
				OBCollateralType colType = new OBCollateralType();
				String typeCode = subTypeCode.substring(0, 2);
				colType.setTypeCode(typeCode);
				colType.setTypeName(CommonCodeList.getInstance(CategoryCodeConstant.COMMON_CODE_SECURITY_TYPE)
						.getCommonCodeLabel(typeCode));
				collateralItem.setCollateralType(colType);

				return collateralItem;
			}
		});
		
		for (int i = 0; i < resultList.size(); i++) {
			OBCollateral collateralItem=(OBCollateral) resultList.get(i);
			hmap.put(collateralItem.getCollateralID(), collateralItem);
		}
		
		DefaultLogger.debug(this, "********** IN MILmtProxyHelper of retrieveCollateralList function 5563 ");
		for (int i = 0; i < allocList.length; i++) {
			ICollateralAllocation alloc = allocList[i];
			ICollateral col = alloc.getCollateral();
			if (null != col) {
				long collateralID = col.getCollateralID();
				// get from hashmap first
				ICollateral temp = (ICollateral) hmap.get(new Long(collateralID));
				if (null != temp) {
					alloc.setCollateral(temp);
					allocList[i] = alloc;
				}
			}
		}
		DefaultLogger.debug(this, "********** OUT MILmtProxyHelper of retrieveCollateralList function 5577 ");
		return allocList;
	}
	
	    public boolean checkSecurityId(String collateralId) {
		String sql="select count(1) from CMS_SECURITY where CMS_COLLATERAL_ID=?";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkSecurityId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}
    
    public String[] getCollateralTypeAndSubtype(String CMS_COLLATERAL_ID) throws SearchDAOException {
    	String sql = "select TYPE_NAME,SUBTYPE_NAME from CMS_SECURITY where CMS_COLLATERAL_ID="+CMS_COLLATERAL_ID;

    	List resultList = getJdbcTemplate().query(sql, new RowMapper() {

    		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    			String [] colTypeList = new String[2];
    			colTypeList[0] = rs.getString("TYPE_NAME");
    			colTypeList[1] = rs.getString("SUBTYPE_NAME");
    			return colTypeList;
    		}
    	});
    	return (String[])resultList.get(0);
    }
    
    @Override
   	public String getSubtypeCodeFromSecurity(String CMS_COLLATERAL_ID) throws SearchDAOException {

   		
    	String sql = "select SECURITY_SUB_TYPE_ID from CMS_SECURITY where CMS_COLLATERAL_ID=?";
   		String securitySubTypeId = "";
   		DBUtil dbUtil = null;
   		ResultSet rs=null; 
   		try {
   		dbUtil=new DBUtil();
   		dbUtil.setSQL(sql);
   		dbUtil.setString(1, CMS_COLLATERAL_ID);
   		rs=dbUtil.executeQuery();	
   		while(rs.next())
   		{ 
   			securitySubTypeId=rs.getString("SECURITY_SUB_TYPE_ID");
   		}
   		
   			}catch (Exception e) {
   				e.printStackTrace();
   				DefaultLogger.debug(this,"Exception in getSubtypeCodeFromSecurity "+e.getMessage());
   				
   			}
   		return securitySubTypeId;
   	
   	}
    
    public String[] getColCatAndCersaiFlag(String CMS_COLLATERAL_ID) throws SearchDAOException {
    	String sql = "select NEW_COLLATERAL_CATEGORY,CERSAI_IND,NEW_COLLATERAL_CODE,INSURANCE from CMS_COLLATERAL_NEW_MASTER where NEW_COLLATERAL_CODE = (select COLLATERAL_CODE from CMS_SECURITY where CMS_COLLATERAL_ID="+CMS_COLLATERAL_ID+")";

    	List resultList = getJdbcTemplate().query(sql, new RowMapper() {

    		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    			String [] colTypeList = new String[3];
    			colTypeList[0] = rs.getString("NEW_COLLATERAL_CATEGORY");
    			colTypeList[1] = rs.getString("CERSAI_IND");
    			colTypeList[2] = rs.getString("INSURANCE");
    			return colTypeList;
    		}
    	});
    	return (String[])resultList.get(0);
    }

    public boolean checkInsurancePolicyId(String insurancePolicyId,String collateralId) {
		String sql="select count(1) from CMS_INSURANCE_POLICY where INSURANCE_POLICY_ID=? and CMS_COLLATERAL_ID=?";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, insurancePolicyId);
		dbUtil.setString(2, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkInsurancePolicyId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}
    
    public boolean checkUniqueInsuranceId(String uniqueInsuranceId,String collateralId) {
		String sql="select count(1) from CMS_INSURANCE_POLICY where UNIQUE_INSURANCE_ID=? and CMS_COLLATERAL_ID=? and STATUS='ACTIVE'";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, uniqueInsuranceId);
		dbUtil.setString(2, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkUniqueInsuranceId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}
    
    public boolean checkPreviousMortCreationDate(String collateralId,String prevDate) {
    	String sql="select count(1) from cms_property_mortgage_det where CMS_COLLATERAL_ID=? and SALE_PURCHASE_DATE=? order by id desc";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, collateralId);
		dbUtil.setString(2, prevDate);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkPreviousMortCreationDate "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}

    public boolean checkCollateralIdCount(String collateralId, String camId) {

		String sql="SELECT count(1) FROM cms_security "
				+ "WHERE CMS_COLLATERAL_ID IN "
				+ "(SELECT DISTINCT cms_collateral_id FROM cms_limit_security_map map, sci_lsp_appr_lmts lmt  "
				+ "WHERE map.cms_lsp_appr_lmts_id = lmt.cms_lsp_appr_lmts_id AND lmt.cms_limit_profile_id = ? and CMS_COLLATERAL_ID=? "
				+ "AND (map.update_status_ind IS NULL OR map.update_status_ind <> 'D')) ";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, camId);
		dbUtil.setString(2, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkCollateralIdCount "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		
    }
    
    public boolean checkAddDocFacDetailsId(String docfacId,String collateralId) {
		String sql="select count(1) from CMS_ADD_DOC_FAC_DET where ADD_DOC_FAC_DET_ID=? and CMS_COLLATERAL_ID=? and STATUS='ACTIVE' ";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, docfacId);
		dbUtil.setString(2, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkAddDocFacDetailsId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}
    
    public boolean checkuniqueAddDocFacDetailsId(String docfacId,String collateralId)
    {

		String sql="select count(1) from CMS_ADD_DOC_FAC_DET where ADD_DOC_FAC_DET_ID=? and CMS_COLLATERAL_ID=?";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, docfacId);
		dbUtil.setString(2, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkInsurancePolicyId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		
    }
    
    
    public boolean checkSecBranchCount(String secBranch, String secCountry) {

		String sql = "SELECT count(1) FROM CMS_SYSTEM_BANK_BRANCH WHERE SYSTEM_BANK_BRANCH_CODE = ? and IS_VAULT='on' and STATUS='ACTIVE' and  DEPRECATED='N' "
				+ "AND COUNTRY= ( SELECT ID FROM CMS_COUNTRY WHERE COUNTRY_CODE= ? and STATUS='ACTIVE' )";

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, secBranch);
			dbUtil.setString(2, secCountry);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkSecBranchCount " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}

    @Override
   	public String getSecLocFromSecurity(String CMS_COLLATERAL_ID) throws SearchDAOException {

   		
    	String sql = "select SECURITY_LOCATION from CMS_SECURITY where CMS_COLLATERAL_ID=?";
   		String secLoc = "";
   		DBUtil dbUtil = null;
   		ResultSet rs=null; 
   		try {
   		dbUtil=new DBUtil();
   		dbUtil.setSQL(sql);
   		dbUtil.setString(1, CMS_COLLATERAL_ID);
   		rs=dbUtil.executeQuery();	
   		while(rs.next())
   		{ 
   			secLoc=rs.getString("SECURITY_LOCATION");
   		}
   		
   			}catch (Exception e) {
   				e.printStackTrace();
   				DefaultLogger.debug(this,"Exception in getSecLocFromSecurity "+e.getMessage());
   				
   			}
   		return secLoc;
    }
    
    
    public boolean checkUniqueStockId(String uniqueStockId,String collateralId) {
		String sql="select count(1) from CMS_PORTFOLIO_ITEM where UNIQUE_ITEM_ID=? and CMS_COLLATERAL_ID=?";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, uniqueStockId);
		dbUtil.setString(2, collateralId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkuniqueStockId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}
    
    
    public boolean checkUniqueStockLineId(String uniqueStockId,String collateralId) {
		String sql="select count(1) from CMS_LINE_DETAIL where LINE_UNIQUE_DETAIL_ID=? ";
		int count=0;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
		dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		dbUtil.setString(1, uniqueStockId);
		rs=dbUtil.executeQuery();	
		while(rs.next())
		{ 
			 count=rs.getInt("count(1)");
		}
		
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this,"Exception in checkuniqueStockId "+e.getMessage());
				
			}
		if(count != 0)
			return true;
		else
			return false;
		}



	public boolean checkStockExchange(String entryCode) {
		String sql = "select count(1) from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'SHARE_TYPE' and REF_ENTRY_CODE = 'STOCK_TYPE' and ENTRY_CODE=?";
		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, entryCode);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkuniqueStockId " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;
	}

	public Map<String, String> getActiveStatePinCodeMap1() {
		String sql = "SELECT STATE_ID,PINCODE FROM CMS_STATE_PINCODE_MAP " + "where STATUS = 'ACTIVE' "
				+ "AND DEPRECATED = 'N' ";

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			HashMap<String, String> pincodeMap = new HashMap();
			while (rs.next()) {
				String stateCode = rs.getString("STATE_ID");
				String pincode = rs.getString("PINCODE");
				pincodeMap.put(stateCode, pincode);
			}
			dbUtil.close();
			rs.close();
			return pincodeMap;
		} catch (Exception e) {
			System.out.println("Exception in getActiveStatePinCodeMap1..e=>" + e);
			e.printStackTrace();
			return null;
		}
	}

	public List getstockLinelList(String secId) throws SearchDAOException {
		String sql = "select fac.facility_name as facility_name ,fac.lmt_id as facility_id  ,fac.line_no as line_no ,fac.facility_system  "
				+ "from SCI_LSP_APPR_LMTS fac inner join CMS_LIMIT_SECURITY_MAP linkage on fac.CMS_LSP_APPR_LMTS_ID = linkage.CMS_LSP_APPR_LMTS_ID "
				+ "inner join CMS_SECURITY col on col.CMS_COLLATERAL_ID = linkage.CMS_COLLATERAL_ID "
				+ "where fac.cms_limit_status = 'ACTIVE' and col.CMS_COLLATERAL_ID ='" + secId + "'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				IMarketableEquityLineDetail obLineDetail = new OBMarketableEquityLineDetail();
				obLineDetail.setFacilityName(rs.getString("facility_name"));
				obLineDetail.setLineNumber(rs.getString("line_no"));
				obLineDetail.setFacilityId(rs.getString("facility_id"));

				return obLineDetail;
			}
		});

		return resultList;
	}

	@Override
	public String getInsuranceStatus(String insurancePolicyID) throws SearchDAOException {

		String sql = "select INSURANCE_STATUS from CMS_INSURANCE_POLICY where INSURANCE_POLICY_ID=?";
		String insStatus = "";
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, insurancePolicyID);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				insStatus = rs.getString("INSURANCE_STATUS");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getInsuranceStatus " + e.getMessage());

		}
		return insStatus;
	}

	public int getIsinCodeCount(String isinCode, String stockExchange) throws SearchDAOException {

		String sql = " select count(*) AS CNT from CMS_PRICE_FEED where EXCHANGE in "
				+ " ( select ENTRY_NAME from  COMMON_CODE_CATEGORY_ENTRY  where CATEGORY_CODE= 'SHARE_TYPE'  and  REF_ENTRY_CODE='STOCK_TYPE' and ENTRY_CODE=? ) AND ISIN_CODE = ? ";
		int isinCodeCount = 0;
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, stockExchange);
			dbUtil.setString(2, isinCode);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				isinCodeCount = rs.getInt("CNT");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getIsinCodeCount " + e.getMessage());

		}
		return isinCodeCount;
	}

	@Override
	public String getSubProfileIdFromSecurityId(String secId) throws SearchDAOException {

		String sql = "select DISTINCT CMS_LE_SUB_PROFILE_ID from SCI_LE_SUB_PROFILE sp, CMS_SECURITY sec, CMS_LIMIT_SECURITY_MAP lsm, SCI_LSP_APPR_LMTS lmts, SCI_LSP_LMT_PROFILE pf where SEC.CMS_COLLATERAL_ID = LSM.CMS_COLLATERAL_ID AND sp.cms_le_sub_profile_id  = pf.cms_customer_id AND pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID AND lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id AND sec.security_sub_type_id  = 'PT701' and SEC.CMS_COLLATERAL_ID= ?";
		String subProfileId = "";
		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, secId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				subProfileId = rs.getString("CMS_LE_SUB_PROFILE_ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in getSubProfileIdFromSecurityId " + e.getMessage());

		}
		return subProfileId;
	}
	

	public String[] getCustomerNameAndEntity(String CMS_COLLATERAL_ID) throws SearchDAOException {
		String sql = "SELECT DISTINCT sp.ENTITY as ENTITY,substr(sp.LSP_SHORT_NAME,0,50) as CUSTNAME\r\n" + 
				"FROM SCI_LE_SUB_PROFILE sp,\r\n" + 
				"  CMS_SECURITY sec,\r\n" + 
				"  CMS_LIMIT_SECURITY_MAP lsm,\r\n" + 
				"  SCI_LSP_APPR_LMTS lmts,\r\n" + 
				"  SCI_LSP_LMT_PROFILE pf\r\n" + 
				"WHERE SEC.CMS_COLLATERAL_ID   = LSM.CMS_COLLATERAL_ID\r\n" + 
				"AND sp.cms_le_sub_profile_id  = pf.cms_customer_id\r\n" + 
				"AND pf.cms_lsp_lmt_profile_id = lmts.CMS_LIMIT_PROFILE_ID\r\n" + 
				"AND lmts.cms_lsp_appr_lmts_id = lsm.cms_lsp_appr_lmts_id\r\n" + 
				"AND SEC.CMS_COLLATERAL_ID     = " + CMS_COLLATERAL_ID;

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] list = new String[2];
				list[0] = rs.getString("ENTITY");
				list[1] = rs.getString("CUSTNAME");
				return list;
			}
		});
		return (String[]) resultList.get(0);
	}
	
		 public void executeDeferralInsurancePolicy() {
			try {
				DefaultLogger.debug(this,"calling executeDeferralInsurancePolicy");
				getJdbcTemplate().execute("{call " + getDeferralInsurancePolicy() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in executeDeferralInsurancePolicy:"+ex.getMessage());
				ex.printStackTrace();
				
			}
		}
	    
	    public void executeDeferralGCPolicy() {
			try {
				DefaultLogger.debug(this,"calling executeDeferralGCPolicy");
				getJdbcTemplate().execute("{call " + getDeferralGCPolicy() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in executeDeferralGCPolicy:"+ex.getMessage());
				ex.printStackTrace();
				
			}
		}
	    
	    public void insertDeferralChecklistProperty() {
			try {
				DefaultLogger.debug(this,"calling getInserDeferralTBCre");
				getJdbcTemplate().execute("{call " + getInserDeferralTBCre() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in getInserDeferralTBCre:"+ex.getMessage());
				ex.printStackTrace();
				
			}
			
			try {
				DefaultLogger.debug(this,"calling getInsertDeferralChecklistProperty");
				getJdbcTemplate().execute("{call " + getInsertDeferralChecklistProperty() + "()}",  new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
						cs.executeUpdate();
						return null;
					}
				});
			}
			catch (Exception ex) {
				DefaultLogger.debug(this, "Exception in getInsertDeferralChecklistProperty:"+ex.getMessage());
				ex.printStackTrace();
				
			}
		}

	    @Override
		public String getGcInsuranceStatus(String insurancePolicyID) throws SearchDAOException {

			String sql = "select INSURANCE_STATUS from CMS_GC_INSURANCE where INS_CODE=?";
			String insStatus = "";
			DBUtil dbUtil = null;
			ResultSet rs = null;
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				dbUtil.setString(1, insurancePolicyID);
				rs = dbUtil.executeQuery();
				while (rs.next()) {
					insStatus = rs.getString("INSURANCE_STATUS");
				}

			} catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in getInsuranceStatus " + e.getMessage());

			}
			return insStatus;
		}
	    
		@Override
		public List securityCoverageByCollateralId(Long collateralId,String limitId){
			String sql = "SELECT ACTUAL.CHARGE_ID AS CHARGE_ID_ACTUAL,ACTUAL.LMT_SECURITY_COVERAGE AS LMT_SECURITY_COVERAGE_ACTUAL,STAGING.CHARGE_ID AS CHARGE_ID_STAGING,STAGING.LMT_SECURITY_COVERAGE AS LMT_SECURITY_COVERAGE_STAGING FROM\r\n" + 
					"(SELECT LMT_SECURITY_COVERAGE,CHARGE_ID FROM CMS_LIMIT_SECURITY_MAP WHERE CHARGE_ID =(SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP \r\n" + 
					"WHERE SCI_LAS_LMT_ID =? AND CMS_COLLATERAL_ID=?)) ACTUAL,\r\n" + 
					"(SELECT LMT_SECURITY_COVERAGE,CHARGE_ID FROM CMS_STAGE_LIMIT_SECURITY_MAP WHERE CHARGE_ID =(SELECT MAX(CHARGE_ID) FROM CMS_STAGE_LIMIT_SECURITY_MAP \r\n" + 
					"WHERE SCI_LAS_LMT_ID =? AND CMS_COLLATERAL_ID=?)) STAGING";
			int securityCoverageActual = 0;
			int securityCoverageStaging = 0;
			long chargeIdActual = 0;
			long chargeIdStagging =0;
			ArrayList securityCoverage = new ArrayList();
		
			DBUtil dbUtil = null;
			ResultSet rs = null;
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				dbUtil.setString(1, limitId);
				dbUtil.setLong(2, collateralId);
				dbUtil.setString(3, limitId);
				dbUtil.setLong(4, collateralId);
				 System.out.println(" **********inside collateraDao**********7219***********securityCoverageByCollateralId sql query =>"+sql);	
				rs = dbUtil.executeQuery();
				while (rs.next()) {
					securityCoverageActual = rs.getInt("LMT_SECURITY_COVERAGE_ACTUAL");
					securityCoverageStaging = rs.getInt("LMT_SECURITY_COVERAGE_STAGING");
					chargeIdActual = rs.getLong("CHARGE_ID_ACTUAL");
					chargeIdStagging	= rs.getLong("CHARGE_ID_STAGING");	
				}
				System.out.println("Inside collateraDao *****7227*******securityCoverageByCollateralId() stagging charge id : "+chargeIdStagging+", Stagging securityCoverage : "+securityCoverageStaging+", Actual charge id : "+chargeIdActual+" Actual securityCoverage : "+securityCoverageActual);
				securityCoverage.add(securityCoverageActual);
				securityCoverage.add(securityCoverageStaging);
				securityCoverage.add(chargeIdActual);
				securityCoverage.add(chargeIdStagging);

			} catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in securityCoverageByCollateralId " + e.getMessage());

			}
			return securityCoverage;
		}
		
		@Override
		public void updateSecurityCoverage(long chargeId,int securityCoverage) {
			String sql = " ";
			
			DefaultLogger.debug(this, " inside updateSecurityCoverage************");
			try{
			/*	System.out.println("chargeId =>"+chargeId+" securityCoverage : "+securityCoverage);*/
				sql = " update CMS_LIMIT_SECURITY_MAP set LMT_SECURITY_COVERAGE='"+securityCoverage+"' where CHARGE_ID='"+chargeId+"'";
					 System.out.println(" inside updateSecurityCoverage sql query =>"+sql);
					 getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateSecurityCoverage() e=>"+e);
					e.printStackTrace();
				}
		}
		
		@Override
		public void updateSecurityCoverageStg(long chargeId,int securityCoverage) {
			String sql = " ";
			
			DefaultLogger.debug(this, " inside updateSecurityCoverage************");
			try{
/*				System.out.println("chargeId =>"+chargeId+" securityCoverage : "+securityCoverage);*/
				sql = " update CMS_STAGE_LIMIT_SECURITY_MAP set LMT_SECURITY_COVERAGE='"+securityCoverage+"' where CHARGE_ID='"+chargeId+"'";
					 System.out.println(" inside updateSecurityCoverage sql query =>"+sql);
					 getJdbcTemplate().execute(sql);
				}
				catch (Exception e) {
					System.out.println("exception in updateSecurityCoverage() e=>"+e);
					e.printStackTrace();
				}
		}

		@Override
		public void updateActualCollateral(ICollateral actualColMap) {
			
			DefaultLogger.debug(this, " inside updateActualCollateral*********** updating SecurityCoverage  ************");

					String sql = "update cms_limit_security_map set LMT_SECURITY_COVERAGE = ? where charge_id = ? ";

					 ICollateralLimitMap[] colMap = actualColMap.getCollateralLimits();
							final List<ICollateralLimitMap> objectList =  Arrays.asList(colMap);
							final int batchSize = objectList.size();
							System.out.println(" batchSize :"+batchSize);
								getJdbcTemplate().batchUpdate(sql,
										new BatchPreparedStatementSetter() {
									public void setValues(PreparedStatement ps, int i)
									throws SQLException {
										ICollateralLimitMap colMap1 = objectList.get(i);
										System.out.println("Charge ID : "+colMap1.getChargeID()+
												"limit id : "+ colMap1.getLimitID()+
												"Sec cov : "+colMap1.getLmtSecurityCoverage());
										ps.setString(1,colMap1.getLmtSecurityCoverage());
										ps.setLong(2,colMap1.getChargeID());
									}
									public int getBatchSize() {
										return objectList.size();
									}
								});
		}
}
