package com.integrosys.cms.batch.ubs;

public class OBUbscopy implements IUbscopy{
	private long id;
	private String customerId;
	private String lineNo;
	private long srNo;
	private String currencyCode;
	private double limitAmount;
	private double utilizationAmount;

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public long getSrNo() {
		return srNo;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public double getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}
	public double getUtilizationAmount() {
		return utilizationAmount;
	}
	public void setUtilizationAmount(double utilizationAmount) {
		this.utilizationAmount = utilizationAmount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
