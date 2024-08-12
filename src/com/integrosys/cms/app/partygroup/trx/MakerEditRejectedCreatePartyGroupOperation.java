package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.PartyGroupReplicationUtils;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;

/**
 * Title: CLIMS Description: Copyright: Integro Technologies Sdn Bhd Author:
 * Andy Wong Date: Jan 18, 2008
 */
public class MakerEditRejectedCreatePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedCreatePartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_PARTY_GROUP;
	}

	/**
	 * Process the transaction 1. Create Staging record 2. Update the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IPartyGroupTrxValue idxTrxValue = getPartyGroupTrxValue(anITrxValue);
		IPartyGroup stage = idxTrxValue.getStagingPartyGroup();
		IPartyGroup replicatedPartygroup = PartyGroupReplicationUtils
				.replicatePartyGroupForCreateStagingCopy(stage);
		idxTrxValue.setStagingPartyGroup(replicatedPartygroup);

		IPartyGroupTrxValue trxValue = createStagingPartyGroup(idxTrxValue);
		trxValue = updatePartyGroupTrx(trxValue);
		return super.prepareResult(trxValue);
	}
}
