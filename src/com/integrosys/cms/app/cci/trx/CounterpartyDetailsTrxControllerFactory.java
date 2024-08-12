package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;


public class CounterpartyDetailsTrxControllerFactory implements ITrxControllerFactory {

    /**
     * Default Constructor
     */
    public CounterpartyDetailsTrxControllerFactory() {
        super();
    }


    /**
     * Returns an ITrxController given the ITrxValue and ITrxParameter objects
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return ITrxController
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException
     *          if any error occurs
     */
    public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {

        if (isReadOperation(param.getAction())) {
            return new CCICounterpartyDetailsReadController();
        }

       return new CounterpartyDetailsTrxController();

    }


    private boolean isReadOperation(String action) {
        return (ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID.equals(action) ||
                ICMSConstant.ACTION_READ_CCIN_BY_GROUP_CCINO.equals(action));
    }
}
