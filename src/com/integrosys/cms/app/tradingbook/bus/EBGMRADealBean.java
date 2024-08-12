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
 * Entity bean implementation for GMRA Deal entity.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBGMRADealBean implements IGMRADeal, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the GMRA Deal. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getCMSDealID", "getDealID", "getAgreementID" };

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

	public abstract String getSecDesc();

	public abstract void setSecDesc(String secDesc);

	public abstract String getISINCode();

	public abstract void setISINCode(String iSINCode);

	public abstract String getCUSIPCode();

	public abstract void setCUSIPCode(String cusipCode);

	public abstract String getDealCurrency();

	public abstract void setDealCurrency(String tradePriceCurrency);

	public abstract BigDecimal getDealAmt();

	public abstract void setDealAmt(BigDecimal dealAmt);

	public abstract Double getHaircut();

	public abstract void setHaircut(Double haircut);

	public abstract String getNotionalCurrency();

	public abstract void setNotionalCurrency(String notionalCurrency);

	public abstract BigDecimal getNotional();

	public abstract void setNotional(BigDecimal notional);

	public abstract Date getTradeDate();

	public abstract void setTradeDate(Date tenorStartDate);

	public abstract Date getMaturityDate();

	public abstract void setMaturityDate(Date maturityDate);

	public abstract String getDealCountry();

	public abstract void setDealCountry(String dealCountry);

	public abstract String getDealBranch();

	public abstract void setDealBranch(String dealBranch);

	public abstract Double getDealRate();

	public abstract void setDealRate(Double dealRate);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getNPVBaseCurrency();

	public abstract void setNPVBaseCurrency(String nPVBaseCurrency);

	public abstract BigDecimal getNPVBase();

	public abstract void setNPVBase(BigDecimal nPVBase);

	public abstract BigDecimal getRepoStartAmt();

	public abstract void setRepoStartAmt(BigDecimal repoStartAmt);

	public abstract BigDecimal getRepoEndAmt();

	public abstract void setRepoEndAmt(BigDecimal repoEndAmt);

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getCMSDealID
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
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setCMSDealID
	 */
	public void setCMSDealID(long value) {
		setDealIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getAgreementID
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
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setAgreementID
	 */
	public void setAgreementID(long value) {
		setAgreementIDFK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getDealAmount
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
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setDealAmount
	 */
	public void setDealAmount(Amount value) {
		if (null != value) {
			setDealCurrency(value.getCurrencyCode());
			setDealAmt(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#getNotionalAmount
	 */
	public Amount getNotionalAmount() {
		String ccy = getNotionalCurrency();
		BigDecimal amt = getNotional();

		if ((amt != null) && (ccy != null)) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IGMRADeal#setNotionalAmount
	 */
	public void setNotionalAmount(Amount value) {
		if (null != value) {
			setNotionalCurrency(value.getCurrencyCode());
			setNotional(value.getAmountAsBigDecimal());
		}

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBGMRADeal#getValue
	 */
	public IGMRADeal getValue() {
		OBGMRADeal deal = new OBGMRADeal();
		AccessorUtil.copyValue(this, deal);
		return deal;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBGMRADeal#setValue
	 */
	public void setValue(IGMRADeal deal) throws VersionMismatchException {
		checkVersionMismatch(deal);
		AccessorUtil.copyValue(deal, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBGMRADeal#setStatusDeleted
	 */
	public void setStatusDeleted(IGMRADeal deal) throws VersionMismatchException {
		checkVersionMismatch(deal);
		setStatus(ICMSConstant.STATE_DELETED);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.EBGMRADeal#setNPVBaseValue
	 */
	public void setNPVBaseValue(IDealValuation dealVal) throws VersionMismatchException {
		Amount value = dealVal.getMarketValue();
		if (null != value) {
			setNPVBaseCurrency(value.getCurrencyCode());
			setNPVBase(value.getAmountAsBigDecimal());
		}
		else {
			setNPVBaseCurrency(null);
			setNPVBase(null);
		}
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this GMRA Deal.
	 * 
	 * @param gmraDeal of type IGMRADeal
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IGMRADeal gmraDeal) throws VersionMismatchException {
		if (getVersionTime() != gmraDeal.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + gmraDeal.getVersionTime());
		}
	}

	/**
	 * Get the sequence of primary key for this GMRA Deal.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_TB_DEAL;
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param agreementID of long
	 * @param gmraDeal of type IGMRADeal
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(long agreementID, IGMRADeal gmraDeal) throws CreateException {
		if (null == gmraDeal) {
			throw new CreateException("IGMRADeal is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == agreementID) {
			throw new CreateException("Agreement ID is uninitialised!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating GMRA Deal with ID: " + pk);
			setDealIDPK(new Long(pk));
			setDealID(String.valueOf(pk));

			AccessorUtil.copyValue(gmraDeal, this, EXCLUDE_METHOD);
			setAgreementIDFK(new Long(agreementID));
			setAgreementType(ICMSConstant.GMRA_TYPE);

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
	 * @param gmraDeal of type IGMRADeal
	 */
	public void ejbPostCreate(long agreementID, IGMRADeal gmraDeal) {
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