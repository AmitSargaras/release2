package com.integrosys.cms.app.riskType.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class RiskTypeException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public RiskTypeException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public RiskTypeException(String msg, Throwable t) {
		super(msg, t);
	}
}
