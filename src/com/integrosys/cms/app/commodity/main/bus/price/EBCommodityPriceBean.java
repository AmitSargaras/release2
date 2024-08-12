/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/EBCommodityPriceBean.java,v 1.4 2006/09/13 01:30:26 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

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
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for Commodity Price entity.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/13 01:30:26 $ Tag: $Name: $
 */
public abstract class EBCommodityPriceBean implements ICommodityPrice, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getCommodityPriceID", "getCommodityProfile" };

	/**
	 * Get commodity price id.
	 * 
	 * @return long
	 */
	public long getCommodityPriceID() {
		return getEBCommodityPriceID().longValue();
	}

	/**
	 * Set commodity price id.
	 * 
	 * @param commodityPriceID of type long
	 */
	public void setCommodityPriceID(long commodityPriceID) {
		setEBCommodityPriceID(new Long(commodityPriceID));
	}

	/**
	 * Get commodity profile id.
	 * 
	 * @return long
	 */
	public long getProfileID() {
		if (getEBProfileID() == null) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		return getEBProfileID().longValue();
	}

	/**
	 * Set commodity profile id.
	 * 
	 * @param profileID of type long
	 */
	public void setProfileID(long profileID) {
		if (profileID == ICMSConstant.LONG_INVALID_VALUE) {
			setEBProfileID(null);
		}
		else {
			setEBProfileID(new Long(profileID));
		}
	}

	/**
	 * Get close price.
	 * 
	 * @return Amount
	 */
	public Amount getClosePrice() {
		if (getEBClosePrice() == null) {
			return null;
		}
		return new Amount(getEBClosePrice(), new CurrencyCode(getClosePriceCcyCode()));
	}

	/**
	 * Set close price.
	 * 
	 * @param closePrice of type Amount
	 */
	public void setClosePrice(Amount closePrice) {
		setEBClosePrice(closePrice == null ? null : closePrice.getAmountAsBigDecimal());
		setClosePriceCcyCode(closePrice == null ? null : closePrice.getCurrencyCode());
	}

	/**
	 * Get current price.
	 * 
	 * @return Amount
	 */
	public Amount getCurrentPrice() {
		if (getEBCurrentPrice() == null) {
			return null;
		}
		return new Amount(getEBCurrentPrice(), new CurrencyCode(getCurrentPriceCcyCode()));
	}

	/**
	 * Set current price.
	 * 
	 * @param currentPrice of type Amount
	 */
	public void setCurrentPrice(Amount currentPrice) {
		setEBCurrentPrice(currentPrice == null ? null : currentPrice.getAmountAsBigDecimal());
		setCurrentPriceCcyCode(currentPrice == null ? null : currentPrice.getCurrencyCode());
	}

	public abstract Long getEBCommodityPriceID();

	public abstract void setEBCommodityPriceID(Long eBCommodityPriceID);

	public abstract Long getEBProfileID();

	public abstract void setEBProfileID(Long eBProfileID);

	public abstract BigDecimal getEBClosePrice();

	public abstract void setEBClosePrice(BigDecimal eBClosePrice);

	public abstract BigDecimal getEBCurrentPrice();

	public abstract void setEBCurrentPrice(BigDecimal eBCurrentPrice);

	public abstract String getClosePriceCcyCode();

	public abstract void setClosePriceCcyCode(String closePriceCcyCode);

	public abstract String getCurrentPriceCcyCode();

	public abstract void setCurrentPriceCcyCode(String currentPriceCcyCode);

	/**
	 * Get commodity profile information.
	 * 
	 * @return IProfile
	 */
	public IProfile getCommodityProfile() {
		return null;
	}

	/**
	 * Set commodity profile information.
	 * 
	 * @param commodityProfile of type IProfile
	 */
	public void setCommodityProfile(IProfile commodityProfile) {
	}

	/**
	 * Get the commodity price business object.
	 * 
	 * @return commodity price
	 */
	public ICommodityPrice getValue() throws CommodityException {
		OBCommodityPrice price = new OBCommodityPrice();
		AccessorUtil.copyValue(this, price);
		return price;
	}

	/**
	 * Set the commodity price to this entity.
	 * 
	 * @param price is of type ICommodityPrice
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	public void setValue(ICommodityPrice price) throws VersionMismatchException {
		checkVersionMismatch(price);
		AccessorUtil.copyValue(price, this, getExcludeMethods());
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param price of type ICommodityPrice
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICommodityPrice price) throws CreateException {
		try {
			String priceID = (new SequenceManager()).getSeqNum(getSequenceName(), true);
			AccessorUtil.copyValue(price, this, getExcludeMethods());
			setEBCommodityPriceID(new Long(priceID));

			if (price.getGroupID() == ICMSConstant.LONG_INVALID_VALUE) {
				setGroupID(Long.parseLong(priceID));
			}
			Date today = new Date();
			if (price.getCloseFirstUpdateDate() == null) {
				setCloseFirstUpdateDate(today);
			}
			if (price.getCurrentFirstUpdateDate() == null) {
				setCurrentFirstUpdateDate(today);
			}
			setVersionTime(VersionGenerator.getVersionNumber());
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * Get the name of the sequence to be used for the price id.
	 * 
	 * @return name of the commodity price sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_COMMODITY_PRICE;
	}

	/**
	 * Get methods to be excluded.
	 * 
	 * @return a list of methods to be excluded
	 */
	protected String[] getExcludeMethods() {
		return EXCLUDE_METHOD;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param price of type ICommodityPrice
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ICommodityPrice price) throws CreateException {
	}

	/**
	 * Check the version of this commodity price against the backend version.
	 * 
	 * @param price commodity price's version to be checked
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	private void checkVersionMismatch(ICommodityPrice price) throws VersionMismatchException {
		if (getVersionTime() != price.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + price.getVersionTime());
		}
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
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