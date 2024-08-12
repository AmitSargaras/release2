package com.integrosys.cms.ui.bfltatparameters;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.common.CountryList;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/19 08:18:43 $ Tag: $Name: $
 */
public class BflTatParametersForm extends CommonForm {

	private String countryCode = "";

	private String countryName = "";

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return CountryList.getInstance().getCountryName(countryCode);
	}

	/*
	 * public String[][] getMapper() { String[][] input = { {"commodityDealObj",
	 * "com.integrosys.cms.ui.commoditydeal.CommodityDealMapper"},
	 * {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}, };
	 * return input; }
	 */

}
