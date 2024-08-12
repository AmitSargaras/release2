/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

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
 * This is the remote interface to the SBTradingBookProxyBean session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBTradingBookProxy extends EJBObject {

	// ******************** Proxy methods for ISDA CSA Deal ****************
	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealSummaryByAgreement
	 */
	public IISDACSADealSummary getISDACSADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADeal
	 */
	public IISDACSADealDetail getISDACSADeal(long cmsDealID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealValuationTrxValue
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getISDACSADealValuationTrxValueByTrxID
	 */
	public IISDACSADealValTrxValue getISDACSADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerSaveISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal,
			IISDACSADealVal[] value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue makerCancelUpdateISDACSADealValuation(ITrxContext ctx, IISDACSADealValTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue checkerApproveUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateISDACSADealValuation
	 */
	public IISDACSADealValTrxValue checkerRejectUpdateISDACSADealValuation(ITrxContext ctx,
			IISDACSADealValTrxValue trxVal) throws TradingBookException, RemoteException;

	// ******************** Proxy methods for GMRA Deal ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealSummaryByAgreement
	 */
	public IGMRADealSummary getGMRADealSummaryByAgreement(long limitProfileID, long agreementID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADeal
	 */
	public IGMRADealDetail getGMRADeal(long cmsDealID) throws TradingBookException, RemoteException;

	public ICPAgreementDetail getCounterPartyAgreementDetail(long agreementID) throws TradingBookException,
			RemoteException;

	// ******************** Proxy methods for Add/Edit/Remove GMRA Deal
	// ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValue
	 */
	public IGMRADealTrxValue getGMRADealTrxValue(ITrxContext ctx, long cmsDealID) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValueByAgreementID
	 */
	public IGMRADealTrxValue[] getGMRADealTrxValueByAgreementID(ITrxContext ctx, long agreementID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealTrxValueByTrxID
	 */
	public IGMRADealTrxValue getGMRADealTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCreateGMRADeal
	 */
	public IGMRADealTrxValue makerCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, long agreementID,
			IGMRADeal value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateGMRADeal
	 */
	public IGMRADealTrxValue makerUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal, IGMRADeal value)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCloseUpdateGMRADeal
	 */
	public IGMRADealTrxValue makerCloseUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCloseCreateGMRADeal
	 */
	public IGMRADealTrxValue makerCloseCreateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerDeleteGMRADeal
	 */
	public IGMRADealTrxValue makerDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerApproveUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveDeleteGMRADeal
	 */
	public IGMRADealTrxValue checkerApproveDeleteGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, DeleteGMRADealException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateGMRADeal
	 */
	public IGMRADealTrxValue checkerRejectUpdateGMRADeal(ITrxContext ctx, IGMRADealTrxValue trxVal)
			throws TradingBookException, RemoteException;

	// ******************** Proxy methods for Input Valuation GMRA Deal
	// ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealValuationTrxValue
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValue(ITrxContext ctx, long agreementID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getGMRADealValuationTrxValueByTrxID
	 */
	public IGMRADealValTrxValue getGMRADealValuationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue makerUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveGMRADealValuation
	 */
	public IGMRADealValTrxValue makerSaveGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal,
			IGMRADealVal[] value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue makerCancelUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue checkerApproveUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateGMRADealValuation
	 */
	public IGMRADealValTrxValue checkerRejectUpdateGMRADealValuation(ITrxContext ctx, IGMRADealValTrxValue trxVal)
			throws TradingBookException, RemoteException;

	// ******************** Proxy methods for Cash Margin ****************

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCashMarginTrxValue
	 */
	public ICashMarginTrxValue getCashMarginTrxValue(ITrxContext ctx, long agreementID) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#getCashMarginTrxValueByTrxID
	 */
	public ICashMarginTrxValue getCashMarginTrxValueByTrxID(ITrxContext ctx, String trxID) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerUpdateCashMargin
	 */
	public ICashMarginTrxValue makerUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerSaveCashMargin
	 */
	public ICashMarginTrxValue makerSaveCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal, ICashMargin[] value)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#makerCancelUpdateCashMargin
	 */
	public ICashMarginTrxValue makerCancelUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerApproveUpdateCashMargin
	 */
	public ICashMarginTrxValue checkerApproveUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy#checkerRejectUpdateCashMargin
	 */
	public ICashMarginTrxValue checkerRejectUpdateCashMargin(ITrxContext ctx, ICashMarginTrxValue trxVal)
			throws TradingBookException, RemoteException;

}