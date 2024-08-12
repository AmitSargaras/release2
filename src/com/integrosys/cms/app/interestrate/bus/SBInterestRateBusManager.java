/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBObject;

/**
 * Remote interface to the interestrate business manager session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBInterestRateBusManager extends EJBObject {

	/**
	 * Gets the interest rate by interest rate type and month.
	 * 
	 * @param intRateType the type of interest rate
	 * @param monthYear the date for the interest rate
	 * @return a list of interest rate
	 * @throws InterestRateException on error getting the interest rate
	 * @throws RemoteException on error during remote method call
	 */
	public IInterestRate[] getInterestRate(String intRateType, Date monthYear) throws InterestRateException,
			RemoteException;

	/**
	 * Get a list of interest rate by its group id.
	 * 
	 * @param groupID group id
	 * @return a list of interest rate
	 * @throws InterestRateException on error getting the interest rate
	 * @throws RemoteException on error during remote method call
	 */
	public IInterestRate[] getInterestRateByGroupID(long groupID) throws InterestRateException, RemoteException;

	/**
	 * Creates a list of interest rate.
	 * 
	 * @param intRates a list of interest rate
	 * @return a newly created interest rate
	 * @throws InterestRateException on erros creating the interest rate
	 * @throws RemoteException on error during remote method call
	 */
	public IInterestRate[] createInterestRates(IInterestRate[] intRates) throws InterestRateException, RemoteException;

	/**
	 * Updates the input list of interest rate.
	 * 
	 * @param intRates a list of interest rate
	 * @return updated list of interest rate
	 * @throws InterestRateException on error updating the interest rate
	 * @throws RemoteException on error during remote method call
	 */
	public IInterestRate[] updateInterestRates(IInterestRate[] intRates) throws InterestRateException, RemoteException;
}