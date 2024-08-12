/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/MISReportParameter.java,v 1.2 2005/09/16 06:51:43 jtan Exp $
 **/
package com.integrosys.cms.batch.reports;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

/**
 * @author $Author: jtan $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/16 06:51:43 $
 */
public class MISReportParameter extends ReportParameter implements IMISReport {


    /*********************
     * Constructor
     *********************/
    public MISReportParameter() {
    }


    public MISReportParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
//        paramMap.put(ReportConstants.RPT_PARAM_BRANCH_LIST, data.getCentre());
        paramMap.put(ReportConstants.RPT_PARAM_CENTER_CODE, data.getCentre());
        paramMap.put(ReportConstants.RPT_HEADER_PARAM_REPORT_TITLE, formatReportTitle(reportConfig.getTitleMask()));
    }
     private String formatReportTitle(String titleMask) {
        String reportTitle = titleMask.replace('$', ' ').trim();
        return reportTitle + " For " + formatDate(getReportDate());
    }

    /**
	 * Format a Date to String in format dd/MM/yyyy
	 *
	 * @param date a Date
	 * @return formatted Date in String dd/MM/yyyy
	 */
	private static String formatDate(Date date) {
        String str= null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMMM yyyy");
			str = sdf1.format(date) ;
		}catch (Exception ex) {
            ex.printStackTrace();
        }
		return str ;
	}


}
