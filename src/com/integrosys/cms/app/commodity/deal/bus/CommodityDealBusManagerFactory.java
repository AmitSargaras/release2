/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CommodityDealBusManagerFactory.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

/**
 * This factory creates ICommodityDealBusManager.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public class CommodityDealBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public CommodityDealBusManagerFactory() {
	}

	/**
	 * Creates the actual deal business manager.
	 * 
	 * @return commodity deal business manager
	 */
	public static ICommodityDealBusManager getActualCommodityDealBusManager() {
		return new CommodityDealBusManagerImpl();
	}

	/**
	 * Creates the staging deal business manager.
	 * 
	 * @return commodity deal business manager
	 */
	public static ICommodityDealBusManager getStagingCommodityDealBusManager() {
		return new CommodityDealBusManagerStagingImpl();
	}
}