package com.integrosys.cms.ui.manualinput.limit;

import java.util.Date;

public class CoBorrower_WsLog implements ICoBorrower_WsLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoBorrower_WsLog() {
	}
	
    private long id;
    private String partyId;
    private String coBorrowerDropDownId;	
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
	
	
	
	public String getCoBorrowerDropDownId() {
		return coBorrowerDropDownId;
	}
	public void setCoBorrowerDropDownId(String coBorrowerDropDownId) {
		this.coBorrowerDropDownId = coBorrowerDropDownId;
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
