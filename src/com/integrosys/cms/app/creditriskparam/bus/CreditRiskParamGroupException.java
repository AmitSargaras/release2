/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

import com.integrosys.base.techinfra.exception.OFACheckedException;

/**
 * CreditRiskParamGroupException
 * 
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamGroupException extends OFACheckedException {

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public CreditRiskParamGroupException(String msg) {
		super(msg);
	}

	/**
	 * Constructor provided throwable cause, and with detailed error message
	 * 
	 * @param cause exception that cause this exception to be thrown
	 * @deprecated consider to use constructor with detailed message and
	 *             throwable cause provided
	 */
	public CreditRiskParamGroupException(Throwable cause) {
		super("Error raised in credit risk param module", cause);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public CreditRiskParamGroupException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
