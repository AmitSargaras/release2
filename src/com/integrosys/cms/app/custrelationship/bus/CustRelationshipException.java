/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * This exception is thrown whenever an error occurs in any
 * processes within the Customer Relationship.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class CustRelationshipException extends OFAException
{

    /**
     * Default Constructor
     */
    public CustRelationshipException() {
        super();
    }

    /**
     * Construct the exception with a string message
     *
     * @param msg is of type String
     */
    public CustRelationshipException (String msg) {
	  super(msg);
    }

    /**
     * Construct the exception with a throwable object
     *
     * @param obj is of type Throwable
     */
    public CustRelationshipException (Throwable obj) {
        super(obj);
    }

    /**
     * Construct the exception with a string message
     * and a throwable object.
     *
     * @param msg is of type String
     * @param obj is of type Throwable
     */
    public CustRelationshipException (String msg, Throwable obj) {
        super(msg, obj);
    }


}