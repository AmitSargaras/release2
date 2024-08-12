package com.integrosys.cms.ui.newTat;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for New Tat
 */

public class NewTatForm extends TrxContextForm implements Serializable {

	
	private String versionTime;
	private String id;
	private String  createBy             ;
	private String  creationDate         ;
	private String  lastUpdateBy         ;
	private String  lastUpdateDate       ;
	private String  deprecated           ;
	private String  status               ;
	private String  cmsLeMainProfileId   ;
	private String  lspLeId              ;
	private String  lspShortName         ;
	private String  caseId               ;
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
	private String  activityTime         ;
	private String  actualActivityTime   ;
	private String  facilitySystem             ;
	private String  facilityManual             ;
	private String  amount               ;
	private String  currency             ;
	private String  lineNumber           ;
	private String  serialNumber         ;
	
	private String  serialNumberManual         ;
	private String  serialNumberSystem         ;
	private String  segment         	 ;
	private String  region           	 ;
	private String  lspShortNameSearch;
	private String  lspLeIdSearch;
	private String  lspShortNameListSearch;
	private String  lspLeIdListSearch;
	private String  caseInitiatorSearch;
	private String  lastUpdateBySearch;
	private String  regionSearch;
	private String  statusSearch;
	private String  segmentSearch;
	private String  moduleSearch;
	private String  isTatBurst         ;
	private String  delayReason         ;
	private String  facilitySection ;
	private String  rmRegion ;
	
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
   
	
	
	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
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

	public String getCmsLeMainProfileId() {
		return cmsLeMainProfileId;
	}

	public void setCmsLeMainProfileId(String cmsLeMainProfileId) {
		this.cmsLeMainProfileId = cmsLeMainProfileId;
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
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

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public String getActualActivityTime() {
		return actualActivityTime;
	}

	public void setActualActivityTime(String actualActivityTime) {
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

	
	
	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getLspShortNameSearch() {
		return lspShortNameSearch;
	}

	public void setLspShortNameSearch(String lspShortNameSearch) {
		this.lspShortNameSearch = lspShortNameSearch;
	}

	public String getLspLeIdSearch() {
		return lspLeIdSearch;
	}

	public void setLspLeIdSearch(String lspLeIdSearch) {
		this.lspLeIdSearch = lspLeIdSearch;
	}

	public String getCaseInitiatorSearch() {
		return caseInitiatorSearch;
	}

	public void setCaseInitiatorSearch(String caseInitiatorSearch) {
		this.caseInitiatorSearch = caseInitiatorSearch;
	}

	public String getLastUpdateBySearch() {
		return lastUpdateBySearch;
	}

	public void setLastUpdateBySearch(String lastUpdateBySearch) {
		this.lastUpdateBySearch = lastUpdateBySearch;
	}

	public String getRegionSearch() {
		return regionSearch;
	}

	public void setRegionSearch(String regionSearch) {
		this.regionSearch = regionSearch;
	}

	public String getStatusSearch() {
		return statusSearch;
	}

	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
	}

	public String getSegmentSearch() {
		return segmentSearch;
	}

	public void setSegmentSearch(String segmentSearch) {
		this.segmentSearch = segmentSearch;
	}

	public String getModuleSearch() {
		return moduleSearch;
	}

	public void setModuleSearch(String moduleSearch) {
		this.moduleSearch = moduleSearch;
	}

	public String getFacilitySection() {
		return facilitySection;
	}
	public void setFacilitySection(String facilitySection) {
		this.facilitySection = facilitySection;
	}
	
	public String getLspShortNameListSearch() {
		return lspShortNameListSearch;
	}
	public void setLspShortNameListSearch(String lspShortNameListSearch) {
		this.lspShortNameListSearch = lspShortNameListSearch;
	}
	public String getLspLeIdListSearch() {
		return lspLeIdListSearch;
	}
	public void setLspLeIdListSearch(String lspLeIdListSearch) {
		this.lspLeIdListSearch = lspLeIdListSearch;
	}
	public String getRmRegion() {
		return rmRegion;
	}
	public void setRmRegion(String rmRegion) {
		this.rmRegion = rmRegion;
	}
	
	
	public String getSerialNumberManual() {
		return serialNumberManual;
	}
	public void setSerialNumberManual(String serialNumberManual) {
		this.serialNumberManual = serialNumberManual;
	}
	public String getSerialNumberSystem() {
		return serialNumberSystem;
	}
	public void setSerialNumberSystem(String serialNumberSystem) {
		this.serialNumberSystem = serialNumberSystem;
	}
	public String[][] getMapper() {
		String[][] input = {  { "newTatObj", NEW_TAT_MAPPER },
				{ "customerSearchCriteria",NEW_TAT_MAPPER },
				{ "customerList",NEW_TAT_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String NEW_TAT_MAPPER = "com.integrosys.cms.ui.newTat.NewTatMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
	
	

}
