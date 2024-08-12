package com.integrosys.cms.host.eai;

/**
 * Based Exception to be raised whenever there is any validaton failed done on
 * the message parsed from XML file.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public abstract class EAIMessageValidationException extends EAIMessageException {

	private static final long serialVersionUID = 3329458136495577022L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public EAIMessageValidationException(String msg) {
		super(msg);
	}
}
