/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/UnitTrustFeedEntryException.java,v 1.2 2003/08/08 04:26:15 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/08 04:26:15 $ Tag: $Name: $
 * 
 */
public class UnitTrustFeedEntryException extends OFAException {

	/**
	 * Default Constructor
	 */
	public UnitTrustFeedEntryException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public UnitTrustFeedEntryException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public UnitTrustFeedEntryException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public UnitTrustFeedEntryException(String msg, Throwable t) {
		super(msg, t);
	}
}
