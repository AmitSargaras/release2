package com.integrosys.cms.app.ws.dto;

import com.integrosys.base.techinfra.exception.OFAException;

public class PSRDataLogException extends OFAException {

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - the message string
	 */
	public PSRDataLogException(String msg) {
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
	public PSRDataLogException(String msg, Throwable t) {
		super(msg, t);
	}
}
