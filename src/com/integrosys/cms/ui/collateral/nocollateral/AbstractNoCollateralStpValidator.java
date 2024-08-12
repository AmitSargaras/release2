package com.integrosys.cms.ui.collateral.nocollateral;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.INoCollateral;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

import java.util.Map;

public abstract class AbstractNoCollateralStpValidator extends AbstractCollateralStpValidator {

	protected boolean validateNoCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}
		return true;
	}

	public boolean validate(Map context) {
		// TODO Auto-generated method stub
		return false;
	}

	protected ActionErrors validateAndAccumulateNoCollateral(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);

		return errorMessages;
	}

}
