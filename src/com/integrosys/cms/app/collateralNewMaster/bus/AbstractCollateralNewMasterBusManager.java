package com.integrosys.cms.app.collateralNewMaster.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

/**
 * @author Abhijit R.
 * Abstract Bus Manager of CollateralNewMaster
 */
public abstract class AbstractCollateralNewMasterBusManager implements
		ICollateralNewMasterBusManager {

	private ICollateralNewMasterDao collateralNewMasterDao;

	private ICollateralNewMasterJdbc collateralNewMasterJdbc;
	
	
	public ICollateralNewMasterDao getCollateralNewMasterDao() {
		return collateralNewMasterDao;
	}

	public void setCollateralNewMasterDao(ICollateralNewMasterDao collateralNewMasterDao) {
		this.collateralNewMasterDao = collateralNewMasterDao;
	}

	public ICollateralNewMasterJdbc getCollateralNewMasterJdbc() {
		return collateralNewMasterJdbc;
	}

	public void setCollateralNewMasterJdbc(
			ICollateralNewMasterJdbc collateralNewMasterJdbc) {
		this.collateralNewMasterJdbc = collateralNewMasterJdbc;
	}
	public abstract String getCollateralNewMasterName();
	
	/**
	  * @return Particular CollateralNewMaster according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ICollateralNewMaster getCollateralNewMasterById(long id)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCollateralNewMasterDao().getCollateralNewMaster(
					getCollateralNewMasterName(), new Long(id));
		} else {
			throw new SystemBankException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized CollateralNewMaster according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllCollateralNewMaster(String searchBy, String searchText)throws CollateralNewMasterException,TrxParameterException,TransactionException {

		return getCollateralNewMasterJdbc().getAllCollateralNewMaster(searchBy,searchText);
	}
	/**
	 * @return List of all authorized CollateralNewMaster
	 */

	public SearchResult getAllCollateralNewMaster()throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCollateralNewMasterJdbc().getAllCollateralNewMaster();
	}
	
	public SearchResult getFilteredCollateral(String collateralCode,String  collateralDescription, String collateralMainType, String collateralSubType)throws CollateralNewMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getCollateralNewMasterJdbc().getFilteredCollateral(collateralCode, collateralDescription, collateralMainType, collateralSubType);
	}
	/**
	 * @return List of all authorized CollateralNewMaster according to Search Criteria provided.
	 * 
	 */
	public List searchCollateralNewMaster(String login)throws CollateralNewMasterException,TrxParameterException,TransactionException {

		return getCollateralNewMasterJdbc().getAllCollateralNewMasterSearch(login);
	}
	
	/**
	 @return CollateralNewMaster Object after update
	 * 
	 */

	public ICollateralNewMaster updateCollateralNewMaster(ICollateralNewMaster item)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		try {
			return getCollateralNewMasterDao().updateCollateralNewMaster(
					getCollateralNewMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CollateralNewMasterException("current CollateralNewMaster");
		}
	}
	/**
	 @return CollateralNewMaster Object after delete
	 * 
	 */
	public ICollateralNewMaster deleteCollateralNewMaster(ICollateralNewMaster item)
			throws CollateralNewMasterException, TrxParameterException,
			TransactionException {
		try {
			return getCollateralNewMasterDao().deleteCollateralNewMaster(
					getCollateralNewMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CollateralNewMasterException("current CollateralNewMaster ");
		}
	}
	/**
	 @return CollateralNewMaster Object after create
	 * 
	 */

	public ICollateralNewMaster createCollateralNewMaster(
			ICollateralNewMaster collateralNewMaster)
			throws CollateralNewMasterException {
		if (!(collateralNewMaster == null)) {
			return getCollateralNewMasterDao().createCollateralNewMaster(getCollateralNewMasterName(), collateralNewMaster);
		} else {
			throw new CollateralNewMasterException(
					"ERROR- CollateralNewMaster object   is null. ");
		}
	}

	public boolean isCollateraNameUnique(String collateralName) {
		return getCollateralNewMasterDao().isCollateraNameUnique(collateralName);
	}
	public boolean isDuplicateRecord(String cpsId) {
		return getCollateralNewMasterDao().isDuplicateRecord(cpsId);
	}
}