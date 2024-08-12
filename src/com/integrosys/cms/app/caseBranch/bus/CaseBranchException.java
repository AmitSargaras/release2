/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/DiaryItemException.java,v 1.2 2004/05/17 02:39:04 jtan Exp $
 */
package com.integrosys.cms.app.caseBranch.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author abhijit.rudrakshawar
 */

public class CaseBranchException extends OFAException {

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public CaseBranchException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public CaseBranchException(String msg, Throwable t) {
		super(msg, t);
	}
}
