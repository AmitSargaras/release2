package com.integrosys.cms.app.valuationAmountAndRating.bus;

import java.util.Date;

public class OBValuationAmountAndRating implements IValuationAmountAndRating,Cloneable{


	
	private long id;
	private long versionTime;

	
	private String criteria;
	private String valuationAmount;
	private String ramRating;
	private String excludePartyId;
	
	private String operationName;
	private String status;
	private String partyId;
	private String partyName;
	
	private long masterId = 0l;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	
	
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getValuationAmount() {
		return valuationAmount;
	}
	public void setValuationAmount(String valuationAmount) {
		this.valuationAmount = valuationAmount;
	}
	public String getRamRating() {
		return ramRating;
	}
	public void setRamRating(String ramRating) {
		this.ramRating = ramRating;
	}
	public String getExcludePartyId() {
		return excludePartyId;
	}
	public void setExcludePartyId(String excludePartyId) {
		this.excludePartyId = excludePartyId;
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
