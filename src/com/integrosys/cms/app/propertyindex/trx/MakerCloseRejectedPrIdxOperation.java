package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */

public class MakerCloseRejectedPrIdxOperation extends AbstractPropertyIdxTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_PRIDX;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseRejectedPrIdxOperation()
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
        IPropertyIdxTrxValue trxValue = super.getPropertyIdxTrxValue (anITrxValue);
        trxValue = updatePropertyIdxTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
