package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedDeleteCaseCreationOperation extends AbstractCaseCreationTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedDeleteCaseCreationOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_CASECREATION;
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
            ICaseCreationTrxValue idxTrxValue = getCaseCreationTrxValue(anITrxValue);
            ICaseCreation stage = idxTrxValue.getStagingCaseCreation();
            ICaseCreation replicatedCaseCreation= new OBCaseCreation();
            replicatedCaseCreation.setId(stage.getId());
            replicatedCaseCreation.setDescription(stage.getDescription());
            replicatedCaseCreation.setCreateBy(stage.getCreateBy());
            replicatedCaseCreation.setCreationDate(stage.getCreationDate());
            replicatedCaseCreation.setDeprecated(stage.getDeprecated());
            replicatedCaseCreation.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedCaseCreation.setLastUpdateDate(stage.getLastUpdateDate());
            replicatedCaseCreation.setBranchCode(stage.getBranchCode());
            replicatedCaseCreation.setCurrRemarks(stage.getCurrRemarks());
            replicatedCaseCreation.setPrevRemarks(stage.getPrevRemarks());
            replicatedCaseCreation.setLimitProfileId(stage.getLimitProfileId());
            replicatedCaseCreation.setStatus(stage.getStatus());
            replicatedCaseCreation.setVersionTime(stage.getVersionTime());
            
            idxTrxValue.setStagingCaseCreation(replicatedCaseCreation);

            ICaseCreationTrxValue trxValue = createStagingCaseCreation(idxTrxValue);
            trxValue = updateCaseCreationTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
