package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jun 7, 2003 Time: 4:01:08 PM To
 * change this template use Options | File Templates.
 */
public class OBDealInfo implements java.io.Serializable {
	private String dealRefNo;

	private String productType;

	private String secured;

	private String enforceable;

	private Amount lastPrice;

	private String lastPriceString;

	private String lastPriceUnit;

	private Amount currentPrice;

	private String currentPriceString;

	private String currentPriceUnit;

	private Amount dealAmount;

	private Amount dealFSV;

	private Amount shortFallValue;

	public Amount getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Amount currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Amount getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(Amount dealAmount) {
		this.dealAmount = dealAmount;
	}

	public Amount getDealFSV() {
		return dealFSV;
	}

	public void setDealFSV(Amount dealFSV) {
		this.dealFSV = dealFSV;
	}

	public String getDealRefNo() {
		return dealRefNo;
	}

	public void setDealRefNo(String dealRefNo) {
		this.dealRefNo = dealRefNo;
	}

	public String getEnforceable() {
		return enforceable;
	}

	public void setEnforceable(String enforceable) {
		this.enforceable = enforceable;
	}

	public Amount getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Amount lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSecured() {
		return secured;
	}

	public void setSecured(String secured) {
		this.secured = secured;
	}

	public Amount getShortFallValue() {
		return shortFallValue;
	}

	public void setShortFallValue(Amount shortFallValue) {
		this.shortFallValue = shortFallValue;
	}

	public String getCurrentPriceString() {
		return currentPriceString;
	}

	public void setCurrentPriceString(String currentPriceString) {
		this.currentPriceString = currentPriceString;
	}

	public String getCurrentPriceUnit() {
		return currentPriceUnit;
	}

	public void setCurrentPriceUnit(String currentPriceUnit) {
		this.currentPriceUnit = currentPriceUnit;
	}

	public String getLastPriceString() {
		return lastPriceString;
	}

	public void setLastPriceString(String lastPriceString) {
		this.lastPriceString = lastPriceString;
	}

	public String getLastPriceUnit() {
		return lastPriceUnit;
	}

	public void setLastPriceUnit(String lastPriceUnit) {
		this.lastPriceUnit = lastPriceUnit;
	}
}
