package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class RiskTypeTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController riskTypeReadController;

    private ITrxController riskTypeTrxController;

	public ITrxController getRiskTypeReadController() {
		return riskTypeReadController;
	}

	public void setRiskTypeReadController(ITrxController valuationAmountAndRatingReadController) {
		this.riskTypeReadController = valuationAmountAndRatingReadController;
	}

	public ITrxController getRiskTypeTrxController() {
		return riskTypeTrxController;
	}

	public void setRiskTypeTrxController(ITrxController valuationAmountAndRatingTrxController) {
		this.riskTypeTrxController = valuationAmountAndRatingTrxController;
	}
	
	public RiskTypeTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getRiskTypeReadController();
        }
        return getRiskTypeTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_RISK_TYPE)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_RISK_TYPE_ID))) {
            return true;
        }
        return false;
    }
}
