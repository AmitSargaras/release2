package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBGroupSubLimitStagingBean extends EBGroupSubLimitBean {


     protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_SUBLIMIT_SEQ_STAGING;
    }


 protected void Debug(String msg) {
	 DefaultLogger.debug(this,"EBGroupSubLimitStagingBean = " + msg);
    }

}
