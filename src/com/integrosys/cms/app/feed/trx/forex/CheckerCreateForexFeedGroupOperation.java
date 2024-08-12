package com.integrosys.cms.app.feed.trx.forex;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateForexFeedGroupOperation extends AbstractForexTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateForexFeedGroupOperation()
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
		IForexFeedGroupTrxValue trxValue = super.getForexFeedGroupTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualForexFeedGroup(trxValue);
		trxValue = updateForexFeedGroup(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IForexFeedGroupTrxValue updateForexFeedGroup(IForexFeedGroupTrxValue anICCForexFeedGroupTrxValue) throws TrxOperationException, ForexFeedGroupException
	{	
		try
		{
			String refTemp= anICCForexFeedGroupTrxValue.getStagingReferenceID();
			IForexFeedGroupTrxValue inForexFeedGroupTrxValue = prepareTrxValue(anICCForexFeedGroupTrxValue);
			
//			inForexFeedGroupTrxValue.setFromState("PENDING_CREATE");
//			inForexFeedGroupTrxValue.setTransactionType("FOREX_FEED_GROUP");
//			inForexFeedGroupTrxValue.setToState("ACTIVE");
//			inForexFeedGroupTrxValue.setStatus("ACTIVE");
//			inForexFeedGroupTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = updateTransaction(inForexFeedGroupTrxValue);
            OBForexFeedGroupTrxValue creditApprovalTrxValue = new OBForexFeedGroupTrxValue (trxValue);
            creditApprovalTrxValue.setForexFeedGroup (anICCForexFeedGroupTrxValue.getForexFeedGroup());
            creditApprovalTrxValue.setForexFeedGroup(anICCForexFeedGroupTrxValue.getForexFeedGroup());
	        return creditApprovalTrxValue;
		}
		catch(ForexFeedGroupException se)
		{
			throw new ForexFeedGroupException("Error in Create ForexFeedGroup Operation ");
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
