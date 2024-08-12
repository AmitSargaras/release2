package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterReplicationUtils;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedCreateFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateFacilityNewMasterOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_FACILITY_NEW_MASTER;
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
        IFacilityNewMasterTrxValue idxTrxValue = getFacilityNewMasterTrxValue(anITrxValue);
        IFacilityNewMaster stage = idxTrxValue.getStagingFacilityNewMaster();
        IFacilityNewMaster replicatedFacilityNewMaster = FacilityNewMasterReplicationUtils.replicateFacilityNewMasterForCreateStagingCopy(stage);
        idxTrxValue.setStagingFacilityNewMaster(replicatedFacilityNewMaster);

        IFacilityNewMasterTrxValue trxValue = createStagingFacilityNewMaster(idxTrxValue);
        trxValue = updateFacilityNewMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
