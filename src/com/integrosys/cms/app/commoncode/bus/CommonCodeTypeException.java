package com.integrosys.cms.app.commoncode.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public CommonCodeTypeException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public CommonCodeTypeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
