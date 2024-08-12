/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/SBCommodityDealBusManager.java,v 1.6 2005/10/06 05:54:33 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * Remote interface to the commodity deal business manager session bean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/06 05:54:33 $ Tag: $Name: $
 */
public interface SBCommodityDealBusManager extends EJBObject {
	/**
	 * Get the complete commodity deal given its deal id.
	 * 
	 * @param dealID commodity deal id
	 * @return commodity deal
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDeal getCommodityDeal(long dealID) throws CommodityDealException, RemoteException;

	/**
	 * Create a deal.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly created deal
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDeal createDeal(ICommodityDeal deal) throws CommodityDealException, RemoteException;

	/**
	 * Update a deal.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDeal updateDeal(ICommodityDeal deal) throws CommodityDealException, RemoteException;

	/**
	 * Update commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDeal updateDealStatus(ICommodityDeal deal) throws CommodityDealException, RemoteException;

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException, RemoteException;

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException,
			RemoteException;

	/**
	 * Process notification for changes in deal state.
	 * 
	 * @param notifyInfo of type ICommodityDealNotifyInfo
	 * @throws CommodityDealException on errors encountered
	 */
	public void processNotification(ICommodityDealNotifyInfo notifyInfo) throws CommodityDealException, RemoteException;

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException, RemoteException;
}