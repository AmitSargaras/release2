package com.integrosys.cms.host.eai.support;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Property editor to parse a text value into instance of
 * <tt>ValidationMandatoryFieldKey</tt>.
 * <p>
 * 3 main value for the key is source id, full class name, and property name.
 * When converting the value, these 3 must be prefixed with <b>SOURCE_</b> ;
 * <b>CLASS_</b> ; <b>PROPERTY_</b> respectively.
 * <p>
 * One can use {@link #ValidationMandatoryFieldKeyPropertyEditor(boolean)} to
 * specify whether the value in <b>CLASS_</b> need to be validated. If set to
 * true, and for the class specify in <b>CLASS_</b> cannot be found, the
 * conversion will fail. This value by default is false.
 * <p>
 * <b>Note: </b> Default separator for the 3 values above is space, comma and
 * newline feed.
 * @author Chong Jun Yong
 * 
 */
public class ValidationMandatoryFieldKeyPropertyEditor extends PropertyEditorSupport {

	public final static String PREFIX_SOURCE_ID = "SOURCE_";

	public final static String PREFIX_CLASS_NAME = "CLASS_";

	public final static String PREFIX_PROPERTY = "PROPERTY_";

	private boolean validateClass = false;

	private boolean validateProperty = false;

	/**
	 * Default construction whereby not to check the class name against class
	 * loader and also the property.
	 */
	public ValidationMandatoryFieldKeyPropertyEditor() {
		this(false, false);
	}

	/**
	 * Construction which require to check the class name against the class
	 * loader, and also the property against the class.
	 * @param validateClass whether the class name to be validated against the
	 *        class loader.
	 * @param validateProperty whether the property belong to the class
	 *        provided.
	 */
	public ValidationMandatoryFieldKeyPropertyEditor(boolean validateClass, boolean validateProperty) {
		this.validateClass = validateClass;
		this.validateProperty = validateProperty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			throw new IllegalArgumentException("There is no value to be converted.");
		}

		if (text.indexOf(PREFIX_CLASS_NAME) < 0) {
			throw new IllegalArgumentException("There is no prefix of 'CLASS_' for [" + text + "]");
		}
		if (text.indexOf(PREFIX_PROPERTY) < 0) {
			throw new IllegalArgumentException("There is no prefix of 'PROPERTY_' for [" + text + "]");
		}
		if (text.indexOf(PREFIX_SOURCE_ID) < 0) {
			throw new IllegalArgumentException("There is no prefix of 'SOURCE_' for [" + text + "]");
		}

		String sourceId = null;
		String fullClassName = null;
		String propertyName = null;

		String[] values = StringUtils.tokenizeToStringArray(text, ",\r\n\f ", true, true);
		if (values.length != 3) {
			throw new IllegalArgumentException("There is no 3 tokens for value [" + text + "], but [" + values.length
					+ "]");
		}

		for (int i = 0; i < values.length; i++) {
			if (values[i].startsWith(PREFIX_SOURCE_ID)) {
				sourceId = values[i].substring(PREFIX_SOURCE_ID.length());
			}
			else if (values[i].startsWith(PREFIX_PROPERTY)) {
				propertyName = values[i].substring(PREFIX_PROPERTY.length());
			}
			else if (values[i].startsWith(PREFIX_CLASS_NAME)) {
				fullClassName = values[i].substring(PREFIX_CLASS_NAME.length());
			}
		}

		if (sourceId == null || sourceId.trim().length() == 0 || fullClassName == null
				|| fullClassName.trim().length() == 0 || propertyName == null || propertyName.trim().length() == 0) {
			throw new IllegalArgumentException("How could be checking for so long, still no value ? provided [" + text
					+ "]");
		}

		Class clazz = null;
		if (validateClass) {
			try {
				clazz = Class.forName(fullClassName);
			}
			catch (ClassNotFoundException ex) {
				throw new IllegalArgumentException("There is no class found for [" + fullClassName + "]");
			}
		}
		if (validateProperty) {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, propertyName);
			if (pd == null) {
				throw new IllegalArgumentException("There is no such property [" + propertyName
						+ "] can be found in the class [" + clazz + "]");
			}
		}

		setValue(new ValidationMandatoryFieldKey(sourceId, fullClassName, propertyName));
	}

	public String getAsText() {
		ValidationMandatoryFieldKey key = (ValidationMandatoryFieldKey) getValue();
		StringBuffer buf = new StringBuffer();
		buf.append(PREFIX_SOURCE_ID).append(key.getSourceId()).append(",");
		buf.append(PREFIX_CLASS_NAME).append(key.getFullClassName()).append(",");
		buf.append(PREFIX_PROPERTY).append(key.getPropertyName());
		return buf.toString();
	}
}
