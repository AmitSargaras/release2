/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/DocumentSearchCriteria.java,v 1.1 2003/06/27 10:36:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:36:23 $ Tag: $Name: $
 */
public class DocumentSearchCriteria extends SearchCriteria {
	private String documentType = null;

	/**
	 * Default Constructor
	 */
	public DocumentSearchCriteria() {
	}

	/**
	 * Getter Methods
	 */
	public String getDocumentType() {
		return this.documentType;
	}

	/**
	 * Setter Methods
	 */
	public void setDocumentType(String aDocumentType) {
		this.documentType = aDocumentType;
	}
}
