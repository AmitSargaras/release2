/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * @author priya
 *
 */
public class InternalCreditRatingException extends OFAException {

	private static final long serialVersionUID = 1L;

	/**
     * Default Constructor
     */
    public InternalCreditRatingException() {
        super();
    }
    
    /**
     * Construct the exception with a string message
     *
     * @param msg is of type String
     */
    public InternalCreditRatingException(String msg) {
        super(msg);
    }
    
    /**
     * Construct the exception with a throwable object
     *
     * @param obj is of type Throwable
     */
    public InternalCreditRatingException (Throwable obj) {
        super(obj);
    }

    /**
     * Construct the exception with a string message
     * and a throwable object.
     *
     * @param msg is of type String
     * @param obj is of type Throwable
     */
    public InternalCreditRatingException (String msg, Throwable obj) {
        super(msg, obj);
    }
}
