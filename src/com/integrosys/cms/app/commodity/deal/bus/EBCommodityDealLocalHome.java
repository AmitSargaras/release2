/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/EBCommodityDealLocalHome.java,v 1.4 2005/10/06 05:53:25 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * Defines commodity deal create and finder methods for remote clients.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/06 05:53:25 $ Tag: $Name: $
 */
public interface EBCommodityDealLocalHome extends EJBLocalHome {
	/**
	 * Create commodity deal record.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return local commodity deal ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBCommodityDealLocal create(ICommodityDeal deal) throws CreateException;

	/**
	 * Find commodity deal by primary key, the deal id.
	 * 
	 * @param dealID deal id
	 * @return local commodity deal ejb object
	 * @throws FinderException on error finding the deal
	 */
	public EBCommodityDealLocal findByPrimaryKey(Long dealID) throws FinderException;

	/**
	 * Search commodity deal given the criteria.
	 * 
	 * @param criteria deal search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException;

	/**
	 * Search closed commodity deal given the criteria.
	 * 
	 * @param criteria deal search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException;

	public Collection findBySubLimitId(long subLimitId) throws FinderException;
}
