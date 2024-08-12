package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class UdfTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController udfReadController;

    private ITrxController udfTrxController;

	public ITrxController getUdfReadController() {
		return udfReadController;
	}

	public void setUdfReadController(ITrxController udfReadController) {
		this.udfReadController = udfReadController;
	}

	public ITrxController getUdfTrxController() {
		return udfTrxController;
	}

	public void setUdfTrxController(ITrxController udfTrxController) {
		this.udfTrxController = udfTrxController;
	}
	
	public UdfTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getUdfReadController();
        }
        return getUdfTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_UDF)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_UDF_ID))) {
            return true;
        }
        return false;
    }
}
