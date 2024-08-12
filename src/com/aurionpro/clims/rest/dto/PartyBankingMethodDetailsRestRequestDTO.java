package com.aurionpro.clims.rest.dto;

public class PartyBankingMethodDetailsRestRequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	
	private String leadNodalFlag;
	private String bankName;
	private String branchName;
	private String branchId;	
	private String bankType;
	private String nodal;
	private String lead;
	private String revisedEmailIds;

	public String getNodal() {
		return nodal;
	}

	public void setNodal(String nodal) {
		this.nodal = nodal;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getLeadNodalFlag() {
		return leadNodalFlag;
	}

	public void setLeadNodalFlag(String leadNodalFlag) {
		this.leadNodalFlag = leadNodalFlag;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getRevisedEmailIds() {
		return revisedEmailIds;
	}

	public void setRevisedEmailIds(String revisedEmailIds) {
		this.revisedEmailIds = revisedEmailIds;
	}
}