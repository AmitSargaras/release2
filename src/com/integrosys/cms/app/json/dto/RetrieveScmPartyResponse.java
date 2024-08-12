package com.integrosys.cms.app.json.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveScmPartyResponse {

	private String statusCode;
	private String responseMessage;
	private String statusMessage;
	
	
	@JsonCreator
	public RetrieveScmPartyResponse(@JsonProperty("statusCode") String statusCode,@JsonProperty("statusMessage")String statusMessage,@JsonProperty("responseMessage")String responseMessage) {
		this.statusCode = statusCode;
		this.responseMessage = responseMessage;
		this.statusMessage = statusMessage;
	}
	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getResponseMessage() {
		return responseMessage;
	}


	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}


	public String getStatusMessage() {
		return statusMessage;
	}


	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	
	@Override
	 public String toString() {
	  StringBuilder str = new StringBuilder();
	  str.append("statusCode " + getStatusCode());
	  str.append("responseMessage " + getResponseMessage());
	  str.append("statusMessage " + getStatusMessage());
	  return str.toString();
	 }
}
