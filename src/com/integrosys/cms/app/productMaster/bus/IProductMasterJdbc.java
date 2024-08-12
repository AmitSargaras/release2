package com.integrosys.cms.app.productMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IProductMasterJdbc {

	SearchResult getAllProductMaster()throws ProductMasterException;
	SearchResult getAllFilteredProductMaster(String code,String name)
			throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
