/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/SBStagingGenerateRequestBusManagerBean.java,v 1.3 2003/09/14 10:58:49 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic for the staging table.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/14 10:58:49 $ Tag: $Name: $
 */
public class SBStagingGenerateRequestBusManagerBean extends SBGenerateRequestBusManagerBean {

	/**
	 * To get the home handler for the staging waiver request Entity Bean
	 * @return EBWaiverRequestHome - the home handler for the waiver request
	 *         entity bean
	 */
	protected EBWaiverRequestHome getEBWaiverRequestHome() {
		EBWaiverRequestHome ejbHome = (EBWaiverRequestHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_WAIVER_REQUEST_JNDI, EBWaiverRequestHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the deferral Request Entity Bean
	 * @return EBDeferralRequestHome - the home handler for the CC Certificate
	 *         entity bean
	 */
	protected EBDeferralRequestHome getEBDeferralRequestHome() {
		EBDeferralRequestHome ejbHome = (EBDeferralRequestHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_STAGING_DEFERRAL_REQUEST_JNDI, EBDeferralRequestHome.class.getName());
		return ejbHome;
	}
}
