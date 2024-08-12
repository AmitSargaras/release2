package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: June 1, 2008
 */
public class BankEntityBranchTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController bkEntityBrchPrmReadController;

    private ITrxController bkEntityBrchPrmTrxController;

    public ITrxController getBkEntityBrchPrmReadController() {
        return bkEntityBrchPrmReadController;
    }

    public void setBkEntityBrchPrmReadController(ITrxController bkEntityBrchPrmReadController) {
        this.bkEntityBrchPrmReadController = bkEntityBrchPrmReadController;
    }

    public ITrxController getBkEntityBrchPrmTrxController() {
        return bkEntityBrchPrmTrxController;
    }

    public void setBkEntityBrchPrmTrxController(ITrxController bkEntityBrchPrmTrxController) {
        this.bkEntityBrchPrmTrxController = bkEntityBrchPrmTrxController;
    }

    public BankEntityBranchTrxControllerFactory()
    {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {

        if (isReadOperation (param.getAction())) {
//            return new BankEntityBranchReadController();
            return getBkEntityBrchPrmReadController();
        }
//        return new BankEntityBranchTrxController();
        return getBkEntityBrchPrmTrxController();
    }

    /**
     * Helper method to check if the action requires a read operation or not.
     *
     * @param anAction of type String
     * @return boolean true if requires a read operation, otherwise false
     */
    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_BANK_ENTITY_BRANCH) ||
            anAction.equals (ICMSConstant.ACTION_READ_BANK_ENTITY_BRANCH_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }
}
