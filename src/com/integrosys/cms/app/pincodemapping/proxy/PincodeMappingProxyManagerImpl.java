package com.integrosys.cms.app.pincodemapping.proxy;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingBusManager;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class PincodeMappingProxyManagerImpl implements IPincodeMappingProxyManager{

	private IPincodeMappingBusManager pincodeMappingBusManager;

	private IPincodeMappingBusManager stagingPincodeMappingBusManager;

	private ITrxControllerFactory trxControllerFactory;

	public IPincodeMappingBusManager getStagingPincodeMappingBusManager() {
		return stagingPincodeMappingBusManager;
	}

	public void setStagingPincodeMappingBusManager(IPincodeMappingBusManager stagingPincodeMappingBusManager) {
		this.stagingPincodeMappingBusManager = stagingPincodeMappingBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public IPincodeMappingBusManager getPincodeMappingBusManager() {
		return pincodeMappingBusManager;
	}

	public void setPincodeMappingBusManager(IPincodeMappingBusManager pincodeMappingBusManager) {
		this.pincodeMappingBusManager = pincodeMappingBusManager;
	}
	
	/**
	 * @return List of PincodeMapping
	 * 
	 *         This method access the Database through jdbc and fetch data.
	 */

	public List getAllActual() {
		return getPincodeMappingBusManager().getAllPincodeMapping();
	}
	/**
	 * @return PincodeMapping Trx Value
	 * @param Trx
	 *            object, PincodeMapping Trx object,PincodeMapping object
	 * 
	 *            This method Approves the Object passed by Maker
	 */
	
	public boolean isStateIdUnique(String stateId,String pincode) {
		
		return getPincodeMappingBusManager().isStateIdUnique(stateId,pincode);
	}
	
	public IPincodeMappingTrxValue checkerApprovePincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anIPincodeMappingTrxValue == null) {
			throw new PincodeMappingException(
					"The IPincodeMappingTrxValue to be updated is null!!!");
		}
		anIPincodeMappingTrxValue = formulateTrxValue(anITrxContext,
				anIPincodeMappingTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PINCODE_MAPPING);
		return operate(anIPincodeMappingTrxValue, param);
	}
	/**
	 * @return Pincode Mapping Trx Value
	 * @param Trx
	 *            object, Pincode Mapping Trx object,Pincode Mapping object
	 * 
	 *            This method Rejects the Object passed by Maker
	 */
	public IPincodeMappingTrxValue checkerRejectPincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anIPincodeMappingTrxValue == null) {
			throw new PincodeMappingException(
					"The IPincodeMappingTrxValue to be updated is null!!!");
		}
		anIPincodeMappingTrxValue = formulateTrxValue(anITrxContext,
				anIPincodeMappingTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PINCODE_MAPPING);
		return operate(anIPincodeMappingTrxValue, param);
	}

	public IPincodeMappingTrxValue makerCreatePincodeMapping(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}
		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPincodeMappingTrxValue, anICCPincodeMapping);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	/**
	 * @return Maker Save PincodeMapping
	 */
	/**
	 * @return Maker Create pincodeMapping
	 */
	public IPincodeMappingTrxValue makerCreateSavePincodeMapping(
			ITrxContext anITrxContext, IPincodeMapping anICCPincodeMapping)
			throws HolidayException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}

		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCPincodeMapping);
		trxValue.setStatus("PENDING_PERFECTION");
		trxValue.setFromState("DRAFT");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}

	/**
	 * @return Maker Create Pincode Mapping
	 */
	public IPincodeMappingTrxValue makerCreatePincodeMapping(ITrxContext anITrxContext,
			IPincodeMapping anICCPincodeMapping) throws HolidayException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}

		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCPincodeMapping);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	/**
	 * @return Maker Update Pincode Mapping
	 */

	public IPincodeMappingTrxValue makerUpdateSaveUpdatePincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}
		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPincodeMappingTrxValue, anICCPincodeMapping);
		// trxValue.setFromState("DRAFT");
		// trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	/**
	 * @return Maker Close draft PincodeMapping
	 */

	public IPincodeMappingTrxValue makerCloseDraftPincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anIPincodeMappingTrxValue == null) {
			throw new PincodeMappingException(
					"The IPincodeMappingTrxValue to be updated is null!!!");
		}
		anIPincodeMappingTrxValue = formulateTrxValue(anITrxContext,
				anIPincodeMappingTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_PINCODE_MAPPING);
		return operate(anIPincodeMappingTrxValue, param);
	}
	/**
	 * @return PincodeMapping Trx Value
	 * @param Trx
	 *            object, PincodeMapping Trx object,PincodeMapping object
	 * 
	 *            This method Close the Object rejected by Checker
	 */
	public IPincodeMappingTrxValue makerCloseRejectedPincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anIPincodeMappingTrxValue == null) {
			throw new PincodeMappingException(
					"The IPincodeMappingTrxValue to be updated is null!!!");
		}
		anIPincodeMappingTrxValue = formulateTrxValue(anITrxContext,
				anIPincodeMappingTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PINCODE_MAPPING);
		return operate(anIPincodeMappingTrxValue, param);
	}
	/**s
	 * @return PincodeMappingTrx Value
	 * @param PincodeMapping
	 *            Object This method fetches the Proper trx value according to
	 *            the Transaction Id passed as parameter.
	 * 
	 */
	public IPincodeMappingTrxValue getPincodeMappingByTrxID(String trxID)
			throws PincodeMappingException, TransactionException {
		IPincodeMappingTrxValue trxValue = new OBPincodeMappingTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PINCODE_MAPPING);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PINCODE_MAPPING_ID);
		return operate(trxValue, param);
	}
	/**
	 * @return updated PincodeMapping Trx value Object
	 * @param Trx
	 *            object, PincodeMapping Trx object,PincodeMapping object to be
	 *            updated
	 * 
	 *            The updated PincodeMapping object in stored in Staging Table of
	 *            PincodeMapping
	 */

	public IPincodeMappingTrxValue makerUpdatePincodeMapping(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}
		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPincodeMappingTrxValue, anICCPincodeMapping);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	/**
	 * @return updated PincodeMapping Trx value Object
	 * @param Trx
	 *            object, PincodeMapping Trx object,PincodeMapping object to be
	 *            updated After once rejected by Checker, if maker attempts to
	 *            update the same record its done by this method. The updated
	 *            PincodeMapping object in stored in Staging Table of PincodeMapping
	 */
	public IPincodeMappingTrxValue makerEditRejectedPincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anIPincodeMappingTrxValue, IPincodeMapping anIPincodeMapping)
			throws PincodeMappingException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anIPincodeMappingTrxValue == null) {
			throw new PincodeMappingException(
					"The IPincodeMappingTrxValue to be updated is null!!!");
		}
		if (anIPincodeMapping == null) {
			throw new PincodeMappingException(
					"The IPincodeMapping to be updated is null !!!");
		}
		anIPincodeMappingTrxValue = formulateTrxValue(anITrxContext,
				anIPincodeMappingTrxValue, anIPincodeMapping);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PINCODE_MAPPING);
		return operate(anIPincodeMappingTrxValue, param);
	}


	/**
	 * 
	 * @param anIPincodeMappingTrxValue
	 * @param anOBCMSTrxParameter
	 * @return IPincodeMappingTrxValue
	 * @throws PincodeMappingException
	 * @throws TrxParameterException
	 * @throws TransactionException
	 */
	private IPincodeMappingTrxValue operate(
			IPincodeMappingTrxValue anIPincodeMappingTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		ICMSTrxResult result = operateForResult(anIPincodeMappingTrxValue,
				anOBCMSTrxParameter);
		return (IPincodeMappingTrxValue) result.getTrxValue();
	}
	/**
	 * @return PincodeMappingTrx Value
	 * @param PincodeMapping
	 *            Object This method fetches the Proper trx value according to
	 *            the Object passed as parameter.
	 * 
	 */
	public IPincodeMappingTrxValue getPincodeMappingTrxValue(long aPincodeMapping)
			throws PincodeMappingException, TrxParameterException,
			TransactionException {
		if (aPincodeMapping == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			throw new PincodeMappingException("Invalid PincodeMappingId");
		}
		IPincodeMappingTrxValue trxValue = new OBPincodeMappingTrxValue();
		trxValue.setReferenceID(String.valueOf(aPincodeMapping));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_PINCODE_MAPPING);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		try {
			ITrxController controller = getTrxControllerFactory()
					.getController(anICMSTrxValue, anOBCMSTrxParameter);
			Validate
					.notNull(controller,
							"'controller' must not be null, check the controller factory");

			ITrxResult result = controller.operate(anICMSTrxValue,
					anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}

		catch (PincodeMappingException ex) {
			throw new PincodeMappingException(ex.toString());
		}catch(TrxParameterException te){
			 te.printStackTrace();
			 throw new PincodeMappingException("ERROR--Cannot update already deleted record");
		 }
	}
	public IPincodeMappingTrxValue makerEditSaveUpdatePincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}
		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPincodeMappingTrxValue, anICCPincodeMapping);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("ACTIVE");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	/**
	 * @return Maker Save PincodeMapping
	 */
	public IPincodeMappingTrxValue makerSavePincodeMapping(ITrxContext anITrxContext,
			IPincodeMapping anICCPincodeMapping) throws HolidayException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPincodeMapping to be updated is null !!!");
		}

		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCPincodeMapping);
		trxValue.setFromState("DRAFT");
		trxValue.setStatus("PENDING_PERFECTION");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	
	public IPincodeMappingTrxValue makerActivatePincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPropertyIdx to be updated is null !!!");
		}
		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPincodeMappingTrxValue, anICCPincodeMapping);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_ACTIVATE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	public IPincodeMappingTrxValue makerDeletePincodeMapping(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new PincodeMappingException("The ITrxContext is null!!!");
		}
		if (anICCPincodeMapping == null) {
			throw new PincodeMappingException(
					"The ICCPropertyIdx to be updated is null !!!");
		}
		IPincodeMappingTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCPincodeMappingTrxValue, anICCPincodeMapping);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_DELETE_PINCODE_MAPPING);
		return operate(trxValue, param);
	}
	
	private IPincodeMappingTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, IPincodeMapping anIPincodeMapping) {
		IPincodeMappingTrxValue ccPincodeMappingTrxValue = null;
		if (anICMSTrxValue != null) {
			ccPincodeMappingTrxValue = new OBPincodeMappingTrxValue(anICMSTrxValue);
		} else {
			ccPincodeMappingTrxValue = new OBPincodeMappingTrxValue();
		}
		ccPincodeMappingTrxValue = formulateTrxValue(anITrxContext,
				(IPincodeMappingTrxValue) ccPincodeMappingTrxValue);
		ccPincodeMappingTrxValue.setStagingPincodeMapping(anIPincodeMapping);
		return ccPincodeMappingTrxValue;
	}
	
	private IPincodeMappingTrxValue formulateTrxValue(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anIPincodeMappingTrxValue) {
		anIPincodeMappingTrxValue.setTrxContext(anITrxContext);
		anIPincodeMappingTrxValue
				.setTransactionType(ICMSConstant.INSTANCE_PINCODE_MAPPING);
		return anIPincodeMappingTrxValue;
	}
	
	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException {
		return getPincodeMappingBusManager().getPincodeMappingList(type, text);
	}
	
	/**
	 * @return the ArrayList
	 */
	public List getStateList() throws PincodeMappingException{
		return (List) getPincodeMappingBusManager().getStateList();
	}
	
	public Map<String,String> getActiveStatePinCodeMap(){
		return getPincodeMappingBusManager().getActiveStatePinCodeMap();
	}

	
}
