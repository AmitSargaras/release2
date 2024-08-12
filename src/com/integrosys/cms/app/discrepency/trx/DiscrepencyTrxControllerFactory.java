package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class DiscrepencyTrxControllerFactory implements ITrxControllerFactory{

	 private ITrxController discrepencyReadController;

	 private ITrxController discrepencyTrxController;

	public ITrxController getDiscrepencyReadController() {
		return discrepencyReadController;
	}

	public void setDiscrepencyReadController(
			ITrxController discrepencyReadController) {
		this.discrepencyReadController = discrepencyReadController;
	}

	public ITrxController getDiscrepencyTrxController() {
		return discrepencyTrxController;
	}

	public void setDiscrepencyTrxController(ITrxController discrepencyTrxController) {
		this.discrepencyTrxController = discrepencyTrxController;
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param)throws TrxParameterException {
		if (isReadOperation(param.getAction()))	        
        {
            return getDiscrepencyReadController();
        }
        return getDiscrepencyTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_DISCREPENCY)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_DISCREPENCY_ID))) {
            return true;
        }
        return false;
    }
}
