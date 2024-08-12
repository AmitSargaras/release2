/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/ProfileSearchCriteria.java,v 1.4 2006/03/23 08:58:58 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoSearchCriteria;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 11:15:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileSearchCriteria extends CommodityMainInfoSearchCriteria {

	private String category;

	private String productSubType;

	public String priceType;

	public String nonRICCode;

	public ProfileSearchCriteria() {
		super(ICommodityMainInfo.INFO_TYPE_PROFILE);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getNonRICCode() {
		return nonRICCode;
	}

	public void setNonRICCode(String nonRICCode) {
		this.nonRICCode = nonRICCode;
	}

	public boolean isEmpty() {
		if (category != null) {
			return false;
		}
		if (productSubType != null) {
			return false;
		}
		if (priceType != null) {
			return false;
		}
		if (nonRICCode != null) {
			return false;
		}
		return true;
	}
}
