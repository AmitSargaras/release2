package com.integrosys.cms.ui.collateral.marketablesec;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

import java.util.Map;

public abstract class AbstractMarketableSecStpValidator extends AbstractCollateralStpValidator {

	protected boolean validateMarketableCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}
		return true;
	}

	public boolean validate(Map context) {
		// TODO Auto-generated method stub
		return false;
	}

	protected ActionErrors validateAndAccumulateMarketable(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);

		return errorMessages;
	}
}
