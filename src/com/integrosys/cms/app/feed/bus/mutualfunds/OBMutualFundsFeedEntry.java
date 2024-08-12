/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/OBBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class OBMutualFundsFeedEntry implements IMutualFundsFeedEntry {

	private String schemeCode;
	
	private String schemeName;
	
	private String schemeType;
	
	private double currentNAV;
	
	private Date startDate;
	
	private Date expiryDate;

	private Date lastUpdatedDate;

	private long mutualFundsFeedEntryID;

	private long mutualFundsFeedEntryRef;

	private long versionTime = 0;
	
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

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	/**
	 * @return the currentNAV
	 */
	public double getCurrentNAV() {
		return currentNAV;
	}

	/**
	 * @param currentNAV the currentNAV to set
	 */
	public void setCurrentNAV(double currentNAV) {
		this.currentNAV = currentNAV;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setLastUpdatedDate(Date lastUpdateDate) {
		this.lastUpdatedDate = lastUpdateDate;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	public long getMutualFundsFeedEntryID() {
		return mutualFundsFeedEntryID;
	}

	public void setMutualFundsFeedEntryID(long mutualFundsFeedEntryID) {
		this.mutualFundsFeedEntryID = mutualFundsFeedEntryID;
	}

	public long getMutualFundsFeedEntryRef() {
		return mutualFundsFeedEntryRef;
	}

	public void setMutualFundsFeedEntryRef(long mutualFundsFeedEntryRef) {
		this.mutualFundsFeedEntryRef = mutualFundsFeedEntryRef;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((schemeCode == null) ? 0 : schemeCode.hashCode());
		result = prime * result + ((schemeName == null) ? 0 : schemeName.hashCode());

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

		final OBMutualFundsFeedEntry other = (OBMutualFundsFeedEntry) obj;
		if (schemeCode == null) {
			if (other.schemeCode != null) {
				return false;
			}
		}
		else if (!schemeCode.equals(other.schemeCode)) {
			return false;
		}
		
		if (schemeName == null) {
			if (other.schemeName != null) {
				return false;
			}
		}
		else if (!schemeName.equals(other.schemeName)) {
			return false;
		}

		return true;
	}
}