package com.integrosys.cms.app.ecbf.limit;

import java.util.Date;

import com.integrosys.cms.app.ws.dto.ECBFLimitRequestDTO;
import com.integrosys.cms.host.eai.support.ReflectionUtils;

public class OBECBFLimitInterfaceLog implements IECBFLimitInterfaceLog{
	
	private long id;
	private String	lineCode;
	private String	documentationAmount;
	private String	finalLimitReleasable;
	private String	revolvingLine;
	private String	capitalMarketExposure;
	private String	realEstateExposure;
	private String estateType;
	private String estateTypeValue;
	private String commEstateType;
	private String commEstateTypeValue;
	private String	ruleID;
	private String ruleIDValue;
	private String	priority;
	private String priorityValue;
	private String priorityFlag;
	private String	uncondiCanclCommitment;
	private String	uncondiCanclCommitmentValue;
	private String	borrowerCustomerID;
	private String	liabilityBranch;
	private String	liabilityBranchValue;
	private String	available;
	private String	udfSequences;
	private String	udfNames;
	private String  udfValues;
	private String	borrowerAdditionDate;
	private String	freeze;
	private String	remarks;
	private String panNumber;
	private String action;
	private String partyId;
	private String lmtId;
	private String lineId;
	
	private String errorCode;
	private Date requestDateTime;
	private Date responseDateTime;
	private String errorMessage;
	private String requestMessage;
	private String responseMessage;
	private String status;
	
	public OBECBFLimitInterfaceLog() { }
	
	public OBECBFLimitInterfaceLog(ECBFLimitRequestDTO dto) {
		copyFields(dto, this);
	}
	
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
	
	public String getEstateTypeValue() {
		return estateTypeValue;
	}

	public void setEstateTypeValue(String estateTypeValue) {
		this.estateTypeValue = estateTypeValue;
		
	}

	public String getCommEstateType() {
		return commEstateType;
	}

	public void setCommEstateType(String commEstateType) {
		this.commEstateType = commEstateType;
	}
	
	public String getCommEstateTypeValue() {
		return commEstateTypeValue;
	}

	public void setCommEstateTypeValue(String commEstateTypeValue) {
		this.commEstateTypeValue = commEstateTypeValue;
		
	}

	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
	
	public String getRuleIDValue() {
		return ruleIDValue;
	}

	public void setRuleIDValue(String ruleIDValue) {
		this.ruleIDValue = ruleIDValue;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getPriorityValue() {
		return priorityValue;
	}

	public void setPriorityValue(String priorityValue) {
		this.priorityValue = priorityValue;
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
	
	public String getUncondiCanclCommitmentValue() {
		return uncondiCanclCommitmentValue;
	}

	public void setUncondiCanclCommitmentValue(String uncondiCanclCommitmentValue) {
		this.uncondiCanclCommitmentValue = uncondiCanclCommitmentValue;
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
	
	public String getLiabilityBranchValue() {
		return liabilityBranchValue;
	}

	public void setLiabilityBranchValue(String liabilityBranchValue) {
		this.liabilityBranchValue = liabilityBranchValue;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getUdfSequences() {
		return udfSequences;
	}

	public void setUdfSequences(String udfSequences) {
		this.udfSequences = udfSequences;
	}

	public String getUdfNames() {
		return udfNames;
	}

	public void setUdfNames(String udfNames) {
		this.udfNames = udfNames;
	}

	public String getUdfValues() {
		return udfValues;
	}

	public void setUdfValues(String udfValues) {
		this.udfValues = udfValues;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	public Date getResponseDateTime() {
		return responseDateTime;
	}

	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getLmtId() {
		return lmtId;
	}

	public void setLmtId(String lmtId) {
		this.lmtId = lmtId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	private void copyFields(Object src, Object dest) {
		ReflectionUtils.copyValuesByProperties(src, dest, copyProperties);
	}

}