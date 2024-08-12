/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBWaiverRequestHome.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the waiver request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */

public interface EBWaiverRequestHome extends EJBHome {
	/**
	 * Create a waiver request
	 * @param anIWaiverRequest of IWaiverRequest type
	 * @return EBWaiverRequest - the remote handler for the waiver request
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBWaiverRequest create(IWaiverRequest anIWaiverRequest) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the request ID
	 * @param aPK of Long type
	 * @return EBWaiverRequest - the remote handler for the waiver request that
	 *         has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBWaiverRequest findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * To get the number of waiver request that satisfy the criteria
	 * @param aCriteria of WaiverRequestSearchCriteria type
	 * @return int - the number of waiver request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws SearchDAOException, RemoteException;
}