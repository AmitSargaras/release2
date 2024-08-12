package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;

/**
 * @author Santosh.Sonmankar
 * Checker approve Operation to approve update made by maker
 */
public class CheckerApproveUpdateProductMasterOperation extends AbstractProductMasterTrxOperation{
	
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateProductMasterOperation() {
		super();
	}
	
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_PRODUCT_MASTER;
    }
	
	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IProductMasterTrxValue trxValue = getProductMasterTrxValue(anITrxValue);
		trxValue = updateActualProductMaster(trxValue);
		trxValue = updateProductMasterTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IProductMasterTrxValue updateActualProductMaster(IProductMasterTrxValue anICCProductMasterTrxValue)
			throws TrxOperationException {
		try {
			IProductMaster staging = anICCProductMasterTrxValue.getStagingProductMaster();
			IProductMaster actual = anICCProductMasterTrxValue.getProductMaster();

			IProductMaster updatedProductMaster = getProductMasterBusManager().updateToWorkingCopy(actual, staging);
			anICCProductMasterTrxValue.setProductMaster(updatedProductMaster);

			return anICCProductMasterTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}
