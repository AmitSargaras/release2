package com.integrosys.cms.host.eai.support;

/**
 * The key to retrieve the list of properties for a class which it's properties
 * required to be copied over to the existing object in persistent storage.
 * 
 * @author Chong Jun Yong
 * 
 */
public final class VariationPropertiesKey {
	private final String sourceId;

	private final String fullClassName;

	/**
	 * Default constructor to provide value for the key
	 * 
	 * @param sourceId source id
	 * @param fullClassName full class name
	 */
	public VariationPropertiesKey(String sourceId, String fullClassName) {
		this.sourceId = sourceId;
		this.fullClassName = fullClassName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("VariationPropertiesKey [");
		buf.append("Source Id=").append(sourceId);
		buf.append(", Full Class Name=").append(fullClassName).append("]");

		return buf.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((fullClassName == null) ? 0 : fullClassName.hashCode());
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

		VariationPropertiesKey other = (VariationPropertiesKey) obj;
		if (fullClassName == null) {
			if (other.fullClassName != null) {
				return false;
			}
		}
		else if (!fullClassName.equals(other.fullClassName)) {
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
}