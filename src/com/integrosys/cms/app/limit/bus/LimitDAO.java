/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/LimitDAO.java,v 1.60 2006/11/23 10:54:59 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.aurionpro.clims.rest.dto.CommonCodeRestRequestDTO;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.OBReleaselinedetailsFile;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.RetrieveScmLineRequest;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaProvisionJob;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTO;
import com.integrosys.cms.app.ws.dto.DigitalLibraryRequestDTOForV2;
import com.integrosys.cms.app.ws.dto.OBFCUBSDataLog;
import com.integrosys.cms.app.ws.dto.OBPSRDataLog;
import com.integrosys.cms.batch.dfso.borrower.OBDFSOLog;
import com.integrosys.cms.batch.fcubsLimitFile.schedular.FCUBSFileConstants;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.ui.createfacupload.OBCreatefacilitylineFile;
import com.integrosys.cms.ui.facilityNewMaster.FacilityNewMasterMapper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.user.app.bus.UserException;

/**
 * DAO for limit.
 *
 * @author $Author: czhou $<br>
 * @version $Revision: 1.60 $
 * @since $Date: 2006/11/23 10:54:59 $ Tag: $Name: $
 */
public class LimitDAO extends JdbcTemplateAdapter implements ILimitDAO {
	private DBUtil dbUtil;
	private final static Logger logger = LoggerFactory.getLogger(LimitDAO.class);
	
	public static final String Proc_SP_Move_Collateral_Data	="SP_MOVE_COLLATERAL_DATA_check";

	private static final String SELECT_LMT_CCY = "SELECT lmt_crrncy_iso_code FROM sci_lsp_appr_lmts WHERE cms_lsp_appr_lmts_id = ?";

	private static final String SELECT_LMT_DETAILS = "SELECT cms_lsp_appr_lmts_id cms_lmt_id, lmt_id sci_lmt_idgetNpaProvisionFileDetails,"
		+ "  lmt_crrncy_iso_code lmt_ccy, lmt_amt appr_lmt_amt," + "  lmt_outer_lmt_id outer_lmt_id "
		+ "FROM sci_lsp_appr_lmts " + "WHERE cms_lsp_appr_lmts_id";

	private static final String SELECT_LMT_DETAILS_4CoBorrower = "SELECT *  FROM SCI_LSP_CO_BORROW_LMT WHERE lcl_id";

	private static final String SELECT_LMT_COL_MAP_DETAILS = "SELECT sci_las_sec_id," + "   	cms_collateral_id,"
	+ "   	update_status_ind " + " FROM cms_limit_security_map " + "WHERE cms_lsp_appr_lmts_id = ? ";

	private static final String SELECT_LMT_PROFILE_BY_AA_NO = "SELECT * FROM SCI_LSP_LMT_PROFILE WHERE LLP_BCA_REF_NUM = ? AND CMS_LSP_LMT_PROFILE_ID <> ?";

	private static final String SELECT_ACOUNT_IFO = "SELECT LMT_FAC_SEQ,LSX_EXT_SYS_ACCT_NUM,LSX_EXT_SYS_ACCT_TYPE  "
		+ "FROM  CMS_FACILITY_MASTER,SCI_LSP_APPR_LMTS,SCI_LSP_LMTS_XREF_MAP,SCI_LSP_SYS_XREF "
		+ "WHERE CMS_FACILITY_MASTER.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
		+ " AND CMS_FACILITY_MASTER.CMS_LSP_APPR_LMTS_ID = SCI_LSP_LMTS_XREF_MAP.CMS_LSP_APPR_LMTS_ID "
		+ " AND SCI_LSP_LMTS_XREF_MAP.CMS_LSP_SYS_XREF_ID=SCI_LSP_SYS_XREF.CMS_LSP_SYS_XREF_ID "
		+ " AND CMS_FACILITY_MASTER.CMS_LSP_APPR_LMTS_ID=? ";

	private static final String SELECT_ACTIVE_LMT_PROFILE_LINK_CUST = "SELECT lmt.CMS_LSP_LMT_PROFILE_ID, lmt.LLP_LE_ID, lmt.LLP_BCA_REF_NUM, cust.LSP_SHORT_NAME "
		+ "FROM SCI_LSP_LMT_PROFILE lmt, SCI_LE_SUB_PROFILE cust "
		+ "WHERE lmt.CMS_CUSTOMER_ID = cust.CMS_LE_SUB_PROFILE_ID "
		+ " AND lmt.CMS_BCA_STATUS <> '"
		+ ICMSConstant.STATE_DELETED
		+ "' "
		+ " AND lmt.CMS_CUSTOMER_ID = ? "
		+ " AND lmt.CMS_LSP_LMT_PROFILE_ID <> ?";
	/**
	 * Default Constructor
	 */
	public LimitDAO() {
	}

	/**
	 * Checks AA number must be unique in the system.
	 *
	 * @param bcaRefNo
	 * @param lmtProfileID
	 * @return boolean true if there is duplicate AA number exists, otherwise
	 *         false
	 * @throws LimitException
	 */
	public boolean checkDuplicateAANumber(String bcaRefNo, long lmtProfileID) throws LimitException {

		Boolean isDuplicate = (Boolean) getJdbcTemplate().query(SELECT_LMT_PROFILE_BY_AA_NO,
				new Object[]{bcaRefNo, new Long(lmtProfileID)}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return new Boolean(rs.next());
			}

		});
		return isDuplicate.booleanValue();
	}

	/**
	 * Checks the specified customer has active limit profile link to it.
	 *
	 * @param customerID   the customer ID
	 * @param lmtProfileID the limit profile ID
	 * @return boolean true if there is active limit profile link to the
	 *         customer, otherwise false
	 * @throws LimitException
	 */
	public boolean checkCustHasLimitProfile(long customerID, long lmtProfileID) throws LimitException {

		Boolean custHasLimitProfile = (Boolean) getJdbcTemplate().query(SELECT_ACTIVE_LMT_PROFILE_LINK_CUST,
				new Object[]{new Long(customerID), new Long(lmtProfileID)}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return new Boolean(rs.next());
			}
		});

		return custHasLimitProfile.booleanValue();
	}

	/**
	 * Get List of Co borrower limit transaction ID by Transaction Reference ID
	 *
	 * @param trxRefID
	 * @return String[] list of transaction ID
	 * @throws LimitException
	 */
	public String[] getCoBorrowerLimitTrxIDByTrxRefID(String trxRefID) throws LimitException {
		String theSql = "SELECT transaction_id from TRANSACTION " + "WHERE transaction_type = 'COBORROWER_LIMIT' "
		+ " AND trx_reference_id = ?";

		List trxIdList = getJdbcTemplate().query(theSql, new Object[]{trxRefID}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("transaction_id");
			}
		});

		return (String[]) trxIdList.toArray(new String[0]);
	}

	/**
	 * Get Limit Profile SCI ID given CMS Limit Profile ID
	 *
	 * @param limitProfileID
	 * @return limitProfileRef
	 * @throws LimitException
	 */
	public String getLimitProfileRefByID(long limitProfileID) throws LimitException {
		String theSql = "SELECT llp_id FROM SCI_LSP_LMT_PROFILE where cms_lsp_lmt_profile_id = ? ";

		return (String) getJdbcTemplate().query(theSql, new Object[]{new Long(limitProfileID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return "0";
			}
		});
	}

	/**
	 * Helper method to get indicator whether all co-borrower limits are deleted
	 * based on LE ref and Limit Profile SCI ID
	 *
	 * @param coBoLERef
	 * @param limitProfileRef
	 * @return true if all co-borrower limits are deleted
	 * @throws LimitException
	 */

	public boolean getAllCoBoLimitDeletedInd(String coBoLERef, String limitProfileRef) throws LimitException {
		String theSql = "SELECT COUNT (*) FROM sci_lsp_co_borrow_lmt " + "WHERE lcl_cobo_le_id = ? AND lcl_llp_id = ? "
		+ " AND (cms_limit_status IS NULL OR cms_limit_status <> 'DELETED')";

		int count = getJdbcTemplate().queryForInt(theSql, new Object[]{coBoLERef, limitProfileRef});

		return count == 0;
	}

	public boolean getCleanBFLReminderRequiredInd(long limitProfileID) throws LimitException {
		StringBuffer strBuf = new StringBuffer();

		String dueDays = "get_due_days_bflacceptance("
			+ "(CASE outerprofile.CMS_BCA_RENEWAL_IND WHEN 'Y' THEN 'RENEWAL' WHEN 'N' THEN 'NEW' ELSE 'NEW' END), "
			+ "SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE, " + "outerprofile.CMS_ORIG_COUNTRY, "
			+ "outerprofile.cms_bca_local_ind )";

		strBuf.append("SELECT outerprofile.CMS_LSP_LMT_PROFILE_ID,SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, ");
		strBuf.append("SCI_LE_SUB_PROFILE.LSP_LE_ID, outerprofile.LLP_BCA_REF_APPR_DATE, ");
		strBuf.append("SCI_LE_MAIN_PROFILE.LMP_SGMNT_CODE_VALUE AS SEGMENT, ");
		strBuf.append("due_days(cast(? as date), LLP_BCA_REF_APPR_DATE, ");
		strBuf.append(dueDays);
		strBuf.append(", cms_orig_country) AS daysdue, ");
		strBuf.append("add_days(LLP_BCA_REF_APPR_DATE, ");
		strBuf.append(dueDays);
		strBuf.append(", cms_orig_country) AS duedate,  cms_orig_country, cms_orig_organisation ");
		strBuf.append("FROM SCI_LSP_LMT_PROFILE outerprofile, SCI_LE_SUB_PROFILE, ");
		strBuf.append("SCI_LE_MAIN_PROFILE ");
		// strBuf.append(
		// "WHERE business_days(TO_DATE(?),
		// TO_DATE(outerprofile.LLP_BCA_REF_APPR_DATE), cms_orig_country) >= "
		// );
		// strBuf.append(dueDays);
		// strBuf.append(" and outerprofile.cms_lsp_lmt_profile_id = ? ");
		strBuf.append("WHERE outerprofile.cms_lsp_lmt_profile_id = ? ");
		strBuf.append(" AND outerprofile.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID ");
		strBuf.append(" AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID ");
		strBuf.append(" AND outerprofile.CMS_TAT_CREATE_DATE IS NOT NULL ");
		strBuf.append(" AND NOT EXISTS ");
		strBuf.append("(SELECT innerprofile.CMS_LSP_LMT_PROFILE_ID ");
		strBuf.append("FROM SCI_LSP_LMT_PROFILE innerprofile, VW_TAT_ENTRY ");
		strBuf.append("WHERE innerprofile.cms_lsp_lmt_profile_id = outerprofile.CMS_LSP_LMT_PROFILE_ID ");
		strBuf.append(" AND innerprofile.cms_lsp_lmt_profile_id = ? ");
		strBuf.append(" AND innerprofile.CMS_LSP_LMT_PROFILE_ID = VW_TAT_ENTRY.LIMIT_PROFILE_ID ");
		// strBuf.append(
		// " AND business_days(TO_DATE(?),
		// TO_DATE(innerprofile.LLP_BCA_REF_APPR_DATE), cms_orig_country) >= "
		// );
		// strBuf.append(dueDays);
		strBuf.append(" AND VW_TAT_ENTRY.TAT_SERVICE_CODE = 'CUSTOMER_ACCEPT_BFL') ");

		String query = strBuf.toString();

		Boolean cleanBflRequired = (Boolean) getJdbcTemplate().query(query,
				new Object[]{new Date(), new Long(limitProfileID), new Long(limitProfileID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return new Boolean(rs.next());
			}
		});
		return cleanBflRequired.booleanValue();
	}

	public HashMap getFAMName(long aLimitProfileID) throws LimitException {
		final HashMap retMap = new HashMap();
//		String theSQL = "SELECT EMP.LEM_EMP_CODE, EMP.LEM_EMP_NAME, EMP.LEM_EMP_TYPE_VALUE, EMP.LEM_PRINCIPAL_FAM_IND FROM "
//		+ "SCI_LSP_LMT_PROFILE C, SCI_LSP_EMP_MAP EMP WHERE C.CMS_CUSTOMER_ID = EMP.CMS_LE_SUB_PROFILE_ID "
//		+ " AND C.CMS_ORIG_COUNTRY = EMP.LEM_EMP_BKG_LOC_CTRY AND C.CMS_ORIG_ORGANISATION = EMP.LEM_EMP_BKG_LOC_ORG "
//		+ " AND (EMP.UPDATE_STATUS_IND <> 'D' OR EMP.UPDATE_STATUS_IND is null) "
//		+ " AND C.CMS_LSP_LMT_PROFILE_ID = ? ";
		String theSQL = "SELECT LMP_ACC_OFFICER_NUM, LMP_ACC_OFFICER_VALUE FROM " +
		"SCI_LE_MAIN_PROFILE WHERE " +
		"CMS_LE_MAIN_PROFILE_ID = ?";

		getJdbcTemplate().query(theSQL, new Object[]{new Long(aLimitProfileID)}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String famName = null;
				String famCode = null;

				while (rs.next()) {

//					if ("FAM".equals(rs.getString("LEM_EMP_TYPE_VALUE"))) {
//					if (famName == null) {
//					famName = rs.getString("LEM_EMP_NAME");
//					famCode = rs.getString("LEM_EMP_CODE");
//					} else if ("Y".equals(rs.getString("LEM_PRINCIPAL_FAM_IND"))) {
//					famName = rs.getString("LEM_EMP_NAME");
//					famCode = rs.getString("LEM_EMP_CODE");
//					}
//					}
					famName = rs.getString("LMP_ACC_OFFICER_VALUE");
					famCode = rs.getString("LMP_ACC_OFFICER_NUM");
				}
				retMap.put(ICMSConstant.FAM_CODE, famCode);
				retMap.put(ICMSConstant.FAM_NAME, famName);

				return null;
			}
		});

		return retMap;
	}

	public HashMap getFAMNameByCustomer(long aCustomerID) throws LimitException {
		final HashMap retMap = new HashMap();
		String theSQL = "SELECT EMP.LEM_EMP_CODE, EMP.LEM_EMP_NAME, EMP.LEM_EMP_TYPE_VALUE, EMP.LEM_PRINCIPAL_FAM_IND FROM "
			+ "SCI_LE_SUB_PROFILE C, SCI_LSP_EMP_MAP EMP WHERE C.CMS_LE_SUB_PROFILE_ID = EMP.CMS_LE_SUB_PROFILE_ID "
			+ " AND C.CMS_SUB_ORIG_COUNTRY = EMP.LEM_EMP_BKG_LOC_CTRY AND C.CMS_SUB_ORIG_ORGANISATION = EMP.LEM_EMP_BKG_LOC_ORG "
			+ " AND (EMP.UPDATE_STATUS_IND <> 'D' OR EMP.UPDATE_STATUS_IND is null) "
			+ " AND C.CMS_LE_SUB_PROFILE_ID = ? ";

		getJdbcTemplate().query(theSQL, new Object[]{new Long(aCustomerID)}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String famName = null;
				String famCode = null;
				while (rs.next()) {

					if ("FAM".equals(rs.getString("LEM_EMP_TYPE_VALUE"))) {
						if (famName == null) {
							famName = rs.getString("LEM_EMP_NAME");
							famCode = rs.getString("LEM_EMP_CODE");
						} else if ("Y".equals(rs.getString("LEM_PRINCIPAL_FAM_IND"))) {
							famName = rs.getString("LEM_EMP_NAME");
							famCode = rs.getString("LEM_EMP_CODE");
						}
					}
				}

				retMap.put(ICMSConstant.FAM_CODE, famCode);
				retMap.put(ICMSConstant.FAM_NAME, famName);

				return null;
			}
		});

		return retMap;
	}

	/**
	 * Computes the due date of a date given its day period and country code.
	 *
	 * @param startDate   start date
	 * @param numOfDays   the number of days before the startDate is due
	 * @param countryCode country code
	 * @return due date
	 * @throws LimitException on error calculating the due date
	 */
	public Date getDueDate(Date startDate, int numOfDays, String countryCode) throws LimitException {

		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(startDate);

		gc.add(Calendar.DATE, numOfDays);

		return gc.getTime();
	}

	/**
	 * Get a list of booking location belong to the country given.
	 *
	 * @param country country code
	 * @return a list of booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(final String country) throws LimitException {
		String selectSQL = "select BKL_BKG_LOCTN_ORGN_VALUE, BKL_BKG_LOCTN_DESC "
			+ "from SCI_BKG_LOCTN, COMMON_CODE_CATEGORY_ENTRY "
			+ "where BKL_CNTRY_ISO_CODE = ? and SCI_BKG_LOCTN.BKL_BKG_LOCTN_ORGN_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE "
			+ " AND ACTIVE_STATUS = '" + ICMSConstant.CATEGORY_ENTRY_ACTIVE_STATUS + "' and CATEGORY_CODE = '"
			+ ICMSConstant.CATEGORY_CODE_BKGLOC + "'";

		List bookingLocationList = getJdbcTemplate().query(selectSQL, new Object[]{country}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBBookingLocation loc = new OBBookingLocation();
				loc.setBookingLocationDesc(rs.getString("BKL_BKG_LOCTN_DESC"));
				loc.setOrganisationCode(rs.getString("BKL_BKG_LOCTN_ORGN_VALUE"));
				loc.setCountryCode(country);

				return loc;
			}
		});

		return (OBBookingLocation[]) bookingLocationList.toArray(new OBBookingLocation[0]);
	}

	/**
	 * Get a booking location given the SCI booking location id.
	 *
	 * @param sciBkgLocknID booking location id from SCI
	 * @return booking location
	 */
	public IBookingLocation getBookingLocation(long sciBkgLocknID) throws LimitException {
		String selectSQL = "select BKL_BKG_LOCTN_ORGN_VALUE, BKL_BKG_LOCTN_DESC, BKL_CNTRY_ISO_CODE "
			+ "from SCI_BKG_LOCTN " + "where BKL_BKG_LOCTN_ID = ? ";

		return (IBookingLocation) getJdbcTemplate().query(selectSQL, new Object[]{new Long(sciBkgLocknID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				OBBookingLocation loc = null;

				if (rs.next()) {
					loc = new OBBookingLocation();
					loc.setBookingLocationDesc(rs.getString("BKL_BKG_LOCTN_DESC"));
					loc.setOrganisationCode(rs.getString("BKL_BKG_LOCTN_ORGN_VALUE"));
					loc.setCountryCode(rs.getString("BKL_CNTRY_ISO_CODE"));
				}
				return loc;
			}
		});
	}

	/**
	 * Get a list of booking location belong to the country given.
	 *
	 * @param country country code
	 * @return a list of booking location
	 */
	public String[] getUniqueBookingLocation(String country) throws LimitException {
		String selectSQL = "select distinct (BKL_BKG_LOCTN_ORGN_VALUE) "
			+ "from SCI_BKG_LOCTN, COMMON_CODE_CATEGORY_ENTRY "
			+ "where BKL_CNTRY_ISO_CODE = ? and SCI_BKG_LOCTN.BKL_BKG_LOCTN_ORGN_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE "
			+ " AND ACTIVE_STATUS = '" + ICMSConstant.CATEGORY_ENTRY_ACTIVE_STATUS + "' and CATEGORY_CODE = '"
			+ ICMSConstant.CATEGORY_CODE_BKGLOC + "'";

		List organisationCodeList = getJdbcTemplate().query(selectSQL, new Object[]{country}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("BKL_BKG_LOCTN_ORGN_VALUE");
			}
		});

		return (String[]) organisationCodeList.toArray(new String[0]);
	}

	/**
	 * Get a list of booking location belong to the given country list.
	 *
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public IBookingLocation[] getBookingLocationByCountry(String[] country) throws LimitException {
		if (CommonUtil.isEmptyArray(country)) {
			return null;
		}

		StringBuffer sqlBuf = new StringBuffer(
				"select BKL_BKG_LOCTN_ORGN_VALUE, BKL_BKG_LOCTN_DESC, BKL_CNTRY_ISO_CODE "
				+ "from SCI_BKG_LOCTN, COMMON_CODE_CATEGORY_ENTRY "
				+ "where SCI_BKG_LOCTN.BKL_BKG_LOCTN_ORGN_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE "
				+ " AND ACTIVE_STATUS = '" + ICMSConstant.CATEGORY_ENTRY_ACTIVE_STATUS
				+ "' and CATEGORY_CODE = '" + ICMSConstant.CATEGORY_CODE_BKGLOC + "' and BKL_CNTRY_ISO_CODE ");

		ArrayList params = new ArrayList();
		CommonUtil.buildSQLInList(country, sqlBuf, params);
		String sql = sqlBuf.toString();

		List bookingLocationList = getJdbcTemplate().query(sql, params.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBBookingLocation loc = new OBBookingLocation();
				loc.setBookingLocationDesc(rs.getString("BKL_BKG_LOCTN_DESC"));
				loc.setOrganisationCode(rs.getString("BKL_BKG_LOCTN_ORGN_VALUE"));
				loc.setCountryCode(rs.getString("BKL_CNTRY_ISO_CODE"));
				return loc;
			}

		});

		return (OBBookingLocation[]) bookingLocationList.toArray(new OBBookingLocation[0]);
	}

	/**
	 * Get a list of booking location belong to the given country list.
	 *
	 * @param country a list of country codes
	 * @return a list of booking location
	 */
	public String[] getUniqueBookingLocation(String[] country) throws LimitException {
		if (CommonUtil.isEmptyArray(country)) {
			return null;
		}

		StringBuffer sqlBuf = new StringBuffer("select distinct (BKL_BKG_LOCTN_ORGN_VALUE) "
				+ "from SCI_BKG_LOCTN, COMMON_CODE_CATEGORY_ENTRY "
				+ "where SCI_BKG_LOCTN.BKL_BKG_LOCTN_ORGN_VALUE = COMMON_CODE_CATEGORY_ENTRY.ENTRY_CODE "
				+ " AND ACTIVE_STATUS = '" + ICMSConstant.CATEGORY_ENTRY_ACTIVE_STATUS + "' and CATEGORY_CODE = '"
				+ ICMSConstant.CATEGORY_CODE_BKGLOC + "' and BKL_CNTRY_ISO_CODE ");

		ArrayList params = new ArrayList();
		CommonUtil.buildSQLInList(country, sqlBuf, params);

		String sql = sqlBuf.toString();

		List organisationCodeList = getJdbcTemplate().query(sql, params.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("BKL_BKG_LOCTN_ORGN_VALUE");
			}
		});

		return (String[]) organisationCodeList.toArray(new String[0]);
	}

	/**
	 * Get country given the booking location.
	 *
	 * @param bookingLocation country code
	 * @return booking location
	 */
	public IBookingLocation getCountryCodeByBookingLocation(String bookingLocation) throws LimitException {
		String selectSQL = "select BKL_CNTRY_ISO_CODE, BKL_BKG_LOCTN_DESC from SCI_BKG_LOCTN where BKL_BKG_LOCTN_ORGN_VALUE = ?";

		return (IBookingLocation) getJdbcTemplate().query(selectSQL, new Object[]{bookingLocation},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					OBBookingLocation loc = new OBBookingLocation();
					loc.setBookingLocationDesc(rs.getString("BKL_BKG_LOCTN_DESC"));
					loc.setCountryCode(rs.getString("BKL_CNTRY_ISO_CODE"));
					return loc;
				}
				return null;
			}
		});
	}

	/**
	 * @param isRenewed    a Boolean, true for renewed, false otherwise
	 * @param segment      Segment
	 * @param country      Country of the
	 * @param bcaRenewDate Date
	 * @return Return BFL Due dates for Local and Foreign
	 * @throws LimitException
	 */

	public Date[] getBFLDueDates(boolean isRenewed, String segment, String country, Date bcaRenewDate)
	throws LimitException {
		String rewStr = ICMSConstant.BCA_TYPE_NEW;
		if (isRenewed) {
			rewStr = ICMSConstant.BCA_TYPE_RENEWAL;
		}

		String selectSQL = "select (cast(? as timestamp) + LOCAL_DAYS days) AS LOCAL_DATE, "
			+ "(cast(? as timestamp) + OVERSEAS_DAYS days) AS OVERSEAS_DAYS "
			+ "from  CMS_BFL_TAT_PARAM PARAM where param.bca_type = ? "
			+ " AND  PARAM.BFL_TYPE = 'CUSTOMER_ACCEPT_BFL' and PARAM.SEGMENT = ? and PARAM.COUNTRY =? ";

		return (Date[]) getJdbcTemplate().query(
				selectSQL,
				new Object[]{new Timestamp(bcaRenewDate.getTime()), new Timestamp(bcaRenewDate.getTime()), rewStr,
						segment, country}, new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Date[] ret = new Date[2];

						if (rs.next()) {
							ret[0] = rs.getTimestamp("LOCAL_DATE");
							ret[1] = rs.getTimestamp("OVERSEAS_DAYS");
						}
						return ret;
					}
				});

	}

	public List getBFLTATParameter(String country) throws LimitException {
		String theSQL = "SELECT BCA_TYPE, BFL_TYPE, SEGMENT, LOCAL_DAYS, OVERSEAS_DAYS "
			+ "FROM CMS_BFL_TAT_PARAM WHERE COUNTRY = ? ORDER BY BFL_TYPE, SEGMENT";

		return getJdbcTemplate().query(theSQL, new Object[]{country}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				IBFLTATParameterSummary summary = new BFLTATParameterSummary();
				summary.setSegment(rs.getString("SEGMENT"));
				summary.setBcaType(rs.getString("BCA_TYPE"));
				summary.setBflType(rs.getString("BFL_TYPE"));
				summary.setLocalDays(rs.getInt("LOCAL_DAYS"));
				summary.setOverseasDays(rs.getInt("OVERSEAS_DAYS"));
				return summary;
			}
		});
	}

	public String getCoinCode(String aApproverEmpID) throws LimitException {
		if (StringUtils.isBlank(aApproverEmpID)) {
			return null;
		}

		String theSQL = "SELECT EMP.EMP_COIN_NUM FROM SCI_EMP_PROFILE EMP WHERE EMP.EMP_ID = ? AND "
			+ " EMP.UPDATE_STATUS_IND <> 'D'";

		return (String) getJdbcTemplate().query(theSQL, new Object[]{new Long(aApproverEmpID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String coinCode = null;
				while (rs.next()) {
					coinCode = rs.getString("EMP_COIN_NUM");
				}

				return coinCode;
			}
		});
	}

	/**
	 * Get country given the booking location.
	 *
	 * @param aLimitProfileID country code
	 * @return booking location
	 */
	public ITATEntry[] getTatEntry(long aLimitProfileID) throws LimitException {
		String selectSQL = "select TAT_TIME_STAMP,TAT_SERVICE_CODE, TAT_REMARKS "
			+ "from CMS_TAT_ENTRY where LIMIT_PROFILE_ID = ? ";

		List tatEntryList = getJdbcTemplate().query(selectSQL, new Object[]{new Long(aLimitProfileID)},
				new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBTATEntry tatentry = new OBTATEntry();
				tatentry.setTATStamp(rs.getTimestamp("TAT_TIME_STAMP"));
				tatentry.setTATServiceCode(rs.getString("TAT_SERVICE_CODE"));
				tatentry.setRemarks(rs.getString("TAT_REMARKS"));

				return tatentry;
			}
		});

		return (OBTATEntry[]) tatEntryList.toArray(new OBTATEntry[0]);
	}

	/**
	 * Get base currency for a limit.
	 *
	 * @param limitID
	 * @return
	 * @throws LimitException
	 */
	public String getLimitBaseCurrency(long limitID) throws LimitException {
		if (limitID == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}

		return (String) getJdbcTemplate().query(SELECT_LMT_CCY, new Object[]{new Long(limitID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String limitCCY = null;
				if (rs.next()) {
					limitCCY = rs.getString(1);
				}
				return limitCCY;
			}
		});
	}

	/**
	 * Get approved limit amount given a list of CMS limit IDs.
	 *
	 * @param - limitIDList : List of unique CMS limit IDs
	 * @return HashMap - Long limit ID as key and ILimit as value
	 */
	public HashMap getApprovedLimitAmount(List limitIDList) throws LimitException {
		if ((limitIDList == null) || (limitIDList.size() == 0)) {
			return null;
		}

		HashMap limitMap = new HashMap();

		List params = new ArrayList();
		String theSQL = getApprovedLimitAmountSQL(limitIDList, params);

		List limitList = getJdbcTemplate().query(theSQL, params.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				long cmsLimitID = rs.getLong("cms_lmt_id");
				String outerLimitIDStr = rs.getString("outer_lmt_id");
				long outerLimitID = ((outerLimitIDStr == null) || (outerLimitIDStr.length() == 0)) ? ICMSConstant.LONG_INVALID_VALUE
						: Long.parseLong(outerLimitIDStr);

				String limitCCYCode = rs.getString("lmt_ccy");
				BigDecimal apprLimitAmtBD = rs.getBigDecimal("appr_lmt_amt");
				Amount apprLimitAmt = null;
				if ((limitCCYCode != null) && (limitCCYCode.length() > 0) && (apprLimitAmtBD != null)) {
					apprLimitAmt = new Amount(apprLimitAmtBD, new CurrencyCode(limitCCYCode));
				} else {
					apprLimitAmt = null;
				}

				OBLimit limit = new OBLimit();
				limit.setLimitID(cmsLimitID);
				limit.setLimitRef(rs.getString("sci_lmt_id"));
				limit.setApprovedLimitAmount(apprLimitAmt);
				limit.setOuterLimitID(outerLimitID);

				return limit;
			}
		});

		for (Iterator itr = limitList.iterator(); itr.hasNext();) {
			OBLimit limit = (OBLimit) itr.next();

			List limitSecMapList = getJdbcTemplate().query(SELECT_LMT_COL_MAP_DETAILS,
					new Object[]{new Long(limit.getLimitID())}, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ICollateral aCol = new OBCollateral();
					aCol.setCollateralID(rs.getLong("cms_collateral_id"));
					aCol.setSCISecurityID(rs.getString("sci_las_sec_id"));

					ICollateralAllocation aColAlloc = new OBCollateralAllocation();
					aColAlloc.setCollateral(aCol);
					aColAlloc.setHostStatus(rs.getString("update_status_ind"));
					return aColAlloc;
				}
			});

			limit.setCollateralAllocations((ICollateralAllocation[]) limitSecMapList
					.toArray(new ICollateralAllocation[0]));

			limitMap.put(String.valueOf(limit.getLimitID()), limit);
		}

		return limitMap;
	}

	public HashMap getApprovedLimitAmount4CoBorrower(List limitIDList) throws LimitException {
		if ((limitIDList == null) || (limitIDList.size() == 0)) {
			return null;
		}

		final HashMap limitMap = new HashMap();

		List params = new ArrayList();
		String theSQL = getApprovedLimitAmountSQL4CoBorrower(limitIDList, params);

		getJdbcTemplate().query(theSQL, params.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					long cmsLimitID = rs.getLong("lcl_id");
					String limitCCYCode = rs.getString("LCL_CRRNCY_ISO_CODE");
					BigDecimal apprLimitAmtBD = rs.getBigDecimal("LCL_LMT_AMT");
					Amount apprLimitAmt = null;
					if ((limitCCYCode != null) && (limitCCYCode.length() > 0) && (apprLimitAmtBD != null)) {
						apprLimitAmt = new Amount(apprLimitAmtBD, new CurrencyCode(limitCCYCode));
					} else {
						apprLimitAmt = null;
					}
					OBLimit limit = new OBLimit();
					limit.setLimitID(cmsLimitID);
					limit.setApprovedLimitAmount(apprLimitAmt);
					limitMap.put(String.valueOf(cmsLimitID), limit);
				}
				return null;
			}
		});

		return limitMap;
	}

	/**
	 * Helper method to constuct the sql to get approved limit amount given a
	 * list of CMS limit IDs.
	 *
	 * @param limitIDList : list of unique CMS limit IDs
	 * @return String containing the sql query
	 */
	private String getApprovedLimitAmountSQL(List limitIDList, List params) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(SELECT_LMT_DETAILS);
		CommonUtil.buildSQLInList(limitIDList, sqlBuf, params);
		return sqlBuf.toString();
	}

	private String getApprovedLimitAmountSQL4CoBorrower(List limitIDList, List params) {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(SELECT_LMT_DETAILS_4CoBorrower);
		CommonUtil.buildSQLInList(limitIDList, sqlBuf, params);
		return sqlBuf.toString();
	}

	/**
	 * Computes the due date of a date given its day period and country code.
	 *
	 * @param limitprofileID start date
	 * @return LimitProfile
	 * @throws LimitException on error calculating the due date
	 */
	public ILimitProfile getLimitProfileLimitOnly(final long limitprofileID) throws LimitException {

		String selectSQL = "SELECT CMS_LSP_LMT_PROFILE_ID, "
			+ "       LLP_LE_ID, "
			+ "       map.CMS_COLLATERAL_ID, "
			+ "       map.SECURITY_SUB_TYPE_ID, "
			+ "       LMT_OUTER_LMT_ID, "
			+ "       appr.LMT_CRRNCY_ISO_CODE, "
			+ "       CMS_ACTIVATED_LIMIT, "
			+ "       CMDT_OP_LMT, "
			+ "       LMT_AMT, "
			+ "       appr.CMS_LSP_APPR_LMTS_ID, "
			+ "       CMS_LIMIT_PROFILE_ID, "
			+ "       LMT_ID "
			+ "FROM SCI_LSP_LMT_PROFILE lmtprof, "
			+ "     SCI_LSP_APPR_LMTS appr LEFT OUTER JOIN  "
			+ "    (SELECT sec.SECURITY_SUB_TYPE_ID, "
			+ "            map.*  "
			+ "     FROM CMS_SECURITY sec, CMS_LIMIT_SECURITY_MAP map "
			+ "     WHERE sec.CMS_COLLATERAL_ID = map.CMS_COLLATERAL_ID) map ON map.CMS_LSP_APPR_LMTS_ID = appr.CMS_LSP_APPR_LMTS_ID "
			+ "WHERE lmtprof.CMS_LSP_LMT_PROFILE_ID = appr.CMS_LIMIT_PROFILE_ID "
			+ "      AND CMS_LSP_LMT_PROFILE_ID = ? " + "ORDER BY appr.LMT_ID ";

		return (ILimitProfile) getJdbcTemplate().query(selectSQL, new Object[]{new Long(limitprofileID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return process(rs, limitprofileID);
			}
		});
	}

	private ILimitProfile process(ResultSet rs, long limitprofileID) throws SQLException {
		ILimitProfile limitProfile = new OBLimitProfile();
		Collection limits = new ArrayList();
		HashMap limitMap = new HashMap();
		Collection alloc = new ArrayList();
		String leID = "";

		while (rs.next()) {
			OBLimit limit = new OBLimit();
			long limitID = rs.getLong("CMS_LSP_APPR_LMTS_ID");
			limit.setLimitID(limitID);

			leID = rs.getString("LLP_LE_ID");
			limit.setLimitProfileID(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));
			// limit.setLimitID(rs.getLong("LMT_ID"));
			limit.setOuterLimitID(rs.getLong("LMT_OUTER_LMT_ID"));

			String currCode = rs.getString("LMT_CRRNCY_ISO_CODE");
			Amount actLimit = new Amount(rs.getDouble("CMS_ACTIVATED_LIMIT"), currCode);
			limit.setActivatedLimitAmount(actLimit);

			Amount optLimitAmount = new Amount(rs.getDouble("CMDT_OP_LMT"), currCode);

			limit.setOperationalLimit(optLimitAmount);
			limit.setApprovedLimitAmount(new Amount(rs.getDouble("LMT_AMT"), currCode));

			String secSubType = rs.getString("SECURITY_SUB_TYPE_ID");
			if ((secSubType != null) && ICMSConstant.SECURITY_TYPE_COMMODITY.equals(secSubType.substring(0, 2))) {
				ICommodityCollateral cmdtCol = new OBCommodityCollateral();
				ICollateralAllocation allocation = new OBCollateralAllocation();
				allocation.setCollateral(cmdtCol);
				alloc.add(allocation);
			} else {
				ICollateral col = new OBCollateral();
				ICollateralAllocation allocation = new OBCollateralAllocation();
				allocation.setCollateral(col);
				alloc.add(allocation);
			}
			limit.setCollateralAllocations((ICollateralAllocation[]) alloc.toArray(new ICollateralAllocation[0]));
			limitMap.put(new Long(limitID), limit);
		}

		for (Iterator iterator = limitMap.values().iterator(); iterator.hasNext();) {
			ILimit iLimit = (ILimit) iterator.next();
			limits.add(iLimit);
		}
		limitProfile.setLimits((ILimit[]) limits.toArray(new ILimit[0]));
		limitProfile.setLimitProfileID(limitprofileID);
		limitProfile.setLEReference(leID);
		return limitProfile;
	}

	/**
	 * Get LimitProfileId given CMS_COLLATERAL_ID.
	 *
	 * @param - CMS_COLLATERAL_ID
	 * @return HashMap - LimitProfileId as key and LE_ID+BCA_REF_NUM as value
	 */
	public HashMap getLimitProfileIds(long collateralId) throws LimitException {
		if (collateralId == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}
		final HashMap limitprofileIdMap = new HashMap();
		String theSql = "SELECT DISTINCT PF.CMS_LSP_LMT_PROFILE_ID ,PF.LLP_LE_ID, PF.LLP_BCA_REF_NUM " + ""
		+ "FROM SCI_LSP_LMT_PROFILE PF , CMS_LIMIT_SECURITY_MAP MP "
		+ "WHERE PF.LLP_ID = MP.SCI_LAS_LLP_ID AND MP.CMS_COLLATERAL_ID = ?";

		getJdbcTemplate().query(theSql, new Object[]{new Long(collateralId)}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				long limitProfileId = ICMSConstant.LONG_INVALID_VALUE;
				long leId = ICMSConstant.LONG_INVALID_VALUE;
				String bcaReferenceNum = null;
				String leIdAndBcaRefNum = null;
				while (rs.next()) {
					limitProfileId = rs.getLong("CMS_LSP_LMT_PROFILE_ID");
					leId = rs.getLong("LLP_LE_ID");
					bcaReferenceNum = rs.getString("LLP_BCA_REF_NUM");
					leIdAndBcaRefNum = leId + "," + bcaReferenceNum;
					limitprofileIdMap.put(new Long(limitProfileId), leIdAndBcaRefNum);
				}
				return null;
			}
		});

		return limitprofileIdMap;
	}

	/**
	 * Get LimitProfileId given CMS_COLLATERAL_ID.
	 *
	 * @param collateralId
	 * @return
	 * @throws LimitException
	 */
	public List getLimitProfileIdsByApprLmts(long collateralId) throws LimitException {
		if (collateralId == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}
		String theSql = "SELECT DISTINCT SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "FROM SCI_LSP_LMT_PROFILE, CMS_LIMIT_SECURITY_MAP, SCI_LSP_APPR_LMTS "
			+ "WHERE SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID "
			+ " AND SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ " AND CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID = ?";
		return getJdbcTemplate().query(theSql, new Object[]{new Long(collateralId)}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Long(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));
			}
		});
	}

	/**
	 * Get LEId&BCARef given limitProfileId.
	 *
	 * @param - limitProfileId
	 * @return LEId&BCARef concatenated as a String.
	 */
	public String getLEIdAndBCARef(long limitProfileId) throws LimitException {
		// DefaultLogger.debug(
		// this,"LimitDAO.getLEId&BCARef:limitProfileId is:" +limitProfileId);
		if (limitProfileId == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}

		String theSql = "SELECT LLP_LE_ID, LLP_BCA_REF_NUM FROM SCI_LSP_LMT_PROFILE PF WHERE CMS_LSP_LMT_PROFILE_ID = ?";

		return (String) getJdbcTemplate().query(theSql, new Object[]{new Long(limitProfileId)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				long leId = ICMSConstant.LONG_INVALID_VALUE;
				String bcaRefNum = null;
				String leIdAndBcaRefNum = null;

				while (rs.next()) {
					leId = rs.getLong("LLP_LE_ID");
					bcaRefNum = rs.getString("LLP_BCA_REF_NUM");
					leIdAndBcaRefNum = leId + "," + bcaRefNum;
				}

				return leIdAndBcaRefNum;
			}
		});
	}

	public List getFSVBalForMainborrowLimit(String limitId) {

		double totalFSV = 0;
		final List result = new ArrayList();
		final List toAdd = new ArrayList();

		String sql = "SELECT LMT1.LMT_ID, SEC.CMS_COLLATERAL_ID, "
			+ "CONVERT_AMT(SEC.FSV_BALANCE, SEC.FSV_BALANCE_CCY, SEC.FSV_CURRENCY), SEC.FSV_CURRENCY "
			+ "FROM SCI_LSP_APPR_LMTS LMT1, CMS_LIMIT_CHARGE_MAP  MAP1, " + "CMS_SECURITY SEC "
			+ "WHERE LMT1.CMS_LSP_APPR_LMTS_ID = ? AND "
			+ "LMT1.CMS_LSP_APPR_LMTS_ID = MAP1.CMS_LSP_APPR_LMTS_ID AND " + "MAP1.STATUS = 'ACTIVE' AND "
			+ "MAP1.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND " + "SEC.STATUS = 'ACTIVE'";

		Map lclIdFsvCcy = (Map) getJdbcTemplate().query(sql, new Object[]{limitId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map lmtIdFsvCcyValuesMap = new HashMap();
				while (rs.next()) {
					lmtIdFsvCcyValuesMap.put("lmtId", rs.getString(1));
					String collateralId = rs.getString(2);
					double fsvBal = rs.getDouble(3);
					lmtIdFsvCcyValuesMap.put("fsvCcy", rs.getString(4));
					result.add(collateralId);
					result.add(new Double(fsvBal));
				}
				return lmtIdFsvCcyValuesMap;
			}
		});

		boolean requireShow = false;
		for (int i = 0; i < result.size(); i = i + 2) {
			if (checkIsLastMbLimitForSec((String) (result.get(i)), (String) lclIdFsvCcy.get("lmtId"))) {
				requireShow = true;
				totalFSV = totalFSV + ((Double) (result.get(i + 1))).doubleValue();
			}
		}

		if (requireShow) {
			toAdd.add(new Double(totalFSV));
			toAdd.add(lclIdFsvCcy.get("fsvCcy"));
			return toAdd;
		} else {
			return null;
		}

	}

	private boolean checkIsLastMbLimitForSec(String collateralId, String lmtId) {

		String sql = "SELECT MAX(LMT.LMT_ID) AS MAXID FROM SCI_LSP_APPR_LMTS LMT, CMS_LIMIT_CHARGE_MAP MAP "
			+ "WHERE LMT.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID AND LMT.LMT_TYPE_VALUE = 'OUTER' AND "
			+ "MAP.STATUS = 'ACTIVE' AND MAP.CMS_COLLATERAL_ID = ? ";

		String maxId = (String) getJdbcTemplate().query(sql, new Object[]{collateralId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return null;
			}
		});

		return StringUtils.equals(maxId, lmtId);

	}

	public List getFSVBalForCoborrowLimit(String limitId) {
		String sql = "SELECT LMT1.LCL_ID, SEC.CMS_COLLATERAL_ID, "
			+ "CONVERT_AMT(SEC.FSV_BALANCE, SEC.FSV_BALANCE_CCY, SEC.FSV_CURRENCY), SEC.FSV_CURRENCY "
			+ "FROM SCI_LSP_CO_BORROW_LMT LMT1, CMS_LIMIT_CHARGE_MAP  MAP1, " + "CMS_SECURITY SEC "
			+ "WHERE LMT1.CMS_LSP_CO_BORROW_LMT_ID = ? AND "
			+ "LMT1.CMS_LSP_CO_BORROW_LMT_ID = MAP1.CMS_LSP_CO_BORROW_LMT_ID AND " + "MAP1.STATUS = 'ACTIVE' AND "
			+ "MAP1.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND " + "SEC.STATUS = 'ACTIVE'";

		final List result = new ArrayList();
		final List toAdd = new ArrayList();

		Map lclIdFsvCcy = (Map) getJdbcTemplate().query(sql, new Object[]{limitId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map lclIdFsvCcyValuesMap = new HashMap();
				while (rs.next()) {
					lclIdFsvCcyValuesMap.put("lclId", rs.getString(1));
					String collateralId = rs.getString(2);
					double fsvBal = rs.getDouble(3);
					lclIdFsvCcyValuesMap.put("fsvCcy", rs.getString(4));

					result.add(collateralId);
					result.add(new Double(fsvBal));
				}
				return lclIdFsvCcyValuesMap;
			}
		});

		double totalFSV = 0;

		boolean requireShow = false;
		for (int i = 0; i < result.size(); i = i + 2) {
			if (checkIsLastCbLimitForSec((String) (result.get(i)), (String) lclIdFsvCcy.get("lclId"))) {
				requireShow = true;
				totalFSV = totalFSV + ((Double) (result.get(i + 1))).doubleValue();
			}
		}

		if (requireShow) {
			toAdd.add(new Double(totalFSV));
			toAdd.add((String) lclIdFsvCcy.get("fsvCcy"));
			return toAdd;
		} else {
			return null;
		}

	}

	private boolean checkIsLastCbLimitForSec(String collateralId, String lclId) {
		String sql = "SELECT MAX(LMT.LCL_ID) AS MAXID FROM SCI_LSP_CO_BORROW_LMT LMT, CMS_LIMIT_CHARGE_MAP MAP "
			+ "WHERE LMT.CMS_LSP_CO_BORROW_LMT_ID = MAP.CMS_LSP_CO_BORROW_LMT_ID AND "
			+ "MAP.STATUS = 'ACTIVE' AND MAP.CMS_COLLATERAL_ID = ? ";

		String maxId = (String) getJdbcTemplate().query(sql, new Object[]{collateralId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return null;
			}
		});

		return StringUtils.equals(lclId, maxId);
	}

	public boolean checkLimitExists(String limitId) throws LimitException {
		String query = "SELECT COUNT(1) FROM SCI_LSP_APPR_LMTS WHERE LMT_ID = ?";

		int count = getJdbcTemplate().queryForInt(query, new Object[]{limitId});

		return count > 0;
	}

	public List getOuterLimitList(String aaId) throws LimitException {
		String queryStr = "SELECT LMT.CMS_LSP_APPR_LMTS_ID, " + "LMT.LMT_ID " + "FROM SCI_LSP_APPR_LMTS LMT "
		+ "WHERE LMT.CMS_LIMIT_PROFILE_ID = ? AND " + "LMT.CMS_LIMIT_STATUS = 'ACTIVE' AND "
		+ "LMT.LMT_TYPE_VALUE = 'OUTER' " + "ORDER BY LMT.LMT_ID";

		return (List) getJdbcTemplate().query(queryStr, new Object[]{aaId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				while (rs.next()) {
					String cmsLmtId = rs.getString("CMS_LSP_APPR_LMTS_ID");
					String limitId = rs.getString("LMT_ID");
					String[] arr = new String[2];
					arr[0] = cmsLmtId;
					arr[1] = limitId;
					limitList.add(arr);
				}

				return limitList;
			}
		});
	}

	/**
	 * To get the transaction subtype of the limit transaction
	 *
	 * @param trxID the ID of the transaction
	 * @return the transaction subtype
	 */
	public String getTrxSubTypeByTrxID(long trxID) throws LimitException {
		String queryStr = "SELECT TRANSACTION_SUBTYPE FROM TRANSACTION " + "WHERE TRANSACTION_ID = ?";

		if (trxID == ICMSConstant.LONG_INVALID_VALUE) {
			return null;
		}

		return (String) getJdbcTemplate().query(queryStr, new Object[]{new Long(trxID)}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return (rs.next() ? rs.getString(1) : null);
			}
		});
	}

	public List getLimitSummaryListByAA(String aaId) throws LimitException {

		String queryStr = "SELECT LMT.CMS_LSP_APPR_LMTS_ID, "
			+ " LMT.LMT_ID, "
			+ " CM1.ENTRY_NAME AS LMT_SOURCE, "
			+ " LMT.FACILITY_NAME, "
			+ " LMT.FACILITY_TYPE, "
			//+ " COUNTRY1.CTR_CNTRY_NAME, "
			//+ " LMT.CMS_BKG_COUNTRY, "
			//+ " LMT.CMS_BKG_ORGANISATION, "
			+ " LMT.LMT_OUTER_LMT_ID, "
			+ " LMT.LMT_CRRNCY_ISO_CODE, "
			+ " LMT.RELEASABLE_AMOUNT, "
			+ " LMT.CMS_ACTIVATED_LIMIT, "
			+ " LMT.TOTAL_RELEASED_AMOUNT, "
			+ " LMT.CMS_OUTSTANDING_AMT, "
			+ " LMT.CMS_REQ_SEC_COVERAGE, "
			+ " LMT.IS_ADHOC_TOSUM, "
			+ " LMT.IS_ADHOC, "
			+ " LMT.ADHOC_LMT_AMOUNT, "
			+ " (SELECT COUNT(L2.CMS_LSP_APPR_LMTS_ID) FROM SCI_LSP_APPR_LMTS L2 WHERE L2.LMT_TYPE_VALUE='INNER' AND L2.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ " L2.LMT_OUTER_LMT_ID=LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_INNER, "
			+ " (SELECT COUNT(M2.CMS_COLLATERAL_ID) FROM CMS_LIMIT_SECURITY_MAP M2 "
			+ " WHERE ( M2.UPDATE_STATUS_IND IS NULL OR M2.UPDATE_STATUS_IND <> 'D' ) AND "
			+ " M2.CMS_LSP_APPR_LMTS_ID = LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_SEC, "
			+ " (SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY "
			+ " WHERE CATEGORY_CODE = 'SEC_SOURCE' "
			+ " AND ENTRY_CODE = SS.SOURCE_ID) AS SEC_SOURCE,"
			+ " SS.SOURCE_SECURITY_ID, "
			+ " SEC.TYPE_NAME, "
			+ " SEC.SUBTYPE_NAME, "
			+ " MAP.LMT_SECURITY_COVERAGE, "
			+ " SEC.COLLATERAL_CODE, "
			+ " SEC.CMS_COLLATERAL_ID, "
			+ " LMT.LMT_TYPE_VALUE "
			//+ " COUNTRY2.CTR_CNTRY_NAME, "
			// + " SEC.SECURITY_ORGANISATION "
			+ " FROM SCI_LSP_APPR_LMTS LMT LEFT OUTER JOIN CMS_LIMIT_SECURITY_MAP MAP ON LMT.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID "
			+ "  AND ( MAP.UPDATE_STATUS_IND IS NULL OR MAP.UPDATE_STATUS_IND <> 'D' ) LEFT OUTER JOIN CMS_SECURITY SEC ON MAP.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND SEC.STATUS = 'ACTIVE' "
			// + " LEFT OUTER JOIN SCI_COUNTRY COUNTRY2 ON SEC.SECURITY_LOCATION = COUNTRY2.CTR_CNTRY_ISO_CODE "
			+ " LEFT OUTER JOIN CMS_SECURITY_SOURCE SS ON SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND SS.STATUS <> 'DELETED', "
			//+ " LEFT OUTER JOIN CMS_COLLATERAL_NEW_MASTER COLNEWMAST ON COLNEWMAST.NEW_COLLATERAL_CODE=SEC.COLLATERAL_CODE , "
			+ // AND SS.SOURCE_ID = LMT.SOURCE_ID
			//"SCI_COUNTRY COUNTRY1, " 
			" COMMON_CODE_CATEGORY_ENTRY CM1 "
			+ " WHERE LMT.CMS_LIMIT_PROFILE_ID = ? AND " + "LMT.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			//+ "LMT.CMS_BKG_COUNTRY = COUNTRY1.CTR_CNTRY_ISO_CODE AND " 
			+ " CM1.CATEGORY_CODE = '" + ICMSConstant.CATEGORY_SOURCE_SYSTEM + "' " + "ORDER BY LMT.FACILITY_NAME ,SS.SOURCE_SECURITY_ID, MAP.CHARGE_ID DESC";
		DefaultLogger.debug(this, "**********In getLimitSummaryListByAA() : 1235 queryStr: "+queryStr+" <<<<<<<<<<<<aaId:"+aaId);
		return (List) getJdbcTemplate().query(queryStr, new Object[]{aaId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				List prevLimitId = new ArrayList();
				//String prevLimitId = "";
				String prevSecId = "";
				HashSet set = new HashSet();

				while (rs.next()) {
					String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
					String securityId = rs.getString("SOURCE_SECURITY_ID");

					LimitListSummaryItemBase curSummary = null;
					if (!prevLimitId.contains(cmsLimitId)) {
						curSummary = new LimitListSummaryItemBase();
						curSummary.setCmsLimitId(cmsLimitId);
						String limitId = rs.getString("LMT_ID") + " - " + rs.getString("LMT_SOURCE");
						curSummary.setLimitId(limitId);
						String prodType = rs.getString("FACILITY_NAME");
						curSummary.setProdTypeCode(prodType);
						String facilityType = rs.getString("FACILITY_TYPE");
						curSummary.setFacilityTypeCode(facilityType);
						String lmtCountry = rs.getString("FACILITY_TYPE");
						curSummary.setLimitLoc(lmtCountry);
						// String lmtCtryCode = rs.getString("CMS_BKG_COUNTRY");
						//String lmtBkgOrg = rs.getString("CMS_BKG_ORGANISATION");
						// IBookingLocation lmtBkgLoc = new OBBookingLocation(lmtCtryCode, lmtBkgOrg);
						//curSummary.setLimitBookingLoc(lmtBkgLoc);
						String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
						if ("0".equals(outerLmtId)
								|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
							outerLmtId = null;
						}
						//curSummary.setOuterLimitId(outerLmtId);
						String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
						if (lmtCurrency != null) {
							curSummary.setCurrencyCode(lmtCurrency);
						}

						String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
						if (lmtApproveAmt != null) {
							curSummary.setApprovedAmount(lmtApproveAmt);
						}
						String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
						if (lmtActivateAmt != null) {
							curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
						}
						String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
						if (lmtDrawAmt != null) {
							curSummary.setDrawingAmount(lmtDrawAmt);
						}
						String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
						if (lmtOutstdAmt != null) {
							curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
						}
						String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
						if (actSeccov != null) {
							curSummary.setActualSecCoverage(actSeccov);
						}
						String isAdhocToSum = rs.getString("IS_ADHOC_TOSUM");
						if (isAdhocToSum != null) {
							curSummary.setIsAdhocToSum(isAdhocToSum);
						}
						String isAdhoc = rs.getString("IS_ADHOC");
						if (isAdhoc != null) {
							curSummary.setIsAdhoc(isAdhoc);
						}

						String adhocAmount = rs.getString("ADHOC_LMT_AMOUNT");
						if (adhocAmount != null) {
							curSummary.setAdhocAmount(adhocAmount);
						}
						
						String isSubLimit = rs.getString("LMT_TYPE_VALUE");
						if (isSubLimit != null) {
							curSummary.setIsSubLimit(isSubLimit);
						}
						
						curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
						curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));
						limitList.add(curSummary);
						prevLimitId.add(cmsLimitId);
						prevSecId = "";
					} else {
						// limit id same as last added item

						for(int i =0 ; i<limitList.size();i++)
						{

							LimitListSummaryItemBase limit=(LimitListSummaryItemBase)limitList.get(i);
							if(limit.getCmsLimitId().equals(cmsLimitId))
							{
								curSummary = (LimitListSummaryItemBase) (limitList.get(i));
							}
						}
					}

					if ((securityId != null) && !securityId.equals(prevSecId)) {
						LimitListSecItemBase secItem = new LimitListSecItemBase();
						secItem.setSecurityId(rs.getString("CMS_COLLATERAL_ID"));
						String typeName = rs.getString("TYPE_NAME");
						secItem.setSecTypeName(typeName);
						String subtypeName = rs.getString("SUBTYPE_NAME");
						secItem.setSecSubtypeName(subtypeName);
						String lmtSecurityCoverage = rs.getString("LMT_SECURITY_COVERAGE");
						secItem.setLmtSecurityCoverage(lmtSecurityCoverage);
						secItem.setSecLocDesc(rs.getString("COLLATERAL_CODE"));
						//String secCountry = rs.getString("CTR_CNTRY_NAME");
						// secItem.setSecLocDesc(secCountry);
						//String secOrg = rs.getString("SECURITY_ORGANISATION");
						//secItem.setSecOrgDesc(secOrg); 
						curSummary.addSecItem(secItem);
						prevSecId = securityId;
						set.add(securityId);
					}
				}

				return limitList;
			}
		});
	}

	public HashMap getLimitProductTypeByLimitIDList(ArrayList limitIDList) throws LimitException {
		final HashMap result = new HashMap();

		if ((limitIDList == null) || (limitIDList.size() == 0)) {
			return result;
		}

		StringBuffer sqlBuf = new StringBuffer(
				"SELECT CMS_LSP_APPR_LMTS_ID, LMT_PRD_TYPE_VALUE, ACCOUNT_TYPE, LMT_CRRNCY_ISO_CODE "
				+ " FROM SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID ");

		List params = new ArrayList();
		CommonUtil.buildSQLInList(limitIDList, sqlBuf, params);

		String sql = sqlBuf.toString();

		getJdbcTemplate().query(sql, params.toArray(), new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					String productType = "";

					if (PropertiesConstantHelper.isProductDescSpecialHandling()) {
						String accountType = rs.getString("ACCOUNT_TYPE");
						productType = (accountType != null) ? (rs.getString("LMT_PRD_TYPE_VALUE") + "|"
								+ rs.getString("LMT_CRRNCY_ISO_CODE") + "|" + accountType) : (rs
										.getString("LMT_PRD_TYPE_VALUE")
										+ "|" + rs.getString("LMT_CRRNCY_ISO_CODE"));
					} else {
						productType = rs.getString("LMT_PRD_TYPE_VALUE");
					}
					result.put(rs.getString("CMS_LSP_APPR_LMTS_ID"), productType);
				}
				return null;
			}
		});

		return result;
	}

	public Amount getNetoutStandingForAccount(String accountId, final String limitCurrency) throws LimitException {
		String query = "SELECT NPL.NET_OUTSTANDING FROM SCI_LSP_SYS_XREF ACCT, CMS_NPL NPL "
			+ "WHERE ACCT.LSX_EXT_SYS_ACCT_NUM = NPL.ACCOUNT_NUMBER AND ACCT.CMS_LSP_SYS_XREF_ID = ?";

		return (Amount) getJdbcTemplate().query(query, new Object[]{accountId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					String netOutStdVal = rs.getString("NET_OUTSTANDING");
					if ((netOutStdVal != null) && (limitCurrency != null)) {
						Amount netOutStdAmt = new Amount(Double.parseDouble(netOutStdVal), limitCurrency);
						return netOutStdAmt;
					}
				}
				return null;
			}

		});
	}

	public boolean checkLimitProfileStpComplete(final ILimitProfile limitProfile) {
		StringBuffer facilityStpQuery = new StringBuffer();
		facilityStpQuery.append("select stp.status from sci_lsp_appr_lmts lmt, cms_facility_master fac, ");
		facilityStpQuery.append("transaction trx left outer join stp_master_trans stp ");
		facilityStpQuery.append("on trx.transaction_id = stp.transaction_id ");
		facilityStpQuery.append("where trx.reference_id = fac.id ");
		facilityStpQuery.append(" AND trx.transaction_type = 'FACILITY' ");
		facilityStpQuery.append(" AND lmt.cms_limit_profile_id = ? ");
		facilityStpQuery.append(" AND lmt.cms_lsp_appr_lmts_id = fac.cms_lsp_appr_lmts_id ");

		Boolean isAllFacilityCompleteStp = (Boolean) getJdbcTemplate().query(facilityStpQuery.toString(),
				new Object[]{new Long(limitProfile.getLimitProfileID())}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				boolean isRecordAvailable = false;
				while (rs.next()) {
					isRecordAvailable = true;
					String stpStatus = rs.getString("status");
					if (!(IStpConstants.MASTER_TRX_COMPLETE.equals(stpStatus))) {
						return Boolean.FALSE;
					}
				}

				if (!isRecordAvailable) {
					return Boolean.FALSE;
				}

				return Boolean.TRUE;
			}
		});

		if (!isAllFacilityCompleteStp.booleanValue()) {
			return false;
		}

		String[] filteredFacilityCodes = StringUtils.split(PropertyManager.getValue("facility.codes.for.trading"), '|');
		List distinctCollateralIds = retrieveUniqueCmsCollateralIdListbyLimitProfileId(
				limitProfile.getLimitProfileID(), filteredFacilityCodes);

		if (!distinctCollateralIds.isEmpty()) {
			Set distinctCollateralIdSet = new HashSet(distinctCollateralIds);
			List argList = new ArrayList();
			StringBuffer collateralStpQuery = new StringBuffer();
			collateralStpQuery.append("select status, reference_id from stp_master_trans where reference_id ");

			CommonUtil.buildSQLInList(distinctCollateralIds, collateralStpQuery, argList);

			collateralStpQuery.append(" and transaction_type = ?");
			argList.add(ICMSConstant.INSTANCE_COLLATERAL);

			final Set collateralHasStpCompleteList = new HashSet();
			Boolean isAllCollateralCompleteStp = (Boolean) getJdbcTemplate().query(collateralStpQuery.toString(),
					argList.toArray(), new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					boolean isRecordAvailable = false;
					while (rs.next()) {
						isRecordAvailable = true;
						String stpStatus = rs.getString("status");
						collateralHasStpCompleteList.add(new Long(rs.getLong("reference_id")));
						if (!(IStpConstants.MASTER_TRX_COMPLETE.equals(stpStatus))) {
							return Boolean.FALSE;
						}
					}

					if (!isRecordAvailable) {
						return Boolean.FALSE;
					}

					return Boolean.TRUE;
				}
			});

			if (!isAllCollateralCompleteStp.booleanValue()
					|| !distinctCollateralIdSet.equals(collateralHasStpCompleteList)) {
				return false;
			}

		}

		return true;
	}

	public HashMap getAccountInfoByLimitId(long limitId) {
		return (HashMap) getJdbcTemplate().query(SELECT_ACOUNT_IFO, new Object[]{new Long(limitId)},
				new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				HashMap returnMap = new HashMap();
				if (rs.next()) {
					returnMap.put("LMT_FAC_SEQ", new Long(rs.getLong("LMT_FAC_SEQ")));
					returnMap.put("LSX_EXT_SYS_ACCT_NUM", rs.getString("LSX_EXT_SYS_ACCT_NUM"));
					returnMap.put("LSX_EXT_SYS_ACCT_TYPE", rs.getString("LSX_EXT_SYS_ACCT_TYPE"));
				}
				return returnMap;
			}
		});
	}

	public boolean checkLimitProfileHasResponseToSource(long cmsLimitProfileId) {
		int count = getJdbcTemplate().queryForInt(
				"SELECT count(*) FROM cms_eai_aa_response_log WHERE cms_lsp_lmt_profile_id = ?",
				new Object[]{new Long(cmsLimitProfileId)});

		return count > 0;
	}

	public void insertOrUpdateLimitProfileResponseLog(ILimitProfile limitProfile) {
		int count = getJdbcTemplate().queryForInt(
				"SELECT count(*) FROM cms_eai_aa_response_log WHERE cms_lsp_lmt_profile_id = ? ",
				new Object[]{new Long(limitProfile.getLimitProfileID())});

		if (count == 0) {
			StringBuffer buf = new StringBuffer();
			buf.append("INSERT INTO cms_eai_aa_response_log (");
			buf.append("cms_lsp_lmt_profile_id, host_bca_ref_num, los_bca_ref_num, source_id, aa_received_date");
			buf.append(") values (?, ?, ?, ?, ?)");

			getJdbcTemplate().update(
					buf.toString(),
					new Object[]{new Long(limitProfile.getLimitProfileID()), limitProfile.getBCAReference(),
						limitProfile.getLosLimitProfileReference(), limitProfile.getSourceID(), new Date()});
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("UPDATE cms_eai_aa_response_log SET response_fire_date = null, aa_received_date = ? "
					+ "WHERE cms_lsp_lmt_profile_id = ?");

			getJdbcTemplate().update(buf.toString(),
					new Object[]{new Date(), new Long(limitProfile.getLimitProfileID())});
		}
	}

	public List retrieveListOfCmsLimitProfileIdNoResponseToSource() {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT cms_lsp_lmt_profile_id, los_bca_ref_num, source_id FROM cms_eai_aa_response_log WHERE ");
		buf.append("response_fire_date IS NULL");

		RowMapper cmsLimitProfileIdsRowMapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ILimitProfile limitProfile = new OBLimitProfile();
				limitProfile.setLimitProfileID(rs.getLong("cms_lsp_lmt_profile_id"));
				limitProfile.setLosLimitProfileReference(rs.getString("los_bca_ref_num"));
				limitProfile.setSourceID(rs.getString("source_id"));
				return limitProfile;
			}
		};

		return getJdbcTemplate().query(buf.toString(), cmsLimitProfileIdsRowMapper);
	}

	public void updateLimitProfileResponseLogAfterFired(ILimitProfile limitProfile) {
		if (!checkLimitProfileHasResponseToSource(limitProfile.getLimitProfileID())) {
			insertOrUpdateLimitProfileResponseLog(limitProfile);
		}

		StringBuffer buf = new StringBuffer();
		buf.append("UPDATE cms_eai_aa_response_log SET response_fire_date = ? WHERE cms_lsp_lmt_profile_id = ?");

		getJdbcTemplate().update(buf.toString(),
				new Object[]{new Date(), new Long(limitProfile.getLimitProfileID())});
	}

	public ICMSTrxValue retrieveLatestCollateralOrFacilityTrxValueByCmsLimitProfileId(long cmsLimitProfileId) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT transaction_id, transaction_date, login_id FROM transaction WHERE ");
		buf.append("limit_profile_id = ? AND transaction_type IN (?, ?) ");

		List cmsTrxValueList = getJdbcTemplate().query(buf.toString(),
				new Object[]{new Long(cmsLimitProfileId), ICMSConstant.INSTANCE_COLLATERAL, "FACILITY"},
				new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ICMSTrxValue cmsTrxValue = new OBCMSTrxValue();
				cmsTrxValue.setTransactionID(rs.getString("transaction_id"));
				cmsTrxValue.setTransactionDate(rs.getTimestamp("transaction_date"));
				cmsTrxValue.setLoginId(rs.getString("login_id"));

				return cmsTrxValue;
			}
		});

		if (!cmsTrxValueList.isEmpty()) {
			Collections.sort(cmsTrxValueList, new Comparator() {

				public int compare(Object thisTrx, Object thatTrx) {
					ICMSTrxValue trx1 = (ICMSTrxValue) thisTrx;
					ICMSTrxValue trx2 = (ICMSTrxValue) thatTrx;

					int result = trx1.getTransactionDate().compareTo(trx2.getTransactionDate());
					if (result == 0) {
						return trx1.getTransactionID().compareTo(trx2.getTransactionID()) * (-1);
					} else {
						return result * (-1);
					}
				}
			});

			return (ICMSTrxValue) cmsTrxValueList.get(0);
		}

		return null;
	}

	public List retrieveUniqueCmsCollateralIdListbyLimitProfileId(long cmsLimitProfileId, String[] filteredFacilityCodes) {
		List argList = new ArrayList();

		StringBuffer buf = new StringBuffer("SELECT DISTINCT cms_collateral_id ");
		buf.append("FROM cms_limit_security_map map, sci_lsp_appr_lmts lmt ");
		buf.append("WHERE map.cms_lsp_appr_lmts_id = lmt.cms_lsp_appr_lmts_id ");
		buf.append(" AND lmt.cms_limit_profile_id = ? ");
		buf.append(" AND (map.update_status_ind IS NULL OR map.update_status_ind <> 'D') ");

		argList.add(new Long(cmsLimitProfileId));

		if (filteredFacilityCodes != null) {
			buf.append(" AND lmt.lmt_fac_type_value NOT ");
			CommonUtil.buildSQLInList(filteredFacilityCodes, buf, argList);
		}

		return getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Long(rs.getLong("cms_collateral_id"));
			}
		});
	}

	public ILimitProfile retrieveLimitProfileByCmsLimitProfileId(final Long cmsLimitProfileId) {
		StringBuffer buf = new StringBuffer("SELECT los_bca_ref_num, llp_bca_ref_num, cms_customer_id, ");
		buf.append("llp_le_id, cms_orig_country, cms_orig_organisation, application_type ");
		buf.append("FROM sci_lsp_lmt_profile WHERE cms_lsp_lmt_profile_id = ?");

		return (ILimitProfile) getJdbcTemplate().query(buf.toString(), new Object[]{cmsLimitProfileId},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					ILimitProfile limitProfile = new OBLimitProfile();
					limitProfile.setLimitProfileID(cmsLimitProfileId.longValue());
					limitProfile.setLosLimitProfileReference(rs.getString("los_bca_ref_num"));
					limitProfile.setBCAReference(rs.getString("llp_bca_ref_num"));
					limitProfile.setCustomerID(rs.getLong("cms_customer_id"));
					limitProfile.setApplicationType(rs.getString("application_type"));
					limitProfile.setLEReference(rs.getString("llp_le_id"));

					OBBookingLocation ob = new OBBookingLocation();
					ob.setCountryCode(rs.getString("cms_orig_country"));
					ob.setOrganisationCode(rs.getString("cms_orig_organisation"));

					limitProfile.setOriginatingLocation(ob);

					return limitProfile;
				}

				return null;
			}
		});
	}

	/**
	 * Get a list of Distinct Outer Limit ID belong to the given Limit Profile ID.
	 *
	 * @param limitProfileId is of type long
	 * @return a list of Distinct Outer Limit ID
	 */
	public String[] getDistinctOuterLimitID(long limitProfileId)
	throws LimitException {
		String selectSQL = "select distinct (LMT_OUTER_LMT_ID) " +
		"from SCI_LSP_APPR_LMTS " +
		"where CMS_LIMIT_PROFILE_ID = ?" +
		" AND CMS_LIMIT_STATUS = '" + ICMSConstant.STATE_ACTIVE + "' and LMT_OUTER_LMT_ID is not null";

		List arrList = getJdbcTemplate().query(selectSQL,
				new Object[] { new Long(limitProfileId) }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("LMT_OUTER_LMT_ID");
			}
		});

		if (arrList.size()<1){
			return null;
		}else{
			return (String[]) arrList.toArray(new String[0]);
		}
	}

	public List getFacNameList(String facCat) throws SearchDAOException {
		//DefaultLogger.debug(this, "limit dropdown issue inside getFacNameList method");
		String sql = "SELECT NEW_FACILITY_CODE, NEW_FACILITY_NAME FROM CMS_FACILITY_NEW_MASTER WHERE STATUS='ACTIVE' AND UPPER(NEW_FACILITY_CATEGORY)=?";
		//String sql = "SELECT NEW_FACILITY_CODE, NEW_FACILITY_NAME FROM CMS_FACILITY_NEW_MASTER WHERE STATUS='ACTIVE' AND UPPER(NEW_FACILITY_CATEGORY)='"+facCat.toUpperCase()+"'";
	//	DefaultLogger.debug(this, "limit dropdown issue inside getFacNameList method sql:"+sql);
		List resultList = getJdbcTemplate().query(sql,new Object[] {facCat.toUpperCase()}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setFacilityCode(rs.getString("NEW_FACILITY_CODE"));
				obLimit.setFacilityName(rs.getString("NEW_FACILITY_NAME"));
				return obLimit;
			}
		});
		
	
		/*List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setFacilityCode(rs.getString("NEW_FACILITY_CODE"));
				obLimit.setFacilityName(rs.getString("NEW_FACILITY_NAME"));
				return obLimit;
			}
		});*/

		return resultList;
	}
	
	public List getFacNameList(String facCat, String system) throws SearchDAOException {
		String sql = "SELECT NEW_FACILITY_CODE, NEW_FACILITY_NAME FROM CMS_FACILITY_NEW_MASTER WHERE "
				+ "STATUS='ACTIVE' AND UPPER(NEW_FACILITY_CATEGORY)=? and NEW_FACILITY_SYSTEM = ?";
		List resultList = getJdbcTemplate().query(sql,new Object[] {facCat.toUpperCase(), system}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setFacilityCode(rs.getString("NEW_FACILITY_CODE"));
				obLimit.setFacilityName(rs.getString("NEW_FACILITY_NAME"));
				return obLimit;
			}
		});
		return resultList;
	}

	public List getRelationShipMngrList() throws SearchDAOException {
		String sql = "SELECT RM_MGR_CODE, RM_MGR_NAME FROM CMS_RELATIONSHIP_MGR WHERE STATUS='ACTIVE'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("RM_MGR_CODE");
				mgnrLst[1] = rs.getString("RM_MGR_NAME");
				return mgnrLst;
			}
		});

		return resultList;
	}
	
	public List getCountryList() throws SearchDAOException {
		String sql = "select country_code,country_name from cms_country where status = 'ACTIVE' order by country_code ";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("country_code");
				mgnrLst[1] = rs.getString("country_name");
				return mgnrLst;
			}
		});

		return resultList;
	}
	
	public List getBankList() throws SearchDAOException {
		String sql = "select bank_code,bank_name from CMS_OTHER_BANK where status = 'ACTIVE' order by bank_name ";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("bank_code");
				mgnrLst[1] = rs.getString("bank_name");
				return mgnrLst;
			}
		});

		return resultList;
	}
	
	public List getCurrList() throws SearchDAOException {
		String sql = "select feed_id,buy_currency from CMS_FOREX where status = 'ENABLE' order by buy_currency ";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("feed_id");
				mgnrLst[1] = rs.getString("buy_currency");
				return mgnrLst;
			}
		});

		return resultList;
	}
	
	public List getGoodsList() throws SearchDAOException {
		String sql = "SELECT distinct trim(concat(concat(goods_parent_code ,' ') ,goods_code)) as goods_cd, goods_name, goods_parent_code\r\n" + 
				"   FROM CMS_GOODS_MASTER where STATUS='ACTIVE' \r\n" + 
				"   CONNECT BY PRIOR goods_code = goods_parent_code ORDER by goods_cd desc    ";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[3];
				mgnrLst[0] = rs.getString("GOODS_CD");
				mgnrLst[1] = rs.getString("GOODS_NAME");
				mgnrLst[2] = rs.getString("GOODS_PARENT_CODE");
				return mgnrLst;
			}
		});

		return resultList;
	}

	public List getSubFacNameList(String profileId) throws SearchDAOException {
		String queryStr = "SELECT LMT.FACILITY_NAME, LMT.LMT_ID  FROM SCI_LSP_APPR_LMTS LMT " +
		"  WHERE LMT.CMS_LIMIT_PROFILE_ID = '" +
		profileId +
		"' AND LMT.CMS_LIMIT_STATUS = 'ACTIVE'  AND LMT.LMT_TYPE_VALUE='No' AND LMT.FACILITY_NAME IS NOT NULL ORDER BY LMT.LMT_ID";

		List resultList = getJdbcTemplate().query(queryStr, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("FACILITY_NAME");
				//String lmt_id=rs.getString("LMT_ID");
				mgnrLst[1] = rs.getString("LMT_ID");
//				mgnrLst[2]=rs.getString("MAIN_FACILITY_ID");
			//mgnrLst[1] = lmt_id;
			
				return mgnrLst;
			}
		});

		return resultList;
	}

	public List getFacDetailList(String facName,final String custID) throws SearchDAOException {
		String sql = "SELECT NEW_FACILITY_CODE, NEW_FACILITY_TYPE, NEW_FACILITY_SYSTEM, LINE_NUMBER, PURPOSE, LINE_CURRENCY, AVAIL_AND_OPTION_APPLICABLE" + 
					 " FROM CMS_FACILITY_NEW_MASTER " + 
					 " WHERE STATUS='ACTIVE' and UPPER(trim(NEW_FACILITY_NAME))=UPPER(trim('"+facName.toUpperCase()+"'))";
		
		String sql1= "select RELATIONSHIP_MANAGER, CMS_APPR_OFFICER_GRADE  from SCI_LSP_LMT_PROFILE "+
					 "where CMS_CUSTOMER_ID='"+custID+"'";

		
		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setFacilityType(rs.getString("NEW_FACILITY_TYPE"));
				obLimit.setFacilitySystem(rs.getString("NEW_FACILITY_SYSTEM"));
				obLimit.setLineNo(rs.getString("LINE_NUMBER"));
				obLimit.setPurpose(rs.getString("PURPOSE"));
				obLimit.setFacilityCode(rs.getString("NEW_FACILITY_CODE"));
				obLimit.setCurrencyCode(rs.getString("LINE_CURRENCY"));
				String riskType = rs.getString("AVAIL_AND_OPTION_APPLICABLE");
				if(riskType!=null)
					obLimit.setRiskType(riskType.trim());
				//String mainId = getMainID(custID);
				//obLimit.setFacilitySystemID(getSystemID(rs.getString("NEW_FACILITY_SYSTEM"),mainId ));

				return obLimit;
			}
		});
		
		List list = getJdbcTemplate().query(sql1, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setRelationShipManager(rs.getString("RELATIONSHIP_MANAGER"));
				obLimit.setGrade(rs.getString("CMS_APPR_OFFICER_GRADE"));
				return obLimit;
			}
		});
		
		resultList.addAll(list);

		return resultList;
	}

	public String getMainID(String custID) throws SearchDAOException {
		String sql = "SELECT CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE CMS_LE_SUB_PROFILE_ID='"+custID+"'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CMS_LE_MAIN_PROFILE_ID");
			}
		});
		if(resultList.size()==0){
			resultList.add("");
		}
		return resultList.get(0).toString();
	}
	
	public String getIdfromParty(String custID) throws SearchDAOException {
		String sql = "SELECT CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE lsp_le_id='"+custID+"'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CMS_LE_MAIN_PROFILE_ID");
			}
		});
		if(resultList.size()==0){
			resultList.add("");
		}
		return resultList.get(0).toString();
	}
	
	

	public List getSystemID(String system, String custID) throws SearchDAOException {
		String mainId = getMainID(custID);
		String sql = "SELECT CMS_LE_OTHER_SYS_CUST_ID FROM SCI_LE_OTHER_SYSTEM WHERE CMS_LE_MAIN_PROFILE_ID='"+mainId+"' AND " +
		"CMS_LE_SYSTEM_NAME='"+system+"'";

		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CMS_LE_OTHER_SYS_CUST_ID");
			}
		});

		return resultList;
	}
	
	public List getVendorDtls(String custID) throws SearchDAOException {
		String sql = "SELECT CMS_LE_VENDOR_NAME FROM SCI_LE_VENDOR_DETAILS vendor,"
				+ " SCI_LE_SUB_PROFILE sub_profile"
				+"	WHERE vendor.CMS_LE_MAIN_PROFILE_ID=sub_profile.CMS_LE_MAIN_PROFILE_ID "
				+"	and sub_profile.CMS_LE_SUB_PROFILE_ID =?";

		List arrList = getJdbcTemplate().query(sql,
				new Object[] { new Long(custID) }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CMS_LE_VENDOR_NAME");
			}
		});
		return arrList;
	}

	/*public List getCoBorrowerList(String custID) throws SearchDAOException {
		String sql = "SELECT CO_BORROWER_LIAB_ID FROM SCI_LE_CO_BORROWER coBorrower, "
				+ " SCI_LE_SUB_PROFILE sub_profile"
				+"	WHERE coBorrower.CMS_LE_MAIN_PROFILE_ID=sub_profile.CMS_LE_MAIN_PROFILE_ID "
				+"	and sub_profile.CMS_LE_SUB_PROFILE_ID =?";
				

		List arrList = getJdbcTemplate().query(sql,
				new Object[] { new Long(custID) }, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("CO_BORROWER_LIAB_ID");
			}
		});
		return arrList;
	}*/
	
	public List getRestrictedCoBorrowerForLine(long LmtId) throws SearchDAOException {
		System.out.println("getRestrictedCoBorrowerForLine() LmtId=="+LmtId);
	    String query="";
		 query = "select  CO_BORROWER_ID, CO_BORROWER_NAME from STAGE_SCI_LINE_COBORROWER  where SCI_LSP_SYS_XREF_ID = ?";
		 
		 List resultList = getJdbcTemplate().query(query, new Object[]{LmtId}, new RowMapper() {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	OBLimitXRefCoBorrower result = new OBLimitXRefCoBorrower();
	       	 result.setCoBorrowerId(rs.getString("CO_BORROWER_ID"));
			 result.setCoBorrowerName(rs.getString("CO_BORROWER_NAME"));
			 
	         return result;
	        }
	    }
	    );
		 return resultList;
	}

	public List getSubPartyNameList(String custId) throws SearchDAOException {
		String mainId = getMainID(custId);
		/*String queryStr = "SELECT LSP_LE_ID,CMS_LE_MAIN_PROFILE_ID,LSP_SHORT_NAME FROM CMS_PARTY_GROUP WHERE LSP_LE_ID in(SELECT CMS_LE_SUBLINE_PARTY_ID FROM SCI_LE_SUBLINE " +
    			"  WHERE CMS_LE_MAIN_PROFILE_ID = '" +
    			mainId + "')";*/

		String queryStr = 	" SELECT LSP_LE_ID,LSP_SHORT_NAME,CMS_LE_SUB_PROFILE_ID "+ 
		" FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE "+
		" ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID "+ 
		" WHERE STATUS = 'ACTIVE' AND CMS_LE_SUB_PROFILE_ID in (SELECT CMS_LE_SUBLINE_PARTY_ID FROM SCI_LE_SUBLINE " +
		"  WHERE CMS_LE_MAIN_PROFILE_ID = '" +
		mainId + "')";



		List resultList = getJdbcTemplate().query(queryStr, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[3];
				mgnrLst[0] = rs.getString("LSP_LE_ID");
				mgnrLst[1] = rs.getString("LSP_SHORT_NAME");
				mgnrLst[2] = rs.getString("CMS_LE_SUB_PROFILE_ID");
				return mgnrLst;
			}
		});

		return resultList;
	}

	public boolean isCpsSecurityIdUnique(String cpsId) throws SearchDAOException {

		String queryStr = 	"select cms_lsp_appr_lmts_id,cms_collateral_id,cps_security_id from cms_limit_security_map "+
		" where cps_security_id = ? group by cms_collateral_id,cms_lsp_appr_lmts_id,cps_security_id";


		boolean flag = false;
		List resultList = getJdbcTemplate().query(queryStr, new Object[] {cpsId}, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[3];
				mgnrLst[0] = rs.getString("cms_lsp_appr_lmts_id");
				mgnrLst[1] = rs.getString("cms_collateral_id");
				mgnrLst[2] = rs.getString("cps_security_id");
				return mgnrLst;
			}
		});

		if(resultList!=null && resultList.size()>0){
			flag = true;
		}
		return flag;
	}


	public List getSubSecurityList(long profileId) throws SearchDAOException {
		List argList = new ArrayList();
		DefaultLogger.debug(this, " ReadXrefDetailCmd.java ::: value of profileId::::"+profileId+">>>>");
		StringBuffer buf = new StringBuffer("SELECT CMS_COLLATERAL_ID, SUBTYPE_NAME ");
		buf.append("FROM cms_security Where CMS_COLLATERAL_ID ");
		buf.append("in (SELECT DISTINCT cms_collateral_id FROM cms_limit_security_map map, sci_lsp_appr_lmts lmt  WHERE map.cms_lsp_appr_lmts_id = lmt.cms_lsp_appr_lmts_id ");
		buf.append(" AND lmt.cms_limit_profile_id = ? ");
		buf.append(" AND (map.update_status_ind IS NULL OR map.update_status_ind <> 'D') )");
		argList.add(new Long(profileId));
		DefaultLogger.debug(this, " ReadXrefDetailCmd.java ::: value of profileId after::::"+profileId+">>>>");

		List resultList = getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("CMS_COLLATERAL_ID");
				mgnrLst[1] = rs.getString("SUBTYPE_NAME");
				return mgnrLst;
			}
		});
		DefaultLogger.debug(this, " ReadXrefDetailCmd.java ::: value of resultList after::::"+resultList+">>>>");
		return resultList;
	}

		
	
//	
//	public List getFacilityNameByAAId(String aaid) {
//		List faciltyNameList = new ArrayList();
//		//faciltyNameList = getJdbcTemplate().queryForList(query);
//		
//		/*if(faciltyNameList.size() == 0 || faciltyNameList == null || faciltyNameList.isEmpty())
//		{	
//			faciltyNameList =  new ArrayList();	
//			return faciltyNameList;
//		}
//		else
//		{			
//		return faciltyNameList;
//		}*/
//
//		String data = "";
//		try {
//		String query = "SELECT DISTINCT FACILITY_NAME As FACILITY_NAME1,FACILITY_NAME as FACILITY_NAME2 "
//				+"FROM SCI_LSP_APPR_LMTS "
//				+"where CMS_LIMIT_PROFILE_ID ="+aaid+"AND FACILITY_NAME IS NOT NULL AND CMS_LIMIT_STATUS NOT IN 'DELETED' ";
//		
//		
//		
//		
//		/*String query="SELECT  * " + 
//				"FROM (SELECT FACILITY_NAME,CMS_LSP_APPR_LMTS_ID," + 
//				"ROW_NUMBER() OVER (PARTITION BY FACILITY_NAME ORDER BY CMS_LSP_APPR_LMTS_ID) AS RowNumber " + 
//				"FROM   SCI_LSP_APPR_LMTS " + 
//				" where CMS_LIMIT_PROFILE_ID ="+aaid+" and FACILITY_NAME is NOT null) " + 
//				" WHERE a.RowNumber = 1";*/
//		
//		System.out.println("query----------------------------------------->"+query);
//		ResultSet rs=null;
//		dbUtil=new DBUtil();
//		dbUtil.setSQL(query);
//		rs = dbUtil.executeQuery();
//		if(null!=rs){
//			while(rs.next()){
//				List resultList1 = new ArrayList();
//				data = rs.getString("FACILITY_NAME1");
//				resultList1.add(data);
//				data = rs.getString("FACILITY_NAME2");
//				resultList1.add(data);
//				faciltyNameList.add(resultList1);
//			}
//		}
//			rs.close();
//		}
//		catch (DBConnectionException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println("Exception in Fecthing Facilty NAME."+e.getMessage());
//			e.printStackTrace();
//		}
//		finally{ 
//			try {
//				dbUtil.close();
//				if(dbUtil != null) {
//					dbUtil.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return faciltyNameList;
//	}
//	

	public List getFacilityNameByAAId(String custid) throws LimitException {
		String query = "SELECT DISTINCT FACILITY_NAME As FACILITY_NAME1,FACILITY_NAME as FACILITY_NAME2 "
				+"FROM SCI_LSP_APPR_LMTS "
				+" where CMS_LIMIT_PROFILE_ID = ? AND FACILITY_NAME IS NOT NULL AND CMS_LIMIT_STATUS NOT IN 'DELETED' ";		
		
		
		
		return (List) getJdbcTemplate().query(query, new Object[]{custid}, new ResultSetExtractor() {
		
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List faciltyNameList = new ArrayList();
				String data = "";
		
			while(rs.next()){
				List resultList1 = new ArrayList();
				data = rs.getString("FACILITY_NAME1");
				resultList1.add(data);
				data = rs.getString("FACILITY_NAME2");
				resultList1.add(data);
				faciltyNameList.add(resultList1);
			}
		return faciltyNameList;
	}
		});
	}
	
	public List getLimitSummaryListByCustID(String aaId) throws LimitException {

		String queryStr = "SELECT LMT.CMS_LSP_APPR_LMTS_ID, "
			+ "LMT.LMT_ID, "
			+ "CM1.ENTRY_NAME AS LMT_SOURCE, "
			+ "LMT.FACILITY_NAME, "
			+ "LMT.FACILITY_TYPE, "
			//+ "COUNTRY1.CTR_CNTRY_NAME, "
			//+ "LMT.CMS_BKG_COUNTRY, "
			//+ "LMT.CMS_BKG_ORGANISATION, "
			+ "LMT.LMT_OUTER_LMT_ID, "
			+ "LMT.LMT_CRRNCY_ISO_CODE, "
			+ "LMT.RELEASABLE_AMOUNT, "
			+ "LMT.CMS_ACTIVATED_LIMIT, "
			+ "LMT.TOTAL_RELEASED_AMOUNT, "
			+ "LMT.CMS_OUTSTANDING_AMT, "
			+ "LMT.CMS_REQ_SEC_COVERAGE, "
			+ "(SELECT COUNT(L2.CMS_LSP_APPR_LMTS_ID) FROM SCI_LSP_APPR_LMTS L2 WHERE L2.LMT_TYPE_VALUE='INNER' AND L2.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ "L2.LMT_OUTER_LMT_ID=LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_INNER, "
			+ "(SELECT COUNT(M2.CMS_COLLATERAL_ID) FROM CMS_LIMIT_SECURITY_MAP M2 "
			+ "WHERE ( M2.UPDATE_STATUS_IND IS NULL OR M2.UPDATE_STATUS_IND <> 'D' ) AND "
			+ "M2.CMS_LSP_APPR_LMTS_ID = LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_SEC, "
			+ "(SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY "
			+ "WHERE CATEGORY_CODE = 'SEC_SOURCE' "
			+ " AND ENTRY_CODE = SS.SOURCE_ID) AS SEC_SOURCE,"
			+ "SS.SOURCE_SECURITY_ID, "
			+ "SEC.TYPE_NAME, "
			+ "SEC.SUBTYPE_NAME "
			//+ "COUNTRY2.CTR_CNTRY_NAME, "
			// + "SEC.SECURITY_ORGANISATION "
			+ "FROM SCI_LSP_APPR_LMTS LMT LEFT OUTER JOIN CMS_LIMIT_SECURITY_MAP MAP ON LMT.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID "
			+ " AND ( MAP.UPDATE_STATUS_IND IS NULL OR MAP.UPDATE_STATUS_IND <> 'D' ) LEFT OUTER JOIN CMS_SECURITY SEC ON MAP.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND SEC.STATUS = 'ACTIVE' "
			// + "LEFT OUTER JOIN SCI_COUNTRY COUNTRY2 ON SEC.SECURITY_LOCATION = COUNTRY2.CTR_CNTRY_ISO_CODE "
			+ "LEFT OUTER JOIN CMS_SECURITY_SOURCE SS ON SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND SS.STATUS <> 'DELETED', "
			+ // AND SS.SOURCE_ID = LMT.SOURCE_ID
			//"SCI_COUNTRY COUNTRY1, " 
			"COMMON_CODE_CATEGORY_ENTRY CM1 "
			+ "WHERE LMT.CMS_LIMIT_PROFILE_ID = (SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = ?) AND " 
			+ "LMT.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			//+ "LMT.CMS_BKG_COUNTRY = COUNTRY1.CTR_CNTRY_ISO_CODE AND " 
			+ "CM1.CATEGORY_CODE = '" + ICMSConstant.CATEGORY_SOURCE_SYSTEM + "' " + "ORDER BY LMT.LMT_ID";

		return (List) getJdbcTemplate().query(queryStr, new Object[]{aaId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				String prevLimitId = "";
				String prevSecId = "";
				HashSet set = new HashSet();

				while (rs.next()) {
					String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
					String securityId = rs.getString("SOURCE_SECURITY_ID");

					LimitListSummaryItemBase curSummary = null;
					if (!cmsLimitId.equals(prevLimitId)) {
						curSummary = new LimitListSummaryItemBase();
						curSummary.setCmsLimitId(cmsLimitId);
						String limitId = rs.getString("LMT_ID") + " - " + rs.getString("LMT_SOURCE");
						curSummary.setLimitId(limitId);
						String prodType = rs.getString("FACILITY_NAME");
						curSummary.setProdTypeCode(prodType);
						String facilityType = rs.getString("FACILITY_TYPE");
						curSummary.setFacilityTypeCode(facilityType);
						String lmtCountry = rs.getString("FACILITY_TYPE");
						curSummary.setLimitLoc(lmtCountry);
						// String lmtCtryCode = rs.getString("CMS_BKG_COUNTRY");
						//String lmtBkgOrg = rs.getString("CMS_BKG_ORGANISATION");
						// IBookingLocation lmtBkgLoc = new OBBookingLocation(lmtCtryCode, lmtBkgOrg);
						//curSummary.setLimitBookingLoc(lmtBkgLoc);
						String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
						if ("0".equals(outerLmtId)
								|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
							outerLmtId = null;
						}
						//curSummary.setOuterLimitId(outerLmtId);
						String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
						String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
						if (lmtApproveAmt != null) {
							curSummary.setApprovedAmount(lmtApproveAmt);
						}
						String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
						if (lmtActivateAmt != null) {
							curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
						}
						String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
						if (lmtDrawAmt != null) {
							curSummary.setDrawingAmount(lmtDrawAmt);
						}
						String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
						if (lmtOutstdAmt != null) {
							curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
						}
						String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
						if (actSeccov != null) {
							curSummary.setActualSecCoverage(actSeccov);
						}
						curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
						curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));
						limitList.add(curSummary);
						prevLimitId = cmsLimitId;
						prevSecId = "";
					} else {
						// limit id same as last added item
						curSummary = (LimitListSummaryItemBase) (limitList.get(limitList.size() - 1));
					}

					if ((securityId != null) && !set.contains(securityId)) {
						LimitListSecItemBase secItem = new LimitListSecItemBase();
						secItem.setSecurityId(securityId);
						String typeName = rs.getString("TYPE_NAME");
						secItem.setSecTypeName(typeName);
						String subtypeName = rs.getString("SUBTYPE_NAME");
						secItem.setSecSubtypeName(subtypeName);
						//String secCountry = rs.getString("CTR_CNTRY_NAME");
						// secItem.setSecLocDesc(secCountry);
						//String secOrg = rs.getString("SECURITY_ORGANISATION");
						//secItem.setSecOrgDesc(secOrg);
						curSummary.addSecItem(secItem);
						prevSecId = securityId;
						set.add(securityId);
					}
				}

				return limitList;
			}
		});
	}

	//Shiv 101011
	public List getLimitSummaryListByCustID(String aaId, String facilityId) throws LimitException {

		String queryStr = "SELECT LMT.CMS_LSP_APPR_LMTS_ID, "
			+ "LMT.LMT_ID, "
			+ "CM1.ENTRY_NAME AS LMT_SOURCE, "
			+ "LMT.FACILITY_NAME, "
			+ "LMT.FACILITY_TYPE, "
			+ "LMT.LINE_NO, "
			//+ "COUNTRY1.CTR_CNTRY_NAME, "
			//+ "LMT.CMS_BKG_COUNTRY, "
			//+ "LMT.CMS_BKG_ORGANISATION, "
			+ "LMT.LMT_OUTER_LMT_ID, "
			+ "LMT.LMT_CRRNCY_ISO_CODE, "
			+ "LMT.RELEASABLE_AMOUNT, "
			+ "LMT.CMS_ACTIVATED_LIMIT, "
			+ "LMT.TOTAL_RELEASED_AMOUNT, "
			+ "LMT.CMS_OUTSTANDING_AMT, "
			+ "LMT.CMS_REQ_SEC_COVERAGE, "
			+ "(SELECT COUNT(L2.CMS_LSP_APPR_LMTS_ID) FROM SCI_LSP_APPR_LMTS L2 WHERE L2.LMT_TYPE_VALUE='INNER' AND L2.CMS_LIMIT_STATUS = 'ACTIVE' AND CMS_LSP_APPR_LMTS_ID = ? AND "
			+ "L2.LMT_OUTER_LMT_ID=LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_INNER, "
			+ "(SELECT COUNT(M2.CMS_COLLATERAL_ID) FROM CMS_LIMIT_SECURITY_MAP M2 "
			+ "WHERE ( M2.UPDATE_STATUS_IND IS NULL OR M2.UPDATE_STATUS_IND <> 'D' ) AND "
			+ "M2.CMS_LSP_APPR_LMTS_ID = LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_SEC, "
			+ "(SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY "
			+ "WHERE CATEGORY_CODE = 'SEC_SOURCE' "
			+ " AND ENTRY_CODE = SS.SOURCE_ID) AS SEC_SOURCE,"
			+ "SS.SOURCE_SECURITY_ID, "
			+ "SEC.TYPE_NAME, "
			+ "SEC.SUBTYPE_NAME "
			//+ "COUNTRY2.CTR_CNTRY_NAME, "
			// + "SEC.SECURITY_ORGANISATION "
			+ "FROM SCI_LSP_APPR_LMTS LMT LEFT OUTER JOIN CMS_LIMIT_SECURITY_MAP MAP ON LMT.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID "
			+ " AND ( MAP.UPDATE_STATUS_IND IS NULL OR MAP.UPDATE_STATUS_IND <> 'D' ) LEFT OUTER JOIN CMS_SECURITY SEC ON MAP.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND SEC.STATUS = 'ACTIVE' "
			// + "LEFT OUTER JOIN SCI_COUNTRY COUNTRY2 ON SEC.SECURITY_LOCATION = COUNTRY2.CTR_CNTRY_ISO_CODE "
			+ "LEFT OUTER JOIN CMS_SECURITY_SOURCE SS ON SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND SS.STATUS <> 'DELETED', "
			+ // AND SS.SOURCE_ID = LMT.SOURCE_ID
			//"SCI_COUNTRY COUNTRY1, " 
			"COMMON_CODE_CATEGORY_ENTRY CM1 "
			+ "WHERE LMT.CMS_LIMIT_PROFILE_ID = (SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = ?) AND " 
			+ "LMT.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ "LMT.CMS_LSP_APPR_LMTS_ID = ? AND "
			//+ "LMT.CMS_BKG_COUNTRY = COUNTRY1.CTR_CNTRY_ISO_CODE AND " 
			+ "CM1.CATEGORY_CODE = '" + ICMSConstant.CATEGORY_SOURCE_SYSTEM + "' " + "ORDER BY LMT.LMT_ID";

		return (List) getJdbcTemplate().query(queryStr, new Object[]{facilityId, aaId,facilityId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				String prevLimitId = "";
				String prevSecId = "";
				HashSet set = new HashSet();

				while (rs.next()) {
					String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
					String securityId = rs.getString("SOURCE_SECURITY_ID");

					LimitListSummaryItemBase curSummary = null;
					if (!cmsLimitId.equals(prevLimitId)) {
						curSummary = new LimitListSummaryItemBase();
						curSummary.setCmsLimitId(cmsLimitId);
						String limitId = rs.getString("LMT_ID") + " - " + rs.getString("LMT_SOURCE");
						curSummary.setLimitId(limitId);
						String prodType = rs.getString("FACILITY_NAME");
						curSummary.setProdTypeCode(prodType);
						String facilityType = rs.getString("FACILITY_TYPE");
						curSummary.setFacilityTypeCode(facilityType);
						String lmtCountry = rs.getString("FACILITY_TYPE");
						curSummary.setLimitLoc(lmtCountry);
						String lineNo = rs.getString("LINE_NO");
						curSummary.setLineNo(lineNo);
						// String lmtCtryCode = rs.getString("CMS_BKG_COUNTRY");
						//String lmtBkgOrg = rs.getString("CMS_BKG_ORGANISATION");
						// IBookingLocation lmtBkgLoc = new OBBookingLocation(lmtCtryCode, lmtBkgOrg);
						//curSummary.setLimitBookingLoc(lmtBkgLoc);
						String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
						if ("0".equals(outerLmtId)
								|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
							outerLmtId = null;
						}
						//curSummary.setOuterLimitId(outerLmtId);
						String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
						String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
						if (lmtApproveAmt != null) {
							curSummary.setApprovedAmount(lmtApproveAmt);
						}
						String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
						if (lmtActivateAmt != null) {
							curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
						}
						String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
						if (lmtDrawAmt != null) {
							curSummary.setDrawingAmount(lmtDrawAmt);
						}
						String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
						if (lmtOutstdAmt != null) {
							curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
						}
						String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
						if (actSeccov != null) {
							curSummary.setActualSecCoverage(actSeccov);
						}
						curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
						curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));
						limitList.add(curSummary);
						prevLimitId = cmsLimitId;
						prevSecId = "";
					} else {
						// limit id same as last added item
						curSummary = (LimitListSummaryItemBase) (limitList.get(limitList.size() - 1));
					}

					if ((securityId != null) && !set.contains(securityId)) {
						LimitListSecItemBase secItem = new LimitListSecItemBase();
						secItem.setSecurityId(securityId);
						String typeName = rs.getString("TYPE_NAME");
						secItem.setSecTypeName(typeName);
						String subtypeName = rs.getString("SUBTYPE_NAME");
						secItem.setSecSubtypeName(subtypeName);
						//String secCountry = rs.getString("CTR_CNTRY_NAME");
						// secItem.setSecLocDesc(secCountry);
						//String secOrg = rs.getString("SECURITY_ORGANISATION");
						//secItem.setSecOrgDesc(secOrg);
						curSummary.addSecItem(secItem);
						prevSecId = securityId;
						set.add(securityId);
					}
				}

				return limitList;
			}
		});
	}


	//Shiv 101011
	public List getLimitTranchListByFacilityFor(String facilityId, String facilityFor) throws LimitException {

		String queryStr1 = "SELECT SERIAL_NO FROM SCI_LSP_SYS_XREF WHERE  CMS_LSP_SYS_XREF_ID IN " +
		"(SELECT CMS_LSP_SYS_XREF_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE " +
		"CMS_LSP_APPR_LMTS_ID =?)  ";

		String queryStr2 = " ";
		if(facilityFor.equalsIgnoreCase("Priority/Non priority Sector")){
			queryStr2=" AND IS_PRIORITY_SECTOR = 'Yes' ";
		}else if(facilityFor.equalsIgnoreCase("Capital Market Exposure")){
			queryStr2=" AND IS_CAPITAL_MARKET_EXPOSER = 'Yes' ";
		}else if(facilityFor.equalsIgnoreCase("Real Estate Exposure")){
			queryStr2=" AND IS_REALESTATE_EXPOSER = 'Yes' ";
		}
		String queryStr = queryStr1 + queryStr2;

		return (List) getJdbcTemplate().query(queryStr, new Object[]{facilityId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				String prevLimitId = "";
				String prevSecId = "";

				while (rs.next()) {
					limitList.add(rs.getString("SERIAL_NO"));	             
				}

				return limitList;
			}
		});
	}

	//Shiv 101011
	public String[] getLimitTranchListByCustID(String facilityId, String serialNo) throws LimitException {

		String queryStr = "SELECT ESTATE_TYPE, COMM_REAL_ESTATE_TYPE, PRIORITY_SECTOR FROM SCI_LSP_SYS_XREF WHERE  CMS_LSP_SYS_XREF_ID IN " +
		"(SELECT CMS_LSP_SYS_XREF_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE " +
		"CMS_LSP_APPR_LMTS_ID =? )  AND SERIAL_NO = ?";

		return (String[]) getJdbcTemplate().query(queryStr, new Object[]{facilityId, serialNo}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				String []limitList = new String[3];

				String prevLimitId = "";
				String prevSecId = "";

				while (rs.next()) {
					limitList[0] = rs.getString("ESTATE_TYPE");
					limitList[1] = rs.getString("COMM_REAL_ESTATE_TYPE");
					limitList[2] = rs.getString("PRIORITY_SECTOR");
				}

				return limitList;
			}
		});
	}

	//Shiv get Data for priority Sec/ Real Estate Exposer / Capital Market Exposer

	public List getLimitListByFacilityFor(String aaId, String facilityFor) throws LimitException {

		String queryStr1 = "SELECT LMT.CMS_LSP_APPR_LMTS_ID, "
			+ "LMT.LMT_ID, "
			+ "CM1.ENTRY_NAME AS LMT_SOURCE, "
			+ "LMT.FACILITY_NAME, "
			+ "LMT.FACILITY_TYPE, "
			+ "LMT.LMT_OUTER_LMT_ID, "
			+ "LMT.LMT_CRRNCY_ISO_CODE, "
			+ "LMT.RELEASABLE_AMOUNT, "
			+ "LMT.CMS_ACTIVATED_LIMIT, "
			+ "LMT.TOTAL_RELEASED_AMOUNT, "
			+ "LMT.CMS_OUTSTANDING_AMT, "
			+ "LMT.CMS_REQ_SEC_COVERAGE, "
			+ "(SELECT COUNT(L2.CMS_LSP_APPR_LMTS_ID) FROM SCI_LSP_APPR_LMTS L2 WHERE L2.LMT_TYPE_VALUE='INNER' AND L2.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ "L2.LMT_OUTER_LMT_ID=LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_INNER, "
			+ "(SELECT COUNT(M2.CMS_COLLATERAL_ID) FROM CMS_LIMIT_SECURITY_MAP M2 "
			+ "WHERE ( M2.UPDATE_STATUS_IND IS NULL OR M2.UPDATE_STATUS_IND <> 'D' ) AND "
			+ "M2.CMS_LSP_APPR_LMTS_ID = LMT.CMS_LSP_APPR_LMTS_ID) AS COUNT_SEC, "
			+ "(SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY "
			+ "WHERE CATEGORY_CODE = 'SEC_SOURCE' "
			+ " AND ENTRY_CODE = SS.SOURCE_ID) AS SEC_SOURCE,"
			+ "SS.SOURCE_SECURITY_ID, "
			+ "SEC.TYPE_NAME, "
			+ "SEC.SUBTYPE_NAME "
			+ "FROM SCI_LSP_APPR_LMTS LMT LEFT OUTER JOIN CMS_LIMIT_SECURITY_MAP MAP ON LMT.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID "
			+ " AND ( MAP.UPDATE_STATUS_IND IS NULL OR MAP.UPDATE_STATUS_IND <> 'D' ) " 
			+		"LEFT OUTER JOIN CMS_SECURITY SEC ON MAP.CMS_COLLATERAL_ID = SEC.CMS_COLLATERAL_ID AND SEC.STATUS = 'ACTIVE' "
			+ "LEFT OUTER JOIN CMS_SECURITY_SOURCE SS ON SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID AND SS.STATUS <> 'DELETED', "
			+ 
			"COMMON_CODE_CATEGORY_ENTRY CM1 "
			+ "WHERE LMT.CMS_LIMIT_PROFILE_ID = (SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE LLP_LE_ID = ?) AND " 
			+ "LMT.CMS_LIMIT_STATUS = 'ACTIVE' AND "
			+ "CM1.CATEGORY_CODE = '" + ICMSConstant.CATEGORY_SOURCE_SYSTEM + "' " 
			+ " AND LMT.CMS_LSP_APPR_LMTS_ID IN" 
			+ "(SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_LMTS_XREF_MAP WHERE CMS_LSP_SYS_XREF_ID IN (SELECT CMS_LSP_SYS_XREF_ID FROM SCI_LSP_SYS_XREF WHERE ";

		String queryStr2 = " ";
		if(facilityFor.equalsIgnoreCase("Priority/Non priority Sector")){
			queryStr2=" IS_PRIORITY_SECTOR ";
		}else if(facilityFor.equalsIgnoreCase("Capital Market Exposure")){
			queryStr2=" IS_CAPITAL_MARKET_EXPOSER ";
		}else if(facilityFor.equalsIgnoreCase("Real Estate Exposure")){
			queryStr2=" IS_REALESTATE_EXPOSER ";
		}

		String queryStr3 = " = 'Yes')) ORDER BY LMT.LMT_ID ";

		String queryStr = queryStr1 + queryStr2 + queryStr3;

		return (List) getJdbcTemplate().query(queryStr, new Object[]{aaId}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List limitList = new ArrayList();

				String prevLimitId = "";
				String prevSecId = "";
				HashSet set = new HashSet();

				while (rs.next()) {
					String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
					String securityId = rs.getString("SOURCE_SECURITY_ID");

					LimitListSummaryItemBase curSummary = null;
					if (!cmsLimitId.equals(prevLimitId)) {
						curSummary = new LimitListSummaryItemBase();
						curSummary.setCmsLimitId(cmsLimitId);
						String limitId = rs.getString("LMT_ID") + " - " + rs.getString("LMT_SOURCE");
						curSummary.setLimitId(limitId);
						String prodType = rs.getString("FACILITY_NAME");
						curSummary.setProdTypeCode(prodType);
						String facilityType = rs.getString("FACILITY_TYPE");
						curSummary.setFacilityTypeCode(facilityType);
						String lmtCountry = rs.getString("FACILITY_TYPE");
						curSummary.setLimitLoc(lmtCountry);
						// String lmtCtryCode = rs.getString("CMS_BKG_COUNTRY");
						//String lmtBkgOrg = rs.getString("CMS_BKG_ORGANISATION");
						// IBookingLocation lmtBkgLoc = new OBBookingLocation(lmtCtryCode, lmtBkgOrg);
						//curSummary.setLimitBookingLoc(lmtBkgLoc);
						String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
						if ("0".equals(outerLmtId)
								|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
							outerLmtId = null;
						}
						//curSummary.setOuterLimitId(outerLmtId);
						String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
						String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
						if (lmtApproveAmt != null) {
							curSummary.setApprovedAmount(lmtApproveAmt);
						}
						String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
						if (lmtActivateAmt != null) {
							curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
						}
						String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
						if (lmtDrawAmt != null) {
							curSummary.setDrawingAmount(lmtDrawAmt);
						}
						String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
						if (lmtOutstdAmt != null) {
							curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
						}
						String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
						if (actSeccov != null) {
							curSummary.setActualSecCoverage(actSeccov);
						}
						curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
						curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));
						limitList.add(curSummary);
						prevLimitId = cmsLimitId;
						prevSecId = "";
					} else {
						// limit id same as last added item
						curSummary = (LimitListSummaryItemBase) (limitList.get(limitList.size() - 1));
					}

					if ((securityId != null) && !set.contains(securityId)) {
						LimitListSecItemBase secItem = new LimitListSecItemBase();
						secItem.setSecurityId(securityId);
						String typeName = rs.getString("TYPE_NAME");
						secItem.setSecTypeName(typeName);
						String subtypeName = rs.getString("SUBTYPE_NAME");
						secItem.setSecSubtypeName(subtypeName);
						//String secCountry = rs.getString("CTR_CNTRY_NAME");
						// secItem.setSecLocDesc(secCountry);
						//String secOrg = rs.getString("SECURITY_ORGANISATION");
						//secItem.setSecOrgDesc(secOrg);
						curSummary.addSecItem(secItem);
						prevSecId = securityId;
						set.add(securityId);
					}
				}

				return limitList;
			}
		});
	}


	public boolean getTrxCam(String customerId) throws LimitException {
		String strStatus ="";
		List camList = new ArrayList();
		String theSql = "SELECT STATUS from TRANSACTION " + " WHERE transaction_type = 'LIMITPROFILE' "
		+ " AND customer_id = '" + customerId +"'";

		camList = getJdbcTemplate().query(theSql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("STATUS");
			}
		});
		if(camList.size() > 0){
			for(int i = 0; i < camList.size(); i++ ){
				strStatus = (String)camList.get(i);
			}
			if(strStatus.trim().equals("PENDING_CREATE")){
				return true;
			}else {
				return false;
			}

		}else{
			return false;
		}
	}


	public String getTotalAmountByFacType(String camId,String facType,String climsFacilityID) throws LimitException {
		String strStatus ="";
		List amountList = new ArrayList();
		String theSql = " select sum(cms_req_sec_coverage) from SCI_LSP_APPR_LMTS where cms_limit_status = 'ACTIVE' AND lmt_type_value='No' " ;
		DefaultLogger.debug(this, " getTotalAmountByFacType ::: value of climsFacilityID::::"+climsFacilityID+">>>>");
		
		if(climsFacilityID!=null && !climsFacilityID.isEmpty() && !"".equalsIgnoreCase(climsFacilityID) && climsFacilityID.length()>0 ){
			theSql = theSql	+" and cms_lsp_appr_lmts_id != " +climsFacilityID;
		}

		if(facType!=null && !facType.trim().isEmpty()){
			theSql = theSql  + " and UPPER(facility_type) = '"+ facType.toUpperCase() +"'";
		}
		
		theSql = theSql  + " and cms_limit_profile_id = "+camId  +" group by cms_limit_profile_id " ;

		amountList = getJdbcTemplate().query(theSql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("sum(cms_req_sec_coverage)");
			}
		});
		if(amountList.size() > 0){
			for(int i = 0; i < amountList.size(); i++ ){
				strStatus = (String)amountList.get(i);
			}
			return strStatus;

		}else{
			return null;
		}
	}




	public String getRemainingPropertyValue() throws LimitException {
//		String strStatus ="";
//		String theSql = "";
//        
//		List secList = getJdbcTemplate().query(theSql, new RowMapper() {
//
//		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//		return rs.getString("STATUS");
//		}
//		});
		return null;

	}

	// Shiv 151212
	public List getLiabilityIDList(String subProfileId) throws SearchDAOException {
		String mainId = getMainID(subProfileId);
		/*String queryStr = "SELECT LSP_LE_ID,CMS_LE_MAIN_PROFILE_ID,LSP_SHORT_NAME FROM CMS_PARTY_GROUP WHERE LSP_LE_ID in(SELECT CMS_LE_SUBLINE_PARTY_ID FROM SCI_LE_SUBLINE " +
    			"  WHERE CMS_LE_MAIN_PROFILE_ID = '" +
    			mainId + "')";*/

		String queryStr = 	" SELECT CMS_LE_SYSTEM_NAME, CMS_LE_OTHER_SYS_CUST_ID  FROM SCI_LE_OTHER_SYSTEM WHERE " +
		" CMS_LE_MAIN_PROFILE_ID = (SELECT CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE CMS_LE_SUB_PROFILE_ID='"+subProfileId+"')";



		List resultList = getJdbcTemplate().query(queryStr, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String [] mgnrLst = new String[2];
				mgnrLst[0] = rs.getString("CMS_LE_SYSTEM_NAME");
				mgnrLst[1] = rs.getString("CMS_LE_OTHER_SYS_CUST_ID");
				return mgnrLst;
			}
		});

		return resultList;
	}
	public boolean getCAMMigreted (String tableName , long key ,String matchColumn) throws Exception
	{
		// tableName = "SCI_LSP_LMT_PROFILE";
		String sql = "SELECT count(*) as count  "+
		"  FROM  "+tableName+"" +
		//   " WHERE "+matchColumn+" = "+key  ;
		" WHERE "+matchColumn+" = "+key+" and ISMIGRATED = 'Y'"  ;

		String value = null;
		StringBuffer strBuffer = new StringBuffer(sql.trim());
		try
		{
			dbUtil = new DBUtil();
			//println(strBuffer.toString());
			try {
				dbUtil.setSQL(strBuffer.toString());

			}
			catch (SQLException e) {
				throw new SearchDAOException("Could not set SQL query statement", e);
			}
			ResultSet rs = dbUtil.executeQuery();
			long  count = 0;
			//return count;
			while (rs.next()) {					
				count = Long.parseLong(rs.getString("count"));						
			}

			if(count>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			throw new UserException("failed to retrieve MIGRATED cam ", e);
		}
		finally{
			dbUtil.close();
		}
	}
	
	public String getFacilityName(String lmtId) throws SearchDAOException {
		
		String queryStr = "SELECT LMT.FACILITY_NAME FROM SCI_LSP_APPR_LMTS LMT " +
		"  WHERE LMT.LMT_ID = ? AND LMT.CMS_LIMIT_STATUS = 'ACTIVE' ";
		
		DBUtil curUtil = null;
		ResultSet rs = null;
		try {
			String facilityName = "";
			curUtil = new DBUtil();
			curUtil.setSQL(queryStr);
			curUtil.setString(1, lmtId);
			rs = curUtil.executeQuery();
			if (rs.next()) {
				facilityName = rs.getString("FACILITY_NAME");
			}
			return facilityName;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		finally{
			try {
				curUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public BigDecimal getSanctionedAmount(String lmtId)throws SearchDAOException {
		
		String queryStr = "SELECT LMT.CMS_REQ_SEC_COVERAGE FROM SCI_LSP_APPR_LMTS LMT " +
		"  WHERE LMT.LMT_ID = ? AND LMT.CMS_LIMIT_STATUS = 'ACTIVE' ";
		
		DBUtil curUtil = null;
		ResultSet rs = null;
		try {
			BigDecimal sanctionAmount = new BigDecimal("0");
			curUtil = new DBUtil();
			curUtil.setSQL(queryStr);
			curUtil.setString(1, lmtId);
			rs = curUtil.executeQuery();
			if (rs.next()) {
			sanctionAmount = rs.getBigDecimal("CMS_REQ_SEC_COVERAGE");
			}
			return sanctionAmount;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		finally{
			try {
				curUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List getReleasedAmountOfSubLimit(String mainFacilityId,String lmtId)throws SearchDAOException {
		String queryStr = "";
		if(lmtId!=null && !lmtId.isEmpty()){
			queryStr="SELECT LMT.TOTAL_RELEASED_AMOUNT FROM SCI_LSP_APPR_LMTS LMT " +
		"  WHERE LMT.MAIN_FACILITY_ID = ? AND LMT_ID <> ? AND LMT.lmt_type_value='Yes' AND LMT.CMS_LIMIT_STATUS = 'ACTIVE' ";
		}
		else{
			queryStr="SELECT LMT.TOTAL_RELEASED_AMOUNT FROM SCI_LSP_APPR_LMTS LMT " +
			"  WHERE LMT.MAIN_FACILITY_ID = ? AND LMT.CMS_LIMIT_STATUS = 'ACTIVE' ";	
		}
		DBUtil curUtil = null;
		ResultSet rs = null;
		List<BigDecimal> releasedAmount=new ArrayList<BigDecimal>();
		try {
			//BigDecimal releasedAmount = new BigDecimal("0");
			curUtil = new DBUtil();
			curUtil.setSQL(queryStr);
			curUtil.setString(1, mainFacilityId);
			if(lmtId!=null && !lmtId.isEmpty()){
			curUtil.setString(2, lmtId);
			}
			rs = curUtil.executeQuery();
	
			while(rs.next()) {
				if(rs.getBigDecimal("TOTAL_RELEASED_AMOUNT")!=null){
				releasedAmount.add(rs.getBigDecimal("TOTAL_RELEASED_AMOUNT"));
				}
				else{
					releasedAmount.add(new BigDecimal("0"));
				}
			}
			return releasedAmount;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		finally{
			try {
				curUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List getSanctionedAndReleasedAmountOfMainLimit(String lmtId)throws SearchDAOException {
		
		
		String queryStr = "SELECT LMT.CMS_REQ_SEC_COVERAGE ,LMT.TOTAL_RELEASED_AMOUNT FROM SCI_LSP_APPR_LMTS LMT " +
		"  WHERE LMT.LMT_ID = ? AND LMT.CMS_LIMIT_STATUS = 'ACTIVE' ";
		
		DBUtil curUtil = null;
		ResultSet rs = null;
		List<BigDecimal> sanctionedAndReleasedAmount= new ArrayList<BigDecimal>();
		try {
			BigDecimal sanctionAmount = new BigDecimal("0");
			curUtil = new DBUtil();
			curUtil.setSQL(queryStr);
			curUtil.setString(1, lmtId);
			rs = curUtil.executeQuery();
			if (rs.next()) {
				if(rs.getBigDecimal("CMS_REQ_SEC_COVERAGE")!=null){
				sanctionedAndReleasedAmount.add(rs.getBigDecimal("CMS_REQ_SEC_COVERAGE"));
				}
				else{
					sanctionedAndReleasedAmount.add(new BigDecimal("0"));	
				}
				if(rs.getBigDecimal("TOTAL_RELEASED_AMOUNT")!=null){
				sanctionedAndReleasedAmount.add(rs.getBigDecimal("TOTAL_RELEASED_AMOUNT"));
				}
				else{
					sanctionedAndReleasedAmount.add(new BigDecimal("0"));
				}
			}
			return sanctionedAndReleasedAmount;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		finally{
			try {
				curUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateSanctionedLimitToZero(String camId){
		
		try{
			DefaultLogger.debug(this, "camId:::"+camId);
			StringBuffer buf = new StringBuffer();
			buf.append(" update sci_lsp_appr_lmts set CMS_REQ_SEC_COVERAGE = 0 " +
					" where cms_limit_profile_id = ? and (IS_FROM_CAMONLINE_REQ = 'N' OR IS_FROM_CAMONLINE_REQ is NULL) and cms_limit_status = ? ");
			
			DefaultLogger.debug(this, "Update Query:::"+buf.toString());
			
			getJdbcTemplate().update(buf.toString(),
					new Object[]{Long.parseLong(camId),"ACTIVE"});
			
		}catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "error encountered in updateSanctionedLimitToZero() method:  "+e);
		}
	}	

  //Start:Uma Khot:Don't Delete the facility if facility doc pending in case creation.
	public boolean getActualCaseCreationDetails(String apprLmtId){
		
		int count=0;
		String query="select count(1) as count from cms_casecreation_item where checklistitemid in ( select doc_item_id from cms_checklist_item where "+
		//" checklist_id in (select checklist_id from cms_checklist where cms_collateral_id='"+apprLmtId+"'" +" and category='F'"+")) and status not in ('3','5')";
				" checklist_id in (select checklist_id from cms_checklist where cms_collateral_id='"+apprLmtId+"'" +" and category='F'"+") AND STATUS != 'WAIVED' AND is_deleted='N' ) and status not in ('7','5')";
		try
	     {
		dbUtil = new DBUtil();
		dbUtil.setSQL(query);
		ResultSet rs = dbUtil.executeQuery();
		
		while(null!=rs && rs.next()){
			 count = Integer.parseInt(rs.getString("count"));
		}
		 if(count >0){
			 return true;
		 }
		 else {
			 return false;
		 }
	     }
	     
		catch(Exception e){
			DefaultLogger.debug(this, "Exception in method getActualCaseCreationDetails():"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			 try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	
	}
	
	public boolean getStageCaseCreationDetails(String apprLmtId) {
		int count=0;
		String query="select count(1) as count from transaction where transaction_type='CASECREATION' and reference_id is null and status!='ACTIVE' and "+
		" staging_reference_id in (select casecreationid from cms_stage_casecreation_item where checklistitemid in ( "+
		" select doc_item_id from cms_checklist_item where checklist_id in ( "+
		" select checklist_id from cms_checklist where cms_collateral_id='"+apprLmtId +"' and category='F')))";
		try
	     {
		dbUtil = new DBUtil();
		dbUtil.setSQL(query);
		ResultSet rs = dbUtil.executeQuery();
		
		while(null!=rs && rs.next()){
			 count = Integer.parseInt(rs.getString("count"));
		}
		 if(count >0){
			 return true;
		 }
		 else {
			 return false;
		 }
	     }
	     
		catch(Exception e){
			DefaultLogger.debug(this, "Exception in method getStageCaseCreationDetails():"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			 try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//End:Uma Khot:Don't Delete the facility if facility doc pending in case creation.
	
	//Start:Uma Khot:Don't Delete the facility if facility doc pending in facility checklist.
	public boolean getPendingFacilityDocCount(String apprLmtId) {
		int count=0;
		String query="select count(1) as count from transaction where transaction_type='CHECKLIST' and transaction_subtype='FAC_CHECKLIST_REC'"+
			 " and status!='ACTIVE' and reference_id in (select checklist_id from cms_checklist where cms_collateral_id='"+apprLmtId +"' and category='F')";
		try
	     {
		dbUtil = new DBUtil();
		dbUtil.setSQL(query);
		ResultSet rs = dbUtil.executeQuery();
		
		while(null!=rs && rs.next()){
			 count = Integer.parseInt(rs.getString("count"));
		}
		 if(count >0){
			 return true;
		 }
		 else {
			 return false;
		 }
	     }
	     
		catch(Exception e){
			DefaultLogger.debug(this, "Exception in method getPendingFacilityDocCount():"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			 try {
				dbUtil.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	//End:Uma Khot:Don't Delete the facility if facility doc pending in facility checklist.
	
	public List getFacNameList() throws SearchDAOException {
		
		List lbValList = new ArrayList();
		String sql = "SELECT NEW_FACILITY_CODE, NEW_FACILITY_NAME FROM CMS_FACILITY_NEW_MASTER WHERE STATUS='ACTIVE' ";
		List queryForList = getJdbcTemplate().queryForList(sql);
		
			for (int i = 0; i < queryForList.size(); i++) {
				Map  map = (Map) queryForList.get(i);
//			
				String id= map.get("NEW_FACILITY_CODE").toString();
				String val =  map.get("NEW_FACILITY_NAME").toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		 
		return lbValList;
	}
	
	//Ankit : Phase-3 CAMonline CR requirement
	public boolean checkFacilityDocumentsIsPendingReceived(String apprLmtId) throws SearchDAOException, RemoteException{
		int count=0;
		StringBuffer sb = new StringBuffer();
		String[] mappedSecArray = getListOfMappedSecuritiesWithFacility(apprLmtId);
		
		sb.append(apprLmtId);
		if(mappedSecArray!=null && mappedSecArray.length>0){
			for(String s : mappedSecArray){
				sb.append(","+ s);
			}
		}
		
		String query="select count(1) as count from transaction where transaction_type='CHECKLIST' " +
			 " and transaction_subtype in('FAC_CHECKLIST_REC','COL_CHECKLIST_REC')"+
			 " AND status <> 'ACTIVE' AND reference_id in (select checklist_id from cms_checklist where cms_collateral_id in ("+sb.toString()+") and category in ('F','S'))"+
			 " AND STAGING_REFERENCE_ID IN(SELECT CHECKLIST_ID FROM STAGE_CHECKLIST_ITEM WHERE STATUS='PENDING_RECEIVED')";
		try{
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			ResultSet rs = dbUtil.executeQuery();
			
			 while(null!=rs && rs.next()){
				 count = Integer.parseInt(rs.getString("count"));
			 }
			 
			 if(count >0){
				 return true;
			 }
			 else {
				 return false;
			 }
	     }catch(Exception e){
			DefaultLogger.debug(this, "Exception in method getPendingFacilityDocCount():"+e.getMessage());
			e.printStackTrace();
		 }
		 finally{
			 try {
				dbUtil.close();
			 }catch (SQLException e) {
				e.printStackTrace();
			 }
		 }
		 return false;
	}
	
	public String[] getListOfMappedSecuritiesWithFacility(String apprLmtId) throws SearchDAOException, RemoteException {
		
		try{
			String selectSQL = "select distinct (cms_collateral_id) from cms_limit_security_map where "+
			" CMS_LSP_APPR_LMTS_ID= ? and update_status_ind='I'";
	
			List listOfSecMappedWithFac = getJdbcTemplate().query(selectSQL, new Object[]{apprLmtId}, new RowMapper() {
	
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("cms_collateral_id");
				}
			});

			return (String[]) listOfSecMappedWithFac.toArray(new String[0]);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkFacilityDocumentsIsReceived(String apprLmtId) throws SearchDAOException, RemoteException{
		int count=0;
		StringBuffer sb = new StringBuffer();
		String[] mappedSecArray = getListOfMappedSecuritiesWithFacility(apprLmtId);
		
		sb.append(apprLmtId);
		if(mappedSecArray!=null && mappedSecArray.length>0){
			for(String s : mappedSecArray){
				sb.append(","+ s);
			}
		}
		
		String query="select count(1) as count from cms_checklist_item where status='RECEIVED'" +
			 " and checklist_id in(select checklist_id from cms_checklist where cms_collateral_id in("+sb.toString()+") "+
			 " and category in('S','F'))";
		
		try{
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			ResultSet rs = dbUtil.executeQuery();
			
			 while(null!=rs && rs.next()){
				 count = Integer.parseInt(rs.getString("count"));
			 }
			 
			 if(count >0){
				 return true;
			 }
			 else {
				 return false;
			 }
	     }catch(Exception e){
			DefaultLogger.debug(this, "Exception in method checkFacilityDocumentsIsReceived():"+e.getMessage());
			e.printStackTrace();
		 }
		 finally{
			 try {
				dbUtil.close();
			 }catch (SQLException e) {
				e.printStackTrace();
			 }
		 }
		 return false;
	}

	//Insurance Deff CR
	public boolean getPendingPropertySecCount(List<String> collteralIds) {
		int count=0;
		
		String query="select count(1) from transaction where reference_id in (:collteralIds) and transaction_type='COL' and status <> 'ACTIVE'";
		try
	     {
			NamedParameterJdbcTemplate namedJdbcTemplate=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
			
			Map<String, List<String>> parameter=new HashMap<String, List<String>>();
			parameter.put("collteralIds", collteralIds);
			
			count=namedJdbcTemplate.queryForInt(query, parameter);
		
		 if(count >0){
			 return true;
		 }
		 else {
			 return false;
		 }
	     }
	     
		catch(Exception e){
			DefaultLogger.debug(this, "Exception in method getPendingPropertySecCount():"+e.getMessage());
			e.printStackTrace();
		}
	
		return false;
	}

	public boolean getLimitSecurityCount(long limitProfileId, String collteralId,long limitID) {
		int count=0;
		
		String query="SELECT count(1) as count FROM SCI_LSP_APPR_LMTS WHERE  CMS_LIMIT_STATUS='ACTIVE' AND  LMT_ID not in ('"+limitID+"') AND CMS_LIMIT_PROFILE_ID ='"+limitProfileId +
		"' AND CMS_LSP_APPR_LMTS_ID IN (select CMS_LSP_APPR_LMTS_ID from CMS_LIMIT_SECURITY_MAP where UPDATE_STATUS_IND='I' and CMS_COLLATERAL_ID in ('"+
		collteralId+"'))";
		try
	     {

			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			ResultSet rs = dbUtil.executeQuery();
			
			while(null!=rs && rs.next()){
				 count = Integer.parseInt(rs.getString("count"));
			}
			 if(count >0){
				 return true;
			 }
			 else {
				 return false;
			 }
		     
	     }
	   catch(Exception e){
			DefaultLogger.debug(this, "Exception in method getLimitSecurityCount():"+e.getMessage());
			e.printStackTrace();
		}
	   finally{
		 try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   return false;
	}
	
	
	
	public OBCustomerSysXRef[] getLimitProfileforFCUBSFile() throws LimitException {

		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem = bundle.getString("ubs.systemName");
		String batchSize = bundle.getString("fcubs.batch.size");
		String fcubsSystemNewLogicFlag = bundle.getString("fcubs.intraday.stp.oldandnew.flag");
		DefaultLogger.debug(this, "In the getLimitProfileforFCUBSFile() >>>fcubsSystem:"+fcubsSystem+" >>>ubsSystem:"+ubsSystem+" >>>batchSize:"+batchSize );
		System.out.println(".............4.......fcubsSystemNewLogicFlag=>"+fcubsSystemNewLogicFlag);
		String selectSQL = "";
		if("true".equalsIgnoreCase(fcubsSystemNewLogicFlag)) {
			selectSQL = "SELECT * " + 
					"FROM " + 
					"  (SELECT b.CMS_LSP_SYS_XREF_ID, " + 
					"    b.ACTION, " + 
					"    b.SOURCE_REF_NO, " + 
					"    b.LIAB_BRANCH, " + 
					"    b.FACILITY_SYSTEM_ID, " + 
					"    b.LINE_NO, " + 
					"    b.SERIAL_NO, " + 
					"    b.MAIN_LINE_CODE, " + 
					"    b.CURRENCY, " + 
					"    b.RULE_ID, " + 
					"    b.CURRENCY_RESTRICTION, " + 
					"    b.REVOLVING_LINE, " + 
					"    b.AVAILABLE, " + 
					"    b.FREEZE, " + 
					"    ( " + 
					"    CASE " + 
					"      WHEN FAC.IDL_APPLICABLE = 'Yes' " + 
					"      THEN " + 
					"        CASE " + 
					"          WHEN b.IDL_AMOUNT = 0 " + 
					"          THEN b.IDL_EFFECTIVE_FROM_DATE " + 
					"          ELSE " + 
					"            CASE " + 
					"              WHEN TRUNC(b.IDL_EFFECTIVE_FROM_DATE) = TRUNC(sysdate) " + 
					"              THEN b.IDL_EFFECTIVE_FROM_DATE " + 
					"             WHEN TRUNC(b.IDL_EFFECTIVE_FROM_DATE) < TRUNC(sysdate) " + 
					"              THEN b.LIMIT_START_DATE " + 
					"              WHEN b.IDL_EFFECTIVE_FROM_DATE IS NULL " + 
					"              THEN b.LIMIT_START_DATE " + 
					"            END " + 
					"        END " + 
					"      ELSE b.LIMIT_START_DATE " + 
					"    END ) AS LIMIT_START_DATE, " + 
					"    ( " + 
					"    CASE " + 
					"      WHEN FAC.IDL_APPLICABLE = 'Yes' " + 
					"      THEN " + 
					"        CASE " + 
					"          WHEN b.IDL_AMOUNT = 0 " + 
					"          THEN b.IDL_EXPIRY_DATE " + 
					"          ELSE " + 
					"            CASE " + 
					"              WHEN TRUNC(b.IDL_EFFECTIVE_FROM_DATE) = TRUNC(sysdate) " + 
					"              THEN b.IDL_EXPIRY_DATE " + 
					"              WHEN TRUNC(b.IDL_EFFECTIVE_FROM_DATE) < TRUNC(sysdate) " + 
					"              THEN b.DATE_OF_RESET " + 
					"              WHEN b.IDL_EFFECTIVE_FROM_DATE IS NULL " + 
					"              THEN b.DATE_OF_RESET " + 
					"            END " + 
					"        END " + 
					"      ELSE b.DATE_OF_RESET " + 
					"    END ) AS DATE_OF_RESET, " + 
					"    b.LAST_AVAILABLE_DATE, " + 
					"    ( " + 
					"    CASE " + 
					"      WHEN FAC.IDL_APPLICABLE = 'Yes' " + 
					"      THEN " + 
					"        CASE " + 
					"          WHEN b.IDL_AMOUNT = 0 " + 
					"          THEN TO_CHAR(b.IDL_AMOUNT) " + 
					"          ELSE " + 
					"            CASE " + 
					"              WHEN TRUNC(b.IDL_EFFECTIVE_FROM_DATE) = TRUNC(sysdate) " + 
					"              THEN TO_CHAR(b.IDL_AMOUNT) " + 
					"              WHEN TRUNC(b.IDL_EFFECTIVE_FROM_DATE) < TRUNC(sysdate) " + 
					"              THEN TO_CHAR( b.RELEASED_AMOUNT) " + 
					"              WHEN b.IDL_EFFECTIVE_FROM_DATE IS NULL " + 
					"              THEN TO_CHAR( b.RELEASED_AMOUNT) " + 
					"            END " + 
					"        END " + 
					"      ELSE TO_CHAR( b.RELEASED_AMOUNT) " + 
					"    END ) AS RELEASED_AMOUNT, " + 
					"    b.INTERNAL_REMARKS, " + 
					"    b.LIMIT_TENOR_DAYS, " + 
					"    b.BRANCH_ALLOWED, " + 
					"    b.PRODUCT_ALLOWED, " + 
					"    b.CURRENCY_ALLOWED, " + 
					"    b.SEGMENT_1, " + 
					"    b.ESTATE_TYPE, " + 
					"    b.UNCONDI_CANCL, " + 
					"    b.IS_CAPITAL_MARKET_EXPOSER, " + 
					"    b.IS_REALESTATE_EXPOSER, " + 
					"    b.PRIORITY_SECTOR, " + 
					"    b.BRANCH_ALLOWED_FLAG, " + 
					"    b.PRODUCT_ALLOWED_FLAG, " + 
					"    b.CURRENCY_ALLOWED_FLAG, " + 
					"    b.LIMIT_RESTRICTION_FLAG, " + 
					"    b.IS_CAPITAL_MARKET_EXPOSER_FLAG, " + 
					"    b.SEGMENT_1_FLAG, " + 
					"    b.ESTATE_TYPE_FLAG, " + 
					"    b.PRIORITY_SECTOR_FLAG, " + 
					"    b.UNCONDI_CANCL_FLAG, " + 
					"    b.UDF_ALLOWED, " + 
					"    b.UDF_DELETE, " + 
					"    b.COMM_REAL_ESTATE_TYPE_FLAG, " + 
					"    b.COMM_REAL_ESTATE_TYPE, " + 
					"    b.INTRADAY_LIMIT_EXPIRY_DATE, " + 
					"    b.DAY_LIGHT_LIMIT, " + 
					"    b.INTRADAY_LIMIT_FLAG, " + 
					"    b.ISDAYLIGHTLIMITAVL, " + 
					"    a.PROJECT_FINANCE , " + 
					"    a.PROJECT_LOAN, " + 
					"    a.INFRA_FLAG, " + 
					"    TO_CHAR(a.SCOD,'MM/dd/yyyy')             AS SCOD, " + 
					"    TO_CHAR(a.ESCOD_L1,'MM/dd/yyyy')         AS ESCOD_L1, " + 
					"    TO_CHAR(a.ESCOD_L2,'MM/dd/yyyy')         AS ESCOD_L2, " + 
					"    TO_CHAR(a.ESCOD_L3,'MM/dd/yyyy')         AS ESCOD_L3, " + 
					"    TO_CHAR(a.REVISED_ESCOD_L1,'MM/dd/yyyy') AS REVISED_ESCOD_L1, " + 
					"    TO_CHAR(a.REVISED_ESCOD_L2,'MM/dd/yyyy') AS REVISED_ESCOD_L2, " + 
					"    TO_CHAR(a.REVISED_ESCOD_L3,'MM/dd/yyyy') AS REVISED_ESCOD_L3, " + 
					"    TO_CHAR(a.ACOD,'MM/dd/yyyy')             AS ACOD, " + 
					"    a.DELAY_LEVEL, " + 
					"    a.EXE_ASSET_CLASS, " + 
					"    a.REV_ASSET_CLASS, " + 
					"    a.REV_ASSET_CLASS_L1, " + 
					"    a.REV_ASSET_CLASS_L2, " + 
					"    a.REV_ASSET_CLASS_L3, " + 
					"    TO_CHAR(a.REV_ASSET_CLASS_DT,'MM/dd/yyyy')    AS REV_ASSET_CLASS_DT, " + 
					"    TO_CHAR(a.REV_ASSET_CLASS_DT_L1,'MM/dd/yyyy') AS REV_ASSET_CLASS_DT_L1, " + 
					"    TO_CHAR(a.REV_ASSET_CLASS_DT_L2,'MM/dd/yyyy') AS REV_ASSET_CLASS_DT_L2, " + 
					"    TO_CHAR(a.REV_ASSET_CLASS_DT_L3,'MM/dd/yyyy') AS REV_ASSET_CLASS_DT_L3, " + 
					"    TO_CHAR(a.EXE_ASSET_CLASS_DT,'MM/dd/yyyy')    AS EXE_ASSET_CLASS_DT, " + 
					"    a.LMT_ID, " + 
					"    b.STOCK_LIMIT_FLAG, " + 
					"    b.STOCK_DOC_MONTH_LMT, " + 
					"    b.STOCK_DOC_YEAR_LMT , " + 
					"    b.UDF_ALLOWED_2 , " + 
					"    b.UDF_DELETE_2 " + 
					"  FROM SCI_LSP_SYS_XREF b, " + 
					"    SCI_LSP_APPR_LMTS a, " + 
					"    SCI_LSP_LMTS_XREF_MAP c, " + 
					"    CMS_FACILITY_NEW_MASTER FAC " + 
					"  WHERE b.STATUS                   = ? " + 
					"  AND b.FACILITY_SYSTEM           IN  (?,?)" + 
					"  AND b.SENDTOCORE                 = 'N' " + 
					"  AND b.SENDTOFILE                 = 'Y' " + 
					"  AND c.CMS_LSP_APPR_LMTS_ID       = a.CMS_LSP_APPR_LMTS_ID " + 
					"  AND c.CMS_LSP_SYS_XREF_ID        = b.CMS_LSP_SYS_XREF_ID " + 
					"  AND (( TRUNC(b.IDL_EFFECTIVE_FROM_DATE) = TRUNC(sysdate)) " + 
					"  OR (b.IDL_EFFECTIVE_FROM_DATE   IS NULL) " + 
					"   OR ( TRUNC(b.IDL_EFFECTIVE_FROM_DATE)   < TRUNC(sysdate)) " + 
					"  OR (FAC.IDL_APPLICABLE           = 'Yes' " + 
					"  AND b.IDL_AMOUNT                 =0)) " + 
					"  AND FAC.NEW_FACILITY_CODE        = a.LMT_FAC_CODE " + 
					"  ORDER BY LMT_ID " + 
					"  ) " + 
					"WHERE ROWNUM <= ? ";
		}else {
			
		selectSQL = "SELECT * FROM ( "
			+ "SELECT b.CMS_LSP_SYS_XREF_ID,b.ACTION,b.SOURCE_REF_NO, "
			+ "b.LIAB_BRANCH, "
			+ "b.FACILITY_SYSTEM_ID, "
			+ "b.LINE_NO, "
			+ "b.SERIAL_NO, "
			+ "b.MAIN_LINE_CODE, "
			+ "b.CURRENCY, "
			+ "b.RULE_ID, "
			+ "b.CURRENCY_RESTRICTION, "
			+ "b.REVOLVING_LINE, "
			+ "b.AVAILABLE, "
			+ "b.FREEZE,"
			+" b.LIMIT_START_DATE, "
			+" b.DATE_OF_RESET,"
			+" b.LAST_AVAILABLE_DATE,"
			+" b.RELEASED_AMOUNT,"
			+" b.INTERNAL_REMARKS,"
			+" b.LIMIT_TENOR_DAYS,"
			+" b.BRANCH_ALLOWED,"
			+" b.PRODUCT_ALLOWED,"
			+" b.CURRENCY_ALLOWED,"
			+" b.SEGMENT_1,"
			+" b.ESTATE_TYPE,"
			+" b.UNCONDI_CANCL,"
			+" b.IS_CAPITAL_MARKET_EXPOSER,"
			+" b.IS_REALESTATE_EXPOSER,"
			+" b.PRIORITY_SECTOR,"
			+"b.BRANCH_ALLOWED_FLAG,"
			+"b.PRODUCT_ALLOWED_FLAG,"
			+"b.CURRENCY_ALLOWED_FLAG,"
			+"b.LIMIT_RESTRICTION_FLAG,"
			+"b.IS_CAPITAL_MARKET_EXPOSER_FLAG,"
			+"b.SEGMENT_1_FLAG,"
			+"b.ESTATE_TYPE_FLAG,"
			+"b.PRIORITY_SECTOR_FLAG,"
			+"b.UNCONDI_CANCL_FLAG,"
			+"b.UDF_ALLOWED,"
			+"b.UDF_DELETE,"
			+"b.COMM_REAL_ESTATE_TYPE_FLAG,"
			+"b.COMM_REAL_ESTATE_TYPE,"
			+"b.INTRADAY_LIMIT_EXPIRY_DATE,"
			+"b.DAY_LIGHT_LIMIT,"
			+"b.INTRADAY_LIMIT_FLAG, "
			+"b.ISDAYLIGHTLIMITAVL,"
			+"  a.PROJECT_FINANCE ,"
			+"   a.PROJECT_LOAN,"
		    +"   a.INFRA_FLAG,"
		    +"   TO_CHAR(a.SCOD,'MM/dd/yyyy') as SCOD,"
		    +"   TO_CHAR(a.ESCOD_L1,'MM/dd/yyyy') as ESCOD_L1,"
		    +"   TO_CHAR(a.ESCOD_L2,'MM/dd/yyyy') as ESCOD_L2,"
		    +"   TO_CHAR(a.ESCOD_L3,'MM/dd/yyyy') as ESCOD_L3,"
		    +"   TO_CHAR(a.REVISED_ESCOD_L1,'MM/dd/yyyy') as REVISED_ESCOD_L1,"
		    +"   TO_CHAR(a.REVISED_ESCOD_L2,'MM/dd/yyyy') as REVISED_ESCOD_L2,"
		    +"   TO_CHAR(a.REVISED_ESCOD_L3,'MM/dd/yyyy') as REVISED_ESCOD_L3,"
		    +"   TO_CHAR(a.ACOD,'MM/dd/yyyy') as ACOD,"
		    +"   a.DELAY_LEVEL,"
		    +"   a.EXE_ASSET_CLASS,"
		    +"   a.REV_ASSET_CLASS,"
		    +"   a.REV_ASSET_CLASS_L1,"
		    +"   a.REV_ASSET_CLASS_L2,"
		    +"   a.REV_ASSET_CLASS_L3,"
		    +"   TO_CHAR(a.REV_ASSET_CLASS_DT,'MM/dd/yyyy') as REV_ASSET_CLASS_DT,"
		    +"   TO_CHAR(a.REV_ASSET_CLASS_DT_L1,'MM/dd/yyyy') as REV_ASSET_CLASS_DT_L1,"
		    +"   TO_CHAR(a.REV_ASSET_CLASS_DT_L2,'MM/dd/yyyy') as REV_ASSET_CLASS_DT_L2,"
		    +"   TO_CHAR(a.REV_ASSET_CLASS_DT_L3,'MM/dd/yyyy') as  REV_ASSET_CLASS_DT_L3,"
		    +"   TO_CHAR(a.EXE_ASSET_CLASS_DT,'MM/dd/yyyy') as  EXE_ASSET_CLASS_DT, "
		    +"   a.LMT_ID, "
		    +"   b.STOCK_LIMIT_FLAG, "
		    +"   b.STOCK_DOC_MONTH_LMT, "
		    +"   b.STOCK_DOC_YEAR_LMT ,"
		    +"   b.UDF_ALLOWED_2 ,"
		    +"   b.UDF_DELETE_2 "
		    
			+" FROM SCI_LSP_SYS_XREF  b,SCI_LSP_APPR_LMTS  a,SCI_LSP_LMTS_XREF_MAP c"
			+" WHERE b.STATUS = ? AND"
			+" b.FACILITY_SYSTEM IN (?,?)"
//			+" AND (b.scheduler_status is null or b.scheduler_status = 'COMPLETED')"
			+" AND b.SENDTOCORE = 'N'"
			+" AND b.SENDTOFILE = 'Y'"
//			+" AND  CMS_LSP_SYS_XREF_ID IN ('20180312000002072')"
			+" AND c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID"
		    +"  AND c.CMS_LSP_SYS_XREF_ID = b.CMS_LSP_SYS_XREF_ID"
//			+" ORDER BY SOURCE_REF_NO ";
		    +" ORDER BY LMT_ID "
		    +" )WHERE ROWNUM <= ? ";
		}
		System.out.println("OBCustomerSysXRef[] getLimitProfileforFCUBSFile() =>FCUBS Query=>"+selectSQL);
		List<Object> param = new ArrayList<Object>();
		param.add(ICMSConstant.FCUBS_STATUS_PENDING);
		param.add(fcubsSystem);
		param.add(ubsSystem);
		param.add(batchSize);
		

//ADDED STOCK_DOC_MONTH AND STOCK_DOC_YEAR TWO NEW FIELDS  FOR NPA STOCK FCUBS
		
		/*String selectSQL = " SELECT  " + 
				"  b.CMS_LSP_SYS_XREF_ID, " + 
				"  b.ACTION, " + 
				"  b.SOURCE_REF_NO, " + 
				"  b.LIAB_BRANCH, " + 
				"  b.FACILITY_SYSTEM_ID, " + 
				"  b.LINE_NO, " + 
				"  b.SERIAL_NO, " + 
				"  b.MAIN_LINE_CODE, " + 
				"  b.CURRENCY, " + 
				"  b.RULE_ID, " + 
				"  b.CURRENCY_RESTRICTION, " + 
				"  b.REVOLVING_LINE, " + 
				"  b.AVAILABLE, " + 
				"  b.FREEZE, " + 
				"  b.LIMIT_START_DATE, " + 
				"  b.DATE_OF_RESET, " + 
				"  b.LAST_AVAILABLE_DATE, " + 
				"  b.RELEASED_AMOUNT, " + 
				"  b.INTERNAL_REMARKS, " + 
				"  b.LIMIT_TENOR_DAYS, " + 
				"  b.BRANCH_ALLOWED, " + 
				"  b.PRODUCT_ALLOWED, " + 
				"  b.CURRENCY_ALLOWED, " + 
				"  b.SEGMENT_1, " + 
				"  b.ESTATE_TYPE, " + 
				"  b.UNCONDI_CANCL, " + 
				"  b.IS_CAPITAL_MARKET_EXPOSER, " + 
				"  b.IS_REALESTATE_EXPOSER, " + 
				"  b.PRIORITY_SECTOR, " + 
				"  b.BRANCH_ALLOWED_FLAG, " + 
				"  b.PRODUCT_ALLOWED_FLAG, " + 
				"  b.CURRENCY_ALLOWED_FLAG, " + 
				"  b.LIMIT_RESTRICTION_FLAG, " + 
				"  b.IS_CAPITAL_MARKET_EXPOSER_FLAG, " + 
				"  b.SEGMENT_1_FLAG, " + 
				"  b.ESTATE_TYPE_FLAG, " + 
				"  b.PRIORITY_SECTOR_FLAG, " + 
				"  b.UNCONDI_CANCL_FLAG, " + 
				"  b.UDF_ALLOWED, " + 
				"  b.UDF_DELETE, " + 
				"  b.COMM_REAL_ESTATE_TYPE_FLAG, " + 
				"  b.COMM_REAL_ESTATE_TYPE, " + 
				"  b.INTRADAY_LIMIT_EXPIRY_DATE, " + 
				"  b.DAY_LIGHT_LIMIT, " + 
				"  b.INTRADAY_LIMIT_FLAG, " + 
				"  b.ISDAYLIGHTLIMITAVL, " + 
				"  a.PROJECT_FINANCE , " + 
				"  a.PROJECT_LOAN, " + 
				"  a.INFRA_FLAG, " + 
				"  TO_CHAR(a.SCOD,'MM/dd/yyyy')             AS SCOD, " + 
				"  TO_CHAR(a.ESCOD_L1,'MM/dd/yyyy')         AS ESCOD_L1, " + 
				"  TO_CHAR(a.ESCOD_L2,'MM/dd/yyyy')         AS ESCOD_L2, " + 
				"  TO_CHAR(a.ESCOD_L3,'MM/dd/yyyy')         AS ESCOD_L3, " + 
				"  TO_CHAR(a.REVISED_ESCOD_L1,'MM/dd/yyyy') AS REVISED_ESCOD_L1, " + 
				"  TO_CHAR(a.REVISED_ESCOD_L2,'MM/dd/yyyy') AS REVISED_ESCOD_L2, " + 
				"  TO_CHAR(a.REVISED_ESCOD_L3,'MM/dd/yyyy') AS REVISED_ESCOD_L3, " + 
				"  TO_CHAR(a.ACOD,'MM/dd/yyyy')             AS ACOD, " + 
				"  a.DELAY_LEVEL, " + 
				"  a.EXE_ASSET_CLASS, " + 
				"  a.REV_ASSET_CLASS, " + 
				"  a.REV_ASSET_CLASS_L1, " + 
				"  a.REV_ASSET_CLASS_L2, " + 
				"  a.REV_ASSET_CLASS_L3, " + 
				"  TO_CHAR(a.REV_ASSET_CLASS_DT,'MM/dd/yyyy')    AS REV_ASSET_CLASS_DT, " + 
				"  TO_CHAR(a.REV_ASSET_CLASS_DT_L1,'MM/dd/yyyy') AS REV_ASSET_CLASS_DT_L1, " + 
				"  TO_CHAR(a.REV_ASSET_CLASS_DT_L2,'MM/dd/yyyy') AS REV_ASSET_CLASS_DT_L2, " + 
				"  TO_CHAR(a.REV_ASSET_CLASS_DT_L3,'MM/dd/yyyy') AS REV_ASSET_CLASS_DT_L3, " + 
				"  TO_CHAR(a.EXE_ASSET_CLASS_DT,'MM/dd/yyyy')    AS EXE_ASSET_CLASS_DT, " + 
				" FindStockDocMonthAndYear(MAP.cms_collateral_id ,'M') AS STOCK_DOC_MONTH, " + 
				" FindStockDocMonthAndYear(MAP.cms_collateral_id ,'Y') AS STOCK_DOC_YEAR " + 
				"FROM SCI_LSP_SYS_XREF b, " + 
				"  SCI_LSP_APPR_LMTS a, " + 
				"  SCI_LSP_LMTS_XREF_MAP c , " + 
				"  CMS_LIMIT_SECURITY_MAP MAP " + 
				" " + 
				"WHERE " + 
				"   b.STATUS = '"+ICMSConstant.FCUBS_STATUS_PENDING+"'  " + 
				"   AND " + 
				"  b.FACILITY_SYSTEM IN ('"+fcubsSystem+"','"+ubsSystem+"') " + 
				" AND (b.scheduler_status   IS NULL " + 
				"OR b.scheduler_status      = 'COMPLETED') " + 
				" AND b.SENDTOCORE           = 'N' " + 
				" AND b.SENDTOFILE           = 'Y' " + 
				" AND " + 
				"c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID " + 
				" AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
				" " + 
				" AND a.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID " + 
				" AND MAP.CHARGE_ID   in " + 
				"               (SELECT MAX(maps2.CHARGE_ID) " + 
				"       from cms_limit_security_map maps2 " + 
				"       where maps2.cms_lsp_appr_lmts_id = a.cms_lsp_appr_lmts_id " + 
				"       AND maps2.cms_collateral_id      =MAP.cms_collateral_id " + 
				"        " + 
				"       )  " + 
				" AND (MAP.UPDATE_STATUS_IND <> 'D' OR MAP.UPDATE_STATUS_IND IS NULL) " + 
				"ORDER BY SOURCE_REF_NO ";*/

		return (OBCustomerSysXRef[]) getJdbcTemplate().query(selectSQL,param.toArray(),

				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processLimit(rs);
			}
		});
	}
	
	//Co Borrower
	public OBCustomerSysXRef[] getCoBorrowerList( final OBCustomerSysXRef obCustomerSysXRef, long xrefId) throws LimitException {
	//	System.out.println("in getCoBorrowerList() xrefId====="+xrefId);
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String fcubsSystem = bundle.getString("fcubs.systemName");
		String ubsSystem = bundle.getString("ubs.systemName");
		String selectSQL = "select CO_BORROWER_ID,CO_BORROWER_NAME from SCI_LINE_COBORROWER where SCI_LSP_SYS_XREF_ID ="+xrefId;
				
				return (OBCustomerSysXRef[]) getJdbcTemplate().query(selectSQL,
						new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						return processCoBorrower( obCustomerSysXRef,rs);
					}
				});
			}

	private OBCustomerSysXRef[] processLimit(ResultSet rs) throws SQLException {
		
		ArrayList list = new ArrayList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
		
		String norm = PropertyManager.getValue("scod.asset.classification.standard.norm");
		String pdo4 = PropertyManager.getValue("scod.asset.classification.npa.pdo4");

		while (rs.next()) {
			OBCustomerSysXRef customerSysXRef = new OBCustomerSysXRef();
			long xrefId = rs.getLong("CMS_LSP_SYS_XREF_ID");
			customerSysXRef.setXRefID(xrefId);

			String action = rs.getString("ACTION");
			
			if(null!=action && !"".equalsIgnoreCase(action))
			customerSysXRef.setAction(action);
			else
			customerSysXRef.setAction("");

			String sourceRefNo = rs.getString("SOURCE_REF_NO");
			
			if(null!=sourceRefNo && !"".equalsIgnoreCase(sourceRefNo))
			customerSysXRef.setSourceRefNo(sourceRefNo);
			else
				customerSysXRef.setSourceRefNo("");
			
			String liabBranch = rs.getString("LIAB_BRANCH");
			if(null!=liabBranch && !"".equalsIgnoreCase(liabBranch))
			customerSysXRef.setLiabBranch(liabBranch);
			else
				customerSysXRef.setLiabBranch("");
			
			
			
				String guaranteeLiabId = "";
			try {
				guaranteeLiabId = checkIsGuarantee(xrefId);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			if(null != guaranteeLiabId && !"".equalsIgnoreCase(guaranteeLiabId)){
				
				customerSysXRef.setFacilitySystemID(guaranteeLiabId);
				if((ICMSConstant.FCUBSLIMIT_ACTION_NEW).equals(customerSysXRef.getAction())){
				String liabId = rs.getString("FACILITY_SYSTEM_ID");
				if(null!=liabId && !"".equalsIgnoreCase(liabId)){
					
					String limitRestFlag = rs.getString("LIMIT_RESTRICTION_FLAG");
					if(null!=limitRestFlag && !"".equalsIgnoreCase(limitRestFlag))
						customerSysXRef.setLimitRestrictionFlag(limitRestFlag+","+liabId+"#A");
					else
						customerSysXRef.setLimitRestrictionFlag(liabId+"#A");
				}
				}else{
					String limitRestFlag = rs.getString("LIMIT_RESTRICTION_FLAG");
					if(null!=limitRestFlag && !"".equalsIgnoreCase(limitRestFlag))
					customerSysXRef.setLimitRestrictionFlag(limitRestFlag);
					else
						customerSysXRef.setLimitRestrictionFlag("");
				}
				
			}
			else {
			String liabId = rs.getString("FACILITY_SYSTEM_ID");
			if(null!=liabId && !"".equalsIgnoreCase(liabId))
			customerSysXRef.setFacilitySystemID(liabId);
			else
				customerSysXRef.setFacilitySystemID("");
			
			String limitRestFlag = rs.getString("LIMIT_RESTRICTION_FLAG");
			if(null!=limitRestFlag && !"".equalsIgnoreCase(limitRestFlag))
			customerSysXRef.setLimitRestrictionFlag(limitRestFlag);
			else
				customerSysXRef.setLimitRestrictionFlag("");
			
			}
			
			
			
			
			String lineNo = rs.getString("LINE_NO");
			if(null!=lineNo && !"".equalsIgnoreCase(lineNo))
			customerSysXRef.setLineNo(lineNo);
			else
				customerSysXRef.setLineNo("");
			
			String serialNo = rs.getString("SERIAL_NO");
			String defaultSerialno = PropertyManager.getValue("ecbf.line.web.service.default.serial.no","1000");
			
			if(null!=serialNo && !"".equalsIgnoreCase(serialNo) && !((ICMSConstant.FCUBSLIMIT_ACTION_NEW).equals(customerSysXRef.getAction()))) {
				if(serialNo.trim().equals(defaultSerialno))
					customerSysXRef.setSerialNo("1");
				else
					customerSysXRef.setSerialNo(serialNo);
			}
			else
				customerSysXRef.setSerialNo("1");
			
			String mainLineCode = rs.getString("MAIN_LINE_CODE");
			if(null!=mainLineCode && !"".equalsIgnoreCase(mainLineCode))
			customerSysXRef.setMainLineCode(mainLineCode);
			else
				customerSysXRef.setMainLineCode("");
			
			String currency = rs.getString("CURRENCY");
			if(null!=currency && !"".equalsIgnoreCase(currency))
			customerSysXRef.setCurrency(currency);
			else
				customerSysXRef.setCurrency("");
			
			String ruleId = rs.getString("RULE_ID");
			if(null!=ruleId && !"".equalsIgnoreCase(ruleId))
			customerSysXRef.setRuleId(ruleId);
			else
				customerSysXRef.setRuleId("");
			
			String currencyRest = rs.getString("CURRENCY_RESTRICTION");
			if(null!=currencyRest && !"".equalsIgnoreCase(currencyRest))
			customerSysXRef.setCurrencyRestriction(currencyRest);
			else
				customerSysXRef.setCurrencyRestriction("");
			
			String segment = rs.getString("SEGMENT_1");
			if(null!=segment && !"".equalsIgnoreCase(segment))
			customerSysXRef.setSegment(segment);
			else
				customerSysXRef.setSegment("");
			
			String revolvingLine = rs.getString("REVOLVING_LINE");
			if(null!=revolvingLine && !"".equalsIgnoreCase(revolvingLine)) {
			customerSysXRef.setRevolvingLine(revolvingLine);
			
			if(revolvingLine.equalsIgnoreCase("Yes"))
				customerSysXRef.setRevolvingLine("Y");
			
			if(revolvingLine.equalsIgnoreCase("No"))
				customerSysXRef.setRevolvingLine("N");
			}
			else
				customerSysXRef.setRevolvingLine("");
			
			String available = rs.getString("AVAILABLE");
			if(null!=available && !"".equalsIgnoreCase(available)){
			customerSysXRef.setAvailable(available);
			if(available.equalsIgnoreCase("Yes"))
				customerSysXRef.setAvailable("Y");
			
			if(available.equalsIgnoreCase("No"))
				customerSysXRef.setAvailable("N");
			}
			else
				customerSysXRef.setAvailable("");
			
			String freeze = rs.getString("FREEZE");
			if(null!=freeze && !"".equalsIgnoreCase(freeze)){
			customerSysXRef.setFreeze(freeze);
			if(freeze.equalsIgnoreCase("Yes"))
				customerSysXRef.setFreeze("Y");
			
			if(freeze.equalsIgnoreCase("No"))
				customerSysXRef.setFreeze("N");
			}
			else
				customerSysXRef.setFreeze("");
		
			Date startDate = rs.getDate("LIMIT_START_DATE");
			if(null!=startDate && !"".equals(startDate))
			customerSysXRef.setLimitStartDate(startDate);
			
			
			Date expiryDate = rs.getDate("DATE_OF_RESET");
			if(null!=expiryDate && !"".equals(expiryDate))
			customerSysXRef.setDateOfReset(expiryDate);
			
			
			Date lastAvailableDate = rs.getDate("LAST_AVAILABLE_DATE");
			if(null!=lastAvailableDate && !"".equals(lastAvailableDate))
			customerSysXRef.setLastavailableDate(lastAvailableDate);
			
			
			String releasedAmt =  rs.getString("RELEASED_AMOUNT");
			if(null!=releasedAmt && !"".equalsIgnoreCase(releasedAmt))
			customerSysXRef.setReleasedAmount(releasedAmt);
			else
				customerSysXRef.setReleasedAmount("");
			
			String internalRemarks = rs.getString("INTERNAL_REMARKS");
			if(null!=internalRemarks && !"".equalsIgnoreCase(internalRemarks))
			customerSysXRef.setInternalRemarks(internalRemarks);
			else
				customerSysXRef.setInternalRemarks("");
			
			
			String tenorDays = rs.getString("LIMIT_TENOR_DAYS");
			if(null!=tenorDays && !"".equalsIgnoreCase(tenorDays))
			customerSysXRef.setLimitTenorDays(tenorDays);
			else
				customerSysXRef.setLimitTenorDays("");
			
			
			String estateType = rs.getString("ESTATE_TYPE");
			if(null!=estateType && !"".equalsIgnoreCase(estateType)){
				customerSysXRef.setEstateType(estateType);
				
				if(estateType.equalsIgnoreCase(FCUBSFileConstants.FCUBS_COMREALESTATE)){
					
					customerSysXRef.setEstateType(FCUBSFileConstants.FCUBS_COMMERCIAL);	
				}
				
				if(estateType.equalsIgnoreCase(FCUBSFileConstants.FCUBS_RESDREALESTATE)){
					
					customerSysXRef.setEstateType(FCUBSFileConstants.FCUBS_RESIDENTIAL);	
				}

				if(estateType.equalsIgnoreCase(FCUBSFileConstants.FCUBS_INDIRECT)){
	
					customerSysXRef.setEstateType(FCUBSFileConstants.FCUBS_INDIRECTFINANCE);	
				}
				
			}
			else
				customerSysXRef.setEstateType("");
			
			
			String uncondCancl = rs.getString("UNCONDI_CANCL");
			if(null!=uncondCancl && !"".equalsIgnoreCase(uncondCancl))
			customerSysXRef.setUncondiCancl(uncondCancl);
			else
				customerSysXRef.setUncondiCancl("");
			
			String capitalExposer = rs.getString("IS_CAPITAL_MARKET_EXPOSER");
			if(null!=capitalExposer && !"".equalsIgnoreCase(capitalExposer)){
				customerSysXRef.setIsCapitalMarketExposer(capitalExposer);
				if(capitalExposer.equalsIgnoreCase("Yes"))
				customerSysXRef.setIsCapitalMarketExposer("YES");
				
				if(capitalExposer.equalsIgnoreCase("No"))
					customerSysXRef.setIsCapitalMarketExposer("NO");
				
			}
			else
				customerSysXRef.setIsCapitalMarketExposer("");
			
			String realEstateExposer = rs.getString("IS_REALESTATE_EXPOSER");
			if(null!=realEstateExposer && !"".equalsIgnoreCase(realEstateExposer))
			customerSysXRef.setIsRealEstateExposer(realEstateExposer);
			else
				customerSysXRef.setIsRealEstateExposer("");
			
			String prioritySector = rs.getString("PRIORITY_SECTOR");
			if(null!=prioritySector && !"".equalsIgnoreCase(prioritySector))
			customerSysXRef.setPrioritySector(prioritySector);
			else
				customerSysXRef.setPrioritySector("");
			
			String branchAllowedFlag = rs.getString("BRANCH_ALLOWED_FLAG");
			if(null!=branchAllowedFlag && !"".equalsIgnoreCase(branchAllowedFlag))
			customerSysXRef.setBranchAllowedFlag(branchAllowedFlag);
			else
				customerSysXRef.setBranchAllowedFlag("");
			
			
			String productAllowedFlag = rs.getString("PRODUCT_ALLOWED_FLAG");
			if(null!=productAllowedFlag && !"".equalsIgnoreCase(productAllowedFlag))
			customerSysXRef.setProductAllowedFlag(productAllowedFlag);
			else
				customerSysXRef.setProductAllowedFlag("");
			
			
			
			String currencyAllowedFlag = rs.getString("CURRENCY_ALLOWED_FLAG");
			if(null!=currencyAllowedFlag && !"".equalsIgnoreCase(currencyAllowedFlag))
			customerSysXRef.setCurrencyAllowedFlag(currencyAllowedFlag);
			else
				customerSysXRef.setCurrencyAllowedFlag("");
			
			
			
			
			
			String capitalMarketFlag = rs.getString("IS_CAPITAL_MARKET_EXPOSER_FLAG");
			if(null!=capitalMarketFlag && !"".equalsIgnoreCase(capitalMarketFlag))
			customerSysXRef.setIsCapitalMarketExposerFlag(capitalMarketFlag);
			else
				customerSysXRef.setIsCapitalMarketExposerFlag("");
			
			
			
			String segmentFlag = rs.getString("SEGMENT_1_FLAG");
			if(null!=segmentFlag && !"".equalsIgnoreCase(segmentFlag))
			customerSysXRef.setSegment1Flag(segmentFlag);
			else
				customerSysXRef.setSegment1Flag("");
			
			
			String estateTypeFlag = rs.getString("ESTATE_TYPE_FLAG");
			if(null!=estateTypeFlag && !"".equalsIgnoreCase(estateTypeFlag))
			customerSysXRef.setEstateTypeFlag(estateTypeFlag);
			else
				customerSysXRef.setEstateTypeFlag("");
			
			
			String prioritySectorFlag = rs.getString("PRIORITY_SECTOR_FLAG");
			if(null!=prioritySectorFlag && !"".equalsIgnoreCase(prioritySectorFlag))
			customerSysXRef.setPrioritySectorFlag(prioritySectorFlag);
			else
				customerSysXRef.setPrioritySectorFlag("");
			
			
			String uncondiCanclFlag = rs.getString("UNCONDI_CANCL_FLAG");
			if(null!=uncondiCanclFlag && !"".equalsIgnoreCase(uncondiCanclFlag))
			customerSysXRef.setUncondiCanclFlag(uncondiCanclFlag);
			else
				customerSysXRef.setUncondiCanclFlag("");
			
			String udfAllowed = rs.getString("UDF_ALLOWED");
			if(null!=udfAllowed && !"".equalsIgnoreCase(udfAllowed))
			customerSysXRef.setUdfAllowed(udfAllowed);
			else
				customerSysXRef.setUdfAllowed("");
			
			String udfAllowed_2 = rs.getString("UDF_ALLOWED_2");
			if(null!=udfAllowed_2 && !"".equalsIgnoreCase(udfAllowed_2))
			customerSysXRef.setUdfAllowed_2(udfAllowed_2);
			else
				customerSysXRef.setUdfAllowed_2("");
			
			
			String udfDelete = rs.getString("UDF_DELETE");
			if(null!=udfDelete && !"".equalsIgnoreCase(udfDelete))
			customerSysXRef.setUdfDelete(udfDelete);
			else
				customerSysXRef.setUdfDelete("");
			
			String udfDelete_2 = rs.getString("UDF_DELETE_2");
			if(null!=udfDelete_2 && !"".equalsIgnoreCase(udfDelete_2))
			customerSysXRef.setUdfDelete_2(udfDelete_2);
			else
				customerSysXRef.setUdfDelete_2("");
			
			
			String comm_real_flag = rs.getString("COMM_REAL_ESTATE_TYPE_FLAG");
			if(null!=comm_real_flag && !"".equalsIgnoreCase(comm_real_flag))
			customerSysXRef.setCommRealEstateTypeFlag(comm_real_flag);
			else
				customerSysXRef.setCommRealEstateTypeFlag("");
			
			String comm_real = rs.getString("COMM_REAL_ESTATE_TYPE");
			if(null!=comm_real && !"".equalsIgnoreCase(comm_real))
			customerSysXRef.setCommRealEstateType(comm_real);
			else
				customerSysXRef.setCommRealEstateType("");
			
			Date intradayLimitExpiryDate = rs.getDate("INTRADAY_LIMIT_EXPIRY_DATE");
			if(null!=intradayLimitExpiryDate && !"".equals(intradayLimitExpiryDate))
			customerSysXRef.setIntradayLimitExpiryDate(intradayLimitExpiryDate);
			
			
			String dayLightLimit =  rs.getString("DAY_LIGHT_LIMIT");
			if(null!=dayLightLimit && !"".equalsIgnoreCase(dayLightLimit))
			customerSysXRef.setDayLightLimit(dayLightLimit);
			else
				customerSysXRef.setDayLightLimit("");
			
			String intradayLimitFlag =  rs.getString("INTRADAY_LIMIT_FLAG");
			if(null!=intradayLimitFlag && !"".equalsIgnoreCase(intradayLimitFlag))
			customerSysXRef.setIntradayLimitFlag(intradayLimitFlag);
			else
				customerSysXRef.setIntradayLimitFlag("");
						
			String dayLightLimitAvailableFlag =  rs.getString("ISDAYLIGHTLIMITAVL");
			if(null!=dayLightLimitAvailableFlag && !"".equalsIgnoreCase(dayLightLimitAvailableFlag)) {
			if("Yes".equalsIgnoreCase(dayLightLimitAvailableFlag)) {
			customerSysXRef.setIsDayLightLimitAvailable("Y");
			}else {
				customerSysXRef.setIsDayLightLimitAvailable("N");
			}
			}else {
				customerSysXRef.setIsDayLightLimitAvailable("");
			}
			
			String projectFinance =  rs.getString("PROJECT_FINANCE");
			if(null!=projectFinance && !"".equalsIgnoreCase(projectFinance))
			customerSysXRef.setProjectFinance(projectFinance);
			else
				customerSysXRef.setProjectFinance("");
			
			String projectLoan =  rs.getString("PROJECT_LOAN");
			if(null!=projectLoan && !"".equalsIgnoreCase(projectLoan))
			customerSysXRef.setProjectLoan(projectLoan);
			else
				customerSysXRef.setProjectLoan("");
			
			String infraFlag =  rs.getString("INFRA_FLAG");
			if(null!=infraFlag && !"".equalsIgnoreCase(infraFlag))
			customerSysXRef.setInfaFlag(infraFlag);
			else
				customerSysXRef.setInfaFlag("");
			
			String scod =  rs.getString("SCOD");
			if(null!=scod && !"".equalsIgnoreCase(scod))
			customerSysXRef.setScod(scod);
			else
				customerSysXRef.setScod("");
			
			if(rs.getString("ESCOD_L1") != null) {
			String escod_L1 =  rs.getString("ESCOD_L1");
			if(null!=escod_L1 && !"".equalsIgnoreCase(escod_L1))
			customerSysXRef.setEscod_l1(escod_L1);
			else
				customerSysXRef.setEscod_l1("");
			}else {
				customerSysXRef.setEscod_l1("");
			}
			
			if(rs.getString("ESCOD_L2") != null) {
			String escod_L2 =  rs.getString("ESCOD_L2");
			if(null!=escod_L2 && !"".equalsIgnoreCase(escod_L2))
			customerSysXRef.setEscod_l2(escod_L2);
			else
				customerSysXRef.setEscod_l2("");
			}else {
				customerSysXRef.setEscod_l2("");
			}
			
			if(rs.getString("ESCOD_L3") != null) {
			String escod_L3 =  rs.getString("ESCOD_L3");
			if(null!=escod_L3 && !"".equalsIgnoreCase(escod_L3))
			customerSysXRef.setEscod_l3(escod_L3);
			else
				customerSysXRef.setEscod_l3("");
			}else {
				customerSysXRef.setEscod_l3("");
			}
			
			if(rs.getString("REVISED_ESCOD_L1") != null) {
			String revised_esocd_L1 =  rs.getString("REVISED_ESCOD_L1");
			if(null!=revised_esocd_L1 && !"".equalsIgnoreCase(revised_esocd_L1))
			customerSysXRef.setRevised_escod_l1(revised_esocd_L1);
			else
				customerSysXRef.setRevised_escod_l1("");
			}else {
				customerSysXRef.setRevised_escod_l1("");
			}
			
			if(rs.getString("REVISED_ESCOD_L2") != null) {
			String revised_esocd_L2 =  rs.getString("REVISED_ESCOD_L2");
			if(null!=revised_esocd_L2 && !"".equalsIgnoreCase(revised_esocd_L2))
			customerSysXRef.setRevised_escod_l2(revised_esocd_L2);
			else
				customerSysXRef.setRevised_escod_l2("");
			}else {
				customerSysXRef.setRevised_escod_l2("");
			}
			
			if(rs.getString("REVISED_ESCOD_L3") != null) {
			String revised_esocd_L3 =  rs.getString("REVISED_ESCOD_L3");
			if(null!=revised_esocd_L3 && !"".equalsIgnoreCase(revised_esocd_L3))
			customerSysXRef.setRevised_escod_l3(revised_esocd_L3);
			else
				customerSysXRef.setRevised_escod_l3("");
			}else {
				customerSysXRef.setRevised_escod_l3("");
			}
			
			if(rs.getString("ACOD") != null) {
			String acod =  rs.getString("ACOD");
			if(null!=acod && !"".equalsIgnoreCase(acod))
			customerSysXRef.setAcod(acod);
			else
				customerSysXRef.setAcod("");
			}else {
				customerSysXRef.setAcod("");
			}
			
			String delay_level =  rs.getString("DELAY_LEVEL");
			if(null!=delay_level && !"".equalsIgnoreCase(delay_level))
			customerSysXRef.setDelay_level(delay_level);
			else
				customerSysXRef.setDelay_level("");
			
			String exe_asset_class =  rs.getString("EXE_ASSET_CLASS");
			if(null!=exe_asset_class && !"".equalsIgnoreCase(exe_asset_class)) {
				if(exe_asset_class.equals("1") || exe_asset_class.equals("2") || exe_asset_class.equals("3")) {
			customerSysXRef.setExt_asset_class("NORM");
				}else {
			customerSysXRef.setExt_asset_class("PDO4");
				}
			}
			else {
				customerSysXRef.setExt_asset_class("");
			}
			if(rs.getString("REV_ASSET_CLASS_DT_L1") != null) {
			String rev_ext_asset_class_date_L1 = rs.getString("REV_ASSET_CLASS_DT_L1");
			if(null!=rev_ext_asset_class_date_L1 && !"".equalsIgnoreCase(rev_ext_asset_class_date_L1))
			customerSysXRef.setRev_ext_asset_class_date_L1(rev_ext_asset_class_date_L1);
			else
				customerSysXRef.setRev_ext_asset_class_date_L1("");
			}else {
				customerSysXRef.setRev_ext_asset_class_date_L1("");
			}
			
			if(rs.getString("REV_ASSET_CLASS_DT_L2") != null) {
			String rev_ext_asset_class_date_L2 =  rs.getString("REV_ASSET_CLASS_DT_L2");
			if(null!=rev_ext_asset_class_date_L2 && !"".equalsIgnoreCase(rev_ext_asset_class_date_L2))
			customerSysXRef.setRev_ext_asset_class_date_L2(rev_ext_asset_class_date_L2);
			else
				customerSysXRef.setRev_ext_asset_class_date_L2("");
			}else {
				customerSysXRef.setRev_ext_asset_class_date_L2("");
			}
			
			if(rs.getString("REV_ASSET_CLASS_DT_L3") != null) {
			String rev_ext_asset_class_date_L3 =  rs.getString("REV_ASSET_CLASS_DT_L3");
			if(null!=rev_ext_asset_class_date_L3 && !"".equalsIgnoreCase(rev_ext_asset_class_date_L3))
			customerSysXRef.setRev_ext_asset_class_date_L3(rev_ext_asset_class_date_L3);
			else
				customerSysXRef.setRev_ext_asset_class_date_L3("");
			}else {
				customerSysXRef.setRev_ext_asset_class_date_L3("");
			}
			
			if(rs.getString("REV_ASSET_CLASS_DT") != null) {
				String rev_ext_asset_class_date =  rs.getString("REV_ASSET_CLASS_DT");
				if(null!=rev_ext_asset_class_date && !"".equalsIgnoreCase(rev_ext_asset_class_date))
				customerSysXRef.setRev_asset_class_date(rev_ext_asset_class_date);
				else
					customerSysXRef.setRev_asset_class_date("");
				}else {
					customerSysXRef.setRev_asset_class_date("");
				}
			
			String rev_asset_class =  rs.getString("REV_ASSET_CLASS");
			if(null!=rev_asset_class && !"".equalsIgnoreCase(rev_asset_class)) {
			if(rev_asset_class.equals("1") || rev_asset_class.equals("2") || rev_asset_class.equals("3")) {
		customerSysXRef.setRev_asset_class("NORM");
			}else {
		customerSysXRef.setRev_asset_class("PDO4");
			}
		
			}
			else {
				customerSysXRef.setRev_asset_class("");
			}
			
			String rev_asset_class_L1 =  rs.getString("REV_ASSET_CLASS_L1");
			if(null!=rev_asset_class_L1 && !"".equalsIgnoreCase(rev_asset_class_L1)) {
			if(rev_asset_class_L1.equals("1") || rev_asset_class_L1.equals("2") || rev_asset_class_L1.equals("3")) {
		customerSysXRef.setRev_asset_class_L1("NORM");
			}else {
		customerSysXRef.setRev_asset_class_L1("PDO4");
			}
			}
			else {
				customerSysXRef.setRev_asset_class_L1("");
			}
			
			String rev_asset_class_L2 =  rs.getString("REV_ASSET_CLASS_L2");
			if(null!=rev_asset_class_L2 && !"".equalsIgnoreCase(rev_asset_class_L2)) {
				if(rev_asset_class_L2.equals("1") || rev_asset_class_L2.equals("2") || rev_asset_class_L2.equals("3")) {
					customerSysXRef.setRev_asset_class_L2("NORM");
						}else {
					customerSysXRef.setRev_asset_class_L2("PDO4");
						}
						}
			else {
				customerSysXRef.setRev_asset_class_L2("");
			}
			
			String rev_asset_class_L3 =  rs.getString("REV_ASSET_CLASS_L3");
			if(null!=rev_asset_class_L3 && !"".equalsIgnoreCase(rev_asset_class_L3)) {
				if(rev_asset_class_L3.equals("1") || rev_asset_class_L3.equals("2") || rev_asset_class_L3.equals("3")) {
					customerSysXRef.setRev_asset_class_L3("NORM");
						}else {
					customerSysXRef.setRev_asset_class_L3("PDO4");
						}
			}
			else {
				customerSysXRef.setRev_asset_class_L3("");
			}
			
			if(rs.getString("EXE_ASSET_CLASS_DT") != null) {
				String ext_asset_class_date =  rs.getString("EXE_ASSET_CLASS_DT");
				if(null!=ext_asset_class_date && !"".equalsIgnoreCase(ext_asset_class_date))
				customerSysXRef.setExt_asset_class_date(ext_asset_class_date);
				else
					customerSysXRef.setExt_asset_class_date("");
				}else {
					customerSysXRef.setExt_asset_class_date("");
				}
			
//			String stockDocMonth = rs.getString("STOCK_DOC_MONTH");
			String stockDocMonth = rs.getString("STOCK_DOC_MONTH_LMT");
			if(stockDocMonth != null && !"".equals(stockDocMonth)) {
				customerSysXRef.setStockDocMonth(stockDocMonth);
			}else {
				customerSysXRef.setStockDocMonth("");
			}
			
//			String stockDocYear = rs.getString("STOCK_DOC_YEAR");
			String stockDocYear = rs.getString("STOCK_DOC_YEAR_LMT");
			if(stockDocYear != null && !"".equals(stockDocYear)) {
				customerSysXRef.setStockDocYear(stockDocYear);
			}else {
				customerSysXRef.setStockDocYear("");
			}
			
			String facilityId =  rs.getString("LMT_ID");
			if(facilityId != null && !"".equals(facilityId)) {
				customerSysXRef.setFacilityId(facilityId);
			}else {
				customerSysXRef.setFacilityId("");
			}
			
			String stockLimitFlag =  rs.getString("STOCK_LIMIT_FLAG");
			if(stockLimitFlag != null && !"".equals(stockLimitFlag)) {
				customerSysXRef.setStockLimitFlag(stockLimitFlag);
			}else {
				customerSysXRef.setStockLimitFlag("");
			}

			list.add(customerSysXRef);
			}
		
		rs.close();
		
		return (OBCustomerSysXRef[]) list.toArray(new OBCustomerSysXRef[0]);
		
	}
	
private OBCustomerSysXRef[] processCoBorrower(  OBCustomerSysXRef customerSysXRef, ResultSet rs) throws SQLException {
		
		ArrayList list = new ArrayList();
        int cnt=0;
        
		while (rs.next()) {
			cnt++;
			System.out.println("processCoBorrower() cnt===================="+cnt);
		//	OBCustomerSysXRef customerSysXRef = new OBCustomerSysXRef();
		
			if(1 == cnt) {
				String coBorrowerId_1 =  rs.getString("CO_BORROWER_ID");
				if(null != coBorrowerId_1  && !"".equals(coBorrowerId_1)) {
					customerSysXRef.setCoBorrowerId_1(coBorrowerId_1);
				}else {
					customerSysXRef.setCoBorrowerId_1("");
				}
						
				String coBorrowerName_1 =  rs.getString("CO_BORROWER_NAME");
				if(null != coBorrowerName_1  && !"".equals(coBorrowerName_1)) {
					customerSysXRef.setCoBorrowerName_1(coBorrowerName_1);
				}else {
					customerSysXRef.setCoBorrowerName_1("");
				}
				System.out.println("1.customerSysXRef=="+customerSysXRef.getCoBorrowerId_1());
			}
		
			if(2 == cnt) {
				String coBorrowerId_2 =  rs.getString("CO_BORROWER_ID");
				if(null != coBorrowerId_2  && !"".equals(coBorrowerId_2)) {
					customerSysXRef.setCoBorrowerId_2(coBorrowerId_2);
				}else {
					customerSysXRef.setCoBorrowerId_2("");
				}
				String coBorrowerName_2 =  rs.getString("CO_BORROWER_NAME");
				if(null != coBorrowerName_2  && !"".equals(coBorrowerName_2)) {
					customerSysXRef.setCoBorrowerName_2(coBorrowerName_2);
				}else {
					customerSysXRef.setCoBorrowerName_2("");
				}
				System.out.println("2.customerSysXRef=="+customerSysXRef.getCoBorrowerId_2());

			}
			if(3 == cnt) {
				String coBorrowerId_3 =  rs.getString("CO_BORROWER_ID");
				if(null != coBorrowerId_3  && !"".equals(coBorrowerId_3)) {
					customerSysXRef.setCoBorrowerId_3(coBorrowerId_3);
				}else {
					customerSysXRef.setCoBorrowerId_3("");
				}
				String coBorrowerName_3 =  rs.getString("CO_BORROWER_NAME");
				if(null != coBorrowerName_3  && !"".equals(coBorrowerName_3)) {
					customerSysXRef.setCoBorrowerName_3(coBorrowerName_3);
				}else {
					customerSysXRef.setCoBorrowerName_3("");
				}
				System.out.println("3.customerSysXRef=="+customerSysXRef.getCoBorrowerId_3());

			}
			
			if(4 == cnt) {
				String coBorrowerId_4 =  rs.getString("CO_BORROWER_ID");
				if(null != coBorrowerId_4  && !"".equals(coBorrowerId_4)) {
					customerSysXRef.setCoBorrowerId_4(coBorrowerId_4);
				}else {
					customerSysXRef.setCoBorrowerId_4("");
				}
				String coBorrowerName_4 =  rs.getString("CO_BORROWER_NAME");
				if(null != coBorrowerName_4  && !"".equals(coBorrowerName_4)) {
					customerSysXRef.setCoBorrowerName_4(coBorrowerName_4);
				}else {
					customerSysXRef.setCoBorrowerName_4("");
				}
				System.out.println("4.customerSysXRef=="+customerSysXRef.getCoBorrowerId_4());

			}
			if(5 == cnt) {
				String coBorrowerId_5 =  rs.getString("CO_BORROWER_ID");
				if(null != coBorrowerId_5  && !"".equals(coBorrowerId_5)) {
					customerSysXRef.setCoBorrowerId_5(coBorrowerId_5);
				}else {
					customerSysXRef.setCoBorrowerId_5("");
				}
				String coBorrowerName_5 =  rs.getString("CO_BORROWER_NAME");
				if(null != coBorrowerName_5  && !"".equals(coBorrowerName_5)) {
					customerSysXRef.setCoBorrowerName_5(coBorrowerName_5);
				}else {
					customerSysXRef.setCoBorrowerName_5("");
				}
				System.out.println("5.customerSysXRef=="+customerSysXRef.getCoBorrowerId_5());
			}

			list.add(customerSysXRef);
		}
	
	rs.close();
	
	return (OBCustomerSysXRef[]) list.toArray(new OBCustomerSysXRef[0]);
	
}
	
	private String checkIsGuarantee(long xrefId) throws Exception {
		
		String queryStr = "select distinct lmts.liability_ID from sci_lsp_appr_lmts lmts,SCI_LSP_lmts_xref_map map,SCI_LSP_SYS_XREF xref where lmts.LMT_TYPE_VALUE = 'Yes' and lmts.guarantee ='Yes' and lmts.cms_lsp_appr_lmts_id = map.cms_lsp_appr_lmts_id "
				+ " AND map.cms_lsp_sys_xref_id = " +xrefId;
				
				
				
				String liabId = "";
				
				try {
					
					List queryForList = getJdbcTemplate().queryForList(queryStr);
					
					for (int i = 0; i < queryForList.size(); i++) {
						Map  map = (Map) queryForList.get(i);
				
						liabId= map.get("liability_ID").toString();
						if(null!= liabId && liabId.contains(" ")){
							
							liabId = liabId.substring(liabId.indexOf(" ")+2, liabId.length());
							liabId = liabId.trim();
						}
						 
						
					}
					
					return liabId;
				}
				catch (Exception ex) {
					ex.printStackTrace();
					throw ex;
				}
		
	}

	public void updateStageLineDetails(final String sourceRefNo,final String segment1Flag,final String prioritySecFlag,final String esatateTypeFlag,final String capitalFlag, final String unconFlag,final String xrefId,final String branchAllFlag,final String productAllFlag,final String currencyAllFlag, final String limitRestrFlag,final String sendToCore,final String commRealEstateTypeFlag){
		String updateStageLine="update cms_stage_lsp_sys_xref set status='PENDING',source_ref_no= ? , SEGMENT_1_FLAG=? , PRIORITY_SECTOR_FLAG=? ,"+ 
"  ESTATE_TYPE_FLAG=? , IS_CAPITAL_MARKET_EXPOSER_FLAG=? , UNCONDI_CANCL_FLAG=? , BRANCH_ALLOWED_FLAG=? , PRODUCT_ALLOWED_FLAG=? , " +
" CURRENCY_ALLOWED_FLAG=? , LIMIT_RESTRICTION_FLAG=? ,SENDTOCORE=? , COMM_REAL_ESTATE_TYPE_FLAG=?  where cms_lsp_sys_xref_id=?";
		
		DefaultLogger.debug(this, "inside updateStageLineDetails");
		
		System.out.println("updateStageLine: "+updateStageLine);
		System.out.println("Parameter : "+sourceRefNo+" xrefId "+xrefId);
//		DBUtil dbUtil = null;
//		ResultSet rs=null;
		try {
//			dbUtil = new DBUtil();
//			dbUtil.setSQL(updateStageLine);
//	
//			dbUtil.setString(1, sourceRefNo);
//			dbUtil.setString(2, segment1Flag);
//			dbUtil.setString(3, prioritySecFlag);
//			dbUtil.setString(4, esatateTypeFlag);
//			dbUtil.setString(5, capitalFlag );
//			dbUtil.setString(6, unconFlag);
//			dbUtil.setString(7, xrefId);
//			
//			int executeUpdate = dbUtil.executeUpdate();
//			DefaultLogger.debug(this, "updated STageLineDetails before commit"+executeUpdate);
//			dbUtil.commit();
			
			int executeUpdate=getJdbcTemplate().update(updateStageLine, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, sourceRefNo);
					ps.setString(2, segment1Flag);
					ps.setString(3, prioritySecFlag);
					ps.setString(4, esatateTypeFlag);
					ps.setString(5, capitalFlag );
					ps.setString(6, unconFlag);
					ps.setString(7, branchAllFlag);
					ps.setString(8, productAllFlag);
					ps.setString(9, currencyAllFlag);
					ps.setString(10, limitRestrFlag);
					ps.setString(11, sendToCore);
					ps.setString(12, commRealEstateTypeFlag);
					ps.setString(13, xrefId);
					
					
					
				}
			});
			DefaultLogger.debug(this, "updated STageLineDetails");
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updateStageLineDetails:"+e.getMessage());
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updateStageLineDetails:"+e.getMessage());
		}
//		finalize(dbUtil,rs);
		DefaultLogger.debug(this, "completed updateStageLineDetails");
	}


	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	public int generateSourceSeqNo() {
		String generateSourceString="select FCUBS_SOURCESEQNO_SEQ.nextval from dual";
		int queryForInt = 0;
		try{
			
			queryForInt = getJdbcTemplate().queryForInt(generateSourceString);
		
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in generateSourceSeqNo"+e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
		
		return queryForInt;
	}
	
	public List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getCurrencyIsoCode();
						String value = lst.getCurrencyIsoCode();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return com.integrosys.cms.ui.manualinput.CommonUtil.sortDropdown(lbValList);
	}

	public String getRuleId(String facilityCode){
		String ruleId="";
		String getRuleId="select ruleid  from cms_facility_new_master where new_facility_code='"+facilityCode+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getRuleId);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempRuleId = rs.getString(1);
				if(null!=tempRuleId){
					ruleId=tempRuleId;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return ruleId;
	}
	public String getLineDescription(String facilityCode){
		String lineDescription="";
		String getLineDescription="select line_description  from cms_facility_new_master where new_facility_code='"+facilityCode+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getLineDescription);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempLineDescription = rs.getString(1);
				if(null!=tempLineDescription){
					lineDescription=tempLineDescription;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return lineDescription;
	}
	
	public String getCurrencyRestriction(String facilityCode){
		String currencyRestriction="";
		String getLineDescription="select Currency_Restriction  from cms_facility_new_master where new_facility_code='"+facilityCode+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getLineDescription);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempCurrencyRestriction = rs.getString(1);
				if(null!=tempCurrencyRestriction){
					currencyRestriction=tempCurrencyRestriction;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return currencyRestriction;
	}
	
	public String getRevolvingLine(String facilityCode){
		String revolvingLine="";
		String getRevolvingLine="select Revolving_Line  from cms_facility_new_master where new_facility_code='"+facilityCode+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getRevolvingLine);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempRevolvingLine = rs.getString(1);
				if(null!=tempRevolvingLine){
					revolvingLine=tempRevolvingLine;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return revolvingLine;
	}
	public double getReleaseAmountForParty(String partyId){
		String rlsAmountQry=" SELECT  sum(XREF.RELEASED_AMOUNT) " + 
				"	 FROM " + 
				"	 SCI_LSP_APPR_LMTS SCI,SCI_LSP_SYS_XREF XREF,SCI_LSP_LMTS_XREF_MAP MAPSS,SCI_LSP_LMT_PROFILE PF,SCI_LE_SUB_PROFILE SPRO " + 
				"	   WHERE SCI.CMS_LIMIT_PROFILE_ID    = PF.CMS_LSP_LMT_PROFILE_ID " + 
				"	   AND PF.CMS_CUSTOMER_ID          = Spro.CMS_LE_SUB_PROFILE_ID " + 
				"	   AND SCI.CMS_LSP_APPR_LMTS_ID  = MAPSS.CMS_LSP_APPR_LMTS_ID(+) " + 
				"	   AND MAPSS.CMS_LSP_SYS_XREF_ID = XREF.CMS_LSP_SYS_XREF_ID(+) " + 
				"	   AND XREF.SCM_FLAG='Yes' AND XREF.FACILITY_SYSTEM like '%UBS%' and XREF.status = 'SUCCESS'"+
			    "     AND SPRO.lsp_le_id = '"+partyId+"'" ;
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		double amount = 0.0;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(rlsAmountQry);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 amount = rs.getDouble(1);
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return amount;
	}
	
	
	
	public String getScmFlag(String facilityCode){
		String getScmFlag="select scm_flag  from cms_facility_new_master "
				+ " where new_facility_code=? and status = 'ACTIVE'";
		
		return (String) getJdbcTemplate().query(getScmFlag, new Object[]{new String(facilityCode)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return  rs.getString(1);
				}
				return null;
			}
		});
	}
	
	public String getBorrowerScmFlag(String custId){
		String borrowerScmFlag=null;
		String mainId = getMainID(custId);
		//String getBorrowerScmFlag="select udf17  from SCI_LE_UDF where cms_le_main_profile_id='"+mainId+"'";
		//This is changed as bank has 97 sequence number as borrower scm flag
		String getBorrowerScmFlag="select udf97  from SCI_LE_UDF where cms_le_main_profile_id='"+mainId+"'";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getBorrowerScmFlag);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempscmFlag = rs.getString(1);
				if(null!=tempscmFlag){
					borrowerScmFlag=tempscmFlag;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return borrowerScmFlag;
	}
	
	public String getBorrowerScmFlagForAlert(String custId){
		String borrowerScmFlag=null;
		String profID = getIdfromParty(custId);
		//String getBorrowerScmFlag="select udf17  from SCI_LE_UDF where cms_le_main_profile_id='"+profID+"'";
		//This is changed as bank has 97 sequence number as borrower scm flag
		String getBorrowerScmFlag="select udf97  from SCI_LE_UDF where cms_le_main_profile_id='"+profID+"'";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getBorrowerScmFlag);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempscmFlag = rs.getString(1);
				if(null!=tempscmFlag){
					borrowerScmFlag=tempscmFlag;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return borrowerScmFlag;
	}
	
	
	
	public String getLineCurrency(String facilityCode){
		String lineCurrency="";
		String getLineCurrency="select Line_Currency  from cms_facility_new_master where new_facility_code='"+facilityCode+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getLineCurrency);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempLineCurrency = rs.getString(1);
				if(null!=tempLineCurrency){
					lineCurrency=tempLineCurrency;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return lineCurrency;
	}
	
	public String getIntradayLimit(String facilityCode){
		String intradayLimit="";
		String getIntradayLimit="select Intraday_Limit  from cms_facility_new_master where new_facility_code='"+facilityCode+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getIntradayLimit);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				String tempIntradayLimit = rs.getString(1);
				if(null!=tempIntradayLimit){
					intradayLimit=tempIntradayLimit;
				}
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return intradayLimit;
	}
	public Date getCamExtentionDate(String limitProfileIDStr){
		Date camExtensionDate=null;
		String getcamExt="select llp_extd_next_rvw_date from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id='"+limitProfileIDStr+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getcamExt);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 camExtensionDate = rs.getDate(1);
				
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		finally{
		finalize(dbUtil,rs);
		}
		return camExtensionDate;
	}
	
	public Date getCamDate(String limitProfileIDStr){
		Date camExtensionDate=null;
		String getcamExt="select LLP_BCA_REF_APPR_DATE from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id='"+limitProfileIDStr+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(getcamExt);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 camExtensionDate = rs.getDate(1);
				
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}	
		finally{
		finalize(dbUtil,rs);
		}
		return camExtensionDate;
	}
	
	public String getSeqNoForFile() throws Exception {

		String fileSeqNo = "";
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob in seqno");
		int queryForInt = 0;
		String queryStr = "select FCUBS_FILE_SEQ.NEXTVAL from dual";
		
		
		ResultSet rs = null;
		try {

			queryForInt = getJdbcTemplate().queryForInt(queryStr);
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  seqno"+queryForInt);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		
		
		fileSeqNo = String.format("%04d", queryForInt);
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get seqno" +fileSeqNo);
		return fileSeqNo;
	}
	
	public String getSeqNoForOtherCovenant() throws Exception {

		String OtherCovenantSeqNo = "";
		int queryForInt = 0;
		String queryStr = "select CAM_OC_MAP_SEQ.NEXTVAL from dual";
		
		
		ResultSet rs = null;
		try {

			queryForInt = getJdbcTemplate().queryForInt(queryStr);
			//DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  seqno"+queryForInt);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		
		
		OtherCovenantSeqNo = String.format("%04d", queryForInt);
		//DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get seqno" +fileSeqNo);
		return OtherCovenantSeqNo;
	}
	
	
	public String getProductCodeFromId(String productId)throws SearchDAOException {

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+productId+" getProductCodeFromId");
		String queryStr = "SELECT product_code from CMS_PRODUCT_MASTER where id = ?";
		try {

		
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(productId)},
					new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "0";
				}
			});
			
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		
	}
	
	
	public String[] getUDFDetails(String udfField,long xrefId)throws SearchDAOException {

		String queryStr = "SELECT UDF"+udfField+"_LABEL,UDF"+udfField+"_VALUE,UDF"+udfField+"_FLAG from SCI_LSP_LMT_XREF_UDF where SCI_LSP_SYS_XREF_ID = "+xrefId +" and UDF"+udfField+"_FLAG is not null";
		String[] UDFList = new String[3];
		try{
		List udfListData = getJdbcTemplate().queryForList(queryStr);

		for (int i = 0; i < udfListData.size(); i++) {
			Map  map = (Map) udfListData.get(i);
	
			if(null != map.get("UDF"+udfField+"_LABEL"))
			UDFList[0] =  map.get("UDF"+udfField+"_LABEL").toString();
			if(null != map.get("UDF"+udfField+"_VALUE"))
			UDFList[1] =  map.get("UDF"+udfField+"_VALUE").toString();
			else
			UDFList[1] = "";
			if(null != map.get("UDF"+udfField+"_FLAG"))
			UDFList[2] = map.get("UDF"+udfField+"_FLAG").toString();
			
		}
		
		return UDFList;
		
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		
	}
	
	public String[] getUDFDetails2(String udfField,long xrefId)throws SearchDAOException {

		String queryStr = "SELECT UDF"+udfField+"_LABEL,UDF"+udfField+"_VALUE,UDF"+udfField+"_FLAG from SCI_LSP_LMT_XREF_UDF_2 where SCI_LSP_SYS_XREF_ID = "+xrefId +" and UDF"+udfField+"_FLAG is not null";
		String[] UDFList = new String[3];
		try{
		List udfListData = getJdbcTemplate().queryForList(queryStr);

		for (int i = 0; i < udfListData.size(); i++) {
			Map  map = (Map) udfListData.get(i);
	
			if(null != map.get("UDF"+udfField+"_LABEL"))
			UDFList[0] =  map.get("UDF"+udfField+"_LABEL").toString();
			if(null != map.get("UDF"+udfField+"_VALUE"))
			UDFList[1] =  map.get("UDF"+udfField+"_VALUE").toString();
			else
			UDFList[1] = "";
			if(null != map.get("UDF"+udfField+"_FLAG"))
			UDFList[2] = map.get("UDF"+udfField+"_FLAG").toString();
			
		}
		
		return UDFList;
		
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		
	}
	
	public OBFCUBSDataLog fetchPartyDetails(
			OBCustomerSysXRef obCustomerSysXRef, OBFCUBSDataLog fcubsObj) throws Exception {
		
		
		String queryStr = "select unique sp.lmp_le_id, sp.lmp_short_name from sci_LE_MAIN_PROFILE sp,SCI_LSP_LMT_PROFILE profile,"+
		" sci_lsp_appr_lmts lmts,SCI_LSP_lmts_xref_map map,SCI_LSP_SYS_XREF xref where sp.lmp_le_id = profile.llp_le_id and"+
		" profile.cms_lsp_lmt_profile_id = lmts.cms_limit_profile_id and lmts.cms_lsp_appr_lmts_id = map.cms_lsp_appr_lmts_id"+
		" and map.cms_lsp_sys_xref_id = '"+obCustomerSysXRef.getXRefID()+"'";
		
		
		
		String partyId = "";
		String partyName = "";
		try {
			
			List queryForList = getJdbcTemplate().queryForList(queryStr);
			
			for (int i = 0; i < queryForList.size(); i++) {
				Map  map = (Map) queryForList.get(i);
		
				 partyId= map.get("lmp_le_id").toString();
				 fcubsObj.setPartyId(partyId);
				 partyName =  map.get("lmp_short_name").toString();
				 fcubsObj.setPartyName(partyName);
				
			}
			
			return fcubsObj;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
	}
	
	public OBFCUBSDataLog fetchMakerCheckerDetails(
			OBCustomerSysXRef obCustomerSysXRef, OBFCUBSDataLog fcubsObj) throws Exception {
		
		String queryStr = "SELECT CREATED_BY,UPDATED_BY,CREATED_ON,UPDATED_ON "
				+ "FROM SCI_LSP_SYS_XREF WHERE CMS_LSP_SYS_XREF_ID='"+obCustomerSysXRef.getXRefID()+"'" ;
		String createdBy = "";
		String updatedBy = "";
		Date createdOn=null;
		Date updatedOn=null;
		
		try {
			List queryForList = getJdbcTemplate().queryForList(queryStr);
			for (int i = 0; i < queryForList.size(); i++) {
				Map  map = (Map) queryForList.get(i);
				if(null!=map.get("CREATED_BY") && !"".equals(map.get("CREATED_BY"))) {
					createdBy= map.get("CREATED_BY").toString();
				}
				fcubsObj.setMakerId(createdBy);
				
				if(null!=map.get("UPDATED_BY") && !"".equals(map.get("UPDATED_BY"))) {
					updatedBy =  map.get("UPDATED_BY").toString();
				}
				fcubsObj.setCheckerId(updatedBy);
				
				if(null!=map.get("CREATED_ON") && !"".equals(map.get("CREATED_ON"))) {
					createdOn= (Date)map.get("CREATED_ON");
				}
				fcubsObj.setMakerDateTime(createdOn);
				
				if(null!=map.get("UPDATED_ON") && !"".equals(map.get("UPDATED_ON"))) {
					updatedOn = (Date) map.get("UPDATED_ON");
				}
				fcubsObj.setCheckerDateTime(updatedOn);
			}
			return fcubsObj;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
	}
	
		public void updateStatus(long xrefId, String sourceRefNo) throws DBConnectionException, SQLException,Exception {
		
		
		try{
		    	 String queryStr = "update SCI_LSP_SYS_XREF set SENDTOCORE = 'Y', STOCK_LIMIT_FLAG = 'N' where CMS_LSP_SYS_XREF_ID = "+xrefId;
		    	 getJdbcTemplate().update(queryStr);
				 
				 String queryStagingStr = "update CMS_STAGE_LSP_SYS_XREF set SENDTOCORE = 'Y', STOCK_LIMIT_FLAG = 'N' where SOURCE_REF_NO  = '"+sourceRefNo+"'";
				 getJdbcTemplate().update(queryStagingStr);
			}
		catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}
		
		
		
		
	}
		
		// Duplicate record sent to core issue
		public void updateStatusSchedulerProgress(long xrefId, String sourceRefNo) throws DBConnectionException, SQLException,Exception {
			
			
			try{
			    	 String queryStr = "update SCI_LSP_SYS_XREF set SCHEDULER_STATUS = 'IN_PROGRESS' where CMS_LSP_SYS_XREF_ID = "+xrefId;
			    	 getJdbcTemplate().update(queryStr);
					 
				}
			catch(Exception e){
				
				e.printStackTrace();
				throw e;
			}
			
			
			
			
		}
		
		// Duplicate record sent to core issue
		public void updateStatusSchedulerCompleted(long xrefId, String sourceRefNo) throws DBConnectionException, SQLException,Exception {
			
			
			try{
			    	 String queryStr = "update SCI_LSP_SYS_XREF set SCHEDULER_STATUS = 'COMPLETED' where CMS_LSP_SYS_XREF_ID = "+xrefId;
			    	 getJdbcTemplate().update(queryStr);
					 
				}
			catch(Exception e){
				
				e.printStackTrace();
				throw e;
			}
			
			
			
			
		}
		
		public void clearUDFFields(String sourceRef) throws DBConnectionException, SQLException,Exception {
			
			
			ResultSet rs = null;
			ArrayList<String[]> udfList = new ArrayList<String[]>();
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob clearUDFFields");
			try{


				String queryIs = "select CMS_LSP_SYS_XREF_ID, UDF_ALLOWED, UDF_DELETE ,UDF_ALLOWED_2, UDF_DELETE_2 FROM SCI_LSP_SYS_XREF where SOURCE_REF_NO IN ("+sourceRef+")";
				List queryForList =  getJdbcTemplate().queryForList(queryIs);
				
				for (int i = 0; i < queryForList.size(); i++) {
					Map  map = (Map) queryForList.get(i);
			
						String[] UDFList = new String[5];
						if(null!= map.get("CMS_LSP_SYS_XREF_ID"))
						UDFList[0] = map.get("CMS_LSP_SYS_XREF_ID").toString();
						if(null!= map.get("UDF_ALLOWED"))
						UDFList[1] = map.get("UDF_ALLOWED").toString();
						if(null!= map.get("UDF_DELETE"))
						UDFList[2] = map.get("UDF_DELETE").toString();
						
						if(null!= map.get("UDF_ALLOWED_2"))
						UDFList[3] = map.get("UDF_ALLOWED_2").toString();
						if(null!= map.get("UDF_DELETE_2"))
						UDFList[4] = map.get("UDF_DELETE_2").toString();
						
						
						udfList.add(UDFList);
					
				}

				for(String[] udfFields : udfList){
					
					String[] udfSelectList = getUDFSelectList(udfFields[1]);
					String[] udfDeleteList = getUDFSelectList(udfFields[2]);
					if(null!=udfSelectList){
					for(String udfField : udfSelectList){

						if(null!=udfField){
						String queryStr = "update SCI_LSP_LMT_XREF_UDF set UDF"+udfField+"_FLAG = '' where SCI_LSP_SYS_XREF_ID = "+udfFields[0] +" and UDF"+udfField+"_FLAG is not null";
						 getJdbcTemplate().update(queryStr);
						}

						}
					
					}

					if(null!=udfDeleteList){
						for(String udfField : udfDeleteList){

							if(null!=udfField){
							String queryStr = "update SCI_LSP_LMT_XREF_UDF set UDF"+udfField+"_FLAG = '',UDF"+udfField+"_LABEL = '',UDF"+udfField+"_VALUE = '' where SCI_LSP_SYS_XREF_ID = "+udfFields[0] +" and UDF"+udfField+"_FLAG is not null";
							 getJdbcTemplate().update(queryStr);
							}

							}
						
						}
					
					String[] udfSelectList2 = getUDFSelectList(udfFields[3]);
					String[] udfDeleteList2 = getUDFSelectList(udfFields[4]);
					if(null!=udfSelectList2){
					for(String udfField : udfSelectList2){

						int newUdfField= Integer.parseInt(udfField);
						newUdfField=newUdfField+115;
						udfField= String.valueOf(newUdfField);
						
						if(null!=udfField){
						String queryStr = "update SCI_LSP_LMT_XREF_UDF_2 set UDF"+udfField+"_FLAG = '' where SCI_LSP_SYS_XREF_ID = "+udfFields[0] +" and UDF"+udfField+"_FLAG is not null";
						 getJdbcTemplate().update(queryStr);
						}

						}
					
					}

					if(null!=udfDeleteList2){
						for(String udfField : udfDeleteList2){
							int newUdfField= Integer.parseInt(udfField);
							newUdfField=newUdfField+115;
							udfField= String.valueOf(newUdfField);
							
							if(null!=udfField){
							String queryStr = "update SCI_LSP_LMT_XREF_UDF_2 set UDF"+udfField+"_FLAG = '',UDF"+udfField+"_LABEL = '',UDF"+udfField+"_VALUE = '' where SCI_LSP_SYS_XREF_ID = "+udfFields[0] +" and UDF"+udfField+"_FLAG is not null";
							 getJdbcTemplate().update(queryStr);
							}

							}
						
						}



				}


				String queryStr = "update SCI_LSP_SYS_XREF set UDF_DELETE = '' , UDF_DELETE_2 = '' where SOURCE_REF_NO IN ("+sourceRef+")";
				 getJdbcTemplate().update(queryStr);



			}
			
			catch(Exception e){
				DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob Exception......"+e.getMessage());
				e.printStackTrace();
				throw e;
			}

			
			
			
		}

		public void updateFCUBSDataLog(String sourceRef, Map<String, String> map,
				String fcubsStatusSuccess,Date responseDate) throws Exception {

			
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob updateFCUBSDataLog......"+fcubsStatusSuccess);
			try{
				if(fcubsStatusSuccess.equalsIgnoreCase(ICMSConstant.FCUBS_STATUS_SUCCESS)){

					String queryStr = "update CMS_FCUBSDATA_LOG set status = '"+fcubsStatusSuccess+"' ,RESPONSEDATE = ? where SOURCE_REF_NO IN ("+sourceRef+")";
					getJdbcTemplate().update(queryStr,
							new Object[]{new Timestamp(responseDate.getTime())});
					for (Map.Entry<String, String> entry : map.entrySet()) {

						String queryForSerialNo = "update CMS_FCUBSDATA_LOG set SERIALNO = '"+entry.getValue()+"' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
						 getJdbcTemplate().update(queryForSerialNo);

					}

				}

				if(fcubsStatusSuccess.equalsIgnoreCase(ICMSConstant.FCUBS_STATUS_REJECTED)){


					for (Map.Entry<String, String> entry : map.entrySet()) {

						String queryStr = "update CMS_FCUBSDATA_LOG set ERRORDESC = '"+entry.getValue().replaceAll("'", "''")+"' , status = '"+fcubsStatusSuccess+"', RESPONSEDATE = ? where SOURCE_REF_NO  = '"+entry.getKey()+"'";
						getJdbcTemplate().update(queryStr,
								new Object[]{new Timestamp(responseDate.getTime())});

					}

				}


			}
			
			catch(Exception e){
				DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob Exception......"+e.getMessage());
				e.printStackTrace();
				throw e;
			}

			

		}

		public void updateLineDetails(String sourceRef, Map<String, String> map,
				String fcubsStatusSuccess) throws DBConnectionException, SQLException,Exception {

			System.out.println("Starting FCUBSLimitFileDownloadJob fcubsStatusSuccess......"+fcubsStatusSuccess);
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob fcubsStatusSuccess......"+fcubsStatusSuccess);
			try{
				if(fcubsStatusSuccess.equalsIgnoreCase(ICMSConstant.FCUBS_STATUS_SUCCESS)){

					String queryStr = "update SCI_LSP_SYS_XREF set status = '"+fcubsStatusSuccess+"' , SENDTOCORE = 'N' ,LIMIT_RESTRICTION_FLAG = '' , BRANCH_ALLOWED_FLAG = '', PRODUCT_ALLOWED_FLAG = '' ,CURRENCY_ALLOWED_FLAG = '', IS_CAPITAL_MARKET_EXPOSER_FLAG = '', SEGMENT_1_FLAG = '', ESTATE_TYPE_FLAG = '', PRIORITY_SECTOR_FLAG = '', UNCONDI_CANCL_FLAG = '' , ACTION = '' , CORE_STP_REJECTED_REASON = '', COMM_REAL_ESTATE_TYPE_FLAG = '' where SOURCE_REF_NO IN ("+sourceRef+")";
					System.out.println("LimitDAO=>updateLineDetails method =>Actual queryStr=>"+queryStr); 
					getJdbcTemplate().update(queryStr);
					for (Map.Entry<String, String> entry : map.entrySet()) {

						String queryForSerialNo = "update SCI_LSP_SYS_XREF set SERIAL_NO = '"+entry.getValue()+"', HIDDEN_SERIAL_NO = '"+entry.getValue()+"' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
						System.out.println("LimitDAO=>updateLineDetails method =>1)Actual queryForSerialNo=>"+queryForSerialNo);  
						getJdbcTemplate().update(queryForSerialNo);

					}
					
					String queryStagingStr = "update CMS_STAGE_LSP_SYS_XREF set status = '"+fcubsStatusSuccess+"' , SENDTOCORE = 'N' ,LIMIT_RESTRICTION_FLAG = '' , BRANCH_ALLOWED_FLAG = '', PRODUCT_ALLOWED_FLAG = '' ,CURRENCY_ALLOWED_FLAG = '', IS_CAPITAL_MARKET_EXPOSER_FLAG = '', SEGMENT_1_FLAG = '', ESTATE_TYPE_FLAG = '', PRIORITY_SECTOR_FLAG = '', UNCONDI_CANCL_FLAG = '', ACTION = '', CORE_STP_REJECTED_REASON = '' , COMM_REAL_ESTATE_TYPE_FLAG = '' where SOURCE_REF_NO IN ("+sourceRef+")";
					System.out.println("LimitDAO=>updateLineDetails method =>Stage queryStagingStr=>"+queryStagingStr);  
					getJdbcTemplate().update(queryStagingStr);
					for (Map.Entry<String, String> entry : map.entrySet()) {

						String queryForSerialNo = "update CMS_STAGE_LSP_SYS_XREF set SERIAL_NO = '"+entry.getValue()+"', HIDDEN_SERIAL_NO = '"+entry.getValue()+"' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
						System.out.println("LimitDAO=>updateLineDetails method =>2)Stage queryForSerialNo=>"+queryForSerialNo);   
						getJdbcTemplate().update(queryForSerialNo);

					}

				}

				if(fcubsStatusSuccess.equalsIgnoreCase(ICMSConstant.FCUBS_STATUS_REJECTED)){


					for (Map.Entry<String, String> entry : map.entrySet()) {

						String queryStr = "update SCI_LSP_SYS_XREF set CORE_STP_REJECTED_REASON = '"+entry.getValue().replaceAll("'", "''")+"' , status = '"+fcubsStatusSuccess+"' , SENDTOCORE = 'N' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
						System.out.println("LimitDAO=>updateLineDetails method FCUBS_STATUS_REJECTED =>line 4725.. queryStr=>"+queryStr);  
						getJdbcTemplate().update(queryStr);

					}
					
					for (Map.Entry<String, String> entry : map.entrySet()) {

						String queryStr = "update CMS_STAGE_LSP_SYS_XREF set CORE_STP_REJECTED_REASON = '"+entry.getValue().replaceAll("'", "''")+"' , status = '"+fcubsStatusSuccess+"' , SENDTOCORE = 'N' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
						System.out.println("LimitDAO=>updateLineDetails method FCUBS_STATUS_REJECTED =>line 4733 .. queryStr=>"+queryStr);
						getJdbcTemplate().update(queryStr);

					}

				}


			}
			
			catch(Exception e){
				DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob Exception......"+e.getMessage());
				System.out.println("Exception caught in FCUBSLimitFileDownloadJob=> updateLineDetails =>e=>"+e);
				e.printStackTrace();
				throw e;
			}

			

		}
		
		
		public String[] getUDFSelectList(String udf) {

			String[] udfList = null;
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  UDF Allowed "+udf+" in UDF list ");
			
			if(null!=udf && udf.contains(",")){
				udfList = udf.split(",");
			}
			else if(null != udf && !"".equalsIgnoreCase(udf)){
				udfList = new String[1];
				udfList[0] = udf;
			}

			return udfList;
		}


		public String getBranchCodeFromId(String branchId)throws SearchDAOException {
			
			String queryStr = "SELECT branchCode from CMS_FCCBRANCH_MASTER where id = ?";
				
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(branchId)},
					new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "0";
				}
			});
			
		}
	
		
		public String getDescFromCode(String code) throws SearchDAOException {
			
			String queryStr = "SELECT entry_name from common_code_category_entry where category_code IN ('UNCONDI_CANCL_COMMITMENT','PRIORITY_SECTOR','PRIORITY_SECTOR_Y','SEGMENT_1','COMMERCIAL_REAL_ESTATE') and active_status = '1' and entry_code = ?";
				
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(code)},
					new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "";
				}
			});
			
		}
		
		
		
			public ArrayList<String[]> getFileNames() throws SearchDAOException,Exception {
			ArrayList<String[]> ackList = new ArrayList<String[]>();
			try{
			String queryStr = "SELECT distinct FILENAME from cms_fcubsdata_log where status = '"+ICMSConstant.FCUBS_STATUS_PENDING+"'";
			List queryForList =  getJdbcTemplate().queryForList(queryStr);
			
			for (int i = 0; i < queryForList.size(); i++) {
					Map  map = (Map) queryForList.get(i);
		
					String[] ackFiles = new String[2];
					if(null!= map.get("FILENAME")){
					String fileName = map.get("FILENAME").toString();
					String ackFileName = fileName.substring(0,fileName.lastIndexOf("."));
					ackFiles[0] = ackFileName+"_SUCCESS.txt";
					ackFiles[1] = ackFileName+"_ERROR.txt";
					ackList.add(ackFiles);
					}
				
			}
				}
			catch(Exception e){
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob getFileNames......"+e.getMessage());
					e.printStackTrace();
					throw e;
				}
	
			
			return ackList;
		}
			
			public void updateStageSendToCore(final String xrefId,final String sendToCore){
				String updateStageLine="update cms_stage_lsp_sys_xref set SENDTOCORE=? where cms_lsp_sys_xref_id=?";
				
				DefaultLogger.debug(this, "inside updateStageSendToCore");

				try {
					int executeUpdate=getJdbcTemplate().update(updateStageLine, new PreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, sendToCore);
							ps.setString(2, xrefId);
							}
					});
					DefaultLogger.debug(this, "updated updateStageSendToCore");
					
				} catch (DBConnectionException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  updateStageSendToCore:"+e.getMessage());
				} catch (NoSQLStatementException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  updateStageSendToCore:"+e.getMessage());
				}

				DefaultLogger.debug(this, "completed updateStageSendToCore");
			}	
	//added by santosh for UBS CR
			public List getRejectedLimitSummaryListByAA() throws LimitException {

				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				String fcubsSystem = bundle.getString("fcubs.systemName");
				String ubsSystem = bundle.getString("ubs.systemName");
				
				String queryStr = "SELECT DISTINCT SCI.CMS_LSP_APPR_LMTS_ID, " + 
						"  XREF.status, " + 
						"  SCI.FACILITY_SYSTEM, " + 
						"  SCI.LMT_ID, " + 
						"  SCI.FACILITY_NAME, " + 
						"  SCI.FACILITY_TYPE, " + 
						"  SCI.LMT_OUTER_LMT_ID, " + 
						"  SCI.LMT_CRRNCY_ISO_CODE, " + 
						"  SCI.RELEASABLE_AMOUNT, " + 
						"  SCI.CMS_ACTIVATED_LIMIT, " + 
						"  SCI.TOTAL_RELEASED_AMOUNT, " + 
						"  SCI.CMS_OUTSTANDING_AMT, " + 
						"  SCI.CMS_REQ_SEC_COVERAGE, " + 
						"  SCI.IS_ADHOC_TOSUM, " + 
						"  SCI.IS_ADHOC, " + 
						"  SCI.ADHOC_LMT_AMOUNT, " + 
						"  SCI.LMT_TYPE_VALUE, " + 
						"  SEC.CMS_COLLATERAL_ID, " + 
						"  SEC.TYPE_NAME, " + 
						"  SEC.SUBTYPE_NAME, " + 
						"  SPRO.LSP_LE_ID, " + 
						"  SPRO.LSP_SHORT_NAME, " + 
						"  MAPS.LMT_SECURITY_COVERAGE, " +
						"  SS.SOURCE_SECURITY_ID, " +
						"  SEC.COLLATERAL_CODE, " + 
						"  SPRO.CMS_LE_SUB_PROFILE_ID, "+
						"  PF.CMS_LSP_LMT_PROFILE_ID " +
						"FROM CMS_SECURITY SEC, " + 
						"  CMS_SECURITY_SUB_TYPE SUB, " + 
						"  SECURITY_TYPE TYP, " + 
						"  SCI_LSP_APPR_LMTS SCI, " + 
						"  SCI_LSP_SYS_XREF XREF, " + 
						"  SCI_LSP_LMTS_XREF_MAP MAPSS, " + 
						"  CMS_LIMIT_SECURITY_MAP MAPS, " + 
						"  SCI_LSP_LMT_PROFILE PF, " + 
						"  SCI_LE_SUB_PROFILE SPRO, " + 
						"  CMS_SECURITY_SOURCE SS " +
						"WHERE SEC.SECURITY_SUB_TYPE_ID = SUB.SECURITY_SUB_TYPE_ID " + 
						" AND SUB.SECURITY_TYPE_ID       = TYP.SECURITY_TYPE_ID " + 
						" AND (MAPS.update_status_ind   <> 'D' " + 
						"OR MAPS.update_status_ind     IS NULL) " + 
						" AND SEC.CMS_COLLATERAL_ID      = MAPS.CMS_COLLATERAL_ID " + 
						" AND MAPS.CMS_LSP_APPR_LMTS_ID  = SCI.CMS_LSP_APPR_LMTS_ID " + 
						" AND SCI.CMS_LIMIT_PROFILE_ID   = PF.CMS_LSP_LMT_PROFILE_ID " + 
						" AND PF.CMS_CUSTOMER_ID         = Spro.CMS_LE_SUB_PROFILE_ID " + 
						" AND XREF.status                ='REJECTED' " + 
						" AND XREF.CMS_LSP_SYS_XREF_ID   =MAPSS.CMS_LSP_SYS_XREF_ID " + 
						" AND MAPS.CHARGE_ID            IN " + 
						"  (SELECT MAX(MAPS2.CHARGE_ID) " + 
						"  FROM cms_limit_security_map maps2 " + 
						"  WHERE maps2.cms_lsp_appr_lmts_id = SCI.cms_lsp_appr_lmts_id " + 
						"  AND maps2.cms_collateral_id      =sec.cms_collateral_id " + 
						"  ) " + 
						" AND MAPSS.cms_lsp_appr_lmts_id=SCI.cms_lsp_appr_lmts_id " + 
						" AND SCI.FACILITY_SYSTEM      IN ('"+fcubsSystem+"','"+ubsSystem+"') " +  
						" AND spro.status               = 'ACTIVE' "+
						" AND SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID "+
						" AND SS.STATUS           <> 'DELETED' ";

				return (List) getJdbcTemplate().query(queryStr,new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List limitList = new ArrayList();

						List prevLimitId = new ArrayList();
						//String prevLimitId = "";
						String prevSecId = "";
						HashSet set = new HashSet();

						while (rs.next()) {
							String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
							String securityId = rs.getString("SOURCE_SECURITY_ID");

							LimitListSummaryItemBase curSummary = null;
							if (!prevLimitId.contains(cmsLimitId)) {
								curSummary = new LimitListSummaryItemBase();
								curSummary.setCmsLimitId(cmsLimitId);
								String limitId = rs.getString("LMT_ID");
								curSummary.setLimitId(limitId);
								String prodType = rs.getString("FACILITY_NAME");
								curSummary.setProdTypeCode(prodType);
								String facilityType = rs.getString("FACILITY_TYPE");
								curSummary.setFacilityTypeCode(facilityType);
								String lmtCountry = rs.getString("FACILITY_TYPE");
								curSummary.setLimitLoc(lmtCountry);
								String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
								if ("0".equals(outerLmtId)
										|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
									outerLmtId = null;
								}
								String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
								if (lmtCurrency != null) {
									curSummary.setCurrencyCode(lmtCurrency);
								}

								String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
								if (lmtApproveAmt != null) {
									curSummary.setApprovedAmount(lmtApproveAmt);
								}
								String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
								if (lmtActivateAmt != null) {
									curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
								}
								String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
								if (lmtDrawAmt != null) {
									curSummary.setDrawingAmount(lmtDrawAmt);
								}
								String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
								if (lmtOutstdAmt != null) {
									curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
								}
								String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
								if (actSeccov != null) {
									curSummary.setActualSecCoverage(actSeccov);
								}
								String isAdhocToSum = rs.getString("IS_ADHOC_TOSUM");
								if (isAdhocToSum != null) {
									curSummary.setIsAdhocToSum(isAdhocToSum);
								}
								String isAdhoc = rs.getString("IS_ADHOC");
								if (isAdhoc != null) {
									curSummary.setIsAdhoc(isAdhoc);
								}

								String adhocAmount = rs.getString("ADHOC_LMT_AMOUNT");
								if (adhocAmount != null) {
									curSummary.setAdhocAmount(adhocAmount);
								}
								
								String isSubLimit = rs.getString("LMT_TYPE_VALUE");
								if (isSubLimit != null) {
									curSummary.setIsSubLimit(isSubLimit);
								}
								String partyID = rs.getString("LSP_LE_ID");
								if (partyID != null) {
									curSummary.setPartyID(partyID);
								}
								String customerID = rs.getString("CMS_LE_SUB_PROFILE_ID");
								if (customerID != null) {
									curSummary.setCustomerID(customerID);
								}
								else if ("null"==customerID) {
									curSummary.setCustomerID("");
								}
								String partyName = rs.getString("LSP_SHORT_NAME");
								if (partyName != null) {
									curSummary.setPartyName(partyName);
								}
								String limitProfileId = rs.getString("CMS_LSP_LMT_PROFILE_ID");
								if (limitProfileId != null) {
									curSummary.setLimitProfileId(limitProfileId);
								}
								
								/*curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
								curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));*/
								limitList.add(curSummary);
								prevLimitId.add(cmsLimitId);
								prevSecId = "";
							} else {
								// limit id same as last added item

								for(int i =0 ; i<limitList.size();i++)
								{

									LimitListSummaryItemBase limit=(LimitListSummaryItemBase)limitList.get(i);
									if(limit.getCmsLimitId().equals(cmsLimitId))
									{
										curSummary = (LimitListSummaryItemBase) (limitList.get(i));
									}
								}
							}

							if ((securityId != null) && !securityId.equals(prevSecId)) {
								LimitListSecItemBase secItem = new LimitListSecItemBase();
								secItem.setSecurityId(rs.getString("CMS_COLLATERAL_ID"));
								String typeName = rs.getString("TYPE_NAME");
								secItem.setSecTypeName(typeName);
								String subtypeName = rs.getString("SUBTYPE_NAME");
								secItem.setSecSubtypeName(subtypeName);
								String lmtSecurityCoverage = rs.getString("LMT_SECURITY_COVERAGE");
								secItem.setLmtSecurityCoverage(lmtSecurityCoverage);
								secItem.setSecLocDesc(rs.getString("COLLATERAL_CODE"));
								curSummary.addSecItem(secItem);
								prevSecId = securityId;
								set.add(securityId);
							}
						}

						return limitList;
					}
				});
			}
			
			public List getRejectedLimitSummaryListByAA(String searchCriteria) throws LimitException {

				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				String fcubsSystem = bundle.getString("fcubs.systemName");
				String ubsSystem = bundle.getString("ubs.systemName");
				
				String partyName=""; String partyID="";
				String searchCriteriaList[] = searchCriteria.split(",");
				if("PartyName".equals(searchCriteriaList[1]))
					partyName = searchCriteriaList[0];
				else
					partyID = searchCriteriaList[0];
					
				String queryStr = "SELECT DISTINCT SCI.CMS_LSP_APPR_LMTS_ID, " + 
						"  XREF.status, " + 
						"  SCI.FACILITY_SYSTEM, " + 
						"  SCI.LMT_ID, " + 
						"  SCI.FACILITY_NAME, " + 
						"  SCI.FACILITY_TYPE, " + 
						"  SCI.LMT_OUTER_LMT_ID, " + 
						"  SCI.LMT_CRRNCY_ISO_CODE, " + 
						"  SCI.RELEASABLE_AMOUNT, " + 
						"  SCI.CMS_ACTIVATED_LIMIT, " + 
						"  SCI.TOTAL_RELEASED_AMOUNT, " + 
						"  SCI.CMS_OUTSTANDING_AMT, " + 
						"  SCI.CMS_REQ_SEC_COVERAGE, " + 
						"  SCI.IS_ADHOC_TOSUM, " + 
						"  SCI.IS_ADHOC, " + 
						"  SCI.ADHOC_LMT_AMOUNT, " + 
						"  SCI.LMT_TYPE_VALUE, " + 
						"  SEC.CMS_COLLATERAL_ID, " + 
						"  SEC.TYPE_NAME, " + 
						"  SEC.SUBTYPE_NAME, " + 
						"  SPRO.LSP_LE_ID, " + 
						"  SPRO.LSP_SHORT_NAME, " + 
						"  MAPS.LMT_SECURITY_COVERAGE, " +
						"  SS.SOURCE_SECURITY_ID, " +
						"  SEC.COLLATERAL_CODE, " + 
						"  SPRO.CMS_LE_SUB_PROFILE_ID, "+
						"  PF.CMS_LSP_LMT_PROFILE_ID " +
						"FROM CMS_SECURITY SEC, " + 
						"  CMS_SECURITY_SUB_TYPE SUB, " + 
						"  SECURITY_TYPE TYP, " + 
						"  SCI_LSP_APPR_LMTS SCI, " + 
						"  SCI_LSP_SYS_XREF XREF, " + 
						"  SCI_LSP_LMTS_XREF_MAP MAPSS, " + 
						"  CMS_LIMIT_SECURITY_MAP MAPS, " + 
						"  SCI_LSP_LMT_PROFILE PF, " + 
						"  SCI_LE_SUB_PROFILE SPRO, " + 
						"  CMS_SECURITY_SOURCE SS " +
						"WHERE SEC.SECURITY_SUB_TYPE_ID = SUB.SECURITY_SUB_TYPE_ID " + 
						" AND SUB.SECURITY_TYPE_ID       = TYP.SECURITY_TYPE_ID " + 
						" AND (MAPS.update_status_ind   <> 'D' " + 
						"OR MAPS.update_status_ind     IS NULL) " + 
						" AND SEC.CMS_COLLATERAL_ID      = MAPS.CMS_COLLATERAL_ID " + 
						" AND MAPS.CMS_LSP_APPR_LMTS_ID  = SCI.CMS_LSP_APPR_LMTS_ID " + 
						" AND SCI.CMS_LIMIT_PROFILE_ID   = PF.CMS_LSP_LMT_PROFILE_ID " + 
						" AND PF.CMS_CUSTOMER_ID         = Spro.CMS_LE_SUB_PROFILE_ID " + 
						" AND XREF.status                ='REJECTED' " + 
						" AND XREF.CMS_LSP_SYS_XREF_ID   =MAPSS.CMS_LSP_SYS_XREF_ID " + 
						" AND MAPS.CHARGE_ID            IN " + 
						"  (SELECT MAX(MAPS2.CHARGE_ID) " + 
						"  FROM cms_limit_security_map maps2 " + 
						"  WHERE maps2.cms_lsp_appr_lmts_id = SCI.cms_lsp_appr_lmts_id " + 
						"  AND maps2.cms_collateral_id      =sec.cms_collateral_id " + 
						"  ) " + 
						" AND MAPSS.cms_lsp_appr_lmts_id=SCI.cms_lsp_appr_lmts_id " + 
						" AND SCI.FACILITY_SYSTEM      IN ('"+fcubsSystem+"','"+ubsSystem+"') " + 
						" AND spro.status               = 'ACTIVE' "+
						" AND SEC.CMS_COLLATERAL_ID = SS.CMS_COLLATERAL_ID "+
						" AND SS.STATUS           <> 'DELETED' ";
				
				/*if(null!=partyName && !"".equals(partyName))
					queryStr=queryStr+" AND SPRO.LSP_SHORT_NAME like ? ";
				else if(null!=partyID && !"".equals(partyID))
					queryStr=queryStr+" AND SPRO.LSP_LE_ID = ?  ";*/
				if(null!=partyName && !"".equals(partyName)) {
					queryStr=queryStr+" AND SPRO.LSP_SHORT_NAME like ?";
					return executeQuery(queryStr,partyName.toUpperCase()+"%");
				}else  {
					queryStr=queryStr+" AND SPRO.LSP_LE_ID = ?  ";
					return executeQuery(queryStr,partyID.toUpperCase());
			}
		}	
		private List executeQuery(String queryStr,String param) {
			return (List) getJdbcTemplate().query(queryStr,new Object[]{param},new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List limitList = new ArrayList();

					List prevLimitId = new ArrayList();
					//String prevLimitId = "";
					String prevSecId = "";
					HashSet set = new HashSet();

					while (rs.next()) {
						String cmsLimitId = rs.getString("CMS_LSP_APPR_LMTS_ID");
						String securityId = rs.getString("SOURCE_SECURITY_ID");

						LimitListSummaryItemBase curSummary = null;
						if (!prevLimitId.contains(cmsLimitId)) {
							curSummary = new LimitListSummaryItemBase();
							curSummary.setCmsLimitId(cmsLimitId);
							String limitId = rs.getString("LMT_ID");
							curSummary.setLimitId(limitId);
							String prodType = rs.getString("FACILITY_NAME");
							curSummary.setProdTypeCode(prodType);
							String facilityType = rs.getString("FACILITY_TYPE");
							curSummary.setFacilityTypeCode(facilityType);
							String lmtCountry = rs.getString("FACILITY_TYPE");
							curSummary.setLimitLoc(lmtCountry);
							String outerLmtId = rs.getString("LMT_OUTER_LMT_ID");
							if ("0".equals(outerLmtId)
									|| String.valueOf(ICMSConstant.LONG_INVALID_VALUE).equals(outerLmtId)) {
								outerLmtId = null;
							}
							String lmtCurrency = rs.getString("LMT_CRRNCY_ISO_CODE");
							if (lmtCurrency != null) {
								curSummary.setCurrencyCode(lmtCurrency);
							}

							String lmtApproveAmt = rs.getString("RELEASABLE_AMOUNT");
							if (lmtApproveAmt != null) {
								curSummary.setApprovedAmount(lmtApproveAmt);
							}
							String lmtActivateAmt = rs.getString("CMS_ACTIVATED_LIMIT");
							if (lmtActivateAmt != null) {
								curSummary.setAuthorizedAmount(new Amount(Double.parseDouble(lmtActivateAmt), lmtCurrency));
							}
							String lmtDrawAmt = rs.getString("TOTAL_RELEASED_AMOUNT");
							if (lmtDrawAmt != null) {
								curSummary.setDrawingAmount(lmtDrawAmt);
							}
							String lmtOutstdAmt = rs.getString("CMS_OUTSTANDING_AMT");
							if (lmtOutstdAmt != null) {
								curSummary.setOutstandingAmount(new Amount(Double.parseDouble(lmtOutstdAmt), lmtCurrency));
							}
							String actSeccov = rs.getString("CMS_REQ_SEC_COVERAGE");
							if (actSeccov != null) {
								curSummary.setActualSecCoverage(actSeccov);
							}
							String isAdhocToSum = rs.getString("IS_ADHOC_TOSUM");
							if (isAdhocToSum != null) {
								curSummary.setIsAdhocToSum(isAdhocToSum);
							}
							String isAdhoc = rs.getString("IS_ADHOC");
							if (isAdhoc != null) {
								curSummary.setIsAdhoc(isAdhoc);
							}

							String adhocAmount = rs.getString("ADHOC_LMT_AMOUNT");
							if (adhocAmount != null) {
								curSummary.setAdhocAmount(adhocAmount);
							}
							
							String isSubLimit = rs.getString("LMT_TYPE_VALUE");
							if (isSubLimit != null) {
								curSummary.setIsSubLimit(isSubLimit);
							}
							String partyID = rs.getString("LSP_LE_ID");
							if (partyID != null) {
								curSummary.setPartyID(partyID);
							}
							String customerID = rs.getString("CMS_LE_SUB_PROFILE_ID");
							if (customerID != null) {
								curSummary.setCustomerID(customerID);
							}
							else if ("null"==customerID) {
								curSummary.setCustomerID("");
							}
							String partyName = rs.getString("LSP_SHORT_NAME");
							if (partyName != null) {
								curSummary.setPartyName(partyName);
							}
							String limitProfileId = rs.getString("CMS_LSP_LMT_PROFILE_ID");
							if (limitProfileId != null) {
								curSummary.setLimitProfileId(limitProfileId);
							}
							
							/*curSummary.setInnerLimitCount(rs.getInt("COUNT_INNER"));
							curSummary.setLinkSecCount(rs.getInt("COUNT_SEC"));*/
							limitList.add(curSummary);
							prevLimitId.add(cmsLimitId);
							prevSecId = "";
						} else {
							// limit id same as last added item

							for(int i =0 ; i<limitList.size();i++)
							{

								LimitListSummaryItemBase limit=(LimitListSummaryItemBase)limitList.get(i);
								if(limit.getCmsLimitId().equals(cmsLimitId))
								{
									curSummary = (LimitListSummaryItemBase) (limitList.get(i));
								}
							}
						}

						if ((securityId != null) && !securityId.equals(prevSecId)) {
							LimitListSecItemBase secItem = new LimitListSecItemBase();
							secItem.setSecurityId(rs.getString("CMS_COLLATERAL_ID"));
							String typeName = rs.getString("TYPE_NAME");
							secItem.setSecTypeName(typeName);
							String subtypeName = rs.getString("SUBTYPE_NAME");
							secItem.setSecSubtypeName(subtypeName);
							String lmtSecurityCoverage = rs.getString("LMT_SECURITY_COVERAGE");
							secItem.setLmtSecurityCoverage(lmtSecurityCoverage);
							secItem.setSecLocDesc(rs.getString("COLLATERAL_CODE"));
							curSummary.addSecItem(secItem);
							prevSecId = securityId;
							set.add(securityId);
						}
					}

					return limitList;
				}
			});
		}	
	//end santosh
				
			public void updateStageLineDetails(final String sourceRefNo,final String segment1Flag,final String prioritySecFlag,final String esatateTypeFlag,final String capitalFlag, final String unconFlag,final String xrefId,final String branchAllFlag,final String productAllFlag,final String currencyAllFlag, final String limitRestrFlag,final String sendToCore,
					final String status,final String action,final String coreRejectReason,final String udfDelete,final String commRealEstateTypeFlag,final String udfDelete2){
				String updateStageLine="update cms_stage_lsp_sys_xref set source_ref_no= ? , SEGMENT_1_FLAG=? , PRIORITY_SECTOR_FLAG=? ,"+ 
		"  ESTATE_TYPE_FLAG=? , IS_CAPITAL_MARKET_EXPOSER_FLAG=? , UNCONDI_CANCL_FLAG=? , BRANCH_ALLOWED_FLAG=? , PRODUCT_ALLOWED_FLAG=? , " +
		" CURRENCY_ALLOWED_FLAG=? , LIMIT_RESTRICTION_FLAG=? ,SENDTOCORE=?, status=? ," +
		" ACTION = ? , CORE_STP_REJECTED_REASON = ? , UDF_DELETE= ? ,COMM_REAL_ESTATE_TYPE_FLAG=?, UDF_DELETE_2= ? where cms_lsp_sys_xref_id=?";
				
				DefaultLogger.debug(this, "inside updateStageLineDetails");

				try {
					int executeUpdate=getJdbcTemplate().update(updateStageLine, new PreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, sourceRefNo);
							ps.setString(2, segment1Flag);
							ps.setString(3, prioritySecFlag);
							ps.setString(4, esatateTypeFlag);
							ps.setString(5, capitalFlag );
							ps.setString(6, unconFlag);
							ps.setString(7, branchAllFlag);
							ps.setString(8, productAllFlag);
							ps.setString(9, currencyAllFlag);
							ps.setString(10, limitRestrFlag);
							ps.setString(11, sendToCore);
							ps.setString(12, status);
							ps.setString(13, action);
							ps.setString(14, coreRejectReason);
							ps.setString(15, udfDelete);
							ps.setString(16, commRealEstateTypeFlag);
							ps.setString(17, udfDelete2);

							ps.setString(18, xrefId);
							
							
							
						}
					});
					DefaultLogger.debug(this, "updated STageLineDetails");
					
				} catch (DBConnectionException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  updateStageLineDetails:"+e.getMessage());
				} catch (NoSQLStatementException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  updateStageLineDetails:"+e.getMessage());
				}
				DefaultLogger.debug(this, "completed updateStageLineDetails");
				
			}

	
			public void updateFCUBSDataLogDuringCoreDown(String sourceRef,String serialNo,
					String rejectReason,String fcubsStatusSuccess) throws Exception {

				
				DefaultLogger.debug(this,"Starting updateFCUBSDataLogDuringCoreDown updateFCUBSDataLog......"+fcubsStatusSuccess);
				try{
					if(fcubsStatusSuccess.equalsIgnoreCase(ICMSConstant.FCUBS_STATUS_SUCCESS)){

						String queryStr = "update CMS_FCUBSDATA_LOG set status = '"+fcubsStatusSuccess+"' where SOURCE_REF_NO = '"+sourceRef+"'";
						getJdbcTemplate().update(queryStr);
						if(null!= serialNo && !"".equalsIgnoreCase(serialNo)) {

							String queryForSerialNo = "update CMS_FCUBSDATA_LOG set SERIALNO = '"+serialNo+"' where SOURCE_REF_NO  = '"+sourceRef+"'";
							 getJdbcTemplate().update(queryForSerialNo);

						}

					}

					if(fcubsStatusSuccess.equalsIgnoreCase(ICMSConstant.FCUBS_STATUS_REJECTED)){


							String queryStr = "update CMS_FCUBSDATA_LOG set ERRORDESC = '"+rejectReason.replaceAll("'", "''")+"' , status = '"+fcubsStatusSuccess+"' where SOURCE_REF_NO  = '"+sourceRef+"'";
							getJdbcTemplate().update(queryStr);

						

					}


				}
				
				catch(Exception e){
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob Exception......"+e.getMessage());
					e.printStackTrace();
					throw e;
				}

				

			}

			
			public List<String> getlmtId(long limitProfileId,String facilitySystem, String lineNumbers){
				String fetchLmtId=" SELECT facility.cms_lsp_appr_lmts_id FROM sci_lsp_appr_lmts facility "
										+" WHERE  facility.CMS_LIMIT_STATUS='ACTIVE' and "
										+" facility.cms_limit_profile_id = '"+limitProfileId+"' AND   facility.facility_system IN ("+facilitySystem+")   ";
				
				
				if(lineNumbers != null && lineNumbers.length() > 0) {
					fetchLmtId += " AND  " + lineNumbers;
				}
				System.out.println("LimitDAO.java=>fetchLmtId SQL=>"+fetchLmtId);
				System.out.println("LimitDAO.java=>Inside dao1.getlmtId(limitProfileId, facilitySystem, lineNumbers)=>SQL Query=>"+fetchLmtId);
				List lmtIdList=new ArrayList<String>();
						DefaultLogger.debug(this, "inside getlmtId");

						try {
							List lmtIdQueryList=getJdbcTemplate().queryForList(fetchLmtId);
							for (int i = 0; i < lmtIdQueryList.size(); i++) {
								Map  map = (Map) lmtIdQueryList.get(i);
//							
								String lmtId= map.get("CMS_LSP_APPR_LMTS_ID").toString();
								lmtIdList.add(lmtId);
							}
							
						
						} catch (Exception e) {
							System.out.println("Exception in LimitDAO.java=> List<String> getlmtId(limitProfileId, facilitySystem, lineNumbers)=>e=>"+e);
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getlmtId:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed getlmtId");
						System.out.println("LimitDAO.java=>completed getlmtId()=>lmtIdList=>"+lmtIdList);
						
						return lmtIdList;
			}
			
			public void updateStageActualLine(final String available, final String status, final Date newExtendedNextReviewDate,Map<String, String> sysXrefIdmap,String name){
				
				String availableExpiryStage="update cms_stage_lsp_sys_xref set source_ref_no= ? , status=? ," +
				" ACTION = ? ,AVAILABLE= ? ,DATE_OF_RESET=?  where cms_lsp_sys_xref_id=?";
				String availableExpiryActual="update sci_lsp_sys_xref set source_ref_no= ? , status=? ," +
				" ACTION = ? ,AVAILABLE= ? ,DATE_OF_RESET=?  where cms_lsp_sys_xref_id=?";
				
				String expiryStage="update cms_stage_lsp_sys_xref set source_ref_no= ? , status=? ," +
				" ACTION = ? ,DATE_OF_RESET= ? where cms_lsp_sys_xref_id=?";
				String expiryActual="update sci_lsp_sys_xref set source_ref_no= ? , status=? ," +
				" ACTION = ? ,DATE_OF_RESET=?  where cms_lsp_sys_xref_id=?";
				
				String availableStage="update cms_stage_lsp_sys_xref set source_ref_no= ? ,  status=? ," +
				" ACTION = ? ,AVAILABLE= ? where cms_lsp_sys_xref_id=?";
				String availableActual="update sci_lsp_sys_xref set source_ref_no= ? , status=? ," +
				" ACTION = ? ,AVAILABLE= ? where cms_lsp_sys_xref_id=?";
				
						DefaultLogger.debug(this, "inside updateStageActualLine");
						
						try {
							
							if("availableActual".equals(name)){
								
								for(Entry<String, String> entrySet : sysXrefIdmap.entrySet()){
									final String xrefId = entrySet.getKey();
									String[] split = entrySet.getValue().split(",");
									final String stageXrefId = split[0];
									final String sourceRefNo = split[1];
									final String action = split[2];
								int executeUpdate=getJdbcTemplate().update(availableActual, new PreparedStatementSetter() {
										public void setValues(PreparedStatement ps) throws SQLException {
											ps.setString(1, sourceRefNo);
											ps.setString(2, status);
											ps.setString(3, action);
											ps.setString(4, available);
											ps.setString(5, xrefId);
											
										}
									});
								
								 executeUpdate=getJdbcTemplate().update(availableStage, new PreparedStatementSetter() {
									public void setValues(PreparedStatement ps) throws SQLException {
										ps.setString(1, sourceRefNo);
										ps.setString(2, status);
										ps.setString(3, action);
										ps.setString(4, available);
										ps.setString(5, stageXrefId);
										
									}
								});
								}
							}else if("expiryActual".equals(name)){
								
								for(Entry<String, String> entrySet : sysXrefIdmap.entrySet()){
									final String xrefId = entrySet.getKey();
									String[] split = entrySet.getValue().split(",");
									final String stageXrefId = split[0];
									final String sourceRefNo = split[1];
									final String action = split[2];
								int executeUpdate=getJdbcTemplate().update(expiryActual, new PreparedStatementSetter() {
										public void setValues(PreparedStatement ps) throws SQLException {
											ps.setString(1, sourceRefNo);
											ps.setString(2, status);
											ps.setString(3, action);
											ps.setDate(4,new java.sql.Date(newExtendedNextReviewDate.getTime()));
											ps.setString(5, xrefId);
											
										}
									});
								
								 executeUpdate=getJdbcTemplate().update(expiryStage, new PreparedStatementSetter() {
									public void setValues(PreparedStatement ps) throws SQLException {
										ps.setString(1, sourceRefNo);
										ps.setString(2, status);
										ps.setString(3, action);
										ps.setDate(4,new java.sql.Date(newExtendedNextReviewDate.getTime()));
										ps.setString(5, stageXrefId);
										
									}
								});
								}
							}else if("availableExpiryActual".equals(name)){
							//	for(int i=0; i<sysXrefIdmap.size(); i ++){
								//Set<Entry<String, String>> entrySet = sysXrefIdmap.entrySet();
								
								for(Entry<String, String> entrySet : sysXrefIdmap.entrySet()){
									final String xrefId = entrySet.getKey();
									String[] split = entrySet.getValue().split(",");
									final String stageXrefId = split[0];
									final String sourceRefNo = split[1];
									final String action = split[2];
								int executeUpdate=getJdbcTemplate().update(availableExpiryActual, new PreparedStatementSetter() {
										public void setValues(PreparedStatement ps) throws SQLException {
											ps.setString(1, sourceRefNo);
											ps.setString(2, status);
											ps.setString(3, action);
											ps.setString(4, available);
											ps.setDate(5, new java.sql.Date(newExtendedNextReviewDate.getTime()));
											ps.setString(6, xrefId);
											
										}
									});
								
								 executeUpdate=getJdbcTemplate().update(availableExpiryStage, new PreparedStatementSetter() {
									public void setValues(PreparedStatement ps) throws SQLException {
										ps.setString(1, sourceRefNo);
										ps.setString(2, status);
										ps.setString(3, action);
										ps.setString(4, available);
										ps.setDate(5, new java.sql.Date(newExtendedNextReviewDate.getTime()));
										ps.setString(6, stageXrefId);
										
									}
								});
								}
							}
									DefaultLogger.debug(this, "updated updateStageActualLine");
					
						}catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  updateStageActualLine:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed updateStageActualLine");
			
				}

			
			
			public String getProductIdList(String productCodes){
				String fetchProductId="select id from cms_product_master where status='ACTIVE' and deprecated='N' and product_code in ("+productCodes+") ";
				
				String productIdList="";
				DefaultLogger.debug(this, "inside getProductIdList");

				try {
					List productIdQueryList=getJdbcTemplate().queryForList(fetchProductId);
					for (int i = 0; i < productIdQueryList.size(); i++) {
						Map  map = (Map) productIdQueryList.get(i);
//					
						String productId= map.get("id").toString();
						
						if(i==productIdQueryList.size()-1){
						productIdList=productIdList+productId.trim();
						}else{
						productIdList=productIdList+productId.trim()+",";	
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getProductIdList:"+e.getMessage());
				}
				DefaultLogger.debug(this, "completed getProductIdList");
				
				return productIdList;
			}

			
			public String getStatusFromFcubs(String sourceRefNo){
				String statusQuery=" select distinct status from cms_fcubsdata_log where source_ref_no='"+sourceRefNo+"'";
				String status="";
						DefaultLogger.debug(this, "inside getStatusFromFcubs");

						try {
							List statusQueryList=getJdbcTemplate().queryForList(statusQuery);
							for (int i = 0; i < statusQueryList.size(); i++) {
								Map  map = (Map) statusQueryList.get(i);
								status= map.get("status").toString();
								return status;
							}
							
						
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getStatusFromFcubs:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed getStatusFromFcubs");
						return status;
						
						}
			
			
			public Map<String,String >  getRejectedFcubsMap(String sourceRefNo){
				String rejectQ=" select distinct source_ref_no,errordesc from cms_fcubsdata_log where source_ref_no in ( "+sourceRefNo+" )";
				Map<String,String > rejectedMap = new HashMap<String,String >();
						DefaultLogger.debug(this, "inside getRejectedFcubsMap");

						try {
							List statusQueryList=getJdbcTemplate().queryForList(rejectQ);
							for (int i = 0; i < statusQueryList.size(); i++) {
								Map  map = (Map) statusQueryList.get(i);
								String refNo= map.get("source_ref_no").toString();
								String errordesc= map.get("errordesc").toString();
								rejectedMap.put(refNo, errordesc);
							}
							
						
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getRejectedFcubsMap:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed getRejectedFcubsMap");
						return rejectedMap;
						
						}
			
			public Map<String,String >  getSuccessFcubsMap(String sourceRefNo){
				String successQ=" select distinct source_ref_no,serialno from cms_fcubsdata_log where source_ref_no in ( "+sourceRefNo+" )";
				Map<String,String > successMap = new HashMap<String,String >();
						DefaultLogger.debug(this, "inside getSuccessFcubsMap");

						try {
							List statusQueryList=getJdbcTemplate().queryForList(successQ);
							for (int i = 0; i < statusQueryList.size(); i++) {
								Map  map = (Map) statusQueryList.get(i);
								String refNo= map.get("source_ref_no").toString();
								String serialNo= map.get("serialno").toString();
								successMap.put(refNo, serialNo);
								
							}
							
						
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getSuccessFcubsMap:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed getSuccessFcubsMap");
						return successMap;
						
						}
			
			public Map<String,String >  getSuccessPSRMap(String sourceRefNo){
				String successQ=" select distinct source_ref_no,serialno from CMS_PSRDATA_LOG where source_ref_no in ( "+sourceRefNo+" )";
				Map<String,String > successMap = new HashMap<String,String >();
						DefaultLogger.debug(this, "inside getSuccessPSRMap");

						try {
							List statusQueryList=getJdbcTemplate().queryForList(successQ);
							for (int i = 0; i < statusQueryList.size(); i++) {
								Map  map = (Map) statusQueryList.get(i);
								String refNo= map.get("source_ref_no").toString();
								String serialNo= map.get("serialno").toString();
								successMap.put(refNo, serialNo);
								
							}
							
						
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getSuccessPSRMap:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed getSuccessPSRMap");
						return successMap;
						
			}
			
			public Map<String,String >  getRejectedPSRMap(String sourceRefNo){
				String rejectQ=" select distinct source_ref_no,errordesc from CMS_PSRDATA_LOG where source_ref_no in ( "+sourceRefNo+" )";
				Map<String,String > rejectedMap = new HashMap<String,String >();
						DefaultLogger.debug(this, "inside getRejectedFcubsMap");

						try {
							List statusQueryList=getJdbcTemplate().queryForList(rejectQ);
							for (int i = 0; i < statusQueryList.size(); i++) {
								Map  map = (Map) statusQueryList.get(i);
								String refNo= map.get("source_ref_no").toString();
								String errordesc= map.get("errordesc").toString();
								rejectedMap.put(refNo, errordesc);
							}
							
						
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getRejectedPSRMap:"+e.getMessage());
						}
						DefaultLogger.debug(this, "completed getRejectedPSRMap");
						return rejectedMap;
						
						}
			
			public BigDecimal getSubLimitReleasedAmt(String lmtId,String system) {
				BigDecimal maxReleasedAmount = new BigDecimal("0");
				String	queryStr=" select max(TOTAL_RELEASED_AMOUNT) as releasedAmt from sci_lsp_appr_lmts where LMT_TYPE_VALUE='Yes' and "+
					" cms_limit_status='ACTIVE' and main_facility_id=? and  FACILITY_SYSTEM IN (?)";	
				
				DBUtil curUtil = null;
				ResultSet rs = null;
				List<BigDecimal> releasedAmount=new ArrayList<BigDecimal>();
				try {
					//BigDecimal releasedAmount = new BigDecimal("0");
					List amtForList = getJdbcTemplate().queryForList(queryStr, new Object[] {lmtId, system});
					for (int i = 0; i < amtForList.size(); i++) {
						Map  map = (Map) amtForList.get(i);
						String releasedAmt="0";
						if(null!=map.get("releasedAmt")){
						 releasedAmt= map.get("releasedAmt").toString();
						}
						maxReleasedAmount=new BigDecimal(releasedAmt);
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getSubLimitReleasedAmt:"+e.getMessage());
				}
				DefaultLogger.debug(this, "completed getSubLimitReleasedAmt");
				return maxReleasedAmount;
			}
			
			
			
			
			public String getSumOfSancAmount(String llpId) {
			    
				String sql="select sum(cms_req_sec_coverage) as Sum_Of_SancAmount "+
						"from SCI_LSP_APPR_LMTS "+
						"where cms_limit_status='ACTIVE' and facility_type='FUNDED' and "+
						"cms_limit_profile_id=(select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id=?)";

			    String sumOfSancAmount = (String) getJdbcTemplate().queryForObject(
			            sql, new Object[] { llpId }, String.class);

			    return sumOfSancAmount;
			}
			
			public String getSumOfSancAmountForNonFund(String llpId) {
			    
				String sql="select sum(cms_req_sec_coverage) as Sum_Of_SancAmount "+
						"from SCI_LSP_APPR_LMTS "+
						"where cms_limit_status='ACTIVE' and facility_type='NON_FUNDED' and "+
						"cms_limit_profile_id=(select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id=?)";

			    String sumOfSancAmount = (String) getJdbcTemplate().queryForObject(
			            sql, new Object[] { llpId }, String.class);

			    return sumOfSancAmount;
			}
				
			public List getFacilityLimitId() {
				ArrayList releaseStageList = new ArrayList();
				ArrayList facilityIdList = new ArrayList();
				ArrayList facilityIdListForCompare=new ArrayList();
				DBUtil dbUtil = null;
				DBUtil dbUtil1 = null;
				ResultSet rs=null;
				ResultSet rs1=null;
				
				try {
						dbUtil=new DBUtil();
						dbUtil.setSQL("SELECT SYSTEM_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH FROM STAGE_RELEASELINEDET_UPLOAD WHERE FILE_ID=("+
								"SELECT STAGING_REFERENCE_ID FROM TRANSACTION WHERE TRANSACTION_TYPE='RLD_UPLOAD' AND STATUS LIKE 'PENDING%')");
						rs = dbUtil.executeQuery();	
						if(rs!=null)
						{
							while(rs.next())
							{
								ArrayList releaseLineDetails = new ArrayList();
								releaseLineDetails.add(rs.getString("SYSTEM_ID"));
								releaseLineDetails.add(rs.getString("LINE_NO"));
								releaseLineDetails.add(rs.getString("SERIAL_NO"));
								releaseLineDetails.add(rs.getString("LIAB_BRANCH"));
								
								releaseStageList.add(releaseLineDetails);
							}
						}
						
						dbUtil1=new DBUtil();
						if(releaseStageList.size() > 0) {
						for(int i=0;i<releaseStageList.size();i++) {
							facilityIdList= (ArrayList) releaseStageList.get(i);
							//for(int j=0;j<facilityIdList.size();j++) {
							dbUtil1.setSQL("select LMT_ID from SCI_LSP_APPR_LMTS WHERE LMT_ID=" + 
								"(" + 
								"select LMT_ID from SCI_LSP_APPR_LMTS WHERE CMS_LSP_APPR_LMTS_ID=" + 
								"(" + 
								"select CMS_LSP_APPR_LMTS_ID from SCI_LSP_LMTS_XREF_MAP where CMS_LSP_SYS_XREF_ID IN (" + 
								"SELECT CMS_LSP_SYS_XREF_ID FROM  SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID='"+facilityIdList.get(0)+"' "
										+ " AND LINE_NO ='"+facilityIdList.get(1)+"' AND SERIAL_NO='"+facilityIdList.get(2)+"' AND LIAB_BRANCH='"+facilityIdList.get(3)+"'" + 
								")))");
							rs1 = dbUtil1.executeQuery();	
						//}
						//}
						if(rs1!=null) {
							while(rs1.next())
							{
								facilityIdListForCompare.add(rs1.getString("LMT_ID"));
							}
						}
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
				finalize(dbUtil1,rs1);
				return facilityIdListForCompare;
			}

			

			public String getActiveProductCodeFromId(String productId)throws SearchDAOException {

				DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+productId+" getActiveProductCodeFromId");
				String queryStr = "SELECT product_code from CMS_PRODUCT_MASTER where status='ACTIVE' and deprecated='N' and id = ?";
				try {

				
					return (String) getJdbcTemplate().query(queryStr, new Object[]{(productId)},
							new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								return rs.getString(1);
							}
							return "0";
						}
					});
					
					
				}
				catch (Exception ex) {
					ex.printStackTrace();
					throw new SearchDAOException();
				}
				
			}

			
			@Override
			public String getChecklistId(long limitProfileID) {
					String queryStr = "select checklist_id from cms_checklist where cms_lsp_lmt_profile_id=? and CATEGORY='REC' ";
					System.out.println("<<<<<<<<getChecklistId>>>>>>>>>>>sql: "+queryStr);
						
					return (String) getJdbcTemplate().query(queryStr, new Object[]{(limitProfileID)},
							new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								return rs.getString(1);
							}
							return "";
						}
					});
					
				}
				

			@Override
			public void updateChecklistDetails(String checkListId ,String todayYear ,String ramRatingYear) {
				String queryStr="";
				try{
				if(todayYear.equals(ramRatingYear)){
				 queryStr = "update cms_checklist_item set status='RECEIVED' where checklist_id='"+checkListId+"' and statement_type='RAM_RATING'";
				 getJdbcTemplate().update(queryStr);
				}
				else{
				queryStr = "update cms_checklist_item set status='AWAITING' where checklist_id='"+checkListId+"' and statement_type='RAM_RATING'";
			    getJdbcTemplate().update(queryStr);
				}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
			@Override
			public void updateRAMChecklistDetails(String checkListId) {
				String queryStr="";
				try{
					 queryStr = "update cms_checklist_item set status='RECEIVED' where checklist_id='"+checkListId+"' and statement_type='RAM_RATING' AND STATUS='AWAITING'";
					 System.out.println("<<<<<<<<<<updateRAMChecklistDetails>>>>>>>>>>>>"+queryStr);
					 getJdbcTemplate().update(queryStr);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			@Override
			public String getChecklistIdByPartyId(String partyId) {
				String queryStr = "select checklist_id from cms_checklist where cms_lsp_lmt_profile_id=(select cms_lsp_lmt_profile_id  from sci_lsp_lmt_profile  where llp_le_id=?)";
				
				return (String) getJdbcTemplate().query(queryStr, new Object[]{(partyId)},
						new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString(1);
						}
						return "";
					}
				});
			}
			
			@Override
			public String getLimitProfileID(String partyID) {
			
					String queryStr = "SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE where LLP_LE_ID=? ";
						
					return (String) getJdbcTemplate().query(queryStr, new Object[]{(partyID)},
							new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								return rs.getString(1);
							}
							return "";
						}
					});
					
				}
			
			@Override
			public String getCustomerFyClosure(long limitProfileID) {
			
					String queryStr = "select CUSTOMER_FY_CLOUSER from sci_le_cri where CMS_LE_MAIN_PROFILE_ID="
							+ "(select CMS_LE_MAIN_PROFILE_ID from sci_le_main_profile where lmp_le_id="
							+ "(select LLP_LE_ID from SCI_LSP_LMT_PROFILE where cms_lsp_lmt_profile_id=?))";
						
					return (String) getJdbcTemplate().query(queryStr, new Object[]{(limitProfileID)},
							new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
							if (rs.next()) {
								return rs.getString(1);
							}
							return "";
						}
					});
					
				}



			
	public OBDocumentItem getAllRamratingDocument() {
		String sql = "select * from cms_document_globallist where document_description like '%RAM%' and category='REC' and statement_type='RAM_RATING' AND STATUS='ENABLE' ";
		System.out.println("<<<<<<OBDocumentItem>>>>>>>" + sql);
		;
		OBDocumentItem resultList = new OBDocumentItem();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(100);

		try {
			resultList = (OBDocumentItem) getJdbcTemplate().query(sql, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					OBDocumentItem col = null;
					if (rs.next()) {
						col = processGlobalDocument(rs);
					}
					return col;
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return resultList;
	}

	private OBDocumentItem processGlobalDocument(ResultSet rs) throws SQLException {
		OBDocumentItem documentItem = new OBDocumentItem();

		documentItem.setItemID(rs.getLong("DOCUMENT_ID"));
		documentItem.setItemDesc(rs.getString("DOCUMENT_DESCRIPTION"));
		documentItem.setItemCode(rs.getString("DOCUMENT_CODE"));
		documentItem.setExpiryDate(rs.getDate("EXPIRY_DATE"));
		documentItem.setStatementType(rs.getString("STATEMENT_TYPE"));

		return documentItem;
	}
	
	public String getDocSeqId() {
		int docId=0;
		try{
			String sql = "select CHECKLIST_ITEM_SEQ.nextval S from dual";
			docId = (int) getJdbcTemplate().queryForInt(sql);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		String seq= String.valueOf(docId);
		if(null!=seq) {
			int size=9-seq.length();
			for(int i=0;i<size;i++) {
				seq="0"+seq;
			}
		}
		return seq;
	}
	
	@Override
	public void insertRAMStatement(String customerFyClosure, String docId, OBDocumentItem ramRatingChecklist, String checkListId, String docRefId, String year) {
		String sql ="";
		int newRAMYear=Integer.valueOf(year)+1;
		Calendar cal = Calendar.getInstance();
		Date dateApplication=new Date();
		String date="";
		String newExpDate="";
		
		try{
			if ("December Ending".equals(customerFyClosure)) {
				date="01-Dec-"+newRAMYear;
				dateApplication=new Date(date);
				cal.setTime(dateApplication);
				cal.add(Calendar.MONTH, 8);
				Date newDate=cal.getTime();
				newExpDate=new SimpleDateFormat("dd/MM/yyyy").format(newDate);
				
				 sql = "INSERT INTO CMS_CHECKLIST_ITEM (DOC_ITEM_ID,DOC_DESCRIPTION,IS_PRE_APPROVE,IS_INHERITED,"
    					+ "IN_VAULT,IN_EXT_CUSTODY,IS_MANDATORY,STATUS,IS_DELETED,CHECKLIST_ID,DOCUMENT_ID,DOCUMENT_CODE,"
    					+ "DOC_ITEM_REF,STATEMENT_TYPE,DOCUMENTSTATUS,EXPIRY_DATE,RAMYEAR)" 
    					+"VALUES ('"+docId+"','"+ramRatingChecklist.getItemDesc()+"',"
    							+ "'N','N','N','N','N','AWAITING','N','"+checkListId+"','-999999999','"+ramRatingChecklist.getItemCode()+"','"+docRefId+"',"
    									+ "'RAM_RATING','ACTIVE',TO_DATE('"+newExpDate+"','DD-MM-YYYY'),'"+year+"')";
			}
			if ("March Ending".equals(customerFyClosure)) {
				date="01-Mar-"+newRAMYear;
				dateApplication=new Date(date);
				cal.setTime(dateApplication);
				cal.add(Calendar.MONTH, 8);
				Date newDate=cal.getTime();
				newExpDate=new SimpleDateFormat("dd/MM/yyyy").format(newDate);
				
				sql = "INSERT INTO CMS_CHECKLIST_ITEM (DOC_ITEM_ID,DOC_DESCRIPTION,IS_PRE_APPROVE,IS_INHERITED,"
    					+ "IN_VAULT,IN_EXT_CUSTODY,IS_MANDATORY,STATUS,IS_DELETED,CHECKLIST_ID,DOCUMENT_ID,DOCUMENT_CODE,"
    					+ "DOC_ITEM_REF,STATEMENT_TYPE,DOCUMENTSTATUS,EXPIRY_DATE,RAMYEAR)" 
    					+"VALUES ('"+docId+"','"+ramRatingChecklist.getItemDesc()+"',"
    							+ "'N','N','N','N','N','AWAITING','N','"+checkListId+"','-999999999','"+ramRatingChecklist.getItemCode()+"','"+docRefId+"',"
    									+ "'RAM_RATING','ACTIVE',TO_DATE('"+newExpDate+"','DD-MM-YYYY'),'"+year+"')";
			}
			 System.out.println("<<<<<<<<<<insertRAMStatement>>>>>>>>>>>>"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())+", SQL: "+sql);
			 getJdbcTemplate().update(sql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String getOldRAMYear(long limitProfileID) {
		String queryStr = "select RATING_YEAR from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID=?";
		
		return (String) getJdbcTemplate().query(queryStr, new Object[]{(limitProfileID)},
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return "";
			}
		});
	}
	
	public void disableRAMChecklistDetails(String checkListId) {
		String queryStr="";
		try{
			 queryStr = "update cms_checklist_item set IS_DELETED='Y' where checklist_id='"+checkListId+"' and statement_type='RAM_RATING' AND STATUS='RECEIVED' AND IS_DELETED='N'";
			 System.out.println("<<<<<<<<<<disableRAMChecklistDetails>>>>>>>>>>>>"+queryStr);
			 getJdbcTemplate().update(queryStr);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
public String  getFacLiabErrorDesc(String errorCode){
	String errorDesc="";
	String sql=" select distinct ERROR_DESC from FACILITY_LIABILITY_ERROR_CODE where ERROR_CODE ='"+errorCode+"'";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getFacLiabErrorDesc");

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{
										
										errorDesc=rs.getString("ERROR_DESC");
									
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getFacLiabErrorDesc:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getFacLiabErrorDesc");
						return errorDesc;
						
						}

public boolean getNewPendingFacilityDocCount(String apprLmtId) {
	int count=0;
	String query="select count(1) as count from transaction where transaction_type='CHECKLIST' and transaction_subtype like '%FAC_CHECKLIST%'"+
//		 " and status!='ACTIVE' and staging_reference_id in (select checklist_id from stage_checklist where cms_collateral_id='"+apprLmtId +"' and category='F')  and reference_id is null";
		 " and status NOT IN ('ACTIVE','CLOSED') and staging_reference_id in (select checklist_id from stage_checklist where cms_collateral_id='"+apprLmtId +"' and category='F')  and reference_id is null ";
	try
     {
	dbUtil = new DBUtil();
	dbUtil.setSQL(query);
	ResultSet rs = dbUtil.executeQuery();
	
	while(null!=rs && rs.next()){
		 count = Integer.parseInt(rs.getString("count"));
	}
	 if(count >0){
		 return true;
	 }
	 else {
		 return false;
	 }
     }
     
	catch(Exception e){
		DefaultLogger.debug(this, "Exception in method getNewPendingFacilityDocCount():"+e.getMessage());
		e.printStackTrace();
	}
	finally{
		 try {
			dbUtil.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return false;
}

	//SCOD CR
	public OBDocumentItem getSCODDocument() {
		String sql = "select * from cms_document_globallist where document_description like '%Deferral on SCOD Change%' and category='F' AND STATUS='ENABLE' AND DEPRECATED='N'";
		System.out.println("<<<<<<OBDocumentItem>>>>>>>" + sql);
		OBDocumentItem resultList = new OBDocumentItem();
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(100);

		try {
			resultList = (OBDocumentItem) getJdbcTemplate().query(sql, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					OBDocumentItem col = null;
					if (rs.next()) {
						col = processGlobalDocument(rs);
					}
					return col;
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultList;
	}
	
	public void insertSCODChecklistItem(String docId, OBDocumentItem scodDocument, long checkListId, String docRefId, String remarks) {
		String sql ="";
		try{
				 sql = "INSERT INTO CMS_CHECKLIST_ITEM (DOC_ITEM_ID,DOC_DESCRIPTION,IS_PRE_APPROVE,IS_INHERITED,"
    					+ "IN_VAULT,IN_EXT_CUSTODY,IS_MANDATORY,STATUS,IS_DELETED,CHECKLIST_ID,DOCUMENT_ID,DOCUMENT_CODE,"
    					+ "DOC_ITEM_REF,DOCUMENTSTATUS,DOC_DATE,DOCUMENTVERSION,UPDATEDBY,UPDATEDDATE,APPROVEDBY,APPROVEDDATE,REMARKS)" 
    					+"VALUES ('"+docId+"','"+scodDocument.getItemDesc()+"',"
    					+ "'N','N','N','N','N','AWAITING','N','"+checkListId+"','-999999999','"+scodDocument.getItemCode()+"','"+docRefId+"',"
    					+ " 'ACTIVE', to_timestamp(SYSDATE) , '0', 'SYSTEM', to_timestamp(SYSDATE), 'SYSTEM', to_timestamp(SYSDATE), '"+remarks+"' )";
		
			 System.out.println("<<<<<<<<<<insertRAMStatement>>>>>>>>>>>>"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())+", SQL: "+sql);
			 getJdbcTemplate().update(sql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String  getFacilityIdAndCamIdCombineCount(String FacilityId, String CamId){
		String count = "";
		String sql=" SELECT COUNT(*) AS CNT FROM SCI_LSP_APPR_LMTS " + 
				"WHERE CMS_LSP_APPR_LMTS_ID =? AND CMS_LIMIT_PROFILE_ID = ?";
		DBUtil dbUtil = null;
					ResultSet rs=null;
					DefaultLogger.debug(this, "inside getFacilityIdAndCamIdCombineCount");
					
					DefaultLogger.debug(this, "getFacilityIdAndCamIdCombineCount Query:"+sql+"...FacilityId-"+FacilityId+"...CamId-"+CamId);

							try {
								dbUtil=new DBUtil();
								dbUtil.setSQL(sql);
								dbUtil.setString(1,  FacilityId);
								dbUtil.setString(2,  CamId);
								 rs = dbUtil.executeQuery();
								 if(rs!=null)
									{
										while(rs.next())
										{
											count=rs.getString("CNT");
										}
									}
							} catch (Exception e) {
								e.printStackTrace();
								DefaultLogger.debug(this, "Exception in  getFacilityIdAndCamIdCombineCount:"+e.getMessage());
							}finally {
								finalize(dbUtil,rs);
							}
							DefaultLogger.debug(this, "completed getFacilityIdAndCamIdCombineCount");
							return count;
							
							}
	
	public void updateFlagsFlagForLMTSTP(ILimitTrxValue lmtTrxObj) {
		
		final ArrayList releaseLineDetails = new ArrayList();
		
		
		String sql="SELECT CMS_LSP_SYS_XREF_ID,LINE_NO,SERIAL_NO,LIAB_BRANCH,FACILITY_SYSTEM_ID " + 
				"FROM SCI_LSP_SYS_XREF " + 
				"WHERE CMS_LSP_SYS_XREF_ID IN " + 
				"  (SELECT CMS_LSP_SYS_XREF_ID " + 
				"  FROM SCI_LSP_LMTS_XREF_MAP " + 
				"  WHERE CMS_LSP_APPR_LMTS_ID = ? " + 
				"  )" + 
				"  and " + 
				"STATUS = 'SUCCESS' AND " + 
				"FACILITY_SYSTEM IN ('FCUBS-LIMITS','UBS-LIMITS')";
		

				 getJdbcTemplate().query(sql, new Object[]{lmtTrxObj.getLimit().getLimitID()}, new RowMapper() {

					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						ArrayList lineDetails = new ArrayList();
						lineDetails.add(rs.getString("CMS_LSP_SYS_XREF_ID"));
						lineDetails.add(rs.getString("FACILITY_SYSTEM_ID"));
						lineDetails.add(rs.getString("LINE_NO"));
						lineDetails.add(rs.getString("SERIAL_NO"));
						lineDetails.add(rs.getString("LIAB_BRANCH"));
						
						releaseLineDetails.add(lineDetails);
						 return null;
					}
				});	
				
				
				for(int i=0; i<releaseLineDetails.size() ; i++) {
					//System.out.println(lineIdList.get(i));
					 ArrayList<String> linedetailsList = new ArrayList();
					linedetailsList= (ArrayList) releaseLineDetails.get(i);
					Date d = DateUtil.getDate();
					String dateFormat = "yyMMdd";
					SimpleDateFormat s=new SimpleDateFormat(dateFormat);
					String date = s.format(d);
					
					String tempSourceRefNo="";
					 tempSourceRefNo=""+generateSourceSeqNo();
					 int len=tempSourceRefNo.length();
					 String concatZero="";
					if(null!=tempSourceRefNo && len!=5){
						
						for(int m=5;m>len;m--){
							concatZero="0"+concatZero;
						}

					}
					tempSourceRefNo=concatZero+tempSourceRefNo;
					
					String sorceRefNo=ICMSConstant.FCUBS_CAD+date+tempSourceRefNo;
					
					StringBuffer buf = new StringBuffer();
					buf.append("update SCI_LSP_SYS_XREF set STATUS = 'PENDING',SENDTOCORE = 'N', SENDTOFILE = 'Y', ACTION='MODIFY', SOURCE_REF_NO='"+sorceRefNo+"' where CMS_LSP_SYS_XREF_ID = ?");

					getJdbcTemplate().update(buf.toString(),
							new Object[]{linedetailsList.get(0)});
					
				final String  FACILITY_SYSTEM_ID= linedetailsList.get(1);
				final String LINE_NO= linedetailsList.get(2);
				final String SERIAL_NO=linedetailsList.get(3);
				final String LIAB_BRANCH= linedetailsList.get(4);

				String query = "UPDATE CMS_STAGE_LSP_SYS_XREF SET STATUS = 'PENDING',SENDTOCORE = 'N', SENDTOFILE = 'Y' , ACTION='MODIFY',  SOURCE_REF_NO='"+sorceRefNo+"' WHERE FACILITY_SYSTEM_ID=? AND "
						+ "LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=? and CMS_LSP_SYS_XREF_ID in (select max(CMS_LSP_SYS_XREF_ID) from CMS_STAGE_LSP_SYS_XREF where FACILITY_SYSTEM_ID=? AND "
						+"LINE_NO=? AND SERIAL_NO=? AND LIAB_BRANCH=?)";
				getJdbcTemplate().execute(query,new PreparedStatementCallback(){  
		        
		        	public Object doInPreparedStatement(PreparedStatement ps)  
		        	           throws SQLException {  
		        		//java.sql.Date sqlDate = new java.sql.Date(releaseAmountFile.getDateOfReset().getTime());
		        		ps.setString(1, FACILITY_SYSTEM_ID);//setDate(1, (java.sql.Date) releaseAmountFile.getDateOfReset());
		        		ps.setString(2,LINE_NO);	
		        		ps.setString(3,SERIAL_NO); 
						ps.setString(4,LIAB_BRANCH);
						ps.setString(5,FACILITY_SYSTEM_ID);
						ps.setString(6,LINE_NO);
						ps.setString(7,SERIAL_NO);
						ps.setString(8,LIAB_BRANCH);
						
		                return ps.execute();       
		            }  
		           }); 
				}
			
	}
	
	




public String getFacDetFromMaster(OBCreatefacilitylineFile oBCreatefacilitylineFile) {

	String facilityDetails="";
	String sql="select NEW_FACILITY_CODE,NEW_FACILITY_TYPE,LINE_NUMBER,PURPOSE from cms_facility_new_master where status='ACTIVE' and DEPRECATED='N' and " + 
			"   NEW_FACILITY_CATEGORY='"+ oBCreatefacilitylineFile.getFacilityCategory() +"'" +  
			"   and NEW_FACILITY_SYSTEM='"+ oBCreatefacilitylineFile.getSystem()+"' and NEW_FACILITY_NAME='"+oBCreatefacilitylineFile.getFacilityName()+"'";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getFacDetFromMaster sql:"+sql);

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{
										if("".equals(rs.getString("NEW_FACILITY_CODE")) || null==rs.getString("NEW_FACILITY_CODE")) {
											facilityDetails="";
										}else {
											facilityDetails=rs.getString("NEW_FACILITY_CODE");
										}
										if("".equals(rs.getString("NEW_FACILITY_TYPE")) || null==rs.getString("NEW_FACILITY_TYPE")) {
											facilityDetails=facilityDetails+"|"+"";
										}else {
										facilityDetails=facilityDetails+"|"+rs.getString("NEW_FACILITY_TYPE");
										}
										if("".equals(rs.getString("LINE_NUMBER")) || null==rs.getString("LINE_NUMBER")) {
										facilityDetails=facilityDetails+"|"+"";
										}else {
											facilityDetails=facilityDetails+"|"+rs.getString("LINE_NUMBER");	
										}
										if("".equals(rs.getString("PURPOSE")) || null==rs.getString("PURPOSE")) {
											facilityDetails=facilityDetails+"|"+"";	
										}else {
										facilityDetails=facilityDetails+"|"+rs.getString("PURPOSE");
										}
									
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getFacDetFromMaster:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getFacDetFromMaster facilityDetails:"+facilityDetails);
						return facilityDetails;					
	
}

public Set getCommonCode(String categoryCode) {
 
	Set<String> entryCode=new HashSet<String>();
	
	String sql="select ENTRY_CODE from COMMON_CODE_CATEGORY_ENTRY where ACTIVE_STATUS='1' and category_code='"+categoryCode+"'";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getCommonCode sql:"+sql);

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{
										entryCode.add(rs.getString("ENTRY_CODE"));
									
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getCommonCode:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getCommonCode ");
						return entryCode;					
	
}

public Set getCurrencySet() {
	 
	Set<String> currencyCode=new HashSet<String>();
	
	String sql="select CURRENCY_ISO_CODE from cms_forex where status='ENABLE'";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getCurrencySet sql:"+sql);

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{
										currencyCode.add(rs.getString("CURRENCY_ISO_CODE"));
									
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getCurrencySet:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getCurrencySet ");
						return currencyCode;					
	
}


public Set getValidPartySet(){
	
	Set<String> partySet=new HashSet<String>();
	
	String sql="select sub.LSP_LE_ID from" + 
			"  sci_le_sub_profile sub," + 
			"  SCI_LSP_LMT_PROFILE cam" + 
			"  where cam.llp_le_id=sub.LSP_LE_ID" + 
			"  and" + 
			"  sub.status='ACTIVE'";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getValidPartySet sql:"+sql);

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{
										partySet.add(rs.getString("LSP_LE_ID"));
									
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getValidPartySet:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getValidPartySet ");
						return partySet;	
}

public HashMap getFacMaster() {

	HashMap<String,String> facMaster=new HashMap<String,String>();
	
	String sql="select  NEW_FACILITY_CATEGORY,NEW_FACILITY_NAME,NEW_FACILITY_SYSTEM , NEW_FACILITY_CODE,NEW_FACILITY_TYPE,LINE_NUMBER,PURPOSE from cms_facility_new_master where status='ACTIVE' and DEPRECATED='N' and " + 
			"   (UPPER(NEW_FACILITY_TYPE) in ('FUNDED','NON_FUNDED') or UPPER(NEW_FACILITY_TYPE) like 'NON%FUNDED')";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getFacMaster sql:"+sql);

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{ 
										String key="";
										String value="";
										if("".equals(rs.getString("NEW_FACILITY_CATEGORY")) || null==rs.getString("NEW_FACILITY_CATEGORY")) {
											key="";
										}else {
											key=rs.getString("NEW_FACILITY_CATEGORY");
										}
										if("".equals(rs.getString("NEW_FACILITY_NAME")) || null==rs.getString("NEW_FACILITY_NAME")) {
											key=key+"|"+"";
										}else {
											key=key+"|"+rs.getString("NEW_FACILITY_NAME");
										}
										if("".equals(rs.getString("NEW_FACILITY_SYSTEM")) || null==rs.getString("NEW_FACILITY_SYSTEM")) {
											key=key+"|"+"";
										}else {
											key=key+"|"+rs.getString("NEW_FACILITY_SYSTEM");
										}
										
										if("".equals(rs.getString("NEW_FACILITY_CODE")) || null==rs.getString("NEW_FACILITY_CODE")) {
											value="";
										}else {
											value=rs.getString("NEW_FACILITY_CODE");
										}
										if("".equals(rs.getString("NEW_FACILITY_TYPE")) || null==rs.getString("NEW_FACILITY_TYPE")) {
											value=value+"|"+"";
										}else {
											value=value+"|"+rs.getString("NEW_FACILITY_TYPE");
										}
										if("".equals(rs.getString("LINE_NUMBER")) || null==rs.getString("LINE_NUMBER")) {
											value=value+"|"+"";
										}else {
											value=value+"|"+rs.getString("LINE_NUMBER");	
										}
										if("".equals(rs.getString("PURPOSE")) || null==rs.getString("PURPOSE")) {
											value=value+"|"+"";	
										}else {
											value=value+"|"+rs.getString("PURPOSE");
										}
									
										facMaster.put(key, value);
										//DefaultLogger.debug(this, "getFacMaster key value:"+key+" "+value);
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getFacMaster:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getFacMaster.");
						return facMaster;					
	
}

public List getPartySystemId(String system, String partyId) throws SearchDAOException {
	String sql = "SELECT CMS_LE_OTHER_SYS_CUST_ID FROM SCI_LE_OTHER_SYSTEM WHERE CMS_LE_MAIN_PROFILE_ID in (select CMS_LE_MAIN_PROFILE_ID from sci_le_main_profile where lmp_le_id='"+partyId+"') AND " + 
			"CMS_LE_SYSTEM_NAME='"+system+"'";

	DefaultLogger.debug(this, "inside getPartySystemId: sql"+sql);
	List resultList = getJdbcTemplate().query(sql, new RowMapper() {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("CMS_LE_OTHER_SYS_CUST_ID");
		}
	});

	DefaultLogger.debug(this, "completed getPartySystemId: sql"+sql);
	return resultList;
}

public BigDecimal getfundedNonfunAmt(String facilityType,String partyId){
	String sql="";
	BigDecimal amt=new BigDecimal(0);
	if("funded".equals(facilityType)) {
	 sql = "select TOTAL_FUNDED_LIMIT from sci_le_main_profile where lmp_le_id='"+partyId+"'";
	}else if ("nonfunded".equals(facilityType)) {
		 sql = "select TOTAL_NON_FUNDED_LIMIT from sci_le_main_profile where lmp_le_id='"+partyId+"'";	
	}
	DefaultLogger.debug(this, "inside getfundedNonfunAmt: sql"+sql);
	DBUtil dbUtil = null;
	ResultSet rs=null;

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(sql);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{
							amt=rs.getBigDecimal(1);
						
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getfundedNonfunAmt:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
				
	DefaultLogger.debug(this, "completed getfundedNonfunAmt.");
	return amt;
}

public List getSanctionedAmtOfLmt(String partyId,String facilityType) {

	List<String> amtCurrency=new ArrayList<String>();
	
	String sql="select CMS_REQ_SEC_COVERAGE,LMT_CRRNCY_ISO_CODE from SCI_LSP_APPR_LMTS where cms_limit_status = 'ACTIVE' " + 
			" AND lmt_type_value='No'  and UPPER(facility_type) in ("+facilityType+") and  cms_limit_profile_id in "+
			"(select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id='"+partyId+"')";
	DBUtil dbUtil = null;
				ResultSet rs=null;
				DefaultLogger.debug(this, "inside getSanctionedAmtOfLmt sql:"+sql);

						try {
							dbUtil=new DBUtil();
							dbUtil.setSQL(sql);
							 rs = dbUtil.executeQuery();
							 if(rs!=null)
								{
									while(rs.next())
									{ 
										String value="";
										
										if("".equals(rs.getString("CMS_REQ_SEC_COVERAGE")) || null==rs.getString("CMS_REQ_SEC_COVERAGE")) {
											value="0";
										}else {
											value=rs.getString("CMS_REQ_SEC_COVERAGE");	
										}
										if("".equals(rs.getString("LMT_CRRNCY_ISO_CODE")) || null==rs.getString("LMT_CRRNCY_ISO_CODE")) {
											value=value+"|"+"";	
										}else {
											value=value+"|"+rs.getString("LMT_CRRNCY_ISO_CODE");
										}
									
										amtCurrency.add(value);
										
									}
								}
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.debug(this, "Exception in  getSanctionedAmtOfLmt:"+e.getMessage());
						}finally {
							finalize(dbUtil,rs);
						}
						DefaultLogger.debug(this, "completed getSanctionedAmtOfLmt.");
						return amtCurrency;					
	
}

public List getImageIdList(String code,DigitalLibraryRequestDTO digitalLibraryRequestDTO) throws SearchDAOException {
	
	StringBuffer queryStr = new StringBuffer();
	
	queryStr.append("SELECT " +
	"distinct UPIMG.IMG_ID img_id , " +
	"UPIMG.HCP_FILENAME hcp_filename "+		


	
	"FROM CMS_UPLOADED_IMAGES UPIMG, "+
	"SCI_LE_SUB_PROFILE SUBPR, "+
	"sci_le_other_system OTHSYS  ");
	
	if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate()
			|| null !=digitalLibraryRequestDTO.getDocFromDate() || null !=digitalLibraryRequestDTO.getDocToDate()
			|| null !=digitalLibraryRequestDTO.getDocFromAmt() || null !=digitalLibraryRequestDTO.getDocToAmt() || null !=digitalLibraryRequestDTO.getDocCode()) {
	queryStr.append(",sci_lsp_lmt_profile lmtPr ");
	queryStr.append(",CMS_CHECKLIST chklst ");
	}
	
	if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate()
			|| null !=digitalLibraryRequestDTO.getDocFromDate() || null !=digitalLibraryRequestDTO.getDocToDate()
			|| null !=digitalLibraryRequestDTO.getDocFromAmt() || null !=digitalLibraryRequestDTO.getDocToAmt()
			|| null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
		queryStr.append(",CMS_CHECKLIST_ITEM chklstitm ");
	}
	
	if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate() 
			|| null != digitalLibraryRequestDTO.getDocFromAmt() || null != digitalLibraryRequestDTO.getDocToAmt()
			|| null != digitalLibraryRequestDTO.getDocFromDate() || null != digitalLibraryRequestDTO.getDocToDate()
			|| null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()){
		queryStr.append(",cms_image_tag_map imgmap");
		queryStr.append(",cms_image_tag_details imgdtl ");
		//queryStr.append("cms_image_tag_map map ");
	}
	
	
	
	if(null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
	/*queryStr.append(",cms_image_tag_map imgmap, ");
	queryStr.append("cms_image_tag_details imgdtl, ");
	queryStr.append("CMS_CHECKLIST_ITEM chklstitm, ");*/
	queryStr.append(",transaction trans ");
	}
	
	if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
		queryStr.append(" ,SCI_LE_MAIN_PROFILE MAINPR ");
		
	}
	
	queryStr.append("where UPIMG.CUST_ID = SUBPR.LSP_LE_ID ");
	queryStr.append(" AND OTHSYS.CMS_LE_MAIN_PROFILE_ID = SUBPR.CMS_LE_MAIN_PROFILE_ID ");
	
	queryStr.append(" and UPIMG.IMG_DEPRICATED != 'Y' ");
	
	
	
	if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate()
			|| null !=digitalLibraryRequestDTO.getDocFromDate() || null !=digitalLibraryRequestDTO.getDocToDate()
			|| null !=digitalLibraryRequestDTO.getDocFromAmt() || null !=digitalLibraryRequestDTO.getDocToAmt()|| null !=digitalLibraryRequestDTO.getDocCode()) {
	queryStr.append(" AND lmtPr.LLP_LE_ID = SUBPR.LSP_LE_ID ");
	queryStr.append(" AND lmtPr.CMS_LSP_LMT_PROFILE_ID = chklst.CMS_LSP_LMT_PROFILE_ID ");
	queryStr.append(" AND chklstitm.CHECKLIST_ID = chklst.CHECKLIST_ID ");
	}
	
	if(null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
	queryStr.append(" AND trans.LEGAL_NAME = SUBPR.LSP_SHORT_NAME ");
	queryStr.append(" AND imgmap.TAG_ID = trans.REFERENCE_ID ");
	queryStr.append(" AND UPIMG.IMG_ID = imgmap.IMAGE_ID ");
	queryStr.append(" AND imgmap.TAG_ID = imgdtl.ID ");
	queryStr.append(" AND imgdtl.DOC_DESC = to_char(chklstitm.DOC_ITEM_ID) ");
	}
	
	if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
		queryStr.append(" AND MAINPR.LMP_LE_ID = SUBPR.LSP_LE_ID ");
		
	}
	
	if(null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
	queryStr.append(" AND trans.TRANSACTION_TYPE='IMAGE_TAG' ");
	queryStr.append(" AND trans.status='ACTIVE' ");
	queryStr.append(" AND imgmap.UNTAGGED_STATUS = 'N' ");
	}
	
	
	if(null !=digitalLibraryRequestDTO.getDocType()) {
		queryStr.append(" and UPIMG.CATEGORY ='"+digitalLibraryRequestDTO.getDocType()+"'");
	}
	
	if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() && null == digitalLibraryRequestDTO.getDocRecvToDate()) {
		queryStr.append(" and chklstitm.RECEIVED_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocRecvFromDate()+"'"+",'DD/MM/YY')");
	}
	
	if(null ==digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
		queryStr.append(" and chklstitm.RECEIVED_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocRecvToDate()+"'"+",'DD/MM/YY')");
	}
	
	if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
		queryStr.append(" and chklstitm.RECEIVED_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocRecvFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocRecvToDate()+"', 'DD/MM/YY')");
	}
	
	if(null !=digitalLibraryRequestDTO.getDocFromAmt() && null == digitalLibraryRequestDTO.getDocToAmt()) {
		queryStr.append(" and chklstitm.doc_amt >= to_number('"+digitalLibraryRequestDTO.getDocFromAmt()+"')");
	}
	
	if(null ==digitalLibraryRequestDTO.getDocFromAmt() && null != digitalLibraryRequestDTO.getDocToAmt()) {
		queryStr.append(" and chklstitm.doc_amt <= to_number('"+digitalLibraryRequestDTO.getDocToAmt()+"')");
	}

	if(null !=digitalLibraryRequestDTO.getDocFromAmt() && null != digitalLibraryRequestDTO.getDocToAmt()) {
		queryStr.append(" and chklstitm.doc_amt >= to_number('"+digitalLibraryRequestDTO.getDocFromAmt()+"')" + " and chklstitm.doc_amt <= to_number('"+digitalLibraryRequestDTO.getDocToAmt()+"')");
	}
	
	if(null !=digitalLibraryRequestDTO.getSystemName()) {
		queryStr.append(" and OTHSYS.CMS_LE_SYSTEM_NAME ='"+digitalLibraryRequestDTO.getSystemName()+"'");
	}
	
	
	if(null !=digitalLibraryRequestDTO.getDocTagFromDate() && null == digitalLibraryRequestDTO.getDocTagToDate()) {
		queryStr.append(" and trans.TRANSACTION_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocTagFromDate()+"'"+",'DD/MM/YY')");
	}
	
	if(null ==digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
		queryStr.append(" and trans.TRANSACTION_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocTagToDate()+"'"+",'DD/MM/YY')");
	}
	
	
	if(null !=digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
		if(!digitalLibraryRequestDTO.getDocTagFromDate().equals(digitalLibraryRequestDTO.getDocTagToDate())) {
		queryStr.append(" and trans.TRANSACTION_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocTagFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocTagToDate()+"', 'DD/MM/YY')");
		}
		
		if(digitalLibraryRequestDTO.getDocTagFromDate().equals(digitalLibraryRequestDTO.getDocTagToDate())) {
			queryStr.append(" AND trans.TRANSACTION_DATE like to_date ('"+digitalLibraryRequestDTO.getDocTagFromDate()+"', 'DD-MON-YY')");	
		}
		}
	
	if(null !=digitalLibraryRequestDTO.getDocCode() ) {
		queryStr.append(" and chklstitm.DOCUMENT_CODE = '"+digitalLibraryRequestDTO.getDocCode()+"'");
	}
	
	if(null !=digitalLibraryRequestDTO.getDocFromDate() && null == digitalLibraryRequestDTO.getDocToDate()) {
		queryStr.append(" and chklstitm.DOC_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocFromDate()+"'"+",'DD/MM/YY')");
	}
	if(null ==digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
		queryStr.append(" and chklstitm.DOC_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocToDate()+"'"+",'DD/MM/YY')");
	}
	
	if(null !=digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
		queryStr.append(" and chklstitm.DOC_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocToDate()+"', 'DD/MM/YY')");
	}
	
	if(null != digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
	queryStr.append(" AND UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' ");
	}
	
	
	if(null == digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" AND UPIMG.CUST_ID in (select lsp_le_id from sci_le_sub_profile where pan= '"+digitalLibraryRequestDTO.getPanNo()+"') ");
		}

	if(null == digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" AND UPIMG.IMG_ID IN " + 
				"  (SELECT image_id " + 
				"  FROM cms_image_tag_map " + 
				"  WHERE tag_id IN " + 
				"    (SELECT id " + 
				"    FROM cms_image_tag_details " + 
				"    WHERE FACILITY_ID IN " + 
				"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
				"      FROM sci_lsp_lmts_xref_map " + 
				"      WHERE cms_lsp_sys_xref_id IN " + 
				"        (SELECT cms_lsp_sys_xref_id " + 
				"        FROM sci_lsp_sys_xref " + 
				"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
				"        ) " + 
				"      ) " + 
				"    ) " + 
				"  ) " + 
				"");
		}
	
	if(null != digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" AND UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' and pan ='"+digitalLibraryRequestDTO.getPanNo()+"'");
		}
	
	if(null != digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" AND UPIMG.IMG_ID IN " + 
				"  (SELECT image_id " + 
				"  FROM cms_image_tag_map " + 
				"  WHERE tag_id IN " + 
				"    (SELECT id " + 
				"    FROM cms_image_tag_details " + 
				"    WHERE FACILITY_ID IN " + 
				"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
				"      FROM sci_lsp_lmts_xref_map " + 
				"      WHERE cms_lsp_sys_xref_id IN " + 
				"        (SELECT cms_lsp_sys_xref_id " + 
				"        FROM sci_lsp_sys_xref " + 
				"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
				"        ) " + 
				"      ) " + 
				"    ) " + 
				"  ) " + 
				"");
		
		queryStr.append(" and SUBPR.LSP_LE_ID='"+digitalLibraryRequestDTO.getPartyId()+"'");
		}
	
	if(null == digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" AND UPIMG.IMG_ID IN " + 
				"  (SELECT image_id " + 
				"  FROM cms_image_tag_map " + 
				"  WHERE tag_id IN " + 
				"    (SELECT id " + 
				"    FROM cms_image_tag_details " + 
				"    WHERE FACILITY_ID IN " + 
				"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
				"      FROM sci_lsp_lmts_xref_map " + 
				"      WHERE cms_lsp_sys_xref_id IN " + 
				"        (SELECT cms_lsp_sys_xref_id " + 
				"        FROM sci_lsp_sys_xref " + 
				"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
				"        ) " + 
				"      ) " + 
				"    ) " + 
				"  ) " + 
				"");
		
		queryStr.append(" and SUBPR.PAN='"+digitalLibraryRequestDTO.getPanNo()+"'");
		}
	
	if(null != digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' and SUBPR.pan ='"+digitalLibraryRequestDTO.getPanNo()+"'");
		queryStr.append(" AND UPIMG.IMG_ID IN " + 
				"  (SELECT image_id " + 
				"  FROM cms_image_tag_map " + 
				"  WHERE tag_id IN " + 
				"    (SELECT id " + 
				"    FROM cms_image_tag_details " + 
				"    WHERE FACILITY_ID IN " + 
				"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
				"      FROM sci_lsp_lmts_xref_map " + 
				"      WHERE cms_lsp_sys_xref_id IN " + 
				"        (SELECT cms_lsp_sys_xref_id " + 
				"        FROM sci_lsp_sys_xref " + 
				"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
				"        ) " + 
				"      ) " + 
				"    ) " + 
				"  ) " + 
				"");
		}
	
	if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate() 
			|| null != digitalLibraryRequestDTO.getDocFromAmt() || null != digitalLibraryRequestDTO.getDocToAmt()
			|| null != digitalLibraryRequestDTO.getDocFromDate() || null != digitalLibraryRequestDTO.getDocToDate() || null !=digitalLibraryRequestDTO.getDocCode()){
		queryStr.append(" AND to_char(chklstitm.doc_item_id) = imgdtl.DOC_DESC "+
			" AND imgmap.tag_id = imgdtl.id "+
			" AND imgmap.IMAGE_ID = UPIMG.IMG_ID  ");
	}
	
	if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
		queryStr.append(" AND MAINPR.RELATION_MGR_EMP_CODE ='"+digitalLibraryRequestDTO.getEmployeeCodeRM()+"'");
		
	}
	
	final List<String> imageList=new ArrayList<String>();
	DBUtil dbUtil = null;
	ResultSet rs=null;
//	DefaultLogger.debug(this, "inside getImageIdList sql:"+queryStr);

//System.out.println("inside getImageIdList sql:"+queryStr);			
try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr.toString());
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							imageList.add(rs.getString("hcp_filename"));
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getImageIdList:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return imageList;
}


public Boolean getPartyId(String code) throws SearchDAOException {
	
	String queryStr = "select lsp_le_id from sci_le_sub_profile where lsp_le_id= '"+code+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getPartyId sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyId:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}

public Boolean getPanNo(String panNo) throws SearchDAOException {
	
	String queryStr = "select pan from sci_le_sub_profile where pan= '"+panNo+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
//	DefaultLogger.debug(this, "inside getPanNo sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPanNo:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}


public Boolean getPartyName(String code) throws SearchDAOException{
	
	String queryStr = "select LSP_SHORT_NAME from sci_le_sub_profile where LSP_SHORT_NAME= '"+code+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getPartyName sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyName:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}

public Boolean getPartyNameAndPartyID(String partyName,String PartyID) throws SearchDAOException{
	
	String queryStr = "select * from sci_le_sub_profile where LSP_SHORT_NAME= '"+partyName+"'"+" and  LSP_LE_ID='"+PartyID+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getPartyNameAndPartyID sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyNameAndPartyID:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}    

public Boolean getPartyNameAndSystemID(String partyName,String SystemId) throws SearchDAOException{
	
	String queryStr = "select * from sci_le_other_system where CMS_LE_MAIN_PROFILE_ID   = (select CMS_LE_MAIN_PROFILE_ID from SCI_LE_SUB_PROFILE where LSP_SHORT_NAME= '"+partyName+"'"+" ) and CMS_LE_SYSTEM_NAME IN (SELECT CMS_LE_SYSTEM_NAME "  
			+"FROM sci_le_other_system "  
			+"WHERE CMS_LE_OTHER_SYS_CUST_ID='"+SystemId+"')";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getPartyNameAndSystemID sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyNameAndSystemID:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}

public Boolean getPanNoAndPartyID(String panNo,String partyId) throws SearchDAOException{
	
	String queryStr = "select pan from sci_le_sub_profile where pan= '"+panNo+"'"+" and lsp_le_id='"+partyId+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
//	DefaultLogger.debug(this, "inside getPanNoAndPartyID sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPanNoAndPartyID:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}


public Boolean getSystemIDAndPartyID(String systemId,String partyId) throws SearchDAOException{
	
	String queryStr = "select * from sci_le_other_system where CMS_LE_MAIN_PROFILE_ID   in (select CMS_LE_MAIN_PROFILE_ID from SCI_LE_SUB_PROFILE where lsp_le_id= '"+partyId+"'"+" ) and CMS_LE_SYSTEM_NAME IN (SELECT CMS_LE_SYSTEM_NAME " + 
			"FROM sci_le_other_system " + 
			"WHERE CMS_LE_OTHER_SYS_CUST_ID='"+systemId+"')";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getPartyNameAndSystemID sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyNameAndSystemID:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}


public Boolean getSystemIDAndPartyIDAndPanNo(String systemId,String partyId,String panNo) throws SearchDAOException{
	
	String queryStr = "select * from sci_le_other_system where CMS_LE_MAIN_PROFILE_ID   in (select CMS_LE_MAIN_PROFILE_ID from SCI_LE_SUB_PROFILE where lsp_le_id= '"+partyId+"'"+" and pan='"+panNo+"') and CMS_LE_SYSTEM_NAME IN (SELECT CMS_LE_SYSTEM_NAME " + 
			"FROM sci_le_other_system " + 
			"WHERE CMS_LE_OTHER_SYS_CUST_ID='"+systemId+"')";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
//	DefaultLogger.debug(this, "inside getSystemIDAndPartyIDAndPanNo sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getSystemIDAndPartyIDAndPanNo:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}


public Boolean getPanNoAndSystemId(String panNo,String systemId) throws SearchDAOException{
	
	String queryStr = "select * from sci_le_other_system where CMS_LE_MAIN_PROFILE_ID   IN (select CMS_LE_MAIN_PROFILE_ID from SCI_LE_SUB_PROFILE where  pan='"+panNo+"') and CMS_LE_SYSTEM_NAME IN (SELECT CMS_LE_SYSTEM_NAME " + 
			"FROM sci_le_other_system " +  
			"WHERE CMS_LE_OTHER_SYS_CUST_ID='"+systemId+"')";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
//	DefaultLogger.debug(this, "inside getPanNoAndSystemId sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPanNoAndSystemId:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}

public String getPartyIdByPanNo(String panNo)throws SearchDAOException{

	
	String queryStr = "select lsp_le_id from sci_le_sub_profile where pan= '"+panNo+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	String partyList = new String();
	
	final List<String> arraylist =new ArrayList<String>();
//	DefaultLogger.debug(this, "inside getPartyIdByPanNo sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							arraylist.add(rs.getString("lsp_le_id"));
						}
					}
				 for(int i = 0; i < arraylist.size() ; i++) {
						if(null != arraylist.get(i)) {
							partyList = partyList.concat(arraylist.get(i));
						}
						if(arraylist.size() > 1 ) {
								if((i + 1 ) < arraylist.size() ) {
									partyList = partyList.concat(",");
								}
							}
						}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyIdByPanNo:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return partyList;

	
}



public String getPartyIdBySystemId(String systemId)throws SearchDAOException{

	
	String queryStr = "SELECT lsp_le_id " + 
			"  FROM SCI_LE_SUB_PROFILE " + 
			"  WHERE CMS_LE_MAIN_PROFILE_ID in (SELECT CMS_LE_MAIN_PROFILE_ID " + 
			"FROM sci_le_other_system " + 
			"WHERE CMS_LE_OTHER_SYS_CUST_ID= '"+systemId+"')";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	String partyList = new String();
	
	final List<String> arraylist =new ArrayList<String>();
	DefaultLogger.debug(this, "inside getPartyIdBySystemId sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							arraylist.add(rs.getString("lsp_le_id"));
						}
					}
				 for(int i = 0; i < arraylist.size() ; i++) {
						if(null != arraylist.get(i)) {
							partyList = partyList.concat(arraylist.get(i));
						}
						if(arraylist.size() > 1 ) {
								if((i + 1 ) < arraylist.size() ) {
									partyList = partyList.concat(",");
								}
							}
						}
					
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getPartyIdByPanNo:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return partyList;

	
}

public boolean getSystemName(String systemName)throws SearchDAOException{
	
	String queryStr = "SELECT CMS_LE_SYSTEM_NAME " + 
			"FROM sci_le_other_system " + 
			"WHERE CMS_LE_OTHER_SYS_CUST_ID='"+systemName+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	String sysName = null;
	
	DefaultLogger.debug(this, "inside getSystemName sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getSystemName:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}


public String getEntryCode(String entryName,String categoryCode)throws SearchDAOException{
	
	String queryStr = "select ENTRY_CODE from common_code_category_entry where category_code = '"+categoryCode+"' and entry_name = '"+entryName+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	String Name = null;
	
	DefaultLogger.debug(this, "inside getEntryCode sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							Name = rs.getString("ENTRY_CODE");
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getEntryCode:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return Name;
}
	
	//For PSR limit handOff
	public OBCustomerSysXRef[] getLimitProfileforPSRFile() throws LimitException {
		
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String psrSystem = bundle.getString("psr.systemName");
		
		String selectSQL = "SELECT CMS_LSP_SYS_XREF_ID,ACTION,SOURCE_REF_NO, "
			+ "FACILITY_SYSTEM_ID, "
			+ "LINE_NO, "
			+ "SERIAL_NO, "
			+ "CURRENCY, "
			+" SEGMENT_1,"
			+" RELEASE_DATE, "
			+" DATE_OF_RESET,"
			+" RELEASED_AMOUNT, "
			+" LIMIT_RESTRICTION_FLAG, "
			+" TENURE, "
			+" SELL_DOWN_PERIOD, "
			+" LIABILITY_ID, "
			+" LIMIT_REMARKS, "
			+" CREATED_BY, "
			+" UPDATED_BY "
			+" FROM SCI_LSP_SYS_XREF"
			+" WHERE STATUS = '"+ICMSConstant.PSR_STATUS_PENDING+"' AND"
			+" FACILITY_SYSTEM IN ('"+psrSystem+"')"
			+" AND (scheduler_status is null or scheduler_status = 'COMPLETED')"
			+" AND SENDTOCORE = 'N'"
			+" AND SENDTOFILE = 'Y'"
			+" ORDER BY SOURCE_REF_NO ";

		return (OBCustomerSysXRef[]) getJdbcTemplate().query(selectSQL,
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processPSRLimit(rs);
			}
		});
	}

	private OBCustomerSysXRef[] processPSRLimit(ResultSet rs) throws SQLException {
		
		ArrayList list = new ArrayList();
		while (rs.next()) {
			OBCustomerSysXRef customerSysXRef = new OBCustomerSysXRef();
			
			long xrefId = rs.getLong("CMS_LSP_SYS_XREF_ID");
			customerSysXRef.setXRefID(xrefId);

			String action = rs.getString("ACTION");
			if(null!=action && !"".equalsIgnoreCase(action))
				customerSysXRef.setAction(action);
			else
			customerSysXRef.setAction("");

			String sourceRefNo = rs.getString("SOURCE_REF_NO");
			if(null!=sourceRefNo && !"".equalsIgnoreCase(sourceRefNo))
			customerSysXRef.setSourceRefNo(sourceRefNo);
			else
				customerSysXRef.setSourceRefNo("");
			
			String guaranteeLiabId = "";
			try {
				guaranteeLiabId = checkIsGuarantee(xrefId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null != guaranteeLiabId && !"".equalsIgnoreCase(guaranteeLiabId)) {
				customerSysXRef.setFacilitySystemID(guaranteeLiabId);
				if ((ICMSConstant.FCUBSLIMIT_ACTION_NEW).equals(customerSysXRef.getAction())) {
					String liabId = rs.getString("FACILITY_SYSTEM_ID");
					if (null != liabId && !"".equalsIgnoreCase(liabId)) {
						String limitRestFlag = rs.getString("LIMIT_RESTRICTION_FLAG");
						if (null != limitRestFlag && !"".equalsIgnoreCase(limitRestFlag))
							customerSysXRef.setLimitRestrictionFlag(limitRestFlag + "," + liabId + "#A");
						else
							customerSysXRef.setLimitRestrictionFlag(liabId + "#A");
					}
				} else {
					String limitRestFlag = rs.getString("LIMIT_RESTRICTION_FLAG");
					if (null != limitRestFlag && !"".equalsIgnoreCase(limitRestFlag))
						customerSysXRef.setLimitRestrictionFlag(limitRestFlag);
					else
						customerSysXRef.setLimitRestrictionFlag("");
				}
			} else {
				String liabId = rs.getString("FACILITY_SYSTEM_ID");
				if (null != liabId && !"".equalsIgnoreCase(liabId))
					customerSysXRef.setFacilitySystemID(liabId);
				else
					customerSysXRef.setFacilitySystemID("");

				String limitRestFlag = rs.getString("LIMIT_RESTRICTION_FLAG");
				if (null != limitRestFlag && !"".equalsIgnoreCase(limitRestFlag))
					customerSysXRef.setLimitRestrictionFlag(limitRestFlag);
				else
					customerSysXRef.setLimitRestrictionFlag("");
			}
			
			String lineNo = rs.getString("LINE_NO");
			if(null!=lineNo && !"".equalsIgnoreCase(lineNo))
			customerSysXRef.setLineNo(lineNo);
			else
				customerSysXRef.setLineNo("");
			
			String serialNo = rs.getString("SERIAL_NO");
			if(null!=serialNo && !"".equalsIgnoreCase(serialNo) && !((ICMSConstant.FCUBSLIMIT_ACTION_NEW).equals(customerSysXRef.getAction())))
			customerSysXRef.setSerialNo(serialNo);
			else
				customerSysXRef.setSerialNo("1");
			
			String currency = rs.getString("CURRENCY");
			if(null!=currency && !"".equalsIgnoreCase(currency))
			customerSysXRef.setCurrency(currency);
			else
				customerSysXRef.setCurrency("");
			
			String segment = rs.getString("SEGMENT_1");
			if(null!=segment && !"".equalsIgnoreCase(segment))
			customerSysXRef.setSegment(segment);
			else
				customerSysXRef.setSegment("");
			
			Date startDate = rs.getDate("RELEASE_DATE");
			if(null!=startDate && !"".equals(startDate))
			customerSysXRef.setReleaseDate(startDate);
			
			
			Date expiryDate = rs.getDate("DATE_OF_RESET");
			if(null!=expiryDate && !"".equals(expiryDate))
			customerSysXRef.setDateOfReset(expiryDate);
			
			String releasedAmt =  rs.getString("RELEASED_AMOUNT");
			if(null!=releasedAmt && !"".equalsIgnoreCase(releasedAmt))
			customerSysXRef.setReleasedAmount(releasedAmt);
			else
				customerSysXRef.setReleasedAmount("");
			
			String tenure =  rs.getString("TENURE");
			if(null!=tenure && !"".equalsIgnoreCase(tenure))
				customerSysXRef.setTenure(tenure);
			else
				customerSysXRef.setTenure(tenure);
			
			String sellDownPeriod =  rs.getString("SELL_DOWN_PERIOD");
			if(null!=sellDownPeriod && !"".equalsIgnoreCase(sellDownPeriod))
				customerSysXRef.setSellDownPeriod(sellDownPeriod);
			else
				customerSysXRef.setSellDownPeriod(sellDownPeriod);
			
			String liabilityId =  rs.getString("LIABILITY_ID");
			if(null!=liabilityId && !"".equalsIgnoreCase(liabilityId))
				customerSysXRef.setLiabilityId(liabilityId);
			else
				customerSysXRef.setLiabilityId(liabilityId);
			
			String limitRemarks =  rs.getString("LIMIT_REMARKS");
			if(null!=limitRemarks && !"".equalsIgnoreCase(limitRemarks))
				customerSysXRef.setLimitRemarks(limitRemarks);
			else
				customerSysXRef.setLimitRemarks(limitRemarks);
			
			String createdBy =  rs.getString("CREATED_BY");
			if(null!=createdBy && !"".equalsIgnoreCase(createdBy))
				customerSysXRef.setCreatedBy(createdBy);
			else
				customerSysXRef.setCreatedBy(createdBy);
			
			String updatedBy =  rs.getString("UPDATED_BY");
			if(null!=updatedBy && !"".equalsIgnoreCase(updatedBy))
				customerSysXRef.setUpdatedBy(updatedBy);
			else
				customerSysXRef.setUpdatedBy(updatedBy);
			
			list.add(customerSysXRef);
			}
		rs.close();
		return (OBCustomerSysXRef[]) list.toArray(new OBCustomerSysXRef[0]);
	}
	
	public String getSeqNoForPSRFile() throws Exception {

		System.out.println("getSeqNoForPSRFile()...... ");
		String fileSeqNo = "";
		DefaultLogger.debug(this, "PSRLimitFileUploadJob in seqno");
		int queryForInt = 0;
		String queryStr = "select PSR_FILE_SEQ.NEXTVAL from dual";

		ResultSet rs = null;
		try {

			queryForInt = getJdbcTemplate().queryForInt(queryStr);
			DefaultLogger.debug(this, "PSRLimitFileUploadJob  seqno" + queryForInt);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}

		fileSeqNo = String.format("%04d", queryForInt);
		DefaultLogger.debug(this, "PSRLimitFileUploadJob get seqno" + fileSeqNo);
		return fileSeqNo;
	}
	
	public Map<String,String> getDataforPSRFile(long xref_ID) throws Exception{
		
		Map<String,String > resultMap = new HashMap<String,String >();
		String sql = "SELECT CAM.LLP_LE_ID	AS CLIMS_PARTY_ID, "
		  + "CAM.CMS_APPR_OFFICER_GRADE 	AS RAM_RATING, "
//		  + "MGR.RM_MGR_NAME            	AS RM_NAME, "
		  + "SUB.RELATION_MGR_EMP_CODE      AS RELATION_MGR_EMP_CODE, "
		  + "RGN.REGION_NAME            	AS RM_REGION, "
		  + "SUB.PAN                    	AS PAN_NO, "
		  + "SUB.RBI_IND_CODE            	AS RBI_IND_CODE, "
		  + "COMN.ENTRY_NAME            	AS INDUST_DESC, "
		  + "SUB.LSP_SGMNT_CODE_VALUE       AS PARTY_SEGMENT "
		  + "FROM SCI_LSP_LMT_PROFILE CAM, "
		  + "SCI_LSP_APPR_LMTS LMTS, "
		  + "SCI_LSP_LMTS_XREF_MAP MAP, "
		  + "CMS_RELATIONSHIP_MGR MGR, "
		  + "SCI_LE_SUB_PROFILE SUB, "
		  + "CMS_REGION RGN, "
		  + "COMMON_CODE_CATEGORY_ENTRY COMN "
		  + "WHERE CAM.CMS_LSP_LMT_PROFILE_ID=LMTS.CMS_LIMIT_PROFILE_ID "
		  + " AND LMTS.CMS_LSP_APPR_LMTS_ID   =MAP.CMS_LSP_APPR_LMTS_ID "
		  + " AND SUB.RELATION_MGR            =MGR.ID "
		  + " AND SUB.RM_REGION               =RGN.ID "
		  + " AND CAM.LLP_LE_ID               =SUB.LSP_LE_ID "
		  + " AND COMN.CATEGORY_CODE          ='HDFC_INDUSTRY' "
		  + " AND COMN.ENTRY_CODE             =SUB.IND_NM "
		  + " AND MAP.CMS_LSP_SYS_XREF_ID     ='"+xref_ID+"' ";
		
		System.out.println("getDataforPSRFile SQL is: "+sql);
		try {
			List queryList=getJdbcTemplate().queryForList(sql);
			
			for (int i = 0; i < queryList.size(); i++) {
				Map  map = (Map) queryList.get(i);
				
				if(null!=map.get("CLIMS_PARTY_ID"))
					resultMap.put("CLIMS_PARTY_ID", map.get("CLIMS_PARTY_ID").toString());
				else
					resultMap.put("CLIMS_PARTY_ID", "");
				
				if(null!=map.get("RAM_RATING"))
					resultMap.put("RAM_RATING", map.get("RAM_RATING").toString());
				else
					resultMap.put("RAM_RATING", "");
				
//				if(null!=map.get("RM_NAME"))
//					resultMap.put("RM_NAME", map.get("RM_NAME").toString());
//				else
//					resultMap.put("RM_NAME", "");
				
				if(null!=map.get("RELATION_MGR_EMP_CODE"))
					resultMap.put("RELATION_MGR_EMP_CODE", map.get("RELATION_MGR_EMP_CODE").toString());
				else
					resultMap.put("RELATION_MGR_EMP_CODE", "");
				
				if(null!=map.get("RM_REGION"))
					resultMap.put("RM_REGION", map.get("RM_REGION").toString());
				else
					resultMap.put("RM_REGION", "");
				
				if(null!=map.get("PAN_NO"))
					resultMap.put("PAN_NO", map.get("PAN_NO").toString());
				else
					resultMap.put("PAN_NO", "");
				
				if(null!=map.get("RBI_IND_CODE"))
					resultMap.put("RBI_IND_CODE", map.get("RBI_IND_CODE").toString());
				else
					resultMap.put("RBI_IND_CODE", "");
				
				if(null!=map.get("PARTY_SEGMENT"))
					resultMap.put("PARTY_SEGMENT", map.get("PARTY_SEGMENT").toString());
				else
					resultMap.put("PARTY_SEGMENT", "");
				
				if(null!=map.get("INDUST_DESC"))
					resultMap.put("INDUST_DESC", map.get("INDUST_DESC").toString());
				else
					resultMap.put("INDUST_DESC", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  getDataforPSRFile:"+e.getMessage());
		}
		DefaultLogger.debug(this, "completed getDataforPSRFile");
		return resultMap;
	}
	
	public OBPSRDataLog fetchPartyDetails(OBCustomerSysXRef obCustomerSysXRef, OBPSRDataLog psrObj) throws Exception {

		String sql=" SELECT SP.LMP_LE_ID, "
			+ " SP.LMP_SHORT_NAME, "
			+ " SP.LMP_SGMNT_CODE_VALUE "
			+ " FROM SCI_LE_MAIN_PROFILE SP, "
			+ " SCI_LSP_LMT_PROFILE CAM, "
			+ " SCI_LSP_APPR_LMTS LMTS, "
			+ " SCI_LSP_LMTS_XREF_MAP MAP "
			+ " WHERE SP.LMP_LE_ID            =CAM.LLP_LE_ID "
			+ " AND CAM.CMS_LSP_LMT_PROFILE_ID=LMTS.CMS_LIMIT_PROFILE_ID "
			+ " AND LMTS.CMS_LSP_APPR_LMTS_ID =MAP.CMS_LSP_APPR_LMTS_ID "
			+ " AND MAP.CMS_LSP_SYS_XREF_ID   ='"+obCustomerSysXRef.getXRefID()+"'";
		
		System.out.println("fetchPartyDetails SQL is: "+sql);
		String partyId = "";
		String partyName = "";
		String segment="";
		
		try {
			List queryForList = getJdbcTemplate().queryForList(sql);
			for (int i = 0; i < queryForList.size(); i++) {
				Map map = (Map) queryForList.get(i);
				partyId = map.get("LMP_LE_ID").toString();
				psrObj.setPartyId(partyId);
				partyName = map.get("LMP_SHORT_NAME").toString();
				psrObj.setPartyName(partyName);
				segment = map.get("LMP_SGMNT_CODE_VALUE").toString();
				psrObj.setSegment(segment);
			}
			return psrObj;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public OBPSRDataLog fetchMakerCheckerDetails(OBCustomerSysXRef obCustomerSysXRef, OBPSRDataLog psrObj)
			throws Exception {

		String queryStr = "SELECT CREATED_BY,UPDATED_BY,CREATED_ON,UPDATED_ON "
				+ "FROM SCI_LSP_SYS_XREF WHERE CMS_LSP_SYS_XREF_ID='" + obCustomerSysXRef.getXRefID() + "'";
		System.out.println("fetchPartyDetails SQL is: "+queryStr);
		String createdBy = "";
		String updatedBy = "";
		Date createdOn = null;
		Date updatedOn = null;

		try {
			List queryForList = getJdbcTemplate().queryForList(queryStr);
			for (int i = 0; i < queryForList.size(); i++) {
				Map map = (Map) queryForList.get(i);
				if (null != map.get("CREATED_BY") && !"".equals(map.get("CREATED_BY"))) {
					createdBy = map.get("CREATED_BY").toString();
				}
				psrObj.setMakerId(createdBy);

				if (null != map.get("UPDATED_BY") && !"".equals(map.get("UPDATED_BY"))) {
					updatedBy = map.get("UPDATED_BY").toString();
				}
				psrObj.setCheckerId(updatedBy);

				if (null != map.get("CREATED_ON") && !"".equals(map.get("CREATED_ON"))) {
					createdOn = (Date) map.get("CREATED_ON");
				}
				psrObj.setMakerDateTime(createdOn);

				if (null != map.get("UPDATED_ON") && !"".equals(map.get("UPDATED_ON"))) {
					updatedOn = (Date) map.get("UPDATED_ON");
				}
				psrObj.setCheckerDateTime(updatedOn);
			}
			return psrObj;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

	}
	
	public ArrayList<String[]> getPSRFileNames() throws SearchDAOException, Exception {

		ArrayList<String[]> ackList = new ArrayList<String[]>();
		try {
			String queryStr = "SELECT DISTINCT FILENAME FROM CMS_PSRDATA_LOG WHERE STATUS='"
					+ ICMSConstant.PSR_STATUS_PENDING + "'";
			System.out.println("In PSRLimitFileDownloadJob getPSRFileNames() SQL is: "+queryStr);
			List queryForList = getJdbcTemplate().queryForList(queryStr);

			for (int i = 0; i < queryForList.size(); i++) {
				Map map = (Map) queryForList.get(i);
				String[] ackFiles = new String[2];
				
				if (null != map.get("FILENAME")) {
					String fileName = map.get("FILENAME").toString();
					String ackFileName = fileName.substring(0, fileName.lastIndexOf("."));
					ackFiles[0] = ackFileName + "_Success.txt";
					ackFiles[1] = ackFileName + "_Fail.txt";
					ackList.add(ackFiles);
				}
			}
		} catch (Exception e) {
			DefaultLogger.debug(this,"Got exception in PSRLimitFileDownloadJob getPSRFileNames......" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return ackList;
	}
	
	public void updatePSRLineDetails(String sourceRef, Map<String, String> map, String psrFileStatus) throws DBConnectionException, SQLException,Exception {
		
		System.out.println("Starting PSRLimitFileDownloadJob updatePSRLineDetails()......"+psrFileStatus);
		try{
			if(psrFileStatus.equalsIgnoreCase(ICMSConstant.PSR_STATUS_SUCCESS)){

				String queryStr = "update SCI_LSP_SYS_XREF set status = '"+psrFileStatus+"' , SENDTOCORE = 'N' , ACTION = '' , "
						+ "CORE_STP_REJECTED_REASON = '' where SOURCE_REF_NO IN ("+sourceRef+")";
				getJdbcTemplate().update(queryStr);
				
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String queryForSerialNo = "update SCI_LSP_SYS_XREF set SERIAL_NO = '"+entry.getValue()+"', HIDDEN_SERIAL_NO = '"+entry.getValue()+"' "
							+ "where SOURCE_REF_NO  = '"+entry.getKey()+"'";
					 getJdbcTemplate().update(queryForSerialNo);
				}
				
				String queryStagingStr = "update CMS_STAGE_LSP_SYS_XREF set status = '"+psrFileStatus+"' , SENDTOCORE = 'N' , "
						+ " ACTION = '', CORE_STP_REJECTED_REASON = '' where SOURCE_REF_NO IN ("+sourceRef+")";
				getJdbcTemplate().update(queryStagingStr);
				System.out.println("Starting queryStagingStr......"+queryStagingStr);
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String queryForSerialNo = "update CMS_STAGE_LSP_SYS_XREF set SERIAL_NO = '"+entry.getValue()+"', HIDDEN_SERIAL_NO = '"+entry.getValue()+"' "
							+ "where SOURCE_REF_NO  = '"+entry.getKey()+"'";
					 getJdbcTemplate().update(queryForSerialNo);
				}
			}

			if(psrFileStatus.equalsIgnoreCase(ICMSConstant.PSR_STATUS_REJECTED)){

				for (Map.Entry<String, String> entry : map.entrySet()) {
					String queryStr = "update SCI_LSP_SYS_XREF set CORE_STP_REJECTED_REASON = '"+entry.getValue().replaceAll("'", "''")+"' , "
							+ "status = '"+psrFileStatus+"' , SENDTOCORE = 'N' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
					getJdbcTemplate().update(queryStr);
				}
				
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String queryStr = "update CMS_STAGE_LSP_SYS_XREF set CORE_STP_REJECTED_REASON = '"+entry.getValue().replaceAll("'", "''")+"' , "
							+ "status = '"+psrFileStatus+"' , SENDTOCORE = 'N' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
					getJdbcTemplate().update(queryStr);
				}
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,"Got PSRLimitFileDownloadJob Exception......"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updatePSRDataLog(String sourceRef, Map<String, String> map, String psrFileStatus,Date responseDate) throws Exception {
		
		System.out.println("Starting PSRLimitFileDownloadJob updatePSRDataLog......"+psrFileStatus);
		try{
			if(psrFileStatus.equalsIgnoreCase(ICMSConstant.PSR_STATUS_SUCCESS)){
				String queryStr = "update CMS_PSRDATA_LOG set status = '"+psrFileStatus+"' ,RESPONSEDATE = ? where SOURCE_REF_NO IN ("+sourceRef+")";
				getJdbcTemplate().update(queryStr, new Object[]{new Timestamp(responseDate.getTime())});
				
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String queryForSerialNo = "update CMS_PSRDATA_LOG set SERIALNO = '"+entry.getValue()+"' where SOURCE_REF_NO  = '"+entry.getKey()+"'";
					 getJdbcTemplate().update(queryForSerialNo);
				}
			}

			if(psrFileStatus.equalsIgnoreCase(ICMSConstant.PSR_STATUS_REJECTED)){
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String queryStr = "update CMS_PSRDATA_LOG set ERRORDESC = '"+entry.getValue().replaceAll("'", "''")+"' , status = '"+psrFileStatus+"', "
							+ "RESPONSEDATE = ? where SOURCE_REF_NO  = '"+entry.getKey()+"'";
					getJdbcTemplate().update(queryStr, new Object[]{new Timestamp(responseDate.getTime())});
				}
			}
		}
		
		catch(Exception e){
			DefaultLogger.debug(this,"Got PSRLimitFileDownloadJob Exception......"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<String> getPSRlmtId(long limitProfileId, String facilitySystem) {
		String fetchLmtId = " SELECT facility.cms_lsp_appr_lmts_id FROM sci_lsp_appr_lmts facility "
				+ " WHERE  facility.CMS_LIMIT_STATUS='ACTIVE' and " + " facility.cms_limit_profile_id = '"
				+ limitProfileId + "' AND   facility.facility_system IN (" + facilitySystem + ")";
		List lmtIdList = new ArrayList<String>();
		DefaultLogger.debug(this, "inside getPSRlmtId");

		try {
			List lmtIdQueryList = getJdbcTemplate().queryForList(fetchLmtId);
			for (int i = 0; i < lmtIdQueryList.size(); i++) {
				Map map = (Map) lmtIdQueryList.get(i);
				String lmtId = map.get("CMS_LSP_APPR_LMTS_ID").toString();
				lmtIdList.add(lmtId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  getPSRlmtId:" + e.getMessage());
		}
		DefaultLogger.debug(this, "completed getPSRlmtId");

		return lmtIdList;
	}
	
	public void updatePSRStageActualLine(final String available, final String status,
			final Date newExtendedNextReviewDate, Map<String, String> sysXrefIdmap, String name) {

		String expiryStage = "update cms_stage_lsp_sys_xref set source_ref_no= ? , status=? ,"
				+ " ACTION = ? ,DATE_OF_RESET= ? where cms_lsp_sys_xref_id=?";

		String expiryActual = "update sci_lsp_sys_xref set source_ref_no= ? , status=? ,"
				+ " ACTION = ? ,DATE_OF_RESET=?  where cms_lsp_sys_xref_id=?";

		DefaultLogger.debug(this, "inside updatePSRStageActualLine");

		try {

			if ("expiryActualForPSR".equals(name)) {

				for (Entry<String, String> entrySet : sysXrefIdmap.entrySet()) {
					final String xrefId = entrySet.getKey();
					String[] split = entrySet.getValue().split(",");
					final String stageXrefId = split[0];
					final String sourceRefNo = split[1];
					final String action = split[2];
					int executeUpdate = getJdbcTemplate().update(expiryActual, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, sourceRefNo);
							ps.setString(2, status);
							ps.setString(3, action);
							ps.setDate(4, new java.sql.Date(newExtendedNextReviewDate.getTime()));
							ps.setString(5, xrefId);

						}
					});

					executeUpdate = getJdbcTemplate().update(expiryStage, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, sourceRefNo);
							ps.setString(2, status);
							ps.setString(3, action);
							ps.setDate(4, new java.sql.Date(newExtendedNextReviewDate.getTime()));
							ps.setString(5, stageXrefId);

						}
					});
				}
			}
			DefaultLogger.debug(this, "updated updatePSRStageActualLine");

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updatePSRStageActualLine:" + e.getMessage());
		}
		DefaultLogger.debug(this, "completed updatePSRStageActualLine");

	}
	
	
	//Covenant

	public ArrayList getCollateralIdList(long xrefId) throws Exception { 
		System.out.println("Inside getCollateralIdList(long xrefId)");
		String collateralId = "";
		long id = 0; 
		ArrayList collateralIdList = new ArrayList();
		
		String sql = " SELECT CMS.CMS_COLLATERAL_ID AS COLLATERAL_ID "
				+ " FROM SCI_LSP_SYS_XREF b, " + 
				"  SCI_LSP_APPR_LMTS a, " + 
				"  SCI_LSP_LMTS_XREF_MAP c , " + 
				"  CMS_LIMIT_SECURITY_MAP MAP, "
				+ "CMS_SECURITY CMS "
				+ " WHERE  "
				+ " b.CMS_LSP_SYS_XREF_ID= '"+xrefId+"' "
				+ " AND c.CMS_LSP_APPR_LMTS_ID = a.CMS_LSP_APPR_LMTS_ID " + 
				" AND c.CMS_LSP_SYS_XREF_ID  = b.CMS_LSP_SYS_XREF_ID " + 
				" " + 
				" AND a.CMS_LSP_APPR_LMTS_ID = MAP.CMS_LSP_APPR_LMTS_ID " + 
				" AND MAP.CHARGE_ID   in " + 
				"               (SELECT MAX(maps2.CHARGE_ID) " + 
				"       from cms_limit_security_map maps2 " + 
				"       where maps2.cms_lsp_appr_lmts_id = a.cms_lsp_appr_lmts_id " + 
				"       AND maps2.cms_collateral_id      = MAP.cms_collateral_id "
				+ " AND MAP.cms_collateral_id = CMS.CMS_COLLATERAL_ID "
				+ " AND CMS.SECURITY_SUB_TYPE_ID = 'AB100' " + 
				"        " + 
				"       )  " + 
				" AND (MAP.UPDATE_STATUS_IND <> 'D' OR MAP.UPDATE_STATUS_IND IS NULL) ";
		System.out.println("getCollateralIdList(long xrefId) => sql query =>"+sql);
		
		try {
			List queryForList = getJdbcTemplate().queryForList(sql);
			for (int i = 0; i < queryForList.size(); i++) {
				Map  map = (Map) queryForList.get(i);
				if(null!=map.get("COLLATERAL_ID") && !"".equals(map.get("COLLATERAL_ID"))) {
					collateralId= map.get("COLLATERAL_ID").toString();
				}
				collateralIdList.add(collateralId);
			}
			
		}
		catch (Exception ex) {
			System.out.println("exception in getCollateralIdList(long xrefId)");
		
			ex.printStackTrace();
			throw ex;
		}
		return collateralIdList;
		
	}
	
	
	
	public String getStockDocMonthByColId(String colId) throws Exception {
		System.out.println("Inside getStockDocMonthByColId.");
		String docMonth = "";
			
			String sql = " SELECT STOCK_DOC_MONTH,STOCK_DOC_YEAR FROM CMS_ASSET_GC_DET WHERE DUE_DATE IN (  " + 
					" SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID= '"+colId+"'  ) AND CMS_COLLATERAL_ID= '"+colId+"'  ";
			
			System.out.println("getStockDocMonthByColId(String colId) => sql query =>"+sql);
			
			try {
				List queryForList = getJdbcTemplate().queryForList(sql);
				for (int i = 0; i < queryForList.size(); i++) {
					Map  map = (Map) queryForList.get(i);
					if(null!=map.get("STOCK_DOC_MONTH") && !"".equals(map.get("STOCK_DOC_MONTH"))) {
						docMonth= map.get("STOCK_DOC_MONTH").toString();
					}
				}
				
			}
			catch (Exception ex) {
				System.out.println("exception in getStockDocMonthByColId(String colId)");
			
				ex.printStackTrace();
				throw ex;
			}
			return docMonth;
	}
	
	public String getStockDocYearByColId(String colId) throws Exception {
		System.out.println("Inside getStockDocYearByColId.");
	
		String docYear = "";
		DBUtil dbUtil = null;
			
			String sql = " SELECT STOCK_DOC_MONTH,STOCK_DOC_YEAR FROM CMS_ASSET_GC_DET WHERE DUE_DATE IN (  " + 
					" SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID= '"+colId+"'   ) AND CMS_COLLATERAL_ID= '"+colId+"'  ";
			
			System.out.println("getStockDocYearByColId(String colId) => sql query =>"+sql);
			try {
				List queryForList = getJdbcTemplate().queryForList(sql);
				for (int i = 0; i < queryForList.size(); i++) {
					Map  map = (Map) queryForList.get(i);
					if(null!=map.get("STOCK_DOC_YEAR") && !"".equals(map.get("STOCK_DOC_YEAR"))) {
						docYear= map.get("STOCK_DOC_YEAR").toString();
					}
	}	
	

			}
			catch (Exception ex) {
				System.out.println("exception in getStockDocYearByColId(String colId)");
			
				ex.printStackTrace();
				throw ex;
			}
			return docYear;
	}
	
	
	/*public String getStockDocMonthByColId(String colId) {
		System.out.println("Inside getStockDocMonthByColId.");
		String docMonth = "";
		DBUtil dbUtil = null;
		try{
			
			String sql = " SELECT STOCK_DOC_MONTH,STOCK_DOC_YEAR FROM CMS_ASSET_GC_DET WHERE DUE_DATE IN (  " + 
					" SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID= '"+colId+"'  ) AND CMS_COLLATERAL_ID= '"+colId+"'  ";
			
			System.out.println("getStockDocMonthByColId(String colId) => sql query =>"+sql);
			ResultSet rs=null;
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			 rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					docMonth = rs.getString("STOCK_DOC_MONTH");
				
				}
			}
			rs.close();
			if (dbUtil != null) {
				dbUtil.close();
			}
			}
			
			catch (Exception e) {
				System.out.println("exception in getStockDocMonthByColId(String colId");
				e.printStackTrace();
			}
		return docMonth;
	}*/

	
	
	/*public String getStockDocYearByColId(String colId) {
		String docYear = "";
		DBUtil dbUtil = null;
		try{
			
			String sql = " SELECT STOCK_DOC_MONTH,STOCK_DOC_YEAR FROM CMS_ASSET_GC_DET WHERE DUE_DATE IN (  " + 
					" SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID= '"+colId+"'   ) AND CMS_COLLATERAL_ID= '"+colId+"'  ";
			
			System.out.println("getStockDocYearByColId(String colId) => sql query =>"+sql);
			ResultSet rs=null;
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			 rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					docYear = rs.getString("STOCK_DOC_YEAR");
				
				}
			}
			rs.close();
			if (dbUtil != null) {
				dbUtil.close();
			}
			}
			
			catch (Exception e) {
				System.out.println("exception in getStockDocYearByColId(String colId");
				e.printStackTrace();
			}
		return docYear;
	}*/
	
	public OBNpaProvisionJob[] getNpaProvisionFileDetails() {

		System.out.println("Called getNpaProvisionFileDetails()...... ");
		String selectSQL = "SELECT TRUNC(REPORTING_DATE) AS REPORTING_DATE ," + 
				"  SYSTEM," + 
				"  PARTY_ID," + 
				"  COLLATERAL_TYPE," + 
				"  MIN(VALUATION_DATE) AS VALUATION_DATE," + 
				"  SUM(VALUATION_AMT_INR) AS VALUATION_AMOUNT," + 
				"  ORIGINAL_VALUE," + 
				"  START_DATE," + 
				"  MATURITY_DATE," + 
				"  ERROSION_NPA_TRACK" +
				"  FROM CMS_BASEL_NPA_TRACK " + 
				"  GROUP BY PARTY_ID," + 
				"  COLLATERAL_TYPE," + 
				"  SYSTEM," + 
				"  TRUNC(REPORTING_DATE)," + 
				"  ORIGINAL_VALUE," + 
				"  START_DATE," + 
				"  MATURITY_DATE," +
				"  ERROSION_NPA_TRACK";
		
		System.out.println("In getNpaProvisionFileDetails()...Query = "+selectSQL);

		return (OBNpaProvisionJob[]) getJdbcTemplate().query(selectSQL,
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processData(rs);
			}
		});
	}

	private OBNpaProvisionJob[] processData(ResultSet rs) throws SQLException {
		
		ArrayList list = new ArrayList();
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");  
        
		while (rs.next()) {
			OBNpaProvisionJob obj = new OBNpaProvisionJob();
			
			Date reportingDate = rs.getDate("REPORTING_DATE");
			if(null!=reportingDate && !"".equals(reportingDate))
			obj.setReportingDate(reportingDate);
			
			String system = rs.getString("SYSTEM");
			if(null!=system && !"".equalsIgnoreCase(system))
			obj.setSystem(system);
			else
				obj.setSystem("");
			
			String partyId = rs.getString("PARTY_ID");
			System.out.println("In getNpaProvisionFileDetails()...partyId = "+partyId);
			if(null!=partyId && !"".equalsIgnoreCase(partyId))
			obj.setPartyID(partyId);
			else
				obj.setMaturityDate("");
			
			String collateralType = rs.getString("COLLATERAL_TYPE");
			if(null!=collateralType && !"".equalsIgnoreCase(collateralType))
			obj.setCollateralType(collateralType);
			else
				obj.setCollateralType("");
			
			Date valuationDate = rs.getDate("VALUATION_DATE");
			if(null!=valuationDate && !"".equals(valuationDate))
			obj.setValuationDate(valuationDate);
			
			BigDecimal valuationAmt = rs.getBigDecimal("VALUATION_AMOUNT");
			obj.setValuationAmount(valuationAmt);

			Double originalValue = rs.getDouble("ORIGINAL_VALUE");
			obj.setOriginalValue(originalValue);
			
			String startDate = rs.getString("START_DATE");
			if(null!=startDate && !"".equalsIgnoreCase(startDate))
			obj.setStartDate(startDate);
			else
				obj.setStartDate("-");
			
			String maturityDate = rs.getString("MATURITY_DATE");
			if(null!=maturityDate && !"".equalsIgnoreCase(maturityDate))
			obj.setMaturityDate(maturityDate);
			else
				obj.setMaturityDate("-");
			
			BigDecimal erosion = rs.getBigDecimal("ERROSION_NPA_TRACK");
			if(null!=erosion )
				obj.setErosion(erosion);
			
			list.add(obj);
			}
		
		rs.close();
		
		return (OBNpaProvisionJob[]) list.toArray(new OBNpaProvisionJob[0]);
		
	}

//Below method for NPA Daily Stamping count
	public List getNpaDailyStampingCount() throws Exception {

		String selectSQL = "SELECT 'CLIMS' AS SOURCE_SYSTEM, COUNT(RECP.CUSTOMER_BKEY) AS CHILD_CUSTOMER_BKEY "
				+ "FROM  recp_customer_ALL RECP "
				+ "LEFT OUTER JOIN RECP_PARTY_ALL RECP_P  ON RECP.party_bkey = recp_p.party_bkey "
				+ " WHERE RECP.Customer_source_system NOT IN ('FW-LIMITS','ET','METAGRID ID','BAHRAIN','BTHKG','BTBAH') ";

		System.out.println("In getNpaDailyStampingCount() Query = " + selectSQL);

		return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List countList = new ArrayList();

				while (rs.next()) {
					String system = rs.getString("SOURCE_SYSTEM");
					String custBKey = rs.getString("CHILD_CUSTOMER_BKEY");
					String[] arr = new String[2];
					arr[0] = system;
					arr[1] = custBKey;
					countList.add(arr);
				}
				return countList;
			}
		});
}
	
	
	//Below method for EWS Stock Deferral
		public List getEwsStockDeferral() throws Exception {

			/*String selectSQL = "SELECT DISTINCT sp.lsp_le_id AS partyId,  sp.lsp_short_name AS partyname, ci.entry_name  AS segmentname,"
					// + " (SELECT region_name FROM cms_region WHERE id =sp.rm_region ) AS rmRegion,"
					 + " '' AS rmRegion, "
					+ " rm.rm_mgr_name AS rmname, "
					+ "TO_CHAR(date1.due_date,'DD/MON/YYYY') AS duedate,"
					+ " TO_CHAR(date1.LAST_UPDATED_ON,'DD/MON/YYYY') AS receiveddate,"
					+ "  ( SELECT DISTINCT document_description  FROM cms_document_globallist WHERE document_code = date1.doc_code ) AS periodDesc, "
					+ "cc.entry_name  AS component,stockdet.stock_type AS header, "
					+ " CASE "
				   // +" WHEN stockdet.stock_type = 'CurrentAsset'  THEN (SELECT has_insurance FROM cms_component WHERE component_code=cc.entry_code AND component_type = 'Current_Asset') "
					+ " WHEN stockdet.stock_type = 'CurrentLiabilities'  THEN  (SELECT has_insurance FROM cms_component WHERE component_code=cc.entry_code   AND component_type  = 'Current_Liability' )  END  AS flag,"
					+ " date1.fundedshare  AS collateralShare,"
					+ " stockdet.COMPONENT_AMOUNT  AS grossValue,"
					+ "  stockdet.margin  AS margin, "
					+ "stockdet.lonable AS netAmount,"
					+ " date1.calculateddp    AS DP,"
					+ "  CASE  WHEN date1.DP_CALCULATE_MANUALLY is null THEN 'NO'  WHEN date1.DP_CALCULATE_MANUALLY = 'NO' THEN 'NO' END  AS DP_CALCULATE_MANUALLY ,"
					+ " cms_com.COMPONENT_CATEGORY  AS COMPONENT_CATEGORY,"
					+ "  CASE WHEN trx.from_state = 'PENDING_PERFECTION' THEN(SELECT hist.login_id FROM trans_history hist  WHERE hist.transaction_id = trx.transaction_id  AND hist.from_state  = 'PENDING_PERFECTION'  AND hist.to_state  ='DRAFT'  )  WHEN trx.from_state = 'PENDING_CREATE'"
					+ "   THEN  (SELECT hist.login_id   FROM trans_history hist    WHERE hist.transaction_id = trx.transaction_id"
					+ "    AND hist.from_state    IN ('ND','DRAFT')    AND hist.to_state   ='PENDING_CREATE' )  WHEN trx.from_state = 'PENDING_UPDATE' THEN (SELECT hist.login_id  FROM trans_history hist"
					+ "    WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID)  FROM trans_history  WHERE transaction_id = trx.transaction_id AND from_state    IN ('ACTIVE','DRAFT') "
					+ "	AND to_state   = 'PENDING_UPDATE')  ) WHEN trx.from_state = 'PENDING_DELETE' THEN  (SELECT hist.login_id  FROM trans_history hist "
					+ " WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) FROM trans_history WHERE transaction_id = trx.transaction_id  AND from_state  ='ACTIVE' "
					+ "  AND to_state   ='PENDING_DELETE' )) WHEN trx.from_state = 'ACTIVE' THEN (SELECT hist.login_id  FROM trans_history hist   WHERE hist.TR_HISTORY_ID= "
					+ "  (SELECT MAX(TR_HISTORY_ID) FROM trans_history  WHERE transaction_id = trx.transaction_id "
					+ " AND from_state  ='ACTIVE' AND to_state  IN ('PENDING_UPDATE','PENDING_DELETE')) ) WHEN trx.from_state = 'REJECTED' THEN (SELECT hist.login_id "
					+ " FROM trans_history hist  WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) FROM trans_history WHERE transaction_id = trx.transaction_id"
					+ " AND from_state  = 'REJECTED' AND to_state   ='ACTIVE' )) WHEN trx.from_state = 'DRAFT' THEN  CASE"
					+ "  WHEN trx.status = 'PENDING_UPDATE'  THEN (SELECT hist.login_id FROM trans_history hist WHERE hist.TR_HISTORY_ID=(SELECT MAX(TR_HISTORY_ID) FROM trans_history"
					 + " WHERE transaction_id = trx.transaction_id AND from_state  = 'DRAFT' AND to_state   ='PENDING_UPDATE'))"
					  + " WHEN trx.status = 'ACTIVE' THEN (SELECT hist.login_id    FROM trans_history hist  WHERE hist.TR_HISTORY_ID= "
					+ "  (SELECT MAX(TR_HISTORY_ID) FROM trans_history WHERE transaction_id = trx.transaction_id  AND from_state  = 'DRAFT' AND to_state   ='ACTIVE')) END END AS Maker, "
					+ "  CASE   WHEN trx.from_state = 'PENDING_PERFECTION'  THEN (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')"
					+ "    FROM trans_history hist WHERE hist.transaction_id = trx.transaction_id  AND hist.from_state  = 'PENDING_PERFECTION' AND hist.to_state   ='DRAFT'  ) "
					+ "   WHEN trx.from_state = 'PENDING_CREATE' THEN (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					 + "   FROM trans_history hist WHERE hist.transaction_id = trx.transaction_id  AND hist.from_state    IN ('ND','DRAFT') "
					 + "   AND hist.to_state   ='PENDING_CREATE')  WHEN trx.from_state = 'PENDING_UPDATE' THEN (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					+ "    FROM trans_history hist WHERE hist.TR_HISTORY_ID=(SELECT MAX(TR_HISTORY_ID) FROM trans_history WHERE transaction_id = trx.transaction_id "
					+ "  AND from_state    IN ('ACTIVE','DRAFT') AND to_state   = 'PENDING_UPDATE') ) WHEN trx.from_state = 'PENDING_DELETE' THEN"
					 + "   (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') FROM trans_history hist WHERE hist.TR_HISTORY_ID=(SELECT MAX(TR_HISTORY_ID) FROM trans_history "
					+ "	WHERE transaction_id = trx.transaction_id  AND from_state  ='ACTIVE' "
					+ "  AND to_state   ='PENDING_DELETE' )) WHEN trx.from_state = 'ACTIVE'  THEN   (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					 + "   FROM trans_history hist  WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) "
					 + " FROM trans_history  WHERE transaction_id = trx.transaction_id  AND from_state  ='ACTIVE' AND to_state  IN ('PENDING_UPDATE','PENDING_DELETE') )  ) "
					 + "  WHEN trx.from_state = 'REJECTED'  THEN (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')  FROM trans_history hist    WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) FROM trans_history  WHERE transaction_id = trx.transaction_id "
					+ "  AND from_state  = 'REJECTED' AND to_state   ='ACTIVE'  ))  WHEN trx.from_state = 'DRAFT' THEN CASE "
					+ "  WHEN trx.status = 'PENDING_UPDATE' THEN   (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') FROM trans_history hist "
					+ "    WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) FROM trans_history  WHERE transaction_id = trx.transaction_id AND from_state  = 'DRAFT' "
					+ "  AND to_state   ='PENDING_UPDATE')) WHEN trx.status = 'ACTIVE' THEN "
					 + "   (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')  FROM trans_history hist "
					+ "    WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID)FROM trans_history  WHERE transaction_id = trx.transaction_id "
					+ "  AND from_state  = 'DRAFT' AND to_state   ='ACTIVE' ))  END END AS MakerDateTime, "
					+ "  CASE  WHEN trx.status  = 'PENDING_CREATE' OR trx.from_state = 'PENDING_PERFECTION' THEN '' WHEN trx.status = 'PENDING_UPDATE' THEN "
					+ "    (SELECT hist.login_id FROM trans_history hist WHERE hist.TR_HISTORY_ID=(SELECT MAX(TR_HISTORY_ID) FROM trans_history  WHERE transaction_id = trx.transaction_id "
					+ "  AND from_state    IN ('PENDING_UPDATE','ACTIVE') AND to_state  IN ('PENDING_UPDATE','ACTIVE'))) "
					 + "  WHEN ((trx.status  != 'PENDING_CREATE' AND trx.from_state != 'PENDING_PERFECTION') AND trx.status    != 'PENDING_UPDATE')THEN TO_CHAR(trx.login_id) END AS Approved_By, "
					+ "  CASE  WHEN trx.from_state = 'PENDING_CREATE'  THEN    (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					+ "    FROM trans_history hist   WHERE hist.transaction_id = trx.transaction_id   AND hist.from_state  ='PENDING_CREATE' "
					+ "    AND hist.to_state   ='ACTIVE'  ) WHEN trx.from_state = 'PENDING_UPDATE' THEN  (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					 + "   FROM trans_history hist  WHERE hist.TR_HISTORY_ID=(SELECT MAX(TR_HISTORY_ID) FROM trans_history WHERE transaction_id = trx.transaction_id  AND from_state  ='PENDING_UPDATE' "
					+ "  AND to_state   ='ACTIVE' )   ) WHEN trx.from_state = 'PENDING_DELETE'  THEN    (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					+ "    FROM trans_history hist  WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) FROM trans_history WHERE transaction_id = trx.transaction_id  AND from_state  ='PENDING_DELETE' "
					+ "  AND to_state   ='ACTIVE' )  )   WHEN trx.from_state = 'ACTIVE'  THEN   (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					 + "   FROM trans_history hist WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID) "
					+ "  FROM trans_history  WHERE transaction_id = trx.transaction_id AND from_state    IN ('PENDING_UPDATE','ACTIVE') "
					+ "  AND to_state  IN ('PENDING_UPDATE','ACTIVE') ) ) WHEN trx.from_state = 'REJECTED' "
					+ "   THEN (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')  FROM trans_history hist  WHERE hist.TR_HISTORY_ID=  (SELECT MAX(TR_HISTORY_ID)"
					+ " FROM trans_history WHERE transaction_id = trx.transaction_id  AND from_state  ='REJECTED'  AND to_state   ='ACTIVE'  )   ) "
					+ " WHEN trx.from_state = 'DRAFT'  THEN (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS') "
					+ "  FROM trans_history hist   WHERE hist.TR_HISTORY_ID= (SELECT MAX(TR_HISTORY_ID)  "
					+ " FROM trans_history WHERE transaction_id = trx.transaction_id  AND to_state   ='ACTIVE' )) END AS CheckerDateTime "
						
				 + "	FROM CMS_ASSET_GC_DET date1,  CMS_ASSET_GC_STOCK_DET stockdet,  CMS_RELATIONSHIP_MGR rm,  CMS_LIMIT_SECURITY_MAP lsm,  SCI_LSP_APPR_LMTS lmts ,  SCI_LSP_LMT_PROFILE pf, "
					+ "  SCI_LE_SUB_PROFILE sp, common_code_category_entry ci,common_code_category_entry cc,cms_component cms_com,TRANSACTION trx  "
				+ "	  WHERE trx.transaction_type ='COL'  AND trx.reference_id  = lsm.CMS_COLLATERAL_ID AND lsm.CMS_LSP_APPR_LMTS_ID  = lmts.CMS_LSP_APPR_LMTS_ID "
				+ "	AND (lsm.UPDATE_STATUS_IND  != 'D' OR lsm.UPDATE_STATUS_IND    IS NULL) AND lsm.CMS_COLLATERAL_ID    = date1.CMS_COLLATERAL_ID "
				+ "	AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID AND sp.CMS_LE_SUB_PROFILE_ID  = pf.CMS_CUSTOMER_ID AND rm.id(+)  = sp.relation_mgr "
				+ "	AND ci.entry_code(+)    = sp.lsp_sgmnt_code_value AND lsm.CHARGE_ID IN  (SELECT MAX(MAPS2.CHARGE_ID) "
				+ "	  FROM cms_limit_security_map maps2  WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id AND maps2.cms_collateral_id    = date1.cms_collateral_id ) "
				+ "	AND date1.STATUS  = 'APPROVED' "
				+ "	AND ci.category_code  ='HDFC_SEGMENT' "
				+ " AND ci.active_status  ='1' "
				+ "	AND sp.status   != 'INACTIVE' "
				+ "	AND cc.active_status  ='1' "
				+ "	AND stockdet.component    = cc.entry_code "
				+ "	AND cms_com.component_code  =stockdet.component "
				+ "	AND stockdet.gc_det_id    =date1.gc_det_id "
				+ "	AND (DATE1.DP_CALCULATE_MANUALLY !='YES' OR date1.DP_CALCULATE_MANUALLY is null)"; 
*/		
			String selectSQL ="SELECT * FROM EWS_STOCK_DEFERRAL_MV";
			System.out.println("In  getEwsStockDeferral Query = " + selectSQL);

			return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List ewsStockDef = new ArrayList();

					while (rs.next()) {
						String partyId = rs.getString("partyId");						
						String partyName= rs.getString("partyname");
						String segmentName = rs.getString("segmentname");
						String rmRegion= rs.getString("rmRegion");
						String rmName = rs.getString("rmname");
						String dueDate= rs.getString("duedate");
						String receivedDate = rs.getString("receiveddate");
						String periodDesc= rs.getString("periodDesc");
						String component = rs.getString("component");
						String header= rs.getString("header");
						String flag = rs.getString("flag");
						String collateralShare= rs.getString("collateralShare");
						String grossValue = rs.getString("grossValue");
						String margin= rs.getString("margin");
						String netAmount = rs.getString("netAmount");
						String dp= rs.getString("DP");
						String dpCalculateManually = rs.getString("DP_CALCULATE_MANUALLY");
						String componentCategory= rs.getString("COMPONENT_CATEGORY");
						String maker = rs.getString("Maker");
						String makerDateTime= rs.getString("MakerDateTime");
						String approvedBy = rs.getString("Approved_By");
						String checkerDateTime= rs.getString("CheckerDateTime");
																						
					//	System.out.println("partyId:: "+partyId + " componentCategory:: "+componentCategory);
						String[] arr = new String[22];
						
						arr[0] = partyId;
						arr[1] = partyName;
						arr[2] = segmentName;
						arr[3] = rmRegion;
						arr[4] = rmName;
						arr[5] = dueDate;
						arr[6] = receivedDate;
						arr[7] = periodDesc;
						arr[8] = component;
						arr[9] = header;
						arr[10] = flag;
						arr[11] = collateralShare;
						arr[12] = grossValue;
						arr[13] = margin;
						arr[14] = netAmount;
						arr[15] = dp;
						arr[16] = dpCalculateManually;
						arr[17] = componentCategory;
						arr[18] = maker;
						arr[19] = makerDateTime;
						arr[20] = approvedBy;
						arr[21]= checkerDateTime;
						
						ewsStockDef.add(arr);
					}
					return ewsStockDef;
				}
			});
	}
  //For SCM Scheduler
	@Override
	public List<Long> getFailedReleaseLineRequestforScm()  {
		/*String sql = "select  id from cms_js_interface_log " + 
		" where modulename in ('RELEASE LINE','Release Line STP')"+
//		" and status in ('Error','Fail')" + 
		" and status in ('Error') " + 
		"  AND PARTYID IS NOT NULL " +
		" order by id asc";*/

/*String sql = "SELECT DISTINCT JS.requestdatetime,JS.PARTYID,JS.MODULENAME,JS.id as id,JS.SERIAL_NO,JS.LINE_NO FROM          " + 
		"		(SELECT MAX(requestdatetime) AS requestdatetime ,PARTYID,MODULENAME,SERIAL_NO,LINE_NO FROM (         " + 
		"			select partyId,count(partyId),operation,requestdatetime,ID,modulename,SERIAL_NO,LINE_NO from cms_js_interface_log " + 
		"			where           " + 
		"			modulename in ('RELEASE LINE','Release Line STP')          " + 
		"			and status in  ('Error')          " + 
		"			AND PARTYID IS NOT NULL          " + 
		"			group by partyId,operation ,requestdatetime,modulename,ID,SERIAL_NO,LINE_NO   )      " + 
		"			 GROUP BY PARTYID,MODULENAME,SERIAL_NO,LINE_NO) A,         " + 
		"			cms_js_interface_log JS         " + 
		"			 WHERE A.requestdatetime = JS.requestdatetime         " + 
		"			AND A.MODULENAME = JS.MODULENAME         " + 
		"			AND JS.status ='Error'";
*/

String sql = "SELECT DISTINCT JS.requestdatetime, JS.PARTYID, JS.id AS id, JS.SERIAL_NO, JS.LINE_NO FROM (SELECT MAX(requestdatetime) AS requestdatetime , PARTYID, SERIAL_NO, LINE_NO FROM  " + 
		"  (SELECT partyId, COUNT(partyId), operation, requestdatetime, ID, SERIAL_NO, LINE_NO FROM cms_js_interface_log WHERE modulename IN ('RELEASE LINE','Release Line STP') AND status IN  " + 
		"  ('Error') AND PARTYID IS NOT NULL GROUP BY partyId, operation , requestdatetime, ID, SERIAL_NO, LINE_NO ) GROUP BY PARTYID, SERIAL_NO, LINE_NO ) A, cms_js_interface_log JS WHERE  " + 
		"  A.requestdatetime = JS.requestdatetime AND JS.status ='Error' AND JS.modulename IN ('RELEASE LINE','Release Line STP') AND JS.LINE_NO = A.LINE_NO AND JS.SERIAL_NO = A.SERIAL_NO ";

		System.out.println("getFailedReleaseLineRequestforScm =>Select query is getFailedReleaseLineRequestforScm=> "+sql);  	//limitProfileId,line_no,serial_no, removed the columns for now
		
	  return (List<Long>) getJdbcTemplate().query(sql,new ResultSetExtractor() {
			
			@Override
			public List<Long> extractData(ResultSet rs) throws SQLException {
				List<Long> returnData = new ArrayList<Long>();
				while(rs.next()) {
						returnData.add(rs.getLong("id"));
				}
				return returnData;
			}
		});

	}
	
	public List<OBJsInterfaceLog> getInterfaceLogDetailsForLine(Long id) {
		String query ="select ID ," + 
				"	INTERFACENAME, " + 
				"	REQUESTDATETIME, " + 
				"	RESPONSEDATETIME , " + 
				"	ERRORMESSAGE, " + 
				"	ERRORCODE, " + 
				"	STATUS, " + 
				"	TRANSACTIONID , " + 
				"    REQUESTMESSAGE , " + 
				"	RESPONSEMESSAGE , " + 
				"	MODULENAME , " + 
				"	OPERATION ," + 
				"	PARTYID, " + 
				"	PARTYNAME ," + 
				"	scmflag ," + 
				"	LIMITPROFILEID ," + 
				"    LINE_NO ,  " + 
				"    SERIAL_NO, " + 
				"    FAILCOUNT " + 
				"    from cms_js_interface_log where id ='"+id+"' " ;
		System.out.println("getInterfaceLogDetailsForLine =>Select query is => "+query);
		List resultList = getJdbcTemplate().query(query, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	OBJsInterfaceLog result = new OBJsInterfaceLog();
    			result.setId(rs.getLong("id"));
    			result.setInterfaceName(rs.getString("INTERFACENAME"));
    			result.setRequestDateTime(rs.getDate("REQUESTDATETIME"));
    			result.setResponseDateTime(rs.getDate("REQUESTDATETIME"));
    			result.setRequestMessage(rs.getString("REQUESTMESSAGE"));
    			result.setResponseMessage(rs.getString("RESPONSEMESSAGE"));
    			result.setErrorCode(rs.getString("ERRORCODE"));
    			result.setErrorMessage(rs.getString("ERRORMESSAGE"));
    			result.setStatus(rs.getString("STATUS"));
    			result.setTransactionId(rs.getLong("TRANSACTIONID"));
    			result.setModuleName(rs.getString("MODULENAME"));
    			result.setOperation(rs.getString("OPERATION"));
    			result.setPartyId(rs.getString("PARTYID"));
    			result.setPartyName(rs.getString("PARTYNAME"));
    			result.setScmFlag(rs.getString("scmflag"));
    			result.setLimitProfileId(rs.getString("LIMITPROFILEID"));
    			result.setLine_no(rs.getString("LINE_NO"));
    			result.setSerial_no(rs.getString("SERIAL_NO"));
    			result.setFailCount(rs.getInt("FAILCOUNT"));
    			
                return result;
            }
        }
        );
		System.out.println("getInterfaceLogDetailsForLine =>Done.");
   	 return resultList;

		
	}
	
	public void updateTheFailedResponseLog(OBJsInterfaceLog log) {
		System.out.println("Inside the updateTheFailedResponseLog to update the failed request log.getId : "+log.getId()+"log.getfailCount : "+log.getFailCount());
		
		String query = "UPDATE cms_js_interface_log SET RESPONSEMESSAGE = ?, RESPONSEDATETIME = ?,ERRORCODE = ?, ERRORMESSAGE = ?,STATUS = ?,FAILCOUNT = ?"
					+ " WHERE id = ?";
			System.out.println(" updateTheFailedResponseLog =>SQL query=>"+query);
			System.out.println(" updateTheFailedResponseLog parameters=>log.getResponseMessage()=>"+log.getResponseMessage()+" .. log.getResponseDateTime()=>"+log.getResponseDateTime()+"..log.getErrorCode()=>"+log.getErrorCode()+
					"..log.getErrorMessage()=>"+log.getErrorMessage()+"..log.getStatus()=>"+log.getStatus()+" .. new Long(log.getId())=>"+new Long(log.getId()));
			getJdbcTemplate().update(query,
					new Object[]{log.getResponseMessage(),log.getResponseDateTime(),log.getErrorCode(),log.getErrorMessage(),
							log.getStatus(),log.getFailCount(),new Long(log.getId())});
		}
	
	
	public void insertLogForSTP(OBJsInterfaceLog log) {
		DefaultLogger.debug(this, "Inside Starting insertLogForSTP(log)...");
		System.out.println("Inside Starting insertLogForSTP(log)...");
		String id = generateSourceSeqNoForStp();
			String query = "INSERT INTO  cms_js_interface_log (ID ," + 
					"			INTERFACENAME, " + 
					"			REQUESTDATETIME, " + 
					"			RESPONSEDATETIME , " + 
					"			ERRORMESSAGE, " + 
					"		    ERRORCODE, " + 
					"			STATUS, " + 
					"			TRANSACTIONID , " + 
					"		    REQUESTMESSAGE ," + 
					"		    RESPONSEMESSAGE , " + 
					"			MODULENAME , " + 
					"		    OPERATION ," + 
					"	        PARTYID, " + 
					"	        PARTYNAME ," + 
					"	        scmflag ," + 
					"           LIMITPROFILEID ," + 
					"    		LINE_NO ,  " + 
					"			SERIAL_NO ) values (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?)";
			DefaultLogger.debug(this, "insertLogForSTP(log)...id....."+id+"....query=>"+query);
			System.out.println("insertLogForSTP(log)...id....."+id+"....query=>"+query);
			System.out.println("insertLogForSTP(log)...log.getRequestMessage()=>"+log.getRequestMessage());
			System.out.println("insertLogForSTP(log)...log.getErrorMessage()=>"+log.getErrorMessage());
			System.out.println("insertLogForSTP(log)...log.getResponseDateTime()=>"+log.getResponseDateTime());
			System.out.println("insertLogForSTP(log)...log.getRequestDateTime()=>"+log.getRequestDateTime());
			try{
			getJdbcTemplate().update(
					query,
					new Object[]{id,log.getInterfaceName(),log.getRequestDateTime(),log.getResponseDateTime(),log.getErrorMessage(),log.getErrorCode(),
							log.getStatus(),log.getTransactionId(),log.getRequestMessage(),log.getResponseMessage(),log.getModuleName(),log.getOperation(),
							log.getPartyId(),log.getPartyName(),log.getScmFlag(),log.getLimitProfileId(),log.getLine_no(),log.getSerial_no()});
			
	}catch(Exception e){
		DefaultLogger.error(this, "Exception Caught =>insertLogForSTP(log)...id....."+id+"....query=>"+query, e);
		System.out.println("Exception caught in insertLogForSTP(OBJsInterfaceLog log) => e=>"+e);
		e.printStackTrace();
	}
	}
	
	public void updateResponseLogForSTP(IJsInterfaceLog log) {
		 System.out.println("Inside the updateTheFailedResponseLog to update the failed request "+log.getId());
	
			String query = "UPDATE cms_js_interface_log SET RESPONSEMESSAGE = ?, RESPONSEDATETIME = ?,ERRORCODE = ?, ERRORMESSAGE = ?,STATUS = ?"
					+ " WHERE id = ?";

			getJdbcTemplate().update(query,
					new Object[]{log.getResponseMessage(),log.getResponseDateTime(),log.getErrorCode(),log.getErrorMessage(),
							log.getStatus(),new Long(log.getId())});
		}
	
	
	public String getScmFlagStgforStp(String srcRefNo) throws SearchDAOException {
		
		String stgScmFlagqry ="  select nvl(scm_flag,'No') scm_flag from CMS_STAGE_LSP_SYS_XREF where SOURCE_REF_NO  = '"+srcRefNo+"'";
		
		DefaultLogger.debug(this,"Select query for getScmFlagStgforStp is  "+stgScmFlagqry);
		
		List resultList = getJdbcTemplate().query(stgScmFlagqry, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("scm_flag");
			}
		});
		if(resultList.size()==0){
			resultList.add("No");
		}
		return resultList.get(0).toString();
	}
	
	public String getScmFlagMainforStp(String srcRefNo) throws SearchDAOException {
		
	String mainScmFlagqry ="  select nvl(scm_flag,'No') scm_flag from SCI_LSP_SYS_XREF where SOURCE_REF_NO  = '"+srcRefNo+"'";
	
	DefaultLogger.debug(this,"Select query for mainScmFlagqry is  "+mainScmFlagqry);
	System.out.println("Select query for mainScmFlagqry is  =>"+mainScmFlagqry);

	List resultList = getJdbcTemplate().query(mainScmFlagqry, new RowMapper() {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("scm_flag");
		}
	});
	if(resultList.size()==0){
		resultList.add("No");
	}
	return resultList.get(0).toString();
	}
	
	
	
	
	public List<RetrieveScmLineRequest>  getLineDetailsforStp(String srcRefId) {
		DefaultLogger.debug(this, "Inside getLineDetailsforStp(String srcRefId).. ");
		System.out.println("Inside getLineDetailsforStp(String srcRefId).. ");
		
		String query ="SELECT   "+
				"SCI.CMS_LIMIT_PROFILE_ID,"+
				"SCI.CMS_LSP_APPR_LMTS_ID,"+
				" nvl(XREF.scm_flag,'No') scm_flag ,   "+
				" MAPSS.CMS_STATUS,   "+
				" XREF.ACTION,"+
				" XREF.status,"+
				"SPRO.lsp_le_id,   "+
				"SPRO.lsp_short_name,   "+
				" XREF.FACILITY_SYSTEM,  "+
				"SCI.FACILITY_NAME,   "+
				" XREF.CURRENCY,  "+
				"SCI.CMS_REQ_SEC_COVERAGE ,  "+ 
				"nvl(SCI.guarantee,'No') guarantee,  "+
				"(select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyName," + 
				" REGEXP_SUBSTR(SCI.liability_id,'[^-]+',3,3)  AS liability_id,XREF.RELEASED_AMOUNT,SCI.RELEASABLE_AMOUNT,XREF.MAIN_LINE_CODE," + 
				" (select(CASE when(SCI.guarantee = 'No' and  XREF.MAIN_LINE_CODE is not null) then SPRO.lsp_le_id " + 
				" when(SCI.guarantee = 'Yes' and  XREF.MAIN_LINE_CODE is not null) then " + 
				" (select lsp_le_id  from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name)" + 
				" else null END) from dual) mainline_partyId," + 
				" (select(CASE when(SCI.guarantee = 'No' and  XREF.MAIN_LINE_CODE is not null) then SPRO.lsp_le_id " + 
				" when(SCI.guarantee = 'Yes' and  XREF.MAIN_LINE_CODE is not null) then " + 
				" (select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name)" + 
				" else null END) from dual) mainline_party_name,"+
				" (select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyName,   "+
				"  nvl(SCI.LMT_TYPE_VALUE,'No') LMT_TYPE_VALUE,"+
				" XREF.RELEASED_AMOUNT,   "+
				" XREF.CMS_LSP_SYS_XREF_ID,   "+
				" XREF.ACTION,   "+
				" XREF.MAIN_LINE_CODE,   "+
				" XREF.FACILITY_SYSTEM_ID,   "+
				" XREF.LINE_NO,    "+
				" XREF.SERIAL_NO,    "+
				" (select branchcode from CMS_FCCBRANCH_MASTER where id = XREF.LIAB_BRANCH and status ='ACTIVE') LIAB_BRANCH ,   "+
				" to_char(XREF.LIMIT_START_DATE,'DD/MON/YYYY') LIMIT_START_DATE,    "+
				" nvl(XREF.AVAILABLE,'No') AVAILABLE ,   "+
				" nvl(XREF.REVOLVING_LINE,'No') REVOLVING_LINE,   "+
				" nvl(XREF.FREEZE,'No') FREEZE,   "+
				" XREF.SEGMENT_1,   "+
				" (select entry_name from common_code_category_entry where XREF.UNCONDI_CANCL = entry_code and ACTIVE_STATUS = '1'" + 
				" AND category_code='UNCONDI_CANCL_COMMITMENT' ) UNCONDI_CANCL,    "+
				" to_char(XREF.DATE_OF_RESET,'DD/MON/YYYY') DATE_OF_RESET,   "+
				" to_char(XREF.LAST_AVAILABLE_DATE,'DD/MON/YYYY') LAST_AVAILABLE_DATE ,   "+
				" XREF.INTERNAL_REMARKS,   "+
				" XREF.LIMIT_TENOR_DAYS,"+
				"SCI.IS_RELEASED,"+
				"SCI.IS_ADHOC,"+
				"SCI.ADHOC_LMT_AMOUNT,"+
				"SCI.RELEASABLE_AMOUNT,"+
				" XREF.product_allowed,"+
				" nvl(XREF.is_priority_sector,'No') is_priority_sector,"+
				" XREF.PRIORITY_SECTOR,"+
				" XREF.INT_RATE_FIX,"+
				"  (SELECT UDF20_VALUE FROM SCI_LSP_LMT_XREF_UDF WHERE SCI_LSP_SYS_XREF_ID = XREF.CMS_LSP_SYS_XREF_ID) AS UDF20_VALUE   "+
				" FROM "+
				"  SCI_LSP_APPR_LMTS SCI,"+
				 " SCI_LSP_SYS_XREF XREF,"+
				"  SCI_LSP_LMTS_XREF_MAP MAPSS,"+
				 " SCI_LSP_LMT_PROFILE PF,"+
				 " SCI_LE_SUB_PROFILE SPRO "+
//				 " ,SCI_LSP_LMT_XREF_UDF UDF"+
				 " WHERE SCI.CMS_LIMIT_PROFILE_ID    = PF.CMS_LSP_LMT_PROFILE_ID"+
				 " AND PF.CMS_CUSTOMER_ID          = Spro.CMS_LE_SUB_PROFILE_ID"+
				  " AND SCI.CMS_LSP_APPR_LMTS_ID  = MAPSS.CMS_LSP_APPR_LMTS_ID"+
				 "  AND MAPSS.CMS_LSP_SYS_XREF_ID = XREF.CMS_LSP_SYS_XREF_ID"+
//				  " AND XREF.CMS_LSP_SYS_XREF_ID = UDF.SCI_LSP_SYS_XREF_ID"+
				  " AND XREF.SOURCE_REF_NO = '"+srcRefId+"'";
		DefaultLogger.debug(this, "Sql Query => "+query);
		System.out.println("List<RetrieveScmLineRequest>  getLineDetailsforStp(String srcRefId)=>Sql Query => "+query);
		final String sourceRefNum = srcRefId;
		List resultList = getJdbcTemplate().query(query, new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        		RetrieveScmLineRequest lineDetails=new RetrieveScmLineRequest();
        		try{
            	lineDetails.setScmFlag("Yes".equalsIgnoreCase(rs.getString("scm_flag"))?"Y":"N");
			    if("CLOSE".equalsIgnoreCase(rs.getString("action"))) {lineDetails.setAction("C");}
			    else if ("REOPEN".equalsIgnoreCase(rs.getString("action"))) {lineDetails.setAction("O");}
				else if ("MODIFY".equalsIgnoreCase(rs.getString("action"))) {lineDetails.setAction("U");}
				else if ("NEW".equalsIgnoreCase(rs.getString("action"))){lineDetails.setAction("A");} 
				else { lineDetails.setAction(lineDetails.getAction());}
			    lineDetails.setPartyId(rs.getString("LSP_LE_ID"));
			    lineDetails.setPartyName(rs.getString("lsp_short_name"));
			    lineDetails.setAdhocFlag(rs.getString("IS_ADHOC"));
			    lineDetails.setAdhocLimitAmount(rs.getString("ADHOC_LMT_AMOUNT"));
			    lineDetails.setAvailableFlag("Yes".equalsIgnoreCase(rs.getString("AVAILABLE"))?"Y":"N");
			    lineDetails.setCommitment(rs.getString("UNCONDI_CANCL"));
			    lineDetails.setCurrency(rs.getString("CURRENCY"));
			    lineDetails.setFacilityName(rs.getString("FACILITY_NAME"));
			    lineDetails.setSublimitFlag("Yes".equalsIgnoreCase(rs.getString("LMT_TYPE_VALUE"))?"Y":"N");
			    lineDetails.setFreezeFlag("Yes".equalsIgnoreCase(rs.getString("FREEZE"))?"Y":"N");
			    lineDetails.setGuarantee("Yes".equalsIgnoreCase(rs.getString("GUARANTEE"))?"Y":"N");
			    lineDetails.setGuaranteeliabilityId(rs.getString("liability_id"));
			    lineDetails.setGuaranteePartyName(rs.getString("guaranteePartyName"));
			    lineDetails.setLiabBranch(rs.getString("LIAB_BRANCH"));
			    lineDetails.setLimitExpiryDate(rs.getString("DATE_OF_RESET"));
			    lineDetails.setLimitStartDate(rs.getString("LIMIT_START_DATE"));
			    lineDetails.setLineNumber(rs.getString("LINE_NO"));
			    lineDetails.setMainLineNumber(rs.getString("MAIN_LINE_CODE"));
			    lineDetails.setMainLinePartyId(rs.getString("mainline_partyId"));
				lineDetails.setMainLinePartyName(rs.getString("mainline_party_name"));
			    lineDetails.setMainLineSystemID(rs.getString("FACILITY_SYSTEM"));
			    lineDetails.setSystem(rs.getString("FACILITY_SYSTEM"));
			    lineDetails.setSystemId(rs.getString("FACILITY_SYSTEM_ID"));
			    lineDetails.setSerialNumber(rs.getString("SERIAL_NO"));
			    lineDetails.setSegment(rs.getString("SEGMENT_1"));
			    lineDetails.setNpa(rs.getString("UDF20_VALUE"));
			    lineDetails.setProductAllowed(rs.getString("product_allowed"));
			    lineDetails.setPslFlag("Yes".equalsIgnoreCase(rs.getString("is_priority_sector"))?"Y":"N");
			    lineDetails.setPslValue(rs.getString("PRIORITY_SECTOR"));
			    lineDetails.setRateValue(rs.getString("INT_RATE_FIX"));
			    lineDetails.setReleaseableAmount(rs.getString("RELEASABLE_AMOUNT"));
			    lineDetails.setReleasedAmount(rs.getString("RELEASED_AMOUNT"));
			    lineDetails.setReleaseFlag(rs.getString("IS_RELEASED"));
			    lineDetails.setRevolvingLine("Yes".equalsIgnoreCase(rs.getString("REVOLVING_LINE"))?"Y":"N");
			    lineDetails.setRemarks(rs.getString("INTERNAL_REMARKS"));
			    lineDetails.setSanctionAmountINR(rs.getString("CMS_REQ_SEC_COVERAGE"));
			    lineDetails.setSanctionAmount(rs.getString("CMS_REQ_SEC_COVERAGE"));
			    lineDetails.setTenorDays(rs.getString("LIMIT_TENOR_DAYS"));
			    lineDetails.setLimitProfileId(rs.getString("CMS_LIMIT_PROFILE_ID"));
			    
			    String actionFromFcubsDataLog = getActionFromFcubsDataLog(sourceRefNum);
			    System.out.println("actionFromFcubsDataLog=>"+actionFromFcubsDataLog);
			    String latestAction = "";
			    if("NEW".equalsIgnoreCase(actionFromFcubsDataLog)){
			    	lineDetails.setAction("A");
			    }else{
			    	latestAction = getLatestOperationStatus(rs.getString("CMS_LIMIT_PROFILE_ID"),rs.getString("LINE_NO"),rs.getString("SERIAL_NO"));
			    	if(!"".equals(latestAction) && null != latestAction){
			    	lineDetails.setAction(latestAction);
			    	}else{
			    		System.out.println("latestAction is empty=>"+latestAction);
			    		latestAction = "A";
			    		lineDetails.setAction("A");
			    		System.out.println("After Set Action latestAction=>"+latestAction);
			    	}
			    	/*if(!"C".equals(lineDetails.getAction())) {
			    		if(latestAction!=null && "A".equalsIgnoreCase(latestAction)) {
			    			lineDetails.setAction("A");
			    		}else{
			    			lineDetails.setAcgetActionFromFcubsDataLogtion(latestAction);
			    		}
					}*/
			    }
			    System.out.println("lineDetails.getAction=>"+lineDetails.getAction());
			    System.out.println("latestAction=>"+latestAction);
//			    DefaultLogger.debug(this,"Select query for lineDetails is  "+lineDetails); 
			    DefaultLogger.debug(this,"latestAction is  "+latestAction); 
//			    System.out.println("Select query for lineDetails is  "+lineDetails);
			    System.out.println("latestAction is  "+latestAction);
			    System.out.println("Serial Number =>"+lineDetails.getSerialNumber()+" ** System=>"+lineDetails.getSystem()+" ** System Id =>"+lineDetails.getSystemId()
			    		+" ** Segment =>"+lineDetails.getSegment()+" ** Party Id=>"+lineDetails.getPartyId()+" ** Line number=>"+lineDetails.getLineNumber()+
			    		" ** Action=>"+lineDetails.getAction());
        		}
        		catch(Exception e){
        			DefaultLogger.debug(this,"Exception caught in getLineDetailsforStp(String srcRefId)=>  e=>"+e);
        			System.out.println("Exception caught in getLineDetailsforStp(String srcRefId)=>  e=>"+e);
        			e.printStackTrace();
        		}
                return lineDetails;
            }
        }
        );
   	 return resultList;

		
	}
	
	
	
	public String generateSourceSeqNoForStp()  {
		String generateSourceString="select concat (to_char(sysdate,'YYYYMMDD'), LPAD(CMS_JS_INTERFACE_SEQ.nextval, 9,'0')) sequence from dual";
		
		DefaultLogger.debug(this,"Select query for generateSourceString is  "+generateSourceString);

		List resultList = getJdbcTemplate().query(generateSourceString, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("sequence");
			}
		});
		if(resultList.size()==0){
			resultList.add("0");
		}
		return resultList.get(0).toString();
		    
	}
	
	public String getLatestOperationStatus(String limitProfileId,String LineNo,String SerialNo )  {
		String operationSuccessCount = "select count (1) AS CNT from cms_js_interface_log "+ 
		" where limitprofileId = '"+limitProfileId+"'  and line_no = '"+LineNo+"' and serial_no = '"+SerialNo+"'   AND STATUS='Success' "+
		 " order by requestdatetime desc";
		
	DefaultLogger.debug(this,"Select query for operationSuccessCount is ==  "+operationSuccessCount);
	System.out.println("Select query for operationSuccessCount is ==  "+operationSuccessCount);
	
	String counts = "";
	try {
		List queryForList = getJdbcTemplate().queryForList(operationSuccessCount);
		for (int i = 0; i < queryForList.size(); i++) {
			Map  map = (Map) queryForList.get(i);
			if(null!=map.get("CNT") && !"".equals(map.get("CNT"))) {
				counts= map.get("CNT").toString();
			}else{
				counts = "";
			}
		}
		System.out.println("LIMITDAO.java=> getLatestOperationStatus=>counts=>"+counts);
	}
	catch (Exception ex) {
		System.out.println("Exception in getLatestOperationStatus=>Line 9171=>counts=>"+counts);
	
		ex.printStackTrace();
		
	}
	
	
	System.out.println(" getLatestOperationStatus=>Line 9178=>counts=>"+counts);
	
	if ("".equals(counts) || "0".equals(counts) || null==counts) {
		
		
	
		
		
		String operationStatusQuery = "select OPERATION , status from (select OPERATION,SCMFLAG,partyId,requestdatetime,status from cms_js_interface_log " + 
				" where limitprofileId = '"+limitProfileId+"'  and line_no = '"+LineNo+"' and serial_no = '"+SerialNo+"'  " + 
				" order by requestdatetime desc) where rownum=1";
		
	DefaultLogger.debug(this,"Select query for operationStatusQuery is  "+operationStatusQuery);

	List resultList = getJdbcTemplate().query(operationStatusQuery, new RowMapper() {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			if(rs.getString("status").equalsIgnoreCase("Error")||rs.getString("status").equalsIgnoreCase("Fail")) {
				return rs.getString("OPERATION");
				}else {
					return "U";
				}
		}
	});
	if(resultList.size()==0){
		resultList.add("");
	}
	return resultList.get(0).toString();
	}else {
		return "U";
	}
}


	@Override
	public List<ICoBorrowerDetails> getPartyCoBorrowerDetails(Long customerId) {
		String sql = "SELECT co_borrower_liab_id, co_borrower_name FROM sci_le_co_borrower bor "+
				" INNER JOIN sci_le_sub_profile sub ON sub.cms_le_main_profile_id = bor.cms_le_main_profile_id "+
				" WHERE cms_le_sub_profile_id=?";
		
		return getJdbcTemplate().query(sql, new Object[] {customerId}, new RowMapper<ICoBorrowerDetails>() {
			@Override
			public ICoBorrowerDetails mapRow(ResultSet rs, int rn) throws SQLException {
				ICoBorrowerDetails coBorrower = new OBCoBorrowerDetails();
				coBorrower.setCoBorrowerLiabId(rs.getString("co_borrower_liab_id"));
				coBorrower.setCoBorrowerName(rs.getString("co_borrower_liab_id") +"-"+ rs.getString("co_borrower_name"));
				return coBorrower;
			}
		});
	}
	
	public String checkUserRoleAccess(String role, String moduleId, String operation) throws SearchDAOException {

		String access = "";
		String queryStr = "";

		if ("ADD".equalsIgnoreCase(operation))
			access = "ADD_ACCESS";
		else if ("EDIT".equalsIgnoreCase(operation))
			access = "EDIT_ACCESS";
		else if ("DELETE".equalsIgnoreCase(operation))
			access = "DELETE_ACCESS";
		else if ("APPROVE".equalsIgnoreCase(operation))
			access = "APPROVE_ACCESS";
		else if("DOWNLOAD".equalsIgnoreCase(operation))
			access = "DOWNLOAD_IMG";
		else
			access = "VIEW_ACCESS";

		queryStr = "SELECT " + access + " FROM CMS_MODULE_ACCESS WHERE ROLE=? AND MODULE_ID=? ";
		final String roleAccess = new String(access);
		List argList = new ArrayList();
		argList.add(new Long(Long.parseLong(role)));
		argList.add(new Long(Long.parseLong(moduleId)));

		List resultList = getJdbcTemplate().query(queryStr, argList.toArray(), new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(roleAccess);
			}
		});

		if (resultList.size() == 0) {
			resultList.add("-");
		}
		return resultList.get(0).toString();
	}
	public boolean isCoBorrowerAddedInFacility(String coBorrowerId, long profileId) {

	//	String sql = "SELECT COUNT(1) FROM sci_fac_co_borrower WHERE co_borrower_liab_id=? AND cms_le_main_profile_id =?";

	 String sql = " SELECT count (distinct bor.id ) FROM sci_fac_co_borrower bor "
	  		+ "INNER JOIN SCI_LSP_APPR_LMTS APPR  ON bor.CMS_LSP_APPR_LMTS_ID = APPR.CMS_LSP_APPR_LMTS_ID"
	  		+ "  INNER JOIN SCI_LSP_LMT_PROFILE LMT  ON LMT.CMS_LSP_LMT_PROFILE_ID = APPR.CMS_LIMIT_PROFILE_ID  "
	  		+ "INNER JOIN SCI_LE_SUB_PROFILE SUB   ON SUB.LSP_LE_ID  = LMT.LLP_LE_ID "
	  		+ "where   bor.co_borrower_liab_id= ? "
	  		+ " AND   SUB.CMS_LE_MAIN_PROFILE_ID = ? ";

	  
		return (int) getJdbcTemplate().queryForObject(sql, new Object[] { coBorrowerId, profileId }, Integer.class) > 0
				? true
				: false;
	}
	
	public boolean getCheckDocumentCode(String docCode) throws SearchDAOException{
		String queryStr = "select * from cms_document_globallist where DOCUMENT_CODE ='"+docCode+"' and status='ENABLE'";
		System.out.println("<<<<<<getCheckDocumentCode>>>>>>>" + queryStr);
		DBUtil dbUtil = null;
		ResultSet rs=null;
		boolean flag= false;
		DefaultLogger.debug(this, "inside getCheckDocumentCode sql:"+queryStr);

				try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr);
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
								flag = true;
							}
						}
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getCheckDocumentCode:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
					
		return flag;
	}

	public Set getCheckDocumentCodeForV2(String docCode) throws SearchDAOException{
		String[] codes = docCode.split(",");
		Set<String> codesSet = new HashSet(Arrays.asList(codes));
		Set<String> codesSetDB = new HashSet<String>();
		StringBuilder queryStr = new StringBuilder("SELECT DISTINCT chk.CATEGORY FROM CMS_CHECKLIST chk , CMS_CHECKLIST_ITEM item, SCI_LSP_LMT_PROFILE cam WHERE item.CHECKLIST_ID = chk.CHECKLIST_ID AND cam.CMS_LSP_LMT_PROFILE_ID= chk.CMS_LSP_LMT_PROFILE_ID AND item.IS_DELETED = 'N' AND item.DOCUMENT_CODE in ( ");
		StringBuilder tempStr = new  StringBuilder();
		for (String code : codes) {
		tempStr.append("'");
		tempStr.append(code); 
		tempStr.append("',");		
		}
		String str = tempStr.toString();
		str = str.replaceAll(",$", "");
		
		queryStr.append(str);
		queryStr.append(" )");
		//queryStr.append(" ) and status='ENABLE'");
		System.out.println("<<<<<<getCheckDocumentCode>>>>>>>" + queryStr);
		DBUtil dbUtil = null;
		ResultSet rs=null;
		boolean flag= false;
		DefaultLogger.debug(this, "inside getCheckDocumentCode sql:"+queryStr);

				try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr.toString());
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
								//flag = true;
								codesSetDB.add(rs.getString("CATEGORY"));
							}
						}
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getCheckDocumentCode:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
				codesSet.removeAll(codesSetDB);			
		return codesSetDB;
	}	
	
	
	public boolean getEmployeeCodeRM(String code) throws SearchDAOException{
		
		String queryStr = "select RM_MGR_CODE from CMS_RELATIONSHIP_MGR where RM_MGR_CODE= '"+code+"' and STATUS = 'ACTIVE'";
		boolean flag = false;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		DefaultLogger.debug(this, "inside getEmployeeCodeRM sql:"+queryStr);

				try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr);
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
								flag = true;
							}
						}
				}catch (Exception e) {
					System.out.println("Exception in  getEmployeeCodeRM:"+e.getMessage());
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getEmployeeCodeRM:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
							
		return flag;
	}


	public String getActualLineXrefIdNew(long sid)throws SearchDAOException{
		String queryStr = "select CMS_LSP_SYS_XREF_ID from SCI_LSP_LMTS_XREF_MAP  where CMS_SID= '"+sid+"' ";
	
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String xrefId = "";
		
		DefaultLogger.debug(this, "inside getActualLineXrefId sql:"+queryStr);
				try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr);
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
								xrefId = rs.getString("CMS_LSP_SYS_XREF_ID");
							}
						}
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getActualLineXrefId:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
							
		return xrefId;
	}
	public void insertLineCoBorrowers(String xrefId, String borroId, String borrowName) {
		String sql ="";
		try{
			
		   System.out.println("xrefId========"+xrefId);
		   /* System.out.println("borroId========"+borroId);
		   System.out.println("borrowName========"+borrowName);*/
		   String seq = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIMIT_XREF_COBORROWER, true);
		 //  System.out.println("seq No========="+seq);
		 
		   
		   String query = " SELECT COUNT(1) FROM SCI_LINE_COBORROWER WHERE SCI_LSP_SYS_XREF_ID  = ? and CO_BORROWER_ID = ? ";
			int count = getJdbcTemplate().queryForInt(query, new Object[]{xrefId,borroId});
			if(count<=0) {
			 sql = "INSERT INTO SCI_LINE_COBORROWER (ID,SCI_LSP_SYS_XREF_ID,CO_BORROWER_ID,CO_BORROWER_NAME )" 
    					+"VALUES ('"+seq+"','"+xrefId+"', '"+borroId+"','"+borrowName+"'  )";
		
			 getJdbcTemplate().update(sql);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}


	public void clearMainCovenant(String lmtID) {
		try{
			String sql = "delete from CMS_SPEC_COVENANT_DTLS s where s.CMS_LSP_APPR_LMTS_ID = ?";

			getJdbcTemplate().update(sql, new Object[] { lmtID });
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	//Covenant
public ILimitCovenant[] getCovenantData(String lmtID,String tableType) throws LimitException {
	String tableName = null;
	String columnName = null;
	
	if(tableType.equalsIgnoreCase("mainTable")) {
		tableName = "CMS_SPEC_COVENANT_DTLS";
	}else {
		tableName = "STG_SPEC_COVENANT_DETAILS";
	}
	
	if (lmtID==null) {
		lmtID = "0";
	}
		String selectSQL = "SELECT \r\n" + 
				"COVENANT_ID,\r\n" + 
				"COVENANT_REQ,"+
				"CMS_LSP_APPR_LMTS_ID,\r\n" + 
				"CMS_LSP_SYS_XREF_ID,\r\n" + 
				"COUNTRY_REST_REQ,\r\n" + 
				"DRAWER_REQ,\r\n" + 
				"DRAWEE_REQ,\r\n" + 
				"BENEFICIARY_REQ,\r\n" + 
				"COMBINED_TENOR_REQ,\r\n" + 
				"RUNNING_ACCOUNT_REQ,\r\n" + 
				"SELL_DOWN_REQ,\r\n" + 
				"LAST_AVAILABLE_DATE_REQ,\r\n" + 
				"MORATORIUM_REQ,\r\n" + 
				"GOODS_REST_REQ,\r\n" + 
				"CURRENCY_REST_REQ,\r\n" + 
				"BANK_REST_REQ,\r\n" + 
				"BUYERS_RATING_REQ,\r\n" + 
				"ECGC_COVER_REQ,\r\n" + 
				"RESTRICTEDCOUNTRYNAME,\r\n" + 
				"RESTRICTEDAMOUNT,\r\n" + 
				"DRAWERNAME,\r\n" + 
				"DRAWERAMOUNT,\r\n" + 
				"DRAWERCUSTID,\r\n" + 
				"DRAWERCUSTNAME,\r\n" + 
				"DRAWEENAME,\r\n" + 
				"DRAWEEAMOUNT,\r\n" + 
				"DRAWEECUSTID,\r\n" + 
				"DRAWEECUSTNAME,\r\n" + 
				"BENENAME,\r\n" + 
				"BENEAMOUNT,\r\n" + 
				"BENECUSTID,\r\n" + 
				"BENECUSTNAME,\r\n" + 
				"MAXCOMBINEDTENOR,\r\n" + 
				"PRESHIPMENTLINKAGE,\r\n" + 
				"POSTSHIPMENTLINKAGE,\r\n" + 
				"RUNNINGACCOUNT,\r\n" + 
				"ORDERBACKEDBYLC,\r\n" + 
				"INCOTERM,\r\n" + 
				"INCOTERMMARGINPERCENT,\r\n" + 
				"INCOTERMDESC,\r\n" + 
				"MODULECODE,\r\n" + 
				"COMMITMENTTENOR,\r\n" + 
				"SELLDOWN,\r\n" + 
				"LASTAVAILABLEDATE,\r\n" + 
				"MORATORIUMPERIOD,\r\n" + 
				"EMIFREQUENCY,\r\n" + 
				"NO_OF_INSTALLMENTS,\r\n" + 
				"GoodsRestrictionCode,\r\n" + 
				"GoodsRestrictionParentCode,\r\n" +
				"RESTRICTEDCURRENCY,\r\n" + 
				"RESTRICTEDCURRENCYAMOUNT,\r\n" + 
				"RESTRICTEDBANK,\r\n" + 
				"RESTRICTEDBANKAMOUNT,\r\n" + 
				"BUYERSRATING,\r\n" + 
				"AGENCYMASTER,\r\n" + 
				"RATINGMASTER,\r\n" +
				"SINGLECOVENANTIND,\r\n" + 
				"GOODSRESTRICTIONIND,\r\n" +
				"GOODSRESTRICTIONPARENTCODE,\r\n" +
				"GOODSRESTRICTIONCHILDCODE,\r\n" +
				"GOODSRESTRICTIONSUBCHILDCODE,\r\n" +
				"GOODSRESTRICTIONCOMBOCODE,\r\n" +
				"RESTRICTEDCOUNTRYIND,\r\n" +
				"RESTRICTEDBANKIND,\r\n" +
				"RESTRICTEDCURRENCYIND,\r\n" +
				"DRAWERIND,\r\n" +
				"DRAWEEIND,\r\n" +
				"BENEIND,\r\n" +
				"ECGCCOVER FROM "+tableName+" WHERE CMS_LSP_APPR_LMTS_ID = '"+lmtID+"'";

		return (ILimitCovenant[]) getJdbcTemplate().query(selectSQL,
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processLimitCovenant(rs);
			}
		});
	}

private ILimitCovenant[] processLimitCovenant(ResultSet rs) throws SQLException {
	
	ArrayList list = new ArrayList();
	while (rs.next()) {
		OBLimitCovenant covenantData = new OBLimitCovenant();
		
		covenantData.setCovenantId(rs.getLong("COVENANT_ID"));//>0?rs.getLong("COVENANT_ID"):""
		covenantData.setCovenantReqd(rs.getString("COVENANT_REQ")!=null?rs.getString("COVENANT_REQ"):"");
		//covenantData.setLineId(rs.getString("CMS_LSP_SYS_XREF_ID")!=null?rs.getString("CMS_LSP_SYS_XREF_ID"):"");
		covenantData.setCountryRestrictionReqd(rs.getString("COUNTRY_REST_REQ")!=null?rs.getString("COUNTRY_REST_REQ"):"");
		covenantData.setDraweeReqd(rs.getString("DRAWEE_REQ")!=null?rs.getString("DRAWEE_REQ"):"");
		covenantData.setDrawerReqd(rs.getString("DRAWER_REQ")!=null?rs.getString("DRAWER_REQ"):"");
		covenantData.setBeneficiaryReqd(rs.getString("BENEFICIARY_REQ")!=null?rs.getString("BENEFICIARY_REQ"):"");
		covenantData.setCombinedTenorReqd(rs.getString("COMBINED_TENOR_REQ")!=null?rs.getString("COMBINED_TENOR_REQ"):"");
		covenantData.setRunningAccountReqd(rs.getString("RUNNING_ACCOUNT_REQ")!=null?rs.getString("RUNNING_ACCOUNT_REQ"):"");
		covenantData.setSellDownReqd(rs.getString("SELL_DOWN_REQ")!=null?rs.getString("SELL_DOWN_REQ"):"");
		covenantData.setLastAvailableDateReqd(rs.getString("LAST_AVAILABLE_DATE_REQ")!=null?rs.getString("LAST_AVAILABLE_DATE_REQ"):"");
		covenantData.setMoratoriumReqd(rs.getString("MORATORIUM_REQ")!=null?rs.getString("MORATORIUM_REQ"):"");
		covenantData.setGoodsRestrictionReqd(rs.getString("GOODS_REST_REQ")!=null?rs.getString("GOODS_REST_REQ"):"");
		covenantData.setCurrencyRestrictionReqd(rs.getString("CURRENCY_REST_REQ")!=null?rs.getString("CURRENCY_REST_REQ"):"");
		covenantData.setBankRestrictionReqd(rs.getString("BANK_REST_REQ")!=null?rs.getString("BANK_REST_REQ"):"");
		covenantData.setBuyersRatingReqd(rs.getString("BUYERS_RATING_REQ")!=null?rs.getString("BUYERS_RATING_REQ"):"");
		covenantData.setEcgcCoverReqd(rs.getString("ECGC_COVER_REQ")!=null?rs.getString("ECGC_COVER_REQ"):"");
		covenantData.setRestrictedCountryname(rs.getString("RESTRICTEDCOUNTRYNAME")!=null?rs.getString("RESTRICTEDCOUNTRYNAME"):"");
		covenantData.setRestrictedAmount(rs.getString("RESTRICTEDAMOUNT")!=null?rs.getString("RESTRICTEDAMOUNT"):"");
		covenantData.setDrawerName(rs.getString("DRAWERNAME")!=null?rs.getString("DRAWERNAME"):"");
		covenantData.setDrawerAmount(rs.getString("DRAWERAMOUNT")!=null?rs.getString("DRAWERAMOUNT"):"");
		covenantData.setDrawerCustId(rs.getString("DRAWERCUSTID")!=null?rs.getString("DRAWERCUSTID"):"");
		covenantData.setDrawerCustName(rs.getString("DRAWERCUSTNAME")!=null?rs.getString("DRAWERCUSTNAME"):"");
		covenantData.setDraweeName(rs.getString("DRAWEENAME")!=null?rs.getString("DRAWEENAME"):"");
		covenantData.setDraweeAmount(rs.getString("DRAWEEAMOUNT")!=null?rs.getString("DRAWEEAMOUNT"):"");
		covenantData.setDraweeCustId(rs.getString("DRAWEECUSTID")!=null?rs.getString("DRAWEECUSTID"):"");
		covenantData.setDraweeCustName(rs.getString("DRAWEECUSTNAME")!=null?rs.getString("DRAWEECUSTNAME"):"");
		covenantData.setBeneName(rs.getString("BENENAME")!=null?rs.getString("BENENAME"):"");
		covenantData.setBeneAmount(rs.getString("BENEAMOUNT")!=null?rs.getString("BENEAMOUNT"):"");
		covenantData.setBeneCustId(rs.getString("BENECUSTID")!=null?rs.getString("BENECUSTID"):"");
		covenantData.setBeneCustName(rs.getString("BENECUSTNAME")!=null?rs.getString("BENECUSTNAME"):"");
		covenantData.setMaxCombinedTenor(rs.getString("MAXCOMBINEDTENOR")!=null?rs.getString("MAXCOMBINEDTENOR"):"");
		covenantData.setPreshipmentLinkage(rs.getString("PRESHIPMENTLINKAGE")!=null?rs.getString("PRESHIPMENTLINKAGE"):"");
		covenantData.setPostShipmentLinkage(rs.getString("POSTSHIPMENTLINKAGE")!=null?rs.getString("POSTSHIPMENTLINKAGE"):"");
		covenantData.setRunningAccount(rs.getString("RUNNINGACCOUNT")!=null?rs.getString("RUNNINGACCOUNT"):"");
		covenantData.setOrderBackedbylc(rs.getString("ORDERBACKEDBYLC")!=null?rs.getString("ORDERBACKEDBYLC"):"");
		covenantData.setIncoTerm(rs.getString("INCOTERM")!=null?rs.getString("INCOTERM"):"");
		covenantData.setIncoTermMarginPercent(rs.getString("INCOTERMMARGINPERCENT")!=null?rs.getString("INCOTERMMARGINPERCENT"):"");
		covenantData.setIncoTermDesc(rs.getString("INCOTERMDESC")!=null?rs.getString("INCOTERMDESC"):"");
		covenantData.setModuleCode(rs.getString("MODULECODE")!=null?rs.getString("MODULECODE"):"");
		covenantData.setCommitmentTenor(rs.getString("COMMITMENTTENOR")!=null?rs.getString("COMMITMENTTENOR"):"");
		covenantData.setSellDown(rs.getString("SELLDOWN")!=null?rs.getString("SELLDOWN"):"");
		covenantData.setLastAvailableDate(rs.getDate("LASTAVAILABLEDATE"));
		covenantData.setMoratoriumPeriod(rs.getString("MORATORIUMPERIOD")!=null?rs.getString("MORATORIUMPERIOD"):"");
		covenantData.setEmiFrequency(rs.getString("EMIFREQUENCY")!=null?rs.getString("EMIFREQUENCY"):"");
		covenantData.setNoOfInstallments(rs.getString("NO_OF_INSTALLMENTS")!=null?rs.getString("NO_OF_INSTALLMENTS"):"");
		covenantData.setRestrictedCurrency(rs.getString("RESTRICTEDCURRENCY")!=null?rs.getString("RESTRICTEDCURRENCY"):"");
		covenantData.setRestrictedCurrencyAmount(rs.getString("RESTRICTEDCURRENCYAMOUNT")!=null?rs.getString("RESTRICTEDCURRENCYAMOUNT"):"");
		covenantData.setRestrictedBank(rs.getString("RESTRICTEDBANK")!=null?rs.getString("RESTRICTEDBANK"):"");
		covenantData.setRestrictedBankAmount(rs.getString("RESTRICTEDBANKAMOUNT")!=null?rs.getString("RESTRICTEDBANKAMOUNT"):"");
		covenantData.setBuyersRating(rs.getString("BUYERSRATING")!=null?rs.getString("BUYERSRATING"):"");
		covenantData.setAgencyMaster(rs.getString("AGENCYMASTER")!=null?rs.getString("AGENCYMASTER"):"");
		covenantData.setRatingMaster(rs.getString("RATINGMASTER")!=null?rs.getString("RATINGMASTER"):"");
		covenantData.setEcgcCover(rs.getString("ECGCCOVER")!=null?rs.getString("ECGCCOVER"):"");
		covenantData.setGoodsRestrictionCode(rs.getString("goodsRestrictionCode")!=null?rs.getString("goodsRestrictionCode"):"");
		covenantData.setGoodsRestrictionParentCode(rs.getString("GOODSRESTRICTIONPARENTCODE")!=null?rs.getString("GOODSRESTRICTIONPARENTCODE"):"");
		covenantData.setSingleCovenantInd(rs.getString("SINGLECOVENANTIND")!=null?rs.getString("SINGLECOVENANTIND"):"");
		covenantData.setBeneInd(rs.getString("BENEIND")!=null?rs.getString("BENEIND"):"");
		covenantData.setDraweeInd(rs.getString("DRAWEEIND")!=null?rs.getString("DRAWEEIND"):"");
		covenantData.setDrawerInd(rs.getString("DRAWERIND")!=null?rs.getString("DRAWERIND"):"");
		covenantData.setGoodsRestrictionInd(rs.getString("GOODSRESTRICTIONIND")!=null?rs.getString("GOODSRESTRICTIONIND"):"");
		covenantData.setRestrictedBankInd(rs.getString("RESTRICTEDBANKIND")!=null?rs.getString("RESTRICTEDBANKIND"):"");
		covenantData.setRestrictedCountryInd(rs.getString("RESTRICTEDCOUNTRYIND")!=null?rs.getString("RESTRICTEDCOUNTRYIND"):"");
		covenantData.setRestrictedCurrencyInd(rs.getString("RESTRICTEDCURRENCYIND")!=null?rs.getString("RESTRICTEDCURRENCYIND"):"");
		covenantData.setGoodsRestrictionChildCode(rs.getString("GOODSRESTRICTIONCHILDCODE")!=null?rs.getString("GOODSRESTRICTIONCHILDCODE"):"");
		covenantData.setGoodsRestrictionSubChildCode(rs.getString("GOODSRESTRICTIONSUBCHILDCODE")!=null?rs.getString("GOODSRESTRICTIONSUBCHILDCODE"):"");
		covenantData.setGoodsRestrictionComboCode(rs.getString("GOODSRESTRICTIONCOMBOCODE")!=null?rs.getString("GOODSRESTRICTIONCOMBOCODE"):"");
		list.add(covenantData);
		}
	rs.close();
	return (OBLimitCovenant[]) list.toArray(new OBLimitCovenant[0]);
}

	public String getSeqNoForDocBarCode() throws Exception {

		String fileSeqNo = "";
		DefaultLogger.debug(this,"getSeqNoForDocBarCode in seqno");
		int queryForInt = 0;
		String queryStr = "select DOC_BAR_CODE_SEQ.NEXTVAL from dual";
		
		
		ResultSet rs = null;
		try {

			queryForInt = getJdbcTemplate().queryForInt(queryStr);
			DefaultLogger.debug(this,"getSeqNoForDocBarCode  seqno"+queryForInt);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		
		
		fileSeqNo = String.format("%04d", queryForInt);
		DefaultLogger.debug(this,"getSeqNoForDocBarCode get seqno" +fileSeqNo);
		return fileSeqNo;
	}	

	@Override
	public List getImageIdListForV2(String code, DigitalLibraryRequestDTO digitalLibraryRequestDTO,String imgid)
			throws SearchDAOException {
		
		String DocCategoryVal;
		ArrayList<String> DocCategoryList = new ArrayList<String>();
		if(digitalLibraryRequestDTO.getDocType() == null)
		{
			DocCategoryList.add("CAM");
			DocCategoryList.add("REC");	
			DocCategoryList.add("S");
			DocCategoryList.add("F");
			DocCategoryList.add("O");
			DocCategoryList.add("LAD");
		}
		else
		{
			DocCategoryList.add(digitalLibraryRequestDTO.getDocType());
		}
		final List<String> imageList=new ArrayList<String>();
		for (int i = 0; i < DocCategoryList.size(); i++) {
		DocCategoryVal = (String) DocCategoryList.get(i);
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("SELECT DISTINCT chklstitm.DOCUMENT_CODE,		"+
				"	  CASE		"+
				"	    WHEN imgmap.UNTAGGED_STATUS = 'N'		"+
				"	    THEN UPIMG.IMG_ID		"+
				"	    ELSE 0		"+
				"	  END AS img_id,		"+
				"	  CASE		"+
				"	    WHEN imgmap.UNTAGGED_STATUS = 'N'		"+
				"	    THEN UPIMG.IMG_FILENAME		"+
				"	    ELSE 'NA'		"+
				"	  END AS IMG_FILENAME,		"+
				" 	  REGEXP_REPLACE( UPIMG.HCP_FILENAME, '[[:space:]]', '' ) as HCP_FILENAME,"+
				"	  chklstitm.DOC_DESCRIPTION ,");
		if("CAM".equalsIgnoreCase(DocCategoryVal) || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_CAM"))
		{
			queryStr.append(
					"lmtPr.CAM_TYPE,"+
					"lmtPr.LLP_BCA_REF_NUM,"+
					"lmtPr.LLP_BCA_REF_APPR_DATE,"+
					"chklst.CATEGORY");
		}
		if(DocCategoryVal.equalsIgnoreCase("S")  || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
		{
			queryStr.append(
					"chklst.CMS_COLLATERAL_ID,"+
					"colmas.NEW_COLLATERAL_DESCRIPTION,"
					);
		}
		if(DocCategoryVal.equalsIgnoreCase("F") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
		{
			queryStr.append(
					"SLAL.LMT_ID, "+
					"SLAL.FACILITY_NAME, "
					);
		}
		
		if("LAD".equalsIgnoreCase(DocCategoryVal)){
			queryStr.append(
					"chklstitm.COMPLETED_DATE,"
					
					);
		}
		
		 if(DocCategoryVal.equalsIgnoreCase("LAD") || DocCategoryVal.equalsIgnoreCase("S") || DocCategoryVal.equalsIgnoreCase("F") || DocCategoryVal.equalsIgnoreCase("O") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_FACILITY") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_SECURITY") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_OTHERS"))
		{
			queryStr.append(
					"chklstitm.DOC_DATE,"+
					"chklstitm.DOC_AMT,"+
					"chklstitm.HDFC_AMT,"+
					"chklstitm.CURRENCY"
					);
		}
		 if(DocCategoryVal.equalsIgnoreCase("REC"))
		{
			queryStr.append(
					"chklstitm.DOC_DATE,"+
					"common.ENTRY_NAME AS Statement_type"
					);
		}
		
		queryStr.append(	
		" FROM CMS_UPLOADED_IMAGES UPIMG, "+
		" SCI_LE_SUB_PROFILE SUBPR, "+
		" sci_le_other_system OTHSYS  "); 
		//if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocFromDate() || null !=digitalLibraryRequestDTO.getDocToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocFromAmt() || null !=digitalLibraryRequestDTO.getDocToAmt() || null !=digitalLibraryRequestDTO.getDocCode()) {
		if(DocCategoryVal.equalsIgnoreCase("F") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
		{
			queryStr.append(",SCI_LSP_APPR_LMTS SLAL ");
		}
		if(DocCategoryVal.equalsIgnoreCase("S")   || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
		{
			queryStr.append(",CMS_SECURITY sec ");
			queryStr.append(" ,CMS_COLLATERAL_NEW_MASTER colmas  ");
		}
		if(DocCategoryVal.equalsIgnoreCase("REC"))
		{
			queryStr.append(",COMMON_CODE_CATEGORY_ENTRY common");
		}
		queryStr.append(",sci_lsp_lmt_profile lmtPr ");
		queryStr.append(",CMS_CHECKLIST chklst ");
		//}
		//if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocFromDate() || null !=digitalLibraryRequestDTO.getDocToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocFromAmt() || null !=digitalLibraryRequestDTO.getDocToAmt()
		//		|| null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
			queryStr.append(",CMS_CHECKLIST_ITEM chklstitm ");
		//}
		
		//if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate() 
		//		|| null != digitalLibraryRequestDTO.getDocFromAmt() || null != digitalLibraryRequestDTO.getDocToAmt()
		//		|| null != digitalLibraryRequestDTO.getDocFromDate() || null != digitalLibraryRequestDTO.getDocToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()){
			queryStr.append(",cms_image_tag_map imgmap");
			queryStr.append(",cms_image_tag_details imgdtl ");
			
		//}
		
		
		
		//if(null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {		
		queryStr.append(",transaction trans ");
	//	}
		
	//	if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
			queryStr.append(" ,SCI_LE_MAIN_PROFILE MAINPR ");
			
	//	}
		
		queryStr.append("where UPIMG.CUST_ID = SUBPR.LSP_LE_ID ");
		queryStr.append("and OTHSYS.CMS_LE_MAIN_PROFILE_ID = SUBPR.CMS_LE_MAIN_PROFILE_ID ");
		
		queryStr.append(" and UPIMG.IMG_DEPRICATED != 'Y' ");
		
		
		
		//if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocFromDate() || null !=digitalLibraryRequestDTO.getDocToDate()
		//		|| null !=digitalLibraryRequestDTO.getDocFromAmt() || null !=digitalLibraryRequestDTO.getDocToAmt()|| null !=digitalLibraryRequestDTO.getDocCode()) {
		queryStr.append("AND lmtPr.LLP_LE_ID = SUBPR.LSP_LE_ID ");
		queryStr.append("AND lmtPr.CMS_LSP_LMT_PROFILE_ID = chklst.CMS_LSP_LMT_PROFILE_ID ");
		queryStr.append("AND chklstitm.CHECKLIST_ID = chklst.CHECKLIST_ID ");
		
		//}
		
		//if(null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
		queryStr.append("and trans.LEGAL_NAME = SUBPR.LSP_SHORT_NAME ");
		queryStr.append("and imgmap.TAG_ID = trans.REFERENCE_ID ");
		queryStr.append("and UPIMG.IMG_ID = imgmap.IMAGE_ID ");
		queryStr.append("and imgmap.TAG_ID = imgdtl.ID ");
		if(!(DocCategoryVal.equalsIgnoreCase("CAM") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_CAM")))
		{
		queryStr.append("and imgdtl.DOC_DESC = to_char(chklstitm.DOC_ITEM_ID) ");
		}
		//}
		queryStr.append(" AND chklstitm.STATUS = 'RECEIVED' ");
		//if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
			queryStr.append(" AND MAINPR.LMP_LE_ID = SUBPR.LSP_LE_ID ");
		
			
		//}
		
		//if(null !=digitalLibraryRequestDTO.getDocTagFromDate() || null !=digitalLibraryRequestDTO.getDocTagToDate() || null !=digitalLibraryRequestDTO.getDocCode()) {
		queryStr.append("and trans.TRANSACTION_TYPE='IMAGE_TAG' ");
		queryStr.append("and trans.status='ACTIVE' ");
		//queryStr.append("and imgmap.UNTAGGED_STATUS = 'N' ");
		//}
		
		
		if(null !=digitalLibraryRequestDTO.getDocType()) {
			queryStr.append(" and UPIMG.CATEGORY ='"+digitalLibraryRequestDTO.getDocType()+"'");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() && null == digitalLibraryRequestDTO.getDocRecvToDate()) {
			queryStr.append(" and chklstitm.RECEIVED_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocRecvFromDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null ==digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
			queryStr.append(" and chklstitm.RECEIVED_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocRecvToDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
			queryStr.append(" and chklstitm.RECEIVED_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocRecvFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocRecvToDate()+"', 'DD/MM/YY')");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocFromAmt() && null == digitalLibraryRequestDTO.getDocToAmt()) {
			queryStr.append(" and chklstitm.doc_amt >= to_number('"+digitalLibraryRequestDTO.getDocFromAmt()+"')");
		}
		
		if(null ==digitalLibraryRequestDTO.getDocFromAmt() && null != digitalLibraryRequestDTO.getDocToAmt()) {
			queryStr.append(" and chklstitm.doc_amt <= to_number('"+digitalLibraryRequestDTO.getDocToAmt()+"')");
		}

		if(null !=digitalLibraryRequestDTO.getDocFromAmt() && null != digitalLibraryRequestDTO.getDocToAmt()) {
			queryStr.append(" and chklstitm.doc_amt >= to_number('"+digitalLibraryRequestDTO.getDocFromAmt()+"')" + " and chklstitm.doc_amt <= to_number('"+digitalLibraryRequestDTO.getDocToAmt()+"')");
		}
		
		if(null !=digitalLibraryRequestDTO.getSystemName()) {
			queryStr.append(" and OTHSYS.CMS_LE_SYSTEM_NAME ='"+digitalLibraryRequestDTO.getSystemName()+"'");
		}
		
		
		if(null !=digitalLibraryRequestDTO.getDocTagFromDate() && null == digitalLibraryRequestDTO.getDocTagToDate()) {
			queryStr.append(" and trans.TRANSACTION_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocTagFromDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null ==digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
			queryStr.append(" and trans.TRANSACTION_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocTagToDate()+"'"+",'DD/MM/YY')");
		}
		
		
		if(null !=digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
			if(!digitalLibraryRequestDTO.getDocTagFromDate().equals(digitalLibraryRequestDTO.getDocTagToDate())) {
			queryStr.append(" and trans.TRANSACTION_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocTagFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocTagToDate()+"', 'DD/MM/YY')");
			}
			
			if(digitalLibraryRequestDTO.getDocTagFromDate().equals(digitalLibraryRequestDTO.getDocTagToDate())) {
				queryStr.append(" AND trans.TRANSACTION_DATE like to_date ('"+digitalLibraryRequestDTO.getDocTagFromDate()+"', 'DD-MON-YY')");	
			}
			}
		
		
		if(DocCategoryVal.equalsIgnoreCase("CAM") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_CAM"))
		{
			queryStr.append(" AND UPIMG.IMG_ID in ("+imgid+") ");
			queryStr.append(" and chklst.CATEGORY = 'CAM' ");
			queryStr.append(" and UPIMG.CATEGORY ='IMG_CATEGORY_CAM' ");
		}
		if(DocCategoryVal.equalsIgnoreCase("O") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_OTHERS"))
		{
			queryStr.append(" AND chklstitm.DOC_DESCRIPTION like 'INSURANCE_POLICY%' ");
			queryStr.append("and UPIMG.CATEGORY ='IMG_CATEGORY_OTHERS'");
		}
		if(DocCategoryVal.equalsIgnoreCase("REC"))
		{
			queryStr.append("AND chklst.CATEGORY = 'REC' ");
			queryStr.append(" AND chklstitm.STATEMENT_TYPE = common.ENTRY_CODE ")
			;
		}
		if(DocCategoryVal.equalsIgnoreCase("LAD"))
		{
			queryStr.append(" AND chklst.CATEGORY = 'LAD'");
		}
		if(DocCategoryVal.equalsIgnoreCase("S") ||  DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
		{
			queryStr.append(" AND chklst.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID");
			queryStr.append(" AND sec.collateral_code = colmas.NEW_COLLATERAL_CODE");
			queryStr.append(" and UPIMG.CATEGORY ='IMG_CATEGORY_SECURITY' ");
		}
		if(DocCategoryVal.equalsIgnoreCase("F") ||  DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
		{
			queryStr.append(" AND chklst.CMS_COLLATERAL_ID = SLAL.CMS_LSP_APPR_LMTS_ID(+) "+" and UPIMG.CATEGORY ='IMG_CATEGORY_FACILITY' ");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocFromDate() && null == digitalLibraryRequestDTO.getDocToDate()) {
			queryStr.append(" and chklstitm.DOC_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocFromDate()+"'"+",'DD/MM/YY')");
		}
		if(null ==digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
			queryStr.append(" and chklstitm.DOC_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocToDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
			queryStr.append(" and chklstitm.DOC_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocToDate()+"', 'DD/MM/YY')");
		}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append(" and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' ");
		}
		
		
		if(null == digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" and UPIMG.CUST_ID in (select lsp_le_id from sci_le_sub_profile where pan= '"+digitalLibraryRequestDTO.getPanNo()+"') ");
			}

		if(null == digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append("and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' and pan ='"+digitalLibraryRequestDTO.getPanNo()+"'");
			}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			
			queryStr.append(" and SUBPR.LSP_LE_ID='"+digitalLibraryRequestDTO.getPartyId()+"'");
			}
		
		if(null == digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			
			queryStr.append(" and SUBPR.PAN='"+digitalLibraryRequestDTO.getPanNo()+"'");
			}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' and SUBPR.pan ='"+digitalLibraryRequestDTO.getPanNo()+"'");
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			}
		
		if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate() 
				|| null != digitalLibraryRequestDTO.getDocFromAmt() || null != digitalLibraryRequestDTO.getDocToAmt()
				|| null != digitalLibraryRequestDTO.getDocFromDate() || null != digitalLibraryRequestDTO.getDocToDate() || null !=digitalLibraryRequestDTO.getDocCode()){
			queryStr.append(" and to_char(chklstitm.doc_item_id) = imgdtl.DOC_DESC "+
				"and imgmap.tag_id = imgdtl.id "+
				"and imgmap.IMAGE_ID = UPIMG.IMG_ID  ");
		}
		
		if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
			queryStr.append(" AND MAINPR.RELATION_MGR_EMP_CODE ='"+digitalLibraryRequestDTO.getEmployeeCodeRM()+"'");
			
		}
		
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		//DefaultLogger.debug(this, "inside getImageIdList sql:"+queryStr);

	//System.out.println("inside getImageIdList sql:"+queryStr);			
	try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr.toString());
					System.out.println("Type of DOC-------------------->"+DocCategoryVal);
					System.out.println("DIGITAL LIB IMAGE FETCH QUERY ----------------------->"+queryStr.toString());
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{	
							if(DocCategoryVal.equalsIgnoreCase("CAM") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_CAM"))
							{	
								if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CAM_TYPE") + "," + rs.getString("LLP_BCA_REF_NUM") + "," + rs.getString("LLP_BCA_REF_APPR_DATE") + "," + rs.getString("CATEGORY") );
									}
									else if(rs.getString("IMG_ID").equalsIgnoreCase("0"))
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CAM_TYPE") + "," + rs.getString("LLP_BCA_REF_NUM") + "," + rs.getString("LLP_BCA_REF_APPR_DATE") + "," + rs.getString("CATEGORY") );
									}
									else
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
									}
							}
							
							if(DocCategoryVal.equalsIgnoreCase("LAD"))
							{	
								if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("COMPLETED_DATE") + ","+ rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else if(rs.getString("IMG_ID").equalsIgnoreCase("0"))
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("COMPLETED_DATE") + ","+ rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
								}
							}
							if(DocCategoryVal.equalsIgnoreCase("O") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_OTHERS"))
							{	
								if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION")  + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
								}
							}
							if(DocCategoryVal.equalsIgnoreCase("REC"))
							{	
								if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("STATEMENT_TYPE"));
								}
								else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("STATEMENT_TYPE"));
								}
								else
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
								}
							}
							if(DocCategoryVal.equalsIgnoreCase("F") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
							{	
								if (rs.getString("IMG_ID") != null) {							 
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("LMT_ID") + "," + rs.getString("FACILITY_NAME") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
								{
								imageList.add(rs.getString("DOCUMENT_CODE")  + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION")+ "," + rs.getString("LMT_ID") + "," + rs.getString("FACILITY_NAME") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
								}
								
							}
							if(DocCategoryVal.equalsIgnoreCase("S") || DocCategoryVal.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
							{	
								if (rs.getString("IMG_ID") != null) {							 
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CMS_COLLATERAL_ID") + "," + rs.getString("NEW_COLLATERAL_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
								{
								imageList.add(rs.getString("DOCUMENT_CODE")  + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CMS_COLLATERAL_ID") + "," + rs.getString("NEW_COLLATERAL_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
								}
								else
								{
								imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
								}
							}
							}
						}
					 else{
						 imageList.add("No Records Found");
					 }
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getImageIdList:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
		}
		System.out.println("Total no of fetched records"+imageList.size());	
		return imageList;
	}

	public String getActionFromFcubsDataLog(String sourceRefId) throws LimitException {
		String theSQL = "SELECT ID,ACTION FROM(SELECT ID,ACTION FROM CMS_FCUBSDATA_LOG WHERE SOURCE_REF_NO = ? ORDER BY ID DESC)WHERE ROWNUM=1 ";
		
		List<String> queryForList=new ArrayList<String>();
		
		List<Object> param = new ArrayList<Object>();
		param.add(sourceRefId);
		System.out.println("<<<<<<getActionFromFcubsDataLog>>>>>>>sourceRefId=>"+sourceRefId + " ... theSQL=>"+ theSQL);
		
		try{
		 queryForList = getJdbcTemplate().query(theSQL,param.toArray(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rownum) throws SQLException {
				String str =rs.getString("ACTION");
	            return str;
			}
		});


		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception caught in getActionFromFcubsDataLog => e =>"+e);
			DefaultLogger.debug(this, e.getMessage());
		}
		if(queryForList.size()==0){
			queryForList.add("");
		}
		
		return queryForList.get(0).toString();
	}
	public String getSecurityPriorityValue(String colId) throws Exception {
		System.out.println("Inside getSecurityPriorityValue.");
	
		String securityPriorityValue = "";
		DBUtil dbUtil = null;
			
			String sql = " SELECT SEC_PRIORITY FROM CMS_SECURITY WHERE CMS_COLLATERAL_ID =?  ";
			
			System.out.println("getSecurityPriorityValue(String colId) => sql query =>"+sql+"...colId>>"+colId);
			try {
				List queryForList = getJdbcTemplate().queryForList(sql, new Object[] {colId});
				for (int i = 0; i < queryForList.size(); i++) {
					Map  map = (Map) queryForList.get(i);
					if(null!=map.get("SEC_PRIORITY") && !"".equals(map.get("SEC_PRIORITY"))) {
						securityPriorityValue= map.get("SEC_PRIORITY").toString();
					}
				}
				
			}
			catch (Exception ex) {
				System.out.println("exception in getSecurityPriorityValue(String colId)");
			
				ex.printStackTrace();
				throw ex;
			}
			return securityPriorityValue;
	}
	
	
	public String getSecurityDrawingPower(String colId,String customerId) throws Exception {
		System.out.println("Inside getSecurityDrawingPower.");
	
		String securityDrawingPower = "";
		DBUtil dbUtil = null;
			
			String sql = "SELECT DISTINCT " + 
					"					APPR.lmt_id, " + 
					"          APPR.CMS_LSP_APPR_LMTS_ID, " + 
					"          CAGC.CALCULATEDDP AS CALCULATEDDP " + 
					"					FROM " + 
					"					CMS_SECURITY CSEC, " + 
					"					 SCI_LSP_APPR_LMTS APPR,  " + 
					"					  CMS_LIMIT_SECURITY_MAP MAP, " + 
					"					 SCI_LSP_LMT_PROFILE LMT,  " + 
					"						SCI_LE_SUB_PROFILE SLSP, " + 
					"            CMS_ASSET_GC_DET CAGC " + 
					"					WHERE   " + 
					"           CSEC.CMS_COLLATERAL_ID = CAGC.CMS_COLLATERAL_ID " + 
					"          AND CSEC.CMS_COLLATERAL_ID            = MAP.CMS_COLLATERAL_ID  " + 
					"					AND APPR.CMS_LSP_APPR_LMTS_ID        = MAP.CMS_LSP_APPR_LMTS_ID  " + 
					"					 AND LMT.CMS_LSP_LMT_PROFILE_ID       = APPR.CMS_LIMIT_PROFILE_ID  " + 
					"					AND LMT.LLP_LE_ID                    =SLSP.LSP_LE_ID   " + 
					"					AND SLSP.STATUS = 'ACTIVE'  " + 
					"        AND SLSP.CMS_LE_SUB_PROFILE_ID = ? " + 
					"		AND CSEC.CMS_COLLATERAL_ID = ? "	+	
					"        AND CAGC.GC_DET_ID = (SELECT MAX(GC_DET_ID) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID = ? AND DUE_DATE = (SELECT MAX(DUE_DATE) FROM CMS_ASSET_GC_DET WHERE CMS_COLLATERAL_ID = ?)) " +		
					"          AND APPR.LMT_TYPE_VALUE = 'No' " + 
					"      AND APPR.CMS_LIMIT_STATUS = 'ACTIVE'  " + 
					"			AND CSEC.STATUS = 'ACTIVE'   " + 
					"          AND CSEC.SECURITY_SUB_TYPE_ID = 'AB100' ";
			
			System.out.println("getSecurityDrawingPower(String colId,String customerId) => sql query =>"+sql);
			try {
				List queryForList = getJdbcTemplate().queryForList(sql, new Object[] {customerId, colId, colId, colId});
				for (int i = 0; i < queryForList.size(); i++) {
					Map  map = (Map) queryForList.get(i);
					if(null!=map.get("CALCULATEDDP") && !"".equals(map.get("CALCULATEDDP"))) {
						securityDrawingPower= map.get("CALCULATEDDP").toString();
					}
				}
				
			}
			catch (Exception ex) {
				System.out.println("exception in getSecurityDrawingPower(String colId,String customerId)");
			
				ex.printStackTrace();
				throw ex;
			}
			return securityDrawingPower;
	}

	
	public List getStockStatementDPYes() {
		String selectSQL ="SELECT DISTINCT sp.lsp_le_id AS partyId,\r\n" + 
				"  sp.lsp_short_name          AS partyname,\r\n" + 
				"  ci.entry_name              AS segmentname,\r\n" + 
				"  (SELECT region_name FROM cms_region WHERE id =sp.rm_region\r\n" + 
				"  )                                     AS rmRegion,\r\n" + 
				"  rm.rm_mgr_name                        AS rmname,\r\n" + 
				"  TO_CHAR(date1.due_date,'DD/MON/YYYY') AS duedate,\r\n" + 
				"  NULL					 AS receiveddate,\r\n" + 
				"  ( SELECT DISTINCT document_description\r\n" + 
				"  FROM cms_document_globallist\r\n" + 
				"  WHERE document_code = date1.doc_code\r\n" + 
				"  )                           AS periodDesc,\r\n" + 
				"  NULL                        AS component,\r\n" + 
				"  NULL			      AS \"header\",\r\n" + 
				"  NULL                        AS flag,\r\n" + 
				"  NULL                        AS collateralShare,\r\n" + 
				"  NULL                        AS grossValue,\r\n" + 
				"  NULL                        AS margin,\r\n" + 
				"  NULL                        AS netAmount,\r\n" + 
				"  date1.calculateddp          AS DP,\r\n" + 
				"  date1.DP_CALCULATE_MANUALLY AS DP_CALCULATE_MANUALLY,\r\n" + 
				"  NULL                        AS COMPONENT_CATEGORY,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.from_state = 'PENDING_PERFECTION'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state       = 'PENDING_PERFECTION'\r\n" + 
				"      AND hist.to_state         ='DRAFT'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_CREATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state      IN ('ND','DRAFT')\r\n" + 
				"      AND hist.to_state         ='PENDING_CREATE'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('ACTIVE','DRAFT')\r\n" + 
				"        AND to_state         = 'PENDING_UPDATE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_DELETE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state         ='PENDING_DELETE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'ACTIVE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','PENDING_DELETE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'REJECTED'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       = 'REJECTED'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'DRAFT'\r\n" + 
				"    THEN\r\n" + 
				"      CASE\r\n" + 
				"        WHEN trx.status = 'PENDING_UPDATE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT hist.login_id\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='PENDING_UPDATE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"        WHEN trx.status = 'ACTIVE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT hist.login_id\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='ACTIVE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"      END\r\n" + 
				"  END AS Maker,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.from_state = 'PENDING_PERFECTION'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state       = 'PENDING_PERFECTION'\r\n" + 
				"      AND hist.to_state         ='DRAFT'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_CREATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state      IN ('ND','DRAFT')\r\n" + 
				"      AND hist.to_state         ='PENDING_CREATE'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('ACTIVE','DRAFT')\r\n" + 
				"        AND to_state         = 'PENDING_UPDATE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_DELETE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state         ='PENDING_DELETE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'ACTIVE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','PENDING_DELETE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'REJECTED'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       = 'REJECTED'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'DRAFT'\r\n" + 
				"    THEN\r\n" + 
				"      CASE\r\n" + 
				"        WHEN trx.status = 'PENDING_UPDATE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='PENDING_UPDATE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"        WHEN trx.status = 'ACTIVE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='ACTIVE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"      END\r\n" + 
				"  END AS MakerDateTime,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.status   = 'PENDING_CREATE'\r\n" + 
				"    OR trx.from_state = 'PENDING_PERFECTION'\r\n" + 
				"    THEN ''\r\n" + 
				"    WHEN trx.status = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN ((trx.status  != 'PENDING_CREATE'\r\n" + 
				"    AND trx.from_state != 'PENDING_PERFECTION')\r\n" + 
				"    AND trx.status     != 'PENDING_UPDATE')\r\n" + 
				"    THEN TO_CHAR(trx.login_id)\r\n" + 
				"  END AS Approved_By,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.from_state = 'PENDING_CREATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state       ='PENDING_CREATE'\r\n" + 
				"      AND hist.to_state         ='ACTIVE'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='PENDING_UPDATE'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_DELETE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='PENDING_DELETE'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'ACTIVE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'REJECTED'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='REJECTED'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'DRAFT'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"  END AS CheckerDateTime\r\n" + 
				"FROM CMS_ASSET_GC_DET date1,\r\n" + 
				"  CMS_RELATIONSHIP_MGR rm,\r\n" + 
				"  CMS_LIMIT_SECURITY_MAP lsm,\r\n" + 
				"  SCI_LSP_APPR_LMTS lmts ,\r\n" + 
				"  SCI_LSP_LMT_PROFILE pf,\r\n" + 
				"  SCI_LE_SUB_PROFILE sp,\r\n" + 
				"  common_code_category_entry ci,\r\n" + 
				"  TRANSACTION trx\r\n" + 
				"WHERE trx.transaction_type    ='COL'\r\n" + 
				" AND trx.reference_id          = lsm.CMS_COLLATERAL_ID\r\n" + 
				" AND lsm.CMS_LSP_APPR_LMTS_ID  = lmts.CMS_LSP_APPR_LMTS_ID\r\n" + 
				" AND (lsm.UPDATE_STATUS_IND   != 'D'\r\n" + 
				"OR lsm.UPDATE_STATUS_IND     IS NULL)\r\n" + 
				" AND lsm.CMS_COLLATERAL_ID     = date1.CMS_COLLATERAL_ID\r\n" + 
				" AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID\r\n" + 
				" AND sp.CMS_LE_SUB_PROFILE_ID  = pf.CMS_CUSTOMER_ID\r\n" + 
				" AND rm.id(+)                  = sp.relation_mgr\r\n" + 
				" AND ci.entry_code(+)          = sp.lsp_sgmnt_code_value\r\n" + 
				" AND lsm.CHARGE_ID            IN\r\n" + 
				"  (SELECT MAX(MAPS2.CHARGE_ID)\r\n" + 
				"  FROM cms_limit_security_map maps2\r\n" + 
				"  WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id\r\n" + 
				"  AND maps2.cms_collateral_id      = date1.cms_collateral_id\r\n" + 
				"  )\r\n" + 
				" AND date1.STATUS     = 'APPROVED'\r\n" + 
				" AND ci.category_code ='HDFC_SEGMENT'\r\n" + 
				" AND ci.active_status ='1'\r\n" + 
				" AND sp.status       != 'INACTIVE'\r\n" + 
				" AND date1.DP_CALCULATE_MANUALLY ='YES'";
		
		System.out.println("In  getStockStatementDPYes Query = " + selectSQL);

		return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List stockStatementDPYesList = new ArrayList();

				while (rs.next()) {
					String partyId = rs.getString("PARTYID");						
					String partyName= rs.getString("partyname");
					String segmentName = rs.getString("segmentname");
					String rmRegion= rs.getString("rmRegion");
					String rmName = rs.getString("rmname");
					String dueDate= rs.getString("duedate");
					String receivedDate = rs.getString("receiveddate");
					String periodDesc= rs.getString("PERIODDESC");
					String component = rs.getString("component");
					String header= rs.getString("header");
					String flag = rs.getString("flag");
					String collateralShare= rs.getString("collateralShare");
					String grossValue = rs.getString("grossValue");
					String margin= rs.getString("margin");
					String netAmount = rs.getString("netAmount");
					String dp= rs.getString("DP");
					String dpCalculateManually = rs.getString("DP_CALCULATE_MANUALLY");
					String componentCategory= rs.getString("COMPONENT_CATEGORY");
					String maker = rs.getString("Maker");
					String makerDateTime= rs.getString("MakerDateTime");
					String approvedBy = rs.getString("Approved_By");
					String checkerDateTime= rs.getString("CheckerDateTime");
																					
				//	System.out.println("partyId:: "+partyId + " componentCategory:: "+componentCategory);
					String[] arr = new String[22];
					
					arr[0] = partyId;
					arr[1] = partyName;
					arr[2] = segmentName;
					arr[3] = rmRegion;
					arr[4] = rmName;
					arr[5] = dueDate;
					arr[6] = receivedDate;
					arr[7] = periodDesc;
					arr[8] = component;
					arr[9] = header;
					arr[10] = flag;
					arr[11] = collateralShare;
					arr[12] = grossValue;
					arr[13] = margin;
					arr[14] = netAmount;
					arr[15] = dp;
					arr[16] = dpCalculateManually;
					arr[17] = componentCategory;
					arr[18] = maker;
					arr[19] = makerDateTime;
					arr[20] = approvedBy;
					arr[21]= checkerDateTime;
					
					stockStatementDPYesList.add(arr);
				}
				return stockStatementDPYesList;
			}
		});
		
	}
	
	public List getStockStatementDPNo() {
		String selectSQL ="SELECT DISTINCT sp.lsp_le_id AS partyId,\r\n" + 
				"  sp.lsp_short_name          AS partyname,\r\n" + 
				"  ci.entry_name              AS segmentname,\r\n" + 
				"  (SELECT region_name FROM cms_region WHERE id =sp.rm_region\r\n" + 
				"  )                                     AS rmRegion,\r\n" + 
				"  rm.rm_mgr_name                        AS rmname,\r\n" + 
				"  TO_CHAR(date1.due_date,'DD/MON/YYYY') AS duedate,\r\n" + 
				"  TO_CHAR(date1.LAST_UPDATED_ON,'DD/MON/YYYY') AS receiveddate,\r\n" + 
				"  ( SELECT DISTINCT document_description\r\n" + 
				"  FROM cms_document_globallist\r\n" + 
				"  WHERE document_code = date1.doc_code\r\n" + 
				"  )                   AS periodDesc,\r\n" + 
				"  cc.entry_name       AS component,\r\n" + 
				"  stockdet.stock_type AS \"header\",\r\n" + 
				"  CASE\r\n" + 
				"    WHEN stockdet.stock_type = 'CurrentAsset'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT has_insurance\r\n" + 
				"      FROM cms_component\r\n" + 
				"      WHERE component_code=cc.entry_code\r\n" + 
				"      AND component_type  = 'Current_Asset'\r\n" + 
				"      )\r\n" + 
				"    WHEN stockdet.stock_type = 'CurrentLiabilities'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT has_insurance\r\n" + 
				"      FROM cms_component\r\n" + 
				"      WHERE component_code=cc.entry_code\r\n" + 
				"      AND component_type  = 'Current_Liability'\r\n" + 
				"      )\r\n" + 
				"  END                         AS flag,\r\n" + 
				"  date1.fundedshare           AS collateralShare,\r\n" + 
				"  stockdet.COMPONENT_AMOUNT   AS grossValue,\r\n" + 
				"  stockdet.margin             AS margin,\r\n" + 
				"  stockdet.lonable            AS netAmount,\r\n" + 
				"  date1.calculateddp          AS DP,\r\n" + 
				" --- date1.DP_CALCULATE_MANUALLY AS DP_CALCULATE_MANUALLY,\r\n" + 
				"  CASE\r\n" + 
				"      WHEN date1.DP_CALCULATE_MANUALLY is null\r\n" + 
				"      THEN 'NO'\r\n" + 
				"      WHEN date1.DP_CALCULATE_MANUALLY = 'NO'\r\n" + 
				"      THEN 'NO'\r\n" + 
				"       END  AS DP_CALCULATE_MANUALLY ,\r\n" + 
				"  cms_com.COMPONENT_CATEGORY  AS COMPONENT_CATEGORY,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.from_state = 'PENDING_PERFECTION'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state       = 'PENDING_PERFECTION'\r\n" + 
				"      AND hist.to_state         ='DRAFT'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_CREATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state      IN ('ND','DRAFT')\r\n" + 
				"      AND hist.to_state         ='PENDING_CREATE'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('ACTIVE','DRAFT')\r\n" + 
				"        AND to_state         = 'PENDING_UPDATE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_DELETE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state         ='PENDING_DELETE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'ACTIVE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','PENDING_DELETE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'REJECTED'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       = 'REJECTED'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'DRAFT'\r\n" + 
				"    THEN\r\n" + 
				"      CASE\r\n" + 
				"        WHEN trx.status = 'PENDING_UPDATE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT hist.login_id\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='PENDING_UPDATE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"        WHEN trx.status = 'ACTIVE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT hist.login_id\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='ACTIVE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"      END\r\n" + 
				"  END AS Maker,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.from_state = 'PENDING_PERFECTION'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state       = 'PENDING_PERFECTION'\r\n" + 
				"      AND hist.to_state         ='DRAFT'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_CREATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state      IN ('ND','DRAFT')\r\n" + 
				"      AND hist.to_state         ='PENDING_CREATE'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('ACTIVE','DRAFT')\r\n" + 
				"        AND to_state         = 'PENDING_UPDATE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_DELETE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state         ='PENDING_DELETE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'ACTIVE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='ACTIVE'\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','PENDING_DELETE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'REJECTED'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       = 'REJECTED'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'DRAFT'\r\n" + 
				"    THEN\r\n" + 
				"      CASE\r\n" + 
				"        WHEN trx.status = 'PENDING_UPDATE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='PENDING_UPDATE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"        WHEN trx.status = 'ACTIVE'\r\n" + 
				"        THEN\r\n" + 
				"          (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"          FROM trans_history hist\r\n" + 
				"          WHERE hist.TR_HISTORY_ID=\r\n" + 
				"            (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"            FROM trans_history\r\n" + 
				"            WHERE transaction_id = trx.transaction_id\r\n" + 
				"            AND from_state       = 'DRAFT'\r\n" + 
				"            AND to_state         ='ACTIVE'\r\n" + 
				"            )\r\n" + 
				"          )\r\n" + 
				"      END\r\n" + 
				"  END AS MakerDateTime,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.status   = 'PENDING_CREATE'\r\n" + 
				"    OR trx.from_state = 'PENDING_PERFECTION'\r\n" + 
				"    THEN ''\r\n" + 
				"    WHEN trx.status = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT hist.login_id\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN ((trx.status  != 'PENDING_CREATE'\r\n" + 
				"    AND trx.from_state != 'PENDING_PERFECTION')\r\n" + 
				"    AND trx.status     != 'PENDING_UPDATE')\r\n" + 
				"    THEN TO_CHAR(trx.login_id)\r\n" + 
				"  END AS Approved_By,\r\n" + 
				"  CASE\r\n" + 
				"    WHEN trx.from_state = 'PENDING_CREATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.transaction_id = trx.transaction_id\r\n" + 
				"      AND hist.from_state       ='PENDING_CREATE'\r\n" + 
				"      AND hist.to_state         ='ACTIVE'\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_UPDATE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='PENDING_UPDATE'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'PENDING_DELETE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='PENDING_DELETE'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'ACTIVE'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state      IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        AND to_state        IN ('PENDING_UPDATE','ACTIVE')\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'REJECTED'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND from_state       ='REJECTED'\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"    WHEN trx.from_state = 'DRAFT'\r\n" + 
				"    THEN\r\n" + 
				"      (SELECT TO_CHAR(hist.TRANSACTION_DATE ,'DD-Mon-yy HH:MI:SS')\r\n" + 
				"      FROM trans_history hist\r\n" + 
				"      WHERE hist.TR_HISTORY_ID=\r\n" + 
				"        (SELECT MAX(TR_HISTORY_ID)\r\n" + 
				"        FROM trans_history\r\n" + 
				"        WHERE transaction_id = trx.transaction_id\r\n" + 
				"        AND to_state         ='ACTIVE'\r\n" + 
				"        )\r\n" + 
				"      )\r\n" + 
				"  END AS CheckerDateTime\r\n" + 
				"FROM CMS_ASSET_GC_DET date1,\r\n" + 
				"  CMS_ASSET_GC_STOCK_DET stockdet,\r\n" + 
				"  CMS_RELATIONSHIP_MGR rm,\r\n" + 
				"  CMS_LIMIT_SECURITY_MAP lsm,\r\n" + 
				"  SCI_LSP_APPR_LMTS lmts ,\r\n" + 
				"  SCI_LSP_LMT_PROFILE pf,\r\n" + 
				"  SCI_LE_SUB_PROFILE sp,\r\n" + 
				"  common_code_category_entry ci,\r\n" + 
				"  common_code_category_entry cc,\r\n" + 
				"  cms_component cms_com,\r\n" + 
				"  TRANSACTION trx\r\n" + 
				" WHERE\r\n" + 
				" trx.transaction_type ='COL'\r\n" + 
				"  AND trx.reference_id          = lsm.CMS_COLLATERAL_ID\r\n" + 
				" AND lsm.CMS_LSP_APPR_LMTS_ID  = lmts.CMS_LSP_APPR_LMTS_ID\r\n" + 
				" AND (lsm.UPDATE_STATUS_IND   != 'D'\r\n" + 
				"OR lsm.UPDATE_STATUS_IND     IS NULL)\r\n" + 
				" AND lsm.CMS_COLLATERAL_ID     = date1.CMS_COLLATERAL_ID\r\n" + 
				" AND pf.CMS_LSP_LMT_PROFILE_ID = lmts.CMS_LIMIT_PROFILE_ID\r\n" + 
				" AND sp.CMS_LE_SUB_PROFILE_ID  = pf.CMS_CUSTOMER_ID\r\n" + 
				" AND rm.id(+)                  = sp.relation_mgr\r\n" + 
				" AND ci.entry_code(+)          = sp.lsp_sgmnt_code_value\r\n" + 
				" AND lsm.CHARGE_ID IN\r\n" + 
				"  (SELECT MAX(MAPS2.CHARGE_ID)\r\n" + 
				"  FROM cms_limit_security_map maps2\r\n" + 
				"  WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id\r\n" + 
				"  AND maps2.cms_collateral_id      = date1.cms_collateral_id\r\n" + 
				"  )\r\n" + 
				" AND date1.STATUS                 = 'APPROVED'\r\n" + 
				" AND ci.category_code             ='HDFC_SEGMENT'\r\n" + 
				" AND ci.active_status             ='1'\r\n" + 
				" AND sp.status                   != 'INACTIVE'\r\n" + 
				" AND cc.active_status             ='1'\r\n" + 
				" AND stockdet.component           = cc.entry_code\r\n" + 
				" AND cms_com.component_code       =stockdet.component\r\n" + 
				" AND stockdet.gc_det_id           =date1.gc_det_id\r\n" + 
				" AND (DATE1.DP_CALCULATE_MANUALLY !='YES' OR date1.DP_CALCULATE_MANUALLY is null)\r\n" + 
				"" ;
		
		System.out.println("In  getStockStatementDPNo Query = " + selectSQL);

		return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List stockStatementDPNoList = new ArrayList();

				while (rs.next()) {
					String partyId = rs.getString("PARTYID");						
					String partyName= rs.getString("partyname");
					String segmentName = rs.getString("segmentname");
					String rmRegion= rs.getString("rmRegion");
					String rmName = rs.getString("rmname");
					String dueDate= rs.getString("duedate");
					String receivedDate = rs.getString("receiveddate");
					String periodDesc= rs.getString("PERIODDESC");
					String component = rs.getString("component");
					String header= rs.getString("header");
					String flag = rs.getString("flag");
					String collateralShare= rs.getString("collateralShare");
					String grossValue = rs.getString("grossValue");
					String margin= rs.getString("margin");
					String netAmount = rs.getString("netAmount");
					String dp= rs.getString("DP");
					String dpCalculateManually = rs.getString("DP_CALCULATE_MANUALLY");
					String componentCategory= rs.getString("COMPONENT_CATEGORY");
					String maker = rs.getString("Maker");
					String makerDateTime= rs.getString("MakerDateTime");
					String approvedBy = rs.getString("Approved_By");
					String checkerDateTime= rs.getString("CheckerDateTime");
																					
				//	System.out.println("partyId:: "+partyId + " componentCategory:: "+componentCategory);
					String[] arr = new String[22];
					
					arr[0] = partyId;
					arr[1] = partyName;
					arr[2] = segmentName;
					arr[3] = rmRegion;
					arr[4] = rmName;
					arr[5] = dueDate;
					arr[6] = receivedDate;
					arr[7] = periodDesc;
					arr[8] = component;
					arr[9] = header;
					arr[10] = flag;
					arr[11] = collateralShare;
					arr[12] = grossValue;
					arr[13] = margin;
					arr[14] = netAmount;
					arr[15] = dp;
					arr[16] = dpCalculateManually;
					arr[17] = componentCategory;
					arr[18] = maker;
					arr[19] = makerDateTime;
					arr[20] = approvedBy;
					arr[21]= checkerDateTime;
					
					stockStatementDPNoList.add(arr);
				}
				return stockStatementDPNoList;
			}
		});
		
	}
	
	///
	public List getDiscrepencyDeferralFacility() {		
			String selectSQL ="SELECT DISTINCT descp.id AS discrepancyId,\r\n" + 
					"  cc_segment.entry_name  AS segmentName,\r\n" + 
					"  rm.rm_mgr_name         AS rmname,\r\n" + 
					"  descp.DISCREPENCY_TYPE,\r\n" + 
					"  (SELECT region_name FROM cms_region WHERE id=party.rm_region\r\n" + 
					"  ) AS region,\r\n" + 
					"  (SELECT entry_name\r\n" + 
					"  FROM common_code_category_entry\r\n" + 
					"  WHERE entry_code =cam.rbi_asset_classification\r\n" + 
					"  AND CATEGORY_CODE='RBI_ASSET_ClASSIFICATION'\r\n" + 
					"  )                                                 AS RBI_Asset,\r\n" + 
					"  party.lsp_short_name                              AS partyName,\r\n" + 
					"  party.lsp_LE_id                                   AS partyID,\r\n" + 
					"  cc_descp.ENTRY_NAME                               AS discrepancyDesc,\r\n" + 
					"  descp.STATUS                                      AS status,\r\n" + 
					"  TO_CHAR(descp.ORIGINAL_TARGET_DATE,'DD/Mon/YYYY') AS originalDuedate,\r\n" + 
					"  TO_CHAR(descp.CREATION_DATE,'DD/Mon/YYYY')        AS CREATIONDATE,\r\n" + 
					"\r\n" + 
					"  ca.APPROVAL_NAME,\r\n" + 
					"  (replace (replace (descp.remarks, chr (13), ''), chr (10), ' '))       AS creationRemark,\r\n" + 
					"  (replace (replace (descp.doc_remarks, chr (13), ''), chr (10), ' ')) AS DOC_REMARKS,\r\n" + 
					"\r\n" + 
					" (replace (replace ( (SELECT ENTRY_NAME\r\n" + 
					"  FROM COMMON_CODE_CATEGORY_ENTRY\r\n" + 
					"  WHERE category_code = 'FACILITY_CATEGORY'\r\n" + 
					"  AND entry_code      = sci.FACILITY_CAT\r\n" + 
					"  ), chr (13), ''), chr (10), ' '))                            AS FACILITYCATEGORY ,\r\n" + 
					"  sci.cms_req_sec_coverage     AS SANCTIONEDAMOUNT,\r\n" + 
					"  NVL(SCI.RELEASABLE_AMOUNT,0) AS RELEASABLEAMOUNT,\r\n" + 
					"  NVL(xref.RELEASED_AMOUNT,0)  AS RELEASEDAMOUNT,\r\n" + 
					"  NVL(xref.utilized_amount,0)  AS UTILIZEDAMOUNT,\r\n" + 
					"  fac.FACILITY_NAME,\r\n" + 
					"  fac.FACILITY_ID\r\n" + 
					"FROM SCI_LE_SUB_PROFILE party,\r\n" + 
					"  CMS_DISCREPENCY descp,\r\n" + 
					"  common_code_category_entry cc_segment,\r\n" + 
					"  cms_relationship_mgr rm,\r\n" + 
					"  SCI_LSP_APPR_LMTS SCI,\r\n" + 
					"  SCI_LSP_LMTS_XREF_MAP MAPSS,\r\n" + 
					"  SCI_LSP_SYS_XREF XREF,\r\n" + 
					"  CMS_DISC_FACILITY_LIST fac,\r\n" + 
					"  SCI_LSP_LMT_PROFILE cam,\r\n" + 
					"  common_code_category_entry cc_descp,\r\n" + 
					"  transaction trx,\r\n" + 
					"  cms_credit_approval ca\r\n" + 
					"WHERE party.lsp_sgmnt_code_value=cc_segment.entry_code(+)\r\n" + 
					"  AND party.lsp_le_id             = cam.llp_le_id\r\n" + 
					"  AND SCI.CMS_LSP_APPR_LMTS_ID    = MAPSS.CMS_LSP_APPR_LMTS_ID(+)\r\n" + 
					"  AND MAPSS.CMS_LSP_SYS_XREF_ID   = XREF.CMS_LSP_SYS_XREF_ID(+)\r\n" + 
					"  AND sci.cms_limit_profile_id    = cam.cms_lsp_lmt_profile_id\r\n" + 
					"  AND cam.llp_le_id               =party.lsp_le_id\r\n" + 
					"  AND rm.id(+)                    = party.relation_mgr\r\n" + 
					"  AND ca.approval_code(+)                    = descp.waived_by\r\n" + 
					"  AND sci.cms_limit_status(+)     = 'ACTIVE'\r\n" + 
					"  AND MAPSS.CMS_STATUS(+)         ='ACTIVE'\r\n" + 
					"  AND party.cms_le_sub_profile_id = descp.customer_id(+)\r\n" + 
					"  AND cc_descp.category_code      ='DISCREPENCY'\r\n" + 
					"  AND fac.DISCREPENCY_ID          = DESCP.ID\r\n" + 
					"  AND descp.discrepency           = cc_descp.entry_code(+)\r\n" + 
					"  AND party.status                = 'ACTIVE'\r\n" + 
					"  AND Upper(descp.status)        IN ('AWAITING','DEFERED','ACTIVE','WAIVED','CLOSED')\r\n" + 
					"  AND SCI.CMS_LSP_APPR_LMTS_ID    = FAC.FACILITY_ID\r\n" + 
					"  AND trx.transaction_type        ='DISCREPENCY'\r\n" + 
					"  AND trx.reference_id            =descp.id\r\n" + 
					"  AND descp.DISCREPENCY_TYPE      ='Facility'\r\n" + 
					"  AND cc_segment.category_code    = 'HDFC_SEGMENT'\r\n" + 
					"  AND trx.status                  ='ACTIVE'" ;
			
			System.out.println("In  getDiscrepencyDeferralFacility Query = " + selectSQL);

			return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List discrepencyDeferralFacilityList = new ArrayList();

					while (rs.next()) {
						String discrepencyId = rs.getString("DISCREPANCYID");
						String segmentName = rs.getString("SEGMENTNAME");
						String rmName = rs.getString("RMNAME");
						String discrepencyType = rs.getString("DISCREPENCY_TYPE");
						String region = rs.getString("REGION");
						String rbiAsset = rs.getString("RBI_ASSET");
						String partyName = rs.getString("PARTYNAME");
						String partyId = rs.getString("PARTYID");
						String discrepancyDesc = rs.getString("DISCREPANCYDESC");
						String status = rs.getString("STATUS");
						String originalDueDate = rs.getString("ORIGINALDUEDATE");
						String creationDate= rs.getString("CREATIONDATE");
						String approvalName = rs.getString("APPROVAL_NAME");
						String creationRemark = rs.getString("CREATIONREMARK");
						String docRemarks = rs.getString("DOC_REMARKS");
						String facilityCategory = rs.getString("FACILITYCATEGORY");
						String sanctionedAmount = rs.getString("SANCTIONEDAMOUNT");
						String releasableAmount = rs.getString("RELEASABLEAMOUNT");
						String releasedAmount = rs.getString("RELEASEDAMOUNT");
						String utilisedAmount = rs.getString("UTILIZEDAMOUNT");
						String facilityName = rs.getString("FACILITY_NAME");
						String facilityId = rs.getString("FACILITY_ID");
																						
					//	System.out.println("partyId:: "+partyId + " componentCategory:: "+componentCategory);
						String[] arr = new String[22];
						
						arr[0] = discrepencyId;
						arr[1] = segmentName;
						arr[2] = rmName;
						arr[3] = discrepencyType;
						arr[4] = region;
						arr[5] = rbiAsset;
						arr[6] = partyName;
						arr[7] = partyId;
						arr[8] = discrepancyDesc;
						arr[9] = status;
						arr[10] = originalDueDate;
						arr[11] = creationDate;
						arr[12] = approvalName;
						arr[13] = creationRemark;
						arr[14] = docRemarks;
						arr[15] = facilityCategory;
						arr[16] = sanctionedAmount;
						arr[17] = releasableAmount;
						arr[18] = releasedAmount;
						arr[19] = utilisedAmount;
						arr[20] = facilityName;
						arr[21]= facilityId;
						
						discrepencyDeferralFacilityList.add(arr);
					}
					return discrepencyDeferralFacilityList;
				}
			});		
		
	}
	public List getDiscrepencyDeferralGeneral() {
		String selectSQL ="SELECT descp.id         AS discrepancyId,\r\n" + 
				"  cc_segment.entry_name  AS segmentName,\r\n" + 
				"  rm.RM_MGR_NAME         AS rmname,\r\n" + 
				"  descp.discrepency_type                            AS DISCREPENCY_TYPE,\r\n" + 
				"  (SELECT region_name FROM cms_region WHERE id=party.rm_region\r\n" + 
				"  ) AS region,\r\n" + 
				"  (SELECT entry_name\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE entry_code =cam.rbi_asset_classification\r\n" + 
				"  AND CATEGORY_CODE='RBI_ASSET_ClASSIFICATION'\r\n" + 
				"  )                                                 AS RBI_Asset,\r\n" + 
				"  party.lsp_short_name                              AS partyName,\r\n" + 
				"  party.LSP_LE_ID                                   AS PARTYID,\r\n" + 
				"  cc_descp.ENTRY_NAME                               AS discrepancyDesc,\r\n" + 
				"  descp.STATUS                                      AS status,\r\n" + 
				"  TO_CHAR(descp.ORIGINAL_TARGET_DATE,'DD/Mon/YYYY') AS originalDuedate,\r\n" + 
				"  TO_CHAR(descp.CREATION_DATE,'DD/Mon/YYYY')        AS CREATIONDATE,\r\n" + 
				"  ca.APPROVAL_NAME,\r\n" + 
				"  (replace (replace (descp.remarks, chr (13), ''), chr (10), ' '))         AS CREATION_REMARKS\r\n" + 
				"FROM SCI_LE_SUB_PROFILE party,\r\n" + 
				"  CMS_DISCREPENCY descp,\r\n" + 
				"  common_code_category_entry cc_segment,\r\n" + 
				"  cms_relationship_mgr rm,\r\n" + 
				"  SCI_LSP_LMT_PROFILE cam,\r\n" + 
				"  common_code_category_entry cc_descp,\r\n" + 
				"  cms_credit_approval ca,\r\n" + 
				"  transaction trx\r\n" + 
				"WHERE party.lsp_sgmnt_code_value=cc_segment.entry_code(+)\r\n" + 
				"  AND party.lsp_le_id             = cam.llp_le_id\r\n" + 
				"  AND cam.llp_le_id               =party.lsp_le_id\r\n" + 
				"  AND rm.id(+)                   = party.relation_mgr\r\n" + 
				"  AND ca.approval_code(+)                    = descp.CREDIT_APPROVER\r\n" + 
				"  AND party.cms_le_sub_profile_id = descp.customer_id(+)\r\n" + 
				"  AND cc_descp.category_code      ='DISCREPENCY'\r\n" + 
				"  AND descp.discrepency           = cc_descp.entry_code(+)\r\n" + 
				"  AND Upper(descp.status)        IN ('AWAITING','DEFERED','ACTIVE','WAIVED','CLOSED')\r\n" + 
				"  AND party.status                = 'ACTIVE'\r\n" + 
				"  AND descp.DISCREPENCY_TYPE      ='General'\r\n" + 
				"  AND trx.transaction_type        ='DISCREPENCY'\r\n" + 
				"  AND trx.reference_id            =descp.id\r\n" + 
				"  AND cc_segment.category_code    = 'HDFC_SEGMENT'\r\n" + 
				"  AND trx.status                  ='ACTIVE'";
		
		System.out.println("In  getDiscrepencyDeferralGeneral Query = " + selectSQL);

		return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List discrepencyDeferralGeneralList = new ArrayList();

				while (rs.next()) {
					String discrepencyId = rs.getString("DISCREPANCYID");
					String segmentName = rs.getString("SEGMENTNAME");
					String rmName = rs.getString("RMNAME");
					String discrepencyType = rs.getString("DISCREPENCY_TYPE");
					String region = rs.getString("REGION");
					String rbiAsset = rs.getString("RBI_ASSET");
					String partyName = rs.getString("PARTYNAME");
					String partyId = rs.getString("PARTYID");
					String discrepancyDesc = rs.getString("DISCREPANCYDESC");
					String status = rs.getString("STATUS");
					String originalDueDate = rs.getString("ORIGINALDUEDATE");
					String creationDate= rs.getString("CREATIONDATE");
					String approvalName = rs.getString("APPROVAL_NAME");
					String creationRemark = rs.getString("CREATION_REMARKS");
					
																					
				//	System.out.println("partyId:: "+partyId + " componentCategory:: "+componentCategory);
					String[] arr = new String[14];
					
					arr[0] = discrepencyId;
					arr[1] = segmentName;
					arr[2] = rmName;
					arr[3] = discrepencyType;
					arr[4] = region;
					arr[5] = rbiAsset;
					arr[6] = partyName;
					arr[7] = partyId;
					arr[8] = discrepancyDesc;
					arr[9] = status;
					arr[10] = originalDueDate;
					arr[11] = creationDate;
					arr[12] = approvalName;
					arr[13] = creationRemark;
					
					
					discrepencyDeferralGeneralList.add(arr);
				}
				return discrepencyDeferralGeneralList;
			}
		});	
	}
	
	public List getDFSOJobDetails() {
		String selectSQL ="SELECT DISTINCT\r\n" + 
				"  ---1. CIBIL Report - Borrower Segment------RPT0033_1\r\n" + 
				"  sub_profile. lsp_le_id       AS PARTYID,\r\n" + 
				"  sub_profile.main_branch      AS BRANCHCODE,\r\n" + 
				"  SUB_PROFILE.LSP_SHORT_NAME   AS PARTYNAME,\r\n" + 
				"  SUB_PROFILE.BORROWER_DUNS_NO AS DUNSNUMBER,\r\n" + 
				"  (SELECT entry_name\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE entry_code =sub_profile.entity\r\n" + 
				"  AND CATEGORY_CODE='Entity'\r\n" + 
				"  )                            AS borrowerLegalConstitution,\r\n" + 
				" (replace (replace ( ADDRESS.LRA_ADDR_LINE_1, chr (13), ''), chr (10), ' '))      AS borrowerAddressLine1,\r\n" + 
				" (replace (replace ( ADDRESS.LRA_ADDR_LINE_2 , chr (13), ''), chr (10), ' '))      AS borrowerAddressLine2,\r\n" + 
				" (replace (replace ( ADDRESS.LRA_ADDR_LINE_3 , chr (13), ''), chr (10), ' '))     AS borrowerAddressLine3,\r\n" + 
				"  CTY.CITY_NAME                AS borrowerCity,\r\n" + 
				"  STATE.STATE_NAME             AS borrowerState,\r\n" + 
				"  ADDRESS.LRA_POST_CODE        AS borrowerPincode,\r\n" + 
				"  ADDRESS.LRA_TELEPHONE_TEXT   AS borrowerTelephone,\r\n" + 
				"  SUB_PROFILE.PAN              AS borrowerPan,\r\n" + 
				"  sub_profile.class_activity_1 AS classActivity1,\r\n" + 
				"  sub_profile.class_activity_2 AS classActivity2,\r\n" + 
				"  REGION.REGION_NAME           AS REGION,\r\n" + 
				"  RM.RM_MGR_NAME               AS RMNAME,\r\n" + 
				"  CC_SEGMENT.ENTRY_NAME        AS SEGMENTNAME,\r\n" + 
				"  --2. CLIMS - CAM\r\n" + 
				"  sub_profile.total_sanctioned_limit              AS sanctionAmount,\r\n" + 
				"  TO_CHAR(pf.LLP_BCA_REF_APPR_DATE,'DD/Mon/YYYY') AS sanctionDate,\r\n" + 
				"  --3. CIBIL Report - Guarantor Segment---RPT0033_3\r\n" + 
				"  --SUB_PROFILE.LSP_LE_ID AS PARTYID,\r\n" + 
				"  (replace (replace (grnt.guaranters_name, chr (13), ''), chr (10), ' ')) AS GUARANTOR_NAME,\r\n" + 
				"  (SELECT entry_name\r\n" + 
				"  FROM COMMON_CODE_CATEGORY_ENTRY\r\n" + 
				"  WHERE entry_code  = grnt.guarantor_type\r\n" + 
				"  AND category_code = 'GUARANTOR_TYPE'\r\n" + 
				"  ) AS GuarantorType,\r\n" + 
				"  (SELECT entry_name\r\n" + 
				"  FROM COMMON_CODE_CATEGORY_ENTRY\r\n" + 
				"  WHERE ENTRY_CODE  = GRNT.GUARANTOR_NATURE\r\n" + 
				"  AND CATEGORY_CODE = 'GUARANTOR_NATURE'\r\n" + 
				"  ) AS GuarantorNature,\r\n" + 
				"(replace (replace ((grnt.address_line1\r\n" +
				"		  || ' '\r\n" +
				"		  || grnt.address_line2\r\n" +
				"		  || ' '\r\n" +
				"		  || grnt.address_line3), chr (13), ''), chr (10), ' ')) AS guarantorADDRESS, \r\n" +
				"  (SELECT CITY_NAME FROM CMS_CITY WHERE id = grnt.city\r\n" + 
				"  ) AS guarantorCITY,\r\n" + 
				"  (SELECT STATE_NAME FROM CMS_STATE WHERE id = grnt.state\r\n" + 
				"  )            AS guarantorSTATE,\r\n" + 
				"  grnt.pincode AS guarantorPINCODE,\r\n" + 
				"  (SELECT COUNTRY_NAME\r\n" + 
				"  FROM CMS_COUNTRY\r\n" + 
				"  WHERE id = grnt.country\r\n" + 
				"  )                           AS guarantorCOUNTRY,\r\n" + 
				"  grnt.guaranters_pam         AS guarantorPAN,\r\n" + 
				"  grnt.guaranters_duns_number AS guarantorDUNSNUMBER,\r\n" + 
				"  grnt.guarantee_amt guarantorAmount,\r\n" + 
				"  --4. Borrower – Guarantor Report - RPT0029\r\n" + 
				"  secDetail.subtype_name AS guaranteeSecuritySubType,\r\n" + 
				"  --5. CIBIL Report - Relationship Segment - RPT0033_2\r\n" + 
				"  (\r\n" + 
				"  SELECT entry_name\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE category_code = 'RELATIONSHIP_TYPE'\r\n" + 
				"  AND ENTRY_CODE      = DIRECTOR.RELATIONSHIP_TYPE\r\n" + 
				"  )                  AS RELATIONSHIP_NUMBER,\r\n" + 
				"  DIRECTOR.FULL_NAME AS FULL_NAME,\r\n" + 
				"  (replace (replace (director.DIR_ADD1, chr (13), ''), chr (10), ' '))  AS DIR_ADD1,\r\n" + 
				"  (replace (replace (director.DIR_ADD2, chr (13), ''), chr (10), ' '))  AS DIR_ADD2,\r\n" + 
				"  (replace (replace (director.DIR_ADD3, chr (13), ''), chr (10), ' '))  AS DIR_ADD3,\r\n" + 
				"  (SELECT city_name FROM CMS_CITY WHERE id = director.DIR_CITY\r\n" + 
				"  ) AS DIR_CITY,\r\n" + 
				"  (SELECT STATE_NAME FROM CMS_STATE WHERE id = director.DIR_STATE\r\n" + 
				"  )                      AS DIR_STATE,\r\n" + 
				"  director.DIR_POST_CODE AS DIR_POST_CODE,\r\n" + 
				"  (SELECT COUNTRY_NAME FROM CMS_COUNTRY WHERE id = director.DIR_COUNTRY\r\n" + 
				"  )                   AS DIR_COUNTRY,\r\n" + 
				"  DIRECTOR.DIR_TEL_NO AS DIR_TEL_NO,\r\n" + 
				"  director.DIN_NO     AS DIN_NO,\r\n" + 
				"  --6. Customer wise security - RPT0010\r\n" + 
				"  smv.FACILITYNAME,\r\n" + 
				"  smv.COLLATERAL_NAME,\r\n" + 
				"  smv.SECURITYAMOUNT,\r\n" + 
				"  smv.VALUATIONAMT,\r\n" + 
				"  smv.DATEOFVAL,\r\n" + 
				"  smv.TYPEOFCHARGE,\r\n" + 
				"  ----7. Customer wise Property Master - RPT0009\r\n" + 
				"  (secDetail.CMS_COLLATERAL_ID) AS SecurityID,\r\n" + 
				"  (replace (replace ((prop.property_address\r\n" + 
				"  ||' '\r\n" + 
				"  ||prop.property_address_2\r\n" + 
				"  ||' '\r\n" + 
				"  ||prop.property_address_3\r\n" + 
				"  ||' '\r\n" + 
				"  ||prop.property_address_4\r\n" + 
				"  ||' '\r\n" + 
				"  ||prop.property_address_5\r\n" + 
				"  ||' '\r\n" + 
				"  ||prop.property_address_6), chr (13), ''), chr (10), ' ')) AS propertyAddress,\r\n" + 
				"  (SELECT r.country_name FROM cms_country r WHERE r.id= prop.country\r\n" + 
				"  ) AS propertyCountry,\r\n" + 
				"  (SELECT state_name FROM cms_state r WHERE r.id= prop.state\r\n" + 
				"  ) AS propertyState, --new\r\n" + 
				"  (SELECT city_name FROM cms_city c WHERE c.id= prop.nearest_city\r\n" + 
				"  )                           AS propertyCity,   --new\r\n" + 
				"  prop.pincode                AS propertyPincode,--new\r\n" + 
				"  sbb.SYSTEM_BANK_BRANCH_NAME AS VaultLocation,\r\n" + 
				"  (\r\n" + 
				"  CASE\r\n" + 
				"    WHEN secDetail.sec_priority ='Y'\r\n" + 
				"    THEN 'Primary'\r\n" + 
				"    WHEN secDetail.sec_priority ='N'\r\n" + 
				"    THEN 'Secondary'\r\n" + 
				"    ELSE '-'\r\n" + 
				"  END ) AS securityPriority,\r\n" + 
				"  (SELECT ENTRY_NAME\r\n" + 
				"  FROM COMMON_CODE_CATEGORY_ENTRY\r\n" + 
				"  WHERE ENTRY_CODE  = prop.margage_type\r\n" + 
				"  AND active_status ='1'\r\n" + 
				"  AND CATEGORY_CODE = 'MORTGAGE_TYPE'\r\n" + 
				"  )                                              AS MORTGAGE_TYPE,\r\n" + 
				"  TO_CHAR(prop.SALE_PURCHASE_DATE,'DD/Mon/YYYY') AS DateOfMortgage,\r\n" + 
				"  (replace (replace (prop.mortage_registered_ref, chr (13), ''), chr (10), ' '))                    AS mortgageRegdRef,\r\n" + 
				"  (replace (replace (prop.developer_group_company, chr (13), ''), chr (10), ' '))                   AS propertyownername,\r\n" + 
				"  (SELECT ENTRY_NAME\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE category_code = 'PROPERTY_TYPE'\r\n" + 
				"  AND active_status   ='1'\r\n" + 
				"  AND ENTRY_CODE      = prop.property_type\r\n" + 
				"  )                                          AS property_type,\r\n" + 
				"  TO_CHAR(prop.valuation_date,'DD/MON/YYYY') AS ValuationDate,\r\n" + 
				"  prop.total_property_amount,--new\r\n" + 
				"  (replace (replace ((SELECT valuation_agency_name\r\n" + 
				"  FROM cms_valuation_agency\r\n" + 
				"  WHERE id=prop.valuator_company\r\n" + 
				"  ), chr (13), ''), chr (10), ' ')) AS valuationCmpny,\r\n" + 
				"  (\r\n" + 
				"  CASE\r\n" + 
				"    WHEN prop.builtup_area=0\r\n" + 
				"    THEN '-'\r\n" + 
				"    ELSE prop.builtup_area\r\n" + 
				"      ||' '\r\n" + 
				"      ||\r\n" + 
				"      (SELECT entry_name\r\n" + 
				"      FROM common_code_category_entry\r\n" + 
				"      WHERE entry_code =prop.builtup_area_uom\r\n" + 
				"      AND active_status='1'\r\n" + 
				"      )\r\n" + 
				"  END )AS built_up_area,--new\r\n" + 
				"  (\r\n" + 
				"  CASE\r\n" + 
				"    WHEN prop.land_area=0\r\n" + 
				"    THEN '-'\r\n" + 
				"    ELSE prop.land_area\r\n" + 
				"      ||' '\r\n" + 
				"      ||\r\n" + 
				"      (SELECT entry_name\r\n" + 
				"      FROM common_code_category_entry\r\n" + 
				"      WHERE entry_code =prop.land_area_uom\r\n" + 
				"      AND active_status='1'\r\n" + 
				"      )\r\n" + 
				"  END)AS land_area,--new\r\n" + 
				"  (SELECT entry_name\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE category_code = 'ADV_NAME'\r\n" + 
				"  AND active_status   ='1'\r\n" + 
				"  AND entry_code      = prop.advocate_lawyer_name\r\n" + 
				"  )           AS advocate_name, --new\r\n" + 
				"  prop.lot_no AS cersai_Id,\r\n" + 
				"  (SELECT ENTRY_NAME\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE category_code='TYPE_CHARGE'\r\n" + 
				"  AND active_status  ='1'\r\n" + 
				"  AND entry_code     = secDetail.change_type\r\n" + 
				"  ) AS propertyTypeOfCharge,\r\n" + 
				"  TO_CHAR(prop.TSR_DATE,'DD/MON/YYYY') TSR_DATE,\r\n" + 
				"  DECODE(prop.CERSIA_REG_DATE,NULL,NULL,TO_CHAR(prop.CERSIA_REG_DATE,'DD/MON/YYYY')) CERSIA_DATE,\r\n" + 
				"  prop.LAND_VALUE_IRB           AS LAND_VALUE_IRB,\r\n" + 
				"  prop.BUILDING_VALUE_IRB       AS BUILDING_VALUE_IRB,\r\n" + 
				"  prop.RECONSTRUCTION_VALUE_IRB AS RECONSTRUCTION_VALUE_IRB\r\n" + 
				"FROM SCI_LE_SUB_PROFILE SUB_PROFILE,\r\n" + 
				" -- SCI_LE_MAIN_PROFILE MAIN_PROFILE,\r\n" + 
				"  (SELECT entry_name,\r\n" + 
				"    entry_code\r\n" + 
				"  FROM common_code_category_entry\r\n" + 
				"  WHERE category_code = 'HDFC_SEGMENT'\r\n" + 
				"  ) CC_SEGMENT ,\r\n" + 
				"  SCI_LE_REG_ADDR ADDRESS ,\r\n" + 
				"  CMS_RELATIONSHIP_MGR RM ,\r\n" + 
				"  CMS_CITY CTY ,\r\n" + 
				"  CMS_STATE STATE ,\r\n" + 
				"  CMS_REGION REGION ,\r\n" + 
				"  CMS_LIMIT_SECURITY_MAP lsm,\r\n" + 
				"  SCI_LSP_APPR_LMTS lmts,\r\n" + 
				"  SCI_LSP_LMT_PROFILE pf,\r\n" + 
				"  CMS_SECURITY secDetail,\r\n" + 
				"  CMS_GUARANTEE grnt,\r\n" + 
				"  SCI_LE_DIRECTOR DIRECTOR,\r\n" + 
				"  CUSTOMER_WISE_SECURITY_MV smv,\r\n" + 
				"  CMS_PROPERTY prop,\r\n" + 
				"  CMS_SYSTEM_BANK_BRANCH sbb\r\n" + 
				"WHERE SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = ADDRESS.CMS_LE_MAIN_PROFILE_ID\r\n" + 
				"AND SUB_PROFILE.LSP_SGMNT_CODE_VALUE    =CC_SEGMENT.ENTRY_CODE\r\n" + 
				"AND DIRECTOR.CMS_LE_MAIN_PROFILE_ID      = SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID\r\n" + 
				"AND ADDRESS.LRA_TYPE_VALUE               = 'CORPORATE'\r\n" + 
				"AND RM.ID                                = SUB_PROFILE.RELATION_MGR\r\n" + 
				"AND CTY.ID                               = ADDRESS.LRA_CITY_TEXT\r\n" + 
				"AND STATE.ID                             = ADDRESS.LRA_STATE\r\n" + 
				"AND REGION.ID                            = ADDRESS.LRA_REGION\r\n" + 
				"AND sub_profile.cms_le_sub_profile_id    = pf.cms_customer_id(+)\r\n" + 
				"--AND sub_profile.CMS_LE_MAIN_PROFILE_ID   = main_profile.CMS_LE_MAIN_PROFILE_ID\r\n" + 
				"  --AND secdetail.cms_collateral_id          = grnt.cms_collateral_id\r\n" + 
				"AND CC_SEGMENT.entry_code (+)   =sub_profile.lsp_sgmnt_code_value\r\n" + 
				"AND pf.cms_lsp_lmt_profile_id   = lmts.CMS_LIMIT_PROFILE_ID\r\n" + 
				"AND lmts.cms_lsp_appr_lmts_id   = lsm.cms_lsp_appr_lmts_id\r\n" + 
				"AND secdetail.cms_collateral_id =LSM.CMS_COLLATERAL_ID\r\n" + 
				"  --AND secDetail.TYPE_NAME                  = 'Guarantees'\r\n" + 
				"AND sub_profile.status   != 'INACTIVE'\r\n" + 
				"AND secDetail.STATUS      = 'ACTIVE'\r\n" + 
				"AND lmts.CMS_LIMIT_STATUS = 'ACTIVE'\r\n" + 
				"AND lsm.deletion_date    IS NULL\r\n" + 
				"AND lsm.CHARGE_ID        IN\r\n" + 
				"  (SELECT MAX(maps2.CHARGE_ID)\r\n" + 
				"  FROM cms_limit_security_map maps2\r\n" + 
				"  WHERE maps2.cms_lsp_appr_lmts_id = lmts.cms_lsp_appr_lmts_id\r\n" + 
				"  AND maps2.cms_collateral_id      =lsm.cms_collateral_id\r\n" + 
				"  AND lsm.cms_collateral_id        = secDetail.CMS_COLLATERAL_ID\r\n" + 
				"  )\r\n" + 
				"AND (LSM.UPDATE_STATUS_IND         != 'D'\r\n" + 
				"OR lsm.UPDATE_STATUS_IND           IS NULL)\r\n" + 
				"AND secdetail.cms_collateral_id     =smv.colId\r\n" + 
				"AND smv.colId                       = grnt.cms_collateral_id(+)\r\n" + 
				"AND smv.colId                       = prop.cms_collateral_id(+)\r\n" + 
				"AND secdetail.SECURITY_ORGANISATION = sbb.SYSTEM_BANK_BRANCH_CODE(+) ";
		
		System.out.println("In  getDiscrepencyDeferralGeneral Query = " + selectSQL);

		return (List) getJdbcTemplate().query(selectSQL, new Object[] {}, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List dfsoList = new ArrayList();

				while (rs.next()) {
					String partyId = rs.getString("PARTYID");
					String branchCode = rs.getString("BRANCHCODE");
					String partyName = rs.getString("PARTYNAME");
					String dnsNumber = rs.getString("DUNSNUMBER");
					String borrowerLegalConstitution = rs.getString("borrowerLegalConstitution");
					String borrowerAddressLine1 = rs.getString("borrowerAddressLine1");
					String borrowerAddressLine2 = rs.getString("borrowerAddressLine2");
					String borrowerAddressLine3 = rs.getString("borrowerAddressLine3");
					String borrowerCity = rs.getString("borrowerCity");
					String borrowerState = rs.getString("borrowerState");
					String borrowerPincode = rs.getString("borrowerPincode");
					String borrowerTelephone= rs.getString("borrowerTelephone");
					String borrowerPan = rs.getString("borrowerPan");
					String classActivity1 = rs.getString("classActivity1");
					String classactivity2 = rs.getString("classactivity2");
					String region = rs.getString("region");
					String rmname = rs.getString("rmname");
					String segmentname = rs.getString("segmentname");
					
					String sanctionamount = rs.getString("sanctionamount");
					String sanctiondate = rs.getString("sanctiondate");
					
					String guarantor_name = rs.getString("guarantor_name");
					String guarantortype = rs.getString("guarantortype");
					String guarantornature = rs.getString("guarantornature");
					String guarantoraddress = rs.getString("guarantoraddress");
					String guarantorcity = rs.getString("guarantorcity");
					String guarantorstate= rs.getString("guarantorstate");
					String guarantorpincode = rs.getString("guarantorpincode");
					String guarantorcountry = rs.getString("guarantorcountry");	
					String guarantorpan = rs.getString("guarantorpan");
					String guarantordunsnumber = rs.getString("guarantordunsnumber");	
					String guarantoramount = rs.getString("guarantoramount");	
					
					String guaranteesecuritysubtype = rs.getString("guaranteesecuritysubtype");	
					
					String relationship_number = rs.getString("relationship_number");	
					String full_name = rs.getString("full_name");	
					String dir_add1 = rs.getString("dir_add1");	
					String dir_add2 = rs.getString("dir_add2");
					String dir_add3 = rs.getString("dir_add3");	
					String dir_city = rs.getString("dir_city");	
					String dir_state = rs.getString("dir_state");	
					String dir_post_code = rs.getString("dir_post_code");	
					String dir_country = rs.getString("dir_country");	
					String dir_tel_no = rs.getString("dir_tel_no");	
					String din_no = rs.getString("din_no");
					
					String facilityname = rs.getString("facilityname");	
					String collateral_name = rs.getString("collateral_name");	
					String securityamount = rs.getString("securityamount");	
					String valuationamt = rs.getString("valuationamt");	
					String dateofval = rs.getString("dateofval");	
					String typeofcharge = rs.getString("typeofcharge");	
					
					String securityid = rs.getString("securityid");
					String propertyaddress = rs.getString("propertyaddress");	
					String propertycountry = rs.getString("propertycountry");	
					String propertystate = rs.getString("propertystate");	
					String propertycity = rs.getString("propertycity");	
					String propertypincode = rs.getString("propertypincode");	
					String vaultlocation = rs.getString("vaultlocation");	
					String securitypriority = rs.getString("securitypriority");
					String mortgage_type = rs.getString("mortgage_type");	
					String dateofmortgage = rs.getString("dateofmortgage");	
					String mortgageregdref = rs.getString("mortgageregdref");	
					String propertyownername = rs.getString("propertyownername");	
					String property_type = rs.getString("property_type");	
					String valuationdate = rs.getString("valuationdate");	
					String total_property_amount = rs.getString("total_property_amount");
					String valuationcmpny = rs.getString("valuationcmpny");	
					String built_up_area = rs.getString("built_up_area");	
					String land_area = rs.getString("land_area");	
					String advocate_name = rs.getString("advocate_name");	
					String cersai_id = rs.getString("cersai_id");	
					String propertytypeofcharge = rs.getString("propertytypeofcharge");	
					String tsr_date = rs.getString("tsr_date");
					String cersia_date = rs.getString("cersia_date");	
					String land_value_irb = rs.getString("land_value_irb");	
					String building_value_irb = rs.getString("building_value_irb");	
					String reconstruction_value_irb = rs.getString("reconstruction_value_irb");	
					

					String[] arr = new String[75];
					
					arr[0] = partyId;
					arr[1] = branchCode;
					arr[2] = partyName;
					arr[3] = dnsNumber;
					arr[4] = borrowerLegalConstitution;
					arr[5] = borrowerAddressLine1;
					arr[6] = borrowerAddressLine2;
					arr[7] = borrowerAddressLine3;
					arr[8] = borrowerCity;
					arr[9] = borrowerState;
					arr[10] = borrowerPincode;
					arr[11] = borrowerTelephone;
					arr[12] = borrowerPan;
					arr[13] = classActivity1;
					arr[14] = classactivity2;
					arr[15] = region;
					arr[16] = rmname;
					arr[17] = segmentname;
					
					arr[18] = sanctionamount;
					arr[19] = sanctiondate;
					
					arr[20] = guarantor_name;
					arr[21] = guarantortype;
					arr[22] = guarantornature;
					arr[23] = guarantoraddress;
					arr[24] = guarantorcity;
					arr[25] = guarantorstate;
					arr[26] = guarantorpincode;
					arr[27] = guarantorcountry;
					arr[28] = guarantorpan;
					arr[29] = guarantordunsnumber;
					arr[30] = guarantoramount;
					
					arr[31] = guaranteesecuritysubtype;
					
					arr[32] = relationship_number;
					arr[33] = full_name;
					arr[34] = dir_add1;
					arr[35] = dir_add2;
					arr[36] = dir_add3;
					arr[37] = dir_city;
					arr[38] = dir_state;
					arr[39] = dir_post_code;
					arr[40] = dir_country;
					arr[41] = dir_tel_no;
					arr[42] = din_no;
					
					arr[43] = facilityname;
					arr[44] = collateral_name;
					arr[45] = securityamount;
					arr[46] = valuationamt;
					arr[47] = dateofval;
					arr[48] = typeofcharge;
					
					arr[49] = securityid;
					arr[50] = propertyaddress;
					arr[51] = propertycountry;
					arr[52] = propertystate;
					arr[53] = propertycity;
					arr[54] = propertypincode;
					arr[55] = vaultlocation;
					arr[56] = securitypriority;
					arr[57] = mortgage_type;
					arr[58] = dateofmortgage;
					arr[59] = mortgageregdref;
					arr[60] = propertyownername;
					arr[61] = property_type;
					arr[62] = valuationdate;
					arr[63] = total_property_amount;
					arr[64] = valuationcmpny;
					arr[65] = built_up_area;
					arr[66] = land_area;
					arr[67] = advocate_name;
					arr[68] = cersai_id;
					arr[69] = propertytypeofcharge;
					arr[70] = tsr_date;
					arr[71] = cersia_date;
					arr[72] = land_value_irb;
					arr[73] = building_value_irb;
					arr[74] = reconstruction_value_irb;
					
					dfsoList.add(arr);
				}
				return dfsoList;
			}
		});	
	}
	
	public String generateSeqForDFSO()  {
		String generateSourceString="select concat (to_char(sysdate,'YYYYMMDD'), LPAD(DFSO_DATA_LOG_SEQ.nextval, 9,'0')) sequence from dual";
		
		DefaultLogger.debug(this,"Select query for generateSeqForDFSO is  "+generateSourceString);

		List resultList = getJdbcTemplate().query(generateSourceString, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("sequence");
			}
		});
		if(resultList.size()==0){
			resultList.add("0");
		}
		return resultList.get(0).toString();
		    
	}
	
	public void insertLogForDFSO(OBDFSOLog log) {
		DefaultLogger.debug(this, "Inside Starting insertLogForDFSO(log)...");
		System.out.println("Inside Starting insertLogForDFSO(log)...");
		String id = generateSeqForDFSO();
		String query = "INSERT INTO  CMS_DFSO_DATA_LOGS (ID ," + " FILENAME, " + " STATUS, "
				+ " UPLOADTIME ) values (?, ?, ?, ?)";
		DefaultLogger.debug(this, "insertLogForDFSO(log)...id....." + id + "....query=>" + query);
		try {
			getJdbcTemplate().update(query,
					new Object[] { id, log.getFileName(), log.getStatus(), log.getUploadTime()});

		} catch (Exception e) {
			DefaultLogger.error(this, "Exception Caught =>insertLogForDFSO(log)...id....." + id + "....query=>" + query,
					e);
			System.out.println("Exception caught in insertLogForDFSO e=>" + e);
			e.printStackTrace();
		}
	}
	
	public List getScfStatusForLineById(String limitProfileId,String LineNo, String SerialNo ) {
		
		String sql ="select STATUS,ERRORMESSAGE,nvl(SCMFLAG,'N'),requestDateTime from CMS_JS_INTERFACE_LOG where requestdatetime in "
				+ "(select max(requestDateTime) from CMS_JS_INTERFACE_LOG where limitProfileId =? and line_no =? and serial_no=?)";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		
		DefaultLogger.debug(this,"Select query is>>"+sql);
		
		System.out.println("_________Query in limit dao for SCF_________:" + sql);
		System.out.println("_________limitProfileId_________:" + limitProfileId);
		System.out.println("_________LineNo_________:" + LineNo);
		System.out.println("_________SerialNo_________:" + SerialNo);

		List resultList = new ArrayList();

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, limitProfileId);
			dbUtil.setString(2, LineNo);
			dbUtil.setString(3, SerialNo);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				resultList.add( rs.getString("STATUS") );
				resultList.add( rs.getString("ERRORMESSAGE") );
				resultList.add( rs.getString(3) );	
			}
			
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}
		
		return resultList;
	}

	
	public String getFacilityTransactionStatus(String facilityId) throws LimitException {
		String theSQL = "SELECT STATUS FROM TRANSACTION WHERE REFERENCE_ID = (SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_APPR_LMTS WHERE LMT_ID = ?) ";
		
		List<String> queryForList=new ArrayList<String>();
		
		List<Object> param = new ArrayList<Object>();
		param.add(facilityId);
		System.out.println("<<<<<<getFacilityTransactionStatus>>>>>>>facilityId=>"+facilityId + "... theSQL=>"+ theSQL);
		
		try{
		 queryForList = getJdbcTemplate().query(theSQL,param.toArray(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rownum) throws SQLException {
				String str =rs.getString("STATUS");
	            return str;
			}
		});


		}
		catch(Exception e){
			System.out.println("Exception caught in getFacilityTransactionStatus => e =>"+e);
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
		}
		if(queryForList.size()==0){
			queryForList.add("");
		}
		
		return queryForList.get(0).toString();
	}
	
	public String getFacilityUpdateUploadTransactionStatus(String facilityId) throws LimitException {
		String theSQL = "SELECT STATUS FROM TRANSACTION WHERE STAGING_REFERENCE_ID= (select  MAX(FILE_ID) from STAGE_FACILITYDET_UPLOAD WHERE FACILITY_ID= ?)";
		
		List<String> queryForList=new ArrayList<String>();
		
		List<Object> param = new ArrayList<Object>();
		param.add(facilityId);
		System.out.println("<<<<<<getFacilityUpdateUploadTransactionStatus>>>>>>>Limit facilityId=>"+facilityId + "... the SQL=>"+ theSQL);
		
		try{
		 queryForList = getJdbcTemplate().query(theSQL,param.toArray(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rownum) throws SQLException {
				String str =rs.getString("STATUS");
	            return str;
			}
		});


		}
		catch(Exception e){
			System.out.println("Exception caught in getFacilityUpdateUploadTransactionStatus => e =>"+e);
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
		}
		if(queryForList.size()==0){
			queryForList.add("");
		}
		
		return queryForList.get(0).toString();
	}


	@Override
	public List getImageIdListForV2(DigitalLibraryRequestDTO digitalLibraryRequestDTO,String code,String imgid)
			throws SearchDAOException {
		String allDocCodes[] = digitalLibraryRequestDTO.getDocCode().split(",");
		final List<String> imageList=new ArrayList<String>();
		for (int i = 0; i < allDocCodes.length; i++) {
			String docCategory = getDocumentCodeType(allDocCodes[i]);
			if(docCategory!=null) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("	SELECT DISTINCT chklstitm.DOCUMENT_CODE,		"+
				"	  CASE		"+
				"	    WHEN imgmap.UNTAGGED_STATUS = 'N'	"+
				"	    THEN UPIMG.IMG_ID "+
				"	    ELSE 0	"+
				"	  END AS img_id,		"+
				"	  CASE		"+
				"	    WHEN imgmap.UNTAGGED_STATUS = 'N'		"+
				"	    THEN UPIMG.IMG_FILENAME		"+
				"	    ELSE 'NA'		"+
				"	  END AS IMG_FILENAME,		"+
				" 	  REGEXP_REPLACE( UPIMG.HCP_FILENAME, '[[:space:]]', '' ) as HCP_FILENAME,"+
				"	  chklstitm.DOC_DESCRIPTION , ");  
				  
		if(docCategory.equalsIgnoreCase("CAM") || docCategory.equalsIgnoreCase("IMG_CATEGORY_CAM"))
		{
			queryStr.append(
					"lmtPr.CAM_TYPE,"+
					"lmtPr.LLP_BCA_REF_NUM,"+
					"lmtPr.LLP_BCA_REF_APPR_DATE,"+
					"chklst.CATEGORY");
		}
		if(docCategory.equalsIgnoreCase("S")  || docCategory.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
		{
			queryStr.append(
					"chklst.CMS_COLLATERAL_ID,"+
					"colmas.NEW_COLLATERAL_DESCRIPTION, "
					);
		}
		if(docCategory.equalsIgnoreCase("F") || docCategory.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
		{
			queryStr.append(
					"SLAL.LMT_ID,"+
					"SLAL.FACILITY_NAME,"
					);
		}
		
		if("LAD".equalsIgnoreCase(docCategory)){
			queryStr.append(
					"chklstitm.COMPLETED_DATE,"
					
					);
		}
		
		 if(docCategory.equalsIgnoreCase("LAD") || docCategory.equalsIgnoreCase("S") || docCategory.equalsIgnoreCase("F") || docCategory.equalsIgnoreCase("O") || docCategory.equalsIgnoreCase("IMG_CATEGORY_SECURITY") ||  docCategory.equalsIgnoreCase("IMG_CATEGORY_OTHERS"))
		{
			queryStr.append(
					"chklstitm.DOC_DATE,"+
					"chklstitm.DOC_AMT,"+
					"chklstitm.HDFC_AMT,"+
					"chklstitm.CURRENCY"
					);
		}
		 if(docCategory.equalsIgnoreCase("REC"))
		{
			queryStr.append(
					"chklstitm.DOC_DATE,"+
					"common.ENTRY_NAME AS Statement_type"
					);
		}
		queryStr.append("	FROM CMS_UPLOADED_IMAGES UPIMG,	"+
				"	  SCI_LE_SUB_PROFILE SUBPR,	"+
				"	  sci_le_other_system OTHSYS	"
				);
		if(docCategory.equalsIgnoreCase("F") || docCategory.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
		{
			queryStr.append(",SCI_LSP_APPR_LMTS SLAL ");
		}
		if(docCategory.equalsIgnoreCase("S") || docCategory.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
		{
			queryStr.append(",CMS_SECURITY sec ");
			queryStr.append(" ,CMS_COLLATERAL_NEW_MASTER colmas  ");
		}
		if(docCategory.equalsIgnoreCase("REC"))
		{
			queryStr.append(",COMMON_CODE_CATEGORY_ENTRY common ");
		}
		queryStr.append(",sci_lsp_lmt_profile lmtPr ");
		queryStr.append(",CMS_CHECKLIST chklst ");
		queryStr.append(",CMS_CHECKLIST_ITEM chklstitm ");
		queryStr.append(",cms_image_tag_map imgmap");
		queryStr.append(",cms_image_tag_details imgdtl ");
		queryStr.append(",transaction trans ");
		queryStr.append(" ,SCI_LE_MAIN_PROFILE MAINPR ");
		queryStr.append("where UPIMG.CUST_ID = SUBPR.LSP_LE_ID ");
		queryStr.append("and OTHSYS.CMS_LE_MAIN_PROFILE_ID = SUBPR.CMS_LE_MAIN_PROFILE_ID ");
		queryStr.append(" and UPIMG.IMG_DEPRICATED != 'Y' ");
		queryStr.append("AND lmtPr.LLP_LE_ID = SUBPR.LSP_LE_ID ");
		queryStr.append("AND lmtPr.CMS_LSP_LMT_PROFILE_ID = chklst.CMS_LSP_LMT_PROFILE_ID ");
		queryStr.append("AND chklstitm.CHECKLIST_ID = chklst.CHECKLIST_ID ");
		queryStr.append("and trans.LEGAL_NAME = SUBPR.LSP_SHORT_NAME ");
		queryStr.append("and imgmap.TAG_ID = trans.REFERENCE_ID ");
		queryStr.append("and UPIMG.IMG_ID = imgmap.IMAGE_ID ");
		queryStr.append("and imgmap.TAG_ID = imgdtl.ID ");
		
		if(!(docCategory.equalsIgnoreCase("CAM") || docCategory.equalsIgnoreCase("IMG_CATEGORY_CAM")))
		{
		queryStr.append("and imgdtl.DOC_DESC = to_char(chklstitm.DOC_ITEM_ID) ");
		}
		
		queryStr.append(" AND chklstitm.STATUS = 'RECEIVED' ");
		
		queryStr.append(" AND MAINPR.LMP_LE_ID = SUBPR.LSP_LE_ID ");
		if(docCategory.equalsIgnoreCase("F") || docCategory.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
				{
					queryStr.append(" AND chklst.CMS_COLLATERAL_ID = SLAL.CMS_LSP_APPR_LMTS_ID(+)"+" AND chklst.CATEGORY = 'F'");
				}
		queryStr.append("and trans.TRANSACTION_TYPE='IMAGE_TAG' ");
		queryStr.append("and trans.status='ACTIVE' ");
		if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() && null == digitalLibraryRequestDTO.getDocRecvToDate()) {
			queryStr.append(" and chklstitm.RECEIVED_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocRecvFromDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null ==digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
			queryStr.append(" and chklstitm.RECEIVED_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocRecvToDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocRecvFromDate() && null != digitalLibraryRequestDTO.getDocRecvToDate()) {
			queryStr.append(" and chklstitm.RECEIVED_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocRecvFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocRecvToDate()+"', 'DD/MM/YY')");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocFromAmt() && null == digitalLibraryRequestDTO.getDocToAmt()) {
			queryStr.append(" and chklstitm.doc_amt >= to_number('"+digitalLibraryRequestDTO.getDocFromAmt()+"')");
		}
		
		if(null ==digitalLibraryRequestDTO.getDocFromAmt() && null != digitalLibraryRequestDTO.getDocToAmt()) {
			queryStr.append(" and chklstitm.doc_amt <= to_number('"+digitalLibraryRequestDTO.getDocToAmt()+"')");
		}

		if(null !=digitalLibraryRequestDTO.getDocFromAmt() && null != digitalLibraryRequestDTO.getDocToAmt()) {
			queryStr.append(" and chklstitm.doc_amt >= to_number('"+digitalLibraryRequestDTO.getDocFromAmt()+"')" + " and chklstitm.doc_amt <= to_number('"+digitalLibraryRequestDTO.getDocToAmt()+"')");
		}
		
		if(null !=digitalLibraryRequestDTO.getSystemName()) {
			queryStr.append(" and OTHSYS.CMS_LE_SYSTEM_NAME ='"+digitalLibraryRequestDTO.getSystemName()+"'");
		}
		
		
		if(null !=digitalLibraryRequestDTO.getDocTagFromDate() && null == digitalLibraryRequestDTO.getDocTagToDate()) {
			queryStr.append(" and trans.TRANSACTION_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocTagFromDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null ==digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
			queryStr.append(" and trans.TRANSACTION_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocTagToDate()+"'"+",'DD/MM/YY')");
		}
		
		
		if(null !=digitalLibraryRequestDTO.getDocTagFromDate() && null != digitalLibraryRequestDTO.getDocTagToDate()) {
			if(!digitalLibraryRequestDTO.getDocTagFromDate().equals(digitalLibraryRequestDTO.getDocTagToDate())) {
			queryStr.append(" and trans.TRANSACTION_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocTagFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocTagToDate()+"', 'DD/MM/YY')");
			}
			
			if(digitalLibraryRequestDTO.getDocTagFromDate().equals(digitalLibraryRequestDTO.getDocTagToDate())) {
				queryStr.append(" AND trans.TRANSACTION_DATE like to_date ('"+digitalLibraryRequestDTO.getDocTagFromDate()+"', 'DD-MON-YY')");	
			}
			}
		
		if(allDocCodes.length > 0)
		{
		queryStr.append(" and chklstitm.DOCUMENT_CODE = '"+allDocCodes[i]+"'");
		}
		if(null !=digitalLibraryRequestDTO.getDocFromDate() && null == digitalLibraryRequestDTO.getDocToDate()) {
			queryStr.append(" and chklstitm.DOC_DATE >= to_timestamp('"+digitalLibraryRequestDTO.getDocFromDate()+"'"+",'DD/MM/YY')");
		}
		if(null ==digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
			queryStr.append(" and chklstitm.DOC_DATE <= to_timestamp('"+digitalLibraryRequestDTO.getDocToDate()+"'"+",'DD/MM/YY')");
		}
		
		if(null !=digitalLibraryRequestDTO.getDocFromDate() && null != digitalLibraryRequestDTO.getDocToDate()) {
			queryStr.append(" and chklstitm.DOC_DATE BETWEEN to_timestamp ('"+digitalLibraryRequestDTO.getDocFromDate()+"', 'DD/MM/YY') AND to_timestamp ('"+digitalLibraryRequestDTO.getDocToDate()+"', 'DD/MM/YY')");
		}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
		queryStr.append("and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' ");
		}
		
		if(docCategory.equalsIgnoreCase("CAM") || docCategory.equalsIgnoreCase("IMG_CATEGORY_CAM"))
		{
			queryStr.append(" AND UPIMG.IMG_ID in ("+imgid+") ");
			queryStr.append("and chklst.CATEGORY = 'CAM' ");
			queryStr.append("AND UPIMG.CATEGORY = 'IMG_CATEGORY_CAM'");
		}
		if(docCategory.equalsIgnoreCase("O") || docCategory.equalsIgnoreCase("IMG_CATEGORY_OTHERS"))
		{
			queryStr.append(" AND chklstitm.DOC_DESCRIPTION like 'INSURANCE_POLICY%' ");
			queryStr.append(" AND UPIMG.CATEGORY = 'IMG_CATEGORY_OTHERS'");
		}
		if(docCategory.equalsIgnoreCase("REC"))
		{
			queryStr.append(" AND chklst.CATEGORY = 'REC'");
			queryStr.append(" AND chklstitm.STATEMENT_TYPE = common.ENTRY_CODE")
			;
		}
		if(docCategory.equalsIgnoreCase("LAD"))
		{
			queryStr.append(" AND chklst.CATEGORY = 'LAD'");
		}
		if(docCategory.equalsIgnoreCase("S") || docCategory.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
		{
			queryStr.append(" AND chklst.CMS_COLLATERAL_ID = sec.CMS_COLLATERAL_ID");
			queryStr.append(" AND sec.collateral_code = colmas.NEW_COLLATERAL_CODE");
			queryStr.append(" and UPIMG.CATEGORY ='IMG_CATEGORY_SECURITY' ");
		}
		/*if(docCategory.equalsIgnoreCase("F") || docCategory.equalsIgnoreCase("IMG_CATEGORY_FACILITY")) 
		{
			queryStr.append(" AND imgdtl.FACILITY_ID = SLAL.CMS_LSP_APPR_LMTS_ID(+)"+"AND chklst.CATEGORY = 'F'");
		}*/
		
		if(null == digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" and UPIMG.CUST_ID in (select lsp_le_id from sci_le_sub_profile where pan= '"+digitalLibraryRequestDTO.getPanNo()+"') ");
			}

		if(null == digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null==digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append("and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' and pan ='"+digitalLibraryRequestDTO.getPanNo()+"'");
			}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null==digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			
			queryStr.append(" and SUBPR.LSP_LE_ID='"+digitalLibraryRequestDTO.getPartyId()+"'");
			}
		
		if(null == digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			
			queryStr.append(" and SUBPR.PAN='"+digitalLibraryRequestDTO.getPanNo()+"'");
			}
		
		if(null != digitalLibraryRequestDTO.getPartyId() && null!=digitalLibraryRequestDTO.getSystemId() && null!=digitalLibraryRequestDTO.getPanNo()) {
			queryStr.append(" and UPIMG.CUST_ID = '"+digitalLibraryRequestDTO.getPartyId()+"' and SUBPR.pan ='"+digitalLibraryRequestDTO.getPanNo()+"'");
			queryStr.append(" AND UPIMG.IMG_ID IN " + 
					"  (SELECT image_id " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE FACILITY_ID IN " + 
					"      (SELECT CMS_LSP_APPR_LMTS_ID " + 
					"      FROM sci_lsp_lmts_xref_map " + 
					"      WHERE cms_lsp_sys_xref_id IN " + 
					"        (SELECT cms_lsp_sys_xref_id " + 
					"        FROM sci_lsp_sys_xref " + 
					"        WHERE facility_system_id ='"+digitalLibraryRequestDTO.getSystemId()+"' " + 
					"        ) " + 
					"      ) " + 
					"    ) " + 
					"  ) " + 
					"");
			}
		
		if(null != digitalLibraryRequestDTO.getDocRecvFromDate() || null !=digitalLibraryRequestDTO.getDocRecvToDate() 
				|| null != digitalLibraryRequestDTO.getDocFromAmt() || null != digitalLibraryRequestDTO.getDocToAmt()
				|| null != digitalLibraryRequestDTO.getDocFromDate() || null != digitalLibraryRequestDTO.getDocToDate() || null !=digitalLibraryRequestDTO.getDocCode()){
			queryStr.append(" and to_char(chklstitm.doc_item_id) = imgdtl.DOC_DESC "+
				"and imgmap.tag_id = imgdtl.id "+
				"and imgmap.IMAGE_ID = UPIMG.IMG_ID ");
		}
		
		if(null != digitalLibraryRequestDTO.getEmployeeCodeRM()) {
			queryStr.append(" AND MAINPR.RELATION_MGR_EMP_CODE ='"+digitalLibraryRequestDTO.getEmployeeCodeRM()+"'");
			
		}
		DBUtil dbUtil = null;
		ResultSet rs=null;
		//DefaultLogger.debug(this, "inside getImageIdList sql:"+queryStr);

	//System.out.println("inside getImageIdList sql:"+queryStr);			
	try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr.toString());
					System.out.println("Type of DOC-------------------->"+docCategory +"DIGITAL LIB IMAGE FETCH QUERY"+queryStr.toString());		 
					rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{
								
								if(docCategory.equalsIgnoreCase("CAM") || docCategory.equalsIgnoreCase("IMG_CATEGORY_CAM"))
								{	
									if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
										imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + ","  + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CAM_TYPE") + "," + rs.getString("LLP_BCA_REF_NUM") + "," + rs.getString("LLP_BCA_REF_APPR_DATE") + "," + rs.getString("CATEGORY") );
										}
										else if(rs.getString("IMG_ID").equalsIgnoreCase("0"))
										{
										imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CAM_TYPE") + "," + rs.getString("LLP_BCA_REF_NUM") + "," + rs.getString("LLP_BCA_REF_APPR_DATE") + "," + rs.getString("CATEGORY") );
										}
										else
										{
										imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
										}
								}
								
								if(docCategory.equalsIgnoreCase("LAD"))
								{	
									if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("COMPLETED_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else if(rs.getString("IMG_ID").equalsIgnoreCase("0"))
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("COMPLETED_DATE") +"," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
									}
								}
								if(docCategory.equalsIgnoreCase("O") || docCategory.equalsIgnoreCase("IMG_CATEGORY_OTHERS"))
								{	
									if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + ","+ rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA" + "," + rs.getString("DOC_DESCRIPTION")  + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA");
									}
								}
								if(docCategory.equalsIgnoreCase("REC"))
								{	
									if (rs.getString("IMG_ID") != null && !rs.getString("IMG_ID").equalsIgnoreCase("0")) {							 
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("STATEMENT_TYPE"));
									}
									else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("STATEMENT_TYPE"));
									}
									else
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
									}
								}
								if(docCategory.equalsIgnoreCase("F") || docCategory.equalsIgnoreCase("IMG_CATEGORY_FACILITY"))
								{	
									if (rs.getString("IMG_ID") != null) {							 
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + ","+ rs.getString("DOC_DESCRIPTION") + "," + rs.getString("LMT_ID") + "," + rs.getString("FACILITY_NAME") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
									{
									imageList.add(rs.getString("DOCUMENT_CODE")  + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION")+ "," + rs.getString("LMT_ID") + "," + rs.getString("FACILITY_NAME") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
									}
									 
								}
								if(docCategory.equalsIgnoreCase("S") || docCategory.equalsIgnoreCase("IMG_CATEGORY_SECURITY"))
								{	
									if (rs.getString("IMG_ID") != null) {							 
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + rs.getString("IMG_ID") + "," + rs.getString("IMG_FILENAME") + "," + rs.getString("HCP_FILENAME") + ","+ rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CMS_COLLATERAL_ID") + "," + rs.getString("NEW_COLLATERAL_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else if (rs.getString("IMG_ID").equalsIgnoreCase("0"))
									{
									imageList.add(rs.getString("DOCUMENT_CODE")  + "," +"NA,NA,NA" + "," + rs.getString("DOC_DESCRIPTION") + "," + rs.getString("CMS_COLLATERAL_ID") + "," + rs.getString("NEW_COLLATERAL_DESCRIPTION") + "," + rs.getString("DOC_DATE") + "," + rs.getString("DOC_AMT") + "," + rs.getString("HDFC_AMT") + "," + rs.getString("CURRENCY") );
									}
									else
									{
									imageList.add(rs.getString("DOCUMENT_CODE") + "," + "Does Not Exist,NA,NA,NA");
									}
								}
								
							}
							
						}
					 else{
						 imageList.add("No Records Found");
					 }
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getImageIdList:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
			}else {
		imageList.add("Invalid Doc Code -: "+allDocCodes[i]);
	}
		}
		System.out.println("Total no of fetched records"+imageList.size());					
		return imageList;
	}
	
	public String getDocumentCodeType(String docCode) throws SearchDAOException{
		
		String m = null;
		StringBuilder queryStr = new StringBuilder("SELECT DISTINCT chk.CATEGORY FROM CMS_CHECKLIST chk , CMS_CHECKLIST_ITEM item, SCI_LSP_LMT_PROFILE cam WHERE item.CHECKLIST_ID = chk.CHECKLIST_ID AND cam.CMS_LSP_LMT_PROFILE_ID= chk.CMS_LSP_LMT_PROFILE_ID AND item.IS_DELETED = 'N' AND item.DOCUMENT_CODE = ?");
		//System.out.println("<<<<<<getCheckDocumentCategory>>>>>>>" + queryStr);
		DBUtil dbUtil = null;
		ResultSet rs=null;
		boolean flag= false;
		DefaultLogger.debug(this, "inside getCheckDocumentCode sql:"+queryStr);

				try {
					dbUtil=new DBUtil();
					
					dbUtil.setSQL(queryStr.toString());
					dbUtil.setString(1, docCode);
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
							m = rs.getString("category");
							}
						}
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getCheckDocumentCode:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
						
		return m;
	}

	
public StringBuilder getTagIdwithCamDetails(String customerId, String panNo, String systemiId){
		long tagId;
		StringBuilder tagIdStr = new StringBuilder();
		StringBuilder queryStr = new StringBuilder("select ID from CMS_IMAGE_TAG_DETAILS,SCI_LE_MAIN_PROFILE where DOC_TYPE='CAM_NOTE' AND LMP_LE_ID=CUST_ID ");
		if(customerId != null && !customerId.isEmpty())
		{
			queryStr.append(" AND  CUST_ID = '"+customerId+"'");
		}
		if(customerId == null && panNo != null &&  !panNo.isEmpty())
		{
			queryStr.append(" AND PAN = '"+panNo+"'");
		}
		DBUtil dbUtil = null;
		ResultSet rs=null;
		boolean flag= false;
		DefaultLogger.debug(this, "inside getTagIdwithCamDetails sql:"+queryStr);

				try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr.toString());
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
								tagId = rs.getLong("ID");
								tagIdStr = tagIdStr.append(String.valueOf(tagId)+",");
							}
						}
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getTagIdwithCamDetails:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
						
		return tagIdStr;
	}
	@Override
	public String getCamDetails(String customerId, String PanNo, String SystemiId) {
		//System.out.println("Inside getCamDetails(String customerId)");
		String camDetails = null,camDatestr = null ,camType = null,camNo = null;
		Date camDate = null;
		
		StringBuilder queryStr = new StringBuilder("SELECT LLP_BCA_REF_NUM,LLP_BCA_REF_APPR_DATE,CAM_TYPE FROM SCI_LSP_LMT_PROFILE ");		
		
		if(customerId != null && !customerId.isEmpty())
		{
			queryStr.append(" where LLP_LE_ID = '"+customerId+"'");
		}
		if(customerId == null && PanNo != null &&  !PanNo.isEmpty())
		{
			queryStr.append(" , SCI_LE_MAIN_PROFILE WHERE LMP_LE_ID=LLP_LE_ID AND PAN = '"+PanNo+"'");
		} 
		DBUtil dbUtil = null;
		ResultSet rs=null;
		DefaultLogger.debug(this, "inside getCamDetails sql:"+queryStr);

				try {
					dbUtil=new DBUtil();
					dbUtil.setSQL(queryStr.toString());
					 rs = dbUtil.executeQuery();
					 if(rs!=null)
						{
							while(rs.next())
							{ 
								camNo = rs.getString("LLP_BCA_REF_NUM");
								camDate =  rs.getDate("LLP_BCA_REF_APPR_DATE");
								camType = rs.getString("CAM_TYPE");
								SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
								camDatestr= DateFor.format(camDate);
								
							}
						}
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in  getCamDetails:"+e.getMessage());
				}finally {
					finalize(dbUtil,rs);
				}
				if(camNo != null && camType != null && camDatestr != null)
				{
					camDetails= camNo +"-"+camType+"-"+camDatestr;	
				}
				
		return  camDetails;
	}
	public String getLimitCustomerId(String profile) throws LimitException {
		String theSQL = "SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID =( " + 
				"SELECT LLP_LE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID = ?)";
		
		List<String> queryForList=new ArrayList<String>();
		
		List<Object> param = new ArrayList<Object>();
		param.add(profile);
		System.out.println("<<<<<<getLimitCustomerId>>>>>>>CustomerId=>"+profile + "... theSQL=>"+ theSQL);
		
		try{
		 queryForList = getJdbcTemplate().query(theSQL,param.toArray(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rownum) throws SQLException {
				String str =rs.getString("CMS_LE_SUB_PROFILE_ID");
	            return str;
			}
		});


		}
		catch(Exception e){
			System.out.println("Exception caught in getLimitCustomerId => e =>"+e);
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
		}
		if(queryForList.size()==0){
			queryForList.add("");
		}
		
		return queryForList.get(0).toString();
	}
	



public ILineCovenant[] getLineCovenantData(String lmtID,String tableType) throws LimitException {
	String tableName = null;
	String columnName = null;
	
	if(tableType.equalsIgnoreCase("mainTable")) {
		tableName = "CMS_SPEC_LINE_COVENANT_DTLS";
	}else {
		tableName = "STG_SPEC_LINE_COVENANT_DETAILS";
	}
		
	if (lmtID==null) {
		lmtID = "0";
	}
		String selectSQL = "SELECT \r\n" + 
				"COVENANT_ID,\r\n" + 
				"COVENANT_REQ,"+
				"CMS_LSP_APPR_LMTS_ID,\r\n" + 
				"CMS_LSP_SYS_XREF_ID,\r\n" + 
				"COUNTRY_REST_REQ,\r\n" + 
				"DRAWER_REQ,\r\n" + 
				"DRAWEE_REQ,\r\n" + 
				"BENEFICIARY_REQ,\r\n" + 
				"COMBINED_TENOR_REQ,\r\n" + 
				"RUNNING_ACCOUNT_REQ,\r\n" + 
				"SELL_DOWN_REQ,\r\n" + 
				"LAST_AVAILABLE_DATE_REQ,\r\n" + 
				"MORATORIUM_REQ,\r\n" + 
				"GOODS_REST_REQ,\r\n" + 
				"CURRENCY_REST_REQ,\r\n" + 
				"BANK_REST_REQ,\r\n" + 
				"BUYERS_RATING_REQ,\r\n" + 
				"ECGC_COVER_REQ,\r\n" + 
				"RESTRICTEDCOUNTRYNAME,\r\n" + 
				"RESTRICTEDAMOUNT,\r\n" + 
				"DRAWERNAME,\r\n" + 
				"DRAWERAMOUNT,\r\n" + 
				"DRAWERCUSTID,\r\n" + 
				"DRAWERCUSTNAME,\r\n" + 
				"DRAWEENAME,\r\n" + 
				"DRAWEEAMOUNT,\r\n" + 
				"DRAWEECUSTID,\r\n" + 
				"DRAWEECUSTNAME,\r\n" + 
				"BENENAME,\r\n" + 
				"BENEAMOUNT,\r\n" + 
				"BENECUSTID,\r\n" + 
				"BENECUSTNAME,\r\n" + 
				"MAXCOMBINEDTENOR,\r\n" + 
				"PRESHIPMENTLINKAGE,\r\n" + 
				"POSTSHIPMENTLINKAGE,\r\n" + 
				"RUNNINGACCOUNT,\r\n" + 
				"ORDERBACKEDBYLC,\r\n" + 
				"INCOTERM,\r\n" + 
				"INCOTERMMARGINPERCENT,\r\n" + 
				"INCOTERMDESC,\r\n" + 
				"MODULECODE,\r\n" + 
				"COMMITMENTTENOR,\r\n" + 
				"SELLDOWN,\r\n" + 
				"LASTAVAILABLEDATE,\r\n" + 
				"MORATORIUMPERIOD,\r\n" + 
				"EMIFREQUENCY,\r\n" + 
				"NO_OF_INSTALLMENTS,\r\n" + 
				"RESTRICTEDCURRENCY,\r\n" + 
				"RESTRICTEDCURRENCYAMOUNT,\r\n" + 
				"RESTRICTEDBANK,\r\n" + 
				"RESTRICTEDBANKAMOUNT,\r\n" + 
				"RESTRICTEDBANK,\r\n" + 
				"BUYERSRATING,\r\n" + 
				"AGENCYMASTER,\r\n" + 
				"RATINGMASTER,\r\n" +
				"SINGLECOVENANTIND,\r\n" + 
				"GOODSRESTRICTIONIND,\r\n" +
				"GOODSRESTRICTIONPARENTCODE,\r\n" +
				"GOODSRESTRICTIONCHILDCODE,\r\n" +
				"GOODSRESTRICTIONSUBCHILDCODE,\r\n" +
				"GOODSRESTRICTIONCOMBOCODE,\r\n" +
				"RESTRICTEDCOUNTRYIND,\r\n" +
				"RESTRICTEDBANKIND,\r\n" +
				"RESTRICTEDCURRENCYIND,\r\n" +
				"DRAWERIND,\r\n" +
				"DRAWEEIND,\r\n" +
				"BENEIND,\r\n" +
				"ECGCCOVER FROM "+tableName+" WHERE CMS_LSP_SYS_XREF_ID = '"+lmtID+"'";

		return (ILineCovenant[]) getJdbcTemplate().query(selectSQL,
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processLineCovenant(rs);
			}
		});
	}

private ILineCovenant[] processLineCovenant(ResultSet rs) throws SQLException {
	
	ArrayList list = new ArrayList();
	while (rs.next()) {
		OBLineCovenant covenantData = new OBLineCovenant();
		
		covenantData.setCovenantId(rs.getLong("COVENANT_ID"));//>0?rs.getLong("COVENANT_ID"):""
		covenantData.setCovenantReqd(rs.getString("COVENANT_REQ")!=null?rs.getString("COVENANT_REQ"):"");
		covenantData.setCountryRestrictionReqd(rs.getString("COUNTRY_REST_REQ")!=null?rs.getString("COUNTRY_REST_REQ"):"");
		covenantData.setDraweeReqd(rs.getString("DRAWEE_REQ")!=null?rs.getString("DRAWEE_REQ"):"");
		covenantData.setDrawerReqd(rs.getString("DRAWER_REQ")!=null?rs.getString("DRAWER_REQ"):"");
		covenantData.setBeneficiaryReqd(rs.getString("BENEFICIARY_REQ")!=null?rs.getString("BENEFICIARY_REQ"):"");
		covenantData.setCombinedTenorReqd(rs.getString("COMBINED_TENOR_REQ")!=null?rs.getString("COMBINED_TENOR_REQ"):"");
		covenantData.setRunningAccountReqd(rs.getString("RUNNING_ACCOUNT_REQ")!=null?rs.getString("RUNNING_ACCOUNT_REQ"):"");
		covenantData.setSellDownReqd(rs.getString("SELL_DOWN_REQ")!=null?rs.getString("SELL_DOWN_REQ"):"");
		covenantData.setLastAvailableDateReqd(rs.getString("LAST_AVAILABLE_DATE_REQ")!=null?rs.getString("LAST_AVAILABLE_DATE_REQ"):"");
		covenantData.setMoratoriumReqd(rs.getString("MORATORIUM_REQ")!=null?rs.getString("MORATORIUM_REQ"):"");
		covenantData.setGoodsRestrictionReqd(rs.getString("GOODS_REST_REQ")!=null?rs.getString("GOODS_REST_REQ"):"");
		covenantData.setCurrencyRestrictionReqd(rs.getString("CURRENCY_REST_REQ")!=null?rs.getString("CURRENCY_REST_REQ"):"");
		covenantData.setBankRestrictionReqd(rs.getString("BANK_REST_REQ")!=null?rs.getString("BANK_REST_REQ"):"");
		covenantData.setBuyersRatingReqd(rs.getString("BUYERS_RATING_REQ")!=null?rs.getString("BUYERS_RATING_REQ"):"");
		covenantData.setEcgcCoverReqd(rs.getString("ECGC_COVER_REQ")!=null?rs.getString("ECGC_COVER_REQ"):"");
		covenantData.setRestrictedCountryname(rs.getString("RESTRICTEDCOUNTRYNAME")!=null?rs.getString("RESTRICTEDCOUNTRYNAME"):"");
		covenantData.setRestrictedAmount(rs.getString("RESTRICTEDAMOUNT")!=null?rs.getString("RESTRICTEDAMOUNT"):"");
		covenantData.setDrawerName(rs.getString("DRAWERNAME")!=null?rs.getString("DRAWERNAME"):"");
		covenantData.setDrawerAmount(rs.getString("DRAWERAMOUNT")!=null?rs.getString("DRAWERAMOUNT"):"");
		covenantData.setDrawerCustId(rs.getString("DRAWERCUSTID")!=null?rs.getString("DRAWERCUSTID"):"");
		covenantData.setDrawerCustName(rs.getString("DRAWERCUSTNAME")!=null?rs.getString("DRAWERCUSTNAME"):"");
		covenantData.setDraweeName(rs.getString("DRAWEENAME")!=null?rs.getString("DRAWEENAME"):"");
		covenantData.setDraweeAmount(rs.getString("DRAWEEAMOUNT")!=null?rs.getString("DRAWEEAMOUNT"):"");
		covenantData.setDraweeCustId(rs.getString("DRAWEECUSTID")!=null?rs.getString("DRAWEECUSTID"):"");
		covenantData.setDraweeCustName(rs.getString("DRAWEECUSTNAME")!=null?rs.getString("DRAWEECUSTNAME"):"");
		covenantData.setBeneName(rs.getString("BENENAME")!=null?rs.getString("BENENAME"):"");
		covenantData.setBeneAmount(rs.getString("BENEAMOUNT")!=null?rs.getString("BENEAMOUNT"):"");
		covenantData.setBeneCustId(rs.getString("BENECUSTID")!=null?rs.getString("BENECUSTID"):"");
		covenantData.setBeneCustName(rs.getString("BENECUSTNAME")!=null?rs.getString("BENECUSTNAME"):"");
		covenantData.setMaxCombinedTenor(rs.getString("MAXCOMBINEDTENOR")!=null?rs.getString("MAXCOMBINEDTENOR"):"");
		covenantData.setPreshipmentLinkage(rs.getString("PRESHIPMENTLINKAGE")!=null?rs.getString("PRESHIPMENTLINKAGE"):"");
		covenantData.setPostShipmentLinkage(rs.getString("POSTSHIPMENTLINKAGE")!=null?rs.getString("POSTSHIPMENTLINKAGE"):"");
		covenantData.setRunningAccount(rs.getString("RUNNINGACCOUNT")!=null?rs.getString("RUNNINGACCOUNT"):"");
		covenantData.setOrderBackedbylc(rs.getString("ORDERBACKEDBYLC")!=null?rs.getString("ORDERBACKEDBYLC"):"");
		covenantData.setIncoTerm(rs.getString("INCOTERM")!=null?rs.getString("INCOTERM"):"");
		covenantData.setIncoTermMarginPercent(rs.getString("INCOTERMMARGINPERCENT")!=null?rs.getString("INCOTERMMARGINPERCENT"):"");
		covenantData.setIncoTermDesc(rs.getString("INCOTERMDESC")!=null?rs.getString("INCOTERMDESC"):"");
		covenantData.setModuleCode(rs.getString("MODULECODE")!=null?rs.getString("MODULECODE"):"");
		covenantData.setCommitmentTenor(rs.getString("COMMITMENTTENOR")!=null?rs.getString("COMMITMENTTENOR"):"");
		covenantData.setSellDown(rs.getString("SELLDOWN")!=null?rs.getString("SELLDOWN"):"");
		covenantData.setLastAvailableDate(rs.getDate("LASTAVAILABLEDATE"));
		covenantData.setMoratoriumPeriod(rs.getString("MORATORIUMPERIOD")!=null?rs.getString("MORATORIUMPERIOD"):"");
		covenantData.setEmiFrequency(rs.getString("EMIFREQUENCY")!=null?rs.getString("EMIFREQUENCY"):"");
		covenantData.setNoOfInstallments(rs.getString("NO_OF_INSTALLMENTS")!=null?rs.getString("NO_OF_INSTALLMENTS"):"");
		//covenantData.setGoodsRestrictionCode(rs.getString("GoodsRestrictionCode")!=null?rs.getString("RESTRICTEDGOODSCODE"):"");
		covenantData.setRestrictedCurrency(rs.getString("RESTRICTEDCURRENCY")!=null?rs.getString("RESTRICTEDCURRENCY"):"");
		covenantData.setRestrictedCurrencyAmount(rs.getString("RESTRICTEDCURRENCYAMOUNT")!=null?rs.getString("RESTRICTEDCURRENCYAMOUNT"):"");
		covenantData.setRestrictedBank(rs.getString("RESTRICTEDBANK")!=null?rs.getString("RESTRICTEDBANK"):"");
		covenantData.setRestrictedBankAmount(rs.getString("RESTRICTEDBANKAMOUNT")!=null?rs.getString("RESTRICTEDBANKAMOUNT"):"");
		covenantData.setBuyersRating(rs.getString("BUYERSRATING")!=null?rs.getString("BUYERSRATING"):"");
		covenantData.setAgencyMaster(rs.getString("AGENCYMASTER")!=null?rs.getString("AGENCYMASTER"):"");
		covenantData.setRatingMaster(rs.getString("RATINGMASTER")!=null?rs.getString("RATINGMASTER"):"");
		covenantData.setEcgcCover(rs.getString("ECGCCOVER")!=null?rs.getString("ECGCCOVER"):"");
		//covenantData.setGoodsRestrictionCode(rs.getString("goodsRestrictionCode")!=null?rs.getString("goodsRestrictionCode"):"");
		covenantData.setGoodsRestrictionParentCode(rs.getString("GOODSRESTRICTIONPARENTCODE")!=null?rs.getString("GOODSRESTRICTIONPARENTCODE"):"");
		covenantData.setSingleCovenantInd(rs.getString("SINGLECOVENANTIND")!=null?rs.getString("SINGLECOVENANTIND"):"");
		covenantData.setBeneInd(rs.getString("BENEIND")!=null?rs.getString("BENEIND"):"");
		covenantData.setDraweeInd(rs.getString("DRAWEEIND")!=null?rs.getString("DRAWEEIND"):"");
		covenantData.setDrawerInd(rs.getString("DRAWERIND")!=null?rs.getString("DRAWERIND"):"");
		covenantData.setGoodsRestrictionInd(rs.getString("GOODSRESTRICTIONIND")!=null?rs.getString("GOODSRESTRICTIONIND"):"");
		covenantData.setRestrictedBankInd(rs.getString("RESTRICTEDBANKIND")!=null?rs.getString("RESTRICTEDBANKIND"):"");
		covenantData.setRestrictedCountryInd(rs.getString("RESTRICTEDCOUNTRYIND")!=null?rs.getString("RESTRICTEDCOUNTRYIND"):"");
		covenantData.setRestrictedCurrencyInd(rs.getString("RESTRICTEDCURRENCYIND")!=null?rs.getString("RESTRICTEDCURRENCYIND"):"");
		covenantData.setGoodsRestrictionChildCode(rs.getString("GOODSRESTRICTIONCHILDCODE")!=null?rs.getString("GOODSRESTRICTIONCHILDCODE"):"");
		covenantData.setGoodsRestrictionSubChildCode(rs.getString("GOODSRESTRICTIONSUBCHILDCODE")!=null?rs.getString("GOODSRESTRICTIONSUBCHILDCODE"):"");
		covenantData.setGoodsRestrictionComboCode(rs.getString("GOODSRESTRICTIONCOMBOCODE")!=null?rs.getString("GOODSRESTRICTIONCOMBOCODE"):"");
		
		list.add(covenantData);
		}
	rs.close();
	return (OBLineCovenant[]) list.toArray(new OBLineCovenant[0]);

}


//If line does not have data.Fetch details from its facility

public ILineCovenant[] getCovenantDataForMissingLine(String lmtID,String tableType) throws LimitException {
	String tableName = null;
	String columnName = null;
	
	if(tableType.equalsIgnoreCase("mainTable")) {
		tableName = "CMS_SPEC_COVENANT_DTLS";
	}else {
		tableName = "STG_SPEC_COVENANT_DETAILS";
	}
	
	if (lmtID==null) {
		lmtID = "0";
	}
		String selectSQL = "SELECT \r\n" + 
				"COVENANT_ID,\r\n" + 
				"COVENANT_REQ,"+
				"CMS_LSP_APPR_LMTS_ID,\r\n" + 
				"CMS_LSP_SYS_XREF_ID,\r\n" + 
				"COUNTRY_REST_REQ,\r\n" + 
				"DRAWER_REQ,\r\n" + 
				"DRAWEE_REQ,\r\n" + 
				"BENEFICIARY_REQ,\r\n" + 
				"COMBINED_TENOR_REQ,\r\n" + 
				"RUNNING_ACCOUNT_REQ,\r\n" + 
				"SELL_DOWN_REQ,\r\n" + 
				"LAST_AVAILABLE_DATE_REQ,\r\n" + 
				"MORATORIUM_REQ,\r\n" + 
				"GOODS_REST_REQ,\r\n" + 
				"CURRENCY_REST_REQ,\r\n" + 
				"BANK_REST_REQ,\r\n" + 
				"BUYERS_RATING_REQ,\r\n" + 
				"ECGC_COVER_REQ,\r\n" + 
				"RESTRICTEDCOUNTRYNAME,\r\n" + 
				"RESTRICTEDAMOUNT,\r\n" + 
				"DRAWERNAME,\r\n" + 
				"DRAWERAMOUNT,\r\n" + 
				"DRAWERCUSTID,\r\n" + 
				"DRAWERCUSTNAME,\r\n" + 
				"DRAWEENAME,\r\n" + 
				"DRAWEEAMOUNT,\r\n" + 
				"DRAWEECUSTID,\r\n" + 
				"DRAWEECUSTNAME,\r\n" + 
				"BENENAME,\r\n" + 
				"BENEAMOUNT,\r\n" + 
				"BENECUSTID,\r\n" + 
				"BENECUSTNAME,\r\n" + 
				"MAXCOMBINEDTENOR,\r\n" + 
				"PRESHIPMENTLINKAGE,\r\n" + 
				"POSTSHIPMENTLINKAGE,\r\n" + 
				"RUNNINGACCOUNT,\r\n" + 
				"ORDERBACKEDBYLC,\r\n" + 
				"INCOTERM,\r\n" + 
				"INCOTERMMARGINPERCENT,\r\n" + 
				"INCOTERMDESC,\r\n" + 
				"MODULECODE,\r\n" + 
				"COMMITMENTTENOR,\r\n" + 
				"SELLDOWN,\r\n" + 
				"LASTAVAILABLEDATE,\r\n" + 
				"MORATORIUMPERIOD,\r\n" + 
				"EMIFREQUENCY,\r\n" + 
				"NO_OF_INSTALLMENTS,\r\n" + 
				"GoodsRestrictionCode,\r\n" + 
				"RESTRICTEDCURRENCY,\r\n" + 
				"RESTRICTEDCURRENCYAMOUNT,\r\n" + 
				"RESTRICTEDBANK,\r\n" + 
				"RESTRICTEDBANKAMOUNT,\r\n" + 
				"BUYERSRATING,\r\n" + 
				"AGENCYMASTER,\r\n" + 
				"RATINGMASTER,\r\n" +
				"SINGLECOVENANTIND,\r\n" + 
				"GOODSRESTRICTIONIND,\r\n" +
				"GOODSRESTRICTIONPARENTCODE,\r\n" +
				"GOODSRESTRICTIONCHILDCODE,\r\n" +
				"GOODSRESTRICTIONSUBCHILDCODE,\r\n" +
				"GOODSRESTRICTIONCOMBOCODE,\r\n" +
				"RESTRICTEDCOUNTRYIND,\r\n" +
				"RESTRICTEDBANKIND,\r\n" +
				"RESTRICTEDCURRENCYIND,\r\n" +
				"DRAWERIND,\r\n" +
				"DRAWEEIND,\r\n" +
				"BENEIND,\r\n" +
				"ECGCCOVER FROM "+tableName+" WHERE CMS_LSP_APPR_LMTS_ID = '"+lmtID+"'";

		return (ILineCovenant[]) getJdbcTemplate().query(selectSQL,
				new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				return processLineCovenant(rs);
			}
		});
	}



public List getRestrictedCountryList(String LmtId) throws SearchDAOException {
    String query="";
	 query = "select  RESTRICTEDCOUNTRYNAME,RESTRICTEDAMOUNT from CMS_SPEC_COVENANT_DTLS where CMS_LSP_APPR_LMTS_ID = '"+LmtId+"'";
	 
	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
       	 OBLimitCovenant result = new OBLimitCovenant();
       	 result.setRestrictedCountryname(rs.getString("RESTRICTEDCOUNTRYNAME"));
		 result.setRestrictedAmount(rs.getString("RESTRICTEDAMOUNT"));
         return result;
        }
    }
    );
	 return resultList;
}

public List getRestrictedBankList(String LmtId) throws SearchDAOException {
    String query="";
	 query = "select  RESTRICTEDBANK,RESTRICTEDBANKAMOUNT from CMS_SPEC_COVENANT_DTLS where CMS_LSP_APPR_LMTS_ID = '"+LmtId+"'";
	 
	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
       	 OBLimitCovenant result = new OBLimitCovenant();
       	 result.setRestrictedBank(rs.getString("RESTRICTEDBANK"));
		 result.setRestrictedBankAmount(rs.getString("RESTRICTEDBANKAMOUNT"));
         return result;
        }
    }
    );
	 return resultList;
}

public List getRestrictedCurrencyList(String LmtId) throws SearchDAOException {
    String query="";
	 query = "select  RESTRICTEDCURRENCY,RESTRICTEDCURRENCYAMOUNT from CMS_SPEC_COVENANT_DTLS where CMS_LSP_APPR_LMTS_ID = '"+LmtId+"'";
	 
	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
       	 OBLimitCovenant result = new OBLimitCovenant();
       	 result.setRestrictedCurrency(rs.getString("RESTRICTEDCURRENCY"));
		 result.setRestrictedCurrencyAmount(rs.getString("RESTRICTEDCURRENCYAMOUNT"));
         return result;
        }
    }
    );
	 return resultList;
}

public List getRestrictedCustomerList(String LmtId) throws SearchDAOException {
    String query="";
	 query = "select DRAWERAMOUNT,DRAWERCUSTID,DRAWERCUSTNAME,DRAWERNAME,DRAWEEAMOUNT,DRAWEECUSTID,DRAWEECUSTNAME,DRAWEENAME,BENEAMOUNT, " + 
	 		" BENECUSTID,BENECUSTNAME,BENENAME from CMS_SPEC_COVENANT_DTLS where CMS_LSP_APPR_LMTS_ID = '"+LmtId+"'";
	 
	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
       	 OBLimitCovenant result = new OBLimitCovenant();
       	 result.setDrawerAmount(rs.getString("DRAWERAMOUNT"));
		 result.setDrawerCustId(rs.getString("DRAWERCUSTID"));
		 result.setDrawerCustName(rs.getString("DRAWERCUSTNAME"));
		 result.setDrawerName(rs.getString("DRAWERNAME"));
		 result.setDraweeAmount(rs.getString("DRAWEEAMOUNT"));
		 result.setDraweeCustId(rs.getString("DRAWEECUSTID"));
		 result.setDraweeCustName(rs.getString("DRAWEECUSTNAME"));
		 result.setDraweeName(rs.getString("DRAWEENAME"));
		 result.setBeneAmount(rs.getString("BENEAMOUNT"));
		 result.setBeneCustId(rs.getString("BENECUSTID"));
		 result.setBeneCustName(rs.getString("BENECUSTNAME"));
		 result.setBeneName(rs.getString("BENENAME"));

         return result;
        }
    }
    );
	 return resultList;
} 

public List getRestrictedCountryListForLine(String LmtId) throws SearchDAOException {
    String query="";
	 query = "select  RESTRICTEDCOUNTRYNAME,RESTRICTEDAMOUNT from CMS_SPEC_LINE_COVENANT_DTLS where CMS_LSP_SYS_XREF_ID = '"+LmtId+"'";
	 
	 List resultList = getJdbcTemplate().query(query, new RowMapper() {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OBLineCovenant result = new OBLineCovenant();
       	 result.setRestrictedCountryname(rs.getString("RESTRICTEDCOUNTRYNAME"));
		 result.setRestrictedAmount(rs.getString("RESTRICTEDAMOUNT"));
         return result;
        }
    }
    );
	 return resultList;
}
	 public List getRestrictedBankListForLine(String LmtId) throws SearchDAOException {
		    String query="";
			 query = "select  RESTRICTEDBANK,RESTRICTEDBANKAMOUNT from CMS_SPEC_LINE_COVENANT_DTLS where CMS_LSP_SYS_XREF_ID = '"+LmtId+"'";
			 
			 List resultList = getJdbcTemplate().query(query, new RowMapper() {
		        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	OBLineCovenant result = new OBLineCovenant();
		       	 result.setRestrictedBank(rs.getString("RESTRICTEDBANK"));
				 result.setRestrictedBankAmount(rs.getString("RESTRICTEDBANKAMOUNT"));
		         return result;
		        }
		    }
		    );
			 return resultList;
		}

		public List getRestrictedCurrencyListForLine(String LmtId) throws SearchDAOException {
		    String query="";
			 query = "select  RESTRICTEDCURRENCY,RESTRICTEDCURRENCYAMOUNT from CMS_SPEC_LINE_COVENANT_DTLS where CMS_LSP_SYS_XREF_ID = '"+LmtId+"'";
			 
			 List resultList = getJdbcTemplate().query(query, new RowMapper() {
		        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	OBLineCovenant result = new OBLineCovenant();
		       	 result.setRestrictedCurrency(rs.getString("RESTRICTEDCURRENCY"));
				 result.setRestrictedCurrencyAmount(rs.getString("RESTRICTEDCURRENCYAMOUNT"));
		         return result;
		        }
		    }
		    );
			 return resultList;
		}

		public List getRestrictedCustomerListForLine(String LmtId) throws SearchDAOException {
		    String query="";
			 query = "select DRAWERAMOUNT,DRAWERCUSTID,DRAWERCUSTNAME,DRAWERNAME,DRAWEEAMOUNT,DRAWEECUSTID,DRAWEECUSTNAME,DRAWEENAME,BENEAMOUNT, " + 
			 		" BENECUSTID,BENECUSTNAME,BENENAME from CMS_SPEC_LINE_COVENANT_DTLS where CMS_LSP_SYS_XREF_ID = '"+LmtId+"'";
			 
			 List resultList = getJdbcTemplate().query(query, new RowMapper() {
		        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		       	 OBLineCovenant result = new OBLineCovenant();
		       	 result.setDrawerAmount(rs.getString("DRAWERAMOUNT"));
				 result.setDrawerCustId(rs.getString("DRAWERCUSTID"));
				 result.setDrawerCustName(rs.getString("DRAWERCUSTNAME"));
				 result.setDrawerName(rs.getString("DRAWERNAME"));
				 result.setDraweeAmount(rs.getString("DRAWEEAMOUNT"));
				 result.setDraweeCustId(rs.getString("DRAWEECUSTID"));
				 result.setDraweeCustName(rs.getString("DRAWEECUSTNAME"));
				 result.setDraweeName(rs.getString("DRAWEENAME"));
				 result.setBeneAmount(rs.getString("BENEAMOUNT"));
				 result.setBeneCustId(rs.getString("BENECUSTID"));
				 result.setBeneCustName(rs.getString("BENECUSTNAME"));
				 result.setBeneName(rs.getString("BENENAME"));

		         return result;
		        }
		    }
		    );
			 return resultList;	 
}
		
		
		public String getIncoDesc(String incoTerm){
			String incoDesc=null;
			String getcamExt="select ENTRY_NAME from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'RUNNING_ACC_COVENANT' "
					+ " and ENTRY_CODE = '"+incoTerm+"' and ACTIVE_STATUS='1'";
			
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(getcamExt);
				 rs = dbUtil.executeQuery();
				while(rs.next()){
					incoDesc = rs.getString(1);
					
				}
			}catch(Exception e){
				DefaultLogger.debug(this, e.getMessage());
				e.printStackTrace();
			}	
			finally{
			finalize(dbUtil,rs);
			}
			return incoDesc;
		}
		
		
		public List getIncoTermList() throws SearchDAOException {
			String sql = "select ENTRY_CODE from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'RUNNING_ACC_COVENANT' and ACTIVE_STATUS='1' ";

			List resultList = getJdbcTemplate().query(sql, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String [] mgnrLst = new String[1];
					mgnrLst[0] = rs.getString("ENTRY_CODE");
					return mgnrLst;
				}
			});

			return resultList;
		}
		
		public ILineCovenant[] getLineCovenantDataForStp(long xrefId) throws LimitException {
			
				String selectSQL = "SELECT \r\n" + 
						"COVENANT_ID,\r\n" + 
						"COVENANT_REQ,"+
						"CMS_LSP_APPR_LMTS_ID,\r\n" + 
						"CMS_LSP_SYS_XREF_ID,\r\n" + 
						"MAXCOMBINEDTENOR,\r\n" + 
						"PRESHIPMENTLINKAGE,\r\n" + 
						"POSTSHIPMENTLINKAGE,\r\n" + 
						"RUNNINGACCOUNT,\r\n" + 
						"INCOTERM,\r\n" + 
						"INCOTERMMARGINPERCENT,\r\n" +
						"COMMITMENTTENOR,\r\n" + 
						"MORATORIUMPERIOD,\r\n" + 
						"EMIFREQUENCY,\r\n" + 
						"NO_OF_INSTALLMENTS,\r\n" + 						
						"COUNTRYRESTRICTIONFLAG,\r\n" +                
						"DRAWEEFLAG,\r\n" +                                
						"DRAWERFLAG,\r\n" +                                   
						"BENEFICIARYFLAG,\r\n" +                       
						"GOODSRESTRICTIONFLAG,\r\n" +                         
						"CURRENCYRESTRICTIONFLAG,\r\n" +                  
						"BANKRESTRICTIONFLAG,\r\n" +  
						"BUYERSRATING,\r\n" + 
						"SINGLECOVENANTIND,\r\n" + 
						"RUNNINGACCOUNTFLAG,\r\n" +
						"ORDERBACKEDBYLC,\r\n" +
						"MODULECODE,\r\n" +
						"ECGCCOVER FROM CMS_SPEC_LINE_COVENANT_DTLS WHERE SINGLECOVENANTIND='Y' AND CMS_LSP_SYS_XREF_ID = '"+xrefId+"'";

				DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record Query"+selectSQL);

				return (ILineCovenant[]) getJdbcTemplate().query(selectSQL,
						new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						return processLineCovenantForStp(rs);
					}
				});
			}

		private ILineCovenant[] processLineCovenantForStp(ResultSet rs) throws SQLException {
			
			ArrayList list = new ArrayList();
			while (rs.next()) {
				OBLineCovenant covenantData = new OBLineCovenant();
				
				covenantData.setSingleCovenantInd(rs.getString("SINGLECOVENANTIND")!=null?rs.getString("SINGLECOVENANTIND"):"");
				covenantData.setRunningAccount(rs.getString("RUNNINGACCOUNT")!=null?rs.getString("RUNNINGACCOUNT"):"");
				covenantData.setCommitmentTenor(rs.getString("COMMITMENTTENOR")!=null?rs.getString("COMMITMENTTENOR"):"");
				covenantData.setIncoTerm(rs.getString("INCOTERM")!=null?rs.getString("INCOTERM"):"");
				covenantData.setIncoTermMarginPercent(rs.getString("INCOTERMMARGINPERCENT")!=null?rs.getString("INCOTERMMARGINPERCENT"):"");
				covenantData.setMoratoriumPeriod(rs.getString("MORATORIUMPERIOD")!=null?rs.getString("MORATORIUMPERIOD"):"");
				covenantData.setEmiFrequency(rs.getString("EMIFREQUENCY")!=null?rs.getString("EMIFREQUENCY"):"");
				covenantData.setNoOfInstallments(rs.getString("NO_OF_INSTALLMENTS")!=null?rs.getString("NO_OF_INSTALLMENTS"):"");
				covenantData.setEcgcCover(rs.getString("ECGCCOVER")!=null?rs.getString("ECGCCOVER"):"");
				covenantData.setBuyersRating(rs.getString("BUYERSRATING")!=null?rs.getString("BUYERSRATING"):"");
				covenantData.setPreshipmentLinkage(rs.getString("PRESHIPMENTLINKAGE")!=null?rs.getString("PRESHIPMENTLINKAGE"):"");
				covenantData.setPostShipmentLinkage(rs.getString("POSTSHIPMENTLINKAGE")!=null?rs.getString("POSTSHIPMENTLINKAGE"):"");
				covenantData.setMaxCombinedTenor(rs.getString("MAXCOMBINEDTENOR")!=null?rs.getString("MAXCOMBINEDTENOR"):"");
				covenantData.setCountryRestrictionFlag(rs.getString("COUNTRYRESTRICTIONFLAG")!=null?rs.getString("COUNTRYRESTRICTIONFLAG"):"");
				covenantData.setDraweeFlag(rs.getString("DRAWEEFLAG")!=null?rs.getString("DRAWEEFLAG"):"");
				covenantData.setDrawerFlag(rs.getString("DRAWERFLAG")!=null?rs.getString("DRAWERFLAG"):"");
				covenantData.setBeneficiaryFlag(rs.getString("BENEFICIARYFLAG")!=null?rs.getString("BENEFICIARYFLAG"):"");
				covenantData.setGoodsRestrictionFlag(rs.getString("GOODSRESTRICTIONFLAG")!=null?rs.getString("GOODSRESTRICTIONFLAG"):"");
				covenantData.setCurrencyRestrictionFlag(rs.getString("CURRENCYRESTRICTIONFLAG")!=null?rs.getString("CURRENCYRESTRICTIONFLAG"):"");
				covenantData.setBankRestrictionFlag(rs.getString("BANKRESTRICTIONFLAG")!=null?rs.getString("BANKRESTRICTIONFLAG"):"");
				covenantData.setRunningAccountFlag(rs.getString("RUNNINGACCOUNTFLAG")!=null?rs.getString("RUNNINGACCOUNTFLAG"):"");
				covenantData.setOrderBackedbylc(rs.getString("ORDERBACKEDBYLC")!=null?rs.getString("ORDERBACKEDBYLC"):"");
				covenantData.setModuleCode(rs.getString("MODULECODE")!=null?rs.getString("MODULECODE"):"");
				
				list.add(covenantData);
				}
			rs.close();
			return (OBLineCovenant[]) list.toArray(new OBLineCovenant[0]);
		}
		
		public HashMap getCountryCodeList() throws SearchDAOException {
			String sql = "select country_code,country_name from cms_country where status = 'ACTIVE' order by country_code ";
			final HashMap retMap = new HashMap();

			getJdbcTemplate().query(sql, new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					String countryCcode = null;
					String countryName = null;
					while (rs.next()) {
						countryCcode = rs.getString("country_code");
						countryName = rs.getString("country_name");
						retMap.put(countryName, countryCcode);
					}
					return null;
				}
			});
			return retMap;
		}
		
		public String getFCUBSCountryCode(String code)throws SearchDAOException {

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+code+" getActiveProductCodeFromId");
			String queryStr = "SELECT COUNTRY_FCUBS_CODE from CMS_COUNRTY_CODE_MASTER where CONTRY_CLIMS_CODE = ?";
			try {

			
				return (String) getJdbcTemplate().query(queryStr, new Object[]{(code)},
						new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString(1);
						}
						return "0";
					}
				});
				
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new SearchDAOException();
			}
			
		}

		public String getExclusionLine()throws SearchDAOException{
			String queryStr = "SELECT LISTAGG( LINE_CODE ,',') AS LINE_CODE FROM CMS_EXC_LINE_FR_STP_SRM where status = 'ACTIVE' and deprecated = 'N' and excluded = 'on'";
		
			DBUtil dbUtil = null;
			ResultSet rs=null;
			String lineNumbers = "";
			
			DefaultLogger.debug(this, "inside getExclusionLine sql:"+queryStr);
					try {
						dbUtil=new DBUtil();
						dbUtil.setSQL(queryStr);
						 rs = dbUtil.executeQuery();
						 if(rs!=null)
							{
								while(rs.next())
								{ 
									lineNumbers = rs.getString("LINE_CODE");
								}
							}
					}catch (Exception e) {
						e.printStackTrace();
						DefaultLogger.debug(this, "Exception in  getExclusionLine:"+e.getMessage());
					}finally {
						finalize(dbUtil,rs);
					}
								
			return lineNumbers;
		}

		public Boolean getEntry_id(String entry_id) throws SearchDAOException{
			
			String queryStr = "select ENTRY_ID from COMMON_CODE_CATEGORY_ENTRY where ENTRY_ID= '"+entry_id+"'";
			boolean flag = false;
			DBUtil dbUtil = null;
			ResultSet rs=null;
			DefaultLogger.debug(this, "inside getEntry_id sql:"+queryStr);


					try {
						dbUtil=new DBUtil();
						dbUtil.setSQL(queryStr);
						 rs = dbUtil.executeQuery();
						 if(rs!=null)
							{
								while(rs.next())
								{ 
									flag = true;
								}
							}
					}catch (Exception e) {
						e.printStackTrace();
						DefaultLogger.debug(this, "Exception in  getEntry_id:"+e.getMessage());
					}finally {
						finalize(dbUtil,rs);
					}
								
			return flag;
		}
		public void executeSpCollateralMove(final String PartyId) throws SQLException {
			try {//Proc_SP_Move_Collateral_Data
				System.out.println("Inside executeSpCollateralMove(final String PartyId)=>PartyId=>"+PartyId);
	            getJdbcTemplate().execute("{call " + Proc_SP_Move_Collateral_Data + "(?)}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.setString(1, PartyId);
	                	cs.execute();
	                    return null;
	                }
	            });
	        }
	        catch (Exception ex) {
	        	System.out.println("Exception in executeSpCollateralMove(final String PartyId)=>PartyId=>"+PartyId+" ex=>"+ex);
	        	ex.printStackTrace();
	            throw new SQLException("Exception Error executeSpCollateralMove.");
	        }
		}

		public String getPartyIdUsingLimitProfileId(String LimitId) {
			System.out.println("Inside getPartyIdUsingLimitProfileId(String LimitId)=>LimitId=>"+LimitId);
			List party;
			String partyId;
			try {
				String SQL= "SELECT LLP_LE_ID " + 
						"  FROM SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID='"+LimitId+"' ";
				System.out.println("executeSpCollateralMove(final String PartyId)=>SQL=>"+SQL);
				party=getJdbcTemplate().queryForList(SQL);
				 								
						Map  map = (Map) party.get(0);
						 partyId = map.get("LLP_LE_ID").toString();		
			}
			catch(Exception e)
			{
				System.out.println("Exception in getPartyIdUsingLimitProfileId(String LimitId)=>LimitId=>"+LimitId+".. e=>"+e);
				throw new FileUploadException("ERROR-- Exception in getPartyIdUsingLimitProfileId(String LimitId) in LimitDAO.");
			}
			return partyId;

		}
		
		public String getRestrictedTypeForScheduler(String currencyCode){
			String restrictedType=null;
			String sql="SELECT distinct RESTRICTION_TYPE FROM CMS_FOREX WHERE CURRENCY_ISO_CODE = '"+currencyCode+"'  and rownum = 1 order by 1 desc ";
			System.out.println("getRestrictedTypeForScheduler => sql=>"+sql);
			DBUtil dbUtil = null;
			ResultSet rs=null;
			try {
				dbUtil = new DBUtil();
				dbUtil.setSQL(sql);
				 rs = dbUtil.executeQuery();
				while(rs.next()){
					restrictedType = rs.getString(1);
					
				}
			}catch(Exception e){
				DefaultLogger.debug(this, e.getMessage());
				e.printStackTrace();
			}	
			finally{
			finalize(dbUtil,rs);
			}
			return restrictedType;
		}
public Boolean getEntry_name(String entry_name) throws SearchDAOException{
			
			String queryStr = "select ENTRY_NAME from COMMON_CODE_CATEGORY_ENTRY where ENTRY_NAME= '"+entry_name+"'";
			boolean flag = false;
			DBUtil dbUtil = null;
			ResultSet rs=null;
			DefaultLogger.debug(this, "inside getEntry_name sql:"+queryStr);


					try {
						dbUtil=new DBUtil();
						dbUtil.setSQL(queryStr);
						 rs = dbUtil.executeQuery();
						 if(rs!=null)
							{
								while(rs.next())
								{ 
									flag = true;
								}
							}
					}catch (Exception e) {
						e.printStackTrace();
						DefaultLogger.debug(this, "Exception in  getEntry_name:"+e.getMessage());
					}finally {
						finalize(dbUtil,rs);
					}
								
			return flag;
		}

		public List getImageDetails(String tagId) {
			List<String> imgList=new ArrayList<String>();
			
			String sql="SELECT IMAGE_ID FROM CMS_IMAGE_TAG_MAP,CMS_UPLOADED_IMAGES WHERE IMAGE_ID = IMG_ID AND IMG_DEPRICATED ='N' AND UNTAGGED_STATUS='N' AND TAG_ID in ("+tagId+")";
			DBUtil dbUtil = null;
						ResultSet rs=null;
						DefaultLogger.debug(this, "inside getImageDetails sql:"+sql);

								try {
									dbUtil=new DBUtil();
									dbUtil.setSQL(sql);
									 rs = dbUtil.executeQuery();
									 if(rs!=null)
										{
											while(rs.next())
											{ 
												long imgId = rs.getLong("IMAGE_ID");
												String imgIdstr = String.valueOf(imgId);
												imgList.add(imgIdstr);	
											}
										}
								} catch (Exception e) {
									e.printStackTrace();
									DefaultLogger.debug(this, "Exception in  getImageDetails:"+e.getMessage());
								}finally {
									finalize(dbUtil,rs);
								}
								DefaultLogger.debug(this, "completed getImageDetails.");
								return imgList;					
				}



	
public Boolean getEntry_code(String entry_code) throws SearchDAOException{
	
	String queryStr = "select ENTRY_CODE from COMMON_CODE_CATEGORY_ENTRY where ENTRY_CODE= '"+entry_code+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getEntry_code sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getEntry_code:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}

public Boolean getCategory_code(String category_code) throws SearchDAOException{
	
	String queryStr = "select CATEGORY_CODE from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE= '"+category_code+"'";
	boolean flag = false;
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this, "inside getCategory_code sql:"+queryStr);

			try {
				dbUtil=new DBUtil();
				dbUtil.setSQL(queryStr);
				 rs = dbUtil.executeQuery();
				 if(rs!=null)
					{
						while(rs.next())
						{ 
							flag = true;
						}
					}
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getCategory_code:"+e.getMessage());
			}finally {
				finalize(dbUtil,rs);
			}
						
	return flag;
}



public List<OBCommonCodeEntry> getCommonCodeList(CommonCodeRestRequestDTO commonCodeRestRequestDTO)throws SearchDAOException {
	
	List<OBCommonCodeEntry> obcommoncodeentryList = new ArrayList<OBCommonCodeEntry>();
	ResultSet rs;
	try {
		dbUtil = new DBUtil();
		StringBuffer queryStr = new StringBuffer();
		
		queryStr.append("select CATEGORY_CODE,ENTRY_ID,ENTRY_NAME,ENTRY_CODE,ACTIVE_STATUS from COMMON_CODE_CATEGORY_ENTRY ");
		
		
		if(commonCodeRestRequestDTO.getBodyDetails().get(0).getCategoryCode() != null && !commonCodeRestRequestDTO.getBodyDetails().get(0).getCategoryCode().isEmpty())
		{
			queryStr.append(" where CATEGORY_CODE ='"+commonCodeRestRequestDTO.getBodyDetails().get(0).getCategoryCode()+"' ");
		}
		if(commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryId()!= null && !commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryId().isEmpty())
		{
			queryStr.append(" and ENTRY_ID ='"+commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryId()+"' ");
		}
		if(commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryName()!= null && !commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryName().isEmpty())
		{
			queryStr.append(" and ENTRY_NAME ='"+commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryName()+"' ");
		}
		if(commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryCode() != null && !commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryCode().isEmpty())
		{
			queryStr.append(" and ENTRY_CODE ='"+commonCodeRestRequestDTO.getBodyDetails().get(0).getEntryCode()+"' ");
		}
		/*if(null != commonCodeRestRequestDTO.getBodyDetails().get(0).getRefEntryCode() && !commonCodeRestRequestDTO.getBodyDetails().get(0).getRefEntryCode().trim().isEmpty())
		{
			queryStr.append(" and REF_ENTRY_CODE ='"+commonCodeRestRequestDTO.getBodyDetails().get(0).getRefEntryCode()+"' ");
		}*/
		if(commonCodeRestRequestDTO.getBodyDetails().get(0).getStatus() != null && !commonCodeRestRequestDTO.getBodyDetails().get(0).getStatus().isEmpty())
		{
			
			if(commonCodeRestRequestDTO.getBodyDetails().get(0).getStatus().equals("ACTIVE"))
			{
			queryStr.append(" and ACTIVE_STATUS ='1' ");
			}
			if(commonCodeRestRequestDTO.getBodyDetails().get(0).getStatus().equals("INACTIVE"))
			{
				queryStr.append(" and ACTIVE_STATUS ='0' ");
			}
		}
		dbUtil.setSQL(queryStr.toString());
		rs = dbUtil.executeQuery();
		while(rs.next()){
			OBCommonCodeEntry obcommoncodeentry = new OBCommonCodeEntry();
			
			obcommoncodeentry.setCategoryCode(rs.getString(1));
			obcommoncodeentry.setEntryId(rs.getLong(2));
			obcommoncodeentry.setEntryName(rs.getString(3));
			obcommoncodeentry.setEntryCode(rs.getString(4));
			obcommoncodeentry.setActiveStatusStr(rs.getString(5));
			obcommoncodeentryList.add(obcommoncodeentry);
		}
		rs.close();
		return obcommoncodeentryList;
	}catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getCommonCodeList ", ex);
	}catch (Exception ex) {
		throw new SearchDAOException("Exception in getCommonCodeList ", ex);
	}finally {
		try {
			dbUtil.close();
		}catch (SQLException ex) {
			System.out.println("SQLException message"+ex.getCause().getMessage());
			throw new SearchDAOException("SQLException in getCommonCodeList ", ex);
		}
	}
}
	
	public boolean checkVendorCount(String camId, String vendorName) {

		String sql = "select count(1) from SCI_LE_VENDOR_DETAILS vendor, SCI_LE_MAIN_PROFILE mainProfile "
				+ "where mainProfile.LMP_LE_ID= "
				+ "(select LLP_LE_ID from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID= ? ) "
				+ "and mainProfile.CMS_LE_MAIN_PROFILE_ID=vendor.CMS_LE_MAIN_PROFILE_ID and vendor.CMS_LE_VENDOR_NAME= ?";

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, camId);
			dbUtil.setString(2, vendorName);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkVendorCount " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}
	

	
	
	public boolean checkLiabBrancProdCurrCount(String module, String code) {

		String sql = "";

		if (module.equals("branch") || module.equals("liabBranch")) {
			sql = "select count(1) from CMS_FCCBRANCH_MASTER where ID = ? AND STATUS ='ACTIVE'";
		} else if (module.equals("product")) {
			sql = "select count(1) from CMS_PRODUCT_MASTER where ID = ? AND STATUS ='ACTIVE'";
		} else if (module.equals("currency")) {
			sql = "select count(1) from CMS_FOREX where CURRENCY_ISO_CODE = ?";
		}

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, code);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkLiabBrancProdCurrCount " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}
	
	
	public boolean checkSystemCount(String camId, String sysId) {

		String sql = "select count(1) from SCI_LE_OTHER_SYSTEM where CMS_LE_MAIN_PROFILE_ID="
				+ "(select CMS_LE_MAIN_PROFILE_ID from SCI_LE_MAIN_PROFILE where LMP_LE_ID= "
				+ "(select LLP_LE_ID from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID= ?))"
				+ "and CMS_LE_OTHER_SYS_CUST_ID= ?";

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, camId);
			dbUtil.setString(2, sysId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkSystemCount " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}
	
	
	public boolean checkMainLineCount(String camId) {

		String sql = "SELECT count(1) FROM sci_lsp_sys_xref WHERE CMS_LSP_SYS_XREF_ID IN "
				+ "(SELECT CMS_LSP_SYS_XREF_ID FROM sci_lsp_lmts_xref_map WHERE CMS_LSP_APPR_LMTS_ID IN "
				+ "(SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_APPR_LMTS WHERE CMS_LIMIT_PROFILE_ID= ? AND cms_limit_status ='ACTIVE' AND "
				+ "(LMT_TYPE_VALUE = 'No' OR LMT_TYPE_VALUE IS NULL) ) ) "
				+ "AND (action <> 'NEW' OR action IS NULL)";

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, camId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkMainLineCount " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}
	
	public boolean checkpartyCount(String camId, String partyname) {

		String sql = "select count(1) from (SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE ON "
				+ "SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID WHERE STATUS = 'ACTIVE' AND CMS_LE_SUB_PROFILE_ID in "
				+ "(SELECT CMS_LE_SUBLINE_PARTY_ID FROM SCI_LE_SUBLINE WHERE CMS_LE_MAIN_PROFILE_ID = "
				+ "( SELECT CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID="
				+ "(select LLP_LE_ID from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID=?)))) "
				+ "where CMS_LE_SUB_PROFILE_ID= ? ";
				

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, camId);
			dbUtil.setString(2, partyname);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkpartyCount " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}
	
	public boolean checkLiabilityID(String subProfileId, String liabilityId) {

		String sql = "select count(1) from ( SELECT CMS_LE_SYSTEM_NAME || ' - ' || CMS_LE_OTHER_SYS_CUST_ID as Liability_ID  FROM SCI_LE_OTHER_SYSTEM WHERE CMS_LE_MAIN_PROFILE_ID = (SELECT CMS_LE_MAIN_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE CMS_LE_SUB_PROFILE_ID=?)) where Liability_ID = ?";
				

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, subProfileId);
			dbUtil.setString(2, liabilityId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkLiabilityID " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;

	}

	
	public boolean checkFacNameGuar(String facNameGuar, String mainFacId ) {

		String sql = "SELECT count(1)  FROM SCI_LSP_APPR_LMTS   "
				+ "WHERE LMT_ID= ? AND CMS_LSP_APPR_LMTS_ID = ?"
				+ "AND CMS_LIMIT_STATUS = 'ACTIVE' "
				+ "AND LMT_TYPE_VALUE='No' "
				+ "AND FACILITY_NAME IS NOT NULL ORDER BY LMT_ID";

		int count = 0;

		DBUtil dbUtil = null;
		ResultSet rs = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setString(1, facNameGuar);
			dbUtil.setString(2, mainFacId);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(1)");
			}

		} catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in checkFacNameGuar " + e.getMessage());

		}
		if (count != 0)
			return true;
		else
			return false;
	}

		public String getCamExtensionDateMethod(String customerID) {
			String queryStr = "select to_char(LLP_EXTD_NEXT_RVW_DATE,'dd/MON/yyyy') as cam_extension_date from SCI_LSP_LMT_PROFILE where CMS_LSP_LMT_PROFILE_ID=?";
			System.out.println("LimitDAO.java=>getCamExtensionDateMethod()=>queryStr=>"+queryStr+"...customerID=>"+customerID);
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(customerID)},
					new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "";
				}
			});
		}
		
		public String getIDLApplicableFlagFacilityMaster(String facilityCode) {
			String queryStr = "SELECT IDL_APPLICABLE FROM CMS_FACILITY_NEW_MASTER WHERE NEW_FACILITY_CODE = ? ";
			System.out.println("LimitDAO.java=>getIDLApplicableFlagFacilityMaster()=>queryStr=>"+queryStr+"...facilityCode=>"+facilityCode);
			return (String) getJdbcTemplate().query(queryStr, new Object[]{(facilityCode)},
					new ResultSetExtractor() { 

				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "";
				}
			});
		}

	
	
	public List getFacDetailListRest(String facName,final String custID) throws SearchDAOException {
		String sql = "SELECT NEW_FACILITY_CODE, NEW_FACILITY_TYPE, NEW_FACILITY_SYSTEM, LINE_NUMBER, PURPOSE, LINE_CURRENCY, AVAIL_AND_OPTION_APPLICABLE , NEW_FACILITY_CATEGORY" + 
					 " FROM CMS_FACILITY_NEW_MASTER " + 
					 " WHERE STATUS='ACTIVE' and UPPER(trim(NEW_FACILITY_NAME))=UPPER(trim('"+facName.toUpperCase()+"'))";
		
		String sql1= "select RELATIONSHIP_MANAGER, CMS_APPR_OFFICER_GRADE  from SCI_LSP_LMT_PROFILE "+
					 "where CMS_CUSTOMER_ID='"+custID+"'";

		
		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setFacilityType(rs.getString("NEW_FACILITY_TYPE"));
				obLimit.setFacilitySystem(rs.getString("NEW_FACILITY_SYSTEM"));
				obLimit.setLineNo(rs.getString("LINE_NUMBER"));
				obLimit.setPurpose(rs.getString("PURPOSE"));
				obLimit.setFacilityCode(rs.getString("NEW_FACILITY_CODE"));
				obLimit.setCurrencyCode(rs.getString("LINE_CURRENCY"));
				obLimit.setFacilityCat(rs.getString("NEW_FACILITY_CATEGORY"));
				String riskType = rs.getString("AVAIL_AND_OPTION_APPLICABLE");
				if(riskType!=null)
					obLimit.setRiskType(riskType.trim());
				//String mainId = getMainID(custID);
				//obLimit.setFacilitySystemID(getSystemID(rs.getString("NEW_FACILITY_SYSTEM"),mainId ));

				return obLimit;
			}
		});
		
		List list = getJdbcTemplate().query(sql1, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLimit obLimit = new OBLimit();
				obLimit.setRelationShipManager(rs.getString("RELATIONSHIP_MANAGER"));
				obLimit.setGrade(rs.getString("CMS_APPR_OFFICER_GRADE"));
				return obLimit;
			}
		});
		
		resultList.addAll(list);

		return resultList;
	}
	
	public int checkFacilityReceiptPending(String  limitProfileID){
		int countPending =0;
		String sql="select count(limitProfileId) from Temp_LimitProfileId";
		System.out.println("inside  LimitDao checkFacilityReceiptPending  => sql=>"+sql);
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				countPending = rs.getInt(1);
				
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
		}	
		finally{
		finalize(dbUtil,rs);
		}
		return countPending;
	}
	
	public void insertLimitProfileID(final String  limitProfileID){
	//String updatelimitProfileID="update tempLimitProfileID set updatelimitProfileID=?";
	String updatelimitProfileID="INSERT INTO Temp_LimitProfileId (limitProfileId ) VALUES(?)";  
	DefaultLogger.debug(this, "inside  ReadStagingFacilityReceiptCommand ");

	try {
		int executeUpdate=getJdbcTemplate().update(updatelimitProfileID, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, limitProfileID);
				}
		});
		DefaultLogger.debug(this, "inserted limitProfileID");
		
	} catch (DBConnectionException e) {
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in   insertLimitProfileID:"+e.getMessage());
	} catch (NoSQLStatementException e) {
		e.printStackTrace();
		DefaultLogger.debug(this, "Exception in  insertLimitProfileID:"+e.getMessage());
	}

	DefaultLogger.debug(this, "completed insertLimitProfileID");
	}
	
	public String checkLimitProfileID() throws SearchDAOException {			
		String sql = "select limitProfileId from Temp_LimitProfileId ";
		try {
			return (String) getJdbcTemplate().query(sql,new ResultSetExtractor() {
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return rs.getString(1);
					}
					return "0";
				}
			});	
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}	
	}
	
	public void deleteLimitProfileID() throws SearchDAOException {			
		String deletelimitProfileID="DELETE FROM Temp_LimitProfileId";  
		DefaultLogger.debug(this, "inside  ApproveFacilityReceiptCommand ");

		try {
			int executeUpdate=getJdbcTemplate().update(deletelimitProfileID);
			DefaultLogger.debug(this, "deleted limitProfileID");
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in   deletelimitProfileID:"+e.getMessage());
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  deletelimitProfileID:"+e.getMessage());
		}

		DefaultLogger.debug(this, "completed deletelimitProfileID");
	}
	
	public void updateStageLineCheckerIdNewDetails(ICustomerSysXRef xref,final String loginId,final Date applicationDate){
		System.out.println("LimitDAO.java=>inside updateStageLineCheckerIdNewDetails..");
		final String xrefId = String.valueOf(xref.getXRefID());
		String updateStageLine="update cms_stage_lsp_sys_xref set CHECKER_ID_NEW = ? , UPDATED_ON = ? where cms_lsp_sys_xref_id=?";
		
		System.out.println("LimitDAO.java=>updateStageLineCheckerIdNewDetails SQL=>"+updateStageLine+"**** xrefId=>"+xrefId+"*****loginId=>"+loginId+"***new Timestamp(xref.getUpdatedOn().getTime())=>"+new Timestamp(applicationDate.getTime()));

		try {
			int executeUpdate=getJdbcTemplate().update(updateStageLine, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, loginId);
					ps.setTimestamp(2, new Timestamp(applicationDate.getTime()));
					ps.setString(3, xrefId);
					
				}
			});
			System.out.println("updated updateStageLineCheckerIdNewDetails");
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updateStageLineCheckerIdNewDetails:"+e);
			System.out.println("Exception in  updateStageLineCheckerIdNewDetails:"+e);
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updateStageLineCheckerIdNewDetails:"+e);
			System.out.println("Exception in  updateStageLineCheckerIdNewDetails:"+e);
		}
		DefaultLogger.debug(this, "completed updateStageLineCheckerIdNewDetails");
		System.out.println("completed updateStageLineCheckerIdNewDetails");
		
	}
	
	public void updateStageLineStatus(final String sid){
		String updateStageLine="update cms_stage_lsp_sys_xref set  status = ? where cms_lsp_sys_xref_id= ? ";
		
		DefaultLogger.debug(this, "inside updateStageLineDetails");

		try {
			int executeUpdate=getJdbcTemplate().update(updateStageLine, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, "SUCCESS");	
					ps.setString(2, sid);	
				}
			});
			DefaultLogger.debug(this, "updated updateStageLineStatus");
			
		} catch (DBConnectionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updateStageLineStatus:"+e.getMessage());
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in  updateStageLineStatus:"+e.getMessage());
		}
		DefaultLogger.debug(this, "completed updateStageLineStatus");
		
	}
	
	public void updateSCFJSLogTableForLine() {
		 System.out.println("Inside the updateSCFJSLogTableForLine to update the Error Status records except max records.");
	
			String query = "UPDATE CMS_JS_INTERFACE_LOG SET status ='Fail' where ID NOT IN " + 
					"					                          (SELECT DISTINCT JS.id FROM " + 
					"					 				 				 (SELECT MAX(requestdatetime) AS requestdatetime ,PARTYID,SERIAL_NO,LINE_NO FROM ( " + 
					"					 				 				 				select partyId,count(partyId),operation,requestdatetime,ID,SERIAL_NO,LINE_NO from cms_js_interface_log " + 
					"					 				 				 				where " + 
					"					 				 				 				modulename in ('RELEASE LINE','Release Line STP') " + 
					"					 				 				 				and status in  ('Error') " + 
					"					 				 				 				  AND PARTYID IS NOT NULL " + 
					"					 				 				 				group by partyId,operation ,requestdatetime,ID,SERIAL_NO,LINE_NO  ) " + 
					"					 				 				         GROUP BY PARTYID,SERIAL_NO,LINE_NO) A, " + 
					"					 				 				         cms_js_interface_log JS " + 
					"					 				 				         WHERE A.requestdatetime = JS.requestdatetime " + 
					"					 				 				         AND JS.status ='Error' " + 
					"                                   AND JS.modulename IN ('RELEASE LINE','Release Line STP') " + 
					"                                  AND JS.LINE_NO = A.LINE_NO " + 
					"                                  AND JS.SERIAL_NO= A.SERIAL_NO  ) " + 
					"					                          AND STATUS = 'Error' " + 
					"					                          AND modulename in ('RELEASE LINE','Release Line STP') ";

			System.out.println("Before updateSCFJSLogTableForLine to update the Error Status records except max records.SQL Query=>"+query);
			getJdbcTemplate().update(query);
			System.out.println("After updateSCFJSLogTableForLine to update the Error Status records except max records..");
			
		}
	
	public String getSeqNoForPanRequest()  {

		String fileSeqNo = "";
		DefaultLogger.debug(this,"getSeqNoForPanRequest......");
		int queryForInt = 0;
		String queryStr = "select PAN_VALIDATION_REQ_NO_SEQ.NEXTVAL from dual";
		
		
		ResultSet rs = null;
		try {

			queryForInt = getJdbcTemplate().queryForInt(queryStr);
			DefaultLogger.debug(this,"getSeqNoForPanRequest  seqno"+queryForInt);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		fileSeqNo = String.format("%06d", queryForInt);
		DefaultLogger.debug(this,"getSeqNoForPanRequest get seqno" +fileSeqNo);
		return fileSeqNo;
	}

	@Override
	public int insertLineToInterfaceLogBackupTable() {
		   String sql = "insert into cms_js_interface_log_audit (select * from cms_js_interface_log where id in (SELECT DISTINCT JS.id FROM (SELECT MAX(requestdatetime) AS requestdatetime , PARTYID, SERIAL_NO, LINE_NO FROM (SELECT partyId, COUNT(partyId), operation, requestdatetime, ID, SERIAL_NO, LINE_NO FROM cms_js_interface_log WHERE modulename IN ('RELEASE LINE','Release Line STP') AND status       IN ('Error') AND PARTYID      IS NOT NULL GROUP BY partyId, operation , requestdatetime, ID, SERIAL_NO, LINE_NO ) GROUP BY PARTYID, SERIAL_NO, LINE_NO ) A, cms_js_interface_log JS WHERE A.requestdatetime = JS.requestdatetime AND JS.status           ='Error' AND JS.modulename      IN ('RELEASE LINE','Release Line STP') AND JS.LINE_NO          = A.LINE_NO AND JS.SERIAL_NO        = A.SERIAL_NO )) ";	
			
				System.out.println("Select query is insertLineToInterfaceLogBackupTable=> "+sql);
			int	insetionCount =	getJdbcTemplate().update(sql);
			return insetionCount;
	}
	
	public void updateRecordsBasedOnTransId(String transId){
		 System.out.println("Inside the updateRecordsBasedOnTransId to set transId of extra 14 records to 0 based on transId.");
		 
		 String query = "select sys_id from (SELECT * FROM CMS_FILE_MAPPER WHERE TRANS_ID = '"+transId+"' ORDER BY sys_id DESC) where rownum<15"; 
		 
			System.out.println("sql query to fetch latest 14 records Query=>"+query);
               
           	try {
				List lmtIdQueryList=getJdbcTemplate().queryForList(query);
				for (int i = 0; i < lmtIdQueryList.size(); i++) {
					Map  map = (Map) lmtIdQueryList.get(i);
						String sys_id= map.get("SYS_ID").toString();
					String uddateQuery = "UPDATE CMS_FILE_MAPPER SET TRANS_ID  = '0' WHERE SYS_ID = '"+sys_id+"' ";
					System.out.println("Inside updateRecordsBasedOnTransId uddateQuery to set transId of extra 14 records to 0 based on transId. Query=>"+uddateQuery);
					getJdbcTemplate().update(uddateQuery);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in  getlmtId:"+e.getMessage());
			}
           	
			System.out.println("******14229*****After updating trans_id of extra 14 records to 0 based on transId and sys_id inside updateRecordsBasedOnTransId  ");
	}
	

}
