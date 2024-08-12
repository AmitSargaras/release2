package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateFCCBranchOperation extends AbstractFCCBranchTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateFCCBranchOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_FCCBRANCH;
        }

        /**
        * Process the transaction
        * 1.    Create Staging record
        * 2.    Update the transaction record
        * @param anITrxValue - ITrxValue
        * @return ITrxResult - the transaction result
        * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
        */
        public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
        {
            IFCCBranchTrxValue idxTrxValue = getFCCBranchTrxValue(anITrxValue);
            IFCCBranch stage = idxTrxValue.getStagingFCCBranch();
            IFCCBranch replicatedFCCBranch= new OBFCCBranch();
            replicatedFCCBranch.setId(stage.getId());
            replicatedFCCBranch.setBranchCode(stage.getBranchCode());
            replicatedFCCBranch.setBranchName(stage.getBranchName());
            replicatedFCCBranch.setDeprecated(stage.getDeprecated());
            replicatedFCCBranch.setStatus(stage.getStatus());
            replicatedFCCBranch.setVersionTime(stage.getVersionTime());
            replicatedFCCBranch.setAliasBranchCode(stage.getAliasBranchCode());
            replicatedFCCBranch.setCreatedBy(stage.getCreatedBy());
            replicatedFCCBranch.setCreatedOn(stage.getCreatedOn());
            replicatedFCCBranch.setLastUpdatedBy(stage.getLastUpdatedBy());
            replicatedFCCBranch.setLastUpdatedOn(stage.getLastUpdatedOn());
            idxTrxValue.setStagingFCCBranch(replicatedFCCBranch);

            IFCCBranchTrxValue trxValue = createStagingFCCBranch(idxTrxValue);
            trxValue = updateFCCBranchTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
