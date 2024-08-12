/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for Trading Agreement.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBTradingAgreementBean implements EntityBean, ITradingAgreement {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_AGREEMENT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getAgreementID", "getLimitProfileID" };

	public static final String EXCLUDE_STATUS = ICMSConstant.STATE_DELETED;

	public static final String EMPTY_STR = "";

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBTradingAgreementBean() {
	}

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getAgreementID
	 */
	public long getAgreementID() {
		if (null != getAgreementPK()) {
			return getAgreementPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getLimitProfileID
	 */
	public long getLimitProfileID() {
		if (null != getLimitProfileFK()) {
			return getLimitProfileFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getMinTransferAmount
	 */
	public Amount getMinTransferAmount() {
		String ccy = getMinTransferCurrency();
		BigDecimal amt = getMinTransferAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getCounterPartyThresholdAmount
	 */
	public Amount getCounterPartyThresholdAmount() {
		String ccy = getBaseCurrency();
		BigDecimal amt = getCounterPartyThresholdAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getMbbThresholdAmount
	 */
	public Amount getMbbThresholdAmount() {
		String ccy = getBaseCurrency();
		BigDecimal amt = getMbbThresholdAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getCounterPartyExtRating
	 */
	public IExternalRating getCounterPartyExtRating() {

		if (getCounterPartyRatingType() != null) {
			OBExternalRating ob = new OBExternalRating(getCounterPartyRatingType());
			// ob.setRatingType(getCounterPartyRatingType());
			ob.setRating(getCounterPartyRating());

			return ob;
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getMbbExtRating
	 */
	public IExternalRating getMbbExtRating() {

		if (getMbbRatingType() != null) {
			OBExternalRating ob = new OBExternalRating(getMbbRatingType());
			// ob.setRatingType(getMbbRatingType());
			ob.setRating(getMbbRating());

			return ob;
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getThresholdRatingList
	 */
	public List getThresholdRatingList() {
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setAgreementID
	 */
	public void setAgreementID(long value) {
		setAgreementPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setLimitProfileID
	 */
	public void setLimitProfileID(long value) {
		setLimitProfileFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setMinTransferAmount
	 */
	public void setMinTransferAmount(Amount value) {
		if (null != value) {
			setMinTransferCurrency(value.getCurrencyCode());
			setMinTransferAmt(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setCounterPartyThresholdAmount
	 */
	public void setCounterPartyThresholdAmount(Amount value) {
		if (null != value) {
//			System.out.println("setCounterPartyThresholdAmount.getAmountAsBigDecimal()="
//					+ value.getAmountAsBigDecimal());
			setCounterPartyThresholdAmt(formatAmount(value.getAmountAsBigDecimal()));
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setMbbThresholdAmount
	 */
	public void setMbbThresholdAmount(Amount value) {
		if (null != value) {
//			System.out.println("setMbbThresholdAmount.getAmountAsBigDecimal()=" + value.getAmountAsBigDecimal());
			setMbbThresholdAmt(formatAmount(value.getAmountAsBigDecimal()));
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setCounterPartyExtRating
	 */
	public void setCounterPartyExtRating(IExternalRating value) {
		setCounterPartyRatingType(null);
		setCounterPartyRating(null);

		if (null != value) {
			setCounterPartyRatingType(value.getRatingType());
			setCounterPartyRating(value.getRating());
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setMbbExtRating
	 */
	public void setMbbExtRating(IExternalRating value) {
		setMbbRatingType(null);
		setMbbRating(null);

		if (null != value) {
			setMbbRatingType(value.getRatingType());
			setMbbRating(value.getRating());
		}

	}

	private BigDecimal formatAmount(BigDecimal value) {

		DefaultLogger.debug(this, "amount=" + value);

		BigDecimal newVal = value.setScale(4, BigDecimal.ROUND_HALF_UP);

		DefaultLogger.debug(this, "after set scale, amount=" + newVal);
		return newVal;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setThresholdRatingList
	 */
	public void setThresholdRatingList(List value) {
		// do nothing
	}

	// ********************** Abstract Methods **********************

	// Getters
	public abstract Long getAgreementPK();

	public abstract Long getLimitProfileFK();

	public abstract String getAgreementType();

	public abstract String getCounterPartyBranch();

	public abstract String getMinTransferCurrency();

	public abstract BigDecimal getMinTransferAmt();

	public abstract String getEligibleMargin();

	public abstract String getAgreeIntRateType();

	public abstract String getCounterPartyRatingType();

	public abstract String getCounterPartyRating();

	public abstract String getMbbRatingType();

	public abstract String getMbbRating();

	public abstract String getBaseCurrency();

	public abstract BigDecimal getCounterPartyThresholdAmt();

	public abstract BigDecimal getMbbThresholdAmt();

	public abstract String getAgentBankName();

	public abstract String getAgentBankAddress();

	public abstract String getBankClearanceID();

	public abstract String getBankAccountID();

	public abstract String getClearingDesc();

	public abstract String getNotificationTime();

	public abstract String getValuationTime();

	public abstract String getStatus();

	public abstract long getVersionTime();

	// Setters

	public abstract void setAgreementPK(Long value);

	public abstract void setLimitProfileFK(Long value);

	public abstract void setAgreementType(String value);

	public abstract void setCounterPartyBranch(String value);

	public abstract void setMinTransferCurrency(String value);

	public abstract void setMinTransferAmt(BigDecimal value);

	public abstract void setCounterPartyRatingType(String value);

	public abstract void setCounterPartyRating(String value);

	public abstract void setMbbRatingType(String value);

	public abstract void setMbbRating(String value);

	public abstract void setEligibleMargin(String value);

	public abstract void setAgreeIntRateType(String value);

	public abstract void setBaseCurrency(String value);

	public abstract void setCounterPartyThresholdAmt(BigDecimal value);

	public abstract void setMbbThresholdAmt(BigDecimal value);

	public abstract void setAgentBankName(String value);

	public abstract void setAgentBankAddress(String value);

	public abstract void setBankClearanceID(String value);

	public abstract void setBankAccountID(String value);

	public abstract void setClearingDesc(String value);

	public abstract void setNotificationTime(String value);

	public abstract void setValuationTime(String value);

	public abstract void setStatus(String value);

	public abstract void setVersionTime(long versionTime);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a Trading Agreement
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITradingAgreement object
	 * @return Long the primary key
	 */
	public Long ejbCreate(long limitProfileID, ITradingAgreement value) throws CreateException {
		if (null == value) {
			throw new CreateException("ITradingAgreement is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == limitProfileID) {
			throw new CreateException("LimitProfileID is uninitialised!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating Trading Agreement with ID: " + pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setAgreementID(pk);
			setLimitProfileFK(new Long(limitProfileID));
			setVersionTime(VersionGenerator.getVersionNumber());

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post Create a Trading Agreement
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITradingAgreement object
	 */
	public void ejbPostCreate(long limitProfileID, ITradingAgreement value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ITradingAgreement
	 */
	public ITradingAgreement getValue() throws LimitException {

		try {
			OBTradingAgreement value = new OBTradingAgreement();
			AccessorUtil.copyValue(this, value);

			value.setThresholdRatingList(retrieveThresholdRatingList());

			return value;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type ITradingAgreement
	 * @throws LimitException on error
	 */
	public void setValue(ITradingAgreement value) throws LimitException, ConcurrentUpdateException {
		long beanVer = value.getVersionTime();
		long currentVer = getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("Version mismatch!");
		}
		try {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setVersionTime(VersionGenerator.getVersionNumber());
			updateThresholdRatingList(value.getThresholdRatingList());
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.EBTradingAgreement#setStatusDeleted
	 */
	public void setStatusDeleted(ITradingAgreement value) throws LimitException, ConcurrentUpdateException {
		checkVersionMismatch(value);
		setStatus(ICMSConstant.STATE_DELETED);
		setVersionTime(VersionGenerator.getVersionNumber());

		removeThresholdRating(value.getThresholdRatingList());

	}

	/**
	 * Check the version of this limit profile.
	 * 
	 * @param value of type ITradingAgreement
	 * @throws ConcurrentUpdateException if the entity version is invalid
	 */
	private void checkVersionMismatch(ITradingAgreement value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.EBTradingAgreement#createDependants
	 */
	public void createDependants(ITradingAgreement value, long verTime) throws LimitException,
			ConcurrentUpdateException {
		if (verTime != getVersionTime()) {
			throw new ConcurrentUpdateException("Version mismatched!");
		}
		else {
			updateThresholdRatingList(value.getThresholdRatingList());

		}
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() throws RemoveException, EJBException {
		try {

			// remove threshold rating
			EBThresholdRatingLocalHome home = getEBLocalHomeThresholdRating();
			Collection c = home.findByAgreement(new Long(getAgreementID()), EMPTY_STR);

			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBThresholdRatingLocal local = (EBThresholdRatingLocal) i.next();
				i.remove(); // remove this local interface from the collection
			}

		}
		catch (FinderException e) {
			// do nothing
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException("Exception during cascade delete:" + e.toString());
		}

	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	/**
	 * Method to update threshold rating. A delete-insert will be performed.
	 * This method is not implemented via CMR due to time constraint.
	 */
	private void updateThresholdRatingList(List entries) throws LimitException {
		try {
			EBThresholdRatingLocalHome home = getEBLocalHomeThresholdRating();
			Collection c = home.findByAgreement(new Long(getAgreementID()), EXCLUDE_STATUS);

			// delete all
			if (null != c) {
				int count = 0;
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBThresholdRatingLocal local = (EBThresholdRatingLocal) i.next();

					local.remove();
					count++;
				}
				DefaultLogger.debug(this, "Number of ThresholdRating deleted: " + count);
			}

			// insert all
			if (null != entries) {
				long agreementID = getAgreementID();
				int count = 0;
				for (int i = 0; i < entries.size(); i++) {
					home.create(agreementID, (IThresholdRating) entries.get(i));
					count++;
				}
				DefaultLogger.debug(this, "Number of ThresholdRating inserted: " + count);
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception: " + e.toString());
		}
	}

	private void removeThresholdRating(List entries) throws LimitException {
		try {
			EBThresholdRatingLocalHome home = getEBLocalHomeThresholdRating();

			if (null != entries) {
				long agreementID = getAgreementID();
				int count = 0;
				for (int i = 0; i < entries.size(); i++) {
					IThresholdRating obRate = (IThresholdRating) entries.get(i);
					EBThresholdRatingLocal local = home.findByPrimaryKey(new Long(obRate.getThresholdRatingID()));

					local.setStatusDeleted(obRate);
					count++;
				}
				DefaultLogger.debug(this, "Number of ThresholdRating deleted: " + count);
			}

		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Method to retrieve threshold rating.
	 * 
	 * @return List of IThresholdRating
	 * @throws LimitException on errors
	 */
	private List retrieveThresholdRatingList() throws LimitException {
		try {
			EBThresholdRatingLocalHome home = getEBLocalHomeThresholdRating();
			Collection c = home.findByAgreement(new Long(getAgreementID()), "");
			DefaultLogger.debug(this, "retrieveThresholdRatingList, getAgreementID()=" + getAgreementID());
			DefaultLogger.debug(this, "retrieveThresholdRatingList, c.size()=" + c.size());

			Iterator i = c.iterator();
			ArrayList aList = new ArrayList(c.size());
			while (i.hasNext()) {
				EBThresholdRatingLocal local = (EBThresholdRatingLocal) i.next();
				IThresholdRating entry = local.getValue();
				DefaultLogger.debug(this, "retrieveThresholdRatingList, entry=" + entry);
				aList.add(entry);
			}
			return aList;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (FinderException e) {
			return null;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to get EB Local Home for EBThresholdRating
	 * 
	 * @return EBThresholdRatingLocalHome
	 * @throws LimitException on errors
	 */
	protected EBThresholdRatingLocalHome getEBLocalHomeThresholdRating() throws LimitException {
		EBThresholdRatingLocalHome home = (EBThresholdRatingLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_THRESHOLD_RATING_LOCAL_JNDI, EBThresholdRatingLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBThresholdRatingLocalHome is null!");
		}
	}

}