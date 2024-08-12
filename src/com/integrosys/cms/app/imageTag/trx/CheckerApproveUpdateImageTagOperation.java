package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateImageTagOperation extends AbstractImageTagTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateImageTagOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_SYSTEM_BANK_BRANCH;
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
		IImageTagTrxValue trxValue = getImageTagTrxValue(anITrxValue);
		trxValue = updateActualImageTag(trxValue);
		trxValue = updateImageTagTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IImageTagTrxValue updateActualImageTag(IImageTagTrxValue imageTagTrxValue)
			throws TrxOperationException {
		try {
			IImageTagDetails staging = imageTagTrxValue.getStagingImageTagDetails();
			IImageTagDetails actual = imageTagTrxValue.getImageTagDetails();

			IImageTagDetails updatedImageTag = getImageTagBusManager().updateToWorkingCopy(actual, staging);
			imageTagTrxValue.setImageTagDetails(updatedImageTag);

			return imageTagTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
