/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/EBCommodityDealHome.java,v 1.4 2005/10/06 05:51:31 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * Defines commodity deal create and finder methods for remote clients.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/06 05:51:31 $ Tag: $Name: $
 */
public interface EBCommodityDealHome extends EJBHome {
	/**
	 * Create commodity deal record.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return commodity deal ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCommodityDeal create(ICommodityDeal deal) throws CreateException, RemoteException;

	/**
	 * Find commodity deal by primary key, the deal id.
	 * 
	 * @param dealID deal id
	 * @return commodity deal ejb object
	 * @throws FinderException on error finding the deal
	 * @throws RemoteException on error during remote method call
	 */
	public EBCommodityDeal findByPrimaryKey(Long dealID) throws FinderException, RemoteException;

	/**
	 * Search commodity deal given the criteria.
	 * 
	 * @param criteria deal search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the deal
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException, RemoteException;

	/**
	 * Search closed commodity deal given the criteria.
	 * 
	 * @param criteria deal search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException,
			RemoteException;

	public Collection findBySubLimitId(long subLimitId) throws FinderException, RemoteException;
}
