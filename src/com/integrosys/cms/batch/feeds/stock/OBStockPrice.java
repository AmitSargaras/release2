package com.integrosys.cms.batch.feeds.stock;

import java.util.Date;

public class OBStockPrice implements IStockPrice {

	private long tempID;

	private String recordType;

	private String stockCode;

	private String counterName;

	private String exchangeCode;

	private Double unitPrice;

	private String unitPriceCurrency;

	private String isinCode;

	private String stockExcTraBoardCode;

	private String stockExcTraBoardDes;

	private String stockTypes;

	private long listedSharesQuantity;

	private Double parValue;

	private Double paidUpCapital;

	private Date expiryDate;

	private String shareStatus;

	private String stockExcSusCouIndicator;

    private Date dateLaunched;

    public long getTempID() {
		return tempID;
	}

	public void setTempID(long tempID) {
		this.tempID = tempID;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUnitPriceCurrency() {
		return unitPriceCurrency;
	}

	public void setUnitPriceCurrency(String unitPriceCurrency) {
		this.unitPriceCurrency = unitPriceCurrency;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getStockExcTraBoardCode() {
		return stockExcTraBoardCode;
	}

	public void setStockExcTraBoardCode(String stockExcTraBoardCode) {
		this.stockExcTraBoardCode = stockExcTraBoardCode;
	}

	public String getStockExcTraBoardDes() {
		return stockExcTraBoardDes;
	}

	public void setStockExcTraBoardDes(String stockExcTraBoardDes) {
		this.stockExcTraBoardDes = stockExcTraBoardDes;
	}

	public String getStockTypes() {
		return stockTypes;
	}

	public void setStockTypes(String stockTypes) {
		this.stockTypes = stockTypes;
	}

	public long getListedSharesQuantity() {
		return listedSharesQuantity;
	}

	public void setListedSharesQuantity(long listedSharesQuantity) {
		this.listedSharesQuantity = listedSharesQuantity;
	}

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public Double getPaidUpCapital() {
		return paidUpCapital;
	}

	public void setPaidUpCapital(Double paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	public String getStockExcSusCouIndicator() {
		return stockExcSusCouIndicator;
	}

	public void setStockExcSusCouIndicator(String stockExcSusCouIndicator) {
		this.stockExcSusCouIndicator = stockExcSusCouIndicator;
	}

    public Date getDateLaunched() {
        return dateLaunched;
    }

    public void setDateLaunched(Date dateLaunched) {
        this.dateLaunched = dateLaunched;
    }
}
