package com.integrosys.cms.ui.collateral.assetbased.assetrecopen;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;

import java.util.Map;

public class AssetRecOpenStpValidator extends AbstractAssetBasedStpValidator {
	public boolean validate(Map context) {
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
//		IReceivableOpen receivableOpen = (IReceivableOpen) collateral;
		/*if (StringUtils.isNotBlank(receivableOpen.getRemarks())) {
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
