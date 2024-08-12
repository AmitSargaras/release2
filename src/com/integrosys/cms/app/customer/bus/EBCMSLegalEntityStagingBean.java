/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntityStagingBean.java,v 1.1 2003/07/02 04:17:46 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for Legal Entity details
 * (staging)
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/02 04:17:46 $ Tag: $Name: $
 */
public abstract class EBCMSLegalEntityStagingBean extends EBCMSLegalEntityBean {
	/**
	 * Default Constructor
	 */
	public EBCMSLegalEntityStagingBean() {
		super();
	}

	// ************************ BeanController Methods **************
	/**
	 * Method to get EB Local Home for Registered Address
	 */
	protected EBContactLocalHome getEBLocalHomeRegAddress() throws CustomerException {
		EBContactLocalHome home = (EBContactLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_REG_ADDRESS_LOCAL_JNDI_STAGING, EBContactLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBContactLocalHome is null!");
		}
	}

	protected EBSystemLocalHome getEBLocalHomeOtherSystem() throws CustomerException {
		EBSystemLocalHome home = (EBSystemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SYSTEM_LOCAL_JNDI_STAGING, EBSystemLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBSystemLocalHome is null!");
		}
	}
	
	protected EBDirectorLocalHome getEBLocalHomeDirector() throws CustomerException {
		EBDirectorLocalHome home = (EBDirectorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SYSTEM_DIRECTOR_JNDI_STAGING, EBDirectorLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBDirectorLocalHome is null!");
		}
	}

	protected EBVendorDetailsLocalHome getEBLocalHomeVendor() throws CustomerException {
		EBVendorDetailsLocalHome home = (EBVendorDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_VENDOR_JNDI_STAGING, EBVendorDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBVendorDetailsLocalHome is null!");
		}
	}
	
	protected EBSublineLocalHome getEBLocalHomeSubline() throws CustomerException {
		EBSublineLocalHome home = (EBSublineLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SUBLINE_LOCAL_JNDI_STAGING, EBSublineLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBSublineLocalHome is null!");
		}
	}
	
	
	protected EBBankingMethodLocalHome getEBLocalHomeBankingMethod() throws CustomerException {
		EBBankingMethodLocalHome home = (EBBankingMethodLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BANKING_LOCAL_JNDI_STAGING, EBBankingMethodLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBBankingMethodLocalHome is null!");
		}
	}
	/**
	 * Method to get EB Local Home for Credit Grade
	 */
	protected EBCreditGradeLocalHome getEBLocalHomeCreditGrade() throws CustomerException {
		EBCreditGradeLocalHome home = (EBCreditGradeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CREDIT_GRADE_LOCAL_JNDI_STAGING, EBCreditGradeLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCreditGradeLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for ISIC Code
	 */
	protected EBISICCodeLocalHome getEBLocalHomeISICCode() throws CustomerException {
		EBISICCodeLocalHome home = (EBISICCodeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_ISIC_CODE_LOCAL_JNDI_STAGING, EBISICCodeLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBISICCodeLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for ISIC Code
	 */
	protected EBCreditStatusLocalHome getEBLocalHomeCreditStatus() throws CustomerException {
		EBCreditStatusLocalHome home = (EBCreditStatusLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CREDIT_STATUS_LOCAL_JNDI_STAGING, EBCreditStatusLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCreditStatusLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for KYC
	 */
	protected EBKYCLocalHome getEBLocalHomeKYC() throws CustomerException {
		EBKYCLocalHome home = (EBKYCLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_KYC_LOCAL_JNDI_STAGING, EBKYCLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBKYCLocalHome is null!");
		}
	}
	
	//A Shiv 260811
	protected EBCriInfoLocalHome getEBLocalHomeCriInfo() throws CustomerException {
		EBCriInfoLocalHome home = (EBCriInfoLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CRI_LOCAL_JNDI_STAGING, EBCriInfoLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCriInfoLocalHome is null!");
		}
	}
	
	//A Shiv 290811
	protected EBCriFacLocalHome getEBLocalHomeCriFac() throws CustomerException {
		EBCriFacLocalHome home = (EBCriFacLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CRI_FAC_LOCAL_JNDI_STAGING, EBCriFacLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCriFacLocalHome is null!");
		}
	}
	
	protected EBCMSCustomerUdfLocalHome getEBLocalHomeUdfInfo() throws CustomerException {
		EBCMSCustomerUdfLocalHome home = (EBCMSCustomerUdfLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_CMS_CUSTOMER_UDF_LOCAL_JNDI_STAGING, EBCMSCustomerUdfLocalHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new CustomerException("EBCMSCustomerUdfLocalHome is null!");
		}
	}
	
	protected EBCoBorrowerDetailsStagingLocalHome getEBCoBorrowerDetailsLocalHome() throws CustomerException {
		EBCoBorrowerDetailsStagingLocalHome home = (EBCoBorrowerDetailsStagingLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CO_BORROWER_DETAILS_STAGING_JNDI, EBCoBorrowerDetailsStagingLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCoBorrowerDetailsStagingLocalHome is null!");
		}
	}
}