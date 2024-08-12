package com.integrosys.cms.app.excludedfacility.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractExcludedFacilityBusManager implements
IExcludedFacilityBusManager{

	private IExcludedFacilityDao excludedFacilityDao;

	private IExcludedFacilityJdbc excludedFacilityJdbc;

	public IExcludedFacilityDao getExcludedFacilityDao() {
		return excludedFacilityDao;
	}

	public void setExcludedFacilityDao(IExcludedFacilityDao excludedFacilityDao) {
		this.excludedFacilityDao = excludedFacilityDao;
	}

	public IExcludedFacilityJdbc getExcludedFacilityJdbc() {
		return excludedFacilityJdbc;
	}

	public void setExcludedFacilityJdbc(IExcludedFacilityJdbc excludedFacilityJdbc) {
		this.excludedFacilityJdbc = excludedFacilityJdbc;
	}
	
	public abstract String getExcludedFacilityName();
	
	/**
	  * @return Particular Excluded Facility according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IExcludedFacility getExcludedFacilityById(long id)
			throws ExcludedFacilityException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getExcludedFacilityDao().getExcludedFacility(
					getExcludedFacilityName(), new Long(id));
		} else {
			throw new ExcludedFacilityException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized FacilityNewMaster according to Search Criteria provided.
	 * 
	 */

	public SearchResult getSearchedExcludedFacility(String searchBy, String searchText)throws ExcludedFacilityException,TrxParameterException,TransactionException {

		return getExcludedFacilityDao().getSearchedExcludedFacility(searchBy,searchText);
	}
	
	/**
	 @return ExcludedFacility Object after create
	 * 
	 */

	public IExcludedFacility createExcludedFacility(
			IExcludedFacility excludedFacility)
			throws ExcludedFacilityException {
		if (!(excludedFacility == null)) {
			return getExcludedFacilityDao().createExcludedFacility(getExcludedFacilityName(), excludedFacility);
		} else {
			throw new ExcludedFacilityException(
					"ERROR- Facility object   is null. ");
		}
	}
	
	/**
	 @return ExcludedFacility Object after update
	 * 
	 */

	public IExcludedFacility updateExcludedFacility(IExcludedFacility item)
			throws ExcludedFacilityException, TrxParameterException,
			TransactionException {
		try {
			return getExcludedFacilityDao().updateExcludedFacility(
					getExcludedFacilityName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ExcludedFacilityException("current Excluded Facility");
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
}
