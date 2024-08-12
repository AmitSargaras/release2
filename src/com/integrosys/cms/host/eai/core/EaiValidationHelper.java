package com.integrosys.cms.host.eai.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Substitution;
import org.apache.oro.text.regex.Util;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.host.eai.DecimalValueRequirementNotMetException;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FieldValueBelowRangeException;
import com.integrosys.cms.host.eai.FieldValueExceedRangeException;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.StandardCodeCategoryIntegrityException;
import com.integrosys.cms.host.eai.StandardCodeNotFoundException;
import com.integrosys.cms.host.eai.StandardCodeStaleStateException;
import com.integrosys.cms.host.eai.UnparsableDateException;
import com.integrosys.cms.host.eai.support.MessageDate;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * <p>
 * Validation helper to validate the message passed in, such as mandatory field,
 * string value, date value, decimal value field length and most important, the
 * standard code.
 * 
 * <p>
 * The caller only need to catch {@link EAIMessageValidationException}, the
 * error message will shown gracefully.
 * 
 * <p>
 * For subclasses of
 * {@link com.integrosys.cms.host.eai.EAIStandardCodeException}, can use the
 * {@link com.integrosys.cms.host.eai.EAIStandardCodeException#getStandardCode()}
 * to retrieve the standard code which cause the error. And at the same time can
 * do further process such as persistence.
 * <p>
 * Some validation routines provided here should be considered first
 * <ul>
 * <li>{@link #validateString(String, String, boolean, int, int)}
 * <li>{@link #validateString(String, String, boolean, int, int, String[])}
 * <li>{@link #validateDoubleDigit(String, String, boolean, int, int, boolean)}
 * <li>{@link #validateDoubleDigit(Double, String, boolean, int, int, boolean)}
 * <li>
 * {@link #validateDoubleDigit(BigDecimal, String, boolean, int, int, boolean)}
 * <li>{@link #validateStdCode(StandardCode, String, String)}
 * <li>{@link #validateStdCodeAllowNull(StandardCode, String, String)}
 * <li>
 * {@link #validateStardardCode(StandardCode, String, boolean, String, String)}
 * <li>{@link #validateNumber(Number, String, boolean, long, long)}
 * <li>{@link #rejectIfNull(Object, String)}
 * <li>{@link #validateLong(String, long, long, long)}
 * </ul>
 * 
 * @author marvin
 * @author Chong Jun Yong
 */
public class EaiValidationHelper {

	private static final EaiValidationHelper instance = new EaiValidationHelper();

	public static final String EMPTY_STRING = "";

	private static final BigInteger BIG_INTEGER_10 = new BigInteger("10");

	private EaiValidationHelper() {
	}

	public static EaiValidationHelper getInstance() {
		return instance;
	}

    private static boolean isCommonCodeValidationEnable() {
        return PropertyManager.getBoolean("glos.clims.commoncode.validate");
    }

	/**
	 * Throws <tt>MandatoryFieldMissingException</tt> if provided object is
	 * null, empty for a <tt>Collection</tt> kind, or blank for a
	 * <tt>String</tt> value.
	 * 
	 * @param object object to check against for null
	 * @param fieldName the field name for the object
	 * @throws MandatoryFieldMissingException if the object provided is null,
	 *         empty for a <tt>Collection</tt> kind or blank for a
	 *         <tt>String</tt> value
	 */
	public void rejectIfNull(Object object, String fieldName) throws MandatoryFieldMissingException {
		if (object == null) {
			throw new MandatoryFieldMissingException(fieldName);
		}

		if (object instanceof Collection) {
			if (((Collection) object).isEmpty()) {
				throw new MandatoryFieldMissingException(fieldName);
			}
		}

		if (object instanceof String) {
			if (StringUtils.isBlank((String) object)) {
				throw new MandatoryFieldMissingException(fieldName);
			}
		}
	}

	/**
	 * Validate the standard code, allow null category code and entry code, but
	 * the category code must the one supplied.
	 * 
	 * @param stdCode the standard code to be validated
	 * @param source the source of the standard code
	 * @param expectedCategoryCode expected category code for the stand code
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateStdCodeAllowNull(StandardCode stdCode, String source, String expectedCategoryCode)
			throws EAIMessageValidationException {
        if (!isCommonCodeValidationEnable()) {
            return;
        }

		if ((stdCode != null) && (stdCode.getStandardCodeNumber() != null)) {
			if (stdCode.getStandardCodeNumber().trim().equals(expectedCategoryCode)) {
				doValidateStdCodeAllowNull(stdCode, source);
			}
			else {
				throw new StandardCodeCategoryIntegrityException(expectedCategoryCode, stdCode);
			}
		}
	}

	/**
	 * Validate the standard code, allow null category code and entry code.
	 * 
	 * @param stdCode the standard code to be validated
	 * @param source the source of the standard code
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	private void doValidateStdCodeAllowNull(StandardCode stdCode, String source) throws EAIMessageValidationException {
		if ((stdCode != null) && (stdCode.getStandardCodeNumber() != null) && (stdCode.getStandardCodeValue() != null)
				&& !stdCode.getStandardCodeNumber().trim().equals(EMPTY_STRING)
				&& !stdCode.getStandardCodeValue().trim().equals(EMPTY_STRING)) {
			doValidateStdCode(stdCode, source);
		}
	}

	/**
	 * Validate the standard code to the host category, which is not
	 * maintainable by the CMS, standard code category code must met the
	 * expected category supplied
	 * 
	 * @param stdCode standard code to be validated
	 * @param source the source of the standard code
	 * @param expectedCategory expected category code of the standard code
	 * @param check mandatory check for the standard code
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateStdCodeHostCategory(StandardCode stdCode, String source, String expectedCategory, boolean check)
			throws EAIMessageValidationException {
        if (!isCommonCodeValidationEnable()) {
            return;
        }

		if ((stdCode != null) && (stdCode.getStandardCodeNumber() != null)
				&& !stdCode.getStandardCodeNumber().trim().equals(EMPTY_STRING)) {
			if (stdCode.getStandardCodeNumber().trim().equals(expectedCategory)) {
				if ((stdCode.getStandardCodeValue() != null)
						&& !stdCode.getStandardCodeValue().trim().equals(EMPTY_STRING)) {
					validateStdCodeHostCategory(stdCode, source);
				}
				else if (check) {
					StandardCodeNotFoundException scex = new StandardCodeNotFoundException(stdCode);
					scex.setCategoryType("3");
					throw scex;
				}
			}
			else {
				StandardCodeCategoryIntegrityException siex = new StandardCodeCategoryIntegrityException(
						expectedCategory, stdCode);
				siex.setCategoryType("3");
				throw siex;
			}
		}
		else if (check) {
			StandardCodeCategoryIntegrityException siex = new StandardCodeCategoryIntegrityException(expectedCategory,
					stdCode);
			siex.setCategoryType("3");
			throw siex;
		}
	}

	/**
	 * Validate the standard code of host category type.
	 * 
	 * @param stdCode standard code to be validated
	 * @param source the source of the standard code
	 * @throws EAIMessageValidationException if there is any validation error.
	 */
	private void validateStdCodeHostCategory(StandardCode stdCode, String source) throws EAIMessageValidationException {
		Validate.notNull(stdCode, "standard code to be validated must not be null");
		Validate.notNull(stdCode.getStandardCodeNumber(),
				"standard code category code to be validated must not be null");
		Validate.notNull(stdCode.getStandardCodeValue(), "standard code entry code to be validated must not be null");

		// Strips Common Code
		// Replace Special Characters with _
		try {
			stdCode.setStandardCodeValue(replaceRegExp("[^0-9a-zA-Z_ ]", "_", stdCode.getStandardCodeValue()));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "failed to replace regular express '[^0-9a-zA-Z_ ]' to '_'", e);
		}

		try {
			stdCode
					.setStandardCodeDescription(replaceRegExp(
							"[^0-9a-zA-Z_ \\~\\!\\@\\#\\$\\%\\^\\*\\(\\)\\_\\+\\=\\-\\`\\\"\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\,\\.\\/\\?]",
							" ", stdCode.getStandardCodeDescription()));
		}
		catch (Exception e) {
			DefaultLogger.error(this, "failed to replace regular express to ' '", e);
		}

		String country = getCountryFromSource(source);

		String dbDescription = CommonDataSingleton.getCodeCategoryLabelByValue(stdCode.getStandardCodeNumber(), source,
				country, stdCode.getStandardCodeValue());
		// Check if source cannot provide description, use DB to populate
		if ((stdCode.getStandardCodeDescription() == null) || "".equals(stdCode.getStandardCodeDescription())) {
			DefaultLogger.debug(this, "Local Description :" + dbDescription);

			// Not accepting Null as Description .
			stdCode.setStandardCodeDescription(StringUtils.defaultString(dbDescription));
		}

		// For category code type 3 Check current value and compare, if
		// different, update local
		DefaultLogger.debug(this, "CommonDataSingleton returns : '" + dbDescription + "' for '"
				+ stdCode.getStandardCodeNumber() + "' : '" + stdCode.getStandardCodeValue() + "' : '" + source);

		if (dbDescription == null) {
			StandardCodeNotFoundException scex = new StandardCodeNotFoundException(stdCode);
			scex.setCategoryType("3");
			throw scex;
		}
		else if (!dbDescription.equals(stdCode.getStandardCodeDescription())) {
			StandardCodeStaleStateException ssex = new StandardCodeStaleStateException(stdCode, dbDescription);
			ssex.setCategoryType("3");
			throw ssex;
		}

	}

	/**
	 * Validate standard code with the expected category code must be met
	 * 
	 * @param stdCode standard code to be validated
	 * @param source the source of the standard code
	 * @param expectedCategoryCode expected category code of the standard code
	 * @throws EAIMessageValidationException if there is any validation error.
	 */
	public void validateStdCode(StandardCode stdCode, String source, String expectedCategoryCode)
			throws EAIMessageValidationException {
        if (!isCommonCodeValidationEnable()) {
            return;
        }

		if ((stdCode != null) && (stdCode.getStandardCodeNumber() != null)) {
			if (!stdCode.getStandardCodeNumber().trim().equals(expectedCategoryCode)) {
				throw new StandardCodeCategoryIntegrityException(expectedCategoryCode, stdCode);
			}
		}

		try {
			doValidateStdCode(stdCode, source);
		}
		catch (IllegalArgumentException ex) {
			throw new MandatoryFieldMissingException("Standard Code with Category [" + expectedCategoryCode + "]");
		}
	}

	/**
	 * Validate the standard code, providing the field name, and mandatory
	 * check.
	 * 
	 * @param fieldName field name in the message
	 * @param categoryCode category code of the standard code
	 * @param entryCode entry code of the standard code
	 * @param isMandatory mandatory field check
	 * @throws EAIMessageValidationException if there is any validation error.
	 */
	public void validateStdCode(String fieldName, String categoryCode, String entryCode, boolean isMandatory)
			throws EAIMessageValidationException {

        if (!isCommonCodeValidationEnable()) {
            return;
        }

		if (!isMandatory && StringUtils.isEmpty(entryCode)) {
			return;
		}

		try {
			doValidateStdCode(new StandardCode(categoryCode, entryCode), null);
		}
		catch (IllegalArgumentException ex) {
			throw new MandatoryFieldMissingException(fieldName);
		}
	}

	/**
	 * Validate standard code, support field name, mandatory check, expected
	 * category code.
	 * @param sc standard code instance to be validated
	 * @param fieldName field name of the standard code
	 * @param mandatory whether the field is mandatory
	 * @param source source of the standard code
	 * @param expectedCategoryCode expected category code for the standard code
	 */
	public void validateStardardCode(StandardCode sc, String fieldName, boolean mandatory, String source,
			String expectedCategoryCode) {
        if (!isCommonCodeValidationEnable()) {
            return;
        }

		if (sc == null) {
			if (mandatory) {
				throw new MandatoryFieldMissingException(fieldName);
			}
		}
		else {
			if (StringUtils.isBlank(sc.getStandardCodeNumber())) {
				throw new MandatoryFieldMissingException(fieldName + " - StandardCodeNumber");
			}
			if (StringUtils.isBlank(sc.getStandardCodeValue())) {
				throw new MandatoryFieldMissingException(fieldName + " - StandardCodeValue");
			}
			if (!sc.getStandardCodeNumber().equals(expectedCategoryCode)) {
				throw new StandardCodeCategoryIntegrityException(expectedCategoryCode, sc);
			}
			doValidateStdCode(sc, source);
		}
	}

	/**
	 * Validate the standard code
	 * 
	 * @param stdCode standard code to be validated
	 * @param source the source of the standard code
	 * @throws EAIMessageValidationException if there is any validation error.
	 */
	private void doValidateStdCode(StandardCode stdCode, String source) throws EAIMessageValidationException {
		Validate.notNull(stdCode, "standard code supplied to be validated must not be null");

		if (StringUtils.isBlank(stdCode.getStandardCodeNumber())) {
			throw new MandatoryFieldMissingException("Standard Code - Code Number");
		}

		Collection codeCategory = CommonDataSingleton.getCodeCategoryValues(stdCode.getStandardCodeNumber().trim());

		if (codeCategory == null || codeCategory.isEmpty()) {
			throw new StandardCodeNotFoundException(stdCode);
		}

		for (Iterator iter = codeCategory.iterator(); iter.hasNext();) {
			String tmpValue = (String) iter.next();

			if (tmpValue.trim().equals(stdCode.getStandardCodeValue().trim())) {
				// to enter this condition means there is a stdCodeValue match
				// found for the stdCode .
				return;
			}
		}

		throw new StandardCodeNotFoundException(stdCode);
	}

	/**
	 * Validate for the string type mandatory field
	 * 
	 * @param fieldName the field name in the message
	 * @param value the value for the field
	 * @throws EAIMessageValidationException if there is any validation error
	 * @deprecated use
	 *             {@link #validateString(String, String, boolean, int, int)} or
	 *             {@link #validateString(String, String, boolean, int, int, String[])}
	 *             to provide more fine grain detail on validation
	 */
	public void validateMandatoryFieldForString(String fieldName, String value) throws EAIMessageValidationException {
		if (StringUtils.isBlank(value)) {
			throw new MandatoryFieldMissingException(fieldName);
		}
	}

	/**
	 * Validate the string value with minimum and maximum length required.
	 * 
	 * @param input the value of the field
	 * @param fieldName the field name in the message
	 * @param check indicate to allow to check for "null" or "" values.
	 * @param min the minimum length of the value
	 * @param max the maximum length of the value
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateString(String input, String fieldName, boolean check, int min, int max)
			throws EAIMessageValidationException {
		validateString(input, fieldName, check, min, max, null);
	}

	/**
	 * Validate the string value with minimum and maximum length required, and
	 * also the values allowed
	 * 
	 * @param input the value of the field
	 * @param fieldName the field name in the message
	 * @param mandatory indicate to allow to check for "null" or "" values.
	 * @param min the minimum length of the value
	 * @param max the maximum length of the value
	 * @param allowableValue the values allowed for the field
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateString(String input, String fieldName, boolean mandatory, int min, int max,
			String[] allowableValue) throws EAIMessageValidationException {
		String error = Validator.checkString(input, mandatory, min, max);

		if (Validator.ERROR_NONE.equals(error)) {
			if (StringUtils.isBlank(input) && !mandatory) {
				return;
			}
		}
		else if (Validator.ERROR_LESS_THAN.equals(error)) {
			throw new FieldValueBelowRangeException(fieldName, input, min);
		}
		else if (Validator.ERROR_GREATER_THAN.equals(error)) {
			throw new FieldValueExceedRangeException(fieldName, input, max);
		}
		else if (Validator.ERROR_MANDATORY.equals(error)) {
			throw new MandatoryFieldMissingException(fieldName);
		}
		else {
			DefaultLogger.error(this, "Error code found for field  \"" + fieldName + "\" : " + error);
		}

		if (allowableValue != null) {
			if (!ArrayUtils.contains(allowableValue, input)) {
				throw new FieldValueNotAllowedException(fieldName, input, allowableValue);
			}
		}
	}

	/**
	 * <p>
	 * Validate the decimal value.
	 * <p>
	 * This will take out decimal unscaled value, and scale, and check against
	 * the integer digits and decimal digits given respectively. If there is
	 * negative value for the unscaled value, the number of digits to be checked
	 * need to decrease by 1.
	 * <p>
	 * If the scale is less than 0, unscaled value will multiply with
	 * 10<sup>-scale</sup>, then scale become 0.
	 * 
	 * @param input the value represent a decimal value to be validated against
	 * @param fieldName the field name in the message
	 * @param check indicate whether it is mandatory field
	 * @param integerDigits the number of digits before decimal points
	 * @param decimalDigits the number of digits after decimal points
	 * @param negativeAllowed indicate whether negative values is allowed
	 * @throws MandatoryFieldMissingException if the value is mandatory but is
	 *         blank
	 * @throws DecimalValueRequirementNotMetException if the value not fulfil
	 *         any criteria given
	 */
	public void validateDoubleDigit(String input, String fieldName, boolean check, int integerDigits,
			int decimalDigits, boolean negativeAllowed) throws EAIMessageValidationException {
		if (StringUtils.isBlank(input)) {
			if (!check) {
				return;
			}
			else {
				throw new MandatoryFieldMissingException(fieldName);
			}
		}

		BigDecimal value = new BigDecimal(input);
		validateDoubleDigit(value, fieldName, check, integerDigits, decimalDigits, negativeAllowed);
	}

	/**
	 * <p>
	 * Validate the decimal value.
	 * <p>
	 * This will take out decimal unscaled value, and scale, and check against
	 * the integer digits and decimal digits given respectively. If there is
	 * negative value for the unscaled value, the number of digits to be checked
	 * need to decrease by 1.
	 * <p>
	 * If the scale is less than 0, unscaled value will multiply with
	 * 10<sup>-scale</sup>, then scale become 0.
	 * 
	 * @param input the value represent a decimal value to be validated against
	 * @param fieldName the field name in the message
	 * @param check indicate whether it is mandatory field
	 * @param integerDigits the number of digits before decimal points
	 * @param decimalDigits the number of digits after decimal points
	 * @param negativeAllowed indicate whether negative values is allowed
	 * @throws MandatoryFieldMissingException if the value is mandatory but is
	 *         blank
	 * @throws DecimalValueRequirementNotMetException if the value not fulfil
	 *         any criteria given
	 */
	public void validateDoubleDigit(Double input, String fieldName, boolean check, int integerDigits,
			int decimalDigits, boolean negativeAllowed) throws EAIMessageValidationException {
		if (input == null) {
			if (!check) {
				return;
			}
			else {
				throw new MandatoryFieldMissingException(fieldName);
			}
		}

		BigDecimal value = new BigDecimal(input.toString());
		validateDoubleDigit(value, fieldName, check, integerDigits, decimalDigits, negativeAllowed);
	}

	/**
	 * <p>
	 * Validate the decimal value.
	 * <p>
	 * This will take out decimal unscaled value, and scale, and check against
	 * the integer digits and decimal digits given respectively. If there is
	 * negative value for the unscaled value, the number of digits to be checked
	 * need to decrease by 1.
	 * <p>
	 * If the scale is less than 0, unscaled value will multiply with
	 * 10<sup>-scale</sup>, then scale become 0.
	 * 
	 * @param decimal the bigdecimal object value to be validated against
	 * @param fieldName the field name in the message
	 * @param check indicate whether it is mandatory field
	 * @param integerDigits the number of digits before decimal points
	 * @param decimalDigits the number of digits after decimal points
	 * @param negativeAllowed indicate whether negative values is allowed
	 * @throws MandatoryFieldMissingException if the value is mandatory but is
	 *         null
	 * @throws DecimalValueRequirementNotMetException if the value not fulfil
	 *         any criteria given
	 */
	public void validateDoubleDigit(BigDecimal decimal, String fieldName, boolean check, int integerDigits,
			int decimalDigits, boolean negativeAllowed) throws EAIMessageValidationException {
		if (decimal == null) {
			if (!check) {
				return;
			}
			else {
				throw new MandatoryFieldMissingException(fieldName);
			}
		}

		BigInteger unscaledValue = decimal.unscaledValue();
		int scale = decimal.scale();
		boolean isNegativeValue = decimal.signum() < 0;

		if (scale < 0) {
			unscaledValue = unscaledValue.multiply(BIG_INTEGER_10.pow(scale * -1));
			scale = 0;
		}

		if (!negativeAllowed && isNegativeValue) {
			throw new DecimalValueRequirementNotMetException(fieldName, decimal.toString(), integerDigits
					+ decimalDigits, decimalDigits, negativeAllowed);
		}

		if (scale > decimalDigits) {
			if (scale == 1 && decimalDigits == 0) {
				if (!unscaledValue.toString().endsWith("0")) {
					throw new DecimalValueRequirementNotMetException(fieldName, decimal.toString(), integerDigits
							+ decimalDigits, decimalDigits, negativeAllowed);
				}
			}
			else {
				throw new DecimalValueRequirementNotMetException(fieldName, decimal.toString(), integerDigits
						+ decimalDigits, decimalDigits, negativeAllowed);
			}
		}

		int integerLength = unscaledValue.toString().length() - scale - (isNegativeValue ? 1 : 0);
		if (integerLength > integerDigits) {
			throw new DecimalValueRequirementNotMetException(fieldName, decimal.toString(), integerDigits
					+ decimalDigits, decimalDigits, negativeAllowed);
		}
	}

	/**
	 * Validate the date of the field
	 * 
	 * @param date the value of the date
	 * @param fieldName the field name in the message
	 * @param check to check for the mandatory field
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateDate(String date, String fieldName, boolean check) throws EAIMessageValidationException {
		// checking for mandatory field. if it is then exception is throw if
		// empty or null
		validateString(date, fieldName, check, 8, 14);

		Date obj = MessageDate.getInstance().getDate(date);

		// the date string is not empty , but cannot be converted into a date
		// object
		if ((obj == null) && (date != null) && !date.trim().equals(EMPTY_STRING)) {
			throw new UnparsableDateException(fieldName, date);
		}

		// base case just in case
		if (check && (obj == null)) {
			// impossible to get here
			throw new MandatoryFieldMissingException(fieldName);
		}
	}

	/**
	 * Validate the date object for the field name, mainly mandatory checking
	 * only
	 * 
	 * @param date the date object
	 * @param fieldName the field name in the message
	 * @param check to indicate whether check for mandatory field
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateDate(Date date, String fieldName, boolean check) throws EAIMessageValidationException {
		if (check && (date == null)) {
			throw new MandatoryFieldMissingException(fieldName);
		}
	}

	/**
	 * To validate a long value for mandatory
	 * @param fieldName the field name in the message
	 * @param value the long value
	 * @throws EAIMessageValidationException if there is any validation error
	 * @deprecated use
	 *             {@link #validateNumber(Number, String, boolean, long, long)}
	 *             for more fine grain detail on validation
	 */
	public void validateMandatoryFieldForLong(String fieldName, long value) throws EAIMessageValidationException {
		validateMandatoryFieldForString(fieldName, String.valueOf(value));
	}

	/**
	 * Validate a long value which include the minimum and maximum validation
	 * @param fieldName the field name in the message
	 * @param value the long value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateLong(String fieldName, long value, long min, long max) throws EAIMessageValidationException {
		if (value < min) {
			throw new FieldValueBelowRangeException(fieldName, new Long(value), (int) min);
		}

		if (value > max) {
			throw new FieldValueExceedRangeException(fieldName, new Long(value), (int) max);
		}
	}

	/**
	 * Validate a whole number value which include the mandatory, minimum and
	 * maximum validation
	 * @param input the whole number value to be validated
	 * @param fieldName the field name in the message
	 * @param mandatory whether the field is mandatory
	 * @param min the minimum value
	 * @param max the maximum value
	 * @throws EAIMessageValidationException if there is any validation error
	 */
	public void validateNumber(Number input, String fieldName, boolean mandatory, long min, long max)
			throws EAIMessageValidationException {
		if (input == null) {
			if (mandatory) {
				throw new MandatoryFieldMissingException(fieldName);
			}
			else {
				return;
			}
		}

		if (input.longValue() < min) {
			throw new FieldValueBelowRangeException(fieldName, input, (int) min);
		}

		if (input.longValue() > max) {
			throw new FieldValueExceedRangeException(fieldName, input, (int) max);
		}
	}

	/**
	 * Validate a long value for mandatory
	 * 
	 * @param fieldName the field name in the message
	 * @param value the double value
	 * @throws EAIMessageValidationException if there is any validation error
	 * @deprecated use one of the <code>validateDoubleDigit</code> routine to
	 *             provide more fine grain detail on validation
	 */
	public void validateMandatoryFieldForDouble(String fieldName, double value) throws EAIMessageValidationException {
		validateMandatoryFieldForString(fieldName, String.valueOf(value));
	}

	/**
	 * Retrieve the country for the source
	 * 
	 * @param source the source of the standard code
	 * @return the country tied to the source
	 * @throws EAIMessageValidationException if there any validation error
	 */
	private String getCountryFromSource(String source) throws EAIMessageValidationException {
		if (source == null) {
			throw new MandatoryFieldMissingException("Source");
		}

		String result = CommonDataSingleton.getCodeCategoryCountryByValue("37", source);

		if (StringUtils.isEmpty(result)) {
			StandardCode sc = new StandardCode();
			sc.setStandardCodeNumber("37");
			sc.setStandardCodeValue(source);

			throw new StandardCodeNotFoundException(sc);
		}

		return result;
	}

	/**
	 * Method to do string replacement by using Regular Expression
	 * 
	 * @param regularExpression the regular expression
	 * @param replace the value to replace the occurrence found for the regular
	 *        expression
	 * @param input the input value
	 * @return the value get replaced
	 * @throws MalformedPatternException if the regular expression is not able
	 *         to be compiled
	 */
	public static final String replaceRegExp(String regularExpression, String replace, String input)
			throws MalformedPatternException {

		PatternMatcher matcher = new Perl5Matcher();
		Pattern pattern = null;
		PatternCompiler compiler = new Perl5Compiler();

		String result;

		Substitution sub;

		sub = new Perl5Substitution(replace);

		pattern = compiler.compile(regularExpression);

		// Perform substitution and print result.
		result = Util.substitute(matcher, pattern, sub, input, Util.SUBSTITUTE_ALL);

		return result;
	}
}
