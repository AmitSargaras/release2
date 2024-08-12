package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerRejectValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation{

	 public CheckerRejectValuationAmountAndRatingOperation(){
		 super();
	 }
	  
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_REJECT_VALUATION_AMOUNT_AND_RATING;
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
        IValuationAmountAndRatingTrxValue trxValue = super.getValuationAmountAndRatingTrxValue(anITrxValue);
        trxValue = super.updateValuationAmountAndRatingTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
