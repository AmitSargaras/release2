package com.integrosys.cms.app.collateral.bus.valuation.valuator;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.IValuator;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationDetailIncompleteException;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.model.CashValuationModel;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;

public class CashValuator implements IValuator {

	public boolean checkCompleteForVal(IValuationModel model, List errorDesc) {
		return true;
	}

	public void performValuation(IValuationModel model) throws ValuationException {

		CashValuationModel cValModel = (CashValuationModel) model;
		Amount zeroAmt = new Amount(0, cValModel.getSecCurrency());
		List depositList = cValModel.getDepositList();
		if (depositList != null) {
			for (int i = 0; i < depositList.size(); i++) {
				ICashDeposit curDeposit = (ICashDeposit) (depositList.get(i));
				try {
					Amount newAmt = AmountConversion.getConversionAmount(curDeposit.getDepositAmount(), cValModel
							.getSecCurrency());
					try {
						zeroAmt.addToThis(newAmt);
					}
					catch (ChainedException e) {
						throw new ValuationDetailIncompleteException("not able to add new amount [" + newAmt
								+ "] to zero amount for collateral id [" + model.getCollateralId() + "]", e);
					}
				}
				catch (AmountConversionException e) {
					throw new ValuationDetailIncompleteException("not able to convert the amount", e);
				}

			}
		}
		cValModel.setValuationDate(new Date());
		cValModel.setValOMV(zeroAmt);
	}

	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model) {
	}

	public IValuationModel createValuationModelInstance() {
		return new CashValuationModel();
	}
}
