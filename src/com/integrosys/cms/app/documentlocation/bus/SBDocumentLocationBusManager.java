/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/SBDocumentLocationBusManager.java,v 1.3 2004/04/06 09:22:39 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Session bean remote interface for the services provided by the documentation
 * location bus manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:39 $ Tag: $Name: $
 */
public interface SBDocumentLocationBusManager extends EJBObject {
	/**
	 * To get the number of cc documentation location that satisfy the criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return int - the number of cc documentation location that satisfy the
	 *         criteria
	 * @throws DocumentLocationException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria) throws DocumentLocationException,
			SearchDAOException, RemoteException;

	/**
	 * To get the list of cc documentation location that satisfy the criteria
	 * @param aCritieria of CCDocumentLocationSearchCritieria type
	 * @return DocumentLocationSearchResult[] - the list of cc documentation
	 *         location
	 * @throws SearchDAOException, DocumentLocationException
	 * @throws RemoteException on remote errors
	 */
	public CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException, SearchDAOException, RemoteException;

	/**
	 * Get the CC documentation location by the CC documentation location ID
	 * @param aDocumentLocationID of long type
	 * @return ICCDocumentLocation - the cc documentation location
	 * @throws DocumentLocationException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public ICCDocumentLocation getCCDocumentLocation(long aDocumentLocationID) throws DocumentLocationException,
			RemoteException;

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws DocumentLocationException on error
	 * @throws RemoteException on remote errors
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws DocumentLocationException, RemoteException;

	/**
	 * Create a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation type
	 * @return ICCDocumentLocation - the CC documentation location created
	 * @throws DocumentLocationException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCDocumentLocation createCCDocumentLocation(ICCDocumentLocation anICCDocumentLocation)
			throws DocumentLocationException, RemoteException;

	/**
	 * Update a CC documentation location
	 * @param anICCDocumentLocation of ICCDocumentLocation
	 * @return ICCDocumentLocation - the CC documentation location updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws DocumentLocationException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCDocumentLocation updateCCDocumentLocation(ICCDocumentLocation anICCDocumentLocation)
			throws ConcurrentUpdateException, DocumentLocationException, RemoteException;
}
