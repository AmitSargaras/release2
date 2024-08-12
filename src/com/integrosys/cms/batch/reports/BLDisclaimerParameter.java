package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.HashMap;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Jun 1, 2007 Tag: $Name$
 */
public class BLDisclaimerParameter extends MISReportParameter { // to extend
																// from
																// ReportParameter
																// or
																// MISReportParameter
																// ??

    /*********************
     * Constructor
     *********************/
    public BLDisclaimerParameter() {
    }

    public BLDisclaimerParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig);
        paramMap.put(ReportConstants.RPT_PARAM_BL_BUILDUP_ID, new Long(data.getBuildupID()));
    }


}
