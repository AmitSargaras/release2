/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Remote home interface for EBRecovery.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBRecoveryHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param recovery of type IRecovery
	 * @return EBRecovery ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBRecovery create(IRecovery recovery) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the recoveryID.
	 * 
	 * @param recoveryID
	 * @return local Recovery subtype ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBRecovery findByPrimaryKey(Long recoveryID) throws FinderException, RemoteException;

	/**
	 * Find Recovery by liquidationID
	 * 
	 * @param liquidationID
	 * @return a collection of <code>EBRecovery</code>s
	 * @throws javax.ejb.FinderException on error finding the Recovery
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	// public Collection findByLiquidationID (long liquidationID) throws
	// FinderException, RemoteException;
}