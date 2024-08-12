/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the services of a interest rate business manager.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IInterestRateBusManager extends Serializable {

	/**
	 * Gets the interest rate by interest rate type and month.
	 * 
	 * @param intRateType the type of interest rate
	 * @param monthYear the date for the interest rate
	 * @return a list of interest rate
	 * @throws InterestRateException on error getting the interest rate
	 */
	public IInterestRate[] getInterestRate(String intRateType, Date monthYear) throws InterestRateException;

	/**
	 * Get a list of interest rate by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of interest rate
	 * @throws InterestRateException on error getting the interest rate
	 */
	public IInterestRate[] getInterestRateByGroupID(long groupID) throws InterestRateException;

	/**
	 * Creates a list of interest rate.
	 * 
	 * @param intRates a list of interest rate
	 * @return a newly created interest rate
	 * @throws InterestRateException on erros creating the interest rate
	 */
	public IInterestRate[] createInterestRates(IInterestRate[] intRates) throws InterestRateException;

	/**
	 * Updates the input list of interest rate.
	 * 
	 * @param intRates a list of interest rate
	 * @return updated list of interest rate
	 * @throws InterestRateException on error updating the interest rate
	 */
	public IInterestRate[] updateInterestRates(IInterestRate[] intRates) throws InterestRateException;

}
