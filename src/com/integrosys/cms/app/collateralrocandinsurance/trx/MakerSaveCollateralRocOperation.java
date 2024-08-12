package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveCollateralRocOperation extends AbstractCollateralRocTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveCollateralRocOperation() {
		super();
	}
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_COLLATERAL_ROC;
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
	    ICollateralRocTrxValue trxValue = super.getCollateralRocTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingCollateralRoc()==null));

	    trxValue = createStagingCollateralRoc(trxValue);
		trxValue = createCollateralRocTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICollateralRocTrxValue createCollateralRocTransaction(ICollateralRocTrxValue anICCCollateralRocTrxValue) throws TrxOperationException,CollateralRocException
	{
		try
		{
            anICCCollateralRocTrxValue = prepareTrxValue(anICCCollateralRocTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCCollateralRocTrxValue);
            OBCollateralRocTrxValue collateralRocTrxValue = new OBCollateralRocTrxValue (trxValue);
            collateralRocTrxValue.setStagingCollateralRoc(anICCCollateralRocTrxValue.getStagingCollateralRoc());
            collateralRocTrxValue.setCollateralRoc(anICCCollateralRocTrxValue.getCollateralRoc());
	        return collateralRocTrxValue;
		}
		catch(CollateralRocException se)
		{
			throw new CollateralRocException("Error in Create CollateralRoc");
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
