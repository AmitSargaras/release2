/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/BondFeedEntryException.java,v 1.2 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 * 
 */
public class ApprovalMatrixEntryException extends OFAException {

	/**
	 * Default Constructor
	 */
	public ApprovalMatrixEntryException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public ApprovalMatrixEntryException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public ApprovalMatrixEntryException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public ApprovalMatrixEntryException(String msg, Throwable t) {
		super(msg, t);
	}
}
