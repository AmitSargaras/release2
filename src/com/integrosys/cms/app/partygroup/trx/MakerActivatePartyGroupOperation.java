package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupReplicationUtils;
import com.integrosys.cms.app.partygroup.trx.AbstractPartyGroupTrxOperation;
import com.integrosys.cms.app.partygroup.trx.IPartyGroupTrxValue;

/**
 * @author Bharat Waghela Maker Update operation to update party group
 */
public class MakerActivatePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerActivatePartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_ACTIVATE_PARTY_GROUP;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * 
	 * @param anITrxValue
	 *            is of type ITrxValue
	 * @return ITrxValue
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		IPartyGroupTrxValue trxValue = getPartyGroupTrxValue(anITrxValue);
		IPartyGroup staging = trxValue.getStagingPartyGroup();
		try {
			// if (staging != null) {
			//                
			//
			// if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
			// ICMSTrxValue parentTrx =
			// getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()),
			// ICMSConstant.INSTANCE_SYSTEM_BANK);
			// trxValue.setTrxReferenceID(parentTrx.getTransactionID());
			// }
			//                
			// }
			return trxValue;
		}

		catch (Exception ex) {
			throw new TrxOperationException("Exception in preProcess: "
					+ ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IPartyGroupTrxValue idxTrxValue = getPartyGroupTrxValue(anITrxValue);
		IPartyGroup stage = idxTrxValue.getStagingPartyGroup();
		IPartyGroup replicatedPartyGroup = PartyGroupReplicationUtils
				.replicatePartyGroupForCreateStagingCopy(stage);
		idxTrxValue.setStagingPartyGroup(replicatedPartyGroup);

		IPartyGroupTrxValue trxValue = createStagingPartyGroup(idxTrxValue);
		trxValue = updatePartyGroupTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
