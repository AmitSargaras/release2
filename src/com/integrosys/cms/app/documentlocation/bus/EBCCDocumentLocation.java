/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/EBCCDocumentLocation.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the CC documentaton location entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public interface EBCCDocumentLocation extends EJBObject {
	/**
	 * Retrieve an instance of a CC documentation location
	 * @return ICCDocumentLocation - the object encapsulating the cc certificate
	 *         info
	 * @throws DocumentLocationException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCDocumentLocation getValue() throws DocumentLocationException, RemoteException;

	/**
	 * Set the cc certificate object
	 * @param anICCCertificate - ICCCertificate
	 * @throws DocumentLocationException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICCDocumentLocation anICCDocumentLocation) throws DocumentLocationException,
			ConcurrentUpdateException, RemoteException;
}