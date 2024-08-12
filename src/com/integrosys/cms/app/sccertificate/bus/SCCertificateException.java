/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SCCertificateException.java,v 1.1 2003/08/06 05:28:54 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the certificate package. It should be thrown when
 * there is any exception encountered in the certificate process that requires
 * no special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 05:28:54 $ Tag: $Name: $
 */

public class SCCertificateException extends OFAException {
	/**
	 * Default Constructor
	 */
	public SCCertificateException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public SCCertificateException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public SCCertificateException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public SCCertificateException(String msg, Throwable t) {
		super(msg, t);
	}
}
