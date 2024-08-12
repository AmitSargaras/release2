package com.integrosys.cms.ui.collateral;

import java.util.Map;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * Collateral Stp Validation Utils to use the stp validator factory to retrieve
 * the correct stp validate and validate.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class CollateralStpValidateUtils {
	public static boolean validate(Map context) {
		ActionErrors errors = validateAndAccumulate(context);
		return !errors.isEmpty();
	}

	public static ActionErrors validateAndAccumulate(Map context) {
		CollateralStpValidatorFactory collateralStpValidatorFactory = (CollateralStpValidatorFactory) BeanHouse
				.get("collateralStpValidatorFactory");

		CollateralStpValidator collateralStpValidator = collateralStpValidatorFactory
				.getCollateralStpValidator((ICollateral) context.get(CollateralStpValidator.COL_OB));
		ActionErrors actionErrors = collateralStpValidator.validateAndAccumulate(context);

		return actionErrors;
	}
}
