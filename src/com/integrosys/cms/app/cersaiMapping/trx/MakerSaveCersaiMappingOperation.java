package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveCersaiMappingOperation extends AbstractCersaiMappingTrxOperation implements ICommonEventConstant{
	/**
	 * Defaulc Constructor
	 */
	public MakerSaveCersaiMappingOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_CERSAI_MAPPING;
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
	    ICersaiMappingTrxValue trxValue = super.getCersaiMappingTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingCersaiMapping()==null));

	    trxValue = createStagingCersaiMapping(trxValue);
		trxValue = createCersaiMappingTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	* Create a property index transaction
	* @param anICCProductMasterIdxTrxValue of ICCProductMasterIdxTrxValue type
	* @return ICCProductMasterIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICersaiMappingTrxValue createCersaiMappingTransaction(ICersaiMappingTrxValue anICCICersaiMappingTrxValue) throws TrxOperationException,CersaiMappingException
	{
		try
		{
			anICCICersaiMappingTrxValue = prepareTrxValue(anICCICersaiMappingTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCICersaiMappingTrxValue);
			OBCersaiMappingTrxValue cersaiMappingTrxValue = new OBCersaiMappingTrxValue(trxValue);
            cersaiMappingTrxValue.setStagingCersaiMapping((anICCICersaiMappingTrxValue.getStagingCersaiMapping()));
            cersaiMappingTrxValue.setCersaiMapping(anICCICersaiMappingTrxValue.getCersaiMapping());
	        return cersaiMappingTrxValue;
		}
		catch(CersaiMappingException se)
		{
			throw new CersaiMappingException("Error in Create CersaiMapping");
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
