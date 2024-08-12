/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.List;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a Trading Agreement.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ITradingAgreement extends java.io.Serializable {

	// Getters

	/**
	 * Get Trading Agreement ID
	 * 
	 * @return long
	 */
	public long getAgreementID();

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Get Trading Agreement Type
	 * 
	 * @return String
	 */
	public String getAgreementType();

	/**
	 * Get Counter Party Branch
	 * 
	 * @return String
	 */
	public String getCounterPartyBranch();

	/**
	 * Get Minimum Transfer Amount
	 * 
	 * @return Amount
	 */
	public Amount getMinTransferAmount();

	/**
	 * Get Eligible Margin
	 * 
	 * @return String
	 */
	public String getEligibleMargin();

	/**
	 * Get Agreed Interest Rate Type
	 * 
	 * @return String
	 */
	public String getAgreeIntRateType();

	/**
	 * Get Counter Party External Rating
	 * 
	 * @return IExternalRating
	 */
	public IExternalRating getCounterPartyExtRating();

	/**
	 * Get MBB External Rating
	 * 
	 * @return IExternalRating
	 */
	public IExternalRating getMbbExtRating();

	/**
	 * Get Base Currency Code
	 * 
	 * @return String
	 */
	public String getBaseCurrency();

	/**
	 * Get Counter Party Threshold Amount
	 * 
	 * @return Amount
	 */
	public Amount getCounterPartyThresholdAmount();

	/**
	 * Get MBB Threshold Amount
	 * 
	 * @return Amount
	 */
	public Amount getMbbThresholdAmount();

	/**
	 * Get Agent Bank Name
	 * 
	 * @return String
	 */
	public String getAgentBankName();

	/**
	 * Get Agent Bank Address
	 * 
	 * @return String
	 */
	public String getAgentBankAddress();

	/**
	 * Get Bank Clearance ID
	 * 
	 * @return String
	 */
	public String getBankClearanceID();

	/**
	 * Get Bank Account ID
	 * 
	 * @return String
	 */
	public String getBankAccountID();

	/**
	 * Get Clearing Description
	 * 
	 * @return String
	 */
	public String getClearingDesc();

	/**
	 * Get Notification Time
	 * 
	 * @return String
	 */
	public String getNotificationTime();

	/**
	 * Get Valuation Time
	 * 
	 * @return String
	 */
	public String getValuationTime();

	/**
	 * Get List of Threshold Rating
	 * 
	 * @return List of OBThresholdRating
	 */
	public List getThresholdRatingList();

	/**
	 * Get the status of this agreement.
	 * 
	 * @return long
	 */
	public String getStatus();

	/**
	 * Get the version time of this agreement.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	// Setters

	/**
	 * Set Trading Agreement ID
	 * 
	 * @param value is of type long
	 */
	public void setAgreementID(long value);

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value);

	/**
	 * Set Trading Agreement Type
	 * 
	 * @param value is of type String
	 */
	public void setAgreementType(String value);

	/**
	 * Set Counter Party Branch
	 * 
	 * @param value is of type String
	 */
	public void setCounterPartyBranch(String value);

	/**
	 * Set Minimum Transfer Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setMinTransferAmount(Amount value);

	/**
	 * Set Counter Party External Rating
	 * 
	 * @param value is of type IExternalRating
	 */
	public void setCounterPartyExtRating(IExternalRating value);

	/**
	 * Set MBB External Rating
	 * 
	 * @param value is of type IExternalRating
	 */
	public void setMbbExtRating(IExternalRating value);

	/**
	 * Set Eligible Margin
	 * 
	 * @param value is of type String
	 */
	public void setEligibleMargin(String value);

	/**
	 * Set Agreed Interest Rate Type
	 * 
	 * @param value is of type String
	 */
	public void setAgreeIntRateType(String value);

	/**
	 * Set Base Currency Code
	 * 
	 * @param value is of type String
	 */
	public void setBaseCurrency(String value);

	/**
	 * Set Counter Party Threshold Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setCounterPartyThresholdAmount(Amount value);

	/**
	 * Set MBB Threshold Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setMbbThresholdAmount(Amount value);

	/**
	 * Set Agent Bank Name
	 * 
	 * @param value is of type String
	 */
	public void setAgentBankName(String value);

	/**
	 * Set Agent Bank Address
	 * 
	 * @param value is of type String
	 */
	public void setAgentBankAddress(String value);

	/**
	 * Set Bank Clearance ID
	 * 
	 * @param value is of type String
	 */
	public void setBankClearanceID(String value);

	/**
	 * Set Bank Account ID
	 * 
	 * @param value is of type String
	 */
	public void setBankAccountID(String value);

	/**
	 * Set Clearance description.
	 * 
	 * @param value is of type String
	 */
	public void setClearingDesc(String value);

	/**
	 * Set notification time.
	 * 
	 * @param value is of type String
	 */
	public void setNotificationTime(String value);

	/**
	 * Set valuation time.
	 * 
	 * @param value is of type String
	 */
	public void setValuationTime(String value);

	/**
	 * Set list of threshold rating
	 * 
	 * @param value is of type List of OBThresholdRating
	 */
	public void setThresholdRatingList(List value);

	/**
	 * Set the status of this agreement.
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value);

	/**
	 * Set the version time of this agreement.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);

}
