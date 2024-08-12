/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/SBCollaborationTaskBusManager.java,v 1.3 2003/08/31 13:56:57 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Session bean remote interface for the services provided by the certificate
 * bus manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/31 13:56:57 $ Tag: $Name: $
 */
public interface SBCollaborationTaskBusManager extends EJBObject {
	/**
	 * To get the number of collateral task that satisfy the criteria
	 * @param aCriteria of CollateralTaskSearchCriteria type
	 * @return int - the number of Collateral task that satisfy the criteria
	 * @throws CollaborationTaskException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria) throws CollaborationTaskException,
			SearchDAOException, RemoteException;

	/**
	 * To get the number of cc task that satisfy the criteria
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return int - the number of CC task that satisfy the criteria
	 * @throws CollaborationTaskException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException, SearchDAOException,
			RemoteException;

	/**
	 * To get the list of collateral task that satisfy the criteria
	 * @param aCritieria of CollateralTaskSearchCritieria type
	 * @return CollateralTaskSearchResult[] - the list of collateral task
	 * @throws SearchDAOException, CollaborationTaskException
	 * @throws RemoteException on remote errors
	 */
	public CollateralTaskSearchResult[] getCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws CollaborationTaskException, SearchDAOException, RemoteException;

	/**
	 * To get the list of CC task that satisfy the criteria
	 * @param aCritieria of CCTaskSearchCritieria type
	 * @return CCTaskSearchResult[] - the list of CC task
	 * @throws SearchDAOException, CollaborationTaskException
	 * @throws RemoteException on remote errors
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException,
			SearchDAOException, RemoteException;

	/**
	 * Get the collateral task by the collateral task ID
	 * @param aTaskID of long type
	 * @return ICollateralTask - the sc certificate
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public ICollateralTask getCollateralTask(long aTaskID) throws CollaborationTaskException, RemoteException;

	/**
	 * Get the CC task by the CC task ID
	 * @param aTaskID of long type
	 * @return ICCTask - the sc certificate
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteExcepion on remote errors
	 */
	public ICCTask getCCTask(long aTaskID) throws CollaborationTaskException, RemoteException;

	/**
	 * Get the CC task by limit profile ID, cust category and customer ID
	 * @param aLimitProfileID of long type
	 * @param aCustCategory of String type
	 * @param aCustomerID of long type
	 * @return ICCTask - the CC Task
	 * @throws CollaborationTaskException, RemoteException
	 */
	public ICCTask getCCTask(long aLimitProfileID, String aCustCategory, long aCustomerID)
			throws CollaborationTaskException, RemoteException;

	/**
	 * Create a collateral task
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTask - the collateral task created
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICollateralTask createCollateralTask(ICollateralTask anICollateralTask) throws CollaborationTaskException,
			RemoteException;

	/**
	 * Create a CC task
	 * @param anICCTask of ICCTask type
	 * @return ICCTask - the CC task created
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCTask createCCTask(ICCTask anICCTask) throws CollaborationTaskException, RemoteException;

	/**
	 * Update a collateral task
	 * @param anICollateralTask of ICollateralTask
	 * @return ICollateralTask - the collateral task updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICollateralTask updateCollateralTask(ICollateralTask anICollateralTask) throws ConcurrentUpdateException,
			CollaborationTaskException, RemoteException;

	/**
	 * Update a CC task
	 * @param anICCTask of ICCTask
	 * @return ICCTask - the CC task updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws CollaborationTaskException on errors
	 * @throws RemoteException on remote errors
	 */
	public ICCTask updateCCTask(ICCTask anICCTask) throws ConcurrentUpdateException, CollaborationTaskException,
			RemoteException;

}
