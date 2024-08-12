package com.integrosys.cms.app.lei.json.dao;

import java.util.Date;

public class OBLEIValidationLog implements ILEIValidationLog {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public OBLEIValidationLog() {}

private long id;
private String partyId;	
private String leiCode;
private Long cmsMainProfileID;
private String interfaceName;
private Date requestDateTime;
private Date responseDateTime;
private String requestMessage;
private String responseMessage;
private String errorMessage;
private String errorCode;
private String status;
private Date lastValidatedDate;
private String lastValidatedBy;
private char isLEICodeValidated;

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getInterfaceName() {
	return interfaceName;
}
public void setInterfaceName(String interfaceName) {
	this.interfaceName = interfaceName;
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
public String getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}
public String getErrorCode() {
	return errorCode;
}
public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
}
public String getRequestMessage() {
	return requestMessage;
}
public void setRequestMessage(String requestMessage) {
	this.requestMessage = requestMessage;
}
public String  getResponseMessage() {
	return responseMessage;
}
public void setResponseMessage(String responseMessage) {
	this.responseMessage = responseMessage;
}
public String getPartyId() {
	return partyId;
}
public void setPartyId(String partyId) {
	this.partyId = partyId;
}
public String getLeiCode() {
	return leiCode;
}
public void setLeiCode(String leiCode) {
	this.leiCode = leiCode;
}
public Long getCmsMainProfileID() {
	return cmsMainProfileID;
}
public void setCmsMainProfileID(Long cmsMainProfileID) {
	this.cmsMainProfileID = cmsMainProfileID;
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
public char getIsLEICodeValidated() {
	return isLEICodeValidated;
}
public void setIsLEICodeValidated(char isLEICodeValidated) {
	this.isLEICodeValidated = isLEICodeValidated;
}

}
