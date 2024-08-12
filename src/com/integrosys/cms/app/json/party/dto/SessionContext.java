package com.integrosys.cms.app.json.party.dto;

public class SessionContext{
	public String channel;
    public String userId;
    public String externalReferenceNo;
    public String bankCode;
    public String transactionBranch;
    public String transactingPartyCode;
    
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
	public String getTransactingPartyCode() {
		return transactingPartyCode;
	}
	public void setTransactingPartyCode(String transactingPartyCode) {
		this.transactingPartyCode = transactingPartyCode;
	}
	
}
