package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;

public class OBDeferralExtensionFileUpload {
	private String partyID;
	private String partyName;
	private String segment;
	private String module;
	private String checklistID;
	private String checklistType;
	private String docID;
	private String discrepancyID;
	private String ladCode;
	private Date dateDeferred;
	private Date nextDueDate;
	private Date previousNextDueDate;
	private String action;
	private String creditApprover;
	private String remarks;
	private String status;
	private String uploadStatus;
	private long fileId;
	private String fileName;
	private String failReason;
	private Date dateOfRequest;
	private Date dateOfProcess;
	private Date originalTargetDate;
	public String getPartyID() {
		return partyID;
	}
	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getChecklistID() {
		return checklistID;
	}
	public void setChecklistID(String checklistID) {
		this.checklistID = checklistID;
	}
	public String getChecklistType() {
		return checklistType;
	}
	public void setChecklistType(String checklistType) {
		this.checklistType = checklistType;
	}
	public String getDocID() {
		return docID;
	}
	public void setDocID(String docID) {
		this.docID = docID;
	}
	public String getDiscrepancyID() {
		return discrepancyID;
	}
	public void setDiscrepancyID(String discrepancyID) {
		this.discrepancyID = discrepancyID;
	}
	public String getLadCode() {
		return ladCode;
	}
	public void setLadCode(String ladCode) {
		this.ladCode = ladCode;
	}
	public Date getDateDeferred() {
		return dateDeferred;
	}
	public void setDateDeferred(Date dateDeferred) {
		this.dateDeferred = dateDeferred;
	}
	public Date getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public Date getPreviousNextDueDate() {
		return previousNextDueDate;
	}
	public void setPreviousNextDueDate(Date previousNextDueDate) {
		this.previousNextDueDate = previousNextDueDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCreditApprover() {
		return creditApprover;
	}
	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Date getDateOfRequest() {
		return dateOfRequest;
	}
	public void setDateOfRequest(Date dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}
	public Date getDateOfProcess() {
		return dateOfProcess;
	}
	public void setDateOfProcess(Date dateOfProcess) {
		this.dateOfProcess = dateOfProcess;
	}
	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}
	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}
}
