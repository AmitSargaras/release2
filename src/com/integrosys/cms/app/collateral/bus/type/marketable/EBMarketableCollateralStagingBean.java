/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/EBMarketableCollateralStagingBean.java,v 1.1 2003/07/31 11:15:58 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Staging entity bean implementation for marketable collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/31 11:15:58 $ Tag: $Name: $
 */
public abstract class EBMarketableCollateralStagingBean extends EBMarketableCollateralBean {
	/**
	 * Get marketable equity local home
	 * 
	 * @return EBMarketableEquityLocalHome
	 */
	protected EBMarketableEquityLocalHome getEBMarketableEquityLocalHome() {
		EBMarketableEquityLocalHome ejbHome = (EBMarketableEquityLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MARKETABLE_EQUITY_STAGING_LOCAL_JNDI, EBMarketableEquityLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBMarketableEquityLocalHome for Staging is Null!");
		}

		return ejbHome;
	}
}