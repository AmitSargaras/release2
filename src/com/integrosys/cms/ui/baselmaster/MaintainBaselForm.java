package com.integrosys.cms.ui.baselmaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class MaintainBaselForm extends TrxContextForm implements Serializable{
	
public static final String BASEL_MAPPER = "com.integrosys.cms.ui.baselmaster.BaselMapper";
	
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	private String id;		
	private String versionTime;
	private String status;
	private String deprecated;
	private String system;
	private String systemValue;
	private String exposureSource;
	private String baselValidation;
	private String reportHandOff;
	private String lastUpdatedBy;
	private String createdBy;
	private String lastUpdatedOn;
	private String createdOn;
	
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
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	
	public String[][] getMapper() {
		String[][] input = {  { "baselObj", BASEL_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}

}
