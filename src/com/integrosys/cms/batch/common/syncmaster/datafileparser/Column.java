package com.integrosys.cms.batch.common.syncmaster.datafileparser;
/**
 * Pojo for Column tag of template xml the 
 * 
 * @author Archana Panchapakesan
 * @since 5th July 2011
 * 
 */
public class Column {
	private String name;
	private String dataType;
	private Integer length;
	private String mappedFieldName;
	private boolean isDefualtValueRequried;
	private String defaultValue;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the mappedFieldName
	 */
	public String getMappedFieldName() {
		return mappedFieldName;
	}
	/**
	 * @param mappedFieldName the mappedFieldName to set
	 */
	public void setMappedFieldName(String mappedFieldName) {
		this.mappedFieldName = mappedFieldName;
	}
	/**
	 * @return the isDefualtValueRequried
	 */
	public boolean isDefualtValueRequried() {
		return isDefualtValueRequried;
	}
	/**
	 * @param isDefualtValueRequried the isDefualtValueRequried to set
	 */
	public void setDefualtValueRequried(boolean isDefualtValueRequried) {
		this.isDefualtValueRequried = isDefualtValueRequried;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
