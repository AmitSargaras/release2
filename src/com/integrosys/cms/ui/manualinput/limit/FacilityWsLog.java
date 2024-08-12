package com.integrosys.cms.ui.manualinput.limit;

import java.util.Date;

public class FacilityWsLog implements IFacilityWsLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacilityWsLog() {
	}
	
    private long id;
    private String partyId;
    private String serialNo;	
    private String facilitySysId;	
    private String lineNo;
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getFacilitySysId() {
		return facilitySysId;
	}
	public void setFacilitySysId(String facilitySysId) {
		this.facilitySysId = facilitySysId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
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
	public String getCoBorrowerDropDownId() {
		return coBorrowerDropDownId;
	}
	public void setCoBorrowerDropDownId(String coBorrowerDropDownId) {
		this.coBorrowerDropDownId = coBorrowerDropDownId;
	}
	@Override
	public String getLiability() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLiability(String liability) {
		// TODO Auto-generated method stub
		
	}
   

}
