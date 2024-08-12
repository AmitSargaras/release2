/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplate.java,v 1.3 2003/07/02 01:18:22 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;


/**
 * Remote interface for the template entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/02 01:18:22 $ Tag: $Name: $
 */
public interface EBTemplate extends EJBObject {
	/**
	 * Retrieve an instance of a template
	 * @return ITemplate - the object encapsulating the template info
	 * @throws RemoteException
	 */
	public ITemplate getValue() throws CheckListTemplateException, RemoteException;

	/**
	 * Set the template object
	 * @param anITemplate - ITemplate
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ITemplate anITemplate) throws CheckListTemplateException, ConcurrentUpdateException, RemoteException;

	/**
	 * Create the child items that are under this template
	 * @param anITemplate - ITemplate
	 * @throws CheckListTemplateException
	 * @throws RemoteException
	 */
	public void createTemplateItems(ITemplate anITemplate) throws CheckListTemplateException, RemoteException;

}