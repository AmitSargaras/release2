/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealDetail;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class facades the ITradingBookProxy implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookProxyImpl implements ITradingBookProxy {

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealSummaryByAgreement
	 */
	public IISDACSADealSummary getISDACSADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getISDACSADealSummaryByAgreement(limitProfileID, agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealSummaryByAgreement: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealSummaryByAgreement: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADeal
	 */
	public IISDACSADealDetail getISDACSADeal(long cmsDealID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getISDACSADeal(cmsDealID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealValuationTrxValue
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getISDACSADealValuationTrxValue(ctx, agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealValuationTrxValue: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealValuationTrxValue: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealValuationTrxValueByTrxID
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getISDACSADealValuationTrxValueByTrxID(ctx, trxID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealValuationTrxValueByTrxID: "
					+ e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealValuationTrxValueByTrxID: "
					+ e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerUpdateISDACSADealValuation(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateISDACSADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateISDACSADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerSaveISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerSaveISDACSADealValuation(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerSaveISDACSADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerSaveISDACSADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerCancelUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerCancelUpdateISDACSADealValuation(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCancelUpdateISDACSADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCancelUpdateISDACSADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue checkerApproveUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerApproveUpdateISDACSADealValuation(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateISDACSADealValuation: "
					+ e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateISDACSADealValuation: "
					+ e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue checkerRejectUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerRejectUpdateISDACSADealValuation(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateISDACSADealValuation: "
					+ e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateISDACSADealValuation: "
					+ e.toString());
		}
	}

	// ******************** Proxy methods for GMRA Deal ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealSummaryByAgreement
	 */
	public IGMRADealSummary getGMRADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADealSummaryByAgreement(limitProfileID, agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealSummaryByAgreement: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealSummaryByAgreement: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADeal
	 */
	public IGMRADealDetail getGMRADeal(long cmsDealID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADeal(cmsDealID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADeal: " + e.toString());
		}
	}

	public ICPAgreementDetail getCounterPartyAgreementDetail(long agreementID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getCounterPartyAgreementDetail(agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getCounterPartyAgreementDetail: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getCounterPartyAgreementDetail: " + e.toString());
		}
	}

	// ******************** Proxy methods for Add/Edit/Remove GMRA Deal
	// ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValue
	 */
	public IGMRADealTrxValue getGMRADealTrxValue(ITrxContext ctx, long cmsDealID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADealTrxValue(ctx, cmsDealID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealTrxValue: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealTrxValue: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValueByAgreementID
	 */
	public IGMRADealTrxValue[] getGMRADealTrxValueByAgreementID(ITrxContext ctx, long agreementID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADealTrxValueByAgreementID(ctx, agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealTrxValueByAgreementID: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealTrxValueByAgreementID: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValueByTrxID
	 */
	public IGMRADealTrxValue getGMRADealTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADealTrxValueByTrxID(ctx, trxID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealTrxValueByTrxID: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealTrxValueByTrxID: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCreateGMRADeal
	 */
	public IGMRADealTrxValue makerCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, long agreementID,
			IGMRADeal value) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerCreateGMRADeal(ctx, trxVal, agreementID, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCreateGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCreateGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateGMRADeal
	 */
	public IGMRADealTrxValue makerUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, IGMRADeal value)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerUpdateGMRADeal(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCloseCreateGMRADeal
	 */
	public IGMRADealTrxValue makerCloseCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerCloseCreateGMRADeal(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCloseCreateGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCloseCreateGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCloseUpdateGMRADeal
	 */
	public IGMRADealTrxValue makerCloseUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerCloseUpdateGMRADeal(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCloseUpdateGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCloseUpdateGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerDeleteGMRADeal
	 */
	public IGMRADealTrxValue makerDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerDeleteGMRADeal(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerDeleteGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerDeleteGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerApproveUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerApproveUpdateGMRADeal(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerApproveDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, DeleteGMRADealException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerApproveDeleteGMRADeal(ctx, trxVal);
		}
		catch (DeleteGMRADealException e) {
			throw e;
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveDeleteGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveDeleteGMRADeal: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerRejectUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerRejectUpdateGMRADeal(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateGMRADeal: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateGMRADeal: " + e.toString());
		}
	}

	// ******************** Proxy methods for Input Valuation GMRA Deal
	// ****************
	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealValuationTrxValue
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADealValuationTrxValue(ctx, agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealValuationTrxValue: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealValuationTrxValue: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealValuationTrxValueByTrxID
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getGMRADealValuationTrxValueByTrxID(ctx, trxID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealValuationTrxValueByTrxID: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getGMRADealValuationTrxValueByTrxID: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue makerUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerUpdateGMRADealValuation(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateGMRADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateGMRADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveGMRADealValuation
	 */
	public IGMRADealValTrxValue makerSaveGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerSaveGMRADealValuation(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerSaveGMRADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerSaveGMRADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue makerCancelUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerCancelUpdateGMRADealValuation(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCancelUpdateGMRADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCancelUpdateGMRADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue checkerApproveUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerApproveUpdateGMRADealValuation(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateGMRADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateGMRADealValuation: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue checkerRejectUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerRejectUpdateGMRADealValuation(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateGMRADealValuation: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateGMRADealValuation: " + e.toString());
		}
	}

	// ******************** Proxy methods for Cash Margin ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCashMarginTrxValue
	 */
	public ICashMarginTrxValue getCashMarginTrxValue(ITrxContext ctx, long agreementID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getCashMarginTrxValue(ctx, agreementID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getCashMarginTrxValue: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getCashMarginTrxValue: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCashMarginTrxValueByTrxID
	 */
	public ICashMarginTrxValue getCashMarginTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.getCashMarginTrxValueByTrxID(ctx, trxID);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getCashMarginTrxValueByTrxID: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getCashMarginTrxValueByTrxID: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateCashMargin
	 */
	public ICashMarginTrxValue makerUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerUpdateCashMargin(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateCashMargin: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerUpdateCashMargin: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveCashMargin
	 */
	public ICashMarginTrxValue makerSaveCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerSaveCashMargin(ctx, trxVal, value);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerSaveCashMargin: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerSaveCashMargin: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateCashMargin
	 */
	public ICashMarginTrxValue makerCancelUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.makerCancelUpdateCashMargin(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCancelUpdateCashMargin: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at makerCancelUpdateCashMargin: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateCashMargin
	 */
	public ICashMarginTrxValue checkerApproveUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerApproveUpdateCashMargin(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateCashMargin: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerApproveUpdateCashMargin: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateCashMargin
	 */
	public ICashMarginTrxValue checkerRejectUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException {
		try {
			SBTradingBookProxy proxy = getProxy();
			return proxy.checkerRejectUpdateCashMargin(ctx, trxVal);
		}
		catch (TradingBookException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateCashMargin: " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at checkerRejectUpdateCashMargin: " + e.toString());
		}
	}

	/**
	 * Method to rollback a transaction. Not implemented at online proxy level.
	 * 
	 * @throws TradingBookException for any errors encountered
	 */
	protected void rollback() throws TradingBookException {
	}

	/**
	 * Helper method to get ejb object of tradingbook proxy session bean.
	 * 
	 * @return tradingbook proxy ejb object
	 */
	private SBTradingBookProxy getProxy() throws TradingBookException {
		SBTradingBookProxy proxy = (SBTradingBookProxy) BeanController.getEJB(
				ICMSJNDIConstant.SB_TRADING_BOOK_PROXY_JNDI, SBTradingBookProxyHome.class.getName());

		if (proxy == null) {
			throw new TradingBookException("SBTradingBookProxy is null!");
		}
		return proxy;
	}
}