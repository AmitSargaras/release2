package com.integrosys.cms.ui.collateral.nocollateral.nocollateral;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral;
import com.integrosys.cms.ui.collateral.nocollateral.AbstractNoCollateralStpValidator;

import java.util.Map;

public class NoCollateralaStpValidator extends AbstractNoCollateralStpValidator {
	public boolean validate(Map context) {
		if (!validateNoCollateral(context)) {
			return false;
		}
		if (validateAndAccumulate(context).size() <= 0) {
			// do nothing
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateNoCollateral(context);

		return errorMessages;
	}
}
