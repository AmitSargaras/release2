/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for Threshold Rating.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBThresholdRatingBean implements EntityBean, IThresholdRating {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_THRESHOLD_RATING;

	private static final String[] EXCLUDE_METHOD = new String[] { "getThresholdRatingID", "getAgreementID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBThresholdRatingBean() {
	}

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getThresholdRatingID
	 */
	public long getThresholdRatingID() {
		if (null != getRatingPK()) {
			return getRatingPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setThresholdRatingID
	 */
	public void setThresholdRatingID(long value) {
		setRatingPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getAgreementID
	 */
	public long getAgreementID() {
		if (null != getAgreementFK()) {
			return getAgreementFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setAgreementID
	 */
	public void setAgreementID(long value) {
		setAgreementFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#getThresholdAmount
	 */
	public Amount getThresholdAmount() {
		String ccy = getThresholdCurrency();
		BigDecimal amt = getThresholdAmt();
		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IThresholdRating#setThresholdAmount
	 */
	public void setThresholdAmount(Amount value) {
		if (null != value) {
			setThresholdCurrency(value.getCurrencyCode());
			setThresholdAmt(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.EBThresholdRating#setStatusDeleted
	 */
	public void setStatusDeleted(IThresholdRating value) throws ConcurrentUpdateException {
		checkVersionMismatch(value);
		setStatus(ICMSConstant.STATE_DELETED);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this limit profile.
	 * 
	 * @param value of type IThresholdRating
	 * @throws ConcurrentUpdateException if the entity version is invalid
	 */
	private void checkVersionMismatch(IThresholdRating value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	// ********************** Abstract Methods **********************

	// Getters

	public abstract Long getRatingPK();

	public abstract Long getAgreementFK();

	public abstract String getRatingType();

	public abstract String getRating();

	public abstract String getThresholdCurrency();

	public abstract BigDecimal getThresholdAmt();

	public abstract String getStatus();

	public abstract long getVersionTime();

	// Setters
	public abstract void setRatingPK(Long value);

	public abstract void setAgreementFK(Long value);

	public abstract void setRatingType(String value);

	public abstract void setRating(String value);

	public abstract void setThresholdCurrency(String value);

	public abstract void setThresholdAmt(BigDecimal value);

	public abstract void setStatus(String value);

	public abstract void setVersionTime(long versionTime);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a Threshold Rating
	 * 
	 * @param agreementID is the agreement ID in long value
	 * @param value is the IThresholdRating object
	 * @return Long the primary key
	 */
	public Long ejbCreate(long agreementID, IThresholdRating value) throws CreateException {
		if (null == value) {
			throw new CreateException("IThresholdRating is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == agreementID) {
			throw new CreateException("agreementID is uninitialised!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating Threshold Rating with ID: " + pk);
			DefaultLogger.debug(this, "Creating agreementID: " + agreementID);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setThresholdRatingID(pk);
			setAgreementFK(new Long(agreementID));
			setVersionTime(VersionGenerator.getVersionNumber());

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post Create a Threshold Rating
	 * 
	 * @param agreementID is the agreement ID in long value
	 * @param value is the IThresholdRating object
	 */
	public void ejbPostCreate(long agreementID, IThresholdRating value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return IThresholdRating
	 */
	public IThresholdRating getValue() {
		OBThresholdRating value = new OBThresholdRating(getRatingType());
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type IThresholdRating
	 * @throws LimitException on error
	 */
	public void setValue(IThresholdRating value) throws LimitException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		}
		else {
			throw new LimitException("IThresholdRating is null!");
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
}