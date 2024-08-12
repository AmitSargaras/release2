/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListException.java,v 1.1 2003/06/24 11:36:00 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * General exception class for the checklist package. It should be thrown when
 * there is any exception encountered in the checklist process that requires no
 * special handling.
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */

public class CheckListException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public CheckListException(String msg) {
		super(msg);
	}

	/**
	 * Constructor provided throwable cause, and with detailed error message
	 * 
	 * @param cause exception that cause this exception to be thrown
	 * @deprecated consider to use constructor with detailed message and
	 *             throwable cause provided
	 */
	public CheckListException(Throwable t) {
		super("Error Raised in Checklist module", t);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public CheckListException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
