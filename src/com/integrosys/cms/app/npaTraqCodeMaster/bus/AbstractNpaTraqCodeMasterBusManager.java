package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.*;

public abstract class AbstractNpaTraqCodeMasterBusManager implements INpaTraqCodeMasterBusManager{

	private INpaTraqCodeMasterDao npaTraqCodeMasterDao;
	private INpaTraqCodeMasterJdbc npaTraqCodeMasterJdbc;

	public INpaTraqCodeMasterDao getNpaTraqCodeMasterDao() {
		return npaTraqCodeMasterDao;
	}

	public void setNpaTraqCodeMasterDao(INpaTraqCodeMasterDao npaTraqCodeMasterDao) {
		this.npaTraqCodeMasterDao = npaTraqCodeMasterDao;
	}

	public INpaTraqCodeMasterJdbc getNpaTraqCodeMasterJdbc() {
		return npaTraqCodeMasterJdbc;
	}

	public void setNpaTraqCodeMasterJdbc(INpaTraqCodeMasterJdbc npaTraqCodeMasterJdbc) {
		this.npaTraqCodeMasterJdbc = npaTraqCodeMasterJdbc;
	}
	
	public abstract String getNpaTraqCodeMasterName();
	
	/**
	  * @return Particular NpaTraqCodeMaster according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public INpaTraqCodeMaster getNpaTraqCodeMasterById(long id)
			throws NpaTraqCodeMasterException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getNpaTraqCodeMasterDao().getNpaTraqCodeMaster(
					getNpaTraqCodeMasterName(), new Long(id));
		} else {
			throw new NpaTraqCodeMasterException(
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
	 @return NpaTraqCodeMaster Object after create
	 * 
	 */

	public INpaTraqCodeMaster createNpaTraqCodeMaster(
			INpaTraqCodeMaster npaTraqCodeMaster)
			throws NpaTraqCodeMasterException {
		if (!(npaTraqCodeMaster == null)) {
			return getNpaTraqCodeMasterDao().createNpaTraqCodeMaster(getNpaTraqCodeMasterName(), npaTraqCodeMaster);
		} else {
			throw new NpaTraqCodeMasterException(
					"ERROR- NpaTraqCodeMaster object   is null. ");
		}
	}
	
	/**
	 @return NpaTraqCodeMaster Object after update
	 * 
	 */

	public INpaTraqCodeMaster updateNpaTraqCodeMaster(INpaTraqCodeMaster item)
			throws NpaTraqCodeMasterException, TrxParameterException,
			TransactionException {
		try {
			return getNpaTraqCodeMasterDao().updateNpaTraqCodeMaster(
					getNpaTraqCodeMasterName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NpaTraqCodeMasterException("current NpaTraqCodeMaster");
		}
	}
	
	/**
	 * @return List of all authorized NpaTraqCodeMaster
	 */

	public SearchResult getAllNpaTraqCodeMaster()throws NpaTraqCodeMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getNpaTraqCodeMasterJdbc().getAllNpaTraqCodeMaster();
	}
	
	public boolean isNpaTraqCodeUniqueJdbc(String securityType, String securitySubType, String propertyTypeDesc) {
		return getNpaTraqCodeMasterJdbc().isNpaTraqCodeUniqueJdbc(securityType,securitySubType,propertyTypeDesc);
	}
	
	public boolean isNpaTraqCodeUnique(String npaTraqCode, String securityType, String securitySubType, String propertyTypeDesc) {
		return getNpaTraqCodeMasterDao().isNpaTraqCodeUnique(npaTraqCode,securityType,securitySubType,propertyTypeDesc);
	}
	
/*	public SearchResult getAllFilteredProductMaster(String code, String name)
			throws ProductMasterException,TrxParameterException,TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}*/
}
