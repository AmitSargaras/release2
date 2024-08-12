package com.integrosys.cms.host.eai.limit;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * Base exception to be raised in the limit module when encounter any error
 * except for those subclass of base EAIMessageException.
 * 
 * @author marvin
 * @author Chong Jun Yong
 */
public abstract class EaiLimitProfileMessageException extends EAIMessageException {

	private static final long serialVersionUID = 4266779199020148975L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public EaiLimitProfileMessageException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public EaiLimitProfileMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
