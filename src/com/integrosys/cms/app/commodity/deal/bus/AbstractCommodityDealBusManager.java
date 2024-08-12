/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/AbstractCommodityDealBusManager.java,v 1.4 2004/07/19 06:26:46 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

/**
 * This abstract class provides helper services to subclasses that extends from
 * it. It provides implementation of ICommodityDealBusManager services such that
 * the implementation is non-mechanism specific.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/19 06:26:46 $ Tag: $Name: $
 */
public abstract class AbstractCommodityDealBusManager implements ICommodityDealBusManager {
	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CommodityDealException on errors encountered
	 */
	protected abstract void rollback() throws CommodityDealException;

}