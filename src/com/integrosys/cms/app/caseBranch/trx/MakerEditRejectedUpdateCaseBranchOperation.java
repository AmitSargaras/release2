package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateCaseBranchOperation extends AbstractCaseBranchTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateCaseBranchOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_CASEBRANCH;
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
            ICaseBranchTrxValue idxTrxValue = getCaseBranchTrxValue(anITrxValue);
            ICaseBranch stage = idxTrxValue.getStagingCaseBranch();
            ICaseBranch replicatedCaseBranch= new OBCaseBranch();
            replicatedCaseBranch.setId(stage.getId());
            replicatedCaseBranch.setBranchCode(stage.getBranchCode());
            replicatedCaseBranch.setCoordinator1(stage.getCoordinator1());
            replicatedCaseBranch.setCoordinator1Email(stage.getCoordinator1Email());
            replicatedCaseBranch.setCoordinator2(stage.getCoordinator2());
            replicatedCaseBranch.setCoordinator2Email(stage.getCoordinator2Email());
            replicatedCaseBranch.setDeprecated(stage.getDeprecated());
            replicatedCaseBranch.setStatus(stage.getStatus());
            replicatedCaseBranch.setVersionTime(stage.getVersionTime());
            
            idxTrxValue.setStagingCaseBranch(replicatedCaseBranch);

            ICaseBranchTrxValue trxValue = createStagingCaseBranch(idxTrxValue);
            trxValue = updateCaseBranchTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
