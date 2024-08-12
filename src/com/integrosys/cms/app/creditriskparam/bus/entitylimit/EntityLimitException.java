/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * This exception is thrown whenever an error occurs in any
 * processes within the Entity Limit.
 *
 * @author  $Author: skchai $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class EntityLimitException extends OFAException {

	private static final long serialVersionUID = 1L;

	/**
     * Default Constructor
     */
    public EntityLimitException() {
        super();
    }
    
    /**
     * Construct the exception with a string message
     *
     * @param msg is of type String
     */
    public EntityLimitException(String msg) {
        super(msg);
    }
    
    /**
     * Construct the exception with a throwable object
     *
     * @param obj is of type Throwable
     */
    public EntityLimitException (Throwable obj) {
        super(obj);
    }

    /**
     * Construct the exception with a string message
     * and a throwable object.
     *
     * @param msg is of type String
     * @param obj is of type Throwable
     */
    public EntityLimitException (String msg, Throwable obj) {
        super(msg, obj);
    }
}
