package com.integrosys.cms.app.leiDateValidation.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class LeiDateValidationException extends OFAException{

	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public LeiDateValidationException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public LeiDateValidationException(String msg, Throwable t) {
		super(msg, t);
	}
}
