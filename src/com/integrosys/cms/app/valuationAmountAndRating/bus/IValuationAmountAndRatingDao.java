package com.integrosys.cms.app.valuationAmountAndRating.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface IValuationAmountAndRatingDao {

	static final String ACTUAL_VALUATION_AMOUNT_AND_RATING_NAME = "actualValuationAmountAndRating";
	static final String STAGE_VALUATION_AMOUNT_AND_RATING_NAME = "stageValuationAmountAndRating";
	
	IValuationAmountAndRating getValuationAmountAndRating(String entityName, Serializable key)throws ValuationAmountAndRatingException;
	IValuationAmountAndRating updateValuationAmountAndRating(String entityName, IValuationAmountAndRating item)throws ValuationAmountAndRatingException;
	IValuationAmountAndRating createValuationAmountAndRating(String entityName, IValuationAmountAndRating excludedFacility)
			throws ValuationAmountAndRatingException;
	IValuationAmountAndRating deleteValuationAmountAndRating(String entityName, IValuationAmountAndRating item)throws ValuationAmountAndRatingException;
	
	public boolean isProductCodeUnique(String productCode);
	
	/*SearchResult getSearchedValuationAmountAndRating(String searchBy,String searchText)throws ValuationAmountAndRatingException;*/
	
	public List getValuationAmountAndRatingList();
}
