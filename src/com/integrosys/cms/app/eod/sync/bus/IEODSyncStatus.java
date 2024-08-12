package com.integrosys.cms.app.eod.sync.bus;

import java.util.Date;


public interface IEODSyncStatus {
	public Long getId();
	public void setId(Long id); 
	public long getVersionTime();
	public void setVersionTime(long versionTime) ;
	public Date getApplicationDate() ;
	public void setApplicationDate(Date applicationDate) ;
	public Date getCurrentDate() ;
	public void setCurrentDate(Date currentDate) ;
	public String getSyncDirection() ;
	public void setSyncDirection(String syncDirection) ;
	public String getProcessKey() ;
	public void setProcessKey(String processKey) ;
	public String getProcessDesc() ;
	public void setProcessDesc(String processDesc) ;
	public Date getProcessStartTime() ;
	public void setProcessStartTime(Date processStartTime) ;
	public Date getProcessEndTime() ;
	public void setProcessEndTime(Date processEndTime) ;
	public String getProcessStatus() ;
	public void setProcessStatus(String processStatus) ;
	public String getProcessException() ;
	public void setProcessException(String processException) ;
	public long getTotalCount() ;
	public void setTotalCount(long totalCount) ;
	public long getSuccessCount() ;
	public void setSuccessCount(long successCount) ;
	public long getFailedCount() ;
	public void setFailedCount(long failedCount) ;
	public String getFileName();
	public void setFileName(String fileName);
	public String getAcknowledgementFileName();
	public void setAcknowledgementFileName(String acknowledgementFileName);
}
