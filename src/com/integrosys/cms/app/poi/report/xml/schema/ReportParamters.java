package com.integrosys.cms.app.poi.report.xml.schema;

public class ReportParamters {

	private ReportColumns reportColumns; 
	private String reportName;
	
	
	/**
	 * @return the reportColumns
	 */
	public ReportColumns getReportColumns() {
		return reportColumns;
	}
	/**
	 * @param reportColumns the reportColumns to set
	 */
	public void setReportColumns(ReportColumns reportColumns) {
		this.reportColumns = reportColumns;
	}
	public String getReportName() {
		return reportName;
	}
	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
