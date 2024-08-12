package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationException;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerSaveLeiDateValidationOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_LEI_DATE_VALIDATION;
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
	    ILeiDateValidationTrxValue trxValue = super.getLeiDateValidationTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingLeiDateValidation()==null));

	    trxValue = createStagingLeiDateValidation(trxValue);
		trxValue = createLeiDateValidationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	* Create a property index transaction
	* @param anICCLeiDateValidationIdxTrxValue of ICCLeiDateValidationIdxTrxValue type
	* @return ICCLeiDateValidationIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ILeiDateValidationTrxValue createLeiDateValidationTransaction(ILeiDateValidationTrxValue anICCLeiDateValidationTrxValue) throws TrxOperationException,LeiDateValidationException
	{
		try
		{
            anICCLeiDateValidationTrxValue = prepareTrxValue(anICCLeiDateValidationTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCLeiDateValidationTrxValue);
            OBLeiDateValidationTrxValue leiDateValidationTrxValue = new OBLeiDateValidationTrxValue (trxValue);
            leiDateValidationTrxValue.setStagingLeiDateValidation(anICCLeiDateValidationTrxValue.getStagingLeiDateValidation());
            leiDateValidationTrxValue.setLeiDateValidation(anICCLeiDateValidationTrxValue.getLeiDateValidation());
	        return leiDateValidationTrxValue;
		}
		catch(LeiDateValidationException se)
		{
			throw new LeiDateValidationException("Error in Create LeiDateValidation");
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
