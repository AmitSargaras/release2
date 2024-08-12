/**
 * 
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bhushan.malankar
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "ValidationErrorDetailsDTO", propOrder = {"field", "validationMessage","referenceId"})
@XmlRootElement(name = "ValidationErrorDetail" , namespace ="http://www.aurionprosolutions.com/CLIMSFault/")

public class ValidationErrorDetailsDTO {
	
	public ValidationErrorDetailsDTO() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlElement(name = "Field")
	protected String field;
	
	@XmlElement(name = "ValidationMessage")
	protected String validationMessage;
	
	@XmlElement(name = "ReferenceId")
	protected String referenceId;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}
