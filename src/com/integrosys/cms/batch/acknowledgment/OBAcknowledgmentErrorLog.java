package com.integrosys.cms.batch.acknowledgment;

import java.util.Date;
import java.util.Set;

public class OBAcknowledgmentErrorLog implements IAcknowledgmentErrorLog{
public long id;
public String uploadId;
public String fileName;
public String noOfRecords;
public String successRecords;
public String failedRecords;
public Date uploadTime;
public Set errEntriesSet;
public Set getErrEntriesSet() {
	return errEntriesSet;
}
public void setErrEntriesSet(Set errEntriesSet) {
	this.errEntriesSet = errEntriesSet;
}
public String getUploadId() {
	return uploadId;
}
public void setUploadId(String uploadId) {
	this.uploadId = uploadId;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getNoOfRecords() {
	return noOfRecords;
}
public void setNoOfRecords(String noOfRecords) {
	this.noOfRecords = noOfRecords;
}
public String getSuccessRecords() {
	return successRecords;
}
public void setSuccessRecords(String successRecords) {
	this.successRecords = successRecords;
}
public String getFailedRecords() {
	return failedRecords;
}
public void setFailedRecords(String failedRecords) {
	this.failedRecords = failedRecords;
}
public Date getUploadTime() {
	return uploadTime;
}
public void setUploadTime(Date uploadTime) {
	this.uploadTime = uploadTime;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
}

