/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CommodityDealBusManagerImpl.java,v 1.8 2005/10/06 05:21:06 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ICommodityDealBusManager implementation by delegating
 * the handling of requests to an ejb session bean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/10/06 05:21:06 $ Tag: $Name: $
 */
public class CommodityDealBusManagerImpl extends AbstractCommodityDealBusManager {
	/**
	 * Get the complete commodity deal given its deal id.
	 * 
	 * @param dealID commodity deal id
	 * @return commodity deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal getCommodityDeal(long dealID) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();

		try {
			return theEjb.getCommodityDeal(dealID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * Create a deal.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly created deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal createDeal(ICommodityDeal deal) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();

		try {
			return theEjb.createDeal(deal);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * Update a deal.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal updateDeal(ICommodityDeal deal) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();

		try {
			return theEjb.updateDeal(deal);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * Update commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal updateDealStatus(ICommodityDeal deal) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();

		try {
			return theEjb.updateDealStatus(deal);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();
		try {
			return theEjb.searchDeal(criteria);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();
		try {
			return theEjb.searchClosedDeal(criteria);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * Process notification for changes in deal state.
	 * 
	 * @param notifyInfo of type ICommodityDealNotifyInfo
	 * @throws CommodityDealException on errors encountered
	 */
	public void processNotification(ICommodityDealNotifyInfo notifyInfo) throws CommodityDealException {
		SBCommodityDealBusManager theEjb = getBusManager();
		try {
			theEjb.processNotification(notifyInfo);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * helper method to get an ejb object to deal business manager session bean.
	 * 
	 * @return commodity deal manager ejb object
	 * @throws CommodityDealException on errors encountered
	 */
	protected SBCommodityDealBusManager getBusManager() throws CommodityDealException {
		SBCommodityDealBusManager theEjb = (SBCommodityDealBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMODITY_DEAL_MGR_JNDI, SBCommodityDealBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new CommodityDealException("SBCommodityDealBusManager for Actual is null!");
		}

		return theEjb;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CommodityDealException on errors encountered
	 */
	protected void rollback() throws CommodityDealException {
	}

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException {
		try {
			return getBusManager().hasSLRelatedDeal(subLimitId);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			throw new CommodityDealException(e.getMessage());
		}
	}
}