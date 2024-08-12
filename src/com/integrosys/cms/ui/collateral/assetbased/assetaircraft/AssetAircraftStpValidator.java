package com.integrosys.cms.ui.collateral.assetbased.assetaircraft;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.ISpecificChargeAircraft;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;

import java.util.Map;

public class AssetAircraftStpValidator extends AbstractAssetBasedStpValidator {
	public boolean validate(Map context) {
//		ISpecificChargeAircraft specChargeAircraft = (ISpecificChargeAircraft) collateral;
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
		if (validateAndAccumulate(context).size() <= 0
				/*StringUtils.isNotBlank(specChargeAircraft.getEnvRiskyRemarks())
				&& StringUtils.isNotBlank(specChargeAircraft.getRemarks())
				&& StringUtils.isNotBlank(specChargeAircraft.getModelNo())
				&& StringUtils.isNotBlank(specChargeAircraft.getAircraftSerialNo())
				&& specChargeAircraft.getPurchaseDate() != null
				&& specChargeAircraft.getScrapValue() != null
				&& StringUtils.isNotBlank(specChargeAircraft.getPubTransport())
				&& specChargeAircraft.getChattelSoldDate() != null
				&& StringUtils.isNotBlank(specChargeAircraft.getRlSerialNumber())
				&& specChargeAircraft.getRepossessionDate() != null*/
				) {
			// do nothing
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateAssetBased(context);
		
		ISpecificChargeAircraft specChargeAircraft = (ISpecificChargeAircraft) context.get(COL_OB);
		// Allow passive
		/*if (StringUtils.isBlank(specChargeAircraft.getPubTransport())) {
			errorMessages.add("pubTransport", new ActionMessage("error.mandatory"));
		}
		// Model number
		if (StringUtils.isBlank(specChargeAircraft.getModelNo())) {
			errorMessages.add("modelNo", new ActionMessage("error.mandatory"));
		}*/
		return errorMessages;
	}
}
