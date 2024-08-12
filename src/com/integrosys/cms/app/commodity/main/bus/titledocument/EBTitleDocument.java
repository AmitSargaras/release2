/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/EBTitleDocument.java,v 1.3 2004/07/22 10:01:19 lyng Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Defines title document type home methods for clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/22 10:01:19 $ Tag: $Name: $
 */
public interface EBTitleDocument extends EJBObject {
	/**
	 * Retrieve an instance of title document type.
	 * 
	 * @return title document business object
	 * @throws RemoteException on error during remote method call
	 */
	public ITitleDocument getValue() throws RemoteException;

	/**
	 * Set the title document type object.
	 * 
	 * @param value of type ITitleDocument
	 * @throws ConcurrentUpdateException if more than one client accessing this
	 *         title document type at the same time
	 * @throws RemoteException on error during remote method call
	 */
	public void setValue(ITitleDocument value) throws ConcurrentUpdateException, RemoteException;

	/**
	 * To soft delete the title document type.
	 * 
	 * @throws RemoteException on error during remote method call
	 */
	public void softDelete() throws RemoteException;
}