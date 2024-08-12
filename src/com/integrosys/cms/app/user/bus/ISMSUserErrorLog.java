package com.integrosys.cms.app.user.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;


public interface ISMSUserErrorLog extends Serializable, IValueObject{

	public long getId();
	public void setId(long id);
	
	public String getUploadId();
	public void setUploadId(String uploadId);
	
	public String getUploadFileName();
	public void setUploadFileName(String uploadFileName) ;
	
	public Date getUploadDate();
	public void setUploadDate(Date uploadDate);
	
	public String getErrorCode();
	public void setErrorCode(String errorCode);
	
	public String getErrorDesc();
	public void setErrorDesc(String errorDesc);
	
	public String getRecordCount();
	public void setRecordCount(String recordCount);
	
	public String getUserId();
	public void setUserId(String userId);
	

    public long getVersionTime();
    public void setVersionTime(long versionTime);
}
