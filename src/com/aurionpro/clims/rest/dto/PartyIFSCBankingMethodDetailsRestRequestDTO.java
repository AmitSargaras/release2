package com.aurionpro.clims.rest.dto;

public class PartyIFSCBankingMethodDetailsRestRequestDTO {
	
	
	private String ifscCode;
	private String bankName;
	private String branchName;
	private String emailID;
	private String address;
	private String revisedEmailIds;
	private String nodal;
	private String lead;
	private String bankType;

	private String branchId;
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

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
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

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getRevisedEmailIds() {
		return revisedEmailIds;
	}

	public void setRevisedEmailIds(String revisedEmailIds) {
		this.revisedEmailIds = revisedEmailIds;
	}

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



}