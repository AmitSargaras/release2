package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationException;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationReplicationUtils;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;

public class CheckerApproveCreateLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_LEI_DATE_VALIDATION;
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
        ILeiDateValidationTrxValue trxValue = getLeiDateValidationTrxValue(anITrxValue);
      try{
        trxValue = createActualLeiDateValidation(trxValue);
        trxValue = updateLeiDateValidationTrx(trxValue);
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
     * @throws LeiDateValidationException 
     */
    private ILeiDateValidationTrxValue createActualLeiDateValidation(ILeiDateValidationTrxValue idxTrxValue) throws LeiDateValidationException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ILeiDateValidation staging = idxTrxValue.getStagingLeiDateValidation();
            // Replicating is necessary or else stale object error will arise
            ILeiDateValidation replicatedLeiDateValidation = LeiDateValidationReplicationUtils.replicateLeiDateValidationForCreateStagingCopy(staging);
            ILeiDateValidation actual = getLeiDateValidationBusManager().createLeiDateValidation(replicatedLeiDateValidation);
            idxTrxValue.setLeiDateValidation(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getLeiDateValidationBusManager().updateLeiDateValidation(actual);
            return idxTrxValue;
        }
        catch (LeiDateValidationException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
