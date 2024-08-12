package com.integrosys.cms.app.feed.trx.stock;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateStockFeedGroupOperation extends AbstractStockTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateStockFeedGroupOperation()
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
		return ICMSConstant.ACTION_CHECKER_FILE_MASTER;
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
		IStockFeedGroupTrxValue trxValue = super.getStockFeedGroupTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualStockFeedGroup(trxValue);
		trxValue = updateStockFeedGroup(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IStockFeedGroupTrxValue updateStockFeedGroup(IStockFeedGroupTrxValue anICCStockFeedGroupTrxValue) throws TrxOperationException, StockFeedGroupException
	{	
		try
		{
			String refTemp= anICCStockFeedGroupTrxValue.getStagingReferenceID();
			IStockFeedGroupTrxValue inStockFeedGroupTrxValue = prepareTrxValue(anICCStockFeedGroupTrxValue);
			
//			inStockFeedGroupTrxValue.setFromState("PENDING_CREATE");
//			inStockFeedGroupTrxValue.setTransactionType("FOREX_FEED_GROUP");
//			inStockFeedGroupTrxValue.setToState("ACTIVE");
//			inStockFeedGroupTrxValue.setStatus("ACTIVE");
//			inStockFeedGroupTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = updateTransaction(inStockFeedGroupTrxValue);
            OBStockFeedGroupTrxValue creditApprovalTrxValue = new OBStockFeedGroupTrxValue (trxValue);
            creditApprovalTrxValue.setStockFeedGroup (anICCStockFeedGroupTrxValue.getStockFeedGroup());
            creditApprovalTrxValue.setStockFeedGroup(anICCStockFeedGroupTrxValue.getStockFeedGroup());
	        return creditApprovalTrxValue;
		}
		catch(StockFeedGroupException se)
		{
			throw new StockFeedGroupException("Error in Create StockFeedGroup Operation ");
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
