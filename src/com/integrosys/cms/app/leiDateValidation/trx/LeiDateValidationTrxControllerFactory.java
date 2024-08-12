package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class LeiDateValidationTrxControllerFactory implements ITrxControllerFactory{

	private ITrxController leiDateValidationReadController;

    private ITrxController leiDateValidationTrxController;

	public ITrxController getLeiDateValidationReadController() {
		return leiDateValidationReadController;
	}

	public void setLeiDateValidationReadController(ITrxController leiDateValidationReadController) {
		this.leiDateValidationReadController = leiDateValidationReadController;
	}

	public ITrxController getLeiDateValidationTrxController() {
		return leiDateValidationTrxController;
	}

	public void setLeiDateValidationTrxController(ITrxController leiDateValidationTrxController) {
		this.leiDateValidationTrxController = leiDateValidationTrxController;
	}
	
	public LeiDateValidationTrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getLeiDateValidationReadController();
        }
        return getLeiDateValidationTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_LEI_DATE_VALIDATION)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_LEI_DATE_VALIDATION_ID))) {
            return true;
        }
        return false;
    }
}
