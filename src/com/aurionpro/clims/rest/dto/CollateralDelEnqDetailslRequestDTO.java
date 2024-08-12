package com.aurionpro.clims.rest.dto;

import org.apache.struts.action.ActionErrors;

public class CollateralDelEnqDetailslRequestDTO {
	
	private String event;
	
	private ActionErrors errors;

	private String securityId;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	
	

}
