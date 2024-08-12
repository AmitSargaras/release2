package com.integrosys.cms.batch.reports;

import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: Report Configuration Parameters for risk profile concentration
 * reports It accepts the parameter
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */
public class RiskProfileReportParameter extends ReportParameter {

	String reportMasterId;

	String reportName;

	String reportType;

	HashMap paramMap;

	String reportDisplayName;

	Date reportDate;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.paramMap.put("param_date", reportDate);
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

	/**
	 * Concentration Risk Profile Report UI Display Name "Report Name"
	 * @return
	 */
	public String getReportDisplayName() {
		return reportDisplayName;
	}

	public void setReportDisplayName(String reportDisplayName) {
		this.reportDisplayName = reportDisplayName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public HashMap getParamMap() {
		return paramMap;
	}

	public void setParamMap(HashMap paramMap) {
		this.paramMap = paramMap;
	}

	public RiskProfileReportParameter(String reportName, String reportDisplayName, String paramString) {
		this.reportName = reportName;
		this.reportDisplayName = reportDisplayName;
		this.paramMap = new HashMap();
		parseParamString(paramString);
	}

	public RiskProfileReportParameter() {
	}

	public void setParameters(String param) {
		if (this.paramMap == null) {
			this.paramMap = new HashMap();
		}
		this.parseParamString(param);
	}

	/**
	 * gets the value referred by key
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return (String) this.paramMap.get(key);
	}

	/**
	 * breaks up parameter string from UI and map it to report parameters
	 * @param str
	 */
	private void parseParamString(String str) {
		final String DELIMITER = ",";
		if (str == null) {
			return;
		}
		StringTokenizer stk = new StringTokenizer(str, DELIMITER);
		while (stk.hasMoreTokens()) {
			this.paramMap.put(stk.nextToken(), stk.nextToken());
		}
		// this.paramMap.put("param_date", DateUtil.getDate());
		this.printMap();
	}

	public void printMap() {
		DefaultLogger.debug(this, " ------- Report Parameters -------");
		DefaultLogger.debug(this, "entries" + this.paramMap.entrySet());
		DefaultLogger.debug(this, "keys" + this.paramMap.keySet());
	}


    public Map getTemplateParameters() {
        return getParamMap();
    }
}
