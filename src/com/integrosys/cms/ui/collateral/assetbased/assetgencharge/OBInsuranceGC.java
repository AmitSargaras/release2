package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Date;

public class OBInsuranceGC implements IInsuranceGC {

	private long id;	
	private long versionTime;
	private long parentId;
	private Date creationDate;
	private String isProcessed;
	private String deprecated;
	private String insuranceCode;
	
	private String insuranceRequired;
	private String insurancePolicyNo;
	private String coverNoteNo;
	private String insuranceCompany;
	private String insuranceCurrency;
	private String insuranceCoverge;
	private String insurancePolicyAmt;
	private String insuredAmount;
	private Date receivedDate;
	private Date effectiveDate;
	private Date expiryDate;
	private String insuranceDefaulted;
	private String insurancePremium;
	private String remark;
	//private String allComponent;
	private String selectComponent;
	private String appendedComponent;
	
	private String lastUpdatedBy;
	private String lastApproveBy;
	private Date lastUpdatedOn;
	private Date lastApproveOn;
	
	
//	private String fundedShare;
	private String  calculatedDP;
	private String dueDate;
	private String stockLocation;
	
	//Uma Khot::Insurance Deferral maintainance
	private String insuranceStatus;
	private String insuredAddress;
	private String remark2;
	private String insuredAgainst;
	private Date originalTargetDate;
	private Date nextDueDate;
	private Date dateDeferred;
	private String creditApprover;
	private Date waivedDate;
	private String oldPolicyNo;
	
	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}
	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}
	public Date getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public Date getDateDeferred() {
		return dateDeferred;
	}
	public void setDateDeferred(Date dateDeferred) {
		this.dateDeferred = dateDeferred;
	}
	public String getCreditApprover() {
		return creditApprover;
	}
	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}
	public Date getWaivedDate() {
		return waivedDate;
	}
	public void setWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getInsuredAgainst() {
		return insuredAgainst;
	}
	public void setInsuredAgainst(String insuredAgainst) {
		this.insuredAgainst = insuredAgainst;
	}
	public String getInsuredAddress() {
		return insuredAddress;
	}
	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	public String getInsuranceStatus() {
		return insuranceStatus;
	}
	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpShare;
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastApproveBy() {
		return lastApproveBy;
	}
	public void setLastApproveBy(String lastApproveBy) {
		this.lastApproveBy = lastApproveBy;
	}
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public Date getLastApproveOn() {
		return lastApproveOn;
	}
	public void setLastApproveOn(Date lastApproveOn) {
		this.lastApproveOn = lastApproveOn;
	}
	
	public String getAppendedComponent() {
		return appendedComponent;
	}
	public void setAppendedComponent(String appendedComponent) {
		this.appendedComponent = appendedComponent;
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public String getInsuranceRequired() {
		return insuranceRequired;
	}
	public void setInsuranceRequired(String insuranceRequired) {
		this.insuranceRequired = insuranceRequired;
	}
	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}
	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
	}
	public String getCoverNoteNo() {
		return coverNoteNo;
	}
	public void setCoverNoteNo(String coverNoteNo) {
		this.coverNoteNo = coverNoteNo;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getInsuranceCurrency() {
		return insuranceCurrency;
	}
	public void setInsuranceCurrency(String insuranceCurrency) {
		this.insuranceCurrency = insuranceCurrency;
	}
	public String getInsuranceCoverge() {
		return insuranceCoverge;
	}
	public void setInsuranceCoverge(String insuranceCoverge) {
		this.insuranceCoverge = insuranceCoverge;
	}
	public String getInsurancePolicyAmt() {
		return insurancePolicyAmt;
	}
	public void setInsurancePolicyAmt(String insurancePolicyAmt) {
		this.insurancePolicyAmt = insurancePolicyAmt;
	}
	public String getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	
	public String getInsuranceDefaulted() {
		return insuranceDefaulted;
	}
	public void setInsuranceDefaulted(String insuranceDefaulted) {
		this.insuranceDefaulted = insuranceDefaulted;
	}
	public String getInsurancePremium() {
		return insurancePremium;
	}
	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
/*	public String getAllComponent() {
		return allComponent;
	}
	public void setAllComponent(String allComponent) {
		this.allComponent = allComponent;
	}*/
	public String getSelectComponent() {
		return selectComponent;
	}
	public void setSelectComponent(String selectComponent) {
		this.selectComponent = selectComponent;
	}
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
//	public String getFundedShare() {
//		return fundedShare;
//	}
//	public void setFundedShare(String fundedShare) {
//		this.fundedShare = fundedShare;
//	}
	public String getCalculatedDP() {
		return calculatedDP;
	}
	public void setCalculatedDP(String calculatedDP) {
		this.calculatedDP = calculatedDP;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getStockLocation() {
		return stockLocation;
	}
	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}
	public String getDpShare() {
		return dpShare;
	}
	public void setDpShare(String dpShare) {
		this.dpShare = dpShare;
	}
	public String getOldPolicyNo() {
		return oldPolicyNo;
	}
	public void setOldPolicyNo(String oldPolicyNo) {
		this.oldPolicyNo = oldPolicyNo;
	}

}
