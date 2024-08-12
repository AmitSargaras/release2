/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBCommodityCollateralStagingBean.java,v 1.4 2005/07/15 06:24:03 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Staging entity bean implementation for commodity collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/15 06:24:03 $ Tag: $Name: $
 */
public abstract class EBCommodityCollateralStagingBean extends EBCommodityCollateralBean {
	protected EBContractLocalHome getEBContractLocalHome() {
		EBContractLocalHome ejbHome = (EBContractLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CONTRACT_STAGING_LOCAL_JNDI, EBContractLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBContractLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBApprovedCommodityTypeLocalHome getEBApprovedCommodityTypeLocalHome() {
		EBApprovedCommodityTypeLocalHome ejbHome = (EBApprovedCommodityTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_APPROVED_COMMODITY_TYPE_STAGING_LOCAL_JNDI, EBApprovedCommodityTypeLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBApprovedCommodityTypeLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBHedgingContractInfoLocalHome getEBHedgingContractInfoLocalHome() {
		EBHedgingContractInfoLocalHome ejbHome = (EBHedgingContractInfoLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_HEDGING_CONTRACT_INFO_STAGING_LOCAL_JNDI, EBHedgingContractInfoLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBHedgingContractInfoLocalHome is Null!");
		}

		return ejbHome;
	}

	protected EBLoanAgencyLocalHome getEBLoanAgencyLocalHome() {
		EBLoanAgencyLocalHome ejbHome = (EBLoanAgencyLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LOAN_AGENCY_STAGING_LOCAL_JNDI, EBLoanAgencyLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLoanAgencyLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get local home of precondition.
	 * 
	 * @return EBPreConditionLocalHome
	 */
	protected EBPreConditionLocalHome getEBPreConditionLocalHome() {
		EBPreConditionLocalHome ejbHome = (EBPreConditionLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PRECONDITION_STAGING_LOCAL_JNDI, EBPreConditionLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("Staging EBPreConditionLocalHome is Null!");
		}

		return ejbHome;
	}
}