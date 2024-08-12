package com.integrosys.cms.batch.reports;

/**
 * Exception to be raised when there is a failure in report server. Normally
 * caller cant do anything on this. Simply check the server status.
 * 
 * @author Chong Jun Yong
 * 
 */
public class ReportServerFailureException extends ReportException {
	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public ReportServerFailureException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public ReportServerFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
