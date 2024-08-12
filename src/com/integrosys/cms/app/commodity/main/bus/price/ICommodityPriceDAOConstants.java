/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/ICommodityPriceDAOConstants.java,v 1.4 2006/03/03 04:53:44 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

/**
 * Interface that defines constants for table and column names used in Commodity
 * Price DAO.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/03 04:53:44 $ Tag: $Name: $
 */
public interface ICommodityPriceDAOConstants {
	// table name and column names for commodity profile.
	// todo: get profile names from profile package when it is ready!!!
	public static final String PROFILE_TABLE = "CMS_CMDT_PROFILE";

	public static final String PROFILE_PROFILE_ID = "PROFILE_ID";

	public static final String PROFILE_COMMODITY_CATEGORY = "CATEGORY";

	public static final String PROFILE_PRODUCT_TYPE = "PRODUCT_TYPE";

	public static final String PROFILE_PRODUCT_SUBTYPE = "PRODUCT_SUB_TYPE";

	public static final String PROFILE_RIC = "RIC";

	public static final String PROFILE_RIC_TYPE = "RIC_TYPE";

	public static final String PROFILE_COUNTRY_CODE = "COUNTRY_AREA";

	public static final String PROFILE_PRICE_TYPE = "PRICE_TYPE";

	public static final String PROFILE_STATUS = "STATUS";

	// table name and column name for commodity price.
	public static final String PRICE_TABLE = "CMS_CMDT_PRICE";

	public static final String PRICE_STAGE_TABLE = "CMS_STAGE_CMDT_PRICE";

	public static final String PRICE_ID = "COMMODITY_PRICE_ID";

	public static final String PRICE_PROFILE_ID = "PROFILE_ID";

	public static final String PRICE_CLOSE_PRICE_CCY_CODE = "CLOSE_PRICE_CURRENCY";

	public static final String PRICE_CLOSE_PRICE = "CLOSE_PRICE";

	public static final String PRICE_CURRENT_PRICE_CCY_CODE = "CURRENT_PRICE_CURRENCY";

	public static final String PRICE_CURRENT_PRICE = "CURRENT_PRICE";

	public static final String PRICE_CLOSE_UPDATE_DATE = "CLOSE_UPDATE_DATE";

	public static final String PRICE_CURRENT_UPDATE_DATE = "CURRENT_UPDATE_DATE";

	public static final String PRICE_GROUP_ID = "GROUP_ID";

	public static final String PRICE_STATUS = "STATUS";

	public static final String PRICE_VERSION_TIME = "VERSION_TIME";
}
