package com.integrosys.cms.host.eai;

/**
 * Exception to be raised when the number not having a correct format
 * 
 * @author Chong Jun Yong
 * 
 */
public class FieldValueNumberFormatException extends FieldValueRequirementNotMetException {

	private static final long serialVersionUID = 7407039478142329316L;

	private static final String FIELD_VALUE_NUMBER_FORMAT_ERROR_CODE = "NUMBER_FORMAT";

	/**
	 * Constructor to take in input value and the field name
	 * 
	 * @param input the input value for the field
	 * @param fieldName the field name in the message
	 */
	public FieldValueNumberFormatException(String input, String fieldName) {
		super("The value of [" + fieldName + "] which [" + input + "] is not a number");
	}

	public String getErrorCode() {
		return FIELD_VALUE_NUMBER_FORMAT_ERROR_CODE;
	}

}