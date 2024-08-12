/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ConcentrationReportParameter.java,v 1.1 2005/09/16 06:51:43 jtan Exp $
 **/
package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.HashMap;

/**
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/16 06:51:43 $ Tag: $Name: $
 */
public class ConcentrationReportParameter extends ReportParameter {

    /*********************
     * Constructor
     *********************/
    public ConcentrationReportParameter() {
    }

    public ConcentrationReportParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
    }


    /*********************
     * Java Method
     *********************/
    public String toString() {
		return super.toString();
	}
}
