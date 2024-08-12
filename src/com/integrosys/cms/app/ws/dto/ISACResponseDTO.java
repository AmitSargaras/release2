package com.integrosys.cms.app.ws.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 * @author $Author: Bhushan$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ISACResponseInfo")
public class ISACResponseDTO extends ResponseDTO {
	
	@XmlElement(name = "refNumber",required=true)
	private String refNumber;
	
	@XmlElement(name = "ErrorCode",required=true)
	private String errorCode;
	
	@XmlElement(name = "ErrorMessage",required=true)
	private String errorMessage;

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
	
	

	
	
}