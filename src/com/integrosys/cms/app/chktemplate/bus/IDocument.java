/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IDocument.java,v 1.2 2003/06/24 11:35:09 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This interface defines the list of attributes that will be available to a
 * document (global)
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/24 11:35:09 $ Tag: $Name: $
 */
public interface IDocument extends IValueObject, Serializable {
	/**
	 * Get the document ID
	 * @return long - the document ID
	 */
	public long getDocumentID();

	/**
	 * Get the document Type
	 * @return String - the document type
	 */
	public String getDocumentType();

	/**
	 * Get the document item list
	 * @return IDocumentItem[] - the list of document items
	 */
	public IDocumentItem[] getDocumentItemList();

	/**
	 * Set the document ID
	 * @param aDocumentID - long
	 */
	public void setDocumentID(long aDocumentID);

	/**
	 * Set the document type
	 * @param aDocumentType - String
	 */
	public void setDocumentType(String aDocumentType);

	/**
	 * Set the document item list
	 * @param anIDocumentItemList - IDocumentItem[]
	 */
	public void setDocumentItemList(IDocumentItem[] anIDocumentItemList);
}
