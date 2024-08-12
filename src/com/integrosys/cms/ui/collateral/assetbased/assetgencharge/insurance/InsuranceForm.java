/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/InsuranceForm.java,v 1.4 2005/08/12 03:19:29 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.insurancepolicy.InsurancePolicyForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/12 03:19:29 $ Tag: $Name: $
 */

public class InsuranceForm extends InsurancePolicyForm implements Serializable {

	private String[] insuranceLinks;

	public String getCmsSecurityCurrency() {
		return this.conversionCurrency;
	}

	public void setCmsSecurityCurrency(String cmsSecurityCurrency) {
		this.conversionCurrency = cmsSecurityCurrency;
	}

	public String getInsuredAmtCMS() {
		return this.insuredAmtConvert;
	}

	public void setInsuredAmtCMS(String insuredAmtCMS) {
		this.insuredAmtConvert = insuredAmtCMS;
	}

	public String[] getInsuranceLinks() {
		return this.insuranceLinks;
	}

	public void setInsuranceLinks(String[] insuranceLinks) {
		this.insuranceLinks = insuranceLinks;
	}

	public String[][] getMapper() {
		String[][] input = { { "insuranceObj",
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance.InsuranceMapper" }, };
		return input;
	}
}
