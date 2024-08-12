package com.integrosys.cms.batch.reports;

import java.util.Date;

import com.integrosys.base.techinfra.util.DateUtil;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: This value object represents a report that is created. It maps
 * directly to the database table CMS_REPORT
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */
public class OBReport implements ICMSReport {

	/**
	 * constants for constructing archival filename
	 */
	public static final String UNDER_SCORE = "_";

	public static final String GLOBAL_SCOPE_CODE = "GG";

	public static final String ALL_COUNTRIES = "All";

	long reportId;

	String reportMasterId;

	String title;

	String fileName;

	String MISReportNumber;

	boolean hasPDF;

	boolean hasXLS;

	boolean hasWord;

	String frequency;

	String folderPath;

	String pdfName;

	String xlsName;

	String wordName;

	String scope;

	Date createdDate;

	byte[] reportPDFBytes;

	byte[] reportXLSBytes;

	byte[] reportDOCBytes;

    String groupID;

	public byte[] getReportPDFBytes() {
		return reportPDFBytes;
	}

	public void setReportPDFBytes(byte[] reportPDFBytes) {
		this.reportPDFBytes = reportPDFBytes;
	}

	public byte[] getReportXLSBytes() {
		return reportXLSBytes;
	}

	public void setReportXLSBytes(byte[] reportXLSBytes) {
		this.reportXLSBytes = reportXLSBytes;
	}

	public byte[] getReportDOCBytes() {
		return reportXLSBytes;
	}

	public void setReportDOCBytes(byte[] reportDOCBytes) {
		this.reportDOCBytes = reportDOCBytes;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getScope() {
		return this.scope;
	}

	public String getFolderPath() {
		return this.folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getPdfName() {
		return this.pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getXlsName() {
		return this.xlsName;
	}

	public void setXlsName(String xlsName) {
		this.xlsName = xlsName;
	}

	public String getWordName() {
		return this.wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public String getMISReportNumber() {
		return MISReportNumber;
	}

	public void setMISReportNumber(String MISReportNumber) {
		this.MISReportNumber = MISReportNumber;
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getReportMasterId() {
		return reportMasterId;
	}

	public void setReportMasterId(String reportMasterId) {
		this.reportMasterId = reportMasterId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getHasPDF() {
		return hasPDF;
	}

	public void setHasPDF(boolean hasPDF) {
		this.hasPDF = hasPDF;
	}

	public boolean getHasXLS() {
		return hasXLS;
	}

	public void setHasXLS(boolean hasXLS) {
		this.hasXLS = hasXLS;
	}

	public boolean getHasWord() {
		return hasWord;
	}

	public void setHasWord(boolean hasWord) {
		this.hasWord = hasWord;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String toString() {
		return "\nrptId = " + reportId + "\ntitle = " + title + "\nfilename = " + fileName + "\nfolderPath = "
				+ folderPath + "\npdfName = " + pdfName + "\nxlsName = " + xlsName + "\nwordName = " + wordName;
	}

	/**
	 * constructs the physical filename for saving as a report the filename will
	 * be in the following format
	 * YYYYMMDD_MISReportCode_CountryCode_ReportTemplateName
	 * 
	 * Global reports will have the countryCode as "All" instead
	 * 
	 * @return filename
	 */
	public String getArchivalFileName() {

		StringBuffer sb = new StringBuffer();

		sb.append(DateUtil.formatTimeCCYYMMDD(getCreatedDate()));

		if (MISReportNumber != null) {
			sb.append(UNDER_SCORE);
			sb.append(MISReportNumber);
		}

		if (scope != null) {
			sb.append(UNDER_SCORE);

			if (scope.equalsIgnoreCase(GLOBAL_SCOPE_CODE)) {
				sb.append(ALL_COUNTRIES);
			}
			else {
				sb.append(scope);
			}

		}

		sb.append(UNDER_SCORE);
		sb.append(fileName);

		return sb.toString();

	}

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}