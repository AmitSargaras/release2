package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class CustExposureException extends OFAException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 */
	public CustExposureException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - the message string
	 */
	public CustExposureException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * 
	 * @param t
	 *            - Throwable
	 */
	public CustExposureException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            - message String
	 * @param t
	 *            - Throwable
	 */
	public CustExposureException(String msg, Throwable t) {
		super(msg, t);
	}
}
