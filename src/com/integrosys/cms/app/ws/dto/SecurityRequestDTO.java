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
 * Describe this class. Purpose: To set getter and setter method for the value needed
 * For Security
 * 
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:08-SEPT-2014 $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SecurityRequest")
public class SecurityRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266735L;
	
	@XmlElement(name = "sourceSecurityId",required=true)
	private String sourceSecurityId;
	
	@XmlElement(name = "securitySubType",required=true)
	private String securitySubType;

	public String getSourceSecurityId() {
		return sourceSecurityId;
	}
	public void setSourceSecurityId(String sourceSecurityId) {
		this.sourceSecurityId = sourceSecurityId;
	}
	public String getSecuritySubType() {
		return securitySubType;
	}
	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

}