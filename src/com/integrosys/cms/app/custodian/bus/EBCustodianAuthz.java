/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Remote interface for the Memo EntityBean
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/17 01:46:28 $ Tag: $Name: $
 */
public interface EBCustodianAuthz extends EJBObject {
	/**
	 * Retrieve an instance of a custodian document
	 * @return ICustodianDoc - the object encapsulating the custodian doc info
	 * @throws RemoteException
	 */
	public ICustAuthorize getValue() throws RemoteException;

	/**
	 * Set the custodian doc object
	 * @param anICustAuthorize - ICustAuthorize
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws RemoteException
	 */
	public void setValue(ICustAuthorize anICustAuthorize) throws ConcurrentUpdateException, RemoteException;
}