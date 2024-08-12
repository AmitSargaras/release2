package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   delete rejected record by checker
 */
public class MakerEditRejectedDeleteValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedDeleteValuationAmountAndRatingOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_VALUATION_AMOUNT_AND_RATING;
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
        	IValuationAmountAndRatingTrxValue idxTrxValue = getValuationAmountAndRatingTrxValue(anITrxValue);
            IValuationAmountAndRating stage = idxTrxValue.getStagingValuationAmountAndRating();
            idxTrxValue.setStagingValuationAmountAndRating(stage);

            IValuationAmountAndRatingTrxValue trxValue = createStagingValuationAmountAndRating(idxTrxValue);
            trxValue = updateValuationAmountAndRatingTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
