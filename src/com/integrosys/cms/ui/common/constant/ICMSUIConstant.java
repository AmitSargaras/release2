/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.common.constant;

/**
 * This is an Interface to store common constants to be used accross the various
 * ui packages in cms project
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/09/05 06:26:57 $ Tag: $Name: $
 */
public interface ICMSUIConstant {

	static final String BORROWER_ID = "borrower_Id";

	static final String COLLATRAL_ID = "collatral_Id";

	static final String CUSTODIAN_OP_CODE_LODGE = "lodge";

	static final String CUSTODIAN_OP_CODE_RELODGE = "relodge";

	static final String CUSTODIAN_OP_CODE_TEMPUPLIFT = "tempuplift";

	static final String CUSTODIAN_OP_CODE_PERMUPLIFT = "permuplift";

	static final String CUSTODIAN_OP_CODE_ALLOW_TEMPUPLIFT = "allowtempuplift";

	static final String CUSTODIAN_OP_CODE_ALLOW_PERMUPLIFT = "allowpermuplift";

	static final String CUSTODIAN_OP_CODE_VIEW = "view";

	static final String CUSTODIAN_OP_CODE_PENDING_REVERSAL = "reversal";

    static final String CUSTODIAN_OP_CODE_LODGE_REVERSAL = "lodge_reversal";

	static final String REPORT_TEMPLATE_PATH = "integrosys.report.template.path";

	public static final int PRICE_DEC_PLACES = 2;

	public static final String VALIDATION_NUMBER_EXCEED_ERR = "moredecimalexceeded";

	// category code constant
	static final String FREQ_TYPE = "FREQ_TYPE";

	static final String TIME_FREQ = "TIME_FREQ";

	// category code for contract financing
	static final String CONTRACT_TERMS = "CONTRACT_TERMS";

	static final String CONTRACT_FAC_TYPE = "CONTRACT_FAC_TYPE";

	static final String AWARDER_TYPE = "AWARDER_TYPE";

	static final String CONTRACT_TYPE = "CONTRACT_TYPE";

	static final String SINKING_FUND_PARTY = "SINKING_FUND_PARTY";

	public static final String STOCK_EXCHANGE = "STOCK_EXCHANGE";

	public static final String STOCK_TYPE = "STOCK_TYPE";

	public static final String SHARE_TYPE = "SHARE_TYPE";

	// Generic index for "Others" - handle label not starting with A-z
	static final String IDX_OTHERS = "Others";

	// country constant
	static final String COUNTRY_MALAYSIA = "MY";

	static final String COUNTRY_INDIA = "IN";

	// country constant
	static final String PROPERTY_TENURE_TYPE_OTHERS = "L5";

	static final String PROPERTY_TENURE_LEASEHOLD = "L";

	static final String PROPERTY_QUIT_RENT_YES = "Y";

	static final String PROPERTY_TITLE_TYPE_MARST = "M";

	// Class name constant
	String CN_STRING = "java.lang.String";

	String CN_HASHMAP = "java.util.HashMap";

	String CN_COLLECTION = "java.util.Collection";

	String CN_LIST = "java.util.List";

	String CMS_APP_URL = "cms.app.url";

	String CMS_REPORT_URL = "cms.report.url";

	String COMMON_CODE_REF_LE_ID_TYPE = "LE_ID_TYPE";

	String LOAN_TYPE_CODE = "LOAN_TYPE";

	String AGREEMENT_TYPE_CODE = "AGREEMENT_TYPE";

	String INTEREST_RATE_TYPE_CODE = "INT_RATE_TYPE";

	String RATING_TYPE_CODE = "RATING_TYPE";

	String RATING_CODE = "RATING";

	String AA_SOURCE_CODE = "AA_SOURCE";

	public final static String PROPERTY_TYPE = "PROPERTY_TYPE";

	public final static String AREA_UOM = "AREA_UOM";

	public final static String SALES_PROCEED_PUR = "SALES_PROCEED_PUR";

	public final static String DISBURSE_PURPOSE = "DISBURSE_PURPOSE";

	public final static String DISBURSE_SUBPURPOSE = "DISBURSE_SUBPURPOSE";

	public final static String DISBURSE_MODE = "DISBURSE_MODE";

	public final static String SALES_PROCEED_STAT = "SALES_PROCEED_STAT";

    public final static String CMS_PUBLISH_REPORT_URL = "cms.publish.report.url";
    
    public final static String STYLE_FIELDNAME = "fieldname";
    public final static String STYLE_FIELDNAME_DIFF = "fieldnamediff";

	// Used in Entity for Customer Relationship
    public final static String ENTITY_REL = "ENT_REL";

    //  Used in Group Module
    public final static String GRP_TYPE ="GRP_TYPE";
    public final static String ISO_CUR ="ISO_CUR";
    public final static String ACC_MGNT ="STATE";
    public final static String GROUP_ACC_MANAGER ="GROUP_ACC_MANAGER";
    public final static String GENT_REL ="GENT_REL";
    public final static String BIZ_UNIT ="5";
    public final static String ENT_REL ="ENT_REL";
    public final static String APPROVED_BY_TYPE ="APP_BY";
    public final static String INT_LMT_APPLY ="INTERNAL_LIMIT";

    public final static String CREDIT_GRADE_TYPE ="CREDIT_GRADE_TYPE";
    public final static String RATING_TYPE = RATING_TYPE_CODE;
    public final static String SUB_LIMIT_TYPE ="SUB_LIMIT_TYPE";
    public final static String SUB_LIMIT_DESC ="SUB_LIMIT_DESC";
    
    // Andy Wong, 1 July 2008: customization for ABG
    public final static String OTR_LIMIT_TYPE ="OTHER_LIMIT_TYPE";
    public final static String OTR_LIMIT_DESC ="OTHER_LIMIT_DESC";
	public final static String CREDIT_GRADE_RATING = "INTERNAL_CREDIT_GRADE";

    public final static String EXEMPT_FACILITY_DUPLICATE ="exempt.facility.duplicate";
    public final static String COMMON_CODE_BACK_SYS ="BACK_SYS";

    public final static String SECTORLIMIT_DUPLICATE_SECTOR = "error.sectorlimit.sectorcode.duplicate";

    public final static String PRODUCTLIMIT_DUPLICATE_PRODUCT = "error.productlimit.refcode.duplicate"; 
    
    static final String ACTION_ADD = "Add";
    static final String ACTION_EDIT = "Edit";
}