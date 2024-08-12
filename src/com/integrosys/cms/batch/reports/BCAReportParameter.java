/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/BCAReportParameter.java,v 1.3 2005/11/23 11:48:41 jtan Exp $
 **/

package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.CommonUtil;

import java.util.HashMap;
import java.util.Date;

/**
 * Description: Report parameter that is limit profile specific
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/11/23 11:48:41 $ Tag: $Name: $
 */
public class BCAReportParameter extends MISReportParameter {

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	//private String[] allowedCountries;       //this is not even used!


    /*********************
     * Constructor
     *********************/
    public BCAReportParameter() {
    }

    public BCAReportParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
        setLimitProfileID(data.getLimitProfileID());
        paramMap.put(ReportConstants.RPT_LMT_PROFILE, new Long(getLimitProfileID()));
        
    }


    /*********************
     * Getter & Setter
     *********************/
    /**
	 * input date to report - governs date of generation
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(long limitprofileID) {
		this.limitProfileID = limitprofileID;
	}

//	public String[] getAllowedCountries() {
//		return allowedCountries;
//	}
//
//	public void setAllowedCountries(String[] allowedCountries) {
//		this.allowedCountries = allowedCountries;
//	}

    

    /*********************
     * Standard Method
     *********************/
    public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append("LimitProfileID:  " + getLimitProfileID());
		return sb.toString();
	}


}
