package com.integrosys.cms.app.customer.bus;

public class OBIfscMethod implements IIfscMethod{
	
	private static final long serialVersionUID=1L;
	private long id = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private long customerId;
	private String nodal;
	private String lead;
	private String ifscCode;
	private String bankName;
	private String branchName;
	private String branchNameAddress;
	private String bankType;
	private String emailID;
	private String status;
	private String revisedEmailID;
	
	
	public String getRevisedEmailID() {
		return revisedEmailID;
	}
	public void setRevisedEmailID(String revisedEmailID) {
		this.revisedEmailID = revisedEmailID;
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
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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
	public String getBranchNameAddress() {
		return branchNameAddress;
	}
	public void setBranchNameAddress(String branchNameAddress) {
		this.branchNameAddress = branchNameAddress;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	
}
