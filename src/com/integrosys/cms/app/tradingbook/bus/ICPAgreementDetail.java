/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:  $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents counter party, agreement details.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICPAgreementDetail extends java.io.Serializable {

	/**
	 * Get the counter party name
	 * 
	 * @return String
	 */
	public String getCustomerName();

	/**
	 * Set the counter party name
	 * 
	 * @param value is of type String
	 */
	public void setCustomerName(String value);

	/**
	 * Get the counter party LE ID
	 * 
	 * @return long
	 */
	// public long getLEID();
	/**
	 * Set the counter party LE ID
	 * 
	 * @param value is of type long
	 */
	// public void setLEID(long value);
	/**
	 * Get the counter party LE ID
	 * 
	 * @return long
	 */
	public String getLEID();

	/**
	 * Set the counter party LE ID
	 * 
	 * @param value is of type long
	 */
	public void setLEID(String value);

	/**
	 * Get the relationship start date
	 * 
	 * @return Date
	 */
	public Date getRelationshipStartDate();

	/**
	 * Set the relationship start date
	 * 
	 * @param value is of type Date
	 */
	public void setRelationshipStartDate(Date value);

	/*
	 * Get the country of incorporation
	 * 
	 * @return String
	 */
	public String getIncorpCountry();

	/**
	 * Set the country of incorporation
	 * 
	 * @param value is of type String
	 */
	public void setIncorpCountry(String value);

	/**
	 * Get agreement ID.
	 * 
	 * @return long
	 */
	public long getAgreementID();

	/**
	 * Set the agreement ID
	 * 
	 * @param value is of type long
	 */
	public void setAgreementID(long value);

	/**
	 * Get limit profile ID.
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Set the limit profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value);

	/**
	 * Get agreement type.
	 * 
	 * @return String
	 */
	public String getAgreementType();

	/**
	 * Set the agreement type
	 * 
	 * @param value is of type String
	 */
	public void setAgreementType(String value);

	/**
	 * Get base currency code.
	 * 
	 * @return String
	 */
	public String getBaseCurrency();

	/**
	 * Set base currency code
	 * 
	 * @param value is of type String
	 */
	public void setBaseCurrency(String value);

	/**
	 * Get mininum transfer amount.
	 * 
	 * @return Amount
	 */
	public Amount getMinTransferAmount();

	/**
	 * Set the mininum transfer amount
	 * 
	 * @param value is of type Amount
	 */
	public void setMinTransferAmount(Amount value);

	/**
	 * Get counter party rating.
	 * 
	 * @return String
	 */
	public String getCPRating();

	/**
	 * Set the counter party rating
	 * 
	 * @param value is of type String
	 */
	public void setCPRating(String value);

	/**
	 * Get the closing cash interest date
	 * 
	 * @return Date
	 */
	public Date getClosingCashInterestDate();

	/**
	 * Set the closing cash interest date
	 * 
	 * @param value is of type Date
	 */
	public void setClosingCashInterestDate(Date value);

	/**
	 * Get closing cash interest.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getClosingCashInterest();

	/**
	 * Set the closing cash interest
	 * 
	 * @param value is of type BigDecimal
	 */
	public void setClosingCashInterest(BigDecimal value);

	/**
	 * Get interest rate type.
	 * 
	 * @return String
	 */
	public String getIntRateType();

	/**
	 * Set the interest rate type
	 * 
	 * @param value is of type String
	 */
	public void setIntRateType(String value);

	/**
	 * Get the BCA Status
	 * 
	 * @return String
	 */
	public String getBCAStatus();

	/**
	 * Set the BCA Status
	 * 
	 * @param value is of type String
	 */
	public void setBCAStatus(String value);

}