/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeMapStagingBean.java,v 1.1 2003/11/04 11:18:13 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for limit charge map entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/11/04 11:18:13 $ Tag: $Name: $
 */
public abstract class EBLimitChargeMapStagingBean extends EBLimitChargeMapBean {
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