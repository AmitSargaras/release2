/**
 * 
 */
package com.integrosys.cms.app.ws.common;

/**
 * @author bhushan.malankar
 *
 */
public enum CLIMSWebService {
	
	PARTY, 
	CAM, 
	FACILITY, 
	DESCRIPENCY, 
	SECURITY, 
	SECURITYSEARCH, 
	DOCUMENTS,
	DIGITALLIBRARY,
	COMMONCODE,
	UPDATE_SECURITY,
	ISACUSER,
	WMS,
	UDF;
	
	public String fieldPrefix(){
		
		return "field."+name().toLowerCase()+".";
	}

}
