package com.integrosys.cms.app.commodity.main;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 11:10:32 AM To change this template use File | Settings | File Templates.
 */
public class CommodityException extends OFAException {
	/**
	 * Default Constructor
	 */
	public CommodityException() {
		super();
	}

	/**
	 * Construct the exception with a string message
	 * 
	 * @param msg is of type String
	 */
	public CommodityException(String msg) {
		super(msg);
	}

	/**
	 * Construct the exception with a throwable object
	 * 
	 * @param obj is of type Throwable
	 */
	public CommodityException(Throwable obj) {
		super(obj);
	}

	/**
	 * Construct the exception with a string message and a throwable object.
	 * 
	 * @param msg is of type String
	 * @param obj is of type Throwable
	 */
	public CommodityException(String msg, Throwable obj) {
		super(msg, obj);
	}
}
