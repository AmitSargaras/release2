package com.integrosys.cms.app.goodsMaster.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface IGoodsMasterDao {

	static final String ACTUAL_GOODS_MASTER_NAME = "actualGoodsMaster";
	static final String STAGE_GOODS_MASTER_NAME = "stageGoodsMaster";
	
	IGoodsMaster getGoodsMaster(String entityName, Serializable key)throws GoodsMasterException;
	IGoodsMaster updateGoodsMaster(String entityName, IGoodsMaster item)throws GoodsMasterException;
	IGoodsMaster createGoodsMaster(String entityName, IGoodsMaster excludedFacility)
			throws GoodsMasterException;
	
	public boolean isGoodsCodeUnique(String goodsCode);
	
	/*SearchResult getSearchedGoodsMaster(String searchBy,String searchText)throws GoodsMasterException;*/
	
	//public List getGoodsMasterList();
	public List getGoodsParentCodeList();
}
