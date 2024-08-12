/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.proxy;

/**
 * This factory creates IInterestRateProxy object.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateProxyFactory {
	/**
	 * Default Constructor
	 */
	public InterestRateProxyFactory() {
	}

	/**
	 * Creates an IInterestRateProxy.
	 * 
	 * @return IInterestRateProxy
	 */
	public static IInterestRateProxy getProxy() {
		return new InterestRateProxyImpl();
	}
}