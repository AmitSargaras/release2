package com.integrosys.cms.app.leiDateValidation.bus;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractLeiDateValidationBusManager implements
ILeiDateValidationBusManager{

	
	private ILeiDateValidationDao leiDateValidationDao;

	private ILeiDateValidationJdbc leiDateValidationJdbc;		
	
	
	public ILeiDateValidationDao getLeiDateValidationDao() {
		return leiDateValidationDao;
	}

	public void setLeiDateValidationDao(ILeiDateValidationDao leiDateValidationDao) {
		this.leiDateValidationDao = leiDateValidationDao;
	}

	public ILeiDateValidationJdbc getLeiDateValidationJdbc() {
		return leiDateValidationJdbc;
	}

	public void setLeiDateValidationJdbc(ILeiDateValidationJdbc leiDateValidationJdbc) {
		this.leiDateValidationJdbc = leiDateValidationJdbc;
	}

	public SearchResult getAllLeiDateValidation()throws LeiDateValidationException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getLeiDateValidationJdbc().getAllLeiDateValidation();
	}
	
	public SearchResult getAllFilteredLeiDateValidation(String code, String name)
			throws LeiDateValidationException,TrxParameterException,TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public abstract String getLeiDateValidationName();
	
	/**
	  * @return Particular LeiDateValidation according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ILeiDateValidation getLeiDateValidationById(long id)
			throws LeiDateValidationException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getLeiDateValidationDao().getLeiDateValidation(
					getLeiDateValidationName(), new Long(id));
		} else {
			throw new LeiDateValidationException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized LeiDateValidation according to Search Criteria provided.
	 * 
	 */

	/*public SearchResult getSearchedLeiDateValidation(String searchBy, String searchText)throws LeiDateValidationException,TrxParameterException,TransactionException {

		return getLeiDateValidationDao().getSearchedLeiDateValidation(searchBy,searchText);
	}*/
	
	/**
	 @return LeiDateValidation Object after create
	 * 
	 */

	public ILeiDateValidation createLeiDateValidation(
			ILeiDateValidation leiDateValidation)
			throws LeiDateValidationException {
		if (!(leiDateValidation == null)) {
			return getLeiDateValidationDao().createLeiDateValidation(getLeiDateValidationName(), leiDateValidation);
		} else {
			throw new LeiDateValidationException(
					"ERROR- LeiDateValidation object   is null. ");
		}
	}
	
	/**
	 @return LeiDateValidation Object after update
	 * 
	 */

	public ILeiDateValidation updateLeiDateValidation(ILeiDateValidation item)
			throws LeiDateValidationException, TrxParameterException,
			TransactionException {
		try {
			return getLeiDateValidationDao().updateLeiDateValidation(
					getLeiDateValidationName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new LeiDateValidationException("current LeiDateValidation");
		}
	}
	
	
	public boolean isPartyIDUnique(String partyID) {
		return getLeiDateValidationDao().isPartyIDUnique(partyID);
	}
	
}
