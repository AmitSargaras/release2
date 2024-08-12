package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveProductMasterOperation extends AbstractProductMasterTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerSaveProductMasterOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_PRODUCT_MASTER;
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
	    IProductMasterTrxValue trxValue = super.getProductMasterTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingProductMaster()==null));

	    trxValue = createStagingProductMaster(trxValue);
		trxValue = createProductMasterTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	* Create a property index transaction
	* @param anICCProductMasterIdxTrxValue of ICCProductMasterIdxTrxValue type
	* @return ICCProductMasterIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IProductMasterTrxValue createProductMasterTransaction(IProductMasterTrxValue anICCProductMasterTrxValue) throws TrxOperationException,ProductMasterException
	{
		try
		{
            anICCProductMasterTrxValue = prepareTrxValue(anICCProductMasterTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCProductMasterTrxValue);
            OBProductMasterTrxValue productMasterTrxValue = new OBProductMasterTrxValue (trxValue);
            productMasterTrxValue.setStagingProductMaster(anICCProductMasterTrxValue.getStagingProductMaster());
            productMasterTrxValue.setProductMaster(anICCProductMasterTrxValue.getProductMaster());
	        return productMasterTrxValue;
		}
		catch(ProductMasterException se)
		{
			throw new ProductMasterException("Error in Create ProductMaster");
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
