package com.integrosys.cms.host.eai.support;

import java.util.Set;

/**
 * <p>
 * Factory to store a set of instance of <tt>ValidationMandatoryFieldKey</tt>.
 * <p>
 * Provide several contracts to retrieve whether for a given class, the property
 * is mandatory field.
 * @author Chong Jun Yong
 * 
 */
public final class ValidationMandatoryFieldFactory {

	/**
	 * A set of instance of <tt>ValidationMandatoryFieldKey</tt>
	 */
	private final Set validationMandatoryFields;

	/**
	 * <p>
	 * Instances of <tt>ValidationMandatoryFieldKey</tt> to state that whether
	 * for a source, a given full class name and a property (of the class) is a
	 * mandatory field.
	 * <p>
	 * By providing <tt>ValidationMandatoryFieldKey</tt>, if there is item
	 * found, meaning it's a mandatory field.
	 * @param validationMandatoryFields instances of
	 *        <tt>ValidationMandatoryFieldKey</tt>
	 */
	public ValidationMandatoryFieldFactory(Set validationMandatoryFields) {
		this.validationMandatoryFields = validationMandatoryFields;
	}

	/**
	 * Given the source id, full class name, and the property name of the class,
	 * get to know whether the the property is mandatory field.
	 * @param sourceId source id of the information come from
	 * @param fullClassName full name of the class to be validated
	 * @param propertyName the property of the class to be checked against
	 * @return whether the field is mandatory for the system
	 */
	public boolean isMandatoryField(String sourceId, String fullClassName, String propertyName) {
		return isMandatoryField(new ValidationMandatoryFieldKey(sourceId, fullClassName, propertyName));
	}

	/**
	 * Given the source id, class instance, and the property name of the class,
	 * get to know whether the the property is mandatory field.
	 * @param sourceId source id of the information come from
	 * @param clazz the class of the object to be validated
	 * @param propertyName the property of the class to be checked against
	 * @return whether the field is mandatory for the system
	 */
	public boolean isMandatoryField(String sourceId, Class clazz, String propertyName) {
		return isMandatoryField(sourceId, clazz.getName(), propertyName);
	}

	/**
	 * Given the instance of <tt>ValidationMandatoryFieldKey</tt> to know
	 * whether the property is mandatory field
	 * @param key instance of <tt>ValidationMandatoryFieldKey</tt>, consist of
	 *        major information on source id, class name, property name
	 * @return whether the field is mandatory for the system
	 */
	public boolean isMandatoryField(ValidationMandatoryFieldKey key) {
		return this.validationMandatoryFields.contains(key);
	}

	/**
	 * To show all the possible <tt>ValidationMandatoryFieldKey</tt> instance.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("ValidationMandatoryFieldFactory [");
		buf.append(validationMandatoryFields);
		buf.append("]");
		return buf.toString();
	}
}
