package com.integrosys.cms.batch;

/**
 * Super class for all batch feed exception.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class BatchFeedException extends BatchJobException {
	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public BatchFeedException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public BatchFeedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
