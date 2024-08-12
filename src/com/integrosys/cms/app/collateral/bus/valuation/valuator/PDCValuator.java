package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.PDCValuationModel;
import com.integrosys.cms.app.commodity.common.AmountConversion;

public class PDCValuator implements IValuator {

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		return true;
	}

	public void performValuation(IValuationModel model) throws ValuationException {
		PDCValuationModel pdcValModel = (PDCValuationModel) model;
		List chequeList = pdcValModel.getPostDatedChequeList();
		Amount zeroAmt = new Amount(0, pdcValModel.getSecCurrency());
		if (chequeList != null) {
			for (int i = 0; i < chequeList.size(); i++) {
				IPostDatedCheque curCheque = (IPostDatedCheque) (chequeList.get(i));
				Amount newAmt = AmountConversion.getConversionAmount(curCheque.getChequeAmount(), pdcValModel
						.getSecCurrency());
				try {
					zeroAmt.addToThis(newAmt);
				}
				catch (ChainedException e) {
					throw new ValuationDetailIncompleteException("not able to add new amount [" + newAmt
							+ "] to zero amount for collateral id [" + model.getCollateralId() + "]", e);
				}
			}
		}
		pdcValModel.setValuationDate(new Date());
		pdcValModel.setValOMV(zeroAmt);
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new PDCValuationModel();
	}

}
