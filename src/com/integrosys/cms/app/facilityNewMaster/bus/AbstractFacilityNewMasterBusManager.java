package com.integrosys.cms.app.facilityNewMaster.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of FacilityNewMaster
 */
public abstract class AbstractFacilityNewMasterBusManager implements
		IFacilityNewMasterBusManager {

	private IFacilityNewMasterDao facilityNewMasterDao;

	private IFacilityNewMasterJdbc facilityNewMasterJdbc;
	
	
	public IFacilityNewMasterDao getFacilityNewMasterDao() {
		return facilityNewMasterDao;
	}

	public void setFacilityNewMasterDao(IFacilityNewMasterDao facilityNewMasterDao) {
		this.facilityNewMasterDao = facilityNewMasterDao;
	}

	public IFacilityNewMasterJdbc getFacilityNewMasterJdbc() {
		return facilityNewMasterJdbc;
	}

	public void setFacilityNewMasterJdbc(
			IFacilityNewMasterJdbc facilityNewMasterJdbc) {
		this.facilityNewMasterJdbc = facilityNewMasterJdbc;
	}
	public abstract String getFacilityNewMasterName();
	
	/**
	  * @return Particular FacilityNewMaster according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IFacilityNewMaster getFacilityNewMasterById(long id)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getFacilityNewMasterDao().getFacilityNewMaster(
					getFacilityNewMasterName(), new Long(id));
		} else {
			throw new FacilityNewMasterException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized FacilityNewMaster according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllFacilityNewMaster(String searchBy, String searchText)throws FacilityNewMasterException,TrxParameterException,TransactionException {

		return getFacilityNewMasterJdbc().getAllFacilityNewMaster(searchBy,searchText);
	}
	
	
	/**
	 @return Validate Line Number
	 * 
	 */

	public boolean isUniqueCode(
			String branchCode,String system)
			throws FacilityNewMasterException{
		if (!(branchCode == null)) {
			return getFacilityNewMasterDao().isUniqueCode(branchCode,system);
		} else {
			throw new FacilityNewMasterException(
					"ERROR- Facility object   is null. ");
		}
	}
	public boolean isUniqueFacilityCode(
			String facilityCode)
			throws FacilityNewMasterException{
		if (!(facilityCode == null)) {
			return getFacilityNewMasterDao().isUniqueFacilityCode(facilityCode);
		} else {
			throw new FacilityNewMasterException(
					"ERROR- Facility object   is null. ");
		}
	}
	
	
	
	
	/**
	 * @return List of all authorized FacilityNewMaster
	 */

	public SearchResult getAllFacilityNewMaster()throws FacilityNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getFacilityNewMasterJdbc().getAllFacilityNewMaster();
	}
	/**
	 * @return List of all authorized FacilityNewMaster according to Search Criteria provided.
	 * 
	 */
	public List searchFacilityNewMaster(String login)throws FacilityNewMasterException,TrxParameterException,TransactionException {

		return getFacilityNewMasterJdbc().getAllFacilityNewMasterSearch(login);
	}
	
	/**
	 @return FacilityNewMaster Object after update
	 * 
	 */

	public IFacilityNewMaster updateFacilityNewMaster(IFacilityNewMaster item)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		try {
			return getFacilityNewMasterDao().updateFacilityNewMaster(
					getFacilityNewMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FacilityNewMasterException("current FacilityNewMaster");
		}
	}
	/**
	 @return FacilityNewMaster Object after delete
	 * 
	 */
	public IFacilityNewMaster deleteFacilityNewMaster(IFacilityNewMaster item)
			throws FacilityNewMasterException, TrxParameterException,
			TransactionException {
		try {
			return getFacilityNewMasterDao().deleteFacilityNewMaster(
					getFacilityNewMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new FacilityNewMasterException("current FacilityNewMaster ");
		}
	}
	/**
	 @return FacilityNewMaster Object after create
	 * 
	 */

	public IFacilityNewMaster createFacilityNewMaster(
			IFacilityNewMaster facilityNewMaster)
			throws FacilityNewMasterException {
		if (!(facilityNewMaster == null)) {
			return getFacilityNewMasterDao().createFacilityNewMaster(getFacilityNewMasterName(), facilityNewMaster);
		} else {
			throw new FacilityNewMasterException(
					"ERROR- FacilityNewMaster object   is null. ");
		}
	}

	public boolean isFacilityNameUnique(String facilityName) {
		return getFacilityNewMasterDao().isFacilityNameUnique(facilityName);
	}
	
	public boolean isFacilityCpsIdUnique(String cpsId) {
		return getFacilityNewMasterDao().isFacilityCpsIdUnique(cpsId);
	}
	
	
	
	

}