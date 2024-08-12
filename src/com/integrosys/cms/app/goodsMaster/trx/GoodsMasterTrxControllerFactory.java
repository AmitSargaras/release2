package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class GoodsMasterTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController goodsMasterReadController;

    private ITrxController goodsMasterTrxController;

	public ITrxController getGoodsMasterReadController() {
		return goodsMasterReadController;
	}

	public void setGoodsMasterReadController(ITrxController goodsMasterReadController) {
		this.goodsMasterReadController = goodsMasterReadController;
	}

	public ITrxController getGoodsMasterTrxController() {
		return goodsMasterTrxController;
	}

	public void setGoodsMasterTrxController(ITrxController goodsMasterTrxController) {
		this.goodsMasterTrxController = goodsMasterTrxController;
	}
	
	public GoodsMasterTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getGoodsMasterReadController();
        }
        return getGoodsMasterTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_GOODS_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_GOODS_MASTER_ID))) {
            return true;
        }
        return false;
    }
}
