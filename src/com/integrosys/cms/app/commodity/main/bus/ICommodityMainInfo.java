/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/ICommodityMainInfo.java,v 1.4 2005/10/06 03:45:40 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

/**
 * markup interface for all commodity maintenance.
 * 
 * @author $Author: hmbao $
 * @version $
 * @since $Date: 2005/10/06 03:45:40 $ Tag: $Name: $
 */

public interface ICommodityMainInfo extends java.io.Serializable {

	public static final String INFO_TYPE_TITLEDOC = "td";

	public static final String INFO_TYPE_WAREHOUSE = "w";

	public static final String INFO_TYPE_PROFILE = "profile";

	public static final String INFO_TYPE_PROFILE_STAGING = "profile_staging";

	public static final String INFO_TYPE_PRICE = "price";

	public static final String INFO_TYPE_UOM = "uom";

	public static final String INFO_TYPE_SUBLIMITTYPE = "sublimittype";

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getStatus();

	public abstract void setStatus(String status);

}
