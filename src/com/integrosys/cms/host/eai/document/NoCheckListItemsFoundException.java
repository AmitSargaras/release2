package com.integrosys.cms.host.eai.document;

import com.integrosys.cms.host.eai.EAIHeader;

/**
 * Exception to be raised when there is no checklist item found in the message
 * provided or no checklist item found provided the internal checklist key
 * 
 * @author Chong Jun Yong
 * 
 */
public class NoCheckListItemsFoundException extends InvalidCheckListItemException {

	private static final long serialVersionUID = 8438863059844830139L;

	private static final String NO_CHECKLIST_ITEM_ERROR_CODE = "NO_DOC_ITEMS";

	/**
	 * Default constructor to provide the message header as the base info to
	 * show that checklist items are not provided for the message
	 * 
	 * @param header the message header.
	 */
	public NoCheckListItemsFoundException(EAIHeader header) {
		super("No Checklist Item(s) found in the message provided, header info [" + header + "]");
	}

	/**
	 * Default constructor to provide the checklist id as the base info to
	 * indicate that no checklist items found in the persistent storage
	 * 
	 * @param cmsCheckListId internal checklist id
	 */
	public NoCheckListItemsFoundException(long cmsCheckListId) {
		super("No Checklist Item(s) found for the checklist with checklist id [" + cmsCheckListId + "] provided");
	}

	public String getErrorCode() {
		return NO_CHECKLIST_ITEM_ERROR_CODE;
	}
}
