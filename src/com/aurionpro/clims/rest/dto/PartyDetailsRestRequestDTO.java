/**
 * 
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

public class PartyDetailsRestRequestDTO{
	
	
	private List<RestApiHeaderRequestDTO> headerDetails;
	
	private List<PartyDetailsRestBodyRequestDTO> bodyDetails;

	public List<RestApiHeaderRequestDTO> getHeaderDetails() {
		return headerDetails;
	}

	public void setHeaderDetails(List<RestApiHeaderRequestDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}

	public List<PartyDetailsRestBodyRequestDTO> getBodyDetails() {
		return bodyDetails;
	}

	public void setBodyDetails(List<PartyDetailsRestBodyRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}

	
	

	
		

}
