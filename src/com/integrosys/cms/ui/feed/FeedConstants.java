/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/FeedConstants.java,v 1.16 2003/12/17 07:45:37 btchng Exp $
 */

package com.integrosys.cms.ui.feed;

/**
 * This class contains all the constants needed for feed.
 * 
 * When modifying the constant values here, make sure these modifications are
 * accompanied by changes to external entities like property files, database
 * values, etc.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.16 $
 * @since $Date: 2003/12/17 07:45:37 $ Tag: $Name: $
 */
public class FeedConstants {

	// public static final String CODE_CATEGORY_STOCK_EXCHANGE =
	// "STOCK_EXCHANGE";

	public static final String ERROR_INVALID = "error.feeds.invalid.number.format";

	public static final String ERROR_DUPLICATE = "error.feeds.duplicate";

	public static final String ERROR_PAST_DATE = "error.feeds.past.date";
	
	public static final String ERROR_FUTURE_DATE = "error.feeds.future.date";
	
	public static final String ERROR_MANDATORY = "error.number.mandatory";

	public static final String ERROR_CHKBOX_MANDATORY = "error.checkbox.mandatory";

	public static final String ERROR_NO_SELECTION = "error.feeds.no.selection";

	public static final String PRICE_REGEX = "[0-9]{1,4}\\.[0-9]{1,6}|[0-9]{1,4}";

	public static final long FOREX_PRICE_MAX = 10000000;

	public static final long GOLD_PRICE_MAX = 1000000000000000000l;

	public static final String FOREX_PRICE_FORMAT = "[0-9]{1,9}\\.[0-9]{1,6}|[0-9]{1,4}";

	public static final double FOREX_PRICE_REGEX_MAX = 9999999.999999;
	
	public static final double GOLD_PRICE_REGEX_MAX = 999999999999999999.999999;

	public static final String FOREX_EXCHANGE_MAX_STR = String.valueOf(FOREX_PRICE_REGEX_MAX);
	
	public static final String GOLD_EXCHANGE_MAX_STR = String.valueOf(GOLD_PRICE_REGEX_MAX);

	public static final String INDEX_REGEX = "[0-9]{1,9}\\.[0-9]{1,4}|[0-9]{1,9}";

	public static final String INFO_MISSING_SETUP_DATA = "error.missing.setup.data";
	
	public static final String FUND_SIZE_MAX_STR = "999999999999999";
	
	public static final double FUND_SIZE_MAX = Double.parseDouble(FUND_SIZE_MAX_STR);
	
	public static final String CURRENCY_CHECK_MAX = String.valueOf(9999.99);
	
	public static final String CUPPON_RATE_MAX = String.valueOf(99.99);
}
