package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Trx controller factory
 */
public class SystemBankTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController systemBankReadController;

    private ITrxController systemBankTrxController;

    public ITrxController getSystemBankReadController() {
        return systemBankReadController;
    }

    public void setSystemBankReadController(ITrxController systemBankReadController) {
        this.systemBankReadController = systemBankReadController;
    }

    public ITrxController getSystemBankTrxController() {
        return systemBankTrxController;
    }

    public void setSystemBankTrxController(ITrxController systemBankTrxController) {
        this.systemBankTrxController = systemBankTrxController;
    }

    public SystemBankTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) 
        
        {
            return getSystemBankReadController();
        }
        return getSystemBankTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_SYSTEM_BANK)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_SYSTEM_BANK_ID))) {
            return true;
        }
        return false;
    }
}
