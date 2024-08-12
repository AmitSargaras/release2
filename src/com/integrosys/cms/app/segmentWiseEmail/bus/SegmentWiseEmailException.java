package com.integrosys.cms.app.segmentWiseEmail.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class SegmentWiseEmailException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public SegmentWiseEmailException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public SegmentWiseEmailException(String msg, Throwable t) {
		super(msg, t);
	}
}
