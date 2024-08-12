package com.integrosys.cms.app.poi.report.xml.schema;



public class ReportColumn {
	/** The label of the column. */
	private String header;
	/** The format of the column.This must be either textFormat or amountFormat. */
	private String format;
	/** The width of the column. */
	private Integer width;

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}


}
