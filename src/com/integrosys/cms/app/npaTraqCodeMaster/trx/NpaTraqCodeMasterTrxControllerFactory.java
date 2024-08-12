package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class NpaTraqCodeMasterTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController npaTraqCodeMasterReadController;
    private ITrxController npaTraqCodeMasterTrxController;
    
	public ITrxController getNpaTraqCodeMasterReadController() {
		return npaTraqCodeMasterReadController;
	}

	public void setNpaTraqCodeMasterReadController(ITrxController npaTraqCodeMasterReadController) {
		this.npaTraqCodeMasterReadController = npaTraqCodeMasterReadController;
	}

	public ITrxController getNpaTraqCodeMasterTrxController() {
		return npaTraqCodeMasterTrxController;
	}

	public void setNpaTraqCodeMasterTrxController(ITrxController npaTraqCodeMasterTrxController) {
		this.npaTraqCodeMasterTrxController = npaTraqCodeMasterTrxController;
	}

	public NpaTraqCodeMasterTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getNpaTraqCodeMasterReadController();
        }
        return getNpaTraqCodeMasterTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_NPA_TRAQ_CODE_MASTER)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_NPA_TRAQ_CODE_MASTER_ID))) {
            return true;
        }
        return false;
    }
}
