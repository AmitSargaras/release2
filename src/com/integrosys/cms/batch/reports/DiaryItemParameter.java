package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.HashMap;

public class DiaryItemParameter extends ReportParameter {


    public DiaryItemParameter() {
    }

    public DiaryItemParameter(ParamData data, OBReportConfig reportConfig) {
        super(data, reportConfig, false);
        paramMap.put(ReportConstants.RPT_TEAM_TYPE_MEMBERSHIP_ID,
                new Long(data.getTeamTypeMembershipID()));
        paramMap.put(ReportConstants.RPT_START_EXP_DATE, data.getStartExpDate());
        paramMap.put(ReportConstants.RPT_END_EXP_DATE, data.getEndExpDate());
        paramMap.put(ReportConstants.RPT_LE_ID, new Long(data.getLeID()));
        paramMap.put(ReportConstants.RPT_CUSTOMER_INDEX, data.getCustomerIndex());
        paramMap.put(ReportConstants.RPT_COUNTRY_LIST, data.getCountryList());
    }


}
