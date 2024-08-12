package com.integrosys.cms.app.insurancecoveragedtls.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;


/**
 *@author $Author: Dattatray Thorat $
 * Abstract Insurance Coverage Details Bus manager 
 */
public abstract class AbstractInsuranceCoverageDtlsBusManager implements IInsuranceCoverageDtlsBusManager {
	
	IInsuranceCoverageDtlsDAO insuranceCoverageDtlsDAO;
	
	/**
	 * @return the insuranceCoverageDtlsDAO
	 */
	public IInsuranceCoverageDtlsDAO getInsuranceCoverageDtlsDAO() {
		return insuranceCoverageDtlsDAO;
	}


	/**
	 * @param insuranceCoverageDtlsDAO the insuranceCoverageDtlsDAO to set
	 */
	public void setInsuranceCoverageDtlsDAO(
			IInsuranceCoverageDtlsDAO insuranceCoverageDtlsDAO) {
		this.insuranceCoverageDtlsDAO = insuranceCoverageDtlsDAO;
	}


	public abstract String getInsuranceCoverageDtlsName();
	
	
	/**
	  * @return Particular Insurance Coverage Details  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IInsuranceCoverageDtls getInsuranceCoverageDtlsById(long id) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException  {
		if(id!=0){
		return  getInsuranceCoverageDtlsDAO().getInsuranceCoverageDtls(getInsuranceCoverageDtlsName(),new Long(id));
		}else{
			throw new InsuranceCoverageDtlsException("ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	  * @return Particular Insurance Coverage Details  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IInsuranceCoverageDtls getInsuranceCoverageDtls(long id) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException  {
		if(id!=0){
			return  getInsuranceCoverageDtlsDAO().getInsuranceCoverageDtls(getInsuranceCoverageDtlsName(),new Long(id));
		}else{
			throw new InsuranceCoverageDtlsException("ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return InsuranceCoverageDtls Object after create
	 * 
	 */
	
	public IInsuranceCoverageDtls createInsuranceCoverageDtls(IInsuranceCoverageDtls insuranceCoverageDtls)throws InsuranceCoverageDtlsException {
		if(!(insuranceCoverageDtls==null)){
		return getInsuranceCoverageDtlsDAO().createInsuranceCoverageDtls(getInsuranceCoverageDtlsName(), insuranceCoverageDtls);
		}else{
			throw new InsuranceCoverageDtlsException("ERROR- Insurance Coverage Details object   is null. ");
		}
	}
	/**
	 @return InsuranceCoverageDtls Object after update
	 * 
	 */
	public IInsuranceCoverageDtls updateInsuranceCoverageDtls(IInsuranceCoverageDtls item) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getInsuranceCoverageDtlsDAO().updateInsuranceCoverageDtls(getInsuranceCoverageDtlsName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new InsuranceCoverageDtlsException("Current InsuranceCoverageDtls [" + item + "] was updated before by ["
					+ item.getInsuranceCoverageCode() + "] at [" + item.getInsuranceCategoryName() + "]");
		}
		
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageDtlsList() throws InsuranceCoverageDtlsException{
		return (SearchResult)getInsuranceCoverageDtlsDAO().getInsuranceCoverageDtlsList();
	}
	
	public boolean isICCodeUnique(String rmCode){
		 return getInsuranceCoverageDtlsDAO().isICCodeUnique(rmCode);
	 }
}