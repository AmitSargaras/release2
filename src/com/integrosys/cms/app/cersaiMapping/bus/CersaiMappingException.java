package com.integrosys.cms.app.cersaiMapping.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class CersaiMappingException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CersaiMappingException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CersaiMappingException(String msg, Throwable t) {
		super(msg, t);
	}
}
