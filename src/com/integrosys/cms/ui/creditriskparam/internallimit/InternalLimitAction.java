/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/ForexAction.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.IPin;

import java.util.Map;

public abstract class InternalLimitAction extends CommonAction implements IPin {

	public static final String FAIL_VALIDATE = "fail_validate";
    
    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }
}
