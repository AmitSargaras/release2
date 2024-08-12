package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;

public class MakerEditRejectedEnablePincodeMappingOperation extends
AbstractPincodeMappingTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerEditRejectedEnablePincodeMappingOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_ENABLE_PINCODE_MAPPING;
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
		IPincodeMappingTrxValue idxTrxValue = getPincodeMappingTrxValue(anITrxValue);
		IPincodeMapping stage = idxTrxValue.getStagingPincodeMapping();
		IPincodeMapping replicatedPincodeMapping = new OBPincodeMapping();
		replicatedPincodeMapping.setStateId(stage.getStateId());
		replicatedPincodeMapping.setPincode(stage.getPincode());
		replicatedPincodeMapping.setId(stage.getId());
		replicatedPincodeMapping.setCreatedBy(stage.getCreatedBy());
		replicatedPincodeMapping.setCreationDate(stage.getCreationDate());
		replicatedPincodeMapping.setDeprecated(stage.getDeprecated());
		replicatedPincodeMapping.setLastUpdateBy(stage.getLastUpdateBy());
		replicatedPincodeMapping.setLastUpdateDate(stage.getLastUpdateDate());
		replicatedPincodeMapping.setStatus(stage.getStatus());
		replicatedPincodeMapping.setVersionTime(stage.getVersionTime());

		idxTrxValue.setStagingPincodeMapping(replicatedPincodeMapping);

		IPincodeMappingTrxValue trxValue = createStagingPincodeMapping(idxTrxValue);
		trxValue = updatePincodeMappingTrx(trxValue);
		return super.prepareResult(trxValue);
	}

}
