/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/ConcReportNewConstants.java,v 1.8 2003/09/17 04:24:49 btchng Exp $
 */
package com.integrosys.cms.ui.report;

/**
 * Contains the constants for Concentration Report -> New.
 * @author $Author: btchng $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/09/17 04:24:49 $ Tag: $Name: $
 */
public class ConcReportNewConstants {

	/**
	 * common_code_category table value.
	 */
	// public static final String CODE_CATEGORY_STOCK_EXCHANGE =
	// "STOCK_EXCHANGE";
	/**
	 * Error message.
	 */
	public static final String ERROR_NO_SELECTION = "error.no.selection";

	/**
	 * An option.
	 */
	public static final String SEC_TYPE_ALL = "All";

	/**
	 * An option.
	 */
	public static final String SEC_TYPE_MARKETABLE = "MarketableSec";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_ALL = "All";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_MAIN_LOCAL = "MARKSECMAINLOCAL";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_MAIN_FOREIGN = "MARKSECMAINFOREIGN";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_OTHER_LOCAL = "MARKSECOTHERLISTEDLOCAL";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_OTHER_FOREIGN = "MARKSECOTHERLISTEDFOREIGN";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_MAIN = "Main";

	/**
	 * An option.
	 */
	public static final String SEC_SUBTYPE_OTHER = "Other";

	/**
	 * An option.
	 */
	public static final String STATE = "TOWN";

	/**
	 * An option.
	 */
	public static final String POSTCODE = "POSTCODE";

	/**
	 * An option.
	 */
	public static final String LOCATION = "LOT_LOCATION";

	/**
	 * A report type and also a sample type value.
	 */
	public static final String RPT_TYPE_STOCK_SHARES = "stockShares";

	/**
	 * A report type and also a sample type value.
	 */
	public static final String RPT_TYPE_SEC_STOCK_EX = "securitiesByStockExchange";

	/**
	 * A report type and also a sample type value.
	 */
	public static final String RPT_TYPE_SEC_SEC_TYPE = "securitiesBySecurityType";

	/**
	 * A report type and also a sample type value.
	 */
	public static final String RPT_TYPE_PROPERTY = "property";

	/**
	 * A report type and also a sample type value.
	 */
	public static final String RPT_TYPE_CURRENCY_SEC_TYPE = "currencyBySecurityType";

	/**
	 * Concentration Risk Profile Reports in CMS_REPORT_MASTER
	 */
	public static final int STOCK_SHARES_RPT_ID = 9;

	public static final int SECURITIES_STOCK_EXCHANGE_RPT_ID = 10;

	public static final int PROPERTY_RPT_ID = 12;

	public static final int SECURITY_SUB_TYPE_REGION_RPT_ID = 13;

	public static final int CURRENCY_BY_SEC_TYPE_RPT_ID = 11;
}
