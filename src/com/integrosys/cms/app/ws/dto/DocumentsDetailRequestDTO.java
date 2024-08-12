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
@XmlRootElement(name = "DocumentsDetailInfo")
public class DocumentsDetailRequestDTO {
	
	private static final long serialVersionUID = -114309476199266724L;
	
	@XmlElement(name = "documentCode",required=true)
	private String documentCode;

	@XmlElement(name = "documentCategory",required=true)
	private String documentCategory;
	
	@XmlElement(name = "mappingId",required=true)
	private String mappingId;
	
	@XmlElement(name = "cpsDocumentId",required=true)
	private String cpsDocumentId;

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getCpsDocumentId() {
		return cpsDocumentId;
	}

	public void setCpsDocumentId(String cpsDocumentId) {
		this.cpsDocumentId = cpsDocumentId;
	}
}