package com.integrosys.cms.app.customer.bus;

public interface IIfscMethod extends java.io.Serializable{

	public long getId() ;
	public void setId(long id);
	
	public long getCustomerId();
	public void setCustomerId(long customerId);
	
	public String getNodal();
	public void setNodal(String nodal);
	
	public String getLead();
	public void setLead(String lead);
	
	public String getIfscCode();
	public void setIfscCode(String ifscCode);
	
	public String getBankName();
	public void setBankName(String bankName);
	
	public String getBranchName();
	public void setBranchName(String branchName);
	
	public String getBranchNameAddress();
	public void setBranchNameAddress(String branchNameAddress);
	
	public String getBankType();
	public void setBankType(String bankType);
	
	public String getEmailID() ;
	public void setEmailID(String emailID);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getRevisedEmailID();
	public void setRevisedEmailID(String revisedEmailID);
}
