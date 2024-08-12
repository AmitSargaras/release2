package com.aurionpro.clims.rest.dto;

import java.util.List;

public class ABEnquiryResponseDTO {
	
	private List<HeaderDetailRestResponseDTO> headerDetails;

	private List bodyDetails;
	
	public List<HeaderDetailRestResponseDTO> getHeaderDetails() {
		return headerDetails;
	}

	public void setHeaderDetails(List<HeaderDetailRestResponseDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}

	public List getBodyDetails() {
		return bodyDetails;
	}

	public void setBodyDetails(List bodyDetails) {
		this.bodyDetails = bodyDetails;
	}

}
