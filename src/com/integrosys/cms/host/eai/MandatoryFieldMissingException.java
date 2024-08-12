package com.integrosys.cms.host.eai;

/**
 * Exception to be raised where there is missing mandatory field in the message.
 * 
 * @author Chong Jun Yong
 * 
 */
public class MandatoryFieldMissingException extends MessageFieldException {

	private static final long serialVersionUID = 858811509270974004L;

	private static final String errorCode = "MANDATORY";

	private String fieldName;

	/**
	 * Default constructor to supplied the field name which is mandatory.
	 * 
	 * @param fieldName the field name which is mandatory field and missing from
	 *        the message
	 */
	public MandatoryFieldMissingException(String fieldName) {
		super("Mandatory field [" + fieldName + "] is missing, please provide.");
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
