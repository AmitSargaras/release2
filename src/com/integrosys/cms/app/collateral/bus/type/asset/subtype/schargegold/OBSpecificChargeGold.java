/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBChargeCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Asset of type Specific Charge - Gold
 * 
 * Describe this class. Purpose: ISpecificChargeGold Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class OBSpecificChargeGold extends OBChargeCommon implements ISpecificChargeGold {

	public String goldGrade;

	public String purchaseReceiptNo;

	public Amount goldUnitPrice;

	public Date certExpiryDate;

	public double goldWeight;

	public String goldUOM;

	public Date auctionDate;

	public Amount auctionPrice;

	public String auctioneer;

	public String goldUnitPriceUOM;

	/**
	 * Default Constructor.
	 */
	public OBSpecificChargeGold() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISpecificChargePlant
	 */
	public OBSpecificChargeGold(ISpecificChargeGold obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public String getGoldGrade() {
		return goldGrade;
	}

	public void setGoldGrade(String goldGrade) {
		this.goldGrade = goldGrade;
	}

	public String getPurchaseReceiptNo() {
		return purchaseReceiptNo;
	}

	public void setPurchaseReceiptNo(String purchaseReceiptNo) {
		this.purchaseReceiptNo = purchaseReceiptNo;
	}

	public Amount getGoldUnitPrice() {
		return goldUnitPrice;
	}

	public void setGoldUnitPrice(Amount goldUnitPrice) {
		this.goldUnitPrice = goldUnitPrice;
	}

	public Date getCertExpiryDate() {
		return certExpiryDate;
	}

	public void setCertExpiryDate(Date certExpiryDate) {
		this.certExpiryDate = certExpiryDate;
	}

	public double getGoldWeight() {
		return goldWeight;
	}

	public void setGoldWeight(double goldWeight) {
		this.goldWeight = goldWeight;
	}

	public String getGoldUOM() {
		return goldUOM;
	}

	public void setGoldUOM(String goldUOM) {
		this.goldUOM = goldUOM;
	}

	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}

	public Amount getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(Amount auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	public String getAuctioneer() {
		return auctioneer;
	}

	public void setAuctioneer(String auctioneer) {
		this.auctioneer = auctioneer;
	}

	public String getGoldUnitPriceUOM() {
		return goldUnitPriceUOM;
	}

	public void setGoldUnitPriceUOM(String goldUnitPriceUOM) {
		this.goldUnitPriceUOM = goldUnitPriceUOM;
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
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBSpecificChargeGold)) {
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