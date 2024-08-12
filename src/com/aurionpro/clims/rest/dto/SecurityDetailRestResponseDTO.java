/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class SecurityDetailRestResponseDTO {
	
	
	private String cpsSecurityId;
	
	private String uniqueReqId;
	
	private String securityId;


	public String getCpsSecurityId() {
		return cpsSecurityId;
	}


	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}


	public String getSecurityId() {
		return securityId;
	}


	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}


	public String getUniqueReqId() {
		return uniqueReqId;
	}


	public void setUniqueReqId(String uniqueReqId) {
		this.uniqueReqId = uniqueReqId;
	}

	
	
	
	
		
	
}