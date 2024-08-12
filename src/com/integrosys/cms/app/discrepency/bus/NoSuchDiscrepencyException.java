package com.integrosys.cms.app.discrepency.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Discrepency package. This exception is thrown
 * for any errors during Discrepency processing
 * 
 * @author $Author: Sandeep Shinde 
 * @version 1.0
 * @since $Date: 01/06/2011 02:44:00 $ Tag: $Name: $
 */

public class NoSuchDiscrepencyException extends OFAException{

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public NoSuchDiscrepencyException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public NoSuchDiscrepencyException(String msg, Throwable t) {
		super(msg, t);
	}

}
