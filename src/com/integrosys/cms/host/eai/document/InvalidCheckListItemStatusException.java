package com.integrosys.cms.host.eai.document;

/**
 * Exception to be raised whenever there is error for document having invalid
 * status.
 * 
 * @author Chong Jun Yong
 * 
 */
public class InvalidCheckListItemStatusException extends InvalidCheckListItemException {

	private static final long serialVersionUID = 4941198196938355439L;

	private static final String ERROR_CODE = "INV_DOC_STAT";

	/**
	 * Default constructor to provide which document (docNo) having invalid
	 * status.
	 * 
	 * @param docNo the docNo of the document having invalid status
	 * @param status the status provided
	 */
	public InvalidCheckListItemStatusException(long docNo, String status) {
		super("Invalid CheckList item status for DocNo [" + docNo + "], status provided is [" + status + "]");
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}

}
