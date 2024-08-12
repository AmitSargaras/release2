package com.integrosys.cms.app.valuationAmountAndRating.bus;

import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public abstract class AbstractValuationAmountAndRatingBusManager implements
IValuationAmountAndRatingBusManager{

	private IValuationAmountAndRatingDao valuationAmountAndRatingDao;

	private IValuationAmountAndRatingJdbc valuationAmountAndRatingJdbc;

	public IValuationAmountAndRatingDao getValuationAmountAndRatingDao() {
		return valuationAmountAndRatingDao;
	}

	public void setValuationAmountAndRatingDao(IValuationAmountAndRatingDao valuationAmountAndRatingDao) {
		this.valuationAmountAndRatingDao = valuationAmountAndRatingDao;
	}

	public IValuationAmountAndRatingJdbc getValuationAmountAndRatingJdbc() {
		return valuationAmountAndRatingJdbc;
	}

	public void setValuationAmountAndRatingJdbc(IValuationAmountAndRatingJdbc valuationAmountAndRatingJdbc) {
		this.valuationAmountAndRatingJdbc = valuationAmountAndRatingJdbc;
	}
	
	public abstract String getValuationAmountAndRatingName();
	
	/**
	  * @return Particular ValuationAmountAndRating according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public IValuationAmountAndRating getValuationAmountAndRatingById(long id)
			throws ValuationAmountAndRatingException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getValuationAmountAndRatingDao().getValuationAmountAndRating(
					getValuationAmountAndRatingName(), new Long(id));
		} else {
			throw new ValuationAmountAndRatingException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 * @return List of all authorized ValuationAmountAndRating according to Search Criteria provided.
	 * 
	 */

	/*public SearchResult getSearchedValuationAmountAndRating(String searchBy, String searchText)throws ValuationAmountAndRatingException,TrxParameterException,TransactionException {

		return getValuationAmountAndRatingDao().getSearchedValuationAmountAndRating(searchBy,searchText);
	}*/
	
	/**
	 @return ValuationAmountAndRating Object after create
	 * 
	 */

	public IValuationAmountAndRating createValuationAmountAndRating(
			IValuationAmountAndRating valuationAmountAndRating)
			throws ValuationAmountAndRatingException {
		if (!(valuationAmountAndRating == null)) {
			return getValuationAmountAndRatingDao().createValuationAmountAndRating(getValuationAmountAndRatingName(), valuationAmountAndRating);
		} else {
			throw new ValuationAmountAndRatingException(
					"ERROR- ValuationAmountAndRating object   is null. ");
		}
	}
	
	/**
	 @return ValuationAmountAndRating Object after update
	 * 
	 */

	public IValuationAmountAndRating updateValuationAmountAndRating(IValuationAmountAndRating item)
			throws ValuationAmountAndRatingException, TrxParameterException,
			TransactionException {
		try {
			return getValuationAmountAndRatingDao().updateValuationAmountAndRating(
					getValuationAmountAndRatingName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAmountAndRatingException("current ValuationAmountAndRating");
		}
	}
	
	public IValuationAmountAndRating deleteValuationAmountAndRating(IValuationAmountAndRating item)
			throws ValuationAmountAndRatingException, TrxParameterException,
			TransactionException {
		try {
			return getValuationAmountAndRatingDao().deleteValuationAmountAndRating(
					getValuationAmountAndRatingName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAmountAndRatingException("current ValuationAmountAndRating");
		}
	}
	
	/**
	 * @return List of all authorized ValuationAmountAndRating
	 */

	public SearchResult getAllValuationAmountAndRating()throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException {

		return getValuationAmountAndRatingJdbc().getAllValuationAmountAndRating();
	}
	
	public boolean isProductCodeUnique(String productCode) {
		return getValuationAmountAndRatingDao().isProductCodeUnique(productCode);
	}
	
	public SearchResult getAllFilteredValuationAmountAndRating(String code, String name)
			throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,
			ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
