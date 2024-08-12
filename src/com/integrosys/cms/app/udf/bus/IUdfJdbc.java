package com.integrosys.cms.app.udf.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IUdfJdbc {

	SearchResult getAllUdf()throws UdfException;
	SearchResult getAllFilteredUdf(String code,String name)
			throws UdfException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
