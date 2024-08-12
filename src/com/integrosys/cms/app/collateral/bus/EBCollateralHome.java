/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralHome.java,v 1.4 2003/08/11 13:21:15 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * Defines collateral create and finder methods for clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/11 13:21:15 $ Tag: $Name: $
 */
public interface EBCollateralHome extends EJBHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return collateral ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateral create(ICollateral collateral) throws CreateException, RemoteException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param collateralID collateral id
	 * @return collateral ejb object
	 * @throws FinderException on error finding the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateral findByPrimaryKey(Long collateralID) throws FinderException, RemoteException;

	/**
	 * Find collateral by SCI security id.
	 * 
	 * @param sciSecurityID SCI security id
	 * @return collateral
	 * @throws FinderException on error finding the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateral findBySCISecurityID(String sciSecurityID) throws FinderException, RemoteException;

	/**
	 * Search collateral given the criteria.
	 * 
	 * @param criteria collateral search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public SearchResult searchCollateral(CollateralSearchCriteria criteria) throws SearchDAOException, RemoteException;
}
