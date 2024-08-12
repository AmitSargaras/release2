/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

/**
 * This factory creates ITradingBookProxy object.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookProxyFactory {
	/**
	 * Default Constructor
	 */
	public TradingBookProxyFactory() {
	}

	/**
	 * Creates an ITradingBookProxy.
	 * 
	 * @return ITradingBookProxy
	 */
	public static ITradingBookProxy getProxy() {
		return new TradingBookProxyImpl();
	}
}