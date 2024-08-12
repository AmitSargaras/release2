/**
 * 
 */
package com.integrosys.cms.app.ws.common;

/**
 * @author bhushan.malankar
 *
 */
public enum WebServiceStatusCode {
	SUCCESS("Success"),
	FAIL("Fail"),
	VALIDATION_ERROR("Found problem in validating request"),
	SERVER_ERROR("Server error");
	
	private WebServiceStatusCode(String message) {
		this.message = message;
	}
	
	public final String message;
	
	
}
