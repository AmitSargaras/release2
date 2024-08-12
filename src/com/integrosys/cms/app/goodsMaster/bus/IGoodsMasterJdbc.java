package com.integrosys.cms.app.goodsMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface IGoodsMasterJdbc {

	SearchResult getAllGoodsMaster()throws GoodsMasterException;
	SearchResult getAllFilteredGoodsMaster(String code,String name)
			throws GoodsMasterException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	List<String> getProhibitedGoodsCode() throws GoodsMasterException;
	List<String> getParentGoodsCodeList() throws GoodsMasterException;
	List<String> getNonProhibitedChildGoodsCodeListByParent(String parentGoodsCode) throws GoodsMasterException;
	List<IGoodsMaster> getAllNonProhibitedGoodsMasterListByParent(String parentGoodsCode) throws GoodsMasterException;
}
