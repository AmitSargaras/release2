package com.integrosys.cms.app.cersaiMapping.bus;

import java.io.Serializable;

public interface ICersaiMappingDao {

	static final String ACTUAL_CERSAI_MAPPING_NAME = "actualCersaiMapping";
	static final String STAGE_CERSAI_MAPPING_NAME = "stageCersaiMapping";
	
	ICersaiMapping createCersaiMapping(String cersaiMappingName, ICersaiMapping cersaiMapping)throws CersaiMappingException;

	ICersaiMapping getCersaiMapping(String entityName, Serializable key)throws CersaiMappingException;
	
	ICersaiMapping updateCersaiMapping(String entityName, ICersaiMapping item)throws CersaiMappingException;
	
}
