package com.integrosys.cms.batch.common.filereader;

import com.integrosys.base.techinfra.exception.OFAException;

/**
 * Base exception to be used by Process File.
 * 
 * @author Archana Panchapakesan
 * @since 5th July 2011
 * 
 */
public class ProcessFileException extends OFAException {

	/**
	 * Constructor with error detail message supplied
	 * 
	 * @param msg error detail message
	 */
	public ProcessFileException(String msg) {
		super(msg);
	}

	/**
	 * Constructor with error detail message and cause supplied
	 * 
	 * @param msg error detail message
	 * @param cause the cause that throw this exception
	 */
	public ProcessFileException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
