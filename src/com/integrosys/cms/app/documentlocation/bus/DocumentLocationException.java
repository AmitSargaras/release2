/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/DocumentLocationException.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the documentlocation package. It should be thrown
 * when there is any exception encountered in the certificate process that
 * requires no special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */

public class DocumentLocationException extends OFAException {
	/**
	 * Default Constructor
	 */
	public DocumentLocationException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public DocumentLocationException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public DocumentLocationException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public DocumentLocationException(String msg, Throwable t) {
		super(msg, t);
	}
}
