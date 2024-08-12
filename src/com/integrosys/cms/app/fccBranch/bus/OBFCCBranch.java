
package com.integrosys.cms.app.fccBranch.bus;

import java.util.Date;



/****
 * 
 * @author komal.agicha
 *
 */
public class OBFCCBranch implements IFCCBranch {
	
	/**
	 * constructor
	 */
	public OBFCCBranch() {
		
	}
    private long id;
	private long versionTime;
	private String status;
	private String deprecated;
	private String branchCode;       
	private String branchName;
	private String lastUpdatedBy;
	private String createdBy;
	private Date lastUpdatedOn;
	private Date createdOn;
	private String aliasBranchCode; 
	

	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	/**
	 * @param lastUpdatedOn the lastUpdatedOn to set
	 */
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
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