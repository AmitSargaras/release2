/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/ICommodityPrice.java,v 1.4 2006/09/13 01:30:26 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

/**
 * This interface represents Commodity Pricy entity.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/13 01:30:26 $ Tag: $Name: $
 */
public interface ICommodityPrice extends ICommodityMainInfo {
	/**
	 * Get commodity price id.
	 * 
	 * @return long
	 */
	public long getCommodityPriceID();

	/**
	 * Set commodity price id.
	 * 
	 * @param commodityPriceID of type long
	 */
	public void setCommodityPriceID(long commodityPriceID);

	/**
	 * Get group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get commodity profile id.
	 * 
	 * @return long
	 */
	public long getProfileID();

	/**
	 * Set commodity profile id.
	 * 
	 * @param profileID of type long
	 */
	public void setProfileID(long profileID);

	/**
	 * Get close price.
	 * 
	 * @return Amount
	 */
	public Amount getClosePrice();

	/**
	 * Set close price.
	 * 
	 * @param closePrice of type Amount
	 */
	public void setClosePrice(Amount closePrice);

	/**
	 * Set close price update date.
	 * 
	 * @return Date
	 */
	public Date getCloseUpdateDate();

	/**
	 * Set close price update date.
	 * 
	 * @param closeUpdateDate of type Date
	 */
	public void setCloseUpdateDate(Date closeUpdateDate);

	/**
	 * Get current price.
	 * 
	 * @return Amount
	 */
	public Amount getCurrentPrice();

	/**
	 * Set current price.
	 * 
	 * @param currentPrice of type Amount
	 */
	public void setCurrentPrice(Amount currentPrice);

	/**
	 * Get currenty price update date.
	 * 
	 * @return Date
	 */
	public Date getCurrentUpdateDate();

	/**
	 * Set current price update date.
	 * 
	 * @param currentUpdateDate of type Date
	 */
	public void setCurrentUpdateDate(Date currentUpdateDate);

	/**
	 * Get commodity profile information.
	 * 
	 * @return IProfile
	 */
	public IProfile getCommodityProfile();

	/**
	 * Set commodity profile information.
	 * 
	 * @param commodityProfile of type IProfile
	 */
	public void setCommodityProfile(IProfile commodityProfile);

	/**
	 * Get price UOM.
	 * 
	 * @return String
	 */
	public String getPriceUOM();

	/**
	 * Set price UOM.
	 * 
	 * @param priceUOM of type String
	 */
	public void setPriceUOM(String priceUOM);

	public Date getCloseFirstUpdateDate();

	public void setCloseFirstUpdateDate(Date firstUDate);

	public Date getCurrentFirstUpdateDate();

	public void setCurrentFirstUpdateDate(Date firstUDate);
}