package com.integrosys.cms.host.eai.support;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface IEAIResponseConstant {
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

	/**
	 * error code to indicate there is an technical error, but not
	 * business/functional specific
	 */
	public static final String TECHNICAL_ERROR_CODE = "TECHNICAL_ERROR";

}
