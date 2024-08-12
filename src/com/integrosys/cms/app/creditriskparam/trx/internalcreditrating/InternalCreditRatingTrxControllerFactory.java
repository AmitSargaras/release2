/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author priya
 *
 */
public class InternalCreditRatingTrxControllerFactory implements ITrxControllerFactory {

    private ITrxController intCrRtReadController;

    private ITrxController intCrRtTrxController;

    public ITrxController getIntCrRtReadController() {
        return intCrRtReadController;
    }

    public void setIntCrRtReadController(ITrxController intCrRtReadController) {
        this.intCrRtReadController = intCrRtReadController;
    }

    public ITrxController getIntCrRtTrxController() {
        return intCrRtTrxController;
    }

    public void setIntCrRtTrxController(ITrxController intCrRtTrxController) {
        this.intCrRtTrxController = intCrRtTrxController;
    }

    public ITrxController getController (ITrxValue value, ITrxParameter param) throws TrxParameterException
    {
        if (isReadOperation (param.getAction())) {
//            return new InternalCreditRatingReadController();
            return getIntCrRtReadController();
        }
//        return new InternalCreditRatingTrxController();
        return getIntCrRtTrxController();
    }

    /**
     * Helper method to check if the action requires a read operation or not.
     *
     * @param anAction of type String
     * @return boolean true if requires a read operation, otherwise false
     */
    private boolean isReadOperation (String anAction)
    {
        if (anAction.equals (ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING) ||
            anAction.equals (ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING_BY_TRXID)) {
            return true;
        }
        else {
            return false;
        }
    }

}
