package com.integrosys.cms.app.feed.trx.bond;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author govind.sahu
 * Checker reject Operation to reject update made by maker
 */

public class CheckerRejectInsertMasterOperation extends AbstractBondTrxOperation{

    public CheckerRejectInsertMasterOperation()
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
        return ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER;
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
    	IBondFeedGroupTrxValue trxValue = super.getBondFeedGroupTrxValue(anITrxValue);
        trxValue = super.updateMasterInsertTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
