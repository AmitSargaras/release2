package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;

public interface INpaTraqCodeMasterJdbc {

	SearchResult getAllNpaTraqCodeMaster()throws NpaTraqCodeMasterException;
	public boolean isNpaTraqCodeUniqueJdbc(String securityType, String securitySubType, String propertyTypeDesc);
	/*SearchResult getAllFilteredProductMaster(String code,String name)
			throws ProductMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;*/
}
