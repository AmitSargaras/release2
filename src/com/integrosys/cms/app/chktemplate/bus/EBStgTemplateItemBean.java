/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgTemplateItemBean.java,v 1.3 2003/07/11 01:47:34 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.techinfra.beanloader.BeanController;

/**
 * This entity bean represents the persistence for template item Information
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/11 01:47:34 $ Tag: $Name: $
 */
public abstract class EBStgTemplateItemBean extends EBTemplateItemBean {
	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_STAGING_TEMPLATE_ITEM;
	}

    /**
     * Method to get EB Local Home for the item dynamic properties
     */
    protected EBDynamicPropertyLocalHome getEBDynamicPropertyLocalHome() throws CheckListTemplateException {
        EBDynamicPropertyLocalHome home = (EBDynamicPropertyLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_STAGING_DYNAMIC_PROPERTY_LOCAL_JNDI, EBDynamicPropertyLocalHome.class.getName());
        if (home != null) {
            return home;
        }
        throw new CheckListTemplateException("EBStageDynamicPropertyLocalHome is null!");
    }

}