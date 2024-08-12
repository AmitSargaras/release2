/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/DiaryItemException.java,v 1.2 2004/05/17 02:39:04 jtan Exp $
 */
package com.integrosys.cms.app.email.notification.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author Anil Pandey
 */

public class EmailNotificationException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public EmailNotificationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public EmailNotificationException(String msg, Throwable t) {
		super(msg, t);
	}
}
