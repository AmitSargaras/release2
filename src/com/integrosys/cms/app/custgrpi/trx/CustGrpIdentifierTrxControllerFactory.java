package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;


public class CustGrpIdentifierTrxControllerFactory implements ITrxControllerFactory {

    /**
     * Default Constructor
     */
    public CustGrpIdentifierTrxControllerFactory() {
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
            Debug("read operation = CustGrpIdentifierReadController ");
            return new CustGrpIdentifierReadController();
        }

       return new CustGrpIdentifierTrxController();

    }


    private boolean isReadOperation(String action) {
        return (ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID.equals(action) ||
                ICMSConstant.ACTION_READ_BY_GROUP_ID.equals(action));
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"CustGrpIdentifierTrxControllerFactory = " + msg);
          }

}
