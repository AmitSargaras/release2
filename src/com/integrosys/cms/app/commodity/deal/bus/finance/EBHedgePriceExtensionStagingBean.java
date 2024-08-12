/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBHedgePriceExtensionStagingBean.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging Deal Hedge Price Extension details.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public abstract class EBHedgePriceExtensionStagingBean extends EBHedgePriceExtensionBean {
	/**
	 * Get staging hedge price extension local home.
	 * 
	 * @return EBHedgePriceExtensionLocalHome
	 */
	protected EBHedgePriceExtensionLocalHome getLocalHome() {
		EBHedgePriceExtensionLocalHome ejbHome = (EBHedgePriceExtensionLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_HEDGE_PRICE_EXTENSION_STAGING_LOCAL_JNDI, EBHedgePriceExtensionLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBHedgePriceExtensionStagingLocalHome is Null!");
		}

		return ejbHome;
	}
}