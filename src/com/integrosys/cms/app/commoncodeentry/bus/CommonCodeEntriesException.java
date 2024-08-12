/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeException.java
 *
 * Created on February 6, 2007, 11:14 AM
 *
 * Purpose: Custom exception class
 * Description: Used by the mainly by the MaintainCommonCodeEntry Module to indicate an exception
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

public class CommonCodeEntriesException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public CommonCodeEntriesException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public CommonCodeEntriesException(String msg, Throwable t) {
		super(msg, t);
	}

}
