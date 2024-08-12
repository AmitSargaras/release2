package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.bus.PartyGroupReplicationUtils;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;

/**
 * @author Bharat Waghela
 */

public class CheckerApproveCreatePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_PARTY_GROUP;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the
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
		try {
			trxValue = createActualPartyGroup(trxValue);
			trxValue = updatePartyGroupTrx(trxValue);
		} catch (TrxOperationException e) {
			throw new TrxOperationException(e.getMessage());
		} catch (Exception e) {
			throw new TrxOperationException(e.getMessage());
		}

		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual property index
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item trx value
	 * @throws ConcurrentUpdateException
	 * @throws TransactionException
	 * @throws TrxParameterException
	 * @throws PartyGroupException
	 */
	private IPartyGroupTrxValue createActualPartyGroup(
			IPartyGroupTrxValue idxTrxValue) throws PartyGroupException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		try {
			IPartyGroup staging = idxTrxValue.getStagingPartyGroup();
			// Replicating is necessary or else stale object error will arise
			IPartyGroup replicatedPartyGroup = PartyGroupReplicationUtils
					.replicatePartyGroupForCreateStagingCopy(staging);
			IPartyGroup actual = getPartyGroupBusManager().createPartyGroup(
					replicatedPartyGroup);
			idxTrxValue.setPartyGroup(actual);
			idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
			getPartyGroupBusManager().updatePartyGroup(actual);
			return idxTrxValue;
		} catch (PartyGroupException ex) {
			throw new TrxOperationException(ex);
		}
	}
}
