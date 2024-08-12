package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingReplicationUtils;

public class CheckerApproveCreateValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_VALUATION_AMOUNT_AND_RATING;
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
        IValuationAmountAndRatingTrxValue trxValue = getValuationAmountAndRatingTrxValue(anITrxValue);
      try{
        trxValue = createActualValuationAmountAndRating(trxValue);
        trxValue = updateValuationAmountAndRatingTrx(trxValue);
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
     * @throws ValuationAmountAndRatingException 
     */
    private IValuationAmountAndRatingTrxValue createActualValuationAmountAndRating(IValuationAmountAndRatingTrxValue idxTrxValue) throws ValuationAmountAndRatingException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IValuationAmountAndRating staging = idxTrxValue.getStagingValuationAmountAndRating();
            // Replicating is necessary or else stale object error will arise
            IValuationAmountAndRating replicatedValuationAmountAndRating = ValuationAmountAndRatingReplicationUtils.replicateValuationAmountAndRatingForCreateStagingCopy(staging);
            IValuationAmountAndRating actual = getValuationAmountAndRatingBusManager().createValuationAmountAndRating(replicatedValuationAmountAndRating);
            idxTrxValue.setValuationAmountAndRating(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getValuationAmountAndRatingBusManager().updateValuationAmountAndRating(actual);
            return idxTrxValue;
        }
        catch (ValuationAmountAndRatingException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
