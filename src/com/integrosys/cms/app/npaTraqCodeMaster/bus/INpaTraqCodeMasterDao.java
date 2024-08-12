package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.productMaster.bus.*;

public interface INpaTraqCodeMasterDao {

	static final String ACTUAL_NPA_TRAQ_CODE_MASTER_NAME = "actualNpaTraqCodeMaster";
	static final String STAGE_NPA_TRAQ_CODE_MASTER_NAME = "stageNpaTraqCodeMaster";
	
	INpaTraqCodeMaster getNpaTraqCodeMaster(String entityName, Serializable key)throws NpaTraqCodeMasterException;
	INpaTraqCodeMaster updateNpaTraqCodeMaster(String entityName, INpaTraqCodeMaster item)throws NpaTraqCodeMasterException;
	INpaTraqCodeMaster createNpaTraqCodeMaster(String entityName, INpaTraqCodeMaster excludedFacility)throws NpaTraqCodeMasterException;
	
	public boolean isNpaTraqCodeUnique(String npaTraqCode, String securityType, String securitySubType, String propertyTypeDesc);
	
	/*SearchResult getSearchedProductMaster(String searchBy,String searchText)throws ProductMasterException;*/
	
	public List getNpaTraqCodeMasterList();
}
