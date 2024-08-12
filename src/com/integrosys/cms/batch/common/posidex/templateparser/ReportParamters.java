package com.integrosys.cms.batch.common.posidex.templateparser;

public class ReportParamters {

	private ReportColumns reportColumns; 
	private String reportName;
	
	private String additionalHeader;
	private String additionalTrailer;
	
	
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
	public String getAdditionalHeader() {
		return additionalHeader;
	}
	public void setAdditionalHeader(String additionalHeader) {
		this.additionalHeader = additionalHeader;
	}
	public String getAdditionalTrailer() {
		return additionalTrailer;
	}
	public void setAdditionalTrailer(String additionalTrailer) {
		this.additionalTrailer = additionalTrailer;
	}
}
