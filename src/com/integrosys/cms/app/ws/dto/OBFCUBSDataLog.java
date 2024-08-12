package com.integrosys.cms.app.ws.dto;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

public class OBFCUBSDataLog implements IFCUBSDataLog {
	
	public OBFCUBSDataLog() {}
	
    private long id;
    private String partyId;
	private String partyName;
	private String fileName;
	private String sourceRefNo;
	private String liabilityId;
	private String lineNo;
	private String serialNo;
	private String liabBranch;
	private String currency;
	private Double limitAmount;
	private String tenor;
	private Date startDate;
	private Date expiryDate;
	private String action;
	private Date requestDateTime;
	private String status;
	private Date responseDateTime;
	private String errorCode;
	private String errorDesc;
	private Date makerDateTime;
	private Date checkerDateTime;
	private String makerId;
	private String checkerId;
	private Date intradayLimitExpiryDate;
	private String dayLightLimit;
	private String intradayLimitFlag;
	private String stockDocMonthAndYear;
	
	public String getStockDocMonthAndYear() {
		return stockDocMonthAndYear;
	}
	public void setStockDocMonthAndYear(String stockDocMonthAndYear) {
		this.stockDocMonthAndYear = stockDocMonthAndYear;
	}
	public Date getIntradayLimitExpiryDate() {
		return intradayLimitExpiryDate;
	}
	public void setIntradayLimitExpiryDate(Date intradayLimitExpiryDate) {
		this.intradayLimitExpiryDate = intradayLimitExpiryDate;
	}
	public String getDayLightLimit() {
		return dayLightLimit;
	}
	public void setDayLightLimit(String dayLightLimit) {
		this.dayLightLimit = dayLightLimit;
	}
	public String getIntradayLimitFlag() {
		return intradayLimitFlag;
	}
	public void setIntradayLimitFlag(String intradayLimitFlag) {
		this.intradayLimitFlag = intradayLimitFlag;
	}
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
	/**
	 * @return the partyId
	 */
	public String getPartyId() {
		return partyId;
	}
	/**
	 * @param partyId the partyId to set
	 */
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	/**
	 * @return the partyName
	 */
	public String getPartyName() {
		return partyName;
	}
	/**
	 * @param partyName the partyName to set
	 */
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the sourceRefNo
	 */
	public String getSourceRefNo() {
		return sourceRefNo;
	}
	/**
	 * @param sourceRefNo the sourceRefNo to set
	 */
	public void setSourceRefNo(String sourceRefNo) {
		this.sourceRefNo = sourceRefNo;
	}
	/**
	 * @return the liabilityId
	 */
	public String getLiabilityId() {
		return liabilityId;
	}
	/**
	 * @param liabilityId the liabilityId to set
	 */
	public void setLiabilityId(String liabilityId) {
		this.liabilityId = liabilityId;
	}
	/**
	 * @return the lineNo
	 */
	public String getLineNo() {
		return lineNo;
	}
	/**
	 * @param lineNo the lineNo to set
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the liabBranch
	 */
	public String getLiabBranch() {
		return liabBranch;
	}
	/**
	 * @param liabBranch the liabBranch to set
	 */
	public void setLiabBranch(String liabBranch) {
		this.liabBranch = liabBranch;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the limitAmount
	 */
	public Double getLimitAmount() {
		return limitAmount;
	}
	/**
	 * @param limitAmount the limitAmount to set
	 */
	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	/**
	 * @return the tenor
	 */
	public String getTenor() {
		return tenor;
	}
	/**
	 * @param tenor the tenor to set
	 */
	public void setTenor(String tenor) {
		this.tenor = tenor;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the requestDateTime
	 */
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	/**
	 * @param requestDateTime the requestDateTime to set
	 */
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the responseDateTime
	 */
	public Date getResponseDateTime() {
		return responseDateTime;
	}
	/**
	 * @param responseDateTime the responseDateTime to set
	 */
	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
}
