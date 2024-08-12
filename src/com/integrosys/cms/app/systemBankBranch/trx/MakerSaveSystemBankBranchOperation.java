package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveSystemBankBranchOperation extends AbstractSystemBankBranchTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveSystemBankBranchOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_SYSTEM_BANK_BRANCH;
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
	    ISystemBankBranchTrxValue trxValue = super.getSystemBankBranchTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingSystemBankBranch()==null));

	    trxValue = createStagingSystemBankBranch(trxValue);
		trxValue = createSystemBankBranchTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ISystemBankBranchTrxValue createSystemBankBranchTransaction(ISystemBankBranchTrxValue anICCSystemBankBranchTrxValue) throws TrxOperationException,SystemBankBranchException
	{
		try
		{
            anICCSystemBankBranchTrxValue = prepareTrxValue(anICCSystemBankBranchTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCSystemBankBranchTrxValue);
            OBSystemBankBranchTrxValue systemBankBranchTrxValue = new OBSystemBankBranchTrxValue (trxValue);
            systemBankBranchTrxValue.setStagingSystemBankBranch (anICCSystemBankBranchTrxValue.getStagingSystemBankBranch());
            systemBankBranchTrxValue.setSystemBankBranch(anICCSystemBankBranchTrxValue.getSystemBankBranch());
	        return systemBankBranchTrxValue;
		}
		catch(SystemBankBranchException se)
		{
			throw new SystemBankBranchException("Error in Create System Bank Branch Operation ");
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
