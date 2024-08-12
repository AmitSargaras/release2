/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/EBCommodityPriceHistoryBean.java,v 1.5 2006/09/13 01:30:26 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.CreateException;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for Commodity Price History entity.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/13 01:30:26 $ Tag: $Name: $
 */
public abstract class EBCommodityPriceHistoryBean extends EBCommodityPriceBean {
	/** A list of methods to be excluded. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getCommodityPriceID" };

	/**
	 * Get the name of the sequence to be used for the price id.
	 * 
	 * @return name of the commodity price history sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_FEED_PRICE_FEED_HISTORY;
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
		if (commodityProfile != null) {
			setRIC(commodityProfile.getReuterSymbol());
		}
	}

	/**
	 * Set the commodity price to this entity.
	 * 
	 * @param price is of type ICommodityPrice
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	public void setValue(ICommodityPrice price) throws VersionMismatchException {
		// history is never to be updated!
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param price of type ICommodityPrice
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICommodityPrice price) throws CreateException {
		super.ejbCreate(price);
		setFeedGroupType(ICMSConstant.COMMODITY_FEED_GROUP_TYPE);
		return null;
	}

	public Date getCloseFirstUpdateDate() {
		return null;
	}

	public void setCloseFirstUpdateDate(Date firstUDate) {
	}

	public Date getCurrentFirstUpdateDate() {
		return null;
	}

	public void setCurrentFirstUpdateDate(Date firstUDate) {
	}

	public Date getCurrentUpdateDate() {
		return null;
	}

	public void setCurrentUpdateDate(Date currentUpdateDate) {
	}

	public BigDecimal getEBCurrentPrice() {
		return null;
	}

	public void setEBCurrentPrice(BigDecimal eBCurrentPrice) {
	}

	public String getCurrentPriceCcyCode() {
		return null;
	}

	public void setCurrentPriceCcyCode(String currentPriceCcyCode) {
	}

	public String getPriceUOM() {
		return null;
	}

	public void setPriceUOM(String priceUOM) {
	}

	public String getStatus() {
		return null;
	}

	public void setStatus(String status) {
	}

	public abstract String getRIC();

	public abstract void setRIC(String ric);

	public abstract String getFeedGroupType();

	public abstract void setFeedGroupType(String feedGroupType);
}