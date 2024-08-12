package com.integrosys.cms.host.eai;

/**
 * Exception to be raised when the length value of the field does not meet the
 * minimum requirement.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FieldValueBelowRangeException extends FieldValueRequirementNotMetException {
	private static final long serialVersionUID = -4442952208605356923L;

	private static final String FIELD_VALUE_BELOW_ERROR_CODE = "VALUE_BELOW_RANGE";

	private String fieldName;

	private String value;

	private int expectedRange;

	public String getFieldName() {
		return fieldName;
	}

	public String getValue() {
		return value;
	}

	public int getExpectedRange() {
		return expectedRange;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setExpectedRange(int expectedRange) {
		this.expectedRange = expectedRange;
	}

	/**
	 * Default Constructor to supplied field name, value and the expected
	 * minimum number
	 * 
	 * @param fieldName the field name in the message
	 * @param value the value of that field
	 * @param expectedRange minimum number of characters or digits allowed for
	 *        the value
	 */
	public FieldValueBelowRangeException(String fieldName, String value, int expectedRange) {
		super("Message field [" + fieldName + "], value [" + value + "] length is [" + value.length()
				+ "], is below the minimum length of [" + expectedRange + "]");
		setFieldName(fieldName);
		setValue(value);
		setExpectedRange(expectedRange);
	}

	/**
	 * Default Constructor to supplied field name, number value and the expected
	 * minimum number
	 * 
	 * @param fieldName the field name in the message
	 * @param value the number value of that field
	 * @param expectedRange minimum number of characters or digits allowed for
	 *        the value
	 */
	public FieldValueBelowRangeException(String fieldName, Number value, int expectedRange) {
		super("Message field [" + fieldName + "], value [" + value + "], is below the minimum value of ["
				+ expectedRange + "]");
		setFieldName(fieldName);
		setValue(String.valueOf(value));
		setExpectedRange(expectedRange);
	}

	public String getErrorCode() {
		return FIELD_VALUE_BELOW_ERROR_CODE;
	}
}
