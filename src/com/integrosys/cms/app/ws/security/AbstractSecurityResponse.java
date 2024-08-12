package com.integrosys.cms.app.ws.security;

import com.integrosys.cms.app.collateral.bus.ICollateral;

public abstract class AbstractSecurityResponse implements SecurityResponseInterface{
	
	@Override
	public String getResponseMessage(ICollateral iCollateralInstance,String... args){
		return null;
	}
	
	protected String setResponseMessageValues(String lbl,String value){
		String responseString =  "\n" + lbl + DELIMITER_COLON + SPACE + value + SPACE+DELIMITER;
		return responseString;
	}
	protected String setLastLineMessageInResponse(String lbl,String value){
		String responseString =  "\n" + lbl + DELIMITER_COLON + SPACE + value;
		return responseString;
	}
}
