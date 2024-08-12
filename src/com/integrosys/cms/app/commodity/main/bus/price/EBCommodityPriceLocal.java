/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/EBCommodityPriceLocal.java,v 1.2 2004/06/04 04:52:52 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Entity bean remote interface to EBCommodityPriceBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:52:52 $ Tag: $Name: $
 */
public interface EBCommodityPriceLocal extends EJBLocalObject {
	/**
	 * Get the commodity price business object.
	 * 
	 * @return commodity price
	 * @throws CommodityException on error getting the value
	 */
	public ICommodityPrice getValue() throws CommodityException;

	/**
	 * Persist the commodity price.
	 * 
	 * @param price is of type ICommodityPrice
	 * @throws VersionMismatchException if the commodity price version is
	 *         different from backend
	 */
	public void setValue(ICommodityPrice price) throws VersionMismatchException;
}