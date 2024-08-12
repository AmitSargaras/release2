/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.fccBranch;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/****
 * 
 * @author komal.agicha
 *
 */

public class FCCBranchForm extends TrxContextForm implements Serializable {

	
 
		private String versionTime;
		private String id;
		private String status;
		private String deprecated;
		private String branchCode;
		private String branchName;
		private String lastUpdatedBy;
		private String createdBy;
		private String lastUpdatedOn;
		private String createdOn;
		private String aliasBranchCode;
		
	
	public String[][] getMapper() {
		String[][] input = {  { "fccBranchObj", FCCBRANCH_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	public static final String FCCBRANCH_MAPPER = "com.integrosys.cms.ui.fccBranch.FCCBranchMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdatedOn
	 */
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	/**
	 * @param lastUpdatedOn the lastUpdatedOn to set
	 */
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the aliasBranchCode
	 */
	public String getAliasBranchCode() {
		return aliasBranchCode;
	}
	/**
	 * @param aliasBranchCode the aliasBranchCode to set
	 */
	public void setAliasBranchCode(String aliasBranchCode) {
		this.aliasBranchCode = aliasBranchCode;
	}
	
	
	
	
	

}
