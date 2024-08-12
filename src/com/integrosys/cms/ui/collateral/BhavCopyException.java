package com.integrosys.cms.ui.collateral;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the Bhav Copy package. This exception is thrown
 * for any errors during Bhav Copy processing
 * 
 * @author $Author: Dattatray Thorat 
 * @version 1.0
 * @since $Date: 01/03/2011 02:37:00 
 */

public class BhavCopyException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public BhavCopyException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public BhavCopyException(String msg, Throwable t) {
		super(msg, t);
	}


}
