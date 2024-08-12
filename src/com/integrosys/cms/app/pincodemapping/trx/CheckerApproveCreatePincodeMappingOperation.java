package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingReplicationUtils;

public class CheckerApproveCreatePincodeMappingOperation extends
AbstractPincodeMappingTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_PINCODE_MAPPING;
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
		IPincodeMappingTrxValue trxValue = getPincodeMappingTrxValue(anITrxValue);
		try {
			trxValue = createActualPincodeMapping(trxValue);
			trxValue = updatePincodeMappingTrx(trxValue);
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
	 * @throws PincodeMappingException
	 */
	private IPincodeMappingTrxValue createActualPincodeMapping(
			IPincodeMappingTrxValue idxTrxValue) throws PincodeMappingException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		try {
			IPincodeMapping staging = idxTrxValue.getStagingPincodeMapping();
			// Replicating is necessary or else stale object error will arise
			IPincodeMapping replicatedPincodeMapping = PincodeMappingReplicationUtils
					.replicatePincodeMappingForCreateStagingCopy(staging);
			IPincodeMapping actual = getPincodeMappingBusManager().createPincodeMapping(
					replicatedPincodeMapping);
			idxTrxValue.setPincodeMapping(actual);
			idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
			getPincodeMappingBusManager().updatePincodeMapping(actual);
			return idxTrxValue;
		} catch (PincodeMappingException ex) {
			throw new TrxOperationException(ex);
		}
	}
}
