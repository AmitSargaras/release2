package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterReplicationUtils;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;

/**
 * @author abhijit.rudrakshawar
 * Maker Update operation to  update system 
 */
public class MakerUpdateFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateFacilityNewMasterOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_FACILITY_NEW_MASTER;
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
        IFacilityNewMasterTrxValue trxValue = getFacilityNewMasterTrxValue(anITrxValue);
        IFacilityNewMaster staging = trxValue.getStagingFacilityNewMaster();
       
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
        IFacilityNewMasterTrxValue idxTrxValue = getFacilityNewMasterTrxValue(anITrxValue);
        IFacilityNewMaster stage = idxTrxValue.getStagingFacilityNewMaster();
        IFacilityNewMaster replicatedFacilityNewMaster = FacilityNewMasterReplicationUtils.replicateFacilityNewMasterForCreateStagingCopy(stage);
     //   replicatedFacilityNewMaster.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingFacilityNewMaster(replicatedFacilityNewMaster);

        IFacilityNewMasterTrxValue trxValue = createStagingFacilityNewMaster(idxTrxValue);
        trxValue = updateFacilityNewMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
