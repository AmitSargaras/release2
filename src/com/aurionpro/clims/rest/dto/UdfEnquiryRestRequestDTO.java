package com.aurionpro.clims.rest.dto;

import java.util.List;

import org.apache.struts.action.ActionErrors;

public class UdfEnquiryRestRequestDTO {

	private List<RestApiHeaderRequestDTO> headerDetails;
	
	private List<UdfEnqRestRequestDTO> bodyDetails;
	
	private ActionErrors errors;
	
	private String event;


	public List<RestApiHeaderRequestDTO> getHeaderDetails() {
		return headerDetails;
	}

	public void setHeaderDetails(List<RestApiHeaderRequestDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}

	public List<UdfEnqRestRequestDTO> getBodyDetails() {
		return bodyDetails;
	}

	public void setBodyDetails(List<UdfEnqRestRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
