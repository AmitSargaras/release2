/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/SBCommodityDealProxy.java,v 1.13 2006/05/16 02:25:34 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is the remote interface to the SBCommodityDealProxy session bean.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/05/16 02:25:34 $ Tag: $Name: $
 */
public interface SBCommodityDealProxy extends EJBObject {
	/**
	 * Get the commodity deal transaction value given its deal id.
	 * 
	 * @param ctx transaction context
	 * @param dealID commodity deal id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote call
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID) throws CommodityDealException,
			RemoteException;

	/**
	 * Get the commodity deal transaction value given its deal id. If
	 * includeDealOB is true it will get the actual and staging deal
	 * information. Otherwise it will just get the transaction information.
	 * 
	 * @param ctx transaction context
	 * @param dealID commodity deal id
	 * @param includeDealOB boolean
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID, boolean includeDealOB,
			boolean includeHistory) throws CommodityDealException, RemoteException;

	/**
	 * Get the commodity deal transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, String trxID)
			throws CommodityDealException, RemoteException;

	/**
	 * Get the commodity deal transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @param includeHistory boolean
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, String trxID, boolean includeHistory)
			throws CommodityDealException, RemoteException;

	/**
	 * Maker updates commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @param deal deal to be updated
	 * @return newly updated commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue makerUpdateCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal,
			ICommodityDeal deal) throws CommodityDealException, RemoteException;

	/**
	 * Maker saves the commodity deal as draft.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @param deal commodity deal to be saved
	 * @return the draft commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue makerSaveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal,
			ICommodityDeal deal) throws CommodityDealException, RemoteException;

	/**
	 * Maker closes commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return closed commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue makerCloseCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Maker cancel rejected or draft commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return cancelled commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue makerCancelCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Checker approves commodity deal updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return approved commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue checkerApproveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Checker rejects commodity deal updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * System revaluates commodity deal.
	 * 
	 * @param trxVal commodity deal transaction value
	 * @return newly updated transaction
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on errors encountered
	 */
	public ICommodityDealTrxValue systemValuateCommodityDeal(ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchDeal(ITrxContext ctx, CommodityDealSearchCriteria criteria)
			throws CommodityDealException, RemoteException;

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchClosedDeal(ITrxContext ctx, CommodityDealSearchCriteria criteria)
			throws CommodityDealException, RemoteException;

	/**
	 * Checker forwards create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue checkerForwardCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * FAM rejection of create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue FAMRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Officer forwards create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue officerForwardCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Officer approves create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue officerApproveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	/**
	 * Officer rejects create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 * @throws RemoteException on error during remote method call
	 */
	public ICommodityDealTrxValue officerRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException, RemoteException;

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException, RemoteException;

	public void revaluateCustomerDeals(CommodityDealSearchCriteria objSearch) throws CommodityDealException,
			RemoteException;
}