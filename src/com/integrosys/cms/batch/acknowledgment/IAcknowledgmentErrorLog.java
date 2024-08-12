package com.integrosys.cms.batch.acknowledgment;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public interface IAcknowledgmentErrorLog extends Serializable{
	public Set getErrEntriesSet() ;
	public void setErrEntriesSet(Set errEntriesSet) ;
	public String getUploadId() ;
	public void setUploadId(String uploadId) ;
	public String getFileName() ;
	public void setFileName(String fileName) ;
	public String getNoOfRecords() ;
	public void setNoOfRecords(String noOfRecords) ;
	public String getSuccessRecords() ;
	public void setSuccessRecords(String successRecords) ;
	public String getFailedRecords() ;
	public void setFailedRecords(String failedRecords) ;
	public Date getUploadTime() ;
	public void setUploadTime(Date uploadTime) ;
	public long getId() ;
	public void setId(long id) ;
}
