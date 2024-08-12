package com.integrosys.cms.host.stp;

import com.integrosys.cms.host.eai.EAIMessageException;

/**
 * Exception to be raised whenever there is any error when interface with CMS
 * workflow engine.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 * @since 1.1
 */
public class STPTransactionException extends STPMessageException {

	private static final long serialVersionUID = -8073876638288703555L;

	private static final String WORKFLOW_ERROR_CODE = "WORKFLOW_ERROR";

	/**
	 * Default Constructor to provide error message
	 *
	 * @param msg error message for this exception
	 */
	public STPTransactionException(String msg) {
		super(msg);
	}

	/**
	 * Default Constructor to provide error message and throwable cause.
	 *
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public STPTransactionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getErrorCode() {
		return WORKFLOW_ERROR_CODE;
	}
}