package com.integrosys.cms.ui.autoupdationlmtsupload;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the FILE  Upload package. This exception is thrown
 * for any errors during FILE Upload processing
 * 
 * @author $Author: Mukesh Mohapatra 
 */

public class AutoupdationLmtsUploadException extends OFAException{
	
	/**
	 * Constructor
	 * @param msg - the message string
	 */
	public AutoupdationLmtsUploadException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public AutoupdationLmtsUploadException(String msg, Throwable t) {
		super(msg, t);
	}


}
