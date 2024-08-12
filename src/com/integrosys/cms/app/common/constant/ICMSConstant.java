/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/constant/ICMSConstant.java,v 1.356 2006/08/15 10:40:06 jzhai Exp $
 */
package com.integrosys.cms.app.common.constant;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * This interface contains commonly used constants in CMS.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.356 $
 * @since $Date: 2006/08/15 10:40:06 $ Tag: $Name: $
 */
public interface ICMSConstant {
	// ********* constants for CMS: Sequence Names ****************
	
	public static final String STATUS_TERMINATE = "T";

	public static final String SEQUENCE_FEE_DETAILS = "FEE_DETAILS_SEQ";

	public static final String SEQUENCE_TAT_ENTRY = "TAT_ENTRY_SEQ";

	public static final String SEQUENCE_LIMIT_PROFILE = "LIMIT_PROFILE_SEQ";

	public static final String SEQUENCE_LIMIT = "LIMIT_SEQ";

	public static final String SEQUENCE_LIMIT_REPORT = "LIMIT_SEQ_REPORT";

	public static final String SEQUENCE_COBORROWER_LIMIT = "COBORROWER_LIMIT_SEQ";

	public static final String SEQUENCE_LIMIT_X_REF = "LIMIT_X_REF_SEQ";
	
	public static final String SEQUENCE_LIMIT_COVENANT = "LIMIT_COVENANT_SEQ";

	public static final String SEQUENCE_CUSTOMER = "CUSTOMER_SEQ";

	public static final String SEQUENCE_CONTACT = "CONTACT_SEQ";

	public static final String SEQUENCE_LEGAL_ENTITY = "LEGAL_ENTITY_SEQ";
	
	public static final String SEQUENCE_CO_BORROWER = "SCI_LE_CO_BORROWER_SEQ";
	
	public static final String SEQUENCE_FACILITY_CO_BORROWER = "SCI_FAC_CO_BORROWER_SEQ";

	public static final String SEQUENCE_CREDIT_GRADE = "CREDIT_GRADE_SEQ";

	public static final String SEQUENCE_ISIC_CODE = "ISIC_CODE_SEQ";

	public static final String SEQUENCE_CREDIT_STATUS = "CREDIT_STATUS_SEQ";

	public static final String SEQUENCE_KYC = "KYC_SEQ";

	public static final String SEQUENCE_CUSTOMER_X_REF = "CUSTOMER_X_REF_SEQ";

	public static final String SEQUENCE_CUST_OFFICIAL_ADDR = "LSP_OFF_ADDR_SEQ";

	public static final String SEQUENCE_COMMODITY_PRICE = "COMMODITY_PRICE_SEQ";

	public static final String SEQUENCE_COMMODITY_DEAL = "COMMODITY_DEAL_SEQ";

	public static final String SEQUENCE_COMMODITY_DEAL_NO_SPECIFIC = "COMMODITY_DEAL_NO_SPECIFIC_SEQ";

	public static final String SEQUENCE_COMMODITY_DEAL_NO_POOL = "COMMODITY_DEAL_NO_POOL_SEQ";

	public static final String SEQUENCE_COMMODITY_FIN_DOC = "COMMODITY_FIN_DOC_SEQ";

	public static final String SEQUENCE_COMMODITY_SETTLEMENT = "COMMODITY_SETTLEMENT_SEQ";

	public static final String SEQUENCE_COMMODITY_RELEASE = "COMMODITY_RELEASE_SEQ";

	public static final String SEQUENCE_WAREHOUSE_RECEIPT = "WAREHOUSE_RECEIPT_SEQ";

	public static final String SEQUENCE_SETTLEMENT_WAREHOUSE = "SETTLEMENT_WAREHOUSE_SEQ";

	public static final String SEQUENCE_PURCHASE_SALE = "PURCHASE_SALE_SEQ";

	public static final String SEQUENCE_HEDGE_PRICE_EXTENSION = "HEDGE_PRICE_EXTENSION_SEQ";

	public static final String SEQUENCE_COMMODITY_DEAL_CASH = "COMMODITY_DEAL_CASH_SEQ";

	public static final String SEQUENCE_COMMODITY_TITLE_DOC = "COMMODITY_TITLE_DOC_SEQ";

	public static final String SEQUENCE_COMMODITY_CONTRACT = "COMMODITY_CONTR_SEQ";

	public static final String SEQUENCE_COMMODITY_CONTRACT_STAGING = "COMMODITY_CONTR_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_APPROVED_COMMODITY_TYPE = "APPR_CMDT_TYPE_SEQ";

	public static final String SEQUENCE_COMMODITY_APPROVED_COMMODITY_TYPE_STAGING = "APPR_CMDT_TYPE_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_HEDGING_CONTRACT = "HEDGING_CONTR_SEQ";

	public static final String SEQUENCE_COMMODITY_HEDGING_CONTRACT_STAGING = "HEDGING_CONTR_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_LOAN_AGENCY = "LOAN_AGENCY_SEQ";

	public static final String SEQUENCE_COMMODITY_LOAN_AGENCY_STAGING = "LOAN_AGENCY_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_LOANLIMIT_AGENCY = "LOAN_LIMIT_SEQ";

	public static final String SEQUENCE_COMMODITY_BORROWER = "LOAN_BORROWER_SEQ";

	public static final String SEQUENCE_COMMODITY_BORROWER_STAGING = "LOAN_BORROWER_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_GUARANTOR = "LOAN_GUARANTOR_SEQ";

	public static final String SEQUENCE_COMMODITY_GUARANTOR_STAGING = "LOAN_GUARANTOR_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_PARTICIPANT = "LOAN_PARTICIPANT_SEQ";

	public static final String SEQUENCE_COMMODITY_PARTICIPANT_STAGING = "LOAN_PARTICIPANT_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_SUBLIMIT = "LOAN_SUBLIMIT_SEQ";

	public static final String SEQUENCE_COMMODITY_SUBLIMIT_STAGING = "LOAN_SUBLIMIT_STAGING_SEQ";

	public static final String SEQUENCE_COMMODITY_LOAN_SCHEDULE = "LOAN_SCHEDULE_SEQ";

	public static final String SEQUENCE_COMMODITY_LOAN_SCHEDULE_STAGING = "LOAN_SCHEDULE_STAGING_SEQ";

	public static final String SEQUENCE_PRECOND = "PRECOND_SEQ";

	public static final String SEQUENCE_COLLATERAL = "COLLATERAL_SEQ";

	public static final String SEQUENCE_PLEDGOR = "PLEDGOR_SEQ";

	public static final String SEQUENCE_PLEDGOR_REPORT = "SCI_PLG_RPT_SEQ";

	public static final String SEQUENCE_PLEDGOR_CREDIT_GRADE = "PLEDGOR_CREDIT_GRADE_SEQ";

	public static final String SEQUENCE_PLEDGOR_MAP = "COL_PLEDGOR_MAP_SEQ";

	public static final String SEQUENCE_VALUATION = "VALUATION_SEQ";

	public static final String SEQUENCE_CDS = "CDS_SEQ";

	public static final String SEQUENCE_ASSET_PDC = "ASSET_PDC_SEQ";

	public static final String SEQUENCE_ASSET_GC_STOCK = "CMS_ASST_GC_STOCK_SEQ";
	
	public static final String SEQUENCE_LEAD_BANK_STOCK = "CMS_LEAD_BANK_STOCK_SEQ";

	public static final String SEQUENCE_INS_STOCK_MAP = "CMS_ASST_GC_INSR_STK_MAP_SEQ";

	public static final String SEQUENCE_INS_FAO_MAP = "CMS_ASST_GC_INSR_FAO_MAP_SEQ";

	public static final String SEQUENCE_ASSET_INSURANCE = "CMS_ASST_GC_INSR_SEQ";

	public static final String SEQUENCE_ASSET_GC_FAO = "CMS_ASST_GC_FAO_SEQ";

	public static final String SEQUENCE_ASSET_GC_DEBTOR = "CMS_ASST_GC_DEBTOR_SEQ";

	public static final String SEQUENCE_CASH_DEPOSIT = "CASH_DEPOSIT_SEQ";

	public static final String SEQUENCE_MARKETABLE_EQUITY = "MARKETABLE_EQUITY_SEQ";

	public static final String SEQUENCE_MARKETABLE_COMMODITY = "MARKETABLE_COMMODITY_SEQ";

	public static final String SEQUENCE_MARKETABLE_EQUITY_DETAIL = "MARKETABLE_EQUITY_DETAIL_SEQ";

	public static final String SEQUENCE_INSURANCE_POLICY = "INSURANCE_POLICY_SEQ";
	
	public static final String SEQUENCE_SECURITY_COVERAGE = "CMS_SECURITY_COVERAGE_SEQ";
	
	public static final String SEQUENCE_ADD_FAC_DOC_DET = "CMS_ADD_DOC_FAC_DET_SEQ";

	public static final String SEQUENCE_COL_LIMIT_CHARGE = "COL_LIMIT_CHARGE_SEQ";

	public static final String SEQUENCE_COL_LIMIT_MAP = "COL_LIMIT_MAP_SEQ";

	public static final String SEQUENCE_COL_PARAMETER = "SECURITY_PARAMETER_SEQ";

	public static final String SEQUENCE_COL_SUBTYPE = "SECURITY_SUBTYPE_SEQ";

	public static final String SEQUENCE_COL_ASSETLIFE = "SECURITY_ASSETLIFE_SEQ";

	public static final String SEQUENCE_LIMIT_CHARGE_MAP = "LIMIT_CHARGE_MAP_SEQ";

	public static final String SEQUENCE_TRX_HISTORY = "TRX_HISTORY_SEQ";

	public static final String SEQUENCE_TRX = "TRX_SEQ";

	public static final String SEQUENCE_CUSTODIAN_DOC = "CMS_CUSTODIAN_DOC_SEQ";

	public static final String SEQUENCE_CUSTODIAN_DOC_ITEM = "CMS_CUSTODIAN_DOC_ITEM_SEQ";

	public static final String SEQUENCE_DOCUMENT_ITEM = "DOCUMENT_ITEM_SEQ";

	public static final String SEQUENCE_DOCUMENT_ITEM_CODE = "DOCUMENT_ITEM_CODE_SEQ";

	public static final String SEQUENCE_DOC_DYNAMIC_PROP = "DOCUMENT_DYNAMIC_PROPERTY_SEQ";

	public static final String SEQUENCE_TEMPLATE = "TEMPLATE_SEQ";

	public static final String SEQUENCE_TEMPLATE_ITEM = "TEMPLATE_ITEM_SEQ";

	public static final String SEQUENCE_DOC_APP_ITEM = "CMS_DOC_LOAN_APP_TYPE_SEQ ";

	public static final String SEQUENCE_STAGE_DOC_APP_ITEM = "STAGE_CMS_DOC_LOANAPPTYPE_SEQ";

	public static final String SEQUENCE_TEMPLATE_ITEM_CODE = "TEMPLATE_ITEM_CODE_SEQ";

	public static final String SEQUENCE_CHECKLIST = "CHECKLIST_SEQ";

	public static final String SEQUENCE_CHECKLIST_ITEM = "CHECKLIST_ITEM_SEQ";

	// for cr-17
	public static final String SEQUENCE_CMS_CHECKLIST_ITEM_SHARE = "CMS_CHECKLIST_ITEM_SHARE_SEQ";

	public static final String SEQUENCE_STAGING_CHECKLIST_ITEM_SHARE = "STG_CHECKLIST_ITEM_SHARE_SEQ";

	public static final String SEQUENCE_CHECKLIST_ITEM_REF = "CHECKLIST_ITEM_REF_SEQ";

	public static final String SEQUENCE_RECURRENT_CHECKLIST = "RECURRENT_CHECKLIST_SEQ";

	public static final String SEQUENCE_RECURRENT_CHECKLIST_ITEM = "RECURRENT_CHECKLIST_ITEM_SEQ";

	public static final String SEQUENCE_RECURRENT_CHECKLIST_SUB_ITEM = "REC_CHECKLIST_SUB_ITEM_SEQ";

	public static final String SEQUENCE_CONVENANT = "CONVENANT_SEQ";

	public static final String SEQUENCE_CONVENANT_SUB_ITEM = "CONVENANT_SUB_ITEM_SEQ";

	public static final String SEQUENCE_CC_CERTIFICATE = "CC_CERTIFICATE_SEQ";

	public static final String SEQUENCE_CC_CERTIFICATE_ITEM = "CC_CERTIFICATE_ITEM_SEQ";

	public static final String SEQUENCE_SC_CERTIFICATE = "SC_CERTIFICATE_SEQ";

	public static final String SEQUENCE_SC_CERTIFICATE_ITEM = "SC_CERTIFICATE_ITEM_SEQ";

	public static final String SEQUENCE_PARTIAL_SC_CERTIFICATE = "PSC_CERTIFICATE_SEQ";

	public static final String SEQUENCE_PARTIAL_SC_CERTIFICATE_ITEM = "PSC_CERTIFICATE_ITEM_SEQ";

	public static final String SEQUENCE_DDN = "DDN_SEQ";

	public static final String SEQUENCE_DDN_ITEM = "DDN_ITEM_SEQ";

	public static final String SEQUENCE_COLLATERAL_TASK = "COLLATERAL_TASK_SEQ";

	public static final String SEQUENCE_CC_TASK = "CC_TASK_SEQ";

	public static final String SEQUENCE_WAIVER_REQUEST = "WAIVER_REQUEST_SEQ";

	public static final String SEQUENCE_WAIVER_REQUEST_ITEM = "WAIVER_REQUEST_ITEM_SEQ";

	public static final String SEQUENCE_DEFERRAL_REQUEST = "DEFER_REQUEST_SEQ";

	public static final String SEQUENCE_DEFERRAL_REQUEST_ITEM = "DEFER_REQUEST_ITEM_SEQ";

	public static final String SEQUENCE_CCC_REF = "CCC_CERT_REF_SEQ";

	public static final String SEQUENCE_SCC_REF = "SCC_CERT_REF_SEQ";

	public static final String SEQUENCE_PSCC_REF = "PSCC_CERT_REF_SEQ";

	public static final String SEQUENCE_DDN_REF = "DDN_CERT_REF_SEQ";

	public static final String SEQUENCE_DIARY_ITEM = "DIARY_ITEM_SEQ";

	public static final String SEQUENCE_STAGING_DOCUMENT_ITEM = "STG_DOCUMENT_ITEM_SEQ";

	public static final String SEQUENCE_STAGING_DOC_DYNAMIC_PROP = "STG_DOCUMENT_DYNAMIC_PROP_SEQ";

	public static final String SEQUENCE_STAGING_TEMPLATE = "STG_TEMPLATE_SEQ";

	public static final String SEQUENCE_STAGING_TEMPLATE_ITEM = "STG_TEMPLATE_ITEM_SEQ";

	public static final String SEQUENCE_STAGING_CHECKLIST = "STG_CHECKLIST_SEQ";

	public static final String SEQUENCE_STAGING_CHECKLIST_ITEM = "STG_CHECKLIST_ITEM_SEQ";

	public static final String SEQUENCE_CUSTODIAN_AUTHZ = "CUSTODIAN_AUTHORIZER_SEQ";

	public static final String SEQUENCE_FEED_FEED_EXCEPTION = "CMS_FEED_EXCEPTION_SEQ";

	public static final String SEQUENCE_FEED_FEED_GROUP = "CMS_FEED_GROUP_SEQ";

	public static final String SEQUENCE_FEED_FOREX = "CMS_FOREX_SEQ";

	public static final String SEQUENCE_FEED_FOREX_HISTORY = "CMS_FOREX_HISTORY_SEQ";

	public static final String SEQUENCE_FEED_PRICE_FEED = "CMS_PRICE_FEED_SEQ";

	public static final String SEQUENCE_FEED_PRICE_FEED_HISTORY = "CMS_PRICE_FEED_HISTORY_SEQ";

	public static final String SEQUENCE_FEED_PROPERTY_INDEX = "CMS_PROPERTY_INDEX_SEQ";

	public static final String SEQUENCE_REPORT_REQUEST = "REPORT_REQUEST_SEQ";

	public static final String SEQUENCE_COUNTRY = "SCI_COUNTRY_SEQ";

	public static final String SEQUENCE_GROUP_CREDIT_GRADE = "SCI_GRP_CREDIT_GRADE_SEQ";

	public static final String SEQUENCE_GROUP_LE_MAP = "SCI_GRP_LE_MAP_SEQ";

	public static final String SEQUENCE_GROUP_PROFILE = "SCI_GRP_PROFILE_SEQ";

	public static final String SEQUENCE_GROUP_MAP = "SCI_GRP_MAP_SEQ";

	public static final String SEQUENCE_GROUP_LE_LE_MAP = "SCI_GRP_LE_LE_MAP_SEQ";

	public static final String SEQUENCE_CC_DOC_LOCATION = "CC_DOC_SEQ";

	public static final String SEQUENCE_ASST_GC_DEBTOR = "DEBTOR_SEQ";

	public static final String SEQUENCE_CMS_REV_CHECKLIST_ITEM = "CMS_REV_CHECKLIST_ITEM_SEQ";

	public static final String SEQUENCE_CMD_SUBLIMIT_SEQ = "CMD_SUBLIMIT_SEQ";

	public static final String SEQUENCE_CMD_SUBLIMIT_STAGING_SEQ = "CMD_SUBLIMIT_STAGING_SEQ";

	public static final String SEQUENCE_JOINT_BORROWER = "SCI_LSP_JOINT_BORROWER_SEQ";

	public static final String SEQUENCE_EAI_HEADER_REF_NO = "EAI_MESSAGE_REF_NO_SEQ";

	public static final String SEQUENCE_STP_HEADER_REF_NO = "STP_MESSAGE_REF_NO_SEQ";

	// Credit Risk Param - Policy Cap Module
	public static final String SEQUENCE_POLICY_CAP_SEQ = "POLICY_CAP_SEQ";

	public static final String SEQUENCE_POLICY_CAP_GROUP_SEQ = "POLICY_CAP_GRP_SEQ";

	public static final String SEQUENCE_INT_RATE = "INT_RATE_SEQ";

	public static final String SEQUENCE_INT_RATE_REF = "INT_RATE_REF_SEQ";

	// Contract Financing Module
	public static final String PRODUCT_DESCRIPTION = "Contract Financing";

	public static final String SEQUENCE_CONTRACT_FINANCE = "CMS_CONTRACT_FINANCE_SEQ";

	public static final String SEQUENCE_CF_FACILITY_TYPE = "CMS_CF_FACILITY_TYPE_SEQ";

	public static final String SEQUENCE_CF_ADVANCE = "CMS_CF_ADVANCE_SEQ";

	public static final String SEQUENCE_CF_PAYMENT = "CMS_CF_PAYMENT_SEQ";

	public static final String SEQUENCE_CF_TNC = "CMS_CF_TNC_SEQ";

	public static final String SEQUENCE_CF_FDR = "CMS_CF_FDR_SEQ";

	// Bridging Loan Module
	public static final String BL_PRODUCT_DESCRIPTION = "Bridging Loan";

	public static final String SEQUENCE_BRIDGING_LOAN = "CMS_BRIDGING_LOAN_SEQ";

	public static final String SEQUENCE_BL_PROPERTY_TYPE = "CMS_BL_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_BL_PROJECT_SCHEDULE = "CMS_BL_PROJECT_SCHEDULE_SEQ";

	public static final String SEQUENCE_BL_DEV_DOC = "CMS_BL_DEV_DOC_SEQ";

	public static final String SEQUENCE_BL_DISBURSEMENT = "CMS_BL_DISBURSE_SEQ";

	public static final String SEQUENCE_BL_DISBURSE_DETAIL = "CMS_BL_DISBURSE_DETAIL_SEQ";

	public static final String SEQUENCE_BL_SETTLEMENT = "CMS_BL_SETTLEMENT_SEQ";

	public static final String SEQUENCE_BL_BUILDUP = "CMS_BL_BUILDUP_SEQ";

	public static final String SEQUENCE_BL_SALES_PROCEEDS = "CMS_BL_SALES_PROCEEDS_SEQ";

	public static final String SEQUENCE_BL_FDR = "CMS_BL_FDR_SEQ";

	public static final String SEQUENCE_BATCH_CUSTOMER_UPDATE = "CMS_BATCH_CIF_UPDATE_SEQ";

	public static final String SEQUENCE_BATCH_CUSTOMER_FUSION = "CMS_BATCH_CIF_FUSION_SEQ";

	// SI AA - charge Detail
	public static final String SEQUENCE_CHARGE_DETAIL = "CMS_CHARGE_DETAIL_SEQ";

	// public static final String SEQUENCE_LIMIT_CHARGE_MAP =
	// "CMS_LIMIT_CHARGE_MAP_SEQ" ;
	public static final String SEQUENCE_LIMIT_SECURITY_MAP = "CMS_LIMIT_SECURITY_MAP_SEQ";

	public static final String SEQUENCE_CHARGE_DETAIL_STAGING = "CMS_CHARGE_DETAIL_STG_SEQ";

	public static final String SEQUENCE_LIMIT_CHARGE_MAP_STAGING = "CMS_LIMIT_CHARGE_MAP_STG_SEQ";

	public static final String SEQUENCE_LIMIT_SECURITY_MAP_STAGING = "CMS_LIMIT_SECURITY_MAP_STG_SEQ";

	public static final String SEQUENCE_TAT_DOC = "CMS_TAT_DOC_SEQ";

	public static final String SEQUENCE_TAT_DOC_DRAFT = "CMS_TAT_DOC_DRAFT_SEQ";

	// ************** Constant for custodian
	// **********************************************/
	public static final String ACTION_MAKER_CREATE_CUSTODIAN_DOC = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_CUSTODIAN_DOC = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_CUSTODIAN_DOC = "MAKER_SAVE";

	public static final String ACTION_MAKER_CLOSE_CREATE_CUSTODIAN_DOC = "MAKER_CLOSE_CREATE";

	public static final String ACTION_MAKER_CLOSE_UPDATE_CUSTODIAN_DOC = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_CREATE_CUSTODIAN_DOC = "CHECKER_APPROVE_CREATE";

	public static final String ACTION_CHECKER_REJECT_CREATE_CUSTODIAN_DOC = "CHECKER_REJECT_CREATE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CUSTODIAN_DOC = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_REJECT_UPDATE_CUSTODIAN_DOC = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_REQUIRED = "REQUIRED_ACTION";

	// ************** Constant for the Common Data Category Code
	// **************************/
	public static final String CATEGORY_COUNTRY_LAW_MAP = "COUNTRY_LAW_MAP";

	public static final String CATEGORY_ENTRY_ACTIVE_STATUS = "1";

	public static final String CATEGORY_CODE_BKGLOC = "40";

	public static final String CATEGORY_CODE_ORG_GROUP = "CENTRE";

	public static final String CATEGORY_DOC_MONITOR_TYPE = "DOC_MONITOR_TYPE";

	public static final String CATEGORY_LEGAL_FIRM = "LEGAL_FIRM";

	public static final String CATEGORY_INSURANCE_RECEIPT = "INS_RCPT_MAP";

	public static final String CATEGORY_LIQ_EXPENSE_TYPE = "LIQUIDATION_EXPENSE_TYPE";

	public static final String CATEGORY_LIQ_RECOVERY_TYPE = "LIQUIDATION_RECOVERY_TYPE";

	// *********** Common constants for CMS *********************

	/** Trx constant: State Instance */
	public static final String STATE_REQUIRED = "REQUIRED_STATE";

	public static final String INSTANCE_CMS = "CMS";

	/** Trx State: ND (Not Defined) */
	public static final String STATE_ND = "ND";

	/** Trx State: DELETED */
	public static final String STATE_DELETED = "DELETED";

	/** Trx State: REJECTED */
	public static final String STATE_REJECTED = "REJECTED";

	/** Trx State: OFFICER_REJECTED */
	public static final String STATE_OFFICER_REJECTED = "OFFICER_REJECTED";

	/** Trx State: ACTIVE */
	public static final String STATE_ACTIVE = "ACTIVE";

	/** Trx State: PENDING UPDATE */
	public static final String STATE_PENDING_UPDATE = "PENDING_UPDATE";

	/** Trx State: UPDATED */
	public static final String STATE_UPDATED = "UPDATED";

	/** Trx State: PENDING_DELETE */
	public static final String STATE_PENDING_DELETE = "PENDING_DELETE";

	/** Trx State: DRAFT */
	public static final String STATE_DRAFT = "DRAFT";

	/** Trx State: PENDING_CREATE */
	public static final String STATE_PENDING_CREATE = "PENDING_CREATE";

	public static final String PENDING_UPDATE = "PENDING_UPDATE";

	/** Trx State: PENDING_VERIFY */
	public static final String STATE_PENDING_AUTH = "PENDING_AUTH";

	public static final String STATE_PENDING_OFFICE = "PENDING_OFFICE";

	public static final String STATE_PENDING_VERIFY = "PENDING_VERIFY";

	/** Trx State: PENDING_REJECT */
	public static final String STATE_PENDING_REJECT = "PENDING_REJECT";

	/** Trx State: NEW */
	public static final String STATE_NEW = "NEW";

	/** Trx State: CANCELLED */
	public static final String STATE_CANCELLED = "CANCELLED";

	public static final String STATE_PENDING_CLOSE = "PENDING_CLOSE";

	public static final String STATE_CLOSED = "CLOSED";

	public static final String STATE_OBSOLETE = "OBSOLETE";

	/** ---- Commodity Deal Trx States --- */
	public static final String STATE_PENDING_CREATE_VERIFY = "PENDING_CREATE_VERIFY";

	public static final String STATE_PENDING_UPDATE_VERIFY = "PENDING_UPDATE_VERIFY";

	public static final String STATE_PENDING_CLOSE_VERIFY = "PENDING_CLOSE_VERIFY";

	public static final String STATE_REJECT_CLOSE = "REJECT_CLOSE";

	/** ---- Custodian Trx States --- */
	public static final String STATE_RECEIVED = "RECEIVED";

	public static final String STATE_AWAITING = "AWAITING";

	public static final String STATE_PENDING_RECEIVED = "PENDING_RECEIVE";

	public static final String STATE_PENDING_LODGE = "PENDING_LODGE";

	public static final String STATE_PENDING_RELODGE = "PENDING_RELODGE";

	public static final String STATE_LODGED = "LODGED";

	public static final String STATE_RELODGED = "RELODGED";

	public static final String STATE_PENDING_AUTHZ_TEMP_UPLIFT = "PENDING_ALLOW_TEMP_UPLIFT";

	public static final String STATE_PENDING_AUTHZ_PERM_UPLIFT = "PENDING_ALLOW_PERM_UPLIFT";

	public static final String STATE_PENDING_AUTHZ_RELODGE = "PENDING_ALLOW_RELODGE";

	public static final String STATE_AUTHZ_TEMP_UPLIFTED = "ALLOW_TEMP_UPLIFT";

	public static final String STATE_AUTHZ_PERM_UPLIFTED = "ALLOW_PERM_UPLIFT";

	public static final String STATE_AUTHZ_RELODGED = "ALLOW_RELODGE";

	public static final String STATE_PENDING_TEMP_UPLIFT = "PENDING_TEMP_UPLIFT";

	public static final String STATE_ALLOW_PENDING_TEMP_UPLIFT = "ALLOW_PENDING_TEMP_UPLIFT";

	public static final String STATE_TEMP_UPLIFTED = "TEMP_UPLIFTED";

	public static final String STATE_ALLOW_PENDING_PERM_UPLIFT = "ALLOW_PENDING_PERM_UPLIFT";

	public static final String STATE_PENDING_PERM_UPLIFT = "PENDING_PERM_UPLIFT";

	public static final String STATE_PERM_UPLIFTED = "PERM_UPLIFTED";

	public static final String STATE_LODGED_REVERSAL = "PENDING_ALLOW_LODGED_REVERSAL";

	public static final String STATE_TEMP_UPLIFT_REVERSAL = "PENDING_ALLOW_TEMP_UPLIFT_REVERSAL";

	public static final String STATE_PERM_UPLIFT_REVERSAL = "PENDING_ALLOW_PERM_UPLIFT_REVERSAL";

	public static final String STATE_RELODGE_REVERSAL = "PENDING_ALLOW_RELODGE_REVERSAL";

	public static final String STATE_REJECTED_CREATE = "REJECTED_CREATE";

	public static final String STATE_REJECTED_UPDATE = "REJECTED_UPDATE";

	public static final String STATE_REJECTED_DELETE = "REJECTED_DELETE";

	// [Start] - Add new custodian status, customize based on Alliance bank
	public static final String STATE_ITEM_ALLOW_PREFIX = "ALLOW_";
	public static final String STATE_AUTHZ_LODGE = "ALLOW_LODGE"; // related
																	// status
	public static final String STATE_CUST_PENDING_lODGE_REVERSAL = "PENDING_LODGE_REVERSAL";
	public static final String STATE_CUST_LODGED_REVERSAL = "LODGED_REVERSAL";
	public static final String STATE_CUST_PENDING_TEMP_UPLIFT_REVERSAL = "PENDING_TEMP_UPLIFT_REVERSAL";
	public static final String STATE_CUST_PENDING_PERM_UPLIFT_REVERSAL = "PENDING_PERM_UPLIFT_REVERSAL";
	public static final String STATE_CUST_PENDING_RELODGE_REVERSAL = "PENDING_RELODGE_REVERSAL";
	// [End]

/*	public static final List MULTI_LEVEL_APPROVAL_STATES = Arrays
			.asList(new String[] { ICMSConstant.STATE_PENDING_CREATE_VERIFY,
					ICMSConstant.STATE_PENDING_UPDATE_VERIFY,
					ICMSConstant.STATE_PENDING_CLOSE_VERIFY,
					ICMSConstant.STATE_PENDING_OFFICE,
					ICMSConstant.STATE_PENDING_VERIFY,
					ICMSConstant.STATE_PENDING_AUTH, });*/

	/** String representation for boolean true value. */
	public static final String TRUE_VALUE = "Y";

	/** String representation for boolean false value. */
	public static final String FALSE_VALUE = "N";

	public static final String NOT_AVAILABLE_VALUE = "O";

	public static final String NOT_APPLICABLE_VALUE = "NA";

	public static final long LONG_INVALID_VALUE = -999999999l;

	public static final long LONG_MIN_VALUE = -999999999l;

	public static final double DOUBLE_INVALID_VALUE = -1;

	public static final float FLOAT_INVALID_VALUE = -1;

	public static final int INT_INVALID_VALUE = -1;

	public static final int SHORT_INVALID_VALUE = -1;

	public static final int PERCENTAGE_SCALE = 2;

	public static final String CURRENCYCODE_INVALID_VALUE = "###";

	public static final String CTRY_CODE_INVALID_VALUE = "--";

	public static final String ORG_CODE_INVALID_VALUE = "--";

	public static final String SEGMENT_INVALID_VALUE = "--";

	public static final double DOUBLE_PERCENT = 0.01;

	public static final BigDecimal BIGDECIMAL_ONE_HUNDRED = new BigDecimal(100d);

	public static final String CHARGE_TYPE_GENERAL = "G";

	public static final String CHARGE_TYPE_SPECIFIC = "S";

	public static final String CATEGORY_TIME_FREQ = "TIME_FREQ";

	public static final String TIME_FREQ_DAY = "1";

	public static final String TIME_FREQ_WEEK = "2";

	public static final String TIME_FREQ_MONTH = "3";

	public static final String TIME_FREQ_YEAR = "4";

	public static final String SIMPLE_SIGN_PLUS = "pl";

	public static final String SIMPLE_SIGN_MINUS = "mi";

	public static final String SIMPLE_SIGN_PLUS_OR_MINUS = "pm";

	public static final int BFL_TAT_MAX_EXTENDED_DAY = 90;

	public static final long DEFAULT_CUST_REF = 1l;

	public static final String CATEGORY_OF_PBR_PBT = "PBR_PBT_IND";

	public static final String CATEGORY_OF_GOODS_STATUS = "GOODS_STATUS";

	/*
	 * -------------------------------------- CUSTODIAN TERITORY
	 * ------------------------------------------------
	 */

	/** State Instance Name for Custodian. */
	public static final String INSTANCE_CUSTODIAN = "CUSTODIAN";

	public static final String TRX_TYPE_CC_CUSTODIAN = "CC_CT";

	public static final String TRX_TYPE_COL_CUSTODIAN = "COL_CT";

	/** ---Custodian Transaction Actions--- */
	public static final String ACTION_CREATE_CUSTODIAN_DOC = "CREATE";

	public static final String ACTION_LODGE_CUSTODIAN_DOC = "LODGE";

	public static final String ACTION_APPROVE_LODGE_CUSTODIAN_DOC = "APPROVE_LODGE";

	public static final String ACTION_TEMP_UPLIFT_AUTHZ_CUSTODIAN_DOC = "TEMP_UPLIFT_AUTHZ";

	public static final String ACTION_PERM_UPLIFT_AUTHZ_CUSTODIAN_DOC = "PERM_UPLIFT_AUTHZ";

	public static final String ACTION_APPROVE_AUTHZ_TEMP_UPLIFT_CUSTODIAN_DOC = "APPROVE_TEMP_UPLIFT_AUTHZ";

	public static final String ACTION_APPROVE_AUTHZ_PERM_UPLIFT_CUSTODIAN_DOC = "APPROVE_PERM_UPLIFT_AUTHZ";

	public static final String ACTION_APPROVE_AUTHZ_RELODGE_CUSTODIAN_DOC = "APPROVE_RELODGE_AUTHZ";

	public static final String ACTION_TEMP_UPLIFT_CUSTODIAN_DOC = "TEMP_UPLIFT";

	public static final String ACTION_APPROVE_TEMP_UPLIFT_CUSTODIAN_DOC = "APPROVE_TEMP_UPLIFT";

	public static final String ACTION_PERM_UPLIFT_CUSTODIAN_DOC = "PERM_UPLIFT";

	public static final String ACTION_APPROVE_PERM_UPLIFT_CUSTODIAN_DOC = "APPROVE_PERM_UPLIFT";

	public static final String ACTION_RELODGE_CUSTODIAN_DOC = "RELODGE";

	public static final String ACTION_APPROVE_RELODGE_CUSTODIAN_DOC = "APPROVE_RELODGE";

	public static final String ACTION_REJECT_CUSTODIAN_DOC = "REJECT";

	public static final String ACTION_CNCL_REJECT_LODGE_CUSTODIAN_DOC = "CNCL_REJECT_LODGE";

	public static final String ACTION_CNCL_REJECT_RELODGE_CUSTODIAN_DOC = "CNCL_REJECT_RELODGE";

	public static final String ACTION_CNCL_REJECT_TEMP_UPLIFT_AUTHZ_CUSTODIAN_DOC = "CNCL_REJECT_TEMP_UPLIFT_AUTHZ";

	public static final String ACTION_CNCL_REJECT_TEMP_UPLIFT_CUSTODIAN_DOC = "CNCL_REJECT_TEMP_UPLIFT";

	public static final String ACTION_CNCL_REJECT_PERM_UPLIFT_AUTHZ_CUSTODIAN_DOC = "CNCL_REJECT_PERM_UPLIFT_AUTHZ";

	public static final String ACTION_CNCL_REJECT_PERM_UPLIFT_CUSTODIAN_DOC = "CNCL_REJECT_PERM_UPLIFT";

	public static final String ACTION_EDIT_REJECT_LODGE_CUSTODIAN_DOC = "EDIT_REJECT_LODGE";

	public static final String ACTION_EDIT_REJECT_RELODGE_CUSTODIAN_DOC = "EDIT_REJECT_RELODGE";

	public static final String ACTION_EDIT_REJECT_TEMP_UPLIFT_AUTHZ_CUSTODIAN_DOC = "EDIT_REJECT_TEMP_UPLIFT_AUTHZ";

	public static final String ACTION_EDIT_REJECT_TEMP_UPLIFT_CUSTODIAN_DOC = "EDIT_REJECT_TEMP_UPLIFT";

	public static final String ACTION_EDIT_REJECT_PERM_UPLIFT_AUTHZ_CUSTODIAN_DOC = "EDIT_REJECT_PERM_UPLIFT_AUTHZ";

	public static final String ACTION_EDIT_REJECT_PERM_UPLIFT_CUSTODIAN_DOC = "EDIT_REJECT_PERM_UPLIFT";

	public static final String ACTION_EDIT_REJECT_CUSTODIAN_DOC = "EDIT_REJECT";

	public static final String ACTION_CNCL_REJECT_CUSTODIAN_DOC = "CNCL_REJECT";

	// todo need to be checked and remove the below actions for custodian if not
	// used..
	public static final String ACTION_RECEIVE_CUSTODIAN_DOC = "RECEIVE";

	public static final String ACTION_DRAFT_CUSTODIAN_DOC = "DRAFT";

	public static final String ACTION_RECEIVE_DRAFT_CUSTODIAN_DOC = "RECEIVE_DRAFT";

	public static final String ACTION_APPROVE_CREATE_CUSTODIAN_DOC = "APPROVE_CREATE";

	public static final String ACTION_READ_CUSTODIAN_DOC = "READ";

	public static final String ACTION_READ_CUSTODIAN_ID_DOC = "READ_ID";

	public static final String ACTION_UPDATE_CUSTODIAN_DOC = "UPDATE";

	public static final String ACTION_APPROVE_UPDATE_CUSTODIAN_DOC = "APPROVE_UPDATE";

	public static final String ACTION_AUTHZ_TEMP_UPLIFT_CUSTODIAN_DOC = "AUTHZ_TEMP_UPLIFT";

	public static final String ACTION_AUTHZ_PERM_UPLIFT_CUSTODIAN_DOC = "AUTHZ_PERM_UPLIFT";

	public static final String ACTION_AUTHZ_RELODGE_CUSTODIAN_DOC = "AUTHZ_RELODGE"; // bernard

	// -
	// referenced
	// by
	// AbstractCustodianProxyManager

	// end of should be delete block..

	// doc type used by custodian..
	public static final String DOC_TYPE_CC = "CC";

	public static final String DOC_TYPE_SECURITY = "S";
	
	public static final String DOC_TYPE_FACILITY = "F";
	
	public static final String DOC_TYPE_CAM = "CAM";
	
	public static final String DOC_TYPE_LAD = "LAD";
	
	public static final String DOC_TYPE_OTHER = "O";
	
	public static final String DOC_TYPE_RECURRENT_MASTER = "REC";
	
	public static final String DOC_TYPE_PARIPASSU = "PARIPASSU";

	public static final String DOC_TYPE_CUSTODIAN = "C";

	public static final String DOC_TYPE_RECURRENT = "R";

	// print memo used by custodian usecase..
	public static final String MEMO_OPERATION_TYPE_LODGEMENT = "LODGEMENT";

	public static final String MEMO_OPERATION_TYPE_WITHDRAWAL = "WITHDRAWAL";

	public static final String MEMO_OPERATION_TYPE_REVERSAL = "REVERSAL";

	public static final String MEMO_AUTHZ_ROLE_1 = "ROLE1";

	public static final String MEMO_AUTHZ_ROLE_2 = "ROLE2";

	/*
	 * ----------------------------- END OF CUSTODIAN TERITORY
	 * --------------------------------------------------------
	 */

	// ********* Constants for Security Parameter ************
	public static final String INSTANCE_COL_PARAMETER = "COLPARAM";

	public static final String ACTION_MAKER_UPDATE_COLPARAM = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_COLPARAM = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_COLPARAM = "MAKER_CANCEL";

	public static final String ACTION_CHECKER_APPROVE_COLPARAM = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_COLPARAM = "CHECKER_REJECT";

	public static final String ACTION_READ_COLPARAM_BY_TYPECODE = "READ_BY_TYPECODE";

	public static final String ACTION_READ_COLPARAM_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_COLPARAM_BY_COUNTRY_COLTYPE = "READ_BY_COUNTRY_COLTYPE";

	public static final String ACTION_CREATE_COLPARAM = "CREATE";

	// ************* Constants for Security Sub Type *********************
	public static final String INSTANCE_COL_SUBTYPE = "COLSUBTYPE";

	public static final String ACTION_MAKER_UPDATE_SUBTYPE = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_SUBTYPE = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_SUBTYPE = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_SUBTYPE = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_SUBTYPE = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_SUBTYPE = "CHECKER_REJECT";

	public static final String ACTION_READ_SUBTYPE_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_SUBTYPE_BY_TYPECODE = "READ_BY_TYPECODE";

	public static final String ACTION_CREATE_SUBTYPE = "CREATE";

	// ************* Constants for Security Asset Life *********************
	public static final String INSTANCE_COL_ASSETLIFE = "COLASSETLIFE";

	public static final String ACTION_MAKER_UPDATE_ASSETLIFE = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_ASSETLIFE = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_ASSETLIFE = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_ASSETLIFE = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_ASSETLIFE = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_ASSETLIFE = "CHECKER_REJECT";

	public static final String ACTION_READ_ASSETLIFE_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_ASSETLIFE = "READ";

	// ********* constants for CMS: Valuation ******************
	public static final String VALUATION_SOURCE_TYPE_S = "S"; // Source

	// Valuation

	public static final String VALUATION_SOURCE_TYPE_M = "M"; // Manual

	// valuation

	public static final String VALUATION_SOURCE_TYPE_A = "A"; // Auto

	// Valuation

	public static final String INSTANCE_VALUATION = "VAL";

	public static final String ACTION_READ_VAL_BY_VALID = "READ_BY_VALID";

	public static final String ACTION_READ_VAL_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_VAL_BY_TRXREFID = "READ_BY_TRXREFID";

	public static final String ACTION_MANUAL_FEEDS_CREATE_REVAL = "MANUAL_FEEDS_CREATE";

	public static final String ACTION_MANUAL_FEEDS_UPDATE_REVAL = "MANUAL_FEEDS_UPDATE";

	public static final String ACTION_MANUAL_CREATE_REVAL = "MANUAL_CREATE";

	public static final String ACTION_MANUAL_UPDATE_REVAL = "MANUAL_UPDATE";

	public static final String ACTION_MANUAL_RESUBMIT_UPDATE_REVAL = "MANUAL_RESUBMIT_UPDATE";

	public static final String ACTION_MANUAL_RESUBMIT_CREATE_REVAL = "MANUAL_RESUBMIT_CREATE";

	public static final String ACTION_MANUAL_CANCEL_CREATE_REVAL = "MANUAL_CANCEL_CREATE";

	public static final String ACTION_MANUAL_CANCEL_UPDATE_REVAL = "MANUAL_CANCEL_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_REVAL = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_REVAL = "CHECKER_REJECT";

	public static final String ACTION_SYSTEM_CLOSE_REVAL = "SYSTEM_CLOSE";

	public static final String ACTION_SYSTEM_UPDATE_REVAL = "SYSTEM_UPDATE";

	public static final String ACTION_SYSTEM_CREATE_REVAL = "SYSTEM_CREATE";

	public static final String VALUER_NAME_SYSTEM = "SYSTEM";

	public static final String VALUATION_TYPE = "VALUATION_TYPE";

	// ********* constants for CMS: Collateral ****************
	public static final String INSTANCE_COLLATERAL = "COL";

	public static final String ACTION_READ_COL_BY_COLID = "READ_BY_COLID";

	public static final String ACTION_READ_COL_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_COL_BY_TRXREFID = "READ_BY_TRXREFID";

	public static final String ACTION_CHECKER_REJECT_UPDATE_COL = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_CHECKER_REJECT_DELETE_COL = "CHECKER_REJECT_DELETE";

	public static final String ACTION_MAKER_UPDATE_COL = "MAKER_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_COL = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_COL = "CHECKER_APPROVE_DELETE";

	public static final String ACTION_MAKER_SAVE_COL = "MAKER_SAVE";

	public static final String ACTION_MAKER_CREATE_COL = "MAKER_CREATE";

	public static final String ACTION_MAKER_CANCEL_COL = "MAKER_CANCEL";

	public static final String ACTION_MAKER_DELETE_COL = "MAKER_DELETE";

	public static final String ACTION_SUBSCRIBE_CREATE_COL = "SUBSCRIBE_CREATE";

	public static final String ACTION_PART_DELETE_COL = "PART_DELETE";

	public static final String ACTION_FULL_DELETE_COL = "FULL_DELETE";

	public static final String ACTION_SYSTEM_CANCEL_COL = "SYSTEM_CANCEL";

	public static final String ACTION_SYSTEM_UPDATE_COL = "SYSTEM_UPDATE";

	public static final String ACTION_HOST_UPDATE_COL = "HOST_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_PASS_COL = "CHECKER_APPROVE_PASS";

	public static final String ACTION_CHECKER_APPROVE_FAIL_COL = "CHECKER_APPROVE_FAIL";

	public static final String ACTION_CHECKER_APPROVE_DELETE_PASS_COL = "CHECKER_APPROVE_LOAD_DELETE";

	public static final String ACTION_SYSTEM_APPROVE_COL = "SYSTEM_APPROVE";

	public static final String ACTION_SYSTEM_REJECT_COL = "SYSTEM_REJECT";

	// ********* constants for collateral subtype codes ******************
	public static final String SECURITY_TYPE_GUARANTEE = "GT";

	public static final String SECURITY_TYPE_INSURANCE = "IN";

	public static final String SECURITY_TYPE_CASH = "CS";

	public static final String SECURITY_TYPE_ASSET = "AB";

	public static final String SECURITY_TYPE_COMMODITY = "CF";

	public static final String SECURITY_TYPE_DOCUMENT = "DC";

	public static final String SECURITY_TYPE_MARKETABLE = "MS";

	public static final String SECURITY_TYPE_PROPERTY = "PT";

	public static final String SECURITY_TYPE_CLEAN = "CL";

	public static final String INTERNAL_COL_CUSTODIAN = "I";

	public static final String EXTERNAL_COL_CUSTODIAN = "E";

	public static final String EQUITY_TYPE_UNIT_TRUST = "U";

	public static final String EQUITY_TYPE_STOCK = "S";

	public static final String COLTYPE_NA = "NA";
	
	// added for vehicle security
	
	public static final String PHYSICAL_INSPECTION_YES = "Y";
	
	public static final String PHYSICAL_INSPECTION_NO = "N";

	/** Collateral of type bank guarantee: different currency. */
	public static final String COLTYPE_GUARANTEE_BANK_DIFFCCY = "GT401";

	/** Collateral of type bank guarantee: same currency. */
	public static final String COLTYPE_GUARANTEE_BANK_SAMECCY = "GT400";

	/** Collateral of type corporate guarantee: related. */
	public static final String COLTYPE_GUARANTEE_CORP_RELATED = "GT404";

	/** Collateral of type corporate guarantee: third party. */
	public static final String COLTYPE_GUARANTEE_CORP_3RDPARTY = "GT405";

	/** Collateral of type government guarantee. */
	public static final String COLTYPE_GUARANTEE_GOVERNMENT = "GT406";

	/** Collateral of type interbranch indemnity guarantee. */
	public static final String COLTYPE_GUARANTEE_INTBR_INDEMNITY = "GT407";

	/** Collateral of type personal guarantee. */
	public static final String COLTYPE_GUARANTEE_PERSONAL = "GT408";

	/**
	 * Collateral of type standby letter of credit guarantee: different
	 * currency.
	 */
	public static final String COLTYPE_GUARANTEE_SBLC_DIFFCCY = "GT403";

	/** collateral of type standby letter of credit guarantee: same currency. */
	public static final String COLTYPE_GUARANTEE_SBLC_SAMECCY = "GT402";

	/** collateral of type standby letter of credit guarantee: inward */
	public static final String COLTYPE_GUARANTEE_BANK_INWARDLC = "GT410";

	/** collateral of type standby letter of credit guarantee: govt link. */
	public static final String COLTYPE_GUARANTEE_GOVT_LINK = "GT409";

	/** collateral of type asset: general charge. */
	public static final String COLTYPE_ASSET_GENERAL_CHARGE = "AB100";

	/** Asset collateral of type post dated cheque. */
	public static final String COLTYPE_ASSET_PDT_CHEQUE = "AB108";

	/**
	 * Asset collateral of type Receivables Assigned - General Invoices via
	 * Agent.
	 */
	public static final String COLTYPE_ASSET_RECV_GEN_AGENT = "AB105";

	/** Asset collateral of type Receivables Assigned - Open. */
	public static final String COLTYPE_ASSET_RECV_OPEN = "AB107";

	/**
	 * Asset collateral of type Receivables Assigned - Specific Invoices via
	 * Agent.
	 */
	public static final String COLTYPE_ASSET_RECV_SPEC_AGENT = "AB104";

	/**
	 * Asset collateral of type Receivables Assigned - Specific Invoices not via
	 * Agent.
	 */
	public static final String COLTYPE_ASSET_RECV_SPEC_NOAGENT = "AB106";

	/** Asset collateral of type Specific Charge - Others. */
	public static final String COLTYPE_ASSET_SPEC_CHARGE_OTHERS = "AB103";

	/** Asset collateral of type Specific Charge - Plant & Equipment. */
	public static final String COLTYPE_ASSET_SPEC_CHARGE_PLANT = "AB101";

	/** Asset collateral of type Vessel. */
	public static final String COLTYPE_ASSET_VESSEL = "AB111";

	/** Asset collateral of type Specific Charge - Vehicles. */
	public static final String COLTYPE_ASSET_SPEC_CHARGE_VEH = "AB102";

	/** Asset of type Specific Charge - Aircraft. */
	public static final String COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT = "AB109";

	/** Asset collateral of type Specific Charge - Gold. */
	public static final String COLTYPE_ASSET_SPEC_CHARGE_GOLD = "AB110";

	public static final String COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT_REG_JET = "AB112";

	public static final String COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT_FREIGHT = "AB113";

	/** Property collateral of type Agricultural. */
	public static final String COLTYPE_PROP_AGRICULTURAL = "PT700";

	/** Property collateral of type Commercial - General. */
	public static final String COLTYPE_PROP_COM_GENERAL = "PT701";

	/** Property collateral of type Commercial - Shop House. */
	public static final String COLTYPE_PROP_COM_SHOP_HOUSE = "PT702";

	/** Property collateral of type Industrial. */
	public static final String COLTYPE_PROP_INDUSTRIAL = "PT703";

	/** Property collateral of type Land - Urban. */
	public static final String COLTYPE_PROP_LAND_URBAN = "PT707";

	/** Property collateral of type Land Vacant - Rural. */
	public static final String COLTYPE_PROP_LAND_VACANT = "PT708";

	/** Property collateral of type Residential - Luxury. */
	public static final String COLTYPE_PROP_RES_LUXURY = "PT705";

	/** Property collateral of type Residential - Standard. */
	public static final String COLTYPE_PROP_RES_STANDARD = "PT704";

	/** Property collateral of type Special Purpose - Hotel. */
	public static final String COLTYPE_PROP_SPEC_HOTEL = "PT706";

	/** Property collateral of type Special Purpose - Others. */
	public static final String COLTYPE_PROP_SPEC_OTHERS = "PT709";

	/** Property collateral of type Special Purpose - Serviced Apartments. */
	public static final String COLTYPE_PROP_SPEC_SERVICE_APT = "PT710";

	/** Property collateral of type Special Purpose - Industrial. */
	public static final String COLTYPE_PROP_SPEC_INDUSTRIAL = "PT711";

	/** Cash collateral of Repo type. */
	/** Cash collateral of Cash Fixed Deposit. */
	public static final String COLTYPE_CASH_FD = "CS202";

	/** Cash collateral of Repo type. */
	public static final String COLTYPE_CASH_REPO = "CS203";

	/** Cash collateral of different currency type. */
	public static final String COLTYPE_CASH_DIFFCCY = "CS204";

	/** Cash collateral of type HKD/USD. */
	public static final String COLTYPE_CASH_HKDUSD = "CS201";

	/** Cash collateral of Cash Cash type. */
	public static final String COLTYPE_CASH_CASH = "CS200";

	/** Cash collateral of same currency type. */
	public static final String COLTYPE_CASH_SAMECCY = "CS208";

	/** Document collateral of type general credit agreement. */
	public static final String COLTYPE_DOC_CR_AGREEMENT = "DC302";

	/** FX/Derivative Document of type ISDA/FEMA. */
	public static final String COLTYPE_DOC_FX_ISDA = "DC300";

	/** FX/Derivative Document of type Netting Agreement. */
	public static final String COLTYPE_DOC_FX_NETTING = "DC301";

	/** Document collateral of type Letter of Undertaking. */
	public static final String COLTYPE_DOC_LOU = "DC303";

	/** Document collateral of type Letter of Comfort. */
	public static final String COLTYPE_DOC_COMFORT = "DC304";

	/** Document collateral of type Negative Pledge. */
	public static final String COLTYPE_DOC_PLEDGE = "DC305";

	/** Document collateral of type Deed of subordination. */
	public static final String COLTYPE_DOC_DEED_SUB = "DC306";

	/** Document collateral of type letter of indemnity. */
	public static final String COLTYPE_DOC_LETTER_INDEMNITY = "DC307";

	/** Document collateral of type deed of assignments. */
	public static final String COLTYPE_DOC_DEED_ASSIGNMENT = "DC308";

	/** Document collateral of type lease agreement. */
	public static final String COLTYPE_DOC_LEASE_AGREEMENT = "DC309";

	/** Document collateral of type Letter of awareness. */
	public static final String COLTYPE_DOC_LETTER_AWARENESS = "DC310";

	/** Insurance collateral of type Credit Derivatives. */
	public static final String COLTYPE_INS_CR_DERIVATIVE = "IN502";

	/** Insurance collateral of type Credit Insurance. */
	public static final String COLTYPE_INS_CR_INS = "IN500";

	/** Insurance collateral of type Credit Default Swaps. */
	public static final String COLTYPE_INS_CR_DEFAULT_SWAPS = "IN503";

	/** Insurance collateral of type Keyman Insurance. */
	public static final String COLTYPE_INS_KEYMAN_INS = "IN501";

	/** Marketable collateral of type bonds - foreign. */
	public static final String COLTYPE_MAR_BONDS_FOREIGN = "MS611";

	/** Marketable collateral of type bonds - local. */
	public static final String COLTYPE_MAR_BONDS_LOCAL = "MS610";

	/** Marketable collateral of type government foreign - different currency. */
	public static final String COLTYPE_MAR_GOVT_FOREIGN_DIFFCCY = "MS604";

	/** Marketable collateral of type government foreign - same currency. */
	public static final String COLTYPE_MAR_GOVT_FOREIGN_SAMECCY = "MS603";

	/** Marketable collateral of type government local/treasury bills. */
	public static final String COLTYPE_MAR_GOVT_LOCAL = "MS602";

	/** Marketable collateral of type Main Index - Foreign. */
	public static final String COLTYPE_MAR_MAIN_IDX_FOREIGN = "MS601";

	/** Marketable collateral of type Main Index - Local. */
	public static final String COLTYPE_MAR_MAIN_IDX_LOCAL = "MS600";

	/** Marketable collateral of type Non-listed Local. */
	public static final String COLTYPE_MAR_NONLISTED_LOCAL = "MS607";

	/** Marketable collateral of type Other Listed - Foreign. */
	public static final String COLTYPE_MAR_OTHERLISTED_FOREIGN = "MS606";

	/** Marketable collateral of type Other Listed - Local. */
	public static final String COLTYPE_MAR_OTHERLISTED_LOCAL = "MS605";

	/**
	 * Marketable collateral of type Portfolio of Securities Controlled via
	 * Custodian.
	 */
	public static final String COLTYPE_MAR_PORTFOLIO_OTHERS = "MS608";

	/**
	 * Marketable collateral of type Portfolio of Securities Controlled by Own
	 * Bank.
	 */
	public static final String COLTYPE_MAR_PORTFOLIO_OWN = "MS609";

	/** Commodity collateral of type chemical. */
	public static final String COLTYPE_COMMODITY_CHEMICAL = "CF804";

	/** Commodity collateral of type energy. */
	public static final String COLTYPE_COMMODITY_ENERGY = "CF803";

	/** Commodity collateral of type ferrous metals. */
	public static final String COLTYPE_COMMODITY_FERROUS_METAL = "CF800";

	/** Commodity collateral of type non ferrous metals. */
	public static final String COLTYPE_COMMODITY_NON_FERROUS_METAL = "CF801";

	/** Commodity collateral of type non ferrous others. */
	public static final String COLTYPE_COMMODITY_NON_FERROUS_OTHERS = "CF802";

	/** Commodity collateral of type soft bulk. */
	public static final String COLTYPE_COMMODITY_SOFT_BULK = "CF805";

	/** Document collateral of type letter of indemnity. */
	// public static final String COLTYPE_DOC_LETTER_INDEMNITY = "DC307";
	/** Document collateral of type deed of assignments. */
	// public static final String COLTYPE_DOC_DEED_ASSIGNMENT = "DC308";
	// ******************** Customer Related Trx Constants ***************
	public static final String INSTANCE_CUSTOMER = "CUSTOMER";

	public static final String ACTION_READ_CUSTOMER = "READ_CUSTOMER";

	public static final String ACTION_READ_CUSTOMER_ID = "READ_CUSTOMER_ID";

	public static final String ACTION_CREATE_CUSTOMER = "CREATE_CUSTOMER";
	
	public static final String ACTION_CREATE_CUSTOMER_PARTY = "CREATE_CUSTOMER_PARTY";
	
	public static final String ACTION_CREATE_CUSTOMER_BRMAKER = "CREATE_CUSTOMER_BRMAKER";
	
	public static final String ACTION_SAVE_CUSTOMER = "SAVE_CUSTOMER";
	
	public static final String ACTION_SAVE_CUSTOMER_BRMAKER = "SAVE_CUSTOMER_BRMAKER";
	
	public static final String ACTION_SAVE_CUSTOMER_PARTY = "SAVE_CUSTOMER_PARTY";
	
	public static final String ACTION_SAVE_CUSTOMER_IN_EDIT = "SAVE_CUSTOMER_IN_EDIT";
	
	public static final String ACTION_SAVE_CUSTOMER_IN_EDIT_BRMAKER = "SAVE_CUSTOMER_IN_EDIT_BRMAKER";
	
	public static final String ACTION_SAVE_CUSTOMER_IN_EDIT_PARTY = "SAVE_CUSTOMER_IN_EDIT_PARTY";

	public static final String ACTION_HOST_UPDATE_CUSTOMER = "HOST_UPDATE";

	public static final String ACTION_MAKER_UPDATE_CUSTOMER = "MAKER_UPDATE_CUSTOMER";
	
	public static final String ACTION_MAKER_UPDATE_CUSTOMER_BRMAKER = "MAKER_UPDATE_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_UPDATE_CUSTOMER_PARTY = "MAKER_UPDATE_CUSTOMER_PARTY";
	
	public static final String ACTION_MAKER_UPDATE_CLOSED_CUSTOMER = "MAKER_UPDATE_CLOSED_CUSTOMER";
	
	public static final String ACTION_MAKER_UPDATE_REJECTED_CUSTOMER = "MAKER_UPDATE_REJECTED_CUSTOMER";
	
	public static final String ACTION_MAKER_UPDATE_REJECTED_CUSTOMER_BRMAKER = "MAKER_UPDATE_REJECTED_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_UPDATE_REJECTED_CUSTOMER_PARTY = "MAKER_UPDATE_REJECTED_CUSTOMER_PARTY";
	
	public static final String ACTION_MAKER_UPDATE_DRAFT_CUSTOMER = "MAKER_UPDATE_DRAFT_CUSTOMER";
	
	public static final String ACTION_MAKER_UPDATE_DRAFT_CUSTOMER_BRMAKER = "MAKER_UPDATE_DRAFT_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_UPDATE_DRAFT_CUSTOMER_PARTY = "MAKER_UPDATE_DRAFT_CUSTOMER_PARTY";
	
	public static final String ACTION_MAKER_CREATE_DRAFT_CUSTOMER = "MAKER_CREATE_DRAFT_CUSTOMER";
	
	public static final String ACTION_MAKER_CREATE_DRAFT_CUSTOMER_BRMAKER = "MAKER_CREATE_DRAFT_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_CREATE_DRAFT_CUSTOMER_PARTY = "MAKER_CREATE_DRAFT_CUSTOMER_PARTY";

	public static final String ACTION_HOST_DELETE_CUSTOMER = "HOST_DELETE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER = "CHECKER_APPROVE_UPDATE_CUSTOMER";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER = "CHECKER_APPROVE_UPDATE_CUSTOMER_BRMAKER";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_CUSTOMER_PARTY = "CHECKER_APPROVE_UPDATE_CUSTOMER_PARTY";

	public static final String ACTION_CHECKER_REJECT_UPDATE_CUSTOMER = "CHECKER_REJECT_UPDATE_CUSTOMER";
	
	public static final String ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_PARTY = "CHECKER_REJECT_UPDATE_CUSTOMER_PARTY";
	
	public static final String ACTION_CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER = "CHECKER_REJECT_UPDATE_CUSTOMER_BRMAKER";

	public static final String ACTION_MAKER_CANCEL_UPDATE = "MAKER_CANCEL_UPDATE";
	
	public static final String ACTION_MAKER_CANCEL_UPDATE_PARTY = "MAKER_CANCEL_UPDATE_PARTY";

	public static final String ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER = "MAKER_RESUBMIT_UPDATE_CUSTOMER";
	
	public static final String ACTION_MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTY = "MAKER_RESUBMIT_UPDATE_CUSTOMER_PARTY";

	public static final String ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER = "MAKER_RESUBMIT_CREATE_CUSTOMER";
	
	public static final String ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER = "MAKER_RESUBMIT_CREATE_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_RESUBMIT_CREATE_CUSTOMER_PARTY = "MAKER_RESUBMIT_CREATE_CUSTOMER_PARTY";
	
	public static final String ACTION_MAKER_RESUBMIT_DELETE_CUSTOMER = "MAKER_RESUBMIT_DELETE_CUSTOMER";
	// ********************* Limit Related Trx Constants ******************
	public static final String INSTANCE_LIMIT = "LIMIT";

	public static final String ACTION_READ_LIMIT = "READ_LIMIT";

	public static final String ACTION_READ_LIMIT_ID = "READ_LIMIT_ID";

	public static final String ACTION_READ_LIMIT_BY_LPID = "READ_LIMIT_BY_LPID";

	public static final String ACTION_CREATE_LIMIT = "CREATE";

	public static final String ACTION_MAKER_UPDATE_LIMIT = "MAKER_UPDATE";

	public static final String ACTION_SYSTEM_CLOSE_LIMIT = "SYSTEM_CLOSE";

	public static final String ACTION_HOST_UPDATE_LIMIT = "HOST_UPDATE";

	public static final String ACTION_SYSTEM_UPDATE_LIMIT = "SYSTEM_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_LIMIT = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_REJECT_UPDATE_LIMIT = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_MAKER_RESUBMIT_UPDATE_LIMIT = "MAKER_RESUBMIT_UPDATE";

	public static final String ACTION_MAKER_CANCEL_UPDATE_LIMIT = "MAKER_CANCEL_UPDATE";

	public static final String ACTION_SYSTEM_DELETE_LIMIT = "SYSTEM_DELETE";

	// ********************* CoBorrower Limit Related Trx Constant
	// ***************
	public static final String INSTANCE_COBORROWER_LIMIT = "COBORROWER_LIMIT";

	public static final String ACTION_READ_CO_LIMIT = "READ_CO_LIMIT";

	public static final String ACTION_READ_CO_LIMIT_ID = "READ_CO_LIMIT_ID";

	public static final String ACTION_READ_CO_LIMIT_BY_LPID = "READ_CO_LIMIT_BY_LPID";

	public static final String ACTION_CREATE_CO_LIMIT = "CREATE";

	public static final String ACTION_MAKER_UPDATE_CO_LIMIT = "MAKER_UPDATE";

	public static final String ACTION_SYSTEM_CLOSE_CO_LIMIT = "SYSTEM_CLOSE";

	public static final String ACTION_HOST_UPDATE_CO_LIMIT = "HOST_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CO_LIMIT = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_REJECT_UPDATE_CO_LIMIT = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_MAKER_RESUBMIT_UPDATE_CO_LIMIT = "MAKER_RESUBMIT_UPDATE";

	public static final String ACTION_MAKER_CANCEL_UPDATE_CO_LIMIT = "MAKER_CANCEL_UPDATE";

	public static final String ACTION_SYSTEM_DELETE_CO_LIMIT = "SYSTEM_DELETE";

	// ********************* Limit Profile Related Trx Constants
	// ******************
	public static final String INSTANCE_LIMIT_PROFILE = "LIMITPROFILE";

	public static final String ACTION_READ_LIMIT_PROFILE = "READ_LIMIT_PROFILE";

	public static final String ACTION_READ_LIMIT_PROFILE_ID = "READ_LIMIT_PROFILE_ID";

	public static final String ACTION_CREATE_LIMIT_PROFILE = "CREATE";

	public static final String ACTION_MAKER_UPDATE_LIMIT_PROFILE = "MAKER_UPDATE";

	public static final String ACTION_SYSTEM_CLOSE_LIMIT_PROFILE = "SYSTEM_CLOSE";

	public static final String ACTION_HOST_UPDATE_LIMIT_PROFILE = "HOST_UPDATE";

	public static final String ACTION_SYSTEM_UPDATE_LIMIT_PROFILE = "SYSTEM_UPDATE";

	public static final String ACTION_RENEW_LIMIT_PROFILE = "RENEW_LIMIT_PROFILE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_LIMIT_PROFILE = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_REJECT_UPDATE_LIMIT_PROFILE = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_MAKER_RESUBMIT_UPDATE_LIMIT_PROFILE = "MAKER_RESUBMIT_UPDATE";

	public static final String ACTION_MAKER_CANCEL_UPDATE_LIMIT_PROFILE = "MAKER_CANCEL_UPDATE";

	public static final String ACTION_MAKER_CREATE_TAT = "MAKER_CREATE_TAT";

	public static final String ACTION_CHECKER_APPROVE_CREATE_TAT = "CHECKER_APPROVE_CREATE_TAT";

	public static final String ACTION_CHECKER_REJECT_CREATE_TAT = "CHECKER_REJECT_CREATE_TAT";

	public static final String ACTION_MAKER_CANCEL_CREATE_TAT = "MAKER_CANCEL_CREATE_TAT";

	public static final String ACTION_MAKER_RESUBMIT_CREATE_TAT = "MAKER_RESUBMIT_CREATE_TAT";

	public static final String ACTION_SYSTEM_DELETE_LIMIT_PROFILE = "SYSTEM_DELETE";

	public static final String ACTION_UPDATE_TAT = "UPDATE_TAT";

	public static final String TAT_CODE_CREATE_TAT = "CREATE_TAT";

	public static final String TAT_CODE_ISSUE_DRAFT_BFL = "ISSUE_DRAFT_BFL";

	public static final String TAT_CODE_SEND_DRAFT_BFL = "SEND_DRAFT_BFL";

	public static final String TAT_CODE_ACK_REC_DRAFT_BFL = "ACK_REC_DRAFT_BFL";

	public static final String TAT_CODE_ISSUE_CLEAN_BFL = "ISSUE_CLEAN_BFL";

	public static final String TAT_CODE_SPECIAL_ISSUE_CLEAN_BFL = "SPECIAL_ISSUE_CLEAN_BFL";

	public static final String TAT_CODE_ISSUE_FINAL_BFL = "ISSUE_FINAL_BFL";

	public static final String TAT_CODE_REC_FIRST_CHECKLIST = "REC_FIRST_CHECKLIST";

	public static final String TAT_CODE_GEN_CCC = "GEN_CCC";

	public static final String TAT_CODE_GEN_SCC = "GEN_SCC";

	public static final String TAT_CODE_COMPLETE_BCA = "COMPLETE_BCA";

	public static final String TAT_CODE_CUSTOMER_ACCEPT_BFL = "CUSTOMER_ACCEPT_BFL";

	public static final String TAT_CODE_PRINT_REMINDER_BFL = "PRINT_REMINDER_BFL";

	public static final String ACTION_MAKER_CANCEL_CREATE_LIMIT_PROFILE = "MAKER_CANCEL_CREATE";

	public static final String ACTION_MAKER_CREATE_LIMIT_PROFILE = "MAKER_CREATE";

	public static final String ACTION_MAKER_DELETE_LIMIT_PROFILE = "MAKER_DELETE";

	public static final String ACTION_MAKER_DIRECT_UPDATE_LIMIT_PROFILE = "MAKER_DIRECT_UPDATE";

	// *********************** Constants for Team Setup *********************
	public static final long MEMBERSHIP_MAKER = 1;

	public static final long MEMBERSHIP_CHECKER = 2;

	// ***************** Checklist Related Trx Constants ******************
	// Checklist Global
	public static final String INSTANCE_DOC_ITEM_LIST = "DOCITEM";

	public static final String ACTION_READ_DOC_ITEM = "READ_DOC_ITEM";

	public static final String ACTION_MAKER_CREATE_DOC_ITEM = "CREATE_DOC_ITEM";

	public static final String ACTION_CHECKER_APPROVE_CREATE_DOC_ITEM = "APPROVE_CREATE_DOC_ITEM";

	public static final String ACTION_CHECKER_REJECT_DOC_ITEM = "REJECT_DOC_ITEM";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DOC_ITEM = "CLOSE_REJECTED_DOC_ITEM";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_DOC_ITEM = "CLOSE_REJECTED_CREATE_DOC_ITEM";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_DOC_ITEM = "CLOSE_REJECTED_UPDATE_DOC_ITEM";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_DOC_ITEM = "CLOSE_REJECTED_DELETE_DOC_ITEM";

	public static final String ACTION_MAKER_EDIT_REJECTED_DOC_ITEM = "EDIT_REJECTED_DOC_ITEM";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_DOC_ITEM = "EDIT_REJECTED_CREATE_DOC_ITEM";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_DOC_ITEM = "EDIT_REJECTED_UPDATE_DOC_ITEM";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_DOC_ITEM = "EDIT_REJECTED_DELETE_DOC_ITEM";

	public static final String ACTION_MAKER_UPDATE_DOC_ITEM = "UPDATE_DOC_ITEM";
	
	public static final String ACTION_MAKER_DELETE_DOC_ITEM = "DELETE_DOC_ITEM";

	public static final String ACTION_CHECKER_APPROVE_DOC_ITEM = "APPROVE_DOC_ITEM";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_DOC_ITEM = "APPROVE_UPDATE_DOC_ITEM";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_DOC_ITEM = "APPROVE_DELETE_DOC_ITEM";

	// Checklist Templates
	public static final String INSTANCE_TEMPLATE_LIST = "TEMPLATE";

	public static final String TRX_TYPE_CC_GLOBAL_TEMPLATE = "CC_GLOBAL_TEMPLATE";

	public static final String TRX_TYPE_CC_MASTER_TEMPLATE = "CC_MASTER_TEMPLATE";

	public static final String TRX_TYPE_CC_COUNTRY_TEMPLATE = "CC_COUNTRY_TEMPLATE";

	public static final String TRX_TYPE_COL_GLOBAL_TEMPLATE = "COL_GLOBAL_TEMPLATE";
	
	public static final String TRX_TYPE_FAC_GLOBAL_TEMPLATE = "FAC_GLOBAL_TEMPLATE";
	
	public static final String TRX_TYPE_CAM_GLOBAL_TEMPLATE = "CAM_GLOBAL_TEMPLATE";
	
	public static final String TRX_TYPE_OTHER_GLOBAL_TEMPLATE = "OTHER_GLOBAL_TEMPLATE";
	
	public static final String TRX_TYPE_RECURRENT_GLOBAL_TEMPLATE ="RECURRENT_GLOBAL_TEMPLATE";

	public static final String TRX_TYPE_COL_MASTER_TEMPLATE = "COL_MASTER_TEMPLATE";
	
	public static final String TRX_TYPE_FAC_MASTER_TEMPLATE = "FAC_MASTER_TEMPLATE";

	public static final String TRX_TYPE_COL_COUNTRY_TEMPLATE = "COL_COUNTRY_TEMPLATE";

	public static final String ACTION_READ_TEMPLATE = "READ_TEMPLATE";

	public static final String ACTION_MAKER_CREATE_TEMPLATE = "CREATE_TEMPLATE";

	public static final String ACTION_CHECKER_APPROVE_TEMPLATE = "APPROVE_TEMPLATE";

	public static final String ACTION_CHECKER_APPROVE_CREATE_TEMPLATE = "APPROVE_CREATE_TEMPLATE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_TEMPLATE = "APPROVE_UPDATE_TEMPLATE";

	public static final String ACTION_CHECKER_REJECT_TEMPLATE = "REJECT_TEMPLATE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_TEMPLATE = "CLOSE_REJECTED_TEMPLATE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_TEMPLATE = "CLOSE_REJECTED_CREATE_TEMPLATE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_TEMPLATE = "CLOSE_REJECTED_UPDATE_TEMPLATE";

	public static final String ACTION_MAKER_EDIT_REJECTED_TEMPLATE = "EDIT_REJECTED_TEMPLATE";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_TEMPLATE = "EDIT_REJECTED_CREATE_TEMPLATE";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_TEMPLATE = "EDIT_REJECTED_UPDATE_TEMPLATE";

	public static final String ACTION_MAKER_UPDATE_TEMPLATE = "UPDATE_TEMPLATE";

	// Checklist
	public static final String INSTANCE_CHECKLIST = "CHECKLIST";

	public static final String INSTANCE_CHECKLIST_ITEM = "CHECKLIST_ITEM";

	public static final String INSTANCE_RECURRENT_CHECKLIST = "RECURRENT_CHECKLIST";

	public static final String TRX_TYPE_CC_CHECKLIST = "CC_CHECKLIST";

	public static final String TRX_TYPE_CC_CHECKLIST_RECEIPT = "CC_CHECKLIST_REC";

	public static final String TRX_TYPE_DELETE_CC_CHECKLIST = "DELETED_CC_CHECKLIST"; // Changed

	// from
	// Delete
	// to
	// Maintain
	// Deleted
	// CC
	// Checklist
	// CR
	// -
	// 236

	public static final String TRX_TYPE_OBSOLETE_CC_CHECKLIST = "OBSOLETE_CC_CHKLIST"; // CR

	// -
	// 236
	// (
	// Delete
	// Checklist
	// )
	// Obsolete
	// CC
	// Checklist

	public static final String TRX_TYPE_OBSOLETE_CC_CUSTODIAN = "OBSOLETE_CC_CT";

	public static final String TRX_TYPE_DELETE_CC_CHECKLIST_RECEIPT = "DEL_CC_CHECKLIST_REC";

	public static final String TRX_TYPE_DELETED_CC_CUSTODIAN = "DEL_CC_CT";

	public static final String TRX_TYPE_COL_CHECKLIST = "COL_CHECKLIST";
	
	
	public static final String TRX_TYPE_CAM_CHECKLIST = "CAM_CHECKLIST";
	
	public static final String TRX_TYPE_OTHER_CHECKLIST = "OTHER_CHECKLIST";
	
	public static final String TRX_TYPE_LAD_CHECKLIST = "LAD_CHECKLIST";
	public static final String TRX_TYPE_RECURRENTDOC_CHECKLIST = "RECURRENTDOC_CHECKLIST";
	
	public static final String TRX_TYPE_FAC_CHECKLIST = "FAC_CHECKLIST";
	
	

	public static final String TRX_TYPE_COL_CHECKLIST_RECEIPT = "COL_CHECKLIST_REC";
	
	public static final String TRX_TYPE_CAM_CHECKLIST_RECEIPT = "CAM_CHECKLIST_REC";
	
	public static final String TRX_TYPE_OTHER_CHECKLIST_RECEIPT = "OTHER_CHECKLIST_REC";
	
	public static final String TRX_TYPE_LAD_CHECKLIST_RECEIPT = "LAD_CHECKLIST_REC";
	
	public static final String TRX_TYPE_RECURRENTDOC_CHECKLIST_RECEIPT = "HDFC_RECURRENT_CHKLST_REC";
	
	public static final String TRX_TYPE_FAC_CHECKLIST_RECEIPT = "FAC_CHECKLIST_REC";

	public static final String TRX_TYPE_DELETE_COL_CHECKLIST = "DEL_COL_CHECKLIST"; // Changed
	public static final String TRX_TYPE_DELETE_CAM_CHECKLIST = "DEL_CAM_CHECKLIST";
	
	public static final String TRX_TYPE_DELETE_OTHER_CHECKLIST = "DEL_OTHER_CHECKLIST";
	public static final String TRX_TYPE_DELETE_LAD_CHECKLIST = "DEL_LAD_CHECKLIST";
	
	public static final String TRX_TYPE_DELETE_RECURRENTDOC_CHECKLIST = "DEL_RECURRENTDOC_CHECKLIST";
	
	public static final String TRX_TYPE_DELETE_FAC_CHECKLIST = "DEL_FAC_CHECKLIST";
	
	//Start:Code added for Pari Passu Check List
	public static final String TRX_TYPE_PARIPASSU_CHECKLIST_RECEIPT = "PARIPASSU_CHECKLIST_REC";
	public static final String TRX_TYPE_PARIPASSU_CHECKLIST = "PARIPASSU_CHECKLIST";
	public static final String TRX_TYPE_OBSOLETE_PARIPASSU_CHECKLIST = "OBSOLETE_PARIPASSU_CHKLIST"; 
	public static final String TRX_TYPE_DELETE_PARIPASSU_CHECKLIST = "DEL_PARIPASSU_CHECKLIST";
	public static final String TRX_TYPE_DELETE_PARIPASSU_CHECKLIST_RECEIPT = "DEL_PARIPASSUCHECKLIST_REC";
	//End  :Code added for Pari Passu Check List
	
	// from
	// Delete
	// to
	// Maintain
	// Deleted
	// Sec
	// Checklist
	// CR
	// -
	// 236

	public static final String TRX_TYPE_OBSOLETE_COL_CHECKLIST = "OBSOLETE_COL_CHKLIST"; 
	
	
	public static final String TRX_TYPE_OBSOLETE_CAM_CHECKLIST = "OBSOLETE_CAM_CHKLIST"; 
	
	public static final String TRX_TYPE_OBSOLETE_OTHER_CHECKLIST = "OBSOLETE_OTHER_CHKLIST"; 
	public static final String TRX_TYPE_OBSOLETE_LAD_CHECKLIST = "OBSOLETE_LAD_CHKLIST"; 
	
	public static final String TRX_TYPE_OBSOLETE_RECURRENTDOC_CHECKLIST = "OBSOLETE_RECURRENTDOC_CHKLIST"; 
	
	public static final String TRX_TYPE_OBSOLETE_FAC_CHECKLIST = "OBSOLETE_FAC_CHKLIST"; 

	// -
	// 236
	// (
	// Delete
	// Checklist
	// )
	// Obsolete
	// Security
	// Checklist

	public static final String TRX_TYPE_OBSOLETE_COL_CUSTODIAN = "OBSOLETE_COL_CT";

	public static final String TRX_TYPE_DELETE_COL_CHECKLIST_RECEIPT = "DEL_COLCHECKLIST_REC";
	
	public static final String TRX_TYPE_DELETE_CAM_CHECKLIST_RECEIPT = "DEL_CAMCHECKLIST_REC";
	
	public static final String TRX_TYPE_DELETE_OTHER_CHECKLIST_RECEIPT = "DEL_OTHERCHECKLIST_REC";
	
	public static final String TRX_TYPE_DELETE_LAD_CHECKLIST_RECEIPT = "DEL_LADCHECKLIST_REC";
	
	public static final String TRX_TYPE_DELETE_RECURRENTDOC_CHECKLIST_RECEIPT = "DEL_HDFC_RECURRENT_CHKLST_REC";
	
	public static final String TRX_TYPE_DELETE_FAC_CHECKLIST_RECEIPT = "DEL_FACCHECKLIST_REC";

	public static final String TRX_TYPE_DELETED_COL_CUSTODIAN = "DEL_COL_CT";

	public static final String TRX_TYPE_REC_CHECKLIST = "REC_CHECKLIST";

	public static final String TRX_TYPE_REC_CHECKLIST_RECEIPT = "REC_CHECKLIST_REC";
	
	public static final String TRX_TYPE_REC_CHECKLIST_ANNEXURE = "REC_CHECKLIST_ANN";

	public static final String ACTION_READ_CHECKLIST = "READ_CHECKLIST";

	public static final String ACTION_READ_CHECKLIST_ID = "READ_CHECKLIST_ID";

	public static final String ACTION_READ_RECURRENT_CHECKLIST = "READ_RECURRENT_CHECKLIST";

	public static final String ACTION_READ_RECURRENT_CHECKLIST_ID = "READ_RECURRENT_CHECKLIST_ID";

	// public static final String ACTION_SYSTEM_CREATE_CHECKLIST =
	// "SYSTEM_CREATE_RECURRENT_CHECKLIST";

	public static final String ACTION_SYSTEM_CREATE_CHECKLIST = "SYSTEM_CREATE";

	public static final String ACTION_SYSTEM_CREATE_DOCUMENT_CHECKLIST = "SYSTEM_CREATE_DOCUMENT";

	public static final String ACTION_SYSTEM_UPDATE_CHECKLIST = "SYSTEM_UPDATE";

	public static final String ACTION_DIRECT_UPDATE_CHECKLIST = "DIRECT_UPDATE";

	public static final String ACTION_SYSTEM_DELETE_CHECKLIST = "SYSTEM_DELETE";

	public static final String ACTION_COPY_CHECKLIST = "COPY_CHECKLIST";

	public static final String ACTION_COPY_RECURRENT_CHECKLIST = "COPY_RECURRENT_CHECKLIST";

	public static final String ACTION_SYSTEM_CLOSE_CHECKLIST = "SYSTEM_CLOSE_CHECKLIST";

	public static final String ACTION_MAKER_CREATE_CHECKLIST = "CREATE_CHECKLIST";

	public static final String ACTION_SYSTEM_CLOSE_RECURRENT_CHECKLIST = "SYSTEM_CLOSE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_CREATE_RECURRENT_CHECKLIST = "CREATE_RECURRENT_CHECKLIST";

	public static final String ACTION_SYSTEM_CREATE_RECURRENT_CHECKLIST = "SYSTEM_CREATE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_SAVE_RECURRENT_CHECKLIST = "SAVE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_SAVE_CREATE_RECURRENT_CHECKLIST = "SAVE_CREATE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_DRAFT_SAVE_RECURRENT_CHECKLIST = "DRAFT_SAVE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_DRAFT_UPDATE_RECURRENT_CHECKLIST = "DRAFT_UPDATE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_DRAFT_CREATE_RECURRENT_CHECKLIST = "DRAFT_CREATE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_CLOSE_DRAFT_CREATE_RECURRENT_CHECKLIST = "CLOSE_DRAFT_CREATE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_CLOSE_DRAFT_ACTIVE_RECURRENT_CHECKLIST = "CLOSE_DRAFT_ACTIVE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_SAVE_REJECTED_RECURRENT_CHECKLIST = "SAVE_REJECTED_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_SAVE_ACTIVE_RECURRENT_CHECKLIST = "SAVE_ACTIVE_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_MAKER_SAVE_DRAFT_RECURRENT_CHECKLIST = "SAVE_DRAFT_RECURRENT_CHECKLIST"; // USED

	// FOR
	// DRAFT
	// -
	// MAINTAIN

	public static final String ACTION_CHECKER_APPROVE_CHECKLIST = "APPROVE_CHECKLIST";

	public static final String ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST = "APPROVE_RECURRENT_CHECKLIST";

	public static final String ACTION_CHECKER_APPROVE_RECURRENT_CHECKLIST_RECEIPT = "APPROVE_	_CHECKLIST_RECEIPT";

	public static final String ACTION_CHECKER_APPROVE_CHECKLIST_RECEIPT = "APPROVE_CHECKLIST_RECEIPT";

	public static final String ACTION_CHECKER_UPDATE_CHECKLIST_RECEIPT = "CHECKER_UPDATE_CHECKLIST_RECEIPT";

	public static final String ACTION_SYSTEM_OBSOLETE_CHECKLIST = "SYSTEM_OBSOLETE_CHECKLIST";

	public static final String ACTION_CHECKER_VERIFY_CHECKLIST_RECEIPT = "VERIFY_CHECKLIST_RECEIPT";

	public static final String ACTION_MANAGER_VERIFY_CHECKLIST_RECEIPT = "MGR_VERIFY_CHECKLIST_RECEIPT";

	public static final String ACTION_MANAGER_REJECT_CHECKLIST_RECEIPT = "MGR_REJECT_CHECKLIST_RECEIPT";

	public static final String ACTION_CHECKER_APPROVE_CREATE_CHECKLIST = "APPROVE_CREATE_CHECKLIST";

	public static final String ACTION_CHECKER_APPROVE_CREATE_RECURRENT_CHECKLIST = "APPROVE_CREATE_RECURRENT_CHECKLIST";

	public static final String ACTION_CHECKER_REJECT_CHECKLIST = "REJECT_CHECKLIST";

	public static final String ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST = "REJECT_RECURRENT_CHECKLIST";

	public static final String ACTION_CHECKER_REJECT_RECURRENT_CHECKLIST_RECEIPT = "REJECT_RECURRENT_CHECKLIST_RECEIPT";

	public static final String ACTION_CHECKER_REJECT_CHECKLIST_RECEIPT = "REJECT_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CHECKLIST = "CLOSE_REJECTED_CHECKLIST";

	public static final String ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST = "CLOSE_REJECTED_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_CLOSE_REJECTED_RECURRENT_CHECKLIST_RECEIPT = "CLOSE_REJECTED_RECURRENT_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_CLOSE_DRAFT_RECURRENT_CHECKLIST_RECEIPT = "CLOSE_DRAFT_RECURRENT_CHECKLIST_RECEIPT"; // USED

	// BY
	// DRAFT

	public static final String ACTION_MAKER_CLOSE_REJECTED_CHECKLIST_RECEIPT = "CLOSE_REJECTED_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CHECKLIST = "CLOSE_REJECTED_CREATE_CHECKLIST";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_RECURRENT_CHECKLIST = "CLOSE_REJECTED_CREATE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_CHECKLIST = "EDIT_REJECTED_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST = "EDIT_REJECTED_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_RECURRENT_CHECKLIST_RECEIPT = "EDIT_REJECTED_RECURRENT_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_EDIT_REJECTED_CHECKLIST_RECEIPT = "EDIT_REJECTED_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_UPDATE_CHECKLIST = "UPDATE_CHECKLIST";

	public static final String ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST = "UPDATE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_UPDATE_RECURRENT_CHECKLIST_RECEIPT = "UPDATE_RECURRENT_CHECKLIST_RECEIPT"; // SHARE

	// BY
	// DRAFT

	public static final String ACTION_MAKER_SAVE_RECURRENT_CHECKLIST_RECEIPT = "SAVE_RECURRENT_CHECKLIST_RECEIPT"; // USED

	// BY
	// DRAFT

	public static final String ACTION_MAKER_UPDATE_CHECKLIST_RECEIPT = "UPDATE_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_SAVE_CHECKLIST = "SAVE_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_CANCEL_SAVE_CHECKLIST = "CANCEL_SAVE_CHECKLIST_RECEIPT";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CHECKLIST = "APPROVE_UPDATE_CHECKLIST";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CHECKLIST_RECEIPT = "APPROVE_UPDATE_CHECKLIST_RECEIPT";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_RECURRENT_CHECKLIST = "APPROVE_UPDATE_RECURRENT_CHECKLIST";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_RECURRENT_CHECKLIST_RECEIPT = "APPROVE_UPDATE_RECURRENT_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CHECKLIST = "CLOSE_REJECTED_UPDATE_CHECKLIST";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CHECKLIST_RECEIPT = "CLOSE_REJECTED_UPDATE_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_RECURRENT_CHECKLIST = "CLOSE_REJECTED_UPDATE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_RECURRENT_CHECKLIST_RECEIPT = "CLOSE_REJECTED_UPDATE_RECURRENT_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CHECKLIST = "EDIT_REJECTED_CREATE_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_RECURRENT_CHECKLIST = "EDIT_REJECTED_CREATE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CHECKLIST = "EDIT_REJECTED_UPDATE_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CHECKLIST_RECEIPT = "EDIT_REJECTED_UPDATE_CHECKLIST_RECEIPT";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_RECURRENT_CHECKLIST = "EDIT_REJECTED_UPDATE_RECURRENT_CHECKLIST";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_RECURRENT_CHECKLIST_RECEIPT = "EDIT_REJECTED_UPDATE_RECURRENT_CHECKLIST_RECEIPT";

	public static final String ACTION_SYSTEM_GENERATE_CHECKLIST_DEFERRAL = "SYSTEM_GENERATE_CHECKLIST_DEFER";

	public static final String ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_DEFERRAL = "SYSTEM_REJECT_GENERATE_CHECKLIST_DEFER";

	public static final String ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_DEFERRAL = "SYSTEM_APPROVE_GENERATE_CHECKLIST_DEFER";

	public static final String ACTION_SYSTEM_GENERATE_CHECKLIST_WAIVER = "SYSTEM_GENERATE_CHECKLIST_WAIVER";

	public static final String ACTION_SYSTEM_REJECT_GENERATE_CHECKLIST_WAIVER = "SYSTEM_REJECT_GENERATE_CHECKLIST_WAIVER";

	public static final String ACTION_SYSTEM_APPROVE_GENERATE_CHECKLIST_WAIVER = "SYSTEM_APPROVE_GENERATE_CHECKLIST_WAIVER";

	// ***************** Common Code Type Related Trx Constants
	// ******************
	// Checklist Global
	public static final String INSTANCE_COMMON_CODE_TYPE_LIST = "COMMON_CODE_TYPE";

	public static final String ACTION_READ_COMMON_CODE_TYPE = "READ_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_CREATE_COMMON_CODE_TYPE = "CREATE_COMMON_CODE_TYPE";

	public static final String ACTION_CHECKER_APPROVE_CREATE_COMMON_CODE_TYPE = "APPROVE_CREATE_COMMON_CODE_TYPE";

	public static final String ACTION_CHECKER_REJECT_COMMON_CODE_TYPE = "REJECT_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_COMMON_CODE_TYPE = "CLOSE_REJECTED_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_COMMON_CODE_TYPE = "CLOSE_REJECTED_CREATE_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_COMMON_CODE_TYPE = "CLOSE_REJECTED_UPDATE_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_EDIT_REJECTED_COMMON_CODE_TYPE = "EDIT_REJECTED_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_COMMON_CODE_TYPE = "EDIT_REJECTED_CREATE_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_COMMON_CODE_TYPE = "EDIT_REJECTED_UPDATE_COMMON_CODE_TYPE";

	public static final String ACTION_MAKER_UPDATE_COMMON_CODE_TYPE = "UPDATE_COMMON_CODE_TYPE";

	public static final String ACTION_CHECKER_APPROVE_COMMON_CODE_TYPE = "APPROVE_COMMON_CODE_TYPE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_COMMON_CODE_TYPE = "APPROVE_UPDATE_COMMON_CODE_TYPE";

	public static final String SEQUENCE_COMMON_CODE_TYPE = "COMMON_CODE_CATEGORY_SEQ";

	// Constants for the checklist attribute type
	public static final String CHECKLIST_MAIN_BORROWER = "MAIN_BORROWER";

	public static final String CHECKLIST_CO_BORROWER = "CO_BORROWER";

	public static final String CHECKLIST_NON_BORROWER = "NON_BORROWER";

	public static final String CHECKLIST_PLEDGER = "PLEDGOR";

	public static final String CHECKLIST_JOINT_BORROWER = "JOINT_BORROWER";

	public static final String CHECKLIST_COLLATERAL = "COLLATERAL";

	public static final String CHECKLIST_SECURITY = "SECURITY";
	
	public static final String CHECKLIST_CAM = "CAM";
	
	public static final String CHECKLIST_OTHER = "O";
	
	public static final String CHECKLIST_RECURRENT = "REC";
	
	public static final String CHECKLIST_FACILITY_F = "F";
	
	public static final String CHECKLIST_SECURITY_S = "S";
	
	public static final String CHECKLIST_FACILITY = "FACILITY";

	public static final String CHECKLIST_SECURITY_MB = "SECURITY_MB";

	public static final String CHECKLIST_SECURITY_CB = "SECURITY_CB";

	public static final String CUSTOMER_CATEGORY_MAIN_BORROWER = "MB";

	public static final String CUSTOMER_CATEGORY_CO_BORROWER = "CB";

	public static final String CHECKLIST_PANEL_LAWYER = "P";

	public static final String CHECKLIST_NON_PANEL_LAWYER = "P";

	// Constants for the checklist item status
	public static final String STATE_ITEM_REMINDED = "REMINDED";

	public static final String STATE_ITEM_RECEIVED = "RECEIVED";

	public static final String STATE_ITEM_DEFERRED = "DEFERRED";
	
	public static final String STATE_ITEM_PENDING_DEFERRED = "PENDING_DEFERRED";

	public static final String STATE_ITEM_WAIVED = "WAIVED";

	public static final String STATE_ITEM_REDEEMED = "REDEEMED";

	public static final String STATE_ITEM_PENDING_COMPLETE = "PENDING_COMPLETE";

	public static final String STATE_ITEM_COMPLETED = "COMPLETED";

	public static final String STATE_ITEM_PENDING_RENEWAL = "PENDING_RENEWAL";

	public static final String STATE_ITEM_PENDING_REDEEM = "PENDING_REDEEM";

	public static final String STATE_ITEM_RENEWED = "RENEWED";

	public static final String STATE_ITEM_PENDING_TEMP_UPLIFT_AUTHZ = "PENDING_ALLOW_TEMP_UPLIFT";

	public static final String STATE_ITEM_TEMP_UPLIFT_AUTHZ = "ALLOW_TEMP_UPLIFT";

	public static final String STATE_ITEM_PENDING_PERM_UPLIFT_AUTHZ = "PENDING_ALLOW_PERM_UPLIFT";

	public static final String STATE_ITEM_PERM_UPLIFT_AUTHZ = "ALLOW_PERM_UPLIFT";

	public static final String STATE_ITEM_PENDING_RELODGE_AUTHZ = "PENDING_ALLOW_RELODGE"; // bernard

	// -
	// added
	// to
	// support
	// allow
	// relodge

	public static final String STATE_ITEM_RELODGE_AUTHZ = "ALLOW_RELODGE"; // bernard

	// -
	// added
	// to
	// support
	// allow
	// relodge

	public static final String STATE_ITEM_PENDING_DELETE = "PENDING_DELETE";

	public static final String STATE_ITEM_DELETED = "DELETED";

	public static final String STATE_ITEM_NOT_USED = "NOT_USED";

	public static final String STATE_ITEM_PENDING_UPDATE = "PENDING_UPDATE";

	public static final String STATE_ITEM_PENDING_PREFIX = "PENDING_";

	public static final String STATE_ITEM_VIEW_COMPLETED = "VIEW_COMPLETED";

	public static final String STATE_ITEM_PENDING_WAIVER_REQ = "PENDING_WAIVER_REQ";

	public static final String STATE_ITEM_WAIVER_REQ = "WAIVER_REQ";

	public static final String STATE_ITEM_PENDING_WAIVER = "PENDING_WAIVER";

	public static final String STATE_ITEM_PENDING_COMPLETED_REQ = "PENDING_COMPLETED_REQ";

	public static final String STATE_ITEM_COMPLETED_REQ = "COMPLETED_REQ";

	public static final String STATE_ITEM_PENDING_DEFER_REQ = "PENDING_DEFER_REQ";

	public static final String STATE_ITEM_DEFER_REQ = "DEFER_REQ";

	public static final String STATE_ITEM_PENDING_DEFERRAL = "PENDING_DEFER";

	public static final String STATE_ITEM_AWAITING = "AWAITING";

	// public static final String STATE_ITEM_DISPLAY_AWAITING_AS_PENDING =
	// "PENDING";

	public static final String STATE_ITEM_EXPIRED = "EXPIRED";

	public static final String STATE_ITEM_REDEEM = "REDEEM";

	public static final String ACTION_ITEM_REMIND = "REMIND";

	public static final String ACTION_ITEM_RECEIVE = "RECEIVE";

	public static final String ACTION_ITEM_DEFER = "DEFER";

	public static final String ACTION_ITEM_WAIVER_REQ = "WAIVER_REQ";

	public static final String ACTION_ITEM_DEFER_REQ = "DEFER_REQ";

	public static final String ACTION_ITEM_VIEW = "VIEW";

	public static final String ACTION_ITEM_COMPLETE = "COMPLETE";

	public static final String ACTION_ITEM_REDEEM = "REDEEM";

	public static final String ACTION_ITEM_ALLOW_TEMP_UPLIFT = "ALLOW_TEMP_UPLIFT";

	public static final String ACTION_ITEM_ALLOW_PERM_UPLIFT = "ALLOW_PERM_UPLIFT";

	public static final String ACTION_ITEM_ALLOW_RELODGE = "ALLOW_RELODGE"; // bernard

	public static final String ACTION_ITEM_ALLOW_UPDATE_LODGED = "UPDATE_LODGED";

	public static final String ACTION_ITEM_ALLOW_LODGE = "LODGE";

	// -
	// added
	// to
	// support
	// cpc
	// allow
	// relodge

	public static final String ACTION_ITEM_DELETE = "DELETE";

	public static final String ACTION_ITEM_APPROVE_DELETE = "APPROVE_DELETE";

	public static final String ACTION_ITEM_RENEW = "RENEW";

	public static final String ACTION_ITEM_APPROVE = "APPROVE";

	public static final String ACTION_ITEM_PENDING_UPDATE = "PENDING_UPDATE";

	public static final String ACTION_ITEM_PENDING_LODGE = "PENDING_LODGE";

	public static final String ACTION_ITEM_PENDING_RELODGE = "PENDING_RELODGE";

	public static final String ACTION_ITEM_ALLOW_PENDING_RELODGE = "ALLOW_PENDING_RELODGE";

	public static final String ACTION_ITEM_ALLOW_PENDING_LODGE = "ALLOW_PENDING_LODGE";

	public static final String ACTION_ITEM_PENDING_TEMP_UPLIFT = "PENDING_TEMP_UPLIFT";

	public static final String ACTION_ITEM_ALLOW_PENDING_TEMP_UPLIFT = "ALLOW_PENDING_TEMP_UPLIFT";

	public static final String ACTION_ITEM_ALLOW_PENDING_PERM_UPLIFT = "ALLOW_PENDING_PERM_UPLIFT";

	public static final String ACTION_ITEM_PENDING_PERM_UPLIFT = "PENDING_PERM_UPLIFT";

	// Constants for the checklist status
	public static final String STATE_CHECKLIST_NEW = "NEW";

	public static final String STATE_CHECKLIST_DELETED = "DELETED";

	public static final String STATE_CHECKLIST_COMPLETED = "COMPLETED";

	public static final String STATE_CHECKLIST_CERT_ALLOWED = "CERTIFICATION_ALLOWED";

	public static final String STATE_CHECKLIST_IN_PROGRESS = "IN_PROGRESS";

	public static final String STATE_CHECKLIST_WAIVED = "WAIVED";

	public static final String STATE_CHECKLIST_DEFERRED = "DEFERRED";

	public static final String STATE_CHECKLIST_OBSOLETE = "OBSOLETE";

	public static final String CHECKLIST_ITEM_STATE = "ITEM";

	public static final String CUSTODIAN_STATE = "CUSTODIAN";

	public static final String CHECKLIST_APPLICATION_TYPE_ALL = "ALL";

	/** ---- Recurrent Sub-Item States --- */
	public static final String RECURRENT_ITEM_STATE_RECEIVED = "RECEIVED";

	public static final String RECURRENT_ITEM_STATE_PENDING = "PENDING";

	public static final String RECURRENT_ITEM_STATE_ITEM_WAIVED = "WAIVED";

	public static final String RECURRENT_ITEM_STATE_PENDING_WAIVER = "PENDING_WAIVER";

	public static final String CONVENANT_VERIFIED = "VERIFIED";

	public static final String CONVENANT_NOT_VERIFIED = "NOT_VERIFIED";

	public static final String CONVENANT_STATE_CHECKED = "RECEIVED";

	public static final String CONVENANT_STATE_PENDING_CHECKED = "PENDING_RECEIVE";

	public static final String RECCOV_SUB_ITEM_COND_PENDING = "PENDING";

	public static final String RECCOV_SUB_ITEM_COND_HISTORY = "HISTORY";

	public static final String RECCOV_SUB_ITEM_COND_NON_PENDING = "NON_PENDING";

	public static final String FREQ_UNIT_DAYS = "D";

	public static final String FREQ_UNIT_WEEKS = "W";

	public static final String FREQ_UNIT_MONTHS = "M";

	public static final String FREQ_UNIT_YEARS = "Y";

	public static final String ASSET_DAYS = "D";

	public static final String ASSET_WEEKS = "W";

	public static final String ASSET_MONTHS = "M";

	public static final String ASSET_YEARS = "Y";

	// Feeds territory
	public static final String INSTANCE_FOREX_FEED_GROUP = "FOREX_FEED_GROUP";

	public static final String ACTION_READ_FOREX_FEED_GROUP = "ACTION_READ_FOREX_GROUP";

	public static final String ACTION_MAKER_UPDATE_FOREX_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_FOREX_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_FOREX_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_FOREX_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_FOREX_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_FOREX_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_FOREX_FEED_GROUP = "ACTION_CHECKER_APPROVE_FOREX_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_FOREX_FEED_GROUP = "ACTION_CHECKER_REJECT_FOREX_FEED_GROUP";

	public static final String FOREX_FEED_GROUP_TYPE = "FOREX";

	public static final String ACTION_READ_GOLD_FEED_GROUP = "ACTION_READ_GOLD_GROUP";

	public static final String ACTION_CHECKER_APPROVE_GOLD_FEED_GROUP = "ACTION_CHECKER_APPROVE_GOLD_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_GOLD_FEED_GROUP = "ACTION_CHECKER_REJECT_GOLD_FEED_GROUP";

	public static final String ACTION_MAKER_CLOSE_DRAFT_GOLD_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GOLD_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_SUBMIT_GOLD_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_GOLD_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_GOLD_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_UPDATE_GOLD_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String GOLD_FEED_GROUP_TYPE = "GOLD";

	public static final String INSTANCE_GOLD_FEED_GROUP = "GOLD_FEED_GROUP";

	public static final String ACTION_READ_TEAM_FUNCTION_GRP = "ACTION_READ_TEAM_FUNCTION_GRP";

	public static final String ACTION_CHECKER_APPROVE_TEAM_FUNCTION_GRP = "ACTION_CHECKER_APPROVE_TEAM_FUNCTION_GRP";

	public static final String ACTION_CHECKER_REJECT_TEAM_FUNCTION_GRP = "ACTION_CHECKER_REJECT_TEAM_FUNCTION_GRP";

	public static final String ACTION_MAKER_CLOSE_DRAFT_TEAM_FUNCTION_GRP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_TEAM_FUNCTION_GRP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_CREATE = "ACTION_MAKER_SUBMIT_CREATE";

	public static final String ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_UPDATE = "ACTION_MAKER_SUBMIT_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_TEAM_FUNCTION_GRP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_TEAM_FUNCTION_GRP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_UPDATE_TEAM_FUNCTION_GRP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_SYSTEM_CLOSE_TEAM_FUNCTION_GRP = "SYSTEM_CLOSE";

	public static final String TEAM_FUNCTION_GRP_TYPE = "TEAM_FUNCTION";

	public static final String INSTANCE_TEAM_FUNCTION_GRP = "TEAM_FUNCTION_GRP";

	public static final String INSTANCE_BOND_FEED_GROUP = "BOND_FEED_GROUP";

	public static final String ACTION_READ_BOND_FEED_GROUP = "ACTION_READ_BOND_GROUP";

	public static final String ACTION_MAKER_UPDATE_BOND_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_BOND_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_BOND_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_BOND_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_BOND_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_BOND_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_BOND_FEED_GROUP = "ACTION_CHECKER_APPROVE_BOND_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_BOND_FEED_GROUP = "ACTION_CHECKER_REJECT_BOND_FEED_GROUP";

	public static final String BOND_FEED_GROUP_TYPE = "BOND";

	public static final String INSTANCE_STOCK_FEED_GROUP = "STOCK_FEED_GROUP";

	public static final String ACTION_READ_STOCK_FEED_GROUP = "ACTION_READ_STOCK_GROUP";

	public static final String ACTION_MAKER_UPDATE_STOCK_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_STOCK_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_STOCK_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_STOCK_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_STOCK_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_STOCK_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_STOCK_FEED_GROUP = "ACTION_CHECKER_APPROVE_STOCK_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_STOCK_FEED_GROUP = "ACTION_CHECKER_REJECT_STOCK_FEED_GROUP";

	public static final String STOCK_FEED_GROUP_TYPE = "STOCK";

	public static final String CORPORATE_ACTION_FEED_GROUP_TYPE = "CORPORATE_ACTION";

	public static final String INSTANCE_UNIT_TRUST_FEED_GROUP = "UNITTRUST_FEED_GROUP";

	public static final String ACTION_READ_UNIT_TRUST_FEED_GROUP = "ACTION_READ_UNIT_TRUST_GROUP";

	public static final String ACTION_MAKER_UPDATE_UNIT_TRUST_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_UNIT_TRUST_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_UNIT_TRUST_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_UNIT_TRUST_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UNIT_TRUST_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_UNIT_TRUST_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_UNIT_TRUST_FEED_GROUP = "ACTION_CHECKER_APPROVE_UNIT_TRUST_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_UNIT_TRUST_FEED_GROUP = "ACTION_CHECKER_REJECT_UNIT_TRUST_FEED_GROUP";

	public static final String UNIT_TRUST_FEED_GROUP_TYPE = "UNIT_TRUST";

	public static final String INSTANCE_STOCK_INDEX_FEED_GROUP = "STOCKIDX_FEED_GROUP";

	public static final String ACTION_READ_STOCK_INDEX_FEED_GROUP = "ACTION_READ_STOCK_INDEX_GROUP";

	public static final String ACTION_MAKER_UPDATE_STOCK_INDEX_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_STOCK_INDEX_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_STOCK_INDEX_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_STOCK_INDEX_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_STOCK_INDEX_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_STOCK_INDEX_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_STOCK_INDEX_FEED_GROUP = "ACTION_CHECKER_APPROVE_STOCK_INDEX_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_STOCK_INDEX_FEED_GROUP = "ACTION_CHECKER_REJECT_STOCK_INDEX_FEED_GROUP";

	public static final String STOCK_INDEX_FEED_GROUP_TYPE = "STOCK_INDEX";

	public static final String INSTANCE_PROPERTY_INDEX_FEED_GROUP = "PROPIDX_FEED_GROUP";

	public static final String ACTION_READ_PROPERTY_INDEX_FEED_GROUP = "ACTION_READ_PROPERTY_INDEX_GROUP";

	public static final String ACTION_MAKER_UPDATE_PROPERTY_INDEX_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_PROPERTY_INDEX_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_PROPERTY_INDEX_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_PROPERTY_INDEX_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_PROPERTY_INDEX_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_PROPERTY_INDEX_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_PROPERTY_INDEX_FEED_GROUP = "ACTION_CHECKER_APPROVE_PROPERTY_INDEX_FEED_GROUP";

	public static final String ACTION_CHECKER_REJECT_PROPERTY_INDEX_FEED_GROUP = "ACTION_CHECKER_REJECT_PROPERTY_INDEX_FEED_GROUP";

	public static final String PROPERTY_INDEX_FEED_GROUP_TYPE = "PROPERTY_INDEX";

	public static final String COMMODITY_FEED_GROUP_TYPE = "COMMODITY";

	// end of Feeds territory

	// Constants for CC Certificate
	public static final String INSTANCE_CCC = "CCC";

	public static final String CCC = "CCC";

	public static final String CCC_OWNER = "CCC_OWNER";

	public static final String CCC_COBORROWER_DETAIL = "CCC_COBORROWER";

	public static final String CCC_PLEDGOR_DETAIL = "CCC_PLEDGOR";

	public static final String CCC_PLEDGOR_COLLATERAL_LIST = "CCC_PLEDGOR_COL_LIST";

	public static final String ACTION_READ_CCC = "READ_CCC";

	public static final String ACTION_READ_CCC_ID = "READ_CCC_ID";

	public static final String ACTION_MAKER_GENERATE_CCC = "GENERATE_CCC";

	public static final String ACTION_CHECKER_APPROVE_GENERATE_CCC = "APPROVE_GENERATE_CCC";

	public static final String ACTION_CHECKER_REJECT_GENERATE_CCC = "REJECT_GENERATE_CCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_GENERATE_CCC = "EDIT_REJECTED_GENERATE_CCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_CCC = "EDIT_REJECTED_CREATE_GENERATE_CCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_CCC = "EDIT_REJECTED_UPDATE_GENERATE_CCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERATE_CCC = "CLOSE_REJECTED_GENERATE_CCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_CCC = "CLOSE_REJECTED_CREATE_GENERATE_CCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_CCC = "CLOSE_REJECTED_UPDATE_GENERATE_CCC";

	public static final String ACTION_SYSTEM_CLOSE_CCC = "SYSTEM_CLOSE";

	// Constants for SC Certificate
	public static final String INSTANCE_SCC = "SCC";

	public static final String SCC = "SCC";

	public static final String SCC_OWNER = "SCC_OWNER";

	public static final String ACTION_READ_SCC = "READ_SCC";

	public static final String ACTION_READ_SCC_ID = "READ_SCC_ID";

	public static final String ACTION_MAKER_GENERATE_SCC = "GENERATE_SCC";

	public static final String ACTION_CHECKER_APPROVE_GENERATE_SCC = "APPROVE_GENERATE_SCC";

	public static final String ACTION_CHECKER_REJECT_GENERATE_SCC = "REJECT_GENERATE_SCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_GENERATE_SCC = "EDIT_REJECTED_GENERATE_SCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_SCC = "EDIT_REJECTED_CREATE_GENERATE_SCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_SCC = "EDIT_REJECTED_UPDATE_GENERATE_SCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERATE_SCC = "CLOSE_REJECTED_GENERATE_SCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_SCC = "CLOSE_REJECTED_CREATE_GENERATE_SCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_SCC = "CLOSE_REJECTED_UPDATE_GENERATE_SCC";

	public static final String ACTION_SYSTEM_CLOSE_SCC = "SYSTEM_SCC_CLOSE";

	public static final String ACTION_SYSTEM_RESET_SCC = "SYSTEM_SCC_RESET";

	// Constants for Partial SC Certificate
	public static final String INSTANCE_PSCC = "PSCC";

	public static final String PSCC = "PSCC";

	public static final String PSCC_OWNER = "PSCC_OWNER";

	public static final String ACTION_READ_PSCC = "READ_PSCC";

	public static final String ACTION_READ_PSCC_ID = "READ_PSCC_ID";

	public static final String ACTION_MAKER_GENERATE_PSCC = "GENERATE_PSCC";

	public static final String ACTION_CHECKER_APPROVE_GENERATE_PSCC = "APPROVE_GENERATE_PSCC";

	public static final String ACTION_CHECKER_REJECT_GENERATE_PSCC = "REJECT_GENERATE_PSCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_GENERATE_PSCC = "EDIT_REJECTED_GENERATE_PSCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_PSCC = "EDIT_REJECTED_CREATE_GENERATE_PSCC";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_PSCC = "EDIT_REJECTED_UPDATE_GENERATE_PSCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERATE_PSCC = "CLOSE_REJECTED_GENERATE_PSCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_PSCC = "CLOSE_REJECTED_CREATE_GENERATE_PSCC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_PSCC = "CLOSE_REJECTED_UPDATE_GENERATE_PSCC";

	public static final String ACTION_SYSTEM_CLOSE_PSCC = "SYSTEM_PSCC_CLOSE";

	public static final String ACTION_SYSTEM_RESET_PSCC = "SYSTEM_PSCC_RESET";

	// `todo totrack indicator
	public static final String TODO_ACTION = "Todo";

	public static final String TOTRACK_ACTION = "totrack";

	// Constants for DDN
	public static final String INSTANCE_DDN = "DDN";

	// public static final String INSTANCE_LLI = "LLI";
	public static final String DDN = "DDN";

	// public static final String LLI = "LLI";
	public static final String DDN_OWNER = "DDN_OWNER";

	// public static final String LLI_OWNER = "LLI_OWNER";

	/*
	 * Constants for OLD DDN public static final String ACTION_READ_DDN =
	 * "READ_DDN"; public static final String ACTION_READ_DDN_ID =
	 * "READ_DDN_ID"; public static final String ACTION_SYSTEM_CREATE_DDN =
	 * "SYSTEM_CREATE_DDN"; public static final String ACTION_MAKER_GENERATE_DDN
	 * = "GENERATE_DDN"; public static final String
	 * ACTION_CHECKER_APPROVE_GENERATE_DDN = "APPROVE_GENERATE_DDN";
	 * 
	 * public static final String ACTION_CHECKER_REJECT_GENERATE_DDN =
	 * "REJECT_GENERATE_DDN"; public static final String
	 * ACTION_MAKER_EDIT_REJECTED_GENERATE_DDN = "EDIT_REJECTED_GENERATE_DDN";
	 * public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_DDN
	 * = "EDIT_REJECTED_CREATE_GENERATE_DDN"; public static final String
	 * ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_DDN =
	 * "EDIT_REJECTED_UPDATE_GENERATE_DDN"; public static final String
	 * ACTION_MAKER_CLOSE_REJECTED_GENERATE_DDN = "CLOSE_REJECTED_GENERATE_DDN";
	 * public static final String
	 * ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_DDN =
	 * "CLOSE_REJECTED_CREATE_GENERATE_DDN"; public static final String
	 * ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_DDN =
	 * "CLOSE_REJECTED_UPDATE_GENERATE_DDN"; public static final String
	 * ACTION_SYSTEM_CLOSE_DDN = "SYSTEM_CLOSE";
	 */

	// ck 02mar05
	// htli 07/06/05
	public static final String ACTION_MAKER_UPDATE_DDN_SUBTYPE = "MAKER_UPDATE_DDN";

	public static final String ACTION_READ_PREVIOUS_DDN = "READ_PREVIOUS_DDN";

	public static final String ACTION_READ_DDN = "READ_DDN";

	public static final String ACTION_READ_DDN_ID = "READ_DDN_ID";

	public static final String ACTION_SYSTEM_CREATE_DDN = "SYSTEM_CREATE_DDN";

	public static final String ACTION_MAKER_GENERATE_DDN = "GENERATE_DDN";

	public static final String ACTION_CHECKER_APPROVE_GENERATE_DDN = "APPROVE_GENERATE_DDN";

	public static final String ACTION_CHECKER_REJECT_GENERATE_DDN = "REJECT_GENERATE_DDN";

	public static final String ACTION_MAKER_EDIT_REJECTED_GENERATE_DDN = "EDIT_REJECTED_GENERATE_DDN";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_DDN = "EDIT_REJECTED_CREATE_GENERATE_DDN";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_DDN = "EDIT_REJECTED_UPDATE_GENERATE_DDN";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERATE_DDN = "CLOSE_REJECTED_GENERATE_DDN";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_DDN = "CLOSE_REJECTED_CREATE_GENERATE_DDN";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_DDN = "CLOSE_REJECTED_UPDATE_GENERATE_DDN";

	public static final String ACTION_SYSTEM_CLOSE_DDN = "SYSTEM_CLOSE";

	// FOR new bca DDN generations
	public static final String ACTION_MAKER_GENERATE_NEW_BCA_DDN = "GENERATE_DDN";

	// Constants for Collaboration Task
	public static final String INSTANCE_COLLATERAL_TASK = "COLLATERAL_TASK";

	public static final String INSTANCE_CC_TASK = "CC_TASK";

	public static final String TASK_REQUIRED = "REQUIRED";

	public static final String TASK_NOT_REQUIRED = "NOT_REQUIRED";

	public static final String TASK_ALREADY_CREATED = "ALREADY_CREATED";

	public static final String TASK_VIEW_ONLY = "Task_VIEW_ONLY";

	public static final String ACTION_READ_COLLATERAL_TASK = "READ_COLLATERAL_TASK";

	public static final String ACTION_READ_COLLATERAL_TASK_ID = "READ_COLLATERAL_TASK_ID";

	public static final String ACTION_SYSTEM_CLOSE_COLLATERAL_TASK = "SYSTEM_CLOSE_COLLATERAL_TASK";

	public static final String ACTION_SYSTEM_UPDATE_COLLATERAL_TASK = "SYSTEM_UPDATE_COLLATERAL_TASK";

	public static final String ACTION_MAKER_CREATE_COLLATERAL_TASK = "CREATE_COLLATERAL_TASK";

	public static final String ACTION_MAKER_UPDATE_COLLATERAL_TASK = "UPDATE_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_APPROVE_COLLATERAL_TASK = "APPROVE_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_TASK = "APPROVE_CREATE_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_COLLATERAL_TASK = "APPROVE_UPDATE_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_REJECT_COLLATERAL_TASK = "REJECT_COLLATERAL_TASK";

	public static final String ACTION_MAKER_EDIT_REJECTED_COLLATERAL_TASK = "EDIT_REJECTED_COLLATERAL_TASK";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_COLLATERAL_TASK = "EDIT_REJECTED_CREATE_COLLATERAL_TASK";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_COLLATERAL_TASK = "EDIT_REJECTED_UPDATE_COLLATERAL_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_TASK = "CLOSE_REJECTED_COLLATERAL_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_COLLATERAL_TASK = "CLOSE_REJECTED_CREATE_COLLATERAL_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_COLLATERAL_TASK = "CLOSE_REJECTED_UPDATE_COLLATERAL_TASK";

	public static final String ACTION_READ_CC_TASK = "READ_CC_TASK";

	public static final String ACTION_READ_CC_TASK_ID = "READ_CC_TASK_ID";

	public static final String ACTION_SYSTEM_CLOSE_CC_TASK = "SYSTEM_CLOSE_CC_TASK";

	public static final String ACTION_SYSTEM_UPDATE_CC_TASK = "SYSTEM_UPDATE_CC_TASK";

	public static final String ACTION_MAKER_CREATE_CC_TASK = "CREATE_CC_TASK";

	public static final String ACTION_MAKER_UPDATE_CC_TASK = "UPDATE_CC_TASK";

	public static final String STATE_REJECT_REJECTED = "REJECT_REJECTED";

	public static final String ACTION_MAKER_REJECT_CC_TASK = "MAKER_REJECT_CC_TASK";

	public static final String ACTION_CHECKER_APPROVE_REJECTED_CC_TASK = "APPROVE_REJECTED_CC_TASK";

	public static final String ACTION_CHECKER_REJECT_REJECTED_CC_TASK = "REJECT_REJECTED_CC_TASK";

	public static final String ACTION_MAKER_EDIT_REJECT_REJECTED_UPDATE_CC_TASK = "EDIT_REJECT_REJECTED_CC_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECT_REJECTED_UPDATE_CC_TASK = "CLOSE_REJECT_REJECTED_CC_TASK";

	public static final String ACTION_MAKER_REJECT_COLLATERAL_TASK = "MAKER_REJECT_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_APPROVE_REJECTED_COLLATERAL_TASK = "APPROVE_REJECTED_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_REJECT_REJECTED_COLLATERAL_TASK = "REJECT_REJECTED_COLLATERAL_TASK";

	public static final String ACTION_MAKER_EDIT_REJECT_REJECTED_UPDATE_COLLATERAL_TASK = "EDIT_REJECT_REJECTED_COLLATERAL_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECT_REJECTED_UPDATE_COLLATERAL_TASK = "CLOSE_REJECT_REJECTED_COLLATERAL_TASK";

	public static final String ACTION_CHECKER_APPROVE_CC_TASK = "APPROVE_CC_TASK";

	public static final String ACTION_CHECKER_APPROVE_CREATE_CC_TASK = "APPROVE_CREATE_CC_TASK";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CC_TASK = "APPROVE_UPDATE_CC_TASK";

	public static final String ACTION_CHECKER_REJECT_CC_TASK = "REJECT_CC_TASK";

	public static final String ACTION_MAKER_EDIT_REJECTED_CC_TASK = "EDIT_REJECTED_CC_TASK";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CC_TASK = "EDIT_REJECTED_CREATE_CC_TASK";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CC_TASK = "EDIT_REJECTED_UPDATE_CC_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CC_TASK = "CLOSE_REJECTED_CC_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CC_TASK = "CLOSE_REJECTED_CREATE_CC_TASK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CC_TASK = "CLOSE_REJECTED_UPDATE_CC_TASK";

	public static final String ACTION_MAKER_EDIT_TAT_DURATION = "EDIT_TAT_DURATION";

	public static final String SORTED_TASK_LIST = "SORTED_LIST";

	// Team Type Consts....
	public static final int TEAM_TYPE_CPC = 10;

	public static final int TEAM_TYPE_CPCCUSTODIAN = 9;

	public static final int TEAM_TYPE_SSC = 1;

	public static final int TEAM_TYPE_SC = 2;

	public static final int TEAM_TYPE_SYSTEMADMIN = 3;

	public static final int TEAM_TYPE_CPCMGR = 4;

	public static final int TEAM_TYPE_FAM = 5;

	public static final int TEAM_TYPE_GAM = 6;

	public static final int TEAM_TYPE_SCO = 7;

	public static final int TEAM_TYPE_GROUPMGM = 8;

	public static final int TEAM_TYPE_MIS = 11;

	public static final int TEAM_TYPE_CMT = 12;

	public static final int TEAM_TYPE_RCO = 13;

	public static final int TEAM_TYPE_SSCSUPPORT = 14;

	public static final int TEAM_TYPE_CPCSUPPORT = 15;

	public static final int TEAM_TYPE_CMTSUPPORT = 16;

	public static final int TEAM_TYPE_MR = 17;

	// Andy Wong, 24 June 2010: include LMS team type Id
	public static final int TEAM_TYPE_LMS_AM = 18;

	public static final int TEAM_TYPE_LMS = 19;

	public static final int TEAM_TYPE_LMS_SC = 20;

	// team_type membership consts..
	public static final int TEAM_TYPE_CPC_CUSTODIAN_MAKER = 2;

	public static final int TEAM_TYPE_CPC_MAKER = 4;

	public static final int TEAM_TYPE_CPC_CHECKER = 5;

//  public static final int TEAM_TYPE_SYS_ADMIN_MAKER = 8;
	public static final int TEAM_TYPE_SYS_ADMIN_MAKER = 1008;

//  public static final int TEAM_TYPE_SYS_ADMIN_CHECKER = 9;
	public static final int TEAM_TYPE_SYS_ADMIN_CHECKER = 1009;
	
	public static final int TEAM_TYPE_SYS_ADMIN_VIEW = 1010;
	
	public static final int TEAM_TYPE_SYS_HANDOVER_MAKER = 1011;

	public static final int TEAM_TYPE_SYS_HANDOVER_CHECKER = 1012;
	

//	public static final int TEAM_TYPE_SSC_MAKER = 10;

//	public static final int TEAM_TYPE_SSC_CHECKER = 11;
	
//	public static final int TEAM_TYPE_SC_MAKER = 6;

//	public static final int TEAM_TYPE_SC_CHECKER = 7;
	
	public static final int TEAM_TYPE_SSC_MAKER = 1006;
	
	public static final int TEAM_TYPE_CPU_MAKER_I = 1024;

	public static final int TEAM_TYPE_SSC_CHECKER = 1007;
	
	public static final int TEAM_TYPE_PARTY_MAKER = 1021;

	public static final int TEAM_TYPE_PARTY_CHECKER = 1022;
	
	public static final int TEAM_TYPE_BRANCH_MAKER = 1001;
	
	public static final int TEAM_TYPE_BRANCH_VIEW = 1003;
	
	public static final int TEAM_TYPE_BRANCH_CHECKER = 1002;
	

	public static final int TEAM_TYPE_SC_MAKER = 1004;

	public static final int TEAM_TYPE_SC_CHECKER = 1005;
	
	public static final int TEAM_TYPE_CAD_MAKER = 1025;

	public static final int TEAM_TYPE_CAD_CHECKER = 1026;

	public static final int TEAM_TYPE_CPC_MANAGER_USER = 12;

	public static final int TEAM_TYPE_FAM_USER = 13;

	public static final int TEAM_TYPE_GAM_USER = 14;

	public static final int TEAM_TYPE_SCO_USER = 15;

	public static final int TEAM_TYPE_GROUP_MANAGEMENT_USER = 16;

	public static final int TEAM_TYPE_MIS_USER = 17;

	public static final int TEAM_TYPE_CPC_CUSTODIAN_CHECKER = 3;

	public static final int TEAM_TYPE_CMT_MAKER = 18;

	public static final int TEAM_TYPE_CMT_CHECKER = 19;

	public static final int TEAM_TYPE_RCO_USER = 20;

	public static final int TEAM_TYPE_SSC_SUPPORT_USER = 21;

	public static final int TEAM_TYPE_CPC_SUPPORT_USER = 22;

	public static final int TEAM_TYPE_CMT_SUPPORT_USER = 23;

	public static final int TEAM_TYPE_MR_MAKER = 24;

	public static final int TEAM_TYPE_MR_CHECKER = 25;

	public static final int TEAM_TYPE_SCO_MAKER = 26;

	public static final int TEAM_TYPE_SCO_CHECKER = 27;

	public static final int TEAM_TYPE_AM_MAKER = 26;

	public static final int TEAM_TYPE_AM_CHECKER = 27;

	// constants for SCC/CCC status representation
	public static final int SCC_NOT_ISSUED = 0;

	public static final int SCC_PARTIAL_ISSUED = 1;

	public static final int SCC_FULL_ISSUED = 2;

	public static final int SCC_NOT_APPLICABLE = 3;

	public static final int CCC_NOT_ISSUED = 0;

	public static final int CCC_PARTIAL_ISSUED = 1;

	public static final int CCC_FULL_ISSUED = 2;

	// constants for BFL progress status
	public static final int BFL_STATUS_UNKNOWN = 0;

	public static final int BFL_NOT_REQUIRED = 1;

	public static final int BFL_REQUIRED_NOT_INIT = 2;

	public static final int BFL_IN_PROGRESS = 3;

	public static final int BFL_COMPLETED = 4;

	// constants for Activated Limit status
	public static final int ACTIVATED_LIMIT_NONE = 0;

	public static final int ACTIVATED_LIMIT_PARTIAL = 1;

	public static final int ACTIVATED_LIMIT_FULL = 2;

	// constants for commondata org code and biz segments..
	public static final String BIZ_SGMT = "5";

	public static final String ORG_CODE = "40";

	public static final String GROUP_RELATIONSHIP_CODE = "49";

	public static final String LEGAL_CONSTITUTION = "56";

	public static final String COUNTRY_CATEGORY_CODE = "CountryList";

	public static final String STATE_CATEGORY_CODE = "StateList";

	public static final String DISTRICT_CATEGORY_CODE = "DistrictList";

	public static final String MUKIM_CATEGORY_CODE = "MukimList";

	public static final String CATEGORY_OF_LAND_USE = "LAND_USE_CAT";

	public static final String CATEGORY_PROP_TYPE = "PROPERTY_TYPE";

	public static final String RISK_MITIGATION_CAT = "RISK_MITIGATION_CAT";

	// constants for host update status
	public static final String HOST_STATUS_UDPATE = "U";

	public static final String HOST_STATUS_INSERT = "I";

	public static final String HOST_STATUS_DELETE = "D";

	// Constants for waiver request
	public static final String INSTANCE_WAIVER_REQ = "WAIVER_REQ";

	public static final String INSTANCE_DEFERRAL_REQ = "DEFER_REQ";

	public static final String ACTION_READ_WAIVER_REQ = "READ_WAIVER_REQ";

	public static final String ACTION_READ_WAIVER_REQ_ID = "READ_WAIVER_REQ_ID";

	public static final String ACTION_MAKER_GENERATE_WAIVER_REQ = "GENERATE_WAIVER_REQ";

	public static final String ACTION_CHECKER_APPROVE_GENERATE_WAIVER_REQ = "APPROVE_GENERATE_WAIVER_REQ";

	public static final String ACTION_RM_APPROVE_GENERATE_WAIVER_REQ = "RM_APPROVE_GENERATE_WAIVER_REQ";

	public static final String ACTION_CHECKER_REJECT_GENERATE_WAIVER_REQ = "REJECT_GENERATE_WAIVER_REQ";

	public static final String ACTION_RM_REJECT_GENERATE_WAIVER_REQ = "RM_REJECT_GENERATE_WAIVER_REQ";

	public static final String ACTION_MAKER_EDIT_REJECTED_GENERATE_WAIVER_REQ = "EDIT_REJECTED_GENERATE_WAIVER_REQ";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_WAIVER_REQ = "EDIT_REJECTED_CREATE_GENERATE_WAIVER_REQ";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_WAIVER_REQ = "EDIT_REJECTED_UPDATE_GENERATE_WAIVER_REQ";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERATE_WAIVER_REQ = "CLOSE_REJECTED_GENERATE_WAIVER_REQ";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_WAIVER_REQ = "CLOSE_REJECTED_CREATE_GENERATE_WAIVER_REQ";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_WAIVER_REQ = "CLOSE_REJECTED_UPDATE_GENERATE_WAIVER_REQ";

	public static final String ACTION_READ_DEFERRAL_REQ = "READ_DEFER_REQ";

	public static final String ACTION_READ_DEFERRAL_REQ_ID = "READ_DEFER_REQ_ID";

	public static final String ACTION_MAKER_GENERATE_DEFERRAL_REQ = "GENERATE_DEFER_REQ";

	public static final String ACTION_CHECKER_APPROVE_GENERATE_DEFERRAL_REQ = "APPROVE_GENERATE_DEFER_REQ";

	public static final String ACTION_RM_APPROVE_GENERATE_DEFERRAL_REQ = "RM_APPROVE_GENERATE_DEFER_REQ";

	public static final String ACTION_CHECKER_REJECT_GENERATE_DEFERRAL_REQ = "REJECT_GENERATE_DEFER_REQ";

	public static final String ACTION_RM_REJECT_GENERATE_DEFERRAL_REQ = "RM_REJECT_GENERATE_DEFER_REQ";

	public static final String ACTION_MAKER_EDIT_REJECTED_GENERATE_DEFERRAL_REQ = "EDIT_REJECTED_GENERATE_DEFER_REQ";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GENERATE_DEFERRAL_REQ = "EDIT_REJECTED_CREATE_GENERATE_DEFER_REQ";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GENERATE_DEFERRAL_REQ = "EDIT_REJECTED_UPDATE_GENERATE_DEFER_REQ";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERATE_DEFERRAL_REQ = "CLOSE_REJECTED_GENERATE_DEFER_REQ";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GENERATE_DEFERRAL_REQ = "CLOSE_REJECTED_CREATE_GENERATE_DEFER_REQ";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_DEFERRAL_REQ = "CLOSE_REJECTED_UPDATE_GENERATE_DEFER_REQ";

	// constant for getting fam name and fam code for a limit profile
	public static final String FAM_NAME = "FAM_NAME";

	public static final String FAM_CODE = "FAM_CODE";

	// New for waiver and deferral generation for checklist
	public static final String STATE_PENDING_GEN_WAIVER_REQ = "PENDING_GEN_WAIVER_REQ";

	public static final String STATE_PENDING_RM_VERIFY = "PENDING_RM_VERIFY";

	public static final String STATE_PENDING_GEN_DEFERRAL_REQ = "PENDING_GEN_DEFER_REQ";

	public static final String STATE_PENDING_MGR_VERIFY = "PENDING_MGR_VERIFY";

	// SSC Due on date
	public static final int SSC_DUE_ON_DATE = 90;

	// Non borrower constants
	public static final String CUST_TYPE_NON_BORROWER_CORP = "CN";

	public static final String CUST_TYPE_NON_BORROWER_PRIV = "PN";

	public static final String CCC_STARTED = "STARTED";

	public static final String CCC_COMPLETED = "COMPLETED";

	// For deleted checklist
	public static final String NORMAL_LIST = "NORMAL_LIST";

	public static final String DELETED_LIST = "DELETED_LIST";

	// Checklist item event monitoring
	public static final String INSURANCE_POLICY = "INS_POLICY";

	public static final String PREMIUM_RECEIPT = "PREM_REC";

	public static final String CAVEAT = "CAVEAT";

	public static final String PROPERTY_VALUATION = "PROP_EVA_RPT";

	public static final String FAX_INDEMNITY = "FAX_INDEM";

	// FOR CREDIT GRADE TYPE
	public static final String CREDIT_GRADE_TYPE = "INTERNAL";

	public static final String CREDIT_GRADE_TYPE_ORG = "ORIGINAL";

	// FOR CCC ITEM LIMIT TYPE
	public static final String CCC_OUTER_LIMIT = "OUTER";

	public static final String CCC_INNER_LIMIT = "INNER";

	public static final String CCC_CB_INNER_LIMIT = "CBINNER";

	// FOR SCC ITEM LIMIT TYPE
	public static final String SCC_OUTER_LIMIT = CCC_OUTER_LIMIT;

	public static final String SCC_INNER_LIMIT = CCC_INNER_LIMIT;

	public static final String SCC_CB_INNER_LIMIT = CCC_CB_INNER_LIMIT;

	// FOR PSCC ITEM LIMIT TYPE
	public static final String PSCC_OUTER_LIMIT = CCC_OUTER_LIMIT;

	public static final String PSCC_INNER_LIMIT = CCC_INNER_LIMIT;

	public static final String PSCC_CB_INNER_LIMIT = CCC_CB_INNER_LIMIT;

	// FOR DDN ITEM LIMIT TYPE
	public static final String DDN_OUTER_LIMIT = CCC_OUTER_LIMIT;

	public static final String DDN_INNER_LIMIT = CCC_INNER_LIMIT;

	public static final String DDN_CB_INNER_LIMIT = CCC_CB_INNER_LIMIT;

	// Constants for CC Document Location
	public static final String INSTANCE_CC_DOC_LOC = "CC_DOC_LOC";

	public static final String ACTION_READ_CC_DOC_LOC = "READ_CC_DOC_LOC";

	public static final String ACTION_READ_CC_DOC_LOC_ID = "READ_CC_DOC_LOC_ID";

	public static final String ACTION_SYSTEM_CLOSE_CC_DOC_LOC = "SYSTEM_CLOSE_CC_DOC_LOC";

	public static final String ACTION_MAKER_CREATE_CC_DOC_LOC = "CREATE_CC_DOC_LOC";

	public static final String ACTION_MAKER_UPDATE_CC_DOC_LOC = "UPDATE_CC_DOC_LOC";

	public static final String ACTION_CHECKER_APPROVE_CC_DOC_LOC = "APPROVE_CC_DOC_LOC";

	public static final String ACTION_CHECKER_APPROVE_CREATE_CC_DOC_LOC = "APPROVE_CREATE_CC_DOC_LOC";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CC_DOC_LOC = "APPROVE_UPDATE_CC_DOC_LOC";

	public static final String ACTION_CHECKER_REJECT_CC_DOC_LOC = "REJECT_CC_DOC_LOC";

	public static final String ACTION_MAKER_EDIT_REJECTED_CC_DOC_LOC = "EDIT_REJECTED_CC_DOC_LOC";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CC_DOC_LOC = "EDIT_REJECTED_CREATE_CC_DOC_LOC";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CC_DOC_LOC = "EDIT_REJECTED_UPDATE_CC_DOC_LOC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CC_DOC_LOC = "CLOSE_REJECTED_CC_DOC_LOC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CC_DOC_LOC = "CLOSE_REJECTED_CREATE_CC_DOC_LOC";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CC_DOC_LOC = "CLOSE_REJECTED_UPDATE_CC_DOC_LOC";

	public static final String SEARCH_RESULT = "RESULT";

	public static final String CC_OWNER = "CC_OWNER";

	public static final String SEC_OWNER = "SEC_OWNER";

	public static final String SUB_CATEGORY = "SUB_CATEGORY";

	public static final int CHECKLIST_MAINTAIN = 1;

	public static final int CHECKLIST_RECEIPT = 2;

	public static final int CHECKLIST_SYSTEM = 3;
	
	public static final int CHECKLIST_ANNEXURE = 4;

	// Commodity main(tenance) constants
	public static final String INSTANCE_COMMODITY_MAIN_TITLEDOC = "COMMODITY_MAIN_TD";

	public static final String INSTANCE_COMMODITY_MAIN_WAREHOUSE = "COMMODITY_MAIN_WH";

	public static final String INSTANCE_COMMODITY_MAIN_PRICE = "COMMODITY_MAIN_PRICE";

	public static final String INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC = "COMMODITY_MAIN_NRIC";

	public static final String INSTANCE_COMMODITY_MAIN_PROFILE = "COMMODITY_MAIN_PROF";

	public static final String INSTANCE_COMMODITY_MAIN_UOM = "COMMODITY_MAIN_UOM";

	public static final String INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE = "COMMODITY_MAIN_SLT";

	public static final String ACTION_READ_COMMODITY_MAIN_COUNTRY = "READ_COMMODITY_MAIN_COUNTRY";

	public static final String ACTION_READ_COMMODITY_MAIN_PROFILE_GROUP = "READ_COMMODITY_MAIN_PROFILE_GROUP";

	public static final String ACTION_READ_COMMODITY_MAIN_ID = "READ_COMMODITY_MAIN_ID";

	public static final String ACTION_READ_COMMODITY_MAIN_TRXID = "READ_COMMODITY_MAIN_TRXID";

	public static final String ACTION_READ_COMMODITY_MAIN_CAT_PROD = "READ_COMMODITY_MAIN_CAT_PROD";

	public static final String ACTION_READ_COMMODITY_MAIN_SLT_GROUP = "READ_COMMODITY_MAIN_SLT_GROUP";

	public static final String ACTION_MAKER_CREATE_COMMODITY_MAIN = "MAKER_CREATE";

	public static final String ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN = "CHECKER_APPROVE_CREATE";

	public static final String ACTION_CHECKER_CREATE_REJECT_COMMODITY_MAIN = "CHECKER_REJECT_CREATE";

	public static final String ACTION_MAKER_CREATE_RESUBMIT_COMMODITY_MAIN = "MAKER_RESUBMIT_CREATE";

	public static final String ACTION_MAKER_CREATE_CLOSE_COMMODITY_MAIN = "MAKER_CLOSE_CREATE";

	public static final String ACTION_MAKER_SAVE_COMMODITY_MAIN = "MAKER_SAVE";

	public static final String ACTION_SYSTEM_UPDATE_COMMODITY_MAIN = "SYSTEM_UPDATE";

	public static final String ACTION_MAKER_UPDATE_COMMODITY_MAIN = "MAKER_UPDATE";

	public static final String ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_UPDATE_REJECT_COMMODITY_MAIN = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_MAKER_UPDATE_RESUBMIT_COMMODITY_MAIN = "MAKER_RESUBMIT_UPDATE";

	public static final String ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_MAKER_DELETE_COMMODITY_MAIN = "MAKER_DELETE";

	public static final String ACTION_CHECKER_DELETE_APPROVE_COMMODITY_MAIN = "CHECKER_APPROVE_DELETE";

	public static final String ACTION_CHECKER_DELETE_REJECT_COMMODITY_MAIN = "CHECKER_REJECT_DELETE";

	public static final String ACTION_MAKER_DELETE_RESUBMIT_COMMODITY_MAIN = "MAKER_RESUBMIT_DELETE";

	public static final String ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN = "MAKER_CLOSE_DELETE";

	// ** CommodityMainInfo constants end here.

	// Constants for commodity deal
	public static final String INSTANCE_COMMODITY_DEAL = "COMMODITY_DEAL";

	public static final String ACTION_READ_DEAL_BY_DEALID = "READ_BY_DEALID";

	public static final String ACTION_READ_DEAL_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_MAKER_UPDATE_DEAL = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CREATE_DEAL = "MAKER_CREATE";

	public static final String ACTION_MAKER_SAVE_DEAL = "MAKER_SAVE";

	public static final String ACTION_MAKER_CLOSE_DEAL = "MAKER_CLOSE";

	public static final String ACTION_MAKER_CANCEL_UPDATE_DEAL = "MAKER_CANCEL_UPDATE";

	public static final String ACTION_MAKER_CANCEL_CREATE_DEAL = "MAKER_CANCEL_CREATE";

	public static final String ACTION_MAKER_CANCEL_CLOSE_DEAL = "MAKER_CANCEL_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_CLOSE_DEAL = "CHECKER_APPROVE_CLOSE";

	public static final String ACTION_CHECKER_REJECT_CLOSE_DEAL = "CHECKER_REJECT_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_DEAL = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_REJECT_UPDATE_DEAL = "CHECKER_REJECT_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_CREATE_DEAL = "CHECKER_APPROVE_CREATE";

	public static final String ACTION_CHECKER_REJECT_CREATE_DEAL = "CHECKER_REJECT_CREATE";

	public static final String ACTION_SYSTEM_VALUATE_DEAL = "SYSTEM_VALUATE";

	// actions for multi-level approval for creating deal
	public static final String ACTION_CHECKER_FORWARD_CREATE_DEAL = "CHECKER_FORWARD_CREATE";

	public static final String ACTION_FAM_CONFIRM_REJECT_CREATE_DEAL = "FAM_CONFIRM_REJECT_CREATE";

	public static final String ACTION_FAM_REJECT_CREATE_DEAL = "FAM_REJECT_CREATE";

	public static final String ACTION_OFFICER_FORWARD_CREATE_DEAL = "OFFICER_FORWARD_CREATE";

	public static final String ACTION_OFFICER_REJECT_CREATE_DEAL = "OFFICER_REJECT_CREATE";

	public static final String ACTION_OFFICER_APPROVE_CREATE_DEAL = "OFFICER_APPROVE_CREATE";

	// actions for multi-level approval for updating deal
	public static final String ACTION_CHECKER_FORWARD_UPDATE_DEAL = "CHECKER_FORWARD_UPDATE";

	public static final String ACTION_FAM_CONFIRM_REJECT_UPDATE_DEAL = "FAM_CONFIRM_REJECT_UPDATE";

	public static final String ACTION_FAM_REJECT_UPDATE_DEAL = "FAM_REJECT_UPDATE";

	public static final String ACTION_OFFICER_FORWARD_UPDATE_DEAL = "OFFICER_FORWARD_UPDATE";

	public static final String ACTION_OFFICER_REJECT_UPDATE_DEAL = "OFFICER_REJECT_UPDATE";

	public static final String ACTION_OFFICER_APPROVE_UPDATE_DEAL = "OFFICER_APPROVE_UPDATE";

	// actions for multi-level approval for closing deal
	public static final String ACTION_CHECKER_FORWARD_CLOSE_DEAL = "CHECKER_FORWARD_CLOSE";

	public static final String ACTION_FAM_CONFIRM_REJECT_CLOSE_DEAL = "FAM_CONFIRM_REJECT_CLOSE";

	public static final String ACTION_FAM_REJECT_CLOSE_DEAL = "FAM_REJECT_CLOSE";

	public static final String ACTION_OFFICER_FORWARD_CLOSE_DEAL = "OFFICER_FORWARD_CLOSE";

	public static final String ACTION_OFFICER_REJECT_CLOSE_DEAL = "OFFICER_REJECT_CLOSE";

	public static final String ACTION_OFFICER_APPROVE_CLOSE_DEAL = "OFFICER_APPROVE_CLOSE";

	public static final String CATEGORY_COMMODITY_OTHER_MARKET_UOM = "MKT_OTHER_UOM";

	public static final String CATEGORY_COMMODITY_METRIC_MARKET_UOM = "MKT_METRIC_UOM";

	public static final String CATEGORY_COMMODITY_DEAL_PRICE_TYPE = "DEAL_PRICE_TYPE";

	public static final String DEAL_TYPE_COLLATERAL_POOL = "CP";

	public static final String DEAL_TYPE_SPECIFIC_TRANSACTION = "ST";

	public static final String CASH_TYPE_REQUIREMENT = "R";

	public static final String CASH_TYPE_SETOFF = "S";

	public static final String CASH_TYPE_COMFORT = "C";

	public static final String DOC_TYPE_WAREHOUSE_RECEIPT = "Warehouse Receipt";

	public static final String DOC_TYPE_WAREHOUSE_RECEIPT_N = "Warehouse Receipt (Negotiable)";

	public static final int NO_PENDING_TRX = 0;

	public static final int HAS_PENDING_CHECKLIST_TRX = 1;

	public static final int HAS_PENDING_DOC_LOC_TRX = 2;

	public static final String DEAL_NO_PREFIX = "CF";

	public static final String COLTYPE_NOCOLLATERAL = "CL001";

	public static final String COL_SUBTYPE_NOCOLLATERAL = "NOCOLLATERAL";

	/** CR CMS-571**Starts **************** */
	/** OTHER Collateral of type Othera. */
	public static final String COLTYPE_OTHERS_OTHERSA = "OT900";

	public static final String SECURITY_TYPE_OTHERS = "OT";

	/** CR CMS-571**Ends ****************** */

	/* ACTION OF OFFICE */
	public static final String ACTION_FORWARD = "FORWARD";

	public static final String ACTION_BACKWARD = "BACKWARD";

	public static final String ACTION_APPROVE = "APPROVE";

	public static final String ACTION_REJECT = "REJECT";

	/* ACTION OF CUSTOMER TEST */
	public static final String ACTION_CREATE_CUSTOMER_TEST = "CREATE_CUSTOMER_TEST";

	public static final String ACTION_READ_CUSTOMER_TEST = "READ_CUSTOMER_TEST";

	/* BFL TYPE */
	public static final String BCA_TYPE_NEW = "NEW";

	public static final String BCA_TYPE_RENEWAL = "RENEWAL";

	/* CMS Notification */
	public static final String STATE_NOTIFICATION_DELETED = "DELETED";

	public static final String PARAM_NOTIFICATION_START_INDEX = "startIndex";

	public static final String ACTION_READ_CO_BORROWER_LIMIT_BY_LPID = "ACTION_READ_CO_BORROWER_LIMIT_BY_LPID";

	/* Customer Type */
	public static final String CUSTOMER_TYPE_BORROWER = "B";

	public static final String CUSTOMER_TYPE_NONBORROWER = "NB";

	/* CC/Security collaboration task */
	public static final int COL_TASK_MAX_HISTORY = 20;

	/* Event Monitor Batch jobs */
	public static final String BATCH_TRX_TIMEOUT_SEC_PROP = "trx.timeout.seconds";

	public static final String BATCH_TRX_COMMIT_LOOPS_PROP = "trx.commit.after.loops";

	public static final String BATCH_THREAD_COUNT = "thread.count";

	public static final String BATCH_THREAD_SLEEP_TIME = "thread.sleep.time";

	/* Report batch jobs */
	public static final String BATCH_REPORT_TRX_TIMEOUT_SEC = "rpt.trx.timeout.seconds";

	public static final String SEC_APPORTION_NA = "N";

	public static final String SEC_APPORTION_PERC = "P";

	public static final String SEC_APPORTION_AMT = "A";

	public static final String SEQUENCE_SEC_APPORTION = "SEC_APPORTIONMENT_SEQ";

	public static final String SEQUENCE_STAGE_APPORTION = "STG_APPORTIONMENT_SEQ";

	// Credit Risk Module
	public static final String INSTANCE_POLICY_CAP = "POLICY_CAP";

	public static final String ACTION_READ_POLICY_CAP = "READ_POLICY_CAP";

	public static final String ACTION_READ_POLICY_CAP_ID = "READ_POLICY_CAP_ID";

	public static final String ACTION_MAKER_UPDATE_POLICY_CAP = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CLOSE_POLICY_CAP = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_POLICY_CAP = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_POLICY_CAP = "CHECKER_REJECT";

	public static final String INSTANCE_POLICY_CAP_GROUP = "POLICY_CAP";

	public static final String ACTION_READ_POLICY_CAP_GROUP = "READ_POLICY_CAP_GROUP";

	public static final String ACTION_READ_POLICY_CAP_GROUP_ID = "READ_POLICY_CAP_GROUP_ID";

	public static final String ACTION_MAKER_UPDATE_POLICY_CAP_GROUP = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CLOSE_POLICY_CAP_GROUP = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_POLICY_CAP_GROUP = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_POLICY_CAP_GROUP = "CHECKER_REJECT";

	// autovaluation property
	public static final String INSTANCE_AUTO_VAL_PARAM = "AUTO_VAL_PARAM";

	public static final String ACTION_READ_PRPA = "READ_PRPA";

	public static final String ACTION_READ_PRPA_ID = "READ_PRPA_ID";

	// public static final String ACTION_SYSTEM_CLOSE_PRPA =
	// "SYSTEM_CLOSE_PRPA";
	public static final String ACTION_MAKER_CREATE_PRPA = "CREATE_PRPA";

	public static final String ACTION_MAKER_UPDATE_PRPA = "UPDATE_PRPA";

	public static final String ACTION_MAKER_DELETE_PRPA = "DELETE_PRPA";

	public static final String ACTION_CHECKER_APPROVE_PRPA = "APPROVE_PRPA";

	public static final String ACTION_CHECKER_APPROVE_CREATE_PRPA = "APPROVE_CREATE_PRPA";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_PRPA = "APPROVE_UPDATE_PRPA";

	public static final String ACTION_CHECKER_APPROVE_DELETE_PRPA = "APPROVE_DELETE_PRPA";

	public static final String ACTION_CHECKER_REJECT_PRPA = "REJECT_PRPA";

	public static final String ACTION_MAKER_EDIT_REJECTED_PRPA = "EDIT_REJECTED_PRPA";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_PRPA = "EDIT_REJECTED_CREATE_PRPA";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_PRPA = "EDIT_REJECTED_UPDATE_PRPA";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_PRPA = "EDIT_REJECTED_DELETE_PRPA";

	public static final String ACTION_MAKER_CLOSE_REJECTED_PRPA = "CLOSE_REJECTED_PRPA";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_PRPA = "CLOSE_REJECTED_CREATE_PRPA";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_PRPA = "CLOSE_REJECTED_UPDATE_PRPA";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_PRPA = "CLOSE_REJECTED_DELETE_PRPA";

	public static final String SEQUENCE_CMS_PROPERTY_PARAMETERS_SEQ = "CMS_PROPERTY_PARAMETERS_SEQ";

	public static final String SEQUENCE_CMS_VALUATION_PARAMETERS_SEQ = "CMS_VALUATION_PARAMETERS_SEQ";

	// ************* Constants for MR - Maintain Interest Rate
	// *********************
	public static final String INSTANCE_INT_RATE = "INT_RATE";

	public static final String ACTION_MAKER_CREATE_INT_RATE = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_INT_RATE = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_INT_RATE = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_INT_RATE = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_INT_RATE = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_INT_RATE = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_INT_RATE = "CHECKER_REJECT";

	public static final String ACTION_READ_INT_RATE_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_INT_RATE = "READ";

	// Credit Risk Parameter
	public static final String SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ = "CMS_CREDIT_RISK_PARAM_SEQ";

	public static final String SEQUENCE_CMS_STG_CREDIT_RISK_PARAM_REF_SEQ = "CMS_STG_CR_RISK_PARAM_REF_SEQ";

	// ************* Constants for MR - Maintain Interest Rate
	// *********************
	public static final String INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER = "CRP_SHARE_COUNTER";

	public static final String INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST = "CRP_UNIT_TRUST";

	// action for maker
	public static final String ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE = "CREDIT_RISK_PARAM_MAKER_UPDATE";

	public static final String ACTION_CREDIT_RISK_PARAM_MAKER_UPDATE_REJECTED = "CREDIT_RISK_PARAM_MAKER_UPDATE_REJ";

	public static final String ACTION_CREDIT_RISK_PARAM_MAKER_CLOSE = "CREDIT_RISK_PARAM_MAKER_CLOSE";

	// action for checker
	public static final String ACTION_CREDIT_RISK_PARAM_CHECKER_APPROVE = "CREDIT_RISK_PARAM_CHECKER_APPROVE";

	public static final String ACTION_CREDIT_RISK_PARAM_CHECKER_REJECT = "CREDIT_RISK_PARAM_CHECKER_REJECT";

	// action for checker and maker
	public static final String ACTION_CREDIT_RISK_PARAM_READBY_TRXID = "CREDIT_RISK_PARAM_READ_BY_TRXID";

	public static final String ACTION_CREDIT_RISK_PARAM_READ = "CREDIT_RISK_PARAM_READ";

	// Contract Financing Module
	public static final String INSTANCE_CONTRACT_FINANCING = "CONTRACT_FINANCE";

	public static final String ACTION_READ_CONTRACT_FINANCING = "READ_CONTRACT_FINANCE";

	public static final String ACTION_READ_CONTRACT_FINANCING_ID = "READ_CONTRACT_FINANCE_ID";

	public static final String ACTION_MAKER_CREATE_CONTRACT_FINANCING = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_CONTRACT_FINANCING = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_CONTRACT_FINANCING = "MAKER_SAVE";

	public static final String ACTION_MAKER_CLOSE_CONTRACT_FINANCING = "MAKER_CLOSE";

	public static final String ACTION_MAKER_CLOSE_UPDATE_CONTRACT_FINANCING = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_MAKER_CLOSE_DELETE_CONTRACT_FINANCING = "MAKER_CLOSE_DELETE";

	public static final String ACTION_MAKER_DELETE_CONTRACT_FINANCING = "MAKER_DELETE";

	public static final String ACTION_CHECKER_APPROVE_CONTRACT_FINANCING = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_CONTRACT_FINANCING = "CHECKER_REJECT";

	// Bridging Loan Module
	public static final String INSTANCE_BRIDGING_LOAN = "BRIDGING_LOAN";

	public static final String ACTION_READ_BRIDGING_LOAN = "READ_BRIDGING_LOAN";

	public static final String ACTION_READ_BRIDGING_LOAN_ID = "READ_BRIDGING_LOAN_ID";

	public static final String ACTION_MAKER_CREATE_BRIDGING_LOAN = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_BRIDGING_LOAN = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_BRIDGING_LOAN = "MAKER_SAVE";

	public static final String ACTION_MAKER_CLOSE_BRIDGING_LOAN = "MAKER_CLOSE_CREATE";

	public static final String ACTION_MAKER_CLOSE_UPDATE_BRIDGING_LOAN = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_MAKER_CLOSE_DELETE_BRIDGING_LOAN = "MAKER_CLOSE_DELETE";

	public static final String ACTION_MAKER_DELETE_BRIDGING_LOAN = "MAKER_DELETE";

	public static final String ACTION_CHECKER_APPROVE_BRIDGING_LOAN = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_BRIDGING_LOAN = "CHECKER_REJECT";

	/** Constants for Common Code Category Entry */

	/** Constants used by the database */
	public static final String SEQUENCE_COMMON_CODE_ENTRY = "COMMON_CODE_CATEGORY_ENTRY_SEQ"; // the

	// seq
	// for
	// the
	// primary
	// key
	// for
	// the
	// actual
	// table

	public static final String SEQUENCE_COMMON_CODE_ENTRY_STAGE = "COMM_CODE_ENTRY_STG_SEQ"; // the

	// seq
	// for
	// primary
	// key
	// for
	// the
	// staging
	// table

	public static final String SEQUENCE_COMMON_CODE_ENTRY_STAGEREF = "COMM_CODE_ENTRY_STGREF_SEQ"; // the

	// seq
	// for
	// staging
	// reference
	// id
	// used
	// by
	// the
	// transaction
	// table
	// and
	// the
	// staging
	// table
	// to
	// group
	// the
	// data

	/** Constant for transcations */
	public static final String COMMON_CODE_ENTRY_INSTANCE_NAME = "COMMON_CODE_ENTRY"; // used

	// by
	// the
	// controller
	// class
	// this
	// is
	// the
	// transaction
	// type

	/** Constants used the XXXOperation classes */
	public static final String COMMON_CODE_ENTRY_UPDATE = "COMMON_CODE_ENTRY_UPDATE";

	public static final String COMMON_CODE_ENTRY_EDIT = "COMMON_CODE_ENTRY_EDIT";

	public static final String COMMON_CODE_ENTRY_REJECT = "COMMON_CODE_ENTRY_REJECT";

	public static final String COMMON_CODE_ENTRY_APPROVE = "COMMON_CODE_ENTRY_APPROVE";

	public static final String COMMON_CODE_ENTRY_CLOSE = "COMMON_CODE_ENTRY_CLOSE";

	public static final String COMMON_CODE_ENTRY_READ = "COMMON_CODE_ENTRY_READ";

	public static final String COMMON_CODE_ENTRY_READ_BY_REF = "COMMON_CODE_ENTRY_READ_BY_REF";

	public static final String SEQUENCE_SHARE_SECURITY = "SHARE_SECURITY_SEQ";

	public static final String CATEGORY_SOURCE_SYSTEM = "37";

	public static final String CATEGORY_SEC_SOURCE = "SEC_SOURCE";

	/**
	 * Property name for User Dormant batch job denoting no of days user has not
	 * logged in.. i.e dormant
	 */
	public static final String DAYS_DORMANT = "integrosys.login.days.dormant";

	public static final String SEQUENCE_SECURITY_INSTRUMENT = "SECURITY_INSTRUMENT_SEQ";

	public static final String SEQUENCE_SECURITY_INSTRUMENT_STAGE = "SECURITY_INSTRUMENT_ST_SEQ";

	// ************* Constants for Trading Book *********************
	public static final String ELIGIBLE_MARGIN_CASH = "C";

	public static final String ELIGIBLE_MARGIN_BOND = "B";

	public static final String AA_TYPE_TRADE = "TRADING";

	public static final String AA_TYPE_BANK = "BANKING";

	public static final String ISDA_CSA_TYPE = "ISDA_CSA";

	public static final String GMRA_TYPE = "GMRA";

	public static final String SEQUENCE_AGREEMENT = "AGREEMT_SEQ";

	public static final String SEQUENCE_AGREEMENT_STAGE = "AGREEMT_ST_SEQ";

	public static final String SEQUENCE_THRESHOLD_RATING = "THRES_RATE_SEQ";

	public static final String SEQUENCE_THRESHOLD_RATING_STAGE = "THRES_RATE_ST_SEQ";

	public static final String SEQUENCE_TB_DEAL = "TB_DEAL_SEQ";

	public static final String SEQUENCE_TB_DEAL_STAGE = "TB_DEAL_ST_SEQ";

	public static final String SEQUENCE_DEAL_VAL = "TB_DEAL_VAL_SEQ";

	public static final String SEQUENCE_DEAL_VAL_STAGE = "TB_DEAL_VAL_ST_SEQ";

	public static final String SEQUENCE_CASH_MARGIN = "CASH_MARGIN_SEQ";

	public static final String SEQUENCE_CASH_MARGIN_STAGE = "CASH_MARGIN_ST_SEQ";

	public static final String INSTANCE_ISDA_DEAL_VAL = "ISDA_DEAL_VAL";

	public static final String ACTION_MAKER_CREATE_ISDA_DEAL_VAL = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_ISDA_DEAL_VAL = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_ISDA_DEAL_VAL = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_ISDA_DEAL_VAL = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_ISDA_DEAL_VAL = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_ISDA_DEAL_VAL = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_ISDA_DEAL_VAL = "CHECKER_REJECT";

	public static final String ACTION_READ_ISDA_DEAL_VAL_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_ISDA_DEAL_VAL = "READ";

	public static final String INSTANCE_GMRA_DEAL_VAL = "GMRA_DEAL_VAL";

	public static final String ACTION_MAKER_CREATE_GMRA_DEAL_VAL = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_GMRA_DEAL_VAL = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_GMRA_DEAL_VAL = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_GMRA_DEAL_VAL = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_GMRA_DEAL_VAL = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_GMRA_DEAL_VAL = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_GMRA_DEAL_VAL = "CHECKER_REJECT";

	public static final String ACTION_READ_GMRA_DEAL_VAL_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_GMRA_DEAL_VAL = "READ";

	public static final String INSTANCE_GMRA_DEAL = "GMRA_DEAL";

	public static final String ACTION_READ_GMRA_DEAL_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_GMRA_DEAL = "READ";

	public static final String ACTION_MAKER_CREATE_GMRA_DEAL = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_GMRA_DEAL = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CLOSE_GMRA_DEAL = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_GMRA_DEAL = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_GMRA_DEAL = "CHECKER_REJECT";

	public static final String ACTION_MAKER_CLOSE_UPDATE_GMRA_DEAL = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_MAKER_DELETE_GMRA_DEAL = "MAKER_DELETE";

	public static final String INSTANCE_CASH_MARGIN = "CASH_MARGIN";

	public static final String ACTION_MAKER_CREATE_CASH_MARGIN = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_CASH_MARGIN = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_CASH_MARGIN = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_CASH_MARGIN = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_CASH_MARGIN = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_CASH_MARGIN = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_CASH_MARGIN = "CHECKER_REJECT";

	public static final String ACTION_READ_CASH_MARGIN_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_CASH_MARGIN = "READ";

	// Constants for predeal
	public static final String INSTANCE_PRE_DEAL = "PRE_DEAL";

	public static final String ACTION_READ_PRE_DEAL_BY_TRX_ID = "READ_BY_TRX_ID";

	public static final String ACTION_READ_PRE_DEAL_BY_FEED_ID = "READ_BY_FEED_ID";

	public static final String ACTION_READ_PRE_DEAL = "READ";

	public static final String ACTION_MAKER_CREATE_EAR_MARK = "MAKER_CREATE_EAR_MARK";

	public static final String ACTION_MAKER_TRANSFER_EAR_MARK = "MAKER_TRANSFER_EAR_MARK";

	public static final String ACTION_MAKER_DELETE_EAR_MARK = "MAKER_DELETE_EAR_MARK";

	public static final String ACTION_MAKER_RELEASE_EAR_MARK = "MAKER_RELEASE_EAR_MARK";

	public static final String ACTION_MAKER_CLOSE_EAR_MARK = "MAKER_CLOSE_EAR_MARK";

	public static final String ACTION_MAKER_UPDATE_REJECTED_EAR_MARK = "MAKER_UPDATE_REJECTED_EAR_MARK";

	public static final String ACTION_CHECKER_APPROVE_EAR_MARK = "CHECKER_APPROVE_EAR_MARK";

	public static final String ACTION_CHECKER_REJECT_EAR_MARK = "CHECKER_REJECT_EAR_MARK";

	// ************* Constants for Liquidation Management *********************
	public static final String SEQUENCE_LIQUIDATION = "LIQUIDATION_SEQ";

	public static final String SEQUENCE_RECOVERY = "SEC_RECOVERY_SEQ";

	public static final String SEQUENCE_RECOVERY_INCOME = "SEC_RECOVER_INC_SEQ";

	public static final String SEQUENCE_RECOVERY_EXPENSE = "SEC_RECOVER_EXPENSE_SEQ";

	public static final String INSTANCE_LIQUIDATION = "LIQUIDATION";

	public static final String ACTION_MAKER_CREATE_LIQUIDATION = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_LIQUIDATION = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_LIQUIDATION = "MAKER_SAVE";

	public static final String ACTION_MAKER_CANCEL_LIQUIDATION = "MAKER_CANCEL";

	public static final String ACTION_MAKER_CLOSE_LIQUIDATION = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_LIQUIDATION = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_LIQUIDATION = "CHECKER_REJECT";

	public static final String ACTION_READ_LIQUIDATION_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_LIQUIDATION_BY_TRXREFID = "READ_BY_TRXREFID";

	public static final String ACTION_READ_LIQUIDATION = "READ";

	public static final String SEQUENCE_LIQUIDATION_SEQ = "LIQUIDATION_SEQ";

	public static final String SOURCE_SYSTEM_MARS_SP = "908";

	public static final String SEQUENCE_CMS_EAR_MARK_SEQ = "CMS_EARMARK_SEQ";

	public static final String SEQUENCE_CMS_EAR_MARK_GROUP_SEQ = "CMS_EARMARK_GROUP_SEQ";

	// ************* Constants for Non Performing Loan *********************
	public static final String INSTANCE_NPL = "NPL";

	public static final String SEQUENCE_NPL = "NPL_SEQ";

	// ************* Constants for Nominee *********************
	public static final String SEQUENCE_CMS_NOMINEES = "CMS_NOMINEES_SEQ";

	// ************* Constants for Documentation status *********************
	public static final String DOCUMENTATION_STATUS_NEW = "NEW";
	//By Abhijit R 
	//public static final String DOCUMENTATION_STATUS_COMPLIED = "COMPLIED";
	
	public static final String DOCUMENTATION_STATUS_COMPLIED = "IN_PROGRESS";
	
	public static final String DOCUMENTATION_STATUS_CANCELLED = "CANCELLED";

	public static final String SOURCE_SYSTEM_CMS = "CMS";
	public static final String SOURCE_SYSTEM_RLOS = "RLOS";

	/*
	 * // Account Related Trx Constants public static final String
	 * INSTANCE_ACCOUNT = "ACCOUNT";
	 */

	// Base currency code constant in property file
	public static final String BASE_CURRENCY_CODE = "baseCurrencyCode";

	public static final String BASE_CURRENCY_USER = "USER";

	public static final String BASE_EXCHANGE_CURRENCY = "baseExchangeCcy";

	// Manual Input constant
	public static final String MANUAL_INPUT_TRX_TYPE = "MANUAL";

	// Added due to error
	String INSTANCE_NON_PERFORMING_LOAN = "INSTANCE_NON_PERFORMING_LOAN";

	String SEQUENCE_CMS_NON_PERFORMING_LOAN = "SEQUENCE_CMS_NON_PERFORMING_LOAN";

	String SEQUENCE_CMS_STAGE_NON_PERFORMING_LOAN = "SEQUENCE_CMS_STAGE_NON_PERFORMING_LOAN";

	public static final String BATCH_INPUT_TRX_TYPE = "BATCH";

	public static final String STOCK_FEED_STOCK_TYPE = "001";

	public static final String FEED_SHARE_TYPE_UNIT_TRUST = "005";

	public static final String BANK_GROUP_ENTITY = "001";

	public static final String SHARE_STATUS_NORMAL = "1";

	public static final String SHARE_STATUS_PN4 = "2";

	public static final String SHARE_STATUS_PN17 = "3";

	public static final String SHARE_STATUS_DESIGNATED = "4";

	public static final String SHARE_STATUS_OTHERS = "5";

	public static final String SHARE_STATUS_SUSPENDED = "6";

	public static final String SOURCE_SYSTEM_NOMINEES = "900";

	public static final String SOURCE_SYSTEM_MARSHA = "992";

	// ************* Constants for Customer Details - Address Type
	// *********************
	public static final String REGISTERED = "REGISTERED";
	
	public static final String CORPORATE = "CORPORATE";

	public static final String OFFICE = "OFFICE";

	public static final String YES = "Y";
	public static final String NO = "N";

	// **** Maintain Team - CMS Business Segment ****************
	public static final String REQUIRE_CMS_SEGMENT = "component.team.require.cms.segment";

	// ***** Maintain Team - Org Code List filter *****************
	public static final String REQUIRE_ORG_CODE_FILTER = "component.team.require.org.code.filter";

	// ***** Maintain Team - Business Segment ******************
	public static final String REQUIRE_BIZ_SEGMENT = "component.team.require.segment";

	// ********** Membership type ID *************************
	public static final String MEMBERSHIP_TYPE_MAKER = "1";

	public static final String MEMBERSHIP_TYPE_CHECKER = "2";

	public static final String MEMBERSHIP_TYPE_USER = "3";

	// ************ Maker checker same role ***************
	public static final String MAKER_CHECKER_SAME_USER = "component.team.maker.checker.same.user";

	// ************* Constants for MF template *********************
	public static final String SEQUENCE_MF_TEMPLATE = "MF_TEMPLATE_SEQ";

	public static final String SEQUENCE_MF_TEMPLATE_STAGE = "ST_MF_TEMPLATE_SEQ";

	public static final String SEQUENCE_MF_SEC_SUBTYPE = "MF_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_MF_SEC_SUBTYPE_STAGE = "ST_MF_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_MF_ITEM = "MF_ITEM_SEQ";

	public static final String SEQUENCE_MF_ITEM_STAGE = "ST_MF_ITEM_SEQ";

	public static final String INSTANCE_MF_TEMPLATE = "MF_TEMPLATE";

	public static final String ACTION_READ_MF_TEMPLATE_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_MF_TEMPLATE = "READ";

	public static final String ACTION_MAKER_CREATE_MF_TEMPLATE = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_MF_TEMPLATE = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CLOSE_MF_TEMPLATE = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_MF_TEMPLATE = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_MF_TEMPLATE = "CHECKER_REJECT";

	public static final String ACTION_MAKER_CLOSE_UPDATE_MF_TEMPLATE = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_MAKER_DELETE_MF_TEMPLATE = "MAKER_DELETE";

	public static final String MF_STATUS_ACTIVE = "A";

	public static final String MF_STATUS_INACTIVE = "I";

	// ************* Constants for MF Checklist *********************
	public static final String SEQUENCE_MF_CHECKLIST = "MF_CHECKLIST_SEQ";

	public static final String SEQUENCE_MF_CHECKLIST_STAGE = "ST_MF_CHECKLIST_SEQ";

	public static final String SEQUENCE_MF_CHECKLIST_ITEM = "MF_CHECKLIST_ITEM_SEQ";

	public static final String SEQUENCE_MF_CHECKLIST_ITEM_STAGE = "ST_MF_CHECKLIST_ITEM_SEQ";

	public static final String INSTANCE_MF_CHECKLIST = "MF_CHECKLIST";

	public static final String ACTION_READ_MF_CHECKLIST_BY_TRXID = "READ_BY_TRXID";

	public static final String ACTION_READ_MF_CHECKLIST = "READ";

	public static final String ACTION_MAKER_CREATE_MF_CHECKLIST = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_MF_CHECKLIST = "MAKER_UPDATE";

	public static final String ACTION_MAKER_CLOSE_MF_CHECKLIST = "MAKER_CLOSE";

	public static final String ACTION_CHECKER_APPROVE_MF_CHECKLIST = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_MF_CHECKLIST = "CHECKER_REJECT";

	public static final String ACTION_MAKER_CLOSE_UPDATE_MF_CHECKLIST = "MAKER_CLOSE_UPDATE";

	// ************ Constants for To Do - Pending Cases ************
	public static final String EXISTING_CASES = "E";

	public static final String NEW_CASES = "N";

	public static final String CREDIT_FOLDER = "CF";

	public static final String FILTER_BY_ORG = "ORG_CODE";

	public static final String FILTER_BY_CMS_SEGMENT = "CMS_SEGMENT";

	public static final String SORT_BY_ORG = "ORG_CODE";

	public static final String SORT_BY_CMS_SEGMENT = "SEGMENT_VALUE";

	public static final String SORT_BY_NAME = "LSP_SHORT_NAME";

	public static final String SORT_BY_LE_ID = "LLP_LE_ID";

	// ************ Constants for Limit Secured standard code ************
	public static final String CATEGORY_CODE_LIMIT_SECURED = "30";

	public static final String LIMIT_SECURED_TYPE_SECURED = "SECURED";

	public static final String LIMIT_SECURED_TYPE_CLEAN = "CLEAN";

	// ************ Constants for Limit Secured standard code ************
	public static final String CATEGORY_CODE_PLEDGOR_RELNSHIP = "RELATIONSHIP";

	// *********** Constnat for Property to maintain account ***************
	public static final String ALLOW_MAINTAIN_ACCOUNT = "build.maintain.limit.account";

	// ************* Default country and currency constant ******************
	public static final String DEFAULT_COUNTRY = "defaultCountryCode";

	public static final String DEFAULT_CURRENCY = "baseExchangeCcy";

	// *********** Constants for Good Status ***************
	public static final String GOOD_STATUS_NEW = "N";

	public static final String GOOD_STATUS_USED = "U";

	public static final String GOOD_STATUS_RECONDITIONED = "R";

	public static final String GOOD_STATUS_IMPORTED_REGISTRATION = "I";

	public static final String GOOD_STATUS_IMPORTED_NON_REGISTRATION = "G";

	// *********** Constants for Los Payment Indicator ***************

	public static final String LOS_PAYMENT_INDICATOR_PBR = "PBR";

	public static final String LOS_PAYMENT_INDICATOR_PBT = "PBT";

	public static final String LOS_PAYMENT_INDICATOR_NA = "NA";

	// *********** Constants for Valuation types ***************
	public static final String VALUATION_TYPE_FORMAL = "F";

	public static final String VALUATION_TYPE_BRANCH = "B";

	public static final String VALUATION_TYPE_APPRAISED = "A";

	public static final String VALUATION_TYPE_VERBAL = "V";

	// WLS 17 Jan 2008: Property index module
	// ************* Constants for Property Index *********************
	public static final String INSTANCE_PROPERTY_IDX = "PROPERTY_INDEX";

	public static final String ACTION_READ_PRIDX = "READ_PRIDX";

	public static final String ACTION_READ_PRIDX_ID = "READ_PRIDX_ID";

	public static final String ACTION_MAKER_CREATE_PRIDX = "CREATE_PRIDX";

	public static final String ACTION_MAKER_UPDATE_PRIDX = "UPDATE_PRIDX";

	public static final String ACTION_MAKER_DELETE_PRIDX = "DELETE_PRIDX";

	public static final String ACTION_CHECKER_APPROVE_PRIDX = "APPROVE_PRIDX";

	public static final String ACTION_CHECKER_APPROVE_CREATE_PRIDX = "APPROVE_CREATE_PRIDX";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_PRIDX = "APPROVE_UPDATE_PRIDX";

	public static final String ACTION_CHECKER_APPROVE_DELETE_PRIDX = "APPROVE_DELETE_PRIDX";

	public static final String ACTION_CHECKER_REJECT_PRIDX = "REJECT_PRIDX";

	public static final String ACTION_MAKER_EDIT_REJECTED_PRIDX = "EDIT_REJECTED_PRIDX";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_PRIDX = "EDIT_REJECTED_CREATE_PRIDX";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_PRIDX = "EDIT_REJECTED_UPDATE_PRIDX";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_PRIDX = "EDIT_REJECTED_DELETE_PRIDX";

	public static final String ACTION_MAKER_CLOSE_REJECTED_PRIDX = "CLOSE_REJECTED_PRIDX";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_PRIDX = "CLOSE_REJECTED_CREATE_PRIDX";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_PRIDX = "CLOSE_REJECTED_UPDATE_PRIDX";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_PRIDX = "CLOSE_REJECTED_DELETE_PRIDX";

	public static final String SEQUENCE_PROPERTY_IDX = "PROPERTY_IDX_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_STAGE = "STG_PROPERTY_IDX_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_SEC_SUBTYPE = "PROPERTY_IDX_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_SEC_SUBTYPE_STAGE = "STG_PROP_IDX_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_ITEM = "PROPERTY_IDX_ITEM_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_ITEM_STAGE = "STG_PROPERTY_IDX_ITEM_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_DISTRICT_CODE = "PROPERTY_IDX_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_DISTRICT_CODE_STAGE = "STG_PROP_IDX_DIST_CODE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_MUKIM_CODE = "PROPERTY_IDX_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_MUKIM_CODE_STAGE = "STG_PROP_IDX_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_PROPERTY_TYPE = "PROPERTY_IDX_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_PROPERTY_IDX_PROPERTY_TYPE_STAGE = "STG_PROP_IDX_PROP_TYPE_SEQ";

	// public static final String PROP_IDX_TYPE = "PROP_IDX_TYPE" ;
	public static final String PROP_VAL_DESCR = "PROP_VAL_DESCR";

	public static final String PROP_IDX_TYPE_Q1 = "QUARTER_ONE";

	public static final String PROP_IDX_TYPE_Q2 = "QUARTER_TWO";

	public static final String PROP_IDX_TYPE_Q3 = "QUARTER_THREE";

	public static final String PROP_IDX_TYPE_Q4 = "QUARTER_FOUR";

	public static final String PROP_IDX_TYPE_A = "ANNUAL";

	public static final String PROP_VAL_DESCR_ISTP = "ISTP";

	public static final String PROP_VAL_DESCR_ITP = "ITP";

	public static final String PROP_VAL_DESCR_ID = "ID";

	public static final String PROP_VAL_DESCR_IS = "IS";

	public static final String PROP_VAL_DESCR_IRH = "IRH";

	public static final String PROP_IDX_PERIOD = "PROP_IDX_PERIOD";

	// ************* Constants for TAT Document *********************
	public static final String INSTANCE_TAT_DOC = "TAT_DOC";

	public static final String ACTION_READ_TAT_DOC = "READ_TAT_DOC";

	public static final String ACTION_READ_TAT_DOC_ID = "READ_TAT_DOC_ID";

	public static final String ACTION_MAKER_CREATE_TAT_DOC = "MAKER_CREATE";

	public static final String ACTION_MAKER_UPDATE_TAT_DOC = "MAKER_UPDATE";

	public static final String ACTION_MAKER_SAVE_TAT_DOC = "MAKER_SAVE";

	public static final String ACTION_MAKER_CLOSE_TAT_DOC = "MAKER_CLOSE";

	public static final String ACTION_MAKER_CLOSE_UPDATE_TAT_DOC = "MAKER_CLOSE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_TAT_DOC = "CHECKER_APPROVE";

	public static final String ACTION_CHECKER_REJECT_TAT_DOC = "CHECKER_REJECT";

	// ***************Constants for lease agreement **********************
	public static final String LEASE_TYPE = "LEASE_TYPE";

	public static final String TITLE_TYPE = "TITLE_TYPE";

	// ***************Constants for Insurance policy *******************

	// ************* Constants for AssetBased Vehicle *********************
	public static final String YARD_YARD = "Y";

	public static final String YARD_WAREHOUSE = "W";

	public static final String YARD_STORE = "S";

	public static final String CAC_CENTRE = "CAC";

	// Andy Wong, 23 Sept 2008
	/** Trx State: STP module */
	public static final String STATE_PENDING_RETRY = "PENDING_RETRY";

	public static final String STATE_LOADING = "LOADING";

	public static final String STATE_LOADING_DELETE = "LOADING_DELETE";

	// Andy Wong, 3 Nov 2008: criteria to search for property collateral during
	// valuation
	public static final String PROP_COMPLETE_STATUS_W_COF_ISSUANCE = "Y";

	public static final String PROP_COMPLETE_STATUS_WO_COF_ISSUANCE = "W";

	// constant for common code - source cms security subtype mapping
	public static final String COMMON_CODE_SOURCE_CMS_SEC_TYPE_MAP = "SOURCE_CMS_SEC_TYPE_MAP";

	public static final String STATE_PENDING_PERFECTION = "PENDING_PERFECTION";

	public static final String COMMON_CODE_SEARCH_TYPE_SEC = "SEARCH_SOURCE_SEC";
	public static final String COMMON_CODE_SEARCH_TYPE_AA = "SEARCH_SOURCE_AA";
	public static final String SEARCH_TYPE_CMS = "CMS";
	public static final String SEARCH_TYPE_LOS = "LOS";
	public static final String SEARCH_TYPE_HOST = "HOST";

	public static final String APPLICATION_TYPE_MO = "MG";
	public static final String APPLICATION_TYPE_HP = "HP";
	public static final String APPLICATION_TYPE_SF = "SF";
	public static final String APPLICATION_TYPE_CO = "CO";
	public static final String APPLICATION_TYPE_COM = "COM";
	public static final String APPLICATION_TYPE_CO_HP = "CHP";
	public static final String APPLICATION_TYPE_PL = "PL";
	public static final String COMMON_CODE_APPLICATION_TYPE = "APPLICATION_TYPE";

	public static final String PRODUCT_GROUP_HP = "HP";
	public static final String PRODUCT_GROUP_IH = "IH";
	public static final String PRODUCT_TYPE_SV = "SV";
	public static final String PRODUCT_TYPE_AR = "AR";
	public static final String PRODUCT_TYPE_RP = "RP";
	public static final String FACILITY_TYPE_3P = "3P";
	public static final String PRODUCT_TYPE_HP = "HP";
	public static final String PRODUCT_TYPE_HN = "HN";

	public static final String DISBURSEMENT_MANNER_LS = "LS";

	public static final String GPP_TERM_CODE_M = "M";

	// constant for common code - product type
	public static final String COMMON_CODE_PRODUCT_TYPE = PropertyManager
			.getValue("common.code.category.product.type", "27");

	// Constants used in SIBS Validation
	public static final String FACILITY_STATUS_CANCELLED = "C";
	public static final String FACILITY_STATUS_REJECTED = "R";
	public static final String FACILITY_STATUS_APPROVED = "A";
	public static final String FACILITY_STATUS_PENDING_ACCEPTANCE = "T";
	public static final String FACILITY_STATUS_PENDING_APPROVAL = "P";

	public static final String FACILITY_DOC_STATUS_PENDING_ACCEPTANCE = "PA";
	public static final String FACILITY_DOC_STATUS_PENDING_DOCUMENTATION = "PD";
	public static final String FACILITY_DOC_STATUS_DOCUMENTATION_COMPLETE = "DC";
	public static final String FACILITY_DOC_STATUS_NOT_REQUIRED = "NR";

	public static final String ACCOUNT_RELATIONSHIP_PRIMARY = "P";
	public static final String ACCOUNT_RELATIONSHIP_GUARANTOR = "GO";

	public static final String BNM_PURPOSE_CODE_NA = "0000";

	public static final String AA_LAW_TYPE_ISLAM = "ISL";

	public static final String AA_LAW_TYPE_CONVENTIONAL = "CON";

	public static final String OFFICER_RELATIONSHIP_RE = "RE";
	public static final String OFFICER_RELATIONSHIP_MK = "MK";
	public static final String OFFICER_RELATIONSHIP_PR = "PR";
	public static final String OFFICER_RELATIONSHIP_EV = "EV";
	public static final String OFFICER_RELATIONSHIP_AR = "AR";

	public static final String OFFICER_INDICATOR_R = "R";
	public static final String OFFICER_INDICATOR_M = "M";
	public static final String OFFICER_INDICATOR_P = "P";
	public static final String OFFICER_INDICATOR_E = "E";
	public static final String OFFICER_INDICATOR_A = "A";

	public static final String DOC_GEN_LI_VALUER = "MO11";
	public static final String DOC_GEN_LI_SOLICITOR = "MO10";
	public static final String DOC_GEN_LI_INSURER = "MO12";
	public static final String DOC_GEN_LI_EB_NOMINEE = "SF06";

	public static final String FACILITY_TERM_CODE_DAY = "D";
	public static final String FACILITY_TERM_CODE_MONTH = "M";

	public static final String ACCOUNT_TYPE_O = "O";
	public static final String ACCOUNT_TYPE_D = "D";
	public static final String ACCOUNT_TYPE_F = "F";

	// Host Collateral Status
	public static final String HOST_COL_STATUS_ACTIVE = "1";
	public static final String HOST_COL_STATUS_DELETED = "3";

	public static String DELETED = STATE_DELETED;
	public static String ACTIVE = STATE_ACTIVE;

	public static final String ACTION_MAKER_SUBMIT_CUST_GRP_IDENTIFIER = "MAKER_SUBMIT";
	public static final String ACTION_MAKER_SAVE_CUST_GRP_IDENTIFIER = "MAKER_SAVE";
	public static final String ACTION_MAKER_UPDATE_CUST_GRP_IDENTIFIER = "MAKER_UPDATE";
	public static final String ACTION_MAKER_DELETE_CUST_GRP_IDENTIFIER = "MAKER_DELETE";

	public static final String ACTION_READ_CUST_GRP_IDENTIFIER_BY_TRX_ID = "READ_BY_TRX_ID";
	// public static final String ACTION_READ_CUST_GRP_IDENTIFIER_BY_GROUP_CCINO
	// = "READ_BY_GROUP_CCINO";

	public static final String ACTION_READ_CUST_GRP_IDENTIFIER = "READ";

	public static final String EXEMPT_FACILITY_STATUS_CONDITIONAL = "C";
	public static final String EXEMPT_FACILITY_STATUS_EXEMPTED = "E";

	public static final String INTERNAL_LIMIT_BY_GP5_REQUIREMENT = "1";
	public static final String INTERNAL_LIMIT_BY_CAPITAL_FUNDS = "2";
	public static final String INTERNAL_LIMIT_BY_INTERNAL_CREDIT_RATING = "3";
	public static final String INTERNAL_LIMIT_BY_ABSOLUTE_AMOUNT = "4";

	// ************* Constants for Credit Risk Param - Maintain Exempted
	// Institution For GP5 Exposure *********************
	public static final String INSTANCE_EXEMPT_INST = "EXEMPT_INST";

	public static final String ACTION_READ_EXEMPT_INST_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_EXEMPT_INST = "READ";
	public static final String ACTION_MAKER_CREATE_EXEMPT_INST = "MAKER_CREATE";
	public static final String ACTION_MAKER_UPDATE_EXEMPT_INST = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_EXEMPT_INST = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_EXEMPT_INST = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_EXEMPT_INST = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_EXEMPT_INST = "MAKER_CLOSE_UPDATE";

	public static final String SEQUENCE_EXEMPT_INST = "CMS_EXEMPTED_INST_SEQ";

	// ************* Constants for Customer relationship
	public static final String SEQUENCE_CUST_RELNSHIP = "CUST_RELNSHIP_SEQ";

	public static final String INSTANCE_CUST_RELNSHIP = "CUST_RELNSHIP";
	public static final String ACTION_READ_CUST_RELNSHIP_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_CUST_RELNSHIP = "READ";

	public static final String ACTION_MAKER_CREATE_CUST_RELNSHIP = "MAKER_CREATE";
	public static final String ACTION_MAKER_UPDATE_CUST_RELNSHIP = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_CUST_RELNSHIP = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_CUST_RELNSHIP = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_CUST_RELNSHIP = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_CUST_RELNSHIP = "MAKER_CLOSE_UPDATE";

	// ************* Constants for Customer shareholder
	public static final String INSTANCE_SHAREHOLDER = "SHAREHOLDER";
	public static final String ACTION_READ_SHAREHOLDER_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_SHAREHOLDER = "READ";

	public static final String ACTION_MAKER_CREATE_SHAREHOLDER = "MAKER_CREATE";
	public static final String ACTION_MAKER_UPDATE_SHAREHOLDER = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_SHAREHOLDER = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_SHAREHOLDER = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_SHAREHOLDER = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_SHAREHOLDER = "MAKER_CLOSE_UPDATE";

	/** Entity Relationship Common Code */
	public static final String RELATIONSHIP_CATEGORY_CODE = "ENT_REL";
	public static final String RELATIONSHIP_SHAREHOLDER_ENTRY_CODE = "8";
	public static final String RELATIONSHIP_DIRECTOR_ENTRY_CODE = "6";
	public static final String RELATIONSHIP_KEY_MANAGEMENT_ENTRY_CODE = "33";

	/*
	 * Description: Constants for Bank Entity Branch Code param Submitter: Andy
	 * Wong Date: 29 may 2008
	 */
	public static final String INSTANCE_BANK_ENTITY_BRANCH_PARAM = "BANK_ENTITY_BRANCH";

	// BANK_ENTITY_TYPE and SUB_LIMIT_DESC_CATEGORY_CODE should be the same list
	// public static final String BANK_ENTITY_TYPE = "BANK_ENTITY_TYPE";

	public static final String ACTION_READ_BANK_ENTITY_BRANCH = "READ";
	public static final String ACTION_READ_BANK_ENTITY_BRANCH_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_MAKER_UPDATE_BANK_ENTITY_BRANCH = "MAKER_UPDATE";
	public static final String ACTION_CHECKER_APPROVE_BANK_ENTITY_BRANCH = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_BANK_ENTITY_BRANCH = "CHECKER_REJECT";
	// public static final String
	// ACTION_MAKER_RESUBMIT_REJECTED_BANK_ENTITY_BRANCH =
	// "MAKER_RESUBMIT_REJECTED";
	public static final String ACTION_MAKER_CLOSE_REJECTED_BANK_ENTITY_BRANCH = "MAKER_CLOSE_REJECTED";

	public static final String SEQUENCE_BANK_ENTITY_BRANCH = "CMS_BANK_ENTITY_SEQ";
	public static final String SEQUENCE_BANK_ENTITY_BRANCH_STAGE = "CMS_STG_BANK_ENTITY_SEQ";

	// *************** Constants for Entity Limit ************************/
	public static final String SEQUENCE_ENTITY_LIMIT = "ENTITY_LIMIT_SEQ";

	public static final String INSTANCE_ENTITY_LIMIT = "ENTITY_LIMIT";

	public static final String ACTION_READ_ENTITY_LIMIT_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_ENTITY_LIMIT = "READ";
	public static final String ACTION_MAKER_CREATE_ENTITY_LIMIT = "MAKER_CREATE";
	public static final String ACTION_MAKER_UPDATE_ENTITY_LIMIT = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_ENTITY_LIMIT = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_ENTITY_LIMIT = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_ENTITY_LIMIT = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_ENTITY_LIMIT = "MAKER_CLOSE_UPDATE";

	// Internal Credit Rating Module

	public static final String INSTANCE_INTERNAL_CREDIT_RATING = "INT_CRED_RAT";

	public static final String ACTION_READ_INTERNAL_CREDIT_RATING_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_INTERNAL_CREDIT_RATING = "READ";
	public static final String ACTION_MAKER_CREATE_INTERNAL_CREDIT_RATING = "MAKER_CREATE";
	public static final String ACTION_MAKER_UPDATE_INTERNAL_CREDIT_RATING = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_INTERNAL_CREDIT_RATING = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_INTERNAL_CREDIT_RATING = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_INTERNAL_CREDIT_RATING = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_INTERNAL_CREDIT_RATING = "MAKER_CLOSE_UPDATE";

	public static final String SEQUENCE_INTERNAL_CREDIT_RATING = "CMS_INTERNAL_CREDIT_RATING_SEQ";
	public static final String SEQUENCE_STAGING_INTERNAL_CREDIT_RATING = "CMS_STG_INT_CREDITRATING_SEQ";

	/** Bank Entity Type Common Code */
	public static final String SUB_LIMIT_DESC_CATEGORY_CODE = "SUB_LIMIT_DESC";

	public static final String BANKING_GROUP_ENTRY_CODE = "ABG";
	public static final String BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE = "2";
	public static final String BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE = "3";
	public static final String BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE = "4";

	public static final String SUB_LIMIT_TYPE_INTER_NON_EXEMPT_ENTRY_CODE = "1";
	// Alliance Bank Entity
	public static final String SUB_LIMIT_TYPE_BANK_ENTITY_ENTRY_CODE = "2";
	public static final String SUB_LIMIT_DESC_INTER_LIMIT_ENTRY_CODE = "ABG";
	public static final String OTHER_LIMIT_TYPE_INTER_EXEMPT_ENTRY_CODE = "2";
	public static final String OTHER_LIMIT_TYPE_PDS_ENTRY_CODE = "1";

	public static final String SUB_LIMIT_DESC_BANK_GROUP_INTER_LIMIT_ENTRY_CODE = "ABG";

	/** Limit Booking Module */

	public static final String INSTANCE_LIMIT_BOOKING = "LIMIT_BOOKING";

	public static final String ACTION_READ_LIMIT_BOOKING_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_LIMIT_BOOKING = "READ";
	public static final String ACTION_MAKER_CREATE_APPROVE_LIMIT_BOOKING = "MAKER_CREATE_APPROVE";
	public static final String ACTION_MAKER_UPDATE_APPROVE_LIMIT_BOOKING = "MAKER_UPDATE_APPROVE";
	public static final String ACTION_MAKER_DELETE_APPROVE_LIMIT_BOOKING = "MAKER_DELETE_APPROVE";
	public static final String ACTION_MAKER_SUCCESS_APPROVE_LIMIT_BOOKING = "MAKER_SUCCESS_APPROVE";

	public static final String SEQUENCE_LIMIT_BOOKING = "LMT_BOOK_SEQ";
	public static final String SEQUENCE_LIMIT_BOOKING_DTL = "LMT_BOOK_DTL_SEQ";

	public static final String BKG_TYPE_BGEL = "BGEL";
	// entity limit - customer
	public static final String BKG_TYPE_ENTITY = "ENTITY";
	public static final String BKG_TYPE_ECO_POL = "ECO_POL";
	public static final String BKG_TYPE_SUB_POL = "SUB_POL";
	public static final String BKG_TYPE_MAIN_POL = "MAIN_POL";
	public static final String BKG_TYPE_COUNTRY = "COUNTRY";

	public static final String BKG_TYPE_PROD_PROG = "PROD_PROG";

	public static final String BKG_SUB_TYPE_CONV = "CONV";
	public static final String BKG_SUB_TYPE_ISLM = "ISLM";
	public static final String BKG_SUB_TYPE_INV = "INV";
	public static final String BKG_SUB_TYPE_BANK = "BANK";
	public static final String BKG_SUB_TYPE_BANK_NONFI = "BANKNONFI";
	public static final String BKG_SUB_TYPE_EXEMPT = "EXEMPT";
	public static final String BKG_SUB_TYPE_NON_EXEMPT = "NONEXEMPT";

	public static final String BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER = "BWBEC";
	public static final String BKG_TYPE_BANK_WIDE_BANK_ENTITY_CUSTOMER_GROUP = "BWBECG";

	public static final String BKG_SUB_TYPE_BANK_WIDE_GP5_CONV = "GP5CONV";
	public static final String BKG_SUB_TYPE_BANK_WIDE_GP5_ISLM = "GP5ISLM";
	public static final String BKG_SUB_TYPE_BANK_WIDE_GP5_INV = "GP5INV";
	public static final String BKG_SUB_TYPE_BANK_WIDE_GP5_BG = "GP5BG";

	public static final String BKG_SUB_TYPE_BANK_WIDE_ILP_CONV = "ILPCONV";
	public static final String BKG_SUB_TYPE_BANK_WIDE_ILP_ISLM = "ILPISLM";
	public static final String BKG_SUB_TYPE_BANK_WIDE_ILP_INV = "ILPINV";
	public static final String BKG_SUB_TYPE_BANK_WIDE_ILP_BG = "ILPBG";

	public static final String CATEGORY_CODE_POL = "POL";
	public static final String CATEGORY_CODE_SECTOR = "SECTOR";
	public static final String CATEGORY_CODE_ECONOMIC_SECTOR = "17";

	public static final String STATUS_LIMIT_BOOKING_BOOKED = "B";
	public static final String STATUS_LIMIT_BOOKING_SUCCESSFUL = "S";
	public static final String STATUS_LIMIT_BOOKING_EXPIRED = "E";
	public static final String STATUS_LIMIT_BOOKING_DELETED = "D";

	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";

	public static final String SOURCE_ID_ARBS = "ARBS";
	public static final String SOURCE_ID_BOST = "BOST";
	public static final String SOURCE_ID_SEMA = "SEMA";
	public static final String SOURCE_ID_QUAN = "QUAN";

	public static final int LIMIT_BOOKING_DECIMAL_PLACES_2 = 2;
	public static final int LIMIT_BOOKING_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

	/** Product Limit Parameter Module **/
	public static final String SEQUENCE_PRODUCT_PROGRAM_LIMIT = "PRODUCT_PROGRAM_SEQ";
	public static final String SEQUENCE_PRODUCT_TYPE_LIMIT = "PRODUCT_TYPE_SEQ";
	public static final String INSTANCE_PRODUCT_LIMIT_PARAMETER = "PRODUCT_LIMIT";
	public static final String ACTION_MAKER_CLOSE_CREATE_PRODUCT_LIMIT_PARAMETER = "MAKER_CLOSE_CREATE";
	public static final String ACTION_MAKER_CLOSE_DELETE_PRODUCT_LIMIT_PARAMETER = "MAKER_CLOSE_DELETE";
	public static final String ACTION_MAKER_CLOSE_UPDATE_PRODUCT_LIMIT_PARAMETER = "MAKER_CLOSE_UPDATE";
	public static final String ACTION_MAKER_CREATE_PRODUCT_LIMIT_PARAMETER = "MAKER_CREATE";
	public static final String ACTION_MAKER_DELETE_PRODUCT_LIMIT_PARAMETER = "MAKER_DELETE";
	public static final String ACTION_MAKER_UPDATE_PRODUCT_LIMIT_PARAMETER = "MAKER_UPDATE";
	public static final String ACTION_READ_PRODUCT_LIMIT_PARAMETER = "READ";
	public static final String ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_ID = "READ_BY_ID";
	public static final String ACTION_CHECKER_APPROVE_PRODUCT_LIMIT_PARAMETER = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_PRODUCT_LIMIT_PARAMETER = "CHECKER_REJECT";

	// ************* Constants for Credit Risk Param - Maintain Country Limit
	// *********************
	public static final String INSTANCE_COUNTRY_LIMIT = "COUNTRY_LIMIT";

	public static final String ACTION_READ_COUNTRY_LIMIT_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_COUNTRY_LIMIT = "READ";
	public static final String ACTION_MAKER_UPDATE_COUNTRY_LIMIT = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_COUNTRY_LIMIT = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_COUNTRY_LIMIT = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_COUNTRY_LIMIT = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_COUNTRY_LIMIT = "MAKER_CLOSE_UPDATE";

	public static final String SEQUENCE_COUNTRY_LIMIT = "COUNTRY_LIMIT_SEQ";
	public static final String SEQUENCE_COUNTRY_RATING = "COUNTRY_RATING_SEQ";

	// ************* Constants for Customer Details - Address Type
	// *********************

	// Internal limit parameter Module
	public static final String INSTANCE_INTERNAL_LIMIT = "INTERNAL_LIMIT_PARAM";
	public static final String ACTION_READ_INTERNAL_LIMIT = "READ_INTERNAL_LIMIT";
	public static final String ACTION_READ_INTERNAL_LIMIT_BY_TRX_ID = "READ_INTERNAL_LIMIT_BY_TRX_ID";
	public static final String ACTION_MAKER_UPDATE_INTERNAL_LIMIT = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CANCEL_INTERNAL_LIMIT = "MAKER_CANCEL";
	public static final String ACTION_MAKER_SAVE_INTERNAL_LIMIT = "MAKER_SAVE";
	public static final String ACTION_MAKER_CLOSE_INTERNAL_LIMIT = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_INTERNAL_LIMIT = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_INTERNAL_LIMIT = "CHECKER_REJECT";

	public static final String SEQUENCE_INTERNAL_LIMIT = "CMS_INT_LIMIT_PARAM_SEQ";
	public static final String SEQUENCE_STAGING_INTERNAL_LIMIT = "CMS_STG_INT_LIMIT_PARAM_SEQ";

	public static final String BANKING_GROUP_CODE = "ABG";
	public static final String BANKING_GROUP_DESC = "Alliance Banking Group";

	// ************* Constants for COUNTER_PARTY *********************
	public static final String INSTANCE_CCI_COUNTER_PARTY = "CCI";

	public static final String ACTION_MAKER_SUBMIT_CCI_COUNTER_PARTY = "MAKER_SUBMIT";
	public static final String ACTION_MAKER_UPDATE_CC_COUNTER_PARTY = "MAKER_UPDATE";
	public static final String ACTION_MAKER_DELETE_CC_COUNTER_PARTY = "MAKER_DELETE";

	public static final String ACTION_READ_CCIN_BY_TRX_ID = "READ_BY_TRX_ID";
	public static final String ACTION_READ_CCIN_BY_GROUP_CCINO = "READ_BY_GROUP_CCINO";
	public static final String ACTION_CHECKER_APPROVE = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CANCEL = "MAKER_CANCEL";

	public static final String ACTION_READ_CCI_COUNTER_PARTY = "READ";
	public static final String SEQUENCE_CMS_GRP_CCI_LE_MAP = "CMS_GRP_CCI_LE_MAP_SEQ";
	public static final String SEQUENCE_CMS_GRP_CCI_NO = "CMS_GRP_CCI_NO_SEQ";
	public static final String SEQUENCE_GRP_CCI_REF_ID_SEQ = "GRP_CCI_REF_ID_SEQ";
	public static final String SEQUENCE_GRP_CCI_REF_ID_SEQ_STAGING = "GRP_CCI_REF_ID_STG_SEQ";

	// ************* Constants for Exempt Facility *********************
	public static final String INSTANCE_EXEMPT_FACILITY_GROUP = "EXEMPT_FACILITY";

	public static final String SEQUENCE_CMS_EXEMPT_FACILITY_SEQ = "CMS_EXEMPT_FAC_SEQ";
	public static final String SEQUENCE_CMS_STG_EXEMPT_FACILITY_SEQ = "CMS_STG_EXEMPT_FAC_SEQ";

	public static final String EXEMPT_FACILITY_GROUP_TYPE = "EXEMPT_FACILITY";
	public static final String ACTION_READ_EXEMPT_FACILITY_GROUP_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_READ_EXEMPT_FACILITY = "READ";
	public static final String ACTION_MAKER_CREATE_EXEMPT_FACILITY = "MAKER_CREATE";
	public static final String ACTION_MAKER_UPDATE_EXEMPT_FACILITY = "MAKER_UPDATE";
	public static final String ACTION_MAKER_CLOSE_EXEMPT_FACILITY = "MAKER_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_EXEMPT_FACILITY = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_EXEMPT_FACILITY = "CHECKER_REJECT";
	public static final String ACTION_MAKER_CLOSE_UPDATE_EXEMPT_FACILITY = "MAKER_CLOSE_UPDATE";

	// ************* Constants for CustGrpIdentifier
	public static final String ACTION_MAKER2_UPDATE_CUST_GRP_IDENTIFIER = "MAKER2_UPDATE";
	public static final String ACTION_MAKER2_SAVE_CUST_GRP_IDENTIFIER = "MAKER2_SAVE";
	public static final String ACTION_MAKER2_CANCEL = "MAKER2_CANCEL";
	public static final String ACTION_CHECKER2_REJECT = "CHECKER2_REJECT";
	public static final String ACTION_CHECKER2_APPROVE = "CHECKER2_APPROVE";

	public static final String INSTANCE_CUST_GRP_IDENTIFIER = "CGID";
	public static final String ENTITY_TYPE_CUSTOMER = "CUSTOMER";
	public static final String ENTITY_TYPE_GROUP = "GROUP";
	public static final String ACTION_READ_BY_GROUP_ID = "READ_BY_GROUP_ID";
	public static final String CATEGORY_CODE_PRODUCT_TYPE = "27";

	// for cci
	public static final String SEQUENCE_CMS_CCI_LE_MAP = "CMS_CCI_MAP_SEQ";
	public static final String SEQUENCE_CMS_CCI_NO = "CMS_CCI_NO_SEQ";
	public static final String SEQUENCE_CCI_REF_ID_SEQ = "CCI_REF_ID_SEQ";
	public static final String SEQUENCE_CCI_REF_ID_SEQ_STAGING = "CCI_REF_ID_STG_SEQ";

	// for CGID
	public static final String SEQUENCE_CMS_CUST_GRP_NO_SEQ = "CMS_CUST_GRP_NO_SEQ";
	public static final String SEQUENCE_CMS_CUST_GRP_SEQ = "CMS_CUST_GRP_SEQ";
	public static final String SEQUENCE_CMS_CUST_GRP_SEQ_STAGING = "CMS_STAGE_CUST_GRP_SEQ";

	public static final String SEQUENCE_CMS_GROUP_CREDIT_GRADE = "CMS_GRP_CREDIT_GRADE_SEQ";
	public static final String SEQUENCE_CMS_GROUP_CREDIT_GRADE_STAGING = "CMS_STAGE_GRP_CREDIT_GRADE_SEQ";

	public static final String SEQUENCE_CMS_GROUP_SUBLIMIT_SEQ = "CMS_GROUP_SUBLIMIT_SEQ";
	public static final String SEQUENCE_CMS_GROUP_SUBLIMIT_SEQ_STAGING = "CMS_STAGE_GROUP_SUBLIMIT_SEQ";

	public static final String SEQUENCE_CMS_GROUP_MEMBER_SEQ = "CMS_GROUP_MEMBER_SEQ";
	public static final String SEQUENCE_CMS_GROUP_MEMBER_SEQ_STAGING = "CMS_STAGE_GROUP_MEMBER_SEQ";

	public static final String SEQUENCE_CMS_GROUP_OTRLIMIT_SEQ = "CMS_GROUP_OTRLIMIT_SEQ";
	public static final String SEQUENCE_CMS_GROUP_OTRLIMIT_SEQ_STAGING = "CMS_STAGE_GROUP_OTRLIMIT_SEQ";

	/** Sector Limit Parameter Module **/
	public static final String SEQUENCE_MAIN_SECTOR_LIMIT = "MAIN_SECTOR_LIMIT_SEQ";
	public static final String SEQUENCE_SUB_SECTOR_LIMIT = "SUB_SECTOR_LIMIT_SEQ";
	public static final String SEQUENCE_ECO_SECTOR_LIMIT = "ECO_SECTOR_LIMIT_SEQ";
	public static final String INSTANCE_SECTOR_LIMIT_PARAMETER = "SECTOR_LIMIT";
	public static final String ACTION_READ_SECTOR_LIMIT_PARAMETER = "READ";
	public static final String ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_TRXID = "READ_BY_TRXID";
	public static final String ACTION_MAKER_CREATE_SECTOR_LIMIT_PARAMETER = "MAKER_CREATE";
	public static final String ACTION_MAKER_CLOSE_CREATE_SECTOR_LIMIT_PARAMETER = "MAKER_CLOSE_CREATE";
	public static final String ACTION_MAKER_CLOSE_UPDATE_SECTOR_LIMIT_PARAMETER = "MAKER_CLOSE_UPDATE";
	public static final String ACTION_CHECKER_APPROVE_SECTOR_LIMIT_PARAMETER = "CHECKER_APPROVE";
	public static final String ACTION_CHECKER_REJECT_SECTOR_LIMIT_PARAMETER = "CHECKER_REJECT";
	public static final String ACTION_MAKER_UPDATE_SECTOR_LIMIT_PARAMETER = "MAKER_UPDATE";

	public static final String ACCOUNT_TYPE_LOAN_TERM = "L";
	public static final String ACCOUNT_TYPE_OVERDRAFT = "D";

	public static final String CUST_TYPE_INDIVIDUAL = "I";
	public static final String CUST_TYPE_CORPORATE = "C";
	public static final String CUST_TYPE_FINANCE = "F";

	/** sector limit -- take from MBB project **/
	public static final String ACTION_READ_SECTOR_LIMIT_PARAMETER_BY_ID = "READ_BY_ID";
	public static final String ACTION_MAKER_DELETE_SECTOR_LIMIT_PARAMETER = "MAKER_DELETE";
	public static final String ACTION_MAKER_CLOSE_DELETE_SECTOR_LIMIT_PARAMETER = "MAKER_CLOSE_DELETE";

	public static final int TEAM_TYPE_GEMS_AM_MAKER = 28;
	public static final int TEAM_TYPE_GEMS_AM_CHECKER = 29;
	public static final int TEAM_TYPE_GEMS_MAKER = 30;
	public static final int TEAM_TYPE_GEMS_CHECKER = 31;
	public static final int TEAM_TYPE_GEMS_SC_MAKER = 32;
	public static final int TEAM_TYPE_GEMS_SC_CHECKER = 33;

	/* MUREX PDS Outstanding Amount Calculation */
	public static final String PROD_DESC_BOND = "BOD";
	public static final String PROD_DESC_FI_BOND = "FI";

	/** disbursed indicator **/
	public static final String DISBURSED_IND_FULL = "F";
	public static final String DISBURSED_IND_PARTIAL = "P";
	public static final String DISBURSED_IND_NO = "N";

	// Security envelope module @ 20090127 by erenewong
	// ************* Constants for Security Envelope *********************
	public static final String INSTANCE_SECURITY_ENVELOPE = "SEC_ENVELOPE";

	public static final String ACTION_READ_SECENVELOPE = "READ_SECENVELOPE";

	public static final String ACTION_READ_SECENVELOPE_ID = "READ_SECENVELOPE_ID";

	public static final String ACTION_READ_SECENVELOPE_LIMITPROFILE = "READ_SECENVELOPE_LIMITPROFILE";

	public static final String ACTION_MAKER_CREATE_SECENV = "CREATE_SECENV";

	public static final String ACTION_MAKER_UPDATE_SECENV = "UPDATE_SECENV";

	public static final String ACTION_MAKER_DELETE_SECENV = "DELETE_SECENV";

	public static final String ACTION_CHECKER_APPROVE_SECENV = "APPROVE_SECENV";

	public static final String ACTION_CHECKER_APPROVE_CREATE_SECENV = "APPROVE_CREATE_SECENV";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_SECENV = "APPROVE_UPDATE_SECENV";

	public static final String ACTION_CHECKER_APPROVE_DELETE_SECENV = "APPROVE_DELETE_SECENV";

	public static final String ACTION_CHECKER_REJECT_SECENV = "REJECT_SECENV";

	public static final String ACTION_MAKER_EDIT_REJECTED_SECENV = "EDIT_REJECTED_SECENV";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_SECENV = "EDIT_REJECTED_CREATE_SECENV";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_SECENV = "EDIT_REJECTED_UPDATE_SECENV";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_SECENV = "EDIT_REJECTED_DELETE_SECENV";

	public static final String ACTION_MAKER_CLOSE_REJECTED_SECENV = "CLOSE_REJECTED_SECENV";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_SECENV = "CLOSE_REJECTED_CREATE_SECENV";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_SECENV = "CLOSE_REJECTED_UPDATE_SECENV";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_SECENV = "CLOSE_REJECTED_DELETE_SECENV";

	public static final String SEC_ENVELOPE_ADDRESS = "SEC_ENVELOPE_ADDRESS";

	public static final String SEC_ENVELOPE_CABINET = "SEC_ENVELOPE_CABINET";

	public static final String SEC_ENVELOPE_DRAWER = "SEC_ENVELOPE_DRAWER";

	// ********************* Constants for System Bank****************

	public static final String INSTANCE_SYSTEM_BANK = "SYSTEM_BANK";

	public static final String ACTION_READ_SYSTEM_BANK = "READ_SYSTEM_BANK";

	public static final String ACTION_READ_SYSTEM_BANK_ID = "READ_SYSTEM_BANK_ID";

	public static final String ACTION_MAKER_CREATE_SYSTEM_BANK = "CREATE_SYSTEM_BANK";

	public static final String ACTION_MAKER_UPDATE_SYSTEM_BANK = "UPDATE_SYSTEM_BANK";

	public static final String ACTION_MAKER_DELETE_SYSTEM_BANK = "DELETE_SYSTEM_BANK";

	public static final String ACTION_CHECKER_APPROVE_SYSTEM_BANK = "APPROVE_SYSTEM_BANK";

	public static final String ACTION_CHECKER_APPROVE_CREATE_SYSTEM_BANK = "APPROVE_CREATE_SYSTEM_BANK";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_SYSTEM_BANK = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_SYSTEM_BANK = "APPROVE_DELETE_SYSTEM_BANK";

	public static final String ACTION_CHECKER_REJECT_SYSTEM_BANK = "REJECT_SYSTEM_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_SYSTEM_BANK = "EDIT_REJECTED_SYSTEM_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_SYSTEM_BANK = "EDIT_REJECTED_CREATE_SYSTEM_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_SYSTEM_BANK = "EDIT_REJECTED_UPDATE_SYSTEM_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_SYSTEM_BANK = "EDIT_REJECTED_DELETE_SYSTEM_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_SYSTEM_BANK = "CLOSE_REJECTED_SYSTEM_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_SYSTEM_BANK = "CLOSE_REJECTED_CREATE_SYSTEM_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_SYSTEM_BANK = "CLOSE_REJECTED_UPDATE_SYSTEM_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_SYSTEM_BANK = "CLOSE_REJECTED_DELETE_SYSTEM_BANK";

	public static final String SEQUENCE_SYSTEM_BANK = "SYSTEM_BANK_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_STAGE = "STG_SYSTEM_BANK_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_SEC_SUBTYPE = "SYSTEM_BANK_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_SEC_SUBTYPE_STAGE = "STG_SYSTEM_BANK_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_ITEM = "SYSTEM_BANK_ITEM_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_ITEM_STAGE = "STG_SYSTEM_BANK_ITEM_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_DISTRICT_CODE = "SYSTEM_BANK_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_DISTRICT_CODE_STAGE = "STG_SYSTEM_BANK_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_MUKIM_CODE = "SYSTEM_BANK_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_MUKIM_CODE_STAGE = "STG_SYSTEM_BANK_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_PROPERTY_TYPE = "SYSTEM_BANK_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_PROPERTY_TYPE_STAGE = "STG_SYSTEM_BANK_PROPERTY_TYPE_SEQ";

	// ********************* Constants for System Bank Branch****************

	public static final String INSTANCE_SYSTEM_BANK_BRANCH = "SYSTEM_BANK_BRANCH";

	public static final String ACTION_READ_SYSTEM_BANK_BRANCH = "READ_SYSTEM_BANK_BRANCH";

	public static final String ACTION_READ_SYSTEM_BANK_BRANCH_ID = "READ_SYSTEM_BANK_BRANCH_ID";

	public static final String ACTION_MAKER_CREATE_SYSTEM_BANK_BRANCH = "CREATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_UPDATE_SYSTEM_BANK_BRANCH = "UPDATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_DELETE_SYSTEM_BANK_BRANCH = "DELETE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_SYSTEM_BANK_BRANCH = "APPROVE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_CREATE_SYSTEM_BANK_BRANCH = "APPROVE_CREATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_SYSTEM_BANK_BRANCH = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_SYSTEM_BANK_BRANCH = "APPROVE_DELETE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_CHECKER_REJECT_SYSTEM_BANK_BRANCH = "REJECT_SYSTEM_BANK_BRANCH";
	public static final String ACTION_CHECKER_REJECT_CREATE_SYSTEM_BANK_BRANCH = "REJECT_SYSTEM_BANK_BRANCH";
	public static final String ACTION_CHECKER_REJECT_EDIT_SYSTEM_BANK_BRANCH = "REJECT_SYSTEM_BANK_BRANCH";
	public static final String ACTION_CHECKER_REJECT_DELETE_SYSTEM_BANK_BRANCH = "REJECT_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_DRAFT_SYSTEM_BANK_BRANCH = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_DRAFT_SYSTEM_BANK = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_DRAFT_SYSTEM_BANK_BRANCH = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_SYSTEM_BANK_BRANCH = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK_BRANCH = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_DRAFT_SYSTEM_BANK = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_SYSTEM_BANK = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_EDIT_REJECTED_SYSTEM_BANK_BRANCH = "EDIT_REJECTED_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_SYSTEM_BANK_BRANCH = "EDIT_REJECTED_CREATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_SYSTEM_BANK_BRANCH = "EDIT_REJECTED_UPDATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_SYSTEM_BANK_BRANCH = "EDIT_REJECTED_DELETE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_SYSTEM_BANK_BRANCH = "CLOSE_REJECTED_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_SYSTEM_BANK_BRANCH = "CLOSE_REJECTED_CREATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_SYSTEM_BANK_BRANCH = "CLOSE_REJECTED_UPDATE_SYSTEM_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_SYSTEM_BANK_BRANCH = "CLOSE_REJECTED_DELETE_SYSTEM_BANK_BRANCH";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH = "SYSTEM_BANK_BRANCH_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_STAGE = "STG_SYSTEM_BANK_BRANCH_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_SEC_SUBTYPE = "SYSTEM_BANK_BRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_SEC_SUBTYPE_STAGE = "STG_SYSTEM_BANK_BRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_ITEM = "SYSTEM_BANK_BRANCH_ITEM_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_ITEM_STAGE = "STG_SYSTEM_BANK_BRANCH_ITEM_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_DISTRICT_CODE = "SYSTEM_BANK_BRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_DISTRICT_CODE_STAGE = "STG_SYSTEM_BANK_BRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_MUKIM_CODE = "SYSTEM_BANK_BRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_MUKIM_CODE_STAGE = "STG_SYSTEM_BANK_BRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_PROPERTY_TYPE = "SYSTEM_BANK_BRANCH_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_SYSTEM_BANK_BRANCH_PROPERTY_TYPE_STAGE = "STG_SYSTEM_BANK_BRANCH_PROPERTY_TYPE_SEQ";

	// ********************* Constants for System Bank Branch****************

	// ********************* Constants for Image Tag****************

	public static final String INSTANCE_IMAGE_TAG = "IMAGE_TAG";

	public static final String ACTION_READ_IMAGE_TAG = "READ_IMAGE_TAG";

	public static final String ACTION_READ_IMAGE_TAG_ID = "READ_IMAGE_TAG_ID";

	public static final String ACTION_MAKER_CREATE_IMAGE_TAG = "CREATE_IMAGE_TAG";

	public static final String ACTION_MAKER_UPDATE_IMAGE_TAG = "UPDATE_IMAGE_TAG";

	public static final String ACTION_MAKER_DELETE_IMAGE_TAG = "DELETE_IMAGE_TAG";

	public static final String ACTION_CHECKER_APPROVE_IMAGE_TAG = "APPROVE_IMAGE_TAG";

	public static final String ACTION_CHECKER_APPROVE_CREATE_IMAGE_TAG = "APPROVE_CREATE_IMAGE_TAG";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_IMAGE_TAG = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_IMAGE_TAG = "APPROVE_DELETE_IMAGE_TAG";

	public static final String ACTION_CHECKER_REJECT_IMAGE_TAG = "REJECT_IMAGE_TAG";

	public static final String ACTION_MAKER_EDIT_REJECTED_IMAGE_TAG = "EDIT_REJECTED_IMAGE_TAG";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_IMAGE_TAG = "EDIT_REJECTED_CREATE_IMAGE_TAG";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_IMAGE_TAG = "EDIT_REJECTED_UPDATE_IMAGE_TAG";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_IMAGE_TAG = "EDIT_REJECTED_DELETE_IMAGE_TAG";

	public static final String ACTION_MAKER_CLOSE_REJECTED_IMAGE_TAG = "CLOSE_REJECTED_IMAGE_TAG";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_IMAGE_TAG = "CLOSE_REJECTED_CREATE_IMAGE_TAG";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_IMAGE_TAG = "CLOSE_REJECTED_UPDATE_IMAGE_TAG";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_IMAGE_TAG = "CLOSE_REJECTED_DELETE_IMAGE_TAG";

	public static final String SEQUENCE_IMAGE_TAG = "IMAGE_TAG_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_STAGE = "STG_IMAGE_TAG_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_SEC_SUBTYPE = "IMAGE_TAG_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_SEC_SUBTYPE_STAGE = "STG_IMAGE_TAG_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_ITEM = "IMAGE_TAG_ITEM_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_ITEM_STAGE = "STG_IMAGE_TAG_ITEM_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_DISTRICT_CODE = "IMAGE_TAG_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_DISTRICT_CODE_STAGE = "STG_IMAGE_TAG_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_MUKIM_CODE = "IMAGE_TAG_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_MUKIM_CODE_STAGE = "STG_IMAGE_TAG_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_PROPERTY_TYPE = "IMAGE_TAG_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_IMAGE_TAG_PROPERTY_TYPE_STAGE = "STG_IMAGE_TAG_PROPERTY_TYPE_SEQ";

	// ********************* Constants for Image Tag****************

	// ********************* Constants for Image Upload****************

	public static final String INSTANCE_IMAGE_UPLOAD = "IMAGE_UPLOAD";

	public static final String ACTION_READ_IMAGE_UPLOAD = "READ_IMAGE_UPLOAD";

	public static final String ACTION_READ_IMAGE_UPLOAD_ID = "READ_IMAGE_UPLOAD_ID";

	public static final String ACTION_MAKER_CREATE_IMAGE_UPLOAD = "CREATE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_UPDATE_IMAGE_UPLOAD = "UPDATE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_DELETE_IMAGE_UPLOAD = "DELETE_IMAGE_UPLOAD";

	public static final String ACTION_CHECKER_APPROVE_IMAGE_UPLOAD = "APPROVE_IMAGE_UPLOAD";

	public static final String ACTION_CHECKER_APPROVE_CREATE_IMAGE_UPLOAD = "APPROVE_CREATE_IMAGE_UPLOAD";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_IMAGE_UPLOAD = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_IMAGE_UPLOAD = "APPROVE_DELETE_IMAGE_UPLOAD";

	public static final String ACTION_CHECKER_REJECT_IMAGE_UPLOAD = "REJECT_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_EDIT_REJECTED_IMAGE_UPLOAD = "EDIT_REJECTED_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_IMAGE_UPLOAD = "EDIT_REJECTED_CREATE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_IMAGE_UPLOAD = "EDIT_REJECTED_UPDATE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_IMAGE_UPLOAD = "EDIT_REJECTED_DELETE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_CLOSE_REJECTED_IMAGE_UPLOAD = "CLOSE_REJECTED_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_IMAGE_UPLOAD = "CLOSE_REJECTED_CREATE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_IMAGE_UPLOAD = "CLOSE_REJECTED_UPDATE_IMAGE_UPLOAD";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_IMAGE_UPLOAD = "CLOSE_REJECTED_DELETE_IMAGE_UPLOAD";

	public static final String SEQUENCE_IMAGE_UPLOAD = "IMAGE_UPLOAD_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_STAGE = "STG_IMAGE_UPLOAD_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_SEC_SUBTYPE = "IMAGE_UPLOAD_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_SEC_SUBTYPE_STAGE = "STG_IMAGE_UPLOAD_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_ITEM = "IMAGE_UPLOAD_ITEM_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_ITEM_STAGE = "STG_IMAGE_UPLOAD_ITEM_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_DISTRICT_CODE = "IMAGE_UPLOAD_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_DISTRICT_CODE_STAGE = "STG_IMAGE_UPLOAD_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_MUKIM_CODE = "IMAGE_UPLOAD_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_MUKIM_CODE_STAGE = "STG_IMAGE_UPLOAD_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_PROPERTY_TYPE = "IMAGE_UPLOAD_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_IMAGE_UPLOAD_PROPERTY_TYPE_STAGE = "STG_IMAGE_UPLOAD_PROPERTY_TYPE_SEQ";

	// ********************* Constants for Image Upload by Abhijit
	// R****************

	// ********************* Constants for Other Bank****************

	public static final String INSTANCE_OTHER_BANK = "OTHER_BANK";

	public static final String ACTION_READ_OTHER_BANK = "READ_OTHER_BANK";

	public static final String ACTION_READ_OTHER_BANK_ID = "READ_OTHER_BANK_ID";

	public static final String ACTION_MAKER_CREATE_OTHER_BANK = "CREATE_OTHER_BANK";

	public static final String ACTION_MAKER_UPDATE_OTHER_BANK = "UPDATE_OTHER_BANK";

	public static final String ACTION_MAKER_DELETE_OTHER_BANK = "DELETE_OTHER_BANK";

	public static final String ACTION_CHECKER_APPROVE_OTHER_BANK = "APPROVE_OTHER_BANK";

	public static final String ACTION_CHECKER_APPROVE_CREATE_OTHER_BANK = "APPROVE_CREATE_OTHER_BANK";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_OTHER_BANK = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_OTHER_BANK = "APPROVE_DELETE_OTHER_BANK";

	public static final String ACTION_CHECKER_REJECT_OTHER_BANK = "REJECT_OTHER_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_OTHER_BANK = "EDIT_REJECTED_OTHER_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_OTHER_BANK = "EDIT_REJECTED_CREATE_OTHER_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_OTHER_BANK = "EDIT_REJECTED_UPDATE_OTHER_BANK";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_OTHER_BANK = "EDIT_REJECTED_DELETE_OTHER_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_OTHER_BANK = "CLOSE_REJECTED_OTHER_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_OTHER_BANK = "CLOSE_REJECTED_CREATE_OTHER_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_OTHER_BANK = "CLOSE_REJECTED_UPDATE_OTHER_BANK";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_OTHER_BANK = "CLOSE_REJECTED_DELETE_OTHER_BANK";

	public static final String SEQUENCE_OTHER_BANK = "OTHER_BANK_SEQ";

	public static final String SEQUENCE_OTHER_BANK_STAGE = "STG_OTHER_BANK_SEQ";

	public static final String SEQUENCE_OTHER_BANK_SEC_SUBTYPE = "OTHER_BANK_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_SEC_SUBTYPE_STAGE = "STG_OTHER_BANK_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_ITEM = "OTHER_BANK_ITEM_SEQ";

	public static final String SEQUENCE_OTHER_BANK_ITEM_STAGE = "STG_OTHER_BANK_ITEM_SEQ";

	public static final String SEQUENCE_OTHER_BANK_DISTRICT_CODE = "OTHER_BANK_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_DISTRICT_CODE_STAGE = "STG_OTHER_BANK_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_MUKIM_CODE = "OTHER_BANK_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_MUKIM_CODE_STAGE = "STG_OTHER_BANK_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_PROPERTY_TYPE = "OTHER_BANK_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_PROPERTY_TYPE_STAGE = "STG_OTHER_BANK_PROPERTY_TYPE_SEQ";

	
	public static final String ACTION_MAKER_SAVE_CREATE_OTHER_BANK = "SAVE_OTHER_BANK";
	
	public static final String ACTION_MAKER_SUBMIT_SAVE_CREATE_OTHER_BANK = "CREATE_OTHER_BANK";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_OTHER_BANK = "CLOSE_CREATE_OTHER_BANK";

	public static final String ACTION_MAKER_UPDATE_SAVE_OTHER_BANK = "SAVE_UPDATE_OTHER_BANK";

	// ********************* Constants for Other Bank Branch****************

	public static final String INSTANCE_OTHER_BANK_BRANCH = "OTHER_BANK_BRANCH";

	public static final String ACTION_READ_OTHER_BANK_BRANCH = "READ_OTHER_BANK_BRANCH";

	public static final String ACTION_READ_OTHER_BANK_BRANCH_ID = "READ_OTHER_BANK_BRANCH_ID";

	public static final String ACTION_MAKER_CREATE_OTHER_BANK_BRANCH = "CREATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_UPDATE_OTHER_BANK_BRANCH = "UPDATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_DELETE_OTHER_BANK_BRANCH = "DELETE_OTHER_BANK_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_OTHER_BANK_BRANCH = "APPROVE_OTHER_BANK_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_CREATE_OTHER_BANK_BRANCH = "APPROVE_CREATE_OTHER_BANK_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_OTHER_BANK_BRANCH = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_OTHER_BANK_BRANCH = "APPROVE_DELETE_OTHER_BANK_BRANCH";

	public static final String ACTION_CHECKER_REJECT_OTHER_BANK_BRANCH = "REJECT_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_OTHER_BANK_BRANCH = "EDIT_REJECTED_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_OTHER_BANK_BRANCH = "EDIT_REJECTED_CREATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_OTHER_BANK_BRANCH = "EDIT_REJECTED_UPDATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_OTHER_BANK_BRANCH = "EDIT_REJECTED_DELETE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_OTHER_BANK_BRANCH = "CLOSE_REJECTED_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_OTHER_BANK_BRANCH = "CLOSE_REJECTED_CREATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_OTHER_BANK_BRANCH = "CLOSE_REJECTED_UPDATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_OTHER_BANK_BRANCH = "CLOSE_REJECTED_DELETE_OTHER_BANK_BRANCH";

	public static final String SEQUENCE_OTHER_BANK_BRANCH = "OTHER_BANK_BRANCH_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_STAGE = "STG_OTHER_BANK_BRANCH_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_SEC_SUBTYPE = "OTHER_BANK_BRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_SEC_SUBTYPE_STAGE = "STG_OTHER_BANK_BRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_ITEM = "OTHER_BANK_BRANCH_ITEM_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_ITEM_STAGE = "STG_OTHER_BANK_BRANCH_ITEM_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_DISTRICT_CODE = "OTHER_BANK_BRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_DISTRICT_CODE_STAGE = "STG_OTHER_BANK_BRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_MUKIM_CODE = "OTHER_BANK_BRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_MUKIM_CODE_STAGE = "STG_OTHER_BANK_BRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_PROPERTY_TYPE = "OTHER_BANK_BRANCH_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_OTHER_BANK_BRANCH_PROPERTY_TYPE_STAGE = "STG_OTHER_BANK_BRANCH_PROPERTY_TYPE_SEQ";
	
	public static final String ACTION_MAKER_SAVE_CREATE_OTHER_BANK_BRANCH = "SAVE_OTHER_BANK_BRANCH";
	
	public static final String ACTION_MAKER_SUBMIT_SAVE_CREATE_OTHER_BANK_BRANCH = "CREATE_OTHER_BANK_BRANCH";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_OTHER_BANK_BRANCH = "CLOSE_CREATE_OTHER_BANK_BRANCH";

	public static final String ACTION_MAKER_UPDATE_SAVE_OTHER_BANK_BRANCH = "SAVE_UPDATE_OTHER_BANK_BRANCH";

	// ********************* Constants for Relationship Manager ****************

	public static final String INSTANCE_RELATIONSHIP_MGR = "RELATIONSHIP_MGR";

	public static final String ACTION_READ_RELATIONSHIP_MGR = "READ_RELATIONSHIP_MGR";

	public static final String ACTION_READ_RELATIONSHIP_MGR_ID = "READ_RELATIONSHIP_MGR_ID";

	public static final String ACTION_MAKER_CREATE_RELATIONSHIP_MGR = "CREATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_UPDATE_RELATIONSHIP_MGR = "UPDATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_DELETE_RELATIONSHIP_MGR = "DELETE_RELATIONSHIP_MGR";

	public static final String ACTION_CHECKER_APPROVE_RELATIONSHIP_MGR = "APPROVE_RELATIONSHIP_MGR";

	public static final String ACTION_CHECKER_APPROVE_CREATE_RELATIONSHIP_MGR = "APPROVE_CREATE_RELATIONSHIP_MGR";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_RELATIONSHIP_MGR = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_RELATIONSHIP_MGR = "APPROVE_DELETE_RELATIONSHIP_MGR";

	public static final String ACTION_CHECKER_REJECT_RELATIONSHIP_MGR = "REJECT_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_EDIT_REJECTED_RELATIONSHIP_MGR = "EDIT_REJECTED_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_RELATIONSHIP_MGR = "EDIT_REJECTED_CREATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_RELATIONSHIP_MGR = "EDIT_REJECTED_UPDATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_RELATIONSHIP_MGR = "EDIT_REJECTED_DELETE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_CLOSE_REJECTED_RELATIONSHIP_MGR = "CLOSE_REJECTED_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_RELATIONSHIP_MGR = "CLOSE_REJECTED_CREATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_RELATIONSHIP_MGR = "CLOSE_REJECTED_UPDATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_RELATIONSHIP_MGR = "CLOSE_REJECTED_DELETE_RELATIONSHIP_MGR";

	public static final String SEQUENCE_RELATIONSHIP_MGR = "RELATIONSHIP_MGR_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_STAGE = "STG_RELATIONSHIP_MGR_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_SEC_SUBTYPE = "RELATIONSHIP_MGR_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_SEC_SUBTYPE_STAGE = "STG_RELATIONSHIP_MGR_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_ITEM = "RELATIONSHIP_MGR_ITEM_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_ITEM_STAGE = "STG_RELATIONSHIP_MGR_ITEM_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_DISTRICT_CODE = "RELATIONSHIP_MGR_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_DISTRICT_CODE_STAGE = "STG_RELATIONSHIP_MGR_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_MUKIM_CODE = "RELATIONSHIP_MGR_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_MUKIM_CODE_STAGE = "STG_RELATIONSHIP_MGR_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_PROPERTY_TYPE = "RELATIONSHIP_MGR_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_RELATIONSHIP_MGR_PROPERTY_TYPE_STAGE = "STG_RELATIONSHIP_MGR_PROPERTY_TYPE_SEQ";
	
	public static final String ACTION_MAKER_SAVE_CREATE_RELATIONSHIP_MGR = "SAVE_RELATIONSHIP_MGR";
	
	public static final String ACTION_MAKER_SUBMIT_SAVE_CREATE_RELATIONSHIP_MGR = "CREATE_RELATIONSHIP_MGR";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_RELATIONSHIP_MGR = "CLOSE_CREATE_RELATIONSHIP_MGR";

	public static final String ACTION_MAKER_UPDATE_SAVE_RELATIONSHIP_MGR = "SAVE_UPDATE_RELATIONSHIP_MGR";

	// ********************************************constant for
	// Holiday***************************************

	public static final String INSTANCE_HOLIDAY = "HOLIDAY";

	public static final String ACTION_READ_HOLIDAY = "READ_HOLIDAY";

	public static final String ACTION_READ_HOLIDAY_ID = "READ_HOLIDAY_ID";

	public static final String ACTION_MAKER_CREATE_HOLIDAY = "CREATE_HOLIDAY";

	public static final String ACTION_MAKER_DRAFT_HOLIDAY = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_HOLIDAY = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_HOLIDAY = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_HOLIDAY = "UPDATE_HOLIDAY";

	public static final String ACTION_MAKER_DELETE_HOLIDAY = "DELETE_HOLIDAY";

	public static final String ACTION_CHECKER_APPROVE_HOLIDAY = "APPROVE_HOLIDAY";

	public static final String ACTION_CHECKER_APPROVE_CREATE_HOLIDAY = "APPROVE_CREATE_HOLIDAY";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_HOLIDAY = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_HOLIDAY = "APPROVE_DELETE_HOLIDAY";

	public static final String ACTION_CHECKER_REJECT_HOLIDAY = "REJECT_HOLIDAY";
	public static final String ACTION_CHECKER_REJECT_CREATE_HOLIDAY = "REJECT_HOLIDAY";
	public static final String ACTION_CHECKER_REJECT_EDIT_HOLIDAY = "REJECT_HOLIDAY";
	public static final String ACTION_CHECKER_REJECT_DELETE_HOLIDAY = "REJECT_HOLIDAY";

	public static final String ACTION_MAKER_EDIT_REJECTED_HOLIDAY = "EDIT_REJECTED_HOLIDAY";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_HOLIDAY = "EDIT_REJECTED_CREATE_HOLIDAY";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_HOLIDAY = "EDIT_REJECTED_UPDATE_HOLIDAY";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_HOLIDAY = "EDIT_REJECTED_DELETE_HOLIDAY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_HOLIDAY = "CLOSE_REJECTED_HOLIDAY";

	public static final String ACTION_MAKER_CLOSE_DRAFT_HOLIDAY = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_HOLIDAY = "CLOSE_REJECTED_CREATE_HOLIDAY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_HOLIDAY = "CLOSE_REJECTED_UPDATE_HOLIDAY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_HOLIDAY = "CLOSE_REJECTED_DELETE_HOLIDAY";

	public static final String SEQUENCE_HOLIDAY = "HOLIDAY_SEQ";

	public static final String SEQUENCE_HOLIDAY_STAGE = "STG_HOLIDAY_SEQ";

	public static final String SEQUENCE_HOLIDAY_SEC_SUBTYPE = "HOLIDAY_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_HOLIDAY_SEC_SUBTYPE_STAGE = "STG_HOLIDAY_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_HOLIDAY_ITEM = "HOLIDAY_ITEM_SEQ";

	public static final String SEQUENCE_HOLIDAY_ITEM_STAGE = "STG_HOLIDAY_ITEM_SEQ";

	public static final String SEQUENCE_HOLIDAY_DISTRICT_CODE = "HOLIDAY_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_HOLIDAY_DISTRICT_CODE_STAGE = "STG_HOLIDAY_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_HOLIDAY_MUKIM_CODE = "HOLIDAY_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_HOLIDAY_MUKIM_CODE_STAGE = "STG_HOLIDAY_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_HOLIDAY_PROPERTY_TYPE = "HOLIDAY_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_HOLIDAY_PROPERTY_TYPE_STAGE = "STG_HOLIDAY_PROPERTY_TYPE_SEQ";

	// ********************************************constant for
	// Facility New Master***************************************

	public static final String INSTANCE_FACILITY_NEW_MASTER = "FACILITY_NEW_MASTER";

	public static final String ACTION_READ_FACILITY_NEW_MASTER = "READ_FACILITY_NEW_MASTER";

	public static final String ACTION_READ_FACILITY_NEW_MASTER_ID = "READ_FACILITY_NEW_MASTER_ID";

	public static final String ACTION_MAKER_CREATE_FACILITY_NEW_MASTER = "CREATE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_DRAFT_FACILITY_NEW_MASTER = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_FACILITY_NEW_MASTER = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_FACILITY_NEW_MASTER = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_FACILITY_NEW_MASTER = "UPDATE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_DELETE_FACILITY_NEW_MASTER = "DELETE_FACILITY_NEW_MASTER";

	public static final String ACTION_CHECKER_APPROVE_FACILITY_NEW_MASTER = "APPROVE_FACILITY_NEW_MASTER";

	public static final String ACTION_CHECKER_APPROVE_CREATE_FACILITY_NEW_MASTER = "APPROVE_CREATE_FACILITY_NEW_MASTER";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_FACILITY_NEW_MASTER = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_FACILITY_NEW_MASTER = "APPROVE_DELETE_FACILITY_NEW_MASTER";

	public static final String ACTION_CHECKER_REJECT_FACILITY_NEW_MASTER = "REJECT_FACILITY_NEW_MASTER";
	public static final String ACTION_CHECKER_REJECT_CREATE_FACILITY_NEW_MASTER = "REJECT_FACILITY_NEW_MASTER";
	public static final String ACTION_CHECKER_REJECT_EDIT_FACILITY_NEW_MASTER = "REJECT_FACILITY_NEW_MASTER";
	public static final String ACTION_CHECKER_REJECT_DELETE_FACILITY_NEW_MASTER = "REJECT_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_FACILITY_NEW_MASTER = "EDIT_REJECTED_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_FACILITY_NEW_MASTER = "EDIT_REJECTED_CREATE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_FACILITY_NEW_MASTER = "EDIT_REJECTED_UPDATE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_FACILITY_NEW_MASTER = "EDIT_REJECTED_DELETE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_FACILITY_NEW_MASTER = "CLOSE_REJECTED_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_DRAFT_FACILITY_NEW_MASTER = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_FACILITY_NEW_MASTER = "CLOSE_REJECTED_CREATE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_FACILITY_NEW_MASTER = "CLOSE_REJECTED_UPDATE_FACILITY_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_FACILITY_NEW_MASTER = "CLOSE_REJECTED_DELETE_FACILITY_NEW_MASTER";

	public static final String SEQUENCE_FACILITY_NEW_MASTER = "FACILITY_NEW_MASTER_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_STAGE = "STG_FACILITY_NEW_MASTER_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_SEC_SUBTYPE = "FACILITY_NEW_MASTER_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_SEC_SUBTYPE_STAGE = "STG_FACILITY_NEW_MASTER_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_ITEM = "FACILITY_NEW_MASTER_ITEM_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_ITEM_STAGE = "STG_FACILITY_NEW_MASTER_ITEM_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_DISTRICT_CODE = "FACILITY_NEW_MASTER_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_DISTRICT_CODE_STAGE = "STG_FACILITY_NEW_MASTER_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_MUKIM_CODE = "FACILITY_NEW_MASTER_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_MUKIM_CODE_STAGE = "STG_FACILITY_NEW_MASTER_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_PROPERTY_TYPE = "FACILITY_NEW_MASTER_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_FACILITY_NEW_MASTER_PROPERTY_TYPE_STAGE = "STG_FACILITY_NEW_MASTER_PROPERTY_TYPE_SEQ";

	// ********************************************constant for
	// Collateral New Master***************************************

	public static final String INSTANCE_COLLATERAL_NEW_MASTER = "COLLATERAL_MASTER";

	public static final String ACTION_READ_COLLATERAL_NEW_MASTER = "READ_COLLATERAL_NEW_MASTER";

	public static final String ACTION_READ_COLLATERAL_NEW_MASTER_ID = "READ_COLLATERAL_NEW_MASTER_ID";

	public static final String ACTION_MAKER_CREATE_COLLATERAL_NEW_MASTER = "CREATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_DRAFT_COLLATERAL_NEW_MASTER = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_COLLATERAL_NEW_MASTER = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_COLLATERAL_NEW_MASTER = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_COLLATERAL_NEW_MASTER = "UPDATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_DELETE_COLLATERAL_NEW_MASTER = "DELETE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_CHECKER_APPROVE_COLLATERAL_NEW_MASTER = "APPROVE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_NEW_MASTER = "APPROVE_CREATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_COLLATERAL_NEW_MASTER = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_COLLATERAL_NEW_MASTER = "APPROVE_DELETE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_CHECKER_REJECT_COLLATERAL_NEW_MASTER = "REJECT_COLLATERAL_NEW_MASTER";
	public static final String ACTION_CHECKER_REJECT_CREATE_COLLATERAL_NEW_MASTER = "REJECT_COLLATERAL_NEW_MASTER";
	public static final String ACTION_CHECKER_REJECT_EDIT_COLLATERAL_NEW_MASTER = "REJECT_COLLATERAL_NEW_MASTER";
	public static final String ACTION_CHECKER_REJECT_DELETE_COLLATERAL_NEW_MASTER = "REJECT_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_COLLATERAL_NEW_MASTER = "EDIT_REJECTED_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_COLLATERAL_NEW_MASTER = "EDIT_REJECTED_CREATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_COLLATERAL_NEW_MASTER = "EDIT_REJECTED_UPDATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_COLLATERAL_NEW_MASTER = "EDIT_REJECTED_DELETE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_NEW_MASTER = "CLOSE_REJECTED_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_DRAFT_COLLATERAL_NEW_MASTER = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_COLLATERAL_NEW_MASTER = "CLOSE_REJECTED_CREATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_COLLATERAL_NEW_MASTER = "CLOSE_REJECTED_UPDATE_COLLATERAL_NEW_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_COLLATERAL_NEW_MASTER = "CLOSE_REJECTED_DELETE_COLLATERAL_NEW_MASTER";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER = "COLLATERAL_NEW_MASTER_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_STAGE = "STG_COLLATERAL_NEW_MASTER_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_SEC_SUBTYPE = "COLLATERAL_NEW_MASTER_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_SEC_SUBTYPE_STAGE = "STG_COLLATERAL_NEW_MASTER_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_ITEM = "COLLATERAL_NEW_MASTER_ITEM_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_ITEM_STAGE = "STG_COLLATERAL_NEW_MASTER_ITEM_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_DISTRICT_CODE = "COLLATERAL_NEW_MASTER_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_DISTRICT_CODE_STAGE = "STG_COLLATERAL_NEW_MASTER_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_MUKIM_CODE = "COLLATERAL_NEW_MASTER_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_MUKIM_CODE_STAGE = "STG_COLLATERAL_NEW_MASTER_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_PROPERTY_TYPE = "COLLATERAL_NEW_MASTER_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_COLLATERAL_NEW_MASTER_PROPERTY_TYPE_STAGE = "STG_COLLATERAL_NEW_MASTER_PROPERTY_TYPE_SEQ";
	/********** Add for CreditApproval*******************/
 	
	public static final String ACTION_CHECKER_APPROVE_CREDIT_APPROVAL = "ACTION_CHECKER_APPROVE_CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_UPDATE_CREDIT_APPROVAL_FEED_GROUP = "ACTION_MAKER_UPDATE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREDIT_APPROVAL = "ACTION_MAKER_CLOSE_REJECTED";
	
	public static final String ACTION_CHECKER_REJECT_CREDIT_APPROVAL = "ACTION_CHECKER_REJECT_CREDIT_APPROVAL";
	  
	public static final String ACTION_MAKER_SUBMIT_CREDIT_APPROVAL = "ACTION_MAKER_SUBMIT";
	
	public static final String ACTION_MAKER_UPDATE_REJECTED_CREDIT_APPROVAL = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_CREDIT_APPROVAL = "ACTION_MAKER_SUBMIT_REJECTED";
	
	public static final String ACTION_READ_CREDIT_APPROVAL = "ACTION_READ_CREDIT_APPROVAL";
	
	public static final String INSTANCE_CREDIT_APPROVAL = "CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_CREDIT_APPROVAL_DRAFT_CREDIT_APPROVAL = "ACTION_MAKER_CLOSE_DRAFT";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_CREDIT_APPROVAL = "CHECKER_APPROVE_CREATE_CREDIT_APPROVAL";
	
	public static final String ACTION_PENDING_CREATE = "PENDING_CREATE";
	
	public static final String ACTION_STATUS = "ACTIVE";
	
	public static final String ACTION_MAKER_UPDATE_CREDIT_APPROVAL = "UPDATE_CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREDIT_APPROVAL = "EDIT_REJECTED_CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CREDIT_APPROVAL = "EDIT_REJECTED_UPDATE_CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CREDIT_APPROVAL = "EDIT_REJECTED_CREATE_CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_CREDIT_APPROVAL = "EDIT_REJECTED_DELETE_CREDIT_APPROVAL";
	
	public static final String ACTION_MAKER_DELETE_CREDIT_APPROVAL = "DELETE_CREDIT_APPROVAL";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_CREDIT_APPROVAL = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_CREDIT_APPROVAL = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_CREATE_CREDIT_APPROVAL = "ACTION_MAKER_SUBMIT";
	
	public static final String ACTION_MAKER_SAVE_CREDIT_APPROVAL = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL = "MAKER_UPDATE_CLOSE";
	
	
//********************* Constants for Insurance Coverage ****************
	
	public static final String INSTANCE_INSURANCE_COVERAGE = "INSURANCE_COVERAGE";

	public static final String ACTION_READ_INSURANCE_COVERAGE = "READ_INSURANCE_COVERAGE";

	public static final String ACTION_READ_INSURANCE_COVERAGE_ID = "READ_INSURANCE_COVERAGE_ID";

	public static final String ACTION_MAKER_CREATE_INSURANCE_COVERAGE = "CREATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_UPDATE_INSURANCE_COVERAGE = "UPDATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_DELETE_INSURANCE_COVERAGE = "DELETE_INSURANCE_COVERAGE";

	public static final String ACTION_CHECKER_APPROVE_INSURANCE_COVERAGE = "APPROVE_INSURANCE_COVERAGE";

	public static final String ACTION_CHECKER_APPROVE_CREATE_INSURANCE_COVERAGE = "APPROVE_CREATE_INSURANCE_COVERAGE";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_INSURANCE_COVERAGE = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_INSURANCE_COVERAGE = "APPROVE_DELETE_INSURANCE_COVERAGE";

	public static final String ACTION_CHECKER_REJECT_INSURANCE_COVERAGE = "REJECT_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_EDIT_REJECTED_INSURANCE_COVERAGE = "EDIT_REJECTED_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_INSURANCE_COVERAGE = "EDIT_REJECTED_CREATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_INSURANCE_COVERAGE = "EDIT_REJECTED_UPDATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_INSURANCE_COVERAGE = "EDIT_REJECTED_DELETE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_INSURANCE_COVERAGE = "CLOSE_REJECTED_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_INSURANCE_COVERAGE = "CLOSE_REJECTED_CREATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_INSURANCE_COVERAGE = "CLOSE_REJECTED_UPDATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_INSURANCE_COVERAGE = "CLOSE_REJECTED_DELETE_INSURANCE_COVERAGE";

	public static final String SEQUENCE_INSURANCE_COVERAGE = "INSURANCE_COVERAGE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_STAGE = "STG_INSURANCE_COVERAGE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_SEC_SUBTYPE = "INSURANCE_COVERAGE_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_SEC_SUBTYPE_STAGE = "STG_INSURANCE_COVERAGE_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_ITEM = "INSURANCE_COVERAGE_ITEM_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_ITEM_STAGE = "STG_INSURANCE_COVERAGE_ITEM_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DISTRICT_CODE = "INSURANCE_COVERAGE_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DISTRICT_CODE_STAGE = "STG_INSURANCE_COVERAGE_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_MUKIM_CODE = "INSURANCE_COVERAGE_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_MUKIM_CODE_STAGE = "STG_INSURANCE_COVERAGE_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_PROPERTY_TYPE = "INSURANCE_COVERAGE_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_PROPERTY_TYPE_STAGE = "STG_INSURANCE_COVERAGE_PROPERTY_TYPE_SEQ";
	
	public static final String ACTION_MAKER_SAVE_CREATE_INSURANCE_COVERAGE = "SAVE_INSURANCE_COVERAGE";
	
	public static final String ACTION_MAKER_SUBMIT_SAVE_CREATE_INSURANCE_COVERAGE = "CREATE_INSURANCE_COVERAGE";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_INSURANCE_COVERAGE = "CLOSE_CREATE_INSURANCE_COVERAGE";

	public static final String ACTION_MAKER_UPDATE_SAVE_INSURANCE_COVERAGE = "SAVE_UPDATE_INSURANCE_COVERAGE";
	
//********************* Constants for Insurance Coverage Details ****************
	
	public static final String INSTANCE_INSURANCE_COVERAGE_DTLS = "INSU_CVRG_DTLS";

	public static final String ACTION_READ_INSURANCE_COVERAGE_DTLS = "READ_INSU_CVRG_DTLS";

	public static final String ACTION_READ_INSURANCE_COVERAGE_DTLS_ID = "READ_INSU_CVRG_DTLS_ID";

	public static final String ACTION_MAKER_CREATE_INSURANCE_COVERAGE_DTLS = "CREATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_UPDATE_INSURANCE_COVERAGE_DTLS = "UPDATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_DELETE_INSURANCE_COVERAGE_DTLS = "DELETE_INSU_CVRG_DTLS";

	public static final String ACTION_CHECKER_APPROVE_INSURANCE_COVERAGE_DTLS = "APPROVE_INSU_CVRG_DTLS";

	public static final String ACTION_CHECKER_APPROVE_CREATE_INSURANCE_COVERAGE_DTLS = "APPROVE_CREATE_INSU_CVRG_DTLS";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_INSURANCE_COVERAGE_DTLS = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_INSURANCE_COVERAGE_DTLS = "APPROVE_DELETE_INSU_CVRG_DTLS";

	public static final String ACTION_CHECKER_REJECT_INSURANCE_COVERAGE_DTLS = "REJECT_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_EDIT_REJECTED_INSURANCE_COVERAGE_DTLS = "EDIT_REJECTED_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_INSURANCE_COVERAGE_DTLS = "EDIT_REJECTED_CREATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_INSURANCE_COVERAGE_DTLS = "EDIT_REJECTED_UPDATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_INSURANCE_COVERAGE_DTLS = "EDIT_REJECTED_DELETE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_CLOSE_REJECTED_INSURANCE_COVERAGE_DTLS = "CLOSE_REJECTED_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_INSURANCE_COVERAGE_DTLS = "CLOSE_REJECTED_CREATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_INSURANCE_COVERAGE_DTLS = "CLOSE_REJECTED_UPDATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_INSURANCE_COVERAGE_DTLS = "CLOSE_REJECTED_DELETE_INSU_CVRG_DTLS";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS = "INSU_CVRG_DTLS_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_STAGE = "STG_INSU_CVRG_DTLS_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_SEC_SUBTYPE = "INSU_CVRG_DTLS_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_SEC_SUBTYPE_STAGE = "STG_INSU_CVRG_DTLS_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_ITEM = "INSU_CVRG_DTLS_ITEM_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_ITEM_STAGE = "STG_INSU_CVRG_DTLS_ITEM_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_DISTRICT_CODE = "INSU_CVRG_DTLS_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_DISTRICT_CODE_STAGE = "STG_INSU_CVRG_DTLS_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_MUKIM_CODE = "INSU_CVRG_DTLS_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_MUKIM_CODE_STAGE = "STG_INSU_CVRG_DTLS_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_PROPERTY_TYPE = "INSURANCE_COVERAGE_DTLS_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_INSURANCE_COVERAGE_DTLS_PROPERTY_TYPE_STAGE = "STG_INSU_CVRG_DTLS_PROPERTY_TYPE_SEQ";	
	
	public static final String ACTION_MAKER_SAVE_CREATE_INSURANCE_COVERAGE_DTLS = "SAVE_INSU_CVRG_DTLS";
	
	public static final String ACTION_MAKER_SUBMIT_SAVE_CREATE_INSURANCE_COVERAGE_DTLS = "CREATE_INSU_CVRG_DTLS";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_INSURANCE_COVERAGE_DTLS = "CLOSE_CREATE_INSU_CVRG_DTLS";

	public static final String ACTION_MAKER_UPDATE_SAVE_INSURANCE_COVERAGE_DTLS = "SAVE_UPDATE_INSU_CVRG_DTLS";
	
	// constants for Doc item
	
	public static final String ACTION_MAKER_DRAFT_DOC_ITEM = "DRAFT_DOC_ITEM";
	
	public static final String ACTION_MAKER_UPDATE_DRAFT_DOC_ITEM = "UPDATE_DRAFT_DOC_ITEM";
	
	public static final String  MAKER_UPDATE_CLOSE ="MAKER_UPDATE_CLOSE";

 /****************************** CONSTANTS FOR DIRECTOR MASTER START **************************/
	
	
	public static final String INSTANCE_DIRECTOR_MASTER = "DIRECTOR_MASTER";

	public static final String ACTION_READ_DIRECTOR_MASTER = "READ_DIRECTOR_MASTER";

	public static final String ACTION_READ_DIRECTOR_MASTER_ID = "READ_DIRECTOR_MASTER_ID";

	public static final String ACTION_MAKER_CREATE_DIRECTOR_MASTER = "CREATE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_SAVE_DIRECTOR_MASTER = "SAVE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_DIRECTOR_MASTER = "SAVE_UPDATE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_UPDATE_DIRECTOR_MASTER = "UPDATE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_DELETE_DIRECTOR_MASTER = "DELETE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_DISABLE_DIRECTOR_MASTER = "DISABLE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_ENABLE_DIRECTOR_MASTER = "ENABLE_DIRECTOR_MASTER";
  
	public static final String ACTION_CHECKER_APPROVE_DIRECTOR_MASTER = "APPROVE_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_APPROVE_CREATE_DIRECTOR_MASTER = "APPROVE_CREATE_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_DIRECTOR_MASTER = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_DIRECTOR_MASTER = "APPROVE_DELETE_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_APPROVE_DISABLE_DIRECTOR_MASTER = "APPROVE_DISABLE_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_APPROVE_ENABLE_DIRECTOR_MASTER = "APPROVE_ENABLE_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_REJECT_DIRECTOR_MASTER = "REJECT_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_REJECT_CREATE_DIRECTOR_MASTER = "REJECT_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_REJECT_EDIT_DIRECTOR_MASTER = "REJECT_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_REJECT_DELETE_DIRECTOR_MASTER = "REJECT_DIRECTOR_MASTER";
	
	public static final String ACTION_CHECKER_REJECT_DISABLE_DIRECTOR_MASTER = "REJECT_DIRECTOR_MASTER";

	public static final String ACTION_CHECKER_REJECT_ENABLE_DIRECTOR_MASTER = "ENABLE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_DIRECTOR_MASTER = "EDIT_REJECTED_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_DIRECTOR_MASTER = "EDIT_REJECTED_CREATE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_DIRECTOR_MASTER = "EDIT_REJECTED_UPDATE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_DIRECTOR_MASTER = "EDIT_REJECTED_DELETE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DISABLE_DIRECTOR_MASTER = "EDIT_REJECTED_DISABLE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_ENABLE_DIRECTOR_MASTER = "EDIT_REJECTED_ENABLE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DIRECTOR_MASTER = "CLOSE_REJECTED_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_DIRECTOR_MASTER = "CLOSE_DRAFT_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_DIRECTOR_MASTER = "CLOSE_REJECTED_CREATE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_DIRECTOR_MASTER = "CLOSE_REJECTED_UPDATE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_DIRECTOR_MASTER = "CLOSE_REJECTED_DELETE_DIRECTOR_MASTER";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DISABLE_DIRECTOR_MASTER = "CLOSE_REJECTED_DISABLE_DIRECTOR_MASTER";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_ENABLE_DIRECTOR_MASTER = "CLOSE_REJECTED_ENABLE_DIRECTOR_MASTER";
	
	public static final String SEQUENCE_DIRECTOR_MASTER = "DIRECTOR_MASTER_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_STAGE = "STG_DIRECTOR_MASTER_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_SEC_SUBTYPE = "DIRECTOR_MASTER_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_SEC_SUBTYPE_STAGE = "STG_DIRECTOR_MASTER_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_ITEM = "DIRECTOR_MASTER_ITEM_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_ITEM_STAGE = "STG_DIRECTOR_MASTER_ITEM_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_DISTRICT_CODE = "DIRECTOR_MASTER_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_DISTRICT_CODE_STAGE = "STG_DIRECTOR_MASTER_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_MUKIM_CODE = "DIRECTOR_MASTER_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_MUKIM_CODE_STAGE = "STG_DIRECTOR_MASTER_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_PROPERTY_TYPE = "DIRECTOR_MASTER_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_DIRECTOR_MASTER_PROPERTY_TYPE_STAGE = "STG_SYSTEM_BANK_BRANCH_PROPERTY_TYPE_SEQ";

	public static final Object ACTION_MAKER_APPROVE_DIRECTOR_MASTER = "APPROVE_DIRECTOR_MASTER";

	public static final Object ACTION_MAKER_ENABLE_REJECT_DIRECTOR_MASTER ="REJECT_DIRECTOR_MASTER";

	public static final Object STATE_ENABLE = "ENABLE";

	public static final String STATE_DISABLE = "DISABLE";
	
	public static final String STATE_DISABLED = "DISABLED";
	
	public static final String STATE_ENABLED = "ENABLED";

	public static final String STATE_PENDING_DISABLE = "PENDING_DISABLE";
	
	public static final String STATE_PENDING_ENABLE = "PENDING_ENABLE";

	public static final String STATE_REJECTED_DISABLE = "REJECTED_DISABLE";
	
	public static final String STATE_REJECTED_ENABLE = "REJECTED_ENABLE";
	public static final String ACTION_CHECKER_REJECT_DISABLE_COL = "CHECKER_REJECT_DISABLE";
	
	public static final String ACTION_CHECKER_REJECT_ENABLE_COL = "CHECKER_REJECT_ENABLE";

	public static final String ACTION_CHECKER_APPROVE_DISABLE_COL = "CHECKER_APPROVE_DISABLE";
	
	public static final String ACTION_CHECKER_APPROVE_ENABLE_COL = "CHECKER_APPROVE_ENABLE";

	public static final String ACTION_MAKER_DISABLE_COL = "MAKER_DISABLE";
	
	public static final String ACTION_MAKER_ENABLE_COL = "MAKER_ENABLE";

	public static final String ACTION_CHECKER_APPROVE_DISABLE_PASS_COL = "CHECKER_APPROVE_LOAD_DISABLE";

 /****************************** CONSTANTS FOR DIRECTOR MASTER END **************************/
	
	 /****************************** CONSTANTS FOR PARTY GROUP START **************************/

	public static final String INSTANCE_PARTY_GROUP = "PARTY_GROUP";

	public static final String ACTION_READ_PARTY_GROUP = "READ_PARTY_GROUP";

	public static final String ACTION_READ_PARTY_GROUP_ID = "READ_PARTY_GROUP_ID";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_PARTY_GROUP = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_DRAFT_PARTY_GROUP = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_PARTY_GROUP = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_PARTY_GROUP = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_PARTY_GROUP = "CLOSE_REJECTED_PARTY_GROUP";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_PARTY_GROUP = "CLOSE_REJECTED_CREATE_PARTY_GROUP";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_PARTY_GROUP = "CLOSE_REJECTED_UPDATE_PARTY_GROUP";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_PARTY_GROUP = "CLOSE_REJECTED_DELETE_PARTY_GROUP";

	public static final String SEQUENCE_PARTY_GROUP = "PARTY_GROUP_SEQ";
	
	public static final String ACTION_CHECKER_REJECT_PARTY_GROUP = "REJECT_PARTY_GROUP";
	
	public static final String ACTION_CHECKER_REJECT_ACTIVATE_PARTY_GROUP = "REJECT_ACTIVATE_PARTY_GROUP";
	
	public static final String ACTION_CHECKER_APPROVE_PARTY_GROUP = "APPROVE_PARTY_GROUP";

	public static final String ACTION_CHECKER_APPROVE_CREATE_PARTY_GROUP = "APPROVE_CREATE_PARTY_GROUP";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_PARTY_GROUP = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_ENABLE_PARTY_GROUP = "CHECKER_APPROVE_ENABLE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_PARTY_GROUP = "CHECKER_APPROVE_DELETE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_PARTY_GROUP = "EDIT_REJECTED_PARTY_GROUP";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_PARTY_GROUP = "EDIT_REJECTED_CREATE_PARTY_GROUP";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_PARTY_GROUP = "EDIT_REJECTED_UPDATE_PARTY_GROUP";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_PARTY_GROUP = "EDIT_REJECTED_DELETE_PARTY_GROUP";

	public static final String ACTION_MAKER_EDIT_REJECTED_ENABLE_PARTY_GROUP = "EDIT_REJECTED_ENABLE_PARTY_GROUP";

	public static final String ACTION_MAKER_UPDATE_PARTY_GROUP = "UPDATE_PARTY_GROUP";
	
	public static final String ACTION_MAKER_CREATE_PARTY_GROUP = "CREATE_PARTY_GROUP";
	
	public static final String ACTION_MAKER_DELETE_PARTY_GROUP = "DELETE_PARTY_GROUP";
	
	public static final String ACTION_MAKER_ACTIVATE_PARTY_GROUP = "ACTIVATE_PARTY_GROUP";
	
	 /****************************** CONSTANTS FOR PARTY GROUP END **************************/
	
/******************* Constants for Master Geography Start By Sandeep Shinde on 11-04-2011****************/
	
	/*	Country Master	*/
	public static final String INSTANCE_COUNTRY = "COUNTRY";
	
	public static final String ACTION_READ_COUNTRY = "READ_COUNTRY";
	
	public static final String ACTION_READ_COUNTRY_ID = "READ_COUNTRY_ID";
	
	public static final String ACTION_MAKER_CREATE_COUNTRY = "CREATE_COUNTRY";
	
	public static final String ACTION_MAKER_UPDATE_COUNTRY = "UPDATE_COUNTRY";
	
	public static final String ACTION_MAKER_DELETE_COUNTRY = "DELETE_COUNTRY";
	
	public static final String ACTION_MAKER_ACTIVATE_COUNTRY = "ACTIVATE_COUNTRY";
	
	public static final String ACTION_CHECKER_APPROVE_COUNTRY = "APPROVE_COUNTRY";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_COUNTRY = "APPROVE_CREATE_COUNTRY";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_COUNTRY = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_COUNTRY = "APPROVE_DELETE_COUNTRY";
	
	public static final String ACTION_CHECKER_REJECT_COUNTRY = "REJECT_COUNTRY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_COUNTRY = "EDIT_REJECTED_COUNTRY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_COUNTRY = "EDIT_REJECTED_CREATE_COUNTRY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_COUNTRY = "EDIT_REJECTED_UPDATE_COUNTRY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_COUNTRY = "EDIT_REJECTED_DELETE_COUNTRY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_INACTIVE_COUNTRY = "EDIT_REJECTED_INACTIVE_COUNTRY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_COUNTRY = "CLOSE_REJECTED_COUNTRY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_COUNTRY = "CLOSE_REJECTED_CREATE_COUNTRY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_COUNTRY = "CLOSE_REJECTED_UPDATE_COUNTRY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_COUNTRY = "CLOSE_REJECTED_DELETE_COUNTRY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_INACTIVE_COUNTRY = "CLOSE_REJECTED_INACTIVE_COUNTRY";
	
	public static final String ACTION_MAKER_DRAFT_COUNTRY = "DRAFT";

	public static final String ACTION_MAKER_SAVE_COUNTRY = "MAKER_SAVE_COUNTRY";
	
	public static final String ACTION_MAKER_UPDATE_SAVE_COUNTRY = "MAKER_UPDATE_SAVE_COUNTRY";
	
			/*	Region Master	*/
	public static final String INSTANCE_REGION = "REGION";
	
	public static final String ACTION_READ_REGION = "READ_REGION";
	
	public static final String ACTION_READ_REGION_ID = "READ_REGION_ID";
	
	public static final String ACTION_MAKER_CREATE_REGION = "CREATE_REGION";
	
	public static final String ACTION_MAKER_UPDATE_REGION = "UPDATE_REGION";
	
	public static final String ACTION_MAKER_DELETE_REGION = "DELETE_REGION";
	
	public static final String ACTION_MAKER_ACTIVATE_REGION = "ACTIVATE_REGION";
	
	public static final String ACTION_CHECKER_APPROVE_REGION = "APPROVE_REGION";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_REGION = "APPROVE_CREATE_REGION";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_REGION = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_REGION = "APPROVE_DELETE_REGION";
	
	public static final String ACTION_CHECKER_REJECT_REGION = "REJECT_REGION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_REGION = "EDIT_REJECTED_REGION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_REGION = "EDIT_REJECTED_CREATE_REGION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_REGION = "EDIT_REJECTED_UPDATE_REGION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_REGION = "EDIT_REJECTED_DELETE_REGION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_INACTIVE_REGION = "EDIT_REJECTED_INACTIVE_REGION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_REGION = "CLOSE_REJECTED_REGION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_REGION = "CLOSE_REJECTED_CREATE_REGION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_REGION = "CLOSE_REJECTED_UPDATE_REGION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_REGION = "CLOSE_REJECTED_DELETE_REGION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_INACTIVE_REGION = "CLOSE_REJECTED_INACTIVE_REGION";
	
	public static final String ACTION_MAKER_DRAFT_REGION = "DRAFT";

	public static final String ACTION_MAKER_SAVE_REGION = "MAKER_SAVE_REGION";
	
	public static final String ACTION_MAKER_UPDATE_SAVE_REGION = "MAKER_UPDATE_SAVE_REGION";
	
		/*	State Master	*/
	public static final String INSTANCE_STATE = "STATE";
	
	public static final String ACTION_READ_STATE = "READ_STATE";
	
	public static final String ACTION_READ_STATE_ID = "READ_STATE_ID";
	
	public static final String ACTION_MAKER_CREATE_STATE = "CREATE_STATE";
	
	public static final String ACTION_MAKER_UPDATE_STATE = "UPDATE_STATE";
	
	public static final String ACTION_MAKER_DELETE_STATE = "DELETE_STATE";
	
	public static final String ACTION_MAKER_ACTIVATE_STATE = "ACTIVATE_STATE";
	
	public static final String ACTION_CHECKER_APPROVE_STATE = "APPROVE_STATE";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_STATE = "APPROVE_CREATE_STATE";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_STATE = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_STATE = "APPROVE_DELETE_STATE";
	
	public static final String ACTION_CHECKER_REJECT_STATE = "REJECT_STATE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_STATE = "EDIT_REJECTED_STATE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_STATE = "EDIT_REJECTED_CREATE_STATE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_STATE = "EDIT_REJECTED_UPDATE_STATE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_STATE = "EDIT_REJECTED_DELETE_STATE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_INACTIVE_STATE = "EDIT_REJECTED_INACTIVE_STATE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_STATE = "CLOSE_REJECTED_STATE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_STATE = "CLOSE_REJECTED_CREATE_STATE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_STATE = "CLOSE_REJECTED_UPDATE_STATE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_STATE = "CLOSE_REJECTED_DELETE_STATE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_INACTIVE_STATE = "CLOSE_REJECTED_INACTIVE_STATE";
	
	public static final String ACTION_MAKER_DRAFT_STATE = "DRAFT";

	public static final String ACTION_MAKER_SAVE_STATE = "MAKER_SAVE_STATE";
	
	public static final String ACTION_MAKER_UPDATE_SAVE_STATE = "MAKER_UPDATE_SAVE_STATE";
	
		/*	City Master	*/
	public static final String INSTANCE_CITY = "CITY";
	
	public static final String ACTION_READ_CITY = "READ_CITY";
	
	public static final String ACTION_READ_CITY_ID = "READ_CITY_ID";
	
	public static final String ACTION_MAKER_CREATE_CITY = "CREATE_CITY";
	
	public static final String ACTION_MAKER_UPDATE_CITY = "UPDATE_CITY";
	
	public static final String ACTION_MAKER_DELETE_CITY = "DELETE_CITY";
	
	public static final String ACTION_MAKER_ACTIVATE_CITY = "ACTIVATE_CITY";
	
	public static final String ACTION_PENDING_ACTIVATE = "PENDING_ACTIVATE";
	
	public static final String ACTION_CHECKER_APPROVE_CITY = "APPROVE_CITY";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_CITY = "APPROVE_CREATE_CITY";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_CITY = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_CITY = "APPROVE_DELETE_CITY";
	
	public static final String ACTION_CHECKER_REJECT_CITY = "REJECT_CITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CITY = "EDIT_REJECTED_CITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CITY = "EDIT_REJECTED_CREATE_CITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CITY = "EDIT_REJECTED_UPDATE_CITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_CITY = "EDIT_REJECTED_DELETE_CITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_INACTIVE_CITY = "EDIT_REJECTED_INACTIVE_CITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CITY = "CLOSE_REJECTED_CITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CITY = "CLOSE_REJECTED_CREATE_CITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CITY = "CLOSE_REJECTED_UPDATE_CITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_CITY = "CLOSE_REJECTED_DELETE_CITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_INACTIVE_CITY = "CLOSE_REJECTED_INACTIVE_CITY";

	public static final String ACTION_MAKER_DRAFT_CITY = "DRAFT";

	public static final String ACTION_MAKER_SAVE_CITY = "MAKER_SAVE_CITY";
	
	public static final String ACTION_MAKER_UPDATE_SAVE_CITY = "MAKER_UPDATE_SAVE_CITY";
	/** End of Geogrphy master*/
	
	// Shive has added for System File upload
	public static final String ACTION_MAKER_FILE_INSERT = "FILE_INSERT";
	
	public static final String ACTION_READ_FILE_INSERT = "READ_FILE_INSERT";
	
	public static final String ACTION_CHECKER_INSERT_APPROVE_MASTER = "APPROVE_INSERT_FILE_MASTER";
																				   
	public static final String ACTION_CHECKER_INSERT_REJECT_MASTER = "REJECT_INSERT_FILE_MASTER";
	
	public static final String ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER = "CLOSE_REJECTED_INSERT_SYSTEM_BANK_BRANCH";
	 																					
	public static final String ACTION_CHECKER_CREATE_SYSTEM_BANK_BRANCH = "INSERT_SYSTEM_BANK_BRANCH";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_FILE_MASTER = "CLOSE_REJECTED_FILE_MASTER";
	
	public static final String STATE_PENDING_INSERT = "PENDING_INSERT";
	
	public static final String INSTANCE_INSERT_SYS_BRANCH = "INSERT_SYS_BRANCH";
	
	public static final String INSTANCE_INSERT_HOLIDAY = "INSERT_HOLIDAY";
	
	public static final String ACTION_CHECKER_FILE_MASTER = "INSERT_FILE_MASTER";

	 /****************************** CONSTANTS FOR VALUATION AGENCY START **************************/
    public static final String INSTANCE_VALUATION_AGENCY = "VALUATION_AGENCY";
	
	public static final String ACTION_READ_VALUATION_AGENCY = "READ_VALUATION_AGENCY";
	
	public static final String ACTION_READ_VALUATION_AGENCY_ID = "READ_VALUATION_AGENCY_ID";

	public static final String ACTION_MAKER_CLOSE_REJECTED_VALUATION_AGENCY = "CLOSE_REJECTED_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_VALUATION_AGENCY = "MAKER_UPDATE_CLOSE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_VALUATION_AGENCY = "CLOSE_REJECTED_CREATE_VALUATION_AGENCY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_VALUATION_AGENCY  = "CLOSE_REJECTED_UPDATE_VALUATION_AGENCY";
    
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_VALUATION_AGENCY = "CLOSE_REJECTED_DELETE_VALUATION_AGENCY";
    
	public static final String ACTION_MAKER_EDIT_REJECTED_VALUATION_AGENCY = "EDIT_REJECTED_VALUATION_AGENCY";
    
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_VALUATION_AGENCY = "EDIT_REJECTED_CREATE_VALUATION_AGENCY";
    

	public static final String ACTION_MAKER_CREATE_VALUATION_AGENCY  = "CREATE_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_VALUATION_AGENCY = "UPDATE_VALUATION_AGENCY";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_VALUATION_AGENCY = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_VALUATION_AGENCY = "APPROVE_CREATE_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_VALUATION_AGENCY = "EDIT_REJECTED_UPDATE_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_ENABLE_VALUATION_AGENCY = "ENABLE_VALUATION_AGENCY"; 

	public static final String ACTION_MAKER_DISABLE_VALUATION_AGENCY = "DISABLE_VALUATION_AGENCY";	
	
	public static final String ACTION_CHECKER_APPROVE_VALUATION_AGENCY = "APPROVE_VALUATION_AGENCY";	
	
	public static final String ACTION_CHECKER_REJECT_VALUATION_AGENCY = "REJECT_VALUATION_AGENCY";
	
	public static final String ACTION_CHECKER_REJECT_DISABLE_VALUATION_AGENCY = "REJECT_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_ENABLE_VALUATION_AGENCY = "EDIT_REJECTED_ENABLE_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DISABLE_VALUATION_AGENCY = "CLOSE_REJECTED_DISABLE_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_ENABLE_VALUATION_AGENCY = "CLOSE_REJECTED_ENABLE_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_VALUATION_AGENCY = "MAKER_UPDATE_SAVE";	

	public static final String ACTION_MAKER_SAVE_VALUATION_AGENCY = "MAKER_SAVE";
	
 public static final String INSTANCE_INSERT_VALUATION_AGENCY = "ADD_VALUATION_AGENCY";
	
	public static final String ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_VALUATION_AGENCY = "CLOSE_REJECTED_INSERT_VALUATION_AGENCY";
	
	//******************************************UPLOAD OTHER BANK*******************************
	
	public static final String ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_OTHER_BANK = "CLOSE_REJECTED_INSERT_OTHER_BANK";
	
	public static final String INSTANCE_INSERT_OTHER_BANK = "INSERT_OTHER_BANK";	
	

	//******************************************UPLOAD OTHER BANK END*******************************
	
	
	//******************************************UPLOAD OTHER BANK BRANCH*******************************
	
	public static final String ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_OTHER_BANK_BRANCH = "CLOSE_REJECTED_INSERT_OTHER_BANK_BRANCH";
	
	public static final String INSTANCE_INSERT_OTHER_BANK_BRANCH = "ADD_OTHER_BANKBRANCH";	
	

	//******************************************UPLOAD OTHER BANK BRANCH END*******************************
	
	//******************************************UPLOAD OTHER BANK*******************************
	
	public static final String ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_STOCK = "CLOSE_REJECTED_INSERT_STOCK";
	
	public static final String INSTANCE_INSERT_STOCK = "INSERT_STOCK";	
	

	
	
	/****************************** CONSTANTS FOR VALUATION AGENCY END **************************/
	
// ************************* Mutual Funds Starts Here **********************************
	
	public static final String INSTANCE_MUTUAL_FUNDS_FEED_GROUP = "MUTUAL_FUNDS_GROUP";

	public static final String ACTION_READ_MUTUAL_FUNDS_FEED_GROUP = "ACTION_READ_MUTUAL_FUNDS_GROUP";

	public static final String ACTION_MAKER_UPDATE_MUTUAL_FUNDS_FEED_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_MUTUAL_FUNDS_FEED_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_MUTUAL_FUNDS_FEED_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_MUTUAL_FUNDS_FEED_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_MUTUAL_FUNDS_FEED_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_MUTUAL_FUNDS_FEED_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_MUTUAL_FUNDS_FEED_GROUP = "ACTION_CHECKER_APPROVE_MUTUAL_FUNDS_GROUP";

	public static final String ACTION_CHECKER_REJECT_MUTUAL_FUNDS_FEED_GROUP = "ACTION_CHECKER_REJECT_MUTUAL_FUNDS_GROUP";

	public static final String MUTUAL_FUNDS_FEED_GROUP_TYPE = "MUTUAL_FUNDS";
	
	// ************************* Mutual Funds Ends Here **********************************
	/*********************  Start Constants for Rbi Category*****************/

	public static final String INSTANCE_RBI_CATEGORY = "RBI_CATEGORY";

	public static final String ACTION_READ_RBI_CATEGORY = "READ_RBI_CATEGORY";

	public static final String ACTION_READ_RBI_CATEGORY_ID = "READ_RBI_CATEGORY_ID";

	public static final String ACTION_MAKER_CREATE_RBI_CATEGORY = "CREATE_RBI_CATEGORY";

	public static final String ACTION_MAKER_UPDATE_RBI_CATEGORY = "UPDATE_RBI_CATEGORY";

	public static final String ACTION_MAKER_DELETE_RBI_CATEGORY = "DELETE_RBI_CATEGORY";

	public static final String ACTION_CHECKER_APPROVE_RBI_CATEGORY = "APPROVE_RBI_CATEGORY";

	public static final String ACTION_CHECKER_APPROVE_CREATE_RBI_CATEGORY = "APPROVE_CREATE_RBI_CATEGORY";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_RBI_CATEGORY = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_RBI_CATEGORY = "APPROVE_DELETE_RBI_CATEGORY";

	public static final String ACTION_CHECKER_REJECT_RBI_CATEGORY = "REJECT_RBI_CATEGORY";
	
	public static final String ACTION_CHECKER_REJECT_CREATE_RBI_CATEGORY = "REJECT_RBI_CATEGORY";
	
	public static final String ACTION_CHECKER_REJECT_EDIT_RBI_CATEGORY = "REJECT_RBI_CATEGORY";
	
/*	-------------------- Constants for ManualInput Customer Start Added by Sandeep Shinde--------------------	*/
	
	public static final String PENDING_CREATE = "PENDING_CREATE";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_CUSTOMER = "CHECKER_APPROVE_CREATE_CUSTOMER";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_PARTY = "CHECKER_APPROVE_CREATE_CUSTOMER_PARTY";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER = "CHECKER_APPROVE_CREATE_CUSTOMER_BRCHECKER";
	
	public static final String ACTION_CHECKER_REJECT_CREATE_CUSTOMER = "CHECKER_REJECT_CREATE_CUSTOMER";
	
	public static final String ACTION_CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER = "CHECKER_REJECT_CREATE_CUSTOMER_BRCHECKER";
	
	public static final String ACTION_CHECKER_REJECT_CREATE_CUSTOMER_PARTY = "CHECKER_REJECT_CREATE_CUSTOMER_PARTY";

	public static final String ACTION_CHECKER_APPROVE_DELETE_CUSTOMER = "CHECKER_APPROVE_DELETE_CUSTOMER";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_CUSTOMER_PARTY = "CHECKER_APPROVE_DELETE_CUSTOMER_PARTY";
	
	public static final String ACTION_CHECKER_REJECT_DELETE_CUSTOMER = "CHECKER_REJECT_DELETE_CUSTOMER";
	
	public static final String ACTION_MAKER_CLOSE_CUSTOMER = "MAKER_CLOSE_CUSTOMER";
	
	public static final String ACTION_MAKER_CLOSE_CUSTOMER_PARTY = "MAKER_CLOSE_CUSTOMER_PARTY";
	
	public static final String ACTION_MAKER_CLOSE_CUSTOMER_BRMAKER = "MAKER_CLOSE_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_CUSTOMER = "MAKER_CLOSE_DRAFT_CUSTOMER";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER = "MAKER_CLOSE_DRAFT_CUSTOMER_BRMAKER";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_CUSTOMER_PARTY = "MAKER_CLOSE_DRAFT_CUSTOMER_PARTY";
	
	/*	-------------------- Constants for ManualInput Customer End	--------------------	*/
	
	public static final String ACTION_CHECKER_REJECT_DELETE_RBI_CATEGORY = "REJECT_RBI_CATEGORY";

	public static final String ACTION_MAKER_CLOSE_DRAFT_RBI_CATEGORY = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_DRAFT_RBI_CATEGORY = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_RBI_CATEGORY = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_RBI_CATEGORY = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_EDIT_REJECTED_RBI_CATEGORY = "EDIT_REJECTED_RBI_CATEGORY";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_RBI_CATEGORY = "EDIT_REJECTED_CREATE_RBI_CATEGORY";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_RBI_CATEGORY = "EDIT_REJECTED_UPDATE_RBI_CATEGORY";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_RBI_CATEGORY = "EDIT_REJECTED_DELETE_RBI_CATEGORY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_RBI_CATEGORY = "CLOSE_REJECTED_RBI_CATEGORY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_RBI_CATEGORY = "CLOSE_REJECTED_CREATE_RBI_CATEGORY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_RBI_CATEGORY = "CLOSE_REJECTED_UPDATE_RBI_CATEGORY";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_RBI_CATEGORY = "CLOSE_REJECTED_DELETE_RBI_CATEGORY";
	/********************* End Constants for Rbi Category****************/
	//add by govind for insert Credit Approval
	public static final String INSTANCE_INSERT_CREDIT_APPROVAL = "INSERT_CREDIT_APP";
	public static final String INSTANCE_INSERT_FOREX_FEED_GROUP = "INSERT_FOREX_FEED_GR";
	public static final String INSTANCE_INSERT_BOND_FEED_GROUP = "INSERT_BOND";

	/**	Discrepency Master	*/
	public static final String INSTANCE_DISCREPENCY = "DISCREPENCY";
	
	public static final String ACTION_READ_DISCREPENCY = "READ_DISCREPENCY";
	
	public static final String ACTION_READ_DISCREPENCY_ID = "READ_DISCREPENCY_ID";
	
	public static final String ACTION_MAKER_CREATE_DISCREPENCY = "CREATE_DISCREPENCY";
	
	public static final String ACTION_MAKER_UPDATE_DISCREPENCY = "UPDATE_DISCREPENCY";
	
	public static final String ACTION_MAKER_DELETE_DISCREPENCY = "DELETE_DISCREPENCY";
	
	public static final String ACTION_MAKER_ACTIVATE_DISCREPENCY = "ACTIVATE_DISCREPENCY";
	
	public static final String ACTION_PENDING_ACTIVATE_DISCREPENCY = "PENDING_ACTIVATE_DISCREPENCY";
	
	public static final String ACTION_CHECKER_APPROVE_DISCREPENCY = "APPROVE_DISCREPENCY";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_DISCREPENCY = "APPROVE_CREATE_DISCREPENCY";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_DISCREPENCY = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_DISCREPENCY = "APPROVE_DELETE_DISCREPENCY";
	
	public static final String ACTION_CHECKER_REJECT_DISCREPENCY = "REJECT_DISCREPENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DISCREPENCY = "EDIT_REJECTED_DISCREPENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_DISCREPENCY = "EDIT_REJECTED_CREATE_DISCREPENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_DISCREPENCY = "EDIT_REJECTED_UPDATE_DISCREPENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_DISCREPENCY = "EDIT_REJECTED_DELETE_DISCREPENCY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_INACTIVE_DISCREPENCY = "EDIT_REJECTED_INACTIVE_DISCREPENCY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DISCREPENCY = "CLOSE_REJECTED_DISCREPENCY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_DISCREPENCY = "CLOSE_REJECTED_CREATE_DISCREPENCY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_DISCREPENCY = "CLOSE_REJECTED_UPDATE_DISCREPENCY";
//	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_DISCREPENCY = "CLOSE_REJECTED_DELETE_DISCREPENCY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_INACTIVE_DISCREPENCY = "CLOSE_REJECTED_INACTIVE_DISCREPENCY";
//
	public static final String ACTION_MAKER_DRAFT_DISCREPENCY = "DRAFT";

	public static final String ACTION_MAKER_SAVE_DISCREPENCY = "MAKER_SAVE_DISCREPENCY";
	
	public static final String ACTION_MAKER_UPDATE_SAVE_DISCREPENCY = "MAKER_UPDATE_SAVE_DISCREPENCY";
	
		/** End of Discrepency master*/

	/**	ExchangeInformation Master	*/
	public static final String INSTANCE_EXCHANGE_INFORMATION = "EXCHANGE_INFORMATION";
	
	public static final String ACTION_READ_EXCHANGE_INFORMATION = "READ_EXCHANGE_INFORMATION";
	
	public static final String ACTION_READ_EXCHANGE_INFORMATION_ID = "READ_EXCHANGE_INFORMATION_ID";
	
	public static final String ACTION_MAKER_CREATE_EXCHANGE_INFORMATION = "CREATE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_UPDATE_EXCHANGE_INFORMATION = "UPDATE_EXCHANGE_INFORMATION";
	
//	public static final String ACTION_MAKER_DELETE_EXCHANGE_INFORMATION = "DELETE_EXCHANGE_INFORMATION";
//	
//	public static final String ACTION_MAKER_ACTIVATE_EXCHANGE_INFORMATION = "ACTIVATE_EXCHANGE_INFORMATION";
//	
//	public static final String ACTION_PENDING_ACTIVATE_EXCHANGE_INFORMATION = "PENDING_ACTIVATE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_CHECKER_APPROVE_EXCHANGE_INFORMATION = "APPROVE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_EXCHANGE_INFORMATION = "APPROVE_CREATE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_EXCHANGE_INFORMATION = "CHECKER_APPROVE_UPDATE";
	
//	public static final String ACTION_CHECKER_APPROVE_DELETE_EXCHANGE_INFORMATION = "APPROVE_DELETE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_CHECKER_REJECT_EXCHANGE_INFORMATION = "REJECT_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_EXCHANGE_INFORMATION = "EDIT_REJECTED_EXCHANGE_INFORMATION";
	
//	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_EXCHANGE_INFORMATION = "EDIT_REJECTED_CREATE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_EXCHANGE_INFORMATION = "EDIT_REJECTED_UPDATE_EXCHANGE_INFORMATION";
	
//	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_EXCHANGE_INFORMATION = "EDIT_REJECTED_DELETE_EXCHANGE_INFORMATION";
//	
//	public static final String ACTION_MAKER_EDIT_REJECTED_INACTIVE_EXCHANGE_INFORMATION = "EDIT_REJECTED_INACTIVE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_EXCHANGE_INFORMATION = "CLOSE_REJECTED_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_EXCHANGE_INFORMATION = "CLOSE_REJECTED_CREATE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_EXCHANGE_INFORMATION = "CLOSE_REJECTED_UPDATE_EXCHANGE_INFORMATION";
	
//	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_EXCHANGE_INFORMATION = "CLOSE_REJECTED_DELETE_EXCHANGE_INFORMATION";
//	
//	public static final String ACTION_MAKER_CLOSE_REJECTED_INACTIVE_EXCHANGE_INFORMATION = "CLOSE_REJECTED_INACTIVE_EXCHANGE_INFORMATION";

	public static final String ACTION_MAKER_DRAFT_EXCHANGE_INFORMATION = "DRAFT";

	public static final String ACTION_MAKER_SAVE_EXCHANGE_INFORMATION = "MAKER_SAVE_EXCHANGE_INFORMATION";
	
	public static final String ACTION_MAKER_UPDATE_SAVE_EXCHANGE_INFORMATION = "MAKER_UPDATE_SAVE_EXCHANGE_INFORMATION";
	
		/** End of ExchangeInformation master*/

	
	// ************************* Constants for file upload **********
	
	public static final String INSTANCE_INSERT_RELATIONSHIP_MGR = "INSERT_RELATION_MGR";
	
	public static final String INSTANCE_INSERT_INSURANCE_COVERAGE = "INSERT_INSU_CVRG";
	
	public static final String INSTANCE_INSERT_COUNTRY = "INSERT_COUNTRY";
	
	public static final String INSTANCE_INSERT_REGION = "INSERT_REGION";
	
	public static final String INSTANCE_INSERT_STATE = "INSERT_STATE";
	
	public static final String INSTANCE_INSERT_CITY = "INSERT_CITY";
	
// ************************* General Param Starts Here **********************************
	
	public static final String INSTANCE_GENERAL_PARAM_GROUP = "GENERAL_PARAM_GROUP";

	public static final String ACTION_READ_GENERAL_PARAM_GROUP = "ACTION_READ_GENERAL_PARAM_GROUP";

	public static final String ACTION_MAKER_UPDATE_GENERAL_PARAM_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_GENERAL_PARAM_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_GENERAL_PARAM_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_GENERAL_PARAM_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_GENERAL_PARAM_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_GENERAL_PARAM_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_GENERAL_PARAM_GROUP = "ACTION_CHECKER_APPROVE_GENERAL_PARAM_GROUP";

	public static final String ACTION_CHECKER_REJECT_GENERAL_PARAM_GROUP = "ACTION_CHECKER_REJECT_GENERAL_PARAM_GROUP";

	public static final String GENERAL_PARAM_GROUP_TYPE = "GENERAL_PARAM";
	
	// ************************* General Param Ends Here **********************************
	
	// ************************* Approval Matrix Starts Here **********************************
	
	public static final String INSTANCE_APROVAL_MATRIX_GROUP = "APROVAL_MATRIX_GROUP";

	public static final String ACTION_READ_APROVAL_MATRIX_GROUP = "ACTION_READ_APROVAL_MATRIX_GROUP";

	public static final String ACTION_MAKER_UPDATE_APROVAL_MATRIX_GROUP = "ACTION_MAKER_UPDATE";

	public static final String ACTION_MAKER_SUBMIT_APROVAL_MATRIX_GROUP = "ACTION_MAKER_SUBMIT";

	public static final String ACTION_MAKER_SUBMIT_REJECTED_APROVAL_MATRIX_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

	public static final String ACTION_MAKER_UPDATE_REJECTED_APROVAL_MATRIX_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_REJECTED_APROVAL_MATRIX_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

	public static final String ACTION_MAKER_CLOSE_DRAFT_APROVAL_MATRIX_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

	public static final String ACTION_CHECKER_APPROVE_APROVAL_MATRIX_GROUP = "ACTION_CHECKER_APPROVE_APROVAL_MATRIX_GROUP";

	public static final String ACTION_CHECKER_REJECT_APROVAL_MATRIX_GROUP = "ACTION_CHECKER_REJECT_APROVAL_MATRIX_GROUP";

	public static final String APROVAL_MATRIX_GROUP_TYPE = "APPROVAL_MATRIX";
	
	// ************************* Approval Matrix Ends Here **********************************
	
/*Constants user for SMS Upload Module - Start*/
	public static final String USER_UPLOAD = "UserUpload";
	
	public static final String HEADER_INDENTIFIER = "1";
	
	public static final String DATA_INDENTIFIER = "2";
	
	public static final String FOOTER_INDENTIFIER = "3";
	
	public static final String EMPOLOYEE_CODE = "EMPOLOYEE_CODE";
	
	public static final String ROW_IDENTIFIER = "ROW_IDENTIFIER";
	
	public static final String RECORD_STATUS = "RECORD_STATUS";
	
	public static final String EMPOLOYEE_NAME = "EMPOLOYEE_NAME";
	
	public static final String BRANCH_CODE = "BRANCH_CODE";

	public static final String NEW_BRANCH_CODE = "NEW_BRANCH_CODE";
	
	public static final String DEPARTMENT_CODE = "DEPARTMENT_CODE";
	
	public static final String USER_ROLE = "USER_ROLE";
	
	public static final String REGION = "REGION";
	
	public static final String SEGMENT = "SEGMENT";
	
	public static final String STATUS = "STATUS";
	
	public static final String ROW_CHECKSUM = "ROW_CHECKSUM";
	
	public static final String RECORD_STATUS_ADD = "A";
	
	public static final String RECORD_STATUS_MODIFY = "M";
	
	public static final String RECORD_STATUS_ENABLE = "E";
	
	public static final String RECORD_STATUS_UNLOCK = "U";
	
	public static final String RECORD_STATUS_DISABLE = "D";
	
	public static final String RECORD_STATUS_CHANGE_BRANCH= "C";
	
	public static final String RECORD_STATUS_TERMINATE= "T";
	
	public static final String WEBLOGIC = "WEBLOGIC";
	
	public static final String WEBSPHERE = "WEBSPHERE";
	
	
	
	/*Constants user for SMS Upload Module - End*/

	public static final String SEQUENCE_CASH_FIXED_DEPOSIT_LIEN = "CASH_FIXED_DEPOSIT_LIEN_SEQ";	/*	Sequence for Lien Creation on Fixed Deposit by Sandeep Shinde*/
	
		//Start:Code added for UBS,Finware Upload
	public static final String SEQUENCE_LIMIT_UPLOAD = "CMS_LIMIT_UPLOAD_SEQ";
	//End  :Code added for UBS,Finware Upload
	
	public static final int CPU_MAKER_CHECKER = 1013;
	public static final int CPU_ADMIN_MAKER_CHECKER = 1014;
	public static final int ALL_VIEW_REPORT = 1016;
	
	public static final int CPU_MAKER = 1006;
	public static final int CPU_CHECKER = 1007;
	
	public static final int CPU_ADMIN_MAKER = 1004;
	public static final int CPU_ADMIN_CHECKER = 1005;
	
	public static final int CAD_MAKER = 1025;
	public static final int CAD_CHECKER = 1026;
	
	
	public static final String ACTION_CREATE_LIEN = "CREATE_LIEN";
	


	
	/*Added by Dattatray Thorat for Annexure List*/
	
	public static final String INSTANCE_ANNEXURE_CHECKLIST = "ANNEXURE_CHECKLIST";
	
	public static final String ACTION_MAKER_UPDATE_ANNEXURE_CHECKLIST = "UPDATE_CHECKLIST_ANNEXURE"; // SHARE
	
	public static final String ACTION_MAKER_EDIT_REJECTED_ANNEXURE_CHECKLIST = "EDIT_REJECTED_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_ANNEXURE_CHECKLIST = "APPROVE_CREATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_ANNEXURE_CHECKLIST = "APPROVE_UPDATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_CHECKER_REJECT_ANNEXURE_CHECKLIST = "REJECT_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_COPY_ANNEXURE_CHECKLIST = "COPY_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_ACTIVE_ANNEXURE_CHECKLIST = "CLOSE_DRAFT_ACTIVE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_CREATE_ANNEXURE_CHECKLIST = "CLOSE_DRAFT_CREATE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_ANNEXURE_CHECKLIST_RECEIPT = "CLOSE_DRAFT_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_ANNEXURE_CHECKLIST = "CLOSE_REJECTED_CREATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_ANNEXURE_CHECKLIST = "CLOSE_REJECTED_UPDATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_ANNEXURE_CHECKLIST_RECEIPT = "CLOSE_REJECTED_UPDATE__CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_CREATE_ANNEXURE_CHECKLIST = "CREATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_DRAFT_CREATE_ANNEXURE_CHECKLIST = "DRAFT_CREATE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_DRAFT_SAVE_ANNEXURE_CHECKLIST = "DRAFT_SAVE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_DRAFT_UPDATE_ANNEXURE_CHECKLIST = "DRAFT_UPDATE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_ANNEXURE_CHECKLIST = "EDIT_REJECTED_CREATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_ANNEXURE_CHECKLIST = "EDIT_REJECTED_UPDATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_ANNEXURE_CHECKLIST_RECEIPT = "EDIT_REJECTED_UPDATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_SAVE_ACTIVE_ANNEXURE_CHECKLIST = "SAVE_ACTIVE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_SAVE_ANNEXURE_CHECKLIST = "SAVE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_MAKER_SAVE_CREATE_ANNEXURE_CHECKLIST = "SAVE_CREATE_CHECKLIST_ANNEXURE"; // USED
	
	public static final String ACTION_READ_ANNEXURE_CHECKLIST_ID = "READ_ANNEXURE_CHECKLIST_ID";
	
	public static final String ACTION_READ_ANNEXURE_CHECKLIST = "READ_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_SYSTEM_CLOSE_ANNEXURE_CHECKLIST = "SYSTEM_CLOSE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_SYSTEM_CREATE_ANNEXURE_CHECKLIST = "SYSTEM_CREATE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_CHECKER_APPROVE_ANNEXURE_CHECKLIST = "APPROVE_CHECKLIST_ANNEXURE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_ANNEXURE_CHECKLIST = "CLOSE_REJECTED_CHECKLIST_ANNEXURE";
	 //Govind S:File Upload for Stock
	public static final String INSTANCE_INSERT_STOCK_ITEM = "INSERT_STOCK";
	public static final String INSTANCE_INSERT_STOCK_NSE_ITEM = "INSERT_STOCK_NSE";	
	public static final String INSTANCE_INSERT_STOCK_OTHERS_ITEM = "INSERT_STOCK_OTHERS";
	// End File Upload for stock
	
	//Govind S:File Upload for mutual funds
		public static final String INSTANCE_INSERT_MUTUAL_FUNDS_FEED_GROUP = "INSERT_MUTUAL_FUNDS";
	// End File Upload for mutual funds
	
	/*Constants DP Module - Start*/
	public static final String SEQUENCE_GENERAL_CHARGE_DETAILS = "CMS_ASSET_GC_DET_SEQ";
	public static final String SEQUENCE_GENERAL_CHARGE_STOCK_DETAILS = "CMS_ASSET_GC_STOCK_DET_SEQ";
	/*Constants DP Module - End*/
	/*Upload related Constants*/
	public static final String EXCHANGE_RATE_UPLOAD = "ExchangeRateUpload";
	public static final String BOND_ITEM_UPLOAD = "BondItemUpload";
	public static final String STOCK_ITEM_UPLOAD = "StockItemUpload";
	public static final String MUTUAL_FUNDS_ITEM_UPLOAD = "MutualFundsItemUpload";
	public static final String UBS_UPLOAD = "UbsUpload";
	public static final String BAHRAIN_UPLOAD = "BahrainUpload";
	public static final String ACKNOWLEDGMENT_UPLOAD = "ACK_UPLOAD";
	public static final String LEIDETAILS_UPLOAD = "LEI_DETAILS_UPLOAD";
	public static final String HONGKONG_UPLOAD = "HongKongUpload";
	public static final String RELEASELINEDETAILS_UPLOAD = "RLD_UPLOAD";
	public static final String BULK_UDF_UPLOAD="BULK_UDF_UPLOAD";
	public static final String FINWARE_UPLOAD = "FinwareUpload";
	public static final String FINWAREFD_UPLOAD = "FinwareFdUpload";
	public static final String SEQUENCE_UDF= "UDF_SEQ";
	public static final String CPUT_REQUESTED = "1";
	public static final String BRANCH_SENT_NOT_RECEIVED = "2";
	public static final String CPUT_RECEIVED = "3";
	public static final String SEQUENCE_LIMIT_UDF = "LIMIT_PROFILE_UDF_SEQ";
	public static final String SEQUENCE_LIMIT_XREF_UDF = "LIMIT_XREF_UDF_SEQ";	
	public static final String FACILITYDETAILS_UPLOAD = "FCT_UPLOAD";
	public static final String SEQUENCE_LIMIT_XREF_COBORROWER = "LIMIT_XREF_COBORROWER_SEQ";

	public static final String AUTOUPDATIONLMTS_UPLOAD = "AUTO_UPLOAD";

	String MF_SCHEMA_DETAILS_UPLOAD = "MF_SCH_DTL_UPLOAD";
	String STOCK_DETAILS_UPLOAD = "STOCK_DTLS_UPLOAD";
	String BOND_DETAILS_UPLOAD = "BOND_DTL_UPLOAD";
		
		
   // constant added to identify system file upload ftp connection or master sync ftp connection		
		public static final String SYSTEM_FILE_UPLOAD = "SystemFileUpload";
		public static final String MASTER_SYNC_UP = "MasterSyncUp";
		
		//Start:Added by Uma Khot:for FD Flexcube CR
		public static final String FD_FILE_UPLOAD = "FixedDepositFileUpload";
		//End:Added by Uma Khot:for FD Flexcube CR

		//For Posidex File Generation :Start
		public static final String POSIDEX_FILE_UPLOAD = "PosidexFileUpload";
		// //For Posidex File Generation :End
		
	//Start:Code added for Mention primary Column for Pari Passu Due Date
	public static final String PARIPASSU_DUE_DATE_GENERAL_PARAM = "3";
	//End  :Code added for Mention primary Column for Pari Passu Due Date
	
	//Added for cutomer search criteria ANIL
	public static final String CUSTOMER_STATUS_ACTIVE = "ACTIVE";
	public static final String CUSTOMER_STATUS_INACTIVE = "INACTIVE";
	public static final String CUSTOMER_STATUS_ALL = "ALL";

	// For GC
	public String CITY_LABEL_ALL="ALL";
	public String CITY_VALUE_ALL="-999999999";
	
	public static final int TEAM_TYPE_DPC_USER = 1015;

	//////////////////////////////////////Constant for case creation//////////////////
	

	public static final String INSTANCE_CASECREATION = "CASECREATION";

	public static final String ACTION_READ_CASECREATION = "READ_CASECREATION";

	public static final String ACTION_READ_CASECREATION_ID = "READ_CASECREATION_ID";

	public static final String ACTION_MAKER_CREATE_CASECREATION = "CREATE_CASECREATION";

	public static final String ACTION_MAKER_DRAFT_CASECREATION = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_CASECREATION = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_CASECREATION = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_CASECREATION = "UPDATE_CASECREATION";
	
	public static final String ACTION_MAKER_UPDATE_CASECREATION_BRANCH = "UPDATE_CASECREATION_BRANCH";

	public static final String ACTION_MAKER_DELETE_CASECREATION = "DELETE_CASECREATION";

	public static final String ACTION_CHECKER_APPROVE_CASECREATION = "APPROVE_CASECREATION";
	
	public static final String ACTION_CHECKER_APPROVE_CASECREATION_BRANCH = "APPROVE_CASECREATION_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_CREATE_CASECREATION = "APPROVE_CREATE_CASECREATION";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CASECREATION = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_CASECREATION_BRANCH = "CHECKER_APPROVE_UPDATE_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_DELETE_CASECREATION = "APPROVE_DELETE_CASECREATION";

	public static final String ACTION_CHECKER_REJECT_CASECREATION = "REJECT_CASECREATION";
	public static final String ACTION_CHECKER_REJECT_CREATE_CASECREATION = "REJECT_CASECREATION";
	public static final String ACTION_CHECKER_REJECT_EDIT_CASECREATION = "REJECT_CASECREATION";
	public static final String ACTION_CHECKER_REJECT_DELETE_CASECREATION = "REJECT_CASECREATION";

	public static final String ACTION_MAKER_EDIT_REJECTED_CASECREATION = "EDIT_REJECTED_CASECREATION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CASECREATION_BRANCH = "EDIT_REJECTED_CASECREATION_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CASECREATION = "EDIT_REJECTED_CREATE_CASECREATION";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CASECREATION = "EDIT_REJECTED_UPDATE_CASECREATION";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CASECREATION_BRANCH = "EDIT_REJECTED_UPDATE_CASECREATION_BRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_CASECREATION = "EDIT_REJECTED_DELETE_CASECREATION";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CASECREATION = "CLOSE_REJECTED_CASECREATION";

	public static final String ACTION_MAKER_CLOSE_DRAFT_CASECREATION = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CASECREATION = "CLOSE_REJECTED_CREATE_CASECREATION";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CASECREATION = "CLOSE_REJECTED_UPDATE_CASECREATION";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_CASECREATION = "CLOSE_REJECTED_DELETE_CASECREATION";

	public static final String SEQUENCE_CASECREATION = "CASECREATION_SEQ";

	public static final String SEQUENCE_CASECREATION_STAGE = "STG_CASECREATION_SEQ";

	public static final String SEQUENCE_CASECREATION_SEC_SUBTYPE = "CASECREATION_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_CASECREATION_SEC_SUBTYPE_STAGE = "STG_CASECREATION_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_CASECREATION_ITEM = "CASECREATION_ITEM_SEQ";

	public static final String SEQUENCE_CASECREATION_ITEM_STAGE = "STG_CASECREATION_ITEM_SEQ";

	public static final String SEQUENCE_CASECREATION_DISTRICT_CODE = "CASECREATION_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_CASECREATION_DISTRICT_CODE_STAGE = "STG_CASECREATION_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_CASECREATION_MUKIM_CODE = "CASECREATION_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_CASECREATION_MUKIM_CODE_STAGE = "STG_CASECREATION_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_CASECREATION_PROPERTY_TYPE = "CASECREATION_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_CASECREATION_PROPERTY_TYPE_STAGE = "STG_CASECREATION_PROPERTY_TYPE_SEQ";
	
	public static final String INSTANCE_INSERT_CASECREATION = "INSERT_CASECREATION";

	
	
	//////////////////////////////////////Constant for case creation//////////////////

	//////////////////////////////////////Constant for case branch//////////////////
	

	public static final String INSTANCE_CASEBRANCH = "CASEBRANCH";

	public static final String ACTION_READ_CASEBRANCH = "READ_CASEBRANCH";

	public static final String ACTION_READ_CASEBRANCH_ID = "READ_CASEBRANCH_ID";

	public static final String ACTION_MAKER_CREATE_CASEBRANCH = "CREATE_CASEBRANCH";

	public static final String ACTION_MAKER_DRAFT_CASEBRANCH = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_CASEBRANCH = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_CASEBRANCH = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_CASEBRANCH = "UPDATE_CASEBRANCH";
	
	public static final String ACTION_MAKER_UPDATE_CASEBRANCH_BRANCH = "UPDATE_CASEBRANCH_BRANCH";

	public static final String ACTION_MAKER_DELETE_CASEBRANCH = "DELETE_CASEBRANCH";

	public static final String ACTION_CHECKER_APPROVE_CASEBRANCH = "APPROVE_CASEBRANCH";
	
	public static final String ACTION_CHECKER_APPROVE_CASEBRANCH_BRANCH = "APPROVE_CASEBRANCH_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_CREATE_CASEBRANCH = "APPROVE_CREATE_CASEBRANCH";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_CASEBRANCH = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_CASEBRANCH_BRANCH = "CHECKER_APPROVE_UPDATE_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_DELETE_CASEBRANCH = "APPROVE_DELETE_CASEBRANCH";

	public static final String ACTION_CHECKER_REJECT_CASEBRANCH = "REJECT_CASEBRANCH";
	public static final String ACTION_CHECKER_REJECT_CREATE_CASEBRANCH = "REJECT_CASEBRANCH";
	public static final String ACTION_CHECKER_REJECT_EDIT_CASEBRANCH = "REJECT_CASEBRANCH";
	public static final String ACTION_CHECKER_REJECT_DELETE_CASEBRANCH = "REJECT_CASEBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_CASEBRANCH = "EDIT_REJECTED_CASEBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CASEBRANCH = "EDIT_REJECTED_CREATE_CASEBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_CASEBRANCH = "EDIT_REJECTED_UPDATE_CASEBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_CASEBRANCH = "EDIT_REJECTED_DELETE_CASEBRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CASEBRANCH = "CLOSE_REJECTED_CASEBRANCH";

	public static final String ACTION_MAKER_CLOSE_DRAFT_CASEBRANCH = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CASEBRANCH = "CLOSE_REJECTED_CREATE_CASEBRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CASEBRANCH = "CLOSE_REJECTED_UPDATE_CASEBRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_CASEBRANCH = "CLOSE_REJECTED_DELETE_CASEBRANCH";

	public static final String SEQUENCE_CASEBRANCH = "CASEBRANCH_SEQ";

	public static final String SEQUENCE_CASEBRANCH_STAGE = "STG_CASEBRANCH_SEQ";

	public static final String SEQUENCE_CASEBRANCH_SEC_SUBTYPE = "CASEBRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_SEC_SUBTYPE_STAGE = "STG_CASEBRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_ITEM = "CASEBRANCH_ITEM_SEQ";

	public static final String SEQUENCE_CASEBRANCH_ITEM_STAGE = "STG_CASEBRANCH_ITEM_SEQ";

	public static final String SEQUENCE_CASEBRANCH_DISTRICT_CODE = "CASEBRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_DISTRICT_CODE_STAGE = "STG_CASEBRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_MUKIM_CODE = "CASEBRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_MUKIM_CODE_STAGE = "STG_CASEBRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_PROPERTY_TYPE = "CASEBRANCH_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_CASEBRANCH_PROPERTY_TYPE_STAGE = "STG_CASEBRANCH_PROPERTY_TYPE_SEQ";
	
	public static final String INSTANCE_INSERT_CASEBRANCH = "INSERT_CASEBRANCH";

	
	
	//////////////////////////////////////Constant for case creation//////////////////


	/////////////////ConStant for Components/////////////////////////////////
	
	public static final String INSTANCE_COMPONENT = "COMPONENT";

	public static final String ACTION_READ_COMPONENT = "READ_COMPONENT";

	public static final String ACTION_READ_COMPONENT_ID = "READ_COMPONENT_ID";

	public static final String ACTION_MAKER_CREATE_COMPONENT = "CREATE_COMPONENT";

	public static final String ACTION_MAKER_DRAFT_COMPONENT = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_COMPONENT= "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_COMPONENT = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_COMPONENT = "UPDATE_COMPONENT";

	public static final String ACTION_MAKER_DELETE_COMPONENT = "DELETE_COMPONENT";

	public static final String ACTION_CHECKER_APPROVE_COMPONENT = "APPROVE_COMPONENT";

	public static final String ACTION_CHECKER_APPROVE_CREATE_COMPONENT = "APPROVE_CREATE_COMPONENT";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_COMPONENT = "CHECKER_APPROVE_UPDATE";

	public static final String ACTION_CHECKER_APPROVE_DELETE_COMPONENT = "APPROVE_DELETE_COMPONENT";

	public static final String ACTION_CHECKER_REJECT_COMPONENT = "REJECT_COMPONENT";
	public static final String ACTION_CHECKER_REJECT_CREATE_COMPONENT = "REJECT_COMPONENT";
	public static final String ACTION_CHECKER_REJECT_EDIT_COMPONENT = "REJECT_COMPONENT";
	public static final String ACTION_CHECKER_REJECT_DELETE_COMPONENT = "REJECT_COMPONENT";

	public static final String ACTION_MAKER_EDIT_REJECTED_COMPONENT = "EDIT_REJECTED_COMPONENT";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_COMPONENT = "EDIT_REJECTED_CREATE_COMPONENT";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_COMPONENT = "EDIT_REJECTED_UPDATE_COMPONENT";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_COMPONENT = "EDIT_REJECTED_DELETE_COMPONENT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_COMPONENT = "CLOSE_REJECTED_COMPONENT";

	public static final String ACTION_MAKER_CLOSE_DRAFT_COMPONENT = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_COMPONENT = "CLOSE_REJECTED_CREATE_COMPONENT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_COMPONENT = "CLOSE_REJECTED_UPDATE_COMPONENT";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_COMPONENT = "CLOSE_REJECTED_DELETE_COMPONENT";

	public static final String SEQUENCE_COMPONENT = "COMPONENT_SEQ";

	public static final String SEQUENCE_COMPONENT_STAGE = "STG_COMPONENT_SEQ";

	public static final String SEQUENCE_COMPONENT_SEC_SUBTYPE = "COMPONENT_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_COMPONENT_SEC_SUBTYPE_STAGE = "STG_COMPONENT_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_COMPONENT_ITEM = "COMPONENT_ITEM_SEQ";

	public static final String SEQUENCE_COMPONENT_ITEM_STAGE = "STG_COMPONENT_ITEM_SEQ";

	public static final String SEQUENCE_COMPONENT_DISTRICT_CODE = "COMPONENT_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_COMPONENT_DISTRICT_CODE_STAGE = "STG_COMPONENT_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_COMPONENT_MUKIM_CODE = "COMPONENT_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_COMPONENT_MUKIM_CODE_STAGE = "STG_COMPONENT_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_COMPONENT_PROPERTY_TYPE = "COMPONENT_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_COMPONENT_PROPERTY_TYPE_STAGE = "STG_COMPONENT_PROPERTY_TYPE_SEQ";
	
	
	
	//////////////////////Constants for TAT Master/////////////////////////////////////
	
	public static final String INSTANCE_TAT_MASTER = "TAT_MASTER";
	
	public static final String ACTION_READ_TAT_MASTER = "READ_TAT_MASTER";
	
	public static final String ACTION_READ_TAT_MASTER_ID = "READ_TAT_MASTER_ID";
	
	public static final String ACTION_MAKER_UPDATE_TAT_MASTER = "UPDATE_TAT_MASTER";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_TAT_MASTER = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_TAT_MASTER = "APPROVE_TAT_MASTER";
	
	public static final String ACTION_CHECKER_REJECT_TAT_MASTER = "REJECT_TAT_MASTER";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_TAT_MASTER = "EDIT_REJECTED_UPDATE_TAT_MASTER";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_TAT_MASTER = "CLOSE_REJECTED_UPDATE_TAT_MASTER";
	
	
	public static final String ACTION_MAKER_EDIT_REJECTED_TAT_MASTER = "EDIT_REJECTED_TAT_MASTER";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_TAT_MASTER = "CLOSE_REJECTED_TAT_MASTER";
	
	public static final String CPS_CLIMS_CONTROL_FILE = "CPS_CLIMS_Control";


////////////////////////////added by abhishek COnstants for Basel System Master//////////////////////
	
	public static final String INSTANCE_BASEL = "BASEL";
	
	public static final String ACTION_READ_BASEL = "READ_BASEL";
	
	public static final String ACTION_READ_BASEL_ID = "READ_BASEL_ID";
	
	public static final String ACTION_MAKER_CREATE_BASEL = "CREATE_BASEL";

	

	public static final String ACTION_MAKER_UPDATE_BASEL = "UPDATE_BASEL";

	public static final String ACTION_MAKER_SAVE_BASEL= "MAKER_SAVE";

	public static final String ACTION_MAKER_SAVE_UPDATE_BASEL = "MAKER_UPDATE_SAVE";

	public static final String ACTION_CHECKER_APPROVE_BASEL = "APPROVE_BASEL";

	public static final String ACTION_CHECKER_REJECT_BASEL = "REJECT_BASEL";

	

	public static final String ACTION_CHECKER_REJECT_CREATE_BASEL = "REJECT_BASEL";

	

	public static final String ACTION_CHECKER_REJECT_EDIT_BASEL = "REJECT_BASEL";

	

	public static final String ACTION_CHECKER_REJECT_DELETE_BASEL = "REJECT_BASEL";

	
	public static final String ACTION_MAKER_DELETE_BASEL = "DELETE_BASEL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_BASEL = "EDIT_REJECTED_BASEL";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_BASEL = "CLOSE_REJECTED_BASEL";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_BASEL = "MAKER_UPDATE_CLOSE";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_BASEL = "APPROVE_CREATE_BASEL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_BASEL = "EDIT_REJECTED_DELETE_BASEL";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_BASEL = "CLOSE_REJECTED_UPDATE_BASEL";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_BASEL = "EDIT_REJECTED_CREATE_BASEL";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_BASEL = "CLOSE_REJECTED_CREATE_BASEL";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_BASEL = "CHECKER_APPROVE_UPDATE";

	///////////////////////////////////Constants For  File Upload////////////////////////
	
	public static final String INSTANCE_FILEUPLOAD = "FILEUPLOAD";
	
	public static final String ACTION_MAKER_CREATE_FILEUPLOAD = "CREATE_FILEUPLOAD";
	
	public static final String ACTION_MAKER_CREATE_FILEUPLOAD_NEW = "CREATE_FILEUPLOAD_NEW";
	
	public static final String ACTION_READ_FILEUPLOAD = "READ_FILEUPLOAD";
	
	public static final String ACTION_READ_FILEUPLOAD_ID = "READ_FILEUPLOAD_ID";
	
	public static final String ACTION_CHECKER_APPROVE_FILEUPLOAD = "APPROVE_FILEUPLOAD";
	
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_BASEL = "EDIT_REJECTED_UPDATE_BASEL";
	
	public static final String ACTION_CHECKER_APPROVE_FILEUPLOAD_NEW = "APPROVE_FILEUPLOAD_NEW";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_BASEL = "CLOSE_REJECTED_DELETE_BASEL";

	public static final String ACTION_CHECKER_APPROVE_CREATE_FILEUPLOAD = "APPROVE_CREATE_FILEUPLOAD";
	
	public static final String ACTION_CHECKER_REJECT_FILEUPLOAD = "REJECT_FILEUPLOAD";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_FILEUPLOAD = "CLOSE_REJECTED_UPDATE_FILEUPLOAD";	
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_FILEUPLOAD_NEW = "CLOSE_REJECTED_UPDATE_FILEUPLOAD_NEW";	
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_FILEUPLOAD = "CLOSE_REJECTED_FILEUPLOAD";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_FILEUPLOAD = "CLOSE_REJECTED_CREATE_FILEUPLOAD";
	
	public static final String FILEUPLOAD_SEPERATOR = ":::";
	public static final String HRMS_FILEUPLOAD_SEPERATOR = "~";
	
	public static final String FD_UPLOAD = "FdUpload";
	
	 /****************************** CONSTANTS FOR PINCODE MAPPING START **************************/
	
	public static final String INSTANCE_PINCODE_MAPPING = "PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_PINCODE_MAPPING = "MAKER_UPDATE_CLOSE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_PINCODE_MAPPING = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_SAVE_PINCODE_MAPPING = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_PINCODE_MAPPING = "CLOSE_REJECTED_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_PINCODE_MAPPING = "EDIT_REJECTED_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_UPDATE_PINCODE_MAPPING = "UPDATE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_CREATE_PINCODE_MAPPING = "CREATE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_DELETE_PINCODE_MAPPING = "DELETE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_ACTIVATE_PINCODE_MAPPING = "ACTIVATE_PINCODE_MAPPING";
	
	public static final String ACTION_READ_PINCODE_MAPPING  = "READ_PINCODE_MAPPING ";

	public static final String ACTION_READ_PINCODE_MAPPING_ID = "READ_PINCODE_MAPPING _ID";
	
	public static final String ACTION_CHECKER_REJECT_PINCODE_MAPPING = "REJECT_PINCODE_MAPPING";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_PINCODE_MAPPING = "APPROVE_CREATE_PINCODE_MAPPING";
	
	public static final String ACTION_CHECKER_APPROVE_PINCODE_MAPPING = "APPROVE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_PINCODE_MAPPING = "CLOSE_REJECTED_DELETE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_PINCODE_MAPPING = "EDIT_REJECTED_CREATE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_PINCODE_MAPPING = "EDIT_REJECTED_UPDATE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_PINCODE_MAPPING = "EDIT_REJECTED_DELETE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_ENABLE_PINCODE_MAPPING = "EDIT_REJECTED_ENABLE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_PINCODE_MAPPING = "CLOSE_REJECTED_CREATE_PINCODE_MAPPING";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_PINCODE_MAPPING = "CLOSE_REJECTED_UPDATE_PINCODE_MAPPING";
	
	public static final String ACTION_CHECKER_APPROVE_DELETE_PINCODE_MAPPING = "CHECKER_APPROVE_DELETE";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_PINCODE_MAPPING = "CHECKER_APPROVE_UPDATE";
	 /****************************** CONSTANTS FOR PINCODE MAPPING END **************************/

	
	public static final String LAD_PENDING = "Pending";
	
	public static final String LAD_SUCCESS = "Success";
	
	public static final String LAD_FAIL = "Fail";
	
	public static final String LAD_PARTIAL_SUCCESS = "Partial Success";
	//End
	
	// ********************************************constant for
		// Excluded Facility ***************************************
	public static final String INSTANCE_EXCLUDED_FACILITY = "EXCLUDED_FACILITY";
	
	public static final String ACTION_READ_EXCLUDED_FACILITY = "READ_EXCLUDED_FACILITY";
	
	public static final String ACTION_READ_EXCLUDED_FACILITY_ID = "READ_EXCLUDED_FACILITY_ID";
	
	public static final String ACTION_MAKER_CREATE_EXCLUDED_FACILITY = "CREATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_CHECKER_APPROVE_EXCLUDED_FACILITY = "APPROVE_EXCLUDED_FACILITY";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_EXCLUDED_FACILITY = "APPROVE_CREATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_CHECKER_REJECT_EXCLUDED_FACILITY = "REJECT_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_SAVE_EXCLUDED_FACILITY = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_EXCLUDED_FACILITY = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_EXCLUDED_FACILITY = "EDIT_REJECTED_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_DELETE_EXCLUDED_FACILITY = "DELETE_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_EXCLUDED_FACILITY = "EDIT_REJECTED_CREATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_UPDATE_EXCLUDED_FACILITY = "UPDATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_EXCLUDED_FACILITY = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_EXCLUDED_FACILITY = "EDIT_REJECTED_UPDATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_EXCLUDED_FACILITY = "EDIT_REJECTED_DELETE_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_EXCLUDED_FACILITY = "MAKER_UPDATE_CLOSE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_EXCLUDED_FACILITY = "CLOSE_REJECTED_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_EXCLUDED_FACILITY = "CLOSE_REJECTED_CREATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_EXCLUDED_FACILITY = "CLOSE_REJECTED_UPDATE_EXCLUDED_FACILITY";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_EXCLUDED_FACILITY = "CLOSE_REJECTED_DELETE_EXCLUDED_FACILITY";
	
		// ********************************************constant for
			// Collateral Roc ***************************************
	
	public static final String INSTANCE_COLLATERAL_ROC = "COLLATERAL_ROC";
	
	public static final String ACTION_READ_COLLATERAL_ROC = "READ_COLLATERAL_ROC";
	
	public static final String ACTION_READ_COLLATERAL_ROC_ID = "READ_COLLATERAL_ROC_ID";
	
	public static final String ACTION_MAKER_CREATE_COLLATERAL_ROC = "CREATE_COLLATERAL_ROC";
	
	public static final String ACTION_CHECKER_APPROVE_COLLATERAL_ROC = "APPROVE_COLLATERAL_ROC";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_ROC = "APPROVE_CREATE_COLLATERAL_ROC";
	
	public static final String ACTION_CHECKER_REJECT_COLLATERAL_ROC = "REJECT_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_COLLATERAL_ROC = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_SAVE_COLLATERAL_ROC = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_UPDATE_COLLATERAL_ROC = "UPDATE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_COLLATERAL_ROC = "EDIT_REJECTED_COLLATERAL_ROC";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_COLLATERAL_ROC = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_MAKER_DELETE_COLLATERAL_ROC = "DELETE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_COLLATERAL_ROC = "EDIT_REJECTED_CREATE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_COLLATERAL_ROC = "EDIT_REJECTED_UPDATE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_COLLATERAL_ROC = "EDIT_REJECTED_DELETE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_CLOSE_DRAFT_COLLATERAL_ROC = "MAKER_UPDATE_CLOSE";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_COLLATERAL_ROC = "CLOSE_REJECTED_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_COLLATERAL_ROC = "CLOSE_REJECTED_CREATE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_COLLATERAL_ROC = "CLOSE_REJECTED_UPDATE_COLLATERAL_ROC";
	
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_COLLATERAL_ROC = "CLOSE_REJECTED_DELETE_COLLATERAL_ROC";
	
	
	 //Uma Khot::Insurance Deferral maintainance
	public static final String INSURANCE_POLICY2="INSURANCE_POLICY";
	
	public static final String PARTYCAM_UPLOAD = "PARTYCAM_UPLOAD";

	public static final String FILE_NAME = "fileName";
	
	public static final String REPORT_FILE = "reportfile";
	
	public static final String OUTPUT = "output";
	
	public static final String FCUBS_MONTHLY_FILE_DOWNLOAD = "fcubsFileDownload";

	public static final String INSTANCE_FCCBRANCH = "FCCBRANCH";

	public static final String ACTION_READ_FCCBRANCH = "READ_FCCBRANCH";

	public static final String ACTION_READ_FCCBRANCH_ID = "READ_FCCBRANCH_ID";

	public static final String ACTION_MAKER_CREATE_FCCBRANCH = "CREATE_FCCBRANCH";

	public static final String ACTION_MAKER_DRAFT_FCCBRANCH = "MAKER_DRAFT";

	public static final String ACTION_MAKER_SAVE_FCCBRANCH = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_FCCBRANCH = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_UPDATE_FCCBRANCH = "UPDATE_FCCBRANCH";
	
	public static final String ACTION_MAKER_UPDATE_FCCBRANCH_BRANCH = "UPDATE_FCCBRANCH_BRANCH";

	public static final String ACTION_MAKER_DELETE_FCCBRANCH = "DELETE_FCCBRANCH";

	public static final String ACTION_CHECKER_APPROVE_FCCBRANCH = "APPROVE_FCCBRANCH";
	
	public static final String ACTION_CHECKER_APPROVE_FCCBRANCH_BRANCH = "APPROVE_FCCBRANCH_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_CREATE_FCCBRANCH = "APPROVE_CREATE_FCCBRANCH";

	public static final String ACTION_CHECKER_APPROVE_UPDATE_FCCBRANCH = "CHECKER_APPROVE_UPDATE";
	
	public static final String ACTION_CHECKER_APPROVE_UPDATE_FCCBRANCH_BRANCH = "CHECKER_APPROVE_UPDATE_BRANCH";

	public static final String ACTION_CHECKER_APPROVE_DELETE_FCCBRANCH = "APPROVE_DELETE_FCCBRANCH";

	public static final String ACTION_CHECKER_REJECT_FCCBRANCH = "REJECT_FCCBRANCH";
	public static final String ACTION_CHECKER_REJECT_CREATE_FCCBRANCH = "REJECT_FCCBRANCH";
	public static final String ACTION_CHECKER_REJECT_EDIT_FCCBRANCH = "REJECT_FCCBRANCH";
	public static final String ACTION_CHECKER_REJECT_DELETE_FCCBRANCH = "REJECT_FCCBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_FCCBRANCH = "EDIT_REJECTED_FCCBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_FCCBRANCH = "EDIT_REJECTED_CREATE_FCCBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_FCCBRANCH = "EDIT_REJECTED_UPDATE_FCCBRANCH";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_FCCBRANCH = "EDIT_REJECTED_DELETE_FCCBRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_FCCBRANCH = "CLOSE_REJECTED_FCCBRANCH";

	public static final String ACTION_MAKER_CLOSE_DRAFT_FCCBRANCH = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_FCCBRANCH = "CLOSE_REJECTED_CREATE_FCCBRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_FCCBRANCH = "CLOSE_REJECTED_UPDATE_FCCBRANCH";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_FCCBRANCH = "CLOSE_REJECTED_DELETE_FCCBRANCH";

	public static final String SEQUENCE_FCCBRANCH = "FCCBRANCH_SEQ";

	public static final String SEQUENCE_FCCBRANCH_STAGE = "STG_FCCBRANCH_SEQ";

	public static final String SEQUENCE_FCCBRANCH_SEC_SUBTYPE = "FCCBRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_SEC_SUBTYPE_STAGE = "STG_FCCBRANCH_SEC_SUBTYPE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_ITEM = "FCCBRANCH_ITEM_SEQ";

	public static final String SEQUENCE_FCCBRANCH_ITEM_STAGE = "STG_FCCBRANCH_ITEM_SEQ";

	public static final String SEQUENCE_FCCBRANCH_DISTRICT_CODE = "FCCBRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_DISTRICT_CODE_STAGE = "STG_FCCBRANCH_DISTRICT_CODE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_MUKIM_CODE = "FCCBRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_MUKIM_CODE_STAGE = "STG_FCCBRANCH_MUKIM_CODE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_PROPERTY_TYPE = "FCCBRANCH_PROPERTY_TYPE_SEQ";

	public static final String SEQUENCE_FCCBRANCH_PROPERTY_TYPE_STAGE = "STG_FCCBRANCH_PROPERTY_TYPE_SEQ";
	
	public static final String INSTANCE_INSERT_FCCBRANCH = "INSERT_FCCBRANCH";
	
	public static final String FCUBSLIMIT_ACTION_NEW ="NEW";
	
	public static final String FCUBSLIMIT_ACTION_MODIFY ="MODIFY";
	
	public static final String FCUBSLIMIT_ACTION_REOPEN ="REOPEN";
	
	public static final String FCUBSLIMIT_ACTION_CLOSE ="CLOSE";
	
	public static final String FCUBS_FILE_UPLOAD = "FCUBSFileUpload";
	
	public static final String FCUBS_STATUS_PENDING = "PENDING";
	
	public static final String FCUBS_STATUS_SUCCESS = "SUCCESS";
	
	public static final String FCUBS_STATUS_REJECTED = "REJECTED";
	
	public static final String INSTANCE_PRODUCT_MASTER = "PRODUCT_MASTER";
	
	public static final String INSTANCE_GOODS_MASTER = "GOODS_MASTER";
	
	public static final String INSTANCE_LEI_DATE_VALIDATION = "LEI_DATE_VALIDATION";
	
	public static final String ACTION_READ_PRODUCT_MASTER = "READ_PRODUCT_MASTER";
	
	public static final String ACTION_READ_GOODS_MASTER = "READ_GOODS_MASTER";
	
	public static final String ACTION_READ_PRODUCT_MASTER_ID = "READ_PRODUCT_MASTER_ID";
	
	public static final String ACTION_READ_GOODS_MASTER_ID = "READ_GOODS_MASTER_ID";
	
	public static final String ACTION_MAKER_CREATE_PRODUCT_MASTER = "CREATE_PRODUCT_MASTER";
	
	public static final String ACTION_MAKER_CREATE_GOODS_MASTER = "CREATE_GOODS_MASTER";
	
	public static final String ACTION_MAKER_CREATE_LEI_DATE_VALIDATION = "CREATE_LEI_DATE_VALIDATION";
	
	public static final String ACTION_READ_LEI_DATE_VALIDATION = "READ_LEI_DATE_VALIDATION";
	
	public static final String ACTION_READ_LEI_DATE_VALIDATION_ID = "READ_LEI_DATE_VALIDATION_ID";	
	
	public static final String ACTION_MAKER_CREATE_CERSAI_MAPPING  = "CREATE_CERSAI_MAPPING";
	
	public static final String ACTION_CHECKER_APPROVE_PRODUCT_MASTER = "APPROVE_PRODUCT_MASTER";
	
	public static final String ACTION_CHECKER_APPROVE_GOODS_MASTER = "APPROVE_GOODS_MASTER";

	public static final String ACTION_CHECKER_APPROVE_LEI_DATE_VALIDATION = "APPROVE_LEI_DATE_VALIDATION";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_PRODUCT_MASTER = "APPROVE_CREATE_PRODUCT_MASTER";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_GOODS_MASTER = "APPROVE_CREATE_GOODS_MASTER";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_LEI_DATE_VALIDATION = "APPROVE_CREATE_LEI_DATE_VALIDATION";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_CERSAI_MAPPING = "APPROVE_CREATE_CERSAI_MAPPING";
	
	public static final String ACTION_MAKER_SAVE_PRODUCT_MASTER = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_SAVE_GOODS_MASTER = "MAKER_SAVE";

	public static final String ACTION_MAKER_SAVE_LEI_DATE_VALIDATION = "MAKER_SAVE";

	public static final String ACTION_MAKER_SAVE_UPDATE_PRODUCT_MASTER = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_GOODS_MASTER = "MAKER_UPDATE_SAVE";

	public static final String ACTION_MAKER_SAVE_UPDATE_LEI_DATE_VALIDATION = "MAKER_UPDATE_SAVE";

	public static final String FCUBS_CAD = "CAD";
		
	public static final String FCUBSLIMIT_FILE_UPLOAD = "FCUBSLimitFileUpload";
	public static final String HRMS_FILE_UPLOAD = "HRMSFileUpload";
	
	public static final String FCUBS_ADD = "A";
	public static final String FCUBS_MODIFY = "M";
	public static final String FCUBS_DELETE = "D";
	
	//added by santosh ubs limit
	public static final String ACTION_MAKER_UPDATE_PRODUCT_MASTER = "UPDATE_PRODUCT_MASTER";
	public static final String ACTION_MAKER_UPDATE_GOODS_MASTER = "UPDATE_GOODS_MASTER";
	public static final String ACTION_MAKER_UPDATE_LEI_DATE_VALIDATION = "UPDATE_LEI_DATE_VALIDATION";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_PRODUCT_MASTER = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_GOODS_MASTER = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_LEI_DATE_VALIDATION = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_PRODUCT_MASTER = "REJECT_PRODUCT_MASTER";
	public static final String ACTION_CHECKER_REJECT_GOODS_MASTER = "REJECT_GOODS_MASTER";
	public static final String ACTION_CHECKER_REJECT_LEI_DATE_VALIDATION = "REJECT_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_EDIT_REJECTED_PRODUCT_MASTER = "EDIT_REJECTED_PRODUCT_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_GOODS_MASTER = "EDIT_REJECTED_GOODS_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_LEI_DATE_VALIDATION = "EDIT_REJECTED_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_PRODUCT_MASTER = "EDIT_REJECTED_CREATE_PRODUCT_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_GOODS_MASTER = "EDIT_REJECTED_CREATE_GOODS_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_LEI_DATE_VALIDATION = "EDIT_REJECTED_CREATE_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_PRODUCT_MASTER = "EDIT_REJECTED_UPDATE_PRODUCT_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_GOODS_MASTER = "EDIT_REJECTED_UPDATE_GOODS_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_LEI_DATE_VALIDATION = "EDIT_REJECTED_UPDATE_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_CLOSE_REJECTED_PRODUCT_MASTER = "CLOSE_REJECTED_PRODUCT_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_GOODS_MASTER = "CLOSE_REJECTED_GOODS_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_LEI_DATE_VALIDATION = "CLOSE_REJECTED_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_PRODUCT_MASTER = "CLOSE_REJECTED_CREATE_PRODUCT_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_GOODS_MASTER = "CLOSE_REJECTED_CREATE_GOODS_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_LEI_DATE_VALIDATION = "CLOSE_REJECTED_CREATE_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_PRODUCT_MASTER = "CLOSE_REJECTED_UPDATE_PRODUCT_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_GOODS_MASTER = "CLOSE_REJECTED_UPDATE_GOODS_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_LEI_DATE_VALIDATION = "CLOSE_REJECTED_UPDATE_LEI_DATE_VALIDATION";
	public static final String ACTION_MAKER_CLOSE_DRAFT_PRODUCT_MASTER = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_MAKER_CLOSE_DRAFT_GOODS_MASTER = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_MAKER_CLOSE_DRAFT_LEI_DATE_VALIDATION = "MAKER_UPDATE_CLOSE";

	
	public static final String FCUBS_STATUS_PENDING_SUCCESS = "PENDING_SUCCESS";
	
	public static final String FCUBS_STATUS_PENDING_REJECTED = "PENDING_REJECTED";
	
	//Diary CR Master BY Santosh
	public static final String ACTION_READ_SEGMENT_WISE_EMAIL = "READ_SEGMENT_WISE_EMAIL";
	public static final String ACTION_READ_SEGMENT_WISE_EMAIL_ID = "READ_SEGMENT_WISE_EMAIL_ID";
	public static final String ACTION_MAKER_CREATE_SEGMENT_WISE_EMAIL = "CREATE_SEGMENT_WISE_EMAIL";
	public static final String INSTANCE_SEGMENT_WISE_EMAIL = "SEGMENT_WISE_EMAIL";
	public static final String ACTION_CHECKER_APPROVE_SEGMENT_WISE_EMAIL = "APPROVE_SEGMENT_WISE_EMAIL";
	public static final String ACTION_CHECKER_REJECT_SEGMENT_WISE_EMAIL = "REJECT_SEGMENT_WISE_EMAIL";
	public static final String ACTION_CHECKER_APPROVE_CREATE_SEGMENT_WISE_EMAIL = "APPROVE_CREATE_SEGMENT_WISE_EMAIL";
	public static final String ACTION_MAKER_CLOSE_REJECTED_SEGMENT_WISE_EMAIL = "CLOSE_REJECTED_SEGMENT_WISE_EMAIL";
	public static final String ACTION_MAKER_UPDATE_SEGMENT_WISE_EMAIL = "UPDATE_SEGMENT_WISE_EMAIL";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_SEGMENT_WISE_EMAIL = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_MAKER_EDIT_REJECTED_SEGMENT_WISE_EMAIL = "EDIT_REJECTED_SEGMENT_WISE_EMAIL";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_SEGMENT_WISE_EMAIL = "EDIT_REJECTED_CREATE_SEGMENT_WISE_EMAIL";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_SEGMENT_WISE_EMAIL = "EDIT_REJECTED_UPDATE_SEGMENT_WISE_EMAIL";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_SEGMENT_WISE_EMAIL = "CLOSE_REJECTED_UPDATE_SEGMENT_WISE_EMAIL";
	public static final String ACTION_MAKER_DELETE_SEGMENT_WISE_EMAIL = "DELETE_SEGMENT_WISE_EMAIL";

	public static final String RAM_RATING = "RAM_RATING";
	
	public static final String DEFERRAL_EXTENSION = "DEFERRAL_EXTENSION";
	
	public static final String TRX_STATE_ACTIVE = "ACTIVE";
	public static final String TRX_STATE_INACTIVE = "INACTIVE";
	
	public static final String INSTANCE_CERSAI_MAPPING = "CERSAI_MAPPING";
	
	public static final String ACTION_READ_CERSAI_MAPPING = "READ_CERSAI_MAPPING";
	
	public static final String ACTION_READ_CERSAI_MAPPING_ID = "READ_CERSAI_MAPPING_ID";
	
	public static final String ACTION_MAKER_SAVE_CERSAI_MAPPING = "CREATE_CERSAI_MAPPING";
	
	public static final String ACTION_CHECKER_REJECT_CERSAI_MAPPING = "REJECT_CERSAI_MAPPING";
	public static final String ACTION_CHECKER_APPROVE_CERSAI_MAPPING = "APPROVE_CERSAI_MAPPING";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CERSAI_MAPPING = "CLOSE_REJECTED_CERSAI_MAPPING";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_CERSAI_MAPPING = "CLOSE_REJECTED_CREATE_CERSAI_MAPPING";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_CERSAI_MAPPING = "CLOSE_REJECTED_UPDATE_CERSAI_MAPPING";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_CERSAI_MAPPING = "EDIT_REJECTED_CREATE_CERSAI_MAPPING";
	public static final String ACTION_MAKER_EDIT_REJECTED_CERSAI_MAPPING = "EDIT_REJECTED_CERSAI_MAPPING";
	
	public static final String INSTANCE_UDF = "UDF";
	public static final String ACTION_READ_UDF = "READ_UDF";
	public static final String ACTION_READ_UDF_ID = "READ_UDF_ID";
	public static final String ACTION_MAKER_CREATE_UDF = "CREATE_UDF";
	public static final String ACTION_CHECKER_APPROVE_UDF = "APPROVE_UDF";
	public static final String ACTION_CHECKER_APPROVE_CREATE_UDF = "APPROVE_CREATE_UDF";
	public static final String ACTION_MAKER_SAVE_UDF = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_UDF = "MAKER_UPDATE_SAVE";
	public static final String ACTION_MAKER_UPDATE_UDF = "UPDATE_UDF";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_UDF = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_UDF = "REJECT_UDF";
	public static final String ACTION_MAKER_EDIT_REJECTED_UDF = "EDIT_REJECTED_UDF";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_UDF = "EDIT_REJECTED_CREATE_UDF";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_UDF = "EDIT_REJECTED_UPDATE_UDF";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UDF = "CLOSE_REJECTED_UDF";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_UDF = "CLOSE_REJECTED_CREATE_UDF";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_UDF = "CLOSE_REJECTED_UPDATE_UDF";
	public static final String ACTION_MAKER_CLOSE_DRAFT_UDF = "MAKER_UPDATE_CLOSE";

	public static final String ACTION_MAKER_DELETE_UDF = "DELETE_UDF";

	public static final String ACTION_MAKER_ACTIVATE_UDF = "ACTIVATE_UDF";

	public static final String ACTION_CHECKER_APPROVE_DELETE_UDF = "CHECKER_APPROVE_DELETE";

	public static final String ACTION_CHECKER_APPROVE_ENABLE_UDF = "CHECKER_APPROVE_ENABLE";
	
	public static final String ACTION_CHECKER_REJECT_ACTIVATE_UDF = "REJECT_ACTIVATE_UDF";

	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_UDF = "CLOSE_REJECTED_DELETE_UDF";

	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_UDF = "EDIT_REJECTED_DELETE_UDF";

	public static final String ACTION_MAKER_EDIT_REJECTED_ENABLE_UDF = "EDIT_REJECTED_ENABLE_UDF";


	//For PSR Limit hand off
	public static final String PSR_STATUS_PENDING = "PENDING";
	public static final String PSRLIMIT_FILE_UPLOAD = "PSRLimitFileUpload";
	public static final String PSR_STATUS_SUCCESS = "SUCCESS";
	public static final String PSR_STATUS_REJECTED = "REJECTED";
	public static final String PSRLIMIT_ACTION_NEW ="NEW";
	public static final String PSRLIMIT_ACTION_MODIFY ="MODIFY";
	
	
	public static final String INSTANCE_VALUATION_AMOUNT_AND_RATING = "VALUATI_AMT_AND_RAT";
	
	public static final String ACTION_READ_VALUATION_AMOUNT_AND_RATING = "READ_VALUATI_AMT_AND_RAT";
	
	public static final String ACTION_READ_VALUATION_AMOUNT_AND_RATING_ID = "READ_VALUATI_AMT_AND_RAT_ID";
	
	public static final String ACTION_MAKER_CREATE_VALUATION_AMOUNT_AND_RATING = "CREATE_VALUATI_AMT_AND_RAT";
	
	public static final String ACTION_CHECKER_APPROVE_VALUATION_AMOUNT_AND_RATING = "APPROVE_VALUATI_AMT_AND_RAT";
	
	public static final String ACTION_CHECKER_APPROVE_CREATE_VALUATION_AMOUNT_AND_RATING = "APPROVE_CREATE_VALUATI_AMT_AND_RAT";
	
	public static final String ACTION_MAKER_SAVE_VALUATION_AMOUNT_AND_RATING = "MAKER_SAVE";
	
	public static final String ACTION_MAKER_SAVE_UPDATE_VALUATION_AMOUNT_AND_RATING = "MAKER_UPDATE_SAVE";
	
	public static final String ACTION_MAKER_UPDATE_VALUATION_AMOUNT_AND_RATING = "UPDATE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_VALUATION_AMOUNT_AND_RATING = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_VALUATION_AMOUNT_AND_RATING = "REJECT_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_EDIT_REJECTED_VALUATION_AMOUNT_AND_RATING = "EDIT_REJECTED_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_VALUATION_AMOUNT_AND_RATING = "EDIT_REJECTED_CREATE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_VALUATION_AMOUNT_AND_RATING = "EDIT_REJECTED_UPDATE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_CLOSE_REJECTED_VALUATION_AMOUNT_AND_RATING = "CLOSE_REJECTED_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_VALUATION_AMOUNT_AND_RATING = "CLOSE_REJECTED_CREATE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_VALUATION_AMOUNT_AND_RATING = "CLOSE_REJECTED_UPDATE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_CLOSE_DRAFT_VALUATION_AMOUNT_AND_RATING = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_MAKER_DELETE_VALUATION_AMOUNT_AND_RATING = "DELETE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_CHECKER_APPROVE_DELETE_VALUATION_AMOUNT_AND_RATING = "CHECKER_APPROVE_DELETE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_VALUATION_AMOUNT_AND_RATING = "CLOSE_REJECTED_DELETE_VALUATI_AMT_AND_RAT";
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_VALUATION_AMOUNT_AND_RATING = "EDIT_REJECTED_DELETE_VALUATI_AMT_AND_RAT";

	//NPA TRAQ Code Master
	public static final String INSTANCE_NPA_TRAQ_CODE_MASTER = "NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_READ_NPA_TRAQ_CODE_MASTER_ID = "READ_NPA_TRAQ_CODE_MASTER_ID";
	public static final String ACTION_MAKER_CREATE_NPA_TRAQ_CODE_MASTER = "CREATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_UPDATE_NPA_TRAQ_CODE_MASTER = "UPDATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_NPA_TRAQ_CODE_MASTER = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_NPA_TRAQ_CODE_MASTER = "REJECT_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_NPA_TRAQ_CODE_MASTER = "EDIT_REJECTED_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_NPA_TRAQ_CODE_MASTER = "EDIT_REJECTED_CREATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_NPA_TRAQ_CODE_MASTER = "EDIT_REJECTED_UPDATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_NPA_TRAQ_CODE_MASTER = "CLOSE_REJECTED_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_NPA_TRAQ_CODE_MASTER = "CLOSE_REJECTED_CREATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_NPA_TRAQ_CODE_MASTER = "CLOSE_REJECTED_UPDATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_CLOSE_DRAFT_NPA_TRAQ_CODE_MASTER = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_CHECKER_APPROVE_NPA_TRAQ_CODE_MASTER = "APPROVE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_SAVE_UPDATE_NPA_TRAQ_CODE_MASTER = "MAKER_UPDATE_SAVE";
	public static final String ACTION_MAKER_SAVE_NPA_TRAQ_CODE_MASTER = "MAKER_SAVE";
	public static final String ACTION_READ_NPA_TRAQ_CODE_MASTER = "READ_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_CHECKER_APPROVE_CREATE_NPA_TRAQ_CODE_MASTER = "APPROVE_CREATE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_DELETE_NPA_TRAQ_CODE_MASTER  = "DELETE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_NPA_TRAQ_CODE_MASTER = "EDIT_REJECTED_DELETE_NPA_TRAQ_CODE_MASTER";
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_NPA_TRAQ_CODE_MASTER = "CLOSE_REJECTED_DELETE_NPA_TRAQ_CODE_MASTER";
	
	public static final String NPA_FILE_UPLOAD = "NpaFileUpload";
	public static final String NPA_UPLOAD_STATUS = "File updated successfully";
	
	
	//FCC Collateral Liquidation
	String FD_STP_FCC_COL_FILE_UPLOAD_BH = "FccColLiqFileUploadBH";
	String FD_STP_FCC_COL_FILE_UPLOAD_GC = "FccColLiqFileUploadGC";
	String FD_STP_FCC_COL_FILE_UPLOAD_HK = "FccColLiqFileUploadHK";
	
	public static final String INSTANCE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "BANKING_ARR_FAC_EXC";
	public static final String ACTION_READ_BANKING_ARRANGEMENT_FAC_EXCLUSION = "READ";
	public static final String ACTION_READ_BANKING_ARRANGEMENT_FAC_EXCLUSION_ID = "READ_ID";
	public static final String ACTION_MAKER_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CREATE";
	public static final String ACTION_CHECKER_APPROVE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "APPROVE";
	public static final String ACTION_CHECKER_APPROVE_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "APPROVE_CREATE";
	public static final String ACTION_MAKER_SAVE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "MAKER_UPDATE_SAVE";
	public static final String ACTION_MAKER_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "UPDATE";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_BANKING_ARRANGEMENT_FAC_EXCLUSION = "REJECT";
	public static final String ACTION_MAKER_EDIT_REJECTED_BANKING_ARRANGEMENT_FAC_EXCLUSION = "EDIT_REJECTED";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "EDIT_REJECTED_CREATE";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "EDIT_REJECTED_UPDATE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CLOSE_REJECTED";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CLOSE_REJECTED_CREATE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CLOSE_REJECTED_UPDATE";
	public static final String ACTION_MAKER_CLOSE_DRAFT_BANKING_ARRANGEMENT_FAC_EXCLUSION = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_MAKER_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "DELETE";
	public static final String ACTION_CHECKER_APPROVE_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CHECKER_APPROVE_DELETE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "CLOSE_REJECTED_DELETE";
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION = "EDIT_REJECTED_DELETE";
	
	public static final String IMAGE_CATEGORY_SECURITY = "Security";
	public static final String IMAGE_CATEGORY_FACILITY = "Facility";
	public static final String IMAGE_CATEGORY_OTHER = "Other";
	public static final String IMAGE_CATEGORY_CAM = "CAM";
	public static final String IMAGE_CATEGORY_STATEMENT = "Statement";
	public static final String IMAGE_CATEGORY_EXCHANGE_OF_INFO = "Exchange of Info";

	public static final String CODE_IMG_CATEGORY_SECURITY = "SECURITY";
	public static final String CODE_IMG_CATEGORY_FACILITY = "IMG_CATEGORY_FACILITY";
	public static final String CODE_IMG_CATEGORY_OTHERS = "IMG_CATEGORY_OTHERS";
	public static final String CODE_IMG_CATEGORY_CAM = "IMG_CATEGORY_CAM";
	public static final String CODE_IMG_CATEGORY_STOCK_STMT = "IMG_CATEGORY_STOCK_STMT";
	public static final String CODE_IMG_CATEGORY_EXCH_INFO = "IMG_CATEGORY_EXCH_INFO";
	
	public static final String NPA_DAILY_STAMPING_FILE_UPLOAD = "NpaDailyStampingFileUpload";
	
	public static final String EWS_STOCK_DEFERRAL_FILE_UPLOAD = "EwsStockDeferralFileUpload";
	
    public static final String REPORT_LN_FILE_UPLOAD ="ReportLeadNodalUpload";

	public static final String DFSO_UPLOAD = "DfsoFileUpload";
	public static final String DFSO_JOB_UPLOAD = "DfsoJobFileUpload";
	
	public static final String DFSO_StockStatementDPYes_FILENAME = "DfsoStockStatementDPYesFileUpload";
	
	public static final String DFSO_StockStatementDPNo_FILENAME = "DfsoStockStatementDPNoFileUpload";
	
	public static final String DFSO_DiscrepencyDeferralFacility_FILENAME = "DfsoDiscrepencyDeferralFacilityFileUpload";
	
	public static final String DFSO_DiscrepencyDeferralGeneral_FILENAME = "DfsoDiscrepencyDeferralGeneralFileUpload";
	
	public static final String INSTANCE_EXC_LINE_FR_STP_SRM = "EXC_LINE_FR_STP_SRM";
	public static final String ACTION_READ_EXC_LINE_FR_STP_SRM = "READ";
	public static final String ACTION_READ_EXC_LINE_FR_STP_SRM_ID = "READ_ID";
	public static final String ACTION_MAKER_CREATE_EXC_LINE_FR_STP_SRM = "CREATE";
	public static final String ACTION_CHECKER_APPROVE_EXC_LINE_FR_STP_SRM = "APPROVE";
	public static final String ACTION_CHECKER_APPROVE_CREATE_EXC_LINE_FR_STP_SRM = "APPROVE_CREATE";
	public static final String ACTION_MAKER_SAVE_EXC_LINE_FR_STP_SRM = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_EXC_LINE_FR_STP_SRM = "MAKER_UPDATE_SAVE";
	public static final String ACTION_MAKER_UPDATE_EXC_LINE_FR_STP_SRM = "UPDATE";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_EXC_LINE_FR_STP_SRM = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_EXC_LINE_FR_STP_SRM = "REJECT";
	public static final String ACTION_MAKER_EDIT_REJECTED_EXC_LINE_FR_STP_SRM = "EDIT_REJECTED";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_EXC_LINE_FR_STP_SRM = "EDIT_REJECTED_CREATE";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_EXC_LINE_FR_STP_SRM = "EDIT_REJECTED_UPDATE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_EXC_LINE_FR_STP_SRM = "CLOSE_REJECTED";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_EXC_LINE_FR_STP_SRM = "CLOSE_REJECTED_CREATE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_EXC_LINE_FR_STP_SRM = "CLOSE_REJECTED_UPDATE";
	public static final String ACTION_MAKER_CLOSE_DRAFT_EXC_LINE_FR_STP_SRM = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_MAKER_DELETE_EXC_LINE_FR_STP_SRM = "DELETE";
	public static final String ACTION_CHECKER_APPROVE_DELETE_EXC_LINE_FR_STP_SRM = "CHECKER_APPROVE_DELETE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_EXC_LINE_FR_STP_SRM = "CLOSE_REJECTED_DELETE";
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_EXC_LINE_FR_STP_SRM = "EDIT_REJECTED_DELETE";
	
	String SBBG_SBLC_FILE_UPLOAD = "SBBCSBLCFileUpload";
	String MF_EQUITY_FILE_UPLOAD = "MFEQUITYFileUpload";
	String FD_STP_FILE_UPLOAD = "FDFileUpload";
	public class LimitsOfAuthorityMaster{
		public static final String INSTANCE_LIMITS_OF_AUTHORITY_MASTER = "LIMITS_OF_AUTHORITY";
		public static final String ACTION_READ_LIMITS_OF_AUTHORITY = "READ";
		public static final String ACTION_READ_LIMITS_OF_AUTHORITY_ID = "READ_ID";
		public static final String ACTION_MAKER_CREATE_LIMITS_OF_AUTHORITY = "CREATE";
		public static final String ACTION_CHECKER_APPROVE_LIMITS_OF_AUTHORITY = "APPROVE";
		public static final String ACTION_CHECKER_APPROVE_CREATE_LIMITS_OF_AUTHORITY = "APPROVE_CREATE";
		public static final String ACTION_MAKER_SAVE_LIMITS_OF_AUTHORITY = "MAKER_SAVE";
		public static final String ACTION_MAKER_SAVE_UPDATE_LIMITS_OF_AUTHORITY = "MAKER_UPDATE_SAVE";
		public static final String ACTION_MAKER_UPDATE_LIMITS_OF_AUTHORITY = "UPDATE";
		public static final String ACTION_CHECKER_APPROVE_UPDATE_LIMITS_OF_AUTHORITY = "CHECKER_APPROVE_UPDATE";
		public static final String ACTION_CHECKER_REJECT_LIMITS_OF_AUTHORITY = "REJECT";
		public static final String ACTION_MAKER_EDIT_REJECTED_LIMITS_OF_AUTHORITY = "EDIT_REJECTED";
		public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_LIMITS_OF_AUTHORITY = "EDIT_REJECTED_CREATE";
		public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_LIMITS_OF_AUTHORITY = "EDIT_REJECTED_UPDATE";
		public static final String ACTION_MAKER_CLOSE_REJECTED_LIMITS_OF_AUTHORITY = "CLOSE_REJECTED";
		public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_LIMITS_OF_AUTHORITY = "CLOSE_REJECTED_CREATE";
		public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_LIMITS_OF_AUTHORITY = "CLOSE_REJECTED_UPDATE";
		public static final String ACTION_MAKER_CLOSE_DRAFT_LIMITS_OF_AUTHORITY = "MAKER_UPDATE_CLOSE";
		public static final String ACTION_MAKER_DELETE_LIMITS_OF_AUTHORITY = "DELETE";
		public static final String ACTION_CHECKER_APPROVE_DELETE_LIMITS_OF_AUTHORITY = "CHECKER_APPROVE_DELETE";
		public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_LIMITS_OF_AUTHORITY = "CLOSE_REJECTED_DELETE";
		public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_LIMITS_OF_AUTHORITY = "EDIT_REJECTED_DELETE";
	}
	
	//FOR WFH CR
	public static final int TEAM_TYPE_BRANCH_MAKER_WFH = 1034;
	public static final int TEAM_TYPE_BRANCH_CHECKER_WFH = 1035;
	public static final int TEAM_TYPE_BRANCH_VIEW_WFH = 1036;
	public static final int ALL_VIEW_REPORT_WFH = 1037;
	public static final int CPU_MAKER_CHECKER_WFH = 1030;
	public static final int CPU_ADMIN_MAKER_CHECKER_WFH = 1033;
	public static final int TEAM_TYPE_CPU_MAKER_I_WFH = 1028;
	public static final int TEAM_TYPE_SC_CHECKER_WFH = 1032;
	public static final int TEAM_TYPE_SSC_CHECKER_WFH = 1029;
	public static final int TEAM_TYPE_SC_MAKER_WFH = 1031;
	
	public static final int TEAM_TYPE_SSC_MAKER_WFH = 1027;
	
	public static final int TEAM_TYPE_CPU_MAKER_II = 1038;
	public static final int TEAM_TYPE_CPU_MAKER_II_WFH = 1039;
	
	public static final String ECBF_DEFERRAL_REPORT_UPLOAD = "ecbfDeferralReportUpload";
	
	// Risk Type Master
	public static final String INSTANCE_RISK_TYPE = "RISK_TYPE";
	public static final String ACTION_READ_RISK_TYPE = "READ_RISK_TYPE";
	public static final String ACTION_READ_RISK_TYPE_ID = "READ_RISK_TYPE_ID";
	public static final String ACTION_MAKER_CREATE_RISK_TYPE = "CREATE_RISK_TYPE";
	public static final String ACTION_CHECKER_APPROVE_RISK_TYPE = "APPROVE_RISK_TYPE";
	public static final String ACTION_CHECKER_APPROVE_CREATE_RISK_TYPE = "APPROVE_CREATE_RISK_TYPE";
	public static final String ACTION_MAKER_SAVE_RISK_TYPE = "MAKER_SAVE";
	public static final String ACTION_MAKER_SAVE_UPDATE_RISK_TYPE = "MAKER_UPDATE_SAVE";
	public static final String ACTION_MAKER_UPDATE_RISK_TYPE = "UPDATE_RISK_TYPE";
	public static final String ACTION_CHECKER_APPROVE_UPDATE_RISK_TYPE = "CHECKER_APPROVE_UPDATE";
	public static final String ACTION_CHECKER_REJECT_RISK_TYPE = "REJECT_RISK_TYPE";
	public static final String ACTION_MAKER_EDIT_REJECTED_RISK_TYPE = "EDIT_REJECTED_RISK_TYPE";
	public static final String ACTION_MAKER_EDIT_REJECTED_CREATE_RISK_TYPE = "EDIT_REJECTED_CREATE_RISK_TYPE";
	public static final String ACTION_MAKER_EDIT_REJECTED_UPDATE_RISK_TYPE = "EDIT_REJECTED_UPDATE_RISK_TYPE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_RISK_TYPE = "CLOSE_REJECTED_RISK_TYPE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_CREATE_RISK_TYPE = "CLOSE_REJECTED_CREATE_RISK_TYPE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_UPDATE_RISK_TYPE = "CLOSE_REJECTED_UPDATE_RISK_TYPE";
	public static final String ACTION_MAKER_CLOSE_DRAFT_RISK_TYPE = "MAKER_UPDATE_CLOSE";
	public static final String ACTION_MAKER_DELETE_RISK_TYPE = "DELETE_RISK_TYPE";
	public static final String ACTION_CHECKER_APPROVE_DELETE_RISK_TYPE = "CHECKER_APPROVE_DELETE";
	public static final String ACTION_MAKER_CLOSE_REJECTED_DELETE_RISK_TYPE = "CLOSE_REJECTED_DELETE_RISK_TYPE";
	public static final String ACTION_MAKER_EDIT_REJECTED_DELETE_RISK_TYPE = "EDIT_REJECTED_DELETE_RISK_TYPE";

	
	public static final String SCF="SCF";
	public static final String ECBF="ECBF";
	
	public static final String CUSTOMER="CUSTOMER";
	public static final String LINE="LINE";

	// ************************* Digital Library Starts Here **********************************
	
	public static final String INSTANCE_DIGITAL_LIBRARY_GROUP = "DIGITAL_LIB_GROUP";
	

	public static final String ACTION_READ_DIGITAL_LIBRARY_GROUP = "ACTION_READ_DIGITAL_LIBRARY_GROUP";

	public static final String ACTION_MAKER_UPDATE_DIGITAL_LIBRARY_GROUP = "ACTION_MAKER_UPDATE";

		public static final String ACTION_MAKER_SUBMIT_DIGITAL_LIBRARY_GROUP = "ACTION_MAKER_SUBMIT";

		public static final String ACTION_MAKER_SUBMIT_REJECTED_DIGITAL_LIBRARY_GROUP = "ACTION_MAKER_SUBMIT_REJECTED";

		public static final String ACTION_MAKER_UPDATE_REJECTED_DIGITAL_LIBRARY_GROUP = "ACTION_MAKER_UPDATE_REJECTED";

		public static final String ACTION_MAKER_CLOSE_REJECTED_DIGITAL_LIBRARY_GROUP = "ACTION_MAKER_CLOSE_REJECTED";

		public static final String ACTION_MAKER_CLOSE_DRAFT_DIGITAL_LIBRARY_GROUP = "ACTION_MAKER_CLOSE_DRAFT";

		public static final String ACTION_CHECKER_APPROVE_DIGITAL_LIBRARY_GROUP = "ACTION_CHECKER_APPROVE_DIGITAL_LIBRARY_GROUP";

		public static final String ACTION_CHECKER_REJECT_DIGITAL_LIBRARY_GROUP = "ACTION_CHECKER_REJECT_DIGITAL_LIBRARY_GROUP";

		public static final String DIGITAL_LIBRARY_GROUP_TYPE = "DIGITAL_LIBRARY";
		
		// ************************* Digital Library Ends Here **********************************
		
		public static final String DIGI_LIB_DOC_FILE_UPLOAD ="DigitalLibraryDocumentDetailsSyncUp";
}