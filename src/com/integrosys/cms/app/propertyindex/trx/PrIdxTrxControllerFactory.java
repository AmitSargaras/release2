package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */
public class PrIdxTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController prIdxReadController;

    private ITrxController prIdxTrxController;

    public ITrxController getPrIdxReadController() {
        return prIdxReadController;
    }

    public void setPrIdxReadController(ITrxController prIdxReadController) {
        this.prIdxReadController = prIdxReadController;
    }

    public ITrxController getPrIdxTrxController() {
        return prIdxTrxController;
    }

    public void setPrIdxTrxController(ITrxController prIdxTrxController) {
        this.prIdxTrxController = prIdxTrxController;
    }

    public PrIdxTrxControllerFactory() {
        super();
    }

    public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
        if (isReadOperation(param.getAction())) {
            return getPrIdxReadController();
        }
        return getPrIdxTrxController();
    }

    private boolean isReadOperation(String anAction) {
        if ((anAction.equals(ICMSConstant.ACTION_READ_PRIDX)) ||
                (anAction.equals(ICMSConstant.ACTION_READ_PRIDX_ID))) {
            return true;
        }
        return false;
    }
}
