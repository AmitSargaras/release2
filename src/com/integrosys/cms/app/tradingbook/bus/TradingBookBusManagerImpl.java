/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ITradingBookManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookBusManagerImpl implements ITradingBookBusManager {
	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADeal
	 */
	public IISDACSADeal getISDACSADeal(long cmsDealID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getISDACSADeal(cmsDealID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADealByAgreementID
	 */
	public IISDACSADeal[] getISDACSADealByAgreementID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getISDACSADealByAgreementID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADealValuationByGroupID
	 */
	public IISDACSADealVal[] getISDACSADealValuationByGroupID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getISDACSADealValuationByGroupID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADealByAgreementID
	 */
	public IGMRADeal[] getGMRADealByAgreementID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getGMRADealByAgreementID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADealValuationByGroupID
	 */
	public IGMRADealVal[] getGMRADealValuationByGroupID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getGMRADealValuationByGroupID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getDealValuation
	 */
	public IDealValuation[] getDealValuation(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getDealValuation(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getDealValuationByCMSDealID
	 */
	public IDealValuation getDealValuationByCMSDealID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getDealValuationByCMSDealID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createDealValuation
	 */
	public IDealValuation[] createDealValuation(IDealValuation[] value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.createDealValuation(value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateISDACSADealValuationByCMSDealID
	 */
	public IISDACSADealVal[] updateISDACSADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.updateISDACSADealValuationByCMSDealID(value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateDealValuation
	 */
	public IDealValuation[] updateDealValuation(IDealValuation[] value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.updateDealValuation(value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateGMRADealValuationByCMSDealID
	 */
	public IGMRADealVal[] updateGMRADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.updateGMRADealValuationByCMSDealID(value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADeal
	 */
	public IGMRADeal getGMRADeal(long cmsDealID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getGMRADeal(cmsDealID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createGMRADeal
	 */
	public IGMRADeal createGMRADeal(long agreementID, IGMRADeal value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.createGMRADeal(agreementID, value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateGMRADeal
	 */
	public IGMRADeal updateGMRADeal(IGMRADeal value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.updateGMRADeal(value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#deleteGMRADeal
	 */
	public IGMRADeal deleteGMRADeal(IGMRADeal value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.deleteGMRADeal(value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getCashMarginByAgreementID
	 */
	public ICashMargin[] getCashMarginByAgreementID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getCashMarginByAgreementID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getCashMarginByGroupID
	 */
	public ICashMargin[] getCashMarginByGroupID(long agreementID) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.getCashMarginByGroupID(agreementID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createCashMargin
	 */
	public ICashMargin[] createCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.createCashMargin(agreementID, value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateCashMargin
	 */
	public ICashMargin[] updateCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException {
		SBTradingBookBusManager theEjb = getBusManager();

		try {
			return theEjb.updateCashMargin(agreementID, value);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * helper method to get an ejb object to trading book business manager
	 * session bean.
	 * 
	 * @return trading book manager ejb object
	 * @throws TradingBookException on errors encountered
	 */
	protected SBTradingBookBusManager getBusManager() throws TradingBookException {
		SBTradingBookBusManager theEjb = (SBTradingBookBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_TRADING_BOOK_MGR_JNDI, SBTradingBookBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new TradingBookException("SBTradingBookManager for Actual is null!");
		}

		return theEjb;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws TradingBookException on errors encountered
	 */
	protected void rollback() throws TradingBookException {
	}
}