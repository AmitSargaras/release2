package com.integrosys.cms.app.valuationAmountAndRating.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class ValuationAmountAndRatingBusManagerStagingImpl extends AbstractValuationAmountAndRatingBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging ValuationAmountAndRating table  
     * 
     */
	
	public String getValuationAmountAndRatingName() {
        return IValuationAmountAndRatingDao.STAGE_VALUATION_AMOUNT_AND_RATING_NAME;
    }
	
	/**
	 * This method returns exception as staging
	 *  system bank branch can never be working copy
	 */
    
    public IValuationAmountAndRating updateToWorkingCopy(IValuationAmountAndRating workingCopy, IValuationAmountAndRating imageCopy)
            throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	public SearchResult getFilteredActualValuationAmountAndRating(String code,
			String name, String category, String type, String system,
			String line) throws ValuationAmountAndRatingException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return null;
	}

	@Override
	public IValuationAmountAndRating deleteToWorkingCopy(IValuationAmountAndRating workingCopy,
			IValuationAmountAndRating imageCopy) throws ValuationAmountAndRatingException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		// TODO Auto-generated method stub
		throw new IllegalStateException("'deleteToWorkingCopy' should not be implemented.");
	}
}
