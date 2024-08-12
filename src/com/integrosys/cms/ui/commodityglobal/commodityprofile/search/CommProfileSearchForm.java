/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/search/CommProfileSearchForm.java,v 1.2 2006/03/23 09:51:05 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.search;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-2-9
 * @Tag com.integrosys.cms.ui.commodityglobal.commodityprofile.search.
 *      CommProfileItemForm.java
 */
public class CommProfileSearchForm extends CommonForm implements Serializable {
	private String commodityCategory;

	private String priceType;

	private String nonRICCode;

	private String commoditySubType;

	public String getCommodityCategory() {
		return commodityCategory;
	}

	public void setCommodityCategory(String commodityCategory) {
		this.commodityCategory = commodityCategory;
	}

	public String getCommoditySubType() {
		return commoditySubType;
	}

	public void setCommoditySubType(String commoditySubType) {
		this.commoditySubType = commoditySubType;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getNonRICCode() {
		return nonRICCode;
	}

	public void setNonRICCode(String nonRicCode) {
		this.nonRICCode = nonRicCode;
	}

	public String[][] getMapper() {
		String[][] input = { { CMDTProfConstants.AN_CMDT_PROF_SEARCH_OBJ, CMDTProfConstants.CN_CMDT_PROF_SEARCH_MAP } };
		return input;
	}
}
