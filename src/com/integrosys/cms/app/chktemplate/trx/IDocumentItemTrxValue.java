/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/IDocumentItemTrxValue.java,v 1.1 2003/06/27 10:44:38 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a Document trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:44:38 $ Tag: $Name: $
 */
public interface IDocumentItemTrxValue extends ICMSTrxValue {
	/**
	 * Get the document item business entity
	 * @return IDocumentItem - the document item
	 */
	public IDocumentItem getDocumentItem();

	/**
	 * Get the staging document item business entity
	 * @return IDocumentItem - the staging document item
	 */
	public IDocumentItem getStagingDocumentItem();

	/**
	 * Set the document item business entity
	 * @param anIDocumentItem - IDocumentItem
	 */
	public void setDocumentItem(IDocumentItem anIDocumentItem);

	/**
	 * Set the staging document item business entity
	 * @param anIDocumentItem - IDocumentItem
	 */
	public void setStagingDocumentItem(IDocumentItem anIDocumentItem);
}