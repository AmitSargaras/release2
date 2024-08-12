package com.integrosys.cms.app.user.bus;

import java.util.Date;
import com.integrosys.cms.app.user.bus.ISMSUserLog;

public class OBSMSUserLog implements ISMSUserLog {


	/**
	 * constructor
	 */
	public OBSMSUserLog() {
		
	}
	private long id;
    private String uploadId;
	private String uploadFileName;
	private Date uploadDate;
	private String fileStatus;
	private String successfullCount;
	private String unsuccessfullCount;
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
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getSuccessfullCount() {
		return successfullCount;
	}
	public void setSuccessfullCount(String successfullCount) {
		this.successfullCount = successfullCount;
	}
	public String getUnsuccessfullCount() {
		return unsuccessfullCount;
	}
	public void setUnsuccessfullCount(String unsuccessfullCount) {
		this.unsuccessfullCount = unsuccessfullCount;
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
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	
	
	
	
}
