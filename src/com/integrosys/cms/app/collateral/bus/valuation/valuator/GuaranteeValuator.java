package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.GuaranteeValuationModel;

public class GuaranteeValuator implements IValuator {

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		GuaranteeValuationModel gValModel = (GuaranteeValuationModel) model;
		if (gValModel.getGuaranteeAmount() == null) {
			errorDesc.add("Guarantee amount is not defined");
			return false;
		}
		return true;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		GuaranteeValuationModel gValModel = (GuaranteeValuationModel) model;
		gValModel.setValuationDate(new Date());
		gValModel.setValOMV(gValModel.getGuaranteeAmount());
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new GuaranteeValuationModel();
	}
}
