package com.integrosys.cms.app.FileUploadLog;

import java.util.Date;

public class OBFileUploadLog implements IFileUploadLog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4635147509159L;
	private String uploadedFileName;
	private Date uploadDate;
	private String noOfRecords;
	private String fileUploadMessage;
	private long fileUploadId;
	private String fileUploadStatus;
	
	
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(String noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	public String getFileUploadMessage() {
		return fileUploadMessage;
	}
	public void setFileUploadMessage(String fileUploadMessage) {
		this.fileUploadMessage = fileUploadMessage;
	}
	public long getFileUploadId() {
		return fileUploadId;
	}
	public void setFileUploadId(long fileUploadId) {
		this.fileUploadId = fileUploadId;
	}
	public String getFileUploadStatus() {
		return fileUploadStatus;
	}
	public void setFileUploadStatus(String fileUploadStatus) {
		this.fileUploadStatus = fileUploadStatus;
	}
	
	


}
