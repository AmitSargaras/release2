package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;

import java.util.Map;

public class AssetGenChargeStpValidator extends AbstractAssetBasedStpValidator {
	public boolean validate(Map context) {
//		IGeneralCharge genCharge = (IGeneralCharge) collateral;
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
		/*if (StringUtils.isNotBlank(genCharge.getRemarks())) {
			// do nothing
		}
		else return false;*/
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateAssetBased(context);

		return errorMessages;
	}
}
