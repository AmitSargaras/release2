package com.integrosys.cms.ui.collateral.assetbased;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

import java.util.Map;

public abstract class AbstractAssetBasedStpValidator extends AbstractCollateralStpValidator {

	protected boolean validateAssetBasedCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}
		return true;
	}

	public boolean validate(Map context) {
		// TODO Auto-generated method stub
		return false;
	}

	protected ActionErrors validateAndAccumulateAssetBased(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);
		   ICollateral collateral = (ICollateral) context.get(COL_OB);
        if(!collateral.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD)){
            context.put(ERRORS, errorMessages);
            errorMessages = validateAndAccumulateInsurancePolicies(context);
        }

        /*IAssetBasedCollateral assetBasedCollateral = (IAssetBasedCollateral) collateral;
		if (assetBasedCollateral.getDepositAmount() != null) {
			errorMessages.add("depAmt", new ActionMessage("error.mandatory"));
		}*/
		return errorMessages;
	}

}
