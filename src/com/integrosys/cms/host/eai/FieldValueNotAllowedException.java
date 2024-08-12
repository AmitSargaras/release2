package com.integrosys.cms.host.eai;

import java.util.Arrays;

/**
 * Exception to be raised whenever there is value of the field which is not
 * found in the values allowed.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FieldValueNotAllowedException extends FieldValueRequirementNotMetException {

	private static final long serialVersionUID = -8600612951191361281L;

	private static final String ERROR_CODE_FIELD_VALUE_NOT_ALLOWED = "VALUE_NOT_VALID";

	private String fieldName;

	private String value;

	private String[] allowValues;

	public String getFieldName() {
		return fieldName;
	}

	public String[] getAllowValues() {
		return allowValues;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setAllowValues(String[] allowValues) {
		this.allowValues = allowValues;
	}

	/**
	 * Default Constructor to provide the values allowed for the field name
	 * 
	 * @param fieldName the field name having value not found in the values
	 *        allowed supplied
	 * @param value value of the field
	 * @param allowValues values allowed for the field
	 */
	public FieldValueNotAllowedException(String fieldName, String value, String[] allowValues) {
		super("field name [" + fieldName + "] having value [" + value + "] not found in allowed values "
				+ Arrays.asList(allowValues));
		this.fieldName = fieldName;
		this.value = value;
		this.allowValues = allowValues;
	}

	public String getErrorCode() {
		return ERROR_CODE_FIELD_VALUE_NOT_ALLOWED;
	}

}
