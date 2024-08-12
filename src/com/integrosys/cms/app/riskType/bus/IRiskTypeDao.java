package com.integrosys.cms.app.riskType.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface IRiskTypeDao {

	static final String ACTUAL_RISK_TYPE_NAME = "actualRiskType";
	static final String STAGE_RISK_TYPE_NAME = "stageRiskType";
	
	IRiskType getRiskType(String entityName, Serializable key)throws RiskTypeException;
	IRiskType updateRiskType(String entityName, IRiskType item)throws RiskTypeException;
	IRiskType createRiskType(String entityName, IRiskType excludedFacility)
			throws RiskTypeException;
	IRiskType deleteRiskType(String entityName, IRiskType item)throws RiskTypeException;
	
	//public boolean isProductCodeUnique(String productCode);
	
	/*SearchResult getSearchedRiskType(String searchBy,String searchText)throws RiskTypeException;*/
	
	public List getRiskTypeList();
}
