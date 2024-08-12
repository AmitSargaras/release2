package com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema;

public class SyncMasterTemplateOut {
	private String query;
	private String reportType;
	private String delimiter;
	private String secondaryDelimiter;
	private WhereClause whereClause;
	private ReportParamters reportParamters;
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the whereClause
	 */
	public WhereClause getWhereClause() {
		return whereClause;
	}
	/**
	 * @param whereClause the whereClause to set
	 */
	public void setWhereClause(WhereClause whereClause) {
		this.whereClause = whereClause;
	}
	/**
	 * @return the reportParamters
	 */
	public ReportParamters getReportParamters() {
		return reportParamters;
	}
	/**
	 * @param reportParamters the reportParamters to set
	 */
	public void setReportParamters(ReportParamters reportParamters) {
		this.reportParamters = reportParamters;
	}
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
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
	
}
