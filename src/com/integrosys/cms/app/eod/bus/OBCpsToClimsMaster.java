package com.integrosys.cms.app.eod.bus;


public class OBCpsToClimsMaster {

	private String masterName;
	private String fileName;
	private Long recordCount;
	private Long successfullRecordCount;
	private Long failureRecordCount;
	private Long rowIndex;
	private Boolean validRecord =true;
	private String errorMessage;
	

	/**
	 * @return the masterName
	 */
	public String getMasterName() {
		return masterName;
	}
	/**
	 * @param masterName the masterName to set
	 */
	public void setMasterName(String masterName) {
		this.masterName = masterName;
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
	/**
	 * @return the recordCount
	 */
	public Long getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	/**
	 * @return the successfullRecordCount
	 */
	public Long getSuccessfullRecordCount() {
		return successfullRecordCount;
	}
	/**
	 * @param successfullRecordCount the successfullRecordCount to set
	 */
	public void setSuccessfullRecordCount(Long successfullRecordCount) {
		this.successfullRecordCount = successfullRecordCount;
	}
	/**
	 * @return the failureRecordCount
	 */
	public Long getFailureRecordCount() {
		return failureRecordCount;
	}
	/**
	 * @param failureRecordCount the failureRecordCount to set
	 */
	public void setFailureRecordCount(Long failureRecordCount) {
		this.failureRecordCount = failureRecordCount;
	}
	/**
	 * @return the rowIndex
	 */
	public Long getRowIndex() {
		return rowIndex;
	}
	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(Long rowIndex) {
		this.rowIndex = rowIndex;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the validRecord
	 */
	public Boolean isValidRecord() {
		return validRecord;
	}
	/**
	 * @param validRecord the validRecord to set
	 */
	public void setValidRecord(Boolean validRecord) {
		this.validRecord = validRecord;
	}
	
}