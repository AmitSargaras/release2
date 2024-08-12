package com.integrosys.cms.app.cersaiMapping.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface ICersaiMappingJdbc {

	List getMasterList();

	ICersaiMapping[] getMasterValueList(String masterName);
	
	void insertDataIntoCersaiStaging(StringBuffer sqlInsertQuery);

	SearchResult getAllCersaiMapping()throws CersaiMappingException;
	
	SearchResult getAllCersaiMapping(String mastername)throws CersaiMappingException;

	String getNameOfMaster(String name) throws Exception;

	ICersaiMapping[] fetchValueList(String stagingRefId);

	String getMasterName(String stagingRefId)throws Exception;

	void insertDataIntoCersaiActual(StringBuffer sqlInsertQuery);

	ICersaiMapping[] getMasterListOfValues(String mastername);

}
