/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBStgWaiverRequestBean.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the scc entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */

public abstract class EBStgWaiverRequestBean extends EBWaiverRequestBean {
	/**
	 * Default Constructor
	 */
	public EBStgWaiverRequestBean() {
	}

	/**
	 * Method to get EB Local Home for scc item
	 */
	protected EBWaiverRequestItemLocalHome getEBWaiverRequestItemLocalHome() throws GenerateRequestException {
		EBWaiverRequestItemLocalHome home = (EBWaiverRequestItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_WAIVER_REQUEST_ITEM_LOCAL_JNDI, EBWaiverRequestItemLocalHome.class
						.getName());
		if (home != null) {
			return home;
		}
		throw new GenerateRequestException("EBWaiverRequestItemLocalHome is null!");
	}

}