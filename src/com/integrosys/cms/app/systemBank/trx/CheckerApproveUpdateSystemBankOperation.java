package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;

/**
 * @author abhijit.rudrakshawar
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveUpdateSystemBankOperation extends AbstractSystemBankTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateSystemBankOperation() {
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
		
		ISystemBankTrxValue trxValue = getSystemBankTrxValue(anITrxValue);
		trxValue = updateActualSystemBank(trxValue);
		trxValue = updateSystemBankTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private ISystemBankTrxValue updateActualSystemBank(ISystemBankTrxValue anICCSystemBankTrxValue)
			throws TrxOperationException {
		try {
			ISystemBank staging = anICCSystemBankTrxValue.getStagingSystemBank();
			ISystemBank actual = anICCSystemBankTrxValue.getSystemBank();

			ISystemBank updatedSystemBank = getSystemBankBusManager().updateToWorkingCopy(actual, staging);
			anICCSystemBankTrxValue.setSystemBank(updatedSystemBank);

			return anICCSystemBankTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
