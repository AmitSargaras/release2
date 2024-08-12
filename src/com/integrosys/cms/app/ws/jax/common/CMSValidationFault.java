/**
 * 
 */
package com.integrosys.cms.app.ws.jax.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebFault;

import com.integrosys.cms.app.ws.common.WebServiceStatusCode;
import com.integrosys.cms.app.ws.dto.ErrorDetailDTO;
import com.integrosys.cms.app.ws.dto.ValidationErrorDetailsDTO;

/**
 * @author bhushan.malankar
 * 
 */
@WebFault(name = "ErrorDetailDTO", targetNamespace = "http://www.aurionprosolutions.com/CLIMSFault/",  faultBean="com.integrosys.cms.app.ws.dto.ErrorDetailDTO")
public class CMSValidationFault extends Exception {

	protected ErrorDetailDTO faultInfo;

	public CMSValidationFault(String message, ErrorDetailDTO faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
		System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace", "false");
	}

	public CMSValidationFault(String message, ErrorDetailDTO faultInfo,
			Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}
	
	public CMSValidationFault(String field,String message) {
		super(message);
		ErrorDetailDTO dto = new ErrorDetailDTO();
		dto.setErrorCode(WebServiceStatusCode.VALIDATION_ERROR.name());
		dto.setErrorDescription("Found problem in validating request");
		ValidationErrorDetailsDTO errDto = new ValidationErrorDetailsDTO();
		errDto.setField(field);
		errDto.setValidationMessage(message);
		List<ValidationErrorDetailsDTO> list = new ArrayList<ValidationErrorDetailsDTO>();
		list.add(errDto);
		dto.setValidationErrorDetails(list);
		this.faultInfo = dto;
	}

	public ErrorDetailDTO getFaultInfo() {
		return faultInfo;
	}

}
