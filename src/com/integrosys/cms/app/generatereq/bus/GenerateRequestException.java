/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/GenerateRequestException.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the certificate package. It should be thrown when
 * there is any exception encountered in the certificate process that requires
 * no special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */

public class GenerateRequestException extends OFAException {
	/**
	 * Default Constructor
	 */
	public GenerateRequestException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public GenerateRequestException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public GenerateRequestException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public GenerateRequestException(String msg, Throwable t) {
		super(msg, t);
	}
}
