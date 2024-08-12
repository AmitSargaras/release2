/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

/**
 * This factory class will load ITradingBookDAO implementations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookDAOFactory {
	/**
	 * Create a default tradingbook DAO implementation.
	 * 
	 * @return ITradingBookDAO
	 */
	public static ITradingBookDAO getDAO() {
		return new TradingBookDAO();
	}

	/**
	 * Create a default tradingbook DAO implementation for staging.
	 * 
	 * @return ITradingBookDAO
	 */
	public static ITradingBookDAO getStagingDAO() {
		return new TradingBookStagingDAO();
	}

}