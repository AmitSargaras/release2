package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerCreateCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreateCollateralNewMasterOperation()
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
		return ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_NEW_MASTER;
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
	    ICollateralNewMasterTrxValue trxValue = super.getCollateralNewMasterTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingCollateralNewMaster()==null));

	    trxValue = createStagingCollateralNewMaster(trxValue);
		trxValue = createCollateralNewMasterTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICollateralNewMasterTrxValue createCollateralNewMasterTransaction(ICollateralNewMasterTrxValue anICCCollateralNewMasterTrxValue) throws TrxOperationException,CollateralNewMasterException
	{
		try
		{
            anICCCollateralNewMasterTrxValue = prepareTrxValue(anICCCollateralNewMasterTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCCollateralNewMasterTrxValue);
            OBCollateralNewMasterTrxValue collateralNewMasterTrxValue = new OBCollateralNewMasterTrxValue (trxValue);
            collateralNewMasterTrxValue.setStagingCollateralNewMaster (anICCCollateralNewMasterTrxValue.getStagingCollateralNewMaster());
            collateralNewMasterTrxValue.setCollateralNewMaster(anICCCollateralNewMasterTrxValue.getCollateralNewMaster());
	        return collateralNewMasterTrxValue;
		}
		catch(CollateralNewMasterException se)
		{
			throw new CollateralNewMasterException("Error in Create System Bank Branch Operation ");
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
