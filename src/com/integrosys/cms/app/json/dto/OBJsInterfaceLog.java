package com.integrosys.cms.app.json.dto;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;


public class OBJsInterfaceLog implements IJsInterfaceLog {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public OBJsInterfaceLog() {}

private long id;
private String interfaceName;
private Date requestDateTime;
private Date responseDateTime;
private String errorMessage;
private String errorCode;
private String status;
private Long transactionId;
private String requestMessage;
private String responseMessage;
private String partyId,partyName,moduleName,operation,scmFlag,limitProfileId,line_no,serial_no,is_udf_upload;	
private int failCount;

public String getIs_udf_upload() {
	return is_udf_upload;
}
public void setIs_udf_upload(String is_udf_upload) {
	this.is_udf_upload = is_udf_upload;
}
public String getLimitProfileId() {
	return limitProfileId;
}
public void setLimitProfileId(String limitProfileId) {
	this.limitProfileId = limitProfileId;
}
public String getLine_no() {
	return line_no;
}
public void setLine_no(String line_no) {
	this.line_no = line_no;
}
public String getSerial_no() {
	return serial_no;
}
public void setSerial_no(String serial_no) {
	this.serial_no = serial_no;
}
public String getScmFlag() {
	return scmFlag;
}
public void setScmFlag(String scmFlag) {
	this.scmFlag = scmFlag;
}
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
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Long getTransactionId() {
	return transactionId;
}
public void setTransactionId(Long transactionId) {
	this.transactionId = transactionId;
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
public String getPartyName() {
	return partyName;
}
public void setPartyName(String partyName) {
	this.partyName = partyName;
}
public String getModuleName() {
	return moduleName;
}
public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}
public String getOperation() {
	return operation;
}
public void setOperation(String operation) {
	this.operation = operation;
}
public int getFailCount() {
	return failCount;
 }
public void setFailCount(int failCount) {
	 this.failCount = failCount;
}
}
