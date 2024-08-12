package com.integrosys.cms.batch.dfso.borrower;

import java.util.Date;

public class OBDFSOLog implements IDFSOLog{

	private static final long serialVersionUID = 1L;
	public OBDFSOLog() {};
	
	private long id;
	private String fileName;
	private Date uploadTime;
	private String status;
	
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
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
