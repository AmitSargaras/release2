package com.integrosys.cms.ui.collateral.assetbased.assetspecplant;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBTradeInInfo;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.ISpecificChargePlant;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.List;
import java.util.Map;

public class AssetSpecPlantStpValidator extends AbstractAssetBasedStpValidator {
	private List collateralCodeRequireCMV;

	public List getCollateralCodeRequireCMV() {
		return collateralCodeRequireCMV;
	}

	public void setCollateralCodeRequireCMV(List collateralCodeRequireCMV) {
		this.collateralCodeRequireCMV = collateralCodeRequireCMV;
	}

	public boolean validate(Map context) {
		// ISpecificChargePlant specChargePlant = (ISpecificChargePlant)
		// collateral;
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
		if (validateAndAccumulate(context).size() <= 0
		/*
		 * StringUtils.isNotBlank(specChargePlant.getBrand()) &&
		 * StringUtils.isNotBlank(specChargePlant.getModelNo()) &&
		 * specChargePlant.getPurchaseDate() != null &&
		 * specChargePlant.getScrapValue() != null &&
		 * specChargePlant.getRepossessionDate() != null &&
		 * specChargePlant.getSalesProceed() != null &&
		 * specChargePlant.getChattelSoldDate() != null &&
		 * specChargePlant.getDptradein() != null &&
		 * specChargePlant.getTradeinValue() != null &&
		 * StringUtils.isNotBlank(specChargePlant.getRlSerialNumber()) &&
		 * specChargePlant.getDpcash() != null &&
		 * StringUtils.isNotBlank(specChargePlant.getPubTransport()) &&
		 * StringUtils.isNotBlank(specChargePlant.getRegistrationNo()) &&
		 * StringUtils.isNotBlank(specChargePlant.getRemarks())
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

		ISpecificChargePlant specChargePlant = (ISpecificChargePlant) context.get(COL_OB);

		/*if (specChargePlant.getInsurancePolicies() == null || specChargePlant.getInsurancePolicies().length == 0) {
			errorMessages.add("insPolicyErr", new ActionMessage("error.collateral.insurance.policy.info.missing"));
		}*/

		// Allow passive
		/*if (StringUtils.isBlank(specChargePlant.getPubTransport())) {
			errorMessages.add("pubTransport", new ActionMessage("error.mandatory"));
		}*/
		// New Used Recond
		/*if (StringUtils.isBlank(specChargePlant.getGoodStatus())) {
			errorMessages.add("goodStatus", new ActionMessage("error.mandatory"));
		}*/
		/*if (specChargePlant.getInvoiceDate()== null) {
			errorMessages.add("invoiceDate", new ActionMessage("error.mandatory"));
		}*/
		// Type Of Unit
		/*if (StringUtils.isBlank(specChargePlant.getAssetType())) {
			errorMessages.add("assetType", new ActionMessage("error.mandatory"));
		}*/
		// Purchase Price
		/*if (specChargePlant.getPurchasePrice() == null) {
			errorMessages.add("purchasePrice", new ActionMessage("error.mandatory"));
		}*/
		// Year Of Manuf.
		/*if (specChargePlant.getYearOfManufacture() == 0) {
			errorMessages.add("yearMfg", new ActionMessage("error.mandatory"));
		}*/
		// Model number
		/*if (StringUtils.isBlank(specChargePlant.getModelNo())) {
			errorMessages.add("modelNo", new ActionMessage("error.mandatory"));
		}*/
		// Make of Goods
		/*if (StringUtils.isBlank(specChargePlant.getBrand())) {
			errorMessages.add("brand", new ActionMessage("error.mandatory"));
		}*/

		OBTradeInInfo[] tradeInInfor = (OBTradeInInfo[]) specChargePlant.getTradeInInfo();
		if (tradeInInfor != null && tradeInInfor.length > 0) {
			if (tradeInInfor[0].getTradeInValue() != null && tradeInInfor[0].getTradeInDeposit() != null) {
				if (tradeInInfor[0].getTradeInDeposit().getAmount() > tradeInInfor[0].getTradeInValue().getAmount()) {
					errorMessages.add("tradeInValue", new ActionMessage("error.collateral.assetVeh.tradeInDeposit"));
				}
			}
		}
//Not required for HDFC
	/*	if (isPreStpValidationRequired(context)) {
			// Andy Wong: validate CMV cannot be empty
			if (collateralCodeRequireCMV.contains(specChargePlant.getSourceSecuritySubType())
					&& !(specChargePlant.getValuationIntoCMS() != null
							&& specChargePlant.getValuationIntoCMS().getCMV() != null && specChargePlant
							.getValuationIntoCMS().getCMV().getAmount() > 0)
					&& (specChargePlant.getCMV() == null || specChargePlant.getCMV().getAmount() <= 0)) {
				errorMessages.add("pendingPerfectError", new ActionMessage("error.valuation.perfected"));
			}
		}*/

		return errorMessages;
	}
}
