/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:05-Aug-2014 $ Tag: $Name$
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SecurityResponse")
public class SecurityResponseDTO extends ResponseDTO{
	
	private static final long serialVersionUID = -114309476199266736L;
	
	@XmlElement(name = "securityDetails")
	private String securityDetails;

	public String getSecurityDetails() {
		return securityDetails;
	}

	public void setSecurityDetails(String securityDetails) {
		this.securityDetails = securityDetails;
	}

	
	
}