/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/StockIndexFeedGroupException.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
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
public class StockIndexFeedGroupException extends OFAException {

	/**
	 * Default Constructor
	 */
	public StockIndexFeedGroupException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public StockIndexFeedGroupException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public StockIndexFeedGroupException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public StockIndexFeedGroupException(String msg, Throwable t) {
		super(msg, t);
	}
}
