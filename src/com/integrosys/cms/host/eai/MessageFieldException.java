package com.integrosys.cms.host.eai;

/**
 * Base class for exception to be throw when there is validation error on the
 * field in the message, such as mandatory, exceed certain range, etc.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class MessageFieldException extends EAIMessageValidationException {

	private static final long serialVersionUID = 376560968024074730L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public MessageFieldException(String msg) {
		super(msg);
	}

}
