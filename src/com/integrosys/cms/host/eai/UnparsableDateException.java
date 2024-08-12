package com.integrosys.cms.host.eai;

/**
 * Exception to be raised when there is date in the message which is not
 * parsable.
 * 
 * @author Chong Jun Yong
 * 
 */
public class UnparsableDateException extends FieldValueRequirementNotMetException {

	private static final long serialVersionUID = 8299245709308708407L;

	private static final String DATE_FORMAT_ERROR_CODE = "DATE_FORMAT";

	private String fieldName;

	private String displayDate;

	public String getFieldName() {
		return fieldName;
	}

	public String getDisplayDate() {
		return displayDate;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	/**
	 * Default constructor to provide the field name and the display date which
	 * cannot be parsed
	 * 
	 * @param fieldName the field name of the date
	 * @param displayDate the value of the date
	 */
	public UnparsableDateException(String fieldName, String displayDate) {
		super("The date field [" + fieldName + "] contain format cannot be parsed, value is [" + displayDate + "]");
		setFieldName(fieldName);
		setDisplayDate(displayDate);
	}

	public String getMessage() {
		StringBuffer buf = new StringBuffer(super.getMessage());
		buf.append("; example correct date format 20080501 (1st May 2008), or 20080501213035 (1st May 2008, 21:30:35)");

		return buf.toString();
	}

	public String getErrorCode() {
		return DATE_FORMAT_ERROR_CODE;
	}

}
