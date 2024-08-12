package com.integrosys.cms.app.leiDateValidation.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface ILeiDateValidationJdbc {

	
	SearchResult getAllLeiDateValidation()throws LeiDateValidationException;
	SearchResult getAllFilteredLeiDateValidation(String code,String name)
			throws LeiDateValidationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
