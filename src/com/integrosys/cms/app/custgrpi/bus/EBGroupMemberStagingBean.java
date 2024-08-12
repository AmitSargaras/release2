package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBGroupMemberStagingBean extends EBGroupMemberBean {


     protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_GROUP_MEMBER_SEQ_STAGING;
    }




}
