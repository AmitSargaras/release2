/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequest.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the deferral request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBDeferralRequest extends EJBObject {
	/**
	 * Retrieve an instance of a deferral request
	 * @return IDeferralRequest - the object encapsulating the deferral request
	 *         info
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDeferralRequest getValue() throws GenerateRequestException, RemoteException;

	/**
	 * Set the deferral request object
	 * @param anIDeferralRequest - IDeferralRequest
	 * @throws GenerateRequestException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(IDeferralRequest anIDeferralRequest) throws GenerateRequestException,
			ConcurrentUpdateException, RemoteException;

	/**
	 * Create the child items that are under this deferral request
	 * @param anIDeferralRequest - IDeferralRequest
	 * @throws GenerateRequestException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createDeferralRequestItems(IDeferralRequest anIDeferralRequest) throws GenerateRequestException,
			RemoteException;
}