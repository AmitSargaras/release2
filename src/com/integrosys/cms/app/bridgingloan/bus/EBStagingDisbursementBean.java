package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 29, 2007 Time: 10:17:09 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EBStagingDisbursementBean extends EBDisbursementBean {
	/**
	 * Method to get EB Local Home for the Development Document
	 */
	protected EBDisbursementDetailLocalHome getEBDisbursementDetailLocalHome() throws BridgingLoanException {
		EBDisbursementDetailLocalHome home = (EBDisbursementDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_BL_DISBURSE_DETAIL_LOCAL_JNDI, EBDisbursementDetailLocalHome.class
						.getName());

		if (home != null) {
			return home;
		}
		throw new BridgingLoanException("EBDisbursementDetailLocalHome is null!");
	}
}