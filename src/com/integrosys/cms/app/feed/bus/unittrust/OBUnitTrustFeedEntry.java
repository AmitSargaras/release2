/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/OBUnitTrustFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Date;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public class OBUnitTrustFeedEntry implements IUnitTrustFeedEntry {

	private String fundManagerCode;

	private String productCode;

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

	private long unitTrustFeedEntryID;

	private long versionTime = 0;

	private long unitTrustFeedEntryRef;

	private String rating;

	private String fundCode;
	
	private Long fundSize;
	
	private Date fundSizeUpdateDate;
	
	private Date dateLaunched;

	public Long getFundSize() {
		return fundSize;
	}

	public void setFundSize(Long fundSize) {
		this.fundSize = fundSize;
	}

	public Date getFundSizeUpdateDate() {
		return fundSizeUpdateDate;
	}

	public void setFundSizeUpdateDate(Date fundSizeUpdateDate) {
		this.fundSizeUpdateDate = fundSizeUpdateDate;
	}

	public Date getDateLaunched() {
		return dateLaunched;
	}

	public void setDateLaunched(Date dateLaunched) {
		this.dateLaunched = dateLaunched;
	}

	public String getFundManagerCode() {
		return fundManagerCode;
	}

	public void setFundManagerCode(String fundManagerCode) {
		this.fundManagerCode = fundManagerCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public long getUnitTrustFeedEntryID() {
		return this.unitTrustFeedEntryID;
	}

	public void setUnitTrustFeedEntryID(long param) {
		this.unitTrustFeedEntryID = param;
	}

	public long getUnitTrustFeedEntryRef() {
		return unitTrustFeedEntryRef;
	}

	public void setUnitTrustFeedEntryRef(long unitTrustFeedEntryRef) {
		this.unitTrustFeedEntryRef = unitTrustFeedEntryRef;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

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

		final OBUnitTrustFeedEntry other = (OBUnitTrustFeedEntry) obj;
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