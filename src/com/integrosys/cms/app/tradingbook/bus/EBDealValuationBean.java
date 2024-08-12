/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for Deal Valuation entity.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBDealValuationBean implements IDealValuation, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the Deal Valuation. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getDealValuationID", "getCMSDealID" };

	public abstract Long getDealValIDPK();

	public abstract void setDealValIDPK(Long dealIDPK);

	public abstract Long getDealIDFK();

	public abstract void setDealIDFK(Long dealIDFK);

	public abstract BigDecimal getMarketValueAmt();

	public abstract void setMarketValueAmt(BigDecimal marketValueAmount);

	public abstract String getMarketValueCurrency();

	public abstract void setMarketValueCurrency(String marketValueCurrency);

	public abstract long getGroupID();

	public abstract void setGroupID(long groupID);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getDealValuationID
	 */
	public long getDealValuationID() {
		if (null != getDealValIDPK()) {
			return getDealValIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setDealValuationID
	 */
	public void setDealValuationID(long value) {
		setDealValIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getCMSDealID
	 */
	public long getCMSDealID() {
		if (null != getDealIDFK()) {
			return getDealIDFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setCMSDealID
	 */
	public void setCMSDealID(long value) {
		setDealIDFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getMarketValue
	 */
	public Amount getMarketValue() {
		String ccy = getMarketValueCurrency();
		BigDecimal amt = getMarketValueAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setMarketValue
	 */
	public void setMarketValue(Amount value) {
		if (null != value) {
			setMarketValueCurrency(value.getCurrencyCode());
			setMarketValueAmt(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBDealValuation#getValue
	 */
	public IDealValuation getValue() {
		OBDealValuation isdaDeal = new OBDealValuation();
		AccessorUtil.copyValue(this, isdaDeal);
		return isdaDeal;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBDealValuation#setValue
	 */
	public void setValue(IDealValuation dealVal) throws VersionMismatchException {
		checkVersionMismatch(dealVal);
		AccessorUtil.copyValue(dealVal, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBDealValuation#setDealMarketValue
	 */
	public void setDealMarketValue(IDealValuation dealVal) throws VersionMismatchException {
		checkVersionMismatch(dealVal);

		setMarketValue(dealVal.getMarketValue());
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBDealValuation#setGroupIDValue
	 */
	public void setGroupIDValue(IDealValuation dealVal) throws VersionMismatchException {
		checkVersionMismatch(dealVal);

		if (dealVal.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
			setGroupID(getDealValuationID());
		}
		else {
			setGroupID(dealVal.getGroupID());
		}
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBDealValuation#setStatusDeleted
	 */
	public void setStatusDeleted(IDealValuation dealVal) throws VersionMismatchException {
		setStatus(ICMSConstant.STATE_DELETED);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Get the sequence of primary key for this Deal Valuation.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_DEAL_VAL;
	}

	/**
	 * Check the version of this ISDA Deal.
	 * 
	 * @param dealVal of type IDealValuation
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IDealValuation dealVal) throws VersionMismatchException {
		if (getVersionTime() != dealVal.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + dealVal.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param cmsDealID of long
	 * @param value of type IDealValuation
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(long cmsDealID, IDealValuation value) throws CreateException {
		if (null == value) {
			throw new CreateException("IDealValuation is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == cmsDealID) {
			throw new CreateException("cmsDealID is uninitialised!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating deal valuation with ID: " + pk);
			setDealValIDPK(new Long(pk));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setDealIDFK(new Long(cmsDealID));
			setVersionTime(VersionGenerator.getVersionNumber());

			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param cmsDealID of long
	 * @param value of type IDealValuation
	 */
	public void ejbPostCreate(long cmsDealID, IDealValuation value) {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}
}