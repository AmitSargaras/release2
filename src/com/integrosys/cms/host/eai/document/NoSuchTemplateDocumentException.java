package com.integrosys.cms.host.eai.document;

/**
 * Exception to be raised when the required template doesn't exist
 * 
 * @author Iwan Satria
 * @author Chong Jun Yong
 * @since 1.0
 */
public class NoSuchTemplateDocumentException extends EAIDocumentMessageException {

	private static final long serialVersionUID = -4147678047480205774L;

	private static final String TEMPLATE_NOT_FOUND_ERROR_CODE = "TEMPLATE_NOT_FOUND";

	/**
	 * Constructor to provide Template Id
	 * 
	 * @param templateId template id
	 */
	public NoSuchTemplateDocumentException(long templateId) {
		super("Checklist Template not found in the system; CMS Template key provided [" + templateId + "]");
	}

	public String getErrorCode() {
		return TEMPLATE_NOT_FOUND_ERROR_CODE;
	}
}
