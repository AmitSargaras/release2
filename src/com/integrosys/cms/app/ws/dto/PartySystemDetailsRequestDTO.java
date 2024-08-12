/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * Describe this class. Purpose: To set getter and setter method for the value needed
 * by Party Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Ankit
 * @version $Revision:$
 * @since $Date:05-AUG-2014 $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "PartySystemDetailsRequestDTO", propOrder = {"system", "systemId"})
//@XmlRootElement(name = "PartyDetails")

public class PartySystemDetailsRequestDTO {
	
	private static final long serialVersionUID = -114309476199266725L;
	
	@XmlElement(name = "system",required=true)
	private String system;
	@XmlElement(name = "systemId",required=true)
	private String systemId;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}