package com.integrosys.cms.app.riskType.bus;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractRiskTypeBusManager implements
IRiskTypeBusManager{

	private IRiskTypeDao riskTypeDao;

	private IRiskTypeJdbc riskTypeJdbc;

	public IRiskTypeDao getRiskTypeDao() {
		return riskTypeDao;
	}

	public void setRiskTypeDao(IRiskTypeDao riskTypeDao) {
		this.riskTypeDao = riskTypeDao;
	}

	public IRiskTypeJdbc getRiskTypeJdbc() {
		return riskTypeJdbc;
	}

	public void setRiskTypeJdbc(IRiskTypeJdbc riskTypeJdbc) {
		this.riskTypeJdbc = riskTypeJdbc;
	}
	
	public abstract String getRiskTypeName();
	
	/**
	  * @return Particular RiskType according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IRiskType getRiskTypeById(long id)
			throws RiskTypeException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getRiskTypeDao().getRiskType(
					getRiskTypeName(), new Long(id));
		} else {
			throw new RiskTypeException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized RiskType according to Search Criteria provided.
	 * 
	 */

	/*public SearchResult getSearchedRiskType(String searchBy, String searchText)throws RiskTypeException,TrxParameterException,TransactionException {

		return getRiskTypeDao().getSearchedRiskType(searchBy,searchText);
	}*/
	
	/**
	 @return RiskType Object after create
	 * 
	 */

	public IRiskType createRiskType(
			IRiskType riskType)
			throws RiskTypeException {
		if (!(riskType == null)) {
			return getRiskTypeDao().createRiskType(getRiskTypeName(), riskType);
		} else {
			throw new RiskTypeException(
					"ERROR- RiskType object   is null. ");
		}
	}
	
	/**
	 @return RiskType Object after update
	 * 
	 */

	public IRiskType updateRiskType(IRiskType item)
			throws RiskTypeException, TrxParameterException,
			TransactionException {
		try {
			return getRiskTypeDao().updateRiskType(
					getRiskTypeName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RiskTypeException("current RiskType");
		}
	}
	
	public IRiskType deleteRiskType(IRiskType item)
			throws RiskTypeException, TrxParameterException,
			TransactionException {
		try {
			return getRiskTypeDao().deleteRiskType(
					getRiskTypeName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RiskTypeException("current RiskType");
		}
	}
	
	/**
	 * @return List of all authorized RiskType
	 */

	public SearchResult getAllRiskType()throws RiskTypeException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getRiskTypeJdbc().getAllRiskType();
	}
	
	/*public boolean isProductCodeUnique(String productCode) {
		return getRiskTypeDao().isProductCodeUnique(productCode);
	}*/
	
	public SearchResult getAllFilteredRiskType(String code, String name)
			throws RiskTypeException,TrxParameterException,TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
