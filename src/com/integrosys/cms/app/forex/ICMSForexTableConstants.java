/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/forex/ICMSForexTableConstants.java,v 1.1 2003/08/05 11:34:53 hltan Exp $
 */
package com.integrosys.cms.app.forex;

/**
 * This interface defines the constant specific to the cms forex table and the
 * methods required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/05 11:34:53 $ Tag: $Name: $
 */
public interface ICMSForexTableConstants {
	public static final String FOREX_GROUP_TABLE = "CMS_FEED_GROUP";

	public static final String FOREX_TABLE = "CMS_FOREX";

	public static final String FGRPTBL_FEED_GROUP_ID = "FEED_GROUP_ID";

	public static final String FGRPTBL_GROUP_TYPE = "GROUP_TYPE";

	public static final String FGRPTBL_GROUP_SUBTYPE = "GROUP_SUBTYPE";

	public static final String FGRPTBL_FEED_GROUP_ID_PREF = FOREX_GROUP_TABLE + "." + FGRPTBL_FEED_GROUP_ID;

	public static final String FGRPTBL_GROUP_TYPE_PREF = FOREX_GROUP_TABLE + "." + FGRPTBL_GROUP_TYPE;

	public static final String FGRPTBL_GROUP_SUBTYPE_PREF = FOREX_GROUP_TABLE + "." + FGRPTBL_GROUP_SUBTYPE;

	public static final String FOREXTBL_FEED_ID = "FEED_ID";

	public static final String FOREXTBL_BUY_CURRENCY = "BUY_CURRENCY";

	public static final String FOREXTBL_SELL_CURRENCY = "SELL_CURRENCY";

	public static final String FOREXTBL_BUY_RATE = "BUY_RATE";

	public static final String FOREXTBL_SELL_RATE = "SELL_RATE";

	public static final String FOREXTBL_BUY_UNIT = "BUY_UNIT";

	public static final String FOREXTBL_SELL_UNIT = "SELL_UNIT";

	public static final String FOREXTBL_EFFECTIVE_DATE = "EFFECTIVE_DATE";

	public static final String FOREXTBL_FEED_GROUP_ID = "FEED_GROUP_ID";

	public static final String FOREXTBL_FEED_ID_PREF = FOREX_TABLE + "." + FOREXTBL_FEED_ID;

	public static final String FOREXTBL_BUY_CURRENCY_PREF = FOREX_TABLE + "." + FOREXTBL_BUY_CURRENCY;

	public static final String FOREXTBL_SELL_CURRENCY_PREF = FOREX_TABLE + "." + FOREXTBL_SELL_CURRENCY;

	public static final String FOREXTBL_BUY_RATE_PREF = FOREX_TABLE + "." + FOREXTBL_BUY_RATE;

	public static final String FOREXTBL_SELL_RATE_PREF = FOREX_TABLE + "." + FOREXTBL_SELL_RATE;

	public static final String FOREXTBL_BUY_UNIT_PREF = FOREX_TABLE + "." + FOREXTBL_BUY_UNIT;

	public static final String FOREXTBL_SELL_UNIT_PREF = FOREX_TABLE + "." + FOREXTBL_SELL_UNIT;

	public static final String FOREXTBL_EFFECTIVE_DATE_PREF = FOREX_TABLE + "." + FOREXTBL_EFFECTIVE_DATE;

	public static final String FOREXTBL_FEED_GROUP_ID_PREF = FOREX_TABLE + "." + FOREXTBL_FEED_GROUP_ID;

}
