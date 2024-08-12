package com.integrosys.cms.host.eai.document;

/**
 * Exception to be raised when there is any error encounter when in checklist
 * template module which is uncategorized.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CheckListTemplateSystemException extends EAIDocumentMessageException {

	private static final long serialVersionUID = -2598224610913600454L;

	private static final String CHECKLIST_TEMPLATE_SYSTEM_ERROR_CODE = "TEMPLATE_ERROR";

	/**
	 * Default Constructor to provide error message and throwable cause.
	 * 
	 * @param msg error message for this exception
	 * @param cause exception that cause this exception to be thrown
	 */
	public CheckListTemplateSystemException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public String getErrorCode() {
		return CHECKLIST_TEMPLATE_SYSTEM_ERROR_CODE;
	}
}
