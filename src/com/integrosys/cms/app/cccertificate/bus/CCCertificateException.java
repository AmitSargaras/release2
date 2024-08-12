/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCertificateException.java,v 1.1 2003/08/04 12:53:48 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the certificate package. It should be thrown when
 * there is any exception encountered in the certificate process that requires
 * no special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/04 12:53:48 $ Tag: $Name: $
 */

public class CCCertificateException extends OFAException {
	/**
	 * Default Constructor
	 */
	public CCCertificateException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CCCertificateException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public CCCertificateException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CCCertificateException(String msg, Throwable t) {
		super(msg, t);
	}
}
