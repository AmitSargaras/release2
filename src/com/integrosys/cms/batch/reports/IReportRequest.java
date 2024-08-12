package com.integrosys.cms.batch.reports;

import java.io.Serializable;
import java.util.Date;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: report request
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public interface IReportRequest extends Serializable {

	/**
	 * report parameters including numerator, denominator & other params.
	 * protocol as pipe separated
	 * @return String
	 */
	public String getParameters();

	public Date getReportGenTime();

	/**
	 * report id. used to determine the type of report to be generated
	 * @return
	 */
	public long getReportID();

	public String getReportName();

	/**
	 * get report req id. pk
	 * @return long
	 */
	public long getReportRequestID();

	public Date getRequestTime();

	/**
	 * Status of the requested report. possible values : 'REQUESTED',
	 * 'IN-PROGRESS', 'GENERATED', 'FAILED'
	 * @return String
	 */
	public String getStatus();

	/**
	 * user id of the requestor of the report
	 * @return long
	 */
	public long getUserID();

	public void setParameters(String parameters);

	public void setReportGenTime(Date reportGenTime);

	/**
	 * Unique report ID for each type of Report.
	 * @param reportID the report Id
	 */
	public void setReportID(long reportID);

	public void setReportName(String reportName);

	public void setReportRequestID(long reportRequestID);

	public void setRequestTime(Date requestTime);

	public void setStatus(String status);

	public void setUserID(long userID);

}
