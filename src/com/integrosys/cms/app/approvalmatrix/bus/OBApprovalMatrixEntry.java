/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/OBBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.approvalmatrix.bus;

import java.util.Date;


/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public class OBApprovalMatrixEntry implements IApprovalMatrixEntry {
	
	private long approvalMatrixEntryID;

	private long approvalMatrixEntryRef;
	
	private int riskGrade;
	
	private String level1;
	
	private String level2;
	
	private String level3;
	
	private String level4;

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
	 * @return the approvalMatrixEntryID
	 */
	public long getApprovalMatrixEntryID() {
		return approvalMatrixEntryID;
	}

	/**
	 * @param approvalMatrixEntryID the approvalMatrixEntryID to set
	 */
	public void setApprovalMatrixEntryID(long approvalMatrixEntryID) {
		this.approvalMatrixEntryID = approvalMatrixEntryID;
	}

	/**
	 * @return the approvalMatrixEntryRef
	 */
	public long getApprovalMatrixEntryRef() {
		return approvalMatrixEntryRef;
	}

	/**
	 * @param approvalMatrixEntryRef the approvalMatrixEntryRef to set
	 */
	public void setApprovalMatrixEntryRef(long approvalMatrixEntryRef) {
		this.approvalMatrixEntryRef = approvalMatrixEntryRef;
	}

	/**
	 * @return the riskGrade
	 */
	public int getRiskGrade() {
		return riskGrade;
	}

	/**
	 * @param riskGrade the riskGrade to set
	 */
	public void setRiskGrade(int riskGrade) {
		this.riskGrade = riskGrade;
	}



	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	
	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getLevel3() {
		return level3;
	}

	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	public String getLevel4() {
		return level4;
	}

	public void setLevel4(String level4) {
		this.level4 = level4;
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

/*	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((Integer.toString(riskGrade) == null) ? 0 : (Integer.toString(riskGrade).hashCode()));

		return result;
		return this.hashCode();
	}*/

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
/*
		final OBApprovalMatrixEntry other = (OBApprovalMatrixEntry) obj;

		if (riskGrade == 0) {
			if (other.riskGrade != 0) {
				return false;
			}
		}
		else if (riskGrade != other.riskGrade) {
			return false;
		}*/

		return true;
	}
}