package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS
 * Description: Checker reject bank entity branch param transaction operation
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: June 1, 2008
 */

public class CheckerRejectBankEntityBranchOp extends AbstractBankEntityBranchTrxOp {

    public CheckerRejectBankEntityBranchOp() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_REJECT_BANK_ENTITY_BRANCH;
    }

    /**
     * Process the transaction
     * 1.    Update the transaction record
     *
     * @param anITrxValue - ITrxValue
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IBankEntityBranchTrxValue trxValue = getBankEntityBranchTrxValue(anITrxValue);
        trxValue = updateTransaction(trxValue);
        return prepareResult(trxValue);
    }
}
