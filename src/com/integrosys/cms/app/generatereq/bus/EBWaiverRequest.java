/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBWaiverRequest.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the waiver request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBWaiverRequest extends EJBObject {
	/**
	 * Retrieve an instance of a waiver request
	 * @return IWaiverRequest - the object encapsulating the waiver request info
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public IWaiverRequest getValue() throws GenerateRequestException, RemoteException;

	/**
	 * Set the waiver request object
	 * @param anIWaiverRequest - IWaiverRequest
	 * @throws GenerateRequestException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(IWaiverRequest anIWaiverRequest) throws GenerateRequestException, ConcurrentUpdateException,
			RemoteException;

	/**
	 * Create the child items that are under this waiver request
	 * @param anIWaiverRequest - IWaiverRequest
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createWaiverRequestItems(IWaiverRequest anIWaiverRequest) throws GenerateRequestException,
			RemoteException;
}