package com.integrosys.cms.ui.manualinput.limit;

import java.util.List;

public class LimitCalculationItem {
	
	private String currency;

	private String currencyRate;
	
	private String lineNo;
	
	private String limitAmount;
	
	private String limitInInr;
	
	private String fdMargin;
	
	private String fdRequired;
	
	private String facilityName;
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String getLimitInInr() {
		return limitInInr;
	}

	public void setLimitInInr(String limitInInr) {
		this.limitInInr = limitInInr;
	}

	public String getFdMargin() {
		return fdMargin;
	}

	public void setFdMargin(String fdMargin) {
		this.fdMargin = fdMargin;
	}

	public String getFdRequired() {
		return fdRequired;
	}

	public void setFdRequired(String fdRequired) {
		this.fdRequired = fdRequired;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
}
