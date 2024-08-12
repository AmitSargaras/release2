package com.integrosys.cms.app.insurancecoverage.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Relationship Manager package. This exception is thrown
 * for any errors during Insurance Coverage processing
 * 
 * @author $Author: Dattatray Thorat 
 * @version 1.0
 */

public class InsuranceCoverageException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public InsuranceCoverageException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public InsuranceCoverageException(String msg, Throwable t) {
		super(msg, t);
	}


}
