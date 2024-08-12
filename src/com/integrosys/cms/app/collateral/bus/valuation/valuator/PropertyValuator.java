package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.PropertyValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.PropertyProfileSingleton;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters;

public class PropertyValuator implements IValuator {

	/**
	 * @return the propertyProfileSingleton
	 */
	public PropertyProfileSingleton getPropertyProfileSingleton() {
		return propertyProfileSingleton;
	}

	/**
	 * @param propertyProfileSingleton the propertyProfileSingleton to set
	 */
	public void setPropertyProfileSingleton(PropertyProfileSingleton propertyProfileSingleton) {
		this.propertyProfileSingleton = propertyProfileSingleton;
	}

	private PropertyProfileSingleton propertyProfileSingleton;

	public static final String OMV_INCREASE = "INC";

	public static final String OMV_DECREASE = "RED";

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		PropertyValuationModel pValModel = (PropertyValuationModel) model;
		if (pValModel.getStateCode() == null) {
			errorDesc.add("state code is not defined");
			return false;
		}

		if (pValModel.getPropertyCompletionStatus() == null) {
			errorDesc.add("property completion status is not define");
			return false;
		}
		//else if (!(pValModel.getPropertyCompletionStatus().equals(ICMSConstant.PROP_COMPLETE_STATUS_W_COF_ISSUANCE) || pValModel
		//		.getPropertyCompletionStatus().equals(ICMSConstant.PROP_COMPLETE_STATUS_WO_COF_ISSUANCE))) {
        else if (!pValModel.getPropertyCompletionStatus().equals(ICMSConstant.PROP_COMPLETE_STATUS_W_COF_ISSUANCE)) {
			//errorDesc.add("property completion status is not Completed with CF or Completed without CF");
            errorDesc.add("property completion status is not Completed with CF");
			return false;
		}

		return true;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		PropertyValuationModel pValModel = (PropertyValuationModel) model;
		Amount currentOmv = pValModel.getCurrentOMV();
		List profiles = propertyProfileSingleton.getProfiles();
		OBPropertyParameters param = (OBPropertyParameters) (matchParam(pValModel, profiles));
		if (param == null) {
			List errorList = new ArrayList();
			errorList.add("no matching valuation profiles are found");
			throw new ValuationDetailIncompleteException("no matching property parameter profiles are found", errorList);
		}
		if (OMV_INCREASE.equals(param.getOmvType())) {
			DefaultLogger.debug(this, "***PROPERTY VALUATION CURRENT OMV: " + currentOmv.getAmount());
			DefaultLogger.debug(this, "***PROPERTY VALUATION INCREASE PERCENTAGE: " + param.getVariationOMV());
			double newAmt = currentOmv.getAmount() * (1.0 + param.getVariationOMV() / 100.0);
			pValModel.setValuationDate(new Date());
			pValModel.setValOMV(new Amount(newAmt, currentOmv.getCurrencyCode()));
		}
		else if (OMV_DECREASE.equals(param.getOmvType())) {
			DefaultLogger.debug(this, "***PROPERTY VALUATION CURRENT OMV: " + currentOmv.getAmount());
			DefaultLogger.debug(this, "***PROPERTY VALUATION DECREASE PERCENTAGE: " + param.getVariationOMV());
			double newAmt = currentOmv.getAmount() * (1.0 - param.getVariationOMV() / 100.0);
			pValModel.setValuationDate(new Date());
			pValModel.setValOMV(new Amount(newAmt, currentOmv.getCurrencyCode()));
		}
		else {
			DefaultLogger.debug(this, "***PROPERTY -- UNKNOW OmvType : " + param.getOmvType());
		}
	}

	private OBPropertyParameters matchParam(PropertyValuationModel pValModel, List profiles) {

		for (int i = 0; i < profiles.size(); i++) {
			OBPropertyParameters nextParam = (OBPropertyParameters) (profiles.get(i));
			if ((nextParam.getCollateralSubType() == null)
					|| (nextParam.getCollateralSubType().trim().indexOf(pValModel.getSecSubtype()) < 0)) {
				continue;
			}
			if ((nextParam.getCountryCode() == null)
					|| (nextParam.getCountryCode().trim().indexOf(pValModel.getSecurityCountry()) < 0)) {
				continue;
			}
			if ((nextParam.getStateCode() != null)
					&& (nextParam.getStateCode().trim().indexOf(pValModel.getStateCode()) < 0)) {
				continue;
			}
			if ((nextParam.getDistrictCode() != null) && !"".equals(nextParam.getDistrictCode().trim())
					&& (nextParam.getDistrictCode().trim().indexOf(pValModel.getDistrictCode()) < 0)) {
				continue;
			}

			if ((nextParam.getMukimCode() != null)
					&& (nextParam.getMukimCode().trim().indexOf(pValModel.getMukimCode()) < 0)) {
				continue;
			}

			if ((nextParam.getPostcode() != null)
					&& !"".equals(nextParam.getPostcode().trim())
					&& !nextParam.getPostcode().trim().equals(
							(pValModel.getPostCode() == null ? null : pValModel.getPostCode().trim()))) {
				continue;
			}
			if ((nextParam.getMinimumCurrentOmv() >= 0)
					&& (nextParam.getMinimumCurrentOmv() > pValModel.getCurrentOMV().getAmount())) {
				continue;
			}

			boolean matchingLandFrom = ((nextParam.getLandAreaValueFrom() < 0) || ((pValModel.getLandArea() != null) && (ValuationUtil
					.convertAreaToSqrtMeter(pValModel.getLandArea().getFrom(), pValModel.getLandArea().getFromUnit()) >= ValuationUtil
					.convertAreaToSqrtMeter(nextParam.getLandAreaValueFrom(), nextParam.getLandAreaUnitFrom()))));
			boolean matchingLandTo = ((nextParam.getLandAreaValueTo() < 0) || ((pValModel.getLandArea() != null) && (ValuationUtil
					.convertAreaToSqrtMeter(pValModel.getLandArea().getFrom(), pValModel.getLandArea().getFromUnit()) <= ValuationUtil
					.convertAreaToSqrtMeter(nextParam.getLandAreaValueTo(), nextParam.getLandAreaUnitTo()))));

			boolean matchingBuildFrom = ((nextParam.getBuildupAreaValueFrom() < 0) || ((pValModel.getBuildupArea() != null) && (ValuationUtil
					.convertAreaToSqrtMeter(pValModel.getBuildupArea().getFrom(), pValModel.getBuildupArea()
							.getFromUnit()) >= ValuationUtil.convertAreaToSqrtMeter(
					nextParam.getBuildupAreaValueFrom(), nextParam.getBuildupAreaUnitFrom()))));
			boolean matchingBuildTo = ((nextParam.getBuildupAreaValueTo() < 0) || ((pValModel.getBuildupArea() != null) && (ValuationUtil
					.convertAreaToSqrtMeter(pValModel.getBuildupArea().getFrom(), pValModel.getBuildupArea()
							.getFromUnit()) <= ValuationUtil.convertAreaToSqrtMeter(nextParam.getBuildupAreaValueTo(),
					nextParam.getBuildupAreaUnitTo()))));
			if (matchingLandFrom && matchingLandTo && matchingBuildFrom && matchingBuildTo) {
				return nextParam;
			}
		}
		return null;
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new PropertyValuationModel();
	}

}
