package com.integrosys.cms.batch.reports;

import java.util.Date;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: Contains the meta information of report types
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public class OBReportRequest implements IReportRequest {

	private static final long serialVersionUID = -1091680902694278124L;

	private long reportRequestID;

	private long reportID;

	private String reportName;

	private String parameters;

	private long userID;

	private Date requestTime;

	private Date reportGenTime;

	private String status;

	public long getReportRequestID() {
		return reportRequestID;
	}

	public void setReportRequestID(long reportRequestID) {
		this.reportRequestID = reportRequestID;
	}

	public long getReportID() {
		return reportID;
	}

	public void setReportID(long reportID) {
		this.reportID = reportID;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getReportGenTime() {
		return reportGenTime;
	}

	public void setReportGenTime(Date reportGenTime) {
		this.reportGenTime = reportGenTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Report Request@ ");
		buf.append("MIS Report ID [").append(reportID).append("], ");
		buf.append("Report Name [").append(reportName).append("], ");
		buf.append("Parameter [").append(parameters).append("], ");
		buf.append("Status [").append(status).append("], ");
		buf.append("Generated Time [").append(reportGenTime).append("]");

		return buf.toString();
	}

}
