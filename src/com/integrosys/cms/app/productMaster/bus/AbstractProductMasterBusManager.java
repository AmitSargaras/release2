package com.integrosys.cms.app.productMaster.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;

public abstract class AbstractProductMasterBusManager implements
IProductMasterBusManager{

	private IProductMasterDao productMasterDao;

	private IProductMasterJdbc productMasterJdbc;

	public IProductMasterDao getProductMasterDao() {
		return productMasterDao;
	}

	public void setProductMasterDao(IProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public IProductMasterJdbc getProductMasterJdbc() {
		return productMasterJdbc;
	}

	public void setProductMasterJdbc(IProductMasterJdbc productMasterJdbc) {
		this.productMasterJdbc = productMasterJdbc;
	}
	
	public abstract String getProductMasterName();
	
	/**
	  * @return Particular ProductMaster according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IProductMaster getProductMasterById(long id)
			throws ProductMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getProductMasterDao().getProductMaster(
					getProductMasterName(), new Long(id));
		} else {
			throw new ProductMasterException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized ProductMaster according to Search Criteria provided.
	 * 
	 */

	/*public SearchResult getSearchedProductMaster(String searchBy, String searchText)throws ProductMasterException,TrxParameterException,TransactionException {

		return getProductMasterDao().getSearchedProductMaster(searchBy,searchText);
	}*/
	
	/**
	 @return ProductMaster Object after create
	 * 
	 */

	public IProductMaster createProductMaster(
			IProductMaster productMaster)
			throws ProductMasterException {
		if (!(productMaster == null)) {
			return getProductMasterDao().createProductMaster(getProductMasterName(), productMaster);
		} else {
			throw new ProductMasterException(
					"ERROR- ProductMaster object   is null. ");
		}
	}
	
	/**
	 @return ProductMaster Object after update
	 * 
	 */

	public IProductMaster updateProductMaster(IProductMaster item)
			throws ProductMasterException, TrxParameterException,
			TransactionException {
		try {
			return getProductMasterDao().updateProductMaster(
					getProductMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ProductMasterException("current ProductMaster");
		}
	}
	
	/**
	 * @return List of all authorized ProductMaster
	 */

	public SearchResult getAllProductMaster()throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getProductMasterJdbc().getAllProductMaster();
	}
	
	public boolean isProductCodeUnique(String productCode) {
		return getProductMasterDao().isProductCodeUnique(productCode);
	}
	
	public SearchResult getAllFilteredProductMaster(String code, String name)
			throws ProductMasterException,TrxParameterException,TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}
}
