package com.integrosys.cms.app.poi.report;
import java.util.Date;

public class OBFilter {
	
private String reportId;
private String fromDate;
private String toDate;

private String party;
private String segment;
private String region;

private String industry;
private String status;

private String branchId;
private String documentType;

private String relatoionship;
private String guarantor;

private String userId;
private String departmentId;

//for UI
private String filterPartyMode;
private String filterUserMode;
private String filterDocument;
private String relationManager;

private String rbiAsset;
private String rmRegion;
private String relationshipMgr;
private String moduleEvent;
private String tatCriteria;
private String category;
private String caseId;
private String profile;

private String uploadSystem;


private String quarter;

//Added by Uma Khot: Start:For Monthly Basel Report 08/09/2015
private String securityType;
//Added by Uma Khot: End:For Monthly Basel Report 08/09/2015

//added by santosh Start: ubs limit CR
private String recordType;
private String facility;

private String scodFromDate;
private String scodToDate;
private String escodFromDate;
private String escodToDate;

//Added by Prachit for cersai Report
private String typeOfSecurity;
private String bankingMethod;
private String reportFormat;
private String securityId="";
private String searchCustomerName = "";
private String partyId;

private String securitySubType;
private String securityType1;

//Added by Prachit for mortgage Report
private String securityStatus;

private String eventOrCriteria;

private String monthsOfAuditTrail;


public String getMonthsOfAuditTrail() {
	return monthsOfAuditTrail;
}

public void setMonthsOfAuditTrail(String monthsOfAuditTrail) {
	this.monthsOfAuditTrail = monthsOfAuditTrail;
}

private String isExceptionalUser;
	
public String getEventOrCriteria() {
	return eventOrCriteria;
}

public void setEventOrCriteria(String eventOrCriteria) {
	this.eventOrCriteria = eventOrCriteria;
}

private String eodSyncUpDate;

public String getSecurityStatus() {
	return securityStatus;
}

public String getSecurityType1() {
	return securityType1;
}

public void setSecurityType1(String securityType1) {
	this.securityType1 = securityType1;
}

public String getSecuritySubType() {
	return securitySubType;
}

public void setSecuritySubType(String securitySubType) {
	this.securitySubType = securitySubType;
}
public void setSecurityStatus(String securityStatus) {
	this.securityStatus = securityStatus;
}

public String getPartyId() {
	return partyId;
}

public void setPartyId(String partyId) {
	this.partyId = partyId;
}

public String getSecurityId() {
	return securityId;
}

public void setSecurityId(String securityId) {
	this.securityId = securityId;
}

public String getSearchCustomerName() {
	return searchCustomerName;
}

public void setSearchCustomerName(String searchCustomerName) {
	this.searchCustomerName = searchCustomerName;
}

public String getReportFormat() {
	return reportFormat;
}

public void setReportFormat(String reportFormat) {
	this.reportFormat = reportFormat;
}

public String getBankingMethod() {
	return bankingMethod;
}

public void setBankingMethod(String bankingMethod) {
	this.bankingMethod = bankingMethod;
}

public String getTypeOfSecurity() {
	return typeOfSecurity;
}

public void setTypeOfSecurity(String typeOfSecurity) {
	this.typeOfSecurity = typeOfSecurity;
}

public String getRecordType() {
	return recordType;
}

public void setRecordType(String recordType) {
	this.recordType = recordType;
}

//santosh End: ubs limit CR

public String getFacility() {
	return facility;
}

public void setFacility(String facility) {
	this.facility = facility;
}

public String getFilterDocument() {
	return filterDocument;
}
public void setFilterDocument(String filterDocument) {
	this.filterDocument = filterDocument;
}
public String getRbiAsset() {
	return rbiAsset;
}
public void setRbiAsset(String rbiAsset) {
	this.rbiAsset = rbiAsset;
}
public String getRelationManager() {
	return relationManager;
}
public void setRelationManager(String relationManager) {
	this.relationManager = relationManager;
}
public String getReportId() {
	return reportId;
}
public void setReportId(String reportId) {
	this.reportId = reportId;
}

public String getFromDate() {
	return fromDate;
}
public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}
public String getToDate() {
	return toDate;
}
public void setToDate(String toDate) {
	this.toDate = toDate;
}
public String getParty() {
	return party;
}
public void setParty(String party) {
	this.party = party;
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
public String getIndustry() {
	return industry;
}
public void setIndustry(String industry) {
	this.industry = industry;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getBranchId() {
	return branchId;
}
public void setBranchId(String branchId) {
	this.branchId = branchId;
}
public String getDocumentType() {
	return documentType;
}
public void setDocumentType(String documentType) {
	this.documentType = documentType;
}
public String getRelatoionship() {
	return relatoionship;
}
public void setRelatoionship(String relatoionship) {
	this.relatoionship = relatoionship;
}
public String getGuarantor() {
	return guarantor;
}
public void setGuarantor(String guarantor) {
	this.guarantor = guarantor;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getDepartmentId() {
	return departmentId;
}
public void setDepartmentId(String departmentId) {
	this.departmentId = departmentId;
}
public String getFilterPartyMode() {
	return filterPartyMode;
}
public void setFilterPartyMode(String filterPartyMode) {
	this.filterPartyMode = filterPartyMode;
}
public String getFilterUserMode() {
	return filterUserMode;
}
public void setFilterUserMode(String filterUserMode) {
	this.filterUserMode = filterUserMode;
}
public String getRmRegion() {
	return rmRegion;
}
public void setRmRegion(String rmRegion) {
	this.rmRegion = rmRegion;
}
public String getRelationshipMgr() {
	return relationshipMgr;
}
public void setRelationshipMgr(String relationshipMgr) {
	this.relationshipMgr = relationshipMgr;
}
public String getModuleEvent() {
	return moduleEvent;
}
public void setModuleEvent(String moduleEvent) {
	this.moduleEvent = moduleEvent;
}
public String getTatCriteria() {
	return tatCriteria;
}
public void setTatCriteria(String tatCriteria) {
	this.tatCriteria = tatCriteria;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getCaseId() {
	return caseId;
}
public void setCaseId(String caseId) {
	this.caseId = caseId;
}
public String getProfile() {
	return profile;
}
public void setProfile(String profile) {
	this.profile = profile;
}
public String getUploadSystem() {
	return uploadSystem;
}
public void setUploadSystem(String uploadSystem) {
	this.uploadSystem = uploadSystem;
}


//Uma:For Cam Quarter Activity CR
public void setQuarter(String quarter) {
	this.quarter = quarter;
}
public String getQuarter() {
	return quarter;
}

//Added by Uma Khot: Start:For Monthly Basel Report 08/09/2015
public String getSecurityType() {
	return securityType;
}
public void setSecurityType(String securityType) {
	this.securityType = securityType;
}
//Added by Uma Khot: End:For Monthly Basel Report 08/09/2015

public String getScodFromDate() {
	return scodFromDate;
}

public void setScodFromDate(String scodFromDate) {
	this.scodFromDate = scodFromDate;
}

public String getScodToDate() {
	return scodToDate;
}

public void setScodToDate(String scodToDate) {
	this.scodToDate = scodToDate;
}

public String getEscodFromDate() {
	return escodFromDate;
}

public void setEscodFromDate(String escodFromDate) {
	this.escodFromDate = escodFromDate;
}

public String getEscodToDate() {
	return escodToDate;
}

public void setEscodToDate(String escodToDate) {
	this.escodToDate = escodToDate;
}

public String getIsExceptionalUser() {
	return isExceptionalUser;
}

public void setIsExceptionalUser(String isExceptionalUser) {
	this.isExceptionalUser = isExceptionalUser;
}
public String getEodSyncUpDate() {
	return eodSyncUpDate;
}

private String selectYearDropdown;

public void setEodSyncUpDate(String eodSyncUpDate) {
	this.eodSyncUpDate = eodSyncUpDate;
}

public String getSelectYearDropdown() {
	return selectYearDropdown;
}

public void setSelectYearDropdown(String selectYearDropdown) {
	this.selectYearDropdown = selectYearDropdown;
}



}
