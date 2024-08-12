package com.integrosys.cms.batch.reports;

import java.util.HashMap;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/BizReportParameterMap.java,v 1.1 2004/01/15 05:50:20 jtan Exp $
 */

/**
 * Description: Maps the report master ids in CMS_REPORT_MASTER table to the
 * parameter code in BUS_PARAM table This links the user configured parameters
 * to each report master template.
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/15 05:50:20 $ Tag: $Name: $
 */
public class BizReportParameterMap {

	protected static final HashMap reportIdMap;

	protected static final HashMap reportMaxRowInfo;

	static {
		/**
		 * reportIdMap - maps report master template to report generation
		 * frequencies configured by user
		 */
		reportIdMap = new HashMap();
		reportIdMap.put("1", "C5");
		reportIdMap.put("2", "C4");
		reportIdMap.put("3", "C6");
		reportIdMap.put("4", "C7");
		reportIdMap.put("5", "C2");
		reportIdMap.put("6", "C1");
		reportIdMap.put("7", "C3");

		/**
		 * reportMaxRowInfo - maps report master template to report Top N group
		 * settings configured by user
		 */
		reportMaxRowInfo = new HashMap();
		reportMaxRowInfo.put("6", "C1.maxrows");
		reportMaxRowInfo.put("1", "C5.maxrows");
		reportMaxRowInfo.put("3", "C6.maxrows");
	}

	/**
	 * returns the corresponding biz parameter parameter code mapped to report
	 * master id
	 * @param reportMasterId
	 * @return string - business parameter code
	 */
	public static String getReportFreqParamCode(String reportMasterId) {
		return (String) reportIdMap.get(reportMasterId);
	}

	/**
	 * returns the corresponding biz parameter code mapped to report master id
	 * @param reportMasterId
	 * @return string - business parameter code
	 */
	public static String getMaxRowParamCode(String reportMasterId) {
		return (String) reportMaxRowInfo.get(reportMasterId);
	}

}
