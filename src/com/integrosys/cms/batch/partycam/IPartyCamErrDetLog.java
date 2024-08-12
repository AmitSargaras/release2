package com.integrosys.cms.batch.partycam;

import java.io.Serializable;
import java.util.Date;

public interface IPartyCamErrDetLog extends Serializable{
	public long getId() ;
	public void setId(long id) ;
	public String getPtId() ;
	public void setPtId(String ptId) ;
	public String getRecordNo() ;
	public void setRecordNo(String recordNo) ;
	public String getErrorMsg() ;
	public void setErrorMsg(String errorMsg) ;
	public Date getTime() ;
	public void setTime(Date time) ;
	public String getFacSystemId() ;
	public void setFacSystemId(String facSystemId) ;
	public String getLineNo() ;
	public void setLineNo(String lineNo);
	public String getSerialNo();
	public void setSerialNo(String serialNo) ;
	public String getUploadStatus() ;
	public void setUploadStatus(String uploadStatus) ;
}
