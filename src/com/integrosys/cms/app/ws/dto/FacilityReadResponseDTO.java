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
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FacilityResponseInfo")
public class FacilityReadResponseDTO extends ResponseDTO {
	
	@XmlElement(name = "facilityID",required=true)
	private String facilityID;
	
	@XmlElement(name = "utilizedAmount",required=true)
	private String utilizedAmount;

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public String getUtilizedAmount() {
		return utilizedAmount;
	}

	public void setUtilizedAmount(String utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}

	
	
}