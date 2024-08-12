package com.aurionpro.clims.rest.dto;

import java.util.List;

public class CollateralDetailslRestResponseDTO {
	
	private String responseStatus;

	private List<ResponseMessageDetailDTO> responseMessageList;

	private String securityId;

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<ResponseMessageDetailDTO> getResponseMessageList() {
		return responseMessageList;
	}

	public void setResponseMessageList(
			List<ResponseMessageDetailDTO> responseMessageList) {
		this.responseMessageList = responseMessageList;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	



}
