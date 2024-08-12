/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:  $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Data model holds counter party and agreement details.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCPAgreementDetail implements ICPAgreementDetail {
	// Counter party info
	private String custName = null;

	// private long leID = ICMSConstant.LONG_INVALID_VALUE;
	private String leID = null;

	private Date relationshipStartDate = null;

	private String incorpCountry = null;

	// Agreement info
	private long agreementID = ICMSConstant.LONG_INVALID_VALUE;

	private String agreementType = null;

	private String baseCurrency = null;

	private Amount minTransferAmount = null;

	private String cpRating = null;

	private Date closingCashInterestDate = null;

	private BigDecimal closingCashInterest = null;

	private String intRateType = null;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private String bcaStatus = null;

	/**
	 * Default Constructor
	 */
	public OBCPAgreementDetail() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICPAgreementDetail
	 */
	public OBCPAgreementDetail(ICPAgreementDetail value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getCustomerName
	 */
	public String getCustomerName() {
		return this.custName;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setCustomerName
	 */
	public void setCustomerName(String value) {
		this.custName = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getLEID
	 */
	// public long getLEID() {
	// return this.leID;
	// }
	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setLEID
	 */
	// public void setLEID(long value) {
	// this.leID = value;
	// }
	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getLEID
	 */
	public String getLEID() {
		return this.leID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setLEID
	 */
	public void setLEID(String value) {
		this.leID = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getRelationshipStartDate
	 */
	public Date getRelationshipStartDate() {
		return this.relationshipStartDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setRelationshipStartDate
	 */
	public void setRelationshipStartDate(Date value) {
		this.relationshipStartDate = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getIncorpCountry
	 */
	public String getIncorpCountry() {
		return this.incorpCountry;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setIncorpCountry
	 */
	public void setIncorpCountry(String value) {
		this.incorpCountry = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getAgreementID
	 */
	public long getAgreementID() {
		return this.agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setAgreementID
	 */
	public void setAgreementID(long value) {
		this.agreementID = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getLimitProfileID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setLimitProfileID
	 */
	public void setLimitProfileID(long value) {
		this.limitProfileID = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getAgreementType
	 */
	public String getAgreementType() {
		return this.agreementType;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setAgreementType
	 */
	public void setAgreementType(String value) {
		this.agreementType = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getBaseCurrency
	 */
	public String getBaseCurrency() {
		return this.baseCurrency;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setBaseCurrency
	 */
	public void setBaseCurrency(String value) {
		this.baseCurrency = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getMinTransferAmount
	 */
	public Amount getMinTransferAmount() {
		return this.minTransferAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setMinTransferAmount
	 */
	public void setMinTransferAmount(Amount value) {
		this.minTransferAmount = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getCPRating
	 */
	public String getCPRating() {
		return this.cpRating;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setCPRating
	 */
	public void setCPRating(String value) {
		this.cpRating = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getClosingCashInterestDate
	 */
	public Date getClosingCashInterestDate() {
		return this.closingCashInterestDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setClosingCashInterestDate
	 */
	public void setClosingCashInterestDate(Date value) {
		this.closingCashInterestDate = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getClosingCashInterest
	 */
	public BigDecimal getClosingCashInterest() {
		return this.closingCashInterest;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setClosingCashInterest
	 */
	public void setClosingCashInterest(BigDecimal value) {
		this.closingCashInterest = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getIntRateType
	 */
	public String getIntRateType() {
		return this.intRateType;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setIntRateType
	 */
	public void setIntRateType(String value) {
		this.intRateType = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#getBCAStatus
	 */
	public String getBCAStatus() {
		return this.bcaStatus;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail#setBCAStatus
	 */
	public void setBCAStatus(String value) {
		this.bcaStatus = value;
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