package com.integrosys.cms.ui.manualinput.customer;

import java.util.Date;

public class IfsccodeWsLog implements IIfsccodeWsLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IfsccodeWsLog() {
	}
	
    private long id;
    private String partyId;
    private String bankName;	
    private String branchName;	
    private String ifscCode;	
    
    private String requestMessage;
    private String responseMessage;
    private Date requestDateTime;
    private Date responseDateTime;
    private String responseCode;
    private String errorMessage;
   
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	

	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public Date getResponseDateTime() {
		return responseDateTime;
	}
	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
