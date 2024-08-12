/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CustomerException.java,v 1.1 2003/06/18 08:05:09 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Description: This exception represents a generic exception that occurs during
 * the execution of Customer module services.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/18 08:05:09 $ Tag: $Name: $
 */
public class CustomerException extends OFAException {

	/*
	 * OFAException constructor - no message, and no exception to chain
	 * 
	 * @param nil
	 * 
	 * @return OFAException
	 */
	public CustomerException() {
		super();
	}

	/*
	 * OFAException constructor - no previous exception to chain
	 * 
	 * @param String msg - message for this exception
	 * 
	 * @return OFAException
	 */
	public CustomerException(String pMsg) {
		super(pMsg);
	} // OFAException()

	/*
	 * OFAException constructor - chain a previous exception
	 * 
	 * @param Throwable pEx - previous exception to chain
	 * 
	 * @return OFAException
	 */
	public CustomerException(Throwable pEx) {
		super(pEx);

	} // OFAException()

	/*
	 * OFAException constructor - add a message, and chain a previous exception
	 * 
	 * @param String pMsg - message to add
	 * 
	 * @param Throwable pEx - previous exception to chain
	 * 
	 * @return OFAException
	 */
	public CustomerException(String pMsg, Throwable pEx) {
		super(pMsg, pEx);
	}

} // OFAException

