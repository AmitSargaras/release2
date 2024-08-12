/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.proxy;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * This exception is thrown during checker approve a deleted GMRA deal because
 * the GMRA deal could not be deleted due to the GMRA deal is still involves in
 * valuation transaction.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class DeleteGMRADealException extends OFAException {

	/**
	 * Default Constructor
	 */
	public DeleteGMRADealException() {
		super();
	}

	/**
	 * Construct the exception with a string message
	 * 
	 * @param msg is of type String
	 */
	public DeleteGMRADealException(String msg) {
		super(msg);
	}

	/**
	 * Construct the exception with a throwable object
	 * 
	 * @param obj is of type Throwable
	 */
	public DeleteGMRADealException(Throwable obj) {
		super(obj);
	}

	/**
	 * Construct the exception with a string message and a throwable object.
	 * 
	 * @param msg is of type String
	 * @param obj is of type Throwable
	 */
	public DeleteGMRADealException(String msg, Throwable obj) {
		super(msg, obj);
	}
}