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
@XmlRootElement(name = "DocumentsRequest")
public class DocumentsRequestDTO extends RequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	@XmlElement(name = "partyId",required=true)
	private String partyId;

	@XmlElement(name = "documentList",required=true)
	private List<DocumentsDetailRequestDTO> documentList;

	public List<DocumentsDetailRequestDTO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentsDetailRequestDTO> documentList) {
		this.documentList = documentList;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

}