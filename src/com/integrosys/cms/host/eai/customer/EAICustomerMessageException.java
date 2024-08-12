package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class EAICustomerMessageException extends EAIMessageException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2752449759371755004L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public EAICustomerMessageException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public EAICustomerMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
