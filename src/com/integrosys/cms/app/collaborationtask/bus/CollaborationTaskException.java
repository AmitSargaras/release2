/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CollaborationTaskException.java,v 1.1 2003/08/14 13:25:11 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the collaborationtask package. It should be
 * thrown when there is any exception encountered in the certificate process
 * that requires no special handling.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 13:25:11 $ Tag: $Name: $
 */

public class CollaborationTaskException extends OFAException {
	/**
	 * Default Constructor
	 */
	public CollaborationTaskException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CollaborationTaskException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public CollaborationTaskException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CollaborationTaskException(String msg, Throwable t) {
		super(msg, t);
	}
}
