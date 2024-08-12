package com.integrosys.cms.host.eai;

/**
 * Exception to be raised when there is length of value exceed the range
 * expected.
 * 
 * @author Chong Jun Yong
 * 
 */
public class FieldValueExceedRangeException extends FieldValueRequirementNotMetException {

	private static final long serialVersionUID = -4442952208605356923L;

	private static final String FIELD_VALUE_EXCEED_ERROR_CODE = "VALUE_EXCEED_RANGE";

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
	 * maximum number
	 * 
	 * @param fieldName the field name in the message
	 * @param value the value of that field
	 * @param expectedRange maximum number of characters or digits allowed for
	 *        the value
	 */
	public FieldValueExceedRangeException(String fieldName, String value, int expectedRange) {
		super("Message field [" + fieldName + "], value [" + value + "] length is [" + value.length()
				+ "], exceed maximum length [" + expectedRange + "]");
		setFieldName(fieldName);
		setValue(value);
		setExpectedRange(expectedRange);
	}

	/**
	 * Default Constructor to supplied field name, a number value and the
	 * expected maximum number
	 * 
	 * @param fieldName the field name in the message
	 * @param value the value of that field
	 * @param expectedRange maximum value allowed for the number
	 */
	public FieldValueExceedRangeException(String fieldName, Number value, int expectedRange) {
		super("Message field [" + fieldName + "], value [" + value + "], exceed maximum value [" + expectedRange + "]");
		setFieldName(fieldName);
		setValue(String.valueOf(value));
		setExpectedRange(expectedRange);
	}

	public String getErrorCode() {
		return FIELD_VALUE_EXCEED_ERROR_CODE;
	}
}
