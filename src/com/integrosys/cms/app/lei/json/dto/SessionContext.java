package com.integrosys.cms.app.lei.json.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionContext {

	@JsonProperty("channel")
	private String channel;
	@JsonProperty("userId")
	private String userId ;
	@JsonProperty("externalReferenceNo")
	private String externalReferenceNo;
	@JsonProperty("bankCode")
	private String bankCode;
	@JsonProperty("transactionBranch")
	private String transactionBranch;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getExternalReferenceNo() {
		return externalReferenceNo;
	}
	public void setExternalReferenceNo(String externalReferenceNo) {
		this.externalReferenceNo = externalReferenceNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getTransactionBranch() {
		return transactionBranch;
	}
	public void setTransactionBranch(String transactionBranch) {
		this.transactionBranch = transactionBranch;
	}
	
	
}
