/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCollateralTaskHome.java,v 1.2 2003/08/22 03:31:05 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the collateral collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 03:31:05 $ Tag: $Name: $
 */

public interface EBCollateralTaskHome extends EJBHome {
	/**
	 * Create a collateral collaboration task
	 * @param anICollateralTask of ICollateralTask
	 * @return EBCollateralTask - the remote handler for the created collateral
	 *         task
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCollateralTask create(ICollateralTask anICollateralTask) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the collateral task ID
	 * @param aPK - Long
	 * @return EBCollateralTask - the remote handler for the collateral task
	 *         that has the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBCollateralTask findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * To get the number of collateral task that satisfy the criteria
	 * @param aCriteria of CollateralTaskSearchCriteria type
	 * @return int - the number of collateral task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

	/**
	 * To get the list of collateral task that satisfy the criteria
	 * @param aCritieria of CollateralTaskSearchCritieria type
	 * @return CollateralTaskSearchResult[] - the list of collateral task
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public CollateralTaskSearchResult[] getCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws SearchDAOException, RemoteException;

}