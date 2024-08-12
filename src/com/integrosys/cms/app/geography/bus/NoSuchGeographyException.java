package com.integrosys.cms.app.geography.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Geography package. This exception is thrown
 * for any errors during Geography processing
 * 
 * @author $Author: Sandeep Shinde 
 * @version 1.0
 * @since $Date: 18/02/2011 02:37:00 $ Tag: $Name: $
 */

public class NoSuchGeographyException extends OFAException{

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public NoSuchGeographyException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public NoSuchGeographyException(String msg, Throwable t) {
		super(msg, t);
	}

}
