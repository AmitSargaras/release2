package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;

public class OBFileUpload implements IFileUpload{
	
	public OBFileUpload(){}
	
	private long id;
	private String fileName;
	private String uploadBy;
	private String approveBy;
	private Date uploadTime;
	private String totalRecords;
	private String approveRecords;
	private String fileType;
	
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	public String getApproveBy() {
		return approveBy;
	}
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getApproveRecords() {
		return approveRecords;
	}
	public void setApproveRecords(String approveRecords) {
		this.approveRecords = approveRecords;
}
	
}
