package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingReplicationUtils;

public class MakerSaveUpdatePincodeMappingOperation extends
AbstractPincodeMappingTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdatePincodeMappingOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PINCODE_MAPPING;
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
		IPincodeMappingTrxValue idxTrxValue = getPincodeMappingTrxValue(anITrxValue);
		IPincodeMapping stage = idxTrxValue.getStagingPincodeMapping();
		IPincodeMapping replicatedPincodeMapping = PincodeMappingReplicationUtils
				.replicatePincodeMappingForCreateStagingCopy(stage);
		idxTrxValue.setStagingPincodeMapping(replicatedPincodeMapping);

		IPincodeMappingTrxValue trxValue = createStagingPincodeMapping(idxTrxValue);
		trxValue = updatePincodeMappingTrx(trxValue);
		return super.prepareResult(trxValue);
	}


}
