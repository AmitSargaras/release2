
package com.integrosys.cms.app.caseCreation.bus;

import java.util.Date;



/**
 * @author abhijit.rudrakshawar 
 */
public class OBCaseCreationRemark implements ICaseCreationRemark {
	
	/**
	 * constructor
	 */
	public OBCaseCreationRemark() {
		
	}
    private long id;
	
	private long versionTime;
	
	private String userTeam="";
	private long userId=0l;
	private String userRole="";
	private Date updatedDate;
	private long limitProfileId=0l;
	private String remark ="";
	private String status;
	

	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getUserTeam() {
		return userTeam;
	}
	public void setUserTeam(String userTeam) {
		this.userTeam = userTeam;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public long getLimitProfileId() {
		return limitProfileId;
	}
	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
	
}