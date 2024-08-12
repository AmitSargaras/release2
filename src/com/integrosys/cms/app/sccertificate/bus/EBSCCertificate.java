/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBSCCertificate.java,v 1.1 2003/08/06 05:28:54 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the sc certificate entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 05:28:54 $ Tag: $Name: $
 */
public interface EBSCCertificate extends EJBObject {
	/**
	 * Retrieve an instance of a sc certificate
	 * @return ISCCertificate - the object encapsulating the sc certificate info
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public ISCCertificate getValue() throws SCCertificateException, RemoteException;

	/**
	 * Set the scc object
	 * @param anISCCertificate - ISCCertificate
	 * @throws SCCertificateException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ISCCertificate anISCCertificate) throws SCCertificateException, ConcurrentUpdateException,
			RemoteException;

	/**
	 * Create the child items that are under this scc
	 * @param anISCCertificate - ISCCertificate
	 * @throws SCCertificateException on errors
	 * @throws RemoteException on remote errors
	 */
	public void createSCCertificateItems(ISCCertificate anISCCertificate) throws SCCertificateException,
			RemoteException;
}