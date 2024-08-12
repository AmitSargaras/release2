package com.integrosys.cms.app.valuationAmountAndRating.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IValuationAmountAndRatingJdbc {

	SearchResult getAllValuationAmountAndRating()throws ValuationAmountAndRatingException;
	SearchResult getAllFilteredValuationAmountAndRating(String code,String name)
			throws ValuationAmountAndRatingException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	List<OBValuationAmountAndRating> getValuationByRamRatingOfCAM(long collateralId);
}
