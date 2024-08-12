package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterReplicationUtils;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateFacilityNewMasterOperation extends AbstractFacilityNewMasterTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_FACILITY_NEW_MASTER;
    }

    /**
     * Process the transaction
     * 1.	Create the actual data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IFacilityNewMasterTrxValue trxValue = getFacilityNewMasterTrxValue(anITrxValue);
      try{
        trxValue = createActualFacilityNewMaster(trxValue);
        trxValue = updateFacilityNewMasterTrx(trxValue);
      }catch (TrxOperationException e) {
  		throw new TrxOperationException(e.getMessage());
  	}
      catch (Exception e) {
    	  throw new TrxOperationException(e.getMessage());
	}
       
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws ConcurrentUpdateException 
     * @throws TransactionException 
     * @throws TrxParameterException 
     * @throws FacilityNewMasterException 
     */
    private IFacilityNewMasterTrxValue createActualFacilityNewMaster(IFacilityNewMasterTrxValue idxTrxValue) throws FacilityNewMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IFacilityNewMaster staging = idxTrxValue.getStagingFacilityNewMaster();
            // Replicating is necessary or else stale object error will arise
            IFacilityNewMaster replicatedFacilityNewMaster = FacilityNewMasterReplicationUtils.replicateFacilityNewMasterForCreateStagingCopy(staging);
            IFacilityNewMaster actual = getFacilityNewMasterBusManager().createFacilityNewMaster(replicatedFacilityNewMaster);
            idxTrxValue.setFacilityNewMaster(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getFacilityNewMasterBusManager().updateFacilityNewMaster(actual);
            return idxTrxValue;
        }
        catch (FacilityNewMasterException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
