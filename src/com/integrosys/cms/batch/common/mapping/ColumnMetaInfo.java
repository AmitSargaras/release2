package com.integrosys.cms.batch.common.mapping;

import java.util.Date;

/**
 * <p>
 * Meta Info for a Column in a line of a feed to specify what's the type of the
 * value.
 * <p>
 * If it's decimal value, please specify what's the decimal points for the
 * value, via Constructor {@link #ColumnMetaInfo(int, Class, int)}. Date value
 * need to provide date format, via Constructor
 * {@link #ColumnMetaInfo(int, Class, String)}
 * @author Chong Jun Yong
 * @see ColumnMetaInfoArrayPropertyEditor
 */
public final class ColumnMetaInfo {

	/** Prefix for decimal value when doing Java bean property editor */
	public static final String DECIMAL_PREFIX = "DECIMAL_";

	/** Prefix for date format value when doing Java bean property editor */
	public static final String DATE_FORMAT_PREFIX = "DATEFORMAT_";

	/**
	 * Prefix for boolean true value when doing Java bean property editor, eg.
	 * can be Y, true, TRUE, 1, Yes, YES
	 */
	public static final String BOOLEAN_TRUE_PREFIX = "BOOLEANTRUE_";
	
	/**
	 * Prefix for number pattern when doing Java bean property editor, e.g.
	 * value = 12.8, format 00000.000, output = 00012.800 
	 */
	public static final String NUMBER_PATTERN_PREFIX = "NUMBERPATTERN_";

	private int columnNumber;

	private int decimalPoints = 0;

	private Class classType;

	private String dateFormat;

	private String booleanTrueValue;
	
	private String numberPattern;

	public ColumnMetaInfo(int columnNumber, Class classType) {
		this.columnNumber = columnNumber;
		this.classType = classType;
	}

	/**
	 * Sole constructor for the column which is boolean type
	 * @param columnNumber column number in the line of a feed
	 * @param booleanTrueValue the value that represent boolean <tt>true</tt>
	 */
	public ColumnMetaInfo(int columnNumber, String booleanTrueValue) {
		this.columnNumber = columnNumber;
		this.classType = Boolean.class;
		this.booleanTrueValue = booleanTrueValue;
	}

	/**
	 * Constructor for Decimal value, such as <tt>Double</tt>,
	 * <tt>BigDecimal</tt>
	 * @param columnNumber column number in the line of a feed
	 * @param classType class type for the column
	 * @param decimalPoints decimal point for the value
	 */
	public ColumnMetaInfo(int columnNumber, Class classType, int decimalPoints) {
		this.columnNumber = columnNumber;
		this.classType = classType;
		this.decimalPoints = decimalPoints;
	}

	/**
	 * The sole constructor for <b>Date</b> value
	 * @param columnNumber column number in the line of a feed
	 * @param classType class type for the column
	 * @param dateFormat date format to parse the column value to date object
	 * @throws IllegalArgumentException if the classType is not a correct Date
	 *         type
	 */
	public ColumnMetaInfo(int columnNumber, Class classType, String dateFormat) {
		if (!Date.class.isAssignableFrom(classType)) {
			throw new IllegalArgumentException("Date type class [" + classType + "], is not a correct Date type");
		}
		this.columnNumber = columnNumber;
		this.classType = classType;
		this.dateFormat = dateFormat;
	}
	
	/**
	 * Constructor for Decimal value, such as <tt>Double</tt>,
	 * <tt>BigDecimal</tt>
	 * @param columnNumber column number in the line of a feed
	 * @param classType class type for the column
	 * @param decimalPoints decimal point for the value
	 */
	public ColumnMetaInfo(int columnNumber, Class classType, int decimalPoints, String numberPattern) {
		this.columnNumber = columnNumber;
		this.classType = classType;
		this.decimalPoints = decimalPoints;
		this.numberPattern = numberPattern;
	}	

	public int getColumnNumber() {
		return columnNumber;
	}

	public int getDecimalPoints() {
		return decimalPoints;
	}

	public Class getClassType() {
		return classType;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public String getBooleanTrueValue() {
		return booleanTrueValue;
	}

	public String getNumberPattern() {
		return numberPattern;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("MetaInfo: ");
		buf.append("Column Number: [").append(columnNumber).append("], ");
		buf.append("Class Type: [").append(classType).append("], ");
		buf.append("Decimal Points: [").append(decimalPoints).append("], ");
		buf.append("Date Format: [").append(dateFormat).append("], ");
		buf.append("Boolean True Value: [").append(booleanTrueValue).append("]");

		return buf.toString();
	}
}
