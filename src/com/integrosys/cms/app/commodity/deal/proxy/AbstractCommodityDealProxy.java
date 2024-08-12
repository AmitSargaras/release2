/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/AbstractCommodityDealProxy.java,v 1.39 2006/09/15 12:45:18 hshii Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealBusManagerFactory;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealBusManager;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealSearchResult;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBCommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.trx.CommodityDealTrxControllerFactory;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.deal.trx.OBCommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class implements the services that are available in CMS with respect to
 * the commodity deal life cycle.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.39 $
 * @since $Date: 2006/09/15 12:45:18 $ Tag: $Name: $
 */
public abstract class AbstractCommodityDealProxy implements ICommodityDealProxy {
	/**
	 * Get the commodity deal transaction value given its deal id.
	 * 
	 * @param ctx transaction context
	 * @param dealID commodity deal id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID) throws CommodityDealException {
		return this.getCommodityDealTrxValue(ctx, dealID, true, true);
	}

	/**
	 * Get the commodity deal transaction value given its deal id. If
	 * includeDealOB is true it will get the actual and staging deal
	 * information. Otherwise it will just get the transaction information.
	 * 
	 * @param dealID commodity deal id
	 * @param includeDealOB boolean
	 * @param includeHistory
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, long dealID, boolean includeDealOB,
			boolean includeHistory) throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DEAL_BY_DEALID);
		OBCommodityDealTrxValue trxValue = new OBCommodityDealTrxValue();
		trxValue.setReferenceID(String.valueOf(dealID));
		trxValue.setIncludeDealInfo(includeDealOB);
		trxValue.setIncludeHistory(includeHistory);
		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxValue), param);
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
		return this.getCommodityDealTrxValue(ctx, trxID, false);
	}

	/**
	 * Get the commodity deal transaction value given the transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue getCommodityDealTrxValue(ITrxContext ctx, String trxID, boolean includeHistory)
			throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_DEAL_BY_TRXID);
		ICommodityDealTrxValue trxValue = new OBCommodityDealTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setIncludeHistory(includeHistory);
		trxValue = (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxValue), param);
		setStageTitleDocsHistory(trxValue.getCommodityDeal(), trxValue.getStagingCommodityDeal());
		return trxValue;
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
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ((trxVal.getTransactionID() == null) || (trxVal.getCommodityDeal() == null)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CREATE_DEAL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_DEAL);
		}
		trxVal.setStagingCommodityDeal(deal);
		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
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
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_DEAL);
		trxVal.setStagingCommodityDeal(deal);
		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
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
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DEAL);
		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Maker cancel rejected or draft commodity deal. Also include Maker cancel
	 * rejected 'close' commodity deal. (Maker (Close) -> Checker (Rejects) ->
	 * Maker Close this Rejected Close Commdity Deal (Close the transaction) )
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return cancelled commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue makerCancelCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getCommodityDeal() == null) {
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_CREATE_DEAL);
		}
		else if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECT_CLOSE)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_CLOSE_DEAL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_DEAL);
		}
		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);

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
		if (trxVal.getStagingCommodityDeal().getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER)) {
			updateOperationalLimit(trxVal.getCommodityDeal(), trxVal.getStagingCommodityDeal());
		}

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE)) {
			param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_DEAL);
			}
			else {
				param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_DEAL);
			}
		}

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
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
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE)) {
			param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CREATE_DEAL);
			}
			else {
				param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_DEAL);
			}
		}

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Checker forwards create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue checkerForwardCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();

		// param.setAction (ICMSConstant.ACTION_CHECKER_FORWARD_CREATE_DEAL);

		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE)) {
			param.setAction(ICMSConstant.ACTION_CHECKER_FORWARD_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param.setAction(ICMSConstant.ACTION_CHECKER_FORWARD_CREATE_DEAL);
			}
			else {
				param.setAction(ICMSConstant.ACTION_CHECKER_FORWARD_UPDATE_DEAL);
			}
		}
		DefaultLogger.debug(this, "########## checkerForwardCommodityDeal : action - " + param.getAction());

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * FAM rejection of create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue FAMRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		// param.setAction
		// (ICMSConstant.STATE_PENDING_CREATE_VERIFY.equals(trxVal.getStatus())
		// ?
		// ICMSConstant.ACTION_FAM_REJECT_CREATE_DEAL :
		// ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CREATE_DEAL);

		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE_VERIFY)) {
			param.setAction(ICMSConstant.ACTION_FAM_REJECT_CLOSE_DEAL);
		}
		else if ((trxVal.getStatus() != null) && trxVal.getFromState().equals(ICMSConstant.STATE_PENDING_CLOSE_VERIFY)) {
			param.setAction(ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param
						.setAction(ICMSConstant.STATE_PENDING_CREATE_VERIFY.equals(trxVal.getStatus()) ? ICMSConstant.ACTION_FAM_REJECT_CREATE_DEAL
								: ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CREATE_DEAL);
			}
			else {
				param
						.setAction(ICMSConstant.STATE_PENDING_UPDATE_VERIFY.equals(trxVal.getStatus()) ? ICMSConstant.ACTION_FAM_REJECT_UPDATE_DEAL
								: ICMSConstant.ACTION_FAM_CONFIRM_REJECT_UPDATE_DEAL);
			}
		}
		DefaultLogger.debug(this, "########## FAMRejectCommodityDeal : action - " + param.getAction());

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Officer forwards create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue officerForwardCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		// param.setAction (ICMSConstant.ACTION_OFFICER_FORWARD_CREATE_DEAL);

		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE_VERIFY)) {
			param.setAction(ICMSConstant.ACTION_OFFICER_FORWARD_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param.setAction(ICMSConstant.ACTION_OFFICER_FORWARD_CREATE_DEAL);
			}
			else {
				param.setAction(ICMSConstant.ACTION_OFFICER_FORWARD_UPDATE_DEAL);
			}
		}
		DefaultLogger.debug(this, "########## officerForwardCommodityDeal : action - " + param.getAction());

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Officer approves create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue officerApproveCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		updateOperationalLimit(trxVal.getCommodityDeal(), trxVal.getStagingCommodityDeal());
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		// param.setAction (ICMSConstant.ACTION_OFFICER_APPROVE_CREATE_DEAL);

		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE_VERIFY)) {
			param.setAction(ICMSConstant.ACTION_OFFICER_APPROVE_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param.setAction(ICMSConstant.ACTION_OFFICER_APPROVE_CREATE_DEAL);
			}
			else {
				param.setAction(ICMSConstant.ACTION_OFFICER_APPROVE_UPDATE_DEAL);
			}
		}
		DefaultLogger.debug(this, "########## officerApproveCommodityDeal : action - " + param.getAction());

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * Officer rejects create commodity deal requests.
	 * 
	 * @param ctx transaction context
	 * @param trxVal commodity deal transaction value
	 * @return rejected commodity deal transaction value
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDealTrxValue officerRejectCommodityDeal(ITrxContext ctx, ICommodityDealTrxValue trxVal)
			throws CommodityDealException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		// param.setAction (ICMSConstant.ACTION_OFFICER_REJECT_CREATE_DEAL);

		if ((trxVal.getStatus() != null) && trxVal.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE_VERIFY)) {
			param.setAction(ICMSConstant.ACTION_OFFICER_REJECT_CLOSE_DEAL);
		}
		else {
			if (trxVal.getCommodityDeal() == null) {
				param.setAction(ICMSConstant.ACTION_OFFICER_REJECT_CREATE_DEAL);
			}
			else {
				param.setAction(ICMSConstant.ACTION_OFFICER_REJECT_UPDATE_DEAL);
			}
		}
		DefaultLogger.debug(this, "########## officerRejectCommodityDeal : action - " + param.getAction());

		return (ICommodityDealTrxValue) operate(constructTrxValue(ctx, trxVal), param);
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
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_VALUATE_DEAL);
		return (ICommodityDealTrxValue) operate(trxVal, param);
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
		ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
		criteria.setTrxContext(ctx);
		SearchResult sr = mgr.searchDeal(criteria);
		if (criteria.isPendingOfficerApproval()) {
			Iterator i = sr.getResultList().iterator();
			if (i.hasNext()) {
				ICommodityDealSearchResult deal = (ICommodityDealSearchResult) i.next();
				ICommodityDealTrxValue trxVal = getCommodityDealTrxValue(ctx, deal.getTrxID(), false);
				mapOBtoSearchResult(trxVal.getCommodityDeal(), trxVal.getStagingCommodityDeal(), deal);
				deal.setTrxValue(trxVal);
			}
		}
		return sr;
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
		ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
		criteria.setTrxContext(ctx);
		SearchResult sr = mgr.searchClosedDeal(criteria);
		if (criteria.isPendingOfficerApproval()) {
			Iterator i = sr.getResultList().iterator();
			if (i.hasNext()) {
				ICommodityDealSearchResult deal = (ICommodityDealSearchResult) i.next();
				ICommodityDealTrxValue trxVal = getCommodityDealTrxValue(ctx, deal.getTrxID(), false);
				mapOBtoSearchResult(trxVal.getCommodityDeal(), trxVal.getStagingCommodityDeal(), deal);
				deal.setTrxValue(trxVal);
			}
		}
		return sr;
	}

	/**
	 * Helper method to map deal business object to deal search result.
	 * 
	 * @param actualDeal of type ICommodityDeal
	 * @param stageDeal of type ICommodityDeal
	 * @param sr of type ICommodityDealSearchResult
	 */
	private void mapOBtoSearchResult(ICommodityDeal actualDeal, ICommodityDeal stageDeal, ICommodityDealSearchResult sr) {
		if ((actualDeal != null) && (actualDeal.getStatus() != null)
				&& actualDeal.getStatus().equals(ICMSConstant.STATE_CLOSED)) {
			sr.setIsDealClosed(true);
		}
		sr.setDealID(stageDeal.getCommodityDealID());
		sr.setDealNo(stageDeal.getDealNo());
		sr.setDealTypeCode(stageDeal.getDealTypeCode());
		sr.setDealReferenceNo(stageDeal.getDealReferenceNo());
		sr.setDealCashReqPct(stageDeal.getCashReqPct());
		sr.setPriceType(stageDeal.getContractPriceType().getName());
		sr.setDealAmt(stageDeal.getDealAmt());

		if (sr.getDealAmt() != null) {
			BigDecimal cashReqAmt = CommonUtil.calcAfterPercent(sr.getDealAmt().getAmountAsBigDecimal(), sr
					.getDealCashReqPct());
			sr.setCashReqAmt(new Amount(cashReqAmt, sr.getDealAmt().getCurrencyCodeAsObject()));
		}
		sr.setMarketPrice(stageDeal.getActualPrice());
		sr.setDealCMV(stageDeal.getCMV());
		sr.setDealFSV(stageDeal.getFSV());

		// just use the main deal formula to get face value amount
		try {
			sr.setFaceValueAmt(stageDeal.getFaceValueAmt());
		}
		catch (Exception e) {
			sr.setFaceValueAmt(new Amount(ICMSConstant.DOUBLE_INVALID_VALUE, ""));
		}

		ICommodityTitleDocument td = OBCommodityTitleDocument.getTitleDocsLatestByTitleDocID(stageDeal
				.getTitleDocsLatest());
		if (td != null) {
			if ((td.getIsSecured() != null) && td.getIsSecured().equals(ICMSConstant.TRUE_VALUE)) {
				sr.setIsDealSecured(true);
			}
			sr.setEligibilityAdvPct(td.getAdvEligibilityPct());
		}

		sr.setQuantity(stageDeal.getBalanceDealQty());
	}

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
			throws CommodityDealException {
		try {
			Amount activatedAmt = limit.getActivatedLimitAmount();
			Amount newOP = calcOperationalLimit(limit.getOperationalLimit(), limit.getApprovedLimitAmount()
					.getCurrencyCode(), actualDeal, stageDeal);
			if (activatedAmt == null) {
				return false;
			}
			if (newOP == null) {
				return true;
			}

			if (activatedAmt.compareTo(newOP) < 0) {
				return false;
			}

			return true;
		}
		catch (AmountConversionException e) {
			throw new CommodityDealException("AmountConversionException caught!", e);
		}
		catch (Exception e) {
			throw new CommodityDealException("Exception caught!", e);
		}
	}

	/**
	 * To get total operational limit for outer limit.
	 * 
	 * @param profile of type ILimitProfile
	 * @param aLimit of type ILimit
	 * @return amount
	 * @throws CommodityDealException on errors encountered
	 */
	public Amount getOuterOperationalLimit(ILimitProfile profile, ILimit aLimit) throws CommodityDealException {
		ILimit[] limits = null;
		if (profile != null) {
			limits = profile.getLimits();
		}
		if (aLimit == null) {
			return null;
		}

		Amount totalOP = null;
		if (aLimit.getOperationalLimit() != null) {
			totalOP = new Amount(aLimit.getOperationalLimit().getAmountAsBigDecimal(), aLimit.getOperationalLimit()
					.getCurrencyCodeAsObject());
		}

		if (limits == null) {
			return totalOP;
		}

		String ccyCode = aLimit.getApprovedLimitAmount().getCurrencyCode();

		try {
			for (int i = 0; i < limits.length; i++) {
				if (limits[i].getOuterLimitID() == aLimit.getLimitID()) {
					Amount innerOP = AmountConversion.getConversionAmount(limits[i].getOperationalLimit(), ccyCode);

					if (totalOP == null) {
						totalOP = innerOP;
					}
					else {
						if (innerOP != null) {
							totalOP.addToThis(innerOP);
						}
					}
				}
			}
			return totalOP;
		}
		catch (AmountConversionException e) {
			throw new CommodityDealException("AmountConversionException caught!", e);
		}
		catch (Exception e) {
			throw new CommodityDealException("Exception caught!", e);
		}
	}

	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @return transaction value
	 */
	private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		((ICMSTrxValue) trxValue).setTrxContext(ctx);
		return trxValue;
	}

	/**
	 * Helper method to operate transactions.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws CommodityDealException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws CommodityDealException {
		if (trxVal == null) {
			throw new CommodityDealException("ICommodityDealTrxValue is null!");
		}

		try {
			ITrxController controller = (new CommodityDealTrxControllerFactory()).getController(trxVal, param);

			if (controller == null) {
				throw new CommodityDealException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (CommodityDealException e) {
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			rollback();
			throw new CommodityDealException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new CommodityDealException("Exception caught! " + e.toString(), e);
		}
	}

	/**
	 * Helper method to update operational limit.
	 * 
	 * @param oldDeal of type ICommodityDeal
	 * @param newDeal of type ICommodityDeal
	 * @throws CommodityDealException on any errors encountered
	 */
	private void updateOperationalLimit(ICommodityDeal oldDeal, ICommodityDeal newDeal) throws CommodityDealException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitTrxValue limitTrx = proxy.getTrxLimit(newDeal.getLimitID());
			Amount oldOP = limitTrx.getLimit().getOperationalLimit();
			Amount newOP = calcOperationalLimit(oldOP, limitTrx.getLimit().getApprovedLimitAmount().getCurrencyCode(),
					oldDeal, newDeal);

			if ((oldOP == null) && (newOP == null)) {
				return;
			}
			if ((oldOP != null) && (newOP != null) && oldOP.equals(newOP)) {
				return;
			}

			// only update limit if there's a diff between old and new
			// operational limit
			limitTrx.getLimit().setOperationalLimit(newOP);
			limitTrx.getStagingLimit().setOperationalLimit(newOP);
			limitTrx.setToUpdateOPLimit(true);
			proxy.systemUpdateLimit(limitTrx);
		}
		catch (AmountConversionException e) {
			throw new CommodityDealException("AmountConversionException in calcOperationalLImit:" + e.getMessage());
		}
		catch (LimitException e) {
			throw new CommodityDealException("LimitException caught at updateOperationalLimit!" + e.toString());
		}
		catch (Exception e) {
			throw new CommodityDealException("Exception caught at updateOperationalLimit!" + e.toString());
		}
	}

	/**
	 * Helper method to calculate current operational limit.
	 * 
	 * @param oldOP existing operational limit
	 * @param limitCcy currency code used in limit
	 * @param oldDeal existing deal
	 * @param newDeal new updated deal
	 * @return newly calculated operational limit
	 * @throws AmountConversionException on error during conversion
	 * @throws ChainedException exception calculating operational limit
	 */
	private Amount calcOperationalLimit(Amount oldOP, String limitCcy, ICommodityDeal oldDeal, ICommodityDeal newDeal)
			throws AmountConversionException, ChainedException {
		if (limitCcy == null) {
			return null;
		}

		Amount oldBalance = null, newBalance = null;
		if (oldDeal != null) {
			oldBalance = AmountConversion.getConversionAmount(oldDeal.getBalanceDealAmt(), limitCcy);
		}
		if (newDeal != null) {
			newBalance = AmountConversion.getConversionAmount(newDeal.getBalanceDealAmt(), limitCcy);
		}
		if (oldOP == null) {
			return roundAmount(newBalance);
		}

		if (newBalance != null) {
			if (oldBalance == null) {
				oldBalance = new Amount(0d, limitCcy);
			}
			Amount newOP = oldOP.subtract(oldBalance).add(newBalance);
			return roundAmount(newOP);
		}
		return null;
	}

	/**
	 * Helper method to round up the amount value.
	 * 
	 * @param amt of type Amount
	 * @return rounded amount
	 */
	private static Amount roundAmount(Amount amt) {
		if (amt == null) {
			return amt;
		}

		BigDecimal bd = amt.getAmountAsBigDecimal();
		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		return new Amount(bd, amt.getCurrencyCodeAsObject());
	}

	/**
	 * Helper method to set actual title docs history to staging deal.
	 * 
	 * @param actual actual commodity deal
	 * @param staging staging commodity deal
	 */
	private void setStageTitleDocsHistory(ICommodityDeal actual, ICommodityDeal staging) {
		ICommodityTitleDocument[] actualDocs = null, stageDocs = null;

		if (actual != null) {
			actualDocs = actual.getTitleDocsHistory();
		}

		if (staging != null) {
			stageDocs = staging.getTitleDocsHistory();
		}

		if ((stageDocs == null) || (stageDocs.length == 0)) {
			staging.setTitleDocsHistory(actualDocs);
		}
		else {
			if ((actualDocs != null) && (actualDocs.length != 0)) {
				ArrayList newList = new ArrayList();
				/*
				 * for (int i=0; i<stageDocs.length; i++) { newList.add
				 * (stageDocs[i]); }
				 */
				for (int i = 0; i < actualDocs.length; i++) {
					boolean found = false;
					for (int j = 0; j < stageDocs.length; j++) {
						if (actualDocs[i].getRefID() == stageDocs[j].getRefID()) {
							found = true;
							break;
						}
					}
					if (!found) {
						newList.add(actualDocs[i]);
					}
				}
				staging.setTitleDocsHistory((ICommodityTitleDocument[]) newList
						.toArray(new OBCommodityTitleDocument[0]));
			}
			else {
				// this is to control a staging without actual. Must change the
				// commodity deal to
				// check for history[0] == null.
				staging.setTitleDocsHistory(new OBCommodityTitleDocument[1]);
			}
		}
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CommodityDealException on errors encountered
	 */
	protected abstract void rollback() throws CommodityDealException;

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException {
		ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
		return mgr.hasSLRelatedDeal(subLimitId);
	}
}