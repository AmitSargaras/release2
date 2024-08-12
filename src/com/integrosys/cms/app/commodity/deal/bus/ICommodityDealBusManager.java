/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/ICommodityDealBusManager.java,v 1.6 2005/10/06 05:54:12 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * This interface defines the services of a deal business manager.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/06 05:54:12 $ Tag: $Name: $
 */
public interface ICommodityDealBusManager extends Serializable {
	/**
	 * Get the complete commodity deal given its deal id.
	 * 
	 * @param dealID commodity deal id
	 * @return commodity deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal getCommodityDeal(long dealID) throws CommodityDealException;

	/**
	 * Create a deal.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly created deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal createDeal(ICommodityDeal deal) throws CommodityDealException;

	/**
	 * Update a deal.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal updateDeal(ICommodityDeal deal) throws CommodityDealException;

	/**
	 * Update commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal updateDealStatus(ICommodityDeal deal) throws CommodityDealException;

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException;

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException;

	/**
	 * Process notification for changes in deal state.
	 * 
	 * @param notifyInfo of type ICommodityDealNotifyInfo
	 * @throws CommodityDealException on errors encountered
	 */
	public void processNotification(ICommodityDealNotifyInfo notifyInfo) throws CommodityDealException;

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException;
}
