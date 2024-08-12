/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBStgDeferralRequestBean.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
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

public abstract class EBStgDeferralRequestBean extends EBDeferralRequestBean {
	/**
	 * Default Constructor
	 */
	public EBStgDeferralRequestBean() {
	}

	/**
	 * Method to get EB Local Home for scc item
	 */
	protected EBDeferralRequestItemLocalHome getEBDeferralRequestItemLocalHome() throws GenerateRequestException {
		EBDeferralRequestItemLocalHome home = (EBDeferralRequestItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_DEFERRAL_REQUEST_ITEM_LOCAL_JNDI, EBDeferralRequestItemLocalHome.class
						.getName());
		if (home != null) {
			return home;
		}
		throw new GenerateRequestException("EBDeferralRequestItemLocalHome is null!");
	}

}