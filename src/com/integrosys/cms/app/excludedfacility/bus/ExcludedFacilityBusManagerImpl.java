package com.integrosys.cms.app.excludedfacility.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;

public class ExcludedFacilityBusManagerImpl extends AbstractExcludedFacilityBusManager implements IExcludedFacilityBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging Excluded Facility table  
     * 
     */
	
	
	public String getExcludedFacilityName() {
		return IExcludedFacilityDao.ACTUAL_EXCLUDED_FACILITY_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated Excluded Facility Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IExcludedFacility updateToWorkingCopy(IExcludedFacility workingCopy, IExcludedFacility imageCopy)
	throws ExcludedFacilityException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IExcludedFacility updated;
		try{
			/*workingCopy.setExcludedFacilityCategory(imageCopy.getExcludedFacilityCategory());*/
			workingCopy.setExcludedFacilityDescription(imageCopy.getExcludedFacilityDescription());
			
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			//AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id" });
			updated = updateExcludedFacility(workingCopy);
			return updateExcludedFacility(updated);
		}catch (Exception e) {
			throw new ExcludedFacilityException("Error while Copying copy to main file");
		}
	}
	
	/**
	 * @return List of all authorized ExcludedFacility
	 */
	

	public SearchResult getAllExcludedFacility()throws ExcludedFacilityException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getExcludedFacilityJdbc().getAllExcludedFacility();
	}
	
	public boolean isExcludedFacilityDescriptionUnique(String excludedFacilityDescription) {
		return getExcludedFacilityDao().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);
	}
	
	public SearchResult getSearchedExcludedFacility(String type,String text)throws ExcludedFacilityException,TrxParameterException,TransactionException {
		 return getExcludedFacilityDao().getSearchedExcludedFacility(type,text);
	}

}
