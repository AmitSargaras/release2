package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * General exception class for the checklist package. It should be thrown when
 * there is any exception encountered in the checklist process that requires no
 * special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:36:00 $ Tag: $Name: $
 */

public class RecurrentException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public RecurrentException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public RecurrentException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
