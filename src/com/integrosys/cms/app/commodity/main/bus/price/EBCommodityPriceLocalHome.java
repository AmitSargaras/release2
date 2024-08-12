/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/EBCommodityPriceLocalHome.java,v 1.3 2004/08/06 11:50:00 lyng Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines commodity price create and finder methods for local clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/06 11:50:00 $ Tag: $Name: $
 */
public interface EBCommodityPriceLocalHome extends EJBLocalHome {
	/**
	 * Create commodity price record.
	 * 
	 * @param price of type ICommodityPrice
	 * @return commodity price local ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBCommodityPriceLocal create(ICommodityPrice price) throws CreateException;

	/**
	 * Find commodity price by its primary key, the commodity price id.
	 * 
	 * @param priceID commodity price ID
	 * @return commodity price local ejb object
	 * @throws FinderException on error finding the commodity price
	 */
	public EBCommodityPriceLocal findByPrimaryKey(Long priceID) throws FinderException;

	/**
	 * Find commodity price by its group id.
	 * 
	 * @param groupID group id
	 * @return a Collection of commodity price local ejb objects
	 * @throws FinderException on error finding the commodity price
	 */
	public Collection findByGroupID(long groupID) throws FinderException;

	/**
	 * Find commodity price based on the profile id.
	 * 
	 * @param profileID profile id
	 * @return a collection of commodity price ejb objects
	 * @throws FinderException on error finding the commodity price
	 */
	public Collection findByProfileID(long profileID) throws FinderException;
}
