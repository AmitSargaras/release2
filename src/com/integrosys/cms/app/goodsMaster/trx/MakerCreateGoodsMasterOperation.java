package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateGoodsMasterOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_CREATE_GOODS_MASTER;
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
		IGoodsMasterTrxValue trxValue = super.getGoodsMasterTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingGoodsMaster()==null));

	    trxValue = createStagingGoodsMaster(trxValue);
		trxValue = createGoodsMasterTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IGoodsMasterTrxValue createGoodsMasterTransaction(IGoodsMasterTrxValue anICCGoodsMasterTrxValue) throws TrxOperationException,GoodsMasterException
	{
		try
		{
			anICCGoodsMasterTrxValue = prepareTrxValue(anICCGoodsMasterTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCGoodsMasterTrxValue);
			OBGoodsMasterTrxValue goodsMasterTrxValue = new OBGoodsMasterTrxValue (trxValue);
			goodsMasterTrxValue.setStagingGoodsMaster(anICCGoodsMasterTrxValue.getStagingGoodsMaster());
			goodsMasterTrxValue.setGoodsMaster(anICCGoodsMasterTrxValue.getGoodsMaster());
	        return goodsMasterTrxValue;
		}
		catch(GoodsMasterException se)
		{
			throw new GoodsMasterException("Error in Create GoodsMaster Operation ");
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
