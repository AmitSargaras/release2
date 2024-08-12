package com.integrosys.cms.app.systemBank.bus;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author $Author: Abhijit R $<br>
 * 
 * Bus Manager Imlication  declares the methods used by Dao and Jdbc
 */

public class SystemBankBusManagerImpl extends AbstractSystemBankBusManager implements ISystemBankBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank table  
     * 
     */
	
	
	
	public String getSystemBankName() {
		return ISystemBankDao.ACTUAL_SYSTEM_BANK_NAME;
	}

	

	/**
	 * @return WorkingCopy-- updated system Bank Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	

	
	public ISystemBank updateToWorkingCopy(ISystemBank workingCopy, ISystemBank imageCopy)
	throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		ISystemBank updated;
		try{
		workingCopy.setAddress(imageCopy.getAddress());
		workingCopy.setCityTown(imageCopy.getCityTown());
		workingCopy.setContactMail(imageCopy.getContactMail());
		workingCopy.setContactNumber(imageCopy.getContactNumber());
		workingCopy.setFaxNumber(imageCopy.getFaxNumber());
		//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
		updated = updateSystemBank(workingCopy);
		return updated;
	}catch (SystemBankException e) {
		throw new SystemBankException("Error while Copying copy to main file");
	}
	}

	
	
}
