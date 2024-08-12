package com.integrosys.cms.host.eai.document;

/**
 * Base Exception to be raised when the checklist items is not valid, either
 * status, or no checklist items.
 * 
 * @author Iwan Satria
 * @since 1.0
 */
public abstract class InvalidCheckListItemException extends EAIDocumentMessageException {

	private static final long serialVersionUID = -1240913997659901495L;

	/**
	 * Default Constructor to provide error message
	 * 
	 * @param msg error message for this exception
	 */
	public InvalidCheckListItemException(String msg) {
		super(msg);
	}
}
