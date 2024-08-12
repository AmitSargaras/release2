package com.integrosys.cms.app.user.bus;

import java.util.Date;



public class OBSMSUserUploadLog implements ISMSUserUploadLog  {


	/**
	 * constructor
	 */
	public OBSMSUserUploadLog() {
		
	}
	private long id;
    private String uploadFileName;
	private Date uploadDate;
	private String userId;
	private String rejectReason;
	private long versionTime;
	
	
	private String activity;
	private String successReject;
	private String deptCode;
	private String branchCode;
	private String userRole;
	private String userName;
	
	
	
	
	
	
	
	
	
	
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getSuccessReject() {
		return successReject;
	}
	public void setSuccessReject(String successReject) {
		this.successReject = successReject;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
}
