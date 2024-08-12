package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateExcludedFacilityOperation extends AbstractExcludedFacilityTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateExcludedFacilityOperation() {
		super();
	}
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_CREATE_EXCLUDED_FACILITY;
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
		IExcludedFacilityTrxValue trxValue = super.getExcludedFacilityTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingExcludedFacility()==null));

	    trxValue = createStagingExcludedFacility(trxValue);
		trxValue = createExcludedFacilityTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IExcludedFacilityTrxValue createExcludedFacilityTransaction(IExcludedFacilityTrxValue anICCExcludedFacilityTrxValue) throws TrxOperationException,ExcludedFacilityException
	{
		try
		{
			anICCExcludedFacilityTrxValue = prepareTrxValue(anICCExcludedFacilityTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCExcludedFacilityTrxValue);
			OBExcludedFacilityTrxValue excludedFacilityTrxValue = new OBExcludedFacilityTrxValue (trxValue);
			excludedFacilityTrxValue.setStagingExcludedFacility(anICCExcludedFacilityTrxValue.getStagingExcludedFacility());
			excludedFacilityTrxValue.setExcludedFacility(anICCExcludedFacilityTrxValue.getExcludedFacility());
	        return excludedFacilityTrxValue;
		}
		catch(ExcludedFacilityException se)
		{
			throw new ExcludedFacilityException("Error in Create Excluded Facility Operation ");
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
