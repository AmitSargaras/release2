/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/SBCustomerStagingBean.java,v 1.1 2003/07/01 06:08:16 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean provides the implementation of the AbstractCAManager,
 * wrapped in an EJB mechanism.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/01 06:08:16 $ Tag: $Name: $
 */
public class SBCustomerStagingBean extends SBCustomerManagerBean {
	/**
	 * Default Constructor
	 */
	public SBCustomerStagingBean() {
	}

	// ************* Private Methods **************
	/**
	 * Method to get the EBHome for Customer
	 * 
	 * @return EBCMSCustomerHome
	 * @throws CustomerException on error
	 */
	protected EBCMSCustomerHome getEBHomeCustomer() throws CustomerException {
		EBCMSCustomerHome home = (EBCMSCustomerHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTOMER_JNDI_STAGING, EBCMSCustomerHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCMSCustomerHome is null!");
		}
	}
}