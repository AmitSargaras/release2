package com.integrosys.cms.ui.collateral;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeForm;

public class MaintainInsuranceGCForm extends TrxContextForm implements Serializable {
	
	private String id;	
	private String versionTime;
	private String parentId;
	private String creationDate;	
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
	private String receivedDate;
	private String effectiveDate;
	private String expiryDate;
	private String insuranceDefaulted;
	private String insurancePremium;
	private String remark;
	private String allComponent;
	private String selectComponent;
	private String appendedComponent;
	
	
//	private String fundedShare;
	private String  calculatedDP;
	private String dueDate;
	private String stockLocation;
	
	private String lastUpdatedBy;
	private String lastApproveBy;
	private String lastUpdatedOnStr;
	private String lastApproveOnStr;
	
	//Uma Khot::Insurance Deferral maintainance
	private String insuranceStatus;
	private String insuredAddress;
	private String remark2;
	private String insuredAgainst;
	private String originalTargetDate;
	private String nextDueDate;
	private String dateDeferred;
	private String creditApprover;
	private String waivedDate;
	private String insuranceStatusRadio;
	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpShare;
	private String oldPolicyNo;
	
	public String getInsuranceStatusRadio() {
		return insuranceStatusRadio;
	}
	public void setInsuranceStatusRadio(String insuranceStatusRadio) {
		this.insuranceStatusRadio = insuranceStatusRadio;
	}
	public String getOriginalTargetDate() {
		return originalTargetDate;
	}
	public void setOriginalTargetDate(String originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}
	public String getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public String getDateDeferred() {
		return dateDeferred;
	}
	public void setDateDeferred(String dateDeferred) {
		this.dateDeferred = dateDeferred;
	}
	public String getCreditApprover() {
		return creditApprover;
	}
	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}
	public String getWaivedDate() {
		return waivedDate;
	}
	public void setWaivedDate(String waivedDate) {
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
	public String getLastUpdatedOnStr() {
		return lastUpdatedOnStr;
	}
	public void setLastUpdatedOnStr(String lastUpdatedOnStr) {
		this.lastUpdatedOnStr = lastUpdatedOnStr;
	}
	public String getLastApproveOnStr() {
		return lastApproveOnStr;
	}
	public void setLastApproveOnStr(String lastApproveOnStr) {
		this.lastApproveOnStr = lastApproveOnStr;
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
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}
		
	
	
	public String getAppendedComponent() {
		return appendedComponent;
	}
	public void setAppendedComponent(String appendedComponent) {
		this.appendedComponent = appendedComponent;
	}
	public String getAllComponent() {
		return allComponent;
	}
	public void setAllComponent(String allComponent) {
		this.allComponent = allComponent;
	}
	public String getSelectComponent() {
		return selectComponent;
	}
	public void setSelectComponent(String selectComponent) {
		this.selectComponent = selectComponent;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
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
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	
	
	
	public String[][] getMapper() {
		String[][] input = {  { "insuranceGCObj", INSURANCEGC_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	public static final String INSURANCEGC_MAPPER = "com.integrosys.cms.ui.collateral.InsuranceGCMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

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
