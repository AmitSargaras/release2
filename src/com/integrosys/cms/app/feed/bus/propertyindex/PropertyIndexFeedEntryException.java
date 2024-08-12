/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/PropertyIndexFeedEntryException.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 */
public class PropertyIndexFeedEntryException extends OFAException {

	/**
	 * Default Constructor
	 */
	public PropertyIndexFeedEntryException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public PropertyIndexFeedEntryException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public PropertyIndexFeedEntryException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public PropertyIndexFeedEntryException(String msg, Throwable t) {
		super(msg, t);
	}
}
