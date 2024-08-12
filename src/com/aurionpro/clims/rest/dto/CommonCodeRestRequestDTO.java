/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

import org.apache.struts.action.ActionErrors;


/**

 * @author $Author: Bhushan$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class CommonCodeRestRequestDTO  {
	
	private List<RestApiHeaderRequestDTO> headerDetails;
	
	private List<RestApiBodyRequestDTO> bodyDetails;

	
	
	
	private ActionErrors errors;
	
	
	private String event;

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

	public List<RestApiHeaderRequestDTO> getHeaderDetails() {
		return headerDetails;
	}

	public void setHeaderDetails(List<RestApiHeaderRequestDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}

	public List<RestApiBodyRequestDTO> getBodyDetails() {
		return bodyDetails;
	}

	public void setBodyDetails(List<RestApiBodyRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}

	

	
	
	}