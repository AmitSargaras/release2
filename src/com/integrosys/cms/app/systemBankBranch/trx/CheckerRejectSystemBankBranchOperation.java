package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Checker reject Operation to reject update made by maker
 */

public class CheckerRejectSystemBankBranchOperation extends AbstractSystemBankBranchTrxOperation{

    public CheckerRejectSystemBankBranchOperation()
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
        return ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK_BRANCH;
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
        ISystemBankBranchTrxValue trxValue = super.getSystemBankBranchTrxValue(anITrxValue);
        trxValue = super.updateSystemBankBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
