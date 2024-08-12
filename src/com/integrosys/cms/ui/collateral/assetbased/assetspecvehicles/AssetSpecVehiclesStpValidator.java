package com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargevehicle.ISpecificChargeVehicle;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.List;
import java.util.Map;

public class AssetSpecVehiclesStpValidator extends AbstractAssetBasedStpValidator {
	private List collateralCodeRequireCMV;

	public List getCollateralCodeRequireCMV() {
		return collateralCodeRequireCMV;
	}

	public void setCollateralCodeRequireCMV(List collateralCodeRequireCMV) {
		this.collateralCodeRequireCMV = collateralCodeRequireCMV;
	}

	public boolean validate(Map context) {
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
		// ISpecificChargeVehicle specChargeVehicle = (ISpecificChargeVehicle)
		// collateral;
		if (validateAndAccumulate(context).size() <= 0
		/*
		 * StringUtils.isNotBlank(specChargeVehicle.getGoodStatus()) &&
		 * StringUtils.isNotBlank(specChargeVehicle.getRegistrationNo()) &&
		 * StringUtils.isNotBlank(specChargeVehicle.getLogBookNumber()) &&
		 * specChargeVehicle.getRegistrationFee() != null &&
		 * specChargeVehicle.getRegistrationDate() != null &&
		 * StringUtils.isNotBlank(specChargeVehicle.getBrand()) &&
		 * StringUtils.isNotBlank(specChargeVehicle.getModelNo()) &&
		 * specChargeVehicle.getYearOfManufacture() != 0 &&
		 * StringUtils.isNotBlank(specChargeVehicle.getAssetType()) &&
		 * specChargeVehicle.getPurchasePrice() != null &&
		 * specChargeVehicle.getPurchaseDate() != null &&
		 * StringUtils.isNotBlank(specChargeVehicle.getChassisNumber()) &&
		 * StringUtils.isNotBlank(specChargeVehicle.getEngineNo()) &&
		 * StringUtils.isNotBlank(specChargeVehicle.getVehColor()) &&
		 * specChargeVehicle.getDptradein() != null &&
		 * specChargeVehicle.getTradeinValue() != null &&
		 * StringUtils.isNotBlank(specChargeVehicle.getOwnershipClaimNumber())
		 * && specChargeVehicle.getDpcash() != null &&
		 * StringUtils.isNotBlank(specChargeVehicle.getPubTransport()) &&
		 * StringUtils.isNotBlank(specChargeVehicle.getYardOptions()) &&
		 * specChargeVehicle.getChattelSoldDate() != null &&
		 * StringUtils.isNotBlank(specChargeVehicle.getRlSerialNumber()) &&
		 * specChargeVehicle.getRoadTaxExpiryDate() != null &&
		 * specChargeVehicle.getScrapValue() != null &&
		 * specChargeVehicle.getRepossessionDate() != null &&
		 * specChargeVehicle.getSalesProceed() != null &&
		 * StringUtils.isNotBlank(specChargeVehicle.getRemarks())
		 */
		) {
			// do nothing
		}
		else
			return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateAssetBased(context);

		ISpecificChargeVehicle specChargeVehicle = (ISpecificChargeVehicle) context.get(COL_OB);

		/*if (specChargeVehicle.getInsurancePolicies() == null || specChargeVehicle.getInsurancePolicies().length == 0) {
			errorMessages.add("insPolicyErr", new ActionMessage("error.collateral.insurance.policy.info.missing"));
		}*/

		// Allow passive
		/*if (StringUtils.isBlank(specChargeVehicle.getPubTransport())) {
			errorMessages.add("pubTransport", new ActionMessage("error.mandatory"));
		}*/
		// New Used Recond
		/*if (StringUtils.isBlank(specChargeVehicle.getGoodStatus())) {
			errorMessages.add("goodStatus", new ActionMessage("error.mandatory"));
		}*/
		// Type Of Unit
		/*if (StringUtils.isBlank(specChargeVehicle.getAssetType())) {
			errorMessages.add("assetType", new ActionMessage("error.mandatory"));
		}*/
		// Purchase Price
		// Andy Wong, 19 Feb 2009: validate purchase amount must > 0
		/*if (specChargeVehicle.getPurchasePrice() == null || specChargeVehicle.getPurchasePrice().getAmount() <= 0) {
			errorMessages.add("purchasePrice", new ActionMessage("error.mandatory"));
		}*/
		// Year Of Manuf.
		/*if (specChargeVehicle.getYearOfManufacture() == 0) {
			errorMessages.add("yearMfg", new ActionMessage("error.mandatory"));
		}*/
		// Model number
		/*if (StringUtils.isBlank(specChargeVehicle.getModelNo())) {
			errorMessages.add("modelNo", new ActionMessage("error.mandatory"));
		}*/
		// Make of Goods
		/*if (StringUtils.isBlank(specChargeVehicle.getBrand())) {
			errorMessages.add("brand", new ActionMessage("error.mandatory"));
		}*/

		OBTradeInInfo[] tradeInInfor = (OBTradeInInfo[]) specChargeVehicle.getTradeInInfo();
		if (tradeInInfor != null && tradeInInfor.length > 0) {
			if (tradeInInfor[0].getTradeInValue() != null && tradeInInfor[0].getTradeInDeposit() != null) {
				if (tradeInInfor[0].getTradeInDeposit().getAmount() > tradeInInfor[0].getTradeInValue().getAmount()) {
					errorMessages.add("tradeInValue", new ActionMessage("error.collateral.assetVeh.tradeInDeposit"));
				}
			}
		}
		
		
		//Not required for HDFC		
		/*
		if (isPreStpValidationRequired(context)) {
			// Andy Wong: validate CMV cannot be empty
			if (collateralCodeRequireCMV.contains(specChargeVehicle.getSourceSecuritySubType())
					&& !(specChargeVehicle.getValuationIntoCMS() != null
							&& specChargeVehicle.getValuationIntoCMS().getCMV() != null && specChargeVehicle
							.getValuationIntoCMS().getCMV().getAmount() > 0)
					&& (specChargeVehicle.getCMV() == null || specChargeVehicle.getCMV().getAmount() <= 0)) {
				errorMessages.add("pendingPerfectError", new ActionMessage("error.valuation.perfected"));
			}
		}*/

		return errorMessages;
	}
}
