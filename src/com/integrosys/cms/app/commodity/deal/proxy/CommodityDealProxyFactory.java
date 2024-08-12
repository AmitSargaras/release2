/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/CommodityDealProxyFactory.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

/**
 * This factory creates ICommodityDealProxy object.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public class CommodityDealProxyFactory {
	/**
	 * Default Constructor
	 */
	public CommodityDealProxyFactory() {
	}

	/**
	 * Creates an ICommodityDealProxy.
	 * 
	 * @return ICommodityDealProxy
	 */
	public static ICommodityDealProxy getProxy() {
		return new CommodityDealProxyImpl();
	}
}