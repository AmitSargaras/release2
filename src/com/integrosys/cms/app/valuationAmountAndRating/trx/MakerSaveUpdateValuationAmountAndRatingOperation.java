package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingReplicationUtils;

public class MakerSaveUpdateValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation  {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdateValuationAmountAndRatingOperation() {
		super();
	}
	
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_VALUATION_AMOUNT_AND_RATING;
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
        IValuationAmountAndRatingTrxValue idxTrxValue = getValuationAmountAndRatingTrxValue(anITrxValue);
        IValuationAmountAndRating stage = idxTrxValue.getStagingValuationAmountAndRating();
        IValuationAmountAndRating replicatedValuationAmountAndRating = ValuationAmountAndRatingReplicationUtils.replicateValuationAmountAndRatingForCreateStagingCopy(stage);
        idxTrxValue.setStagingValuationAmountAndRating(replicatedValuationAmountAndRating);
        IValuationAmountAndRatingTrxValue trxValue = createStagingValuationAmountAndRating(idxTrxValue);
        trxValue = updateValuationAmountAndRatingTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
