/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/CommodityDealProxyImpl.java,v 1.10 2006/05/10 07:39:26 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class facades the ICommodityDealProxy implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/05/10 07:39:26 $ Tag: $Name: $
 */
public class CommodityDealProxyImpl extends AbstractCommodityDealProxy {
	/**
	 * Get the commodity deal transaction value given its deal id.
	 * 
	 * @param ctx transaction context
	 * @param dealID commodity deal id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID) throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.getCommodityDealTrxValue(ctx, dealID);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at getCommodityDealTrxValue by dealID: " + e.toString());
		}
	}

	/**
	 * Get the commodity deal transaction value given its deal id. If
	 * includeDealOB is true it will get the actual and staging deal
	 * information. Otherwise it will just get the transaction information.
	 * 
	 * @param ctx transaction context
	 * @param dealID commodity deal id
	 * @param includeDealOB boolean
	 * @param includeHistory
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID, boolean includeDealOB,
			boolean includeHistory) throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.getCommodityDealTrxValue(ctx, dealID, includeDealOB, includeHistory);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exception caught at getCommodityDealTrxValue by dealID: " + e.toString());
		}
	}

	/**
	 * Get the commodity deal transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, String trxID) throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.getCommodityDealTrxValue(ctx, trxID);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at getCommodityDealTrxValue by trxID: " + e.toString());
		}
	}

	/**
	 * Get the commodity deal transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @param includeHistory boolean
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, String trxID, boolean includeHistory)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.getCommodityDealTrxValue(ctx, trxID, includeHistory);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at getCommodityDealTrxValue by trxID: " + e.toString());
		}
	}

	/**
	 * Maker updates commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @param deal deal to be updated
	 * @return newly updated commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerUpdateCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal,
			ICommodityDeal deal) throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.makerUpdateCommodityDeal(ctx, trxVal, deal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at makerUpdateCommodityDeal: " + e.toString());
		}
	}

	/**
	 * Maker saves the commodity deal as draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @param deal commodity deal to be saved
	 * @return the draft commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerSaveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal,
			ICommodityDeal deal) throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.makerSaveCommodityDeal(ctx, trxVal, deal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at makerSaveCommodityDeal: " + e.toString());
		}
	}

	/**
	 * Maker closes commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return closed commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerCloseCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.makerCloseCommodityDeal(ctx, trxVal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at makerCloseCommodityDeal: " + e.toString());
		}
	}

	/**
	 * Maker cancel rejected or draft commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return cancelled commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerCancelCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.makerCancelCommodityDeal(ctx, trxVal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at makerCancelCommodityDeal: " + e.toString());
		}
	}

	/**
	 * Checker approves commodity deal updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return approved commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerApproveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.checkerApproveCommodityDeal(ctx, trxVal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exceptin caught at checkerApproveCommodityDeal: " + e.toString());
		}
	}

	/**
	 * Checker rejects commodity deal updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.checkerRejectCommodityDeal(ctx, trxVal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exception caught at checkerRejectCommodityDeal: " + e.toString());
		}
	}

	/**
	 * System revaluates commodity deal.
	 * 
	 * @param trxVal commodity deal transaction value
	 * @return newly updated transaction
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue systemValuateCommodityDeal(ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.systemValuateCommodityDeal(trxVal);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exception at systemValuateCommodityDeal:" + e.toString());
		}
	}

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchDeal(ITrxContext ctx, CommodityDealSearchCriteria criteria) throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.searchDeal(ctx, criteria);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exception at searchCollateral:" + e.toString());
		}
	}

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchClosedDeal(ITrxContext ctx, CommodityDealSearchCriteria criteria)
			throws CommodityDealException {
		try {
			SBCommodityDealProxy proxy = getProxy();
			return proxy.searchClosedDeal(ctx, criteria);
		}
		catch (CommodityDealException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("Exception at searchCollateral:" + e.toString());
		}
	}

	/**
	 * Method to rollback a transaction. Not implemented at online proxy level.
	 * 
	 * @throws CommodityDealException for any errors encountered
	 */
	protected void rollback() throws CommodityDealException {
	}

	/**
	 * Helper method to get ejb object of commodity deal proxy session bean.
	 * 
	 * @return commodity deal proxy ejb object
	 */
	private SBCommodityDealProxy getProxy() throws CommodityDealException {
		SBCommodityDealProxy proxy = (SBCommodityDealProxy) BeanController.getEJB(
				ICMSJNDIConstant.SB_COMMODITY_DEAL_PROXY_JNDI, SBCommodityDealProxyHome.class.getName());

		if (proxy == null) {
			throw new CommodityDealException("SBCommodityDealProxy is null!");
		}
		return proxy;
	}

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException {
		try {
			return getProxy().hasSLRelatedDeal(subLimitId);
		}
		catch (RemoteException e) {
			throw new CommodityDealException(e);
		}
	}

	public void revaluateCustomerDeals(CommodityDealSearchCriteria objSearch) throws CommodityDealException {
		try {
			getProxy().revaluateCustomerDeals(objSearch);
		}
		catch (RemoteException e) {
			throw new CommodityDealException(e);
		}
	}
}