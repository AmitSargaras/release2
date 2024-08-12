package com.integrosys.cms.host.eai;

import java.io.Serializable;

/**
 * <p>
 * Common Code reprensentation. Given a Category and Entry code for a
 * StandardCode instance, we can know what's the description should be shown on
 * the screen.
 * <p>
 * Must consist minimum information of <b>Category Code</b> and <b>Entry
 * Code</b> to get the description to be shown correctly.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class StandardCode implements Serializable {

	private static final long serialVersionUID = -8697230816010986672L;

	private String standardCodeNumber;

	private String standardCodeValue;

	private String standardCodeDescription;

	/**
	 * Default constructor accept no value
	 */
	public StandardCode() {
	}

	/**
	 * Constructor to provide category code and entry code
	 * @param codeNumber category code
	 * @param codeValue entry code
	 */
	public StandardCode(String codeNumber, String codeValue) {
		this(codeNumber, codeValue, null);
	}

	/**
	 * Constructor to provide category code, entry code and entry description
	 * @param codeNumber category code
	 * @param codeValue entry code
	 * @param codeDescription entry description
	 */
	public StandardCode(String codeNumber, String codeValue, String codeDescription) {
		this.standardCodeNumber = codeNumber;
		this.standardCodeValue = codeValue;
		this.standardCodeDescription = codeDescription;
	}

	public String getStandardCodeNumber() {
		return standardCodeNumber;
	}

	public void setStandardCodeNumber(String standardCodeNumber) {
		this.standardCodeNumber = standardCodeNumber;
	}

	public String getStandardCodeValue() {
		return standardCodeValue;
	}

	public void setStandardCodeValue(String standardCodeValue) {
		if (standardCodeValue != null) {
			this.standardCodeValue = standardCodeValue.trim();
		}
	}

	public String getStandardCodeDescription() {
		return standardCodeDescription;
	}

	public void setStandardCodeDescription(String standardCodeDescription) {
		this.standardCodeDescription = standardCodeDescription;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((standardCodeNumber == null) ? 0 : standardCodeNumber.hashCode());
		result = prime * result + ((standardCodeValue == null) ? 0 : standardCodeValue.hashCode());

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

		StandardCode other = (StandardCode) obj;
		if (standardCodeNumber == null) {
			if (other.standardCodeNumber != null) {
				return false;
			}
		}
		else if (!standardCodeNumber.equals(other.standardCodeNumber)) {
			return false;
		}

		if (standardCodeValue == null) {
			if (other.standardCodeValue != null) {
				return false;
			}
		}
		else if (!standardCodeValue.equals(other.standardCodeValue)) {
			return false;
		}

		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Standard Code [");
		buf.append("Category Code=");
		buf.append(standardCodeNumber);
		buf.append(", Entry Code=");
		buf.append(standardCodeValue);
		if (standardCodeDescription != null && standardCodeDescription.trim().length() > 0) {
			buf.append(", Entry Description=");
			buf.append(standardCodeDescription);
		}
		buf.append("]");
		return buf.toString();
	}
}