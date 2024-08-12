package com.integrosys.cms.host.eai;

/**
 * Exception to be raised for the decimal value doesnt meet the requirement
 * 
 * @author Chong Jun Yong
 * 
 */
public class DecimalValueRequirementNotMetException extends FieldValueRequirementNotMetException {

	private static final long serialVersionUID = 3789867844108180748L;

	private static final String DECIMAL_VALUE_ERROR_CODE = "DECIMAL_VALUE_FORMAT";

	private String fieldName;

	private String value;

	private int precision;

	private int scale;

	private boolean isNegativeAllowed;

	public String getFieldName() {
		return fieldName;
	}

	public String getValue() {
		return value;
	}

	public int getPrecision() {
		return precision;
	}

	public int getScale() {
		return scale;
	}

	public boolean isNegativeAllowed() {
		return isNegativeAllowed;
	}

	/**
	 * Default Constructor to provide field name, decimal value with precision
	 * and scale.
	 * 
	 * @param fieldName the field name
	 * @param value the decimal value for the field
	 * @param precision the precision of the number (number of digits plus
	 *        number of decimal)
	 * @param scale the scale of the number (number of decimal)
	 * @param isNegativeAllowed whether negative value is allowed
	 */
	public DecimalValueRequirementNotMetException(String fieldName, String value, int precision, int scale,
			boolean isNegativeAllowed) {
		super("Field name [" + fieldName + "] having value [" + value + "] doesn't meet requirement of 9(" + precision
				+ "." + scale + "), negative allowed [" + (isNegativeAllowed ? "Yes" : "No") + "]");
		this.fieldName = fieldName;
		this.value = value;
		this.precision = precision;
		this.scale = scale;
		this.isNegativeAllowed = isNegativeAllowed;
	}

	public String getErrorCode() {
		return DECIMAL_VALUE_ERROR_CODE;
	}

}
