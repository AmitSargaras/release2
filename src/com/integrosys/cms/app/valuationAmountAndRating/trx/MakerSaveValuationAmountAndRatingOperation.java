package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerSaveValuationAmountAndRatingOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_VALUATION_AMOUNT_AND_RATING;
	}
	
	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
	    IValuationAmountAndRatingTrxValue trxValue = super.getValuationAmountAndRatingTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingValuationAmountAndRating()==null));

	    trxValue = createStagingValuationAmountAndRating(trxValue);
		trxValue = createValuationAmountAndRatingTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	* Create a property index transaction
	* @param anICCValuationAmountAndRatingIdxTrxValue of ICCValuationAmountAndRatingIdxTrxValue type
	* @return ICCValuationAmountAndRatingIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IValuationAmountAndRatingTrxValue createValuationAmountAndRatingTransaction(IValuationAmountAndRatingTrxValue anICCValuationAmountAndRatingTrxValue) throws TrxOperationException,ValuationAmountAndRatingException
	{
		try
		{
            anICCValuationAmountAndRatingTrxValue = prepareTrxValue(anICCValuationAmountAndRatingTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCValuationAmountAndRatingTrxValue);
            OBValuationAmountAndRatingTrxValue valuationAmountAndRatingTrxValue = new OBValuationAmountAndRatingTrxValue (trxValue);
            valuationAmountAndRatingTrxValue.setStagingValuationAmountAndRating(anICCValuationAmountAndRatingTrxValue.getStagingValuationAmountAndRating());
            valuationAmountAndRatingTrxValue.setValuationAmountAndRating(anICCValuationAmountAndRatingTrxValue.getValuationAmountAndRating());
	        return valuationAmountAndRatingTrxValue;
		}
		catch(ValuationAmountAndRatingException se)
		{
			throw new ValuationAmountAndRatingException("Error in Create ValuationAmountAndRating");
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}
