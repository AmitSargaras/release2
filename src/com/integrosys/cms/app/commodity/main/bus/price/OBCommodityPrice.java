/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/price/OBCommodityPrice.java,v 1.4 2006/09/13 01:30:26 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.price;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.OBCommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Commodity Price entity.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/13 01:30:26 $ Tag: $Name: $
 */
public class OBCommodityPrice extends OBCommodityMainInfo implements ICommodityPrice {
	private long commodityPriceID = ICMSConstant.LONG_INVALID_VALUE;

	private long groupID = ICMSConstant.LONG_INVALID_VALUE;

	private long profileID = ICMSConstant.LONG_INVALID_VALUE;

	private Amount closePrice;

	private Date closeUpdateDate;

	private Amount currentPrice;

	private Date currentUpdateDate;

	private String priceUOM;

	private IProfile commodityProfile;

	private Date closeFirstUpdateDate;

	private Date currentFirstUpdateDate;

	/**
	 * Default Constructor.
	 */
	public OBCommodityPrice() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommodityPrice
	 */
	public OBCommodityPrice(ICommodityPrice obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get commodity price id.
	 * 
	 * @return long
	 */
	public long getCommodityPriceID() {
		return commodityPriceID;
	}

	/**
	 * Set commodity price id.
	 * 
	 * @param commodityPriceID of type long
	 */
	public void setCommodityPriceID(long commodityPriceID) {
		this.commodityPriceID = commodityPriceID;
	}

	/**
	 * Get group id.
	 * 
	 * @return long
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * Set group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get commodity profile id.
	 * 
	 * @return long
	 */
	public long getProfileID() {
		return profileID;
	}

	/**
	 * Set commodity profile id.
	 * 
	 * @param profileID of type long
	 */
	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}

	/**
	 * Get close price.
	 * 
	 * @return Amount
	 */
	public Amount getClosePrice() {
		return closePrice;
	}

	/**
	 * Set close price.
	 * 
	 * @param closePrice of type Amount
	 */
	public void setClosePrice(Amount closePrice) {
		this.closePrice = closePrice;
	}

	/**
	 * Set close price update date.
	 * 
	 * @return Date
	 */
	public Date getCloseUpdateDate() {
		return closeUpdateDate;
	}

	/**
	 * Set close price update date.
	 * 
	 * @param closeUpdateDate of type Date
	 */
	public void setCloseUpdateDate(Date closeUpdateDate) {
		this.closeUpdateDate = closeUpdateDate;
	}

	/**
	 * Get current price.
	 * 
	 * @return Amount
	 */
	public Amount getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * Set current price.
	 * 
	 * @param currentPrice of type Amount
	 */
	public void setCurrentPrice(Amount currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * Get currenty price update date.
	 * 
	 * @return Date
	 */
	public Date getCurrentUpdateDate() {
		return currentUpdateDate;
	}

	/**
	 * Set current price update date.
	 * 
	 * @param currentUpdateDate of type Date
	 */
	public void setCurrentUpdateDate(Date currentUpdateDate) {
		this.currentUpdateDate = currentUpdateDate;
	}

	/**
	 * Get commodity profile information.
	 * 
	 * @return IProfile
	 */
	public IProfile getCommodityProfile() {
		return commodityProfile;
	}

	/**
	 * Set commodity profile information.
	 * 
	 * @param commodityProfile of type IProfile
	 */
	public void setCommodityProfile(IProfile commodityProfile) {
		this.commodityProfile = commodityProfile;
	}

	/**
	 * Get price UOM.
	 * 
	 * @return String
	 */
	public String getPriceUOM() {
		return priceUOM;
	}

	/**
	 * Set price UOM.
	 * 
	 * @param priceUOM of type String
	 */
	public void setPriceUOM(String priceUOM) {
		this.priceUOM = priceUOM;
	}

	public Date getCloseFirstUpdateDate() {
		return closeFirstUpdateDate;
	}

	public void setCloseFirstUpdateDate(Date firstUDate) {
		closeFirstUpdateDate = firstUDate;
	}

	public Date getCurrentFirstUpdateDate() {
		return currentFirstUpdateDate;
	}

	public void setCurrentFirstUpdateDate(Date firstUDate) {
		currentFirstUpdateDate = firstUDate;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(commodityPriceID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCommodityPrice)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}