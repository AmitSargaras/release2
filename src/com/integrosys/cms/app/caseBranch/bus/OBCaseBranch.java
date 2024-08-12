
package com.integrosys.cms.app.caseBranch.bus;



/**
 * @author abhijit.rudrakshawar 
 */
public class OBCaseBranch implements ICaseBranch {
	
	/**
	 * constructor
	 */
	public OBCaseBranch() {
		
	}
    private long id;
	
	private long versionTime;
	
	
	
	private String status;
	private String deprecated;
	  
	private String branchCode;       
	private String coordinator1;  
	private String coordinator1Email;
	private String coordinator2;   
	private String coordinator2Email;
	      


	
	
    public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getCoordinator1() {
		return coordinator1;
	}
	public void setCoordinator1(String coordinator1) {
		this.coordinator1 = coordinator1;
	}
	public String getCoordinator1Email() {
		return coordinator1Email;
	}
	public void setCoordinator1Email(String coordinator1Email) {
		this.coordinator1Email = coordinator1Email;
	}
	public String getCoordinator2() {
		return coordinator2;
	}
	public void setCoordinator2(String coordinator2) {
		this.coordinator2 = coordinator2;
	}
	public String getCoordinator2Email() {
		return coordinator2Email;
	}
	public void setCoordinator2Email(String coordinator2Email) {
		this.coordinator2Email = coordinator2Email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
	
	
	



}