package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 9, 2010
 * Time: 11:32:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDocumentItemLocal extends EJBLocalObject {
	/**
	 * Return the document item ID of the document item
	 * @return long - the document item ID
	 * @throws RemoteException on remote errors
	 */
	public long getItemID() ;

	/**
	 * Return an object representation of the document item information.
	 * @return IDocumentItem - the document item
	 * @throws RemoteException on remote errors
	 */
	public IDocumentItem getValue() throws CheckListTemplateException;

	/**
	 * Persist a document item information
	 * @param anIDocumentItem - IDocumentItem
	 * @throws ConcurrentUpdateException if enctr concurrent update
	 * @throws RemoteException on remote errors
	 */
	public void setValue(IDocumentItem anIDocumentItem) throws CheckListTemplateException,ConcurrentUpdateException;

	public void createDocumentAppItem(IDocumentItem anIDocumentItem) throws CheckListTemplateException;
}