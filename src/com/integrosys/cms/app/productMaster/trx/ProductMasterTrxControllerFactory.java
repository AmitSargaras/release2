package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class ProductMasterTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController productMasterReadController;

    private ITrxController productMasterTrxController;

	public ITrxController getProductMasterReadController() {
		return productMasterReadController;
	}

	public void setProductMasterReadController(ITrxController productMasterReadController) {
		this.productMasterReadController = productMasterReadController;
	}

	public ITrxController getProductMasterTrxController() {
		return productMasterTrxController;
	}

	public void setProductMasterTrxController(ITrxController productMasterTrxController) {
		this.productMasterTrxController = productMasterTrxController;
	}
	
	public ProductMasterTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getProductMasterReadController();
        }
        return getProductMasterTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_PRODUCT_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_PRODUCT_MASTER_ID))) {
            return true;
        }
        return false;
    }
}
