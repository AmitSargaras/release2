package com.integrosys.cms.batch.common.datafileparser;
/**
 * Pojo for Datafile tag of template xml the 
 * 
 * @author Archana Panchapakesan
 * @since 5th July 2011
 * 
 */
public class DataFile {
	private String delimiter;
	private String fileFormat;
	private Integer dataStartIndex;
	private Header header;
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
	 * @return the header
	 */
	public Header getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
	}

}
