package com.aurionpro.clims.rest.common;

public class ValidationErrorDetailsDTO {
	
	public ValidationErrorDetailsDTO() {}
	

	protected String field;
	
	protected String errorCode;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


}
