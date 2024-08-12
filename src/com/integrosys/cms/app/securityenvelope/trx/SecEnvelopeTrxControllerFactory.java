package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn BhdgetAction
 * Date: Jan 28, 2008
 */

public class SecEnvelopeTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController secEnvelopeReadController;

    private ITrxController secEnvelopeTrxController;

    public ITrxController getSecEnvelopeReadController() {
        return secEnvelopeReadController;
    }

    public void setSecEnvelopeReadController(ITrxController secEnvelopeReadController) {
        this.secEnvelopeReadController = secEnvelopeReadController;
    }

    public ITrxController getSecEnvelopeTrxController() {
        return secEnvelopeTrxController;
    }

    public void setSecEnvelopeTrxController(ITrxController secEnvelopeTrxController) {
        this.secEnvelopeTrxController = secEnvelopeTrxController;
    }

    public SecEnvelopeTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) {
            return getSecEnvelopeReadController();
        }
        return getSecEnvelopeTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_SECENVELOPE)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_SECENVELOPE_ID))) {
            return true;
        }
        return false;
    }
}
