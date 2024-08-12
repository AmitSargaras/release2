package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateCreditApprovalOperation extends AbstractCreditApprovalTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateCreditApprovalOperation()
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
	    ICreditApprovalTrxValue trxValue = super.getCreditApprovalTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualCreditApproval(trxValue);
		trxValue = createCreditApproval(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICreditApprovalTrxValue createCreditApproval(ICreditApprovalTrxValue anICCCreditApprovalTrxValue) throws TrxOperationException, CreditApprovalException
	{	
		try
		{
			String refTemp= anICCCreditApprovalTrxValue.getStagingReferenceID();
			ICreditApprovalTrxValue inCreditApprovalTrxValue = prepareTrxValue(anICCCreditApprovalTrxValue);
			
			inCreditApprovalTrxValue.setFromState("PENDING_CREATE");
			inCreditApprovalTrxValue.setTransactionType("CREDIT_APPROVAL");
			inCreditApprovalTrxValue.setToState("ACTIVE");
			inCreditApprovalTrxValue.setStatus("ACTIVE");
			inCreditApprovalTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = createTransaction(inCreditApprovalTrxValue);
            OBCreditApprovalTrxValue creditApprovalTrxValue = new OBCreditApprovalTrxValue (trxValue);
            creditApprovalTrxValue.setCreditApproval (anICCCreditApprovalTrxValue.getCreditApproval());
            creditApprovalTrxValue.setCreditApproval(anICCCreditApprovalTrxValue.getCreditApproval());
	        return creditApprovalTrxValue;
		}
		catch(CreditApprovalException se)
		{
			throw new CreditApprovalException("Error in Create CreditApproval Operation ");
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
