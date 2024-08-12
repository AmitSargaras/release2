/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ReportException.java,v 1.2 2006/03/08 07:04:29 hshii Exp $
 */
package com.integrosys.cms.batch.reports;

//ofa

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the report package. This exception is thrown for
 * any errors during report processing
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/03/08 07:04:29 $ Tag: $Name: $
 */

public class ReportException extends OFAException {
	/**
	 * Default Constructor
	 */
	public ReportException() {
		super();
	}

	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public ReportException(String msg) {
		super(msg);
	}

	/**
	 * Constructor
	 * @param t - Throwable
	 */
	public ReportException(Throwable t) {
		super(t);
	}

	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public ReportException(String msg, Throwable t) {
		super(msg, t);
	}
}
