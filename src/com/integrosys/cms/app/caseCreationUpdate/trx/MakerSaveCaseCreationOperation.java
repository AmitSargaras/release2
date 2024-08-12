package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveCaseCreationOperation extends AbstractCaseCreationTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveCaseCreationOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_CASECREATION;
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
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingCaseCreation()==null));

	    trxValue = createStagingCaseCreation(trxValue);
		trxValue = createCaseCreationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICaseCreationTrxValue createCaseCreationTransaction(ICaseCreationTrxValue anICCCaseCreationTrxValue) throws TrxOperationException,CaseCreationException
	{
		try
		{
            anICCCaseCreationTrxValue = prepareTrxValue(anICCCaseCreationTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCCaseCreationTrxValue);
            OBCaseCreationTrxValue caseCreationUpdateTrxValue = new OBCaseCreationTrxValue (trxValue);
            caseCreationUpdateTrxValue.setStagingCaseCreation (anICCCaseCreationTrxValue.getStagingCaseCreation());
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
