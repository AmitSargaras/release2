package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBGroupCreditGradeStagingBean extends EBGroupCreditGradeBean {




     protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_CREDIT_GRADE_STAGING;
    }


}
