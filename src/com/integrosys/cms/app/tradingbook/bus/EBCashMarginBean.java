/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;
import java.util.Date;

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
 * Entity bean implementation for cash margin entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBCashMarginBean implements ICashMargin, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the cash margin. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getCashMarginID", "getAgreementID" };

	public abstract Long getCashMarginIDPK();

	public abstract void setCashMarginIDPK(Long cashMarginIDPK);

	public abstract Long getAgreementIDFK();

	public abstract void setAgreementIDFK(Long agreementIDFK);

	public abstract Date getTrxDate();

	public abstract void setTrxDate(Date trxDate);

	public abstract String getNAPCurrency();

	public abstract void setNAPCurrency(String nAPCurrency);

	public abstract BigDecimal getNAP();

	public abstract void setNAP(BigDecimal nAP);

	public abstract String getNAPSignAdd();

	public abstract void setNAPSignAdd(String nAPSignAdd);

	public abstract BigDecimal getCashInterest();

	public abstract void setCashInterest(BigDecimal nAI);

	public abstract long getGroupID();

	public abstract void setGroupID(long groupID);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getCashMarginID
	 */
	public long getCashMarginID() {
		if (null != getCashMarginIDPK()) {
			return getCashMarginIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setCashMarginID
	 */
	public void setCashMarginID(long value) {
		setCashMarginIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getAgreementID
	 */
	public long getAgreementID() {
		if (null != getAgreementIDFK()) {
			return getAgreementIDFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setAgreementID
	 */
	public void setAgreementID(long value) {
		setAgreementIDFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getNAPAmount
	 */
	public Amount getNAPAmount() {
		String ccy = getNAPCurrency();
		BigDecimal amt = getNAP();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setNAPAmount
	 */
	public void setNAPAmount(Amount value) {
		if (null != value) {
			setNAPCurrency(value.getCurrencyCode());
			setNAP(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getNAPSignAddInd
	 */
	public boolean getNAPSignAddInd() {
		String ind = getNAPSignAdd();
		if ((ind != null) && ind.trim().equals("Y")) {

			return true;
		}
		return false;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setNAPSignAddInd
	 */
	public void setNAPSignAddInd(boolean value) {
		if (value) {
			setNAPSignAdd("Y");
		}
		else {
			setNAPSignAdd("N");
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getUpdateIndicator
	 */
	public String getUpdateIndicator() {
		return "";
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setUpdateIndicator
	 */
	public void setUpdateIndicator(String value) {
		// do nothing
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getValue
	 */
	public ICashMargin getValue() {
		OBCashMargin cashMargin = new OBCashMargin();
		AccessorUtil.copyValue(this, cashMargin);
		return cashMargin;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBCashMargin#setValue
	 */
	public void setValue(ICashMargin cashMargin) throws VersionMismatchException {
		checkVersionMismatch(cashMargin);
		AccessorUtil.copyValue(cashMargin, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBCashMargin#setNAPValue
	 */
	public void setNAPValue(ICashMargin cashMargin) throws VersionMismatchException {
		checkVersionMismatch(cashMargin);

		setNAPAmount(cashMargin.getNAPAmount());
		setNAPSignAddInd(cashMargin.getNAPSignAddInd());
		setCashInterest(cashMargin.getCashInterest());
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBCashMargin#setStatusDeleted
	 */
	public void setStatusDeleted(ICashMargin cashMargin) throws VersionMismatchException {
		checkVersionMismatch(cashMargin);
		setStatus(ICMSConstant.STATE_DELETED);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this cash margin.
	 * 
	 * @param cashMargin of type ICashMargin
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(ICashMargin cashMargin) throws VersionMismatchException {
		if (getVersionTime() != cashMargin.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + cashMargin.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param agreementID agreement ID
	 * @param cashMargin of type ICashMargin
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(long agreementID, ICashMargin cashMargin) throws CreateException {
		if (null == cashMargin) {
			throw new CreateException("ICashMargin is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == agreementID) {
			throw new CreateException("Agreement ID is uninitialised!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CASH_MARGIN, true));
			DefaultLogger.debug(this, "Creating CASH MARGIN with ID: " + pk);
			setCashMarginIDPK(new Long(pk));

			AccessorUtil.copyValue(cashMargin, this, EXCLUDE_METHOD);
			setAgreementIDFK(new Long(agreementID));
			setGroupID(agreementID);
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
	 * @param cashMargin of type ICashMargin
	 */
	public void ejbPostCreate(long agreementID, ICashMargin cashMargin) {
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