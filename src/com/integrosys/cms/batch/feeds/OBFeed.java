/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.batch.feeds;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Describes this class Purpose: A value object that represents a feed rate
 * Description:
 * 
 * @author $ravi$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class OBFeed {
	private String ric;

	private String isinCode;

	private String exchange;

	private double closePrice;

	private String currencyCode;

	private String description;

	private Date tradingDate;

	private Date maturityDate;

	private long totalOutStandingShares;

	private String ticker;

	private double offer;

	private String rating;

	private boolean isException;

	private boolean exceptionReasonCode;

	private String suspended = "N";

	private String boardType;

	private String shareType;

	private String fIIndicator;

	private String stockCode;

	private double parValue;

	private double paidUpCapital;

	private String shareStatus;

	private Date expiryDate;

	private String fundCode;

	private String country;

	private double NAV;

	private double stockIndexPrice;

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getExchange() {
		/*
		 * String mapVal = PropertyManager.getValue("reuters."+exchange);
		 * if(mapVal == null){ DefaultLogger.error(this,
		 * "Reuters Stock Exchange code mapping not found for : "+exchange);
		 * mapVal=""; } return mapVal;
		 */
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(Date tradingDate) {
		this.tradingDate = tradingDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public long getTotalOutStandingShares() {
		return totalOutStandingShares;
	}

	public void setTotalOutStandingShares(long totalOutStandingShares) {
		this.totalOutStandingShares = totalOutStandingShares;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getOffer() {
		return offer;
	}

	public void setOffer(double offer) {
		this.offer = offer;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean exception) {
		isException = exception;
	}

	public boolean isExceptionReasonCode() {
		return exceptionReasonCode;
	}

	public void setExceptionReasonCode(boolean exceptionReasonCode) {
		this.exceptionReasonCode = exceptionReasonCode;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String getSuspended() {
		return suspended;
	}

	public void setSuspended(String suspended) {
		/*
		 * if (suspended != null && suspended.equalsIgnoreCase("sus")) {
		 * isSuspended = "Y"; } isSuspended = "N";
		 */
		this.suspended = suspended;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public String getFIIndicator() {
		return fIIndicator;
	}

	public void setFIIndicator(String fIIndicator) {
		this.fIIndicator = fIIndicator;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getParValue() {
		return parValue;
	}

	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	public double getPaidUpCapital() {
		return paidUpCapital;
	}

	public void setPaidUpCapital(double paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public double getStockIndexPrice() {
		return stockIndexPrice;
	}

	public void setStockIndexPrice(double stockIndexPrice) {
		this.stockIndexPrice = stockIndexPrice;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getNAV() {
		return NAV;
	}

	public void setNAV(double NAV) {
		this.NAV = NAV;
	}

}
