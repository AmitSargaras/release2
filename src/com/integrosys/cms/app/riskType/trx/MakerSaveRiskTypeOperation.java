package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveRiskTypeOperation extends AbstractRiskTypeTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerSaveRiskTypeOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_RISK_TYPE;
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
	    IRiskTypeTrxValue trxValue = super.getRiskTypeTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingRiskType()==null));

	    trxValue = createStagingRiskType(trxValue);
		trxValue = createRiskTypeTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	* Create a property index transaction
	* @param anICCRiskTypeIdxTrxValue of ICCRiskTypeIdxTrxValue type
	* @return ICCRiskTypeIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IRiskTypeTrxValue createRiskTypeTransaction(IRiskTypeTrxValue anICCRiskTypeTrxValue) throws TrxOperationException,RiskTypeException
	{
		try
		{
            anICCRiskTypeTrxValue = prepareTrxValue(anICCRiskTypeTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCRiskTypeTrxValue);
            OBRiskTypeTrxValue valuationAmountAndRatingTrxValue = new OBRiskTypeTrxValue (trxValue);
            valuationAmountAndRatingTrxValue.setStagingRiskType(anICCRiskTypeTrxValue.getStagingRiskType());
            valuationAmountAndRatingTrxValue.setRiskType(anICCRiskTypeTrxValue.getRiskType());
	        return valuationAmountAndRatingTrxValue;
		}
		catch(RiskTypeException se)
		{
			throw new RiskTypeException("Error in Create RiskType");
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
