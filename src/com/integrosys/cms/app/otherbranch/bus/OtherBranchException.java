package com.integrosys.cms.app.otherbranch.bus;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Other bank package. This exception is thrown
 * for any errors during other bank processing
 * 
 * @author $Author: Dattatray Thorat 
 * @version 1.0
 * @since $Date: 18/02/2011 02:37:00 
 */

public class OtherBranchException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public OtherBranchException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public OtherBranchException(String msg, Throwable t) {
		super(msg, t);
	}


}
