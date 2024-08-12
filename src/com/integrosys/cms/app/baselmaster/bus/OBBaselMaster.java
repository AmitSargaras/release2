package com.integrosys.cms.app.baselmaster.bus;

import java.util.Date;



public class OBBaselMaster implements IBaselMaster,Cloneable{
	
	public OBBaselMaster(){
		
	}

	private long id;		
	private long versionTime;
	private String status;
	private String deprecated;
	private String system;
	private String systemValue;
	private String exposureSource;
	private String baselValidation;
	private String reportHandOff;
	private String lastUpdatedBy;
	private String createdBy;
	private Date lastUpdatedOn;
	private Date createdOn;
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
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
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSystemValue() {
		return systemValue;
	}
	public void setSystemValue(String systemValue) {
		this.systemValue = systemValue;
	}
	public String getExposureSource() {
		return exposureSource;
	}
	public void setExposureSource(String exposureSource) {
		this.exposureSource = exposureSource;
	}
	public String getBaselValidation() {
		return baselValidation;
	}
	public void setBaselValidation(String baselValidation) {
		this.baselValidation = baselValidation;
	}
	public String getReportHandOff() {
		return reportHandOff;
	}
	public void setReportHandOff(String reportHandOff) {
		this.reportHandOff = reportHandOff;
	}
	public Object clone() throws CloneNotSupportedException {
		 
		OBBaselMaster copyObj = new OBBaselMaster();
		
		 copyObj.setDeprecated(this.deprecated);
		 copyObj.setId(this.id);
		 copyObj.setStatus(this.status);
		 copyObj.setVersionTime(this.versionTime);
		 copyObj.setBaselValidation(this.baselValidation);
		 copyObj.setExposureSource(this.exposureSource);
		 copyObj.setReportHandOff(this.reportHandOff);
		 copyObj.setSystem(this.system);
		 copyObj.setSystemValue(this.systemValue);
		 return copyObj;
		 }
	
}
