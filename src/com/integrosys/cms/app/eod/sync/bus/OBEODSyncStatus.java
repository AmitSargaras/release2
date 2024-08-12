package com.integrosys.cms.app.eod.sync.bus;

import java.util.Date;


public class OBEODSyncStatus implements IEODSyncStatus {
    private Long id;
    private long versionTime;
    private Date applicationDate;
    private Date currentDate;
    private String syncDirection;  
    private String processKey;     
    private String processDesc;    
    private Date processStartTime; 
    private Date processEndTime;   
    private String processStatus;   
    private String processException;
    private long totalCount ;  
    private long successCount ;
    private long failedCount ;
    private String fileName;
    private String acknowledgementFileName;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the versionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}
	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	/**
	 * @return the applicationDate
	 */
	public Date getApplicationDate() {
		return applicationDate;
	}
	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate() {
		return currentDate;
	}
	/**
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	/**
	 * @return the syncDirection
	 */
	public String getSyncDirection() {
		return syncDirection;
	}
	/**
	 * @param syncDirection the syncDirection to set
	 */
	public void setSyncDirection(String syncDirection) {
		this.syncDirection = syncDirection;
	}
	/**
	 * @return the processKey
	 */
	public String getProcessKey() {
		return processKey;
	}
	/**
	 * @param processKey the processKey to set
	 */
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	/**
	 * @return the processDesc
	 */
	public String getProcessDesc() {
		return processDesc;
	}
	/**
	 * @param processDesc the processDesc to set
	 */
	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	/**
	 * @return the processStartTime
	 */
	public Date getProcessStartTime() {
		return processStartTime;
	}
	/**
	 * @param processStartTime the processStartTime to set
	 */
	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}
	/**
	 * @return the processEndTime
	 */
	public Date getProcessEndTime() {
		return processEndTime;
	}
	/**
	 * @param processEndTime the processEndTime to set
	 */
	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}
	/**
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}
	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	/**
	 * @return the processException
	 */
	public String getProcessException() {
		return processException;
	}
	/**
	 * @param processException the processException to set
	 */
	public void setProcessException(String processException) {
		if(processException!=null && processException.length()>4000){
			this.processException = processException.trim().substring(0,3999);
		}else{
			this.processException = processException;
		}
	}
	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the successCount
	 */
	public long getSuccessCount() {
		return successCount;
	}
	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(long successCount) {
		this.successCount = successCount;
	}
	/**
	 * @return the failedCount
	 */
	public long getFailedCount() {
		return failedCount;
	}
	/**
	 * @param failedCount the failedCount to set
	 */
	public void setFailedCount(long failedCount) {
		this.failedCount = failedCount;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getAcknowledgementFileName() {
		return acknowledgementFileName;
	}
	public void setAcknowledgementFileName(String acknowledgementFileName) {
		this.acknowledgementFileName = acknowledgementFileName;
	}

	
}
