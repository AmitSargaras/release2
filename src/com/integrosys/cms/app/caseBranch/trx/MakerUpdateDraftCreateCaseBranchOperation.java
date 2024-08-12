package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchReplicationUtils;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update caseBranch 
 */
public class MakerUpdateDraftCreateCaseBranchOperation extends AbstractCaseBranchTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateDraftCreateCaseBranchOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_CASEBRANCH;
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
        ICaseBranchTrxValue trxValue = getCaseBranchTrxValue(anITrxValue);
       
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
        ICaseBranchTrxValue idxTrxValue = getCaseBranchTrxValue(anITrxValue);
        ICaseBranch stage = idxTrxValue.getStagingCaseBranch();
        ICaseBranch replicatedCaseBranch = CaseBranchReplicationUtils.replicateCaseBranchForCreateStagingCopy(stage);
     //   replicatedCaseBranch.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingCaseBranch(replicatedCaseBranch);

        ICaseBranchTrxValue trxValue = createStagingCaseBranch(idxTrxValue);
        trxValue = updateCaseBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
