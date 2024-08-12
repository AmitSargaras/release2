/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/OBBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.digitalLibrary.bus;

import java.util.Date;


/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public class OBDigitalLibraryEntry implements IDigitalLibraryEntry {
	
	private long digitalLibraryEntryID;

	private long digitalLibraryEntryRef;
	
	private String climsDocCategory;
	
	private String climsDocCategoryCode;

	private String climsDocCategorySeq;
	
	private String digiLibDocCategory;
	
	/*private String level2;
	
	private String level3;
	
	private String level4;*/

	private long versionTime;
	
	private Date lastUpdatedDate;
	
	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * @return the digitalLibraryEntryID
	 */
	public long getDigitalLibraryEntryID() {
		return digitalLibraryEntryID;
	}

	/**
	 * @param digitalLibraryEntryID the digitalLibraryEntryID to set
	 */
	public void setDigitalLibraryEntryID(long digitalLibraryEntryID) {
		this.digitalLibraryEntryID = digitalLibraryEntryID;
	}

	/**
	 * @return the digitalLibraryEntryRef
	 */
	public long getDigitalLibraryEntryRef() {
		return digitalLibraryEntryRef;
	}

	/**
	 * @param digitalLibraryEntryRef the digitalLibraryEntryRef to set
	 */
	public void setDigitalLibraryEntryRef(long digitalLibraryEntryRef) {
		this.digitalLibraryEntryRef = digitalLibraryEntryRef;
	}

	public String getClimsDocCategory() {
		return climsDocCategory;
	}

	public void setClimsDocCategory(String climsDocCategory) {
		this.climsDocCategory = climsDocCategory;
	}

	public String getDigiLibDocCategory() {
		return digiLibDocCategory;
	}

	public void setDigiLibDocCategory(String digiLibDocCategory) {
		this.digiLibDocCategory = digiLibDocCategory;
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

	public String getClimsDocCategoryCode() {
		return climsDocCategoryCode;
	}

	public void setClimsDocCategoryCode(String climsDocCategoryCode) {
		this.climsDocCategoryCode = climsDocCategoryCode;
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

		return true;
	}
	
	
	public String getClimsDocCategorySeq() {
		return climsDocCategorySeq;
	}

	public void setClimsDocCategorySeq(String climsDocCategorySeq) {
		this.climsDocCategorySeq = climsDocCategorySeq;
	}

}