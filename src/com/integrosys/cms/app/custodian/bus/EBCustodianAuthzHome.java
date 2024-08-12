/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Home interface for the custodian memo entity bean
 * 
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/17 01:46:28 $ Tag: $Name: $
 */

public interface EBCustodianAuthzHome extends EJBHome {
	/**
	 * Create a custodian doc
	 * @param anICustAuthorize - ICustAuthorize
	 * @return EBCustodianAuthz - the remote handler for the created custodian
	 *         doc
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCustodianAuthz create(ICustAuthorize anICustAuthorize) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the custodian doc ID
	 * @param aPK - Long
	 * @return EBCustodianAuthz - the remote handler for the custodian doc that
	 *         has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public EBCustodianAuthz findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

}