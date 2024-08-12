package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class TrxControllerFactory implements ITrxControllerFactory{

	private ITrxController readController;

    private ITrxController trxController;

	public ITrxController getReadController() {
		return readController;
	}

	public void setReadController(ITrxController readController) {
		this.readController = readController;
	}

	public ITrxController getTrxController() {
		return trxController;
	}

	public void setTrxController(ITrxController trxController) {
		this.trxController = trxController;
	}
	
	public TrxControllerFactory() {
		super();
	}
	
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getReadController();
        }
        return getTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_EXC_LINE_FR_STP_SRM)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_EXC_LINE_FR_STP_SRM_ID))) {
            return true;
        }
        return false;
    }

}