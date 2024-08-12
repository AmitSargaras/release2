package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreatePincodeMappingOperation extends
AbstractPincodeMappingTrxOperation {

	/**
	 * Default Constructor
	 */
	public MakerCreatePincodeMappingOperation() {
		super();
	}
	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_PINCODE_MAPPING;
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
		IPincodeMappingTrxValue trxValue = super.getPincodeMappingTrxValue(anITrxValue);
		DefaultLogger.debug(this, "trxValue is null ? " + (trxValue == null));
		DefaultLogger.debug(this,
				" ---- trxValue.getStagingPrIdx() is null ? ----- "
						+ (trxValue.getStagingPincodeMapping() == null));

		trxValue = createStagingPincodeMapping(trxValue);
		trxValue = createPincodeMappingTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	/**
	 * Create a property index transaction
	 * 
	 * @param anICCPropertyIdxTrxValue
	 *            of ICCPropertyIdxTrxValue type
	 * @return ICCPropertyIdxTrxValue
	 * @throws TrxOperationException
	 *             if there is any processing errors
	 */
	private IPincodeMappingTrxValue createPincodeMappingTransaction(
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue)
			throws TrxOperationException, PincodeMappingException {
		try {
			anICCPincodeMappingTrxValue = prepareTrxValue(anICCPincodeMappingTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCPincodeMappingTrxValue);
			OBPincodeMappingTrxValue pincodeMappingTrxValue = new OBPincodeMappingTrxValue(
					trxValue);
			pincodeMappingTrxValue.setStagingPincodeMapping(anICCPincodeMappingTrxValue
					.getStagingPincodeMapping());
			pincodeMappingTrxValue.setPincodeMapping(anICCPincodeMappingTrxValue
					.getPincodeMapping());
			return pincodeMappingTrxValue;
		} catch (PincodeMappingException se) {
			throw new PincodeMappingException(
					"Error in Create PincodeMapping Operation ");
		} catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		} catch (Exception ex) {
			throw new TrxOperationException("General Exception: "
					+ ex.toString());
		}
	}
}
