/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/OBStockIndexFeedEntry.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Date;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 * 
 */
public class OBStockIndexFeedEntry implements IStockIndexFeedEntry {

	private String ticker;

	private double offer;

	private double unitPrice;

	private String type;

	private String countryCode;

	private String ric;

	private String name;

	private String fundManagerName;

	private String currencyCode;

	private String isinCode;

	private String exchange;

	private Date lastUpdatedDate;

	private long stockIndexFeedEntryID;

	private long versionTime = 0;

	private String blackListed;

	private String suspended;

	private long stockIndexFeedEntryRef;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getFundManagerName() {
		return fundManagerName;
	}

	public void setFundManagerName(String fundManagerName) {
		this.fundManagerName = fundManagerName;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdateDate) {
		this.lastUpdatedDate = lastUpdateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOffer() {
		return offer;
	}

	public void setOffer(double offer) {
		this.offer = offer;
	}

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public long getStockIndexFeedEntryID() {
		return this.stockIndexFeedEntryID;
	}

	public void setStockIndexFeedEntryID(long param) {
		this.stockIndexFeedEntryID = param;
	}

	public long getStockIndexFeedEntryRef() {
		return stockIndexFeedEntryRef;
	}

	public void setStockIndexFeedEntryRef(long stockIndexFeedEntryRef) {
		this.stockIndexFeedEntryRef = stockIndexFeedEntryRef;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getBlackListed() {
		return blackListed;
	}

	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}

	public String getSuspended() {
		return suspended;
	}

	public void setSuspended(String suspended) {
		this.suspended = suspended;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((isinCode == null) ? 0 : isinCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());

		long temp;
		temp = Double.doubleToLongBits(unitPrice);

		result = prime * result + (int) (temp ^ (temp >>> 32));

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

		final OBStockIndexFeedEntry other = (OBStockIndexFeedEntry) obj;
		if (isinCode == null) {
			if (other.isinCode != null) {
				return false;
			}
		}
		else if (!isinCode.equals(other.isinCode)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		if (Double.doubleToLongBits(unitPrice) != Double.doubleToLongBits(other.unitPrice)) {
			return false;
		}
		return true;
	}

}