/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/WarehouseSearchCriteria.java,v 1.2 2004/06/04 04:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 11:15:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class WarehouseSearchCriteria extends CommodityMainInfoSearchCriteria {

	private String countryCode;

	public WarehouseSearchCriteria() {
		super(ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
