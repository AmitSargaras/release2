package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupReplicationUtils;

/**
 * 
 * @author bharat Waghela
 */
public class MakerSaveUpdatePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdatePartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PARTY_GROUP;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
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
