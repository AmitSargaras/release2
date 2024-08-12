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
 * Author: Govind Sahu 
 * Date: 2011/04/19
 */
public class MakerSaveCreditApprovalOperation extends AbstractCreditApprovalTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveCreditApprovalOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_CREDIT_APPROVAL;
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
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingCreditApproval()==null));

	    trxValue = createStagingCreditApproval(trxValue);
		trxValue = createCreditApprovalTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICreditApprovalTrxValue createCreditApprovalTransaction(ICreditApprovalTrxValue anICCCreditApprovalTrxValue) throws TrxOperationException,CreditApprovalException
	{
		try
		{
            anICCCreditApprovalTrxValue = prepareTrxValue(anICCCreditApprovalTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCCreditApprovalTrxValue);
            OBCreditApprovalTrxValue creditApprovalTrxValue = new OBCreditApprovalTrxValue (trxValue);
            creditApprovalTrxValue.setStagingCreditApproval (anICCCreditApprovalTrxValue.getStagingCreditApproval());
            creditApprovalTrxValue.setCreditApproval(anICCCreditApprovalTrxValue.getCreditApproval());
	        return creditApprovalTrxValue;
		}
		catch(CreditApprovalException se)
		{
			throw new CreditApprovalException("Error in Create Credit Approval Operation ");
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new TrxOperationException("General Exception: " + ex.getMessage());
		}
	}

}
