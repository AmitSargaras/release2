package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchReplicationUtils;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update FCCBranch 
 */
public class MakerUpdateFCCBranchOperation extends AbstractFCCBranchTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateFCCBranchOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_FCCBRANCH;
    }

    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IFCCBranchTrxValue trxValue = getFCCBranchTrxValue(anITrxValue);
       
            return trxValue;
        }
        
    
    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IFCCBranchTrxValue idxTrxValue = getFCCBranchTrxValue(anITrxValue);
        IFCCBranch stage = idxTrxValue.getStagingFCCBranch();
        IFCCBranch replicatedFCCBranch = FCCBranchReplicationUtils.replicateFCCBranchForCreateStagingCopy(stage);
     //   replicatedFCCBranch.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingFCCBranch(replicatedFCCBranch);

        IFCCBranchTrxValue trxValue = createStagingFCCBranch(idxTrxValue);
        trxValue = updateFCCBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
