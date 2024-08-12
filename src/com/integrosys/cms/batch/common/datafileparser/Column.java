package com.integrosys.cms.batch.common.datafileparser;
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
	
}
