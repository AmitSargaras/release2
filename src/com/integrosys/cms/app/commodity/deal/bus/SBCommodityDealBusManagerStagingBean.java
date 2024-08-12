/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/SBCommodityDealBusManagerStagingBean.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean provides the implementation of the
 * AbstractCommodityDealBusManager, wrapped in an EJB mechanism.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public class SBCommodityDealBusManagerStagingBean extends SBCommodityDealBusManagerBean {
	/**
	 * Default Constructor
	 */
	public SBCommodityDealBusManagerStagingBean() {
	}

	/**
	 * helper method to get local home interface of EBCommodityDealBean.
	 * 
	 * @return commodity deal local home interface
	 * @throws CommodityDealException on errors encountered
	 */
	protected EBCommodityDealLocalHome getEBCommodityDealLocalHome() throws CommodityDealException {
		EBCommodityDealLocalHome ejbHome = (EBCommodityDealLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COMMODITY_DEAL_STAGING_LOCAL_JNDI, EBCommodityDealLocalHome.class.getName());

		if (ejbHome == null) {
			throw new CommodityDealException("EBCommodityDealHome is null!");
		}

		return ejbHome;
	}
}