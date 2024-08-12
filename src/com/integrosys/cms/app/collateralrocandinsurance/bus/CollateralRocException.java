package com.integrosys.cms.app.collateralrocandinsurance.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class CollateralRocException extends OFAException{

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CollateralRocException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CollateralRocException(String msg, Throwable t) {
		super(msg, t);
	}
}
