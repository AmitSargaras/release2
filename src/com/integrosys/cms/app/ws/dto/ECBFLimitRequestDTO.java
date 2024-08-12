package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ECBFLimitRequestInfo")
public class ECBFLimitRequestDTO {
	
	@XmlElement(name = "lineId")
	private String lineId;

	@XmlElement(name = "documentationAmount", required=true)
	private String	documentationAmount;

	@XmlElement(name = "finalLimitReleasable", required=true)
	private String	finalLimitReleasable;

	@XmlElement(name = "revolvingLine", required=true)
	private String	revolvingLine;

	@XmlElement(name = "capitalMarketExposure", required=true)
	private String	capitalMarketExposure;

	@XmlElement(name = "realEstateExposure", required=true)
	private String	realEstateExposure;
	
	@XmlElement(name = "estateType")
	private String estateType;
	
	@XmlElement(name = "commEstateType")
	private String commEstateType;

	@XmlElement(name = "ruleID", required=true)
	private String	ruleID;
	
	@XmlElement(name = "priorityFlag", required=true)
	private String	priorityFlag;

	@XmlElement(name = "priority", required=true)
	private String	priority;

	@XmlElement(name = "uncondiCanclCommitment", required=true)
	private String	uncondiCanclCommitment;

	@XmlElement(name = "borrowerCustomerID", required=true)
	private String	borrowerCustomerID;
	
	@XmlElement(name = "liabilityBranch", required=true)
	private String	liabilityBranch;
	
	@XmlElement(name = "available", required=true)
	private String	available;
	
	@XmlElement(name = "udfList")
	private UdfLimitRequestDTO udfList;
	
	@XmlElement(name = "lineCode", required=true)
	private String	lineCode;

	@XmlElement(name = "borrowerAdditionDate", required=true)
	private String	borrowerAdditionDate;

	@XmlElement(name = "freeze", required=true)
	private String	freeze;

	@XmlElement(name = "remarks")
	private String	remarks;

	@XmlElement(name = "panNumber", required=true)
	private String panNumber;

	@XmlElement(name = "action", required=true)
	private String action;
	
	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getDocumentationAmount() {
		return documentationAmount;
	}

	public void setDocumentationAmount(String documentationAmount) {
		this.documentationAmount = documentationAmount;
	}

	public String getFinalLimitReleasable() {
		return finalLimitReleasable;
	}

	public void setFinalLimitReleasable(String finalLimitReleasable) {
		this.finalLimitReleasable = finalLimitReleasable;
	}

	public String getRevolvingLine() {
		return revolvingLine;
	}

	public void setRevolvingLine(String revolvingLine) {
		this.revolvingLine = revolvingLine;
	}

	public String getCapitalMarketExposure() {
		return capitalMarketExposure;
	}

	public void setCapitalMarketExposure(String capitalMarketExposure) {
		this.capitalMarketExposure = capitalMarketExposure;
	}

	public String getRealEstateExposure() {
		return realEstateExposure;
	}

	public void setRealEstateExposure(String realEstateExposure) {
		this.realEstateExposure = realEstateExposure;
	}
	
	public String getEstateType() {
		return estateType;
	}

	public void setEstateType(String estateType) {
		this.estateType = estateType;
	}
	
	public String getCommEstateType() {
		return commEstateType;
	}

	public void setCommEstateType(String commEstateType) {
		this.commEstateType = commEstateType;
	}
	
	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	public String getUncondiCanclCommitment() {
		return uncondiCanclCommitment;
	}

	public void setUncondiCanclCommitment(String uncondiCanclCommitment) {
		this.uncondiCanclCommitment = uncondiCanclCommitment;
	}

	public String getBorrowerCustomerID() {
		return borrowerCustomerID;
	}

	public void setBorrowerCustomerID(String borrowerCustomerID) {
		this.borrowerCustomerID = borrowerCustomerID;
	}

	public String getBorrowerAdditionDate() {
		return borrowerAdditionDate;
	}

	public void setBorrowerAdditionDate(String borrowerAdditionDate) {
		this.borrowerAdditionDate = borrowerAdditionDate;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getLiabilityBranch() {
		return liabilityBranch;
	}

	public void setLiabilityBranch(String liabilityBranch) {
		this.liabilityBranch = liabilityBranch;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	public UdfLimitRequestDTO getUdfList() {
		return udfList;
	}
	
	public boolean isUdfListNotEmpty() {
		return this.getUdfList() != null 
				&& this.getUdfList().getUdf() != null 
				&& this.getUdfList().getUdf().size() > 0;
	}

	public void setUdfList(UdfLimitRequestDTO udfList) {
		this.udfList = udfList;
	}
	
}