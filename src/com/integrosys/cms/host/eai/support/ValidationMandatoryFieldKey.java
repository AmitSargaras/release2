package com.integrosys.cms.host.eai.support;

/**
 * A composite key to be used to know whether for the given source id, and full
 * class name, the property is mandatorys.
 * 
 * @author Chong Jun Yong
 * 
 */
final class ValidationMandatoryFieldKey {
	private final String sourceId;

	private final String fullClassName;

	private final String propertyName;

	/**
	 * Constructor to provide source id, full class name, and property name
	 * @param sourceId source id of the information
	 * @param fullClassName full class name of the object to be validated
	 * @param propertyName property name of the object
	 */
	public ValidationMandatoryFieldKey(String sourceId, String fullClassName, String propertyName) {
		this.sourceId = sourceId;
		this.fullClassName = fullClassName;
		this.propertyName = propertyName;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullClassName == null) ? 0 : fullClassName.hashCode());
		result = prime * result + ((propertyName == null) ? 0 : propertyName.hashCode());
		result = prime * result + ((sourceId == null) ? 0 : sourceId.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValidationMandatoryFieldKey other = (ValidationMandatoryFieldKey) obj;
		if (fullClassName == null) {
			if (other.fullClassName != null) {
				return false;
			}
		}
		else if (!fullClassName.equals(other.fullClassName)) {
			return false;
		}
		if (propertyName == null) {
			if (other.propertyName != null) {
				return false;
			}
		}
		else if (!propertyName.equals(other.propertyName)) {
			return false;
		}
		if (sourceId == null) {
			if (other.sourceId != null) {
				return false;
			}
		}
		else if (!sourceId.equals(other.sourceId)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("MandatoryFieldKey [");
		buf.append("sourceId=");
		buf.append(sourceId);
		buf.append(", fullClassName=");
		buf.append(fullClassName);
		buf.append(", propertyName=");
		buf.append(propertyName);
		buf.append("]");
		return buf.toString();
	}

}
