package com.integrosys.cms.app.valuationAmountAndRating.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public class ValuationAmountAndRatingBusManagerImpl extends AbstractValuationAmountAndRatingBusManager implements IValuationAmountAndRatingBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging ValuationAmountAndRating table  
     * 
     */
	
	
	public String getValuationAmountAndRatingName() {
		return IValuationAmountAndRatingDao.ACTUAL_VALUATION_AMOUNT_AND_RATING_NAME;
	}
	
	/**
	 * @return WorkingCopy-- updated ValuationAmountAndRating Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IValuationAmountAndRating updateToWorkingCopy(IValuationAmountAndRating workingCopy, IValuationAmountAndRating imageCopy)
	throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		IValuationAmountAndRating updated;
		try{
			workingCopy.setCriteria(imageCopy.getCriteria());
			workingCopy.setValuationAmount(imageCopy.getValuationAmount());
			workingCopy.setRamRating(imageCopy.getRamRating());
			workingCopy.setExcludePartyId(imageCopy.getExcludePartyId());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setDeprecated(imageCopy.getDeprecated());
			workingCopy.setCreateBy(imageCopy.getCreateBy());
			workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
			updated = updateValuationAmountAndRating(workingCopy);
			return updateValuationAmountAndRating(updated);
		}catch (Exception e) {
			throw new ValuationAmountAndRatingException("Error while Copying copy to main file");
		}
	}
	
	public IValuationAmountAndRating deleteToWorkingCopy(IValuationAmountAndRating workingCopy, IValuationAmountAndRating imageCopy)
			throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException {
				IValuationAmountAndRating updated;
				try{
					workingCopy.setCriteria(imageCopy.getCriteria());
					workingCopy.setValuationAmount(imageCopy.getValuationAmount());
					workingCopy.setRamRating(imageCopy.getRamRating());
					workingCopy.setExcludePartyId(imageCopy.getExcludePartyId());
					workingCopy.setStatus(imageCopy.getStatus());
					workingCopy.setDeprecated(imageCopy.getDeprecated());
					workingCopy.setCreateBy(imageCopy.getCreateBy());
					workingCopy.setLastUpdateBy(imageCopy.getLastUpdateBy());
					updated = updateValuationAmountAndRating(workingCopy);
					return deleteValuationAmountAndRating(updated);
				}catch (Exception e) {
					throw new ValuationAmountAndRatingException("Error while Copying copy to main file");
				}
			}
			
	
	/**
	 * @return List of all authorized ValuationAmountAndRating
	 */
	

	public SearchResult getAllValuationAmountAndRating()throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getValuationAmountAndRatingJdbc().getAllValuationAmountAndRating();
	}
	
	/**
	 * @return List of all authorized ValuationAmountAndRating
	 */
	

	public SearchResult getAllFilteredValuationAmountAndRating(String code,String name)throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 return getValuationAmountAndRatingJdbc().getAllFilteredValuationAmountAndRating(code,name);
	}
	
	
}
