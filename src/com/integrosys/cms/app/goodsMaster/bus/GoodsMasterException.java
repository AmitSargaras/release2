package com.integrosys.cms.app.goodsMaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class GoodsMasterException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public GoodsMasterException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public GoodsMasterException(String msg, Throwable t) {
		super(msg, t);
	}
}
