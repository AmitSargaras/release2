package com.integrosys.cms.batch;


/**
 * <p>
 * Exception to be raised when there is fail of loading batch feed, but not
 * parsing batch feed. Which consider to be fatal, and whole batch job should be
 * terminated.
 * 
 * <p>
 * Normally the cause is the fatal exception raised from IO.
 * 
 * @author Chong Jun Yong
 * 
 */
public class BatchFeedLoadException extends BatchFeedException {
	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public BatchFeedLoadException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
