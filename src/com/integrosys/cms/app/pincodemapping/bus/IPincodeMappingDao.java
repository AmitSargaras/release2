package com.integrosys.cms.app.pincodemapping.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

public interface IPincodeMappingDao {

	static final String ACTUAL_PINCODE_MAPPING_NAME = "actualOBPincodeMapping";

	static final String STAGE_PINCODE_MAPPING_NAME = "stageOBPincodeMapping";
	
	IPincodeMapping getPincodeMapping(String entityName, Serializable key)
			throws PincodeMappingException;
	
	IPincodeMapping deletePincodeMapping(String entityName, IPincodeMapping item)
			throws PincodeMappingException;
	
	IPincodeMapping getPincodeMappingById(Serializable key)
	throws PincodeMappingException;

	IPincodeMapping updatePincodeMapping(String entityName,IPincodeMapping item)
			throws PincodeMappingException;

	IPincodeMapping createPincodeMapping(String entityName,IPincodeMapping pincodeMapping)
			throws PincodeMappingException;
	
	public boolean isPincodeMappingValid(String pincode,String stateCode);
	
	public String getStateCodeFromId(String entityName,String stateId);
	//public String getCustomerIdCode() ;

	public SearchResult getPincodeMappingList(String type, String text) throws PincodeMappingException;

	public boolean isStateIdUnique(String stateId,String pincode);
	public List getStateList();
	
}
