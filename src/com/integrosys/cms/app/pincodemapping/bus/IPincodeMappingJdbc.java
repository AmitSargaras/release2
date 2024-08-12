package com.integrosys.cms.app.pincodemapping.bus;

import java.util.List;
import java.util.Map;


public interface IPincodeMappingJdbc {

	List getAllPincodeMapping() throws PincodeMappingException;
	
	public Map<String,String> getActiveStatePinCodeMap();
}
