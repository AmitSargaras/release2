/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;

/**
 * This interface represents Asset of type Specific Charge - Gold
 * 
 * Describe this class. Purpose: ISpecificChargeGold Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public interface ISpecificChargeGold extends IChargeCommon {
	public String getGoldGrade();

	public void setGoldGrade(String goldGrade);

	public String getPurchaseReceiptNo();

	public void setPurchaseReceiptNo(String purchaseReceiptNo);

	public Amount getGoldUnitPrice();

	public void setGoldUnitPrice(Amount goldUnitPrice);

	public Date getCertExpiryDate();

	public void setCertExpiryDate(Date certExpiryDate);

	public double getGoldWeight();

	public void setGoldWeight(double goldWeight);

	public String getGoldUOM();

	public void setGoldUOM(String goldUOM);

	public Date getAuctionDate();

	public void setAuctionDate(Date auctionDate);

	public Amount getAuctionPrice();

	public void setAuctionPrice(Amount auctionPrice);

	public String getAuctioneer();

	public void setAuctioneer(String auctioneer);

	public String getGoldUnitPriceUOM();

	public void setGoldUnitPriceUOM(String goldUnitPriceUOM);

}
