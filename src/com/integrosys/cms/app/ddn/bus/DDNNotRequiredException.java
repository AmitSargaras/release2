/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/DDNNotRequiredException.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the ddn package. It should be thrown if DDN is
 * not required for a BCA
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $ Tag: $Name: $
 */

public class DDNNotRequiredException extends OFAException {
	/**
	 * Default Constructor
	 */
	public DDNNotRequiredException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public DDNNotRequiredException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public DDNNotRequiredException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public DDNNotRequiredException(String msg, Throwable t) {
		super(msg, t);
	}
}
