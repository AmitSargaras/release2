package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;


public class OBCommonFile {
	
public OBCommonFile(){}
	
	private long id;
	private long fileId;
	private long sysXrefId;
	private String customer;
	private String line;
	private String serialNo;
	private String currency;
	private double limit;
	private double utilize;
	private String status;
	private String reason;
	private String uploadStatus;
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD 
	private String security_id;
	
	private String makerId;
	private Date makerDate;
	private String checkerId;
	private Date checkerDate;
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSecurity_id() {
		return security_id;
	}
	public void setSecurity_id(String security_id) {
		this.security_id = security_id;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getLimit() {
		return limit;
	}
	public void setLimit(double limit) {
		this.limit = limit;
	}
	public double getUtilize() {
		return utilize;
	}
	public void setUtilize(double utilize) {
		this.utilize = utilize;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public long getSysXrefId() {
		return sysXrefId;
	}
	public void setSysXrefId(long sysXrefId) {
		this.sysXrefId = sysXrefId;
	}
	public String getMakerId() {
		return makerId;
	}
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}
	public Date getMakerDate() {
		return makerDate;
	}
	public void setMakerDate(Date makerDate) {
		this.makerDate = makerDate;
	}
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	public Date getCheckerDate() {
		return checkerDate;
	}
	public void setCheckerDate(Date checkerDate) {
		this.checkerDate = checkerDate;
	}
	
	

}
