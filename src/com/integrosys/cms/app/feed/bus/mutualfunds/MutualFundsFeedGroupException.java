/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedGroupException.java,v 1.2 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsFeedGroupException extends OFAException {

	/**
	 * Default Constructor
	 */
	public MutualFundsFeedGroupException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public MutualFundsFeedGroupException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public MutualFundsFeedGroupException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public MutualFundsFeedGroupException(String msg, Throwable t) {
		super(msg, t);
	}
}
