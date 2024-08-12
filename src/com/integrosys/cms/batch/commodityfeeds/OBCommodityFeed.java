/*
 * Created on Aug 11, 2004
 *
 */
package com.integrosys.cms.batch.commodityfeeds;

import java.util.Date;

/**
 * Describes this class Purpose: A value object that represents a commodity feed
 * rate Description:
 * 
 * @author $Chen Cheng$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class OBCommodityFeed {
	private long profileID;

	private String ric;

	private double lastPrice;

	private Date lastDate;

	private double currentPrice;

	private Date currentDate;

	private String currency;

	private String unitSize;

	private boolean isError;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public long getProfileID() {
		return profileID;
	}

	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public String getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(String unitSize) {
		this.unitSize = unitSize;
	}
}
