package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class NpaTraqCodeMasterException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public NpaTraqCodeMasterException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public NpaTraqCodeMasterException(String msg, Throwable t) {
		super(msg, t);
	}
}
