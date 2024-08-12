package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedDeleteCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedDeleteCollateralNewMasterOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_COLLATERAL_NEW_MASTER;
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
            ICollateralNewMasterTrxValue idxTrxValue = getCollateralNewMasterTrxValue(anITrxValue);
            ICollateralNewMaster stage = idxTrxValue.getStagingCollateralNewMaster();
            ICollateralNewMaster replicatedCollateralNewMaster= new OBCollateralNewMaster();
            replicatedCollateralNewMaster.setNewCollateralCode(stage.getNewCollateralCode());
            replicatedCollateralNewMaster.setNewCollateralDescription(stage.getNewCollateralDescription());
            replicatedCollateralNewMaster.setNewCollateralMainType(stage.getNewCollateralMainType());
            replicatedCollateralNewMaster.setNewCollateralSubType(stage.getNewCollateralSubType());
            replicatedCollateralNewMaster.setInsurance(stage.getInsurance());
            replicatedCollateralNewMaster.setRevaluationFrequencyCount(stage.getRevaluationFrequencyCount());
            replicatedCollateralNewMaster.setRevaluationFrequencyDays(stage.getRevaluationFrequencyDays());
     
    		
            replicatedCollateralNewMaster.setId(stage.getId());
            replicatedCollateralNewMaster.setCreateBy(stage.getCreateBy());
            replicatedCollateralNewMaster.setCreationDate(stage.getCreationDate());
            replicatedCollateralNewMaster.setDeprecated(stage.getDeprecated());
            replicatedCollateralNewMaster.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedCollateralNewMaster.setLastUpdateDate(stage.getLastUpdateDate());
            replicatedCollateralNewMaster.setStatus(stage.getStatus());
            replicatedCollateralNewMaster.setVersionTime(stage.getVersionTime());
            replicatedCollateralNewMaster.setIsApplicableForCersaiInd(stage.getIsApplicableForCersaiInd());
            replicatedCollateralNewMaster.setNewCollateralCategory(stage.getNewCollateralCategory());
            
            idxTrxValue.setStagingCollateralNewMaster(replicatedCollateralNewMaster);

            ICollateralNewMasterTrxValue trxValue = createStagingCollateralNewMaster(idxTrxValue);
            trxValue = updateCollateralNewMasterTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
