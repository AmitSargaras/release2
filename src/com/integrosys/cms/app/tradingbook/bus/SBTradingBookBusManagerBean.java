/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean acts as the facade to the Entity Beans for trading book
 * actual data.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBTradingBookBusManagerBean implements ITradingBookBusManager, SessionBean {
	/** SessionContext object */
	private SessionContext ctx;

	public static final String EXCLUDE_STATUS = ICMSConstant.STATE_DELETED;

	public static final String MY_CURR = CurrencyCode.MYR.getCode();

	/**
	 * Default Constructor
	 */
	public SBTradingBookBusManagerBean() {
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADeal
	 */
	public IISDACSADeal getISDACSADeal(long cmsDealID) throws TradingBookException {

		EBISDACSADealHome ejbHome = getEBISDACSADealHome();
		try {

			EBISDACSADeal theEjb = ejbHome.findByPrimaryKey(new Long(cmsDealID));

			return theEjb.getValue();
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			throw new TradingBookException("FinderException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADealByAgreementID
	 */
	public IISDACSADeal[] getISDACSADealByAgreementID(long agreementID) throws TradingBookException {
		try {
			EBISDACSADealHome ejbHome = getEBISDACSADealHome();
			Iterator i = ejbHome.findByAgreementID(ICMSConstant.ISDA_CSA_TYPE, agreementID, EXCLUDE_STATUS).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBISDACSADeal theEjb = (EBISDACSADeal) i.next();

				arrList.add(theEjb.getValue());

			}

			return (IISDACSADeal[]) arrList.toArray(new OBISDACSADeal[0]);
		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("FinderException caught at getISDACSADealByAgreementID " + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("Exception caught at getISDACSADealByAgreementID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getISDACSADealValuationByGroupID
	 */
	public IISDACSADealVal[] getISDACSADealValuationByGroupID(long groupID) throws TradingBookException {
		try {
			EBDealValuationHome ejbHome = getEBDealValuationHome();
			Iterator i = ejbHome.findByGroupID(groupID, EXCLUDE_STATUS).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBDealValuation theEjb = (EBDealValuation) i.next();
				IDealValuation val = theEjb.getValue();
				IISDACSADeal deal = getISDACSADeal(val.getCMSDealID());
				OBISDACSADealVal dealVal = new OBISDACSADealVal(val);
				dealVal.setISDACSADealDetail(deal);
				arrList.add(dealVal);

			}

			return (IISDACSADealVal[]) arrList.toArray(new OBISDACSADealVal[0]);
		}
		catch (FinderException e) {
			throw new TradingBookException("FinderException caught at getISDACSADealValuationByGroupID " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getISDACSADealValuationByGroupID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateISDACSADealValuationByCMSDealID
	 */
	public IISDACSADealVal[] updateISDACSADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException {
		EBDealValuationHome ejbHome = getEBDealValuationHome();
		EBISDACSADealHome ejbISDACSAHome = getEBISDACSADealHome();
		try {
			ArrayList arrList = new ArrayList();

			for (int i = 0; i < value.length; i++) {
				EBDealValuation theEjb = ejbHome.findByDealID(value[i].getCMSDealID());
//				System.out.println("before setDealMarketValue,from ejb=" + theEjb.getValue());
//				System.out.println("before setDealMarketValue, from ob=" + value[i]);
				theEjb.setDealMarketValue(value[i]);
//				System.out.println("after setDealMarketValue");
				IDealValuation dealVal = theEjb.getValue();

				Amount refAmt = getConversionAmount(dealVal.getMarketValue(), MY_CURR);

				EBISDACSADeal theISDACSAEjb = ejbISDACSAHome.findByPrimaryKey(new Long(value[i].getCMSDealID()));
				theISDACSAEjb.setNPVBaseValue(value[i], refAmt);

				IISDACSADeal isda = theISDACSAEjb.getValue();

				IISDACSADealVal csaVal = new OBISDACSADealVal(dealVal);

				csaVal.setISDACSADealDetail(isda);

				arrList.add(csaVal);

			}
			return (IISDACSADealVal[]) arrList.toArray(new OBISDACSADealVal[0]);
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getDealValuation
	 */
	public IDealValuation[] getDealValuation(long agreementID) throws TradingBookException {
		try {
			EBDealValuationHome ejbHome = getEBDealValuationHome();
			Iterator i = ejbHome.findByGroupID(agreementID, EXCLUDE_STATUS).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBDealValuation theEjb = (EBDealValuation) i.next();
				IDealValuation dealVal = theEjb.getValue();
				arrList.add(dealVal);
			}

			return (IDealValuation[]) arrList.toArray(new OBDealValuation[0]);
		}
		catch (FinderException e) {
			throw new TradingBookException("FinderException caught at getDealValuation " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getDealValuation " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getDealValuationByCMSDealID
	 */
	public IDealValuation getDealValuationByCMSDealID(long cmsDealID) throws TradingBookException {
		try {
			EBDealValuationHome ejbHome = getEBDealValuationHome();
			EBDealValuation theEjb = ejbHome.findByDealID(cmsDealID);

			return theEjb.getValue();

		}
		catch (FinderException e) {
			throw new TradingBookException("FinderException caught at getDealValuationByCMSDealID " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getDealValuationByCMSDealID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createDealValuation
	 */
	public IDealValuation[] createDealValuation(IDealValuation[] value) throws TradingBookException {
		return null;
	}

	private IDealValuation createDealValuation(long agreementID, IDealValuation value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("IDealValuation is null");
		}

		EBDealValuationHome ejbHome = getEBDealValuationHome();

		try {

			OBDealValuation dealVal = new OBDealValuation(value);
			if (dealVal.getCMSDealID() == ICMSConstant.LONG_INVALID_VALUE) {
				rollback();
				throw new TradingBookException("CMS deal id is invalid ! ");
			}

			EBDealValuation theEjb = ejbHome.create(dealVal.getCMSDealID(), dealVal);
			dealVal = (OBDealValuation) theEjb.getValue();
			return dealVal;
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
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateDealValuation
	 */
	public IDealValuation[] updateDealValuation(IDealValuation[] value) throws TradingBookException {
		EBDealValuationHome ejbHome = getEBDealValuationHome();
		try {
			ArrayList arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < value.length; i++) {
				EBDealValuation theEjb = ejbHome.findByPrimaryKey(new Long(value[i].getDealValuationID()));

				value[i].setGroupID(groupID);

				theEjb.setGroupIDValue(value[i]);

				IDealValuation dealVal = (IDealValuation) theEjb.getValue();

				groupID = dealVal.getGroupID();

				arrList.add(dealVal);

			}
			return (IDealValuation[]) arrList.toArray(new OBDealValuation[0]);
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateGMRADealValuationByCMSDealID
	 */
	public IGMRADealVal[] updateGMRADealValuationByCMSDealID(IDealValuation[] value) throws TradingBookException {
		EBDealValuationHome ejbHome = getEBDealValuationHome();
		EBGMRADealHome ejbGMRAHome = getEBGMRADealHome();
		try {
			ArrayList arrList = new ArrayList();

			for (int i = 0; i < value.length; i++) {
				EBDealValuation theEjb = ejbHome.findByDealID(value[i].getCMSDealID());
				theEjb.setDealMarketValue(value[i]);

				IDealValuation dealVal = theEjb.getValue();

				EBGMRADeal theGMRAEjb = ejbGMRAHome.findByPrimaryKey(new Long(value[i].getCMSDealID()));
				theGMRAEjb.setNPVBaseValue(value[i]);

				IGMRADeal gmra = theGMRAEjb.getValue();

				IGMRADealVal gmraVal = new OBGMRADealVal(dealVal);

				gmraVal.setGMRADealDetail(gmra);

				arrList.add(gmraVal);

			}
			return (IGMRADealVal[]) arrList.toArray(new OBGMRADealVal[0]);
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	private IDealValuation deleteDealValuation(IDealValuation value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("IDealValuation is null");
		}

		EBDealValuationHome ejbHome = getEBDealValuationHome();

		try {

			EBDealValuation theEjb = ejbHome.findByDealID(value.getCMSDealID());

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
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADealValuationByGroupID
	 */
	public IGMRADealVal[] getGMRADealValuationByGroupID(long groupID) throws TradingBookException {
		try {
			EBDealValuationHome ejbHome = getEBDealValuationHome();
			Iterator i = ejbHome.findByGroupID(groupID, EXCLUDE_STATUS).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBDealValuation theEjb = (EBDealValuation) i.next();
				IDealValuation val = theEjb.getValue();

				IGMRADeal deal = getGMRADeal(val.getCMSDealID());
				OBGMRADealVal dealVal = new OBGMRADealVal(val);
				dealVal.setGMRADealDetail(deal);
				arrList.add(dealVal);
			}

			return (IGMRADealVal[]) arrList.toArray(new OBGMRADealVal[0]);
		}
		catch (FinderException e) {
			throw new TradingBookException("FinderException caught at getGMRADealValuationByGroupID " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getGMRADealValuationByGroupID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADeal
	 */
	public IGMRADeal getGMRADeal(long cmsDealID) throws TradingBookException {

		EBGMRADealHome ejbHome = getEBGMRADealHome();
		try {

			EBGMRADeal theEjb = ejbHome.findByPrimaryKey(new Long(cmsDealID));

			return theEjb.getValue();
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			throw new TradingBookException("FinderException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new TradingBookException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getGMRADealByAgreementID
	 */
	public IGMRADeal[] getGMRADealByAgreementID(long agreementID) throws TradingBookException {

		try {
			EBGMRADealHome ejbHome = getEBGMRADealHome();
			Iterator i = ejbHome.findByAgreementID(ICMSConstant.GMRA_TYPE, agreementID, EXCLUDE_STATUS).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBGMRADeal theEjb = (EBGMRADeal) i.next();

				IGMRADeal gmra = theEjb.getValue();
				arrList.add(gmra);
			}

			return (IGMRADeal[]) arrList.toArray(new OBGMRADeal[0]);
		}
		catch (FinderException e) {
			throw new TradingBookException("FinderException caught at getGMRADealByAgreementID " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getGMRADealByAgreementID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createGMRADeal
	 */
	public IGMRADeal createGMRADeal(long agreementID, IGMRADeal value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("IGMRADeal is null");
		}
		if (agreementID == ICMSConstant.LONG_INVALID_VALUE) {
			throw new TradingBookException("agreementID is invalid ! ");
		}

		EBGMRADealHome ejbHome = getEBGMRADealHome();

		try {
			EBGMRADeal theEjb = ejbHome.create(agreementID, value);
			IGMRADeal gmra = theEjb.getValue();
			IDealValuation val = new OBDealValuation(gmra);
			IDealValuation dealVal = createDealValuation(agreementID, val);

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
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateGMRADeal
	 */
	public IGMRADeal updateGMRADeal(IGMRADeal value) throws TradingBookException {
		if (value == null) {
			throw new TradingBookException("IGMRADeal is null");
		}

		EBGMRADealHome ejbHome = getEBGMRADealHome();
		try {

			EBGMRADeal theEjb = ejbHome.findByPrimaryKey(new Long(value.getCMSDealID()));
			theEjb.setValue(value);

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
			throw new TradingBookException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
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
			IGMRADeal gmra = theEjb.getValue();

			// do soft delete
			theEjb.setStatusDeleted(value);

			IDealValuation val = new OBDealValuation(gmra);

			val = deleteDealValuation(val);

			return gmra;

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
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getCashMarginByAgreementID
	 */
	public ICashMargin[] getCashMarginByAgreementID(long agreementID) throws TradingBookException {

		try {
			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			return dao.getCashMarginByAgreementID(agreementID);
		}
		catch (SearchDAOException e) {
			throw new TradingBookException("SearchDAOException caught at getCashMarginByAgreementID " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getCashMarginByAgreementID " + e.toString());
		}

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#getCashMarginByGroupID
	 */
	public ICashMargin[] getCashMarginByGroupID(long groupID) throws TradingBookException {
		try {
			EBCashMarginHome ejbHome = getEBCashMarginHome();
			Iterator i = ejbHome.findByGroupID(groupID).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBCashMargin theEjb = (EBCashMargin) i.next();

				arrList.add(theEjb.getValue());
			}

			return (ICashMargin[]) arrList.toArray(new OBCashMargin[0]);
		}
		catch (FinderException e) {
			throw new TradingBookException("FinderException caught at getCashMarginByGroupID " + e.toString());
		}
		catch (Exception e) {
			throw new TradingBookException("Exception caught at getCashMarginByGroupID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#createCashMargin
	 */
	public ICashMargin[] createCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException {
		if ((value == null) || (value.length == 0)) {
			throw new TradingBookException("ICashMargin[] is null");
		}
		if (agreementID == ICMSConstant.LONG_INVALID_VALUE) {
			throw new TradingBookException("Agreement id is invalid ! ");
		}
		EBCashMarginHome ejbHome = getEBCashMarginHome();

		try {
			ArrayList arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < value.length; i++) {
				OBCashMargin cashMargin = new OBCashMargin(value[i]);
				DefaultLogger.debug(this, " Create cash margin group ID: " + cashMargin.getGroupID());

				cashMargin.setGroupID(groupID);

				EBCashMargin theEjb = ejbHome.create(agreementID, cashMargin);
				cashMargin = (OBCashMargin) theEjb.getValue();
				groupID = cashMargin.getGroupID();

				arrList.add(cashMargin);
			}
			return (ICashMargin[]) arrList.toArray(new OBCashMargin[0]);
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
	 * @see com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager#updateCashMargin
	 */
	public ICashMargin[] updateCashMargin(long agreementID, ICashMargin[] value) throws TradingBookException {
		if (agreementID == ICMSConstant.LONG_INVALID_VALUE) {
			throw new TradingBookException("Agreement id is invalid ! ");
		}
		EBCashMarginHome ejbHome = getEBCashMarginHome();

		try {

			ArrayList arrList = new ArrayList();

			for (int i = 0; i < value.length; i++) {
				OBCashMargin cashMargin = new OBCashMargin(value[i]);
				// ICashMargin cashMargin = value[i];
				DefaultLogger.debug(this, " update cashMargin: " + cashMargin);
				if (cashMargin.isUpdateInd()) {
					DefaultLogger.debug(this, " Update cash margin for: " + value[i].getCashMarginID());
					EBCashMargin theEjb = ejbHome.findByPrimaryKey(new Long(value[i].getCashMarginID()));
					theEjb.setNAPValue(value[i]);

					arrList.add(theEjb.getValue());
				}
				else if (cashMargin.isCreateInd()) {
					DefaultLogger.debug(this, " Create cash margin for: " + cashMargin.getCashMarginID());

					EBCashMargin theEjb = ejbHome.create(agreementID, cashMargin);

					arrList.add(theEjb.getValue());
				}
				else if (cashMargin.isDeleteInd()) {
					DefaultLogger.debug(this, " Delete cash margin for: " + value[i].getCashMarginID());
					EBCashMargin theEjb = ejbHome.findByPrimaryKey(new Long(value[i].getCashMarginID()));
					// do soft delete
					theEjb.setStatusDeleted(cashMargin);
				}
				else {
					rollback();
					throw new TradingBookException("Undefine operation for cash margin");

				}
			}

			return (ICashMargin[]) arrList.toArray(new OBCashMargin[0]);

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
	 * Convert the amt using the new currency code.
	 * 
	 * @param amt of type Amount
	 * @param newCcyCode new currency code for exchange
	 * @return new amount object
	 * @throws CollateralException on conversion error
	 */
	public Amount getConversionAmount(Amount amt, String newCcyCode) throws TradingBookException {
		try {
			SBForexManager forexMgr = null;

			if (!isValidAmt(amt)) {
				return null;
			}
			if (newCcyCode == null) {
				return new Amount(amt.getAmountAsBigDecimal(), new CurrencyCode(amt.getCurrencyCode()));
			}
			if (!amt.getCurrencyCode().equals(newCcyCode)) {
				if (forexMgr == null) {
					forexMgr = getSBForexManager();
				}
				CurrencyCode newCurrency = new CurrencyCode(newCcyCode);
				DefaultLogger.debug(this, "before convert, amount=" + amt.getAmountAsBigDecimal());

				Amount newAmt = forexMgr.convert(amt, newCurrency);

				DefaultLogger.debug(this, "after convert, amount=" + newAmt.getAmountAsBigDecimal());

				BigDecimal newVal = newAmt.getAmountAsBigDecimal();

				newVal = newVal.setScale(4, BigDecimal.ROUND_HALF_UP);

				DefaultLogger.debug(this, "after set scale, amount=" + newVal);

				newAmt = new Amount(newVal, newCurrency);

				return newAmt;
			}
			else {
				return new Amount(amt.getAmountAsBigDecimal(), new CurrencyCode(amt.getCurrencyCode()));
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			TradingBookException ex = new TradingBookException();
			// ex.setErrorCode (CollateralException.FX_ERR_CODE);
			throw ex;
		}
	}

	private static boolean isValidAmt(Amount amt) {
		if (amt == null) {
			return false;
		}

		if ((amt.getCurrencyCode() == null) || (amt.getCurrencyCode().length() == 0)) {
			return false;
		}

		return true;
	}

	/**
	 * Get DAO implementation for tradingbook dao.
	 * 
	 * @return ITradingBookDAO
	 */
	protected ITradingBookDAO getTradingBookDAO() {
		return TradingBookDAOFactory.getDAO();
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws TradingBookException on errors encountered
	 */
	protected void rollback() throws TradingBookException {
		ctx.setRollbackOnly();
	}

	/**
	 * Get home interface of EBISDACSADeal.
	 * 
	 * @return ISDA CSA Deal home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBISDACSADealHome getEBISDACSADealHome() throws TradingBookException {
		EBISDACSADealHome ejbHome = (EBISDACSADealHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_ISDA_CSA_DEAL_JNDI, EBISDACSADealHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBISDACSADealHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Get home interface of EBGMRADeal.
	 * 
	 * @return GMRA Deal home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBGMRADealHome getEBGMRADealHome() throws TradingBookException {
		EBGMRADealHome ejbHome = (EBGMRADealHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_GMRA_DEAL_JNDI,
				EBGMRADealHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBGMRADealHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Get home interface of EBDealValuation.
	 * 
	 * @return Deal Valuation home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBDealValuationHome getEBDealValuationHome() throws TradingBookException {
		EBDealValuationHome ejbHome = (EBDealValuationHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_DEAL_VALUATION_JNDI, EBDealValuationHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBDealValuationHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Get home interface of EBCashMargin.
	 * 
	 * @return Cash Margin home interface
	 * @throws TradingBookException on errors encountered
	 */
	protected EBCashMarginHome getEBCashMarginHome() throws TradingBookException {
		EBCashMarginHome ejbHome = (EBCashMarginHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CASH_MARGIN_JNDI,
				EBCashMarginHome.class.getName());

		if (ejbHome == null) {
			throw new TradingBookException("EBCashMarginHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager the remote handler for the forex manager session
	 *         bean
	 * @throws Exception for any errors encountered
	 */
	private static SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());

		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface. No implementation is required for this bean.
	 */
	public void ejbCreate() {
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(SessionContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {
	}
}