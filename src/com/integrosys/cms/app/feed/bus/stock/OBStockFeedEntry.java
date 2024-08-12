/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/OBStockFeedEntry.java,v 1.5 2003/08/14 10:57:07 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/14 10:57:07 $ Tag: $Name: $
 * 
 */
public class OBStockFeedEntry implements IStockFeedEntry {
	
	private String scriptCode;
	
	private String scriptName;
	
	private double scriptValue;
	
	private Date lastUpdatedDate;

	private long stockFeedEntryID;

	private long versionTime = 0;
	
	private long stockFeedEntryRef;
	
	private double faceValue;
	
	private String stockExchangeName;
	
	
	
	private String ticker;

	private double offer;

	private double unitPrice;

	private String type;

	private String countryCode;

	private String ric;

	private String fundManagerName;

	private String currencyCode;

	private String isinCode;

	private String exchange;

	private String blackListed;

	private String suspended;

	private Date expiryDate;
	
	private FormFile fileUpload;
	
	private String groupStockType;

	/**
	 * @return the scriptValue
	 */
	public double getScriptValue() {
		return scriptValue;
	}

	/**
	 * @param scriptValue the scriptValue to set
	 */
	public void setScriptValue(double scriptValue) {
		this.scriptValue = scriptValue;
	}

	/**
	 * @return the scriptCode
	 */
	public String getScriptCode() {
		return scriptCode;
	}

	/**
	 * @param scriptCode the scriptCode to set
	 */
	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}

	/**
	 * @return the scriptName
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * @param scriptName the scriptName to set
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * @return the faceValue
	 */
	public double getFaceValue() {
		return faceValue;
	}

	/**
	 * @param faceValue the faceValue to set
	 */
	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}

	/**
	 * @return the stockExchangeName
	 */
	public String getStockExchangeName() {
		return stockExchangeName;
	}

	/**
	 * @param stockExchangeName the stockExchangeName to set
	 */
	public void setStockExchangeName(String stockExchangeName) {
		this.stockExchangeName = stockExchangeName;
	}
	
	

	public String getBlackListed() {
		return blackListed;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getExchange() {
		return exchange;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public String getFundManagerName() {
		return fundManagerName;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public double getOffer() {
		return offer;
	}

	public String getRic() {
		return ric;
	}

	public long getStockFeedEntryID() {
		return this.stockFeedEntryID;
	}

	public long getStockFeedEntryRef() {
		return stockFeedEntryRef;
	}

	public String getSuspended() {
		return suspended;
	}

	public String getTicker() {
		return ticker;
	}

	public String getType() {
		return type;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setFundManagerName(String fundManagerName) {
		this.fundManagerName = fundManagerName;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public void setLastUpdatedDate(Date lastUpdateDate) {
		this.lastUpdatedDate = lastUpdateDate;
	}


	public void setOffer(double offer) {
		this.offer = offer;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public void setStockFeedEntryID(long param) {
		this.stockFeedEntryID = param;
	}

	public void setStockFeedEntryRef(long stockFeedEntryRef) {
		this.stockFeedEntryRef = stockFeedEntryRef;
	}

	public void setSuspended(String suspended) {
		this.suspended = suspended;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((isinCode == null) ? 0 : isinCode.hashCode());
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final OBStockFeedEntry other = (OBStockFeedEntry) obj;
		if (scriptCode == null) {
			if (other.scriptCode != null) {
				return false;
			}
		}
		else if (!scriptCode.equals(other.scriptCode)) {
			return false;
		}

		if (ticker == null) {
			if (other.ticker != null) {
				return false;
			}
		}
		else if (!ticker.equals(other.ticker)) {
			return false;
		}

		return true;
	}

	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getGroupStockType() {
		return groupStockType;
	}

	public void setGroupStockType(String groupStockType) {
		this.groupStockType = groupStockType;
	}

}