/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Remote interface to the tradingbook business manager session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBTradingBookBusManager extends EJBObject {

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADeal
	 */
	public IISDACSADeal getISDACSADeal(long cmsDealID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADealByAgreementID
	 */
	public IISDACSADeal[] getISDACSADealByAgreementID(long agreementID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADealValuationByGroupID
	 */
	public IISDACSADealVal[] getISDACSADealValuationByGroupID(long groupID) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateISDACSADealValuationByCMSDealID
	 */
	public IISDACSADealVal[] updateISDACSADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getDealValuation
	 */
	public IDealValuation[] getDealValuation(long agreementID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getDealValuationByCMSDealID
	 */
	public IDealValuation getDealValuationByCMSDealID(long agreementID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createDealValuation
	 */
	public IDealValuation[] createDealValuation(IDealValuation[] value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateDealValuation
	 */
	public IDealValuation[] updateDealValuation(IDealValuation[] value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateGMRADealValuationByCMSDealID
	 */
	public IGMRADealVal[] updateGMRADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADealValuationByGroupID
	 */
	public IGMRADealVal[] getGMRADealValuationByGroupID(long groupID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADeal
	 */
	public IGMRADeal getGMRADeal(long cmsDealID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADealByAgreementID
	 */
	public IGMRADeal[] getGMRADealByAgreementID(long agreementID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createGMRADeal
	 */
	public IGMRADeal createGMRADeal(long agreementID, IGMRADeal value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateGMRADeal
	 */
	public IGMRADeal updateGMRADeal(IGMRADeal value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#deleteGMRADeal
	 */
	public IGMRADeal deleteGMRADeal(IGMRADeal value) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getCashMarginByAgreementID
	 */
	public ICashMargin[] getCashMarginByAgreementID(long agreementID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getCashMarginByGroupID
	 */
	public ICashMargin[] getCashMarginByGroupID(long groupID) throws TradingBookException, RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createCashMargin
	 */
	public ICashMargin[] createCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException,
			RemoteException;

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateCashMargin
	 */
	public ICashMargin[] updateCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException,
			RemoteException;
}