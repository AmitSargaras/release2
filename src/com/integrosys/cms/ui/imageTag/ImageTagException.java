package com.integrosys.cms.ui.imageTag;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Image Tag package. This exception is thrown
 * for any errors during Image Tag processing
 * 
 * @author abhijit.rudrakshawar 
 */

public class ImageTagException extends OFAException {

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - the message string
	 */
	public ImageTagException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - message String
	 * @param t
	 *            - Throwable
	 */
	public ImageTagException(String msg, Throwable t) {
		super(msg, t);
	}
}
