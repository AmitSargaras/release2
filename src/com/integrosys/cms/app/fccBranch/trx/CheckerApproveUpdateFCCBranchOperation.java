package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateFCCBranchOperation extends AbstractFCCBranchTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateFCCBranchOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_FCCBRANCH;
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
		IFCCBranchTrxValue trxValue = getFCCBranchTrxValue(anITrxValue);
		trxValue = updateActualFCCBranch(trxValue);
		trxValue = updateFCCBranchTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IFCCBranchTrxValue updateActualFCCBranch(IFCCBranchTrxValue anICCFCCBranchTrxValue)
			throws TrxOperationException {
		try {
			IFCCBranch staging = anICCFCCBranchTrxValue.getStagingFCCBranch();
			IFCCBranch actual = anICCFCCBranchTrxValue.getFCCBranch();

			IFCCBranch updatedFCCBranch = getFccBranchBusManager().updateToWorkingCopy(actual, staging);
			anICCFCCBranchTrxValue.setFCCBranch(updatedFCCBranch);

			return anICCFCCBranchTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
