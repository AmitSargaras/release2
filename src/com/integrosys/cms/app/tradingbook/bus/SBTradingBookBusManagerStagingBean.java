/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean acts as the facade to the Entity Beans for trading book
 * staging data.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBTradingBookBusManagerStagingBean extends SBTradingBookBusManagerBean {
	/**
	 * Default Constructor
	 */
	public SBTradingBookBusManagerStagingBean() {
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createGMRADeal
	 */
	public IGMRADeal createGMRADeal(long agreementID, IGMRADeal value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("IGMRADeal is null");
		}
		if (agreementID == ICMSConstant.LONG_INVALID_VALUE) {
			throw new TradingBookException("agreementID is invalid !");
		}

		EBGMRADealHome ejbHome = getEBGMRADealHome();

		try {
			EBGMRADeal theEjb = ejbHome.create(agreementID, value);
			IGMRADeal gmra = theEjb.getValue();

			return gmra;
		}
		catch (CreateException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("CreateException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#deleteGMRADeal
	 */
	public IGMRADeal deleteGMRADeal(IGMRADeal value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("IGMRADeal is null");
		}

		EBGMRADealHome ejbHome = getEBGMRADealHome();

		try {
			EBGMRADeal theEjb = ejbHome.findByPrimaryKey(new Long(value.getCMSDealID()));

			// do soft delete
			theEjb.setStatusDeleted(value);
			return theEjb.getValue();

		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("VersionMismatchException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createDealValuation
	 */
	public IDealValuation[] createDealValuation(IDealValuation[] value) throws TradingBookException {
		if ((value == null) || (value.length == 0)) {
			throw new TradingBookException("IDealValuation[] is null");
		}

		EBDealValuationHome ejbHome = getEBDealValuationHome();

		try {
			ArrayList arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < value.length; i++) {
				OBDealValuation dealVal = new OBDealValuation(value[i]);
				dealVal.setGroupID(groupID);
				if (dealVal.getCMSDealID() == ICMSConstant.LONG_INVALID_VALUE) {
					rollback();
					throw new TradingBookException("CMS deal id is invalid ! ");
				}

				EBDealValuation theEjb = ejbHome.create(dealVal.getCMSDealID(), dealVal);
				dealVal = (OBDealValuation) theEjb.getValue();
				groupID = dealVal.getGroupID();

				arrList.add(dealVal);
			}
			return (IDealValuation[]) arrList.toArray(new OBDealValuation[0]);
		}
		catch (CreateException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("CreateException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}

	}

	/**
	 * Get staging home interface of EBISDACSADeal.
	 * 
	 * @return ISDA CSA Deal home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBISDACSADealHome getEBISDACSADealHome() throws TradingBookException {
		EBISDACSADealHome ejbHome = (EBISDACSADealHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_ISDA_CSA_DEAL_STAGING_JNDI, EBISDACSADealHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBISDACSADealHome for staging is null!");
		}

		return ejbHome;
	}

	/**
	 * Get staging home interface of EBGMRADeal.
	 * 
	 * @return GMRA Deal home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBGMRADealHome getEBGMRADealHome() throws TradingBookException {
		EBGMRADealHome ejbHome = (EBGMRADealHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_GMRA_DEAL_STAGING_JNDI,
				EBGMRADealHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBGMRADealHome for staging is null!");
		}

		return ejbHome;
	}

	/**
	 * Get staging home interface of EBDealValuation.
	 * 
	 * @return Deal Valuation home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBDealValuationHome getEBDealValuationHome() throws TradingBookException {
		EBDealValuationHome ejbHome = (EBDealValuationHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_DEAL_VALUATION_STAGING_JNDI, EBDealValuationHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBDealValuationHome for staging is null!");
		}

		return ejbHome;
	}

	/**
	 * Get staging home interface of EBCashMargin.
	 * 
	 * @return Cash Margin home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBCashMarginHome getEBCashMarginHome() throws TradingBookException {
		EBCashMarginHome ejbHome = (EBCashMarginHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CASH_MARGIN_STAGING_JNDI, EBCashMarginHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBCashMarginHome for staging is null!");
		}

		return ejbHome;
	}

}