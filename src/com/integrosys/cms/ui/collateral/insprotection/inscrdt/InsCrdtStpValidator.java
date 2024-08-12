package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.ui.collateral.insprotection.AbstractInsProtectionStpValidator;

import java.util.Map;

public class InsCrdtStpValidator extends AbstractInsProtectionStpValidator {
	public boolean validate(Map context) {
		if (!validateInsuranceCollateral(context)) {
			return false;
		}
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateInsurance(context);

		return errorMessages;
	}
}
