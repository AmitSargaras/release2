/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommDealSearchForm.java,v 1.3 2004/06/07 03:34:31 pooja Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import com.integrosys.cms.ui.common.TrxContextForm;

public class CommDealSearchForm extends TrxContextForm implements java.io.Serializable {

	private String dealNo = "";

	public String getDealNo() {
		return this.dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public void reset() {

	}

	/**
	 * 
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it.
	 * 
	 * it has a syntax (key,Mapperclassname)
	 * 
	 * 
	 * 
	 * @return One-dimesnional String Array
	 */

	public String[][] getMapper() {

		String[][] input = {
				{ "commodityDealSearchCriteria", "com.integrosys.cms.ui.commoditydeal.CommDealSearchMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "dealList", "com.integrosys.cms.ui.commoditydeal.CommDealSearchMapper" }

		};

		return input;

	}

}
