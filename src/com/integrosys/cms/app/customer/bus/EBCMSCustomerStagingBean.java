/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSCustomerStagingBean.java,v 1.3 2003/06/26 08:54:15 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for Customer details (staging)
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/06/26 08:54:15 $ Tag: $Name: $
 */
public abstract class EBCMSCustomerStagingBean extends EBCMSCustomerBean {
	/**
	 * Default Constructor
	 */
	public EBCMSCustomerStagingBean() {
		super();
	}

	// ************************ BeanController Methods **************

	/**
	 * Method to get EB Local Home for CMSLegalEntity
	 * 
	 * @return EBCMSLegalEntityLocalHome
	 * @throws CustomerException on errors
	 */
	protected EBCMSLegalEntityLocalHome getEBLocalHomeLegalEntity() throws CustomerException {
		EBCMSLegalEntityLocalHome home = (EBCMSLegalEntityLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LEGAL_ENTITY_LOCAL_JNDI_STAGING, EBCMSLegalEntityLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCMSLegalEntityLocal is null!");
		}
	}

	/**
	 * Method to get the EBLocalHome for Official Address
	 * 
	 * @return EBContactLocalHome
	 * @throws CustomerException on errors
	 */
	protected EBContactLocalHome getEBLocalHomeOfficialAddress() throws CustomerException {
		EBContactLocalHome home = (EBContactLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_OFF_ADDRESS_LOCAL_JNDI_STAGING, EBContactLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBContactLocalHome is null!");
		}
	}

	/**
	 * Method to get the EBLocalHome for Customer Sys Ref
	 * 
	 * @return EBCustomerSysXRefLocalHome
	 * @throws CustomerException on errors
	 */
	protected EBCustomerSysXRefLocalHome getEBLocalHomeSysRef() throws CustomerException {
		EBCustomerSysXRefLocalHome home = (EBCustomerSysXRefLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CUSTOMER_SYS_REF_LOCAL_JNDI_STAGING, EBCustomerSysXRefLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCustomerSysXRefLocalHome is null!");
		}
	}
	
	protected EBLineCovenantLocalHome getEBLocalHomeLineCovenant() throws CustomerException {
		EBLineCovenantLocalHome home = (EBLineCovenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LINE_COVENANT_LOCAL_JNDI_STAGING, EBLineCovenantLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBLineCovenantLocalHome is null!");
		}
	}
}