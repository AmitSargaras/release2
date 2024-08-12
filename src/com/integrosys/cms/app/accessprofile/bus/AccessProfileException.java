/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/AccessProfileException.java,v 1.1 2003/10/20 11:04:13 btchng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/10/20 11:04:13 $ Tag: $Name: $
 */
public class AccessProfileException extends OFAException {

	/**
	 * Default Constructor
	 */
	public AccessProfileException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public AccessProfileException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public AccessProfileException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public AccessProfileException(String msg, Throwable t) {
		super(msg, t);
	}
}
