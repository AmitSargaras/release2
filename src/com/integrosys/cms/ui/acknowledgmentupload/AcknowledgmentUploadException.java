package com.integrosys.cms.ui.acknowledgmentupload;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the FILE  Upload package. This exception is thrown
 * for any errors during FILE Upload processing
 * 
 * @author $Author: Mukesh Mohapatra 
 */

public class AcknowledgmentUploadException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public AcknowledgmentUploadException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public AcknowledgmentUploadException(String msg, Throwable t) {
		super(msg, t);
	}


}
