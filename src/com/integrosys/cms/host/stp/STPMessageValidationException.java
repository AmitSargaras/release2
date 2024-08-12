package com.integrosys.cms.host.stp;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * Based Exception to be raised whenever there is any validaton failed done on
 * the message parsed from XML file.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 * @since 1.1
 */
public abstract class STPMessageValidationException extends STPMessageException {

	private static final long serialVersionUID = 3329458136495577022L;

	/**
	 * Default Constructor to provide error message
	 *
	 * @param msg error message for this exception
	 */
	public STPMessageValidationException(String msg) {
		super(msg);
	}
}