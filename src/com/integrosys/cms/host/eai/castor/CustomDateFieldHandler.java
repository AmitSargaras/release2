package com.integrosys.cms.host.eai.castor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.exolab.castor.mapping.GeneralizedFieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * <p>
 * Castor's <tt>FieldHandler</tt> to parse the date string in XML to a
 * <tt>Date</tt> object.
 * <p>
 * Default date format pattern is <i>yyyyMMdd</i>. If other format need to be
 * used, configure 'dateFormat' property for this field handler.
 * @author Chong Jun Yong
 * 
 */
public class CustomDateFieldHandler extends GeneralizedFieldHandler {

	private String pattern = "yyyyMMdd";

	private DateFormat formatter = new SimpleDateFormat(pattern);

	public void setConfiguration(Properties config) throws ValidityException {
		String pattern = config.getProperty("dateFormat");
		if (pattern == null) {
			throw new ValidityException("Required parameter 'dateFormat' is missing.");
		}

		try {
			formatter = new SimpleDateFormat(pattern);
			this.pattern = pattern;
		}
		catch (IllegalArgumentException e) {
			throw new ValidityException("Pattern [" + pattern + "] is not a valid date format.");
		}
	}

	public Object convertUponGet(Object value) {
		if (value == null) {
			return null;
		}

		Date date = (Date) value;
		return formatter.format(date);
	}

	public Object convertUponSet(Object value) {
		Date date = null;
		try {
			date = formatter.parse((String) value);
		}
		catch (ParseException px) {
			throw new IllegalArgumentException("failed to parse the date value [" + value + "] using pattern ["
					+ pattern + "]");
		}

		return date;
	}

	public Class getFieldType() {
		return Date.class;
	}

}
