package com.integrosys.cms.app.ws.dto;

import java.util.Date;

public class OBPSRDataLog implements IPSRDataLog{

	public OBPSRDataLog() {

	}

	private long id;
	private String partyId;
	private String partyName;
	private String fileName;
	private String sourceRefNo;
	private String liabilityId;
	private String systemId;
	private String lineNo;
	private String serialNo;
	private String currency;
	private Double limitAmount;
	private String tenor;
	private String sellDownPeriod;
	private Date startDate;
	private Date expiryDate;
	private String action;
	private Date requestDateTime;
	private Date responseDateTime;
	private String status;
	private String errorCode;
	private String errorDesc;
	private Date makerDateTime;
	private Date checkerDateTime;
	private String makerId;
	private String checkerId;
	private String segment;

	public Date getMakerDateTime() {
		return makerDateTime;
	}

	public void setMakerDateTime(Date makerDateTime) {
		this.makerDateTime = makerDateTime;
	}

	public Date getCheckerDateTime() {
		return checkerDateTime;
	}

	public void setCheckerDateTime(Date checkerDateTime) {
		this.checkerDateTime = checkerDateTime;
	}

	public String getMakerId() {
		return makerId;
	}

	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

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

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSourceRefNo() {
		return sourceRefNo;
	}

	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}

	public String getLiabilityId() {
		return liabilityId;
	}

	public void setLiabilityId(String liabilityId) {
		this.liabilityId = liabilityId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSellDownPeriod() {
		return sellDownPeriod;
	}

	public void setSellDownPeriod(String sellDownPeriod) {
		this.sellDownPeriod = sellDownPeriod;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

}
