/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This factory creates ITradingBookBusManager.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public TradingBookBusManagerFactory() {
	}

	/**
	 * Create the tradingbook business manager.
	 * 
	 * @return tradingbook business manager
	 */
	public static ITradingBookBusManager getActualTradingBookBusManager() {
		return new TradingBookBusManagerImpl();
	}

	/**
	 * Create the stage tradingbook business manager.
	 * 
	 * @return stage tradingbook business manager
	 */
	public static ITradingBookBusManager getStagingTradingBookBusManager() {
		return new TradingBookBusManagerStagingImpl();
	}

}