package com.integrosys.cms.app.manualinput.party;

import java.util.Date;

public class PANValidationLog implements IPANValidationLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PANValidationLog() {
	}
	
    private long id;
    private String panNo;	
    private String partyID;	
    private Long cmsMainProfileID;
    private String requestMessage;
    private String responseMessage;
    private Date requestDateTime;
    private Date responseDateTime;
    private String responseCode;
    private String errorMessage;
    private String status;
    private Date lastValidatedDate;
    private String lastValidatedBy;
    private char isPANNoValidated;
    private String requestNo;
    private String partyNameAsPerPan;
    private String dateOfIncorporation;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getPartyID() {
		return partyID;
	}
	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}
	public Long getCmsMainProfileID() {
		return cmsMainProfileID;
	}
	public void setCmsMainProfileID(Long cmsMainProfileID) {
		this.cmsMainProfileID = cmsMainProfileID;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLastValidatedDate() {
		return lastValidatedDate;
	}
	public void setLastValidatedDate(Date lastValidatedDate) {
		this.lastValidatedDate = lastValidatedDate;
	}
	public String getLastValidatedBy() {
		return lastValidatedBy;
	}
	public void setLastValidatedBy(String lastValidatedBy) {
		this.lastValidatedBy = lastValidatedBy;
	}
	public char getIsPANNoValidated() {
		return isPANNoValidated;
	}
	public void setIsPANNoValidated(char isPANNoValidated) {
		this.isPANNoValidated = isPANNoValidated;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getPartyNameAsPerPan() {
		return partyNameAsPerPan;
	}
	public void setPartyNameAsPerPan(String partyNameAsPerPan) {
		this.partyNameAsPerPan = partyNameAsPerPan;
	}
	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}
	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}
	
}
