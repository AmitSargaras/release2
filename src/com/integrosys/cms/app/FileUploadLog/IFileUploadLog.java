package com.integrosys.cms.app.FileUploadLog;

import java.io.Serializable;
import java.util.Date;

public interface IFileUploadLog extends Serializable{
	
	public String getUploadedFileName();
	public void setUploadedFileName(String uploadedFileName);
	public Date getUploadDate();
	public void setUploadDate(Date uploadDate);
	public String getNoOfRecords();
	public void setNoOfRecords(String noOfRecords);
	public String getFileUploadMessage();
	public void setFileUploadMessage(String fileUploadMessage);
	public long getFileUploadId();
	public void setFileUploadId(long fileUploadId);
	public String getFileUploadStatus();
	public void setFileUploadStatus(String fileUploadStatus);
}
