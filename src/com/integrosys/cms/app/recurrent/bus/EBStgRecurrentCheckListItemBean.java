/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgRecurrentCheckListItemBean.java,v 1.1 2004/07/01 02:11:21 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for checklist item Information
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/01 02:11:21 $ Tag: $Name: $
 */
public abstract class EBStgRecurrentCheckListItemBean extends EBRecurrentCheckListItemBean {
	/**
	 * Default Constructor
	 */
	public EBStgRecurrentCheckListItemBean() {
	}

	protected EBRecurrentCheckListSubItemLocalHome getEBRecurrentCheckListSubItemLocalHome() throws RecurrentException {
		EBRecurrentCheckListSubItemLocalHome home = (EBRecurrentCheckListSubItemLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_STAGING_RECURRENT_CHECKLIST_SUB_ITEM_LOCAL_JNDI,
						EBRecurrentCheckListSubItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListSubItemLocalHome is null!");
	}
}