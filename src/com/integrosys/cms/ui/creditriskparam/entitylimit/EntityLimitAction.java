/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.IPin;

import java.util.Map;

/**
 * Abstract Class meant to be extended. 
 * Used for persisting session when action path change from one to other.
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public abstract class EntityLimitAction extends  CommonAction implements IPin{

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

}
