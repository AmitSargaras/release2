/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CollaborationTaskNotAllowedException.java,v 1.1 2003/08/15 14:02:05 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//ofa
import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the collaborationtask package. This is thrown if
 * collaboration task is not allowed
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/15 14:02:05 $ Tag: $Name: $
 */

public class CollaborationTaskNotAllowedException extends OFAException {
	/**
	 * Default Constructor
	 */
	public CollaborationTaskNotAllowedException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CollaborationTaskNotAllowedException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public CollaborationTaskNotAllowedException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CollaborationTaskNotAllowedException(String msg, Throwable t) {
		super(msg, t);
	}
}
