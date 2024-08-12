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
@XmlRootElement(name = "DocumentsResponseInfo")
public class DocumentsResponseDTO extends ResponseDTO {
	
	@XmlElement(name = "documentResponseList")
	private List<DocumentsDetailResponseDTO> documentResponseList;

	public List<DocumentsDetailResponseDTO> getDocumentResponseList() {
		return documentResponseList;
	}

	public void setDocumentResponseList(
			List<DocumentsDetailResponseDTO> documentResponseList) {
		this.documentResponseList = documentResponseList;
	}
	
}