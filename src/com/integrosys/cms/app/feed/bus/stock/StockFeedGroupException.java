/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockFeedGroupException.java,v 1.2 2003/08/07 08:34:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/07 08:34:09 $ Tag: $Name: $
 * 
 */
public class StockFeedGroupException extends OFAException {

	/**
	 * Default Constructor
	 */
	public StockFeedGroupException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public StockFeedGroupException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public StockFeedGroupException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public StockFeedGroupException(String msg, Throwable t) {
		super(msg, t);
	}
}
