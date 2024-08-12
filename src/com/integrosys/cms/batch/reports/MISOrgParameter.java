/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/MISOrgParameter.java,v 1.1 2005/09/16 06:51:43 jtan Exp $
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
public class MISOrgParameter extends ReportParameter implements IMISReport {

	/**
	 * organisation code in reports
	 */
	private String organisation;

    /*********************
     * Constructor
     *********************/
    public MISOrgParameter() {
	}


    public MISOrgParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
        setOrganisation(data.getOrganisation());
        paramMap.put(ReportConstants.RPT_PARAM_ORG, getOrganisation());
    }

    
    /*********************
     * Class variables
     *********************/
	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}



    /*********************
     * Standard Method
     *********************/
    public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append("Organisation:  " + getOrganisation());
		return sb.toString();
	}

}
