/*
 * Created by IntelliJ IDEA.
 * User: Sulin
 * Date: Oct 20, 2003
 * Time: 2:51:15 PM
 */
package com.integrosys.cms.host.eai.document;

/**
 * Exception to be raised when the required CheckList Item doesn't exist
 * 
 * @author Iwan Satria
 * @since 1.0
 */
public class NoSuchCheckListItemException extends InvalidCheckListItemException {

	private static final long serialVersionUID = 8757374501941460852L;

	private static final String NO_DOC_ITEM_FOUND_ERROR_CODE = "NO_DOCITEM_FOUND";

	/**
	 * Default Constructor to provide error when the CMS documen item key
	 * provided is not found.
	 * 
	 * @param cmsDocItemId CMS document item internal key.
	 */
	public NoSuchCheckListItemException(long cmsDocItemId) {
		super("Checklist Item not found in the system; CMS Document Item key [" + cmsDocItemId + "]");
	}

	public String getErrorCode() {
		return NO_DOC_ITEM_FOUND_ERROR_CODE;
	}

}
