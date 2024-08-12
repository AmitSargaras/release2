/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/ForexFeedGroupException.java,v 1.1 2003/07/23 11:11:44 phtan Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: phtan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 11:11:44 $ Tag: $Name: $
 * 
 */
public class ForexFeedGroupException extends OFAException {
	/**
	 * Default Constructor
	 */
	public ForexFeedGroupException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public ForexFeedGroupException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public ForexFeedGroupException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public ForexFeedGroupException(String msg, Throwable t) {
		super(msg, t);
	}
}
