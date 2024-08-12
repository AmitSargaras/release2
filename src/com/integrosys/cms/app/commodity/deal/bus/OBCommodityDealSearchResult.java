/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/OBCommodityDealSearchResult.java,v 1.12 2004/09/21 04:57:52 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Commodity Deal search result data.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2004/09/21 04:57:52 $ Tag: $Name: $
 */
public class OBCommodityDealSearchResult implements ICommodityDealSearchResult {
	private long dealID;

	private String dealTypeCode;

	private String categoryCode;

	private String prodTypeCode;

	private String prodSubtypeCode;

	private String dealReferenceNo;

	private String dealNo;

	private Amount dealAmt;

	private Amount marketPrice;

	private Quantity quantity;

	private Amount cashReqAmt;

	private Amount dealFSV;

	private Amount dealCMV;

	private boolean isDealClosed;

	private boolean isDealSecured;

	private long customerID;

	private long limitProfileID;

	private double dealCashReqPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private double custCashReqPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Amount actualCashReqRecdAmt;

	private Amount actualCashSetOffRecdAmt;

	private Amount actualCashComfortRecdAmt;

	private String priceType;

	private String dealStatus;

	private Amount faceValueAmt;

	private double eligibilityAdvPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private String trxID;

	private ICommodityDealTrxValue trxValue;

	/**
	 * Default Constructor
	 */
	public OBCommodityDealSearchResult() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICommodityDealSearchResult
	 */
	public OBCommodityDealSearchResult(ICommodityDealSearchResult value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Get commodity deal id.
	 * 
	 * @return long
	 */
	public long getDealID() {
		return dealID;
	}

	/**
	 * Set commodity deal id.
	 * 
	 * @param dealID of type long
	 */
	public void setDealID(long dealID) {
		this.dealID = dealID;
	}

	/**
	 * Get commodity deal type code, specific or pool.
	 * 
	 * @return String
	 */
	public String getDealTypeCode() {
		return dealTypeCode;
	}

	/**
	 * Set commodity deal type code, specific or pool.
	 * 
	 * @param dealTypeCode of type String
	 */
	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}

	/**
	 * Get commodity category code.
	 * 
	 * @return String
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Set commodity category code.
	 * 
	 * @param categoryCode of type String
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * Get commodity product type code.
	 * 
	 * @return String
	 */
	public String getProdTypeCode() {
		return prodTypeCode;
	}

	/**
	 * Set commodity product type code.
	 * 
	 * @param prodTypeCode of type String
	 */
	public void setProdTypeCode(String prodTypeCode) {
		this.prodTypeCode = prodTypeCode;
	}

	/**
	 * Get commodity product subtype code.
	 * 
	 * @return String
	 */
	public String getProdSubtypeCode() {
		return prodSubtypeCode;
	}

	/**
	 * Set commodity product subtype code.
	 * 
	 * @param prodSubtypeCode of type String
	 */
	public void setProdSubtypeCode(String prodSubtypeCode) {
		this.prodSubtypeCode = prodSubtypeCode;
	}

	/**
	 * Get TP deal reference.
	 * 
	 * @return String
	 */
	public String getDealReferenceNo() {
		return dealReferenceNo;
	}

	/**
	 * Set TP deal reference.
	 * 
	 * @param dealReferenceNo of type String
	 */
	public void setDealReferenceNo(String dealReferenceNo) {
		this.dealReferenceNo = dealReferenceNo;
	}

	/**
	 * Get deal number.
	 * 
	 * @return String
	 */
	public String getDealNo() {
		return dealNo;
	}

	/**
	 * Set deal number.
	 * 
	 * @param dealNo of type String
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	/**
	 * Get deal amount.
	 * 
	 * @return Amount
	 */
	public Amount getDealAmt() {
		return dealAmt;
	}

	/**
	 * Set deal amount.
	 * 
	 * @param dealAmt of type Amount
	 */
	public void setDealAmt(Amount dealAmt) {
		this.dealAmt = dealAmt;
	}

	/**
	 * Get commodity market price.
	 * 
	 * @return Amount
	 */
	public Amount getMarketPrice() {
		return marketPrice;
	}

	/**
	 * Set commodity market price.
	 * 
	 * @param marketPrice of type Amount
	 */
	public void setMarketPrice(Amount marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * Get commodity deal quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getQuantity() {
		return quantity;
	}

	/**
	 * Set commodity deal quantity.
	 * 
	 * @param quantity of type Quantity
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	/**
	 * Get cash requirement amount.
	 * 
	 * @return Amount
	 */
	public Amount getCashReqAmt() {
		return cashReqAmt;
	}

	/**
	 * Set cash requirement amount.
	 * 
	 * @param cashReqAmt of type Amount
	 */
	public void setCashReqAmt(Amount cashReqAmt) {
		this.cashReqAmt = cashReqAmt;
	}

	/**
	 * Get deal fsv.
	 * 
	 * @return Amount
	 */
	public Amount getDealFSV() {
		return dealFSV;
	}

	/**
	 * Set deal fsv.
	 * 
	 * @param dealFSV of type Amount
	 */
	public void setDealFSV(Amount dealFSV) {
		this.dealFSV = dealFSV;
	}

	/**
	 * Get deal cmv.
	 * 
	 * @return Amount
	 */
	public Amount getDealCMV() {
		return dealCMV;
	}

	/**
	 * Set deal cmv.
	 * 
	 * @param dealCMV of type Amount
	 */
	public void setDealCMV(Amount dealCMV) {
		this.dealCMV = dealCMV;
	}

	/**
	 * Get if the deal is closed.
	 * 
	 * @return boolean
	 */
	public boolean getIsDealClosed() {
		return isDealClosed;
	}

	/**
	 * Set if the deal is closed.
	 * 
	 * @param isDealClosed
	 */
	public void setIsDealClosed(boolean isDealClosed) {
		this.isDealClosed = isDealClosed;
	}

	/**
	 * Get deal secure status.
	 * 
	 * @return boolean
	 */
	public boolean getIsDealSecured() {
		return isDealSecured;
	}

	/**
	 * Set deal secure status.
	 * 
	 * @param isDealSecured of type boolean
	 */
	public void setIsDealSecured(boolean isDealSecured) {
		this.isDealSecured = isDealSecured;
	}

	/**
	 * Get deal business status.
	 * 
	 * @return String
	 */
	public String getDealStatus() {
		return dealStatus;
	}

	/**
	 * Set deal business status.
	 * 
	 * @param dealStatus of type String
	 */
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	/**
	 * Get customer id.
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return customerID;
	}

	/**
	 * Set customer id.
	 * 
	 * @param customerID of type long
	 */
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	/**
	 * Get limit profile id.
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Set limit profile id.
	 * 
	 * @param limitProfileID of type long
	 */
	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * Get cash requirement percentage at deal level.
	 * 
	 * @return double
	 */
	public double getDealCashReqPct() {
		return dealCashReqPct;
	}

	/**
	 * Set cash requirement percentage at deal level.
	 * 
	 * @param dealCashReqPct of type double
	 */
	public void setDealCashReqPct(double dealCashReqPct) {
		this.dealCashReqPct = dealCashReqPct;
	}

	/**
	 * Get cash requirement percentage at customer level.
	 * 
	 * @return double
	 */
	public double getCustCashReqPct() {
		return custCashReqPct;
	}

	/**
	 * Set cash requirement percentage at customer level.
	 * 
	 * @param custCashReqPct of type double
	 */
	public void setCustCashReqPct(double custCashReqPct) {
		this.custCashReqPct = custCashReqPct;
	}

	/**
	 * Get actual cash requirement received percentage.
	 * 
	 * @return double
	 */
	public double getActualCashReqRecdPct() {
		if ((cashReqAmt != null) && (actualCashReqRecdAmt != null)) {
			return (actualCashReqRecdAmt.getAmountAsDouble() / cashReqAmt.getAmountAsDouble()) * 100;
		}

		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * Get actual cash requirement received amount.
	 * 
	 * @return Amount
	 */
	public Amount getActualCashReqRecdAmt() {
		return actualCashReqRecdAmt;
	}

	/**
	 * Set actual cash requirement received amount.
	 * 
	 * @param actualCashReqRecdAmt of type Amount
	 */
	public void setActualCashReqRecdAmt(Amount actualCashReqRecdAmt) {
		this.actualCashReqRecdAmt = actualCashReqRecdAmt;
	}

	/**
	 * Get actual cash setoff received amount.
	 * 
	 * @return Amount
	 */
	public Amount getActualCashSetOffRecdAmt() {
		return actualCashSetOffRecdAmt;
	}

	/**
	 * Set actual cash set off received amount.
	 * 
	 * @param actualCashSetOffRecdAmt of type Amount
	 */
	public void setActualCashSetOffRecdAmt(Amount actualCashSetOffRecdAmt) {
		this.actualCashSetOffRecdAmt = actualCashSetOffRecdAmt;
	}

	/**
	 * Get actual cash comfort received amount.
	 * 
	 * @return Amount
	 */
	public Amount getActualCashComfortRecdAmt() {
		return actualCashComfortRecdAmt;
	}

	/**
	 * Set actual cash comfort received amount.
	 * 
	 * @param actualCashComfortRecdAmt of type Amount
	 */
	public void setActualCashComfortRecdAmt(Amount actualCashComfortRecdAmt) {
		this.actualCashComfortRecdAmt = actualCashComfortRecdAmt;
	}

	/**
	 * Get price type.
	 * 
	 * @return String
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * Set price type.
	 * 
	 * @param priceType of type String
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	/**
	 * Get face value amount.
	 * 
	 * @return Amount
	 */
	public Amount getFaceValueAmt() {
		return faceValueAmt;
	}

	/**
	 * Set face value amount.
	 * 
	 * @param faceValueAmt of type Amount
	 */
	public void setFaceValueAmt(Amount faceValueAmt) {
		this.faceValueAmt = faceValueAmt;
	}

	/**
	 * Get eligibility advance percentage.
	 * 
	 * @return double
	 */
	public double getEligibilityAdvPct() {
		return eligibilityAdvPct;
	}

	/**
	 * Set eligibility advance percentage.
	 * 
	 * @param eligibilityAdvPct of type double
	 */
	public void setEligibilityAdvPct(double eligibilityAdvPct) {
		this.eligibilityAdvPct = eligibilityAdvPct;
	}

	/**
	 * Get eligibility face value amount.
	 * 
	 * @return Amount
	 */
	public Amount getEligibleFaceValue() {
		if ((getFaceValueAmt() != null) && (getEligibilityAdvPct() != ICMSConstant.DOUBLE_INVALID_VALUE)) {
			return new Amount(getFaceValueAmt().getAmountAsDouble() * getEligibilityAdvPct() / 100, getFaceValueAmt()
					.getCurrencyCode());
		}
		return null;
	}

	/**
	 * Get deal transaction id.
	 * 
	 * @return String
	 */
	public String getTrxID() {
		return trxID;
	}

	/**
	 * Set deal transaction id.
	 * 
	 * @param trxID of type String
	 */
	public void setTrxID(String trxID) {
		this.trxID = trxID;
	}

	/**
	 * Get commodity deal transaction value.
	 * 
	 * @return ICommodityDealTrxValue
	 */
	public ICommodityDealTrxValue getTrxValue() {
		return trxValue;
	}

	/**
	 * Set commodity deal transaction value.
	 * 
	 * @param trxValue of type ICommodityDealTrxValue
	 */
	public void setTrxValue(ICommodityDealTrxValue trxValue) {
		this.trxValue = trxValue;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}