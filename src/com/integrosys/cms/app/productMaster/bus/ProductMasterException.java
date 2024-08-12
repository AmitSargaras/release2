package com.integrosys.cms.app.productMaster.bus;

import com.integrosys.base.techinfra.exception.OFAException;

public class ProductMasterException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public ProductMasterException(String msg) {
		super(msg);
	}
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public ProductMasterException(String msg, Throwable t) {
		super(msg, t);
	}
}
