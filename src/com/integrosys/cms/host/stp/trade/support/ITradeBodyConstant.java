package com.integrosys.cms.host.stp.trade.support;

/**
 * @author $Author: marvin $<br>
 * @author Chin Kok Cheong
 * @version $Id$
 */
public interface ITradeBodyConstant {
    public static final int DEFAULT_LOB_BUFFER_SIZE = 256;

	public static final String ACK_GOOD = "0";

	public static final String CUSTOMER_ERROR_PREFIX = "CUST";

	public static final String COLLATERAL_ERROR_PREFIX = "COLL";

	public static final String AA_ERROR_PREFIX = "AACC";

	public static final String PLEDGOR_ERROR_PREFIX = "PLED";

	public static final String DOCUMENT_ERROR_PREFIX = "DOCU";

	public static final String STANDARD_CODE_ERROR_PREFIX = "STCD";

	public static final String MESSAGE_FORMAT_ERROR_PREFIX = "MESG";

	public static final String GENERAL_FORMAT_ERROR_PREFIX = "GEN";

	public static final String DB_FORMAT_ERROR_PREFIX = "DB";

	public static final String BOOKING_LOC_ERROR_PREFIX = "BOOK";

	public static final String EMPLOYEE_ERROR_PREFIX = "EMPL";

	public static final String GROUP_ERROR_PREFIX = "GROU";

	public static final String SECURITY_ERROR_PREFIX = "SECU";

	public static final String CODE_ERROR_PREFIX = "CODE";

	public static final String CURRENCY_ERROR_PREFIX = "CURR";

	public static final String COUNTRY_ERROR_PREFIX = "COUN";

	public static final String ERROR_GENERAL = "000001";

	public static final String ERROR_FUNCTION = "000002";

	public static final String ERROR_PERSIST = "000003";

	/**
	 * error code to indicate there is validation fail in the message before
	 * processing
	 */
	public static final String ERROR_VALIDATION = "VALIDATION_ERROR";

	public static final String CUSTOMER_ERROR_GENERAL = CUSTOMER_ERROR_PREFIX + ERROR_GENERAL;

	public static final String BOOKING_LOC_ERROR_GENERAL = BOOKING_LOC_ERROR_PREFIX + ERROR_GENERAL;

	public static final String EMPLOYEE_ERROR_GENERAL = EMPLOYEE_ERROR_PREFIX + ERROR_GENERAL;

	public static final String GROUP_ERROR_GENERAL = GROUP_ERROR_PREFIX + ERROR_GENERAL;

	public static final String PLEDGOR_ERROR_GENERAL = PLEDGOR_ERROR_PREFIX + ERROR_GENERAL;

	public static final String SECURITY_ERROR_GENERAL = SECURITY_ERROR_PREFIX + ERROR_GENERAL;

	// Collateral General Error
	public static final String COLLATERAL_ERROR_GENERAL = COLLATERAL_ERROR_PREFIX + ERROR_GENERAL;

	public static final String MESSAGE_FORMAT_ERROR_GENERAL = MESSAGE_FORMAT_ERROR_PREFIX + ERROR_GENERAL;

	public static final String GENERAL_FORMAT_ERROR_GENERAL = GENERAL_FORMAT_ERROR_PREFIX + ERROR_GENERAL;

	public static final String DB_FORMAT_ERROR_GENERAL = DB_FORMAT_ERROR_PREFIX + ERROR_GENERAL;

	public static final String AA_ERROR_GENERAL = AA_ERROR_PREFIX + ERROR_GENERAL;

	public static final String CODE_ERROR_GENERAL = CODE_ERROR_PREFIX + ERROR_GENERAL;

	public static final String CURRENCY_ERROR_GENERAL = CURRENCY_ERROR_PREFIX + ERROR_GENERAL;

	public static final String COUNTRY_ERROR_GENERAL = COUNTRY_ERROR_PREFIX + ERROR_GENERAL;

	public static final String DOCUMENT_ERROR_GENERAL = DOCUMENT_ERROR_PREFIX + ERROR_GENERAL;

	public static final char TYPE_TECHNICAL = 'T';

	public static final String TYPE_FUNCTIONAL = "F";

	public static final String RESPONSE_CODE_TAG = "ResponseCode";

	public static final String RESPONSE_MESSAGE_TAG = "ResponseMessage";

	public static final String RESPONSE_MESSAGE_XML = "ResponseMessageXML";

    public static final String XML_ID = "Id";

    public static final String XML_CDT_FILE_ID = "Id";

    public static final String XML_ACCT_NO = "accountNo";

    public static final String XML_LIMIT_NO = "limitNo";

    public static final String XML_FIRST_LINE_FAC_ID = "Id";

    public static final String XML_MAIN_LINE_FAC_ID = "Id";

    public static final String XML_MT_FAC_CD = "mtFacilityCode";

    public static final String XML_MT_BR_CD = "Id";

    public static final String XML_MT_CUR_CD = "mtCurrencyCode";

    public static final String XML_LMT_APPR = "approvedLimit";

    public static final String XML_TENURE_MT_TIME_CD = "mtTenureTimeCode";

    public static final String XML_TENURE_APPR = "approvedTenure";

    public static final String XML_DT_REVIEW = "reviewDt";

    public static final String XML_IS_TEMP = "tempFlag";

    public static final String XML_CREATED_BY = "createBy";

    public static final String XML_UPDATED_BY = "updateBy";

    public static final String XML_DT_CREATED = "createdDt";

    public static final String XML_DT_UPDATED = "updatedDt";

    public static final String XML_VERSION = "version";

    public static final String XML_INT_RATE = "intRate";

    public static final String XML_MT_INT_RATE_CD = "mtIntRateCode";

    public static final String XML_DT_EXPIRY = "expiryDt";

    public static final String XML_SPECIAL_REMARK = "specialRemark";

    public static final String XML_MT_MAINT_STS_CD = "Id";

    public static final String XML_PEN_INT_RATE = "penaltyIntRate";

    public static final String XML_MT_PEN_INT_RATE_CD = "mtPenaltyIntRateCode";

    public static final String XML_OD_RATE = "odRate";

    public static final String XML_MT_OD_RATE_CD = "mtOdRateCode";

    public static final String XML_IS_AUTO_PURGE = "autoPurgeFlag";

    public static final String XML_MT_FAC_ID = "mtFacilityId";

    public static final String XML_LSM_CD = "lsmCode";

    public static final String XML_PPS_OF_LOAN = "rbsPurposeCode";

    public static final String XML_LIMIT_DESC = "limitDesc";

    public static final String XML_IS_REVOLVING = "revolvingFlag";

    public static final String XML_IS_CGC = "cgcFlag";

    public static final String XML_IS_SECTOR = "sectorFlag";

    public static final String XML_CGC_SCHEMA = "cgcSchema";

    public static final String XML_CGC_REF_NO = "cgcRefNo";

    public static final String XML_COM_RATE = "commissionRate";

    public static final String XML_IS_DEL = "delFlag";

    public static final String XML_APPROVED_DATE = "approvedDt";

    public static final String XML_IS_MTR = "mtrFlag";

    public static final String XML_IS_MBG = "mbgFlag";
	/**
	 * error code to indicate there is an technical error, but not
	 * business/functional specific
	 */
	public static final String TECHNICAL_ERROR_CODE = "TECHNICAL_ERROR";

}