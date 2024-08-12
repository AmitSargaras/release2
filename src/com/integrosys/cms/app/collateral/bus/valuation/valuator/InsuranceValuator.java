package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.InsuranceValuationModel;
import com.integrosys.cms.app.collateral.bus.ICollateral;

import java.util.Date;
import java.util.List;

public class InsuranceValuator implements IValuator {

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		InsuranceValuationModel iValModel = (InsuranceValuationModel) model;
		if (iValModel.getInsuredAmount() == null) {
			errorDesc.add("Insurance amount is not defined");
			return false;
		}
		return true;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		InsuranceValuationModel iValModel = (InsuranceValuationModel) model;
		iValModel.setValuationDate(new Date());
		iValModel.setValOMV(iValModel.getInsuredAmount());
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new InsuranceValuationModel();
	}

}
