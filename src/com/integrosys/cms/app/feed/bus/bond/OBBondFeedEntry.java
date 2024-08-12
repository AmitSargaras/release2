/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/OBBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.feed.bus.bond;

import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public class OBBondFeedEntry implements IBondFeedEntry {
	
	private String bondCode;
	
	private Date issueDate;
	
	private Date maturityDate;
	
	private Date lastUpdateDate;

	private String name;
	
	private String isinCode;
	
	private String rating;
	
	private long bondFeedEntryID;

	private long bondFeedEntryRef;
	
	private double couponRate;

	private double unitPrice;
	
	private FormFile fileUpload;


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

	/**
	 * @return the bondCode
	 */
	public String getBondCode() {
		return bondCode;
	}

	/**
	 * @param bondCode the bondCode to set
	 */
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the isinCode
	 */
	public String getIsinCode() {
		return isinCode;
	}

	/**
	 * @param isinCode the isinCode to set
	 */
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the couponRate
	 */
	public double getCouponRate() {
		return couponRate;
	}

	/**
	 * @param couponRate the couponRate to set
	 */
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}

	/**
	 * @return the unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the versionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * @param bondFeedEntryID the bondFeedEntryID to set
	 */
	public void setBondFeedEntryID(long bondFeedEntryID) {
		this.bondFeedEntryID = bondFeedEntryID;
	}

	/**
	 * @param bondFeedEntryRef the bondFeedEntryRef to set
	 */
	public void setBondFeedEntryRef(long bondFeedEntryRef) {
		this.bondFeedEntryRef = bondFeedEntryRef;
	}

	private long versionTime = 0;

	public long getBondFeedEntryID() {
		return this.bondFeedEntryID;
	}

	public long getBondFeedEntryRef() {
		return bondFeedEntryRef;
	}
	
	

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((name == null) ? 0 : name.hashCode());

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

		final OBBondFeedEntry other = (OBBondFeedEntry) obj;

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}

		return true;
	}
}