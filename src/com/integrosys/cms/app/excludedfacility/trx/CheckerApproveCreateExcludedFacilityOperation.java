package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityReplicationUtils;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;

public class CheckerApproveCreateExcludedFacilityOperation extends AbstractExcludedFacilityTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_EXCLUDED_FACILITY;
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
        IExcludedFacilityTrxValue trxValue = getExcludedFacilityTrxValue(anITrxValue);
      try{
        trxValue = createActualExcludedFacility(trxValue);
        trxValue = updateExcludedFacilityTrx(trxValue);
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
    private IExcludedFacilityTrxValue createActualExcludedFacility(IExcludedFacilityTrxValue idxTrxValue) throws ExcludedFacilityException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IExcludedFacility staging = idxTrxValue.getStagingExcludedFacility();
            // Replicating is necessary or else stale object error will arise
            IExcludedFacility replicatedExcludedFacility = ExcludedFacilityReplicationUtils.replicateExcludedFacilityForCreateStagingCopy(staging);
            IExcludedFacility actual = getExcludedFacilityBusManager().createExcludedFacility(replicatedExcludedFacility);
            idxTrxValue.setExcludedFacility(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getExcludedFacilityBusManager().updateExcludedFacility(actual);
            return idxTrxValue;
        }
        catch (ExcludedFacilityException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
