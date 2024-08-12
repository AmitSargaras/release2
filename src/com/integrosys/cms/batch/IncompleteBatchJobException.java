package com.integrosys.cms.batch;


/**
 * Exception to be used when the batch job is incomplete.
 * 
 * @author Chong Jun Yong
 * 
 */
public class IncompleteBatchJobException extends BatchJobException {
	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public IncompleteBatchJobException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public IncompleteBatchJobException(String msg, Throwable t) {
		super(msg, t);
	}

}
