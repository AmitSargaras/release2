package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateCaseCreationBranchOperation extends AbstractCaseCreationTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCaseCreationBranchOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CASECREATION_BRANCH;
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
		ICaseCreationTrxValue trxValue = getCaseCreationTrxValue(anITrxValue);
		trxValue = updateActualCaseCreation(trxValue);
		trxValue = updateCaseCreationTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ICaseCreationTrxValue updateActualCaseCreation(ICaseCreationTrxValue anICCCaseCreationTrxValue)
			throws TrxOperationException {
		try {
			ICaseCreation staging = anICCCaseCreationTrxValue.getStagingCaseCreation();
			ICaseCreation actual = anICCCaseCreationTrxValue.getCaseCreation();

			ICaseCreation updatedCaseCreation = getCaseCreationBusManager().updateToWorkingCopy(actual, staging);
			anICCCaseCreationTrxValue.setCaseCreation(updatedCaseCreation);

			return anICCCaseCreationTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
