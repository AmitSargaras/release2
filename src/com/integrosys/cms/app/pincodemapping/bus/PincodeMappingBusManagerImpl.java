package com.integrosys.cms.app.pincodemapping.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class PincodeMappingBusManagerImpl extends AbstractPincodeMappingBusManager
implements IPincodeMappingBusManager  {

	/**
	 * 
	 * This method give the entity name of staging pincode mapping table
	 * 
	 */

	public String getPincodeMappingName() {
		return IPincodeMappingDao.ACTUAL_PINCODE_MAPPING_NAME;
	}
	/**
	 * @return List of all authorized Pincode Mapping
	 */

	public List getAllPincodeMapping() {
		return getPincodeMappingJdbc().getAllPincodeMapping();
	}

	/**
	 * @return WorkingCopy-- updated pincode mapping Object
	 * @param working
	 *            copy-- Entry of Actual Table
	 * @param image
	 *            Copy-- Entry Of Staging Table
	 * 
	 *            After Approval From Checker the Working Copy is updated as per
	 *            the image copy.
	 * 
	 */

	public IPincodeMapping updateToWorkingCopy(IPincodeMapping workingCopy,
			IPincodeMapping imageCopy) throws PincodeMappingException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		IPincodeMapping updated;
		try {
			workingCopy.setPincode(imageCopy.getPincode());
			workingCopy.setStateId(imageCopy.getStateId());
			
			// AccessorUtil.copyValue(imageCopy, workingCopy, new String[] {
			// "Id" });
			updated = updatePincodeMapping(workingCopy);
		} catch (PincodeMappingException e) {
			throw new PincodeMappingException(
					"Error while Copying copy to main file");
		}

		return updatePincodeMapping(updated);
	}
	
	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException {
		return getPincodeMappingDao().getPincodeMappingList(type, text);
	}
	
	/**
	 * @return the ArrayList
	 */
	public List getStateList() throws PincodeMappingException{
		return (List)getPincodeMappingDao().getStateList();
	}
	
	public Map<String,String> getActiveStatePinCodeMap(){
		return getPincodeMappingJdbc().getActiveStatePinCodeMap();
		
	}
	
}
