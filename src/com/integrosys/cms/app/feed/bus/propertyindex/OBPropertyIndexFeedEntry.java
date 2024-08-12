/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/OBPropertyIndexFeedEntry.java,v 1.3 2003/08/20 12:38:21 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.util.Date;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/20 12:38:21 $ Tag: $Name: $
 * 
 */
public class OBPropertyIndexFeedEntry implements IPropertyIndexFeedEntry {

	private double unitPrice;

	private String type;

	private String region;

	private String countryCode;

	private Date lastUpdatedDate;

	private long propertyIndexFeedEntryID;

	private long versionTime = 0;

	private long propertyIndexFeedEntryRef;

	private boolean deletedInd;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdateDate) {
		this.lastUpdatedDate = lastUpdateDate;
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

	public long getPropertyIndexFeedEntryID() {
		return this.propertyIndexFeedEntryID;
	}

	public void setPropertyIndexFeedEntryID(long param) {
		this.propertyIndexFeedEntryID = param;
	}

	public long getPropertyIndexFeedEntryRef() {
		return propertyIndexFeedEntryRef;
	}

	public void setPropertyIndexFeedEntryRef(long propertyIndexFeedEntryRef) {
		this.propertyIndexFeedEntryRef = propertyIndexFeedEntryRef;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public boolean isDeletedInd() {
		return deletedInd;
	}

	public void setDeletedInd(boolean deletedInd) {
		this.deletedInd = deletedInd;
	}

}