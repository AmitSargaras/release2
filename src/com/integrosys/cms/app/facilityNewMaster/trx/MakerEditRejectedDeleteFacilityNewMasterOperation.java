package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedDeleteFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedDeleteFacilityNewMasterOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_FACILITY_NEW_MASTER;
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
            IFacilityNewMasterTrxValue idxTrxValue = getFacilityNewMasterTrxValue(anITrxValue);
            IFacilityNewMaster stage = idxTrxValue.getStagingFacilityNewMaster();
            IFacilityNewMaster replicatedFacilityNewMaster= new OBFacilityNewMaster();
            replicatedFacilityNewMaster.setNewFacilityCode(stage.getNewFacilityCode());
            replicatedFacilityNewMaster.setNewFacilityName(stage.getNewFacilityName());
            replicatedFacilityNewMaster.setNewFacilityType(stage.getNewFacilityType());
            replicatedFacilityNewMaster.setNewFacilityCategory(stage.getNewFacilityCategory());
            replicatedFacilityNewMaster.setLineNumber(stage.getLineNumber());
            replicatedFacilityNewMaster.setPurpose(stage.getPurpose());
            replicatedFacilityNewMaster.setNewFacilitySystem(stage.getNewFacilitySystem());
            replicatedFacilityNewMaster.setWeightage(stage.getWeightage());
    		
            replicatedFacilityNewMaster.setId(stage.getId());
            replicatedFacilityNewMaster.setCreateBy(stage.getCreateBy());
            replicatedFacilityNewMaster.setCreationDate(stage.getCreationDate());
            replicatedFacilityNewMaster.setDeprecated(stage.getDeprecated());
            replicatedFacilityNewMaster.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedFacilityNewMaster.setLastUpdateDate(stage.getLastUpdateDate());
            replicatedFacilityNewMaster.setStatus(stage.getStatus());
            replicatedFacilityNewMaster.setVersionTime(stage.getVersionTime());
            replicatedFacilityNewMaster.setRuleId(stage.getRuleId());
            replicatedFacilityNewMaster.setProductAllowed(stage.getProductAllowed());
            replicatedFacilityNewMaster.setCurrencyRestriction(stage.getCurrencyRestriction());
            replicatedFacilityNewMaster.setRevolvingLine(stage.getRevolvingLine());
            replicatedFacilityNewMaster.setLineCurrency(stage.getLineCurrency());
            replicatedFacilityNewMaster.setIntradayLimit(stage.getIntradayLimit());
            replicatedFacilityNewMaster.setStlFlag(stage.getStlFlag());
            replicatedFacilityNewMaster.setLineDescription(stage.getLineDescription());
            replicatedFacilityNewMaster.setScmFlag(stage.getScmFlag());
            replicatedFacilityNewMaster.setSelectedRiskTypes(stage.getSelectedRiskTypes());
            replicatedFacilityNewMaster.setAvailAndOptionApplicable(stage.getAvailAndOptionApplicable());
			replicatedFacilityNewMaster.setLineExcludeFromLoa(stage.getLineExcludeFromLoa());	
			replicatedFacilityNewMaster.setIdlApplicableFlag(stage.getIdlApplicableFlag());
			
            idxTrxValue.setStagingFacilityNewMaster(replicatedFacilityNewMaster);

            IFacilityNewMasterTrxValue trxValue = createStagingFacilityNewMaster(idxTrxValue);
            trxValue = updateFacilityNewMasterTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
