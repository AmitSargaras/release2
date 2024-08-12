/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBStgDDNBean.java,v 1.1 2003/08/20 04:14:23 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the ddn entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 04:14:23 $ Tag: $Name: $
 */

public abstract class EBStgDDNBean extends EBDDNBean {
	/**
	 * Method to get EB Local Home for ddn item
	 */
	protected EBDDNItemLocalHome getEBDDNItemLocalHome() throws DDNException {
		EBDDNItemLocalHome home = (EBDDNItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_DDN_ITEM_LOCAL_JNDI, EBDDNItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new DDNException("EBDDNItemLocalHome is null!");
	}

}