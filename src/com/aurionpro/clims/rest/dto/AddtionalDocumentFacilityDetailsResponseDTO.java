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


public class AddtionalDocumentFacilityDetailsResponseDTO {
	
	private String addDocFacDetID;
	
	private String uniqueAddDocFacDetID;

	public String getAddDocFacDetID() {
		return addDocFacDetID;
	}

	public void setAddDocFacDetID(String addDocFacDetID) {
		this.addDocFacDetID = addDocFacDetID;
	}

	public String getUniqueAddDocFacDetID() {
		return uniqueAddDocFacDetID;
	}

	public void setUniqueAddDocFacDetID(String uniqueAddDocFacDetID) {
		this.uniqueAddDocFacDetID = uniqueAddDocFacDetID;
	}

	
	
}