package com.integrosys.cms.ui.collateral.others;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

import java.util.Map;

public abstract class AbstractOthersStpValidator extends AbstractCollateralStpValidator {

	protected boolean validateOthersCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}
		return true;
	}

	public boolean validate(Map context) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected ActionErrors validateAndAccumulateOthers(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);
        context.put(ERRORS, errorMessages);
		errorMessages = validateAndAccumulateInsurancePolicies(context);
		
		return errorMessages;
	}
}
