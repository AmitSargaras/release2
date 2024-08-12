package com.integrosys.cms.app.pincodemapping.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IPincodeMappingBusManager {

	/*IPincodeMapping getPincodeMappingById(long id) throws PincodeMappingException,
	TrxParameterException, TransactionException;
*/
	List getAllPincodeMapping();
	
	public boolean isStateIdUnique(String stateId,String pincode);
	public List getStateList();
		
	IPincodeMapping updatePincodeMapping(IPincodeMapping item) throws PincodeMappingException,
	TrxParameterException, TransactionException,
	ConcurrentUpdateException;

	IPincodeMapping updateToWorkingCopy(IPincodeMapping workingCopy,
			IPincodeMapping imageCopy) throws PincodeMappingException,
	TrxParameterException, TransactionException,
	ConcurrentUpdateException;

	IPincodeMapping createPincodeMapping(IPincodeMapping partyGroup)
	throws PincodeMappingException;

	IPincodeMapping deletePincodeMapping(IPincodeMapping item) throws PincodeMappingException,
	TrxParameterException, TransactionException,
	ConcurrentUpdateException;

	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException;

	IPincodeMapping getPincodeMappingById(long id) throws PincodeMappingException,
	TrxParameterException, TransactionException;
	
	public Map<String,String> getActiveStatePinCodeMap();
}
