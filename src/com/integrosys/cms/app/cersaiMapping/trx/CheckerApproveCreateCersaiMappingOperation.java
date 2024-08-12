package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingReplicationUtils;
import com.integrosys.cms.app.cersaiMapping.trx.AbstractCersaiMappingTrxOperation;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;

public class CheckerApproveCreateCersaiMappingOperation extends AbstractCersaiMappingTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CERSAI_MAPPING;
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
        ICersaiMappingTrxValue trxValue = getCersaiMappingTrxValue(anITrxValue);
      try{
        trxValue = createActualCersaiMapping(trxValue);
        trxValue = updateCersaiMappingTrx(trxValue);
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
     * @throws CersaiMappingException 
     */
    private ICersaiMappingTrxValue createActualCersaiMapping(ICersaiMappingTrxValue idxTrxValue) throws CersaiMappingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ICersaiMapping staging = idxTrxValue.getStagingCersaiMapping();
            // Replicating is necessary or else stale object error will arise
            ICersaiMapping replicatedCersaiMapping = CersaiMappingReplicationUtils.replicateCersaiMappingForCreateStagingCopy(staging);
            ICersaiMapping actual = getCersaiMappingBusManager().createCersaiMapping(replicatedCersaiMapping);
            idxTrxValue.setCersaiMapping(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getCersaiMappingBusManager().updateCersaiMapping(actual);
            return idxTrxValue;
        }
        catch (CersaiMappingException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
