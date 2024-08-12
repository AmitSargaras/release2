package com.aurionpro.clims.rest.dto;

public class RestApiHeaderRequestDTO{
	
	private String channelCode;
	
	private String requestId;
	
	private String passCode;

	
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}
	
}