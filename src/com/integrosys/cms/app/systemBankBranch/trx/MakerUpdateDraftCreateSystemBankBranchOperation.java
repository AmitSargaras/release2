package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchReplicationUtils;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update system 
 */
public class MakerUpdateDraftCreateSystemBankBranchOperation extends AbstractSystemBankBranchTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateDraftCreateSystemBankBranchOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_SYSTEM_BANK_BRANCH;
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
        ISystemBankBranchTrxValue trxValue = getSystemBankBranchTrxValue(anITrxValue);
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
        ISystemBankBranchTrxValue idxTrxValue = getSystemBankBranchTrxValue(anITrxValue);
        ISystemBankBranch stage = idxTrxValue.getStagingSystemBankBranch();
        ISystemBankBranch replicatedSystemBankBranch = SystemBankBranchReplicationUtils.replicateSystemBankBranchForCreateStagingCopy(stage);
        idxTrxValue.setStagingSystemBankBranch(replicatedSystemBankBranch);
        ISystemBankBranchTrxValue trxValue = createStagingSystemBankBranch(idxTrxValue);
        trxValue = updateSystemBankBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
