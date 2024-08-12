/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Remote interface to EBLiquidationBean.
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBLiquidationStagingBean extends EBLiquidationBean {

	/**
	 * Getting the Recovery Local interface
	 * @return
	 * @throws LiquidationException
	 */
	protected EBRecoveryLocalHome getEBRecoveryLocalHome() throws LiquidationException {

		EBRecoveryLocalHome ejbHome = (EBRecoveryLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECOVERY_STAGING_LOCAL_JNDI, EBRecoveryLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryLocalHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Getting the RecoveryExpense Local interface
	 * @return
	 * @throws LiquidationException
	 */
	protected EBRecoveryExpenseLocalHome getEBRecoveryExpenseLocalHome() throws LiquidationException {

		EBRecoveryExpenseLocalHome ejbHome = (EBRecoveryExpenseLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECOVERY_EXPENSE_STAGING_LOCAL_JNDI, EBRecoveryExpenseLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryExpenseLocalHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Getting the NPLInfo Local interface
	 * @return
	 * @throws LiquidationException
	 */
	protected EBNPLInfoLocalHome getEBNPLInfoLocalHome() throws LiquidationException {

		EBNPLInfoLocalHome ejbHome = (EBNPLInfoLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_NPL_INFO_STAGING_LOCAL_JNDI, EBNPLInfoLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBNPLInfoLocalHome is null!");
		}

		return ejbHome;
	}
}
