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
@XmlRootElement(name = "DiscrepancyDetailResponseInfo")
public class DiscrepancyDetailResponseDTO {
	
	@XmlElement(name = "cpsDiscrepancyId",required=true)
	private String cpsDiscrepancyId;
	
	@XmlElement(name = "discrepancyId",required=true)
	private String discrepancyId;

	public String getCpsDiscrepancyId() {
		return cpsDiscrepancyId;
	}

	public void setCpsDiscrepancyId(String cpsDiscrepancyId) {
		this.cpsDiscrepancyId = cpsDiscrepancyId;
	}

	public String getDiscrepancyId() {
		return discrepancyId;
	}

	public void setDiscrepancyId(String discrepancyId) {
		this.discrepancyId = discrepancyId;
	}

	
	
	
		
	
}