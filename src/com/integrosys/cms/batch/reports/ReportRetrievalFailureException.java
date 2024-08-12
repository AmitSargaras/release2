package com.integrosys.cms.batch.reports;

/**
 * Exception to be raised when fail to retrieve report object from persistent
 * storage, such as Database or File Storage
 * 
 * @author Chong Jun Yong
 * 
 */
public class ReportRetrievalFailureException extends ReportException {
	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public ReportRetrievalFailureException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public ReportRetrievalFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
