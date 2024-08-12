/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CommodityDealBusManagerStagingImpl.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ICommodityDealBusManager implementation by delegating
 * the handling of requests to an ejb session bean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public class CommodityDealBusManagerStagingImpl extends CommodityDealBusManagerImpl {
	/**
	 * Default constructor.
	 */
	public CommodityDealBusManagerStagingImpl() {
		super();
	}

	/**
	 * helper method to get an ejb object to deal business manager session bean.
	 * 
	 * @return commodity deal manager ejb object
	 * @throws CommodityDealException on errors encountered
	 */
	protected SBCommodityDealBusManager getBusManager() throws CommodityDealException {
		SBCommodityDealBusManager theEjb = (SBCommodityDealBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMODITY_DEAL_MGR_STAGING_JNDI, SBCommodityDealBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new CommodityDealException("SBCommodityDealBusManager for Staging is null!");
		}

		return theEjb;
	}
}