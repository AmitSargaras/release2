package com.aurionpro.clims.rest.dto;

import java.util.List;

public class CamBodyRestRequestDTO{

	private List<RestApiHeaderRequestDTO> headerDetails;		
	
	private List<CamDetailsRestRequestDTO> bodyDetails;

	
	public List<RestApiHeaderRequestDTO> getHeaderDetails() {
		return headerDetails;
	}
	public void setHeaderDetails(List<RestApiHeaderRequestDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}
	public List<CamDetailsRestRequestDTO> getBodyDetails() {
		return bodyDetails;
	}
	public void setBodyDetails(List<CamDetailsRestRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}
	
}
