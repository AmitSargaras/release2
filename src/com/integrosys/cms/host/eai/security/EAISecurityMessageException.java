package com.integrosys.cms.host.eai.security;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * Base exception to be raised when there is any error in the security module.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class EAISecurityMessageException extends EAIMessageException {

	private static final long serialVersionUID = -4192967568273409305L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public EAISecurityMessageException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public EAISecurityMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
