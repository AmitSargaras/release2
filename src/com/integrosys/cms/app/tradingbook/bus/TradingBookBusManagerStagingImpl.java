/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the ITradingBookManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookBusManagerStagingImpl extends TradingBookBusManagerImpl {
	/**
	 * Default constructor.
	 */
	public TradingBookBusManagerStagingImpl() {
		super();
	}

	/**
	 * helper method to get an ejb object to tradingbook business manager
	 * session bean.
	 * 
	 * @return tradingbook manager ejb object
	 * @throws TradingBookException on errors encountered
	 */
	protected SBTradingBookBusManager getBusManager() throws TradingBookException {
		SBTradingBookBusManager theEjb = (SBTradingBookBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_TRADING_BOOK_MGR_STAGING_JNDI, SBTradingBookBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new TradingBookException("SBTradingBookBusManager for Staging is null!");
		}

		return theEjb;
	}
}