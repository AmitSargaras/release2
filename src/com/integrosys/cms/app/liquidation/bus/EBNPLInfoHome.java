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
 * Remote home interface for EBNPLInfo.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBNPLInfoHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param nPLInfo of type INPLInfo
	 * @return NPL Info ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBNPLInfo create(INPLInfo nPLInfo) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the NPL Info ID.
	 * 
	 * @param nPLInfoID NPL Info ID
	 * @return local NPLInfo subtype ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBNPLInfo findByPrimaryKey(Long nPLInfoID) throws FinderException, RemoteException;

	/**
	 * Find NPL Info its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBNPLInfo</code>s
	 * @throws javax.ejb.FinderException on error finding the NPL Info
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	// public Collection findByGroupID (long groupID) throws FinderException,
	// RemoteException;
}