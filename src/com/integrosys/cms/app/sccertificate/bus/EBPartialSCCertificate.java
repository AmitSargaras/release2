/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBPartialSCCertificate.java,v 1.1 2003/08/11 12:49:32 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the partial sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:49:32 $ Tag: $Name: $
 */
public interface EBPartialSCCertificate extends EJBObject {
	/**
	 * Retrieve an instance of a partial sc certificate
	 * @return IParitalSCCertificate - the object encapsulating the sc
	 *         certificate info
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPartialSCCertificate getValue() throws SCCertificateException, RemoteException;

	/**
	 * Set the partial scc object
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @throws SCCertificateException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(IPartialSCCertificate anIPartialSCCertificate) throws SCCertificateException,
			ConcurrentUpdateException, RemoteException;

	/**
	 * Create the child items that are under this scc
	 * @param anIPartialSCCertificate - IPartialSCCertificate
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createPartialSCCertificateItems(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException, RemoteException;
}