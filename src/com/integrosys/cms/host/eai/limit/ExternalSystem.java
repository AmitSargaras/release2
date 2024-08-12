package com.integrosys.cms.host.eai.limit;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class ExternalSystem implements java.io.Serializable {

	private String externalSystemTypeNumber;

	private String externalSystemTypeValue;

	private String externalSystemTypeDescription;

	public String getExternalSystemTypeNumber() {
		return externalSystemTypeNumber;
	}

	public void setExternalSystemTypeNumber(String externalSystemTypeNumber) {
		this.externalSystemTypeNumber = externalSystemTypeNumber;
	}

	public String getExternalSystemTypeValue() {
		return externalSystemTypeValue;
	}

	public void setExternalSystemTypeValue(String externalSystemTypeValue) {
		this.externalSystemTypeValue = externalSystemTypeValue;
	}

	public String getExternalSystemTypeDescription() {
		return externalSystemTypeDescription;
	}

	public void setExternalSystemTypeDescription(String externalSystemTypeDescription) {
		this.externalSystemTypeDescription = externalSystemTypeDescription;
	}

}
