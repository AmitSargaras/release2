/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCCTaskHome.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the CC collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */

public interface EBCCTaskHome extends EJBHome {
	/**
	 * Create a CC collaboration task
	 * @param anICCTask of ICCTask
	 * @return EBCCTask - the remote handler for the created CC task
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCCTask create(ICCTask anICCTask) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the CC task ID
	 * @param aPK - Long
	 * @return EBCCTask - the remote handler for the CC task that has the PK as
	 *         specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCCTask findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by Limit Profile ID and Sub Profile ID
	 * @param aLimitProfileID of Long type
	 * @param aCustomerID of Long type
	 * @return Collection - the list of remote handlers for the CC task
	 * @throws FinderException, RemoteException
	 */
	public Collection findByLimitProfileAndSubProfile(Long aLimitProfileID, Long aCustomerID) throws FinderException,
			RemoteException;

	/**
	 * Find by Limit Profile and Pledgor ID
	 * @param aLiimtProfileID of Long type
	 * @param aPledgorID of Long type
	 * @return Collection - the list of remote handlers for the CC task
	 * @throws FinderException, RemoteException
	 */
	public Collection findByLimitProfileAndPledgorID(Long aLimitProfileID, Long aPledgorID) throws FinderException,
			RemoteException;

	/**
	 * To get the number of CC task that satisfy the criteria
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return int - the number of CC task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

	/**
	 * To get the list of CC task that satisfy the criteria
	 * @param aCritieria of CCTaskSearchCritieria type
	 * @return CCTaskSearchResult[] - the list of CC task
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

}