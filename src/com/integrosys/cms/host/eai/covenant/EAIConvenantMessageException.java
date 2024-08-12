package com.integrosys.cms.host.eai.covenant;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * 
 * @author Thurein</br>
 * @since 1.1</br>
 * @version 1.0</br>
 * 
 *          Super class for all the covenant messages related exceptions i.e <li>
 *          CV001<li>CV002R <li>CV002I
 * 
 */
public abstract class EAIConvenantMessageException extends EAIMessageException {

	private static final long serialVersionUID = -3071446915274726571L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public EAIConvenantMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public EAIConvenantMessageException(String msg) {
		super(msg);
	}
}
