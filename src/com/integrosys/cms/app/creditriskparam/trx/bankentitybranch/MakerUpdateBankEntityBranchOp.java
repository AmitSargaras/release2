package com.integrosys.cms.app.creditriskparam.trx.bankentitybranch;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Title: CLIMS 
 * Description: Maker update bank entity branch param transaction operation
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: June 1, 2008
 */
public class MakerUpdateBankEntityBranchOp extends AbstractBankEntityBranchTrxOp {
/**
    * Defaulc Constructor
    */
    public MakerUpdateBankEntityBranchOp()
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
        return ICMSConstant.ACTION_MAKER_UPDATE_BANK_ENTITY_BRANCH;
    }

    /**
    * Process the transaction
    * 1.	Create the staging data
    * 2.	Update the transaction record
    * @param anITrxValue of ITrxValue type
    * @return ITrxResult - the transaction result
    * @throws TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException
    {
        try {
            IBankEntityBranchTrxValue trxValue = super.getBankEntityBranchTrxValue(value);
            trxValue = super.createStagingBankEntityBranch(trxValue);

            if (trxValue.getStatus().equals (ICMSConstant.STATE_ND))
            {
                trxValue = super.createTransaction (trxValue);
            }
            else
                trxValue = super.updateTransaction(trxValue);

            return super.prepareResult(trxValue);
        }
        catch(TransactionException ex)
        {
            throw new TrxOperationException(ex);
        }
    }
}
