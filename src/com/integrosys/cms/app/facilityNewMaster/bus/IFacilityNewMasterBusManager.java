package com.integrosys.cms.app.facilityNewMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
/**
 * @author  Abhijit R. 
 */
public interface IFacilityNewMasterBusManager {
	

		List searchFacilityNewMaster(String login) throws FacilityNewMasterException,TrxParameterException,TransactionException;
		IFacilityNewMaster getFacilityNewMasterById(long id) throws FacilityNewMasterException,TrxParameterException,TransactionException;
	
		SearchResult getAllFacilityNewMaster()throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getFilteredActualFacilityNewMaster(String code,String name,String category,String type,String system,String line)throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllFacilityNewMaster(String searchBy,String searchText)throws FacilityNewMasterException,TrxParameterException,TransactionException;
		IFacilityNewMaster updateFacilityNewMaster(IFacilityNewMaster item) throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IFacilityNewMaster deleteFacilityNewMaster(IFacilityNewMaster item) throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IFacilityNewMaster updateToWorkingCopy(IFacilityNewMaster workingCopy, IFacilityNewMaster imageCopy) throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IFacilityNewMaster createFacilityNewMaster(IFacilityNewMaster facilityNewMaster)throws FacilityNewMasterException;
		boolean isUniqueCode(String lineNumber,String system) throws FacilityNewMasterException,TrxParameterException,TransactionException;
		boolean isUniqueFacilityCode(String facilityCode) throws FacilityNewMasterException,TrxParameterException,TransactionException;
		
		
		public boolean isFacilityNameUnique(String facilityName);
		public boolean isFacilityCpsIdUnique(String cpsId);
		
		
}
