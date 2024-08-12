/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeStagingBean.java,v 1.4 2004/06/04 03:39:53 hltan Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging limit charge entity.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/06/04 03:39:53 $ Tag: $Name: $
 */
public abstract class EBLimitChargeStagingBean extends EBLimitChargeBean {
	/**
	 * Get staging for limit charge map local home.
	 * 
	 * @return EBLimitChargeMapLocalHome
	 */
	protected EBLimitChargeMapLocalHome getEBLimitChargeMapLocalHome() {
		EBLimitChargeMapLocalHome ejbHome = (EBLimitChargeMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_CHARGE_MAP_STAGING_LOCAL_JNDI, EBLimitChargeMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLimitChargeMapLocalHome for staging is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get collateral limit map local home.
	 * 
	 * @return EBCollateralLimitMapLocalHome
	 */
	protected EBCollateralLimitMapLocalHome getEBCollateralLimitMapLocalHome() {
		EBCollateralLimitMapLocalHome ejbHome = (EBCollateralLimitMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMIT_MAP_STAGING_LOCAL_JNDI, EBCollateralLimitMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralLimitMapLocalHome for staging is Null!");
		}

		return ejbHome;
	}
}