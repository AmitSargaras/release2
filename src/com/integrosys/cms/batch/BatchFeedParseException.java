package com.integrosys.cms.batch;

/**
 * Exception to be raised when there fail of parsing batch feed into format we
 * wanted. This suppose to keep checked exception, then throw as
 * RuntimeException.
 * 
 * @author Chong Jun Yong
 * 
 */
public class BatchFeedParseException extends BatchFeedException {
	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public BatchFeedParseException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public BatchFeedParseException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
