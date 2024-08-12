/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CustodianException.java,v 1.1 2003/06/09 11:38:02 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the custodian package. It should be thrown when
 * there is any exception encountered in the custodian process that requires no
 * special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/09 11:38:02 $ Tag: $Name: $
 */

public class CustodianException extends OFAException {
	/**
	 * Default Constructor
	 */
	public CustodianException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CustodianException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public CustodianException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CustodianException(String msg, Throwable t) {
		super(msg, t);
	}
}
