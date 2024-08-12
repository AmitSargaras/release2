package com.integrosys.cms.app.collateralrocandinsurance.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;

public abstract class AbstractCollateralRocBusManager implements
ICollateralRocBusManager{

	private ICollateralRocDao collateralRocDao;

	private ICollateralRocJdbc collateralRocJdbc;

	public ICollateralRocDao getCollateralRocDao() {
		return collateralRocDao;
	}

	public void setCollateralRocDao(ICollateralRocDao collateralRocDao) {
		this.collateralRocDao = collateralRocDao;
	}

	public ICollateralRocJdbc getCollateralRocJdbc() {
		return collateralRocJdbc;
	}

	public void setCollateralRocJdbc(ICollateralRocJdbc collateralRocJdbc) {
		this.collateralRocJdbc = collateralRocJdbc;
	}
	
	public abstract String getCollateralRocName();
	
	/**
	 @return ExcludedFacility Object after update
	 * 
	 */

	public ICollateralRoc updateCollateralRoc(ICollateralRoc item)
			throws CollateralRocException, TrxParameterException,
			TransactionException {
		try {
			return getCollateralRocDao().updateCollateralRoc(
					getCollateralRocName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CollateralRocException("current Collateral Roc");
		}
	}
	
	/**
	  * @return Particular Collateral Roc according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ICollateralRoc getCollateralRocById(long id)
			throws CollateralRocException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCollateralRocDao().getCollateralRoc(getCollateralRocName(), new Long(id));
		} else {
			throw new CollateralRocException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return CollateralRoc Object after create
	 * 
	 */

	public ICollateralRoc createCollateralRoc(
			ICollateralRoc collateralRoc)
			throws CollateralRocException {
		if (!(collateralRoc == null)) {
			return getCollateralRocDao().createCollateralRoc(getCollateralRocName(), collateralRoc);
		} else {
			throw new CollateralRocException(
					"ERROR- CollateralRoc object   is null. ");
		}
	}
	/**
	 * @return List of all authorized CollateralRoc
	 */

	public SearchResult getAllCollateralRoc()throws CollateralRocException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCollateralRocJdbc().getAllCollateralRoc();
	}
	
	public boolean isCollateralRocCategoryUnique(String collateralCategory,String collateralRocCode) {
		return getCollateralRocDao().isCollateralRocCategoryUnique(collateralCategory,collateralRocCode);
	}
	
	/**
	 * @return List of all authorized CollateralRoc according to Search Criteria provided.
	 * 
	 */

	public SearchResult getSearchedCollateralRoc(String searchBy, String searchText)throws CollateralRocException,TrxParameterException,TransactionException {

		return getCollateralRocDao().getSearchedCollateralRoc(searchBy,searchText);
	}
	
}
