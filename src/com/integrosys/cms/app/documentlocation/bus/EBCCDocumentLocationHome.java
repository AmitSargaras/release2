/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/EBCCDocumentLocationHome.java,v 1.3 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the CC documentation location entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */

public interface EBCCDocumentLocationHome extends EJBHome {
	/**
	 * Create a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return EBCCDocumentLocation - the remote handler for the created CC
	 *         documentation location
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCCDocumentLocation create(ICCDocumentLocation anICCDocumentLocation) throws CreateException,
			RemoteException;

	/**
	 * Find by primary Key, the CC documentation location ID
	 * @param aPK - Long
	 * @return EBCCDocumentLocation - the remote handler for the CC
	 *         documentation location that has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCCDocumentLocation findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by Limit Profile ID and Sub Profile ID
	 * @param aLimitProfileID of Long type
	 * @param aCustomerID of Long type
	 * @return Collection - the list of remote handlers for the CC documentation
	 *         location
	 * @throws FinderException, RemoteException
	 */
	public Collection findByLimitProfileAndSubProfile(Long aLimitProfileID, Long aCustomerID) throws FinderException,
			RemoteException;

	/**
	 * Find by Limit Profile and Pledgor ID
	 * @param aLiimtProfileID of Long type
	 * @param aPledgorID of Long type
	 * @return Collection - the list of remote handlers for the CC documentation
	 *         location
	 * @throws FinderException, RemoteException
	 */
	public Collection findByLimitProfileAndPledgorID(Long aLimitProfileID, Long aPledgorID) throws FinderException,
			RemoteException;

	/**
	 * To get the number of CC documentation location that satisfy the criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return int - the number of CC documentation location that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria) throws SearchDAOException,
			RemoteException;

	/**
	 * To get the list of CC documentation location that satisfy the criteria
	 * @param aCritieria of CCDocumentLocationSearchCritieria type
	 * @return CCDocumentLocationSearchResult[] - the list of CC documentation
	 *         locationk
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws SearchDAOException, RemoteException;

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws SearchDAOException on error
	 * @throws RemoteException on remote errors
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws SearchDAOException, RemoteException;

}