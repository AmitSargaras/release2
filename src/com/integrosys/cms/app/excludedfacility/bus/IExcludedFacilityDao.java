package com.integrosys.cms.app.excludedfacility.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.search.SearchResult;


public interface IExcludedFacilityDao {

	static final String ACTUAL_EXCLUDED_FACILITY_NAME = "actualExcludedFacility";
	static final String STAGE_EXCLUDED_FACILITY_NAME = "stageExcludedFacility";
	
	IExcludedFacility getExcludedFacility(String entityName, Serializable key)throws ExcludedFacilityException;
	IExcludedFacility updateExcludedFacility(String entityName, IExcludedFacility item)throws ExcludedFacilityException;
	IExcludedFacility createExcludedFacility(String entityName, IExcludedFacility excludedFacility)
			throws ExcludedFacilityException;
	
	public boolean isExcludedFacilityDescriptionUnique(String excludedFacilityDescription);
	
	SearchResult getSearchedExcludedFacility(String searchBy,String searchText)throws ExcludedFacilityException;
}
