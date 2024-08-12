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
 * Date: Feb 08, 2010
 */

public class MakerCloseRejectedSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_SECENV;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseRejectedSecEnvelopeOperation()
    {
        operationName = DEFAULT_OPERATION_NAME;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
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
        ISecEnvelopeTrxValue trxValue = super.getSecEnvelopeTrxValue (anITrxValue);
        trxValue = updateSecEnvelopeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
