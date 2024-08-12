package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class BridgingLoanException extends OFAException {

	/**
	 * Default Constructor
	 */
	public BridgingLoanException() {
		super();
	}

	/**
	 * Construct the exception with a string message
	 * 
	 * @param msg is of type String
	 */
	public BridgingLoanException(String msg) {
		super(msg);
	}

	/**
	 * Construct the exception with a throwable object
	 * 
	 * @param obj is of type Throwable
	 */
	public BridgingLoanException(Throwable obj) {
		super(obj);
	}

	/**
	 * Construct the exception with a string message and a throwable object.
	 * 
	 * @param msg is of type String
	 * @param obj is of type Throwable
	 */
	public BridgingLoanException(String msg, Throwable obj) {
		super(msg, obj);
	}

}
