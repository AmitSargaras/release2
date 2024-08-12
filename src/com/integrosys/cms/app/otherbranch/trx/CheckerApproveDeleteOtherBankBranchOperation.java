package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author dattatray.thorat
 * Checker approve Operation to approve delete made by maker
 */

public class CheckerApproveDeleteOtherBankBranchOperation extends AbstractOtherBankBranchTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveDeleteOtherBankBranchOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_OTHER_BANK_BRANCH;
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
		
		IOtherBankBranchTrxValue trxValue = getOtherBankBranchTrxValue(anITrxValue);
		trxValue = updateActualOtherBankBranch(trxValue);
		trxValue = updateOtherBankBranchTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IOtherBankBranchTrxValue updateActualOtherBankBranch(IOtherBankBranchTrxValue anICCOtherBankTrxValue)
			throws TrxOperationException {
		try {
			IOtherBranch staging = anICCOtherBankTrxValue.getStagingOtherBranch();
			IOtherBranch actual = anICCOtherBankTrxValue.getOtherBranch();

			IOtherBranch updatedOtherBank = getOtherBranchBusManager().deleteOtherBranch(actual);
			anICCOtherBankTrxValue.setOtherBranch(updatedOtherBank);

			return anICCOtherBankTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
