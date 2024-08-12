package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.OtherBankReplicationUtils;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author dattatray.thorat
 * Checker approve Operation to approve update made by maker
 */

public class CheckerApproveCreateOtherBankOperation extends AbstractOtherBankTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateOtherBankOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_OTHER_BANK;
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
		
		IOtherBankTrxValue trxValue = getOtherBankTrxValue(anITrxValue);
		trxValue = updateActualOtherBank(trxValue);
		trxValue = updateOtherBankTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IOtherBankTrxValue updateActualOtherBank(IOtherBankTrxValue anICCOtherBankTrxValue)
			throws TrxOperationException {
		try {
			IOtherBank staging = anICCOtherBankTrxValue.getStagingOtherBank();
			IOtherBank replicatedOtherBank = OtherBankReplicationUtils.replicateOtherBankForCreateStagingCopy(staging);
			IOtherBank updatedOtherBank = getOtherBankBusManager().createOtherBank(replicatedOtherBank);
			anICCOtherBankTrxValue.setOtherBank(updatedOtherBank);

			return anICCOtherBankTrxValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
}
