package com.integrosys.cms.app.ws.security;

import java.text.SimpleDateFormat;

import com.integrosys.cms.app.collateral.bus.ICollateral;

public interface SecurityResponseInterface {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	
	public static final String SOURCE_SECURITY_ID_LBL = "Source Security Id";
	public static final String DELIMITER_COLON = ":";
	public static final String SPACE = " ";
	public static final String DELIMITER = "|||";
	
	public String getResponseMessage(ICollateral iCollateralInstance,String... args);
}
