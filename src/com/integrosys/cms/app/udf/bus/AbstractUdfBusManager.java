package com.integrosys.cms.app.udf.bus;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractUdfBusManager implements
IUdfBusManager{

	private IUdfDao udfDao;

	private IUdfJdbc udfJdbc;

	public IUdfDao getUdfDao() {
		return udfDao;
	}

	public void setUdfDao(IUdfDao udfDao) {
		this.udfDao = udfDao;
	}

	public IUdfJdbc getUdfJdbc() {
		return udfJdbc;
	}

	public void setUdfJdbc(IUdfJdbc udfJdbc) {
		this.udfJdbc =udfJdbc;
	}
	
	public abstract String getUdfName();
	
	/**
	  * @return Particular Udf according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IUdf getUdfById(long id)
			throws UdfException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getUdfDao().findUdfById(getUdfName(), new Long(id));
			
		} else {
			throw new UdfException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized Udf according to Search Criteria provided.
	 * 
	 */

	/*public SearchResult getSearchedUdf(String searchBy, String searchText)throws UdfException,TrxParameterException,TransactionException {

		return getUdfDao().getSearchedUdf(searchBy,searchText);
	}*/
	
	/**
	 @return Udf Object after create
	 * 
	 */

	public IUdf createUdf(IUdf udf)
			throws UdfException {
		if (!(udf == null)) {
			return getUdfDao().createUdf(getUdfName(),udf);
		} else {
			throw new UdfException(
					"ERROR- Udf object   is null. ");
		}
	}
	
	/**
	 @return Udf Object after update
	 * 
	 */

	public IUdf updateUdfNew(IUdf item) throws UdfException {
		try {
			return getUdfDao().updateUdf(getUdfName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new UdfException("current Udf");
		}
	}
	
	/**
	 * @return List of all authorized Udf
	 */

	public SearchResult getAllUdf()throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getUdfJdbc().getAllUdf();
	}
	
/*	public boolean isProductCodeUnique(String productCode) {
		return getUdfDao().isProductCodeUnique(productCode);
	}*/
	
//	public SearchResult getAllFilteredUdf(String code, String name)
//			throws UdfException,TrxParameterException,TransactionException,
//			ConcurrentUpdateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

	public IUdf deleteUdfNew(IUdf item)
			throws UdfException, TrxParameterException,
			TransactionException {
		try {
			return getUdfDao().deleteUdf(getUdfName(),
					item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new UdfException("current Udf ");
		}
	}

	public IUdf enableUdf(IUdf item)
			throws UdfException, TrxParameterException,
			TransactionException {
		try {
			return getUdfDao().enableUdf(getUdfName(),
					item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new UdfException("current Udf ");
		}
	}
	
	@Override
	public void deleteUdf(IUdf udf) throws UdfException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IUdf insertUdf(IUdf udf) throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUdf findUdfById(String entityName, long id) throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List findAllUdfs() throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getUdfSequencesByModuleId(String moduleId) throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void freezeUdf(IUdf udf) throws UdfException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List findUdfByStatus(String entityName, String status) throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getUdfByModuleIdAndStatus(String moduleId, String status) throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUdf updateUdf(IUdf item) throws UdfException {
		// TODO Auto-generated method stub
		return null;
	}
}
