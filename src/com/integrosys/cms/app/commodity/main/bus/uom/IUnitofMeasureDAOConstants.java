/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/IUnitofMeasureDAOConstants.java,v 1.3 2004/06/14 10:10:24 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/14 10:10:24 $ Tag: $Name: $
 */
public interface IUnitofMeasureDAOConstants {
	// todo: get profile names from profile package when it is ready!!!
	public static final String PROFILE_TABLE = "CMS_CMDT_PROFILE";

	public static final String PROFILE_PROFILE_ID = "PROFILE_ID";

	public static final String PROFILE_COMMODITY_CATEGORY = "CATEGORY";

	public static final String PROFILE_PRODUCT_TYPE = "PRODUCT_TYPE";

	public static final String PROFILE_PRODUCT_SUBTYPE = "PRODUCT_SUB_TYPE";

	public static final String PROFILE_RIC = "RIC";

	public static final String PROFILE_COUNTRY_CODE = "COUNTRY_AREA";

	public static final String PROFILE_PRICE_TYPE = "PRICE_TYPE";

	public static final String PROFILE_STATUS = "STATUS";

	// table name and column name for commodity price.
	public static final String UOM_STAGE_TABLE = "CMS_STAGE_CMDT_UOM";

	public static final String UOM_TABLE = "CMS_CMDT_UOM";

	public static final String UOM_UOM_ID = "COMMODITY_UOM_ID";

	public static final String UOM_PROFILE_ID = "PROFILE_ID";

	public static final String UOM_GROUP_ID = "GROUP_ID";

	public static final String UOM_STATUS = "STATUS";

	public static final String MAX_UOM_GROUP_ID = "MAX_GROUP_ID";

}
