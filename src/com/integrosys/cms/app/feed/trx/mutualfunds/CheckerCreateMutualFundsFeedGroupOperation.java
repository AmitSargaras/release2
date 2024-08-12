package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateMutualFundsFeedGroupOperation extends AbstractMutualFundsTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateMutualFundsFeedGroupOperation()
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
		IMutualFundsFeedGroupTrxValue trxValue = super.getMutualFundsFeedGroupTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualMutualFundsFeedGroup(trxValue);
		trxValue = updateMutualFundsFeedGroup(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IMutualFundsFeedGroupTrxValue updateMutualFundsFeedGroup(IMutualFundsFeedGroupTrxValue anICCMutualFundsFeedGroupTrxValue) throws TrxOperationException, MutualFundsFeedGroupException
	{	
		try
		{
			String refTemp= anICCMutualFundsFeedGroupTrxValue.getStagingReferenceID();
			IMutualFundsFeedGroupTrxValue inMutualFundsFeedGroupTrxValue = prepareTrxValue(anICCMutualFundsFeedGroupTrxValue);
			
//			inMutualFundsFeedGroupTrxValue.setFromState("PENDING_CREATE");
//			inMutualFundsFeedGroupTrxValue.setTransactionType("FOREX_FEED_GROUP");
//			inMutualFundsFeedGroupTrxValue.setToState("ACTIVE");
//			inMutualFundsFeedGroupTrxValue.setStatus("ACTIVE");
//			inMutualFundsFeedGroupTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = updateTransaction(inMutualFundsFeedGroupTrxValue);
            OBMutualFundsFeedGroupTrxValue creditApprovalTrxValue = new OBMutualFundsFeedGroupTrxValue (trxValue);
            creditApprovalTrxValue.setMutualFundsFeedGroup (anICCMutualFundsFeedGroupTrxValue.getMutualFundsFeedGroup());
            creditApprovalTrxValue.setMutualFundsFeedGroup(anICCMutualFundsFeedGroupTrxValue.getMutualFundsFeedGroup());
	        return creditApprovalTrxValue;
		}
		catch(MutualFundsFeedGroupException se)
		{
			throw new MutualFundsFeedGroupException("Error in Create MutualFundsFeedGroup Operation ");
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
