package com.integrosys.cms.app.insurancecoveragedtls.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Insurance Coverage Details package. This exception is thrown
 * for any errors during Insurance Coverage Details processing
 * 
 * @author $Author: Dattatray Thorat 
 * @version 1.0
 * 
 */

public class InsuranceCoverageDtlsException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public InsuranceCoverageDtlsException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public InsuranceCoverageDtlsException(String msg, Throwable t) {
		super(msg, t);
	}


}
