package com.integrosys.cms.app.user.bus;

import java.util.Date;



public class OBSMSUserErrorLog implements ISMSUserErrorLog  {


	/**
	 * constructor
	 */
	public OBSMSUserErrorLog() {
		
	}
	private long id;
    private String uploadId;
    private String uploadFileName;
	private Date uploadDate;
	private String recordCount;
	private String userId;
	private String errorCode;
	private String errorDesc;
	private long versionTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
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
	
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
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
