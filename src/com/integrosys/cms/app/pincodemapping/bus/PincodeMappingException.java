package com.integrosys.cms.app.pincodemapping.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class PincodeMappingException extends OFAException {

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - the message string
	 */
	public PincodeMappingException(String msg) {
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
	public PincodeMappingException(String msg, Throwable t) {
		super(msg, t);
	}
}
