/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IDocumentBusManager.java,v 1.3 2003/07/01 07:27:00 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;


/**
 * This interface defines the biz services that is available for document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/01 07:27:00 $ Tag: $Name: $
 */
public interface IDocumentBusManager extends Serializable {

	/**
	 * Create a document item
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentIte, - the document item being updated
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
	public IDocumentItem create(IDocumentItem anIDocumentItem) throws CheckListTemplateException;

	/**
	 * Update a document item
	 * @param anIDocumentItem - IDocumentItem
	 * @return IDocumentIte, - the document item being updated
	 * @throws CheckListTemplateException
	 */
	public IDocumentItem update(IDocumentItem anIDocumentItem) throws CheckListTemplateException, ConcurrentUpdateException;

	/**
	 * Get a document item by ID
	 * @param aDocumentItemID - long
	 * @return IDocument - the document
	 * @throws CheckListTemplateException
	 */
	public IDocumentItem getDocumentItemByID(long aDocumentItemID) throws CheckListTemplateException;
}
