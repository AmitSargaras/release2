package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class CollateralNewMasterTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController collateralNewMasterReadController;

    private ITrxController collateralNewMasterTrxController;

   

    public ITrxController getCollateralNewMasterReadController() {
		return collateralNewMasterReadController;
	}

	public void setCollateralNewMasterReadController(
			ITrxController collateralNewMasterReadController) {
		this.collateralNewMasterReadController = collateralNewMasterReadController;
	}

	public ITrxController getCollateralNewMasterTrxController() {
		return collateralNewMasterTrxController;
	}

	public void setCollateralNewMasterTrxController(
			ITrxController collateralNewMasterTrxController) {
		this.collateralNewMasterTrxController = collateralNewMasterTrxController;
	}

	public CollateralNewMasterTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getCollateralNewMasterReadController();
        }
        return getCollateralNewMasterTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_NEW_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_COLLATERAL_NEW_MASTER_ID))) {
            return true;
        }
        return false;
    }
}
