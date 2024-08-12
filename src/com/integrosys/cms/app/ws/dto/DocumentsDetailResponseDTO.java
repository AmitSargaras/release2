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
@XmlRootElement(name = "DocumentDetailResponseInfo")
public class DocumentsDetailResponseDTO{
	
	@XmlElement(name = "cpsDocumentId")
	private String cpsDocumentId;
	
	@XmlElement(name = "documentItemId")
	private String documentItemId;

	public String getCpsDocumentId() {
		return cpsDocumentId;
	}

	public void setCpsDocumentId(String cpsDocumentId) {
		this.cpsDocumentId = cpsDocumentId;
	}

	public String getDocumentItemId() {
		return documentItemId;
	}

	public void setDocumentItemId(String documentItemId) {
		this.documentItemId = documentItemId;
	}
	
}