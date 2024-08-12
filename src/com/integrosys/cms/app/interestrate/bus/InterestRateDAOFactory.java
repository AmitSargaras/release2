/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

/**
 * This factory class will load IInterestRateDAO implementations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateDAOFactory {
	/**
	 * Create a default interestrate DAO implementation.
	 * 
	 * @return IInterestRateDAO
	 */
	public static IInterestRateDAO getDAO() {
		return new InterestRateDAO();
	}

	/**
	 * Create a default interestrate DAO implementation for staging.
	 * 
	 * @return IInterestRateDAO
	 */
	public static IInterestRateDAO getStagingDAO() {
		return new InterestRateStagingDAO();
	}

}