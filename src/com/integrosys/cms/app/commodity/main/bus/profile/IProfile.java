/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/IProfile.java,v 1.6 2006/03/03 05:01:04 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 10:00:52 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IProfile extends ICommodityMainInfo, Serializable {
	public static final String PRICE_TYPE_FUTURES = "f";

	public static final String PRICE_TYPE_CASH = "c";

	public static final String PRICE_TYPE_NOC_RIC = "n";

	public static final String RIC_TYPE_FUTURES = "f";

	public static final String RIC_TYPE_OPTIONS = "o";

	public static final String RIC_TYPE_CASH = "c";

	public static final String RIC_TYPE_NON = PRICE_TYPE_NOC_RIC;

	public long getProfileID();

	public String getCategory();

	public String getProductType();

	public String getProductSubType();

	/**
	 * 
	 * @return String representing one of signs from
	 *         ICommodityConstant.SIGN_[<actual sign>] example "pl" represents
	 *         ICommodityConstant.SING_PLUS. Likewise, SIGN_MINUS and
	 *         SIGN_PLUS_OR_MINUS exist.
	 */
	public String getDifferentialSign();

	public BigDecimal getPriceDifferential();

	public ISupplier[] getSuppliers();

	public IBuyer[] getBuyers();

	public String getMarketName();

	/**
	 * 
	 * @return IProfile.PRICE_TYPE_FUTURES or IProfile.PRICE_TYPE_CASH.
	 */
	public String getPriceType();

	/**
	 * @return IProfile.RIC_TYPE_FUTURES or IProfile.RIC_TYPE_OPTIONS.
	 */
	public String getRICType();

	public String getReuterSymbol();

	public String getCountryArea();

	public String getChains();

	public String getOutrights();

	/**
	 * gets the groupsID
	 * 
	 * @return
	 */
	public long getGroupID();

	/**
	 * gets the common Ref. This is a common reference to map to the
	 * corresponding staging record.
	 * 
	 * @return
	 */
	public long getCommonRef();

	/**
	 * Get unit price.
	 * 
	 * @return Amount
	 */
	public Amount getUnitPrice();

	/**
	 * Set unit price.
	 * 
	 * @param unitPrice of type Amount
	 */
	public void setUnitPrice(Amount unitPrice);

	/**
	 * Get unit price date.
	 * 
	 * @return Date
	 */
	public Date getUnitPriceDate();

	/**
	 * Set unit price date.
	 * 
	 * @param unitPriceDate of type Date
	 */
	public void setUnitPriceDate(Date unitPriceDate);

	public String getNonRICDesc();

	public void setNonRICDesc(String nonRICDesc);
}
