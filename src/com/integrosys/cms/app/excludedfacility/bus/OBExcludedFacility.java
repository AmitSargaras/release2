package com.integrosys.cms.app.excludedfacility.bus;

import java.util.Date;

public class OBExcludedFacility implements IExcludedFacility,Cloneable{

	
	private long id;
	private long versionTime;

	/*private String excludedFacilityCategory;
	private String actualExcludedfacilityCategoryCode;*/
	
	private String excludedFacilityDescription;
	
	private String operationName;
	private String status;
	private long masterId = 0l;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String cpsId;
	
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
	/*public String getExcludedFacilityCategory() {
		return excludedFacilityCategory;
	}
	public void setExcludedFacilityCategory(String excludedFacilityCategory) {
		this.excludedFacilityCategory = excludedFacilityCategory;
	}*/
	public String getExcludedFacilityDescription() {
		return excludedFacilityDescription;
	}
	public void setExcludedFacilityDescription(String excludedFacilityDescription) {
		this.excludedFacilityDescription = excludedFacilityDescription;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	/*public String getActualExcludedfacilityCategoryCode() {
		return actualExcludedfacilityCategoryCode;
	}
	public void setActualExcludedfacilityCategoryCode(String actualExcludedfacilityCategoryCode) {
		this.actualExcludedfacilityCategoryCode = actualExcludedfacilityCategoryCode;
	}*/
	


}
