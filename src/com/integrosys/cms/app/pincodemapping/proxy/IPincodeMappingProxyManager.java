package com.integrosys.cms.app.pincodemapping.proxy;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;


public interface IPincodeMappingProxyManager {

	public boolean isStateIdUnique(String stateId,String pincode);
	public List getStateList();
	
	
	public IPincodeMappingTrxValue makerCloseRejectedPincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;

	public List getAllActual() throws PincodeMappingException,
	TrxParameterException, TransactionException;
	
	public IPincodeMappingTrxValue makerCreatePincodeMapping(ITrxContext anITrxContext,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;

	public IPincodeMappingTrxValue makerCreateSavePincodeMapping(
			ITrxContext anITrxContext, IPincodeMapping anICCPincodeMapping)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;
	public IPincodeMappingTrxValue makerSavePincodeMapping(ITrxContext anITrxContext,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;
	
	public IPincodeMappingTrxValue makerEditSaveUpdatePincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;
	
	public IPincodeMappingTrxValue makerUpdatePincodeMapping(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;
	
	public IPincodeMappingTrxValue makerCreatePincodeMapping(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;

	public IPincodeMappingTrxValue getPincodeMappingByTrxID(String aTrxID)
			throws PincodeMappingException, TransactionException,
			CommandProcessingException;

	public IPincodeMappingTrxValue checkerApprovePincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;
	public IPincodeMappingTrxValue checkerRejectPincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;
	public IPincodeMappingTrxValue makerUpdateSaveUpdatePincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;
	public IPincodeMappingTrxValue makerEditRejectedPincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anIPincodeMappingTrxValue, IPincodeMapping anPincodeMapping)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;
	public IPincodeMappingTrxValue makerCloseDraftPincodeMapping(
			ITrxContext anITrxContext, IPincodeMappingTrxValue anIPincodeMappingTrxValue)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;
	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException;

	public IPincodeMappingTrxValue getPincodeMappingTrxValue(long aPincodeMappingId)
			throws PincodeMappingException, TrxParameterException,
			TransactionException;
	public IPincodeMappingTrxValue makerActivatePincodeMapping(
			ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;

	public IPincodeMappingTrxValue makerDeletePincodeMapping(ITrxContext anITrxContext,
			IPincodeMappingTrxValue anICCPincodeMappingTrxValue,
			IPincodeMapping anICCPincodeMapping) throws PincodeMappingException,
			TrxParameterException, TransactionException;
}
