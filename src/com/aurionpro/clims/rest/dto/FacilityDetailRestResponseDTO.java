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

import com.integrosys.cms.app.ws.dto.ResponseDTO;
import com.integrosys.cms.app.ws.dto.SecurityDetailResponseDTO;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class FacilityDetailRestResponseDTO  extends ResponseDTO {
	
	
	private String facilityId;
	
	
	private List<SecurityDetailRestResponseDTO> securityResponseList;
	
	private List<FacilitySCODRestResponseDTO> scodResponseList;

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public List<SecurityDetailRestResponseDTO> getSecurityResponseList() {
		return securityResponseList;
	}

	public void setSecurityResponseList(
			List<SecurityDetailRestResponseDTO> securityResponseList) {
		this.securityResponseList = securityResponseList;
	}

	public List<FacilitySCODRestResponseDTO> getScodResponseList() {
		return scodResponseList;
	}

	public void setScodResponseList(List<FacilitySCODRestResponseDTO> scodResponseList) {
		this.scodResponseList = scodResponseList;
	}
	
	
	
}