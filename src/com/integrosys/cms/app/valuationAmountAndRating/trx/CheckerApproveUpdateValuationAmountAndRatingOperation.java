package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;

/**
 * @author Santosh.Sonmankar
 * Checker approve Operation to approve update made by maker
 */
public class CheckerApproveUpdateValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateValuationAmountAndRatingOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_VALUATION_AMOUNT_AND_RATING;
    }
	
	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IValuationAmountAndRatingTrxValue trxValue = getValuationAmountAndRatingTrxValue(anITrxValue);
		trxValue = updateActualValuationAmountAndRating(trxValue);
		trxValue = updateValuationAmountAndRatingTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IValuationAmountAndRatingTrxValue updateActualValuationAmountAndRating(IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue)
			throws TrxOperationException {
		try {
			IValuationAmountAndRating staging = anICCValuationAmountAndRatingTrxValue.getStagingValuationAmountAndRating();
			IValuationAmountAndRating actual = anICCValuationAmountAndRatingTrxValue.getValuationAmountAndRating();

			IValuationAmountAndRating updatedValuationAmountAndRating = getValuationAmountAndRatingBusManager().updateToWorkingCopy(actual, staging);
			anICCValuationAmountAndRatingTrxValue.setValuationAmountAndRating(updatedValuationAmountAndRating);

			return anICCValuationAmountAndRatingTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
