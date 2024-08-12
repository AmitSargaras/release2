package com.integrosys.cms.app.contractfinancing.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: Tan Kien Leong Date: May 4, 2007 Time:
 * 6:37:19 PM To change this template use File | Settings | File Templates.
 */
public abstract class EBStagingAdvanceBean extends EBAdvanceBean {

	// ==============================
	// Locating EB Methods
	// ==============================

	/**
	 * Method to get EB Local Home for the Staging Payment EB
	 * 
	 * @return home
	 * @throws ContractFinancingException on error
	 */
	protected EBPaymentLocalHome getEBPaymentLocalHome() throws ContractFinancingException {
		EBPaymentLocalHome home = (EBPaymentLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_CF_PAYMENT_LOCAL_JNDI, EBPaymentLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBStagingPaymentLocalHome is null!");
	}

}
