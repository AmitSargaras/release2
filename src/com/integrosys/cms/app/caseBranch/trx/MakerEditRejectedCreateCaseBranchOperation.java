package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchReplicationUtils;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedCreateCaseBranchOperation extends AbstractCaseBranchTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateCaseBranchOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_CASEBRANCH;
    }

    /**
    * Process the transaction
    * 1. Create Staging record
    * 2. Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        ICaseBranchTrxValue idxTrxValue = getCaseBranchTrxValue(anITrxValue);
     /*   ICaseBranch stage = idxTrxValue.getStagingCaseBranch();
        ICaseBranch replicatedCaseBranch = CaseBranchReplicationUtils.replicateCaseBranchForCreateStagingCopy(stage);
        idxTrxValue.setStagingCaseBranch(replicatedCaseBranch);*/

        ICaseBranchTrxValue trxValue = createStagingCaseBranch(idxTrxValue);
        trxValue = updateCaseBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
