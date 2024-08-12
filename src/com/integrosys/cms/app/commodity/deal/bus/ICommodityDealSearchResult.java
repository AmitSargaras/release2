/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/ICommodityDealSearchResult.java,v 1.11 2004/09/21 04:57:52 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;

/**
 * This interface represents Deal search result data.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2004/09/21 04:57:52 $ Tag: $Name: $
 */
public interface ICommodityDealSearchResult extends java.io.Serializable {
	/**
	 * Get commodity deal id.
	 * 
	 * @return long
	 */
	public long getDealID();

	/**
	 * Set commodity deal id.
	 * 
	 * @param dealID of type long
	 */
	public void setDealID(long dealID);

	/**
	 * Get commodity deal type code, specific or pool.
	 * 
	 * @return String
	 */
	public String getDealTypeCode();

	/**
	 * Set commodity deal type code, specific or pool.
	 * 
	 * @param dealTypeCode of type String
	 */
	public void setDealTypeCode(String dealTypeCode);

	/**
	 * Get commodity category code.
	 * 
	 * @return String
	 */
	public String getCategoryCode();

	/**
	 * Set commodity category code.
	 * 
	 * @param categoryCode of type String
	 */
	public void setCategoryCode(String categoryCode);

	/**
	 * Get commodity product type code.
	 * 
	 * @return String
	 */
	public String getProdTypeCode();

	/**
	 * Set commodity product type code.
	 * 
	 * @param prodTypeCode of type String
	 */
	public void setProdTypeCode(String prodTypeCode);

	/**
	 * Get commodity product subtype code.
	 * 
	 * @return String
	 */
	public String getProdSubtypeCode();

	/**
	 * Set commodity product subtype code.
	 * 
	 * @param prodSubtypeCode of type String
	 */
	public void setProdSubtypeCode(String prodSubtypeCode);

	/**
	 * Get TP deal reference.
	 * 
	 * @return String
	 */
	public String getDealReferenceNo();

	/**
	 * Set TP deal reference.
	 * 
	 * @param dealReferenceNo of type String
	 */
	public void setDealReferenceNo(String dealReferenceNo);

	/**
	 * Get deal number.
	 * 
	 * @return String
	 */
	public String getDealNo();

	/**
	 * Set deal number.
	 * 
	 * @param dealNo of type String
	 */
	public void setDealNo(String dealNo);

	/**
	 * Get deal amount.
	 * 
	 * @return Amount
	 */
	public Amount getDealAmt();

	/**
	 * Set deal amount.
	 * 
	 * @param dealAmt of type Amount
	 */
	public void setDealAmt(Amount dealAmt);

	/**
	 * Get commodity market price.
	 * 
	 * @return Amount
	 */
	public Amount getMarketPrice();

	/**
	 * Set commodity market price.
	 * 
	 * @param marketPrice of type Amount
	 */
	public void setMarketPrice(Amount marketPrice);

	/**
	 * Get commodity deal quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getQuantity();

	/**
	 * Set commodity deal quantity.
	 * 
	 * @param quantity of type Quantity
	 */
	public void setQuantity(Quantity quantity);

	/**
	 * Get cash requirement amount.
	 * 
	 * @return Amount
	 */
	public Amount getCashReqAmt();

	/**
	 * Set cash requirement amount.
	 * 
	 * @param cashReqAmt of type Amount
	 */
	public void setCashReqAmt(Amount cashReqAmt);

	/**
	 * Get deal fsv.
	 * 
	 * @return Amount
	 */
	public Amount getDealFSV();

	/**
	 * Set deal fsv.
	 * 
	 * @param dealFSV of type Amount
	 */
	public void setDealFSV(Amount dealFSV);

	/**
	 * Get deal cmv.
	 * 
	 * @return Amount
	 */
	public Amount getDealCMV();

	/**
	 * Set deal cmv.
	 * 
	 * @param dealCMV of type Amount
	 */
	public void setDealCMV(Amount dealCMV);

	/**
	 * Get if the deal is closed.
	 * 
	 * @return boolean
	 */
	public boolean getIsDealClosed();

	/**
	 * Set if the deal is closed.
	 * 
	 * @param isDealClosed
	 */
	public void setIsDealClosed(boolean isDealClosed);

	/**
	 * Get deal secure status.
	 * 
	 * @return boolean
	 */
	public boolean getIsDealSecured();

	/**
	 * Set deal secure status.
	 * 
	 * @param isDealSecured of type boolean
	 */
	public void setIsDealSecured(boolean isDealSecured);

	/**
	 * Get deal business status.
	 * 
	 * @return String
	 */
	public String getDealStatus();

	/**
	 * Set deal business status.
	 * 
	 * @param dealStatus of type String
	 */
	public void setDealStatus(String dealStatus);

	/**
	 * Get customer id.
	 * 
	 * @return long
	 */
	public long getCustomerID();

	/**
	 * Set customer id.
	 * 
	 * @param customerID of type long
	 */
	public void setCustomerID(long customerID);

	/**
	 * Get limit profile id.
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Set limit profile id.
	 * 
	 * @param limitProfileID of type long
	 */
	public void setLimitProfileID(long limitProfileID);

	/**
	 * Get cash requirement percentage at deal level.
	 * 
	 * @return double
	 */
	public double getDealCashReqPct();

	/**
	 * Set cash requirement percentage at deal level.
	 * 
	 * @param dealCashReqPct of type double
	 */
	public void setDealCashReqPct(double dealCashReqPct);

	/**
	 * Get cash requirement percentage at customer level.
	 * 
	 * @return double
	 */
	public double getCustCashReqPct();

	/**
	 * Set cash requirement percentage at customer level.
	 * 
	 * @param custCashReqPct of type double
	 */
	public void setCustCashReqPct(double custCashReqPct);

	/**
	 * Get actual cash requirement received percentage.
	 * 
	 * @return double
	 */
	public double getActualCashReqRecdPct();

	/**
	 * Get actual cash requirement received amount.
	 * 
	 * @return Amount
	 */
	public Amount getActualCashReqRecdAmt();

	/**
	 * Set actual cash requirement received amount.
	 * 
	 * @param actualCashReqRecdAmt of type Amount
	 */
	public void setActualCashReqRecdAmt(Amount actualCashReqRecdAmt);

	/**
	 * Get actual cash setoff received amount.
	 * 
	 * @return Amount
	 */
	public Amount getActualCashSetOffRecdAmt();

	/**
	 * Set actual cash set off received amount.
	 * 
	 * @param actualCashSetOffRecdAmt of type Amount
	 */
	public void setActualCashSetOffRecdAmt(Amount actualCashSetOffRecdAmt);

	/**
	 * Get actual cash comfort received amount.
	 * 
	 * @return Amount
	 */
	public Amount getActualCashComfortRecdAmt();

	/**
	 * Set actual cash comfort received amount.
	 * 
	 * @param actualCashComfortRecdAmt of type Amount
	 */
	public void setActualCashComfortRecdAmt(Amount actualCashComfortRecdAmt);

	/**
	 * Get price type.
	 * 
	 * @return String
	 */
	public String getPriceType();

	/**
	 * Set price type.
	 * 
	 * @param priceType of type String
	 */
	public void setPriceType(String priceType);

	/**
	 * Get face value amount.
	 * 
	 * @return Amount
	 */
	public Amount getFaceValueAmt();

	/**
	 * Set face value amount.
	 * 
	 * @param faceValueAmt of type Amount
	 */
	public void setFaceValueAmt(Amount faceValueAmt);

	/**
	 * Get eligibility advance percentage.
	 * 
	 * @return double
	 */
	public double getEligibilityAdvPct();

	/**
	 * Set eligibility advance percentage.
	 * 
	 * @param eligibilityAdvPct of type double
	 */
	public void setEligibilityAdvPct(double eligibilityAdvPct);

	/**
	 * Get eligibility face value amount.
	 * 
	 * @return Amount
	 */
	public Amount getEligibleFaceValue();

	/**
	 * Get deal transaction id.
	 * 
	 * @return String
	 */
	public String getTrxID();

	/**
	 * Set deal transaction id.
	 * 
	 * @param transactionID of type String
	 */
	public void setTrxID(String transactionID);

	/**
	 * Get commodity deal transaction value.
	 * 
	 * @return ICommodityDealTrxValue
	 */
	public ICommodityDealTrxValue getTrxValue();

	/**
	 * Set commodity deal transaction value.
	 * 
	 * @param trxValue of type ICommodityDealTrxValue
	 */
	public void setTrxValue(ICommodityDealTrxValue trxValue);
}