package com.integrosys.cms.app.contentManager.exception;

public class ContentManagerInitializationException extends Exception {
	
	String errorMessage;
	
	public ContentManagerInitializationException (String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getMessage() {
		return errorMessage;
	}
	
}
