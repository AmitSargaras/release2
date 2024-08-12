package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.EBAssetChargeDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBSpecificChargeGoldBean extends EBAssetChargeDetailBean {

	private static final long serialVersionUID = -4618380788224113779L;

	public Amount getGoldUnitPrice() {
		if (getEBGoldUnitPrice() != null && getEBGoldUnitPriceCurrency() != null) {
			return new Amount(getEBGoldUnitPrice().doubleValue(), getEBGoldUnitPriceCurrency());
		}
		else {
			return null;
		}
	}

	public void setGoldUnitPrice(Amount goldUnitPrice) {
		if (goldUnitPrice != null && goldUnitPrice.getCurrencyCode() != null) {
			setEBGoldUnitPrice(new Double(goldUnitPrice.getAmountAsDouble()));
			setEBGoldUnitPriceCurrency(goldUnitPrice.getCurrencyCode());
		}
		else {
			setEBGoldUnitPrice(null);
			setEBGoldUnitPriceCurrency(null);
		}
	}

	public double getGoldWeight() {
		if (getEBGoldWeight() != null) {
			return getEBGoldWeight().doubleValue();
		}
		else {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
	}

	public void setGoldWeight(double goldWeight) {
		if (goldWeight != ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBGoldWeight(new Double(goldWeight));
		}
		else {
			setEBGoldWeight(null);
		}
	}

	public Amount getAuctionPrice() {
		if (getEBAuctionPrice() != null) {
			return new Amount(getEBAuctionPrice().doubleValue(), currencyCode);
		}
		else {
			return null;
		}
	}

	public void setAuctionPrice(Amount auctionPrice) {
		if (auctionPrice != null) {
			setEBAuctionPrice(new Double(auctionPrice.getAmountAsDouble()));
		}
		else {
			setEBAuctionPrice(null);
		}
	}

	public abstract Double getEBGoldUnitPrice();

	public abstract void setEBGoldUnitPrice(Double eBGoldUnitPrice);

	public abstract Double getEBGoldWeight();

	public abstract void setEBGoldWeight(Double eBGoldWeight);

	public abstract Double getEBAuctionPrice();

	public abstract void setEBAuctionPrice(Double eBAuctionPrice);

	public abstract String getPurchaseReceiptNo();

	public abstract void setPurchaseReceiptNo(String purchaseReceiptNo);

	public abstract Date getCertExpiryDate();

	public abstract void setCertExpiryDate(Date certExpiryDate);

	public abstract String getGoldUOM();

	public abstract void setGoldUOM(String goldUOM);

	public abstract Date getAuctionDate();

	public abstract void setAuctionDate(Date auctionDate);

	public abstract String getAuctioneer();

	public abstract void setAuctioneer(String auctioneer);

	public abstract String getEBGoldUnitPriceCurrency();

	public abstract void setEBGoldUnitPriceCurrency(String goldUnitPriceCurrency);

	public abstract String getGoldGrade();

	public abstract void setGoldGrade(String goldGrade);

	public abstract String getGoldUnitPriceUOM();

	public abstract void setGoldUnitPriceUOM(String goldUnitPriceUOM);

}
