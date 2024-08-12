package com.integrosys.cms.app.relationshipmgr.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.fileUpload.bus.OBCommonFile;

public class OBHRMSData extends OBCommonFile implements IHRMSData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String employeeCode;
	private String name;
	private String emailId;
	private String mobileNo;
	private String region;
	private String city;
	private String state;
	private String wboRegion;
	private String supervisorEmployeeCode;
	private String branchCode;
	private String band;
	
	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private String deprecated;
	private long versionTime;
	private String status;
	@Override
	public long getVersionTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setVersionTime(long arg0) {
		// TODO Auto-generated method stub
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWboRegion() {
		return wboRegion;
	}
	public void setWboRegion(String wboRegion) {
		this.wboRegion = wboRegion;
	}
	public String getSupervisorEmployeeCode() {
		return supervisorEmployeeCode;
	}
	public void setSupervisorEmployeeCode(String supervisorEmployeeCode) {
		this.supervisorEmployeeCode = supervisorEmployeeCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBand() {
		return band;
	}
	public void setBand(String band) {
		this.band = band;
	}

}
