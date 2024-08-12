package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 27, 2007 Time: 3:21:22 PM To
 * change this template use File | Settings | File Templates.
 */
public abstract class EBStagingProjectScheduleBean extends EBProjectScheduleBean {
	/**
	 * Method to get EB Local Home for the Development Document
	 */
	protected EBDevDocLocalHome getEBDevDocLocalHome() throws BridgingLoanException {
		EBDevDocLocalHome home = (EBDevDocLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_DEV_DOC_LOCAL_JNDI, EBDevDocLocalHome.class.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBDevDocLocalHome is null!");
	}
}