package com.integrosys.cms.batch.partycam;

import java.util.Date;

public class OBPartyCamErrDetLog implements IPartyCamErrDetLog{
	
	public long id;
	public String ptId;
	public String recordNo;
	public String errorMsg;
	public Date time;
	public String facSystemId;
	public String lineNo;
	public String serialNo;
	public String uploadStatus;
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public String getFacSystemId() {
		return facSystemId;
	}
	public void setFacSystemId(String facSystemId) {
		this.facSystemId = facSystemId;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPtId() {
		return ptId;
	}
	public void setPtId(String ptId) {
		this.ptId = ptId;
	}
	public String getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	

}
