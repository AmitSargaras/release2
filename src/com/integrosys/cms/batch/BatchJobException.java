package com.integrosys.cms.batch;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Base exception to be used by Batch Job module.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class BatchJobException extends OFAException {

	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public BatchJobException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public BatchJobException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
