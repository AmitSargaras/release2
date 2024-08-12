package com.integrosys.cms.batch.common.syncmaster.datafileparser;


/**
 * Pojo for Datafile tag of template xml the 
 * 
 * @author Archana Panchapakesan
 * @since 5th July 2011
 * 
 */
public class SyncMasterTemplateIn {
	private String requestClassName;
	private String mapperClassName;
	private String validatorClassName;
	private String delimiter;
	private String fileFormat;
	private Integer dataStartIndex;
	private MappedColumns mappedColumns;
	private DefaultColumns defaultColumns;
	private String secondaryDelimiter;
	private int isFooter;
	public int getIsFooter() {
		return isFooter;
	}
	public void setIsFooter(int isFooter) {
		this.isFooter = isFooter;
	}
	/**
	 * @return the secondaryDelimiter
	 */
	public String getSecondaryDelimiter() {
		return secondaryDelimiter;
	}
	/**
	 * @param secondaryDelimiter the secondaryDelimiter to set
	 */
	public void setSecondaryDelimiter(String secondaryDelimiter) {
		this.secondaryDelimiter = secondaryDelimiter;
	}
	/**
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return delimiter;
	}
	/**
	 * @param delimiter the delimiter to set
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	/**
	 * @return the fileFormat
	 */
	public String getFileFormat() {
		return fileFormat;
	}
	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * @return the dataStartIndex
	 */
	public Integer getDataStartIndex() {
		return dataStartIndex;
	}
	/**
	 * @param dataStartIndex the dataStartIndex to set
	 */
	public void setDataStartIndex(Integer dataStartIndex) {
		this.dataStartIndex = dataStartIndex;
	}
	
	/**
	 * @return the mappedColumns
	 */
	public MappedColumns getMappedColumns() {
		return mappedColumns;
	}
	/**
	 * @param mappedColumns the mappedColumns to set
	 */
	public void setMappedColumns(MappedColumns mappedColumns) {
		this.mappedColumns = mappedColumns;
	}
	/**
	 * @return the defaultColumns
	 */
	public DefaultColumns getDefaultColumns() {
		return defaultColumns;
	}
	/**
	 * @param defaultColumns the defaultColumns to set
	 */
	public void setDefaultColumns(DefaultColumns defaultColumns) {
		this.defaultColumns = defaultColumns;
	}
	public String getRequestClassName() {
		return requestClassName;
	}
	public void setRequestClassName(String requestClassName) {
		this.requestClassName = requestClassName;
	}
	/**
	 * @return the mapperClassName
	 */
	public String getMapperClassName() {
		return mapperClassName;
	}
	/**
	 * @param mapperClassName the mapperClassName to set
	 */
	public void setMapperClassName(String mapperClassName) {
		this.mapperClassName = mapperClassName;
	}
	/**
	 * @return the validatorClassName
	 */
	public String getValidatorClassName() {
		return validatorClassName;
	}
	/**
	 * @param validatorClassName the validatorClassName to set
	 */
	public void setValidatorClassName(String validatorClassName) {
		this.validatorClassName = validatorClassName;
	}
	
}
