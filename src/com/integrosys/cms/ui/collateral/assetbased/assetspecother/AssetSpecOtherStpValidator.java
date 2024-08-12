package com.integrosys.cms.ui.collateral.assetbased.assetspecother;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;

import java.util.Map;

public class AssetSpecOtherStpValidator extends AbstractAssetBasedStpValidator {
	public boolean validate(Map context) {
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
		/*ISpecificChargeOthers specChargeOthers = (ISpecificChargeOthers) collateral;
		if (StringUtils.isNotBlank(specChargeOthers.getRemarks())) {
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
