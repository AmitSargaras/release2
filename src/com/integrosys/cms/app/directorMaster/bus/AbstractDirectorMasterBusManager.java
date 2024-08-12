package com.integrosys.cms.app.directorMaster.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Abstract Bus Manager of Director Master
 */
public abstract class AbstractDirectorMasterBusManager implements
		IDirectorMasterBusManager {

	private IDirectorMasterDao directorMasterDao;

	private IDirectorMasterJdbc directorMasterJdbc;
	
	
	public IDirectorMasterDao getDirectorMasterDao() {
		return directorMasterDao;
	}

	public void setDirectorMasterDao(IDirectorMasterDao directorMasterDao) {
		this.directorMasterDao = directorMasterDao;
	}

	public IDirectorMasterJdbc getDirectorMasterJdbc() {
		return directorMasterJdbc;
	}

	public void setDirectorMasterJdbc(
			IDirectorMasterJdbc directorMasterJdbc) {
		this.directorMasterJdbc = directorMasterJdbc;
	}
	public abstract String getDirectorMasterName();
	
	/**
	  * @return Particular Director Master according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IDirectorMaster getDirectorMasterById(long id)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getDirectorMasterDao().getDirectorMaster(
					getDirectorMasterName(), new Long(id));
		} else {
			throw new DirectorMasterException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	/**
	 * @return List of all authorized director master according to Search Criteria provided.
	 * 
	 */

	public SearchResult getAllDirectorMaster(String searchBy, String searchText)throws DirectorMasterException,TrxParameterException,TransactionException {

		return getDirectorMasterDao().getAllDirectorMaster(searchBy,
				searchText);
	}
	/**
	 * @return List of all authorized Director Master
	 */

	public SearchResult getAllDirectorMaster()throws DirectorMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getDirectorMasterDao().getAllDirectorMaster();
	}
	/**
	 * @return List of all authorized Director Master according to Search Criteria provided.
	 * 
	 */
	public List searchDirector(String login)throws DirectorMasterException,TrxParameterException,TransactionException {

		return getDirectorMasterJdbc().getAllDirectorMasterSearch(login);
	}
	
	/**
	 @return DirectorMaster Object after update
	 * 
	 */

	public IDirectorMaster updateDirectorMaster(IDirectorMaster item)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		try {
			return getDirectorMasterDao().updateDirectorMaster(
					getDirectorMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new DirectorMasterException("current DirectorMaster ["
					+ item + "] ");
		}
	}
	/**
	 @return DirectorMaster Object after delete
	 * 
	 */
	public IDirectorMaster disableDirectorMaster(IDirectorMaster item)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		try {
			return getDirectorMasterDao().disableDirectorMaster(
					getDirectorMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new DirectorMasterException("current DirectorMaster ["
					+ item + "]");
		}
	}
	
	/**
	 @return DirectorMaster Object after delete
	 * 
	 */
	public IDirectorMaster enableDirectorMaster(IDirectorMaster item)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		try {
			return getDirectorMasterDao().enableDirectorMaster(
					getDirectorMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new DirectorMasterException("current DirectorMaster ["
					+ item + "]");
		}
	}
	
	
	/**
	 @return DirectorMaster Object after create
	 * 
	 */

	public IDirectorMaster createDirectorMaster(
			IDirectorMaster directorMaster)
			throws DirectorMasterException {
		if (!(directorMaster == null)) {
			return getDirectorMasterDao().createDirectorMaster(getDirectorMasterName(), directorMaster);
		} else {
			throw new DirectorMasterException(
					"ERROR- Director Master object   is null. ");
		}
	}
	/**
	 @return DirectorMaster Object after delete
	 * 
	 */
	public IDirectorMaster saveDirectorMaster(IDirectorMaster item)
			throws DirectorMasterException, TrxParameterException,
			TransactionException {
		try {
			return getDirectorMasterDao().saveDirectorMaster(
					getDirectorMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new DirectorMasterException("current DirectorMaster ["
					+ item + "]");
		}
	}
	
	public boolean isDinNumberUnique(String dinNumber) {
		return getDirectorMasterDao().isDinNumberUnique(dinNumber);
	}
	
	public boolean isDirectorNameUnique(String directorName) {
		return getDirectorMasterDao().isDirectorNameUnique(directorName);
	}
}