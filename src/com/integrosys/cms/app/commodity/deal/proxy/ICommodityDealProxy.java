/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/ICommodityDealProxy.java,v 1.14 2006/05/10 07:39:26 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with respect to
 * the commodity deal life cycle.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/05/10 07:39:26 $ Tag: $Name: $
 */
public interface ICommodityDealProxy {
	/**
	 * Get the commodity deal transaction value given its deal id.
	 * 
	 * @param ctx transaction context
	 * @param dealID commodity deal id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID) throws CommodityDealException;

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
			boolean includeHistory) throws CommodityDealException;

	/**
	 * Get the commodity deal transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, String trxID) throws CommodityDealException;

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
			throws CommodityDealException;

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
			ICommodityDeal deal) throws CommodityDealException;

	/**
	 * Maker closes commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return closed commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerCloseCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * Maker cancel rejected or draft commodity deal.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return cancelled commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerCancelCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

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
			ICommodityDeal deal) throws CommodityDealException;

	/**
	 * Checker approves commodity deal updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return approved commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerApproveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * Checker rejects commodity deal updated/created by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * System revaluates commodity deal.
	 * 
	 * @param trxVal commodity deal transaction value
	 * @return newly updated transaction
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue systemValuateCommodityDeal(ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchDeal(ITrxContext ctx, CommodityDealSearchCriteria criteria) throws CommodityDealException;

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param ctx transaction context
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchClosedDeal(ITrxContext ctx, CommodityDealSearchCriteria criteria)
			throws CommodityDealException;

	/**
	 * Checker forwards create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerForwardCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * FAM rejection of create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue FAMRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * Officer forwards create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue officerForwardCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * Officer approves create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue officerApproveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * Officer rejects create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue officerRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException;

	/**
	 * To check if the newly calculated operational limit is within activated
	 * limit.
	 * 
	 * @param limit actual limit information
	 * @param actualDeal actual commodity deal
	 * @param stageDeal newly updated commodity deal
	 * @return true if the new operational limit is within activated limit,
	 *         otherwise false
	 * @throws CommodityDealException on errors encountered
	 */
	public boolean isValidOperationalLimit(ILimit limit, ICommodityDeal actualDeal, ICommodityDeal stageDeal)
			throws CommodityDealException;

	/**
	 * To get total operational limit for outer limit.
	 * 
	 * @param profile of type ILimitProfile
	 * @param limit of type ILimit
	 * @return amount
	 * @throws CommodityDealException on errors encountered
	 */
	public Amount getOuterOperationalLimit(ILimitProfile profile, ILimit limit) throws CommodityDealException;

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException;

	public void revaluateCustomerDeals(CommodityDealSearchCriteria objSearch) throws CommodityDealException;
}
