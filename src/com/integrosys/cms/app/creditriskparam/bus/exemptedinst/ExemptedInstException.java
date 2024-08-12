/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * This exception is thrown whenever an error occurs in any
 * processes within the Exempted Institution.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class ExemptedInstException extends OFAException {

	private static final long serialVersionUID = 1L;

	/**
     * Default Constructor
     */
    public ExemptedInstException() {
        super();
    }
    
    /**
     * Construct the exception with a string message
     *
     * @param msg is of type String
     */
    public ExemptedInstException(String msg) {
        super(msg);
    }
    
    /**
     * Construct the exception with a throwable object
     *
     * @param obj is of type Throwable
     */
    public ExemptedInstException (Throwable obj) {
        super(obj);
    }

    /**
     * Construct the exception with a string message
     * and a throwable object.
     *
     * @param msg is of type String
     * @param obj is of type Throwable
     */
    public ExemptedInstException (String msg, Throwable obj) {
        super(msg, obj);
    }
}
