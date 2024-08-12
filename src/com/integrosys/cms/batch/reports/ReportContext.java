package com.integrosys.cms.batch.reports;

import java.util.Date;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;

public class ReportContext {
	private OBReportConfig reportConfig;
	private ReportParameter param;
	private int rptFormatIndex;
	private int retryCount;
	private Date reportStartTime;
	private String genReportName;	
	private String reportInfo;
	private String errLog;
	private String reportStatus;
	
	private IInfoObjects oInfoObjects;

	public OBReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(OBReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	public ReportParameter getParam() {
		return param;
	}

	public void setParam(ReportParameter param) {
		this.param = param;
	}

	public int getRptFormatIndex() {
		return rptFormatIndex;
	}

	public void setRptFormatIndex(int rptFormatIndex) {
		this.rptFormatIndex = rptFormatIndex;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public Date getReportStartTime() {
		return reportStartTime;
	}

	public void setReportStartTime(Date reportStartTime) {
		this.reportStartTime = reportStartTime;
	}

	public String getGenReportName() {
		return genReportName;
	}

	public void setGenReportName(String genReportName) {
		this.genReportName = genReportName;
	}

	public IInfoObjects getOInfoObjects() {
		return oInfoObjects;
	}

	public void setOInfoObjects(IInfoObjects infoObjects) {
		oInfoObjects = infoObjects;
	}		
	
	public String getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(String reportInfo) {
		this.reportInfo = reportInfo;
	}

	public String getErrLog() {
		return errLog;
	}

	public void setErrLog(String errLog) {
		this.errLog = errLog;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getReportFileName() {
		if (reportConfig != null && reportConfig.getReportFileNames().length > rptFormatIndex)
			return reportConfig.getReportFileNames()[rptFormatIndex];
		
		return "";
	}
	
	public String getReportFormat() {
		if (reportConfig != null && reportConfig.getExportFormats().length > rptFormatIndex )
			return reportConfig.getExportFormats()[rptFormatIndex];
			
		return "";
	}
	
	public void increaseExportFormatIndex() {
		rptFormatIndex += 1;
	}
	
	public void increaseRetryCount() {
		retryCount += 1;
	}
}
