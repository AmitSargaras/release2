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


public class FacilityDetlRestRequestDTO extends RestApiHeaderRequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	private List<RestApiHeaderRequestDTO> headerDetails;
	
	private List<FacilityBodyRestRequestDTO> bodyDetails;

	
	
	
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

	public List<FacilityBodyRestRequestDTO> getBodyDetails() {
		return bodyDetails;
	}

	public void setBodyDetails(List<FacilityBodyRestRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}

	


	

	
	}