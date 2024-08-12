package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class ValuationAmountAndRatingTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController valuationAmountAndRatingReadController;

    private ITrxController valuationAmountAndRatingTrxController;

	public ITrxController getValuationAmountAndRatingReadController() {
		return valuationAmountAndRatingReadController;
	}

	public void setValuationAmountAndRatingReadController(ITrxController valuationAmountAndRatingReadController) {
		this.valuationAmountAndRatingReadController = valuationAmountAndRatingReadController;
	}

	public ITrxController getValuationAmountAndRatingTrxController() {
		return valuationAmountAndRatingTrxController;
	}

	public void setValuationAmountAndRatingTrxController(ITrxController valuationAmountAndRatingTrxController) {
		this.valuationAmountAndRatingTrxController = valuationAmountAndRatingTrxController;
	}
	
	public ValuationAmountAndRatingTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getValuationAmountAndRatingReadController();
        }
        return getValuationAmountAndRatingTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_VALUATION_AMOUNT_AND_RATING)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_VALUATION_AMOUNT_AND_RATING_ID))) {
            return true;
        }
        return false;
    }
}
