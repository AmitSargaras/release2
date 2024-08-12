package com.integrosys.cms.app.bankingArrangementFacExclusion.bus;

import java.util.Date;

public class OBBankingArrangementFacExclusion implements IBankingArrangementFacExclusion, Cloneable {

	private long id;
	private long versionTime;
	
	private String system;
	private String facCategory;
	private String facName;
	private String excluded;

	private String operationName;
	private String status;

	private long masterId = 0l;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;

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
	
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getFacCategory() {
		return facCategory;
	}

	public void setFacCategory(String facCategory) {
		this.facCategory = facCategory;
	}

	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	public String getExcluded() {
		return excluded;
	}

	public void setExcluded(String excluded) {
		this.excluded = excluded;
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

}