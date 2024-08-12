package com.integrosys.cms.app.email.notification.bus;


public class OBCaseCreationNotificationDetail implements ICaseCreationNotificationDetail {

	private String caseCreationDate;
	private String caseCreationId;
	
	private String docNos;

	private String pendingDays;
	private String remarks;
	private String segment ;
	
	private String branch;
	private String partyId;
	private String partyName;
	
	
	
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
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCaseCreationDate() {
		return caseCreationDate;
	}
	public void setCaseCreationDate(String caseCreationDate) {
		this.caseCreationDate = caseCreationDate;
	}
	public String getCaseCreationId() {
		return caseCreationId;
	}
	public void setCaseCreationId(String caseCreationId) {
		this.caseCreationId = caseCreationId;
	}
	public String getDocNos() {
		return docNos;
	}
	public void setDocNos(String docNos) {
		this.docNos = docNos;
	}
	public String getPendingDays() {
		return pendingDays;
	}
	public void setPendingDays(String pendingDays) {
		this.pendingDays = pendingDays;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
	}
