package com.integrosys.cms.app.productMaster.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface IProductMasterDao {

	static final String ACTUAL_PRODUCT_MASTER_NAME = "actualProductMaster";
	static final String STAGE_PRODUCT_MASTER_NAME = "stageProductMaster";
	
	IProductMaster getProductMaster(String entityName, Serializable key)throws ProductMasterException;
	IProductMaster updateProductMaster(String entityName, IProductMaster item)throws ProductMasterException;
	IProductMaster createProductMaster(String entityName, IProductMaster excludedFacility)
			throws ProductMasterException;
	
	public boolean isProductCodeUnique(String productCode);
	
	/*SearchResult getSearchedProductMaster(String searchBy,String searchText)throws ProductMasterException;*/
	
	public List getProductMasterList();
}
