/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import java.util.Date;

import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual interest rate and staging interest rate for transaction
 * usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IInterestRateTrxValue extends ICMSTrxValue {
	/**
	 * Gets the actual interestrate objects in this transaction.
	 * 
	 * @return The actual interestrate objects
	 */
	public IInterestRate[] getInterestRates();

	/**
	 * Sets the actual interestrate objects for this transaction.
	 * 
	 * @param intRates the actual interestrate objects
	 */
	public void setInterestRates(IInterestRate[] intRates);

	/**
	 * Gets the staging interestrate objects in this transaction.
	 * 
	 * @return the staging interestrate objects
	 */
	public IInterestRate[] getStagingInterestRates();

	/**
	 * Sets the staging interestrate objects for this transaction.
	 * 
	 * @param intRates the staging interestrate objects
	 */
	public void setStagingInterestRates(IInterestRate[] intRates);

	/**
	 * Get interest rate type code.
	 * 
	 * @return String
	 */
	public String getIntRateType();

	/**
	 * Set interest rate type code.
	 * 
	 * @param intRateType of type String
	 */
	public void setIntRateType(String intRateType);

	/**
	 * Get the date of interest rate.
	 * 
	 * @return Date
	 */
	public Date getMonthYear();

	/**
	 * Set the date of interest rate.
	 * 
	 * @param monthYear of type Date
	 */
	public void setMonthYear(Date monthYear);

}
