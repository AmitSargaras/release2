package com.integrosys.cms.ui.collateral.others.othersa;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.ui.collateral.others.AbstractOthersStpValidator;

import java.util.Map;

public class OthersaStpValidator extends AbstractOthersStpValidator {
	public boolean validate(Map context) {
		if (!validateOthersCollateral(context)) {
			return false;
		}
//		IOthersCollateral others = (IOthersCollateral) collateral;
//		IInsurancePolicy[] insurancePolicies = others.getInsurancePolicies();
		if (validateAndAccumulate(context).size() <= 0) {
			
		}
		/*if (others.getUnitsNumber() != 0.0d
				&& insurancePolicies != null
				) {
			for (int i = 0; i < insurancePolicies.length; i++) {
				if (
						StringUtils.isNotBlank(insurancePolicies[i].getPolicyNo())
						&& StringUtils.isNotBlank(insurancePolicies[i].getInsurerName())
						&& StringUtils.isNotBlank(insurancePolicies[i].getInsuranceType())
						&& insurancePolicies[i].getInsuredAmount() != null
						// something is need to be rechecked, screen name: Insured Amount (Conversion Currency)
						// form name: InsuredAmtConvert
						&& insurancePolicies[i].getEffectiveDate() != null
						&& insurancePolicies[i].getExpiryDate() != null
						&& StringUtils.isNotBlank(insurancePolicies[i].getBank_customer_arrange())
						&& insurancePolicies[i].getInsIssueDate() != null
						&& insurancePolicies[i].getInsuranceExchangeRate() != 0.0d
						&& StringUtils.isNotBlank(insurancePolicies[i].getInsuranceCompanyName())
						&& StringUtils.isNotBlank(insurancePolicies[i].getDebitingACNo())
						&& StringUtils.isNotBlank(insurancePolicies[i].getAcType())
					) {
					
				}
			}
			// do nothing
		}*/
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateOthers(context);

		return errorMessages;
	}
}
