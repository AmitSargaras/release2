/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBDocumentItem.java,v 1.1 2003/06/27 10:36:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;

/**
 * This is the interface to the EBDocumentItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:36:23 $ Tag: $Name: $
 */
public interface EBDocumentItem extends EJBObject {
	/**
	 * Return the document item ID of the document item
	 * @return long - the document item ID
	 * @throws RemoteException on remote errors
	 */
	public long getItemID() throws RemoteException;

	/**
	 * Return an object representation of the document item information.
	 * @return IDocumentItem - the document item
	 * @throws RemoteException on remote errors
	 */
	public IDocumentItem getValue() throws CheckListTemplateException, RemoteException;

	/**
	 * Persist a document item information
	 * @param anIDocumentItem - IDocumentItem
	 * @throws ConcurrentUpdateException if enctr concurrent update
	 * @throws RemoteException on remote errors
	 */
	public void setValue(IDocumentItem anIDocumentItem) throws CheckListTemplateException,ConcurrentUpdateException, RemoteException;
	
	public void createDocumentAppItem(IDocumentItem anIDocumentItem) throws CheckListTemplateException, RemoteException;
}