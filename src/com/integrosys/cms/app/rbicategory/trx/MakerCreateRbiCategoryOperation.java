package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;


public class MakerCreateRbiCategoryOperation extends AbstractRbiCategoryTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreateRbiCategoryOperation()
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
		return ICMSConstant.ACTION_MAKER_CREATE_RBI_CATEGORY;
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
	    IRbiCategoryTrxValue trxValue = super.getRbiCategoryTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingRbiCategory()==null));

	    trxValue = createStagingRbiCategory(trxValue);
		trxValue = createRbiCategoryTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IRbiCategoryTrxValue createRbiCategoryTransaction(IRbiCategoryTrxValue anICCRbiCategoryTrxValue) throws TrxOperationException,RbiCategoryException
	{
		try
		{
            anICCRbiCategoryTrxValue = prepareTrxValue(anICCRbiCategoryTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCRbiCategoryTrxValue);
            OBRbiCategoryTrxValue systemBankBranchTrxValue = new OBRbiCategoryTrxValue (trxValue);
            systemBankBranchTrxValue.setStagingRbiCategory (anICCRbiCategoryTrxValue.getStagingRbiCategory());
            systemBankBranchTrxValue.setRbiCategory(anICCRbiCategoryTrxValue.getRbiCategory());
	        return systemBankBranchTrxValue;
		}
		catch(RbiCategoryException se)
		{
			throw new RbiCategoryException("Error in Create Rbi Category Operation ");
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
