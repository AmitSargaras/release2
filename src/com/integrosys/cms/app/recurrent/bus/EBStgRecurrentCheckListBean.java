/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgRecurrentCheckListBean.java,v 1.2 2003/07/29 06:38:30 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the checklist entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/29 06:38:30 $ Tag: $Name: $
 */

public abstract class EBStgRecurrentCheckListBean extends EBRecurrentCheckListBean {

	/**
	 * Default Constructor
	 */
	public EBStgRecurrentCheckListBean() {
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBRecurrentCheckListItemLocalHome getEBRecurrentCheckListItemLocalHome() throws RecurrentException {
		EBRecurrentCheckListItemLocalHome home = (EBRecurrentCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_RECURRENT_CHECKLIST_ITEM_LOCAL_JNDI,
				EBRecurrentCheckListItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListItemLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for convenant
	 */
	protected EBConvenantLocalHome getEBConvenantLocalHome() throws RecurrentException {
		EBConvenantLocalHome home = (EBConvenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CONVENANT_LOCAL_JNDI, EBConvenantLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBConvenantLocalHome is null!");
	}

}