package com.integrosys.cms.host.eai;

/**
 * Exception to be raised whenever for certain fields, not all can be provided
 * at the same, only one of it can be.
 * 
 * @author Chong Jun Yong
 * 
 */
public class EitherFieldRequiredException extends MessageFieldException {

	private static final long serialVersionUID = -1647786667757788572L;

	private static final String EITHER_ONE_ERROR_CODE = "EITHER_ONE";

	/**
	 * Constructor to state that, given 2 fields cannot be provided at the same
	 * time
	 * @param fieldName a field name
	 * @param anotherFieldName another field name
	 */
	public EitherFieldRequiredException(String fieldName, String anotherFieldName) {
		super("Either [" + fieldName + "] or [" + anotherFieldName + "] to be provided, but not both.");
	}

	/**
	 * Constructor to state that, given 3 fields cannot be provided at the same
	 * time
	 * @param fieldName a field name
	 * @param anotherFieldName another field name
	 * @param yetAnotherFieldName third field name
	 */
	public EitherFieldRequiredException(String fieldName, String anotherFieldName, String yetAnotherFieldName) {
		this(new String[] { fieldName, anotherFieldName, yetAnotherFieldName });
	}

	/**
	 * Constructor to state that given an array of fields name, cannot all be
	 * provided, must one of it.
	 * @param fieldNames an array of field names
	 */
	public EitherFieldRequiredException(String[] fieldNames) {
		super("Either " + genenerateEitherFields(fieldNames) + " to be provided, but not all.");
	}

	private static String genenerateEitherFields(String[] fieldNames) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < fieldNames.length; i++) {
			buf.append("[");
			buf.append(fieldNames[i]);
			buf.append("]");
			if (i < (fieldNames.length - 1)) {
				buf.append(" or ");
			}
		}
		return buf.toString();
	}

	public String getErrorCode() {
		return EITHER_ONE_ERROR_CODE;
	}

}
