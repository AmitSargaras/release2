/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/StockIndexFeedEntryException.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 * 
 */
public class StockIndexFeedEntryException extends OFAException {

	/**
	 * Default Constructor
	 */
	public StockIndexFeedEntryException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public StockIndexFeedEntryException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public StockIndexFeedEntryException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public StockIndexFeedEntryException(String msg, Throwable t) {
		super(msg, t);
	}
}
