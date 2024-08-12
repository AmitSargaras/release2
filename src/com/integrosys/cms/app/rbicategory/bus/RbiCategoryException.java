
package com.integrosys.cms.app.rbicategory.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author Govind.Sahu
 */

public class RbiCategoryException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public RbiCategoryException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public RbiCategoryException(String msg, Throwable t) {
		super(msg, t);
	}
}
