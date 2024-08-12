/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ReportParameter.java,v 1.3 2006/09/05 05:39:42 hshii Exp $
 **/

package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: Parameters used by reports
 * This is the main class that holds the parameters for generating the different reports.
 * Each different class/scope/category of report shall extend from this class when the data required is different.
 * This class works with ParamData in that ParamData is the class the holds all the possible input from
 * all the different entry points (e.g. batch generation, online generation etc)
 * and ReportParameter (subclass - concrete class) will be the one to determine and know what are the
 * required parameters that it needed for its report generation.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/09/05 05:39:42 $ Tag: $Name: $
 */
public abstract class ReportParameter {

    private static final String CONFIG_BANK_KEY = "reportBankName";
    private static final String CONFIG_SYS_KEY = "reportSystemName";

    //ParamData data;
    protected Map paramMap = new HashMap();

	private String frequency;
    private String country;
    private Date reportDate;            //input date to report - governs date of generation


    /*********************
     * Constructor
     *********************/
    protected ReportParameter() {
    }


    public ReportParameter(ParamData data, OBReportConfig reportConfig) {
        //this.data = data;
        this(data, reportConfig, true);
        setFrequency(reportConfig.getFrequency());
        setCountry(data.getCountry());
        setReportDate(data.getReportDate());
    }


    public ReportParameter(ParamData data, OBReportConfig reportConfig, boolean createParamMap) {
        if(createParamMap) {
            //parameters for the report data
            paramMap.put(ReportConstants.RPT_PARAM_DATE, ((isMonthlyReport()) ?
                            CalendarHelper.getPreviousDate(data.getReportDate()) : data.getReportDate()));
            paramMap.put(ReportConstants.RPT_PARAM_COUNTRY, data.getCountry());

            //parameters for the report header
            paramMap.put(ReportConstants.RPT_HEADER_PARAM_BANK_NAME, PropertyManager.getValue(CONFIG_BANK_KEY));
            paramMap.put(ReportConstants.RPT_HEADER_PARAM_FREQ, reportConfig.getFrequency());
            paramMap.put(ReportConstants.RPT_HEADER_PARAM_RECIPIENT, reportConfig.getRecipients());
            paramMap.put(ReportConstants.RPT_HEADER_PARAM_REPORT_NUMBER, reportConfig.getReportNumber());
            paramMap.put(ReportConstants.RPT_HEADER_PARAM_REPORT_TITLE, formatReportTitle(reportConfig.getTitleMask(),data));
            paramMap.put(ReportConstants.RPT_HEADER_PARAM_SYS_NAME, PropertyManager.getValue(CONFIG_SYS_KEY));
        }
    }


    /*********************
     * Getter & Setter
     *********************/

    public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setCountry(String country) {
		this.country = country;         //country code - used as BCA Booking Location or Security Location
	}

	public String getCountry() {
		return this.country;
	}

    public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

    public Date getReportDate() {
		return this.reportDate;
	}
//
//	public ParamData getData() {
//		return data;
//	}
//
//	public void setData(ParamData data) {
//		this.data = data;
//	}


    /*********************
     * Main Method
     *********************/
    public Map getTemplateParameters() {
        return paramMap;
    }


    /*********************
     * Helper Method
     *********************/
	public boolean isMonthlyReport() {
		return ReportConstants.MONTHLY.equalsIgnoreCase(this.frequency);
	}


    private String formatReportTitle(String titleMask,ParamData data) {
        String reportTitle = titleMask.replace('$', ' ').trim();
        return reportTitle + " For " + data.getReportDate();
    }
    

    /*********************
     * Standard Method
     *********************/
    public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n##### Printing Report Parameter #####\n\n");
		sb.append("\nCountry: " + getCountry());
        sb.append("\nReportDate:  " + getReportDate());
        return sb.toString();
	}




}
