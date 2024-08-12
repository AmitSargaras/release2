package com.integrosys.cms.app.fileUpload.bus;

import java.util.Date;


public class OBCommonFdFile {
	
public OBCommonFdFile(){}
	
private long id;
private long fileId;
private long sysXrefId;
private String depositNumber;
//private double depositAmount;
private Date dateOfDeposit;
private Date dateOfMaturity;
private Double interestRate;
private String status;
private String reason;
private String uploadStatus;

/*
private String makerId;
private Date makerDate;
private String checkerId;
private Date checkerDate; */

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public long getFileId() {
	return fileId;
}
public void setFileId(long fileId) {
	this.fileId = fileId;
}

public String getDepositNumber() {
	return depositNumber;
}
public void setDepositNumber(String depositNumber) {
	this.depositNumber = depositNumber;
}
/*
public double getDepositAmount() {
	return depositAmount;
}
public void setDepositAmount(double depositAmount) {
	this.depositAmount = depositAmount;
} */

//Uma Khot:Fd Start date issue, 2012 is shown as 2020
public Date getDateOfDeposit() {
	return dateOfDeposit;
}
public void setDateOfDeposit(Date dateOfDeposit) {
	this.dateOfDeposit = dateOfDeposit;
}
public Date getDateOfMaturity() {
	return dateOfMaturity;
}
public void setDateOfMaturity(Date dateOfMaturity) {
	this.dateOfMaturity = dateOfMaturity;
}
public double getInterestRate() {
	return interestRate;
}
public void setInterestRate(double interestRate) {
	this.interestRate = interestRate;
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
/*
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

*/


}
