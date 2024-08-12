package com.integrosys.cms.batch.reports;

import java.util.Date;

import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/OBReportConfig.java,v 1.16 2006/09/05 05:39:42 hshii Exp $
 */

/**
 * Description: Contains the meta information of report types
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */
public class OBReportConfig {

    private String reportMasterId;

	private String reportName;

	private String scope;

	private String frequency;

	private String[] reportFileNames;

	private String[] organisations;

	private String titleMask;

	private int maxRows; // maximum number of rows to display for concentration
							// reports

	private String reportCategoryId; // report category to differentiate between
										// Concentration and MIS reports

	private String organisation;

	private Date reportDate;

	private String country;

	private String remarks;

	private String folderPath;

	private String pdfFileName;

	private String xlsFileName;

	private String wordFileName;

	private int pdfStatus = ICMSConstant.INT_INVALID_VALUE;

	private int xlsStatus = ICMSConstant.INT_INVALID_VALUE;

	private int wordStatus = ICMSConstant.INT_INVALID_VALUE;


    //------- Newly Added -----------
    private String reportNumber;
    private String recipients;


    public OBReportConfig() {

    }
    

    public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public boolean hasReportGenerated() {
		return ((pdfFileName != null) || (xlsFileName != null) || (wordFileName != null));
	}

	public boolean hasFailedReport() {
		return ((pdfStatus == ISchedulingInfo.ScheduleStatus.FAILURE)
				|| (xlsStatus == ISchedulingInfo.ScheduleStatus.FAILURE) || (wordStatus == ISchedulingInfo.ScheduleStatus.FAILURE));
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getOrganisation() {
		return this.organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public int getPdfStatus() {
		return this.pdfStatus;
	}

	public void setPdfStatus(int pdfStatus) {
		this.pdfStatus = pdfStatus;
	}

	public int getXlsStatus() {
		return this.xlsStatus;
	}

	public void setXlsStatus(int xlsStatus) {
		this.xlsStatus = xlsStatus;
	}

	public int getWordStatus() {
		return this.wordStatus;
	}

	public void setWordStatus(int wordStatus) {
		this.wordStatus = wordStatus;
	}

	public String getFolderPath() {
		return this.folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getPdfFileName() {
		return this.pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public String getXlsFileName() {
		return this.xlsFileName;
	}

	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}

	public String getWordFileName() {
		return this.wordFileName;
	}

	public void setWordFileName(String wordFileName) {
		this.wordFileName = wordFileName;
	}

	public void resetFileInfo() {
		this.folderPath = null;
		this.pdfFileName = null;
		this.xlsFileName = null;
		this.wordFileName = null;
		this.pdfStatus = ICMSConstant.INT_INVALID_VALUE;
		this.xlsStatus = ICMSConstant.INT_INVALID_VALUE;
		this.wordStatus = ICMSConstant.INT_INVALID_VALUE;
	}

	/**
	 * to build in instrumentation on the report generation profile tracks when
	 * a report gets generated facilitate SQL for retrieving statistics on
	 * report run time
	 */
	Date startTime; // start time of report generation

	Date endTime; // end time of report generation

	public String[] getOrganisations() {
		return this.organisations;
	}

	public void setOrganisations(String[] organisations) {
		this.organisations = organisations;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public String getReportCategoryId() {
		return reportCategoryId;
	}

	public void setReportCategoryId(String reportCategoryId) {
		this.reportCategoryId = reportCategoryId;
	}

	/**
	 * provides the report title with "$" mask for substituion of report headers
	 * @return title - with substitution characters
	 */
	public String getTitleMask() {
		return titleMask;
	}

	public void setTitleMask(String titleMask) {
		this.titleMask = titleMask;
	}

	String[] exportFormats;

	public String[] getExportFormats() {
		return exportFormats;
	}

	public void setExportFormats(String[] exportFormats) {
		this.exportFormats = exportFormats;
	}

	public String getReportMasterId() {
		return reportMasterId;
	}

	public void setReportMasterId(String reportMasterId) {
		this.reportMasterId = reportMasterId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String[] getReportFileNames() {
		return reportFileNames;
	}

	public void setReportFileNames(String[] reportFileNames) {
		this.reportFileNames = reportFileNames;
	}

	public String getReportFileName() {
		if ((reportFileNames != null) && (reportFileNames.length > 0)) {
			return reportFileNames[0];
		}
		return null;
	}


    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    /**
	 * prints report configuration
	 */
	public void print() {
		DefaultLogger.debug(this, "Printing report configuration\n");
		StringBuffer sb = new StringBuffer();
		sb.append("Report Name = " + getReportName());
		sb.append("\n");
		sb.append("Report File Name = " + getReportFileName());
		sb.append("\n");
		sb.append("Report Master Id = " + getReportMasterId());
		sb.append("\n");
		sb.append("Scope = " + getScope());
		sb.append("\n");
		sb.append("Frequency = " + getFrequency());
		sb.append("\n");
		sb.append("Title Mask = " + getTitleMask());
		sb.append("\n");
		sb.append("Max rows = " + getMaxRows());
		sb.append("\n");
		sb.append("Report Category = " + getReportCategoryId());
		DefaultLogger.debug(this, sb.toString());
	}
}
