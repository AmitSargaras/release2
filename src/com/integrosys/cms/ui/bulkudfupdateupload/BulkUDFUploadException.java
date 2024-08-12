package com.integrosys.cms.ui.bulkudfupdateupload;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * General exception class for the FILE  Upload package. This exception is thrown
 * for any errors during FILE Upload processing
 * 
 */

public class BulkUDFUploadException extends OFAException{
	
	public BulkUDFUploadException(String msg){
		super(msg);
	}
	
	/**
	 * Constructor
	 * @param msg - message String
	 * @param t - Throwable
	 */
	public BulkUDFUploadException(String msg, Throwable t) {
		super(msg, t);
	}

}
