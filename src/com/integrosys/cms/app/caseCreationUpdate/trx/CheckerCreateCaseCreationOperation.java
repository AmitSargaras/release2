package com.integrosys.cms.app.caseCreationUpdate.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateCaseCreationOperation extends AbstractCaseCreationTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateCaseCreationOperation()
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
	    ICaseCreationTrxValue trxValue = super.getCaseCreationTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualCaseCreation(trxValue);
		trxValue = createCaseCreation(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICaseCreationTrxValue createCaseCreation(ICaseCreationTrxValue anICCCaseCreationTrxValue) throws TrxOperationException, CaseCreationException
	{	
		try
		{
			String refTemp= anICCCaseCreationTrxValue.getStagingReferenceID();
			ICaseCreationTrxValue inCaseCreationTrxValue = prepareTrxValue(anICCCaseCreationTrxValue);
			
			inCaseCreationTrxValue.setFromState("PENDING_CREATE");
			inCaseCreationTrxValue.setTransactionType("CASECREATION");
			inCaseCreationTrxValue.setToState("ACTIVE");
			inCaseCreationTrxValue.setStatus("ACTIVE");
			inCaseCreationTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = createTransaction(inCaseCreationTrxValue);
            OBCaseCreationTrxValue caseCreationUpdateTrxValue = new OBCaseCreationTrxValue (trxValue);
            caseCreationUpdateTrxValue.setCaseCreation (anICCCaseCreationTrxValue.getCaseCreation());
            caseCreationUpdateTrxValue.setCaseCreation(anICCCaseCreationTrxValue.getCaseCreation());
	        return caseCreationUpdateTrxValue;
		}
		catch(CaseCreationException se)
		{
			throw new CaseCreationException("Error in Create CaseCreation Operation ");
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
