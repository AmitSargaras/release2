package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 8, 2008
 * Time: 4:49:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EBStgDynamicPropertyBean extends EBDynamicPropertyBean {

    /**
     * Get the name of the sequence to be used
     * @return String - the name of the sequence
     */
    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_STAGING_DOC_DYNAMIC_PROP;
    }
    

}
