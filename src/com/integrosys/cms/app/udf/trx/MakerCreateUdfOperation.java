package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.udf.bus.UdfException;

public class MakerCreateUdfOperation extends AbstractUdfTrxOperation{

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateUdfOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_CREATE_UDF;
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
		IUdfTrxValue trxValue = super.getUdfTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingUdf()==null));

	    trxValue = createStagingUdf(trxValue);
		trxValue = createUdfTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IUdfTrxValue createUdfTransaction(IUdfTrxValue anICCUdfTrxValue) throws TrxOperationException,UdfException
	{
		try
		{
			anICCUdfTrxValue = prepareTrxValue(anICCUdfTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCUdfTrxValue);
			OBUdfTrxValue udfTrxValue = new OBUdfTrxValue (trxValue);
			udfTrxValue.setStagingUdf(anICCUdfTrxValue.getStagingUdf());
			udfTrxValue.setUdf(anICCUdfTrxValue.getUdf());
	        return udfTrxValue;
		}
		catch(UdfException se)
		{
			throw new UdfException("Error in Create UDF Operation ");
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
