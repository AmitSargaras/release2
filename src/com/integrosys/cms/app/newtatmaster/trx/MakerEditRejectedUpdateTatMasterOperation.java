package com.integrosys.cms.app.newtatmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster;

public class MakerEditRejectedUpdateTatMasterOperation extends AbstractTatMasterTrxOperation{
	

	
	 public MakerEditRejectedUpdateTatMasterOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_TAT_MASTER;
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
    	ITatMasterTrxValue idxTrxValue = getTatMasterTrxValue(anITrxValue);
    	INewTatMaster stage = idxTrxValue.getStagingtatMaster();
    	INewTatMaster replicatedTatMaster= new OBNewTatMaster();
    	replicatedTatMaster.setId(stage.getId());
    	replicatedTatMaster.setUserEvent(stage.getUserEvent());
    	replicatedTatMaster.setStartTime(stage.getStartTime());
    	replicatedTatMaster.setTimingHours(stage.getTimingHours());
    	replicatedTatMaster.setTimingMin(stage.getTimingMin());
    	replicatedTatMaster.setEndTime(stage.getEndTime());
    	replicatedTatMaster.setCreatedBy(stage.getCreatedBy());
    	replicatedTatMaster.setCreatedOn(stage.getCreatedOn());
    	replicatedTatMaster.setLastUpdatedBy(stage.getLastUpdatedBy());
    	replicatedTatMaster.setLastUpdatedOn(stage.getLastUpdatedOn());
    	replicatedTatMaster.setEventCode(stage.getEventCode());
        
    	replicatedTatMaster.setStatus(stage.getStatus());
    	replicatedTatMaster.setDeprecated(stage.getDeprecated());
        
        idxTrxValue.setStagingtatMaster(replicatedTatMaster);

        ITatMasterTrxValue trxValue = createStagingTatMaster(idxTrxValue);
        trxValue = updateTatMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }




}
