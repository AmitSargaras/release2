package com.integrosys.cms.app.fileUpload.bus;

import java.sql.Date;

public class OBLeiDetailsFile extends OBCommonFile{
//	private long id;
//	private long fileId;
	private String partyId;
	private String leiCode;
	private Date leiExpDate;
	private String leiValidationFlag;
//	private String status;
//	private String reason;
//	private String uploadStatus;

//	public long getId() {
//		return id;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
//	public long getFileId() {
//		return fileId;
//	}
//	public void setFileId(long fileId) {
//		this.fileId = fileId;
//	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getLeiCode() {
		return leiCode;
	}
	public void setLeiCode(String leiCode) {
		this.leiCode = leiCode;
	}
	public Date getLeiExpDate() {
		return leiExpDate;
	}
	public void setLeiExpDate(Date leiExpDate) {
		this.leiExpDate = leiExpDate;
	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public String getReason() {
//		return reason;
//	}
//	public void setReason(String reason) {
//		this.reason = reason;
//	}
//	public String getUploadStatus() {
//		return uploadStatus;
//	}
//	public void setUploadStatus(String uploadStatus) {
//		this.uploadStatus = uploadStatus;
//	}
	public String getLeiValidationFlag() {
		return leiValidationFlag;
	}
	public void setLeiValidationFlag(String leiValidationFlag) {
		this.leiValidationFlag = leiValidationFlag;
	}
	
}
