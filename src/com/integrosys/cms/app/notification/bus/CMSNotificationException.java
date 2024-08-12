/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/CMSNotificationException.java,v 1.1 2005/09/22 02:32:58 whuang Exp $
 */
package com.integrosys.cms.app.notification.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/22 02:32:58 $ Tag: $Name: $
 */

public class CMSNotificationException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public CMSNotificationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor provided throwable cause, and with detailed error message
	 * 
	 * @param cause exception that cause this exception to be thrown
	 * @deprecated consider to use constructor with detailed message and
	 *             throwable cause provided
	 */
	public CMSNotificationException(Throwable t) {
		super("Error raised in Notification module", t);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public CMSNotificationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
