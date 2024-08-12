/*
 * Created by IntelliJ IDEA.
 * User: Sulin
 * Date: Oct 20, 2003
 * Time: 2:51:15 PM
 */
package com.integrosys.cms.host.eai.document;

import com.integrosys.cms.host.eai.EAIHeader;

/**
 * Exception to be raised when both SCChecklist and CCChecklist tags are
 * provided. Only one can be provided.
 * 
 * @author Iwan Satria
 * @author Chong Jun Yong
 * @since 1.0
 */
public class IncorrectDocumentMessageInfoException extends EAIDocumentMessageException {

	private static final long serialVersionUID = 7610940789121969631L;

	private static final String INCORRECT_DOC_INFO_ERROR_CODE = "INV_DOC";

	/**
	 * Default Constructor to provide header info
	 * 
	 * @param header the message header
	 */
	public IncorrectDocumentMessageInfoException(EAIHeader header) {
		super("Either [SCChecklist] or [CCChecklist] to be provided, but not both; header info [" + header + "]");
	}

	public String getErrorCode() {
		return INCORRECT_DOC_INFO_ERROR_CODE;
	}

}
