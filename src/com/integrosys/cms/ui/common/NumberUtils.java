package com.integrosys.cms.ui.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Handy utitlity to convert number (in string representation) into appropriate
 * <code>Number</code> object, or vice versa. Using JDK
 * <code>java.text.NumberFormat</code> to do the parsing and format.
 * <p>
 * In some case, if the String value cannot be parsed to appropriate
 * <code>Number</code> object, <code>null</code> will be the result. So caller
 * need to cater for it.
 * @author Chong Jun Yong
 * 
 */
public abstract class NumberUtils {

	/** Empty string representation, ie, "" */
	public static final String EMPTY_VALUE = "";

	/**
	 * Parse a string value into appropriate <code>Number</code> object. If the
	 * value cannot be parsed successfully, <code>null</code> will be returned.
	 * Caller must be careful.
	 * @param value the string value of the number
	 * @param locale locale object of the client
	 * @return <code>Number</code> object represent the value, <code>null</code>
	 *         will be returned if it's not a valid number.
	 */
	public static Number parseNumber(String value, Locale locale) {
		NumberFormat formatter = null;
		if (locale == null) {
			formatter = NumberFormat.getInstance();
		}
		else {
			formatter = NumberFormat.getInstance(locale);
		}

		try {
			formatter.parse(value);
		}
		catch (ParseException ex) {
			// ignored.
		}
		return null;
	}

	/**
	 * <p>
	 * Format BigDecimal value into appropriate format, ie, without scientific
	 * symbol 'E'
	 * <p>
	 * <b>note</b>This wont format value which is less than 0, and exceed 3
	 * scales. ie. 0.0001 will get 0 in this case. Please consider to use format
	 * method which to provide pattern.
	 * @param value decimal value object
	 * @return string representation of the decimal value object passed in, ""
	 *         empty string will be returned if the decimal value object is
	 *         null.
	 * @see DecimalFormat
	 * @see #formatBigDecimalValue(BigDecimal, String)
	 */
	public static String formatBigDecimalValue(BigDecimal value) {
		if (value == null) {
			return EMPTY_VALUE;
		}

		NumberFormat formatter = NumberFormat.getInstance();
		if (value.scale() >= 4) {
			formatter = new DecimalFormat("#." + StringUtils.repeat("#", value.scale()));
		}
		return formatter.format(value.doubleValue());
	}

	/**
	 * <p>
	 * Format BigDecimal value into format of the pattern supplied
	 * <p>
	 * @param value decimal value object
	 * @param pattern the pattern to format the decimal value
	 * @return string representation of the decimal value object passed in, ""
	 *         empty string will be returned if the decimal value object is
	 *         null.
	 * @see DecimalFormat
	 */
	public static String formatBigDecimalValue(BigDecimal value, String pattern) {
		if (value == null) {
			return EMPTY_VALUE;
		}

		DecimalFormat formatter = new DecimalFormat(pattern);
		return formatter.format(value.doubleValue());
	}

	/**
	 * <p>
	 * Format BigDecimal value into appropriate format according to the locale
	 * supplied.
	 * @param value decimal value object
	 * @param locale the localization of the client/server
	 * @return string representation of the decimal value object passed in, ""
	 *         empty string will be returned if the decimal value object is
	 *         null.
	 * @see DecimalFormat
	 */
	public static String formatBigDecimalValue(BigDecimal value, Locale locale) {
		if (value == null) {
			return EMPTY_VALUE;
		}

		NumberFormat formatter = NumberFormat.getInstance(locale);
		return formatter.format(value.doubleValue());
	}
}
