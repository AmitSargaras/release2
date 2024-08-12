package com.integrosys.cms.app.excludedfacility.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class ExcludedFacilityException  extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public ExcludedFacilityException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public ExcludedFacilityException(String msg, Throwable t) {
		super(msg, t);
	}
}
