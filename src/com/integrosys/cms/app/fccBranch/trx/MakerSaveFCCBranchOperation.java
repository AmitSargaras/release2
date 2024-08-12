package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerSaveFCCBranchOperation extends AbstractFCCBranchTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveFCCBranchOperation()
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
		return ICMSConstant.ACTION_MAKER_SAVE_FCCBRANCH;
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
	    IFCCBranchTrxValue trxValue = super.getFCCBranchTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingFCCBranch()==null));

	    trxValue = createStagingFCCBranch(trxValue);
		trxValue = createFCCBranchTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IFCCBranchTrxValue createFCCBranchTransaction(IFCCBranchTrxValue anICCFCCBranchTrxValue) throws TrxOperationException,FCCBranchException
	{
		try
		{
            anICCFCCBranchTrxValue = prepareTrxValue(anICCFCCBranchTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCFCCBranchTrxValue);
            OBFCCBranchTrxValue fccBranchTrxValue = new OBFCCBranchTrxValue (trxValue);
            fccBranchTrxValue.setStagingFCCBranch (anICCFCCBranchTrxValue.getStagingFCCBranch());
            fccBranchTrxValue.setFCCBranch(anICCFCCBranchTrxValue.getFCCBranch());
	        return fccBranchTrxValue;
		}
		catch(FCCBranchException se)
		{
			throw new FCCBranchException("Error in Create FCCBranch Operation ");
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
