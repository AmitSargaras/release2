/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/StockFeedEntryException.java,v 1.2 2003/08/07 08:34:09 btchng Exp $
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
public class StockFeedEntryException extends OFAException {

	/**
	 * Default Constructor
	 */
	public StockFeedEntryException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public StockFeedEntryException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public StockFeedEntryException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public StockFeedEntryException(String msg, Throwable t) {
		super(msg, t);
	}
}
