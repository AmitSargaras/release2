
package com.integrosys.cms.app.newTat.bus;


import java.util.Date;

import com.integrosys.cms.app.component.bus.OBComponent;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBNewTat implements INewTat,Cloneable {
	
	/**
	 * constructor
	 */
	public OBNewTat() {
		
	}
	private long id;
	private long versionTime;
	private String  createBy             ;
	private Date  creationDate         ;
	private String  lastUpdateBy         ;
	private Date  lastUpdateDate       ;
	private String  deprecated           ;
	private String  status               ;
	private long  cmsLeMainProfileId   ;
	private String  lspLeId              ;
	private String  lspShortName         ;
	private String  lspLeIdListSearch              ;
	private String  lspShortNameListSearch         ;
	private long  caseId               ;
	private String  module               ;
	private String  caseInitiator        ;
	private String  relationshipManager  ;
	private String  docStatus            ;
	private String  remarks              ;
	private String  facilityCategory     ;
	private String  facilityName         ;
	private String  camType              ;
	private String  deferralType         ;
	private String  lssCoordinatorBranch ;
	private String  type                 ;
	private Date  activityTime         ;
	private Date  actualActivityTime   ;
	private String  facilitySystem             ;
	private String  facilityManual             ;
	private String  amount               ;
	private String  currency             ;
	private String  lineNumber           ;
	private String  serialNumber         ;
	private String  region         ;
	private String  segment         ;
	private String  isTatBurst         ;
	private String  delayReason         ;
	private String  facilitySection;
	private String  rmRegion;
	private String  delayReasonText         ;
	
	public String getDelayReasonText() {
		return delayReasonText;
	}
	public void setDelayReasonText(String delayReasonText) {
		this.delayReasonText = delayReasonText;
	}
	public String getIsTatBurst() {
		return isTatBurst;
	}
	public void setIsTatBurst(String isTatBurst) {
		this.isTatBurst = isTatBurst;
	}
	public String getDelayReason() {
		return delayReason;
	}
	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
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
	
	public String getLspLeId() {
		return lspLeId;
	}
	public void setLspLeId(String lspLeId) {
		this.lspLeId = lspLeId;
	}
	public String getLspShortName() {
		return lspShortName;
	}
	public void setLspShortName(String lspShortName) {
		this.lspShortName = lspShortName;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getCaseInitiator() {
		return caseInitiator;
	}
	public void setCaseInitiator(String caseInitiator) {
		this.caseInitiator = caseInitiator;
	}
	public String getRelationshipManager() {
		return relationshipManager;
	}
	public void setRelationshipManager(String relationshipManager) {
		this.relationshipManager = relationshipManager;
	}
	public String getDocStatus() {
		return docStatus;
	}
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFacilityCategory() {
		return facilityCategory;
	}
	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getCamType() {
		return camType;
	}
	public void setCamType(String camType) {
		this.camType = camType;
	}
	public String getDeferralType() {
		return deferralType;
	}
	public void setDeferralType(String deferralType) {
		this.deferralType = deferralType;
	}
	public String getLssCoordinatorBranch() {
		return lssCoordinatorBranch;
	}
	public void setLssCoordinatorBranch(String lssCoordinatorBranch) {
		this.lssCoordinatorBranch = lssCoordinatorBranch;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}
	public Date getActualActivityTime() {
		return actualActivityTime;
	}
	public void setActualActivityTime(Date actualActivityTime) {
		this.actualActivityTime = actualActivityTime;
	}
	public String getFacilitySystem() {
		return facilitySystem;
	}
	public void setFacilitySystem(String facilitySystem) {
		this.facilitySystem = facilitySystem;
	}
	
	public String getFacilityManual() {
		return facilityManual;
	}
	public void setFacilityManual(String facilityManual) {
		this.facilityManual = facilityManual;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public long getCmsLeMainProfileId() {
		return cmsLeMainProfileId;
	}
	public void setCmsLeMainProfileId(long cmsLeMainProfileId) {
		this.cmsLeMainProfileId = cmsLeMainProfileId;
	}
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	
	public String getLspLeIdListSearch() {
		return lspLeIdListSearch;
	}
	public void setLspLeIdListSearch(String lspLeIdListSearch) {
		this.lspLeIdListSearch = lspLeIdListSearch;
	}
	public String getLspShortNameListSearch() {
		return lspShortNameListSearch;
	}
	public void setLspShortNameListSearch(String lspShortNameListSearch) {
		this.lspShortNameListSearch = lspShortNameListSearch;
	}
	public String getRmRegion() {
		return rmRegion;
	}
	public void setRmRegion(String rmRegion) {
		this.rmRegion = rmRegion;
	}
	public String getFacilitySection() {
		return facilitySection;
	}
	public void setFacilitySection(String facilitySection) {
		this.facilitySection = facilitySection;
	}
	public Object clone() throws CloneNotSupportedException {
		 
		 OBNewTat copyObj = new OBNewTat();
		// private long id;
		 copyObj.setVersionTime(this.versionTime);
		 copyObj.setCreateBy (this.createBy);
		 copyObj.setCreationDate (this.creationDate);
		 copyObj.setLastUpdateBy        (this.lastUpdateBy);
		 copyObj.setLastUpdateDate      (this.lastUpdateDate);
		 copyObj.setDeprecated          (this.deprecated); ;
		 copyObj.setStatus            (this.status);
		 copyObj.setCmsLeMainProfileId (this.cmsLeMainProfileId);
		 copyObj.setLspLeId             (this.lspLeId);
		 copyObj.setLspShortName        (this.lspShortName);
		 copyObj.setCaseId               (this.caseId);
		 copyObj.setModule               (this.module);
		 copyObj.setCaseInitiator     (this.caseInitiator);
		 copyObj.setRelationshipManager  (this.relationshipManager);
		 copyObj.setDocStatus          (this.docStatus);
		 copyObj.setRemarks             (this.remarks);
		 copyObj.setFacilityCategory    (this.facilityCategory);
		 copyObj.setFacilityName        (this.facilityName);
		 copyObj.setCamType              (this.camType);
		 copyObj.setDeferralType        (this.deferralType);
		 copyObj.setLssCoordinatorBranch(this.lssCoordinatorBranch);
		 copyObj.setType                (this.type);
		 copyObj.setActivityTime         (this.activityTime);
		 copyObj.setActualActivityTime   (this.actualActivityTime);
			copyObj.setFacilitySystem            (this.facilitySystem);
			copyObj.setFacilityManual            (this.facilityManual);
			copyObj.setAmount               (this.amount);
			copyObj.setCurrency             (this.currency);
			copyObj.setLineNumber           (this.lineNumber);
			copyObj.setSerialNumber        (this.serialNumber);
			copyObj.setRegion         (this.region);
			copyObj.setSegment         (this.segment);
			copyObj.setFacilitySection(this.facilitySection);
			copyObj.setRmRegion(this.rmRegion);
			copyObj.setDelayReason(this.delayReason);
			copyObj.setDelayReasonText(this.delayReasonText);
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 return copyObj;
		 }
	
	


	
	

}