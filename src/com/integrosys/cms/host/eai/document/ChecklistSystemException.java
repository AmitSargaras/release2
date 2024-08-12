package com.integrosys.cms.host.eai.document;

/**
 * Exception to be raised whenever there is error to interface with CMS
 * Checklist module which is non recoverable.
 * 
 * @author Chong Jun Yong
 * 
 */
public class ChecklistSystemException extends EAIDocumentMessageException {

	private static final long serialVersionUID = -1847945196384581147L;

	private static final String CHECKLIST_SYSTEM_ERROR_CODE = "CHECKLIST_ERROR";

	/**
	 * Default Constructor to provide error detailed message and throwable cause
	 * 
	 * @param msg the detailed message to describe the exception
	 * @param cause the cause to throw this exception, normally is the checklist
	 *        exception
	 */
	public ChecklistSystemException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getErrorCode() {
		return CHECKLIST_SYSTEM_ERROR_CODE;
	}
}
