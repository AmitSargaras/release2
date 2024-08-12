/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgConvenantBean.java,v 1.1 2005/01/24 02:11:27 ckchua Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for checklist item Information
 * 
 * @author $Author: ckchua $
 * @version $Revision: 1.1 $
 * @since $Date: 2005/01/24 02:11:27 $ Tag: $Name: $
 */
public abstract class EBStgConvenantBean extends EBConvenantBean {
	/**
	 * Default Constructor
	 */
	public EBStgConvenantBean() {
	}

	protected EBConvenantSubItemLocalHome getEBConvenantSubItemLocalHome() throws RecurrentException {
		EBConvenantSubItemLocalHome home = (EBConvenantSubItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CONVENANT_SUB_ITEM_LOCAL_JNDI, EBConvenantSubItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBConvenantSubItemLocalHome is null!");
	}
}