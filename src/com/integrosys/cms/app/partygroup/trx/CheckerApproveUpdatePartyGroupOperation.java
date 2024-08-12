package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;

/**
 * @author Bharat Waghela Checker approve Operation to approve update made by
 *         maker
 */

public class CheckerApproveUpdatePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdatePartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_PARTY_GROUP;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {

		IPartyGroupTrxValue trxValue = getPartyGroupTrxValue(anITrxValue);
		trxValue = updateActualPartyGroup(trxValue);
		trxValue = updatePartyGroupTrx(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual property index
	 * 
	 * @param anICCDocumentLocationTrxValue
	 * @return
	 * @throws TrxOperationException
	 */
	private IPartyGroupTrxValue updateActualPartyGroup(
			IPartyGroupTrxValue anICCPartyGroupTrxValue)
			throws TrxOperationException {
		try {
			IPartyGroup staging = anICCPartyGroupTrxValue
					.getStagingPartyGroup();
			IPartyGroup actual = anICCPartyGroupTrxValue.getPartyGroup();

			IPartyGroup updatedPartyGroup = getPartyGroupBusManager()
					.updateToWorkingCopy(actual, staging);
			anICCPartyGroupTrxValue.setPartyGroup(updatedPartyGroup);

			return anICCPartyGroupTrxValue;
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}

	}
}
