/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */
@XmlRootElement(name = "ErrorDetail" , namespace ="http://www.aurionprosolutions.com/CLIMSFault/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorDetailDTO", propOrder={"errorCode", "errorDescription","ValidationErrorDetails"})
	
public class ErrorDetailDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	
	@XmlElement(name = "ErrorCode")
	private String errorCode;
	
	@XmlElement(name = "ErrorDescription")
	private String errorDescription;
	
	@XmlElement(name = "ValidationErrorDetails")
	private List<ValidationErrorDetailsDTO> ValidationErrorDetails;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public List<ValidationErrorDetailsDTO> getValidationErrorDetails() {
		return ValidationErrorDetails;
	}

	public void setValidationErrorDetails(
			List<ValidationErrorDetailsDTO> validationErrorDetails) {
		ValidationErrorDetails = validationErrorDetails;
	}
	
	@Override
	public String toString() {
		StringBuffer errorMessageBuffer= new StringBuffer();;
		errorMessageBuffer.append(getErrorCode() + ":"+getErrorDescription()) ;
		List<ValidationErrorDetailsDTO> validationErrorDetails2 = getValidationErrorDetails();
		for (ValidationErrorDetailsDTO validationErrorDetailsDTO : validationErrorDetails2) {
			errorMessageBuffer.append("::"+validationErrorDetailsDTO.getField()+":"+validationErrorDetailsDTO.getValidationMessage());
		}
			String errorMessage = errorMessageBuffer.toString();
			if(errorMessage.length()>4000) {
				return errorMessage.substring(0,3999);
			}else{
				return errorMessage;
			}
		}
	
}