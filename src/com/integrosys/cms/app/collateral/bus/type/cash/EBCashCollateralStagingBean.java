/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/EBCashCollateralStagingBean.java,v 1.1 2003/07/24 08:18:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging cash collateral type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/24 08:18:05 $ Tag: $Name: $
 */
public abstract class EBCashCollateralStagingBean extends EBCashCollateralBean {
	/**
	 * Get cash deposit local home
	 * 
	 * @return EBCashDepositLocalHome
	 */
	protected EBCashDepositLocalHome getEBCashDepositLocalHome() {
		EBCashDepositLocalHome ejbHome = (EBCashDepositLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CASH_DEPOSIT_STAGING_LOCAL_JNDI, EBCashDepositLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCashDepositLocalHome for staging is Null!");
		}

		return ejbHome;
	}
}