package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateCaseBranchOperation extends AbstractCaseBranchTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCaseBranchOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CASEBRANCH;
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
		ICaseBranchTrxValue trxValue = getCaseBranchTrxValue(anITrxValue);
		trxValue = updateActualCaseBranch(trxValue);
		trxValue = updateCaseBranchTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICaseBranchTrxValue updateActualCaseBranch(ICaseBranchTrxValue anICCCaseBranchTrxValue)
			throws TrxOperationException {
		try {
			ICaseBranch staging = anICCCaseBranchTrxValue.getStagingCaseBranch();
			ICaseBranch actual = anICCCaseBranchTrxValue.getCaseBranch();

			ICaseBranch updatedCaseBranch = getCaseBranchBusManager().updateToWorkingCopy(actual, staging);
			anICCCaseBranchTrxValue.setCaseBranch(updatedCaseBranch);

			return anICCCaseBranchTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
