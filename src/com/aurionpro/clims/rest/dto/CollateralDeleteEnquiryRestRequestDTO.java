
package com.aurionpro.clims.rest.dto;

import java.util.List;

public class CollateralDeleteEnquiryRestRequestDTO {
	
	private List<RestApiHeaderRequestDTO> headerDetails;		
	
	private List<CollateralDelEnqDetailslRequestDTO> bodyDetails;
	
	public List<RestApiHeaderRequestDTO> getHeaderDetails() {
		return headerDetails;
	}
	public void setHeaderDetails(List<RestApiHeaderRequestDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}
	public List<CollateralDelEnqDetailslRequestDTO> getBodyDetails() {
		return bodyDetails;
	}
	public void setBodyDetails(List<CollateralDelEnqDetailslRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}
}