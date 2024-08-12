/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Model to hold trading agreement.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBTradingAgreement implements ITradingAgreement {

	private long agreementID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private String agreementType = null;

	private String counterPartyBranch = null;

	private Amount minTransferAmt = null;

	private String eligibleMargin = null;

	private String agreeIntRateType = null;

	private IExternalRating counterPartyRating = null;

	private IExternalRating mbbRating = null;

	private String baseCurrency = null;

	private Amount counterPartyThresholdAmt = null;

	private Amount mbbThresholdAmt = null;

	private String agentBankName = null;

	private String agentBankAddress = null;

	private String bankClearanceId = null;

	private String bankAccountId = null;

	private String clearingDesc = null;

	private String notificationTime = null;

	private String valuationTime = null;

	private String status = null;

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	private List thresholdRatingList = null;

	/**
	 * Default Constructor
	 */
	public OBTradingAgreement() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITradingAgreement
	 */
	public OBTradingAgreement(ITradingAgreement value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getAgreementID
	 */
	public long getAgreementID() {
		return agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getLimitProfileID
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getAgreementType
	 */
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getCounterPartyBranch
	 */
	public String getCounterPartyBranch() {
		return counterPartyBranch;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getMinTransferAmount
	 */
	public Amount getMinTransferAmount() {
		return minTransferAmt;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getEligibleMargin
	 */
	public String getEligibleMargin() {
		return eligibleMargin;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getAgreeIntRateType
	 */
	public String getAgreeIntRateType() {
		return agreeIntRateType;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getCounterPartyExtRating
	 */
	public IExternalRating getCounterPartyExtRating() {
		return counterPartyRating;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getMbbExtRating
	 */
	public IExternalRating getMbbExtRating() {
		return mbbRating;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getBaseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getCounterPartyThresholdAmount
	 */
	public Amount getCounterPartyThresholdAmount() {
		return counterPartyThresholdAmt;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getMbbThresholdAmount
	 */
	public Amount getMbbThresholdAmount() {
		return mbbThresholdAmt;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getAgentBankName
	 */
	public String getAgentBankName() {
		return agentBankName;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getAgentBankAddress
	 */
	public String getAgentBankAddress() {
		return agentBankAddress;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getBankClearanceID
	 */
	public String getBankClearanceID() {
		return bankClearanceId;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getBankAccountID
	 */
	public String getBankAccountID() {
		return bankAccountId;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getClearingDesc
	 */
	public String getClearingDesc() {
		return clearingDesc;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getNotificationTime
	 */
	public String getNotificationTime() {
		return notificationTime;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getValuationTime
	 */
	public String getValuationTime() {
		return valuationTime;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getThresholdRatingList
	 */
	public List getThresholdRatingList() {
		return thresholdRatingList;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setAgreementID
	 */
	public void setAgreementID(long value) {
		agreementID = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setLimitProfileID
	 */
	public void setLimitProfileID(long value) {
		limitProfileID = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setAgreementType
	 */
	public void setAgreementType(String value) {
		agreementType = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setCounterPartyBranch
	 */
	public void setCounterPartyBranch(String value) {
		counterPartyBranch = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setMinTransferAmount
	 */
	public void setMinTransferAmount(Amount value) {
		minTransferAmt = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setCounterPartyExtRating
	 */
	public void setCounterPartyExtRating(IExternalRating value) {
		counterPartyRating = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setMbbExtRating
	 */
	public void setMbbExtRating(IExternalRating value) {
		mbbRating = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setEligibleMargin
	 */
	public void setEligibleMargin(String value) {
		eligibleMargin = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setAgreeIntRateType
	 */
	public void setAgreeIntRateType(String value) {
		agreeIntRateType = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setBaseCurrency
	 */
	public void setBaseCurrency(String value) {
		baseCurrency = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setCounterPartyThresholdAmount
	 */
	public void setCounterPartyThresholdAmount(Amount value) {
		counterPartyThresholdAmt = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setMbbThresholdAmount
	 */
	public void setMbbThresholdAmount(Amount value) {
		mbbThresholdAmt = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setAgentBankName
	 */
	public void setAgentBankName(String value) {
		agentBankName = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setAgentBankAddress
	 */
	public void setAgentBankAddress(String value) {
		agentBankAddress = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setBankClearanceID
	 */
	public void setBankClearanceID(String value) {
		bankClearanceId = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setBankAccountID
	 */
	public void setBankAccountID(String value) {
		bankAccountId = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setClearingDesc
	 */
	public void setClearingDesc(String value) {
		clearingDesc = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setNotificationTime
	 */
	public void setNotificationTime(String value) {
		notificationTime = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setValuationTime
	 */
	public void setValuationTime(String value) {
		valuationTime = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setThresholdRatingList
	 */
	public void setThresholdRatingList(List value) {
		thresholdRatingList = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getStatus
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setStatus
	 */
	public void setStatus(String value) {
		status = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#getVersionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ITradingAgreement#setVersionTime
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		// return ">>>>>>>>>>>>>> OBTradingAgreement.toString() <<<<<<<<<<<<";
		return AccessorUtil.printMethodValue(this);
	}
}