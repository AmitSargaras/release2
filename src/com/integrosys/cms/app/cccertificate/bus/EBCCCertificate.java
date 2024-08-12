/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificate.java,v 1.2 2004/01/13 06:22:02 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/01/13 06:22:02 $ Tag: $Name: $
 */
public interface EBCCCertificate extends EJBObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return ICCCertificate - the object encapsulating the cc certificate info
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCCertificate getValue() throws CCCertificateException, RemoteException;

	/**
	 * Set the cc certificate object
	 * @param anICCCertificate - ICCCertificate
	 * @throws CCCertificateException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICCCertificate anICCCertificate) throws CCCertificateException, ConcurrentUpdateException,
			RemoteException;

	/**
	 * Create the child items that are under this ccc
	 * @param anICCCertificate - ICCCertificate
	 * @throws CCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createCCCertificateItems(ICCCertificate anICCCertificate) throws CCCertificateException,
			RemoteException;
}