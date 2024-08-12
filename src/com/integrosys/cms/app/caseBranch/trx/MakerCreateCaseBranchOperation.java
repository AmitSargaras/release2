package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerCreateCaseBranchOperation extends AbstractCaseBranchTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreateCaseBranchOperation()
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
		return ICMSConstant.ACTION_MAKER_CREATE_CASEBRANCH;
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
	    ICaseBranchTrxValue trxValue = super.getCaseBranchTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingCaseBranch()==null));

	    trxValue = createStagingCaseBranch(trxValue);
		trxValue = createCaseBranchTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICaseBranchTrxValue createCaseBranchTransaction(ICaseBranchTrxValue anICCCaseBranchTrxValue) throws TrxOperationException,CaseBranchException
	{
		try
		{
            anICCCaseBranchTrxValue = prepareTrxValue(anICCCaseBranchTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCCaseBranchTrxValue);
            OBCaseBranchTrxValue caseBranchTrxValue = new OBCaseBranchTrxValue (trxValue);
            caseBranchTrxValue.setStagingCaseBranch (anICCCaseBranchTrxValue.getStagingCaseBranch());
            caseBranchTrxValue.setCaseBranch(anICCCaseBranchTrxValue.getCaseBranch());
	        return caseBranchTrxValue;
		}
		catch(CaseBranchException se)
		{
			throw new CaseBranchException("Error in Create CaseBranch Operation ");
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
