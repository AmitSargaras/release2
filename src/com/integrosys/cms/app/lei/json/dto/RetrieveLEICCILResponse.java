package com.integrosys.cms.app.lei.json.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RetrieveLEICCILResponse {

	@JsonProperty("ReferenceNumber") 
	private String referenceNumber;
	@JsonProperty("ValidityStatus")
	private String validityStatus;
	@JsonProperty("LEIDetails")
	private String leiDetails;
	@JsonProperty("HttpStatusCode")
	private int httpStatusCode;
	@JsonProperty("ErrorMessage")
	private String errorMessage;
	@JsonProperty("ErrorCode")
	private String errorCode;
	@JsonProperty("Signature") 
	private String signature;	
	
	
	public RetrieveLEICCILResponse() {	}


	public String getReferenceNumber() {
		return referenceNumber;
	}


	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	public String getValidityStatus() {
		return validityStatus;
	}


	public void setValidityStatus(String validityStatus) {
		this.validityStatus = validityStatus;
	}


	public String getLeiDetails() {
		return leiDetails;
	}


	public void setLeiDetails(String leiDetails) {
		this.leiDetails = leiDetails;
	}


	public int getHttpStatusCode() {
		return httpStatusCode;
	}


	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	@Override
	public String toString() {
		  StringBuilder str = new StringBuilder();
		  str.append("referenceNumber " + getReferenceNumber());
		  str.append("validityStatus " + getValidityStatus());
		  str.append("leiDetails " + getLeiDetails());
		  str.append("httpStatusCode " + getHttpStatusCode());
		  str.append("errorCode " + getErrorCode());
		  str.append("errorMessage " + getErrorMessage());
		  str.append("signature " + getSignature());
		  return str.toString();
	}

}
