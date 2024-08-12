/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * This exception is thrown whenever an error occurs in any processes within the
 * Liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public LiquidationException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public LiquidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}