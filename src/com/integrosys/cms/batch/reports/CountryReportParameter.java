package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.HashMap;

public class CountryReportParameter extends ReportParameter {

    /*********************
     * Constructor
     *********************/
    public CountryReportParameter() {
    }

    public CountryReportParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
        //paramMap.put(ReportConstants.RPT_PARAM_DATE, getReportDate());  //originally will not check for freq
    }



    /*********************
     * Java Method
     *********************/
    public String toString() {
		return super.toString();
	}
}
