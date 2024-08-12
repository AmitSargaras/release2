package com.integrosys.cms.app.image.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * @author govind.sahu
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateImageUploadOperation extends AbstractImageUploadTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateImageUploadOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_SYSTEM_BANK;
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
		
		IImageUploadTrxValue trxValue = getImageUploadTrxValue(anITrxValue);
		trxValue = updateActualImageUpload(trxValue);
		trxValue = updateImageUploadTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IImageUploadTrxValue updateActualImageUpload(IImageUploadTrxValue anICCImageUploadTrxValue)
			throws TrxOperationException {
		try {
			IImageUploadAdd staging = anICCImageUploadTrxValue.getStagingImageUploadAdd();
			IImageUploadAdd actual = anICCImageUploadTrxValue.getImageUploadAdd();

			IImageUploadAdd updatedImageUploadAdd = getImageUploadBusManager().updateToWorkingCopy(actual, staging);
			anICCImageUploadTrxValue.setImageUploadAdd(updatedImageUploadAdd);

			return anICCImageUploadTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
