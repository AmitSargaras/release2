package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;

/**
 * @author abhijit.rudrakshawar Maker Edit Rejected operation to update rejected
 *         record by checker
 */
public class MakerEditRejectedEnablePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedEnablePartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_ENABLE_PARTY_GROUP;
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
		IPartyGroup replicatedPartyGroup = new OBPartyGroup();
		replicatedPartyGroup.setPartyCode(stage.getPartyCode());
		replicatedPartyGroup.setPartyName(stage.getPartyName());
		replicatedPartyGroup.setGroupExpLimit(stage.getGroupExpLimit());
		replicatedPartyGroup.setId(stage.getId());
		replicatedPartyGroup.setCreateBy(stage.getCreateBy());
		replicatedPartyGroup.setCreationDate(stage.getCreationDate());
		replicatedPartyGroup.setDeprecated(stage.getDeprecated());
		replicatedPartyGroup.setLastUpdateBy(stage.getLastUpdateBy());
		replicatedPartyGroup.setLastUpdateDate(stage.getLastUpdateDate());
		replicatedPartyGroup.setStatus(stage.getStatus());
		replicatedPartyGroup.setVersionTime(stage.getVersionTime());

		idxTrxValue.setStagingPartyGroup(replicatedPartyGroup);

		IPartyGroupTrxValue trxValue = createStagingPartyGroup(idxTrxValue);
		trxValue = updatePartyGroupTrx(trxValue);
		return super.prepareResult(trxValue);
	}

}
