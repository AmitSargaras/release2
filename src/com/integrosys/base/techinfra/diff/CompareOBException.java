/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.base.techinfra.diff;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Exception rised while comparing objects
 * @author $Author: ravi $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/13 03:02:43 $ Tag: $Name: $
 */

public class CompareOBException extends OFAException {
	public CompareOBException() {
		super();
	}

	public CompareOBException(String pMsg) {
		super(pMsg);
	}

	public CompareOBException(Throwable pEx) {
		super(pEx);
	}

	public CompareOBException(String pMsg, Throwable pEx) {
		super(pMsg, pEx);
	}

}
