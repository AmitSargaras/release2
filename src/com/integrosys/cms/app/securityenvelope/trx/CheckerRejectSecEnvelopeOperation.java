package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Feb 04, 2010
 */

public class CheckerRejectSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation{

    public CheckerRejectSecEnvelopeOperation()
    {
        super();
    }

    /**
    * Get the operation name of the current operation
    *
    * @return String - the operation name of the current operation
    */
    public String getOperationName()
    {
        return ICMSConstant.ACTION_CHECKER_REJECT_SECENV;
    }

    /**
    * Process the transaction
    * 1.    Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        ISecEnvelopeTrxValue trxValue = super.getSecEnvelopeTrxValue(anITrxValue);
        trxValue = super.updateSecEnvelopeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
