package com.aurionpro.clims.rest.interfaceLog;

import java.util.Date;

public class OBRestInterfaceLog implements IRestInterfaceLog{
	
	private long id;
	private String requestId;
	private String channelCode;
	private String referenceNo;
	private String interfaceStatus;
	private String interfaceFailedReason;
	private String requestText;
	private Date   requestDate;
	private String responseText;
	private Date   responseDate;
	
	
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	public String getInterfaceFailedReason() {
		return interfaceFailedReason;
	}
	public void setInterfaceFailedReason(String interfaceFailedReason) {
		this.interfaceFailedReason = interfaceFailedReason;
	}
	public String getRequestText() {
		return requestText;
	}
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
