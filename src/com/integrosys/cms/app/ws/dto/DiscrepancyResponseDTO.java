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
@XmlRootElement(name = "DiscrepancyResponseInfo")
public class DiscrepancyResponseDTO extends ResponseDTO {
	
	@XmlElement(name = "discrepancyResponseList",required=true)
	private List<DiscrepancyDetailResponseDTO> discrepancyResponseList;

	public List<DiscrepancyDetailResponseDTO> getDiscrepancyResponseList() {
		return discrepancyResponseList;
	}

	public void setDiscrepancyResponseList(
			List<DiscrepancyDetailResponseDTO> discrepancyResponseList) {
		this.discrepancyResponseList = discrepancyResponseList;
	}
	
	
	
	
}