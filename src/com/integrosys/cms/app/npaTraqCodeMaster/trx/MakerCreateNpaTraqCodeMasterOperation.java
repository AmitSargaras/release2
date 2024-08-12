package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateNpaTraqCodeMasterOperation extends AbstractNpaTraqCodeMasterTrxOperation{

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateNpaTraqCodeMasterOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_CREATE_NPA_TRAQ_CODE_MASTER;
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
		INpaTraqCodeMasterTrxValue trxValue = super.getNpaTraqCodeMasterTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingNpaTraqCodeMaster()==null));

	    trxValue = createStagingNpaTraqCodeMaster(trxValue);
		trxValue = createNpaTraqCodeMasterTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private INpaTraqCodeMasterTrxValue createNpaTraqCodeMasterTransaction(INpaTraqCodeMasterTrxValue anICCNpaTraqCodeMasterTrxValue) throws TrxOperationException,NpaTraqCodeMasterException
	{
		try
		{
			anICCNpaTraqCodeMasterTrxValue = prepareTrxValue(anICCNpaTraqCodeMasterTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCNpaTraqCodeMasterTrxValue);
			OBNpaTraqCodeMasterTrxValue npaTraqCodeMasterTrxValue = new OBNpaTraqCodeMasterTrxValue (trxValue);
			npaTraqCodeMasterTrxValue.setStagingNpaTraqCodeMaster(anICCNpaTraqCodeMasterTrxValue.getStagingNpaTraqCodeMaster());
			npaTraqCodeMasterTrxValue.setNpaTraqCodeMaster(anICCNpaTraqCodeMasterTrxValue.getNpaTraqCodeMaster());
	        return npaTraqCodeMasterTrxValue;
		}
		catch(NpaTraqCodeMasterException se)
		{
			throw new NpaTraqCodeMasterException("Error in Create NpaTraqCodeMaster Operation ");
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
