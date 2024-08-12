/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/SYSReportParameter.java,v 1.2 2005/09/16 06:51:43 jtan Exp $
 **/
package com.integrosys.cms.batch.reports;

import java.util.HashMap;

/**
 * Description: This class specifies the parameters used in SYSTEM reports
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/16 06:51:43 $ Tag: $Name: $
 */
public class SYSReportParameter extends ReportParameter {


    public SYSReportParameter() {
    }

    public SYSReportParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
        //paramMap.put(ReportConstants.RPT_PARAM_DATE, getReportDate());  //originally will not check for freq
    }


}
