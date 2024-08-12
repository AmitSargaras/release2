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
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Entity bean implementation for ISDA CSA Deal entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBISDACSADealBean implements IISDACSADeal, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the ISDA CSA Deal. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getDealID", "getAgreementID" };

	public abstract Long getDealIDPK();

	public abstract void setDealIDPK(Long dealIDPK);

	public abstract Long getAgreementIDFK();

	public abstract void setAgreementIDFK(Long agreementIDFK);

	public abstract String getAgreementType();

	public abstract void setAgreementType(String agreementType);

	public abstract String getDealID();

	public abstract void setDealID(String dealId);

	public abstract String getProductType();

	public abstract void setProductType(String productType);

	public abstract Date getMaturityDate();

	public abstract void setMaturityDate(Date maturityDate);

	public abstract Date getValueDate();

	public abstract void setValueDate(Date valueDate);

	public abstract Date getTradeDate();

	public abstract void setTradeDate(Date tradeDate);

	public abstract BigDecimal getNotional();

	public abstract void setNotional(BigDecimal notional);

	public abstract String getNPVCurrency();

	public abstract void setNPVCurrency(String nPVCurrency);

	public abstract BigDecimal getNPV();

	public abstract void setNPV(BigDecimal nPV);

	public abstract String getNPVBaseCurrency();

	public abstract void setNPVBaseCurrency(String nPVBaseCurrency);

	public abstract BigDecimal getNPVBase();

	public abstract void setNPVBase(BigDecimal nPVBase);

	public abstract String getDealCurrency();

	public abstract void setDealCurrency(String dealCurrency);

	public abstract BigDecimal getDealAmt();

	public abstract void setDealAmt(BigDecimal dealAmount);

	public abstract BigDecimal getNearAmt();

	public abstract void setNearAmt(BigDecimal nearAmount);

	public abstract BigDecimal getFarAmt();

	public abstract void setFarAmt(BigDecimal farAmount);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getCMSDealID
	 */
	public long getCMSDealID() {
		if (null != getDealIDPK()) {
			return getDealIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setCMSDealID
	 */
	public void setCMSDealID(long value) {
		setDealIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getAgreementID
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
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setAgreementID
	 */
	public void setAgreementID(long value) {
		setAgreementIDFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNPVAmount
	 */
	public Amount getNPVAmount() {
		String ccy = getNPVCurrency();
		BigDecimal amt = getNPV();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNPVAmount
	 */
	public void setNPVAmount(Amount value) {
		if (null != value) {
			setNPVCurrency(value.getCurrencyCode());
			setNPV(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNPVBaseAmount
	 */
	public Amount getNPVBaseAmount() {
		String ccy = getNPVBaseCurrency();
		BigDecimal amt = getNPVBase();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNPVBaseAmount
	 */
	public void setNPVBaseAmount(Amount value) {
		if (null != value) {
			setNPVBaseCurrency(value.getCurrencyCode());
			setNPVBase(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getDealAmount
	 */
	public Amount getDealAmount() {
		String ccy = getDealCurrency();
		BigDecimal amt = getDealAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setDealAmount
	 */
	public void setDealAmount(Amount value) {
		if (null != value) {
			setDealCurrency(value.getCurrencyCode());
			setDealAmt(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNotionalAmount
	 */
	public Amount getNotionalAmount() {
		String ccy = getDealCurrency();
		BigDecimal amt = getNotional();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNotionalAmount
	 */
	public void setNotionalAmount(Amount value) {
		if (null != value) {
			setNotional(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNearAmount
	 */
	public Amount getNearAmount() {
		String ccy = getDealCurrency();
		BigDecimal amt = getNearAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNearAmount
	 */
	public void setNearAmount(Amount value) {
		if (null != value) {
			setNearAmt(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getFarAmount
	 */
	public Amount getFarAmount() {
		String ccy = getDealCurrency();
		BigDecimal amt = getFarAmt();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setFarAmount
	 */
	public void setFarAmount(Amount value) {
		if (null != value) {
			setFarAmt(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBISDACSADeal#getValue
	 */
	public IISDACSADeal getValue() {
		OBISDACSADeal isdaDeal = new OBISDACSADeal();
		AccessorUtil.copyValue(this, isdaDeal);
		return isdaDeal;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBISDACSADeal#setValue
	 */
	public void setValue(IISDACSADeal isdaDeal) throws VersionMismatchException {
		checkVersionMismatch(isdaDeal);
		AccessorUtil.copyValue(isdaDeal, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBISDACSADeal#setNPVBaseValue
	 */
	public void setNPVBaseValue(IDealValuation isdaDeal, Amount NPVRefAmt) throws VersionMismatchException {
		setNPVBaseAmount(isdaDeal.getMarketValue());
		setNPVAmount(NPVRefAmt);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this ISDA Deal.
	 * 
	 * @param isdaDeal of type IISDACSADeal
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IISDACSADeal isdaDeal) throws VersionMismatchException {
		if (getVersionTime() != isdaDeal.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + isdaDeal.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param isdaDeal of type IISDACSADeal
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IISDACSADeal isdaDeal) throws CreateException {
		// do nothing, cannot create thru UI
		return null;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param isdaDeal of type IISDACSADeal
	 */
	public void ejbPostCreate(IISDACSADeal isdaDeal) {
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