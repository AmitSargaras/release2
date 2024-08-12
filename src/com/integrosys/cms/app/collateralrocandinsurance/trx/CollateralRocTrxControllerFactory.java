package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CollateralRocTrxControllerFactory implements ITrxControllerFactory {

	private ITrxController collateralRocReadController;

    private ITrxController collateralRocTrxController;

	public ITrxController getCollateralRocReadController() {
		return collateralRocReadController;
	}

	public void setCollateralRocReadController(ITrxController collateralRocReadController) {
		this.collateralRocReadController = collateralRocReadController;
	}

	public ITrxController getCollateralRocTrxController() {
		return collateralRocTrxController;
	}

	public void setCollateralRocTrxController(ITrxController collateralRocTrxController) {
		this.collateralRocTrxController = collateralRocTrxController;
	}
	
	public CollateralRocTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getCollateralRocReadController();
        }
        return getCollateralRocTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_ROC)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_ROC_ID))) {
            return true;
        }
        return false;
    }
}
