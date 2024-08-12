package com.integrosys.cms.app.poi.report.xml.schema;

public class Report {
	private String query;
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
	
}
