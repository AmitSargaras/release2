package com.integrosys.cms.batch.dfso.borrower;

import java.io.Serializable;
import java.util.Date;

public interface IDFSOLog extends Serializable{
	
	public long getId();
	public void setId(long id);
	
	public String getFileName() ;
	public void setFileName(String fileName) ;
	
	public Date getUploadTime() ;
	public void setUploadTime(Date uploadTime) ;
	
	public String getStatus() ;
	public void setStatus(String status) ;

}
