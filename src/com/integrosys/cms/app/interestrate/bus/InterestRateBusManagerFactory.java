/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

/**
 * This factory creates IInterestRateBusManager.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public InterestRateBusManagerFactory() {
	}

	/**
	 * Create the interestrate business manager.
	 * 
	 * @return interestrate business manager
	 */
	public static IInterestRateBusManager getActualInterestRateBusManager() {
		return new InterestRateBusManagerImpl();
	}

	/**
	 * Create the stage interestrate business manager.
	 * 
	 * @return stage interestrate business manager
	 */
	public static IInterestRateBusManager getStagingInterestRateBusManager() {
		return new InterestRateBusManagerStagingImpl();
	}

}