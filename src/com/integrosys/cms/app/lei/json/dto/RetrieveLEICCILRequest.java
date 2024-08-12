package com.integrosys.cms.app.lei.json.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveLEICCILRequest {
	
	@JsonProperty("ReferenceNumber")
	private String referenceNumber;
	@JsonProperty("ClientId")
	private String clientId ;
	@JsonProperty("Checksum")
	private String checksum;
	@JsonProperty("LEINumber")
	private String leiNumber;
	@JsonProperty("Identifier")
	private String identifier;
	
	
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getLeiNumber() {
		return leiNumber;
	}
	public void setLeiNumber(String leiNumber) {
		this.leiNumber = leiNumber;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
