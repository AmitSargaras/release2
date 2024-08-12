package com.aurionpro.clims.rest.dto;

import java.util.List;


public class CommonRestResponseDTO {
	
	private List<HeaderDetailRestResponseDTO> headerDetails;
	
	private List<BodyRestResponseDTO> bodyDetails;

	public List<HeaderDetailRestResponseDTO> getHeaderDetails() {
		return headerDetails;
	}

	public void setHeaderDetails(List<HeaderDetailRestResponseDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}

	public List<BodyRestResponseDTO> getBodyDetails() {
		return bodyDetails;
	}

	public void setBodyDetails(List<BodyRestResponseDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}

	

	
	
	
	
	
}