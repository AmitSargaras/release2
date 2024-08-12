package com.integrosys.cms.app.pincodemapping.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class PincodeMappingBusManagerStagingImpl extends
AbstractPincodeMappingBusManager {

	/**
	 * 
	 * This method give the entity name of staging pincode mapping table
	 * 
	 */

	public String getPincodeMappingName() {
		return IPincodeMappingDao.STAGE_PINCODE_MAPPING_NAME;
	}
	
	/**
	 * This method returns exception as staging Pincode mapping can never be working
	 * copy
	 */

	public IPincodeMapping updateToWorkingCopy(IPincodeMapping workingCopy,
			IPincodeMapping imageCopy) throws PincodeMappingException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		throw new IllegalStateException(
				"'updateToWorkingCopy' should not be implemented.");
	}

	public List getAllPincodeMapping() {
		return null;
	}

	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException {
		return null;
	}

	public Map<String, String> getActiveStatePinCodeMap() {
		return null;
	}
	
}
