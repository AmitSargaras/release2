package com.integrosys.cms.app.goodsMaster.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;

public abstract class AbstractGoodsMasterBusManager implements
IGoodsMasterBusManager{

	private IGoodsMasterDao goodsMasterDao;

	private IGoodsMasterJdbc goodsMasterJdbc;

	public IGoodsMasterDao getGoodsMasterDao() {
		return goodsMasterDao;
	}

	public void setGoodsMasterDao(IGoodsMasterDao goodsMasterDao) {
		this.goodsMasterDao = goodsMasterDao;
	}

	public IGoodsMasterJdbc getGoodsMasterJdbc() {
		return goodsMasterJdbc;
	}

	public void setGoodsMasterJdbc(IGoodsMasterJdbc goodsMasterJdbc) {
		this.goodsMasterJdbc = goodsMasterJdbc;
	}
	
	public abstract String getGoodsMasterName();
	
	/**
	  * @return Particular GoodsMaster according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IGoodsMaster getGoodsMasterById(long id)
			throws GoodsMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getGoodsMasterDao().getGoodsMaster(
					getGoodsMasterName(), new Long(id));
		} else {
			throw new GoodsMasterException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized GoodsMaster according to Search Criteria provided.
	 * 
	 */

	/*public SearchResult getSearchedGoodsMaster(String searchBy, String searchText)throws GoodsMasterException,TrxParameterException,TransactionException {

		return getGoodsMasterDao().getSearchedGoodsMaster(searchBy,searchText);
	}*/
	
	/**
	 @return GoodsMaster Object after create
	 * 
	 */

	public IGoodsMaster createGoodsMaster(
			IGoodsMaster goodsMaster)
			throws GoodsMasterException {
		if (!(goodsMaster == null)) {
			return getGoodsMasterDao().createGoodsMaster(getGoodsMasterName(), goodsMaster);
		} else {
			throw new GoodsMasterException(
					"ERROR- GoodsMaster object   is null. ");
		}
	}
	
	/**
	 @return GoodsMaster Object after update
	 * 
	 */

	public IGoodsMaster updateGoodsMaster(IGoodsMaster item)
			throws GoodsMasterException, TrxParameterException,
			TransactionException {
		try {
			return getGoodsMasterDao().updateGoodsMaster(
					getGoodsMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new GoodsMasterException("current GoodsMaster");
		}
	}
	
	/**
	 * @return List of all authorized GoodsMaster
	 */

	public SearchResult getAllGoodsMaster()throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getGoodsMasterJdbc().getAllGoodsMaster();
	}
	
	public boolean isGoodsCodeUnique(String goodsCode) {
		return getGoodsMasterDao().isGoodsCodeUnique(goodsCode);
	}
	
	public SearchResult getAllFilteredGoodsMaster(String code, String name)
			throws GoodsMasterException,TrxParameterException,TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}
}
