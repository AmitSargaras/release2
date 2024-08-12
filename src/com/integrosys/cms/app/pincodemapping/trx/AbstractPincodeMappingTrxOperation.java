package com.integrosys.cms.app.pincodemapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingBusManager;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractPincodeMappingTrxOperation extends CMSTrxOperation
implements ITrxRouteOperation {

	
	private IPincodeMappingBusManager pincodeMappingBusManager;

	private IPincodeMappingBusManager stagingPincodeMappingBusManager;

	public IPincodeMappingBusManager getPincodeMappingBusManager() {
		return pincodeMappingBusManager;
	}

	public void setPincodeMappingBusManager(IPincodeMappingBusManager pincodeMappingBusManager) {
		this.pincodeMappingBusManager = pincodeMappingBusManager;
	}

	public IPincodeMappingBusManager getStagingPincodeMappingBusManager() {
		return stagingPincodeMappingBusManager;
	}

	public void setStagingPincodeMappingBusManager(IPincodeMappingBusManager stagingPincodeMappingBusManager) {
		this.stagingPincodeMappingBusManager = stagingPincodeMappingBusManager;
	}
	
	/**
	 * 
	 * @param pincodeMappingTrxValue
	 * @return IPincodeMappingTrxValue
	 */

	protected IPincodeMappingTrxValue prepareTrxValue(
			IPincodeMappingTrxValue pincodeMappingTrxValue) {
		if (pincodeMappingTrxValue != null) {
			IPincodeMapping actual = pincodeMappingTrxValue.getPincodeMapping();
			IPincodeMapping staging = pincodeMappingTrxValue.getStagingPincodeMapping();
			if (actual != null) {
				pincodeMappingTrxValue.setReferenceID(String
						.valueOf(actual.getId()));
			} else {
				pincodeMappingTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				pincodeMappingTrxValue.setStagingReferenceID(String.valueOf(staging
						.getId()));
			} else {
				pincodeMappingTrxValue.setStagingReferenceID(null);
			}
			return pincodeMappingTrxValue;
		} else {
			throw new PincodeMappingException("ERROR-- Pincode is null");
		}
	}
	
	/**
	 * 
	 * @param pincodeMappingTrxValue
	 * @return IPincodeMappingTrxValue
	 * @throws TrxOperationException
	 */

	protected IPincodeMappingTrxValue updatePincodeMappingTrx(
			IPincodeMappingTrxValue pincodeMappingTrxValue)
			throws TrxOperationException {
		try {
			pincodeMappingTrxValue = prepareTrxValue(pincodeMappingTrxValue);
			ICMSTrxValue tempValue = super
					.updateTransaction(pincodeMappingTrxValue);
			OBPincodeMappingTrxValue newValue = new OBPincodeMappingTrxValue(tempValue);
			newValue.setPincodeMapping(pincodeMappingTrxValue.getPincodeMapping());
			newValue.setStagingPincodeMapping(pincodeMappingTrxValue
					.getStagingPincodeMapping());
			return newValue;
		}

		catch (Exception ex) {
			throw new PincodeMappingException("General Exception: " + ex.toString());
		}
	}
	
	/**
	 * 
	 * @param pincodeMappingTrxValue
	 * @return IPincodeMappingTrxValue
	 * @throws TrxOperationException
	 */
	protected IPincodeMappingTrxValue createStagingPincodeMapping(
			IPincodeMappingTrxValue pincodeMappingTrxValue)
			throws TrxOperationException {
		try {
			IPincodeMapping pincodeMapping = getStagingPincodeMappingBusManager()
					.createPincodeMapping(pincodeMappingTrxValue.getStagingPincodeMapping());
			pincodeMappingTrxValue.setStagingPincodeMapping(pincodeMapping);
			pincodeMappingTrxValue.setStagingReferenceID(String.valueOf(pincodeMapping
					.getId()));
			return pincodeMappingTrxValue;
		} catch (Exception ex) {
			throw new PincodeMappingException(
					"ERROR-- While creating Staging value");
		}
	}
	
	/**
	 * 
	 * @param anITrxValue
	 * @return IPincodeMappingTrxValue
	 * @throws TrxOperationException
	 */

	protected IPincodeMappingTrxValue getPincodeMappingTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IPincodeMappingTrxValue) anITrxValue;
		} catch (ClassCastException ex) {
			throw new PincodeMappingException(
					"The ITrxValue is not of type OBCPincodeMappingTrxValue: "
							+ ex.toString());
		}
	}
	
	/**
	 * 
	 * @param anOriginal
	 * @param aCopy
	 * @return IPincodeMappingTrxValue
	 * @throws TrxOperationException
	 */

	protected IPincodeMapping mergePincodeMapping(IPincodeMapping anOriginal,
			IPincodeMapping aCopy) throws TrxOperationException {
		aCopy.setId(anOriginal.getId());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}
	
	/**
	 * 
	 * @param value
	 * @return IPincodeMappingTrxValue
	 */

	protected ITrxResult prepareResult(IPincodeMappingTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
	
	
}
