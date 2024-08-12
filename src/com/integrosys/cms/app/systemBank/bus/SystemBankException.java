/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/DiaryItemException.java,v 1.2 2004/05/17 02:39:04 jtan Exp $
 */
package com.integrosys.cms.app.systemBank.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the diary item package. This exception is thrown
 * for any errors during diary item processing
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/05/17 02:39:04 $ Tag: $Name: $
 */

public class SystemBankException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public SystemBankException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public SystemBankException(String msg, Throwable t) {
		super(msg, t);
	}
}
