package com.integrosys.cms.app.fccBranch.trx;

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
public class MakerEditRejectedCreateFCCBranchOperation extends AbstractFCCBranchTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateFCCBranchOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_FCCBRANCH;
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
        IFCCBranchTrxValue idxTrxValue = getFCCBranchTrxValue(anITrxValue);
     /*   IFCCBranch stage = idxTrxValue.getStagingFCCBranch();
        IFCCBranch replicatedFCCBranch = FCCBranchReplicationUtils.replicateFCCBranchForCreateStagingCopy(stage);
        idxTrxValue.setStagingFCCBranch(replicatedFCCBranch);*/

        IFCCBranchTrxValue trxValue = createStagingFCCBranch(idxTrxValue);
        trxValue = updateFCCBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
