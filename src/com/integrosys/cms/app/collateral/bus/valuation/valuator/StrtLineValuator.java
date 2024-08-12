package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.StrtLineValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.AssetLifeProfileSingleton;

public class StrtLineValuator implements IValuator {

	private AssetLifeProfileSingleton assetLifeProfileSingleton;

	public AssetLifeProfileSingleton getAssetLifeProfileSingleton() {
		return assetLifeProfileSingleton;
	}

	public void setAssetLifeProfileSingleton(AssetLifeProfileSingleton assetLifeProfileSingleton) {
		this.assetLifeProfileSingleton = assetLifeProfileSingleton;
	}

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {

		StrtLineValuationModel valModel = (StrtLineValuationModel) model;
		boolean result = true;

		setAssetLifeDetails(valModel, errorDesc);

		if (valModel.getIsFirstTime()) {
			if (valModel.getIsNewGoodStatus()) {
				if (valModel.getPurchasePrice() == null) {
					errorDesc.add("Purchase price is not defined");
					result = false;
				}
				if (valModel.getPurchaseDate() == null) {
					errorDesc.add("Purchase date is not defined");
					result = false;
				}
			}
			else {
				if (valModel.getInitValOMV() == null) {
					errorDesc.add("Initial OMV is not defined");
					result = false;
				}
				if (valModel.getInitValOMVDate() == null) {
					errorDesc.add("Initial OMV Date is not defined");
					result = false;
				}
			}

			if (valModel.getScrapValue() == null) {
				errorDesc.add("Scrap value is not defined");
				result = false;
			}
			if (valModel.getManufactureYear() == null) {
				errorDesc.add("Year of Manufacture is not defined");
				result = false;
			}

		}
		else { // not first time valuation

			if (valModel.getInitResidualAssetLife() == null) {
				errorDesc.add("Initial Residual Asset Life is missing");
				result = false;
			}

			if (valModel.getInitResidualAssetLifeDate() == null) {
				errorDesc.add("Initial Residual Asset Life Date missing");
				result = false;
			}

			if (valModel.getDepreciableAssetValue() == null) {
				errorDesc.add("Depreciable Asset Value is missing");
				result = false;
			}

			if (valModel.getDepreciateRate() == null) {
				errorDesc.add("Depreciation Rate is missing");
				result = false;
			}
		}

		// only call calcResidualAssetLife if the conditions are satisfied,
		// otherwise method will throw null pointer
		// if exceeded lifespan, just set cmv, fsv to 0
		// if (result && (valModel.calcResidualAssetLife() < 0)) {
		// errorDesc.add("Exceeded life span of asset, no valuation to be performed");
		// result = false;
		// }
		return result;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		StrtLineValuationModel valModel = (StrtLineValuationModel) model;
		double yearSinceStart = valModel.getYearsSinceValStart();

		if (valModel.calcResidualAssetLife() < 0) {
			valModel.setValOMV(new Amount(0, valModel.getCurrencyCode()));
			valModel.setValuationDate(new Date());
			return;
		}

		if (valModel.getIsFirstTime()) {
			valModel.calcDepreciationValue();
			valModel.setDepreciableAssetValueDate(new Date());
			valModel.setInitResidualAssetLifeDate(new Date());
		}

		double curVal = valModel.getDepreciableAssetValue().doubleValue()
				- (valModel.getDepreciateRate().doubleValue() * yearSinceStart);

		valModel.setValOMV(new Amount(curVal, valModel.getCurrencyCode()));
		valModel.setValuationDate(new Date());

	}

	private void setAssetLifeDetails(StrtLineValuationModel model, List errorDesc) {
		Object totalAssetLife = assetLifeProfileSingleton.getProfile().get(model.getSecSubtype());
		if (totalAssetLife != null) {
			model.setTotalAssetLife(((Integer) totalAssetLife).intValue());
		}
		else {
			errorDesc.add("Standard life span for asset base security [" + model.getSecSubtype() + "] is not defined");
			throw new ValuationDetailIncompleteException("Standard life span for asset base security is not defined",
					errorDesc);
		}
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new StrtLineValuationModel();
	}

}
